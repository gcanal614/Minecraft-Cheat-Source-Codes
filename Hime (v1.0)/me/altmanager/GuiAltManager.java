
package me.altmanager;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.Proxy;
import java.util.Random;
import java.util.UUID;

import me.Hime;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.mojang.authlib.Agent;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.realmsclient.gui.ChatFormatting;
import com.thealtening.auth.service.AlteningServiceType;

import me.util.NameGenerator;
import me.util.RenderUtil;
import me.util.TimeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;



public class GuiAltManager
extends GuiScreen {
	
    private GuiButton login;
    private GuiButton gen;
    private static GuiAltManager instance = null;
    private final UserAuthentication auth;
    private GuiButton remove;
    private GuiButton rename;
    String username2;
    String password2;
    private GuiTextField username;
    private GuiTextField password;
	//CustomTextField username;
	//CustomTextField password;
    public TimeUtil time = new TimeUtil();
    public NameGenerator namegen;

    private AltLoginThread loginThread;
    private int offset;
    public Alt selectedAlt = null;
    private String status = (Object)((Object)EnumChatFormatting.GRAY) + "No alts selected";
    public static GuiAltManager getInstance() {
        if (instance == null) {
            instance = new GuiAltManager();
        }
        return instance;
    }

    public static void setSession(Session s) {
        try {
            Field session = null;

            for (Field f : Minecraft.getMinecraft().getClass().getDeclaredFields()) {
                if (f.getType().isInstance(s)) {
                    session = f;
                    System.out.println("Found field " + f.toString() + ", injecting...");
                }
            }

            if (session == null) {
                throw new IllegalStateException("No field of type " + Session.class.getCanonicalName() + " declared.");
            }

            session.setAccessible(true);
            session.set(Minecraft.getMinecraft(), s);
            session.setAccessible(false);
        } catch (Exception e) {
        }
    }
    public GuiAltManager() {
        UUID uuid = UUID.randomUUID();
        AuthenticationService authService = new YggdrasilAuthenticationService(Minecraft.getMinecraft().getProxy(), uuid.toString());
        auth = authService.createUserAuthentication(Agent.MINECRAFT);
        authService.createMinecraftSessionService();
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 1: {
                String user = this.selectedAlt.getUsername();
                String pass = this.selectedAlt.getPassword();
                this.loginThread = new AltLoginThread(user, pass);
                this.loginThread.start();
                break;
            }
            case 2: {
                if (this.loginThread != null) {
                    this.loginThread = null;
                }
                AltManager.registry.remove(this.selectedAlt);
                this.status = "\u00a7aRemoved.";
                this.selectedAlt = null;
                break;

            }
            case 3: {
             if(this.username.getText().contains(":") && !this.username.getText().endsWith(":")) {
               if(this.password.getText().isEmpty()) {
         	   AddAltThread login = new AddAltThread(username.getText().split(":")[0], username.getText().split(":")[1]);
                login.start();
                username.setText("");
                password.setText("");
                this.status = "\u00a7aAdded Alt (Premium).";
               }
         	}else{
         		 AddAltThread login = new AddAltThread(this.username.getText(), this.password.getText());
                  login.start();
                  if(password.getText().isEmpty()) {
                     this.status = "\u00a7aAdded Alt (Cracked).";
                  }else {
                	 this.status = "\u00a7aAdded Alt (Premium).";
                  }
                  username.setText("");
                  password.setText("");
         	}
                break;
            }
           
        }
    }
    

    /*public void Altening(String username, String password) {
        auth.logOut();
        try {
            Login(AlteningServiceType.THEALTENING, username.trim(), password.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
 /*   public static void Login(AlteningServiceType type, String user, String pass) {
        //TheAlteningAuthentication.theAltening().updateService(type);
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
        try {
            auth.setUsername(user);
            auth.setPassword(pass);
            auth.logIn();
            Session session = new Session(auth.getSelectedProfile().getName(), UUIDTypeAdapter.fromUUID(auth.getSelectedProfile().getId()), auth.getAuthenticatedToken(), auth.getUserType().getName());
            setSession(session);
        } catch (Exception e) {
        }
    }*/
    @Override
    public void drawScreen(int par1, int par2, float par3) {
    	//Gui.drawRect(300, 100, 300, 300, 0x80000000);
    	
        if (Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                this.offset += 7;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            } else if (wheel > 0) {
                this.offset -= 7;
                if (this.offset < 0) {
                    this.offset = 0;
                }
            }
        }
        ScaledResolution sr = new ScaledResolution(mc);
    	this.mc.getTextureManager().bindTexture(new ResourceLocation("client/dark.png"));
    	Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight());
        RenderUtil.drawRoundedRect2(sr.getScaledWidth() / 3 - 15, 6, 340, sr.getScaledHeight() / 2 + 220, 5, Color.darkGray);
        RenderUtil.drawRoundedRect2(sr.getScaledWidth() / 3 - 15, 6, 340, 25, 5, Color.gray);

        RenderUtil.drawRoundedRect2(5, 5, 123, 170, 5, Color.gray);
        RenderUtil.drawRoundedRect2(5, 5, (int) Hime.instance.cfrs.getWidth("Current User: " + this.mc.session.getUsername()) + 10, 17, 5, Color.gray);

    	Hime.instance.cfrs.drawString("Current User: " + this.mc.session.getUsername(), 10, 10, -1);



        FontRenderer fontRendererObj = this.fontRendererObj;
        StringBuilder sb2 = new StringBuilder("Alts: ");
       
        Hime.instance.cfrs.drawCenteredString(sb2.append(AltManager.registry.size()).append("").toString(), width / 2 - 15, 10, -1);
        Hime.instance.cfrs.drawCenteredString(this.loginThread == null ? this.status : this.loginThread.getStatus(), width / 2 - 15, 20, -1);
       // RenderUtil1.rectangleBordered(50.0f, 33.0f, width - 50, height - 50, 1.0f, -16777216, Integer.MIN_VALUE);
        /*
    	RenderUtil.rectangleBordered(sr.getScaledWidth() / 2 + 160, 10, sr.getScaledWidth() - 10, sr.getScaledHeight() / 5, 1, 0x80000000, 0x60000000);
        for (Alt alt : AltManager.registry) {
            if (alt == this.selectedAlt) {
             String name = alt.getMask().equals("") ? alt.getUsername() : alt.getMask();
            	if(alt.getPassword().equals("")) {
            		 Hime.instance.rfrs.drawCenteredStringWithShadow("Alt Type: " + ChatFormatting.RED + "Cracked", width / 2 + 204, 16, -1);
            	}else if(!alt.getPassword().equals("") && alt.isHypixel()){
            		 Hime.instance.rfrs.drawCenteredStringWithShadow("Alt Type: " + ChatFormatting.GREEN + "Premium", width / 2 + 204, 32, -1);
            	}else {
            		 Hime.instance.rfrs.drawCenteredStringWithShadow("Alt Type: " + ChatFormatting.GREEN + "Premium", width / 2 + 204, 16, -1);
            	}
            	  if(alt.isHypixel()) {
                Hime.instance.rfrs.drawCenteredStringWithShadow("Hypixel Banned", width / 2 + 210, 16, -1);
               	RenderUtil.instance.draw2DImage(new ResourceLocation("client/hypixel.png"), (int) (width / 2 + 160),  13, 20, 17, Color.WHITE);
                  }
            	  if(alt.isMineplex()) {
                    Hime.instance.rfrs.drawCenteredStringWithShadow("Mineplex Banned", width / 2 + 300, 16, -1);
                    RenderUtil.instance.draw2DImage(new ResourceLocation("client/mineplex.png"), (int) (width / 2 + 245),  13, 20, 17, Color.WHITE);
                  }
              }
        }
        */
        GL11.glPushMatrix();
        this.prepareScissorBox(0.0f, 33.0f, width, height - 50);
        GL11.glEnable(3089);
        int y2 = 38;
        for (Alt alt2 : AltManager.registry) {

            if (!this.isAltInArea(y2)) continue;
            String name = alt2.getMask().equals("") ? alt2.getUsername() : alt2.getMask();
            String pass = alt2.getPassword().equals("") ? "\u00a7cCracked" : alt2.getPassword().replaceAll(".", "*");
            if (alt2 == this.selectedAlt) {
                if (this.isMouseOverAlt(par1, par2, y2 - this.offset) && Mouse.isButtonDown(0)) {
                	RenderUtil.drawRoundedRect2(sr.getScaledWidth() / 3 - 15, y2 - this.offset - 4, 340, 20, 5, new Color(50,50,50,150));
                } else if (this.isMouseOverAlt(par1, par2, y2 - this.offset)) {
                	RenderUtil.drawRoundedRect2(sr.getScaledWidth() / 3 - 15, y2 - this.offset - 4, 340, 20, 5, new Color(50,50,50,150));
                } else {
                	RenderUtil.drawRoundedRect2(sr.getScaledWidth() / 3 - 15, y2 - this.offset - 4, 340, 20, 5, new Color(50,50,50,150));
                }
            }
            if(alt2.isHypixel() && alt2 == this.selectedAlt){
                RenderUtil.drawRoundedRect2(sr.getScaledWidth() / 3 - 15, y2 - this.offset - 4, 340, 20, 5, new Color(50,0,0,150));
                Hime.instance.cfrs.drawCenteredString(name + " Hypixel Banned", width / 2 - 15, y2 - this.offset + 2, -1);
            } else {
                Hime.instance.cfrs.drawCenteredString(name, width / 2 - 15, y2 - this.offset + 2, -1);
            }

            //Hime.instance.cfrs.drawCenteredString(name, width / 2 - 15, y2 - this.offset + 2, -1);
            Hime.instance.rfrs.drawCenteredString(pass, width / 2 - 15, y2 - this.offset + 10, 5592405);
     

        
            
            y2 += 26;
        }
        GL11.glDisable(3089);
        GL11.glPopMatrix();



        username.draw();
        password.draw();
        
        super.drawScreen(par1, par2, par3);
        if (this.selectedAlt == null) {
            this.login.enabled = false;
            this.remove.enabled = false;
        } else {
            this.login.enabled = true;
            this.remove.enabled = true;
        }
        if (Keyboard.isKeyDown(200)) {
            this.offset -= 26;
            if (this.offset < 0) {
                this.offset = 0;
            }
        } else if (Keyboard.isKeyDown(208)) {
            this.offset += 26;
            if (this.offset < 0) {
                this.offset = 0;
            }
        }
    }
    
    /* Credit to Benny for giving to me thanks */
    protected String getHash(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    @Override
    public void initGui() {
        this.login = new GuiButton(1, 10, height / 5, 111, 20, "Login");
        this.remove = new GuiButton(2, 10, height / 3 - 20, 111, 20, "Remove Alt");
        this.buttonList.add(new GuiButton(3, 10, height / 4 - 1, 111, 20, "Add Alt"));
        this.buttonList.add(this.login);
        this.buttonList.add(this.remove);

        username = new GuiTextField(10, 40, "", "Username/Combo");
        password = new GuiTextField(10, 70, "", "Password/Nothing");

        this.login.enabled = false;
        this.remove.enabled = false;
        username.setMaxStringLength(100);
        password.setMaxStringLength(100);
    }

    private boolean isAltInArea(int y2) {
        if (y2 - this.offset <= height - 50) {
            return true;
        }
        return false;
    }

    private boolean isMouseOverAlt(int x2, int y2, int y1) {
        if (x2 >= 250 && y2 >= y1 - 4 && x2 <= width - 250 && y2 <= y1 + 20 && x2 >= 0 && y2 >= 33 && x2 <= width && y2 <= height - 50) {
            return true;
        }
        return false;
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) throws IOException {
        if(username != null) {
        	username.mouseClicked(par1, par2);
        }
	    if(password != null) {
	    	password.mouseClicked(par1, par2);
        }
        if (this.offset < 0) {
            this.offset = 0;
        }
        int y2 = 38 - this.offset;
    
        for (Alt alt2 : AltManager.registry) {
            if (this.isMouseOverAlt(par1, par2, y2)) {
                if (alt2 == this.selectedAlt) {
                    String user = this.selectedAlt.getUsername();
                    String pass = this.selectedAlt.getPassword();
                    this.loginThread = new AltLoginThread(user, pass);
                    this.loginThread.start();
                    return;
                }
                this.selectedAlt = alt2;
            }
            y2 += 26;
        }
        try {
            super.mouseClicked(par1, par2, par3);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    	this.username.textboxKeyTyped(typedChar, keyCode);
        this.password.textboxKeyTyped(typedChar, keyCode);
        
        super.keyTyped(typedChar, keyCode);
    }

    public void prepareScissorBox(float x2, float y2, float x22, float y22) {
        ScaledResolution scale = new ScaledResolution(this.mc);
        int factor = scale.getScaleFactor();
        GL11.glScissor((int)(x2 * (float)factor), (int)(((float)scale.getScaledHeight() - y22) * (float)factor), (int)((x22 - x2) * (float)factor), (int)((y22 - y2) * (float)factor));
    }
    
    public static void access$0(GuiAltManager guiAltManager, String status) {
    	guiAltManager.status = status;
    }

    private class AddAltThread
    extends Thread {
        private final String password;
        private final String username;

        public AddAltThread(String username, String password) {
            this.username = username;
            this.password = password;
            GuiAltManager.access$0(GuiAltManager.this, (Object)((Object)EnumChatFormatting.GRAY) + "Idle...");
        }

        private final void checkAndAddAlt(String username, String password) throws IOException {
        	Hime.switchService(AlteningServiceType.MOJANG);
            YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
            auth.setUsername(username);
            auth.setPassword(password);
            try {
                auth.logIn();
                
                AltManager.registry.add(new Alt(username, password, auth.getSelectedProfile().getName(), auth.getUserID()));
                GuiAltManager.access$0(GuiAltManager.this, "Alt added. (" + username + ")");
            }
            catch (AuthenticationException e) {
            	GuiAltManager.access$0(GuiAltManager.this, (Object)((Object)EnumChatFormatting.RED) + "Alt failed!");
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            if (this.password.equals("")) {
                //AltManager altManager = Hexa.theClient.altManager;
                AltManager.registry.add(new Alt(this.username, ""));
              
                GuiAltManager.access$0(GuiAltManager.this, (Object)((Object)EnumChatFormatting.GREEN) + "Alt added. (" + this.username + " - offline name)");
                return;
            }
            GuiAltManager.access$0(GuiAltManager.this, (Object)((Object)EnumChatFormatting.YELLOW) + "Trying alt...");
            try {
                this.checkAndAddAlt(this.username, this.password);
              
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

