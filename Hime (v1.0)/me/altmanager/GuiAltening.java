
package me.altmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URL;

import me.Hime;
import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;


public class GuiAltening
extends GuiScreen {
    private static GuiAltManager manager = new GuiAltManager();
    public static GuiAltening instance = new GuiAltening(manager);
    private PasswordField password;
    private AltLoginThread thread;
  //  public NameGenerator namegen;
    
    private String status = (Object)((Object)EnumChatFormatting.GRAY) + "Idle...";
    private GuiTextField username;
   // private GuiTextField Passwordusername;


    public GuiAltening(GuiAltManager manager) {
        this.manager = manager;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                if (!this.username.getText().isEmpty()) {
                    this.thread = new AltLoginThread(this.username.getText(), "Explicit", false);
                    this.thread.start();
                    this.status = (Object)((Object)EnumChatFormatting.GREEN) + "Logged Into Alt.";
                }
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(this.manager);
                break;
            }
            case 2: {
            	  Hime.instance.apyKey = password.getText();
                  Thread t = new Thread(new Runnable() {
                      @Override
                      public void run() {
                      }
                  });
                  t.start();
                  theAltening();
                  this.status = (Object)((Object)EnumChatFormatting.GREEN) + "Generated Alt Using Token.";
                break;
            }
        }
    }
    
    
    public void theAltening() throws IOException
    {
        String token = getToken();
        this.thread = new AltLoginThread(token, "Emilio", false);
        this.thread.start();
    }
    /*
     *  BasicDataRetriever data = TheAltening.newBasicRetriever(api);
        Account account = data.getAccount();
        System.out.println("Generated!");
        System.out.println(account.getToken());
        System.out.println("Hypixel Rank: " + account.getInfo().getHypixelRank());
        System.out.println("Hypixel Level: " + account.getInfo().getHypixelLevel());
        System.out.println("Mineplex Rank: " + account.getInfo().getMineplexRank());
     */

    public String getToken() throws IOException
    {
        String text;
        if (Hime.instance.apyKey != null && Hime.instance.apyKey != "" && (password == null || password.getText() == null || password.getText() == "")) {
            text = Hime.instance.apyKey;
        } else {
            text = password.getText();
        }
        int startChar = 10;
        int endChar = 29;
        BufferedReader br = null;
        String token = "";
        String _line = "";
        URL url = new URL("http://api.thealtening.com/v1/generate?token=");
        try {
            System.out.println(text);
            url = new URL("http://api.thealtening.com/v1/generate?token=" + text);
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line = br.readLine();
            _line = line;

            for (int k = 0; k < (endChar - startChar); k++)
            {
                token = token + line.charAt(startChar + k);
            }




        } finally {

            if (br != null) {
                br.close();
            }
        }
        System.out.println(_line);
        System.out.println(token);
        return token;
    }
    
    @Override
    public void drawScreen(int i2, int j2, float f2) {
    	 ScaledResolution sr = new ScaledResolution(mc);
     	this.mc.getTextureManager().bindTexture(new ResourceLocation("client/250472.jpg"));
     	Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight());
        this.username.drawTextBox();
        this.password.drawTextBox();
    //    this.Passwordusername.drawTextBox();
        Hime.instance.cfr.drawCenteredStringWithShadow("Altening", width / 2, 20, -1);
        if (this.username.getText().isEmpty()) {						
        	Hime.instance.cfrs.drawString("Alt Email", width / 2 - 96, 66, -7829368);
        }
        if (this.password.getText().isEmpty()) {											
            Hime.instance.cfrs.drawString("Token", width / 2 - 96, 106, -7829368);
        }
       // if (this.Passwordusername.getText().isEmpty()) {
       //     this.drawString(this.mc.fontRendererObj, "E-Mail:Password", width / 2 - 96, 142, -7829368);
      //  }
        Hime.instance.cfrs.drawCenteredStringWithShadow(this.status, width / 2, 34, -1);
        //if (Hime.instance.saveLoad != null) {
			//Hime.instance.saveLoad.save2();
		//}
        super.drawScreen(i2, j2, f2);
    }

    @Override
    public void initGui() {
    
       // password.maxStringLength = 20;
      //  username.maxStringLength = 20;
      //  Passwordusername.maxStringLength = 20;
    	//password.setMaxStringLength(30);
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 92 + 12, "Login"));
        this.buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 + 116 + 12, "Generate Using Token"));
        buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 116 + 37, "Back"));
      //  this.Passwordusername = new GuiTextField(this.eventButton, this.mc.fontRendererObj, width / 2 - 100, 137, 200, 20);
        this.username = new GuiTextField(this.eventButton, this.mc.fontRendererObj, width / 2 - 100, 60, 200, 20);
        this.password = new PasswordField(this.mc.fontRendererObj, width / 2 - 100, 100, 200, 20);
    }

    @Override
    protected void keyTyped(char par1, int par2) throws IOException {
        this.username.textboxKeyTyped(par1, par2);
        this.password.textboxKeyTyped(par1, par2);
      //  this.Passwordusername.textboxKeyTyped(par1, par2);
        if (par1 == '\t' && (this.username.isFocused() || this.password.isFocused())) { // || this.Passwordusername.isFocused()
            this.username.setFocused(!this.username.isFocused());
            this.password.setFocused(!this.password.isFocused());
          //  this.Passwordusername.setFocused(!this.Passwordusername.isFocused());
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
        this.username.mouseClicked(par1, par2, par3);
        this.password.mouseClicked(par1, par2, par3);
     //  this.Passwordusername.mouseClicked(par1, par2, par3);
    }

    public static void access$0(GuiAltening guiAddAlt, String status) {
        guiAddAlt.status = status;
    }

    private class AddAltThread
    extends Thread {
        private final String password;
        private final String username;

        public AddAltThread(String username, String password) {
            this.username = username;
            this.password = password;
            GuiAltening.access$0(GuiAltening.this, (Object)((Object)EnumChatFormatting.GRAY) + "Idle...");
        }

        private final void checkAndAddAlt(String username, String password) throws IOException {
            YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
            auth.setUsername(username);
            auth.setPassword(password);
            try {
                auth.logIn();
                
                AltManager.registry.add(new Alt(username, password, auth.getSelectedProfile().getName(), auth.getUserID()));
                                GuiAltening.access$0(GuiAltening.this, "Alt added. (" + username + ")");
            }
            catch (AuthenticationException e) {
                GuiAltening.access$0(GuiAltening.this, (Object)((Object)EnumChatFormatting.RED) + "Alt failed!");
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            if (this.password.equals("")) {
                //AltManager altManager = Hexa.theClient.altManager;
                AltManager.registry.add(new Alt(this.username, ""));
              
                GuiAltening.access$0(GuiAltening.this, (Object)((Object)EnumChatFormatting.GREEN) + "Alt added. (" + this.username + " - offline name)");
                return;
            }
            GuiAltening.access$0(GuiAltening.this, (Object)((Object)EnumChatFormatting.YELLOW) + "Trying alt...");
            try {
                this.checkAndAddAlt(this.username, this.password);
              
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

