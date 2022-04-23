// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.dropdown.component.sub.impl;

import bozoware.impl.module.visual.HUD;
import bozoware.base.util.visual.RenderUtil;
import bozoware.base.BozoWare;
import net.minecraft.client.gui.Gui;
import bozoware.impl.property.BooleanProperty;
import bozoware.visual.screens.dropdown.component.sub.ModuleButtonComponent;
import bozoware.visual.screens.dropdown.component.Component;

public class BooleanPropertyComponent extends Component
{
    private final ModuleButtonComponent parent;
    private final BooleanProperty property;
    private int offset;
    private double boolAnimation;
    
    public BooleanPropertyComponent(final BooleanProperty property, final ModuleButtonComponent parent, final int offset) {
        this.parent = parent;
        this.property = property;
        this.offset = offset;
    }
    
    @Override
    public void onDrawScreen(final int mouseX, final int mouseY) {
        final double x = this.parent.getParentFrame().getX();
        final double y = this.parent.getParentFrame().getY() + this.offset;
        Gui.drawRectWithWidth(x, y, 115.0, 14.0, this.isMouseHovering(mouseX, mouseY) ? -15724528 : -15395563);
        BozoWare.getInstance().getFontManager().mediumFontRenderer.drawStringWithShadow(this.property.getPropertyLabel(), x + 3.0, y + 4.0, -1);
        Gui.drawRectWithWidth(x + 115.0 - 28.0, y + 2.0, 26.0, 10.0, -1728053248);
        this.boolAnimation = RenderUtil.animate(this.property.getPropertyValue() ? 12 : 0, this.boolAnimation, 0.04);
        Gui.drawRectWithWidth(x + 115.0 - 27.0 + this.boolAnimation, y + 3.0, 12.0, 8.0, ((boolean)this.property.getPropertyValue()) ? HUD.getInstance().bozoColor : -14671840);
    }
    
    @Override
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isMouseHovering(mouseX, mouseY)) {
            this.property.setPropertyValue(!this.property.getPropertyValue());
        }
    }
    
    @Override
    public void setOffset(final int offset) {
        this.offset = offset;
    }
    
    @Override
    public boolean isHidden() {
        return this.property.isHidden();
    }
    
    private boolean isMouseHovering(final int mouseX, final int mouseY) {
        final double x = this.parent.getParentFrame().getX();
        final double y = this.parent.getParentFrame().getY() + this.offset;
        return mouseX >= x && mouseX <= x + 115.0 && mouseY >= y && mouseY <= y + 14.0;
    }
}
