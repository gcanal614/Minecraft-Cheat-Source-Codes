package me.injusttice.neutron.utils.player;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public class ValueUtil {
    private static final Minecraft getMc = Minecraft.getMinecraft();

    public static double getMotion(double initialSpeed, double speedMultiplier) {
        double speed = initialSpeed;
        if (getMc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int effect = getMc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            speed *= 1.0D + speedMultiplier * (effect + 1.0D);
        }
        return speed;
    }

    public static double getModifiedMotionY(double mY) {
        if (getMc.thePlayer.isPotionActive(Potion.jump))
            mY += (getMc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1D;
        return mY;
    }

    public static double getBaseMotionY() {
        double motion = 0.41999998688697815D;
        if (getMc.thePlayer.isPotionActive(Potion.jump))
            motion += (getMc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1D;
        return motion;
    }
}
