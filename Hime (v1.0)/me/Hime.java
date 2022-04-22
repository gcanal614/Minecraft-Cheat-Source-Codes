package me;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import me.ui.SplashInicialization.SplashProgress;
import org.lwjgl.opengl.Display;

import com.thealtening.auth.TheAlteningAuthentication;
import com.thealtening.auth.service.AlteningServiceType;

import me.command.CommandManager;
import me.command.friend.FriendManager;
import me.event.Event;
import me.file.SaveLoad2;
import me.font.TTFFontRenderer;
import me.hippo.api.lwjeb.bus.PubSub;
import me.hippo.api.lwjeb.configuration.BusConfigurations;
import me.hippo.api.lwjeb.configuration.config.Configuration;
import me.hippo.api.lwjeb.configuration.config.impl.BusPubSubConfiguration;
import me.hippo.api.lwjeb.message.scan.impl.MethodAndFieldBasedMessageScanner;
import me.log.LogUtil;
import me.log.LogUtil.LogType;
import me.module.Module;
import me.module.ModuleManager;
import me.script.ScriptManager;
import me.settings.SettingsManager;
import me.ui.clickgui2.ClickGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.ChatComponentText;


/**
 * 
 * 
 * Hime AC Copyright 1969!!!!
 * 
 * 
 */

public class Hime {
	
	//Instance
	public static Hime instance = new Hime();
	
	//Name/Version
	public String name = "Hime", version = "v1.0";
	
	//Event
	private final static PubSub<Event> EVENT_PUBSUB;
	
    //Fontrenderer
	public TTFFontRenderer cfr, cfrs, cfrs2, rfr, rfrs, csgo;
	
	//Minecraft Instance
	protected static Minecraft mc = Minecraft.getMinecraft();
	
	//Other Instances
	public ModuleManager moduleManager;
    public SettingsManager settingsManager;
    public static FriendManager friendManager;
    public static CommandManager commandManager;
    public SaveLoad2 saveLoad;
    public static ClickGui clickGui;
    public static Scanner scanner;
    public static TheAlteningAuthentication auth;
    public ScriptManager scriptManager;
    public File dir;
    public ServerData serverData;
    
    //Strings/Booleans
    public String config = "";
    public String user = "";
    public String brand = "vanilla";
    public String apyKey = "";
    public String nameprotect = "Hime User";
    public String autogg = "gg";
    public String spam = "get hime ware";
    public String dmplayer = "";
    public boolean cape = true;
    public boolean premium = false;
    public boolean ghost = false;
    
    /** Client Startup */
	public void init(){
        SplashProgress.setProgress(2, "Loading Fonts");
	    setupFont();
        SplashProgress.setProgress(3, "Loading Settings");
        settingsManager = new SettingsManager();
        SplashProgress.setProgress(4, "Loading Modules");
        moduleManager = new ModuleManager();
        SplashProgress.setProgress(5, "Loading GUIs");
        clickGui = new ClickGui();
        SplashProgress.setProgress(6, "Loading Commands");
        friendManager = new FriendManager();
	  commandManager = new CommandManager();
	  saveLoad = new SaveLoad2();
        SplashProgress.setProgress(7, "Loading Alt Server");
        auth = new TheAlteningAuthentication(AlteningServiceType.MOJANG);
        SplashProgress.setProgress(8, "Loading Scripts");
        dir = new File(Minecraft.getMinecraft().mcDataDir, "Hime Scripts");
      if (!dir.exists()) {
		 dir.mkdir();
      }
      scriptManager = new ScriptManager();

        SplashProgress.setProgress(9, "Loading Hime");
        System.out.println("Starting Hime" + "... | Version: " + version);
        SplashProgress.setProgress(10, "Checking Version");
        System.out.println("Running on Latest Version: " + this.isLatestVersion());
      Display.setTitle(name + " " + version);
	}
	
    public PubSub<Event> getEventBus() {
        return EVENT_PUBSUB;
    }
    
