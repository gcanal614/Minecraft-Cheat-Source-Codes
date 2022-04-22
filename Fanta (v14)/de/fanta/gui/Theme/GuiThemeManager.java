/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.gui.Theme;

import de.fanta.Client;
import de.fanta.gui.flux.GuiButtonFanta;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.utils.ChatUtil;
import de.fanta.utils.Translate;
import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiThemeManager
extends GuiScreen {
    Translate translate;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect2(width - 375, height - 420, 368.0, 370.0, new Color(0, 0, 0, 200).getRGB());
        Client.INSTANCE.unicodeBasicFontRenderer.drawStringWithShadow("Theme Selector", width / 2 - 40, 95.0f, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        int i = 110;
        int j = height / 4 + 48;
        this.buttonList.add(new GuiButtonFanta(1, width / 9 + 270, i, I18n.format("Hero", new Object[0])));
        this.buttonList.add(new GuiButtonFanta(2, width / 9 + 270, i + 25, I18n.format("Zeroday", new Object[0])));
        this.buttonList.add(new GuiButtonFanta(3, width / 9 + 270, i + 50, I18n.format("Ambien", new Object[0])));
        this.buttonList.add(new GuiButtonFanta(4, width / 9 + 270, i + 75, I18n.format("Flux", new Object[0])));
        this.buttonList.add(new GuiButtonFanta(5, width / 9 + 270, i + 100, I18n.format("Violence", new Object[0])));
        this.buttonList.add(new GuiButtonFanta(6, width / 9 + 270, i + 125, I18n.format("Skid", new Object[0])));
        this.buttonList.add(new GuiButtonFanta(7, width / 9 + 270, i + 150, I18n.format("Jello", new Object[0])));
        this.buttonList.add(new GuiButtonFanta(8, width / 9 + 270, i + 175, I18n.format("Centaurus", new Object[0])));
        this.buttonList.add(new GuiButtonFanta(9, width / 9 + 270, i + 200, I18n.format("Unify", new Object[0])));
        this.buttonList.add(new GuiButtonFanta(10, width / 9 + 270, i + 225, I18n.format("Novoline2.0", new Object[0])));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            Client.INSTANCE.moduleManager.getModule("Themes").setState(true);
            Client.INSTANCE.moduleManager.getModule("Tabgui").setState(true);
            ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption = "Hero";
            ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Tabgui").getSetting((String)"Mode").getSetting()).curOption = "Hero";
            ChatUtil.sendChatMessageWithPrefix("Hero Theme Loaded");
        }
        if (button.id == 2) {
            Client.INSTANCE.moduleManager.getModule("Themes").setState(true);
            Client.INSTANCE.moduleManager.getModule("Tabgui").setState(true);
            ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption = "Zeroday";
            ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Tabgui").getSetting((String)"Mode").getSetting()).curOption = "ZeroDay";
            ChatUtil.sendChatMessageWithPrefix("Zeroday Theme Loaded");
        }
        if (button.id == 3) {
            Client.INSTANCE.moduleManager.getModule("Themes").setState(true);
            Client.INSTANCE.moduleManager.getModule("Tabgui").setState(true);
            ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption = "Ambien";
            ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Tabgui").getSetting((String)"Mode").getSetting()).curOption = "Ambien2";
            ChatUtil.sendChatMessageWithPrefix("Ambien Theme Loaded");
        }
        if (button.id == 4) {
            Client.INSTANCE.moduleManager.getModule("Themes").setState(true);
            Client.INSTANCE.moduleManager.getModule("Tabgui").setState(true);
            ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption = "Flux";
            ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Tabgui").getSetting((String)"Mode").getSetting()).curOption = "Flux";
            ChatUtil.sendChatMessageWithPrefix("Flux Theme Loaded");
        }
        if (button.id == 5) {
            Client.INSTANCE.moduleManager.getModule("Themes").setState(true);
            Client.INSTANCE.moduleManager.getModule("Tabgui").setState(true);
            ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption = "Violence";
            ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Tabgui").getSetting((String)"Mode").getSetting()).curOption = "Violence";
            ChatUtil.sendChatMessageWithPrefix("Violence Theme Loaded");
        }
        if (button.id == 6) {
            Client.INSTANCE.moduleManager.getModule("Themes").setState(true);
            Client.INSTANCE.moduleManager.getModule("Tabgui").setState(false);
            ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption = "Skid";
            ChatUtil.sendChatMessageWithPrefix("Skid Theme Loaded");
        }
        if (button.id == 7) {
            Client.INSTANCE.moduleManager.getModule("Themes").setState(true);
            Client.INSTANCE.moduleManager.getModule("Tabgui").setState(true);
            ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption = "Jello";
            ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Tabgui").getSetting((String)"Mode").getSetting()).curOption = "Jello";
            ChatUtil.sendChatMessageWithPrefix("Jello Theme Loaded");
        }
        if (button.id == 8) {
            Client.INSTANCE.moduleManager.getModule("Themes").setState(true);
            Client.INSTANCE.moduleManager.getModule("Tabgui").setState(false);
            ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption = "Centaurus";
            ChatUtil.sendChatMessageWithPrefix("Centaurus Theme Loaded");
        }
        if (button.id == 9) {
            Client.INSTANCE.moduleManager.getModule("Themes").setState(true);
            Client.INSTANCE.moduleManager.getModule("Tabgui").setState(false);
            ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption = "Unify";
            ChatUtil.sendChatMessageWithPrefix("Jello Theme Loaded");
        }
        if (button.id == 10) {
            Client.INSTANCE.moduleManager.getModule("Themes").setState(true);
            Client.INSTANCE.moduleManager.getModule("Tabgui").setState(false);
            ((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"Themes").getSetting((String)"Fonts").getSetting()).curOption = "Novoline2.0";
            ChatUtil.sendChatMessageWithPrefix("Novoline Theme Loaded");
        }
    }
}

