package me.hwid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import me.Hime;
import me.notification.Notification;
import me.notification.NotificationManager;
import me.notification.NotificationType;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.altmanager.PasswordField;
import me.util.RenderUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;

public class UserLogin extends GuiScreen {
    private PasswordField password;
    private GuiTextField username;
    public static GuiMainMenu guiMainMenu;
    public static String Status = ChatFormatting.GRAY + "Idle...";
    public static Scanner scanner;
    public UserLogin()
    {
        this.guiMainMenu = new GuiMainMenu();
    }
    private static final char[] hexArray;
    public static byte[] MaKeMeMes() {
        try {
            final MessageDigest hash = MessageDigest.getInstance("MD5");
            final StringBuilder s = new StringBuilder();
            s.append(System.getProperty("os.name"));
            s.append(System.getProperty("os.arch"));
            s.append(System.getProperty("os.version"));
            s.append(Runtime.getRuntime().availableProcessors());
            s.append(System.getenv("PROCESSOR_IDENTIFIER"));
            s.append(System.getenv("PROCESSOR_ARCHITECTURE"));
            s.append(System.getenv("PROCESSOR_ARCHITEW6432"));
            s.append(System.getenv("NUMBER_OF_PROCESSORS"));
            return hash.digest(s.toString().getBytes());
        }
        catch (NoSuchAlgorithmException e) {
            throw new Error("Algorithm wasn't found.", e);
        }
    }

