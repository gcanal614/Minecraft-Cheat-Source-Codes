// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.ot.component.impl.sub;

import bozoware.visual.screens.ot.component.impl.ModuleButtonComponent;
import org.lwjgl.opengl.GL11;
import bozoware.base.util.visual.RenderUtil;
import java.util.function.Consumer;
import java.util.function.Supplier;
import bozoware.visual.screens.ot.component.OTComponent;

public class OTCheckboxComponent extends OTComponent
{
    private final Supplier<Boolean> supplier;
    private final Consumer<Boolean> consumer;
    private final String checkboxLabel;
    
    public OTCheckboxComponent(final OTComponent parentComponent, final String checkboxLabel, final Supplier<Boolean> supplier, final Consumer<Boolean> consumer, final double xPosition, final double yPosition, final double width, final double height) {
        super(parentComponent, xPosition, yPosition, width, height);
        this.supplier = supplier;
        this.consumer = consumer;
        this.checkboxLabel = checkboxLabel;
    }
    
    @Override
    public void onDrawScreen(final int mouseX, final int mouseY) {
        final double x = this.getParentComponent().getParentComponent().getParentComponent().getXPosition() + this.getXPosition();
        final double y = this.getParentComponent().getParentComponent().getParentComponent().getYPosition() + this.getYPosition();
        RenderUtil.drawSmoothRoundedRect((float)x, (float)y, (float)(x + 10.0), (float)(y + 10.0), 3.0f, this.isHovering(mouseX, mouseY) ? -12566464 : -13619152);
        RenderUtil.setColor(-7303024);
        this.getDefaultFontRenderer().drawString(this.checkboxLabel, x + 15.0, y + 2.2, -7303024);
        if (this.supplier.get()) {
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glLineWidth(2.0f);
            GL11.glEnable(2848);
            GL11.glBlendFunc(770, 771);
            GL11.glBegin(3);
            RenderUtil.setColor(-1);
            GL11.glVertex2d(x + 1.0, y + 5.0);
            GL11.glVertex2d(x + 3.0, y + 9.0);
            GL11.glVertex2d(x + 8.0, y + 2.0);
            GL11.glEnd();
            GL11.glEnable(3553);
        }
    }
    
    @Override
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHovering(mouseX, mouseY) && mouseButton == 0) {
            this.consumer.accept(!this.supplier.get());
        }
    }
    
    @Override
    public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    private boolean isHovering(final int mouseX, final int mouseY) {
        final double x = this.getParentComponent().getParentComponent().getParentComponent().getXPosition() + this.getXPosition();
        final double y = this.getParentComponent().getParentComponent().getParentComponent().getYPosition() + this.getYPosition() + ((ModuleButtonComponent)this.getParentComponent()).scrollValue;
        return mouseX >= x && mouseX <= x + 10.0 && mouseY >= y && mouseY <= y + 10.0;
    }
    
    @Override
    public double getHeight() {
        return 16.0;
    }
    
    @Override
    public void onKeyTyped(final int typedKey) {
    }
}
