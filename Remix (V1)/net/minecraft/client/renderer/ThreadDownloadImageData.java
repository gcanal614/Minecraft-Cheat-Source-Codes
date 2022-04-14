package net.minecraft.client.renderer;

import java.util.concurrent.atomic.*;
import java.awt.image.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.*;
import javax.imageio.*;
import net.minecraft.client.*;
import org.apache.commons.io.*;
import java.net.*;
import java.io.*;
import optifine.*;
import org.apache.logging.log4j.*;

public class ThreadDownloadImageData extends SimpleTexture
{
    private static final Logger logger;
    private static final AtomicInteger threadDownloadCounter;
    private final File field_152434_e;
    private final String imageUrl;
    private final IImageBuffer imageBuffer;
    public Boolean imageFound;
    public boolean pipeline;
    private BufferedImage bufferedImage;
    private Thread imageThread;
    private boolean textureUploaded;
    
    public ThreadDownloadImageData(final File p_i1049_1_, final String p_i1049_2_, final ResourceLocation p_i1049_3_, final IImageBuffer p_i1049_4_) {
        super(p_i1049_3_);
        this.imageFound = null;
        this.pipeline = false;
        this.field_152434_e = p_i1049_1_;
        this.imageUrl = p_i1049_2_;
        this.imageBuffer = p_i1049_4_;
    }
    
    private void checkTextureUploaded() {
        if (!this.textureUploaded && this.bufferedImage != null) {
            this.textureUploaded = true;
            if (this.textureLocation != null) {
                this.deleteGlTexture();
            }
            TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
        }
    }
    
    @Override
    public int getGlTextureId() {
        this.checkTextureUploaded();
        return super.getGlTextureId();
    }
    
    public void setBufferedImage(final BufferedImage p_147641_1_) {
        this.bufferedImage = p_147641_1_;
        if (this.imageBuffer != null) {
            this.imageBuffer.func_152634_a();
        }
        this.imageFound = (this.bufferedImage != null);
    }
    
    @Override
    public void loadTexture(final IResourceManager p_110551_1_) throws IOException {
        if (this.bufferedImage == null && this.textureLocation != null) {
            super.loadTexture(p_110551_1_);
        }
        if (this.imageThread == null) {
            if (this.field_152434_e != null && this.field_152434_e.isFile()) {
                ThreadDownloadImageData.logger.debug("Loading http texture from local cache ({})", new Object[] { this.field_152434_e });
                try {
                    this.bufferedImage = ImageIO.read(this.field_152434_e);
                    if (this.imageBuffer != null) {
                        this.setBufferedImage(this.imageBuffer.parseUserSkin(this.bufferedImage));
                    }
                    this.imageFound = (this.bufferedImage != null);
                }
                catch (IOException var3) {
                    ThreadDownloadImageData.logger.error("Couldn't load skin " + this.field_152434_e, (Throwable)var3);
                    this.func_152433_a();
                }
            }
            else {
                this.func_152433_a();
            }
        }
    }
    
    protected void func_152433_a() {
        (this.imageThread = new Thread("Texture Downloader #" + ThreadDownloadImageData.threadDownloadCounter.incrementAndGet()) {
            @Override
            public void run() {
                HttpURLConnection var1 = null;
                ThreadDownloadImageData.logger.debug("Downloading http texture from {} to {}", new Object[] { ThreadDownloadImageData.this.imageUrl, ThreadDownloadImageData.this.field_152434_e });
                if (ThreadDownloadImageData.this.shouldPipeline()) {
                    ThreadDownloadImageData.this.loadPipelined();
                }
                else {
                    try {
                        var1 = (HttpURLConnection)new URL(ThreadDownloadImageData.this.imageUrl).openConnection(Minecraft.getMinecraft().getProxy());
                        var1.setDoInput(true);
                        var1.setDoOutput(false);
                        var1.connect();
                        if (var1.getResponseCode() / 100 != 2) {
                            if (var1.getErrorStream() != null) {
                                Config.readAll(var1.getErrorStream());
                            }
                            return;
                        }
                        BufferedImage var2;
                        if (ThreadDownloadImageData.this.field_152434_e != null) {
                            FileUtils.copyInputStreamToFile(var1.getInputStream(), ThreadDownloadImageData.this.field_152434_e);
                            var2 = ImageIO.read(ThreadDownloadImageData.this.field_152434_e);
                        }
                        else {
                            var2 = TextureUtil.func_177053_a(var1.getInputStream());
                        }
                        if (ThreadDownloadImageData.this.imageBuffer != null) {
                            var2 = ThreadDownloadImageData.this.imageBuffer.parseUserSkin(var2);
                        }
                        ThreadDownloadImageData.this.setBufferedImage(var2);
                    }
                    catch (Exception var3) {
                        ThreadDownloadImageData.logger.error("Couldn't download http texture: " + var3.getClass().getName() + ": " + var3.getMessage());
                    }
                    finally {
                        if (var1 != null) {
                            var1.disconnect();
                        }
                        ThreadDownloadImageData.this.imageFound = (ThreadDownloadImageData.this.bufferedImage != null);
                    }
                }
            }
        }).setDaemon(true);
        this.imageThread.start();
    }
    
    private boolean shouldPipeline() {
        if (!this.pipeline) {
            return false;
        }
        final Proxy proxy = Minecraft.getMinecraft().getProxy();
        return (proxy.type() == Proxy.Type.DIRECT || proxy.type() == Proxy.Type.SOCKS) && this.imageUrl.startsWith("http://");
    }
    
    private void loadPipelined() {
        try {
            final HttpRequest var6 = HttpPipeline.makeRequest(this.imageUrl, Minecraft.getMinecraft().getProxy());
            final HttpResponse resp = HttpPipeline.executeRequest(var6);
            if (resp.getStatus() / 100 != 2) {
                return;
            }
            final byte[] body = resp.getBody();
            final ByteArrayInputStream bais = new ByteArrayInputStream(body);
            BufferedImage var7;
            if (this.field_152434_e != null) {
                FileUtils.copyInputStreamToFile((InputStream)bais, this.field_152434_e);
                var7 = ImageIO.read(this.field_152434_e);
            }
            else {
                var7 = TextureUtil.func_177053_a(bais);
            }
            if (this.imageBuffer != null) {
                var7 = this.imageBuffer.parseUserSkin(var7);
            }
            this.setBufferedImage(var7);
        }
        catch (Exception var8) {
            ThreadDownloadImageData.logger.error("Couldn't download http texture: " + var8.getClass().getName() + ": " + var8.getMessage());
        }
        finally {
            this.imageFound = (this.bufferedImage != null);
        }
    }
    
    static {
        logger = LogManager.getLogger();
        threadDownloadCounter = new AtomicInteger(0);
    }
}
