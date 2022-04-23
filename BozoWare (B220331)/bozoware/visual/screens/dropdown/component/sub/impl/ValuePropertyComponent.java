// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.dropdown.component.sub.impl;

import bozoware.base.BozoWare;
import net.minecraft.client.gui.Gui;
import bozoware.impl.module.visual.HUD;
import bozoware.base.util.visual.RenderUtil;
import bozoware.base.util.misc.MathUtil;
import bozoware.visual.screens.dropdown.component.sub.ModuleButtonComponent;
import bozoware.impl.property.ValueProperty;
import bozoware.visual.screens.dropdown.component.Component;

public class ValuePropertyComponent extends Component
{
    private final ValueProperty property;
    private final ModuleButtonComponent parent;
    private int offset;
    private double sliderAnimation;
    private boolean isSliding;
    
    public ValuePropertyComponent(final ValueProperty property, final ModuleButtonComponent parent, final int offset) {
        this.property = property;
        this.parent = parent;
        this.offset = offset;
    }
    
    @Override
    public void onDrawScreen(final int mouseX, final int mouseY) {
        final double x = this.parent.getParentFrame().getX();
        final double y = this.parent.getParentFrame().getY() + this.offset;
        if (this.isSliding && this.isMouseOverSlider(mouseX, mouseY)) {
            final double max = this.property.getMaximumValue().doubleValue();
            final double min = this.property.getMinimumValue().doubleValue();
            final double mousePercent = (mouseX - x) / 115.0;
            final double valueToSet = MathUtil.linearInterpolate(min, max, mousePercent);
            if (this.property.getPropertyValue() instanceof Double) {
                this.property.setPropertyValue(MathUtil.roundToPlace(valueToSet, 2));
            }
            if (this.property.getPropertyValue() instanceof Integer) {
                this.property.setPropertyValue((int)MathUtil.roundToPlace(valueToSet, 2));
            }
            if (this.property.getPropertyValue() instanceof Float) {
                this.property.setPropertyValue((float)MathUtil.roundToPlace(valueToSet, 2));
            }
            if (this.property.getPropertyValue() instanceof Long) {
                this.property.setPropertyValue((long)MathUtil.roundToPlace(valueToSet, 2));
            }
            if (this.property.getPropertyValue() instanceof Byte) {
                this.property.setPropertyValue((byte)MathUtil.roundToPlace(valueToSet, 2));
            }
        }
        final double sliderRectPercentage = (this.property.getPropertyValue().doubleValue() - this.property.getMinimumValue().doubleValue()) / (this.property.getMaximumValue().doubleValue() - this.property.getMinimumValue().doubleValue()) * (this.parent.getParentFrame().frameWidth - 2);
        if (this.isSliding) {
            this.sliderAnimation = RenderUtil.animate(sliderRectPercentage, this.sliderAnimation, 0.04);
        }
        else {
            this.sliderAnimation = sliderRectPercentage;
        }
        Gui.drawRectWithWidth(x + 1.0, (float)y + 1.0f, this.sliderAnimation, 12.0, HUD.getInstance().bozoColor);
        String currentValue = null;
        if (this.property.getPropertyValue() instanceof Integer || this.property.getPropertyValue() instanceof Long) {
            currentValue = this.property.getPropertyValue().toString();
        }
        else if (this.property.getPropertyValue() instanceof Float || this.property.getPropertyValue() instanceof Double) {
            currentValue = String.format("%.1f", this.property.getPropertyValue().doubleValue());
        }
        this.getFontRenderer().drawStringWithShadow(this.property.getPropertyLabel(), x + 3.0, y + 4.0, -1);
        this.getFontRenderer().drawStringWithShadow("§7" + currentValue, x + 112.0 - BozoWare.getInstance().getFontManager().smallFontRenderer.getStringWidth(currentValue), y + 4.0, -1);
        super.onDrawScreen(mouseX, mouseY);
    }
    
    @Override
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isMouseOverSlider(mouseX, mouseY) && mouseButton == 0) {
            this.isSliding = true;
        }
    }
    
    @Override
    public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        this.isSliding = false;
    }
    
    @Override
    public void setOffset(final int offset) {
        this.offset = offset;
    }
    
    @Override
    public boolean isHidden() {
        return this.property.isHidden();
    }
    
    private boolean isMouseOverSlider(final int mouseX, final int mouseY) {
        final double x = this.parent.getParentFrame().getX();
        final double y = this.parent.getParentFrame().getY() + this.offset;
        return mouseX >= x && mouseX <= x + 115.0 && mouseY >= y && mouseY <= y + 14.0;
    }
}
