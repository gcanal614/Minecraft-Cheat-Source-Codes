// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.dropdown.component.sub.impl;

import net.minecraft.client.renderer.GlStateManager;
import bozoware.base.BozoWare;
import net.minecraft.client.gui.Gui;
import bozoware.impl.property.MultiSelectEnumProperty;
import bozoware.visual.screens.dropdown.component.Component;

public class MultiSelectEnumPropertyComponent extends Component
{
    private int offset;
    private final MultiSelectEnumProperty property;
    private final Component parent;
    private boolean expanded;
    
    public MultiSelectEnumPropertyComponent(final MultiSelectEnumProperty<?> property, final Component parent, final int offset) {
        this.offset = offset;
        this.property = property;
        this.parent = parent;
    }
    
    @Override
    public void onDrawScreen(final int mouseX, final int mouseY) {
        final double x = this.parent.getParentFrame().getX();
        final double y = this.parent.getParentFrame().getY() + this.offset;
        Gui.drawRectWithWidth(x, y, 115.0, 14.0, this.isMouseHovering(mouseX, mouseY) ? -15724528 : -15395563);
        this.getFontRenderer().drawStringWithShadow(this.property.getPropertyLabel(), x + 3.0, y + 4.0, -1);
        final String suffixText = this.property.getSelectedValues().size() + " Selected";
        this.getFontRenderer().drawStringWithShadow(suffixText, x + 115.0 - BozoWare.getInstance().getFontManager().smallFontRenderer.getStringWidth(suffixText) - 8.0, y + 4.0, -6710887);
        if (this.expanded) {
            Gui.drawRectWithWidth(x + 5.0, y + 15.0, 105.0, this.property.getConstantEnumValues().length * 15, -14342875);
            for (int i = 0; i < this.property.getConstantEnumValues().length; ++i) {
                GlStateManager.color(1.0f, 1.0f, 1.0f);
                this.getFontRenderer().drawCenteredString(this.property.getConstantEnumValues()[i].name(), (float)(x + 57.0), (float)(y + 19.0 + i * 15), this.property.isSelected(i) ? -1 : -7303024);
            }
        }
    }
    
    @Override
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        final double x = this.parent.getParentFrame().getX();
        final double y = this.parent.getParentFrame().getY() + this.offset;
        if (this.isMouseHovering(mouseX, mouseY) && mouseButton == 1) {
            this.expanded = !this.expanded;
        }
        if (this.expanded) {
            for (int i = 0; i < this.property.getConstantEnumValues().length; ++i) {
                if (mouseX >= x + 5.0 && mouseY >= y + 19.0 + i * 15 && mouseY <= y + 33.0 + i * 15 && mouseX <= x + 110.0) {
                    this.property.setValue(i, MultiSelectEnumProperty.MultiOption.TOGGLE);
                }
            }
        }
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
    
    @Override
    public double getHeight() {
        return this.expanded ? (this.property.getConstantEnumValues().length * 15 + 16) : 14.0;
    }
    
    @Override
    public void setOffset(final int offset) {
        this.offset = offset;
    }
}
