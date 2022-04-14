package org.apache.logging.log4j.core;

import java.io.*;

public interface Appender extends LifeCycle
{
    void append(final LogEvent p0);
    
    String getName();
    
    Layout<? extends Serializable> getLayout();
    
    boolean ignoreExceptions();
    
    ErrorHandler getHandler();
    
    void setHandler(final ErrorHandler p0);
}
