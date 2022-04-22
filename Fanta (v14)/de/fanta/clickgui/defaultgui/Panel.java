/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.clickgui.defaultgui;

import de.fanta.clickgui.defaultgui.components.CategoryButton;
import de.fanta.module.Module;
import de.fanta.utils.Colors;
import de.fanta.utils.RenderUtil;
import java.util.ArrayList;

public class Panel {
    private float x;
    private float y;
    public float dragX;
    public float dragY;
    private float lastDragX;
    private float lastDragY;
    public boolean isDragged;
    public ArrayList<CategoryButton> buttons;

    public Panel(float x, float y) {
        this.x = x;
        this.y = y;
        this.buttons = new ArrayList();
        float moduleY = 5.0f;
        Module.Type[] typeArray = Module.Type.values();
        int n = typeArray.length;
        int n2 = 0;
        while (n2 < n) {
            Module.Type type = typeArray[n2];
            this.buttons.add(new CategoryButton(type, this, x + 3.0f, y + moduleY));
            moduleY += 15.0f;
            ++n2;
        }
    }

    public void drawPanel(float mouseX, float mouseY) {
        if (this.isDragged) {
            this.dragX = mouseX - this.lastDragX;
            this.dragY = mouseY - this.lastDragY;
        }
        RenderUtil.rectangle(this.x + this.dragX, this.y + this.dragY, this.x + this.dragX + 325.0f, this.y + this.dragY + 215.0f, Colors.getColor(22, 22, 22, 255));
        RenderUtil.rectangle(this.x + this.dragX, this.y + this.dragY, this.x + this.dragX + 325.0f, this.y + this.dragY + 3.0f, Colors.getColor(17, 17, 17, 255));
        RenderUtil.rectangle(this.x + this.dragX + 50.0f, this.y + this.dragY + 3.0f, this.x + this.dragX + 51.0f, this.y + this.dragY + 215.0f, Colors.getColor(100, 100, 100, 255));
        this.buttons.forEach(button -> {
            float xOff;
            button.drawCateButton(mouseX, mouseY);
            if (button.isActive && (xOff = button.catePanel.lengthModule + 25.0f) > 25.0f) {
                RenderUtil.rectangle(this.x + this.dragX + xOff + 48.0f, this.y + this.dragY + 3.0f, this.x + this.dragX + xOff + 49.0f, this.y + this.dragY + 215.0f, Colors.getColor(100, 100, 100, 255));
            }
        });
    }

    public void panelClicked(float mouseX, float mouseY, int mouseButton) {
        boolean hovering;
        boolean bl = hovering = mouseX >= this.x + this.dragX && mouseY >= this.y + this.dragY && mouseX <= this.x + this.dragX + 325.0f && mouseY <= this.y + this.dragY + 3.0f;
        if (hovering && mouseButton == 0) {
            this.isDragged = true;
            this.lastDragX = mouseX - this.dragX;
            this.lastDragY = mouseY - this.dragY;
        }
        this.buttons.forEach(button -> button.cateButtonClicked(mouseX, mouseY, mouseButton));
    }

    public void panelReleased(float mouseX, float mouseY, int state) {
        if (this.isDragged) {
            this.isDragged = false;
        }
        this.buttons.forEach(button -> button.cateButtonReleased(mouseX, mouseY, state));
    }

    public void panelHandleInput() {
        this.buttons.forEach(button -> button.catePanel.categoryHandleInput());
    }
}

