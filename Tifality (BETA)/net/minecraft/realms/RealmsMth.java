/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ns
 */
package net.minecraft.realms;

import java.util.Random;
import org.apache.commons.lang3.StringUtils;

public class RealmsMth {
    public static float sin(float f) {
        return ns.a((float)f);
    }

    public static double nextDouble(Random random, double d, double d2) {
        return ns.a((Random)random, (double)d, (double)d2);
    }

    public static int ceil(float f) {
        return ns.f((float)f);
    }

    public static int floor(double d) {
        return ns.c((double)d);
    }

    public static int intFloorDiv(int n, int n2) {
        return ns.a((int)n, (int)n2);
    }

    public static float abs(float f) {
        return ns.e((float)f);
    }

    public static int clamp(int n, int n2, int n3) {
        return ns.a((int)n, (int)n2, (int)n3);
    }

    public static double clampedLerp(double d, double d2, double d3) {
        return ns.b((double)d, (double)d2, (double)d3);
    }

    public static int ceil(double d) {
        return ns.f((double)d);
    }

    public static boolean isEmpty(String string) {
        return StringUtils.isEmpty(string);
    }

    public static long lfloor(double d) {
        return ns.d((double)d);
    }

    public static float sqrt(double d) {
        return ns.a((double)d);
    }

    public static double clamp(double d, double d2, double d3) {
        return ns.a((double)d, (double)d2, (double)d3);
    }

    public static int getInt(String string, int n) {
        return ns.a((String)string, (int)n);
    }

    public static double getDouble(String string, double d) {
        return ns.a((String)string, (double)d);
    }

    public static int log2(int n) {
        return ns.c((int)n);
    }

    public static int absFloor(double d) {
        return ns.e((double)d);
    }

    public static int smallestEncompassingPowerOfTwo(int n) {
        return ns.b((int)n);
    }

    public static float sqrt(float f) {
        return ns.c((float)f);
    }

    public static float cos(float f) {
        return ns.b((float)f);
    }

    public static int getInt(String string, int n, int n2) {
        return ns.a((String)string, (int)n, (int)n2);
    }

    public static int fastFloor(double d) {
        return ns.b((double)d);
    }

    public static double absMax(double d, double d2) {
        return ns.a((double)d, (double)d2);
    }

    public static float nextFloat(Random random, float f, float f2) {
        return ns.a((Random)random, (float)f, (float)f2);
    }

    public static double wrapDegrees(double d) {
        return ns.g((double)d);
    }

    public static float wrapDegrees(float f) {
        return ns.g((float)f);
    }

    public static float clamp(float f, float f2, float f3) {
        return ns.a((float)f, (float)f2, (float)f3);
    }

    public static double getDouble(String string, double d, double d2) {
        return ns.a((String)string, (double)d, (double)d2);
    }

    public static int roundUp(int n, int n2) {
        return ns.c((int)n, (int)n2);
    }

    public static double average(long[] lArray) {
        return ns.a((long[])lArray);
    }

    public static int floor(float f) {
        return ns.d((float)f);
    }

    public static int abs(int n) {
        return ns.a((int)n);
    }

    public static int nextInt(Random random, int n, int n2) {
        return ns.a((Random)random, (int)n, (int)n2);
    }
}

