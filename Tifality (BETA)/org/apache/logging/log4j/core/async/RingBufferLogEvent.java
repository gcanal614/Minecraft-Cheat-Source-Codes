/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.EventFactory
 */
package org.apache.logging.log4j.core.async;

import com.lmax.disruptor.EventFactory;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.async.AsyncLogger;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.SimpleMessage;

public class RingBufferLogEvent
implements LogEvent {
    private static final long serialVersionUID = 8462119088943934758L;
    public static final Factory FACTORY = new Factory();
    private AsyncLogger asyncLogger;
    private String loggerName;
    private Marker marker;
    private String fqcn;
    private Level level;
    private Message message;
    private Throwable thrown;
    private Map<String, String> contextMap;
    private ThreadContext.ContextStack contextStack;
    private String threadName;
    private StackTraceElement location;
    private long currentTimeMillis;
    private boolean endOfBatch;
    private boolean includeLocation;

    public void setValues(AsyncLogger asyncLogger, String loggerName, Marker marker, String fqcn, Level level, Message data2, Throwable t, Map<String, String> map2, ThreadContext.ContextStack contextStack, String threadName, StackTraceElement location, long currentTimeMillis) {
        this.asyncLogger = asyncLogger;
        this.loggerName = loggerName;
        this.marker = marker;
        this.fqcn = fqcn;
        this.level = level;
        this.message = data2;
        this.thrown = t;
        this.contextMap = map2;
        this.contextStack = contextStack;
        this.threadName = threadName;
        this.location = location;
        this.currentTimeMillis = currentTimeMillis;
    }

    public void execute(boolean endOfBatch) {
        this.endOfBatch = endOfBatch;
        this.asyncLogger.actualAsyncLog(this);
    }

    @Override
    public boolean isEndOfBatch() {
        return this.endOfBatch;
    }

    @Override
    public void setEndOfBatch(boolean endOfBatch) {
        this.endOfBatch = endOfBatch;
    }

    @Override
    public boolean isIncludeLocation() {
        return this.includeLocation;
    }

    @Override
    public void setIncludeLocation(boolean includeLocation) {
        this.includeLocation = includeLocation;
    }

    @Override
    public String getLoggerName() {
        return this.loggerName;
    }

    @Override
    public Marker getMarker() {
        return this.marker;
    }

    @Override
    public String getFQCN() {
        return this.fqcn;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    @Override
    public Message getMessage() {
        if (this.message == null) {
            this.message = new SimpleMessage("");
        }
        return this.message;
    }

    @Override
    public Throwable getThrown() {
        return this.thrown;
    }

    @Override
    public Map<String, String> getContextMap() {
        return this.contextMap;
    }

    @Override
    public ThreadContext.ContextStack getContextStack() {
        return this.contextStack;
    }

    @Override
    public String getThreadName() {
        return this.threadName;
    }

    @Override
    public StackTraceElement getSource() {
        return this.location;
    }

    @Override
    public long getMillis() {
        return this.currentTimeMillis;
    }

    public void mergePropertiesIntoContextMap(Map<Property, Boolean> properties2, StrSubstitutor strSubstitutor) {
        if (properties2 == null) {
            return;
        }
        HashMap<String, String> map2 = this.contextMap == null ? new HashMap<String, String>() : new HashMap<String, String>(this.contextMap);
        for (Map.Entry<Property, Boolean> entry : properties2.entrySet()) {
            Property prop = entry.getKey();
            if (map2.containsKey(prop.getName())) continue;
            String value = entry.getValue() != false ? strSubstitutor.replace(prop.getValue()) : prop.getValue();
            map2.put(prop.getName(), value);
        }
        this.contextMap = map2;
    }

    public void clear() {
        this.setValues(null, null, null, null, null, null, null, null, null, null, null, 0L);
    }

    private static class Factory
    implements EventFactory<RingBufferLogEvent> {
        private Factory() {
        }

        public RingBufferLogEvent newInstance() {
            return new RingBufferLogEvent();
        }
    }
}

