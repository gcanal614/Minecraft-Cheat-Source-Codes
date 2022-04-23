/*
 * Decompiled with CFR 0.152.
 */
package org.apache.logging.log4j.spi;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.AbstractLogger;

public class AbstractLoggerWrapper
extends AbstractLogger {
    protected final AbstractLogger logger;

    public AbstractLoggerWrapper(AbstractLogger logger, String name, MessageFactory messageFactory) {
        super(name, messageFactory);
        this.logger = logger;
    }

    @Override
    public void log(Marker marker, String fqcn, Level level, Message data2, Throwable t) {
        this.logger.log(marker, fqcn, level, data2, t);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String data2) {
        return this.logger.isEnabled(level, marker, data2);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String data2, Throwable t) {
        return this.logger.isEnabled(level, marker, data2, t);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String data2, Object ... p1) {
        return this.logger.isEnabled(level, marker, data2, p1);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Object data2, Throwable t) {
        return this.logger.isEnabled(level, marker, data2, t);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Message data2, Throwable t) {
        return this.logger.isEnabled(level, marker, data2, t);
    }
}

