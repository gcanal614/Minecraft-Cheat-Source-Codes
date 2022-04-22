package me.injusttice.neutron.utils.render;

import net.minecraft.entity.EntityLivingBase;

import java.awt.*;
import java.util.function.Supplier;

public enum ColorUtil {
    BLUE(() -> new Color(116, 202, 255)),
    NICE_BLUE(() -> new Color(116, 202, 255)),
    DARK_PURPLE(() -> new Color(133, 46, 215)),
    GREEN(() -> new Color(0, 255, 138)),
    PURPLE(() -> new Color(198, 139, 255)),
    WHITE(() -> Color.WHITE);

    private Supplier<Color> colorSupplier;

    ColorUtil(Supplier<Color> colorSupplier) {
        this.colorSupplier = colorSupplier;
    }

    public Color fade(Color color) {
        return fade(color, 2, 100);
    }

    public static Color lighterColor(Color baseColor, float factor) {
        return new Color(
                (int)Math.min(255.0F, baseColor.getRed() + factor),
                (int)Math.min(255.0F, baseColor.getGreen() + factor),
                (int)Math.min(255.0F, baseColor.getBlue() + factor), baseColor
                .getAlpha());
    }

    public static int normalRainbow(float speed, float saturation, float brightness) {
        float hue = (float)(System.currentTimeMillis() % (int)(speed * 1000.0F)) / speed * 1000.0F;
        return Color.HSBtoRGB(hue, saturation, brightness);
    }

    public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        if (fractions.length == colors.length) {
            int[] indices = getFractionIndices(fractions, progress);
            float[] range = new float[]{fractions[indices[0]], fractions[indices[1]]};
            Color[] colorRange = new Color[]{colors[indices[0]], colors[indices[1]]};
            float max = range[1] - range[0];
            float value = progress - range[0];
            float weight = value / max;
            Color color = blend(colorRange[0], colorRange[1], (double)(1.0F - weight));
            return color;
        } else {
            throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
        }
    }

    public static Color changeAlpha(Color baseColor, int newAlpha) {
        return new Color(baseColor
                .getRed(), baseColor.getGreen(), baseColor.getBlue(), newAlpha);
    }

    public static Color darkerColor(Color baseColor, float factor) {
        return new Color(
                (int)Math.max(0.0F, baseColor.getRed() - factor),
                (int)Math.max(0.0F, baseColor.getGreen() - factor),
                (int)Math.max(0.0F, baseColor.getBlue() - factor), baseColor
                .getAlpha());
    }

    public static Color getHealthColor(EntityLivingBase entityLivingBase) {
        float health = entityLivingBase.getHealth();
        float[] fractions = new float[]{0.0F, 0.15f, .55F, 0.7f, .9f};
        Color[] colors = new Color[]{new Color(133, 0, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
        float progress = health / entityLivingBase.getMaxHealth();
        return health >= 0.0f ? blendColors(fractions, colors, progress).brighter() : colors[0];
    }

    public static Color getRGB(int speed, int offset) {
        float hue = (System.currentTimeMillis() + offset) % speed;
        return Color.getHSBColor(hue / speed, 0.5f, 1f);
    }

    public static Color getRGB(int speed, int offset, long time) {
        return ColorUtil.getRGB(speed, offset, time, 1.0f);
    }

    public static Color getRGB(int speed, int offset, long time, float s) {
        float hue = (time + offset) % speed;
        return Color.getHSBColor(hue / speed, s, 1.0f);
    }

    public static int waveRainbow(float speed, float saturation, float brightness, long wavefactor) {
        float hue = (float)((System.currentTimeMillis() + wavefactor) % (int)(speed * 1000.0F)) / speed * 1000.0F;
        return Color.HSBtoRGB(hue, saturation, brightness);
    }
    
    public static int[] getFractionIndices(float[] fractions, float progress) {
        int[] range = new int[2];

        int startPoint;
        for(startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
        }

        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }

        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
    }

    public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float)ratio;
        float ir = 1.0F - r;
        float[] rgb1 = color1.getColorComponents(new float[3]);
        float[] rgb2 = color2.getColorComponents(new float[3]);
        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;
        if (red < 0.0F) {
            red = 0.0F;
        } else if (red > 255.0F) {
            red = 255.0F;
        }

        if (green < 0.0F) {
            green = 0.0F;
        } else if (green > 255.0F) {
            green = 255.0F;
        }

        if (blue < 0.0F) {
            blue = 0.0F;
        } else if (blue > 255.0F) {
            blue = 255.0F;
        }

        Color color3 = null;

        try {
            color3 = new Color(red, green, blue);
        } catch (IllegalArgumentException var13) {
        }

        return color3;
    }

    public static Color flash(Color color) {
        return flash(color, 2, 10);
    }

    public static Color fade(Color color, int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs((((System.currentTimeMillis() % 2000) / 1000f + (index / (float) count) * 2F) % 2F) - 1);
        brightness = 0.5f + (0.5f * brightness);
        hsb[2] = brightness % 2F;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static Color fade2(Color color, int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float) (System.currentTimeMillis() % 2000L) / 1000.0F + (float) index / (float) count * 2.0F) % 2.0F - 1.0F);
        brightness = 0.5F + 0.5F * brightness;
        hsb[2] = brightness % 2.0F;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static Color flash(Color color, int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs((((System.currentTimeMillis() % 200) / 500F + (index / (float) count) * 2F) % 2F) - 1);
        brightness = 0.5f + (0.5f * brightness);
        hsb[2] = brightness % 2F;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static Color rainbow(int index, double speed) {
        int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
        float hue = angle / 360f;
        int color = Color.HSBtoRGB(hue, 1, 1);
        return new Color(color);
    }

    public static Color astolfo(int index, int speed, float saturation, float brightness, float opacity) {
        int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
        angle = (angle > 180 ? 360 - angle : angle) + 180;
        float hue = angle / 360f;

        int color = Color.HSBtoRGB(brightness, saturation, hue);
        Color obj = new Color(color);
        return new Color(obj.getRed(), obj.getGreen(), obj.getBlue(), Math.max(0, Math.min(255, (int) (opacity * 255))));
    }

    public static int getAstoGay(int delay, float offset) {
        int yStart = 20;
        float speed = 3000f;
        float hue = (float) (System.currentTimeMillis() % delay) + (offset);
        while (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
        }
        hue += 0.5F;
        return Color.HSBtoRGB(hue, 0.5F, 1F);
    }

    public static int getRainbow(float seconds, float saturation, float brightness) {
        float hue = (System.currentTimeMillis() % (int)(seconds * 1000)) / (seconds * 1000);
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return color;
    }

    public static int getRainbow(float seconds, float saturation, float brightness, long index) {
        float hue = ((System.currentTimeMillis() + index) % (int)(seconds * 1000)) / (float)(seconds * 1000);
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return color;
    }

    public static Color getAstoGayColor(int delay, float offset) {
        int yStart = 20;
        float speed = 3000f;
        float index = 0.3f;
        float hue = (float) (System.currentTimeMillis() % delay) + (offset);
        while (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
        }
        hue += 0.5F;
        return Color.getHSBColor(hue, 0.5F, 1F);
    }

    public static Color rainbow(int index, int speed, float saturation, float brightness, float opacity) {
        int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
        float hue = angle / 360f;
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        Color obj = new Color(color);
        return new Color(obj.getRed(), obj.getGreen(), obj.getBlue(), Math.max(0, Math.min(255, (int) (opacity * 255))));
    }
}
