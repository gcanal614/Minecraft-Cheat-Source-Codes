package net.minecraft.util;

import java.io.*;
import org.apache.logging.log4j.*;

public class LoggingPrintStream extends PrintStream
{
    private static final Logger LOGGER;
    private final String domain;
    
    public LoggingPrintStream(final String p_i45927_1_, final OutputStream p_i45927_2_) {
        super(p_i45927_2_);
        this.domain = p_i45927_1_;
    }
    
    @Override
    public void println(final String p_println_1_) {
        this.logString(p_println_1_);
    }
    
    @Override
    public void println(final Object p_println_1_) {
        this.logString(String.valueOf(p_println_1_));
    }
    
    private void logString(final String p_179882_1_) {
        final StackTraceElement[] var2 = Thread.currentThread().getStackTrace();
        final StackTraceElement var3 = var2[Math.min(3, var2.length)];
        LoggingPrintStream.LOGGER.info("[{}]@.({}:{}): {}", new Object[] { this.domain, var3.getFileName(), var3.getLineNumber(), p_179882_1_ });
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
