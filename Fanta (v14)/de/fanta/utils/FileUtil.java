/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.utils;

import de.fanta.Client;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.BaseSetting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.setting.settings.Slider;
import de.fanta.utils.ChatUtil;
import de.fanta.utils.FriendSystem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.invoke.LambdaMetafactory;
import java.net.URL;
import net.minecraft.client.Minecraft;

public class FileUtil {
    public static boolean isTutorialDone;
    public static File configFile;
    public static File tutorialFile;
    public static File friendFile;
    public String getName;
    public String getTutorialStatus;
    public Minecraft mc = Minecraft.getMinecraft();

    static {
        configFile = new File(Minecraft.getMinecraft().mcDataDir + "/" + Client.INSTANCE.name + "/config.txt");
        tutorialFile = new File(Minecraft.getMinecraft().mcDataDir + "/" + Client.INSTANCE.name + "/tutorial.txt");
        friendFile = new File(Minecraft.getMinecraft().mcDataDir + "/" + Client.INSTANCE.name + "/friends.txt");
    }

    public static void saveTutorial() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(tutorialFile));
            String tutorialString = "isTutorialDone:" + isTutorialDone;
            writer.println(tutorialString);
            writer.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void saveModules() {
        File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Client.INSTANCE.name + "/modules.txt");
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            for (Module module : Client.INSTANCE.moduleManager.modules) {
                String modname = module.name;
                String string = String.valueOf(modname) + ":" + module.state;
                printWriter.println(string);
            }
            printWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveKeys() {
        File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Client.INSTANCE.name + "/keys.txt");
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            for (Module module : Client.INSTANCE.moduleManager.modules) {
                String modulename = module.name;
                int modulekey = module.getKeyBind();
                String endstring = String.valueOf(modulename) + ":" + modulekey;
                writer.println(endstring);
            }
            writer.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void saveFriends() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(friendFile));
            for (String name : FriendSystem.getFriends()) {
                writer.write(String.valueOf(name) + ":" + FriendSystem.getFriends());
                writer.newLine();
            }
            writer.close();
        }
        catch (IOException var4) {
            var4.printStackTrace();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isTutorialDone() {
        try {
            if (!tutorialFile.exists()) return false;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(tutorialFile));
            while (true) {
                String readString;
                if ((readString = bufferedReader.readLine()) == null) {
                    return false;
                }
                String[] split = readString.split(":");
                if (split[1] == null) continue;
                this.getTutorialStatus = split[1];
                if (this.getTutorialStatus.equals("true")) {
                    return true;
                }
                if (this.getTutorialStatus.equals("false")) break;
            }
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void loadModules() {
        try {
            File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Client.INSTANCE.name + "/modules.txt");
            if (!file.exists()) {
                PrintWriter printWriter = new PrintWriter(new FileWriter(file));
                printWriter.println();
                printWriter.close();
            } else if (file.exists()) {
                String readString;
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                while ((readString = bufferedReader.readLine()) != null) {
                    String[] split = readString.split(":");
                    Module mod = Client.INSTANCE.moduleManager.getModule(split[0]);
                    boolean enabled = Boolean.parseBoolean(split[1]);
                    if (mod == null) continue;
                    mod.state = enabled;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadKeys() {
        try {
            File file = new File(Minecraft.getMinecraft().mcDataDir + "/" + Client.INSTANCE.name + "/keys.txt");
            if (!file.exists()) {
                PrintWriter printWriter = new PrintWriter(new FileWriter(file));
                printWriter.println();
                printWriter.close();
            } else if (file.exists()) {
                String readString;
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                while ((readString = bufferedReader.readLine()) != null) {
                    String[] split = readString.split(":");
                    Module mod = Client.INSTANCE.moduleManager.getModule(split[0]);
                    if (mod == null) continue;
                    int key = Integer.parseInt(split[1]);
                    mod.setKeyBind(key);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFriends() {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(friendFile));
            while ((line = reader.readLine()) != null) {
                String[] arguments = line.split(":");
                FriendSystem.addFriend(arguments[0]);
            }
            reader.close();
        }
        catch (FileNotFoundException var4) {
            var4.printStackTrace();
        }
        catch (IOException var5) {
            var5.printStackTrace();
        }
    }

    public static void saveValues(String name, boolean config) {
        File f = new File(Minecraft.getMinecraft().mcDataDir + "/" + Client.INSTANCE.name + (config ? "/configs/" : "/") + name + ".txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            PrintWriter pw = new PrintWriter(f);
            for (Module mod : Client.INSTANCE.moduleManager.modules) {
                pw.println(String.valueOf(mod.name) + ":state:toggled:" + mod.state);
                if (mod.getType().equals((Object)Module.Type.Visual) && config) continue;
                for (Setting setting : mod.settings) {
                    BaseSetting setObject = setting.getSetting();
                    String setName = setting.getName();
                    if (setObject instanceof CheckBox) {
                        pw.println(String.valueOf(mod.name) + ":" + setName + ":b:" + ((CheckBox)setObject).state);
                        continue;
                    }
                    if (setObject instanceof Slider) {
                        pw.println(String.valueOf(mod.name) + ":" + setName + ":d:" + ((Slider)setObject).curValue);
                        continue;
                    }
                    if (!(setObject instanceof DropdownBox)) continue;
                    pw.println(String.valueOf(mod.name) + ":" + setName + ":o:" + ((DropdownBox)setObject).curOption);
                }
            }
            pw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Unable to fully structure code
     */
    public static void loadValues(String name, boolean config, boolean onlineConfig) {
        block26: {
            try {
                f = new File(Minecraft.getMinecraft().mcDataDir + "/" + Client.INSTANCE.name + (config != false ? "/configs/" : "/") + name + ".txt");
                if (!f.exists()) {
                    f.createNewFile();
                }
                br = new BufferedReader(new FileReader(f));
                if (!onlineConfig) ** GOTO lbl65
                thread = new Thread((Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, lambda$0(java.lang.String ), ()V)((String)name));
                thread.start();
                break block26;
lbl-1000:
                // 1 sources

                {
                    values = line.split(":");
                    if (values.length != 4 || (module = Client.INSTANCE.moduleManager.getModule(moduleName = values[0])) == null) continue;
                    setName = values[1];
                    object = values[2];
                    lastValue = values[3];
                    var12_14 = object;
                    tmp = -1;
                    switch (var12_14.hashCode()) {
                        case -1147621488: {
                            if (var12_14.equals("toggled")) {
                                tmp = 1;
                            }
                            break;
                        }
                        case 98: {
                            if (var12_14.equals("b")) {
                                tmp = 2;
                            }
                            break;
                        }
                        case 100: {
                            if (var12_14.equals("d")) {
                                tmp = 3;
                            }
                            break;
                        }
                        case 111: {
                            if (var12_14.equals("o")) {
                                tmp = 4;
                            }
                            break;
                        }
                    }
                    switch (tmp) {
                        case 1: {
                            module.state = Boolean.parseBoolean(lastValue);
                            break;
                        }
                        case 2: {
                            try {
                                ((CheckBox)module.getSetting((String)setName).getSetting()).state = Boolean.parseBoolean(lastValue);
                            }
                            catch (NullPointerException var13_16) {}
                            continue block20;
                        }
                        case 3: {
                            System.out.println("Loading SLider: " + setName + ":" + Double.parseDouble(lastValue));
                            try {
                                ((Slider)module.getSetting((String)setName).getSetting()).curValue = Double.parseDouble(lastValue);
                            }
                            catch (NullPointerException var13_17) {}
                            continue block20;
                        }
                        case 4: {
                            try {
                                if (!(module.getSetting(setName).getSetting() instanceof DropdownBox)) break;
                                ((DropdownBox)module.getSetting((String)setName).getSetting()).curOption = lastValue;
                                break;
                            }
                            catch (NullPointerException var13_18) {
                                // empty catch block
                            }
                        }
                    }
lbl65:
                    // 11 sources

                    ** while ((line = br.readLine()) != null)
                }
lbl66:
                // 1 sources

            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void load() {
        File folder = new File(Minecraft.getMinecraft().mcDataDir + "/" + Client.INSTANCE.name);
        if (!folder.exists()) {
            folder.mkdir();
        }
        FileUtil.loadModules();
        FileUtil.loadKeys();
        this.loadFriends();
        FileUtil.loadValues("values", false, false);
    }

    public static void save() {
        FileUtil.saveModules();
        FileUtil.saveKeys();
        FileUtil.saveFriends();
        FileUtil.saveValues("values", false);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static /* synthetic */ void lambda$0(String var0) {
        ChatUtil.sendChatMessageWithPrefix("Loading...");
        try {
            urlConnection = new URL("https://raw.githubusercontent.com/LCAMODZ/Fanta-configs/main/" + var0.toLowerCase() + ".txt").openConnection();
            urlConnection.setConnectTimeout(10000);
            urlConnection.connect();
            stringBuilder = new StringBuilder();
            var3_4 = null;
            var4_6 = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                try {
                    block26: while (true) lbl-1000:
                    // 11 sources

                    {
                        if ((text = bufferedReader.readLine()) == null) {
                            ChatUtil.sendChatMessageWithPrefix("You successfully loaded the config named " + var0);
                            return;
                        }
                        if (text.contains("404: Not Found")) {
                            ChatUtil.sendChatMessageWithPrefix("An error occurred while loading this config.");
                            return;
                        }
                        values = text.split(":");
                        if (values.length != 4 || (module = Client.INSTANCE.moduleManager.getModule(moduleName = values[0])) == null) continue;
                        setName = values[1];
                        object = values[2];
                        lastValue = values[3];
                        var13_16 = object;
                        tmp = -1;
                        switch (var13_16.hashCode()) {
                            case -1147621488: {
                                if (!var13_16.equals("toggled")) break;
                                tmp = 1;
                                break;
                            }
                            case 98: {
                                if (!var13_16.equals("b")) break;
                                tmp = 2;
                                break;
                            }
                            case 100: {
                                if (!var13_16.equals("d")) break;
                                tmp = 3;
                                break;
                            }
                            case 111: {
                                if (!var13_16.equals("o")) break;
                                tmp = 4;
                                break;
                            }
                        }
                        switch (tmp) {
                            case 1: {
                                module.state = Boolean.parseBoolean(lastValue);
                                ** break;
                            }
                            case 2: {
                                try {
                                    ((CheckBox)module.getSetting((String)setName).getSetting()).state = Boolean.parseBoolean(lastValue);
                                }
                                catch (NullPointerException var14_18) {}
                                continue block26;
                            }
                            case 3: {
                                System.out.println("Loading SLider: " + setName + ":" + Double.parseDouble(lastValue));
                                try {
                                    ((Slider)module.getSetting((String)setName).getSetting()).curValue = Double.parseDouble(lastValue);
                                }
                                catch (NullPointerException var14_19) {}
                                continue block26;
                            }
                            case 4: {
                                try {
                                    if (!(module.getSetting(setName).getSetting() instanceof DropdownBox)) ** break;
                                    ((DropdownBox)module.getSetting((String)setName).getSetting()).curOption = lastValue;
                                }
                                catch (NullPointerException var14_20) {
                                    // empty catch block
                                }
                                continue block26;
                            }
                        }
                    }
                }
                finally {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                }
            }
            catch (Throwable var4_7) {
                if (var3_4 == null) {
                    var3_4 = var4_7;
                    throw var3_4;
                }
                if (var3_4 == var4_7) throw var3_4;
                var3_4.addSuppressed(var4_7);
                throw var3_4;
            }
        }
        catch (IOException e) {
            ChatUtil.sendChatMessageWithPrefix("An error occurred while loading this config.");
            e.printStackTrace();
        }
    }
}

