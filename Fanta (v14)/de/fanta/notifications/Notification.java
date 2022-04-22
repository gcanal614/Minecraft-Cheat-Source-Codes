/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.notifications;

import de.fanta.Client;
import de.fanta.utils.TimeUtil;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class Notification
extends Gui {
    private boolean showing = false;
    private boolean started = false;
    private String name;
    private String description;
    private long wait = 2L;
    private boolean doneWaiting = false;
    private NotificationType type;
    private float xFade = 0.0f;
    private float startFade = 0.0f;
    private int maxWidth = 150;
    private TimeUtil timer = new TimeUtil();

    public Notification(String name, String description, long wait, NotificationType type) {
        this.name = name;
        this.description = description;
        this.type = type;
        int nameWidth = Client.INSTANCE.arial2.getStringWidth(name);
        int descWidth = Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(description);
        if (nameWidth > 170 || descWidth > 170) {
            this.maxWidth = nameWidth > descWidth ? nameWidth + 20 : descWidth + 20;
        }
        this.xFade = 5 - this.maxWidth - 10;
        this.startFade = 5 - this.maxWidth - 10;
    }

    public void draw() {
        if (this.isShowing()) {
            if (!this.doneWaiting && this.xFade < 11.0f) {
                this.xFade += 1.0f;
                this.timer.reset();
            } else if (this.doneWaiting && this.xFade > this.startFade) {
                this.xFade -= 1.0f;
            } else if (this.doneWaiting && this.xFade < this.startFade + 1.0f) {
                this.setShowing(false);
            }
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            int width = this.maxWidth;
            int height = 50;
            int bgColor = Integer.MIN_VALUE;
            switch (this.type) {
                case INFO: {
                    bgColor = new Color(20, 20, 40, 150).getRGB();
                    break;
                }
                case WARNING: {
                    bgColor = new Color(145, 75, 20, 150).getRGB();
                    break;
                }
                case ERROR: {
                    bgColor = new Color(75, 20, 20, 150).getRGB();
                    break;
                }
                default: {
                    bgColor = new Color(20, 20, 20, 150).getRGB();
                }
            }
            Client.blurHelper2.blur2((float)sr.getScaledWidth() - this.xFade - (float)width, sr.getScaledHeight() - height, (float)sr.getScaledWidth() - this.xFade, sr.getScaledHeight() - 20, 30.0f);
            Notification.drawRect((float)sr.getScaledWidth() - this.xFade - (float)width, sr.getScaledHeight() - height, (float)sr.getScaledWidth() - this.xFade, sr.getScaledHeight() - 20, new Color(30, 30, 30, 200).getRGB());
            GlStateManager.resetColor();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.pushMatrix();
            Client.INSTANCE.fluxTabGuiFont.drawString(this.name, (float)sr.getScaledWidth() - this.xFade - (float)width + 5.0f, sr.getScaledHeight() - height + 2, Color.white.getRGB());
            Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(this.description, (float)sr.getScaledWidth() - this.xFade - (float)width + 5.0f, sr.getScaledHeight() - 5 - height + 21, Color.white.getRGB());
            GlStateManager.popMatrix();
            GlStateManager.resetColor();
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            if (this.timer.hasReached(this.wait)) {
                this.doneWaiting = true;
            }
        }
    }

    public boolean isShowing() {
        return this.showing;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
    }

    public boolean isStarted() {
        return this.started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NotificationType getType() {
        return this.type;
    }

    public static enum NotificationType {
        INFO,
        ERROR,
        WARNING,
        NONE;

    }
}

