/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.collect.Maps
 *  com.google.common.io.Files
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonParser
 *  org.apache.commons.io.IOUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.resources;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Map;
import net.minecraft.util.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceIndex {
    private static final Logger logger = LogManager.getLogger();
    private final Map<String, File> resourceMap;

    public ResourceIndex(File p_i1047_1_, String p_i1047_2_) {
        block9: {
            this.resourceMap = Maps.newHashMap();
            if (p_i1047_2_ != null) {
                File file1 = new File(p_i1047_1_, "objects");
                File file2 = new File(p_i1047_1_, "indexes/" + p_i1047_2_ + ".json");
                BufferedReader bufferedreader = null;
                try {
                    bufferedreader = Files.newReader((File)file2, (Charset)Charsets.UTF_8);
                    JsonObject jsonobject = new JsonParser().parse((Reader)bufferedreader).getAsJsonObject();
                    JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, "objects", null);
                    if (jsonobject1 != null) {
                        for (Map.Entry entry : jsonobject1.entrySet()) {
                            JsonObject jsonobject2 = (JsonObject)entry.getValue();
                            String s = (String)entry.getKey();
                            String[] astring = s.split("/", 2);
                            String s1 = astring.length == 1 ? astring[0] : String.valueOf(astring[0]) + ":" + astring[1];
                            String s2 = JsonUtils.getString(jsonobject2, "hash");
                            File file3 = new File(file1, String.valueOf(s2.substring(0, 2)) + "/" + s2);
                            this.resourceMap.put(s1, file3);
                        }
                    }
                }
                catch (JsonParseException var20) {
                    logger.error("Unable to parse resource index file: " + file2);
                    IOUtils.closeQuietly((Reader)bufferedreader);
                    break block9;
                }
                catch (FileNotFoundException var21) {
                    try {
                        logger.error("Can't find the resource index file: " + file2);
                    }
                    catch (Throwable throwable) {
                        IOUtils.closeQuietly(bufferedreader);
                        throw throwable;
                    }
                    IOUtils.closeQuietly((Reader)bufferedreader);
                    break block9;
                }
                IOUtils.closeQuietly((Reader)bufferedreader);
            }
        }
    }

    public Map<String, File> getResourceMap() {
        return this.resourceMap;
    }
}

