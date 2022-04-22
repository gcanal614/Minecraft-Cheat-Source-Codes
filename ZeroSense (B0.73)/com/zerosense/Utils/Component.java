package com.zerosense.Utils;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

public class Component {
    protected int x, y, width, height;
    protected Minecraft mc = Minecraft.getMinecraft();

    public Component(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getTop() {
        return y - height / 2;
    }

    public int getBottom() {
        return y + height / 2;
    }

    public int getRight() {
        return x + width / 2;
    }

    public int getLeft() {
        return x - width / 2;
    }

    public void draw(int mouseX, int mouseY) {

    }

    public boolean mouseOn(int mouseX, int mouseY) {
        if (mouseX > getLeft() && mouseX < getRight()) {
            if (mouseY > getTop() && mouseY < getBottom()) {
                return true;
            }
        }

        return false;
    }

    public boolean isPressed(int mouseX, int mouseY, int...buttons) {
        if (!mouseOn(mouseX, mouseY))
            return false;

        for (int button : buttons) {
            if (Mouse.isButtonDown(button))
                return true;
        }

        return false;
    }

    public void setSize(int width, int height) {
        setHeight(height);
        setWidth(width);
    }

    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void onClick(int mouseX, int mouseY, int mouseButton) {
    }
    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
    }
}
