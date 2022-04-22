package me.notification;

import java.awt.Color;

import me.Hime;
import me.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class Notification {
    public NotificationType type;
    public String title;
	public String messsage;
	public long start;

	public long fadedIn;
	public long fadeOut;
	public long end;
    
    public int length;

    public Notification(NotificationType type, String title, String messsage, int length) {
    	
        this.type = type;
        this.title = title;
        this.messsage = messsage;

        fadedIn = 200 * length;
        fadeOut = fadedIn + 500 * length;
        end = fadeOut + fadedIn;
        this.length = length;
    }

    public void show() {
        start = System.currentTimeMillis();
    }

    public boolean isShown() {
        return getTime() <= end;
    }

    public long getTime() {
        return System.currentTimeMillis() - start;
    }

    
    public String getMesssage() {
 		return messsage;
 	}
    
    public void render() {

        if (!NotificationManager.getNotifications().isEmpty()) {
            NotificationManager.update();
        }

        for (Notification notification : NotificationManager.getNotifications()) {
            double offset = 0;
            int width = 120;
            int height = 30;
            long time = getTime();

            if (time < fadedIn) {
                offset = Math.tanh(time / (double) (fadedIn) * 3.0) * width;
            } else if (time > fadeOut) {
                offset = (Math.tanh(3.0 - (time - fadeOut) / (double) (end - fadeOut) * 3.0) * width);
            } else {
                offset = width;
            }

            Color color = new Color(0, 0, 0, 220);
            Color color1;

            if (type == NotificationType.INFO)
                color1 = new Color(64, 64, 64);
            else if (type == NotificationType.WARNING)
                color1 = new Color(204, 193, 0);
            else if (type == NotificationType.ERROR) {
                color1 = new Color(204, 0, 18);
                int i = Math.max(0, Math.min(255, (int) (Math.sin(time / 100.0) * 255.0 / 2 + 127.5)));
                color = new Color(i, 0, 0, 220);
            } else if (type == NotificationType.ENABLE) {
                color1 = new Color(0, 255, 0);
            } else {
                color1 = new Color(255, 51, 0);
            }

            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

            Gui.drawRect(GuiScreen.width - Hime.instance.cfrs.getWidth(messsage) - offset + 85, GuiScreen.height - 5 - height, GuiScreen.width, GuiScreen.height - 5, 0x60000000);
            Gui.drawRect(GuiScreen.width - Hime.instance.cfrs.getWidth(messsage) + 85 - offset + (time / (this.length == 2 ? 15 : 70)), GuiScreen.height - 5 - height + 28, GuiScreen.width, GuiScreen.height - 5, color1.getRGB());
            if (type == NotificationType.ENABLE || type == NotificationType.DISABLE || type == NotificationType.INFO) {
                RenderUtil.instance.drawImage(null, new ResourceLocation("client/error.png"), (int) (GuiScreen.width - Hime.instance.cfrs.getWidth(messsage) - offset + 90), GuiScreen.height - 5 - height + 2, 24, 24, 24, 24, 24, 24);
            } else {
                RenderUtil.instance.drawImage(null, new ResourceLocation("client/error.png"), (int) (GuiScreen.width - Hime.instance.cfrs.getWidth(messsage) - offset + 90), GuiScreen.height - 5 - height + 2, 24, 24, 24, 24, 24, 24);
            }
            ;
            Hime.instance.cfrs.drawString(title, (float) (GuiScreen.width - Hime.instance.cfrs.getWidth(messsage) - offset + 120), GuiScreen.height - 32, -1);
            Hime.instance.cfrs.drawString(messsage, (float) (GuiScreen.width - Hime.instance.cfrs.getWidth(messsage) - offset + 120), GuiScreen.height - 18, -1);
        }
    }




}
