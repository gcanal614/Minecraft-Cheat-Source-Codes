/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.clickgui.defaultgui.components;

import de.fanta.Client;
import de.fanta.clickgui.defaultgui.components.ConfigButton;
import de.fanta.utils.Colors;
import de.fanta.utils.RenderUtil;
import java.io.File;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ConfigBox {
    private File dir;
    public float x;
    public float y;
    public float dragX;
    public float dragY;
    public float lastDragX;
    public float lastDragY;
    public float scroll;
    public boolean isDragging;
    public ArrayList<ConfigButton> buttons;
    private float buttonsHeight;

    public ConfigBox(float x, float y) {
        this.dir = new File(Minecraft.getMinecraft().mcDataDir + "/" + Client.INSTANCE.name + "/" + "configs" + "/");
        this.buttons = new ArrayList();
        this.x = x;
        this.y = y;
        if (this.dir.exists() && this.dir.listFiles() != null) {
            float yOff = 16.0f;
            File[] fileArray = this.dir.listFiles();
            int n = fileArray.length;
            int n2 = 0;
            while (n2 < n) {
                File file = fileArray[n2];
                this.buttons.add(new ConfigButton(this, file, x + 2.0f, y + yOff));
                yOff += 12.5f;
                ++n2;
            }
            this.buttonsHeight = yOff;
        } else {
            this.dir.mkdir();
        }
    }

    public void drawConfigBox(float mouseX, float mouseY) {
        if (this.isDragging) {
            this.dragX = mouseX - this.lastDragX;
            this.dragY = mouseY - this.lastDragY;
        }
        RenderUtil.rectangle(this.x + this.dragX, this.y + this.dragY, this.x + this.dragX + 120.0f, this.y + this.dragY + 135.0f, Colors.getColor(40, 40, 40, 200));
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Config Menu", this.x + this.dragX + 4.5f, this.y + this.dragY + 2.5f, -1);
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("__________", this.x + this.dragX + 4.5f, this.y + this.dragY + 2.5f, -1);
        RenderUtil.rectangle(this.x + this.dragX + 1.0f, this.y + this.dragY + 16.0f, this.x + this.dragX + 119.0f, this.y + this.dragY + 134.0f, Colors.getColor(25, 25, 25, 25));
        if (mouseX >= this.x + this.dragX && mouseY >= this.y + this.dragY + 16.0f && mouseX <= this.x + this.dragX + 119.0f && mouseY <= this.y + this.dragY + 134.0f) {
            double mouseWheel = (double)Mouse.getDWheel() / 20.0;
            if (Mouse.getEventDWheel() != 0) {
                this.scroll = (float)((double)this.scroll - mouseWheel);
                float diff = this.buttonsHeight - 132.5f;
                this.scroll = MathHelper.clamp_float(this.scroll, Math.min(0.0f, -diff), 0.0f);
            }
        }
        GlStateManager.pushMatrix();
        RenderUtil.scissorBox(this.x + this.dragX, this.y + this.dragY + 17.0f, this.x + this.dragX + 119.0f, this.y + this.dragY + 134.0f);
        GL11.glEnable((int)3089);
        this.buttons.forEach(button -> button.drawButton(mouseX, mouseY));
        GL11.glDisable((int)3089);
        GlStateManager.popMatrix();
    }

    public void configBoxClicked(float mouseX, float mouseY, int mouseButton) {
        boolean hovering;
        boolean bl = hovering = mouseX >= this.x + this.dragX && mouseY >= this.y + this.dragY && mouseX <= this.x + this.dragX + 120.0f && mouseY <= this.y + this.dragY + 15.0f;
        if (mouseButton == 0) {
            if (hovering) {
                this.isDragging = true;
                this.lastDragX = mouseX - this.dragX;
                this.lastDragY = mouseY - this.dragY;
            } else {
                this.buttons.forEach(button -> button.buttonClicked(mouseX, mouseY, mouseButton));
            }
        }
    }

    public void configBoxReleased(float mouseX, float mouseY, int state) {
        this.isDragging = false;
        this.buttons.forEach(button -> button.buttonReleased(mouseX, mouseY, state));
    }
}

