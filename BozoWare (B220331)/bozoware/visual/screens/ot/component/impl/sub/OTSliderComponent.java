// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.ot.component.impl.sub;

import bozoware.visual.screens.ot.component.impl.ModuleButtonComponent;
import bozoware.base.util.misc.MathUtil;
import java.awt.Color;
import bozoware.base.util.visual.RenderUtil;
import java.util.function.Consumer;
import java.util.function.Supplier;
import bozoware.visual.screens.ot.component.OTComponent;

public class OTSliderComponent extends OTComponent
{
    private final Supplier<Number> valueSupplier;
    private final Supplier<Number> maximumSupplier;
    private final Supplier<Number> minimumSupplier;
    private final Consumer<Number> consumer;
    private final String sliderLabel;
    private boolean active;
    private double sliderValueLabelAnimation;
    private double sliderValueLabelTarget;
    
    public OTSliderComponent(final OTComponent parentComponent, final Supplier<Number> supplier, final Supplier<Number> maximumSupplier, final Supplier<Number> minimumSupplier, final Consumer<Number> consumer, final String sliderLabel, final double xPosition, final double yPosition, final double width, final double height) {
        super(parentComponent, xPosition, yPosition, width, height);
        this.valueSupplier = supplier;
        this.maximumSupplier = maximumSupplier;
        this.minimumSupplier = minimumSupplier;
        this.consumer = consumer;
        this.sliderLabel = sliderLabel;
    }
    
    @Override
    public void onDrawScreen(final int mouseX, final int mouseY) {
        final double x = this.getParentComponent().getParentComponent().getParentComponent().getXPosition() + this.getXPosition();
        final double y = this.getParentComponent().getParentComponent().getParentComponent().getYPosition() + this.getYPosition();
        RenderUtil.drawSmoothRoundedRect((float)x - 0.5f, (float)y + 10.5f, (float)(x + 195.5), (float)(y + 18.5), 4.0f, -13290187);
        RenderUtil.drawSmoothRoundedRect((float)x, (float)y + 11.0f, (float)(x + 195.0), (float)(y + 18.0), 4.0f, -14342875);
        final float sliderRectPercentage = 195.5f * ((this.valueSupplier.get().floatValue() - this.minimumSupplier.get().floatValue()) / (this.maximumSupplier.get().floatValue() - this.minimumSupplier.get().floatValue()));
        final float indicatorPercentage = 190.5f * ((this.valueSupplier.get().floatValue() - this.minimumSupplier.get().floatValue()) / (this.maximumSupplier.get().floatValue() - this.minimumSupplier.get().floatValue()));
        if (sliderRectPercentage >= 1.0f) {
            RenderUtil.drawSmoothRoundedRect((float)(x - 0.5), (float)(y + 10.5), (float)(x + sliderRectPercentage), (float)(y + 18.5), 4.0f, this.isHovering(mouseX, mouseY) ? Color.ORANGE.brighter().getRGB() : Color.ORANGE.getRGB());
        }
        RenderUtil.drawSmoothRoundedRect((float)(x + indicatorPercentage), (float)y + 8.0f, (float)(x + indicatorPercentage + 6.0), (float)(y + 21.0), 5.0f, -1);
        RenderUtil.setColor(-7303024);
        this.getDefaultFontRenderer().drawString(this.sliderLabel, x, y, -7303024);
        if (this.active && this.isHovering(mouseX, mouseY)) {
            final double mousePercent = (mouseX - (x - 0.5)) / 195.5;
            final double newValue = MathUtil.linearInterpolate(this.minimumSupplier.get().doubleValue(), this.maximumSupplier.get().doubleValue(), mousePercent);
            if (this.valueSupplier.get() instanceof Double) {
                this.consumer.accept(MathUtil.roundToPlace(newValue, 2));
            }
            if (this.valueSupplier.get() instanceof Integer) {
                this.consumer.accept((int)MathUtil.roundToPlace(newValue, 2));
            }
            if (this.valueSupplier.get() instanceof Float) {
                this.consumer.accept((float)MathUtil.roundToPlace(newValue, 2));
            }
            if (this.valueSupplier.get() instanceof Long) {
                this.consumer.accept((long)MathUtil.roundToPlace(newValue, 2));
            }
        }
        this.sliderValueLabelAnimation = RenderUtil.animate(this.sliderValueLabelTarget, this.sliderValueLabelAnimation, 0.03);
        this.sliderValueLabelAnimation = Math.max(21.0, Math.min(this.sliderValueLabelAnimation, 127.0));
        this.getDefaultFontRenderer().drawString(String.valueOf(MathUtil.roundToPlace(this.valueSupplier.get().doubleValue(), 2)), x + this.getDefaultFontRenderer().getStringWidth(this.sliderLabel) + 3.0, y, new Color((int)this.sliderValueLabelAnimation, (int)this.sliderValueLabelAnimation, (int)this.sliderValueLabelAnimation).getRGB());
        if (!this.active) {
            this.sliderValueLabelTarget = 21.0;
        }
    }
    
    @Override
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHovering(mouseX, mouseY) && mouseButton == 0) {
            this.active = true;
            this.sliderValueLabelTarget = 127.0;
        }
    }
    
    @Override
    public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        this.active = false;
    }
    
    @Override
    public void onKeyTyped(final int typedKey) {
    }
    
    @Override
    public double getHeight() {
        return 26.0;
    }
    
    private boolean isHovering(final int mouseX, final int mouseY) {
        final double x = this.getParentComponent().getParentComponent().getParentComponent().getXPosition() + this.getXPosition();
        final double y = this.getParentComponent().getParentComponent().getParentComponent().getYPosition() + this.getYPosition() + ((ModuleButtonComponent)this.getParentComponent()).scrollValue;
        return mouseX >= x - 0.5 && mouseX <= x + 195.5 && mouseY >= y + 10.5 && mouseY <= y + 18.5;
    }
}
