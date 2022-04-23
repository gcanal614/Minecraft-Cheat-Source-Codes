/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.notification;

import club.tifality.gui.notification.NotificationType;
import club.tifality.utils.Wrapper;
import club.tifality.utils.render.LockedResolution;
import club.tifality.utils.render.OGLUtils;
import club.tifality.utils.render.RenderingUtils;
import club.tifality.utils.render.Translate;
import club.tifality.utils.timer.TimerUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public final class Notification {
    private final String title;
    private final String body;
    private final Translate translate;
    private final float width;
    private final float height;
    private final long duration;
    private final int color;
    private final TimerUtil timer;
    private boolean dead;

    public Notification(String title, String body, long duration, NotificationType type2) {
        this.title = title;
        this.body = body;
        this.width = body != null ? Math.max(Wrapper.getTitleFont().getWidth(title), Wrapper.getNameTagFontRenderer().getWidth(body)) + 4.0f : Wrapper.getFontRenderer().getWidth(title) + 4.0f;
        this.height = 27.0f;
        if (Wrapper.getCurrentScreen() == null) {
            LockedResolution lr = RenderingUtils.getLockedResolution();
            this.translate = new Translate(lr.getWidth(), (float)lr.getHeight() - this.height - 2.0f);
        } else {
            ScaledResolution sr = RenderingUtils.getScaledResolution();
            this.translate = new Translate(sr.getScaledWidth(), (float)sr.getScaledHeight() - this.height - 2.0f);
        }
        this.duration = duration;
        this.color = type2.getColor();
        this.timer = new TimerUtil();
    }

    public Notification(String title, String body, NotificationType type2) {
        this(title, body, (long)(title.length() + body.length()) * 40L, type2);
    }

    public Notification(String title, NotificationType type2) {
        this(title, null, (long)title.length() * 40L, type2);
    }

    public Notification(String title, long duration, NotificationType type2) {
        this(title, null, duration, type2);
    }

    public void render(LockedResolution lockedResolution, ScaledResolution scaledResolution, int index, int yOffset) {
        int height;
        int width;
        if (lockedResolution != null) {
            width = lockedResolution.getWidth();
            height = lockedResolution.getHeight();
        } else {
            width = scaledResolution.getScaledWidth();
            height = scaledResolution.getScaledHeight();
        }
        float notificationY = (float)height - (this.height + 2.0f) * (float)index - (float)yOffset;
        float notificationX = (float)width - this.width;
        if (this.timer.hasElapsed(this.duration)) {
            this.translate.animate(width, notificationY);
        } else {
            this.translate.animate(notificationX, notificationY);
        }
        float x = (float)this.translate.getX();
        float y = (float)this.translate.getY();
        if (x >= (float)width) {
            this.dead = true;
            return;
        }
        GL11.glEnable(3089);
        if (lockedResolution != null) {
            OGLUtils.startScissorBox(lockedResolution, (int)x, (int)y, MathHelper.ceiling_float_int(this.width), (int)this.height);
        } else {
            OGLUtils.startScissorBox(scaledResolution, (int)x, (int)y, MathHelper.ceiling_float_int(this.width), (int)this.height);
        }
        Gui.drawRect(notificationX, y, notificationX + this.width, y + this.height, 0x78000000);
        Gui.drawRect(notificationX, y + this.height - 2.0f, notificationX + this.width, y + this.height, RenderingUtils.darker(this.color, 0.4f));
        double progress = (double)(System.currentTimeMillis() - this.timer.getCurrentMs()) / (double)this.duration * (double)this.width;
        Gui.drawRect((double)x, (double)(y + this.height - 2.0f), (double)(x + this.width) - progress, (double)(y + this.height), this.color);
        if (this.body != null && this.body.length() > 0) {
            Wrapper.getFontRenderer().drawStringWithShadow(this.title, notificationX + 2.0f, y + 2.0f, -1);
            Wrapper.getNameTagFontRenderer().drawStringWithShadow(this.body, notificationX + 2.0f, y + 14.0f, -1);
        } else {
            Wrapper.getFontRenderer().drawStringWithShadow(this.title, notificationX + 2.0f, y + 9.0f, -1);
        }
        GL11.glDisable(3089);
    }

    public boolean isDead() {
        return this.dead;
    }
}

