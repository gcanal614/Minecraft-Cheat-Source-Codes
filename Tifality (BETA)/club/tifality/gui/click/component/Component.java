/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.click.component;

import club.tifality.gui.click.ClickGui;
import club.tifality.module.impl.render.hud.Hud;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.gui.ScaledResolution;

public class Component {
    protected final List<Component> children = new ArrayList<Component>();
    private final Component parent;
    private final String name;
    private int x;
    private int y;
    private int width;
    private int height;

    public Component(Component parent, String name, int x, int y, int width, int height) {
        this.parent = parent;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Component getParent() {
        return this.parent;
    }

    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        for (Component child : this.children) {
            child.drawComponent(scaledResolution, mouseX, mouseY);
        }
        this.getChildren().sort(Comparator.comparing(Component::getName));
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

    public String getName() {
        return this.name;
    }

    public int getX() {
        Component familyMember = this.parent;
        int familyTreeX = this.x;
        while (familyMember != null) {
            familyTreeX += familyMember.x;
            familyMember = familyMember.parent;
        }
        return familyTreeX;
    }

    public void setX(int x) {
        this.x = x;
    }

    protected boolean isHovered(int mouseX, int mouseY) {
        int y;
        int x = this.getX();
        return mouseX >= x && mouseY >= (y = this.getY()) && mouseX < x + this.getWidth() && mouseY < y + this.getHeight();
    }

    public int getY() {
        Component familyMember = this.parent;
        int familyTreeY = this.y;
        while (familyMember != null) {
            familyTreeY += familyMember.y;
            familyMember = familyMember.parent;
        }
        return familyTreeY;
    }

    protected int getBackgroundColor(boolean hovered) {
        return hovered ? ClickGui.getInstance().getPalette().getHoveredBackgroundColor().getRGB() : ClickGui.getInstance().getPalette().getPanelBackgroundColor().getRGB();
    }

    protected int getBackgroundEnabledColor(boolean enabled) {
        return enabled ? Hud.hudColor.get().intValue() : ClickGui.getInstance().getPalette().getPanelBackgroundColor().getRGB();
    }

    protected int getSecondaryBackgroundColor(boolean hovered) {
        return hovered ? ClickGui.getInstance().getPalette().getHoveredSecondaryBackgroundColor().getRGB() : ClickGui.getInstance().getPalette().getSecondaryBackgroundColor().getRGB();
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Component> getChildren() {
        return this.children;
    }
}

