package org.apache.logging.log4j.core.config;

import org.apache.logging.log4j.core.layout.*;
import org.apache.logging.log4j.core.pattern.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.util.*;
import java.io.*;
import org.apache.logging.log4j.core.*;

public class DefaultConfiguration extends BaseConfiguration
{
    public static final String DEFAULT_NAME = "Default";
    public static final String DEFAULT_LEVEL = "org.apache.logging.log4j.level";
    
    public DefaultConfiguration() {
        this.setName("Default");
        final Layout<? extends Serializable> layout = PatternLayout.createLayout("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n", null, null, null, null);
        final Appender appender = ConsoleAppender.createAppender(layout, null, "SYSTEM_OUT", "Console", "false", "true");
        appender.start();
        this.addAppender(appender);
        final LoggerConfig root = this.getRootLogger();
        root.addAppender(appender, null, null);
        final String levelName = PropertiesUtil.getProperties().getStringProperty("org.apache.logging.log4j.level");
        final Level level = (levelName != null && Level.valueOf(levelName) != null) ? Level.valueOf(levelName) : Level.ERROR;
        root.setLevel(level);
    }
    
    @Override
    protected void doConfigure() {
    }
}