    public void setupFont() {
    InputStream istream = this.getClass().getResourceAsStream("/assets/minecraft/client/fonts/Comfortaa-Bold.ttf");
    InputStream istream2 = this.getClass().getResourceAsStream("/assets/minecraft/client/fonts/Comfortaa-Bold.ttf");
    InputStream istream2s = this.getClass().getResourceAsStream("/assets/minecraft/client/fonts/Comfortaa-Bold.ttf");
    InputStream istream3 = this.getClass().getResourceAsStream("/assets/minecraft/client/fonts/Roboto-Light.ttf");
    InputStream istream4 = this.getClass().getResourceAsStream("/assets/minecraft/client/fonts/Roboto-Light.ttf");
    InputStream istream5 = this.getClass().getResourceAsStream("/assets/minecraft/client/fonts/CSGOFont.ttf");
   
    Font font1 = null;
    Font font1s = null;
    Font font1ss = null;
    Font font2 = null;
    Font font2s = null;
    Font font3 = null;
    try {
        font1 = Font.createFont(0, istream);
        font1 = font1.deriveFont(0, 25.0f);
        font1s = Font.createFont(0, istream2);
        font1s = font1s.deriveFont(0, 18.0f);
        font1ss = Font.createFont(0, istream2s);
        font1ss = font1s.deriveFont(0, 48);
        
        font2 = Font.createFont(0, istream3);
        font2 = font2.deriveFont(0, 25.0f);
        font2s = Font.createFont(0, istream4);
        font2s = font2s.deriveFont(0, 18.0f);
        
        font3 = Font.createFont(0, istream5);
        font3 = font3.deriveFont(0, 29.0f);
    }
    catch (Exception e) {
        e.printStackTrace();
    }
    this.cfr = new TTFFontRenderer(font1);
    this.cfrs = new TTFFontRenderer(font1s);
    this.cfrs2 = new TTFFontRenderer(font1ss);
    this.rfr = new TTFFontRenderer(font2);
    this.rfrs = new TTFFontRenderer(font2s);
    this.csgo = new TTFFontRenderer(font3);
}
	
    public static void addClientChatMessage(String text){
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§l§5Hime§f§l > " + text));
	}
    
    /** dream partner with artemis??? :flushed: */
    public int getPiglinEnderPearlDropRate() {
		return 69 * 420 * 69420;
	}
    
    public static void addCustomChatMessage(String text1, String text2){
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text1 + " " + text2));
	}
    
    public static void switchService(AlteningServiceType type) {
        auth = new TheAlteningAuthentication(type);
    }
    
    public void handleKeyPress(final int var1) {
        for (Module mod : moduleManager.getModules()) {
            if (mod.getKey() != var1 || mod.getKey() == 0) {
                continue;
            }
            mod.toggle();
        }
    	int countReal = 0;
		for(File file : ScriptManager.instance.dir.listFiles()) {
			String realName = file.getName().replace(".txt", "");
		if(ScriptManager.instance.scriptKeycodes.get(countReal) == var1) {
		  if(ScriptManager.instance.enabledScripts.get(countReal).equalsIgnoreCase("true")) {
			  ScriptManager.instance.enabledScripts.set(countReal, "false");
			  ScriptManager.instance.scriptDisable(file);
		  }else if(ScriptManager.instance.enabledScripts.get(countReal).equalsIgnoreCase("false")){
			  ScriptManager.instance.enabledScripts.set(countReal, "true");
			  ScriptManager.instance.scriptEnable(file);
		  }
		}
			  countReal++;
		}
    }
    
    public boolean isLatestVersion() {
    	boolean latest = false;
	    try {
            final URL url = new URL("https://pastebin.com/raw/6qFAxm5p");
            try {
                this.scanner = new Scanner(url.openStream());
                while (this.scanner.hasNextLine()) {
                    final String creds = this.scanner.nextLine();
                    if(Integer.valueOf(creds) > 1) {
                    	latest = false;
                     }else {
                    	latest = true;
                     }
                    }
            } catch (IOException e) {
            	e.printStackTrace();
				LogUtil.instance.log("Scanning URL Error!", LogType.ERROR);
            	}
        } catch (MalformedURLException e) {
        	e.printStackTrace();
			LogUtil.instance.log("URL invalid!", LogType.ERROR);
        }
	    return latest;
    }
    
    static {
        final BusPubSubConfiguration busPubSubConfiguration = BusPubSubConfiguration.getDefault();
        EVENT_PUBSUB = new PubSub<Event>(new BusConfigurations.Builder().setConfiguration((Class<? extends Configuration<?>>)BusPubSubConfiguration.class, () -> {
            busPubSubConfiguration.setScanner(new MethodAndFieldBasedMessageScanner<Object>());
            return busPubSubConfiguration;
        }).build());
    }
}	
