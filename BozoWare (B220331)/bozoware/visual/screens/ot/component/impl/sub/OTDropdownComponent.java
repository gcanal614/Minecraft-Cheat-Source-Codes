// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.ot.component.impl.sub;

import bozoware.visual.screens.ot.component.impl.ModuleButtonComponent;
import bozoware.base.util.visual.RenderUtil;
import java.util.function.Supplier;
import bozoware.visual.screens.ot.component.OTComponent;

public class OTDropdownComponent extends OTComponent
{
    private final Supplier<Enum<?>[]> enumSupplier;
    private final Supplier<Enum<?>> currentEnumValueSupplier;
    private final Runnable changeValueListenerIncrement;
    private final Runnable changeValueListenerDecrement;
    private final String dropdownLabel;
    
    public OTDropdownComponent(final OTComponent parentComponent, final String dropdownLabel, final Supplier<Enum<?>[]> enumSupplier, final Supplier<Enum<?>> currentEnumValueSupplier, final Runnable changeValueListenerIncrement, final Runnable changeValueListenerDecrement, final double xPosition, final double yPosition, final double width, final double height) {
        super(parentComponent, xPosition, yPosition, width, height);
        this.enumSupplier = enumSupplier;
        this.currentEnumValueSupplier = currentEnumValueSupplier;
        this.dropdownLabel = dropdownLabel;
        this.changeValueListenerIncrement = changeValueListenerIncrement;
        this.changeValueListenerDecrement = changeValueListenerDecrement;
    }
    
    @Override
    public void onDrawScreen(final int mouseX, final int mouseY) {
        final double x = this.getParentComponent().getParentComponent().getParentComponent().getXPosition() + this.getXPosition();
        final double y = this.getParentComponent().getParentComponent().getParentComponent().getYPosition() + this.getYPosition();
        RenderUtil.setColor(-7303024);
        this.getDefaultFontRenderer().drawString(this.dropdownLabel, x, y, -7303024);
        RenderUtil.drawSmoothRoundedRect((float)x, (float)y + 10.0f, (float)(x + 190.0), (float)(y + 22.0), 4.0f, -11513776);
        RenderUtil.drawSmoothRoundedRect((float)x + 0.5f, (float)y + 10.5f, (float)(x + 189.5), (float)(y + 21.5), 4.0f, this.isHovering(mouseX, mouseY) ? -12566464 : -13619152);
        RenderUtil.setColor(-7303024);
        this.getDefaultFontRenderer().drawString(this.currentEnumValueSupplier.get().name(), x + 2.0, y + 13.0, -7303024);
    }
    
    @Override
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHovering(mouseX, mouseY)) {
            switch (mouseButton) {
                case 0: {
                    this.changeValueListenerIncrement.run();
                    break;
                }
                case 1: {
                    this.changeValueListenerDecrement.run();
                    break;
                }
            }
        }
    }
    
    @Override
    public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    @Override
    public void onKeyTyped(final int typedKey) {
    }
    
    private boolean isHovering(final int mouseX, final int mouseY) {
        final double x = this.getParentComponent().getParentComponent().getParentComponent().getXPosition() + this.getXPosition();
        final double y = this.getParentComponent().getParentComponent().getParentComponent().getYPosition() + this.getYPosition() + ((ModuleButtonComponent)this.getParentComponent()).scrollValue;
        return mouseX >= x && mouseX <= x + 189.5 && mouseY >= y + 10.0 && mouseY <= y + 22.0;
    }
    
    @Override
    public double getHeight() {
        return 28.0;
    }
}
