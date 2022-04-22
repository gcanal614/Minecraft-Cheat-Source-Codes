package me.module.impl.render;

import java.awt.Color;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

import me.Hime;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.event.impl.EventRenderHUD;
import me.event.impl.EventSendPacket;
import me.font.TTFFontRenderer;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.ColorUtil;
import me.util.RainbowUtil;
import me.util.RenderUtil;
import me.util.TimeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;

public class HUD extends Module {
	public TTFFontRenderer font = Hime.instance.cfrs;
	public TTFFontRenderer font2 = Hime.instance.rfrs;
	public Setting mark;
	public Setting fontstyle;
	public Setting style;
	public Setting list;
	public Setting info;
	public Setting armor;
	public Setting potion;
	public Setting background;
	public Setting bar;
	public Setting logo;
//	public Setting keystrokes;
	public Setting player;
	public String barMode;
	public Setting offset2;
	public Setting rainbow1;
	public Setting rainbow2;
	public Setting compass;
	public String infoMode;
	public String suffixMode;
	public Setting infoY;
	public Setting playerX;
	public Setting playerY;
	public Setting transparency;
	public Setting graph;
	public Setting xadd;
	public int fade;
	public int fade2;
	public int fade3;
	public int fade4;
	public int fade5;
    ArrayList<Float> packetList = new ArrayList<>();
    float packetCount = 0;
    public TimeUtil packetTimer = new TimeUtil();
	private int x;
	
	public HUD() {
		super("HUD", 0, Category.RENDER);
		//Settings
		ArrayList<String> options = new ArrayList<String>();
		//default huds
		options.add("Hime");
		options.add("Client Color");
		options.add("Client Color2");
		options.add("Client Color3");
		options.add("Rainbow");
		options.add("Rainbow2");
		options.add("Astolfo");
		//options.add("Astolfo2");
		
		//custom
	//	options.add("PapaNef");
		options.add("Sigma Jello");
		//options.add("Bordered Rainbow");
		//options.add("Bordered Pulsing");
		options.add("Zeroday");
		options.add("German");
		//options.add("Bar Top");
	    Hime.instance.settingsManager.rSetting(list = new Setting("Arraylist Style", this, "Hime", options));
		ArrayList<String> options2 = new ArrayList<String>();
		options2.add("Hime");
		options2.add("Dream");
		options2.add("Novoline");
		options2.add("Novoline2");
		options2.add("Client Color");
		options2.add("Client Color2");
		options2.add("Sigma Classic");
		options2.add("Sigma Jello");
		options2.add("Exhibition");
		options2.add("Sixsense");
		options2.add("Sixsense2");
	    Hime.instance.settingsManager.rSetting(style = new Setting("WaterMark Style", this, "Hime", options2));
	    ArrayList<String> options3 = new ArrayList<String>();
        options3.add("None");
        options3.add("Text");
        options3.add("Logo");
        Hime.instance.settingsManager.rSetting(mark = new Setting("WaterMark Mode", this, "Text", options3));
	    ArrayList<String> options5 = new ArrayList<String>();
        options5.add("Default");
        options5.add("Custom");
        Hime.instance.settingsManager.rSetting(fontstyle = new Setting("Font Style", this, "Custom", options5));
        addModes("Bar Mode", "Left", "Right", "Top", "Bordered", "All");
    	this.barMode = this.getModes("Bar Mode");
        Hime.instance.settingsManager.rSetting(info = new Setting("Info", this, true));
        this.addModes("Info Mode", "Normal", "Normal2", "FPS/COORDS", "FPS", "COORDS", "UHC", "BPS");
        this.infoMode = this.getModes("Info Mode");
        
        
        
        this.addModes("Suffix Mode", "Normal", "Dash", "Bracket", "None");
        this.suffixMode = this.getModes("Suffix Mode");
        Hime.instance.settingsManager.rSetting(new Setting("Custom Hotbar", this, true));
        Hime.instance.settingsManager.rSetting(potion = new Setting("Potion Status", this, false));
        Hime.instance.settingsManager.rSetting(armor = new Setting("Armor Status", this, true));
        //default hud settings
        Hime.instance.settingsManager.rSetting(background = new Setting("Arraylist Background", this, true));
        Hime.instance.settingsManager.rSetting(bar = new Setting("Bar", this, true));
        
      //  Hime.instance.settingsManager.rSetting(keystrokes = new Setting("Keystrokes", this, false));
        Hime.instance.settingsManager.rSetting(player = new Setting("Render Player", this, false));
        Hime.instance.settingsManager.rSetting(compass = new Setting("Compass", this, false));
    	Hime.instance.settingsManager.rSetting(offset2 = new Setting("Arraylist Offset", this, 0, 0, 25, false));
    	Hime.instance.settingsManager.rSetting(infoY = new Setting("Info Y Add", this, 0, 0, 300, true));
    	Hime.instance.settingsManager.rSetting(playerX = new Setting("Player X", this, 0, 0, 300, true));
    	Hime.instance.settingsManager.rSetting(playerY = new Setting("Player Y", this, 0, 0, 300, true));
    	Hime.instance.settingsManager.rSetting(rainbow1 = new Setting("Color Offset", this, 8, 0, 80, false));
    	Hime.instance.settingsManager.rSetting(transparency = new Setting("Background Transparency", this, 150, 0, 255, false));
    	Hime.instance.settingsManager.rSetting(rainbow2 = new Setting("Color Speed", this, 2, 1, 50, false));
    	Hime.instance.settingsManager.rSetting(graph = new Setting("Packet Graph", this, false));
    	Hime.instance.settingsManager.rSetting(xadd = new Setting("Packet Graph X Add", this, 0, 0, 300, true));
	}
	
	
	  public static class ModuleComparator implements Comparator<Module>{
		  @Override
			public int compare(Module o1, Module o2) {
				if(Minecraft.getMinecraft().fontRendererObj.getStringWidth(o1.getName()) > Minecraft.getMinecraft().fontRendererObj.getStringWidth(o2.getName())) {
					return -1;
				}
				if(Minecraft.getMinecraft().fontRendererObj.getStringWidth(o1.getName()) < Minecraft.getMinecraft().fontRendererObj.getStringWidth(o2.getName())) {
					return 1;
				}
				return 0;
			}
			  
		}
	  
	  
	public boolean isFontCustom() {
	  return this.fontstyle.getValString().equalsIgnoreCase("Custom");
	}
	
	public boolean isNormal() {
		return this.suffixMode.equalsIgnoreCase("Normal");
	}
	
