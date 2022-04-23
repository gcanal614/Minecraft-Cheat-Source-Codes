// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.util.misc;

import java.util.Random;
import net.minecraft.client.Minecraft;
import java.math.RoundingMode;
import java.math.BigDecimal;

public class MathUtil
{
    public static double linearInterpolate(final double min, final double max, final double norm) {
        return (max - min) * norm + min;
    }
    
    public static double roundToPlace(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public static float clampRotation() {
        float rotationYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        float n = 1.0f;
        if (Minecraft.getMinecraft().thePlayer.movementInput.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        n = -0.5f;
        if (Minecraft.getMinecraft().thePlayer.movementInput.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }
    
    public static double getRandomInRange(final double min, final double max) {
        final Random random = new Random();
        final double range = max - min;
        double scaled = random.nextDouble() * range;
        if (scaled > max) {
            scaled = max;
        }
        double shifted = scaled + min;
        if (shifted > max) {
            shifted = max;
        }
        return shifted;
    }
    
    public static float[] FindGCD(final float x, float y) {
        if (isZero(y)) {
            return new float[] { x, 0.0f };
        }
        y = x % y;
        return new float[] { x, y };
    }
    
    public static boolean isZero(final float y) {
        return y == 0.0f;
    }
}
