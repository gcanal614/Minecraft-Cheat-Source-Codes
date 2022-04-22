package me.injusttice.neutron;

import me.injusttice.neutron.api.events.impl.EventNigger;
import me.injusttice.neutron.impl.commands.CommandManager;
import me.injusttice.neutron.api.configs.Configs;
import me.injusttice.neutron.api.events.EventManager;
import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.ui.UIs.account.AccountManager;
import me.injusttice.neutron.utils.alt.AltManager;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.impl.modules.ModuleManager;
import me.injusttice.neutron.impl.modules.impl.visual.ClickGUI;
import me.injusttice.neutron.impl.modules.impl.visual.HUD;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.injusttice.neutron.api.events.impl.EventChat;
import me.injusttice.neutron.api.events.impl.EventKey;
import me.injusttice.neutron.utils.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import java.io.*;
import java.util.ArrayList;

public class NeutronMain {

    public static String getLoginUser = "?";
    public static String client_build = "000001";
    public static boolean authorized;
    public String name = "Neutron";
    public String username;
    public String uuid;

    public static NeutronMain instance = new NeutronMain();

    public Configs configManager;
    public EventManager eventManager;

    public ModuleManager moduleManager;
    public ClickGUI clickGui;
    public AltManager altManager;
    public AccountManager accountManager;
    public ServerData serverData;
    public File script;
    public File files;
    public File dir;
    public File dataDr;
    public File scriptFolder;
    public HUD hud;

    public static CommandManager commandManager = new CommandManager();

    public void start() {
        files = new File(Minecraft.getMinecraft().mcDataDir, "Neutron");
        if (!files.exists())
            files.mkdir();
       dir = new File(files + "/configs");
        if (!dir.exists())
            dir.mkdir();
        scriptFolder = new File(files + "/scripts");
        if (!scriptFolder.exists())
            scriptFolder.mkdir();
        eventManager = new EventManager();
        moduleManager = new ModuleManager();
        configManager = new Configs();
        clickGui = new ClickGUI();
        altManager = new AltManager();
        System.out.print("Starting Fonts");
        Fonts.startFonts();
        System.out.print("");
        Display.setTitle("Minecraft 1.8.8");
        EventManager.register(this);
        NeutronMain.authorized = false;
        loadDefaultConfig();
        loadKeys();
    }

    public static void onEvent(EventNigger e) {
        if(e instanceof EventChat) {
            commandManager.handleChat((EventChat)e);
        }
    }

    public void shutdown(){
        saveKeys();
        saveDefaultConfig();
        EventManager.unregister(this);
    }

    public void setAuthorized(boolean authorized) {
        NeutronMain.authorized = authorized;
    }

    public void setName(String newName) {
        this.username = newName;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    @EventTarget
    public void onKey(EventKey event) {
        moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(module -> module.toggle());
    }

    public static void addDebugMessage(String message) {
        message = "§f[" + ChatFormatting.RED + "Debug§f" + "] " + message;

        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }

    public static void addChatMessage(String message) {
        message = ChatFormatting.LIGHT_PURPLE + "Neutron§f" + "> " + message;

        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }
    public void saveKeys() {
        this.dataDr = new File(files, "keys.json");
        ArrayList<String> toSave = new ArrayList<>();
        for (Module m : instance.moduleManager.getModules())
            toSave.add(m.getName() + ">" + m.getKey());
        try {
            PrintWriter pw = new PrintWriter(this.dataDr);
            for (String str : toSave)
                pw.println(str);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadKeys() {
        this.dataDr = new File(files, "keys.json");
        ArrayList<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.dataDr));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            for (String s : lines) {
                String[] args = s.split(">");
                for (Module m : instance.moduleManager.getModules()) {
                    if (m.getName().equalsIgnoreCase(args[0]))
                        m.setKey(Integer.parseInt(args[1]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadDefaultConfig(){
        try {
            NeutronMain.instance.configManager.load("Default");
        } catch (Exception e) {
            e.printStackTrace();
            addDebugMessage("Could Not Load Default Config!");
        }
    }

    public void saveDefaultConfig(){
        try {
            NeutronMain.instance.configManager.save("Default");
        } catch (Exception e) {
            e.printStackTrace();
            addDebugMessage("Could Not Load Default Config!");
        }
    }

    public AccountManager getAccountManager(){
        return accountManager;
    }
}
