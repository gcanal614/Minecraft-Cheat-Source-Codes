package net.minecraft.client.renderer.texture;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import libraries.optifine.Config;
import libraries.shadersmod.client.ShadersTex;

public class TextureClock extends TextureAtlasSprite
{
    private double currentAngle;
    private double angleDelta;
    private static final String __OBFID = "CL_00001070";

    public TextureClock(String iconName)
    {
        super(iconName);
    }

    public void updateAnimation()
    {
        if (!this.framesTextureData.isEmpty())
        {
            Minecraft minecraft = Minecraft.getMinecraft();
            double d0 = 0.0D;

            if (minecraft.theWorld != null && minecraft.thePlayer != null)
            {
                d0 = (double)minecraft.theWorld.getCelestialAngle(1.0F);

                if (!minecraft.theWorld.provider.isSurfaceWorld())
                {
                    d0 = Math.random();
                }
            }

            double d1;

            for (d1 = d0 - this.currentAngle; d1 < -0.5D; ++d1)
            {
                ;
            }

            while (d1 >= 0.5D)
            {
                --d1;
            }

            d1 = MathHelper.clamp_double(d1, -1.0D, 1.0D);
            this.angleDelta += d1 * 0.1D;
            this.angleDelta *= 0.8D;
            this.currentAngle += this.angleDelta;
            int i;

            for (i = (int)((this.currentAngle + 1.0D) * (double)this.framesTextureData.size()) % this.framesTextureData.size(); i < 0; i = (i + this.framesTextureData.size()) % this.framesTextureData.size())
            {
                ;
            }

            if (i != this.frameCounter)
            {
                this.frameCounter = i;

                if (Config.isShaders())
                {
                    ShadersTex.uploadTexSub((int[][])((int[][])this.framesTextureData.get(this.frameCounter)), this.width, this.height, this.originX, this.originY, false, false);
                }
                else
                {
                    TextureUtil.uploadTextureMipmap((int[][])((int[][])this.framesTextureData.get(this.frameCounter)), this.width, this.height, this.originX, this.originY, false, false);
                }
            }
        }
    }
}
