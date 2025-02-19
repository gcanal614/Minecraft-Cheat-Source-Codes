/*
 * Decompiled with CFR 0.152.
 */
package org.slf4j.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.spi.MDCAdapter;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BasicMDCAdapter
implements MDCAdapter {
    private InheritableThreadLocal<Map<String, String>> inheritableThreadLocal = new InheritableThreadLocal<Map<String, String>>(){

        @Override
        protected Map<String, String> childValue(Map<String, String> parentValue) {
            if (parentValue == null) {
                return null;
            }
            return new HashMap<String, String>(parentValue);
        }
    };

    @Override
    public void put(String key, String val) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        HashMap<String, String> map2 = (HashMap<String, String>)this.inheritableThreadLocal.get();
        if (map2 == null) {
            map2 = new HashMap<String, String>();
            this.inheritableThreadLocal.set(map2);
        }
        map2.put(key, val);
    }

    @Override
    public String get(String key) {
        Map map2 = (Map)this.inheritableThreadLocal.get();
        if (map2 != null && key != null) {
            return (String)map2.get(key);
        }
        return null;
    }

    @Override
    public void remove(String key) {
        Map map2 = (Map)this.inheritableThreadLocal.get();
        if (map2 != null) {
            map2.remove(key);
        }
    }

    @Override
    public void clear() {
        Map map2 = (Map)this.inheritableThreadLocal.get();
        if (map2 != null) {
            map2.clear();
            this.inheritableThreadLocal.remove();
        }
    }

    public Set<String> getKeys() {
        Map map2 = (Map)this.inheritableThreadLocal.get();
        if (map2 != null) {
            return map2.keySet();
        }
        return null;
    }

    @Override
    public Map<String, String> getCopyOfContextMap() {
        Map oldMap = (Map)this.inheritableThreadLocal.get();
        if (oldMap != null) {
            return new HashMap<String, String>(oldMap);
        }
        return null;
    }

    @Override
    public void setContextMap(Map<String, String> contextMap) {
        this.inheritableThreadLocal.set(new HashMap<String, String>(contextMap));
    }
}

