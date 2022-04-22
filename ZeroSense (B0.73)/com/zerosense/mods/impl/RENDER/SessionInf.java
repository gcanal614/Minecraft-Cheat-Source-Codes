package com.zerosense.mods.impl.RENDER;

import com.zerosense.Events.Event;
import com.zerosense.Events.impl.EventRenderGUI;
import com.zerosense.Methods;
import com.zerosense.Settings.BooleanSetting;
import com.zerosense.Settings.NumberSetting;
import com.zerosense.Utils.Timer;
import com.zerosense.mods.Module;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

public class SessionInf extends Module {
    public static int hours;
    public static int minutes;
    public static int seconds;
    public static int kills;
    public static boolean canShowKills;
    Timer timer = new com.zerosense.Utils.Timer();
    public static BooleanSetting showKills = new BooleanSetting("Show Kills", false);
    public static NumberSetting x = new NumberSetting("PosX", 1, 100, 1, 1);
    public static NumberSetting y = new NumberSetting("PosY", 1, 100, 1, 1);
    public SessionInf() {
        super(
                "Session Inform", 0, Category.RENDER
        );
        this.addSettings(showKills, x, y);
    }

    @Override
    public void onEvent(Event e){
        if(timer.hasTimeElapsed(1000, true)){
            seconds++;
            if(seconds == 60){
                minutes++;
                seconds = 0;
            }
            if(minutes == 60){
                hours++;
                minutes = 0;
            }
        }
        System.out.println(seconds + " " + minutes + " " + hours);
        if(e instanceof EventRenderGUI){
            ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            FontRenderer fr = mc.fontRendererObj;
            Gui.drawRect(sr.getScaledWidth() - 366 + x.getValue(), sr.getScaledHeight() - 68.5 - 369 - 20 + y.getValue(), sr.getScaledWidth() - 460 + 222  + x.getValue() - 1, sr.getScaledHeight() - 497 + y.getValue(), -1879048192);
            Gui.drawRect(sr.getScaledWidth() - 366 + x.getValue() - 1, sr.getScaledHeight() - 68.5 -427 + y.getValue(), sr.getScaledWidth() - 460 + 222 + x.getValue() - 1, sr.getScaledHeight() - 497 + y.getValue(), Methods.getRainbow(0, 3000, 0.6F, 1.0F).getRGB());

            mc.fontRendererObj.drawStringWithShadow("NickName: " + mc.thePlayer.getName(), (float) (410 + 146F + 40 + x.getValue() + fr.getStringWidth(String.valueOf("Zero".charAt(0)))), (float) (36F + y.getValue()), -1);
            mc.fontRendererObj.drawStringWithShadow("Time: " + hours + "h " + minutes + "m " + seconds + "s",  (float)(410 + 146F + 40 + x.getValue()+ fr.getStringWidth(String.valueOf("Zero".charAt(0)))),  (float)(25F  + y.getValue()), -1);
        }
    }

    @Override
    public void onEnable(){
        kills = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;
    }
}
