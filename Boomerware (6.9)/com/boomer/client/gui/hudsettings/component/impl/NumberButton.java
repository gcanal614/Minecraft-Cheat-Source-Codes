package com.boomer.client.gui.hudsettings.component.impl;

import java.awt.Color;

import com.boomer.client.gui.hudsettings.component.Component;
import com.boomer.client.utils.MathUtils;
import com.boomer.client.utils.RenderUtil;
import com.boomer.client.utils.TimerUtil;
import com.boomer.client.utils.font.Fonts;
import com.boomer.client.utils.value.impl.NumberValue;
import org.lwjgl.input.Keyboard;

import net.minecraft.util.MathHelper;

/**
 * made by oHare for BoomerWare
 *
 * @since 7/26/2019
 **/
public class NumberButton extends Component {
    private NumberValue numberValue;
    private boolean dragging;
    private TimerUtil timer = new TimerUtil();

    public NumberButton(NumberValue numberValue, float posX, float posY) {
        super(numberValue.getLabel(), posX, posY);
        this.numberValue = numberValue;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float sliderwidth = 74;
        float length = MathHelper.floor_double(((numberValue.getValue()).floatValue() - numberValue.getMinimum().floatValue()) / (numberValue.getMaximum().floatValue() - numberValue.getMinimum().floatValue()) * sliderwidth);
        boolean isHovered = mouseWithinBounds(mouseX, mouseY, getPosX(), getPosY(), 80, 10);
        RenderUtil.drawBorderedRect(getPosX(), getPosY(), 80, 10, 0.5, new Color(36, 41, 51, 255).getRGB(), isHovered ? new Color(0x505760).getRGB() : new Color(0xFF3b4149).getRGB());
        RenderUtil.drawRect(getPosX() + length + 1f, getPosY() + 1, 4f, 8, new Color(0, 107, 214, 255).getRGB());
        Fonts.sliderfont.drawString(numberValue.getLabel() + ": " + numberValue.getValue().toString(), getPosX() + 40 - Fonts.sliderfont.getStringWidth(numberValue.getLabel() + ": " + numberValue.getValue().toString()) / 2, getPosY() + 4, -1);

        if (dragging) {
            if (numberValue.getValue() instanceof Double) {
                numberValue.setValue(MathUtils.round(((mouseX - getPosX()) * (numberValue.getMaximum().doubleValue() - numberValue.getMinimum().doubleValue()) / sliderwidth + numberValue.getMinimum().doubleValue()), 2));
            }
            if (numberValue.getValue() instanceof Float) {
                numberValue.setValue((float) MathUtils.round(((mouseX - getPosX()) * (numberValue.getMaximum().floatValue() - numberValue.getMinimum().floatValue()) / sliderwidth + numberValue.getMinimum().floatValue()), 2));
            }
            if (numberValue.getValue() instanceof Long) {
                numberValue.setValue((long) MathUtils.round(((mouseX - getPosX()) * (numberValue.getMaximum().longValue() - numberValue.getMinimum().longValue()) / sliderwidth + numberValue.getMinimum().longValue()), 2));
            }
            if (numberValue.getValue() instanceof Integer) {
                numberValue.setValue((int) MathUtils.round(((mouseX - getPosX()) * (numberValue.getMaximum().intValue() - numberValue.getMinimum().intValue()) / sliderwidth + numberValue.getMinimum().intValue()), 2));
            }
            if (numberValue.getValue() instanceof Short) {
                numberValue.setValue((short) MathUtils.round(((mouseX - getPosX()) * (numberValue.getMaximum().shortValue() - numberValue.getMinimum().shortValue()) / sliderwidth + numberValue.getMinimum().shortValue()), 2));
            }
            if (numberValue.getValue() instanceof Byte) {
                numberValue.setValue((byte) MathUtils.round(((mouseX - getPosX()) * (numberValue.getMaximum().byteValue() - numberValue.getMinimum().byteValue()) / sliderwidth + numberValue.getMinimum().byteValue()), 2));
            }
        }
        if (isHovered) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && timer.sleep(100)) {
                if (numberValue.getValue() instanceof Double) {
                    numberValue.setValue(MathUtils.round(numberValue.getValue().doubleValue() - numberValue.getInc().doubleValue(), 2));
                }
                if (numberValue.getValue() instanceof Float) {
                    numberValue.setValue((float) MathUtils.round(numberValue.getValue().floatValue() - numberValue.getInc().floatValue(), 2));

                }
                if (numberValue.getValue() instanceof Long) {
                    numberValue.setValue((long) MathUtils.round(numberValue.getValue().longValue() - numberValue.getInc().longValue(), 2));

                }
                if (numberValue.getValue() instanceof Integer) {
                    numberValue.setValue((int) MathUtils.round(numberValue.getValue().intValue() - numberValue.getInc().intValue(), 2));

                }
                if (numberValue.getValue() instanceof Short) {
                    numberValue.setValue((short) MathUtils.round(numberValue.getValue().shortValue() - numberValue.getInc().shortValue(), 2));
                }
                if (numberValue.getValue() instanceof Byte) {
                    numberValue.setValue((byte) MathUtils.round(numberValue.getValue().byteValue() - numberValue.getInc().byteValue(), 2));
                }
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && timer.sleep(100)) {
                if (numberValue.getValue() instanceof Double) {
                    numberValue.setValue(MathUtils.round(numberValue.getValue().doubleValue() + numberValue.getInc().doubleValue(), 2));
                }
                if (numberValue.getValue() instanceof Float) {
                    numberValue.setValue((float) MathUtils.round(numberValue.getValue().floatValue() + numberValue.getInc().floatValue(), 2));

                }
                if (numberValue.getValue() instanceof Long) {
                    numberValue.setValue((long) MathUtils.round(numberValue.getValue().longValue() + numberValue.getInc().longValue(), 2));

                }
                if (numberValue.getValue() instanceof Integer) {
                    numberValue.setValue((int) MathUtils.round(numberValue.getValue().intValue() + numberValue.getInc().intValue(), 2));

                }
                if (numberValue.getValue() instanceof Short) {
                    numberValue.setValue((short) MathUtils.round(numberValue.getValue().shortValue() + numberValue.getInc().shortValue(), 2));
                }
                if (numberValue.getValue() instanceof Byte) {
                    numberValue.setValue((byte) MathUtils.round(numberValue.getValue().byteValue() + numberValue.getInc().byteValue(), 2));
                }
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        boolean isHovered = mouseWithinBounds(mouseX, mouseY, getPosX(), getPosY(), 80, 10);
        if (isHovered) {
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (dragging) {
            dragging = false;
        }
    }
}
