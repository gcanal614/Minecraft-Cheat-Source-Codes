package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import me.altmanager.Alt;
import me.altmanager.AltLoginThread;
import me.altmanager.AltManager;
import me.altmanager.GuiAltManager;
import me.altmanager.GuiAltening;
import me.log.LogUtil;
import me.notification.Notification;
import me.notification.NotificationManager;
import me.notification.NotificationType;
import me.util.NameGenerator;
import me.util.ServerUtil;
import me.util.TimeUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;

public class GuiDisconnected extends GuiScreen
{
    private String reason;
    private IChatComponent message;
    private List<String> multilineMessage;
    private final GuiScreen parentScreen;
    private int field_175353_i;
    public TimeUtil time = new TimeUtil();
    public NameGenerator namegen;
    private AltLoginThread thread;

    public GuiDisconnected(GuiScreen screen, String reasonLocalizationKey, IChatComponent chatComp)
    {
        this.parentScreen = screen;
        this.reason = I18n.format(reasonLocalizationKey, new Object[0]);
        this.message = chatComp;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
        this.field_175353_i = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT, I18n.format("gui.toMenu", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 24, I18n.format("Reconnect", new Object[0])));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 48, I18n.format("Gen Cracked and Reconnect", new Object[0])));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 72, I18n.format("Gen Altening and Reconnect", new Object[0])));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 96, I18n.format("Alt Manager", new Object[0])));
        if (this.multilineMessage != null)
        {
        	  for (String s : this.multilineMessage)
              {
        		  if(s.contains("[GWEN Cheat Detection]") || s.contains("Ban Token:")) {
           	       for(Alt alt : AltManager.registry) {
           	    	      String name = alt.getMask().equals("") ? alt.getUsername() : alt.getMask();
           	        if(name.equals(mc.session.getUsername()) && !alt.isMineplex()){
           	         NotificationManager.show(new Notification(NotificationType.WARNING, "Alt Alert", "Mineplex Alt Banned!", 2));
           	          alt.setMineplex(true);
           	        }
           	       }
        		  }
        	      if(s.contains("§r§fWATCHDOG CHEAT DETECTION")|| s.contains("Cheating through the use") || s.contains("You are permanently banned from this server!")) {
        	       for(Alt alt : AltManager.registry) {
        	    	      String name = alt.getMask().equals("") ? alt.getUsername() : alt.getMask();
        	        if(name.equals(mc.session.getUsername()) && !alt.isHypixel()){
        	          alt.setHypixel(true);
                        NotificationManager.show(new Notification(NotificationType.WARNING, "Alt Alert", "Hypixel Alt Banned!", 2));
        	        }
        	       }
        	      } 
              }
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        if (button.id == 1)
        {
            ServerUtil.connectToLastServer();
        }
        if (button.id == 2)
        {
      //  this.namegen = new NameGenerator(RandomUtils.nextInt(5, 10));
        //String name = namegen.getName() + RandomUtils.nextInt(1, 10);
        	String Hash = "";
            String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
            StringBuilder salt = new StringBuilder();
            Random rnd = new Random();
            while (salt.length() < 8) { // length of the random string.
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            String saltStr = salt.toString();
            Hash = saltStr;
            
        String name2 = "Artemis_" + Hash;
        this.mc.session = new Session(name2, "", "", "mojang");
           
           if(time.hasTimePassed(500)) {
            ServerUtil.connectToLastServer();
            time.reset();
           }
        }
        if (button.id == 3)
        {
        	try {
        	String token = GuiAltening.instance.getToken();
            this.thread = new AltLoginThread(token, "Emilio", false);
            this.thread.start();
        	}catch(Exception e) {
        		e.printStackTrace();
        		LogUtil.instance.log("Altening Token is NULL (Nothing)", LogUtil.LogType.ERROR);
        	}
           
           if(time.hasTimePassed(500)) {
            ServerUtil.connectToLastServer();
            time.reset();
           }
        }
        if (button.id == 4)
        {
          this.mc.displayGuiScreen(new GuiAltManager());
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
    	  ScaledResolution sr = new ScaledResolution(mc);
    	this.mc.getTextureManager().bindTexture(new ResourceLocation("client/250472.jpg"));
    	Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight());
      //  this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.reason, this.width / 2, this.height / 2 - this.field_175353_i / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
        int i = this.height / 2 - this.field_175353_i / 2;

        this.drawCenteredString(this.fontRendererObj, "Current Alt: " + mc.session.getUsername(), this.width / 2, this.height / 2 + this.field_175353_i / 2 + this.fontRendererObj.FONT_HEIGHT + 125, -1);
        
        if (this.multilineMessage != null)
        {
            for (String s : this.multilineMessage)
            {
                this.drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 16777215);
                i += this.fontRendererObj.FONT_HEIGHT;
            }
        }
        
        NotificationManager.render();
       // System.out.println(this.multilineMessage);
        // || this.reason.contains("Cheating through the use")
  
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
