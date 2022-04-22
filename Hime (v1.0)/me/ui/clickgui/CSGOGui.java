package me.ui.clickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import me.Hime;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import me.module.Module;
import me.module.Module.Category;
import me.settings.Setting;
import me.ui.clickgui.components.Component;
import me.ui.clickgui.components.sub.BrightnessSlider;
import me.ui.clickgui.components.sub.Checkbox;
import me.ui.clickgui.components.sub.HueSlider;
import me.ui.clickgui.components.sub.ModeButton;
import me.ui.clickgui.components.sub.SaturationSlider;
import me.ui.clickgui.components.sub.Slider;
import me.util.ColorUtil;
import me.util.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class CSGOGui extends GuiScreen{
/*	public boolean combat =  true;
	public boolean render =  false;
	public boolean exploit =  false;
	public boolean movement =  false;
	public boolean player =  false;
	public boolean world =  false;
	public boolean other =  false;
	public boolean targets =  false;*/
	public CategoryType categoryType = CategoryType.COMBAT;
	
	
	double scaling;
	public Module currentModule = null;
	public boolean listening = false;
	
	public boolean renderCheck = false;
	public static CSGOGui instance;
	private ArrayList<Component> subcomponents;
	// UnicodeFontRenderer ufr;
	public int offset;
	 public void prepareScissorBox(float x2, float y2, float x22, float y22) {
	        ScaledResolution scale = new ScaledResolution(this.mc);
	        int factor = scale.getScaleFactor();
	        GL11.glScissor((int)(x2 * (float)factor), (int)(((float)scale.getScaledHeight() - y22) * (float)factor), (int)((x22 - x2) * (float)factor), (int)((y22 - y2) * (float)factor));
	    }
	    
	  float lastY;
	 int index;
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		 
		 if((this.scaling <= 1))
    		 this.scaling += 0.05;
		 
		 
	        GL11.glPushMatrix();
	         if(Hime.instance.settingsManager.getSettingByName("Open Animation").getValBoolean()) {
	          GL11.glTranslated(width / 2, height / 2, 0);
	          GL11.glScalef((float)scaling, (float)scaling, 1f);
	          GL11.glTranslated(-width / 2, -height / 2, 0);
	        }
		 
        if (Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                this.offset += 10;
                if (this.offset < 0) {
                    this.offset = 0;
                }
                if (this.offset > 100) {
                    this.offset = 100;
                }
            } else if (wheel > 0) {
                this.offset -= 10;
                if (this.offset < 0) {
                    this.offset = 0;
                }
                if (this.offset > 100) {
                    this.offset = 100;
                }
            }
        }
		for(Component comp : this.subcomponents) {
			comp.updateComponent(mouseX, mouseY);
		}
		//this.drawRect(55, 20, 430, 260, 0xFF111111);

		//TODO drawrect
		 RenderUtil.drawRoundedRect(20, 20, 600, 280, 4, new Color(34,34,34));
		 RenderUtil.drawRoundedRect(22, 22, 100, 278, 4, new Color(17,17,17));  
	  
	  RenderUtil.drawRoundedRect(207, 28+   offset * 2f, 210, 70+ offset * 2f, 1, Color.GRAY);  
	  final float hue = (float) (ColorUtil.getClickGUIColor());
	  //                           colorSaturation  colorBrightness 
        Color color = Color.getHSBColor(hue, (float) Hime.instance.settingsManager.getSettingByName("Saturation").getValDouble(), (float) Hime.instance.settingsManager.getSettingByName("Brightness").getValDouble());
      //  RenderUtil.instance.draw2DImage(new ResourceLocation("textures/sword.png"), 22,  48, 20, 20, Color.WHITE);
        float targetY = (this.index * 9);
        float diff = targetY - this.lastY;
        targetY = this.lastY;
        this.lastY += diff / 4.0F;
        
        if(this.categoryType == CategoryType.COMBAT) {
	 // this.drawRect(22, 22, 24, 48, color.getRGB());  
	  index = 1;
  }if(this.categoryType == CategoryType.RENDER) {
	 // this.drawRect(22, 52, 24, 78, color.getRGB());   
	  index = 4;
  }if(this.categoryType == CategoryType.PLAYER) {
	 // this.drawRect(22, 112, 24, 138, color.getRGB());
	  index = 11;
  }if(this.categoryType == CategoryType.WORLD) {
	 // this.drawRect(22, 142, 24, 168, color.getRGB()); 
	  index = 14;
  }if(this.categoryType == CategoryType.EXPLOIT) {
	 // this.drawRect(22, 172, 24, 198, color.getRGB());  
	  index = 18;
  }if(this.categoryType == CategoryType.MISC) {
	 // this.drawRect(22, 202, 24, 228, color.getRGB());  
	  index = 21;
  }if(this.categoryType == CategoryType.MOVEMENT) {
	 // this.drawRect(22, 82, 24, 108, color.getRGB());  
	  index = 8;
  }if(this.categoryType == CategoryType.TARGETS) {
	  //this.drawRect(22, 232, 24, 258, color.getRGB());  
	  index = 24;
  }
  Gui.drawRect(22, ((12) + targetY) + 1.5D, 24, ((9 + 10) + targetY + 20.0F + 2.0F), color.getRGB());
    String[] buttons = { "Combat", "Render", "Movement", "Player", "World", "Exploit", "Other", "Targets"};

    int count = 0;
    
    for(String name : buttons) {
       float x = 33;
       float y = 30 + (count * 30);

       boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + GuiButton.appleBig.getWidth(name) - 3 && mouseY < y + mc.fontRendererObj.FONT_HEIGHT;
       final float hue2 = (float) (ColorUtil.getClickGUIColor());
 	  //                           colorSaturation  colorBrightness 
         Color color2 = Color.getHSBColor(hue2, 0.9f, 0.9f);
     
         if(name.equalsIgnoreCase("Targets")) {
           GuiButton.appleBig.drawString("Global", 33, 30 + (count * 30), hovered ? Color.GRAY.getRGB() : -1); 
         }else {
           GuiButton.appleBig.drawString(name, 33, 30 + (count * 30), hovered ? Color.GRAY.getRGB() : -1);
         }
       /*   if(this.combat&& name.equalsIgnoreCase("Combat")) {
        	  Hime.instance.cfr.drawCenteredString(name, 60, 30 + (count * 30), color2.getRGB());
    	  }
          if(this.render&& name.equalsIgnoreCase("Render")) {
        	  Hime.instance.cfr.drawCenteredString(name, 60, 30 + (count * 30), color2.getRGB());
    	  }
    	  if(this.player&& name.equalsIgnoreCase("Player")) {
    		  Hime.instance.cfr.drawCenteredString(name, 60, 30 + (count * 30), color2.getRGB());
    	  }
    	  if(this.world&& name.equalsIgnoreCase("World")) {
    		  Hime.instance.cfr.drawCenteredString(name, 60, 30 + (count * 30), color2.getRGB());
    	  }
    	  if(this.exploit&& name.equalsIgnoreCase("Exploit")) {
    		  Hime.instance.cfr.drawCenteredString(name, 60, 30 + (count * 30), color2.getRGB());
    	  }
    	  if(this.other&& name.equalsIgnoreCase("Other")) {
    		  Hime.instance.cfr.drawCenteredString(name, 60, 30 + (count * 30), color2.getRGB());
    	  }
    	  if(this.movement&& name.equalsIgnoreCase("Movement")) {
    		  Hime.instance.cfr.drawCenteredString(name, 60, 30 + (count * 30), color2.getRGB());
    	  }
    	  if(this.targets && name.equalsIgnoreCase("Targets")) {
    		  Hime.instance.cfr.drawCenteredString(name, 60, 30 + (count * 30), color2.getRGB());
    	  }*/
    		 
    	  
    	 
    
       count++;
         }
    GL11.glPushMatrix();
    this.prepareScissorBox(20, 20, 600, 280);
    GL11.glEnable(3089);
    
 // if(this.categoryType == CategoryType.COMBAT)
    if(this.categoryType == CategoryType.COMBAT) {
    	 int count2 = 0;
    	 for(Module m : Hime.instance.moduleManager.getModules()) {
    		 if(m.getCategory() == Category.COMBAT) {
    		       RenderUtil.drawRoundedRect2(102, 27+ (count2 * 15) - offset, 100, 14, 2, Color.DARK_GRAY.darker());  
    		    count2++;
    		 }
    }
    int count1 = 0;
	 for(Module m : Hime.instance.moduleManager.getModules()) {
		 if(m.getCategory() == Category.COMBAT) {
			 
			    float x = 110;
		         float y = 30 + (count1 * 15)- this.offset;
		         boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT;
		  
		         if(m != this.currentModule) {
		        	 GuiButton.appleSmall.drawString( m.getName(), 110, 29 + (count1 * 15)- this.offset, hovered ? m.isToggled() ? Color.GRAY.darker().getRGB() : Color.GRAY.getRGB() : m.isToggled() ? Color.GRAY.darker().getRGB() : -1);
			        }else {
			        	GuiButton.appleSmall.drawString("Listening...", 110, 29 + (count1 * 15)- this.offset, hovered ? m.isToggled() ? Color.GRAY.darker().getRGB() : Color.GRAY.getRGB() : m.isToggled() ? Color.GRAY.darker().getRGB() : -1);
			        }
			         if(Hime.instance.settingsManager.hasSettings(m) && m != this.currentModule) {
			        	 
			        	 GuiButton.appleSmall.drawString("...", 190, 29 + (count1 * 15)- this.offset, -1);
			         }
		count1++;
	 }
	 }
	
    }
    
    if(this.categoryType == CategoryType.RENDER) {
   	 int count2 = 0;
	 for(Module m : Hime.instance.moduleManager.getModules()) {
		 if(m.getCategory() == Category.RENDER) {
		       RenderUtil.drawRoundedRect2(102, 27+ (count2 * 15) - offset, 100, 14, 2, Color.DARK_GRAY.darker());  
		    count2++;
		 }
}
        int count1 = 0;
    	 for(Module m : Hime.instance.moduleManager.getModules()) {
    		
    		 if(m.getCategory() == Category.RENDER) {
    			   float x = 110;
  		         float y = 30 + (count1 * 15)- this.offset;
  		         boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT;

  		     //  RenderUtil.drawRoundedRect(102, 28+ (count1 * 15) - offset, 200, 42+ (count1 * 15)- offset, 2, Color.DARK_GRAY.darker());  
  		       if(m != this.currentModule) {
		        	 GuiButton.appleSmall.drawString( m.getName(), 110, 29 + (count1 * 15)- this.offset, hovered ? m.isToggled() ? Color.GRAY.darker().getRGB() : Color.GRAY.getRGB() : m.isToggled() ? Color.GRAY.darker().getRGB() : -1);
			        }else {
			        	GuiButton.appleSmall.drawString("Listening...", 110, 29 + (count1 * 15)- this.offset, hovered ? m.isToggled() ? Color.GRAY.darker().getRGB() : Color.GRAY.getRGB() : m.isToggled() ? Color.GRAY.darker().getRGB() : -1);
			        }
			         if(Hime.instance.settingsManager.hasSettings(m) && m != this.currentModule) {
			        	 
			        	 GuiButton.appleSmall.drawString("...", 190, 29 + (count1 * 15)- this.offset, -1);
			         }
		         
    		count1++;
    	 }
    	 }
        }
    if(this.categoryType == CategoryType.TARGETS) {
   	 int count2 = 0;
	 for(Module m : Hime.instance.moduleManager.getModules()) {
		 if(m.getCategory() == Category.TARGETS) {
		       RenderUtil.drawRoundedRect2(102, 27+ (count2 * 15) - offset, 100, 14, 2, Color.DARK_GRAY.darker());  
		    count2++;
		 }
     }
        int count1 = 0;
    	 for(Module m : Hime.instance.moduleManager.getModules()) {
    		
    		 if(m.getCategory() == Module.Category.TARGETS) {
    			   float x = 110;
  		         float y = 30 + (count1 * 15)- this.offset;
  		         boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT;

  		       //RenderUtil.drawRoundedRect(102, 28+ (count1 * 15) - offset, 200, 42+ (count1 * 15)- offset, 2, Color.DARK_GRAY.darker());  
  		       if(m != this.currentModule) {
		        	 GuiButton.appleSmall.drawString( m.getName(), 110, 29 + (count1 * 15)- this.offset, hovered ? m.isToggled() ? Color.GRAY.darker().getRGB() : Color.GRAY.getRGB() : m.isToggled() ? Color.GRAY.darker().getRGB() : -1);
			        }else {
			        	GuiButton.appleSmall.drawString("Listening...", 110, 29 + (count1 * 15)- this.offset, hovered ? m.isToggled() ? Color.GRAY.darker().getRGB() : Color.GRAY.getRGB() : m.isToggled() ? Color.GRAY.darker().getRGB() : -1);
			        }
			         if(Hime.instance.settingsManager.hasSettings(m) && m != this.currentModule) {
			        	 
			        	 GuiButton.appleSmall.drawString("...", 190, 29 + (count1 * 15)- this.offset, -1);
			         }
    		count1++;
    	 }
    	 }
        }
    if(this.categoryType == CategoryType.MOVEMENT) {
   	 int count2 = 0;
	 for(Module m : Hime.instance.moduleManager.getModules()) {
		 if(m.getCategory() == Category.MOVEMENT) {
		       RenderUtil.drawRoundedRect2(102, 27+ (count2 * 15) - offset, 100, 14, 2, Color.DARK_GRAY.darker());  
		    count2++;
		 }
     }
        int count1 = 0;
    	 for(Module m : Hime.instance.moduleManager.getModules()) {
    		
    		 if(m.getCategory() == Category.MOVEMENT) {
    			   float x = 110;
  		         float y = 30 + (count1 * 15)- this.offset;
  		         boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT;

  		      // RenderUtil.drawRoundedRect(102, 28+ (count1 * 15) - offset, 200, 42+ (count1 * 15)- offset, 2, Color.DARK_GRAY.darker());  
  		       if(m != this.currentModule) {
		        	 GuiButton.appleSmall.drawString( m.getName(), 110, 29 + (count1 * 15)- this.offset, hovered ? m.isToggled() ? Color.GRAY.darker().getRGB() : Color.GRAY.getRGB() : m.isToggled() ? Color.GRAY.darker().getRGB() : -1);
			        }else {
			        	GuiButton.appleSmall.drawString("Listening...", 110, 29 + (count1 * 15)- this.offset, hovered ? m.isToggled() ? Color.GRAY.darker().getRGB() : Color.GRAY.getRGB() : m.isToggled() ? Color.GRAY.darker().getRGB() : -1);
			        }
			         if(Hime.instance.settingsManager.hasSettings(m) && m != this.currentModule) {
			        	 
			        	 GuiButton.appleSmall.drawString("...", 190, 29 + (count1 * 15)- this.offset, -1);
			         }
    		count1++;
    	 }
    	 }
        }
    if(this.categoryType == CategoryType.EXPLOIT) {
   	 int count2 = 0;
	 for(Module m : Hime.instance.moduleManager.getModules()) {
		 if(m.getCategory() == Category.EXPLOIT) {
		       RenderUtil.drawRoundedRect2(102, 27+ (count2 * 15) - offset, 100, 14, 2, Color.DARK_GRAY.darker());  
		    count2++;
		 }
     }
        int count1 = 0;
    	 for(Module m : Hime.instance.moduleManager.getModules()) {
    		
    		 if(m.getCategory() == Category.EXPLOIT) {
    			   float x = 110;
  		         float y = 30 + (count1 * 15)- this.offset;
  		         boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT;

  		     //  RenderUtil.drawRoundedRect(102, 28+ (count1 * 15) - offset, 200, 42+ (count1 * 15)- offset, 2, Color.DARK_GRAY.darker());  
  		       if(m != this.currentModule) {
		        	 GuiButton.appleSmall.drawString( m.getName(), 110, 29 + (count1 * 15)- this.offset, hovered ? m.isToggled() ? Color.GRAY.darker().getRGB() : Color.GRAY.getRGB() : m.isToggled() ? Color.GRAY.darker().getRGB() : -1);
			        }else {
			        	GuiButton.appleSmall.drawString("Listening...", 110, 29 + (count1 * 15)- this.offset, hovered ? m.isToggled() ? Color.GRAY.darker().getRGB() : Color.GRAY.getRGB() : m.isToggled() ? Color.GRAY.darker().getRGB() : -1);
			        }
			         if(Hime.instance.settingsManager.hasSettings(m) && m != this.currentModule) {
			        	 
			        	 GuiButton.appleSmall.drawString("...", 190, 29 + (count1 * 15)- this.offset, -1);
			         }
    		count1++;
    	 }
    	 }
        }
    if(this.categoryType == CategoryType.WORLD) {
   	 int count2 = 0;
	 for(Module m : Hime.instance.moduleManager.getModules()) {
		 if(m.getCategory() == Category.WORLD) {
		       RenderUtil.drawRoundedRect2(102, 27+ (count2 * 15) - offset, 100, 14, 2, Color.DARK_GRAY.darker());  
		    count2++;
		 }
     }
        int count1 = 0;
    	 for(Module m : Hime.instance.moduleManager.getModules()) {
    		
    		 if(m.getCategory() == Category.WORLD) {
    			   float x = 110;
  		         float y = 30 + (count1 * 15)- this.offset;
  		         boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT;

  		      // RenderUtil.drawRoundedRect(102, 28+ (count1 * 15) - offset, 200, 42+ (count1 * 15)- offset, 2, Color.DARK_GRAY.darker());  
  		       if(m != this.currentModule) {
		        	 GuiButton.appleSmall.drawString( m.getName(), 110, 29 + (count1 * 15)- this.offset, hovered ? m.isToggled() ? Color.GRAY.darker().getRGB() : Color.GRAY.getRGB() : m.isToggled() ? Color.GRAY.darker().getRGB() : -1);
			        }else {
			        	GuiButton.appleSmall.drawString("Listening...", 110, 29 + (count1 * 15)- this.offset, hovered ? m.isToggled() ? Color.GRAY.darker().getRGB() : Color.GRAY.getRGB() : m.isToggled() ? Color.GRAY.darker().getRGB() : -1);
			        }
			         if(Hime.instance.settingsManager.hasSettings(m) && m != this.currentModule) {
			        	 
			        	 GuiButton.appleSmall.drawString("...", 190, 29 + (count1 * 15)- this.offset, -1);
			         }
    		count1++;
    	 }
    	 }
        }
    if(this.categoryType == CategoryType.MISC) {
   	 int count2 = 0;
	 for(Module m : Hime.instance.moduleManager.getModules()) {
		 if(m.getCategory() == Category.MISC) {
		       RenderUtil.drawRoundedRect2(102, 27+ (count2 * 15) - offset, 100, 14, 2, Color.DARK_GRAY.darker());  
		    count2++;
		 }
     }
        int count1 = 0;
    	 for(Module m : Hime.instance.moduleManager.getModules()) {
    		
    		 if(m.getCategory() == Category.MISC) {
    			   float x = 110;
  		         float y = 30 + (count1 * 15)- this.offset;
  		         boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT;

  		       //RenderUtil.drawRoundedRect(102, 28+ (count1 * 15) - offset, 200, 42+ (count1 * 15)- offset, 2, Color.DARK_GRAY.darker());  
  		       if(m != this.currentModule) {
		        	 GuiButton.appleSmall.drawString( m.getName(), 110, 29 + (count1 * 15)- this.offset, hovered ? m.isToggled() ? Color.GRAY.darker().getRGB() : Color.GRAY.getRGB() : m.isToggled() ? Color.GRAY.darker().getRGB() : -1);
			        }else {
			        	GuiButton.appleSmall.drawString("Listening...", 110, 29 + (count1 * 15)- this.offset, hovered ? m.isToggled() ? Color.GRAY.darker().getRGB() : Color.GRAY.getRGB() : m.isToggled() ? Color.GRAY.darker().getRGB() : -1);
			        }
			         if(Hime.instance.settingsManager.hasSettings(m) && m != this.currentModule) {
			        	 
			        	 GuiButton.appleSmall.drawString("...", 190, 29 + (count1 * 15)- this.offset, -1);
			         }
    		count1++;
    	 }
    	 }
        }
    if(this.categoryType == CategoryType.PLAYER) {
   	 int count2 = 0;
	 for(Module m : Hime.instance.moduleManager.getModules()) {
		 if(m.getCategory() == Category.PLAYER) {
		       RenderUtil.drawRoundedRect2(102, 27+ (count2 * 15) - offset, 100, 14, 2, Color.DARK_GRAY.darker());  
		    count2++;
		 }
    }
        int count1 = 0;
    	 for(Module m : Hime.instance.moduleManager.getModules()) {
    		
    		 if(m.getCategory() == Category.PLAYER) {
    	
    			   float x = 110;
  		         float y = 30 + (count1 * 15)- this.offset;
  		         boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT;
  		      // RenderUtil.drawRoundedRect(102, 28+ (count1 * 15) - offset, 200, 42+ (count1 * 15)- offset, 2, Color.DARK_GRAY.darker());  
  		       if(m != this.currentModule) {
		        	 GuiButton.appleSmall.drawString( m.getName(), 110, 29 + (count1 * 15)- this.offset, hovered ? m.isToggled() ? Color.GRAY.darker().getRGB() : Color.GRAY.getRGB() : m.isToggled() ? Color.GRAY.darker().getRGB() : -1);
			        }else {
			        	GuiButton.appleSmall.drawString("Listening...", 110, 29 + (count1 * 15)- this.offset, hovered ? m.isToggled() ? Color.GRAY.darker().getRGB() : Color.GRAY.getRGB() : m.isToggled() ? Color.GRAY.darker().getRGB() : -1);
			        }
			         if(Hime.instance.settingsManager.hasSettings(m) && m != this.currentModule) {
			        	 
			        	 GuiButton.appleSmall.drawString("...", 190, 29 + (count1 * 15)- this.offset, -1);
			         }
    		count1++;
    	 }
    	 }
        }
    GL11.glDisable(3089);
    GL11.glPopMatrix();

