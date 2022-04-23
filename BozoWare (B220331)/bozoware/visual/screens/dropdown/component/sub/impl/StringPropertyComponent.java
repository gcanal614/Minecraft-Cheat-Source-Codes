// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.dropdown.component.sub.impl;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.Gui;
import bozoware.impl.property.StringProperty;
import bozoware.visual.screens.dropdown.component.Component;

public class StringPropertyComponent extends Component
{
    private int offset;
    private StringProperty property;
    private Component parent;
    private boolean active;
    
    public StringPropertyComponent(final StringProperty stringProperty, final Component parent, final int offset) {
        this.property = stringProperty;
        this.parent = parent;
        this.offset = offset;
    }
    
    @Override
    public void onDrawScreen(final int mouseX, final int mouseY) {
        final double x = this.parent.getParentFrame().getX();
        final double y = this.parent.getParentFrame().getY() + this.offset;
        Gui.drawRectWithWidth(x, y, 115.0, 27.0, -15395563);
        this.getFontRenderer().drawStringWithShadow(this.property.getPropertyLabel(), x + 3.0, y + 3.0, -1);
        Gui.drawRectWithWidth(x + 3.0, y + 14.0, 109.0, 12.0, this.isHovering(mouseX, mouseY) ? -13290187 : -14342875);
        if (this.active) {
            Gui.drawRectWithWidth(x + 4.0 + this.getFontRenderer().getStringWidth(this.property.getPropertyValue()), y + 22.0, 4.0, 2.0, -16777216);
        }
        this.getFontRenderer().drawStringWithShadow(this.property.getPropertyValue(), x + 4.0, y + 17.0, -1);
        super.onDrawScreen(mouseX, mouseY);
    }
    
    @Override
    public double getHeight() {
        return 27.0;
    }
    
    @Override
    public void onKeyTyped(final int typedKey) {
        if (this.active) {
            this.property.setPropertyValue(this.property.getPropertyValue() + Keyboard.getKeyName(Keyboard.getEventKey()).toLowerCase());
        }
        super.onKeyTyped(typedKey);
    }
    
    private boolean isHovering(final int mouseX, final int mouseY) {
        final double x = this.parent.getParentFrame().getX();
        final double y = this.parent.getParentFrame().getY() + this.offset;
        return mouseX >= x + 3.0 && mouseX <= x + 112.0 && mouseY >= y + 14.0 && mouseY <= y + 26.0;
    }
    
    @Override
    public void setOffset(final int offset) {
        this.offset = offset;
    }
}
