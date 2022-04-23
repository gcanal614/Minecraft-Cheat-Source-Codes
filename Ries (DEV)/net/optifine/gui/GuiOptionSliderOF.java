/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  store.intent.intentguard.annotation.Exclude
 *  store.intent.intentguard.annotation.Strategy
 */
package net.optifine.gui;

import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.settings.GameSettings;
import net.optifine.gui.IOptionControl;
import store.intent.intentguard.annotation.Exclude;
import store.intent.intentguard.annotation.Strategy;

@Exclude(value={Strategy.NAME_REMAPPING, Strategy.STRING_ENCRYPTION, Strategy.FLOW_OBFUSCATION, Strategy.NUMBER_OBFUSCATION, Strategy.REFERENCE_OBFUSCATION, Strategy.PARAMETER_OBFUSCATION})
public class GuiOptionSliderOF
extends GuiOptionSlider
implements IOptionControl {
    private final GameSettings.Options option;

    public GuiOptionSliderOF(int id, int x, int y, GameSettings.Options option) {
        super(id, x, y, option);
        this.option = option;
    }

    @Override
    public GameSettings.Options getOption() {
        return this.option;
    }
}