if(!this.subcomponents.isEmpty()) {
	for(Component comp : this.subcomponents) {
		comp.renderComponent();
	  }
	}

GL11.glPopMatrix();
	}
	
	
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		
		for(Component comp : this.subcomponents) {
			comp.mouseClicked(mouseX, mouseY, button);
		  }
     if(button == 0) {
    	 if(this.categoryType == CategoryType.PLAYER) {
		   int count1 = 0;
		  for(Module m  : Hime.instance.moduleManager.getModules()) {
				 if(m.getCategory() == Category.PLAYER) {
		         float x = 110;
		         float y = 30 + (count1 * 15)- this.offset;

		         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
		             m.toggle();
		         }
		         count1++;
				 }
		  }
		} 
    	 if(this.categoryType == CategoryType.TARGETS) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.TARGETS) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			             m.toggle();
			         }
			         count1++;
					 }
			  }
			} 
		if(this.categoryType == CategoryType.COMBAT) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.COMBAT) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			             m.toggle();
			         }
			         count1++;
					 }
			  }
			} 
		if(this.categoryType == CategoryType.MOVEMENT) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.MOVEMENT) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			             m.toggle();
			         }
			         count1++;
					 }
			  }
			} 
		if(this.categoryType == CategoryType.EXPLOIT) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Module.Category.EXPLOIT) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			             m.toggle();
			         }
			         count1++;
					 }
			  }
			} 
	 	 if(this.categoryType == CategoryType.WORLD) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.WORLD) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			             m.toggle();
			         }
			         count1++;
					 }
			  }
			}
		if(this.categoryType == CategoryType.MISC) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.MISC) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			             m.toggle();
			         }
			         count1++;
					 }
			  }
			}
		if(this.categoryType == CategoryType.RENDER) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.RENDER) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			             m.toggle();
			         }
			         count1++;
					 }
			  }
			} 


		}
		if(button == 2) {
		 if(this.categoryType == CategoryType.PLAYER) {
		   int count1 = 0;
		  for(Module m  : Hime.instance.moduleManager.getModules()) {
				 if(m.getCategory() == Category.PLAYER) {
		         float x = 110;
		         float y = 30 + (count1 * 15)- this.offset;

		         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
		             this.currentModule = m;
		             this.listening = true;
		         }
		         count1++;
				 }
		  }
		} 
	 	 if(this.categoryType == CategoryType.TARGETS) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.TARGETS) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			        	 this.currentModule = m;
			        	 this.listening = true;
			         }
			         count1++;
					 }
			  }
			} 
		if(this.categoryType == CategoryType.COMBAT) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.COMBAT) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			        	 this.currentModule = m;
			        	 this.listening = true;
			         }
			         count1++;
					 }
			  }
			} 
		if(this.categoryType == CategoryType.MOVEMENT) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.MOVEMENT) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			        	 this.currentModule = m;
			        	 this.listening = true;
			         }
			         count1++;
					 }
			  }
			} 
		if(this.categoryType == CategoryType.EXPLOIT) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.EXPLOIT) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			        	 this.currentModule = m;
			        	 this.listening = true;
			         }
			         count1++;
					 }
			  }
			} 
	 	 if(this.categoryType == CategoryType.WORLD) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.WORLD) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			        	 this.currentModule = m;
			        	 this.listening = true;
			         }
			         count1++;
					 }
			  }
			}
		if(this.categoryType == CategoryType.MISC) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.MISC) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			        	 this.currentModule = m;
			        	 this.listening = true;
			         }
			         count1++;
					 }
			  }
			}
		if(this.categoryType == CategoryType.RENDER) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.RENDER) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			        	 this.currentModule = m;
			        	 this.listening = true;
			         }
			         count1++;
					 }
			  }
			} 
		  }
		if(button == 1) {
		if(this.categoryType == CategoryType.PLAYER) {
		   int count1 = 0;
		  for(Module m  : Hime.instance.moduleManager.getModules()) {
				 if(m.getCategory() == Category.PLAYER) {
		         float x = 110;
		         float y = 30 + (count1 * 15)- this.offset;

		         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
		        	 this.subcomponents.clear();
		        	 if(Hime.instance.settingsManager.getSettingsByMod(m) != null) {
		        			int count = 0;
		        			//TODO test
		        			int count2 = 0;
		        			for(Setting s : Hime.instance.settingsManager.getSettingsByMod(m)){
		        				if(count >= 15 && !(count >= 17)) {
		        					count2 = 11;
		        					count = 0;
		        				}
		        				if(count >= 17 && !(count < 17)) {
		        					count2 = 20;
		        					count = 1;
		        				}
		        				if(s.isCombo()){
		        					
		        					this.subcomponents.add(new ModeButton(s, 350+ (count2 * 15), 30 + (count * 15)));
		        					//opY += 12;
		        				}
		        				if(s.isSlider()){
		        					this.subcomponents.add(new Slider(s, 350+ (count2 * 15), 24 +(count * 15)));
		        					//this.subcomponents.add(new Slider(s, this, opY));
		        					count++;
		        					//opY += 12;
		        				}
		        				if(s.isCheck()){
		        					this.renderCheck = true;
		        					this.subcomponents.add(new Checkbox(s, 350+ (count2 * 15), 30 + (count * 15)));
		        					///this.subcomponents.add(new Checkbox(s, this, opY));
		        					///opY += 12;
		        				}
		        				count++;
		        			}
		        		}
		         }
		         count1++;
				 }
		  }
		
		} 
		if(this.categoryType == CategoryType.TARGETS) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Module.Category.TARGETS) {
			         float x = 110;
			         float y = 30 + (count1 * 15) - this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			        	 this.subcomponents.clear();
			        	 if(Hime.instance.settingsManager.getSettingsByMod(m) != null) {
			        			int count = 0;
			        			//TODO test
			        			int count2 = 0;
			        			for(Setting s : Hime.instance.settingsManager.getSettingsByMod(m)){
			        				if(count >= 15) {
			        					count2 = 10;
			        					count = 1;
			        				}
		                           if(s.isCombo()){
			        					
			        					this.subcomponents.add(new ModeButton(s, 350+ (count2 * 15), 30 + (count * 15)));
			        					//opY += 12;
			        				}
			        				if(s.isSlider()){
			        					this.subcomponents.add(new Slider(s, 350+ (count2 * 15), 24 +(count * 15)));
			        					//this.subcomponents.add(new Slider(s, this, opY));
			        					count++;
			        					//opY += 12;
			        				}
			        				if(s.isCheck()){
			        					this.renderCheck = true;
			        					this.subcomponents.add(new Checkbox(s, 350+ (count2 * 15), 30 + (count * 15)));
			        					///this.subcomponents.add(new Checkbox(s, this, opY));
			        					///opY += 12;
			        				}
			        				count++;
			        			}
			        		}
			         }
			         count1++;
					 }
			  }
			} 
		if(this.categoryType == CategoryType.COMBAT) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.COMBAT) {
			         float x = 110;
			         float y = 30 + (count1 * 15) - this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			        	 this.subcomponents.clear();
			        	 if(Hime.instance.settingsManager.getSettingsByMod(m) != null) {
			        			int count = 0;
			        			//TODO test
			        			int count2 = 0;
			        			for(Setting s : Hime.instance.settingsManager.getSettingsByMod(m)){
			        				if(count >= 15 && !(count >= 17)) {
			        					count2 = 11;
			        					count = 0;
			        				}
			        				if(count >= 17 && !(count < 17)) {
			        					count2 = 20;
			        					count = 1;
			        				}
			        				if(s.isCombo()){
			        					
			        					this.subcomponents.add(new ModeButton(s, 350+ (count2 * 15), 30 + (count * 15)));
			        					//opY += 12;
			        				}
			        				if(s.isSlider()){
			        					this.subcomponents.add(new Slider(s, 350+ (count2 * 15), 24 +(count * 15)));
			        					//this.subcomponents.add(new Slider(s, this, opY));
			        					count++;
			        					//opY += 12;
			        				}
			        				if(s.isCheck()){
			        					this.renderCheck = true;
			        					this.subcomponents.add(new Checkbox(s, 350+ (count2 * 15), 30 + (count * 15)));
			        					///this.subcomponents.add(new Checkbox(s, this, opY));
			        					///opY += 12;
			        				}
			        				count++;
			        			}
			        		}
			         }
			         count1++;
					 }
			  }
			} 
		if(this.categoryType == CategoryType.MOVEMENT) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.MOVEMENT) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			        	 this.subcomponents.clear();
			        	 if(Hime.instance.settingsManager.getSettingsByMod(m) != null) {
			        			int count = 0;
			        			//TODO test
			        			int count2 = 0;
			        			for(Setting s : Hime.instance.settingsManager.getSettingsByMod(m)){
			        				if(count >= 15 && !(count >= 17)) {
			        					count2 = 11;
			        					count = 0;
			        				}
			        				if(count >= 17 && !(count < 17)) {
			        					count2 = 20;
			        					count = 1;
			        				}
			        				if(s.isCombo()){
			        					
			        					this.subcomponents.add(new ModeButton(s, 350+ (count2 * 15), 30 + (count * 15)));
			        					//opY += 12;
			        				}
			        				if(s.isSlider()){
			        					this.subcomponents.add(new Slider(s, 350+ (count2 * 15), 24 +(count * 15)));
			        					//this.subcomponents.add(new Slider(s, this, opY));
			        					count++;
			        					//opY += 12;
			        				}
			        				if(s.isCheck()){
			        					this.renderCheck = true;
			        					this.subcomponents.add(new Checkbox(s, 350+ (count2 * 15), 30 + (count * 15)));
			        					///this.subcomponents.add(new Checkbox(s, this, opY));
			        					///opY += 12;
			        				}
			        				count++;
			        			}
			        		}
			         }
			         count1++;
					 }
			  }
			} 
		if(this.categoryType == CategoryType.EXPLOIT) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.EXPLOIT) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			        	 this.subcomponents.clear();
			        	 if(Hime.instance.settingsManager.getSettingsByMod(m) != null) {
			        			int count = 0;
			        			//TODO test
			        			int count2 = 0;
			        			for(Setting s : Hime.instance.settingsManager.getSettingsByMod(m)){
			        				if(count >= 15 && !(count >= 17)) {
			        					count2 = 11;
			        					count = 0;
			        				}
			        				if(count >= 17 && !(count < 17)) {
			        					count2 = 20;
			        					count = 1;
			        				}
			        				if(s.isCombo()){
			        					
			        					this.subcomponents.add(new ModeButton(s, 350+ (count2 * 15), 30 + (count * 15)));
			        					//opY += 12;
			        				}
			        				if(s.isSlider()){
			        					this.subcomponents.add(new Slider(s, 350+ (count2 * 15), 24 +(count * 15)));
			        					//this.subcomponents.add(new Slider(s, this, opY));
			        					count++;
			        					//opY += 12;
			        				}
			        				if(s.isCheck()){
			        					this.renderCheck = true;
			        					this.subcomponents.add(new Checkbox(s, 350+ (count2 * 15), 30 + (count * 15)));
			        					///this.subcomponents.add(new Checkbox(s, this, opY));
			        					///opY += 12;
			        				}
			        				count++;
			        			}
			        		}
			         }
			         count1++;
					 }
			  }
			} 
		if(this.categoryType == CategoryType.WORLD) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.WORLD) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			        	 this.subcomponents.clear();
			        	 if(Hime.instance.settingsManager.getSettingsByMod(m) != null) {
			        			int count = 0;
			        			//TODO test
			        			int count2 = 0;
			        			for(Setting s : Hime.instance.settingsManager.getSettingsByMod(m)){
			        				if(count >= 15 && !(count >= 17)) {
			        					count2 = 11;
			        					count = 0;
			        				}
			        				if(count >= 17 && !(count < 17)) {
			        					count2 = 20;
			        					count = 1;
			        				}
			        				if(s.isCombo()){
			        					
			        					this.subcomponents.add(new ModeButton(s, 350+ (count2 * 15), 30 + (count * 15)));
			        					//opY += 12;
			        				}
			        				if(s.isSlider()){
			        					this.subcomponents.add(new Slider(s, 350+ (count2 * 15), 24 +(count * 15)));
			        					//this.subcomponents.add(new Slider(s, this, opY));
			        					count++;
			        					//opY += 12;
			        				}
			        				if(s.isCheck()){
			        					this.renderCheck = true;
			        					this.subcomponents.add(new Checkbox(s, 350+ (count2 * 15), 30 + (count * 15)));
			        					///this.subcomponents.add(new Checkbox(s, this, opY));
			        					///opY += 12;
			        				}
			        				count++;
			        			}
			        		}
			         }
			         count1++;
					 }
			  }
			}
		if(this.categoryType == CategoryType.MISC) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.MISC) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			        	 this.subcomponents.clear();
			        	 if(Hime.instance.settingsManager.getSettingsByMod(m) != null) {
			        			int count = 0;
			        			//TODO test
			        			int count2 = 0;
			        			for(Setting s : Hime.instance.settingsManager.getSettingsByMod(m)){
			        				if(count >= 15 && !(count >= 17)) {
			        					count2 = 11;
			        					count = 0;
			        				}
			        				if(count >= 17 && !(count < 17)) {
			        					count2 = 20;
			        					count = 1;
			        				}
			        				if(s.isCombo()){
			        					
			        					this.subcomponents.add(new ModeButton(s, 350+ (count2 * 15), 30 + (count * 15)));
			        					//opY += 12;
			        				}
			        				if(s.isSlider()){
			        					this.subcomponents.add(new Slider(s, 350+ (count2 * 15), 24 +(count * 15)));
			        					//this.subcomponents.add(new Slider(s, this, opY));
			        					count++;
			        					//opY += 12;
			        				}
			        				if(s.isCheck()){
			        					this.renderCheck = true;
			        					this.subcomponents.add(new Checkbox(s, 350+ (count2 * 15), 30 + (count * 15)));
			        					///this.subcomponents.add(new Checkbox(s, this, opY));
			        					///opY += 12;
			        				}
			        				count++;
			        			}
			        		}
			         }
			         count1++;
					 }
			  }
			}
		if(this.categoryType == CategoryType.RENDER) {
			   int count1 = 0;
			  for(Module m  : Hime.instance.moduleManager.getModules()) {
					 if(m.getCategory() == Category.RENDER) {
			         float x = 110;
			         float y = 30 + (count1 * 15)- this.offset;

			         if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(m.getName()) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
			        	 this.subcomponents.clear();
			        	 if(Hime.instance.settingsManager.getSettingsByMod(m) != null) {
			        			int count = 0;
			        			//TODO test
			        			int count2 = 0;
			        			for(Setting s : Hime.instance.settingsManager.getSettingsByMod(m)){
			        				if(count >= 16 && !(count >= 18)) {
			        					count2 = 11;
			        					count = 0;
			        				}
			        				if(count >= 18 && !(count < 18)) {
			        					count2 = 20;
			        					count = 1;
			        				}
			        				if(s.isCombo()){
			        					
			        					this.subcomponents.add(new ModeButton(s, 350+ (count2 * 15), 30 + (count * 15)));
			        					//opY += 12;
			        				}
			        				if(s.isSlider()){
			        					this.subcomponents.add(new Slider(s, 350+ (count2 * 15), 24 +(count * 15)));
			        					//this.subcomponents.add(new Slider(s, this, opY));
			        					count++;
			        					//opY += 12;
			        				}
			        				if(s.isCheck()){
			        					this.renderCheck = true;
			        					this.subcomponents.add(new Checkbox(s, 350+ (count2 * 15), 30 + (count * 15)));
			        					///this.subcomponents.add(new Checkbox(s, this, opY));
			        					///opY += 12;
			        				}
	                                 if(s.isHueSlider()){
			        					this.subcomponents.add(new HueSlider(s, 250, 30 + (count * 15)));
			        				}
			        				if(s.isBrightSlider()){
			        					this.subcomponents.add(new BrightnessSlider(s, 250, 35 + (count * 15)));
			        				}
			        				if(s.isSaturationSlider()){
			        					this.subcomponents.add(new SaturationSlider(s, 250, 37 + (count * 15)));
			        					count++;
			        				}
			        				count++;
			        			}
			        		}
			         }
			         count1++;
					 }
			  }
			}

		}
		//TODO categorys
		 String[] buttons = { "Combat", "Render", "Movement", "Player", "World", "Exploit", "Other", "Targets" };

	      int count = 0;
	      for(String name : buttons) {
	    	  float x = 33;
	          float y = 30 + (count * 30);

	         if(mouseX >= x && mouseY >= y && mouseX < x + GuiButton.appleBig.getWidth(name) - 3 && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
	            switch (name) {
	               case "Combat":  
	            	   this.subcomponents.clear();
                      this.offset =  0;
	               /*   this.combat = true;
	                  this.render = false;
	                  this.movement = false;
	                  this.player = false;
	                  this.exploit = false;
	                  this.other = false;
	                  this.world = false;
	                  this.targets =  false;*/
	                  this.categoryType = CategoryType.COMBAT;
	                  break;

	               case "Render":
	            	   this.subcomponents.clear();
	            	   this.offset =  0;
		                /*  this.combat = false;
		                  this.render = true;
		                  this.movement = false;
		                  this.player = false;
		                  this.exploit = false;
		                  this.other = false;
		                  this.world = false;
		                  this.targets =  false;*/
		                  this.categoryType = CategoryType.RENDER;
	                  break;

	               case "Movement":
	            	   this.subcomponents.clear();
	            	   this.offset =  0;
		                /*  this.combat = false;
		                  this.render = false;
		                  this.movement = true;
		                  this.player = false;
		                  this.exploit = false;
		                  this.other = false;
		                  this.world = false;
		                  this.targets =  false;*/
		                  this.categoryType = CategoryType.MOVEMENT;
	                  break;

	               case "Player":
	            	   this.subcomponents.clear();
	            	   this.offset =  0;
		                 /* this.combat = false;
		                  this.render = false;
		                  this.movement = false;
		                  this.player = true;
		                  this.exploit = false;
		                  this.other = false;
		                  this.world = false;
		                  this.targets =  false;*/
		                  this.categoryType = CategoryType.PLAYER;
	                  break;

	               case "World":
	            	   this.subcomponents.clear();
	            	   this.offset =  0;
		                /*  this.combat = false;
		                  this.render = false;
		                  this.movement = false;
		                  this.player = false;
		                  this.exploit = false;
		                  this.other = false;
		                  this.world = true;
		                  this.targets =  false;*/
		                  this.categoryType = CategoryType.WORLD;
	                  break;

	               case "Exploit":
	            	   this.subcomponents.clear();
	            	   this.offset =  0;
		                 /* this.combat = false;
		                  this.render = false;
		                  this.movement = false;
		                  this.player = false;
		                  this.exploit = true;
		                  this.other = false;
		                  this.world = false;
		                  this.targets =  false;*/
		                  this.categoryType = CategoryType.EXPLOIT;
	                  break;
	               case "Other":
	            	   this.subcomponents.clear();
	            	   this.offset =  0;
		                 /* this.combat = false;
		                  this.render = false;
		                  this.movement = false;
		                  this.player = false;
		                  this.exploit = false;
		                  this.other = true;
		                  this.world = false;
		                  this.targets =  false;*/
		                  this.categoryType = CategoryType.MISC;
		                  break;
	               case "Targets":
	            	   this.subcomponents.clear();
	            	   this.offset =  0;
		              /*    this.combat = false;
		                  this.render = false;
		                  this.movement = false;
		                  this.player = false;
		                  this.exploit = false;
		                  this.other = false;
		                  this.world = false;
		                  this.targets =  true;*/
		                  this.categoryType = CategoryType.TARGETS;
		                  break;
	            }
	         }

	         count++;
	      }

		}
	


	public void keyTyped(char typedChar, int keyCode) throws IOException {

		if (listening && this.currentModule != null) {
			if (keyCode != Keyboard.KEY_ESCAPE) {
				Hime.addClientChatMessage("§9" + this.currentModule.getName() + " §fhas been §7bound §fto §a" + Keyboard.getKeyName(keyCode));
				this.currentModule.setKey(keyCode);
			} else {
				//Client.sendChatMessage("Unbound '" + mod.getName() + "'");
				this.currentModule.setKey(Keyboard.KEY_NONE);
			}
			listening = false;

		}

		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
			
				
					for(Component component : this.subcomponents) {
						component.mouseReleased(mouseX, mouseY, state);
					}
				
			
	}
	
	
	
	@Override
	public void initGui() {
		 // this.lastY = (Hime.instance.cfr == null) ? this.index : (this.index * 9);
		this.subcomponents = new ArrayList<Component>();
		this.scaling = 0;
		
		/*if(Hime.instance.moduleManager.getModule("Blur").isToggled()) {
		/*
		 * Start blur
		 
		if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
			if (mc.entityRenderer.theShaderGroup != null) {
				mc.entityRenderer.theShaderGroup.deleteShaderGroup();
			}
			mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
		}
		}*/
	}
	
	@Override
	public void onGuiClosed() {
		/*if(Hime.instance.moduleManager.getModule("Blur").isToggled()) {
		/*
		 * End blur 
		 
		if (mc.entityRenderer.theShaderGroup != null) {
			mc.entityRenderer.theShaderGroup.deleteShaderGroup();
			mc.entityRenderer.theShaderGroup = null;
		}
		}*/
	}

	public enum CategoryType {
		COMBAT, RENDER, WORLD, MOVEMENT, PLAYER, TARGETS, EXPLOIT, MISC;
	}
	
}
