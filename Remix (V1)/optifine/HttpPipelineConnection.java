package optifine;

import java.util.regex.*;
import java.net.*;
import java.util.*;
import java.io.*;

public class HttpPipelineConnection
{
    public static final int TIMEOUT_CONNECT_MS = 5000;
    public static final int TIMEOUT_READ_MS = 5000;
    private static final String LF = "\n";
    private static final Pattern patternFullUrl;
    private String host;
    private int port;
    private Proxy proxy;
    private List<HttpPipelineRequest> listRequests;
    private List<HttpPipelineRequest> listRequestsSend;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private HttpPipelineSender httpPipelineSender;
    private HttpPipelineReceiver httpPipelineReceiver;
    private int countRequests;
    private boolean responseReceived;
    private long keepaliveTimeoutMs;
    private int keepaliveMaxCount;
    private long timeLastActivityMs;
    private boolean terminated;
    
    public HttpPipelineConnection(final String host, final int port) {
        this(host, port, Proxy.NO_PROXY);
    }
    
    public HttpPipelineConnection(final String host, final int port, final Proxy proxy) {
        this.host = null;
        this.port = 0;
        this.proxy = Proxy.NO_PROXY;
        this.listRequests = new LinkedList<HttpPipelineRequest>();
        this.listRequestsSend = new LinkedList<HttpPipelineRequest>();
        this.socket = null;
        this.inputStream = null;
        this.outputStream = null;
        this.httpPipelineSender = null;
        this.httpPipelineReceiver = null;
        this.countRequests = 0;
        this.responseReceived = false;
        this.keepaliveTimeoutMs = 5000L;
        this.keepaliveMaxCount = 1000;
        this.timeLastActivityMs = System.currentTimeMillis();
        this.terminated = false;
        this.host = host;
        this.port = port;
        this.proxy = proxy;
        (this.httpPipelineSender = new HttpPipelineSender(this)).start();
        (this.httpPipelineReceiver = new HttpPipelineReceiver(this)).start();
    }
    
    public synchronized boolean addRequest(final HttpPipelineRequest pr) {
        if (this.isClosed()) {
            return false;
        }
        this.addRequest(pr, this.listRequests);
        this.addRequest(pr, this.listRequestsSend);
        ++this.countRequests;
        return true;
    }
    
    private void addRequest(final HttpPipelineRequest pr, final List<HttpPipelineRequest> list) {
        list.add(pr);
        this.notifyAll();
    }
    
    public synchronized void setSocket(final Socket s) throws IOException {
        if (!this.terminated) {
            if (this.socket != null) {
                throw new IllegalArgumentException("Already connected");
            }
            (this.socket = s).setTcpNoDelay(true);
            this.inputStream = this.socket.getInputStream();
            this.outputStream = new BufferedOutputStream(this.socket.getOutputStream());
            this.onActivity();
            this.notifyAll();
        }
    }
    
    public synchronized OutputStream getOutputStream() throws IOException, InterruptedException {
        while (this.outputStream == null) {
            this.checkTimeout();
            this.wait(1000L);
        }
        return this.outputStream;
    }
    
    public synchronized InputStream getInputStream() throws IOException, InterruptedException {
        while (this.inputStream == null) {
            this.checkTimeout();
            this.wait(1000L);
        }
        return this.inputStream;
    }
    
    public synchronized HttpPipelineRequest getNextRequestSend() throws InterruptedException, IOException {
        if (this.listRequestsSend.size() <= 0 && this.outputStream != null) {
            this.outputStream.flush();
        }
        return this.getNextRequest(this.listRequestsSend, true);
    }
    
    public synchronized HttpPipelineRequest getNextRequestReceive() throws InterruptedException {
        return this.getNextRequest(this.listRequests, false);
    }
    
    private HttpPipelineRequest getNextRequest(final List<HttpPipelineRequest> list, final boolean remove) throws InterruptedException {
        while (list.size() <= 0) {
            this.checkTimeout();
            this.wait(1000L);
        }
        this.onActivity();
        if (remove) {
            return list.remove(0);
        }
        return list.get(0);
    }
    
    private void checkTimeout() {
        if (this.socket != null) {
            long timeoutMs = this.keepaliveTimeoutMs;
            if (this.listRequests.size() > 0) {
                timeoutMs = 5000L;
            }
            final long timeNowMs = System.currentTimeMillis();
            if (timeNowMs > this.timeLastActivityMs + timeoutMs) {
                this.terminate(new InterruptedException("Timeout " + timeoutMs));
            }
        }
    }
    
    private void onActivity() {
        this.timeLastActivityMs = System.currentTimeMillis();
    }
    
    public synchronized void onRequestSent(final HttpPipelineRequest pr) {
        if (!this.terminated) {
            this.onActivity();
        }
    }
    
