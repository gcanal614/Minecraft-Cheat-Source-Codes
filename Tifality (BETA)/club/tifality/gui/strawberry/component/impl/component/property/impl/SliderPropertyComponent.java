/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.strawberry.component.impl.component.property.impl;

import club.tifality.gui.strawberry.component.Component;
import club.tifality.gui.strawberry.component.impl.component.property.PropertyComponent;
import club.tifality.module.impl.render.hud.Hud;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.Representation;
import club.tifality.utils.render.OGLUtils;
import java.awt.Color;
import java.math.BigDecimal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;

public final class SliderPropertyComponent
extends Component
implements PropertyComponent {
    private final DoubleProperty doubleProperty;
    private boolean sliding;

    public SliderPropertyComponent(Component parent, DoubleProperty property, int x, int y, int width, int height) {
        super(parent, property.getLabel(), x, y, width, height);
        this.doubleProperty = property;
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        super.drawComponent(scaledResolution, mouseX, mouseY);
        int x = this.getX();
        int y = this.getY();
        int width = this.getWidth();
        int height = this.getHeight();
        double min = this.doubleProperty.getMin();
        double max = this.doubleProperty.getMax();
        Double dValue = (Double)this.doubleProperty.getValue();
        Representation representation = this.doubleProperty.getRepresentation();
        boolean isInt = representation == Representation.INT || representation == Representation.MILLISECONDS;
        double value = isInt ? (double)dValue.intValue() : dValue;
        double sliderPercentage = (value - min) / (this.doubleProperty.getMax() - min);
        boolean hovered = this.isHovered(mouseX, mouseY);
        if (this.sliding) {
            if (hovered) {
                this.doubleProperty.setValue(MathHelper.clamp_double(this.roundToFirstDecimalPlace((double)(mouseX - x) * (max - min) / (double)width + min), min, max));
            } else {
                this.sliding = false;
            }
        }
        String name = this.getName();
        int middleHeight = this.getHeight() / 2;
        String valueString = isInt ? Integer.toString((int)value) : Double.toString(value);
        switch (representation) {
            case PERCENTAGE: {
                valueString = valueString + '%';
                break;
            }
            case MILLISECONDS: {
                valueString = valueString + "ms";
                break;
            }
            case DISTANCE: {
                valueString = valueString + 'm';
            }
        }
        float valueWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(valueString) + 2;
        float overflowWidth = (float)(Minecraft.getMinecraft().fontRendererObj.getStringWidth(name) + 3) - ((float)width - valueWidth);
        boolean needOverflowBox = overflowWidth > 0.0f;
        boolean showOverflowBox = hovered && needOverflowBox;
        boolean needScissorBox = needOverflowBox && !hovered;
        Gui.drawRect((float)x - (showOverflowBox ? overflowWidth : 0.0f), y, x + width + 1, y + height, this.getSecondaryBackgroundColor(hovered));
        Gui.drawRect(x - 2, y, x, y + height, (int)Hud.hudColor.get());
        Gui.drawRect((double)x, (double)(y + 13), (double)x + (double)width * sliderPercentage, (double)(y + height), new Color(255, 255, 255).getRGB());
        Gui.drawRect((double)x, (double)(y + 13), (double)x + (double)width * sliderPercentage - 2.0, (double)(y + height), (int)Hud.hudColor.get());
        if (needScissorBox) {
            OGLUtils.startScissorBox(scaledResolution, x, y, (int)((float)width - valueWidth - 4.0f), height);
        }
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(name, (float)(x + 2) - (showOverflowBox ? overflowWidth : 0.0f), y + middleHeight - 3, new Color(255, 255, 255).getRGB());
        if (needScissorBox) {
            OGLUtils.endScissorBox();
        }
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(valueString, (float)(x + width) - valueWidth, y + middleHeight - 3, new Color(255, 255, 255).getRGB());
    }

    private double roundToFirstDecimalPlace(double value) {
        double inc = this.doubleProperty.getIncrement();
        double halfOfInc = inc / 2.0;
        double floored = Math.floor(value / inc) * inc;
        if (value >= floored + halfOfInc) {
            return new BigDecimal(Math.ceil(value / inc) * inc).setScale(2, 4).doubleValue();
        }
        return new BigDecimal(floored).setScale(2, 4).doubleValue();
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        if (!this.sliding && button == 0 && this.isHovered(mouseX, mouseY)) {
            this.sliding = true;
        }
    }

    @Override
    public void onMouseRelease(int button) {
        this.sliding = false;
    }

    @Override
    public Property<?> getProperty() {
        return this.doubleProperty;
    }
}

