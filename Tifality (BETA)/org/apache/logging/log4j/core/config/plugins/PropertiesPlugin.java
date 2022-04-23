/*
 * Decompiled with CFR 0.152.
 */
package org.apache.logging.log4j.core.config.plugins;

import java.util.HashMap;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.lookup.Interpolator;
import org.apache.logging.log4j.core.lookup.MapLookup;
import org.apache.logging.log4j.core.lookup.StrLookup;

@Plugin(name="properties", category="Core", printObject=true)
public final class PropertiesPlugin {
    private PropertiesPlugin() {
    }

    @PluginFactory
    public static StrLookup configureSubstitutor(@PluginElement(value="Properties") Property[] properties2, @PluginConfiguration Configuration config) {
        if (properties2 == null) {
            return new Interpolator(null);
        }
        HashMap<String, String> map2 = new HashMap<String, String>(config.getProperties());
        for (Property prop : properties2) {
            map2.put(prop.getName(), prop.getValue());
        }
        return new Interpolator(new MapLookup(map2));
    }
}

