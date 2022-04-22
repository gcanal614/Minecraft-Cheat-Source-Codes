/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 */
package de.fanta.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class JsonUtil {
    private static Gson gson = new GsonBuilder().registerTypeAdapter(Double.class, (Object)new JsonSerializer<Double>(){

        public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
            if (src % 1.0 != 0.0) {
                return new JsonPrimitive((Number)src);
            }
            Integer value = (int)Math.round(src);
            return new JsonPrimitive((Number)value);
        }
    }).excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().enableComplexMapKeySerialization().serializeNulls().create();

    public static <T> T getObject(Class<T> c, File file) {
        block4: {
            if (file.exists()) break block4;
            return null;
        }
        try {
            String line;
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder b = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                b.append(line);
            }
            bufferedReader.close();
            reader.close();
            fileInputStream.close();
            return (T)gson.fromJson(b.toString(), c);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static <T> T getObject(Class<T> c, String json) {
        try {
            return (T)gson.fromJson(json, c);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static boolean writeObjectToFile(Object object, File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(gson.toJson(object).getBytes());
            outputStream.flush();
            outputStream.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getObjectAsString(Object o) {
        return gson.toJson(o);
    }

    public static Gson getGson() {
        return gson;
    }
}

