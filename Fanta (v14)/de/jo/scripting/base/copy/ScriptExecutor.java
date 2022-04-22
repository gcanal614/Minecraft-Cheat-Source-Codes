/*
 * Decompiled with CFR 0.152.
 */
package de.jo.scripting.base.copy;

import de.fanta.Client;
import de.jo.scripting.base.copy.Script;
import java.io.File;
import java.io.FileReader;
import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import net.minecraft.client.Minecraft;

public class ScriptExecutor {
    private ScriptEngine js = new ScriptEngineManager().getEngineByName("nashorn");
    private Bindings bindings = this.js.getBindings(100);

    public ScriptExecutor() {
        this.registerVariable("sysout", System.out, true);
        this.registerVariable("syserr", System.err, true);
        this.registerVariable("pi", Math.PI, true);
        this.registerVariable("mc", Minecraft.getMinecraft(), true);
        this.registerVariable("client", Client.INSTANCE, true);
        this.registerVariable("modulemanager", Client.INSTANCE.moduleManager, true);
        this.registerLine("var Module = Java.extend(Java.type(\"de.fanta.module.Module\"))");
        this.registerLine("var Type = Java.type(\"de.fanta.module.Module.Type\")");
        this.registerLine("var Color = Java.type(\"java.awt.Color\")");
    }

    public Object execute(String cmd) {
        try {
            return this.js.eval(cmd);
        }
        catch (Exception e) {
            System.err.println("An Error occurred!");
            e.printStackTrace();
            return null;
        }
    }

    public Object execute(File file) {
        try {
            return this.js.eval(new FileReader(file));
        }
        catch (Exception e) {
            System.err.println("An Error occurred!");
            e.printStackTrace();
            return null;
        }
    }

    public Object execute(Script script) {
        try {
            return this.js.eval(new FileReader(script.getFile()));
        }
        catch (Exception e) {
            System.err.println("An Error occurred!");
            System.err.println("At the Script: " + script.getName());
            e.printStackTrace();
            return null;
        }
    }

    public boolean registerVariable(String var, Object obj, boolean overwrite) {
        block5: {
            block4: {
                try {
                    if (!this.bindings.containsKey(var) || overwrite) break block4;
                    System.out.println("This variable already exists!");
                    return false;
                }
                catch (Exception e) {
                    System.err.println("An Error occurred!");
                    return true;
                }
            }
            if (!this.bindings.containsKey(var)) break block5;
            this.bindings.remove(var);
            this.bindings.put(var, obj);
            return true;
        }
        this.bindings.put(var, obj);
        return true;
    }

    public boolean registerLine(String line) {
        try {
            this.js.eval(line);
            return true;
        }
        catch (Exception e) {
            System.err.println("An Error occurred!");
            return true;
        }
    }

    public boolean removeVariable(String var) {
        block3: {
            try {
                if (this.bindings.containsKey(var)) break block3;
                return false;
            }
            catch (Exception e) {
                System.err.println("An Error occurred!");
                return false;
            }
        }
        this.bindings.remove(var);
        return true;
    }
}

