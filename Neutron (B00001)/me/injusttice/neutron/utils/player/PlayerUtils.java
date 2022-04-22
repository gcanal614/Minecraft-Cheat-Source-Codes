package me.injusttice.neutron.utils.player;

import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class PlayerUtils {

    public static Minecraft mc = Minecraft.getMinecraft();

    public static boolean isOnSameTeam(EntityLivingBase entity) {
        if (entity.getTeam() != null && mc.thePlayer.getTeam() != null) {
            char c1 = entity.getDisplayName().getFormattedText().charAt(1);
            char c2 = mc.thePlayer.getDisplayName().getFormattedText().charAt(1);
            return c1 == c2;
        } else {
            return false;
        }
    }

    public static boolean isBlockUnder(double x, double y, double z) {
        for (int i = (int) y - 1; i > 0; --i) {
            if (new BlockPos(x, i, z).getBlock() instanceof BlockAir) continue;
            return true;
        }
        return false;
    }

    public static boolean isBlockUnder() {
        return isBlockUnder(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
    }

    public static void damage() {
        int damage = 1;
        if (damage > MathHelper.floor_double(mc.thePlayer.getMaxHealth()))
            damage = MathHelper.floor_double(mc.thePlayer.getMaxHealth());

        double offset = 0.0625;
        if (mc.thePlayer != null && mc.getNetHandler() != null && mc.thePlayer.onGround) {
            for (int i = 0; i <= ((3 + damage) / offset); i++) {
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, (i == ((3 + damage) / offset))));
            }
        }
    }
}