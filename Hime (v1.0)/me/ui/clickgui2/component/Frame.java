package me.ui.clickgui2.component;

import java.awt.Color;
import java.util.ArrayList;

import me.Hime;
import me.module.Module;
import me.module.Module.Category;
import me.module.impl.targets.Notifications;
import me.ui.clickgui2.component.components.Button;
import me.util.ColorUtil;
import me.util.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

//Your Imports
public class Frame {

	public ArrayList<Component> components;
	public Category category;
	private boolean open;
	private int width;
	private int y;
	public static Frame instance;
	private int x;
	private int barHeight;
	private boolean isDragging;
	public int dragX;
	public int dragY;
	public int fix = 0;
	public boolean roundthing = false;

	public Frame(Category cat) {
	//	System.out.println("Frame works!");
		this.components = new ArrayList<Component>();
		this.category = cat;
		this.width = 88;
		this.x = 5;
		this.y = 5;
		this.barHeight = 13;
		this.dragX = 0;
		this.open = true;
		this.isDragging = false;
		int tY = this.barHeight;
		int tY2 = this.barHeight + 5;
		int tY3 = this.barHeight + 5;
		
		/**
		 * 		public ArrayList<Module> getModulesInCategory(Category categoryIn){
		 * 			ArrayList<Module> mods = new ArrayList<Module>();
		 * 			for(Module m : this.modules){
		 * 				if(m.getCategory() == categoryIn)
		 * 					mods.add(m);
		 * 			}
		 * 			return mods;
		 * 		}
		 */

		for(Module mod : Hime.instance.moduleManager.getModulebyCategory(category)) {
			//System.out.println("Frame");
			Button modButton;
			if(this.category == Category.TARGETS) {
			 if(mod.equals(Hime.instance.moduleManager.getModule(Notifications.class))) {
		      modButton= new Button(mod, this, tY3); 
			 }else {
		      modButton= new Button(mod, this, tY2);
			 }
			}else {
		      modButton = new Button(mod, this, tY);	
			}
			this.components.add(modButton);
			
			tY += 12;
			tY2 += 12;
			tY3 += 12;
		}
	}
	
	public Frame(String titleName) {
		
	}
	
	public ArrayList<Component> getComponents() {
		return components;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void setDrag(boolean drag) {
		this.isDragging = drag;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	public void renderFrameRect(FontRenderer fontRenderer) {
		  final float hue = (float) (ColorUtil.getClickGUIColor());
		  //                           colorSaturation  colorBrightness
	        Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValInt(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValInt());
		if(this.category == Category.TARGETS) {
		 if(this.open) {
		 Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight + 10, new Color(25,25,25).getRGB());
		 }else {
		  Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, new Color(25,25,25).getRGB());
	     }
		 if(this.open) {
	      Gui.drawRect(this.x, this.y + this.barHeight, this.x + this.width, this.y + this.barHeight + 90, new Color(40,39,42).getRGB());
		 }

		 }else {
		  Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, new Color(25,25,25).getRGB());
		 }

