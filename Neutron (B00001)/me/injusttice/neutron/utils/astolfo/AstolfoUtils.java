package me.injusttice.neutron.utils.astolfo;

import java.awt.*;

public class AstolfoUtils {

    public static int rainbow(int count, float bright, float st) {
        double v1 = Math.ceil(System.currentTimeMillis() + (long) (count * 109)) / 5;
        return Color.getHSBColor((double) ((float) ((v1 %= 360.0) / 360.0)) < 0.5 ? -((float) (v1 / 360.0)) : (float) (v1 / 360.0), st, bright).getRGB();
    }
}
