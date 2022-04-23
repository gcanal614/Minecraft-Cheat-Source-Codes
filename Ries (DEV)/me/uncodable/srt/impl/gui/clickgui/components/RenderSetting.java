/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package me.uncodable.srt.impl.gui.clickgui.components;

import java.math.BigDecimal;
import java.math.RoundingMode;
import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.modules.api.settings.Setting;
import me.uncodable.srt.impl.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

public class RenderSetting {
    private static final Minecraft MC = Minecraft.getMinecraft();
    private final Setting setting;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private static final int SUB_TAB_COLOR = -1442840576;
    private static final int TEXT_COLOR = 0xFFFFFF;
    private static final int SETTING_COLOR = -973078529;
    private boolean draggingSlider;
    private boolean typing;
    private StringBuilder builder = new StringBuilder();

    public RenderSetting(Setting setting, int startX, int startY, int endX, int endY) {
        this.setting = setting;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public void render(int mouseX, int mouseY, int width, int height) {
        int moveCount = 0;
        switch (RenderUtils.getFPSLevel()) {
            case "Very Low": {
                moveCount = 20;
                break;
            }
            case "Low": {
                moveCount = 6;
                break;
            }
            case "Medium": {
                moveCount = 3;
                break;
            }
            case "High": {
                if (RenderSetting.MC.timer.getElapsedTicks() % 5 != 0) break;
                moveCount = 1;
            }
        }
        if (Mouse.isButtonDown((int)0)) {
            if (mouseX <= 50) {
                this.startX += moveCount;
                this.endX += moveCount;
            } else if (mouseX >= width - 60) {
                this.startX -= moveCount;
                this.endX -= moveCount;
            }
            if (mouseY <= 50) {
                this.startY += moveCount;
                this.endY += moveCount;
            } else if (mouseY >= height - 60) {
                this.startY -= moveCount;
                this.endY -= moveCount;
            }
        }
        switch (this.setting.getSettingType()) {
            case CHECKBOX: {
                Gui.drawRect(this.startX, this.startY, this.endX, this.endY, -1442840576);
                this.text(this.setting.getSettingName() + ":", this.startX + 5, this.startY + 6, 0xFFFFFF);
                Gui.drawRect(this.startX, this.endY, this.endX, this.endY + 20, -1442840576);
                Gui.drawRect(this.endX - 17, this.endY + 3, this.endX - 3, this.endY + 17, -1442840576);
                if (this.setting.isTicked()) {
                    Gui.drawRect(this.endX - 14, this.endY + 6, this.endX - 6, this.endY + 14, -973078529);
                }
                this.text("Checked:", this.startX + 5, this.endY + 6, 0xFFFFFF);
                break;
            }
            case SLIDER: {
                double multiplier = (this.setting.getCurrentValue() - this.setting.getMinimumValue()) / (this.setting.getMinimumValue() + this.setting.getMaximumValue());
                double sliderWidth = this.endX - this.startX;
                Gui.drawRect(this.startX, this.startY, this.endX, this.endY, -1442840576);
                this.text(this.setting.getSettingName() + ":", this.startX + 5, this.startY + 6, 0xFFFFFF);
                Gui.drawRect(this.startX, this.endY, this.endX, this.endY + 20, -1442840576);
                if (this.draggingSlider) {
                    double newValue = MathHelper.clamp_double(this.round((double)(mouseX - this.startX) * (this.setting.getMaximumValue() - this.setting.getMinimumValue()) / sliderWidth + this.setting.getMinimumValue(), 2), this.setting.getMinimumValue(), this.setting.getMaximumValue());
                    this.setting.setCurrentValue(this.setting.isTruncate() ? (double)((int)newValue) : newValue);
                }
                if (this.setting.getCurrentValue() >= this.setting.getMaximumValue()) {
                    Gui.drawRect(this.startX, this.endY, this.endX, this.endY + 20, -973078529);
                } else {
                    Gui.drawRect(this.startX, this.endY, MathHelper.clamp_int((int)((double)this.startX + sliderWidth * multiplier), this.startX, this.endX), this.endY + 20, -973078529);
                }
                try {
                    this.text("Value: " + (this.typing ? (this.builder.length() > 0 ? Double.valueOf(Double.parseDouble(this.builder.toString())) : "Type in a value...") : Double.valueOf(this.setting.getCurrentValue())), this.startX + 5, this.endY + 6, 0xFFFFFF);
                }
                catch (NumberFormatException e) {
                    this.text("Value: " + this.setting.getCurrentValue(), this.startX + 5, this.endY + 6, 0xFFFFFF);
                }
                break;
            }
            case COMBO_BOX: {
                Gui.drawRect(this.startX, this.startY, this.endX, this.endY, -1442840576);
                this.text(this.setting.getSettingName() + ":", this.startX + 5, this.startY + 6, 0xFFFFFF);
                Gui.drawRect(this.startX, this.endY, this.endX, this.endY + 20, -1442840576);
                this.text(this.setting.getCombos()[this.setting.getComboIndex()], this.startX + 5, this.endY + 6, 0xFFFFFF);
            }
        }
    }

    public void text(String text, double x, double y, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0.0);
        GlStateManager.scale(0.75, 0.75, 0.75);
        RenderSetting.MC.fontRendererObj.drawStringWithShadow(text, 0.0f, 0.0f, color);
        GlStateManager.popMatrix();
    }

