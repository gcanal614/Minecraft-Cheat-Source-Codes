/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  optifine.Config
 *  shadersmod.client.ShadersTex
 */
package net.minecraft.client.renderer.texture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.MathHelper;
import optifine.Config;
import shadersmod.client.ShadersTex;

public class TextureClock
extends TextureAtlasSprite {
    private double field_94239_h;
    private double field_94240_i;
    private static final String __OBFID = "CL_00001070";

    public TextureClock(String iconName) {
        super(iconName);
    }

    @Override
    public void updateAnimation() {
        if (!this.framesTextureData.isEmpty()) {
            Minecraft minecraft = Minecraft.getMinecraft();
            double d0 = 0.0;
            if (minecraft.theWorld != null && minecraft.thePlayer != null) {
                d0 = minecraft.theWorld.getCelestialAngle(1.0f);
                if (!minecraft.theWorld.provider.isSurfaceWorld()) {
                    d0 = Math.random();
                }
            }
            double d1 = d0 - this.field_94239_h;
            while (d1 < -0.5) {
                d1 += 1.0;
            }
            while (d1 >= 0.5) {
                d1 -= 1.0;
            }
            d1 = MathHelper.clamp_double(d1, -1.0, 1.0);
            this.field_94240_i += d1 * 0.1;
            this.field_94240_i *= 0.8;
            this.field_94239_h += this.field_94240_i;
            int i = (int)((this.field_94239_h + 1.0) * (double)this.framesTextureData.size()) % this.framesTextureData.size();
            while (i < 0) {
                i = (i + this.framesTextureData.size()) % this.framesTextureData.size();
            }
            if (i != this.frameCounter) {
                this.frameCounter = i;
                if (Config.isShaders()) {
                    ShadersTex.uploadTexSub((int[][])((int[][])this.framesTextureData.get(this.frameCounter)), (int)this.width, (int)this.height, (int)this.originX, (int)this.originY, (boolean)false, (boolean)false);
                } else {
                    TextureUtil.uploadTextureMipmap((int[][])this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
                }
            }
        }
    }
}

