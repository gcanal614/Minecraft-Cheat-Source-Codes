/*
 * Decompiled with CFR 0.152.
 */
package de.hero.example;

import de.fanta.Client;
import de.fanta.clickgui.astolfo.ClickGuiScreen;
import de.fanta.events.Event;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.DropdownBox;
import java.awt.Color;
import net.minecraft.client.gui.GuiScreen;

public class GUI
extends Module {
    public static GUI INSTANCE;

    public GUI() {
        super("GUI", 54, Module.Type.Visual, Color.white);
        this.settings.add(new Setting("Modes", new DropdownBox("Mega", new String[]{"DropDown", "Skash", "Astolfo", "IntelliJ", "Mega"})));
    }

    @Override
    public void onEnable() {
        Client.INSTANCE.moduleManager.getModule("GUI").setState(false);
        switch (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption) {
            case "DropDown": {
                mc.displayGuiScreen(Client.clickgui);
                break;
            }
            case "Astolfo": {
                mc.displayGuiScreen(new ClickGuiScreen());
                break;
            }
            case "IntelliJ": {
                mc.displayGuiScreen(new de.fanta.clickgui.intellij.ClickGuiScreen(null));
                break;
            }
            case "Mega": {
                mc.displayGuiScreen((GuiScreen)Client.megaClickGui);
                break;
            }
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        block13: {
            Client.INSTANCE.moduleManager.getModule("GUI").setState(false);
            if (!Client.INSTANCE.moduleManager.getModule("GUI").isState() || !((DropdownBox)Client.INSTANCE.moduleManager.getModule((String)"GUI").getSetting((String)"Modes").getSetting()).curOption.equalsIgnoreCase("DropDown")) break block13;
            switch (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption) {
                case "DropDown": {
                    mc.displayGuiScreen(Client.clickgui);
                    break;
                }
                case "Astolfo": {
                    mc.displayGuiScreen(new ClickGuiScreen());
                    break;
                }
                case "IntelliJ": {
                    mc.displayGuiScreen(new de.fanta.clickgui.intellij.ClickGuiScreen(null));
                    break;
                }
            }
        }
        super.onDisable();
    }

    @Override
    public void onEvent(Event event) {
    }
}