    public void onClicked(int mouseX, int mouseY, int mouseButton) {
        switch (this.setting.getSettingType()) {
            case CHECKBOX: {
                if (!this.hoverOver(mouseX, mouseY, this.endX - 17, this.endY + 3, this.endX - 3, this.endY + 17) || mouseButton != 0) break;
                this.setting.setTicked(!this.setting.isTicked());
                break;
            }
            case SLIDER: {
                if (!this.hoverOver(mouseX, mouseY, this.startX, this.endY, this.endX, this.endY + 20)) break;
                switch (mouseButton) {
                    case 0: {
                        this.draggingSlider = true;
                        break;
                    }
                    case 2: {
                        this.typing = true;
                    }
                }
                break;
            }
            case COMBO_BOX: {
                if (!this.hoverOver(mouseX, mouseY, this.startX, this.endY, this.endX, this.endY + 20)) break;
                switch (mouseButton) {
                    case 0: {
                        if (this.setting.getComboIndex() >= this.setting.getCombos().length - 1) break;
                        this.setting.setComboIndex(this.setting.getComboIndex() + 1);
                        this.setting.setCurrentCombo(this.setting.getCombos()[this.setting.getComboIndex()]);
                        break;
                    }
                    case 1: {
                        if (this.setting.getComboIndex() <= 0) break;
                        this.setting.setComboIndex(this.setting.getComboIndex() - 1);
                        this.setting.setCurrentCombo(this.setting.getCombos()[this.setting.getComboIndex()]);
                    }
                }
                Ries.INSTANCE.getModuleManager().sort();
            }
        }
    }

    public boolean hoverOver(int mouseX, int mouseY, float compareStartX, float compareStartY, float compareEndX, float compareEndY) {
        return (float)mouseX >= compareStartX && (float)mouseX <= compareEndX && (float)mouseY >= compareStartY && (float)mouseY <= compareEndY;
    }

    public void keyTyped(int keyCode) {
        if (this.typing) {
            switch (keyCode) {
                case 2: {
                    this.builder.append(1);
                    break;
                }
                case 3: {
                    this.builder.append(2);
                    break;
                }
                case 4: {
                    this.builder.append(3);
                    break;
                }
                case 5: {
                    this.builder.append(4);
                    break;
                }
                case 6: {
                    this.builder.append(5);
                    break;
                }
                case 7: {
                    this.builder.append(6);
                    break;
                }
                case 8: {
                    this.builder.append(7);
                    break;
                }
                case 9: {
                    this.builder.append(8);
                    break;
                }
                case 10: {
                    this.builder.append(9);
                    break;
                }
                case 11: {
                    this.builder.append(0);
                    break;
                }
                case 28: {
                    double value = Double.parseDouble(this.builder.toString());
                    if (!RenderSetting.MC.gameSettings.expertMode) {
                        if (value > this.setting.getMaximumValue()) {
                            Ries.INSTANCE.msg("The value supplied is greater than the set maximum allowed value. The value has been rounded downwards to the maximum allowed. To bypass this limit, enable expert mode.");
                            value = this.setting.getMaximumValue();
                        } else if (value < this.setting.getMinimumValue()) {
                            Ries.INSTANCE.msg("The value supplied is less than the set minimum allowed value. The value has been rounded upwards to the minimum allowed. To bypass this limit, enable expert mode.");
                            value = this.setting.getMinimumValue();
                        }
                    }
                    this.setting.setCurrentValue(value);
                    this.builder = new StringBuilder();
                    this.typing = false;
                }
            }
        }
    }

    public double round(double n, int places) {
        BigDecimal bigDecimal = new BigDecimal(n);
        return bigDecimal.setScale(places, RoundingMode.HALF_UP).doubleValue();
    }

    public void mouseReleased() {
        this.draggingSlider = false;
    }
}

