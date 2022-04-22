/*
 * Decompiled with CFR 0.152.
 */
package de.jo.scripting.base;

import de.fanta.Client;
import de.jo.scripting.base.Script;
import de.jo.scripting.base.ScriptExecutor;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;

public class ScriptEngine {
    private Minecraft mc = Minecraft.getMinecraft();
    public static final File DIR = new File(Minecraft.getMinecraft().mcDataDir, String.valueOf(Client.INSTANCE.name) + "/scripts");
    private List<Script> scripts;
    private ScriptExecutor executor;

    public ScriptEngine() {
        if (!DIR.exists() || !DIR.isDirectory()) {
            DIR.mkdir();
        }
        if (this.scripts == null) {
            this.scripts = new ArrayList<Script>();
        }
        if (this.executor == null) {
            this.executor = new ScriptExecutor();
        }
        if (DIR.listFiles().length != 0) {
            File[] fileArray = DIR.listFiles();
            int n = fileArray.length;
            int n2 = 0;
            while (n2 < n) {
                File file = fileArray[n2];
                if (file.getName().endsWith(".js")) {
                    Script script = new Script(file);
                    this.scripts.add(script);
                }
                ++n2;
            }
        }
        for (Script script : this.scripts) {
            this.getExecutor().execute(script);
        }
    }

    public ScriptExecutor getExecutor() {
        return this.executor;
    }

    public List<Script> getScripts() {
        return this.scripts;
    }
}

