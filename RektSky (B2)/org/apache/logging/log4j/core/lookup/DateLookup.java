package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.*;
import java.text.*;
import java.util.*;
import org.apache.logging.log4j.status.*;

@Plugin(name = "date", category = "Lookup")
public class DateLookup implements StrLookup
{
    private static final Logger LOGGER;
    
    @Override
    public String lookup(final String key) {
        return this.formatDate(System.currentTimeMillis(), key);
    }
    
    @Override
    public String lookup(final LogEvent event, final String key) {
        return this.formatDate(event.getMillis(), key);
    }
    
    private String formatDate(final long date, final String format) {
        DateFormat dateFormat = null;
        if (format != null) {
            try {
                dateFormat = new SimpleDateFormat(format);
            }
            catch (Exception ex) {
                DateLookup.LOGGER.error("Invalid date format: \"" + format + "\", using default", ex);
            }
        }
        if (dateFormat == null) {
            dateFormat = DateFormat.getInstance();
        }
        return dateFormat.format(new Date(date));
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
    }
}
