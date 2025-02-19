/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  optifine.Config
 *  shadersmod.client.ShadersTex
 */
package net.minecraft.client.renderer.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import optifine.Config;
import shadersmod.client.ShadersTex;

public class DynamicTexture
extends AbstractTexture {
    private final int[] dynamicTextureData;
    private final int width;
    private final int height;
    private static final String __OBFID = "CL_00001048";
    private boolean shadersInitialized = false;

    public DynamicTexture(BufferedImage bufferedImage) {
        this(bufferedImage.getWidth(), bufferedImage.getHeight());
        bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), this.dynamicTextureData, 0, bufferedImage.getWidth());
        this.updateDynamicTexture();
    }

    public DynamicTexture(int textureWidth, int textureHeight) {
        this.width = textureWidth;
        this.height = textureHeight;
        this.dynamicTextureData = new int[textureWidth * textureHeight * 3];
        if (Config.isShaders()) {
            ShadersTex.initDynamicTexture((int)this.getGlTextureId(), (int)textureWidth, (int)textureHeight, (DynamicTexture)this);
            this.shadersInitialized = true;
        } else {
            TextureUtil.allocateTexture(this.getGlTextureId(), textureWidth, textureHeight);
        }
    }

    @Override
    public void loadTexture(IResourceManager resourceManager) throws IOException {
    }

    public void updateDynamicTexture() {
        if (Config.isShaders()) {
            if (!this.shadersInitialized) {
                ShadersTex.initDynamicTexture((int)this.getGlTextureId(), (int)this.width, (int)this.height, (DynamicTexture)this);
                this.shadersInitialized = true;
            }
            ShadersTex.updateDynamicTexture((int)this.getGlTextureId(), (int[])this.dynamicTextureData, (int)this.width, (int)this.height, (DynamicTexture)this);
        } else {
            TextureUtil.uploadTexture(this.getGlTextureId(), this.dynamicTextureData, this.width, this.height);
        }
    }

    public int[] getTextureData() {
        return this.dynamicTextureData;
    }
}

