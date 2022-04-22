/*
 * Decompiled with CFR 0.152.
 */
package de.jo.scripting.base.copy;

import de.fanta.Client;
import de.jo.scripting.base.copy.Script;
import de.jo.scripting.base.copy.ScriptExecutor;
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
        Thread ticker = new Thread(new Runnable(){

            /*
             * Unable to fully structure code
             */
            @Override
            public void run() {
                lastMS = 0L;
                block0: while (true) {
                    if (System.currentTimeMillis() - lastMS < 50L) {
                        continue;
                    }
                    lastMS = System.currentTimeMillis();
                    var4_3 = ScriptEngine.access$0(ScriptEngine.this).iterator();
                    while (true) {
                        if (var4_3.hasNext()) ** break;
                        continue block0;
                        script = (Script)var4_3.next();
                        if (!script.isTick()) continue;
                        ScriptEngine.this.getExecutor().execute(script);
                    }
                    break;
                }
            }
        });
        ticker.start();
    }

    public ScriptExecutor getExecutor() {
        return this.executor;
    }

    public List<Script> getScripts() {
        return this.scripts;
    }

    static /* synthetic */ List access$0(ScriptEngine scriptEngine) {
        return scriptEngine.scripts;
    }
}

