
package me.proxymanager;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;
import java.util.ArrayList;

import me.Hime;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;


public class GuiAddProxy
extends GuiScreen {
    private final GuiMultiplayer manager;
    protected ArrayList<GuiChangeProxyButton> imageButtons = new ArrayList<GuiChangeProxyButton>();
    private static ProxyType proxyType = ProxyType.SOCKS4;
    
    private String status = (Object)((Object)EnumChatFormatting.GRAY) + "Idle...";
    private String proxyStatus = this.proxyType.toString();
    private GuiTextField ipField;
    private GuiTextField portField;
    public static String ip;
    public static int port;
    private int modeIndex;

    public GuiAddProxy(GuiMultiplayer manager) {
        this.manager = manager;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
            	ip = this.ipField.getText();
            	try {
            	port = Integer.valueOf(this.portField.getText());
            	this.status = (Object)((Object)EnumChatFormatting.GREEN) + "Loaded Proxy";
            	}catch(Exception e) {
            		e.printStackTrace();
            		this.status = (Object)((Object)EnumChatFormatting.RED) + "Port Failed!";
            	}
            	
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(this.manager);
            }
            case 2: {
                String data = null;
                try {
                    data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                } catch (Exception e) {}
                
                if (data.contains(":")) {
                    String[] credentials = data.split(":");
                    ipField.setText(credentials[0]);
                    portField.setText(credentials[1]);
                  //  AddAltThread login = new AddAltThread(this.ipField.getText(), this.portField.getText());
                  //  login.start();
                //	ip = this.ipField.getText();
                	try {
						//port = Integer.valueOf(this.portField.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
                    this.status = (Object)((Object)EnumChatFormatting.GRAY) + "Added Proxy to Port and IP";
                }else {
                	this.status = (Object)((Object)EnumChatFormatting.RED) + "Invalid IP:Port";
                }
            }
        }
    }

    @Override
    public void drawScreen(int i2, int j2, float f2) {
    	 ScaledResolution sr = new ScaledResolution(mc);
     	this.mc.getTextureManager().bindTexture(new ResourceLocation("client/250472.jpg"));
     	Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight());
        this.ipField.drawTextBox();
        this.portField.drawTextBox();
        this.proxyStatus = this.proxyType.toString();
        Gui.drawRect(width / 2 - 100, 15, width / 2 + 100, 50, 0x80000000);
        Hime.instance.cfrs.drawCenteredString("Add Proxy", width / 2, 20, -1);
        if (this.ipField.getText().isEmpty()) {						
        	Hime.instance.cfrs.drawString("IP", width / 2 - 96, 66, -7829368);
        }
        if (this.portField.getText().isEmpty()) {											
            Hime.instance.cfrs.drawString("Port", width / 2 - 96, 106, -7829368);
        }
        Hime.instance.cfrs.drawCenteredString(this.status, width / 2, 30, -1);
        Hime.instance.cfrs.drawCenteredString("Proxy Type: " + this.proxyStatus, width / 2, 40, -1);
        //if (Hime.instance.saveLoad != null) {
			//Hime.instance.saveLoad.save2();
		//}
        for(GuiChangeProxyButton button : this.imageButtons)
        	button.drawButton(i2, j2);
        
        super.drawScreen(i2, j2, f2);
    }

    @Override
    public void initGui() {
       // password.maxStringLength = 20;
      //  username.maxStringLength = 20;
      //  Passwordusername.maxStringLength = 20;
    	//password.setMaxStringLength(30);
    	this.modeIndex = 0;
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 92 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 116 + 12, "Back"));
        buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 116 + 36, "Import ip:port"));
        this.imageButtons.clear();
        this.imageButtons.add(new GuiChangeProxyButton(this.width / 2 - 100, this.height / 4 + 28));
       // buttonList.add(new GuiButton(9, this.width / 2 - 100, this.height / 4 + 116 + 60, "Change Proxy Type"));
        this.ipField = new GuiTextField(this.eventButton, this.mc.fontRendererObj, width / 2 - 100, 60, 200, 20);
        this.portField = new GuiTextField(this.eventButton, this.mc.fontRendererObj, width / 2 - 100, 100, 200, 20);
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        this.ipField.textboxKeyTyped(par1, par2);
        this.portField.textboxKeyTyped(par1, par2);
        if (par1 == '\t' && (this.ipField.isFocused() || this.portField.isFocused())) { // 
            this.ipField.setFocused(!this.ipField.isFocused());
            this.portField.setFocused(!this.portField.isFocused());
        }
        if (par1 == '\r') {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        try {
            super.mouseClicked(par1, par2, par3);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.ipField.mouseClicked(par1, par2, par3);
        this.portField.mouseClicked(par1, par2, par3);
        
        for(GuiChangeProxyButton button : this.imageButtons)
        	button.mousePressed(par1, par2, par3);
    }

    public static void access$0(GuiAddProxy guiAddAlt, String status) {
        guiAddAlt.status = status;
    }
    
    
    public static ProxyType getProxyType() {
		return proxyType;
	}

	public static void setProxyType(ProxyType proxyType) {
		GuiAddProxy.proxyType = proxyType;
	}

	public enum ProxyType {
    	SOCKS4, HTTP, SOCKS5;
    }
	
	public static boolean isProxyAvailable() {
        if(ip != null && String.valueOf(port) != null)
            return true;
        return false;
    }

}

