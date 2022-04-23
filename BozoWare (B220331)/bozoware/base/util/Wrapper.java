// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.util;

import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.network.Packet;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;

public class Wrapper
{
    public static boolean isSinglePlayer;
    
    public static Minecraft<?> getMinecraft() {
        return Minecraft.getMinecraft();
    }
    
    public static EntityPlayerSP getPlayer() {
        return getMinecraft().thePlayer;
    }
    
    public static WorldClient getWorld() {
        return getMinecraft().theWorld;
    }
    
    public static void sendMessageToPlayer(final String message) {
        getPlayer().addChatMessage(new ChatComponentText(message));
    }
    
    public static void sendMessageAsPlayer(final String message) {
        getPlayer().sendChatMessage(message);
    }
    
    public static void sendPacketDirect(final Packet<?> packet) {
        getMinecraft().getNetHandler().getNetworkManager().sendPacket(packet);
    }
    
    public static Block getBlock(final BlockPos pos) {
        return getMinecraft().theWorld.getBlockState(pos).getBlock();
    }
    
    public static Vec3 getVec3(final BlockPos blockPos) {
        return new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
    
    public static boolean isServerSinglePlayer() {
        return Wrapper.isSinglePlayer;
    }
    
    public static String getCurrentServerIP() {
        return isServerSinglePlayer() ? "Singleplayer" : getMinecraft().getCurrentServerData().serverIP;
    }
    
    public static float[] getEntityRotations(final EntityPlayer player, final Entity target) {
        final double posX = target.posX - player.posX;
        final double posY = target.posY + target.getEyeHeight() - (player.posY + player.getEyeHeight() + 0.1);
        final double posZ = target.posZ - player.posZ;
        final float yaw = (float)(Math.atan2(posZ, posX) * 180.0 / 3.141592653589793) - 90.0f;
        final double posMulti = MathHelper.sqrt_double(posX * posX + posZ * posZ);
        final float pitch = (float)(-(Math.atan2(posY, posMulti) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
    
    public static float[] getFacePos(final Vec3 vec) {
        final double diffX = vec.xCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posX;
        final double diffY = vec.yCoord + 0.5 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        final double diffZ = vec.zCoord + 0.5 - Minecraft.getMinecraft().thePlayer.posZ;
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[] { Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw), Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch) };
    }
    
    public static void sendPacketDelayed(final Packet packet, final long delay) {
        try {
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(delay);
                        Wrapper.sendPacketDirect(packet);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        catch (Exception ex) {}
    }
}
