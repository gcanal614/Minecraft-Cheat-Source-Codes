/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package de.fanta.clickgui.defaultgui.components;

import de.fanta.clickgui.defaultgui.components.CategoryPanel;
import de.fanta.clickgui.defaultgui.components.CheckBox;
import de.fanta.clickgui.defaultgui.components.DropdownBox;
import de.fanta.clickgui.defaultgui.components.DropdownButton;
import de.fanta.clickgui.defaultgui.components.Slider;
import de.fanta.clickgui.defaultgui.components.TextField;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.utils.RenderUtil;
import java.util.ArrayList;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class SettingsContainer {
    private float x;
    private float y;
    public Module curModule;
    private CategoryPanel panel;
    public boolean scroll;
    private float scrollY;
    private float settingY;
    private ArrayList<CheckBox> checkBoxes;
    private ArrayList<Slider> sliders;
    private ArrayList<DropdownBox> dropdownBoxes;
    private ArrayList<TextField> textFields;

    public SettingsContainer(CategoryPanel panel, Module curModule, float x, float y) {
        this.panel = panel;
        this.curModule = curModule;
        this.x = x;
        this.y = y;
        this.checkBoxes = new ArrayList();
        this.sliders = new ArrayList();
        this.dropdownBoxes = new ArrayList();
        this.textFields = new ArrayList();
        float settingY = 5.0f;
        int i = 0;
        while (i < curModule.settings.size()) {
            Setting s = curModule.settings.get(i);
            if (s.getSetting() instanceof de.fanta.setting.settings.Slider) {
                this.sliders.add(new Slider(s, panel, x + 75.0f, y + (settingY += 5.0f)));
                settingY += 2.0f;
            }
            if (s.getSetting() instanceof de.fanta.setting.settings.CheckBox) {
                this.checkBoxes.add(new CheckBox(s, panel, x + 75.0f, y + (settingY += 5.0f) - 5.0f));
            }
            if (s.getSetting() instanceof de.fanta.setting.settings.TextField) {
                this.textFields.add(new TextField(s, panel, x + 75.0f, y + (settingY += 5.0f) - 5.0f));
            }
            if (s.getSetting() instanceof de.fanta.setting.settings.DropdownBox) {
                DropdownBox dropdownBox = new DropdownBox(s, panel, x + 75.0f, y + (settingY += 30.0f) - 10.0f);
                this.dropdownBoxes.add(dropdownBox);
                if (i == curModule.settings.size() - 1) {
                    settingY += dropdownBox.buttonY;
                }
            }
            settingY += 10.0f;
            ++i;
        }
        if (settingY > 215.0f) {
            this.scroll = true;
            this.settingY = settingY;
        }
    }

    public void drawSettingsContainer(float mouseX, float mouseY) {
        float xOff = this.panel.cateButton.panel.dragX;
        float yOff = this.panel.cateButton.panel.dragY;
        GlStateManager.pushMatrix();
        RenderUtil.scissorBox(this.x + xOff, this.y + yOff + 0.5f, this.x + xOff + 325.0f, this.y + yOff + 212.5f);
        GL11.glEnable((int)3089);
        this.checkBoxes.forEach(checkBox -> checkBox.drawCheckBox(mouseX, mouseY));
        this.dropdownBoxes.forEach(dropdownBox -> dropdownBox.drawDropdownBox(mouseX, mouseY));
        this.sliders.forEach(slider -> slider.drawSlider(mouseX, mouseY));
        this.textFields.forEach(textField -> textField.drawTextField(mouseX, mouseY));
        GL11.glDisable((int)3089);
        GlStateManager.popMatrix();
    }

    public void settingsContainerClicked(float mouseX, float mouseY, int mouseButton) {
        float yOff = this.panel.cateButton.panel.dragY;
        this.checkBoxes.forEach(checkBox -> {
            float checkBoxPos = checkBox.y + yOff;
            if (checkBoxPos < this.y + yOff || checkBoxPos > this.y + yOff + 212.5f) {
                return;
            }
            checkBox.checkBoxClicked(mouseX, mouseY, mouseButton);
        });
        this.dropdownBoxes.forEach(dropdownBox -> {
            float dropdownBoxPos = dropdownBox.y + yOff;
            if (dropdownBoxPos < this.y + yOff || dropdownBoxPos > this.y + yOff + 212.5f) {
                return;
            }
            dropdownBox.dropdownBoxClicked(mouseX, mouseY, mouseButton);
        });
        this.sliders.forEach(slider -> {
            float sliderPos = slider.y + yOff;
            if (sliderPos < this.y + yOff || sliderPos > this.y + yOff + 212.5f) {
                return;
            }
            slider.sliderClicked(mouseX, mouseY, mouseButton);
        });
        this.textFields.forEach(textField -> {
            float textFieldPos = textField.y + yOff;
            if (textFieldPos < this.y + yOff || textFieldPos > this.y + yOff + 212.5f) {
                return;
            }
            textField.textFieldClicked(mouseX, mouseY, mouseButton);
        });
    }

    public void settingsContainerReleased(float mouseX, float mouseY, int state) {
        this.sliders.forEach(slider -> slider.sliderReleased(mouseX, mouseY, state));
    }

    public void settingsContainerHandleInput() {
        int mouseWheel = Mouse.getEventDWheel() / 10;
        this.scrollY -= (float)mouseWheel;
        if (this.scroll && mouseWheel != 0) {
            float diff = this.settingY - 215.0f;
            if (this.scrollY > -diff) {
                this.scrollY = -diff;
                return;
            }
            if (this.scrollY + diff < -diff - 12.0f) {
                this.scrollY += (float)mouseWheel;
                return;
            }
            this.checkBoxes.forEach(checkBox -> {
                float f = checkBox.y = checkBox.y - (float)mouseWheel;
            });
            this.textFields.forEach(textField -> {
                float f = textField.y = textField.y - (float)mouseWheel;
            });
            this.dropdownBoxes.forEach(dropdownBox -> {
                dropdownBox.y -= (float)mouseWheel;
                for (DropdownButton button : dropdownBox.dropdownButtons) {
                    button.y -= (float)mouseWheel;
                }
            });
            this.sliders.forEach(slider -> {
                float f = slider.y = slider.y - (float)mouseWheel;
            });
        }
    }
}

