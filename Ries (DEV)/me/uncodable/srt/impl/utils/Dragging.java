/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.utils;

public class Dragging {
    private float xPos;
    private float yPos;
    private float startX;
    private float startY;
    private boolean dragging;

    public Dragging(int initialXVal, int initialYVal) {
        this.xPos = initialXVal;
        this.yPos = initialYVal;
    }

    public void onDraw(int mouseX, int mouseY) {
        if (this.dragging) {
            this.xPos = (float)mouseX - this.startX;
            this.yPos = (float)mouseY - this.startY;
        }
    }

    public void onClick(int mouseX, int mouseY, int button, boolean canDrag) {
        if (button == 0 && canDrag) {
            this.dragging = true;
            this.startX = (int)((float)mouseX - this.xPos);
            this.startY = (int)((float)mouseY - this.yPos);
        }
    }

    public void onRelease(int button) {
        if (button == 0) {
            this.dragging = false;
        }
    }

    public float getXPos() {
        return this.xPos;
    }

    public float getYPos() {
        return this.yPos;
    }
}

