package wtf.astronicy.UIs.Notifications;

import java.awt.Color;

import wtf.astronicy.IMPL.utils.TimerUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Notification {
    public static Minecraft mc = Minecraft.getMinecraft();

    public boolean isClassicNotification;
    public String message;
    public String title;
    TimerUtility timer;
    private float animationX;
    private float animationY;
    private float width;
    private float height;
    private int delay;
    private Animation animation;
   
    public Notification(String title, String message, int delay) {

        this.message = message;
        this.title = title;
        this.delay = delay;
        
        if(mc.fontRenderer.getStringWidth(title) < mc.fontRenderer.getStringWidth(message)) {
            this.width = mc.fontRenderer.getStringWidth(message) + 45;
        }else{
            this.width = mc.fontRenderer.getStringWidth(title) + 45;
        }
              
        this.height = 22.0f;
        this.animationX = 140.0f;
        animation = new Animation(100.0F, ScreenUtils.getHeight());
        this.timer = new TimerUtility();
        timer.reset();
    }

    public void draw(float x, float offsetY) {
    	
        float target = isFinished() ? width : 0;
        animation.interpolate(target, offsetY, 6);
        this.animationX = animation.getX();

        if (animationY == 0) {
            animationY = offsetY;
        }

        animationY = animation.getY();

        float x1 = x - width + this.animationX;
        float x2 = x + animationX + 0;

        float y1 = animationY - 2;
        float y2 = y1 + height;

    	Gui.drawRect(x1 + 35, y1, x2, y2, new Color(0, 0, 0, 100).getRGB());
    	mc.fontRenderer.drawString(this.title, x1 + 40, y1 + 4, -1, false);
    	mc.fontRenderer.drawString(this.message, x1 + 40, y1 + 14, -1, false);
    }

    public boolean shouldDelete() {
        return isFinished() && this.animationX == width;
    }

    public float getHeight() {
        return height;
    }

    private boolean isFinished() {
        return timer.elapsed(delay);
    }
}