    public synchronized void onResponseReceived(final HttpPipelineRequest pr, final HttpResponse resp) {
        if (!this.terminated) {
            this.responseReceived = true;
            this.onActivity();
            if (this.listRequests.size() <= 0 || this.listRequests.get(0) != pr) {
                throw new IllegalArgumentException("Response out of order: " + pr);
            }
            this.listRequests.remove(0);
            pr.setClosed(true);
            String location = resp.getHeader("Location");
            if (resp.getStatus() / 100 == 3 && location != null && pr.getHttpRequest().getRedirects() < 5) {
                try {
                    location = this.normalizeUrl(location, pr.getHttpRequest());
                    final HttpRequest listener1 = HttpPipeline.makeRequest(location, pr.getHttpRequest().getProxy());
                    listener1.setRedirects(pr.getHttpRequest().getRedirects() + 1);
                    final HttpPipelineRequest hpr2 = new HttpPipelineRequest(listener1, pr.getHttpListener());
                    HttpPipeline.addRequest(hpr2);
                }
                catch (IOException var6) {
                    pr.getHttpListener().failed(pr.getHttpRequest(), var6);
                }
            }
            else {
                final HttpListener listener2 = pr.getHttpListener();
                listener2.finished(pr.getHttpRequest(), resp);
            }
            this.checkResponseHeader(resp);
        }
    }
    
    private String normalizeUrl(final String url, final HttpRequest hr) {
        if (HttpPipelineConnection.patternFullUrl.matcher(url).matches()) {
            return url;
        }
        if (url.startsWith("//")) {
            return "http:" + url;
        }
        String server = hr.getHost();
        if (hr.getPort() != 80) {
            server = server + ":" + hr.getPort();
        }
        if (url.startsWith("/")) {
            return "http://" + server + url;
        }
        final String file = hr.getFile();
        final int pos = file.lastIndexOf("/");
        return (pos >= 0) ? ("http://" + server + file.substring(0, pos + 1) + url) : ("http://" + server + "/" + url);
    }
    
    private void checkResponseHeader(final HttpResponse resp) {
        final String connStr = resp.getHeader("Connection");
        if (connStr != null && !connStr.toLowerCase().equals("keep-alive")) {
            this.terminate(new EOFException("Connection not keep-alive"));
        }
        final String keepAliveStr = resp.getHeader("Keep-Alive");
        if (keepAliveStr != null) {
            final String[] parts = Config.tokenize(keepAliveStr, ",;");
            for (int i = 0; i < parts.length; ++i) {
                final String part = parts[i];
                final String[] tokens = this.split(part, '=');
                if (tokens.length >= 2) {
                    if (tokens[0].equals("timeout")) {
                        final int max = Config.parseInt(tokens[1], -1);
                        if (max > 0) {
                            this.keepaliveTimeoutMs = max * 1000;
                        }
                    }
                    if (tokens[0].equals("max")) {
                        final int max = Config.parseInt(tokens[1], -1);
                        if (max > 0) {
                            this.keepaliveMaxCount = max;
                        }
                    }
                }
            }
        }
    }
    
    private String[] split(final String str, final char separator) {
        final int pos = str.indexOf(separator);
        if (pos < 0) {
            return new String[] { str };
        }
        final String str2 = str.substring(0, pos);
        final String str3 = str.substring(pos + 1);
        return new String[] { str2, str3 };
    }
    
    public synchronized void onExceptionSend(final HttpPipelineRequest pr, final Exception e) {
        this.terminate(e);
    }
    
    public synchronized void onExceptionReceive(final HttpPipelineRequest pr, final Exception e) {
        this.terminate(e);
    }
    
    private synchronized void terminate(final Exception e) {
        if (!this.terminated) {
            this.terminated = true;
            this.terminateRequests(e);
            if (this.httpPipelineSender != null) {
                this.httpPipelineSender.interrupt();
            }
            if (this.httpPipelineReceiver != null) {
                this.httpPipelineReceiver.interrupt();
            }
            try {
                if (this.socket != null) {
                    this.socket.close();
                }
            }
            catch (IOException ex) {}
            this.socket = null;
            this.inputStream = null;
            this.outputStream = null;
        }
    }
    
    private void terminateRequests(final Exception e) {
        if (this.listRequests.size() > 0) {
            if (!this.responseReceived) {
                final HttpPipelineRequest pr = this.listRequests.remove(0);
                pr.getHttpListener().failed(pr.getHttpRequest(), e);
                pr.setClosed(true);
            }
            while (this.listRequests.size() > 0) {
                final HttpPipelineRequest pr = this.listRequests.remove(0);
                HttpPipeline.addRequest(pr);
            }
        }
    }
    
    public synchronized boolean isClosed() {
        return this.terminated || this.countRequests >= this.keepaliveMaxCount;
    }
    
    public int getCountRequests() {
        return this.countRequests;
    }
    
    public synchronized boolean hasActiveRequests() {
        return this.listRequests.size() > 0;
    }
    
    public String getHost() {
        return this.host;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public Proxy getProxy() {
        return this.proxy;
    }
    
    static {
        patternFullUrl = Pattern.compile("^[a-zA-Z]+://.*");
    }
}
