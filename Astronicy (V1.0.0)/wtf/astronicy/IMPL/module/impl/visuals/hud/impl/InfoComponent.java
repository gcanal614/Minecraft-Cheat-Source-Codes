package wtf.astronicy.IMPL.module.impl.visuals.hud.impl;

import java.awt.Color;

import wtf.astronicy.IMPL.module.impl.visuals.hud.Component;
import wtf.astronicy.IMPL.module.impl.visuals.hud.HUDMod;
import wtf.astronicy.IMPL.utils.MathUtils;
import wtf.astronicy.IMPL.utils.render.Palette;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;

public final class InfoComponent extends Component {
    public InfoComponent(HUDMod parent) {
        super(parent);
    }



    public void draw(ScaledResolution sr) {


        HUDMod hud = this.getParent();
        int height = sr.getScaledHeight();
        FontRenderer fr = null;

        switch ((HUDMod.ArrayListFontModes)hud.defaultFont.getValue()) {
            case ADVENT:
                fr = mc.AdventFont;
                break;
            case BUNGEE:
                fr = mc.BungeeFont;
                break;
            case COMFORTAA:
                fr = mc.comfortaaFont;
                break;
            case MONTSERRAT:
                fr = mc.MontserratFont;
                break;
            default:
                break;
        }
        int color = Palette.fade((Color)hud.color.getValue()).getRGB();
        // String bps = String.format("FPS§7: %d", round);
        String fps = String.format("FPS§7: %d", Minecraft.getDebugFPS());
        int fontHeight = 9;
        switch((HUDMod.InfoDisplayMode)hud.infoDisplayMode.getValue()) {
            case LEFT:
                if (mc.currentScreen instanceof GuiChat) {
                    fontHeight += 15;
                }


                double fuckagain2 = Math.hypot((this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX) * (double) this.mc.timer.timerSpeed * 20.0D, (this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ) * (double) this.mc.timer.timerSpeed * 20.0D);
                double round2 = MathUtils.round(fuckagain2, 1);
                fr.drawStringWithShadow("BPS§7: " + round2, 40.0F, (float)(height - fontHeight), color);
                fr.drawStringWithShadow(fps, 2.0F, (float)(height - fontHeight), color);
                break;
            case RIGHT:
                int width = sr.getScaledWidth();
                double fuckagain = Math.hypot((this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX) * (double) this.mc.timer.timerSpeed * 20.0D, (this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ) * (double) this.mc.timer.timerSpeed * 20.0D);
                double round = MathUtils.round(fuckagain, 1);
                fr.drawStringWithShadow("BPS§7: " + round, (float)(width - fr.getStringWidth(fps)) - 43.0F, (float)(height - 9), color);
                fr.drawStringWithShadow(fps, (float)(width - fr.getStringWidth(fps)) - 2.0F, (float)(height - 9), color);
            default:
                break;
        }


    }

}