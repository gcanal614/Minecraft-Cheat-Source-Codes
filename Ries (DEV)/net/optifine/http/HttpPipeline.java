/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.Config;
import net.optifine.http.HttpListener;
import net.optifine.http.HttpPipelineConnection;
import net.optifine.http.HttpPipelineRequest;
import net.optifine.http.HttpRequest;
import net.optifine.http.HttpResponse;

public class HttpPipeline {
    private static final Map mapConnections = new HashMap();

    public static HttpRequest makeRequest(String urlStr, Proxy proxy) throws IOException {
        URL url = new URL(urlStr);
        if (!url.getProtocol().equals("http")) {
            throw new IOException("Only protocol http is supported: " + url);
        }
        String s = url.getFile();
        String s1 = url.getHost();
        int i = url.getPort();
        if (i <= 0) {
            i = 80;
        }
        String s2 = "GET";
        String s3 = "HTTP/1.1";
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("User-Agent", "Java/" + System.getProperty("java.version"));
        map.put("Host", s1);
        map.put("Accept", "text/html, image/gif, image/png");
        map.put("Connection", "keep-alive");
        byte[] abyte = new byte[]{};
        return new HttpRequest(s1, i, proxy, s2, s, s3, map, abyte);
    }

    public static void addRequest(HttpPipelineRequest pr) {
        HttpRequest httprequest = pr.getHttpRequest();
        HttpPipelineConnection httppipelineconnection = HttpPipeline.getConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy());
        while (!httppipelineconnection.addRequest(pr)) {
            HttpPipeline.removeConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy(), httppipelineconnection);
            httppipelineconnection = HttpPipeline.getConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy());
        }
    }

    private static synchronized HttpPipelineConnection getConnection(String host, int port, Proxy proxy) {
        String s = HttpPipeline.makeConnectionKey(host, port, proxy);
        HttpPipelineConnection httppipelineconnection = (HttpPipelineConnection)mapConnections.get(s);
        if (httppipelineconnection == null) {
            httppipelineconnection = new HttpPipelineConnection(host, port, proxy);
            mapConnections.put(s, httppipelineconnection);
        }
        return httppipelineconnection;
    }

    private static synchronized void removeConnection(String host, int port, Proxy proxy, HttpPipelineConnection hpc) {
        String s = HttpPipeline.makeConnectionKey(host, port, proxy);
        HttpPipelineConnection httppipelineconnection = (HttpPipelineConnection)mapConnections.get(s);
        if (httppipelineconnection == hpc) {
            mapConnections.remove(s);
        }
    }

    private static String makeConnectionKey(String host, int port, Proxy proxy) {
        return host + ":" + port + "-" + proxy;
    }

    public static byte[] get(String urlStr) throws IOException {
        return HttpPipeline.get(urlStr, Proxy.NO_PROXY);
    }

    public static byte[] get(String urlStr, Proxy proxy) throws IOException {
        if (urlStr.startsWith("file:")) {
            URL url = new URL(urlStr);
            InputStream inputstream = url.openStream();
            return Config.readAll(inputstream);
        }
        HttpRequest httprequest = HttpPipeline.makeRequest(urlStr, proxy);
        HttpResponse httpresponse = HttpPipeline.executeRequest(httprequest);
        if (httpresponse.getStatus() / 100 != 2) {
            throw new IOException("HTTP response: " + httpresponse.getStatus());
        }
        return httpresponse.getBody();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static HttpResponse executeRequest(HttpRequest req) throws IOException {
        final HashMap map = new HashMap();
        HttpListener httplistener = new HttpListener(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void finished(HttpRequest req, HttpResponse resp) {
                Map map2 = map;
                synchronized (map2) {
                    map.put("Response", resp);
                    map.notifyAll();
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void failed(HttpRequest req, Exception e) {
                Map map2 = map;
                synchronized (map2) {
                    map.put("Exception", e);
                    map.notifyAll();
                }
            }
        };
        HashMap hashMap = map;
        synchronized (hashMap) {
            HttpPipelineRequest httppipelinerequest = new HttpPipelineRequest(req, httplistener);
            HttpPipeline.addRequest(httppipelinerequest);
            try {
                map.wait();
            }
            catch (InterruptedException var10) {
                throw new InterruptedIOException("Interrupted");
            }
            Exception exception = (Exception)map.get("Exception");
            if (exception != null) {
                if (exception instanceof IOException) {
                    throw (IOException)exception;
                }
                if (exception instanceof RuntimeException) {
                    throw (RuntimeException)exception;
                }
                throw new RuntimeException(exception.getMessage(), exception);
            }
            HttpResponse httpresponse = (HttpResponse)map.get("Response");
            if (httpresponse == null) {
                throw new IOException("Response is null");
            }
            return httpresponse;
        }
    }
}

