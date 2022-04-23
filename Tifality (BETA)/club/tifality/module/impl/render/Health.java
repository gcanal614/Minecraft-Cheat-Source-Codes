/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.render;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.render.Render2DEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

@ModuleInfo(label="Health", category=ModuleCategory.RENDER)
public final class Health
extends Module {
    private final DecimalFormat decimalFormat = new DecimalFormat("0.#", new DecimalFormatSymbols(Locale.ENGLISH));
    private int width;

    @Listener
    public void onRender2D(Render2DEvent event) {
        float i;
        ScaledResolution sr = new ScaledResolution(mc);
        if (Health.mc.thePlayer.getHealth() >= 0.0f && Health.mc.thePlayer.getHealth() < 10.0f) {
            this.width = 3;
        }
        if (Health.mc.thePlayer.getHealth() >= 10.0f && Health.mc.thePlayer.getHealth() < 100.0f) {
            this.width = 3;
        }
        float health = Health.mc.thePlayer.getHealth();
        float absorptionHealth = Health.mc.thePlayer.getAbsorptionAmount();
        String absorp = absorptionHealth <= 0.0f ? "" : "\u00a7e" + this.decimalFormat.format(absorptionHealth / 2.0f) + "\u00a76\u2764";
        String string = this.decimalFormat.format(health / 2.0f) + "\u00a7c\u2764 " + absorp;
        int x = new ScaledResolution(mc).getScaledWidth() / 2 - this.width;
        int y = new ScaledResolution(mc).getScaledHeight() / 2 + 25;
        Health.mc.fontRendererObj.drawString(string, absorptionHealth > 0.0f ? (float)x - 15.5f : (float)x - 3.5f, y, this.getHealthColor(), true);
        GL11.glPushAttrib(1048575);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(Gui.icons);
        for (i = 0.0f; i < Health.mc.thePlayer.getMaxHealth() / 2.0f; i += 1.0f) {
            Gui.drawTexturedModalRect((float)sr.getScaledWidth() / 2.0f - Health.mc.thePlayer.getMaxHealth() / 2.5f * 10.0f / 2.0f + i * 8.0f, (float)sr.getScaledHeight() / 2.0f + 15.0f, 16, 0, 9, 9);
        }
        for (i = 0.0f; i < Health.mc.thePlayer.getHealth() / 2.0f; i += 1.0f) {
            Gui.drawTexturedModalRect((float)sr.getScaledWidth() / 2.0f - Health.mc.thePlayer.getMaxHealth() / 2.5f * 10.0f / 2.0f + i * 8.0f, (float)sr.getScaledHeight() / 2.0f + 15.0f, 52, 0, 9, 9);
        }
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    private int getHealthColor() {
        if (Health.mc.thePlayer.getHealth() <= 2.0f) {
            return new Color(255, 0, 0).getRGB();
        }
        if (Health.mc.thePlayer.getHealth() <= 6.0f) {
            return new Color(255, 110, 0).getRGB();
        }
        if (Health.mc.thePlayer.getHealth() <= 8.0f) {
            return new Color(255, 182, 0).getRGB();
        }
        if (Health.mc.thePlayer.getHealth() <= 10.0f) {
            return new Color(255, 255, 0).getRGB();
        }
        if (Health.mc.thePlayer.getHealth() <= 13.0f) {
            return new Color(255, 255, 0).getRGB();
        }
        if (Health.mc.thePlayer.getHealth() <= 15.5f) {
            return new Color(182, 255, 0).getRGB();
        }
        if (Health.mc.thePlayer.getHealth() <= 18.0f) {
            return new Color(108, 255, 0).getRGB();
        }
        if (Health.mc.thePlayer.getHealth() <= 20.0f) {
            return new Color(0, 255, 0).getRGB();
        }
        return 0;
    }
}

