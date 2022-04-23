/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.config;

import com.google.gson.JsonObject;

public interface Serializable {
    public JsonObject save();

    public void load(JsonObject var1);
}

