// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.alt;

import bozoware.base.BozoWare;
import java.util.Set;
import java.io.FileNotFoundException;
import java.util.Map;
import java.io.Reader;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.util.Iterator;
import java.io.IOException;
import java.io.FileWriter;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.util.ArrayList;

public class AltManager
{
    public static Alt lastAlt;
    public static ArrayList<Alt> registry;
    private static File altsFile;
    
    public ArrayList<Alt> getRegistry() {
        return AltManager.registry;
    }
    
    public void setLastAlt(final Alt alt2) {
        AltManager.lastAlt = alt2;
    }
    
    public void onExit() {
        final JsonObject saveJson = new JsonObject();
        for (final Alt alt : this.getRegistry()) {
            final JsonObject altJson = new JsonObject();
            altJson.addProperty("email", alt.getUsername());
            altJson.addProperty("password", alt.getPassword());
            saveJson.add(alt.getUsername(), (JsonElement)altJson);
        }
        final String contentPrettyPrint = new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)saveJson);
        try {
            final FileWriter fileWriter = new FileWriter(AltManager.altsFile);
            fileWriter.write(contentPrettyPrint);
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void onStart() {
        if (AltManager.altsFile.length() > 0L) {
            try {
                final FileReader reader = new FileReader(AltManager.altsFile);
                final JsonParser jsonParser = new JsonParser();
                final JsonObject object = (JsonObject)jsonParser.parse((Reader)reader);
                final Set<Map.Entry<String, JsonElement>> entrySet = (Set<Map.Entry<String, JsonElement>>)object.entrySet();
                for (final Map.Entry<String, JsonElement> entry : entrySet) {
                    if (object.has((String)entry.getKey()) && object.getAsJsonObject((String)entry.getKey()).has("email") && object.getAsJsonObject((String)entry.getKey()).has("password")) {
                        this.getRegistry().add(new Alt(object.getAsJsonObject((String)entry.getKey()).get("email").getAsString(), object.getAsJsonObject((String)entry.getKey()).get("password").getAsString()));
                    }
                }
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    
    static {
        AltManager.altsFile = new File(BozoWare.getInstance().getFileManager().getClientDirectory() + "/alts/alts.json");
        AltManager.registry = new ArrayList<Alt>();
        BozoWare.getInstance().getFileManager().addSubDirectory("alts");
        if (!AltManager.altsFile.exists()) {
            try {
                AltManager.altsFile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
