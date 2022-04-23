/*
 * Decompiled with CFR 0.152.
 */
package org.apache.logging.log4j.spi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.spi.ThreadContextMap;

public class DefaultThreadContextMap
implements ThreadContextMap {
    private final boolean useMap;
    private final ThreadLocal<Map<String, String>> localMap = new InheritableThreadLocal<Map<String, String>>(){

        @Override
        protected Map<String, String> childValue(Map<String, String> parentValue) {
            return parentValue == null || !DefaultThreadContextMap.this.useMap ? null : Collections.unmodifiableMap(new HashMap<String, String>(parentValue));
        }
    };

    public DefaultThreadContextMap(boolean useMap) {
        this.useMap = useMap;
    }

    @Override
    public void put(String key, String value) {
        if (!this.useMap) {
            return;
        }
        Map<String, String> map2 = this.localMap.get();
        map2 = map2 == null ? new HashMap<String, String>() : new HashMap<String, String>(map2);
        map2.put(key, value);
        this.localMap.set(Collections.unmodifiableMap(map2));
    }

    @Override
    public String get(String key) {
        Map<String, String> map2 = this.localMap.get();
        return map2 == null ? null : map2.get(key);
    }

    @Override
    public void remove(String key) {
        Map<String, String> map2 = this.localMap.get();
        if (map2 != null) {
            HashMap<String, String> copy2 = new HashMap<String, String>(map2);
            copy2.remove(key);
            this.localMap.set(Collections.unmodifiableMap(copy2));
        }
    }

    @Override
    public void clear() {
        this.localMap.remove();
    }

    @Override
    public boolean containsKey(String key) {
        Map<String, String> map2 = this.localMap.get();
        return map2 != null && map2.containsKey(key);
    }

    @Override
    public Map<String, String> getCopy() {
        Map<String, String> map2 = this.localMap.get();
        return map2 == null ? new HashMap<String, String>() : new HashMap<String, String>(map2);
    }

    @Override
    public Map<String, String> getImmutableMapOrNull() {
        return this.localMap.get();
    }

    @Override
    public boolean isEmpty() {
        Map<String, String> map2 = this.localMap.get();
        return map2 == null || map2.size() == 0;
    }

    public String toString() {
        Map<String, String> map2 = this.localMap.get();
        return map2 == null ? "{}" : map2.toString();
    }
}

