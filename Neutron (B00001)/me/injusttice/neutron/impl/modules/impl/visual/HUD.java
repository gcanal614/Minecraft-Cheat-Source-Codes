package me.injusttice.neutron.impl.modules.impl.visual;

import java.awt.*;
import java.util.*;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventRender2D;
import me.injusttice.neutron.api.settings.impl.StringSet;
import me.injusttice.neutron.utils.font.Fonts;
import me.injusttice.neutron.utils.font.MCFontRenderer;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.movement.MovementUtils;
import me.injusttice.neutron.utils.astolfo.AstolfoUtils;
import me.injusttice.neutron.utils.render.ColorUtil;
import me.injusttice.neutron.utils.render.Palette;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class HUD extends Module {

    public static StringSet nameprotect = new StringSet("Name", "Neutron");
    public ModeSet hudMode = new ModeSet("Mode", "Default", "Default", "Astolfo", "OneTap");
    public ModeSet mode = new ModeSet("Color Mode", "Static", "Static", "Dynamic", "Astolfo", "Rainbow", "Sexo");
    public BooleanSet clientName = new BooleanSet("Client Name", true);
    public BooleanSet background = new BooleanSet("Background", false);
    public DoubleSet backgroundAlpha = new DoubleSet("Background Alpha", 60, 0, 255, 1);
    public BooleanSet font = new BooleanSet("Font", false);
    public DoubleSet red = new DoubleSet("Red", 255, 0, 255, 1.0);
    public DoubleSet green = new DoubleSet("Green", 255, 0, 255, 1.0);
    public DoubleSet blue = new DoubleSet("Blue", 255, 0, 255, 1.0);

    public static int colorr;
    float astolfoPotionCount;
    String astolfoCoords;
    MCFontRenderer logo;

    public HUD() {
        super("HUD", 0, Category.VISUAL);
        addSettings(nameprotect, hudMode, mode, clientName, background, font, backgroundAlpha, red, green, blue);
        logo = new MCFontRenderer(Fonts.fontFromTTF(new ResourceLocation("Desync/fonts/niggas.ttf"), 18, 0), true, true);
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {
        NeutronMain.instance.name = nameprotect.getText();
        if (!mc.gameSettings.showDebugInfo) {
            GlStateManager.pushMatrix();
            FontRenderer fr = mc.fontRendererObj;
            float height = e.getHeight();

            if (font.isEnabled()) {
                switch (hudMode.getMode()) {
                    case "Default":
                        if (clientName.isEnabled()) {
                            logo.drawStringWithShadow(NeutronMain.instance.name.substring(0, 1) + ChatFormatting.WHITE + NeutronMain.instance.name.substring(1), 1.4f, 3, AstolfoUtils.rainbow(0 * -100, 1f, 0.47f));
                        }
                        break;
                    case "Astolfo":
                        if (clientName.isEnabled()) {
                            logo.drawStringWithShadow(NeutronMain.instance.name.substring(0, 1) + ChatFormatting.WHITE + NeutronMain.instance.name.substring(1), 1.4f, 3, AstolfoUtils.rainbow(0 * -100, 1f, 0.47f));
                        }
                        NetworkPlayerInfo you = mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID());
                        logo.drawStringWithShadow("FPS: " + ChatFormatting.GRAY + Minecraft.getDebugFPS(), 3.0F, height - 26.0F, -1);
                        logo.drawStringWithShadow(((int) Math.round(MovementUtils.getBlocksPerSecond() * 100) / 100.0) + " blocks/sec",  2.3, height - 17.0, -1);
                        astolfoCoords = String.valueOf(Math.round(mc.thePlayer.posX)) + ChatFormatting.GRAY + ", " + ChatFormatting.RESET + Math.round(mc.thePlayer.posY) + ChatFormatting.GRAY + ", " + ChatFormatting.RESET + Math.round(mc.thePlayer.posZ);
                        logo.drawStringWithShadow(astolfoCoords, 2.2F, height - 8.0F, -1);
                        logo.drawStringWithShadow("Ping: §7" + (you == null ? "0" : you.responseTime) + "ms", e.getWidth() - fr.getStringWidth("Ping: §7" + (you == null ? "0" : you.responseTime) + "ms") - 0.2, height - 10.0, -1);
                        astolfoPotionCount = 17.0F;
                        for (PotionEffect potionEffect : mc.thePlayer.getActivePotionEffects()) {
                            Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
                            String potionText = I18n.format(potion.getName(), new Object[0]) + ChatFormatting.WHITE + " " + (potionEffect.getAmplifier() + 1) + ": " + ChatFormatting.GRAY + Potion.getDurationString(potionEffect);
                            int potColor = getPotionColor(potion);
                            logo.drawStringWithShadow(potionText, e.getWidth() - logo.getStringWidth(potionText) - 3.0F, height - astolfoPotionCount, potColor);
                            astolfoPotionCount += 9.0F;
                        }
                        break;
                    case "OneTap":
                        double addX = 0.0D;
                        if (NeutronMain.instance.moduleManager.getMod("Radar").isToggled())
                            addX += 105.0D;
                        String watermark = NeutronMain.instance.name.substring(0, 1) + ChatFormatting.WHITE + NeutronMain.instance.name.substring(1) + " - Build 111321 - User: " + mc.thePlayer.getName();
                        float charCount2 = 0.0F;
                        float cCountW = 0.0F;
                        cCountW = cCountW + logo.getStringWidth(watermark + 0.5D);
                        Gui.drawRect((charCount2 + 5.0F) + addX - 2.0D, 5.0D, (charCount2 - 7.5F) + addX + cCountW + 2.0D, 16.0D, (new Color(12, 12, 12,(int)backgroundAlpha.getValue())).getRGB());
                        Gui.drawRect((charCount2 + 5.0F) + addX - 2.0D, 4.0D, (charCount2 - 7.5F) + addX + cCountW + 2.0D, 5.0D, AstolfoUtils.rainbow(0 * -100, 1f, 0.47f));
                        logo.drawStringWithShadow(watermark, (charCount2 + 5.0F) + addX, 8.0D, AstolfoUtils.rainbow(0 * -100, 1f, 0.47f));
                        break;
                }

                GlStateManager.popMatrix();
                renderArrayListFont();
            } else {
                switch (hudMode.getMode()) {
                    case "Default":
                        if (clientName.isEnabled()) {
                            fr.drawStringWithShadow(NeutronMain.instance.name.substring(0, 1) + ChatFormatting.WHITE + NeutronMain.instance.name.substring(1), 1.4f, 3, AstolfoUtils.rainbow(0 * -100, 1f, 0.47f));
                        }
                        break;
                    case "Astolfo":
                        if (clientName.isEnabled()) {
                            fr.drawStringWithShadow(NeutronMain.instance.name.substring(0, 1) + ChatFormatting.WHITE + NeutronMain.instance.name.substring(1), 1.4f, 3, AstolfoUtils.rainbow(0 * -100, 1f, 0.47f));
                        }
                        NetworkPlayerInfo you = mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID());
                        fr.drawStringWithShadow("FPS: " + ChatFormatting.GRAY + Minecraft.getDebugFPS(), 3.0F, height - 26.0F, -1);
                        fr.drawStringWithShadow(((int) Math.round(MovementUtils.getBlocksPerSecond() * 100) / 100.0) + " blocks/sec",  2.3, height - 17.0, -1);
                        astolfoCoords = String.valueOf(Math.round(mc.thePlayer.posX)) + ChatFormatting.GRAY + ", " + ChatFormatting.RESET + Math.round(mc.thePlayer.posY) + ChatFormatting.GRAY + ", " + ChatFormatting.RESET + Math.round(mc.thePlayer.posZ);
                        fr.drawStringWithShadow(astolfoCoords, 2.2F, height - 8.0F, -1);
                        fr.drawStringWithShadow("Ping: §7" + (you == null ? "0" : you.responseTime) + "ms", e.getWidth() - fr.getStringWidth("Ping: §7" + (you == null ? "0" : you.responseTime) + "ms") - 0.2, height - 10.0, -1);
                        astolfoPotionCount = 17.0F;
                        for (PotionEffect potionEffect : mc.thePlayer.getActivePotionEffects()) {
                            Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
                            String potionText = I18n.format(potion.getName(), new Object[0]) + ChatFormatting.WHITE + " " + (potionEffect.getAmplifier() + 1) + ": " + ChatFormatting.GRAY + Potion.getDurationString(potionEffect);
                            int potColor = getPotionColor(potion);
                            fr.drawStringWithShadow(potionText, e.getWidth() - fr.getStringWidth(potionText) - 3.0F, height - astolfoPotionCount, potColor);
                            astolfoPotionCount += 9.0F;
                        }
                        break;
                    case "OneTap":
                        double addX = 0.0D;
                        if (NeutronMain.instance.moduleManager.getMod("Radar").isToggled())
                            addX += 105.0D;
                        String watermark = NeutronMain.instance.name.substring(0, 1) + ChatFormatting.WHITE + NeutronMain.instance.name.substring(1) + " - Build 111321 - User: " + mc.thePlayer.getName();
                        float charCount2 = 0.0F;
                        float cCountW = 0.0F;
                        cCountW = cCountW + logo.getStringWidth(watermark + 0.5D);
                        Gui.drawRect((charCount2 + 5.0F) + addX - 2.0D, 5.0D, (charCount2 + 36.5F) + addX + cCountW + 2.0D, 18.0D, (new Color(12, 12, 12,(int)backgroundAlpha.getValue())).getRGB());
                        Gui.drawRect((charCount2 + 5.0F) + addX - 2.0D, 4.0D, (charCount2 + 36.5F) + addX + cCountW + 2.0D, 5.0D, AstolfoUtils.rainbow(0 * -100, 1f, 0.47f));
                        fr.drawStringWithShadow(watermark, (charCount2 + 5.0F) + addX, 8.0D, AstolfoUtils.rainbow(0 * -100, 1f, 0.47f));
                        break;
                }

                GlStateManager.popMatrix();
                renderArrayListNoFont();
            }
        }
    }

    public int getPotionColor(Potion potion) {
        int potColor = (new Color(-1)).getRGB();
        if (potion == Potion.moveSpeed) {
            potColor = (new Color(9753087)).getRGB();
        } else if (potion == Potion.fireResistance) {
            potColor = (new Color(16750979)).getRGB();
        } else if (potion == Potion.damageBoost) {
            potColor = (new Color(16748945)).getRGB();
        } else if (potion == Potion.regeneration) {
            potColor = (new Color(16748747)).getRGB();
        } else if (potion == Potion.nightVision) {
            potColor = (new Color(11509503)).getRGB();
        } else if (potion == Potion.jump) {
            potColor = (new Color(9568147)).getRGB();
        } else if (potion == Potion.resistance) {
            potColor = (new Color(10266879)).getRGB();
        } else if (potion == Potion.absorption) {
            potColor = (new Color(16772007)).getRGB();
        } else if (potion == Potion.digSpeed) {
            potColor = (new Color(16300287)).getRGB();
        }
        return potColor;
    }

    public void renderArrayListNoFont() {
        ScaledResolution sr = new ScaledResolution(mc);
        FontRenderer fr = mc.fontRendererObj;

        if (mc.gameSettings.showDebugInfo) {
            return;
        }

        int yPos = 0;
        int count = 0;
        int y = 2;
        NeutronMain.instance.moduleManager.getModules().sort(Comparator.comparingInt(m ->
                fr.getStringWidth(((Module)m).getDisplayName())).reversed());
        for (Module m : NeutronMain.instance.moduleManager.getModules()) {
            if (!m.isToggled() || !m.isBeingEnabled() || !m.isVisible())
                continue;

            double offset = count * (fr.FONT_HEIGHT + 0.5);

            int dr, dg, db, dr2, dg2, db2;
            switch (mode.getMode()) {
                case "Static":
                    dr = (int) Math.round(red.getValue());
                    dg = (int) Math.round(green.getValue());
                    db = (int) Math.round(blue.getValue());
                    dr2 = Math.max(Math.round(dr * 0.5F), 10);
                    dg2 = Math.max(Math.round(dg * 0.5F), 10);
                    db2 = Math.max(Math.round(db * 0.5F), 10);
                    colorr = new Color(dr, dg, db).getRGB();
                    break;
                case "Dynamic":
                    dr = (int) Math.round(red.getValue());
                    dg = (int) Math.round(green.getValue());
                    db = (int) Math.round(blue.getValue());
                    dr2 = Math.max(Math.round(dr * 0.5F), 10);
                    dg2 = Math.max(Math.round(dg * 0.5F), 10);
                    db2 = Math.max(Math.round(db * 0.5F), 10);
                    colorr = Palette.fade(new Color(dr, dg, db), 100, count + 17 + 10).getRGB();
                    break;
                case "Astolfo":
                    colorr = AstolfoUtils.rainbow(count * -100, 1f, 0.47f);
                    break;
                case "Rainbow":
                    colorr = ColorUtil.getRainbow(2, 0.47f, 1, count * -100L);
                    break;
            }

            if(background.isEnabled()) {
                Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getDisplayName()) - 3.4, offset, sr.getScaledWidth(), 0.5 + fr.FONT_HEIGHT + offset, (new Color(16, 16, 16, (int)backgroundAlpha.getValue())).getRGB());
            }
            fr.drawStringWithShadow(m.getDisplayName(), sr.getScaledWidth() - fr.getStringWidth(m.getDisplayName()) - 1, (float) (1.7 + offset), colorr);

            y += 10;

            count++;
        }
    }

    public void renderArrayListFont() {
        ScaledResolution sr = new ScaledResolution(mc);
        FontRenderer fr = mc.fontRendererObj;

        if (mc.gameSettings.showDebugInfo) {
            return;
        }

        float yCount = -17.0F;
        int count = 0;
        int y = 2;
        Collections.sort(NeutronMain.instance.moduleManager.getModules(), new ModuleComparatorFont(logo).reversed());
        for (Module m : NeutronMain.instance.moduleManager.getModules()) {
            if (!m.isToggled() || !m.isBeingEnabled() || !m.isVisible())
                continue;

            double offset = count * (fr.FONT_HEIGHT - 0.5);

            int dr, dg, db, dr2, dg2, db2;
            switch (mode.getMode()) {
                case "Static":
                    dr = (int) Math.round(red.getValue());
                    dg = (int) Math.round(green.getValue());
                    db = (int) Math.round(blue.getValue());
                    dr2 = Math.max(Math.round(dr * 0.5F), 10);
                    dg2 = Math.max(Math.round(dg * 0.5F), 10);
                    db2 = Math.max(Math.round(db * 0.5F), 10);
                    colorr = new Color(dr, dg, db).getRGB();
                    break;
                case "Dynamic":
                    dr = (int) Math.round(red.getValue());
                    dg = (int) Math.round(green.getValue());
                    db = (int) Math.round(blue.getValue());
                    dr2 = Math.max(Math.round(dr * 0.5F), 10);
                    dg2 = Math.max(Math.round(dg * 0.5F), 10);
                    db2 = Math.max(Math.round(db * 0.5F), 10);
                    colorr = Palette.fade(new Color(dr, dg, db), 100, count + 17 + 10).getRGB();
                    break;
                case "Astolfo":
                    colorr = AstolfoUtils.rainbow(count * -100, 1f, 0.47f);
                    break;
                case "Rainbow":
                    colorr = ColorUtil.getRainbow(2, 0.47f, 1, count * -100L);
                    break;
            }

            if(background.isEnabled()) {
                Gui.drawRect(sr.getScaledWidth() - logo.getStringWidth(m.getDisplayName()) - 3.4, offset, sr.getScaledWidth(), -0.5 + fr.FONT_HEIGHT + offset, (new Color(16, 16, 16, (int)backgroundAlpha.getValue())).getRGB());
            }
            logo.drawStringWithShadow(m.getDisplayName(), sr.getScaledWidth() - logo.getStringWidth(m.getDisplayName()) - 1, (float) (1.7 + offset), colorr);

            y += 10;

            count++;
        }
    }

    public class ModuleComparatorFont implements Comparator<Module> {
        MCFontRenderer f2;
        public ModuleComparatorFont(MCFontRenderer f) {
            f2 = f;
        }
        public int compare(Module arg0, Module arg1) {
            if (f2.getStringWidth(arg0.getDisplayName()) < f2.getStringWidth(arg1.getDisplayName())) {
                return -1;
            }
            if (f2.getStringWidth(arg0.getDisplayName()) > f2.getStringWidth(arg1.getDisplayName())) {
                return 1;
            }
            return 0;
        }
    }
}