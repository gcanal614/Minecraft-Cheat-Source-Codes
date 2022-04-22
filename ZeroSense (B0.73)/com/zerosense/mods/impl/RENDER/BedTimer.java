package com.zerosense.mods.impl.RENDER;

import com.zerosense.Events.Event;
import com.zerosense.Utils.Timer;
import com.zerosense.mods.Module;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

public class BedTimer extends Module {
    int pies = 0;
    public Timer timer = new Timer();
    public BedTimer() {
        super("BedTimer", Keyboard.KEY_X, Category.RENDER);
    }
    
    @Override
    public void onEnable() {
        pies = 0;
    }

    public void onEvent(Event event){
        if(timer.hasTimeElapsed(1000, true)){
            pies++;
        }


        if(pies == 15){
            this.toggled = false;
        }

        ScaledResolution sr = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        this.mc.fontRendererObj.drawStringWithShadow("BedTimer : " + pies + "/15", sr.getScaledWidth() / 2 + 10, sr.getScaledHeight() / 2, -1);
    }
    
    @Override
    public void onDisable() {
        pies = 0;
    }
}
