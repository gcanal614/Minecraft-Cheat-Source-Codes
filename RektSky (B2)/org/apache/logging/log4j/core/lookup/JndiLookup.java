package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;
import javax.naming.*;

@Plugin(name = "jndi", category = "Lookup")
public class JndiLookup implements StrLookup
{
    static final String CONTAINER_JNDI_RESOURCE_PATH_PREFIX = "java:comp/env/";
    
    @Override
    public String lookup(final String key) {
        return this.lookup(null, key);
    }
    
    @Override
    public String lookup(final LogEvent event, final String key) {
        if (key == null) {
            return null;
        }
        try {
            final InitialContext ctx = new InitialContext();
            return (String)ctx.lookup(this.convertJndiName(key));
        }
        catch (NamingException e) {
            return null;
        }
    }
    
    private String convertJndiName(String jndiName) {
        if (!jndiName.startsWith("java:comp/env/") && jndiName.indexOf(58) == -1) {
            jndiName = "java:comp/env/" + jndiName;
        }
        return jndiName;
    }
}
