/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.clickgui.defaultgui.components;

import de.fanta.Client;
import de.fanta.clickgui.defaultgui.components.FontButton;
import de.fanta.utils.Colors;
import de.fanta.utils.RenderUtil;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class FontBox {
    public float x;
    public float y;
    public float dragX;
    public float dragY;
    public float lastDragX;
    public float lastDragY;
    public float scroll;
    public boolean isDragging;
    public ArrayList<FontButton> buttons = new ArrayList();
    private float buttonsHeight;

    public FontBox(float x, float y) {
        this.x = x;
        this.y = y;
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        float yOff = 16.0f;
        String[] stringArray = fontNames;
        int n = fontNames.length;
        int n2 = 0;
        while (n2 < n) {
            String font = stringArray[n2];
            this.buttons.add(new FontButton(this, font, x + 2.0f, y + yOff));
            yOff += 12.5f;
            ++n2;
        }
        this.buttonsHeight = yOff;
    }

    public void drawConfigBox(float mouseX, float mouseY) {
        if (this.isDragging) {
            this.dragX = mouseX - this.lastDragX;
            this.dragY = mouseY - this.lastDragY;
        }
        Client.blurHelper.blur2(this.x + this.dragX, this.y + this.dragY, this.x + this.dragX + 200.0f, this.y + this.dragY + 135.0f, 100.0f);
        RenderUtil.rectangle(this.x + this.dragX, this.y + this.dragY, this.x + this.dragX + 200.0f, this.y + this.dragY + 135.0f, Colors.getColor(40, 40, 40, 200));
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Font Menu", this.x + this.dragX + 4.5f, this.y + this.dragY + 2.5f, -1);
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("________", this.x + this.dragX + 4.5f, this.y + this.dragY + 2.5f, -1);
        RenderUtil.rectangle(this.x + this.dragX + 1.0f, this.y + this.dragY + 16.0f, this.x + this.dragX + 199.0f, this.y + this.dragY + 134.0f, Colors.getColor(25, 25, 25, 25));
        if (mouseX >= this.x + this.dragX && mouseY >= this.y + this.dragY + 16.0f && mouseX <= this.x + this.dragX + 199.0f && mouseY <= this.y + this.dragY + 134.0f) {
            double mouseWheel = -((double)Mouse.getDWheel() / 20.0);
            if (Mouse.getEventDWheel() != 0) {
                this.scroll = (float)((double)this.scroll - mouseWheel);
                float diff = this.buttonsHeight - 132.5f;
                this.scroll = MathHelper.clamp_float(this.scroll, Math.min(0.0f, -diff), 0.0f);
            }
        }
        GlStateManager.pushMatrix();
        RenderUtil.scissorBox(this.x + this.dragX, this.y + this.dragY + 17.0f, this.x + this.dragX + 199.0f, this.y + this.dragY + 134.0f);
        GL11.glEnable((int)3089);
        this.buttons.forEach(button -> button.drawButton(mouseX, mouseY));
        GL11.glDisable((int)3089);
        GlStateManager.popMatrix();
    }

    public void configBoxClicked(float mouseX, float mouseY, int mouseButton) {
        boolean hovering;
        boolean bl = hovering = mouseX >= this.x + this.dragX && mouseY >= this.y + this.dragY && mouseX <= this.x + this.dragX + 200.0f && mouseY <= this.y + this.dragY + 15.0f;
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

