/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.utils;

import club.tifality.utils.RotationUtils;
import club.tifality.utils.Wrapper;
import club.tifality.utils.server.HypixelGameUtils;
import club.tifality.utils.server.ServerUtils;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;

public final class PlayerUtils {
    public static boolean isMoving(EntityPlayer player) {
        double xDist = player.posX - player.prevPosX;
        double zDist = player.posZ - player.prevPosZ;
        return StrictMath.sqrt(xDist * xDist + zDist * zDist) > 1.0E-4;
    }

    public static float getMovementDirection(EntityPlayer player) {
        return RotationUtils.getYawBetween(player.rotationYaw, player.posX, player.posZ, player.prevPosX, player.prevPosZ);
    }

    public static boolean checkPing(EntityPlayer entity) {
        NetworkPlayerInfo info = Wrapper.getNetHandler().getPlayerInfo(entity.getUniqueID());
        return info != null && info.getResponseTime() == 1;
    }

    public static int getBounty(EntityPlayer player) {
        if (!ServerUtils.isOnHypixel() || HypixelGameUtils.getGameMode() != HypixelGameUtils.GameMode.PIT) {
            return -1;
        }
        String tabListName = player.getDisplayName().getFormattedText();
        System.out.println(tabListName);
        return 0;
    }

    public static boolean isTeamMate(EntityPlayer entity) {
        String entName = entity.getDisplayName().getFormattedText();
        String playerName = Wrapper.getPlayer().getDisplayName().getFormattedText();
        if (entName.length() < 2 || playerName.length() < 2) {
            return false;
        }
        if (!entName.startsWith("\u00a7") || !playerName.startsWith("\u00a7")) {
            return false;
        }
        return entName.charAt(1) == playerName.charAt(1);
    }
}

