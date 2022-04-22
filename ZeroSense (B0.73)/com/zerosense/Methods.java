package com.zerosense;

import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Timer;

public interface Methods {

    default void sendPacketUnlogged(Packet paramPacket) {
    mc.getNetHandler().getNetworkManager().sendPacket(paramPacket);
  }
  
  public static final Minecraft mc = Minecraft.getMinecraft();
  
  public static final FontRenderer fr = mc.fontRendererObj;
  
  default EntityPlayerSP getPlayer() {
    return mc.thePlayer;
  }
  
  default PlayerControllerMP getPlayerController() {
    return mc.playerController;
  }
  
  default GameSettings getGameSettings() {
    return mc.gameSettings;
  }
  
  default WorldClient getWorld() {
    return mc.theWorld;
  }
  
  default Timer getTimer() {
    return mc.timer;
  }
  
  default String getName(EntityPlayer paramEntityPlayer) {
    return paramEntityPlayer.getGameProfile().getName();
  }
  
  default float getYaw() {
    return (getPlayer()).rotationYawHead;
  }
  
  default double getX() {
    return (getPlayer()).posX;
  }
  
  default double getY() {
    return (getPlayer()).posY;
  }
  
  default double getZ() {
    return (getPlayer()).posZ;
  }
  
  default int getHurtTime() {
    return (getPlayer()).hurtTime;
  }
  
  default void setMotion(double paramDouble) {
    (getPlayer()).motionX = (getPlayer()).motionZ = paramDouble;
  }
  
  default Block getBlockUnderPlayer(float paramFloat) {
    return getWorld().getBlockState(new BlockPos(getX(), getY() - paramFloat, getZ())).getBlock();
  }
  
  default void push(double paramDouble) {
    float f = (getPlayer()).rotationYaw * 0.017453292F;
    (getPlayer()).motionX -= MathHelper.sin(f) * paramDouble;
    (getPlayer()).motionZ += MathHelper.cos(f) * paramDouble;
  }
  
  default void setPosition(double paramDouble1, double paramDouble2, double paramDouble3) {
    getPlayer().setPosition(paramDouble1, paramDouble2, paramDouble3);
  }
  
  default boolean isMoving() {
    return !((getPlayer()).moveForward == 0.0F && (getPlayer()).moveStrafing == 0.0F);
  }
  
  default boolean isMoving(Entity paramEntity) {
    return !(paramEntity.lastTickPosX == paramEntity.posX && paramEntity.lastTickPosZ == paramEntity.posZ && paramEntity.lastTickPosY == paramEntity.posY);
  }
  
  default boolean isInteger(String paramString) {
    try {
      Integer.parseInt(paramString);
      return true;
    } catch (NumberFormatException numberFormatException) {
      return false;
    } 
  }
  
  default int randomInRange(int paramInt1, int paramInt2) {
    if (paramInt1 > paramInt2) {
      System.err.println("The minimal value cannot be higher than the max value");
      return paramInt1;
    } 
    paramInt2 -= paramInt1;
    return (int)Math.round(Math.random() * paramInt2) + paramInt1;
  }
  
  default double randomInRange(double paramDouble1, double paramDouble2) {
    if (paramDouble1 > paramDouble2) {
      System.err.println("The minimal value cannot be higher than the max value");
      return paramDouble1;
    } 
    paramDouble2 -= paramDouble1;
    return Math.random() * paramDouble2 + paramDouble1;
  }
  
  static Color getRainbow(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2) {
    float f = (float)((System.currentTimeMillis() + paramInt1) % paramInt2) / paramInt2;
    return Color.getHSBColor(f, paramFloat1, paramFloat2);
  }
  
  default boolean isValidEntityName(Entity paramEntity) {
    if (!(paramEntity instanceof EntityPlayer))
      return true; 
    String str = getName((EntityPlayer)paramEntity);
    return (str.length() > 16 || str.length() < 3) ? false : (!!str.matches("[a-zA-Z0-9_]*"));
  }
}
