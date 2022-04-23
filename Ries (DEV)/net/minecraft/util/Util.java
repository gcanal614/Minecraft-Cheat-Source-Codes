/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.apache.logging.log4j.Logger;

public class Util {
    public static EnumOS getOSType() {
        String s = System.getProperty("os.name").toLowerCase();
        return s.contains("win") ? EnumOS.WINDOWS : (s.contains("mac") ? EnumOS.OSX : (s.contains("solaris") ? EnumOS.SOLARIS : (s.contains("sunos") ? EnumOS.SOLARIS : (s.contains("linux") ? EnumOS.LINUX : (s.contains("unix") ? EnumOS.LINUX : EnumOS.UNKNOWN)))));
    }

    public static <V> void func_181617_a(FutureTask<V> p_181617_0_, Logger p_181617_1_) {
        try {
            p_181617_0_.run();
            p_181617_0_.get();
        }
        catch (ExecutionException executionexception) {
            if (executionexception.getCause() instanceof OutOfMemoryError) {
                throw (OutOfMemoryError)executionexception.getCause();
            }
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    public static enum EnumOS {
        LINUX,
        SOLARIS,
        WINDOWS,
        OSX,
        UNKNOWN;

    }
}

