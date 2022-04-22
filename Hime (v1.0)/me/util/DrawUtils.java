    package me.util;

import net.minecraft.client.Minecraft;

public enum DrawUtils {
	   instance;
	
       protected Minecraft mc = Minecraft.getMinecraft();
    
       
       public double i(double prev, double cur) {
        return prev + (cur - prev) * this.mc.thePlayer.ticksExisted;
       }
}