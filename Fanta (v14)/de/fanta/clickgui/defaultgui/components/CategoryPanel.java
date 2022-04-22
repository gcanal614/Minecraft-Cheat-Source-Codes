/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.clickgui.defaultgui.components;

import de.fanta.Client;
import de.fanta.clickgui.defaultgui.components.CategoryButton;
import de.fanta.clickgui.defaultgui.components.ModuleButton;
import de.fanta.clickgui.defaultgui.components.SettingsContainer;
import de.fanta.module.Module;
import java.util.ArrayList;

public class CategoryPanel {
    public CategoryButton cateButton;
    public float lengthModule;
    private SettingsContainer settingsContainer;
    private float x;
    private float y;
    public ArrayList<ModuleButton> moduleButtons = new ArrayList();

    public CategoryPanel(CategoryButton cateButton, float x, float y) {
        this.cateButton = cateButton;
        this.x = x;
        this.y = y;
        float moduleY = 4.0f;
        for (Module module : Client.INSTANCE.moduleManager.modules) {
            if (!module.type.equals((Object)cateButton.type)) continue;
            this.moduleButtons.add(new ModuleButton(module, this, x + 2.0f, y + moduleY));
            float length = Client.INSTANCE.unicodeBasicFontRenderer.getStringWidth(module.name);
            if (length > this.lengthModule) {
                this.lengthModule = length;
            }
            moduleY += 15.0f;
        }
    }

    public void drawCategory(float mouseX, float mouseY) {
        this.moduleButtons.forEach(moduleButton -> moduleButton.drawButton(mouseX, mouseY));
        if (this.settingsContainer != null) {
            this.settingsContainer.drawSettingsContainer(mouseX, mouseY);
        }
    }

    public void categoryClicked(float mouseX, float mouseY, int mouseButton) {
        this.moduleButtons.forEach(moduleButton -> {
            moduleButton.buttonClicked(mouseX, mouseY, mouseButton);
            if (moduleButton.isOpened && !moduleButton.module.settings.isEmpty() && (this.settingsContainer == null || this.settingsContainer.curModule != moduleButton.module)) {
                this.settingsContainer = new SettingsContainer(this, moduleButton.module, this.x, this.y);
            }
        });
        if (this.settingsContainer != null) {
            this.settingsContainer.settingsContainerClicked(mouseX, mouseY, mouseButton);
        }
    }

    public void categoryReleased(float mouseX, float mouseY, int state) {
        if (this.settingsContainer != null) {
            this.settingsContainer.settingsContainerReleased(mouseX, mouseY, state);
        }
    }

    public void categoryHandleInput() {
        if (this.settingsContainer != null) {
            this.settingsContainer.settingsContainerHandleInput();
        }
    }
}