    public static String MagicMemes(final byte[] bytes) {
        final char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; ++j) {
            final int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0xF];
        }
        return new String(hexChars);
    }

    @Override
    public void initGui()
    {
	    this.Status = ChatFormatting.GRAY + "Login...";
        int var3 = height / 4 + 24;
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 3 + 110, "Login"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 3 + 140, "Exit Game"));
        this.username = new GuiTextField(var3, this.mc.fontRendererObj, width / 2 - 100, 170, 200, 20);
        this.password = new PasswordField(this.mc.fontRendererObj, width / 2 - 100, 210, 200, 20);

        this.username.setText("");
        this.password.setText("");

        this.username.setFocused(true);

        Keyboard.enableRepeatEvents(true);

        if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
            if (mc.entityRenderer.theShaderGroup != null) {
                mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            }
            mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }

    }

    @Override
    public void onGuiClosed() {
        /*
         * End blur
         */
        if (mc.entityRenderer.theShaderGroup != null) {
            mc.entityRenderer.theShaderGroup.deleteShaderGroup();
            mc.entityRenderer.theShaderGroup = null;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {

        switch (button.id)
        {
            case 1:
            {
        	mc.displayGuiScreen(guiMainMenu);
            //	mc.shutdown();
        	 break;
            }
            case 0:
            {
                if(this.CheckCreds(this.username.getText(), this.password.getText())) {
                    System.out.println("Login Confirmed");
                    //Judgement.INSTANCE.username = this.username.getText();
                    //Judgement.INSTANCE.password = this.password.getText();
                    mc.displayGuiScreen(guiMainMenu);
                 //   mc.displayGuiScreen(guiMainMenu);
                    this.onGuiClosed();
                    
                } else {
                 //   this.Status = ChatFormatting.DARK_RED + "Login failed!";
                }
                break;
            }
        }
    }
    private boolean CheckCreds(String username, String password) {
        boolean found = false;
        String F = MagicMemes(MaKeMeMes());
        try {
            final URL url = new URL("https://pastebin.com/raw/K4e7fr8U");
            //TODO url here
            try {
                UserLogin.scanner = new Scanner(url.openStream());
                while (UserLogin.scanner.hasNextLine()) {
                    if (found) {
                        break;
                    }

                    final String creds = UserLogin.scanner.nextLine();

                    if (creds.contains(":"))
                    {
                        String[] args = creds.split(":");

                        if(username.equals(args[0])) {
                            
                            System.out.println("Confirmed username, " + username);
                            this.Status = "Confirmed username, " + username;
                            if(password.equals(args[1])) {
                                System.out.println("Confirmed password, " + password);
                                this.Status = "Confirmed password, " + password;
                                if(F.equals(args[2])) {
                                    System.out.println("Setting user, " + username);
                                    found = true;
                                    Hime.instance.user = username;
                                    System.out.println("Confirmed HWID, Username, and Password | User: " + Hime.instance.user);
                                    this.Status = ChatFormatting.GREEN + "Confirmed HWID, Username, and Password | User: " + Hime.instance.user;
                                    this.username.setText("");
                                    this.password.setText("");
                                } else {
                                    this.Status = ChatFormatting.DARK_RED + "Denied HWID, " + F;
                                    System.out.println("Denied hwid, " + F + ", " + args[2]);
                                    found = false;
                                }

                            } else {
                                this.Status = ChatFormatting.DARK_RED + "Denied password, " + password;
                                System.out.println("Denied password, " + password);
                                found = false;
                            }
                        } else {
                            this.Status = ChatFormatting.DARK_RED + "Denied username, " + username;
                            System.out.println("Denied username, " + username);
                            found = false;
                        }
                    }
                }

            } catch (IOException e) {e.printStackTrace();}
        } catch (MalformedURLException e) {e.printStackTrace();}
        return found;
    }
    @Override
    public void drawScreen(int x2, int y2, float z2)
    {
    	this.drawBackground(x2, y2);
    	//this.drawDefaultBackground();
       
  
   	 
        GL11.glPushMatrix();
        GL11.glScaled(2, 2, 1);
        GL11.glPopMatrix();
     //   this.drawGradientRect(0, 0, this.width, this.height, -2130706433, 16777215);
       // this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);

      
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        RenderUtil.rectangleBordered(width / 2 - 150, 100/*height / 50*/, (width / 2 - 100) + 250/*this.width - 300*/, height / 4 + 24 + 72 + 12 + 72 + 25, 5, 0x90000000,0x99000000);
        this.username.drawTextBox();
        this.password.drawTextBox();
        Hime.instance.rfrs.drawCenteredStringWithShadow("Hime Beta Login - Log in with your Username & Password", width / 2, 135, -1);
        Hime.instance.rfrs.drawCenteredString(this.Status, width / 2, 115, -1);
        
        Hime.instance.rfrs.drawCenteredStringWithShadow("Made by G8LOL, Bomt, and Raxu", width / 2, height - 10, -1);

        if (this.username.getText().isEmpty())
        {
        	 Hime.instance.rfrs.drawString("Username", width / 2 - 96, 176, -7829368);
        }
        if (this.password.getText().isEmpty())
        {
            Hime.instance.rfrs.drawString("Password", width / 2 - 96, 216, -7829368);
        }
        super.drawScreen(x2, y2, z2);
    }

    @Override
    protected void keyTyped(char character, int key)
    {
        try
        {
            super.keyTyped(character, key);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if (character == '\t')
        {
            if (!this.username.isFocused() && !this.password.isFocused())
            {
                this.username.setFocused(true);
            }
            else
            {
                this.username.setFocused(this.password.isFocused());
                this.password.setFocused(!this.username.isFocused());
            }
        }

        if (character == '\r')
        {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }

        this.username.textboxKeyTyped(character, key);
        this.password.textboxKeyTyped(character, key);
    }

    @Override
    protected void mouseClicked(int x2, int y2, int button)
    {
        try
        {
            super.mouseClicked(x2, y2, button);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        this.username.mouseClicked(x2, y2, button);
        this.password.mouseClicked(x2, y2, button);
    }


    @Override
    public void updateScreen()
    {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
    }
    
    public static String getHWID() {
        try {
            String command = "wmic csproduct get UUID";
            Process sNumProcess = Runtime.getRuntime().exec(command);
            BufferedReader sNumReader = new BufferedReader(new InputStreamReader(sNumProcess.getInputStream()));

            String hwid;
            while ((hwid = sNumReader.readLine()) != null) {
                if (hwid.matches(".*[0123456789].*")) break;
            }

            return hwid;
        } catch (IOException e) {
        }
        return null;
    }
    
    static {
        hexArray = "0123456789ABCDEF".toCharArray();
    }
}
