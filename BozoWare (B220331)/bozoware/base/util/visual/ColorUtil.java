// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.util.visual;

import bozoware.base.util.misc.MathUtil;
import java.awt.Color;
import net.minecraft.entity.EntityLivingBase;

public class ColorUtil
{
    public static Color getHealthColorFromEntity(final EntityLivingBase entity) {
        final double current = entity.getHealth();
        final double max = entity.getMaxHealth();
        final double percent = current / max;
        final Color c1 = Color.GREEN;
        final Color c2 = Color.RED.darker();
        return new Color((int)MathUtil.linearInterpolate(c1.getRed(), c2.getRed(), percent), (int)MathUtil.linearInterpolate(c1.getGreen(), c2.getGreen(), percent), (int)MathUtil.linearInterpolate(c1.getBlue(), c2.getBlue(), percent));
    }
    
    public static Color getHealthColorFromEntity(final EntityLivingBase entity, final Color c1, final Color c2) {
        final double current = entity.getHealth();
        final double max = entity.getMaxHealth();
        final double percent = current / max;
        return new Color((int)MathUtil.linearInterpolate(c1.getRed(), c2.getRed(), percent), (int)MathUtil.linearInterpolate(c1.getGreen(), c2.getGreen(), percent), (int)MathUtil.linearInterpolate(c1.getBlue(), c2.getBlue(), percent));
    }
    
    public static Color interpolateColors(final Color color1, final Color color2, float point) {
        if (point > 1.0f) {
            point = 1.0f;
        }
        return new Color((int)((color2.getRed() - color1.getRed()) * point + color1.getRed()), (int)((color2.getGreen() - color1.getGreen()) * point + color1.getGreen()), (int)((color2.getBlue() - color1.getBlue()) * point + color1.getBlue()));
    }
    
    public static Color interpolateColorsDynamic(final int speed, final int index, final Color start, final Color end) {
        int angle = (int)((System.currentTimeMillis() / speed + index) % 360L);
        angle = ((angle >= 180) ? (360 - angle) : angle) * 2;
        return interpolateColors(start, end, angle / 360.0f);
    }
}