	@Handler
	public void onRenderHUD(EventRenderHUD event) {	
		ScaledResolution sr = new ScaledResolution(mc);
		FontRenderer fr = mc.fontRendererObj;
		
		//this.setSuffix(this.list.getValString());
		this.barMode = this.getModes("Bar Mode");
		this.infoMode = this.getModes("Info Mode");
		this.suffixMode = this.getModes("Suffix Mode");
		
		//3D Player
        if(this.player.getValBoolean()) {
        	GuiInventory.drawEntityOnScreen(this.playerX.getValInt(), this.playerY.getValInt(), 30, 1, 1, mc.thePlayer);
        }
		
		//Pos
		int posX = (int) Math.round(mc.thePlayer.posX);
	    int posY = (int) Math.round(mc.thePlayer.posY);
	    int posZ = (int) Math.round(mc.thePlayer.posZ);
	    
	    //Ping
	    int ping;
		if(mc.isSingleplayer()) {
			ping = 0;
	  	}else {
	  		ping = (int) mc.getCurrentServerData().pingToServer;
	  	}
	    
		//Hotbar
		if(this.info.getValBoolean()) {
	     switch(infoMode) {
	     case "Normal":
		  font.drawStringWithShadow("Ping: " + ChatFormatting.GRAY + ping, 2, sr.getScaledHeight() - 20- this.infoY.getValInt(), new Color(255, 255, 255).getRGB());
		  font.drawStringWithShadow("FPS: " + ChatFormatting.GRAY + mc.getDebugFPS() +  ChatFormatting.WHITE + "  Pos: " + ChatFormatting.GRAY + posX + " " + posY + " " + posZ +  ChatFormatting.WHITE + " Config: " +  ChatFormatting.GRAY + Hime.instance.config, 2, sr.getScaledHeight() - 10 - this.infoY.getValInt(), new Color(255, 255, 255).getRGB());
	      break;
	     case "Normal2":
	      fr.drawStringWithShadow("Ping: " + ChatFormatting.GRAY + ping, 2, sr.getScaledHeight() - 20- this.infoY.getValInt(), new Color(255, 255, 255).getRGB());
	      fr.drawStringWithShadow("FPS: " + ChatFormatting.GRAY + mc.getDebugFPS() +  ChatFormatting.WHITE + "  Pos: " + ChatFormatting.GRAY + posX + " " + posY + " " + posZ +  ChatFormatting.WHITE + " Config: " +  ChatFormatting.GRAY + Hime.instance.config, 2, sr.getScaledHeight() - 10- this.infoY.getValInt(), new Color(255, 255, 255).getRGB());
	      break; 
	     case "FPS/COORDS":
	      GuiButton.sgsmall.drawStringWithShadow("FPS: "  + mc.getDebugFPS(), 2, sr.getScaledHeight() - 20- this.infoY.getValInt(), new Color(255, 255, 255).getRGB());
	   	  GuiButton.sgsmall.drawStringWithShadow(posX + ", " + posY + ", " + posZ, 2, sr.getScaledHeight() - 10- this.infoY.getValInt(), new Color(255, 255, 255).getRGB());
	      break;
	     case "FPS":
	      GuiButton.sgsmall.drawStringWithShadow("FPS: "  + mc.getDebugFPS(), 2, sr.getScaledHeight() - 10- this.infoY.getValInt(), new Color(255, 255, 255).getRGB()); 
	      break;
	     case "COORDS":
		  GuiButton.sgsmall.drawStringWithShadow(posX + ", " + posY + ", " + posZ, 2, sr.getScaledHeight() - 10- this.infoY.getValInt(), new Color(255, 255, 255).getRGB()); 
          break;
	     case "UHC":
	      Chunk chunk = this.mc.theWorld.getChunkFromBlockCoords(new BlockPos(Minecraft.getMinecraft().thePlayer));
	      String biome = chunk.getBiome(new BlockPos(Minecraft.getMinecraft().thePlayer), this.mc.theWorld.getWorldChunkManager()).biomeName;
	      GuiButton.sgsmall.drawStringWithShadow("Biome: " + biome, 2, sr.getScaledHeight() - 20- this.infoY.getValInt(), new Color(255, 255, 255).getRGB()); 
	      GuiButton.sgsmall.drawStringWithShadow("Pos: " + posX + ", " + posY + ", " + posZ, 2, sr.getScaledHeight() - 10- this.infoY.getValInt(), new Color(255, 255, 255).getRGB()); 
	      break;
	     case "BPS":
	    	 
	     }
	    }
		
		//Potion HUD
		if(this.potion.getValBoolean()) {
		 this.renderPotionStatus(sr.getScaledWidth() , sr.getScaledHeight());
	    }
		
		//Armor HUD
	    if(this.armor.getValBoolean()) {
	    for(int i = 3; i > -1; i--) {
	    	ItemStack itemstack = mc.thePlayer.inventory.armorInventory[i];
	    	renderItemStack(-i + 4, itemstack);
	    }
		renderItemStack(0, mc.thePlayer.getCurrentEquippedItem());
	    }
		
	    //Compass
	    if(this.compass.getValBoolean()) {
	    	if(this.isFontCustom()) {
	    		GuiButton.sgsmall.drawCenteredStringWithShadow(mc.thePlayer.getHorizontalFacing().toString().substring(0, 1).toUpperCase() + mc.thePlayer.getHorizontalFacing().toString().substring(1, mc.thePlayer.getHorizontalFacing().toString().length()), sr.getScaledWidth() / 2, 3, -1);
	    	}else {
	    		fr.drawString(mc.thePlayer.getHorizontalFacing().toString().substring(0, 1).toUpperCase() + mc.thePlayer.getHorizontalFacing().toString().substring(1, mc.thePlayer.getHorizontalFacing().toString().length()), sr.getScaledWidth() / 2, 3, -1);
	    	}
	    }
	    
	    //Graph
	    if(this.graph.getValBoolean()) {
         this.renderGraphs();
	    }
	    
		//Watermark
		 if (style.getValString().equalsIgnoreCase("Hime")) {
		        switch(mark.getValString()) {
		        case "Logo":
			        RenderUtil.instance.draw2DImage(new ResourceLocation("client/logo.png"), 5,  5, 80, 70, Color.WHITE);
			        break;
		        case "Text":
		        	if(this.isFontCustom()) {
		        	Hime.instance.cfrs.drawStringWithShadow(Hime.instance.name + " " + Hime.instance.version, 1, 2, new Color(255, 255, 255).getRGB());
		        	}else {
		        	 fr.drawStringWithShadow(Hime.instance.name + " " + Hime.instance.version, 1, 2, new Color(255, 255, 255).getRGB());
		        	}
		        	break;
		        }
		 }
		 if (style.getValString().equalsIgnoreCase("Dream")) {
		        switch(mark.getValString()) {
		        case "Logo":
			        RenderUtil.instance.draw2DImage(new ResourceLocation("client/Dream.png"), -10,  -8, 170, 100, Color.WHITE);
		            break;
		        case "Text":
		        	if(this.isFontCustom()) {
		        	GuiButton.sgsmall.drawStringWithShadow(Hime.instance.name, 1, 2, new Color(181 ,230, 29).getRGB());
		        	}else {
		        	 fr.drawStringWithShadow(Hime.instance.name, 1, 2, new Color(181, 230 ,29).getRGB());
		        	}
		        	break;
		        }
		 }
		 if (style.getValString().equalsIgnoreCase("Sigma Jello")) {
		        switch(mark.getValString()) {
		        case "Logo":
		        	RenderUtil.instance.draw2DImage(new ResourceLocation("client/jello.png"), -130,  -130, 390, 300, new Color(255, 255, 255, 1));
		            break;
		        case "Text":
		        GuiButton.jellowork.drawString(Hime.instance.name, 1, 2, new Color(255, 255, 255, 200).getRGB());
		            Hime.instance.rfrs.drawString(Hime.instance.version, 1, 23, -1);
		        	break;
		        }
		 }
		 if (style.getValString().equalsIgnoreCase("Novoline")) {
		        switch(mark.getValString()) {
		        case "Logo":
		        	char [] words3 = Hime.instance.name.toCharArray();
		        	fr.drawString(words3[0] + "§f" + Hime.instance.name.substring(1, Hime.instance.name.length()), 1, 2, RainbowUtil.rainbow(300));
		            break;
		        case "Text":
		        	char [] words2 = Hime.instance.name.toCharArray();
		        	GuiButton.sgsmall.drawString(words2[0] + "§f" + Hime.instance.name.substring(1, Hime.instance.name.length()), 1, 2, RainbowUtil.rainbow(300));
		            break;
		        }
		 }
		 if (style.getValString().equalsIgnoreCase("Novoline2")) {
			 final String time = LocalTime.now().toString().split("\\.")[0].substring(0, 5);
		        switch(mark.getValString()) {
		        case "Logo":
		        	char [] words3 = Hime.instance.name.toCharArray();
		        	fr.drawString(words3[0] + "§f" + Hime.instance.name.substring(1, Hime.instance.name.length()) + " (" + time + ")", 1, 2, RainbowUtil.rainbow(300));
		            break;
		        case "Text":
		        	char [] words2 = Hime.instance.name.toCharArray();
		        	GuiButton.sgsmall.drawString(words2[0] + "§f" + Hime.instance.name.substring(1, Hime.instance.name.length()) + " (" + time + ")", 1, 2, RainbowUtil.rainbow(300));
		            break;
		        }
		 }
		 if (style.getValString().equalsIgnoreCase("Client Color")) {
		        switch(mark.getValString()) {
		        case "Logo":
		        	final float hue2 = (float) (ColorUtil.getClickGUIColor());
	          		  //                           colorSaturation  colorBrightness 
	          	    Color color2 = Color.getHSBColor(hue2, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
		        	char [] words3 = Hime.instance.name.toCharArray();
		            fr.drawString(words3[0] + "§f" + Hime.instance.name.substring(1, Hime.instance.name.length()), 1, 2, color2.getRGB());
		            break;
		        case "Text":
		        	final float hue = (float) (ColorUtil.getClickGUIColor());
	          		  //                           colorSaturation  colorBrightness 
	          	    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
		        	char [] words2 = Hime.instance.name.toCharArray();
		        	GuiButton.sgsmall.drawString(words2[0] + "§f" + Hime.instance.name.substring(1, Hime.instance.name.length()), 1, 2, color.getRGB());
		        	break;
		        }
		 } if (style.getValString().equalsIgnoreCase("Client Color2")) {
			 final String time = LocalTime.now().toString().split("\\.")[0].substring(0, 5);
		        switch(mark.getValString()) {
		        case "Logo":
		        	final float hue2 = (float) (ColorUtil.getClickGUIColor());
	          		  //                           colorSaturation  colorBrightness 
	          	    Color color2 = Color.getHSBColor(hue2, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
		        	char [] words3 = Hime.instance.name.toCharArray();
		            fr.drawString(words3[0] + "§f" + Hime.instance.name.substring(1, Hime.instance.name.length())+ " (" + time + ")", 1, 2, color2.getRGB());
		            break;
		        case "Text":
		        	final float hue = (float) (ColorUtil.getClickGUIColor());
	          		  //                           colorSaturation  colorBrightness 
	          	    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
		        	char [] words2 = Hime.instance.name.toCharArray();
		        	//GuiButton.sgsmall.drawString(words2[0] + "§f" + Hime.instance.name.substring(1, Hime.instance.name.length()) + " (" + time + ")", 1, 2, color.getRGB());
		        	break;
		        }
		 }
		 if (style.getValString().equalsIgnoreCase("Exhibition")) {
		        switch(mark.getValString()) {
		        case "Logo":
		        	char [] words = Hime.instance.name.toCharArray();
		            fr.drawString(words[0] + "§f" + Hime.instance.name.substring(1, Hime.instance.name.length()) + " §7[" + "§f" +  mc.getDebugFPS() + "§f FPS " + "§7]" + " §7 [" + "§f" + ping +"ms" + "§7]" , 1, 2, RainbowUtil.rainbow(300));
		            break;
		        case "Text":
		        	char [] words2 = Hime.instance.name.toCharArray();
		            font.drawString(words2[0] + "§f" + Hime.instance.name.substring(1, Hime.instance.name.length()) + " §7[" + "§f" +  mc.getDebugFPS() + "§f FPS " + "§7]" + " §7 [" + "§f" + ping +"ms" + "§7]" , 1, 2, RainbowUtil.rainbow(300));
		            break;
		        }
		 }if (style.getValString().equalsIgnoreCase("Sixsense")) {
		        switch(mark.getValString()) {
		        case "Logo":
		        	char [] words = Hime.instance.name.toCharArray();
		            fr.drawString(words[0] + "§f" + Hime.instance.name.substring(1, Hime.instance.name.length()) + " §7[" + "§f" +  mc.getDebugFPS() + "§f FPS " + "§7]" + " §7 [" + "§f" + ping +"ms" + "§7]" , 1, 2, RainbowUtil.rainbow(300));
		            break;
		        case "Text":
					 String server = mc.isSingleplayer() ? "local server" : mc.getCurrentServerData().serverIP.toLowerCase();
					 String user = Hime.instance.user.equalsIgnoreCase("") ? "Default" : Hime.instance.user;
		             String text = Hime.instance.name + " | "+ mc.getDebugFPS() + " fps | " + user + " | "+ server;
		             float width = Hime.instance.rfrs.getWidth(text) + 6;
		             int height = 20;
		             int posX1 = 2;
		             int posY1 = 2;
		             Gui.drawRect(posX1, posY1, posX1 + width + 2, posY1 + height, new Color(5, 5, 5, 255).getRGB());
                     RenderUtil.drawBorderedRect(posX1 + .5, posY1 + .5, posX1 + width + 1.5, posY1 + height - .5, 0.5, new Color(40, 40, 40, 255).getRGB(), new Color(60, 60, 60, 255).getRGB(), true);
	                 RenderUtil.drawBorderedRect(posX1 + 2, posY1 + 2, posX1 + width, posY1 + height - 2, 0.5, new Color(22, 22, 22, 255).getRGB(), new Color(60, 60, 60, 255).getRGB(), true);
     	             Gui.drawRect(posX1 + 2.5, posY1 + 2.5, posX1 + width - .5, posY1 + 4.5, new Color(9, 9, 9, 255).getRGB());
		             
     	             RenderUtil.drawGradientSideways(4, posY1 + 3, 4 + (width / 3), posY1 + 4, new Color(81, 149, 219, 255).getRGB(), new Color(180, 49, 218, 255).getRGB());
		             RenderUtil.drawGradientSideways(4 + (width / 3), posY1 + 3, 4 + ((width / 3) * 2), posY1 + 4, new Color(180, 49, 218, 255).getRGB(), new Color(236, 93, 128, 255).getRGB());
		             RenderUtil.drawGradientSideways(4 + ((width / 3) * 2), posY1 + 3, ((width / 3) * 3) + 1, posY1 + 4, new Color(236, 93, 128, 255).getRGB(), new Color(167, 171, 90, 255).getRGB());
		             Hime.instance.rfrs.drawString(text , 7.5F, 8, Color.white.getRGB());
		            break;
		        }
		 }if (style.getValString().equalsIgnoreCase("Sixsense2")) {
		        switch(mark.getValString()) {
		        case "Logo":
		        	char [] words = Hime.instance.name.toCharArray();
		            fr.drawString(words[0] + "§f" + Hime.instance.name.substring(1, Hime.instance.name.length()) + " §7[" + "§f" +  mc.getDebugFPS() + "§f FPS " + "§7]" + " §7 [" + "§f" + ping +"ms" + "§7]" , 1, 2, RainbowUtil.rainbow(300));
		            break;
		        case "Text":
		        	final float hue = (float) (ColorUtil.getClickGUIColor());
			  		   //                           colorSaturation  colorBrightness 
			  	    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
					 String server = mc.isSingleplayer() ? "local server" : mc.getCurrentServerData().serverIP.toLowerCase();
		             String text = Hime.instance.name + " | "+ mc.getDebugFPS() + " fps | " + server;
		             float width = fr.getStringWidth(text) + 6;
		             int height = 20;
		             int posX1 = 2;
		             int posY1 = 2;
		             Gui.drawRect(posX1, posY1, posX1 + width + 2, posY1 + height - 3, 0x80000000);
		             Gui.drawRect(posX1, posY1, posX1 + width + 2, posY1 + 2, color.getRGB());
		             fr.drawString(text , (int)7.5, 7, Color.white.getRGB());
		            break;
		        }
		 }else if(style.getValString().equalsIgnoreCase("Sigma Classic")) {
		 	   switch(mark.getValString()) {
			    case "Logo":
			       RenderUtil.instance.draw2DImage(new ResourceLocation("textures/logo.png"), 5,  -10, 110, 100, Color.WHITE);
			        break;
			    case "Text":
			    	Gui.drawRect(2, 2, 28 + Hime.instance.cfr.getWidth(Hime.instance.name), 26, 0x80000000);
			    	Gui.drawRect(4, 22, 26 + Hime.instance.cfr.getWidth(Hime.instance.name), 23, -1);
			    	Hime.instance.cfr.drawString(Hime.instance.name, 4, 7, -1);
			    	font.drawString(Hime.instance.version, 6 + Hime.instance.cfr.getWidth(Hime.instance.name), 7, RainbowUtil.rainbow(300));
			    	break;
			    }
		}
		
		
		
	    //Arraylist
		ArrayList<Module> enabledMods = new ArrayList<Module>();
		for(Module m : Hime.instance.moduleManager.getModules())
			if(m.isToggled()){
		      if(!(m.getCategory() == Category.TARGETS)) {
		    	if(m.visible)
				 enabledMods.add(m);
		      }
			}
		
		/*for(File file : ScriptManager.instance.dir.listFiles()) {
			String correctName = file.getName().replace(".txt", "");
		}*/
		
		/*ArrayList<String> enabledScripts = new ArrayList<String>();
		int countReal = 0;
		for(File file : ScriptManager.instance.dir.listFiles()) {
			  if(ScriptManager.instance.enabledScripts.get(countReal).equalsIgnoreCase("true")) {
				String realName = file.getName().replace(".txt", "");
				enabledScripts.add(realName);
			  }
			  countReal++;
		}*/
		
		//   Collections.sort(Hime.instance.moduleManager.getModules(),new ModuleComparator());
		
		
	  if(this.list.getValString().equalsIgnoreCase("Hime")) {
		 if(this.isFontCustom()) {
		  enabledMods.sort((m1, m2) -> (int) font.getWidth(m2.getSuffixedName()) - (int) font.getWidth(m1.getSuffixedName()));
		 }else{
		  enabledMods.sort((m1, m2) -> (int) fr.getStringWidth(m2.getSuffixedName()) - (int) fr.getStringWidth(m1.getSuffixedName())); 
		 }
		  int y = 2;
		for(Module m : enabledMods) {
		 if(this.isFontCustom()) {
			font.drawStringWithShadow(m.getSuffixedName(), sr.getScaledWidth() - font.getWidth(m.getSuffixedName()) - 2, y, -1);
		 }else{
			fr.drawStringWithShadow(m.getSuffixedName(), sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 2, y, -1);
		 }
			y += 11;
		}
	  }
	  if(this.list.getValString().equalsIgnoreCase("German")) {
			  enabledMods.sort((m1, m2) -> (int) fr.getStringWidth(m2.getSuffixedName()) - (int) fr.getStringWidth(m1.getSuffixedName())); 
			  int y = 2;
			for(Module m : enabledMods) {
				fr.drawStringWithShadow(m.getSuffixedName(), sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 2, y, m.getCategory().color);
				y += 11;
			}
		  }
	  if(this.list.getValString().equalsIgnoreCase("Client Color")) {
			 if(this.isFontCustom()) {
				  enabledMods.sort(Comparator.comparingDouble(m -> 
				  GuiButton.sgsmall.getWidth(((Module)m).getSuffixedName()))
						  .reversed()
						);
			 }else{
				  enabledMods.sort(Comparator.comparingDouble(m -> 
				  fr.getStringWidth(((Module)m).getSuffixedName()))
						  .reversed()
						);
			 }
			  int y2 = 0 + this.offset2.getValInt();
				for(Module m : enabledMods) {
					int color2;
					  
					final float hue = (float) (ColorUtil.getClickGUIColor());
			  		   //                           colorSaturation  colorBrightness 
			  	    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
			  	   color2 = ColorUtil.pulseSaturation(color,  y2 * this.rainbow1.getValInt(), 80).getRGB();
				//	color2 = ColorUtil.pulseSaturation(color, 50, (int) (y * 2 + 10)).getRGB();
			  	   if(this.background.getValBoolean()) {
				  		 if(this.isFontCustom()) {
				  		Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, new Color(0, 0, 0, this.transparency.getValInt()).getRGB());
				  		 }else {
				  	    Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, new Color(0, 0, 0, this.transparency.getValInt()).getRGB()); 
				  		 }
				  	   }
			  	   if(this.bar.getValBoolean()) {
			  	    switch(barMode) {
			  	     case "Left":
			  	    if(this.isFontCustom()) {
			  	       Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
			  	    }else {
			  	       Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);	
			  	    }
			  	      break; 
			  	     case "Right":
			  	   	   Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
			  	   	  break;
			  	   case "Top":
			  		if(this.isFontCustom()) {
			  		 Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), color.getRGB());
			  		}else {
			  	     Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), color.getRGB());	
			  		}
			  		 break;
			  	   case "Bordered":
				  	   int toggledIndex = enabledMods.indexOf(m);
		               int m1Offset = -1;
		             if (toggledIndex != enabledMods.size() - 1) {
		            	 if(this.isFontCustom()) {
		               m1Offset += GuiButton.sgsmall.getWidth(enabledMods.get(toggledIndex + 1).getSuffixedName()) + 3;
		            	 }else {
		            	m1Offset += fr.getStringWidth(enabledMods.get(toggledIndex + 1).getSuffixedName()) + 4; 
		            	 }
		               }
		             if(this.isFontCustom()) {
		   	         Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
			         Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3- this.offset2.getValInt(), y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 2, sr.getScaledWidth() - m1Offset- this.offset2.getValInt() - 1, y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 1, color2);
		             }else {
		            	 Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);
				         Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5- this.offset2.getValInt(), y2 + fr.FONT_HEIGHT + 2, sr.getScaledWidth() - m1Offset- this.offset2.getValInt() - 1, y2 + fr.FONT_HEIGHT + 1, color2); 
		             }
			         break; 
			  	   case "All":
			  		   if(this.isFontCustom()) {
			  		   //top
		     		 Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), color.getRGB());
		     	 	  //right 
		     		 Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
		     		 //bordered
		     		 int toggledIndex2 = enabledMods.indexOf(m);
	                 int m1Offset2 = -1;
	                 if (toggledIndex2 != enabledMods.size() - 1) {
	                    m1Offset2 += GuiButton.sgsmall.getWidth(enabledMods.get(toggledIndex2 + 1).getSuffixedName()) + 3;
	                  }
			  	     Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
			  	     Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3- this.offset2.getValInt(), y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 2, sr.getScaledWidth() - m1Offset2- this.offset2.getValInt() - 1, y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 1, color2);
			  		   }else {
				  	   //top
			     	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), color.getRGB());
			      	   //right 
			   		   Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
			   		   //bordered
		         	   int toggledIndex2 = enabledMods.indexOf(m);
                       int m1Offset2 = -1;
		               if (toggledIndex2 != enabledMods.size() - 1) {
		                m1Offset2 += fr.getStringWidth(enabledMods.get(toggledIndex2 + 1).getSuffixedName()) + 3;
		               }
			    	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);
				  	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5- this.offset2.getValInt(), y2 + fr.FONT_HEIGHT + 2, sr.getScaledWidth() - m1Offset2- this.offset2.getValInt() - 2, y2 + fr.FONT_HEIGHT + 1, color2);			  			   
			  		   }
			  	     break;
			  	    }
			  	   }
			  	   y2 += 11;
				}
				
			  int y = 0+ this.offset2.getValInt();
			for(Module m : enabledMods) {
				int color2;
				  
				final float hue = (float) (ColorUtil.getClickGUIColor());
		  		   //                           colorSaturation  colorBrightness 
		  	    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
		  	   color2 = ColorUtil.pulseSaturation(color,  y * this.rainbow1.getValInt(), 80).getRGB();
			//	color2 = ColorUtil.pulseSaturation(color, 50, (int) (y * 2 + 10)).getRGB();
			 if(this.isFontCustom()) {
				  GuiButton.sgsmall.drawStringWithShadow(m.getSuffixedName(), sr.getScaledWidth() - 1 - GuiButton.sgsmall.getWidth(m.getSuffixedName())- this.offset2.getValInt(), y, color2);
			 }else{
				fr.drawStringWithShadow(m.getSuffixedName(), sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 2- this.offset2.getValInt(), y + 1, color2);
			 }
				y += 11;
			}
		  }
	  if(this.list.getValString().equalsIgnoreCase("Client Color2")) {
			 if(this.isFontCustom()) {
				  enabledMods.sort(Comparator.comparingDouble(m -> 
				  GuiButton.sgsmall.getWidth(((Module)m).getSuffixedName()))
						  .reversed()
						);
			 }else{
				  enabledMods.sort(Comparator.comparingDouble(m -> 
				  fr.getStringWidth(((Module)m).getSuffixedName()))
						  .reversed()
						);
			 }
			  int y2 = 0 + this.offset2.getValInt();
				for(Module m : enabledMods) {
					int color2;
					  
					final float hue = (float) (ColorUtil.getClickGUIColor());
			  		   //                           colorSaturation  colorBrightness 
			  	    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
			  	   color2 = ColorUtil.pulseBrightness(color,  y2 * this.rainbow1.getValInt(), 80).getRGB();
				//	color2 = ColorUtil.pulseSaturation(color, 50, (int) (y * 2 + 10)).getRGB();
			  	   if(this.background.getValBoolean()) {
			  		 if(this.isFontCustom()) {
			  		Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, new Color(0, 0, 0, this.transparency.getValInt()).getRGB());
			  		 }else {
			  	    Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, new Color(0, 0, 0, this.transparency.getValInt()).getRGB()); 
			  		 }
			  	   }
			  	 if(this.bar.getValBoolean()) {
				  	    switch(barMode) {
				  	     case "Left":
				  	    if(this.isFontCustom()) {
				  	       Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
				  	    }else {
				  	       Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);	
				  	    }
				  	      break; 
				  	     case "Right":
				  	   	   Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
				  	   	  break;
				  	   case "Top":
				  		if(this.isFontCustom()) {
				  		 Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), color.getRGB());
				  		}else {
				  	     Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), color.getRGB());	
				  		}
				  		 break;
				  	   case "Bordered":
					  	   int toggledIndex = enabledMods.indexOf(m);
			               int m1Offset = -1;
			             if (toggledIndex != enabledMods.size() - 1) {
			            	 if(this.isFontCustom()) {
			               m1Offset += GuiButton.sgsmall.getWidth(enabledMods.get(toggledIndex + 1).getSuffixedName()) + 3;
			            	 }else {
			            	m1Offset += fr.getStringWidth(enabledMods.get(toggledIndex + 1).getSuffixedName()) + 4; 
			            	 }
			               }
			             if(this.isFontCustom()) {
			   	         Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
				         Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3- this.offset2.getValInt(), y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 2, sr.getScaledWidth() - m1Offset- this.offset2.getValInt() - 1, y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 1, color2);
			             }else {
			            	 Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);
					         Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5- this.offset2.getValInt(), y2 + fr.FONT_HEIGHT + 2, sr.getScaledWidth() - m1Offset- this.offset2.getValInt() - 1, y2 + fr.FONT_HEIGHT + 1, color2); 
			             }
				         break; 
				  	   case "All":
				  		   if(this.isFontCustom()) {
				  		   //top
			     		 Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), color.getRGB());
			     	 	  //right 
			     		 Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
			     		 //bordered
			     		 int toggledIndex2 = enabledMods.indexOf(m);
		                 int m1Offset2 = -1;
		                 if (toggledIndex2 != enabledMods.size() - 1) {
		                    m1Offset2 += GuiButton.sgsmall.getWidth(enabledMods.get(toggledIndex2 + 1).getSuffixedName()) + 3;
		                  }
				  	     Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
				  	     Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3- this.offset2.getValInt(), y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 2, sr.getScaledWidth() - m1Offset2- this.offset2.getValInt() - 1, y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 1, color2);
				  		   }else {
					  	   //top
				     	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), color.getRGB());
				      	   //right 
				   		   Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
				   		   //bordered
			         	   int toggledIndex2 = enabledMods.indexOf(m);
	                       int m1Offset2 = -1;
			               if (toggledIndex2 != enabledMods.size() - 1) {
			                m1Offset2 += fr.getStringWidth(enabledMods.get(toggledIndex2 + 1).getSuffixedName()) + 3;
			               }
				    	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);
					  	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5- this.offset2.getValInt(), y2 + fr.FONT_HEIGHT + 2, sr.getScaledWidth() - m1Offset2- this.offset2.getValInt() - 2, y2 + fr.FONT_HEIGHT + 1, color2);			  			   
				  		   }
				  	     break;
				  	    }
				  	   }
			  	   y2 += 11;
				}
				
			  int y = 0+ this.offset2.getValInt();
			for(Module m : enabledMods) {
				int color2;
				  
				final float hue = (float) (ColorUtil.getClickGUIColor());
		  		   //                           colorSaturation  colorBrightness 
		  	    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
		  	   color2 = ColorUtil.pulseBrightness(color,  y * this.rainbow1.getValInt(), 80).getRGB();
			//	color2 = ColorUtil.pulseSaturation(color, 50, (int) (y * 2 + 10)).getRGB();
			 if(this.isFontCustom()) {
				  GuiButton.sgsmall.drawStringWithShadow(m.getSuffixedName(), sr.getScaledWidth() - 1 - GuiButton.sgsmall.getWidth(m.getSuffixedName())- this.offset2.getValInt(), y, color2);
			 }else{
				fr.drawStringWithShadow(m.getSuffixedName(), sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 2- this.offset2.getValInt(), y+ 1, color2);
			 }
				y += 11;
			}
		  }
	  if(this.list.getValString().equalsIgnoreCase("Client Color3")) {
			 if(this.isFontCustom()) {
				  enabledMods.sort(Comparator.comparingDouble(m -> 
				  GuiButton.sgsmall.getWidth(((Module)m).getSuffixedName()))
						  .reversed()
						);
			 }else{
				  enabledMods.sort(Comparator.comparingDouble(m -> 
				  fr.getStringWidth(((Module)m).getSuffixedName()))
						  .reversed()
						);
			 }
			  int y2 = 0 + this.offset2.getValInt();
				for(Module m : enabledMods) {
					  int color2;
					final float hue = (float) (Hime.instance.settingsManager.getSettingByName("Hue").getValDouble() / 255);
			  		   //                           colorSaturation  colorBrightness 
			  	    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
				//	color2 = ColorUtil.pulseSaturation(color, 50, (int) (y * 2 + 10)).getRGB();
			  	    color2 = color.getRGB();
			  	    if(this.background.getValBoolean()) {
			  		 if(this.isFontCustom()) {
			  		Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, new Color(0, 0, 0, this.transparency.getValInt()).getRGB());
			  		 }else {
			  	    Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, new Color(0, 0, 0, this.transparency.getValInt()).getRGB()); 
			  		 }
			  	   }
			  	  if(this.bar.getValBoolean()) {
				  	    switch(barMode) {
				  	     case "Left":
				  	    if(this.isFontCustom()) {
				  	       Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
				  	    }else {
				  	       Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);	
				  	    }
				  	      break; 
				  	     case "Right":
				  	   	   Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
				  	   	  break;
				  	   case "Top":
				  		if(this.isFontCustom()) {
				  		 Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), color.getRGB());
				  		}else {
				  	     Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), color.getRGB());	
				  		}
				  		 break;
				  	   case "Bordered":
					  	   int toggledIndex = enabledMods.indexOf(m);
			               int m1Offset = -1;
			             if (toggledIndex != enabledMods.size() - 1) {
			            	 if(this.isFontCustom()) {
			               m1Offset += GuiButton.sgsmall.getWidth(enabledMods.get(toggledIndex + 1).getSuffixedName()) + 3;
			            	 }else {
			            	m1Offset += fr.getStringWidth(enabledMods.get(toggledIndex + 1).getSuffixedName()) + 4; 
			            	 }
			               }
			             if(this.isFontCustom()) {
			   	         Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
				         Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3- this.offset2.getValInt(), y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 2, sr.getScaledWidth() - m1Offset- this.offset2.getValInt() - 1, y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 1, color2);
			             }else {
			            	 Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);
					         Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5- this.offset2.getValInt(), y2 + fr.FONT_HEIGHT + 2, sr.getScaledWidth() - m1Offset- this.offset2.getValInt() - 1, y2 + fr.FONT_HEIGHT + 1, color2); 
			             }
				         break; 
				  	   case "All":
				  		   if(this.isFontCustom()) {
				  		   //top
			     		 Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), color.getRGB());
			     	 	  //right 
			     		 Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
			     		 //bordered
			     		 int toggledIndex2 = enabledMods.indexOf(m);
		                 int m1Offset2 = -1;
		                 if (toggledIndex2 != enabledMods.size() - 1) {
		                    m1Offset2 += GuiButton.sgsmall.getWidth(enabledMods.get(toggledIndex2 + 1).getSuffixedName()) + 3;
		                  }
				  	     Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
				  	     Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3- this.offset2.getValInt(), y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 2, sr.getScaledWidth() - m1Offset2- this.offset2.getValInt() - 1, y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 1, color2);
				  		   }else {
					  	   //top
				     	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), color.getRGB());
				      	   //right 
				   		   Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
				   		   //bordered
			         	   int toggledIndex2 = enabledMods.indexOf(m);
	                       int m1Offset2 = -1;
			               if (toggledIndex2 != enabledMods.size() - 1) {
			                m1Offset2 += fr.getStringWidth(enabledMods.get(toggledIndex2 + 1).getSuffixedName()) + 3;
			               }
				    	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);
					  	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5- this.offset2.getValInt(), y2 + fr.FONT_HEIGHT + 2, sr.getScaledWidth() - m1Offset2- this.offset2.getValInt() - 2, y2 + fr.FONT_HEIGHT + 1, color2);			  			   
				  		   }
				  	     break;
				  	    }
				  	   }
			  	   y2 += 11;
				}
				
			  int y = 0+ this.offset2.getValInt();
			for(Module m : enabledMods) {
				  
				  int color2;
					final float hue = (float) (Hime.instance.settingsManager.getSettingByName("Hue").getValDouble() / 255);
			  		   //                           colorSaturation  colorBrightness 
			  	    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
				//	color2 = ColorUtil.pulseSaturation(color, 50, (int) (y * 2 + 10)).getRGB();
			  	    color2 = color.getRGB();
			//	color2 = ColorUtil.pulseSaturation(color, 50, (int) (y * 2 + 10)).getRGB();
			 if(this.isFontCustom()) {
				  GuiButton.sgsmall.drawStringWithShadow(m.getSuffixedName(), sr.getScaledWidth() - 1 - GuiButton.sgsmall.getWidth(m.getSuffixedName())- this.offset2.getValInt(), y, color2);
			 }else{
				fr.drawStringWithShadow(m.getSuffixedName(), sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 2- this.offset2.getValInt(), y+1, color2);
			 }
				y += 11;
			}
		  }
	  if(this.list.getValString().equalsIgnoreCase("Rainbow")) {
			 if(this.isFontCustom()) {
				  enabledMods.sort(Comparator.comparingDouble(m -> 
				  GuiButton.sgsmall.getWidth(((Module)m).getSuffixedName()))
						  .reversed()
						);
			 }else{
				  enabledMods.sort(Comparator.comparingDouble(m -> 
				  fr.getStringWidth(((Module)m).getSuffixedName()))
						  .reversed()
						);
			 }
			  int y2 = 0 + this.offset2.getValInt();
				for(Module m : enabledMods) {
					int color2;
					  
					final float hue = (float) (ColorUtil.getClickGUIColor());
			  		   //                           colorSaturation  colorBrightness 
			  	    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
			  	   color2 = RainbowUtil.getRainbow(this.rainbow2.getValInt() * 100, y2 * this.rainbow1.getValInt());
				//	color2 = ColorUtil.pulseSaturation(color, 50, (int) (y * 2 + 10)).getRGB();
			  	   if(this.background.getValBoolean()) {
				  		 if(this.isFontCustom()) {
				  		Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, new Color(0, 0, 0, this.transparency.getValInt()).getRGB());
				  		 }else {
				  	    Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, new Color(0, 0, 0, this.transparency.getValInt()).getRGB()); 
				  		 }
				  	   }
			  	 if(this.bar.getValBoolean()) {
				  	    switch(barMode) {
				  	     case "Left":
				  	    if(this.isFontCustom()) {
				  	       Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
				  	    }else {
				  	       Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);	
				  	    }
				  	      break; 
				  	     case "Right":
				  	   	   Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
				  	   	  break;
				  	   case "Top":
				  		if(this.isFontCustom()) {
				  		 Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), RainbowUtil.getRainbow(this.rainbow2.getValInt() * 100, 1 * this.rainbow1.getValInt()));
				  		}else {
				  	     Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), RainbowUtil.getRainbow(this.rainbow2.getValInt() * 100, 1 * this.rainbow1.getValInt()));	
				  		}
				  		 break;
				  	   case "Bordered":
					  	   int toggledIndex = enabledMods.indexOf(m);
			               int m1Offset = -1;
			             if (toggledIndex != enabledMods.size() - 1) {
			            	 if(this.isFontCustom()) {
			               m1Offset += GuiButton.sgsmall.getWidth(enabledMods.get(toggledIndex + 1).getSuffixedName()) + 3;
			            	 }else {
			            	m1Offset += fr.getStringWidth(enabledMods.get(toggledIndex + 1).getSuffixedName()) + 4; 
			            	 }
			               }
			             if(this.isFontCustom()) {
			   	         Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
				         Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3- this.offset2.getValInt(), y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 2, sr.getScaledWidth() - m1Offset- this.offset2.getValInt() - 1, y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 1, color2);
			             }else {
			            	 Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);
					         Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5- this.offset2.getValInt(), y2 + fr.FONT_HEIGHT + 2, sr.getScaledWidth() - m1Offset- this.offset2.getValInt() - 1, y2 + fr.FONT_HEIGHT + 1, color2); 
			             }
				         break; 
				  	   case "All":
				  		   if(this.isFontCustom()) {
				  		   //top
			     		 Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), RainbowUtil.getRainbow(this.rainbow2.getValInt() * 100, 1 * this.rainbow1.getValInt()));
			     	 	  //right 
			     		 Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
			     		 //bordered
			     		 int toggledIndex2 = enabledMods.indexOf(m);
		                 int m1Offset2 = -1;
		                 if (toggledIndex2 != enabledMods.size() - 1) {
		                    m1Offset2 += GuiButton.sgsmall.getWidth(enabledMods.get(toggledIndex2 + 1).getSuffixedName()) + 3;
		                  }
				  	     Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
				  	     Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3- this.offset2.getValInt(), y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 2, sr.getScaledWidth() - m1Offset2- this.offset2.getValInt() - 1, y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 1, color2);
				  		   }else {
					  	   //top
				     	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), RainbowUtil.getRainbow(this.rainbow2.getValInt() * 100, 1 * this.rainbow1.getValInt()));
				      	   //right 
				   		   Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
				   		   //bordered
			         	   int toggledIndex2 = enabledMods.indexOf(m);
	                       int m1Offset2 = -1;
			               if (toggledIndex2 != enabledMods.size() - 1) {
			                m1Offset2 += fr.getStringWidth(enabledMods.get(toggledIndex2 + 1).getSuffixedName()) + 3;
			               }
				    	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);
					  	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5- this.offset2.getValInt(), y2 + fr.FONT_HEIGHT + 2, sr.getScaledWidth() - m1Offset2- this.offset2.getValInt() - 2, y2 + fr.FONT_HEIGHT + 1, color2);			  			   
				  		   }
				  	     break;
				  	    }
				  	   }
			  	   y2 += 11;
				}
				
			  int y = 0+ this.offset2.getValInt();
			for(Module m : enabledMods) {
				int color2;
				  
				final float hue = (float) (ColorUtil.getClickGUIColor());
		  		   //                           colorSaturation  colorBrightness 
		  	    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
		  	   color2 = ColorUtil.pulseBrightness(color,  y * 8, 80).getRGB();
			//	color2 = ColorUtil.pulseSaturation(color, 50, (int) (y * 2 + 10)).getRGB();
			 if(this.isFontCustom()) {
				  GuiButton.sgsmall.drawStringWithShadow(m.getSuffixedName(), sr.getScaledWidth() - 1 - GuiButton.sgsmall.getWidth(m.getSuffixedName())- this.offset2.getValInt(), y, RainbowUtil.getRainbow(this.rainbow2.getValInt() * 100, y * this.rainbow1.getValInt()));
			 }else{
				fr.drawStringWithShadow(m.getSuffixedName(), sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 2- this.offset2.getValInt(), y+1, RainbowUtil.getRainbow(this.rainbow2.getValInt() * 100, y * this.rainbow1.getValInt()));
			 }
				y += 11;
			}
		  }if(this.list.getValString().equalsIgnoreCase("Rainbow2")) {
					 if(this.isFontCustom()) {
						  enabledMods.sort(Comparator.comparingDouble(m -> 
						  GuiButton.sgsmall.getWidth(((Module)m).getSuffixedName()))
								  .reversed()
								);
					 }else{
						  enabledMods.sort(Comparator.comparingDouble(m -> 
						  fr.getStringWidth(((Module)m).getSuffixedName()))
								  .reversed()
								);
					 }
					  int y2 = 0 + this.offset2.getValInt();
						for(Module m : enabledMods) {
							int color2;
							  
							final float hue = (float) (ColorUtil.getClickGUIColor());
					  		   //                           colorSaturation  colorBrightness 
					  	    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
					  	   color2 = RainbowUtil.getRainbow(this.rainbow2.getValInt() * 100, y2 * this.rainbow1.getValInt(), 1, 0.5f);
						//	color2 = ColorUtil.pulseSaturation(color, 50, (int) (y * 2 + 10)).getRGB();
					  	   if(this.background.getValBoolean()) {
						  		 if(this.isFontCustom()) {
						  		Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, new Color(0, 0, 0, this.transparency.getValInt()).getRGB());
						  		 }else {
						  	    Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, new Color(0, 0, 0, this.transparency.getValInt()).getRGB()); 
						  		 }
						  	   }
					  	 if(this.bar.getValBoolean()) {
						  	    switch(barMode) {
						  	     case "Left":
						  	    if(this.isFontCustom()) {
						  	       Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
						  	    }else {
						  	       Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);	
						  	    }
						  	      break; 
						  	     case "Right":
						  	   	   Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
						  	   	  break;
						  	   case "Top":
						  		if(this.isFontCustom()) {
						  		 Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), RainbowUtil.getRainbow(this.rainbow2.getValInt() * 100, 1 * this.rainbow1.getValInt(), 1, 0.5f));
						  		}else {
						  	     Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), RainbowUtil.getRainbow(this.rainbow2.getValInt() * 100, 1 * this.rainbow1.getValInt(), 1, 0.5f));	
						  		}
						  		 break;
						  	   case "Bordered":
							  	   int toggledIndex = enabledMods.indexOf(m);
					               int m1Offset = -1;
					             if (toggledIndex != enabledMods.size() - 1) {
					            	 if(this.isFontCustom()) {
					               m1Offset += GuiButton.sgsmall.getWidth(enabledMods.get(toggledIndex + 1).getSuffixedName()) + 3;
					            	 }else {
					            	m1Offset += fr.getStringWidth(enabledMods.get(toggledIndex + 1).getSuffixedName()) + 4; 
					            	 }
					               }
					             if(this.isFontCustom()) {
					   	         Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
						         Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3- this.offset2.getValInt(), y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 2, sr.getScaledWidth() - m1Offset- this.offset2.getValInt() - 1, y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 1, color2);
					             }else {
					            	 Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);
							         Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5- this.offset2.getValInt(), y2 + fr.FONT_HEIGHT + 2, sr.getScaledWidth() - m1Offset- this.offset2.getValInt() - 1, y2 + fr.FONT_HEIGHT + 1, color2); 
					             }
						         break; 
						  	   case "All":
						  		   if(this.isFontCustom()) {
						  		   //top
					     		 Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), RainbowUtil.getRainbow(this.rainbow2.getValInt() * 100, 1 * this.rainbow1.getValInt(), 1, 0.5f));
					     	 	  //right 
					     		 Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
					     		 //bordered
					     		 int toggledIndex2 = enabledMods.indexOf(m);
				                 int m1Offset2 = -1;
				                 if (toggledIndex2 != enabledMods.size() - 1) {
				                    m1Offset2 += GuiButton.sgsmall.getWidth(enabledMods.get(toggledIndex2 + 1).getSuffixedName()) + 3;
				                  }
						  	     Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
						  	     Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3- this.offset2.getValInt(), y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 2, sr.getScaledWidth() - m1Offset2- this.offset2.getValInt() - 1, y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 1, color2);
						  		   }else {
							  	   //top
						     	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(),RainbowUtil.getRainbow(this.rainbow2.getValInt() * 100, 1 * this.rainbow1.getValInt(), 1, 0.5f));
						      	   //right 
						   		   Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
						   		   //bordered
					         	   int toggledIndex2 = enabledMods.indexOf(m);
			                       int m1Offset2 = -1;
					               if (toggledIndex2 != enabledMods.size() - 1) {
					                m1Offset2 += fr.getStringWidth(enabledMods.get(toggledIndex2 + 1).getSuffixedName()) + 3;
					               }
						    	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);
							  	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5- this.offset2.getValInt(), y2 + fr.FONT_HEIGHT + 2, sr.getScaledWidth() - m1Offset2- this.offset2.getValInt() - 2, y2 + fr.FONT_HEIGHT + 1, color2);			  			   
						  		   }
						  	     break;
						  	    }
						  	   }
					  	   y2 += 11;
						}
						
					  int y = 0+ this.offset2.getValInt();
					for(Module m : enabledMods) {
						int color2;
						  
						final float hue = (float) (ColorUtil.getClickGUIColor());
				  		   //                           colorSaturation  colorBrightness 
				  	    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
				  	   color2 = ColorUtil.pulseBrightness(color,  y * 8, 80).getRGB();
					//	color2 = ColorUtil.pulseSaturation(color, 50, (int) (y * 2 + 10)).getRGB();
					 if(this.isFontCustom()) {
						  GuiButton.sgsmall.drawStringWithShadow(m.getSuffixedName(), sr.getScaledWidth() - 1 - GuiButton.sgsmall.getWidth(m.getSuffixedName())- this.offset2.getValInt(), y, 	RainbowUtil.getRainbow(this.rainbow2.getValInt() * 100, y * this.rainbow1.getValInt(), 1, 0.5f));
					 }else{
						fr.drawStringWithShadow(m.getSuffixedName(), sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 2- this.offset2.getValInt(), y+1,RainbowUtil.getRainbow(this.rainbow2.getValInt() * 100, y * this.rainbow1.getValInt(), 1, 0.5f));
																													
					 }
						y += 11;
					}
		}if(this.list.getValString().equalsIgnoreCase("Astolfo")) {
			// this.astolfo(3000, (float) (y * (2.3 * 3.7)))
			 if(this.isFontCustom()) {
				  enabledMods.sort(Comparator.comparingDouble(m -> 
				  GuiButton.sgsmall.getWidth(((Module)m).getSuffixedName()))
						  .reversed()
						);
			 }else{
				  enabledMods.sort(Comparator.comparingDouble(m -> 
				  fr.getStringWidth(((Module)m).getSuffixedName()))
						  .reversed()
						);
			 }
			  int y2 = 0 + this.offset2.getValInt();
				for(Module m : enabledMods) {
					int color2;
					  
					final float hue = (float) (ColorUtil.getClickGUIColor());
			  		   //                           colorSaturation  colorBrightness 
			  	    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
			  	   color2 =  this.astolfo(3000, (float) (y2 * (2.3 * 3.7)));
				//	color2 = ColorUtil.pulseSaturation(color, 50, (int) (y * 2 + 10)).getRGB();
			  	   if(this.background.getValBoolean()) {
				  		 if(this.isFontCustom()) {
				  		Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, new Color(0, 0, 0, this.transparency.getValInt()).getRGB());
				  		 }else {
				  	    Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, new Color(0, 0, 0, this.transparency.getValInt()).getRGB()); 
				  		 }
				  	   }
			  	 if(this.bar.getValBoolean()) {
				  	    switch(barMode) {
				  	     case "Left":
				  	    if(this.isFontCustom()) {
				  	       Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
				  	    }else {
				  	       Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);	
				  	    }
				  	      break; 
				  	     case "Right":
				  	   	   Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
				  	   	  break;
				  	   case "Top":
				  		if(this.isFontCustom()) {
				  		 Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), this.astolfo(3000, (float) (1 * (2.3 * 3.7))));
				  		}else {
				  	     Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(), this.astolfo(3000, (float) (1 * (2.3 * 3.7))));	
				  		}
				  		 break;
				  	   case "Bordered":
					  	   int toggledIndex = enabledMods.indexOf(m);
			               int m1Offset = -1;
			             if (toggledIndex != enabledMods.size() - 1) {
			            	 if(this.isFontCustom()) {
			               m1Offset += GuiButton.sgsmall.getWidth(enabledMods.get(toggledIndex + 1).getSuffixedName()) + 3;
			            	 }else {
			            	m1Offset += fr.getStringWidth(enabledMods.get(toggledIndex + 1).getSuffixedName()) + 4; 
			            	 }
			               }
			             if(this.isFontCustom()) {
			   	         Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
				         Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3- this.offset2.getValInt(), y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 2, sr.getScaledWidth() - m1Offset- this.offset2.getValInt() - 1, y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 1, color2);
			             }else {
			            	 Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);
					         Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5- this.offset2.getValInt(), y2 + fr.FONT_HEIGHT + 2, sr.getScaledWidth() - m1Offset- this.offset2.getValInt() - 1, y2 + fr.FONT_HEIGHT + 1, color2); 
			             }
				         break; 
				  	   case "All":
				  		   if(this.isFontCustom()) {
				  		   //top
			     		 Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(),this.astolfo(3000, (float) (1 * (2.3 * 3.7))));
			     	 	  //right 
			     		 Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
			     		 //bordered
			     		 int toggledIndex2 = enabledMods.indexOf(m);
		                 int m1Offset2 = -1;
		                 if (toggledIndex2 != enabledMods.size() - 1) {
		                    m1Offset2 += GuiButton.sgsmall.getWidth(enabledMods.get(toggledIndex2 + 1).getSuffixedName()) + 3;
		                  }
				  	     Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3  - this.offset2.getValInt(), y2, sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y2 + 11, color2);
				  	     Gui.drawRect(sr.getScaledWidth() - GuiButton.sgsmall.getWidth(m.getSuffixedName()) - 3- this.offset2.getValInt(), y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 2, sr.getScaledWidth() - m1Offset2- this.offset2.getValInt() - 1, y2 + GuiButton.sgsmall.getHeight(m.getSuffixedName()) + 1, color2);
				  		   }else {
					  	   //top
				     	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4  - this.offset2.getValInt(), 0 + this.offset2.getValInt(), sr.getScaledWidth() - this.offset2.getValInt(), 1 + this.offset2.getValInt(),this.astolfo(3000, (float) (1 * (2.3 * 3.7))));
				      	   //right 
				   		   Gui.drawRect(sr.getScaledWidth() - 1  - this.offset2.getValInt(), y2, sr.getScaledWidth() - this.offset2.getValInt(), y2 + 11, color2);
				   		   //bordered
			         	   int toggledIndex2 = enabledMods.indexOf(m);
	                       int m1Offset2 = -1;
			               if (toggledIndex2 != enabledMods.size() - 1) {
			                m1Offset2 += fr.getStringWidth(enabledMods.get(toggledIndex2 + 1).getSuffixedName()) + 3;
			               }
				    	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5  - this.offset2.getValInt(), y2, sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 4 - this.offset2.getValInt(), y2 + 11, color2);
					  	   Gui.drawRect(sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 5- this.offset2.getValInt(), y2 + fr.FONT_HEIGHT + 2, sr.getScaledWidth() - m1Offset2- this.offset2.getValInt() - 2, y2 + fr.FONT_HEIGHT + 1, color2);			  			   
				  		   }
				  	     break;
				  	    }
				  	   }
			  	   y2 += 11;
				}
				
			  int y = 0+ this.offset2.getValInt();
			for(Module m : enabledMods) {
				int color2;
				  
				final float hue = (float) (ColorUtil.getClickGUIColor());
		  		   //                           colorSaturation  colorBrightness 
		  	    Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
		  	   color2 = ColorUtil.pulseBrightness(color,  y * 8, 80).getRGB();
			//	color2 = ColorUtil.pulseSaturation(color, 50, (int) (y * 2 + 10)).getRGB();
			 if(this.isFontCustom()) {
			//	 float diff = m.mSize - m.lastSize;
	               // m.lastSize += diff / 4;
				  GuiButton.sgsmall.drawStringWithShadow(m.getSuffixedName(), sr.getScaledWidth() - 1 - GuiButton.sgsmall.getWidth(m.getSuffixedName())- this.offset2.getValInt(), y, this.astolfo(3000, (float) (y * (2.3 * 3.7))));
			 }else{
				fr.drawStringWithShadow(m.getSuffixedName(), sr.getScaledWidth() - fr.getStringWidth(m.getSuffixedName()) - 2 - this.offset2.getValInt(), y+1, this.astolfo(3000, (float) (y * (2.3 * 3.7))));
																											
			 }
				y += 11;
			}
		}if(this.list.getValString().equalsIgnoreCase("Zeroday")) {
		  enabledMods.sort(Comparator.comparingDouble(m -> 
		  font.getWidth(((Module)m).getSuffixedName()))
				  .reversed()
				);
		
		int y2 = 0;
		for(Module m : enabledMods) {
			Gui.drawRect(sr.getScaledWidth() - font.getWidth(m.getSuffixedName()) - 4, y2, sr.getScaledWidth(), y2 + 11, 0x80000000);
			Gui.drawRect(sr.getScaledWidth() - font.getWidth(m.getSuffixedName()) - 6, y2, sr.getScaledWidth() - font.getWidth(m.getSuffixedName()) - 4, y2 + 11, RainbowUtil.rainbow(y2 * 10));
			y2 += 11;
		}
		int y = 2;
		for(Module m : enabledMods) {
			font.drawStringWithShadow(m.getSuffixedName(), sr.getScaledWidth() - font.getWidth(m.getSuffixedName()) - 2, y, RainbowUtil.rainbow(y * 10));
			y += 11;
		}
	  }else if(this.list.getValString().equalsIgnoreCase("Sigma Jello")) {
		  enabledMods.sort((m1, m2) -> (int)   Hime.instance.rfrs.getWidth(m2.getName()) - (int)  Hime.instance.rfrs.getWidth(m1.getName()));
			  int y2 = 0;
				//RenderUtil.drawGradientSideways(sr.getScaledWidth() -   Hime.instance.rfrs.getWidth("aaaaa") - 4, y2, sr.getScaledWidth(), y2 + 13, new Color(0, 0, 0, 0).getRGB(), 0x90000000);
			  for(Module m : enabledMods) {
				    float diff = m.mSize - m.lastSize;
	                m.lastSize += diff / 4;
	            
	               //Hime.instance.rfrs.drawString(m.getName(), sr.getScaledWidth() -   Hime.instance.rfrs.getWidth(m.getName()) - 5 + m.lastSize, y2, -1);
				  y2 += 13;
			  }
		  int y = 5;
		  for(Module m : enabledMods) {
			    float diff = m.mSize - m.lastSize;
                m.lastSize += diff / 4;
		
               Hime.instance.rfrs.drawString(m.getName(), sr.getScaledWidth() -   Hime.instance.rfrs.getWidth(m.getName()) - 5 + m.lastSize, y, -1);
			  y += 13;
		  }
	  }
	  
   }
	
	
	
	public void renderPotionStatus(final float n, final float n2) {
		 FontRenderer fr = mc.fontRendererObj;
	      this.x = 5;
	      for (PotionEffect potion2 : this.mc.thePlayer.getActivePotionEffects()) {
	          Potion potion = Potion.potionTypes[potion2.getPotionID()];
	          String name = I18n.format(potion.getName(), new Object[0]);
	        
	          switch (potion2.getAmplifier()) {
	              case 1: {
	            	  name += " 2";
	                  break;
	              }
	              case 2: {
	            	  name += " 3";
	                  break;
	              }
	              case 3: {
	            	  name += " 4";
	                  break;
	              }
	          }
	          final float y = n2 - this.mc.fontRendererObj.FONT_HEIGHT + this.x - 1f;
	          
	          double infoX = 0;
	          double infoY = 0;
	          final float n7 = n2 - this.mc.fontRendererObj.FONT_HEIGHT + this.x - 18.0f;
	       //   for(Component frame : GuiHudEditor.frames) {
	       	  // if(frame.getName().equalsIgnoreCase("Potion")) {
	       		//   infoX = frame.getX();
	       		//   infoY = frame.getY();
	       	  // }
	         // }
	          if(this.fontstyle.getValString().equalsIgnoreCase("Custom")) {
	        	  
	        	  //TODO
	           font.drawString(name + " " + potion.getDurationString(potion2),  n - font.getWidth(name + " " + potion.getDurationString(potion2)) - 2, n7 - this.mc.fontRendererObj.FONT_HEIGHT - 2, Color.WHITE.getRGB());
	          //font.drawString(name + " " + potion.getDurationString(potion2), (float)infoX, (float)infoY + y - 382, Color.WHITE.getRGB());
	          }else {
	           //fr.drawString(name + " " + potion.getDurationString(potion2), (int)infoX, (int)(infoY + y - 382), Color.GRAY.getRGB());
	           fr.drawString(name + " " + potion.getDurationString(potion2),  (int)(n - fr.getStringWidth(name + " " + potion.getDurationString(potion2)) - 2), (int)(n7 - this.mc.fontRendererObj.FONT_HEIGHT - 2), Color.WHITE.getRGB());
	          }
	          //ufr2.drawString(), n - 101.0f, y + 4.0f, Color.GRAY.getRGB());
	          if (potion.hasStatusIcon()) {
	            
	          }
	          this.x -= 10;
	      }
	  }
	 private void renderItemStack(int i, ItemStack itemstack) {
		    ScaledResolution sr = new ScaledResolution(mc);
		if(itemstack == null) {
			return;
		}
		GL11.glPushMatrix();
		int yAdd =  i * 15;
		
		double infoX = sr.getScaledWidth() / 2 + 10;
	    double infoY = sr.getScaledHeight() - 60;
	   // for(Component frame : GuiHudEditor.frames) {
	 	  // if(frame.getName().equalsIgnoreCase("Armor")) {
	 		//   infoX = frame.getX();
	 		//   infoY = frame.getY();
	 	  // }
	    //}
		
		RenderHelper.enableGUIStandardItemLighting();
		 ///  mc.getRenderItem().renderItemAndEffectIntoGUI(mc.thePlayer.getCurrentEquippedItem(), ((int)(sr.getScaledWidth() / 2) + 25) - 16, ((int)(sr.getScaledHeight()) - 55));
		mc.getRenderItem().renderItemAndEffectIntoGUI(itemstack, (int)infoX + yAdd, (int)infoY);
		GL11.glPopMatrix();
		
	}
	 
	 
	 public static Color white(int a) {
	      Color c = new Color(255, 255, 255, a);
	      return c;
	  }
	 
	 public void renderGraphs(){
	        GuiButton.ttfr.drawString("Packets Out | " + packetCount + " / Sec", 95+ this.xadd.getValInt(), 2.5f, -1);

	        GL11.glPushMatrix();
	        this.prepareScissorBox(95+ this.xadd.getValInt(), 12, 220+ this.xadd.getValInt(), 60);
	        GL11.glEnable(3089);

	        if(packetTimer.hasTimePassed(400)){
	            packetList.add(packetCount * 2);
	            packetCount = 0;
	            packetTimer.reset();
	        }

	        Gui.drawRect(95 + this.xadd.getValInt(), 12, 220+ this.xadd.getValInt(), 60, 0x90000000);


	     //   enableGL2D();
	        
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        
	        GL11.glLineWidth(1.5f);
	        // Tweak to edit coords
	        int x = 68+ this.xadd.getValInt();
	        int y = 55;
	        int space = 8; // space between points
	        // Draw

	        for (int i = 0; i < packetList.size(); i++) {
	            GL11.glBegin(3);
	            GL11.glColor4f(255, 255, 255, 255);
	            GL11.glVertex2f(x + i * space, y - packetList.get(i) / 4);
	            if (i + 1 < packetList.size())
	                GL11.glVertex2f(x + (i + 1) * space, y - packetList.get(i + 1) / 4);
	            GL11.glEnd();
	        }

	        // Shows max 20 elements
	        if (packetList.size() > 20) {
	            packetList.remove(0);
	        }
	        GL11.glLineWidth(1.5f);
	        GL11.glBegin(3);
	        GL11.glColor4f(0, 255, 0, 255);
	        GL11.glVertex2f(95+ this.xadd.getValInt(), y - packetList.get(packetList.size() - 1) / 4);
	        GL11.glVertex2f(220+ this.xadd.getValInt(), y - packetList.get(packetList.size() - 1) / 4);
	        GL11.glEnd();

	        //lines around graph
	        //    Gui.drawRect(95, 12, 220, 60, 0x90000000);
	        //bottom
	        GL11.glBegin(3);
	        GL11.glColor4f(255, 255, 255, 255);
	        GL11.glVertex2f(95+ this.xadd.getValInt(), 60);
	        GL11.glVertex2f(220+ this.xadd.getValInt(), 60);
	        GL11.glEnd();

	        //top
	        GL11.glBegin(3);
	        GL11.glColor4f(255, 255, 255, 255);
	        GL11.glVertex2f(95+ this.xadd.getValInt(), 12);
	        GL11.glVertex2f(220+ this.xadd.getValInt(), 12);
	        GL11.glEnd();

	        
	        //    Gui.drawRect(95, 12, 220, 60, 0x90000000);
	        //left
	        GL11.glBegin(3);
	        GL11.glColor4f(255, 255, 255, 255);
	        GL11.glVertex2f(95+ this.xadd.getValInt(), 12);
	        GL11.glVertex2f(95+ this.xadd.getValInt(), 60);
	        GL11.glEnd();

	        //right
	        GL11.glBegin(3);
	        GL11.glColor4f(255, 255, 255, 255);
	        GL11.glVertex2f(220+ this.xadd.getValInt(), 12);
	        GL11.glVertex2f(220+ this.xadd.getValInt(), 60);
	        GL11.glEnd();
	        
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);

	        GL11.glDisable(3089);
	        GL11.glPopMatrix();
	    }

	    public void prepareScissorBox(float x2, float y2, float x22, float y22) {
	        ScaledResolution scale = new ScaledResolution(this.mc);
	        int factor = scale.getScaleFactor();
	        GL11.glScissor((int)(x2 * (float)factor), (int)(((float)scale.getScaledHeight() - y22) * (float)factor), (int)((x22 - x2) * (float)factor), (int)((y22 - y2) * (float)factor));
	    }

	    @Handler
	    public void EventPacketOut(EventSendPacket event){
	        packetCount++;
	    }
	 
	 //Credit to bomt for this
	 public static int astolfo(int delay, float offset) {
	        int yStart = 20;
	        float speed = 3000f;
	        float index = 0.3f;
	        float hue = (float) (System.currentTimeMillis() % delay) + (offset);
	        while (hue > speed) {
	            hue -= speed;
	        }
	        hue /= speed;
	        if (hue > 0.5) {
	            hue = 0.5F - (hue - 0.5f);
	        }
	        hue += 0.5F;
	        return Color.HSBtoRGB(hue, 0.5F, 1F);
	    }
}
