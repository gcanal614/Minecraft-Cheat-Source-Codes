package non.asset.gui;

import java.awt.Color;
import java.awt.Font;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import non.asset.Clarinet;
import non.asset.gui.account.gui.GuiAltManager;
import non.asset.module.impl.visuals.HUD;
import non.asset.utils.OFC.TimerUtil;
import non.asset.utils.font.Fonts;
import non.asset.utils.font.MCFontRenderer;

public class MainMenu extends GuiScreen {

	
    public TimerUtil timer = new TimerUtil();
    
    public float shadowuwu = 100;
    
    private double smoothY = 0;
    
    private double x = 0;
    private double y = 0;

    private int valuetest = 0;
    
    public MCFontRenderer cf = new MCFontRenderer(new Font ("Arial", Font.PLAIN, 18), true, true);
    public static MCFontRenderer dufnctrmgyot6mh = new MCFontRenderer(Fonts.fontFromTTF(new ResourceLocation("textures/client/bttf.ttf"),21,0), true, true);

    public static MCFontRenderer dufnctrmgyot6m18 = new MCFontRenderer(Fonts.fontFromTTF(new ResourceLocation("textures/client/bttf.ttf"),18,0), true, true);

    
	public MainMenu() {
		smoothY = -height/2f - dufnctrmgyot6mh.getHeight()/2f;
	}
	
	public void initGui() {
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

		ScaledResolution sr = new ScaledResolution(mc);
		FontRenderer fr = mc.fontRendererObj;
        final HUD hud = (HUD) Clarinet.INSTANCE.getModuleManager().getModule("hud");
        
		int LightColorWhenSelected = -1;
		

		//this.drawDefaultBackground();
		
		mc.getTextureManager().bindTexture(new ResourceLocation("textures/client/ocean.png"));
		this.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
		
		this.drawGradientRect(0, (int) (height - 100), width, height, 0x00000000, hud.getGradientOffset(new Color(13, 29, 92), new Color(55, 15, 255), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D)).getRGB());
		
		String[] buttons = { "Solo", "Multi", "Settings","Alt Manager", "Quit" };
		int count = 0;
		for(String name : buttons) {
			float x = (width/buttons.length) * count + (width/buttons.length)/2f + 8 - dufnctrmgyot6mh.getStringWidth(name)/2f;
			float y = height - 20;
			boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + dufnctrmgyot6mh.getStringWidth(name) && mouseY < y + cf.getHeight();
			dufnctrmgyot6mh.drawString(name, (width/buttons.length) * count + (width/buttons.length)/2f - 8, height - 20, hovered ? 0xf9404040 : -1);
			count++;
		}

		Clarinet.name = "AlphaSense X";
		
		int currentX = 0;
		
		if(smoothY < height/2f - dufnctrmgyot6mh.getHeight()/2f) {
			smoothY += 5;
		}
		if(smoothY > height/2f - dufnctrmgyot6mh.getHeight()/2f) {
			smoothY -= 5;
		}
		if(smoothY == height/2f - dufnctrmgyot6mh.getHeight()/2f) {
			smoothY = height/2f - dufnctrmgyot6mh.getHeight()/2f;
		}

		if(x < width/2f - dufnctrmgyot6mh.getStringWidth(Clarinet.name)/2f) {
			x += 5;
		}
		if(x > width/2f - dufnctrmgyot6mh.getStringWidth(Clarinet.name)/2f) {
			x -= 5;
		}
		if(x == width/2f - dufnctrmgyot6mh.getStringWidth(Clarinet.name)/2f) {
			x = width/2f - dufnctrmgyot6mh.getStringWidth(Clarinet.name)/2f;
		}
		
		/*
		int test = 1;
		if(valuetest < width/test) {
			valuetest += 2;
		}
		if(valuetest > width/test ) {
			valuetest -= 2;
		}
		if(valuetest == width/test) {
			valuetest = width/test;
		}
		GlStateManager.pushMatrix();
		GlStateManager.translate(width/15f, height/15f, 0);
		GlStateManager.scale(2, 2, 1);
		RenderUtil.drawRoundedRect(15, 15, valuetest, 20, 13, 0x99000000);
		RenderUtil.drawRoundedRect(15, 15, valuetest, 20, 13, 0x9900f800);
		GlStateManager.popMatrix();*/
		
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(width/2f, height/3f, 0);
		GlStateManager.scale(2, 2, 1);
		GlStateManager.translate(-(width/2f), -(height/3f), 0);
		
		dufnctrmgyot6mh.drawString("By DevNL1 | P11L", (float) width/2f - dufnctrmgyot6mh.getStringWidth(Clarinet.name)/2f, (float) smoothY, hud.getGradientOffset(new Color(13, 29, 92), new Color(55, 15, 255), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D)).getRGB());
		
		GlStateManager.popMatrix();
		
		
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(width/2f, height/2f, 0);
		GlStateManager.scale(2, 2, 1);
		GlStateManager.translate(-(width/2f), -(height/2f), 0);
		
		dufnctrmgyot6mh.drawString(Clarinet.name, (float) width/2f - dufnctrmgyot6mh.getStringWidth(Clarinet.name)/2f, (float) smoothY, hud.getGradientOffset(new Color(13, 29, 92), new Color(55, 15, 255), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D)).getRGB());
		
		GlStateManager.popMatrix();
	}
	
	public void mouseClicked(int mouseX, int mouseY, int button) {
		String[] buttons = { "Solo", "Multi", "Settings","Alt Manager", "Quit" };
		int count = 0;
		for(String name : buttons) {
			float x = (width/buttons.length) * count + (width/buttons.length)/2f + 8 - mc.fontRendererObj.getStringWidth(name)/2f;
			float y = height - 20;
			
			if(mouseX >= x && mouseY >= y && mouseX < x + dufnctrmgyot6mh.getStringWidth(name) && mouseY < y + dufnctrmgyot6mh.getHeight()) {
				switch(name) {
					case "Solo":
						mc.displayGuiScreen(new GuiSelectWorld(this));
						break;
						
					case "Multi":
						mc.displayGuiScreen(new GuiMultiplayer(this));
						break;
						
					case "Settings":
						mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
						break;
					case "Alt Manager":
						mc.displayGuiScreen(new GuiAltManager());
						break;
						
					case "Quit":
						mc.shutdown();
						break;
						
				}
				
			}

			count++;
		}
	}
	public void onGuiClosed() {
		//x = width/2f - dufnctrmgyot6mh.getStringWidth(Clarinet.name)/2f;
		smoothY = -height/2f - dufnctrmgyot6mh.getHeight()/2f;
		valuetest = 0;
		
	}
	
	
}
