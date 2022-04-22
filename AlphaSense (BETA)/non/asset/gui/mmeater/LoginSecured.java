package non.asset.gui.mmeater;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import non.asset.Clarinet;
import non.asset.gui.MainMenu;
import non.asset.module.impl.movement.Fly;
import non.asset.module.impl.visuals.HUD;
import non.asset.utils.RenderUtils;
import non.asset.utils.OFC.MathUtils;
import non.asset.utils.OFC.TimerUtil;

public final class LoginSecured extends GuiScreen {
	
   private String status;
   private String box1;
   private String box2;
   private String box3;
   private String box4;
   private String box5;
   private String box6;
   //private GuiTextField boxxed1;
   private BoxxedOneField boxxed2;
   
   private boolean verified = false;
   private boolean verified2 = false;
   
   private TimerUtil timer = new TimerUtil();

   private float lastTimerCount = 0;
   
   private float timecount = 0;
   
   private double goingrightnow = 0;
   private double textlmaooo = 0;
   

   public LoginSecured() {
	   
	   Fly.flagPositionY = 0;
	   
		this.timecount = Fly.flagPositionY;
		
		
		this.box1 = "";
		this.box2 = "Verify you account";
		this.box3 = "";
		this.box4 = "";
		this.box5 = "";
		this.box6 = "";
		this.textlmaooo = 106;
		this.goingrightnow = 100;
  	  timer.reset();
		
   }
   protected void actionPerformed(GuiButton button) {
      switch(button.id) {
      case 0:
         break;
      case 1:
    	  mc.shutdown();
    	  mc.shutdownMinecraftApplet();
    	  break;
      }

   }

   public void drawScreen(int x2, int y2, float z2) {
      //this.drawDefaultBackground();

		ScaledResolution sr = new ScaledResolution(mc);
		FontRenderer fr = mc.fontRendererObj;
       final HUD hud = (HUD) Clarinet.INSTANCE.getModuleManager().getModule("hud");

       int var3 = this.height / 4 + 24;
       
       
	   mc.getTextureManager().bindTexture(new ResourceLocation("textures/client/dtgygubuhiu.png"));
	   this.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
		

	   GlStateManager.pushMatrix();
	   this.drawGradientRect((int) (width - 900), (int) (height - 300), width, height, 0x00000000, hud.getGradientOffset(new Color(13, 29, 92), new Color(55, 15, 255), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D)).getRGB());
		GlStateManager.popMatrix();
		
      //this.boxxed1.drawTextBox();
      this.boxxed2.drawTextBox();
      MainMenu.dufnctrmgyot6m18.drawCenteredString(box2, this.width / 2, 20, -1);
      //this.drawCenteredString(this.mc.fontRendererObj, this.thread == null? EnumChatFormatting.GRAY + "Idle...":this.thread.getStatus(), this.width / 2, 29, -1);
      

      if(this.boxxed2.getText().isEmpty()) {
    	  MainMenu.dufnctrmgyot6m18.drawString("Captcha", this.width / 2 - 96, (int) textlmaooo, -7829368);
      }
      

      MainMenu.dufnctrmgyot6mh.drawCenteredString(boxxed2.numbero, this.width / 2 - 86, (float) (textlmaooo - 18), -1);
      //MainMenu.dufnctrmgyot6m18.drawCenteredString(box3, this.width / 2, 34, -1);
      if(!boxxed2.passed) {
	      if(timer.hasTimerElapsed((long) (1000 / 0.2), true)) {
	    	  timecount += 1f;
	      }
	      //box3 = "Stopping in.. " +  ((float) (timecount) / 1000) + "/1.0"; 
	      
	      if(timecount >= 1002) {
	    	 // box2 = "u r gay";
	    	 // mc.shutdown();
	    	 // mc.shutdownMinecraftApplet();
	      }
      }
      
      if(boxxed2.getText().equals(boxxed2.numbero)) {
    	  boxxed2.passed = true;
    	  timer.reset();
    	  boxxed2.setText("");
    	  box2 = "Logging in...";
      }
	  
      if(boxxed2.passed) {
    	  
    	  textlmaooo += 15;
    	  goingrightnow += 15;
    	  
          if(this.boxxed2 != null) {
        	  this.boxxed2 = new BoxxedOneField(this.mc.fontRendererObj, (int) (this.width / 2 - goingrightnow), 100, 200, 20);
          }
          
    	  if(timer.reach(3000)) {
    		  mc.displayGuiScreen(new MainMenu());
    	  }
      }else {
    	  boxxed2.setCanLoseFocus(true);
      }
      
      super.drawScreen(x2, y2, z2);
   }

   public void initGui() {
      int var3 = this.height / 4 + 24;
      this.buttonList.add(new GuiButton(0, this.width / 2 - 100, var3 + 72 + 12, "Verify"));
      this.buttonList.add(new GuiButton(1, this.width / 2 - 100, var3 + 72 + 12 + 24, "Close the game"));
      this.boxxed2 = new BoxxedOneField(this.mc.fontRendererObj, (int) (this.width / 2 - goingrightnow), 100, 200, 20);
      Keyboard.enableRepeatEvents(true);
   }

   protected void keyTyped(char character, int key) {
      try {
         super.keyTyped(character, key);
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      if(character == 9) {
         if(!this.boxxed2.isFocused()) {
            this.boxxed2.setFocused(true);
         }
      }

      if(character == 13) {
         this.actionPerformed((GuiButton)this.buttonList.get(0));
      }

      //this.boxxed1.textboxKeyTyped(character, key);
      this.boxxed2.textboxKeyTyped(character, key);
   }
   
   protected void mouseClicked(int x2, int y2, int button) {
      try {
         super.mouseClicked(x2, y2, button);
      } catch (IOException var5) {
         var5.printStackTrace();
      }
      this.boxxed2.mouseClicked(x2, y2, button);
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      
      if(boxxed2 != null) {
    	  if(boxxed2.passed) {
    		  return;
    	  }
      }
      
      timecount = 800;
      
      for(int i = 0; i < 7; i = 7) {
    	  System.out.println("Don't try to reset");
      }
	  
      
	  mc.shutdown();
	  mc.shutdownMinecraftApplet();
      
   }

   public void updateScreen() {
      //this.boxxed1.updateCursorCounter();
      this.boxxed2.updateCursorCounter();
   }
}