	if(this.open) {
		if(!this.components.isEmpty()) {
	for(Component component2 : components) {
		component2.renderComponentRect();
		if(component2.equals(components.get(components.size() - 1))) {

	   }
	}
		}
	}
	}
	
	public void renderFrame(FontRenderer fontRenderer) {
		//Color temp = ColorUtil.getClickGUIColor();
		//int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
		// float hue = ColorUtil.getClickGUIColor();
			//Color color = Color.getHSBColor(hue, 1f, 1f);

		//GL11.glPushMatrix();
		//GL11.glScalef(0.8f,0.8f, 0.8f);
			if(this.category != Category.TARGETS) {
			GuiButton.aaaa.drawString(this.category.toString().substring(0, 1) + this.category.toString().substring(1).toLowerCase(), x + 2, y + 2,  -1);
			}if(this.category == Category.TARGETS) {
				 if(this.open) {
				GuiButton.astolfo2.drawString("-----------Targets-----------", x + 3, y + 13,  -1);	
				GuiButton.astolfo2.drawString("----------GUI/Client---------", x + 3, y + 98,  -1);
				 }
				GuiButton.aaaa.drawString("Global", x + 2, y + 2,  -1);	
			}
			if(this.category == Category.PLAYER) {
				RenderUtil.instance.draw2DImage(new ResourceLocation("client/player.png"), this.x + this.width - 14, this.y, 12, 12, Color.WHITE);
			}
			if(this.category == Category.MOVEMENT) {
				Hime.instance.csgo.drawString("G",  this.x + this.width - 14, this.y, Color.WHITE.getRGB());
			//	RenderUtil.instance.draw2DImage(new ResourceLocation("client/movement.png"), this.x + this.width - 14, this.y, 12, 12, Color.WHITE);
			}
			if(this.category == Category.COMBAT) {
				Hime.instance.csgo.drawString("E",  this.x + this.width - 14, this.y, Color.WHITE.getRGB());
			//	RenderUtil.instance.draw2DImage(new ResourceLocation("client/movement.png"), this.x + this.width - 14, this.y, 12, 12, Color.WHITE);
			}
			if(this.category == Category.RENDER) {
				Hime.instance.csgo.drawString("C",  this.x + this.width - 14, this.y, Color.WHITE.getRGB());
			//	RenderUtil.instance.draw2DImage(new ResourceLocation("client/movement.png"), this.x + this.width - 14, this.y, 12, 12, Color.WHITE);
			}
			if(this.category == Category.MISC) {
				Hime.instance.csgo.drawString("B",  this.x + this.width - 14, this.y, Color.WHITE.getRGB());
			//	RenderUtil.instance.draw2DImage(new ResourceLocation("client/movement.png"), this.x + this.width - 14, this.y, 12, 12, Color.WHITE);
			}
			if(this.category == Category.TARGETS) {
				RenderUtil.instance.draw2DImage(new ResourceLocation("client/Settings.png"), this.x + this.width - 14, this.y, 11, 11, Color.WHITE);
			}
			if(this.category == Category.WORLD) {
				RenderUtil.instance.draw2DImage(new ResourceLocation("client/world.png"), this.x + this.width - 14, this.y, 12, 12, Color.WHITE);
			}
			if(this.category == Category.EXPLOIT) {
				Hime.instance.csgo.drawString("A",  this.x + this.width - 14, this.y, Color.WHITE.getRGB());
		}
		//ufr.drawStringWithShadow(this.open ? "-" : "+", x + 80, y, -1);
		//GL11.glPopMatrix();
		if(this.open) {
			     
			if(!this.components.isEmpty()) {
				//Gui.drawRect(this.x, this.y + this.barHeight, this.x + 1, this.y + this.barHeight + (12 * components.size()), new Color(0, 200, 20, 150).getRGB());
				//Gui.drawRect(this.x, this.y + this.barHeight + (12 * components.size()), this.x + this.width, this.y + this.barHeight + (12 * components.size()) + 1, new Color(0, 200, 20, 150).getRGB());
				//Gui.drawRect(this.x + this.width, this.y + this.barHeight, this.x + this.width - 1, this.y + this.barHeight + (12 * components.size()), new Color(0, 200, 20, 150).getRGB());

				for(Component component : components) {
					component.renderComponent();
				}
			}
		}
	}
	
	public void refresh() {
		int off = 0;
		if(this.category == Category.TARGETS) {
		  for(Module mod : Hime.instance.moduleManager.getModulebyCategory(category)) {
			  if(mod.equals(Hime.instance.moduleManager.getModule(Notifications.class))) {
			      off = this.barHeight + 5;
				 }else {
				   off = this.barHeight + 10;	 
				 }  
		  }
		}else {
		 off = this.barHeight;
		}
		for(Component comp : components) {
			comp.setOff(off);
			off += comp.getHeight();
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void updatePosition(int mouseX, int mouseY) {
		if(this.isDragging) {
			this.setX(mouseX - dragX);
			this.setY(mouseY - dragY);
		}
	}
	
	public boolean isWithinHeader(int x, int y) {
		if(x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight) {
			return true;
		}
		return false;
	}

	public int getBarHeight() {
		return barHeight;
	}
	
}
