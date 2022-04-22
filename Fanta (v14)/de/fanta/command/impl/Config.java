/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.command.impl;

import de.fanta.Client;
import de.fanta.command.Command;
import de.fanta.utils.ChatUtil;
import de.fanta.utils.FileUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;

public class Config
extends Command {
    private File dir;

    public Config() {
        super("config");
        this.dir = new File(Minecraft.getMinecraft().mcDataDir + "/" + Client.INSTANCE.name + "/" + "configs" + "/");
    }

    @Override
    public void execute(String[] args) {
        block27: {
            block25: {
                if (args.length < 1) break block25;
                switch (args[0]) {
                    case "load": {
                        try {
                            if (new File(this.dir, String.valueOf(args[1]) + ".txt").exists()) {
                                FileUtil.loadValues(args[1], true, false);
                                Config.messageWithPrefix("Config loaded");
                                break;
                            }
                            Config.messageWithPrefix("Config does not exist!");
                        }
                        catch (Exception exception) {}
                        break block27;
                    }
                    case "onlineload": {
                        FileUtil.loadValues(args[1], true, true);
                        break;
                    }
                    case "onlinelist": {
                        Thread thread = new Thread(() -> {
                            ChatUtil.sendChatMessageWithPrefix("Loading...");
                            ArrayList<String> configs = new ArrayList<String>();
                            try {
                                URLConnection urlConnection = new URL("https://raw.githubusercontent.com/LCAMODZ/Fanta-configs/main/configs.json").openConnection();
                                urlConnection.setConnectTimeout(10000);
                                urlConnection.connect();
                                Object object = null;
                                Object var3_5 = null;
                                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));){
                                    String text;
                                    while ((text = bufferedReader.readLine()) != null) {
                                        if (text.contains("404: Not Found")) {
                                            ChatUtil.sendChatMessageWithPrefix("An error occurred while loading the configs from Github.");
                                            return;
                                        }
                                        configs.add(text);
                                    }
                                }
                                catch (Throwable throwable) {
                                    if (object == null) {
                                        object = throwable;
                                    } else if (object != throwable) {
                                        ((Throwable)object).addSuppressed(throwable);
                                    }
                                    throw object;
                                }
                            }
                            catch (IOException e) {
                                ChatUtil.sendChatMessageWithPrefix("An error occurred while loading the configs from Github.");
                                e.printStackTrace();
                            }
                            for (String config : configs) {
                                ChatUtil.sendChatMessageWithPrefix(config);
                            }
                        });
                        thread.start();
                        break;
                    }
                    case "save": {
                        FileUtil.saveValues(args[1], true);
                        Config.messageWithPrefix("Config saved");
                        break;
                    }
                    case "list": {
                        try {
                            File[] files = this.dir.listFiles();
                            String list = "";
                            int i = 0;
                            while (i < files.length) {
                                list = String.valueOf(list) + ", " + files[i].getName().replace(".txt", "");
                                ++i;
                            }
                            Config.messageWithPrefix("\u00a77Configs: \u00a7f" + list.substring(2));
                            break;
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                    }
                }
                break block27;
            }
            Config.messageWithPrefix("\u00a77config \u00a78<\u00a7bload/save/list/onlineload/onlinelist\u00a78> \u00a78<\u00a7bname\u00a78>\u00a7f");
        }
    }
}

