package optifine;

import net.minecraft.client.*;

public class FileDownloadThread extends Thread
{
    private String urlString;
    private IFileDownloadListener listener;
    
    public FileDownloadThread(final String urlString, final IFileDownloadListener listener) {
        this.urlString = null;
        this.listener = null;
        this.urlString = urlString;
        this.listener = listener;
    }
    
    @Override
    public void run() {
        try {
            final byte[] e = HttpPipeline.get(this.urlString, Minecraft.getMinecraft().getProxy());
            this.listener.fileDownloadFinished(this.urlString, e, null);
        }
        catch (Exception var2) {
            this.listener.fileDownloadFinished(this.urlString, null, var2);
        }
    }
    
    public String getUrlString() {
        return this.urlString;
    }
    
    public IFileDownloadListener getListener() {
        return this.listener;
    }
}
