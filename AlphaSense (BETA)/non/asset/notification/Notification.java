package non.asset.notification;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import non.asset.Clarinet;
import non.asset.module.impl.visuals.HUD;
import non.asset.utils.OFC.RenderUtil;
import non.asset.utils.OFC.TimerUtil;

import java.awt.*;

public class Notification {
    private TimerUtil timer;
    private static Minecraft mc = Minecraft.getMinecraft();
    private float x, oldX, y, oldY, width;
    private String text;
    private int stayTime;
    private boolean done;
    private float stayBar;

    Notification(String text, int stayTime) {
        this.x = new ScaledResolution(mc).getScaledWidth() - 2;
        this.y = new ScaledResolution(mc).getScaledHeight() - 2;
        this.text = text;
        this.stayTime = stayTime;
        timer = new TimerUtil();
        timer.reset();
        stayBar = stayTime;
        done = false;
    }

    public void draw(float prevY) {
        HUD hud = (HUD) Clarinet.INSTANCE.getModuleManager().getModule("hud");
        final float xSpeed = width / (Minecraft.getDebugFPS() / 2);
        final float ySpeed = (new ScaledResolution(mc).getScaledHeight() - prevY) / (Minecraft.getDebugFPS() / 2);
        if (hud.font.isEnabled()) {
            if (width != hud.fontValue.getValue().getStringWidth(text) + 8) {
                width = hud.fontValue.getValue().getStringWidth(text) + 8;
            }
        } else {
            if (width != mc.fontRendererObj.getStringWidth(text) + 8) {
                width = mc.fontRendererObj.getStringWidth(text) + 8;
            }
        }
        if (done) {
            oldX = x;
            x += xSpeed;
            y += ySpeed;
        }
        if (!done() && !done) {
            timer.reset();
            oldX = x;
            if (x <= new ScaledResolution(mc).getScaledWidth() - 2 - width + xSpeed)
                x = new ScaledResolution(mc).getScaledWidth() - 2 - width;
            else x -= xSpeed;
        } else if (timer.reach(stayTime)) done = true;
        if (x < new ScaledResolution(mc).getScaledWidth() - 2 - width) {
            oldX = x;
            x += xSpeed;
        }
        if (y != prevY) {
            if (y != prevY) {
                if (y > prevY + ySpeed) {
                    y -= ySpeed;
                } else {
                    y = prevY;
                }
            } else if (y < prevY) {
                oldY = y;
                y += ySpeed;
            }
        }
        if (done() && !done) {
            stayBar = timer.time();
        }
        final float finishedX = oldX + (x - oldX);
        final float finishedY = oldY + (y - oldY);
        RenderUtil.drawRoundedRect(finishedX, finishedY, width, 24, 0, new Color(10, 10, 10, 225).getRGB());
        if (hud.font.isEnabled())
            hud.fontValue.getValue().drawStringWithShadow(text, finishedX + 5, finishedY + 9, -1);
        else
            mc.fontRendererObj.drawStringWithShadow(text, finishedX + 5, finishedY + 9, -1);
        if (done())
            RenderUtil.drawRoundedRect(finishedX, finishedY, ((width - 1) / stayTime) * stayBar, 2, 0, 0xff00f400);
        if (delete()) Clarinet.INSTANCE.getNotificationManager().getNotifications().remove(this);
    }

    public boolean done() {
        return x <= new ScaledResolution(mc).getScaledWidth() - 2 - width;
    }

    public boolean delete() {
        return x >= new ScaledResolution(mc).getScaledWidth() - 2 && done;
    }
}
