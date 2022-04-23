/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.authlib.properties;

import com.google.common.collect.ForwardingMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.properties.Property;
import java.lang.reflect.Type;
import java.util.Map;

public class PropertyMap
extends ForwardingMultimap<String, Property> {
    private final Multimap<String, Property> properties = LinkedHashMultimap.create();

    @Override
    protected Multimap<String, Property> delegate() {
        return this.properties;
    }

    public static class Serializer
    implements JsonSerializer<PropertyMap>,
    JsonDeserializer<PropertyMap> {
        @Override
        public PropertyMap deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            PropertyMap result2;
            block5: {
                block4: {
                    result2 = new PropertyMap();
                    if (!(json instanceof JsonObject)) break block4;
                    JsonObject object = (JsonObject)json;
                    for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                        if (!(entry.getValue() instanceof JsonArray)) continue;
                        for (JsonElement element : (JsonArray)entry.getValue()) {
                            result2.put(entry.getKey(), new Property(entry.getKey(), element.getAsString()));
                        }
                    }
                    break block5;
                }
                if (!(json instanceof JsonArray)) break block5;
                for (JsonElement element : (JsonArray)json) {
                    if (!(element instanceof JsonObject)) continue;
                    JsonObject object = (JsonObject)element;
                    String name = object.getAsJsonPrimitive("name").getAsString();
                    String value = object.getAsJsonPrimitive("value").getAsString();
                    if (object.has("signature")) {
                        result2.put(name, new Property(name, value, object.getAsJsonPrimitive("signature").getAsString()));
                        continue;
                    }
                    result2.put(name, new Property(name, value));
                }
            }
            return result2;
        }

        @Override
        public JsonElement serialize(PropertyMap src, Type typeOfSrc, JsonSerializationContext context) {
            JsonArray result2 = new JsonArray();
            for (Property property : src.values()) {
                JsonObject object = new JsonObject();
                object.addProperty("name", property.getName());
                object.addProperty("value", property.getValue());
                if (property.hasSignature()) {
                    object.addProperty("signature", property.getSignature());
                }
                result2.add(object);
            }
            return result2;
        }
    }
}

