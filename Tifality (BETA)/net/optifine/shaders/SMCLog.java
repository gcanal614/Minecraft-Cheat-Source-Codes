/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.shaders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SMCLog {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PREFIX = "[Shaders] ";

    public static void severe(String message) {
        LOGGER.error(PREFIX + message);
    }

    public static void warning(String message) {
        LOGGER.warn(PREFIX + message);
    }

    public static void info(String message) {
        LOGGER.info(PREFIX + message);
    }

    public static void fine(String message) {
        LOGGER.debug(PREFIX + message);
    }

    public static void severe(String format, Object ... args2) {
        String s = String.format(format, args2);
        LOGGER.error(PREFIX + s);
    }

    public static void warning(String format, Object ... args2) {
        String s = String.format(format, args2);
        LOGGER.warn(PREFIX + s);
    }

    public static void info(String format, Object ... args2) {
        String s = String.format(format, args2);
        LOGGER.info(PREFIX + s);
    }

    public static void fine(String format, Object ... args2) {
        String s = String.format(format, args2);
        LOGGER.debug(PREFIX + s);
    }
}

