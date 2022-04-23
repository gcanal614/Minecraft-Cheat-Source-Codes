/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  store.intent.intentguard.annotation.Exclude
 *  store.intent.intentguard.annotation.Strategy
 */
package net.optifine.gui;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;
import store.intent.intentguard.annotation.Exclude;
import store.intent.intentguard.annotation.Strategy;

@Exclude(value={Strategy.NAME_REMAPPING, Strategy.STRING_ENCRYPTION, Strategy.FLOW_OBFUSCATION, Strategy.NUMBER_OBFUSCATION, Strategy.REFERENCE_OBFUSCATION, Strategy.PARAMETER_OBFUSCATION})
public class GuiScreenOF
extends GuiScreen {
    protected void actionPerformedRightClick(GuiButton button) {
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        GuiButton guibutton;
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 1 && (guibutton = GuiScreenOF.getSelectedButton(mouseX, mouseY, this.buttonList)) != null && guibutton.enabled) {
            guibutton.playPressSound(this.mc.getSoundHandler());
            this.actionPerformedRightClick(guibutton);
        }
    }

    public static GuiButton getSelectedButton(int x, int y, List<GuiButton> listButtons) {
        for (GuiButton guibutton : listButtons) {
            if (!guibutton.visible) continue;
            int j = GuiVideoSettings.getButtonWidth(guibutton);
            int k = GuiVideoSettings.getButtonHeight(guibutton);
            if (x < guibutton.xPosition || y < guibutton.yPosition || x >= guibutton.xPosition + j || y >= guibutton.yPosition + k) continue;
            return guibutton;
        }
        return null;
    }
}

