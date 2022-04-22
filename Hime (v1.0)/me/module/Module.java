package me.module;

import java.util.ArrayList;
import java.util.Collections;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.Hime;
import me.event.EventC;
import me.module.impl.render.HUD;
import me.notification.Notification;
import me.notification.NotificationManager;
import me.notification.NotificationType;
import me.settings.Setting;
import me.util.MusicUtil;
import me.util.animations.utilities.Progression;
import net.minecraft.client.Minecraft;

public class Module {
	public static boolean hasModes;
	protected static Minecraft mc = Minecraft.getMinecraft();
	private String name;
	private int key;
	private boolean toggled;
	private Category category;
	private String suffix;
    public float mSize;
    public float lastSize;
	public boolean visible = true;
	private final Progression moduleProgressionX;
	private final Progression moduleProgressionY;
	private double animX, animY;
	
	public Module(String name, int keyCode, Category cat) {
		this.name = name;
		this.key = keyCode;
		this.category = cat;
		this.moduleProgressionX = new Progression();
		this.moduleProgressionY = new Progression();
		setup();
	}
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if (Hime.instance.saveLoad != null && Hime.instance.config == "Default") {
			Hime.instance.saveLoad.saveConfig();
		}
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
	
	   public final void toggle() {
		   if(Hime.instance.settingsManager.getSettingByName("Sound").getValBoolean()) {
			   MusicUtil.playSound("reeee.wav");
		   }
	        this.toggled = !this.toggled;
	        onToggle();
	        this.moduleProgressionX.setValue(0);
			this.moduleProgressionY.setValue(0);
	        if (this.toggled) {
	            if (mc.thePlayer != null) {
	                this.onEnable();
	                if (Hime.instance.saveLoad != null && Hime.instance.config == "Default") {
						Hime.instance.saveLoad.saveConfig();
					}
	            }
	           EventC.subscribe(this);
	        } else {
	           EventC.unsubscribe(this);
	            if (this.mc.thePlayer != null) {
	                this.onDisable();
	                if (Hime.instance.saveLoad != null && Hime.instance.config == "Default") {
						Hime.instance.saveLoad.saveConfig();
					}
	            }
	        }
	    }
	   
		public void setToggled(boolean toggledTo) {
			if (toggledTo) {
				if (isToggled()) {
					if (Hime.instance.saveLoad != null && Hime.instance.config == "Default") {
						Hime.instance.saveLoad.saveConfig();
					}
					return;
				}
				toggle();
				  mSize = 0;
		          lastSize = Hime.instance.rfrs.getWidth(this.getName());
				if (Hime.instance.saveLoad != null && Hime.instance.config == "Default") {
					Hime.instance.saveLoad.saveConfig();
				}
		
				return;
			} else {
				if (isToggled()) {
					toggle();
				}
				  mSize =  Hime.instance.rfrs.getWidth(this.getName());
		          lastSize = 0;
				if (Hime.instance.saveLoad != null && Hime.instance.config == "Default") {
					Hime.instance.saveLoad.saveConfig();
				}
				return;
			}
		}
	   
	public void onEnable() {
		  if(Hime.instance.settingsManager.getSettingByName("Module Enabled/Disabled").getValBoolean()) {
			  NotificationManager.show(new Notification(NotificationType.INFO, "Module Alert", this.getName() + " Was Enabled", 2));
		  }	
	     mSize = 0;
         lastSize = Hime.instance.rfrs.getWidth(this.getName());
	}
	
	public void onDisable() {
	  if(Hime.instance.settingsManager.getSettingByName("Module Enabled/Disabled").getValBoolean()) {
	  	      NotificationManager.show(new Notification(NotificationType.INFO, "Module Alert", this.getName() + " Was Disabled", 2));
	  }
		 mSize = Hime.instance.rfrs.getWidth(this.getName());
         lastSize = 0;
	}
	
    public void onToggle() {
		
	}

    public void setup() {
		
	}
    
    public void setAnimX(double animX) {
		this.animX = animX;
	}
	
	public void setAnimY(double animY) {
		this.animY = animY;
	}
	
	public double getAnimX() {
		return animX;
	}
	
	public double getAnimY() {
		return animY;
	}
	
	public boolean isToggled() {
		return toggled;
	}

	public Category getCategory() {
		return category;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
		if (Hime.instance.saveLoad != null && Hime.instance.config == "Default") {
			Hime.instance.saveLoad.saveConfig();
		}
	}

	public String getSuffixedName() {
		if(this.suffix == null) {
			return this.name;
		}
		if(Hime.instance.moduleManager.getModule(HUD.class).suffixMode.equalsIgnoreCase("Normal")) {
		return this.name + ChatFormatting.GRAY + " " + this.suffix;
		}else if(Hime.instance.moduleManager.getModule(HUD.class).suffixMode.equalsIgnoreCase("Dash")){
	     return this.name + ChatFormatting.GRAY + " - " + this.suffix;	
		}else if(Hime.instance.moduleManager.getModule(HUD.class).suffixMode.equalsIgnoreCase("Bracket")){
		 return this.name + ChatFormatting.GRAY + " [" + this.suffix + "]";	
		}else {
		 return this.name;	
		}
	}
	
	public String getSuffix() {
		if(this.suffix == null) {
			return "";
		}
		return ChatFormatting.GRAY + "" + this.suffix;
	}
	
	public enum Category {
		COMBAT(0xff9100), MOVEMENT(0x00ff2f), PLAYER(0xff0000), RENDER(0x0015ff), MISC(0x7300ff), EXPLOIT(0x00ffea), WORLD(0xff00cc), TARGETS(0xffee00);
		public int color;
		
		 Category(int color) {
				this.color = color;
		}
	}
	
	
	  public void addBool(String string, boolean b) {
	        Hime.instance.settingsManager.rSetting(new Setting(string, this, b));
	    }
	       
	    public boolean getBool(String string) {
	        return Hime.instance.settingsManager.getSettingByName(string).getValBoolean();
	    }
	        
	    public void addDouble(String string, double dval, double min, double max, boolean onlyint) {
	        Hime.instance.settingsManager.rSetting(new Setting(string, this, dval, min, max, false));
	    }
	        
	    public double getDouble(String string) {
	        return Hime.instance.settingsManager.getSettingByName(string).getValDouble();
	    }
	       
	    public void addModes(String modeType, String... modes) {
	        final ArrayList<String> mode = new ArrayList<String>();
	        Collections.addAll(mode, modes);
	        Hime.instance.settingsManager.rSetting(new Setting(modeType, this, modes[0], mode));
	    }
	    
	    public String getModes(String mode) {
	        String nullString = "";
	        if (Hime.instance.settingsManager.getSettingByName(mode) != null) {
	            return Hime.instance.settingsManager.getSettingByName(mode).getValString();
	        }
	        return "" + nullString;
	    }

	public Progression getModuleProgressionX() {
		return moduleProgressionX;
	}
	
	public Progression getModuleProgressionY() {
		return moduleProgressionY;
	}

}

