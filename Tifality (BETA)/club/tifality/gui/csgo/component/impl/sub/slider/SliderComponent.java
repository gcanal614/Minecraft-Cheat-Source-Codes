/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.csgo.component.impl.sub.slider;

import club.tifality.gui.csgo.SkeetUI;
import club.tifality.gui.csgo.component.ButtonComponent;
import club.tifality.gui.csgo.component.Component;
import club.tifality.gui.csgo.component.PredicateComponent;
import club.tifality.property.impl.Representation;
import club.tifality.utils.render.LockedResolution;
import club.tifality.utils.render.RenderingUtils;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public abstract class SliderComponent
extends ButtonComponent
implements PredicateComponent {
    private boolean sliding;

    public SliderComponent(Component parent, float x, float y, float width, float height) {
        super(parent, x, y, width, height);
    }

    @Override
    public void drawComponent(LockedResolution resolution, int mouseX, int mouseY) {
        String valueString;
        float x = this.getX();
        float y = this.getY();
        float width = this.getWidth();
        float height = this.getHeight();
        double min = this.getMin();
        double max = this.getMax();
        double dValue = this.getValue();
        Representation representation = this.getRepresentation();
        boolean isInt = representation == Representation.INT || representation == Representation.MILLISECONDS;
        double value = isInt ? (double)((int)dValue) : dValue;
        boolean hovered = this.isHovered(mouseX, mouseY);
        if (this.sliding) {
            if ((float)mouseX >= x - 0.5f && (float)mouseY >= y - 0.5f && (float)mouseX <= x + width + 0.5f && (float)mouseY <= y + height + 0.5f) {
                this.setValue(MathHelper.clamp_double(this.roundToIncrement((double)((float)mouseX - x) * (max - min) / (double)(width - 1.0f) + min), min, max));
            } else {
                this.sliding = false;
            }
        }
        double sliderPercentage = (value - min) / (max - min);
        if (isInt) {
            valueString = Integer.toString((int)value);
        } else {
            DecimalFormat format = new DecimalFormat("####.##");
            valueString = format.format(value);
        }
        switch (representation) {
            case PERCENTAGE: {
                valueString = String.valueOf(valueString) + '%';
                break;
            }
            case MILLISECONDS: {
                valueString = valueString + "ms";
                break;
            }
            case DISTANCE: {
                valueString = String.valueOf(valueString) + 'm';
            }
        }
        Gui.drawRect(x, y, x + width, y + height, SkeetUI.getColor(855309));
        RenderingUtils.drawGradientRect(x + 0.5f, y + 0.5f, x + width - 0.5f, y + height - 0.5f, false, SkeetUI.getColor(hovered ? RenderingUtils.darker(0x494949, 1.4f) : 0x494949), SkeetUI.getColor(hovered ? RenderingUtils.darker(0x303030, 1.4f) : 0x303030));
        RenderingUtils.drawGradientRect(x + 0.5f, y + 0.5f, (double)x + (double)width * sliderPercentage - 0.5, y + height - 0.5f, false, SkeetUI.getColor(), RenderingUtils.darker(SkeetUI.getColor(), 0.8f));
        if (SkeetUI.shouldRenderText()) {
            GL11.glTranslatef(0.0f, 0.0f, 1.0f);
            if (SkeetUI.getAlpha() > 120.0) {
                RenderingUtils.drawOutlinedString(SkeetUI.GROUP_BOX_HEADER_RENDERER, valueString, x + width - 4.0f, y + height / 2.0f - 7.0f, new Color(230, 230, 230, (int)SkeetUI.getAlpha()).getRGB(), 0x78000000);
            } else {
                SkeetUI.GROUP_BOX_HEADER_RENDERER.drawString(valueString, x + width - 4.0f, y + height / 2.0f - 7.0f, new Color(230, 230, 230, (int)SkeetUI.getAlpha()).getRGB());
            }
            GL11.glTranslatef(0.0f, 0.0f, -1.0f);
        }
    }

    @Override
    public void onPress(int mouseButton) {
        if (!this.sliding && mouseButton == 0) {
            this.sliding = true;
        }
    }

    @Override
    public void onMouseRelease(int button) {
        this.sliding = false;
    }

    private double roundToIncrement(double value) {
        double inc = this.getIncrement();
        double halfOfInc = inc / 2.0;
        double floored = StrictMath.floor(value / inc) * inc;
        if (value >= floored + halfOfInc) {
            return new BigDecimal(StrictMath.ceil(value / inc) * inc).setScale(2, 4).doubleValue();
        }
        return new BigDecimal(floored).setScale(2, 4).doubleValue();
    }

    public abstract double getValue();

    public abstract void setValue(double var1);

    public abstract Representation getRepresentation();

    public abstract double getMin();

    public abstract double getMax();

    public abstract double getIncrement();
}

