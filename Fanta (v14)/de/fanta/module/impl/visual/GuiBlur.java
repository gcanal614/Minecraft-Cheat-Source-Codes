/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.visual;

import de.fanta.events.Event;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.ColorValue;
import de.fanta.utils.Colors;
import de.fanta.utils.RenderUtil;
import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class GuiBlur
extends Module {
    public static GuiBlur INSTANCE;

    public GuiBlur() {
        super("GuiBlur", 0, Module.Type.Visual, Color.YELLOW);
        this.settings.add(new Setting("Color", new ColorValue(Color.RED.getRGB())));
    }

    @Override
    public void onEvent(Event event) {
    }

    public int getColor2() {
        try {
            return ((ColorValue)this.getSetting((String)"Color").getSetting()).color;
        }
        catch (Exception e) {
            return Color.white.getRGB();
        }
    }

    public void renderVignetteScaledResolution(ScaledResolution scaledRes) {
        int[] rgb = Colors.getRGB(this.getColor2());
        GlStateManager.enableTexture2D();
        GlStateManager.resetColor();
        RenderUtil.color(RenderUtil.injectAlpha(Colors.getColor(rgb[0], rgb[1], rgb[2], 255), MathHelper.clamp_int(60, 0, 255)).getRGB());
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(771, 770);
        GlStateManager.tryBlendFuncSeparate(771, 770, 1, 0);
        mc.getTextureManager().bindTexture(new ResourceLocation("textures/misc/vignette.png"));
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0.0, scaledRes.getScaledHeight(), -90.0).tex(0.0, 1.0).endVertex();
        bufferbuilder.pos(scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), -90.0).tex(1.0, 1.0).endVertex();
        bufferbuilder.pos(scaledRes.getScaledWidth(), 0.0, -90.0).tex(1.0, 0.0).endVertex();
        bufferbuilder.pos(0.0, 0.0, -90.0).tex(0.0, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
    }
}

