// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.ot.component.impl;

import java.awt.Color;
import bozoware.base.util.visual.RenderUtil;
import bozoware.base.BozoWare;
import bozoware.visual.screens.ot.component.OTComponent;

public class TabSelectorComponent extends OTComponent
{
    private final String tabName;
    private final String tabIcon;
    public double alphaAnimation;
    public double alphaTarget;
    
    public TabSelectorComponent(final OTComponent parentComponent, final String tabName, final String tabIcon, final double xPosition, final double yPosition, final double width, final double height) {
        super(parentComponent, xPosition, yPosition, width, height);
        this.alphaAnimation = 127.0;
        this.tabName = tabName;
        this.tabIcon = tabIcon;
    }
    
    @Override
    public void onDrawScreen(final int mouseX, final int mouseY) {
        final double posX = this.getParentComponent().getXPosition() + this.getXPosition();
        final double posY = this.getParentComponent().getYPosition() + this.getYPosition();
        final boolean selected = BozoWare.getInstance().getGuiOTUIScreen().currentTabComponent == this;
        if (!selected) {
            this.alphaTarget = 120.0;
        }
        this.alphaAnimation = RenderUtil.animate(this.alphaTarget, this.alphaAnimation, 0.03);
        this.getIconsFontRenderer().drawStringWithShadow(this.tabIcon, posX, posY + 0.5, new Color(255, 255, 255, (int)this.alphaAnimation).getRGB());
        this.getDefaultFontRenderer().drawStringWithShadow(this.tabName, posX + 11.0, posY, new Color(255, 255, 255, (int)this.alphaAnimation).getRGB());
        if (selected) {
            RenderUtil.startScissor();
            RenderUtil.scissor(this.getParentComponent().getXPosition(), this.getParentComponent().getYPosition() + 36.0, this.getParentComponent().getWidth(), this.getParentComponent().getHeight() - 36.0);
            this.getChildrenComponents().forEach(child -> child.onDrawScreen(mouseX, mouseY));
            RenderUtil.endScissor();
        }
    }
    
    @Override
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        final boolean selected = BozoWare.getInstance().getGuiOTUIScreen().currentTabComponent == this;
        if (this.isHovering(mouseX, mouseY) && mouseButton == 0) {
            BozoWare.getInstance().getGuiOTUIScreen().currentTabComponent = this;
            BozoWare.getInstance().getGuiOTUIScreen().currentModule = this.getChildrenComponents().get(0);
            this.alphaTarget = 255.0;
        }
        if (selected) {
            this.getChildrenComponents().forEach(child -> child.onMouseClicked(mouseX, mouseY, mouseButton));
        }
    }
    
    @Override
    public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        final boolean selected = BozoWare.getInstance().getGuiOTUIScreen().currentTabComponent == this;
        if (selected) {
            this.getChildrenComponents().forEach(child -> child.onMouseReleased(mouseX, mouseY, mouseButton));
        }
    }
    
    @Override
    public void onKeyTyped(final int typedKey) {
    }
    
    @Override
    public void handleMouseInput() {
        final boolean selected = BozoWare.getInstance().getGuiOTUIScreen().currentTabComponent == this;
        if (selected) {
            this.getChildrenComponents().forEach(OTComponent::handleMouseInput);
        }
    }
    
    public boolean isHovering(final int mouseX, final int mouseY) {
        final double posX = this.getParentComponent().getXPosition() + this.getXPosition();
        final double posY = this.getParentComponent().getYPosition() + this.getYPosition();
        return mouseX >= posX && mouseX <= posX + 11.0 + this.getDefaultFontRenderer().getStringWidth(this.tabName) && mouseY >= posY && mouseY <= posY + 10.0;
    }
}
