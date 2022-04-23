/*
 * Decompiled with CFR 0.152.
 */
package org.json;

import java.util.Enumeration;
import java.util.Properties;
import org.json.JSONException;
import org.json.JSONObject;

public class Property {
    public static JSONObject toJSONObject(Properties properties2) throws JSONException {
        JSONObject jo = new JSONObject();
        if (properties2 != null && !properties2.isEmpty()) {
            Enumeration<?> enumProperties = properties2.propertyNames();
            while (enumProperties.hasMoreElements()) {
                String name = (String)enumProperties.nextElement();
                jo.put(name, properties2.getProperty(name));
            }
        }
        return jo;
    }

    public static Properties toProperties(JSONObject jo) throws JSONException {
        Properties properties2 = new Properties();
        if (jo != null) {
            for (String key : jo.keySet()) {
                Object value = jo.opt(key);
                if (JSONObject.NULL.equals(value)) continue;
                properties2.put(key, value.toString());
            }
        }
        return properties2;
    }
}

