/*
 * Decompiled with CFR 0.152.
 */
package org.apache.logging.log4j.core.config;

import java.util.Map;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.net.Advertiser;

@Plugin(name="default", category="Core", elementType="advertiser", printObject=false)
public class DefaultAdvertiser
implements Advertiser {
    @Override
    public Object advertise(Map<String, String> properties2) {
        return null;
    }

    @Override
    public void unadvertise(Object advertisedObject) {
    }
}

