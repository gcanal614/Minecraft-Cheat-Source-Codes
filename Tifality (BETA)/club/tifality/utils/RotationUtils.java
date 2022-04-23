/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.utils;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.packet.PacketSendEvent;
import club.tifality.utils.Rotation;
import club.tifality.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

public final class RotationUtils {
    public static Rotation targetRotation;
    public static Rotation serverRotation;

    private RotationUtils() {
    }

    public static float getAngleDifference(float a, float b) {
        return ((a - b) % 360.0f + 540.0f) % 360.0f - 180.0f;
    }

    public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 1.2;
        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static double getRotationDifference(Rotation a, Rotation b) {
        return Math.hypot(RotationUtils.getAngleDifference(a.getYaw(), b.getYaw()), a.getPitch() - b.getPitch());
    }

    public static double getRotationDifference(Rotation rotation) {
        return serverRotation == null ? 0.0 : RotationUtils.getRotationDifference(rotation, serverRotation);
    }

    public static float getYawToEntity(Entity entity) {
        EntityPlayerSP player = Wrapper.getPlayer();
        return RotationUtils.getYawBetween(player.rotationYaw, player.posX, player.posZ, entity.posX, entity.posZ);
    }

    public static float getYawBetween(float yaw, double srcX, double srcZ, double destX, double destZ) {
        double xDist = destX - srcX;
        double zDist = destZ - srcZ;
        float var1 = (float)(StrictMath.atan2(zDist, xDist) * 180.0 / Math.PI) - 90.0f;
        return yaw + MathHelper.wrapAngleTo180_float(var1 - yaw);
    }

    public static float getYawToEntity(Entity entity, boolean useOldPos) {
        EntityPlayerSP player = Wrapper.getPlayer();
        double xDist = (useOldPos ? entity.prevPosX : entity.posX) - (useOldPos ? player.prevPosX : player.posX);
        double zDist = (useOldPos ? entity.prevPosZ : entity.posZ) - (useOldPos ? player.prevPosZ : player.posZ);
        float rotationYaw = useOldPos ? Wrapper.getPlayer().prevRotationYaw : Wrapper.getPlayer().rotationYaw;
        float var1 = (float)(Math.atan2(zDist, xDist) * 180.0 / Math.PI) - 90.0f;
        return rotationYaw + MathHelper.wrapAngleTo180_float(var1 - rotationYaw);
    }

    @Listener
    public void onPacket(PacketSendEvent event) {
        Packet<?> packet = event.getPacket();
        if (packet instanceof C03PacketPlayer) {
            C03PacketPlayer packetPlayer = (C03PacketPlayer)packet;
            if (targetRotation != null && (targetRotation.getYaw() != serverRotation.getYaw() || targetRotation.getPitch() != serverRotation.getPitch())) {
                packetPlayer.yaw = targetRotation.getYaw();
                packetPlayer.pitch = targetRotation.getPitch();
                packetPlayer.rotating = true;
            }
            if (packetPlayer.rotating) {
                serverRotation = new Rotation(packetPlayer.yaw, packetPlayer.pitch);
            }
        }
    }
}

