/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package net.optifine;

import java.nio.ByteBuffer;
import java.util.Properties;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.SmartAnimations;
import net.optifine.TextureAnimationFrame;
import net.optifine.util.TextureUtils;
import org.lwjgl.opengl.GL11;

public class TextureAnimation {
    private final String dstTex;
    final ResourceLocation dstTexLoc;
    private int dstTextId = -1;
    private final int dstX;
    private final int dstY;
    private final int frameWidth;
    private final int frameHeight;
    private final TextureAnimationFrame[] frames;
    private int currentFrameIndex = 0;
    private final boolean interpolate;
    private final int interpolateSkip;
    private ByteBuffer interpolateData = null;
    byte[] srcData;
    private ByteBuffer imageData = null;
    private boolean active = true;
    private boolean valid = true;

    public TextureAnimation(byte[] srcData, String texTo, ResourceLocation locTexTo, int dstX, int dstY, int frameWidth, int frameHeight, Properties props) {
        this.dstTex = texTo;
        this.dstTexLoc = locTexTo;
        this.dstX = dstX;
        this.dstY = dstY;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        int i = frameWidth * frameHeight * 4;
        if (srcData.length % i != 0) {
            Config.warn("Invalid animated texture length: " + srcData.length + ", frameWidth: " + frameWidth + ", frameHeight: " + frameHeight);
        }
        this.srcData = srcData;
        int j = srcData.length / i;
        if (props.get("tile.0") != null) {
            int k = 0;
            while (props.get("tile." + k) != null) {
                j = k + 1;
                ++k;
            }
        }
        String s2 = (String)props.get("duration");
        int l = Math.max(Config.parseInt(s2, 1), 1);
        this.frames = new TextureAnimationFrame[j];
        for (int i1 = 0; i1 < this.frames.length; ++i1) {
            TextureAnimationFrame textureanimationframe;
            String s = (String)props.get("tile." + i1);
            int j1 = Config.parseInt(s, i1);
            String s1 = (String)props.get("duration." + i1);
            int k1 = Math.max(Config.parseInt(s1, l), 1);
            this.frames[i1] = textureanimationframe = new TextureAnimationFrame(j1, k1);
        }
        this.interpolate = Config.parseBoolean(props.getProperty("interpolate"), false);
        this.interpolateSkip = Config.parseInt(props.getProperty("skip"), 0);
        if (this.interpolate) {
            this.interpolateData = GLAllocation.createDirectByteBuffer(i);
        }
    }

    public boolean nextFrame() {
        TextureAnimationFrame textureanimationframe = this.getCurrentFrame();
        if (textureanimationframe == null) {
            return false;
        }
        ++textureanimationframe.counter;
        if (textureanimationframe.counter < textureanimationframe.duration) {
            return this.interpolate;
        }
        textureanimationframe.counter = 0;
        ++this.currentFrameIndex;
        if (this.currentFrameIndex >= this.frames.length) {
            this.currentFrameIndex = 0;
        }
        return true;
    }

    public TextureAnimationFrame getCurrentFrame() {
        return this.getFrame(this.currentFrameIndex);
    }

    public TextureAnimationFrame getFrame(int index) {
        if (this.frames.length <= 0) {
            return null;
        }
        if (index < 0 || index >= this.frames.length) {
            index = 0;
        }
        return this.frames[index];
    }

    public void updateTexture() {
        if (this.valid) {
            if (this.dstTextId < 0) {
                ITextureObject itextureobject = TextureUtils.getTexture(this.dstTexLoc);
                if (itextureobject == null) {
                    this.valid = false;
                    return;
                }
                this.dstTextId = itextureobject.getGlTextureId();
            }
            if (this.imageData == null) {
                this.imageData = GLAllocation.createDirectByteBuffer(this.srcData.length);
                this.imageData.put(this.srcData);
                this.imageData.flip();
                this.srcData = null;
            }
            boolean bl = this.active = !SmartAnimations.isActive() || SmartAnimations.isTextureRendered(this.dstTextId);
            if (this.nextFrame() && this.active) {
                int i;
                int j = this.frameWidth * this.frameHeight * 4;
                TextureAnimationFrame textureanimationframe = this.getCurrentFrame();
                if (textureanimationframe != null && (i = j * textureanimationframe.index) + j <= this.imageData.limit()) {
                    if (this.interpolate && textureanimationframe.counter > 0) {
                        if (this.interpolateSkip <= 1 || textureanimationframe.counter % this.interpolateSkip == 0) {
                            TextureAnimationFrame textureanimationframe1 = this.getFrame(this.currentFrameIndex + 1);
                            double d0 = (double)textureanimationframe.counter / (double)textureanimationframe.duration;
                            this.updateTextureInerpolate(textureanimationframe, textureanimationframe1, d0);
                        }
                    } else {
                        this.imageData.position(i);
                        GlStateManager.bindTexture(this.dstTextId);
                        GL11.glTexSubImage2D((int)3553, (int)0, (int)this.dstX, (int)this.dstY, (int)this.frameWidth, (int)this.frameHeight, (int)6408, (int)5121, (ByteBuffer)this.imageData);
                    }
                }
            }
        }
    }

    private void updateTextureInerpolate(TextureAnimationFrame frame1, TextureAnimationFrame frame2, double dd) {
        int k;
        int i = this.frameWidth * this.frameHeight * 4;
        int j = i * frame1.index;
        if (j + i <= this.imageData.limit() && (k = i * frame2.index) + i <= this.imageData.limit()) {
            this.interpolateData.clear();
            for (int l = 0; l < i; ++l) {
                int i1 = this.imageData.get(j + l) & 0xFF;
                int j1 = this.imageData.get(k + l) & 0xFF;
                int k1 = this.mix(i1, j1, dd);
                byte b0 = (byte)k1;
                this.interpolateData.put(b0);
            }
            this.interpolateData.flip();
            GlStateManager.bindTexture(this.dstTextId);
            GL11.glTexSubImage2D((int)3553, (int)0, (int)this.dstX, (int)this.dstY, (int)this.frameWidth, (int)this.frameHeight, (int)6408, (int)5121, (ByteBuffer)this.interpolateData);
        }
    }

    private int mix(int col1, int col2, double k) {
        return (int)((double)col1 * (1.0 - k) + (double)col2 * k);
    }

    public String getDstTex() {
        return this.dstTex;
    }

    public boolean isActive() {
        return this.active;
    }
}

