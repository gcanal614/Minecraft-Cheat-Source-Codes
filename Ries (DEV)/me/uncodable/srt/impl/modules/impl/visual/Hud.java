/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.modules.impl.visual;

import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.events.render.Event2DRender;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;
import me.uncodable.srt.impl.modules.api.settings.Setting;
import me.uncodable.srt.impl.utils.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

@ModuleInfo(internalName="Hud", name="Hud", desc="Allows you to view an interface with general information you can use.", category=Module.Category.VISUAL, legit=true)
public class Hud
extends Module {
    private final ResourceLocation srt = new ResourceLocation("srt/newsrt2.png");

    public Hud(int key, boolean enabled) {
        super(key, enabled);
    }

    @EventTarget(target=Event2DRender.class)
    public void onRender(Event2DRender e) {
        if (!Hud.MC.gameSettings.showDebugInfo) {
            int renderY = 0;
            int previousWidth = -1;
            int potionRenderY = e.getScaledResolution().getScaledHeight() - 10;
            RenderUtils.drawCustomImage(-10, -28, 192, 108, 192, 108, this.srt);
            Hud.MC.fontRendererObj.drawStringWithShadow(Ries.INSTANCE.getBuild(), 170.0f, 3.0f, 9593855);
            for (Module module : Ries.INSTANCE.getModuleManager().getModules()) {
                if (!module.isEnabled() || Ries.INSTANCE.getSettingManager().getSetting(module, "INTERNAL_HIDDEN", Setting.Type.CHECKBOX).isTicked()) continue;
                Setting setting = Ries.INSTANCE.getSettingManager().getSetting(module, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX);
                int renderX = e.getScaledResolution().getScaledWidth() - Hud.MC.fontRendererObj.getStringWidth(module.getInfo().name());
                if (setting != null) {
                    renderX -= Hud.MC.fontRendererObj.getStringWidth(setting.getCurrentCombo()) + 4;
                }
                Gui.drawRect(renderX - 3, renderY, e.getScaledResolution().getScaledWidth(), renderY + 10, -1879048192);
                RenderUtils.drawVerticalLine(renderX - 3, renderY - 1, renderY + 10, -867329);
                if (previousWidth != -1) {
                    RenderUtils.drawHorizontalLine(previousWidth - 3, renderX - 3, renderY, -867329);
                }
                if (Ries.INSTANCE.getModuleManager().getShortestToggled(true) == module) {
                    RenderUtils.drawHorizontalLine(renderX - 3, e.getScaledResolution().getScaledWidth(), renderY + 10, -867329);
                }
                Hud.MC.fontRendererObj.drawStringWithShadow(String.format("%s \u00a77%s", module.getInfo().name(), setting != null ? setting.getCurrentCombo() : ""), renderX, renderY, 9593855);
                renderY += 10;
                previousWidth = renderX;
            }
            for (PotionEffect potionEffect : Hud.MC.thePlayer.getActivePotionEffects()) {
                String potionAmplifier = potionEffect.getAmplifier() <= 9 && potionEffect.getAmplifier() >= 0 ? I18n.format(String.format("enchantment.level.%s", potionEffect.getAmplifier() + 1), new Object[0]) : potionEffect.getAmplifier() + 1 + "";
                String potionData = String.format("%s %s(\u00a77ID: %d\u00a7r) [\u00a77%s\u00a7r]", I18n.format(potionEffect.getEffectName(), new Object[0]), potionAmplifier.equals("1") ? "" : potionAmplifier + " ", potionEffect.getPotionID(), Potion.getDurationString(potionEffect));
                int potionRenderX = e.getScaledResolution().getScaledWidth() - Hud.MC.fontRendererObj.getStringWidth(potionData);
                Hud.MC.fontRendererObj.drawStringWithShadow(potionData, potionRenderX, potionRenderY, 9593855);
                potionRenderY -= 10;
            }
        }
    }
}

