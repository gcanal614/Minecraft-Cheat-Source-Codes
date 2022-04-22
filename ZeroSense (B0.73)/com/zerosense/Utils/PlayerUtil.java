package com.zerosense.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

public class PlayerUtil {
  public static Minecraft mc = Minecraft.getMinecraft();

  public static double getCurrentMotion() {
    return Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
  }

  public static BlockPos getHypixelBlockpos(String str){
    int val = 89;
    if(str != null && str.length() > 1){
      char[] chs = str.toCharArray();

      int lenght = chs.length;
      for(int i = 0; i < lenght; i++)
        val += (int)chs[i] * str.length()* str.length() + (int)str.charAt(0) + (int)str.charAt(1);
      val/=str.length();
    }
    return new BlockPos(val, -val%255, val);
  }

  public static boolean isMoving2() {
    return ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F));
  }

  public static float getDirection() {
    float f1 = mc.thePlayer.rotationYaw;
    if (mc.thePlayer.moveForward < 0.0F)
      f1 += 180.0F;
    float f2 = 1.0F;
    if (mc.thePlayer.moveForward < 0.0F) {
      f2 = -0.5F;
    } else if (mc.thePlayer.moveForward > 0.0F) {
      f2 = 0.5F;
    }
    if (mc.thePlayer.moveStrafing > 0.0F)
      f1 -= 90.0F * f2;
    if (mc.thePlayer.moveStrafing < 0.0F)
      f1 += 90.0F * f2;
    f1 *= 0.017453292F;
    return f1;
  }

  public static void setSpeed(double paramDouble) {
    mc.thePlayer.motionX = -Math.sin(getDirection()) * paramDouble;
    mc.thePlayer.motionZ = Math.cos(getDirection()) * paramDouble;
  }
}
