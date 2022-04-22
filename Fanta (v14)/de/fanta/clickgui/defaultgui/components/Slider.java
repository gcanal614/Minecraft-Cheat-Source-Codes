/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.clickgui.defaultgui.components;

import de.fanta.Client;
import de.fanta.clickgui.defaultgui.components.CategoryPanel;
import de.fanta.setting.Setting;
import de.fanta.utils.Colors;
import de.fanta.utils.RenderUtil;

public class Slider {
    public float x;
    public float y;
    private CategoryPanel panel;
    private float dragX;
    private Setting setting;
    private boolean isDragged;

    public Slider(Setting setting, CategoryPanel panel, float x, float y) {
        this.setting = setting;
        this.panel = panel;
        this.x = x;
        this.y = y;
    }

    public void drawSlider(float mouseX, float mouseY) {
        float xOff = this.panel.cateButton.panel.dragX;
        float yOff = this.panel.cateButton.panel.dragY;
        de.fanta.setting.settings.Slider slider = (de.fanta.setting.settings.Slider)this.setting.getSetting();
        double percent = this.dragX / 100.0f;
        double value = this.getIncremental(percent * 100.0 * ((Double)slider.getMaxValue() - (Double)slider.getMinValue()) / 100.0 + (Double)slider.getMinValue(), slider.getStepValue());
        value = Math.min(value, (Double)slider.getMaxValue());
        value = Math.max(value, (Double)slider.getMinValue());
        if (this.isDragged) {
            this.dragX = mouseX - (this.x + xOff + 5.0f);
            slider.curValue = value;
        }
        float sliderX = (float)((Double.valueOf(slider.curValue) - (Double)slider.getMinValue()) / ((Double)slider.getMaxValue() - (Double)slider.getMinValue()) * 100.0);
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(this.setting.getName(), (int)(this.x + xOff) + 110, (int)(this.y + yOff) - 2, Colors.getColor(255, 255, 255, 255));
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow(String.valueOf(slider.curValue), (int)(this.x + xOff) + 53 - Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(String.valueOf(slider.curValue)) / 2, (int)(this.y + yOff) + 8, -1);
        RenderUtil.rectangle(this.x + xOff + 4.0f, this.y + yOff + 2.0f, this.x + xOff + 106.0f, this.y + yOff + 9.0f, Colors.getColor(0, 0, 0, 255));
        RenderUtil.rectangle(this.x + xOff + 5.0f, this.y + yOff + 3.0f, this.x + xOff + 105.0f, this.y + yOff + 8.0f, Colors.getColor(75, 75, 75, 255));
        RenderUtil.rectangle(this.x + xOff + 5.0f, this.y + yOff + 3.0f, this.x + xOff + sliderX + 5.0f, this.y + yOff + 8.0f, Colors.getColor(200, 50, 0, 255));
    }

    public void sliderClicked(float mouseX, float mouseY, float mouseButton) {
        boolean hovering;
        float xOff = this.panel.cateButton.panel.dragX;
        float yOff = this.panel.cateButton.panel.dragY;
        boolean bl = hovering = mouseX >= this.x + xOff + 5.0f && mouseY >= this.y + yOff + 3.0f && mouseX <= this.x + xOff + 105.0f && mouseY <= this.y + yOff + 8.0f && mouseButton == 0.0f;
        if (hovering) {
            this.isDragged = true;
        }
    }

    public void sliderReleased(float mouseX, float mouseY, float state) {
        if (this.isDragged) {
            this.isDragged = false;
        }
    }

    private double getIncremental(double val, double inc) {
        double one = 1.0 / inc;
        return (double)Math.round(val * one) / one;
    }
}

