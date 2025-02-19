/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.util;

import java.io.OutputStream;
import java.io.PrintStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingPrintStream
extends PrintStream {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String domain;

    public LoggingPrintStream(String domainIn, OutputStream outStream) {
        super(outStream);
        this.domain = domainIn;
    }

    @Override
    public void println(String p_println_1_) {
        this.logString(p_println_1_);
    }

    @Override
    public void println(Object p_println_1_) {
        this.logString(String.valueOf(p_println_1_));
    }

    private void logString(String string) {
        StackTraceElement[] astacktraceelement = Thread.currentThread().getStackTrace();
        StackTraceElement stacktraceelement = astacktraceelement[Math.min(3, astacktraceelement.length)];
        LOGGER.info("[{}]@.({}:{}): {}", (Object)this.domain, (Object)stacktraceelement.getFileName(), (Object)stacktraceelement.getLineNumber(), (Object)string);
    }
}

