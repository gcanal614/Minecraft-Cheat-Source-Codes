package me.injusttice.neutron.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;

public class MathUtils {

    private static Random rng;

    public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }

    public static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double toPercentage(double current, double max) {
        return current / max;
    }

    public static double distance(float x, float y, float x1, float y1) {
        return Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
    }

    public static double clamp(double value, double minimum, double maximum) {
        return (value > maximum) ? maximum : ((value < minimum) ? minimum : value);
    }

    public static int getRandom(int floor, int cap) {
        return floor + getRNG().nextInt(cap - floor + 1);
    }

    public static Random getRNG() {
        return MathUtils.rng;
    }

    public static double square(double motionX) {
        motionX *= motionX;
        return motionX;
    }

    public static double roundToDecimalPlace(double value, double inc) {
        double halfOfInc = inc / 2.0;
        double floored = Math.floor(value / inc) * inc;
        if (value >= floored + halfOfInc) {
            return new BigDecimal(Math.ceil(value / inc) * inc, MathContext.DECIMAL64).stripTrailingZeros().doubleValue();
        }
        return new BigDecimal(floored, MathContext.DECIMAL64).stripTrailingZeros().doubleValue();
    }

    public static double getIncremental(double val, double inc) {
        double one = 1.0 / inc;
        return Math.round(val * one) / one;
    }

    public static double preciseRound(double value, double precision) {
        double scale = Math.pow(10.0, precision);
        return Math.round(value * scale) / scale;
    }

    public static double round(double num, double increment) {
        if (increment < 0.0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(num);
        bd = bd.setScale((int)increment, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static float[] constrainAngle(float[] vector) {
        vector[0] %= 360.0f;
        vector[1] %= 360.0f;
        while (vector[0] <= -180.0f) {
            vector[0] += 360.0f;
        }
        while (vector[1] <= -180.0f) {
            vector[1] += 360.0f;
        }
        while (vector[0] > 180.0f) {
            vector[0] -= 360.0f;
        }
        while (vector[1] > 180.0f) {
            vector[1] -= 360.0f;
        }
        return vector;
    }

    public static double roundWithPrecision(double val, int precision) {
        int scale = (int)Math.pow(10.0, precision);
        return Math.round(val * scale) / (double)scale;
    }

    public static float randomFloatValue() {
        return (float)getRandomInRange(2.96219E-7, 9.13303E-6);
    }

    static {
        MathUtils.rng = new Random();
    }
    
    public static double getRandomInRange(double min, double max) {
        Random random = new Random();
        double range = max - min;
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
}
