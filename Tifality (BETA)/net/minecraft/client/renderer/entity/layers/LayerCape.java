/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import club.tifality.Tifality;
import club.tifality.module.impl.render.Cape;
import club.tifality.module.impl.render.hud.Hud;
import club.tifality.utils.render.RenderingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LayerCape
implements LayerRenderer<AbstractClientPlayer> {
    private final RenderPlayer playerRenderer;

    public LayerCape(RenderPlayer playerRendererIn) {
        this.playerRenderer = playerRendererIn;
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
        if (entitylivingbaseIn.hasPlayerInfo() && !entitylivingbaseIn.isInvisible() && entitylivingbaseIn.getName().equals(Minecraft.getMinecraft().getSession().getUsername()) && entitylivingbaseIn.isWearing(EnumPlayerModelParts.CAPE) && Tifality.INSTANCE.getModuleManager().getModule(Cape.class).isEnabled()) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.playerRenderer.bindTexture(new ResourceLocation("textures/cape.png"));
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0f, 0.0f, 0.125f);
            double d0 = entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * (double)partialTicks - (entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * (double)partialTicks);
            double d1 = entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * (double)partialTicks - (entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * (double)partialTicks);
            double d2 = entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * (double)partialTicks - (entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * (double)partialTicks);
            float f = entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks;
            double d3 = MathHelper.sin(f * (float)Math.PI / 180.0f);
            double d4 = -MathHelper.cos(f * (float)Math.PI / 180.0f);
            float f1 = (float)d1 * 10.0f;
            f1 = MathHelper.clamp_float(f1, -6.0f, 32.0f);
            float f2 = (float)(d0 * d3 + d2 * d4) * 100.0f;
            float f3 = (float)(d0 * d4 - d2 * d3) * 100.0f;
            if (f2 < 0.0f) {
                f2 = 0.0f;
            }
            if (f2 > 165.0f) {
                f2 = 165.0f;
            }
            if (f1 < -5.0f) {
                f1 = -5.0f;
            }
            float f4 = entitylivingbaseIn.prevCameraYaw + (entitylivingbaseIn.cameraYaw - entitylivingbaseIn.prevCameraYaw) * partialTicks;
            f1 += MathHelper.sin((entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks) * 6.0f) * 32.0f * f4;
            if (entitylivingbaseIn.isSneaking()) {
                f1 += 25.0f;
                GL11.glTranslatef(0.0f, 0.142f, -0.0178f);
            }
            GL11.glRotatef(6.0f + f2 / 2.0f + f1, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(f3 / 2.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(-f3 / 2.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            this.playerRenderer.getMainModel().renderCape(0.0625f);
            this.playerRenderer.bindTexture(new ResourceLocation("textures/overlay.png"));
            Hud hud = Tifality.INSTANCE.getModuleManager().getModule(Hud.class);
            if (hud.arrayListColorModeProperty.get() == Hud.ArrayListColorMode.RAINBOW) {
                int rgb = RenderingUtils.getRainbow(((Double)hud.rainbowSpeed.getValue()).intValue(), ((Double)hud.rainbowWidth.getValue()).intValue(), (int)(System.currentTimeMillis() / 15L));
                float alpha = 0.3f;
                float red = (float)(rgb >> 16 & 0xFF) / 255.0f;
                float green = (float)(rgb >> 8 & 0xFF) / 255.0f;
                float blue = (float)(rgb & 0xFF) / 255.0f;
                GL11.glColor4f(red, green, blue, alpha);
            } else {
                float alpha = 0.3f;
                float red = (float)(Hud.hudColor.get() >> 16 & 0xFF) / 255.0f;
                float green = (float)(Hud.hudColor.get() >> 8 & 0xFF) / 255.0f;
                float blue = (float)(Hud.hudColor.get() & 0xFF) / 255.0f;
                GL11.glColor4f(red, green, blue, alpha);
            }
            this.playerRenderer.getMainModel().renderCape(0.0625f);
            GL11.glPopMatrix();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}

