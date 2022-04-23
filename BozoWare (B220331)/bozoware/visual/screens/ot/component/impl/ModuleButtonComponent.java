// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.ot.component.impl;

import org.lwjgl.input.Mouse;
import net.minecraft.client.renderer.GlStateManager;
import bozoware.base.util.visual.GLDraw;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import bozoware.base.util.visual.RenderUtil;
import bozoware.base.BozoWare;
import bozoware.base.module.Module;
import bozoware.visual.screens.ot.component.OTComponent;

public class ModuleButtonComponent extends OTComponent
{
    private final Module module;
    private double alphaTarget;
    private double alphaAnimation;
    private double childrenHeight;
    public double scrollValue;
    public double scrollAnimation;
    private boolean completedAnimation;
    
    public ModuleButtonComponent(final OTComponent parentComponent, final Module module, final double xPosition, final double yPosition, final double width, final double height) {
        super(parentComponent, xPosition, yPosition, width, height);
        this.module = module;
        this.alphaAnimation = (module.isModuleToggled() ? 255.0 : 127.0);
        this.alphaTarget = (module.isModuleToggled() ? 255.0 : 127.0);
    }
    
    @Override
    public void onDrawScreen(final int mouseX, final int mouseY) {
        final double x = this.getParentComponent().getParentComponent().getXPosition() + this.getXPosition();
        final double y = this.getParentComponent().getParentComponent().getYPosition() + this.getYPosition();
        final boolean selected = BozoWare.getInstance().getGuiOTUIScreen().currentModule == this;
        if (!this.module.isModuleToggled()) {
            this.alphaTarget = 127.0;
        }
        else {
            this.alphaTarget = 255.0;
        }
        this.alphaAnimation = RenderUtil.animate(this.alphaTarget, this.alphaAnimation, 0.03);
        this.getDefaultFontRenderer().drawStringWithShadow(this.module.getModuleName(), x + 2.0, y, new Color(255, 255, 255, (int)this.alphaAnimation).getRGB());
        this.scrollAnimation = RenderUtil.animate(this.scrollValue, this.scrollAnimation, 0.06);
        this.completedAnimation = (Math.round(this.scrollAnimation) == Math.round(this.scrollValue));
        RenderUtil.drawSmoothRoundedRectWithWidth(this.getParentComponent().getParentComponent().getXPosition() + this.getParentComponent().getParentComponent().getWidth() - 10.0, this.getParentComponent().getParentComponent().getYPosition() + 40.0, 6.0, 205.0, 8.0f, -12566464);
        Gui.drawRectWithWidth(this.getParentComponent().getParentComponent().getXPosition() + this.getParentComponent().getParentComponent().getWidth() - 10.0, this.getParentComponent().getParentComponent().getYPosition() + 40.0 + 200.0 * (this.scrollValue / (-this.childrenHeight + 200.0)), 6.0, 20.0, Color.ORANGE.getRGB());
        if (selected) {
            GLDraw.glFilledEllipse(x - 3.0, y + 2.5, 8.0f, -1);
            final double normalizedScrollAnimation = this.completedAnimation ? ((double)Math.round(this.scrollAnimation)) : this.scrollAnimation;
            GlStateManager.translate(0.0, normalizedScrollAnimation, 0.0);
            this.getChildrenComponents().forEach(child -> child.onDrawScreen(mouseX, mouseY));
            GlStateManager.translate(0.0, -normalizedScrollAnimation, 0.0);
        }
    }
    
    @Override
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        final boolean selected = BozoWare.getInstance().getGuiOTUIScreen().currentModule == this;
        if (this.isHovering(mouseX, mouseY)) {
            if (mouseButton == 0) {
                this.module.toggleModule();
            }
            else if (mouseButton == 1) {
                BozoWare.getInstance().getGuiOTUIScreen().currentModule = this;
            }
        }
        if (selected) {
            this.getChildrenComponents().forEach(child -> child.onMouseClicked(mouseX, mouseY, mouseButton));
        }
    }
    
    @Override
    public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        final boolean selected = BozoWare.getInstance().getGuiOTUIScreen().currentModule == this;
        if (selected) {
            this.getChildrenComponents().forEach(child -> child.onMouseReleased(mouseX, mouseY, mouseButton));
        }
    }
    
    @Override
    public void onKeyTyped(final int typedKey) {
        final boolean selected = BozoWare.getInstance().getGuiOTUIScreen().currentModule == this;
        if (selected) {
            this.getChildrenComponents().forEach(child -> child.onKeyTyped(typedKey));
        }
    }
    
    @Override
    public void handleMouseInput() {
        this.scrollValue += Math.signum((float)Mouse.getEventDWheel()) * 15.0f;
        this.scrollValue = Math.min(0.0, Math.max(-this.childrenHeight + 200.0, this.scrollValue));
    }
    
    private boolean isHovering(final int mouseX, final int mouseY) {
        final double x = this.getParentComponent().getParentComponent().getXPosition() + this.getXPosition();
        final double y = this.getParentComponent().getParentComponent().getYPosition() + this.getYPosition();
        return mouseX >= x && mouseX <= x + this.getDefaultFontRenderer().getStringWidth(this.module.getModuleName()) && mouseY >= y && mouseY <= y + 10.0;
    }
    
    @Override
    public void addChild(final OTComponent component) {
        this.childrenHeight += component.getHeight();
        super.addChild(component);
    }
}
