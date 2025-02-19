/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.lang3.math;

public class IEEE754rUtils {
    public static double min(double[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        double min = array[0];
        for (int i = 1; i < array.length; ++i) {
            min = IEEE754rUtils.min(array[i], min);
        }
        return min;
    }

    public static float min(float[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        float min = array[0];
        for (int i = 1; i < array.length; ++i) {
            min = IEEE754rUtils.min(array[i], min);
        }
        return min;
    }

    public static double min(double a2, double b2, double c) {
        return IEEE754rUtils.min(IEEE754rUtils.min(a2, b2), c);
    }

    public static double min(double a2, double b2) {
        if (Double.isNaN(a2)) {
            return b2;
        }
        if (Double.isNaN(b2)) {
            return a2;
        }
        return Math.min(a2, b2);
    }

    public static float min(float a2, float b2, float c) {
        return IEEE754rUtils.min(IEEE754rUtils.min(a2, b2), c);
    }

    public static float min(float a2, float b2) {
        if (Float.isNaN(a2)) {
            return b2;
        }
        if (Float.isNaN(b2)) {
            return a2;
        }
        return Math.min(a2, b2);
    }

    public static double max(double[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        double max = array[0];
        for (int j = 1; j < array.length; ++j) {
            max = IEEE754rUtils.max(array[j], max);
        }
        return max;
    }

    public static float max(float[] array) {
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        float max = array[0];
        for (int j = 1; j < array.length; ++j) {
            max = IEEE754rUtils.max(array[j], max);
        }
        return max;
    }

    public static double max(double a2, double b2, double c) {
        return IEEE754rUtils.max(IEEE754rUtils.max(a2, b2), c);
    }

    public static double max(double a2, double b2) {
        if (Double.isNaN(a2)) {
            return b2;
        }
        if (Double.isNaN(b2)) {
            return a2;
        }
        return Math.max(a2, b2);
    }

    public static float max(float a2, float b2, float c) {
        return IEEE754rUtils.max(IEEE754rUtils.max(a2, b2), c);
    }

    public static float max(float a2, float b2) {
        if (Float.isNaN(a2)) {
            return b2;
        }
        if (Float.isNaN(b2)) {
            return a2;
        }
        return Math.max(a2, b2);
    }
}

