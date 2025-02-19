/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  optifine.Config
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  shadersmod.client.MultiTexID
 *  shadersmod.client.ShadersTex
 */
package net.minecraft.client.renderer.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shadersmod.client.MultiTexID;
import shadersmod.client.ShadersTex;

public class SimpleTexture
extends AbstractTexture {
    private static final Logger logger = LogManager.getLogger();
    protected final ResourceLocation textureLocation;
    private static final String __OBFID = "CL_00001052";

    public SimpleTexture(ResourceLocation textureResourceLocation) {
        this.textureLocation = textureResourceLocation;
    }

    @Override
    public void loadTexture(IResourceManager resourceManager) throws IOException {
        this.deleteGlTexture();
        try (InputStream inputstream = null;){
            IResource iresource = resourceManager.getResource(this.textureLocation);
            inputstream = iresource.getInputStream();
            BufferedImage bufferedimage = TextureUtil.readBufferedImage(inputstream);
            boolean flag = false;
            boolean flag1 = false;
            if (iresource.hasMetadata()) {
                try {
                    TextureMetadataSection texturemetadatasection = (TextureMetadataSection)iresource.getMetadata("texture");
                    if (texturemetadatasection != null) {
                        flag = texturemetadatasection.getTextureBlur();
                        flag1 = texturemetadatasection.getTextureClamp();
                    }
                }
                catch (RuntimeException runtimeexception) {
                    logger.warn("Failed reading metadata of: " + this.textureLocation, (Throwable)runtimeexception);
                }
            }
            if (Config.isShaders()) {
                ShadersTex.loadSimpleTexture((int)this.getGlTextureId(), (BufferedImage)bufferedimage, (boolean)flag, (boolean)flag1, (IResourceManager)resourceManager, (ResourceLocation)this.textureLocation, (MultiTexID)this.getMultiTexID());
            } else {
                TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), bufferedimage, flag, flag1);
            }
        }
    }
}

