// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.dropdown.component;

import java.util.Collection;
import bozoware.base.util.visual.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import java.util.Iterator;
import bozoware.visual.screens.dropdown.component.sub.ModuleButtonComponent;
import bozoware.base.module.Module;
import bozoware.base.BozoWare;
import bozoware.visual.font.MinecraftFontRenderer;
import java.util.ArrayList;
import bozoware.base.module.ModuleCategory;

public class ModuleCategoryFrame
{
    private final ModuleCategory category;
    private boolean frameExpanded;
    private boolean isDraggingFrame;
    private final ArrayList<Component> childrenComponents;
    private int x;
    private int y;
    private int distX;
    private int distY;
    private double motionAnimationX;
    private double motionAnimationY;
    public int frameWidth;
    public int frameHeight;
    MinecraftFontRenderer skeetIcons;
    MinecraftFontRenderer arrowIcons;
    MinecraftFontRenderer basicIcons;
    
    public ModuleCategoryFrame(final ModuleCategory category, final int x, final int y) {
        this.frameExpanded = true;
        this.childrenComponents = new ArrayList<Component>();
        this.frameWidth = 115;
        this.frameHeight = 14;
        this.skeetIcons = BozoWare.getInstance().getFontManager().SkeetIcons;
        this.arrowIcons = BozoWare.getInstance().getFontManager().ArrowIcons;
        this.basicIcons = BozoWare.getInstance().getFontManager().BasicIcons;
        this.category = category;
        this.x = x;
        this.y = y;
        int buttonOffset = 14;
        for (final Module module : BozoWare.getInstance().getModuleManager().getModulesByCategory(category)) {
            this.addChildComponent(new ModuleButtonComponent(module, buttonOffset));
            buttonOffset += 14;
        }
    }
    
    public void onDrawScreen(final int mouseX, final int mouseY) {
        Gui.drawRectWithWidth(this.x - 1, this.y, this.frameWidth + 2, this.frameHeight, -15724528);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.category.categoryName, (float)(this.x + 3), (float)(this.y + 4), -1);
        final String string = this.category.categoryName.toString();
        switch (string) {
            case "Combat": {
                this.basicIcons.drawStringWithShadow("a", this.x + 100, this.y + 5, -1);
                break;
            }
            case "Player": {
                this.basicIcons.drawStringWithShadow("c", this.x + 100, this.y + 5, -1);
                break;
            }
            case "Movement": {
                this.basicIcons.drawStringWithShadow("b", this.x + 100, this.y + 5, -1);
                break;
            }
            case "Visual": {
                this.basicIcons.drawStringWithShadow("g", this.x + 100, this.y + 5, -1);
                break;
            }
            case "World": {
                this.basicIcons.drawStringWithShadow("d", this.x + 100, this.y + 5, -1);
                break;
            }
        }
        if (this.frameExpanded) {
            RenderUtil.drawRoundedRect((float)(this.x - 1), (float)(this.y + 13), (float)(this.x - 1 + this.frameWidth + 2), (float)(this.y + 14 + this.getFrameHeight() + 1), 2.0f, -15395563);
            RenderUtil.drawRoundedRect((float)(this.x - 1), (float)(this.y + 13), (float)(this.x - 1 + this.frameWidth + 2), (float)(this.y + 14 + this.getFrameHeight() + 1), 2.0f, -15724528);
        }
        else {
            RenderUtil.drawRoundedRect((float)(this.x - 1), (float)(this.y + 13), (float)(this.x - 1 + this.frameWidth + 2), (float)(this.y + 14), 2.0f, -15395563);
            RenderUtil.drawRoundedRect((float)(this.x - 1), (float)(this.y + 13), (float)(this.x - 1 + this.frameWidth + 2), (float)(this.y + 14), 2.0f, -15724528);
        }
        if (this.isDraggingFrame) {
            this.motionAnimationX = RenderUtil.animate(mouseX - this.distX, this.motionAnimationX, 0.04);
            this.motionAnimationY = RenderUtil.animate(mouseY - this.distY, this.motionAnimationY, 0.04);
            this.x = (int)this.motionAnimationX;
            this.y = (int)this.motionAnimationY;
        }
        if (this.frameExpanded) {
            this.childrenComponents.forEach(childComponent -> childComponent.onDrawScreen(mouseX, mouseY));
        }
    }
    
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHoveringFrame(mouseX, mouseY)) {
            if (mouseButton == 0) {
                this.distX = mouseX - this.x;
                this.distY = mouseY - this.y;
                this.isDraggingFrame = true;
            }
            if (mouseButton == 1) {
                this.frameExpanded = !this.frameExpanded;
                for (final Component childrenComponent : this.childrenComponents) {
                    if (childrenComponent instanceof ModuleButtonComponent) {
                        ((ModuleButtonComponent)childrenComponent).expanded = false;
                    }
                }
                this.childrenComponents.forEach(Component::onAnimationEvent);
            }
        }
        if (this.frameExpanded) {
            this.childrenComponents.forEach(childComponent -> childComponent.onMouseClicked(mouseX, mouseY, mouseButton));
        }
    }
    
    public void onGuiClosed() {
        this.childrenComponents.forEach(Component::onGuiClosed);
    }
    
    public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        this.isDraggingFrame = false;
        if (this.frameExpanded) {
            this.childrenComponents.forEach(childComponent -> childComponent.onMouseReleased(mouseX, mouseY, mouseButton));
        }
    }
    
    public void onKeyTyped(final char typedKey) {
        if (this.frameExpanded) {
            this.childrenComponents.forEach(childComponent -> childComponent.onKeyTyped(typedKey));
        }
    }
    
    public ArrayList<Component> getChildrenComponents() {
        return this.childrenComponents;
    }
    
    public void addChildComponent(final Component component) {
        component.setParentFrame(this);
        this.childrenComponents.add(component);
    }
    
    public void updateComponents() {
        int off = this.frameHeight;
        for (final Component comp : this.childrenComponents) {
            comp.setOffset(off);
            off += (int)comp.getHeight();
        }
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    private boolean isHoveringFrame(final int mouseX, final int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.frameWidth && mouseY >= this.y && mouseY <= this.y + this.frameHeight;
    }
    
    private int getFrameHeight() {
        int initialHeight = this.childrenComponents.size() * this.frameHeight;
        for (final Component component : this.childrenComponents) {
            if (component instanceof ModuleButtonComponent && ((ModuleButtonComponent)component).expanded) {
                final ArrayList<Component> visibleCheatSubs = new ArrayList<Component>(((ModuleButtonComponent)component).subComponents);
                visibleCheatSubs.removeIf(Component::isHidden);
                for (final Component sub : visibleCheatSubs) {
                    initialHeight += (int)sub.getHeight();
                }
            }
        }
        return initialHeight;
    }
    
    public boolean isFrameExpanded() {
        return this.frameExpanded;
    }
}
