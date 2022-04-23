/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.RandomUtils
 */
package me.uncodable.srt.impl.utils;

import me.uncodable.srt.impl.utils.Timer;
import org.apache.commons.lang3.RandomUtils;

public class CombatUtils {
    private static int counter;
    private static final Timer TIMER;

    public static double elevateCPS(int chance, long duration, double randMin, double randMax) {
        if (TIMER.elapsed(duration) && counter > 0) {
            --counter;
            TIMER.reset();
        } else if (counter > 40) {
            counter = 0;
        }
        if (RandomUtils.nextInt((int)0, (int)100) <= chance) {
            ++counter;
        }
        if (counter > 0) {
            return counter % 3 == 0 ? RandomUtils.nextDouble((double)randMin, (double)randMax) : -RandomUtils.nextDouble((double)randMin, (double)randMax);
        }
        return 0.0;
    }

    public static void setCounter(int newVal) {
        counter = newVal;
    }

    static {
        TIMER = new Timer();
    }
}

