/*
 * Decompiled with CFR 0.152.
 */
package org.apache.logging.log4j.core.appender.db.nosql.couch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.core.appender.db.nosql.NoSQLObject;

public final class CouchDBObject
implements NoSQLObject<Map<String, Object>> {
    private final Map<String, Object> map = new HashMap<String, Object>();

    @Override
    public void set(String field, Object value) {
        this.map.put(field, value);
    }

    @Override
    public void set(String field, NoSQLObject<Map<String, Object>> value) {
        this.map.put(field, value.unwrap());
    }

    @Override
    public void set(String field, Object[] values2) {
        this.map.put(field, Arrays.asList(values2));
    }

    @Override
    public void set(String field, NoSQLObject<Map<String, Object>>[] values2) {
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (NoSQLObject<Map<String, Object>> value : values2) {
            list.add(value.unwrap());
        }
        this.map.put(field, list);
    }

    @Override
    public Map<String, Object> unwrap() {
        return this.map;
    }
}

