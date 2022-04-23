/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.csgo.component;

import club.tifality.utils.render.LockedResolution;
import java.util.ArrayList;
import java.util.List;

public class Component {
    protected final List<Component> children = new ArrayList<Component>();
    private final Component parent;
    private float x;
    private float y;
    private float width;
    private float height;

    public Component(Component parent, float x, float y, float width, float height) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Component getParent() {
        return this.parent;
    }

    public void addChild(Component child) {
        this.children.add(child);
    }

    public void drawComponent(LockedResolution lockedResolution, int mouseX, int mouseY) {
        for (Component child : this.children) {
            child.drawComponent(lockedResolution, mouseX, mouseY);
        }
    }

    public void onMouseClick(int mouseX, int mouseY, int button) {
        for (Component child : this.children) {
            child.onMouseClick(mouseX, mouseY, button);
        }
    }

    public void onMouseRelease(int button) {
        for (Component child : this.children) {
            child.onMouseRelease(button);
        }
    }

    public void onKeyPress(int keyCode) {
        for (Component child : this.children) {
            child.onKeyPress(keyCode);
        }
    }

    public float getX() {
        Component familyMember = this.parent;
        float familyTreeX = this.x;
        while (familyMember != null) {
            familyTreeX += familyMember.x;
            familyMember = familyMember.parent;
        }
        return familyTreeX;
    }

    public void setX(float x) {
        this.x = x;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isHovered(int mouseX, int mouseY) {
        float f;
        float f2;
        float x = this.getX();
        if (!((float)mouseX >= f2)) return false;
        float y = this.getY();
        if (!((float)mouseY >= f)) return false;
        if (!((float)mouseX <= x + this.getWidth())) return false;
        if (!((float)mouseY <= y + this.getHeight())) return false;
        return true;
    }

    public float getY() {
        Component familyMember = this.parent;
        float familyTreeY = this.y;
        while (familyMember != null) {
            familyTreeY += familyMember.y;
            familyMember = familyMember.parent;
        }
        return familyTreeY;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public List<Component> getChildren() {
        return this.children;
    }
}

