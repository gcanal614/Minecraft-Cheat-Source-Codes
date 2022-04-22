
package me.altmanager;

import java.io.IOException;
import java.net.Proxy;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;


public class GuiClipboard
extends GuiScreen {
    private final GuiAltManager manager;
String pass;
String username;
    
    private String status = (Object)((Object)EnumChatFormatting.GRAY) + "Idle...";

    private GuiTextField Passwordusername;

    public GuiClipboard(GuiAltManager manager) {
        this.manager = manager;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
            	if (!this.Passwordusername.getText().isEmpty()) {
                       if(this.Passwordusername.getText().contains(":") && !this.Passwordusername.getText().endsWith(":")) {
            	 username = this.Passwordusername.getText().split(":")[0];
            	pass =  this.Passwordusername.getText().split(":")[1];
            	
            	   AddAltThread login = new AddAltThread(username, pass);
                   login.start();
                  
               }
            	}
            	 break;
            }
               
        
            case 1: {
                this.mc.displayGuiScreen(this.manager);
            }
        }
    }

    @Override
    public void drawScreen(int i2, int j2, float f2) {
        this.drawDefaultBackground();
       
        this.Passwordusername.drawTextBox();
        this.drawCenteredString(this.fontRendererObj, "Add clipboard alt", width / 2, 20, -1);
       
       
        if (this.Passwordusername.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "E-Mail:Password", width / 2 - 96, 142, -7829368);
        }
        this.drawCenteredString(this.fontRendererObj, this.status, width / 2, 30, -1);
        //if (Hime.instance.saveLoad != null) {
			///Hime.instance.saveLoad.save2();
		//}
        super.drawScreen(i2, j2, f2);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 92 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 116 + 12, "Back"));
        this.Passwordusername = new GuiTextField(this.eventButton, this.mc.fontRendererObj, width / 2 - 100, 137, 200, 20);
     
    }

    @Override
    protected void keyTyped(char par1, int par2) {

        this.Passwordusername.textboxKeyTyped(par1, par2);
        if (par1 == '\t' && (this.Passwordusername.isFocused())) {

            this.Passwordusername.setFocused(!this.Passwordusername.isFocused());
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
  
        this.Passwordusername.mouseClicked(par1, par2, par3);
    }

    public static void access$0(GuiClipboard guiAddAlt, String status) {
        guiAddAlt.status = status;
    }

    private class AddAltThread
    extends Thread {
        private final String password;
        private final String username;

        public AddAltThread(String username, String password) {
            this.username = username;
            this.password = password;
            GuiClipboard.access$0(GuiClipboard.this, (Object)((Object)EnumChatFormatting.GRAY) + "Idle...");
        }

        private final void checkAndAddAlt(String username, String password) throws IOException {
            YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
            auth.setUsername(username);
            auth.setPassword(password);
            try {
                auth.logIn();
               // AltManager altManager = Hexa.theClient.altManager;
                AltManager.registry.add(new Alt(username, password, auth.getSelectedProfile().getName()));
          
                GuiClipboard.access$0(GuiClipboard.this, "Alt added. (" + username + ")");
            }
            catch (AuthenticationException e) {
                GuiClipboard.access$0(GuiClipboard.this, (Object)((Object)EnumChatFormatting.RED) + "Alt failed!");
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            if (this.password.equals("")) {
                //AltManager altManager = Hexa.theClient.altManager;
                AltManager.registry.add(new Alt(this.username, ""));
             
                GuiClipboard.access$0(GuiClipboard.this, (Object)((Object)EnumChatFormatting.GREEN) + "Alt added. (" + this.username + " - offline name)");
                return;
            }
            GuiClipboard.access$0(GuiClipboard.this, (Object)((Object)EnumChatFormatting.YELLOW) + "Trying alt...");
            try {
                this.checkAndAddAlt(this.username, this.password);
               
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

