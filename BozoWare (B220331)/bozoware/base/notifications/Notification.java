// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.notifications;

import bozoware.visual.font.MinecraftFontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import bozoware.base.BozoWare;

public class Notification
{
    private NotificationType type;
    private String title;
    private String messsage;
    private long start;
    private double notifOffset;
    private long fadedIn;
    private long fadeOut;
    private long end;
    
    public Notification(final NotificationType type, final String title, final String messsage, final float length) {
        this.type = type;
        this.title = title;
        this.messsage = messsage;
        this.fadedIn = (long)(200.0f * length);
        this.fadeOut = (long)(this.fadedIn + 500.0f * length);
        this.end = this.fadeOut + this.fadedIn;
    }
    
    public void show() {
        this.start = System.currentTimeMillis();
    }
    
    public boolean isShown() {
        return this.getTime() <= this.end;
    }
    
    private long getTime() {
        return System.currentTimeMillis() - this.start;
    }
    
    public void render() {
        double offset = 16.0;
        final int width = 120;
        final int height = 30;
        final long time = this.getTime();
        if (time < this.fadedIn) {
            offset = Math.tanh(time / (double)this.fadedIn * 3.0) * width;
        }
        else if (time > this.fadeOut) {
            offset = Math.tanh(3.0 - (time - this.fadeOut) / (double)(this.end - this.fadeOut) * 3.0) * width;
        }
        else {
            offset = width;
        }
        final MinecraftFontRenderer MFR = BozoWare.getInstance().getFontManager().mediumFontRenderer;
        final MinecraftFontRenderer SFR = BozoWare.getInstance().getFontManager().smallFontRenderer;
        final ScaledResolution SR = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        Gui.drawRect(SR.getScaledWidth() - offset, SR.getScaledHeight() - 5 - height, SR.getScaledWidth(), SR.getScaledHeight(), 1073741824);
        MFR.drawString(this.type.name(), (int)(SR.getScaledWidth() - offset + 5.0), SR.getScaledHeight() - 2 - height, -1);
        SFR.drawString(this.messsage, (int)(SR.getScaledWidth() - offset + 5.0), SR.getScaledHeight() - 17, -1);
    }
}
