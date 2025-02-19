/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  store.intent.intentguard.annotation.Exclude
 *  store.intent.intentguard.annotation.Strategy
 */
package net.optifine.gui;

import java.awt.Rectangle;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.optifine.Lang;
import net.optifine.gui.IOptionControl;
import net.optifine.gui.TooltipProvider;
import store.intent.intentguard.annotation.Exclude;
import store.intent.intentguard.annotation.Strategy;

@Exclude(value={Strategy.NAME_REMAPPING, Strategy.STRING_ENCRYPTION, Strategy.FLOW_OBFUSCATION, Strategy.NUMBER_OBFUSCATION, Strategy.REFERENCE_OBFUSCATION, Strategy.PARAMETER_OBFUSCATION})
public class TooltipProviderOptions
implements TooltipProvider {
    @Override
    public Rectangle getTooltipBounds(GuiScreen guiScreen, int x, int y) {
        int i = guiScreen.width / 2 - 150;
        int j = guiScreen.height / 6 - 7;
        if (y <= j + 98) {
            j += 105;
        }
        int k = i + 150 + 150;
        int l = j + 84 + 10;
        return new Rectangle(i, j, k - i, l - j);
    }

    @Override
    public boolean isRenderBorder() {
        return false;
    }

    @Override
    public String[] getTooltipLines(GuiButton btn, int width) {
        if (!(btn instanceof IOptionControl)) {
            return null;
        }
        IOptionControl ioptioncontrol = (IOptionControl)((Object)btn);
        GameSettings.Options gamesettings$options = ioptioncontrol.getOption();
        return TooltipProviderOptions.getTooltipLines(gamesettings$options.getEnumString());
    }

    public static String[] getTooltipLines(String key) {
        String s;
        String s1;
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 10 && (s1 = Lang.get(s = key + ".tooltip." + (i + 1), null)) != null; ++i) {
            list.add(s1);
        }
        if (list.size() <= 0) {
            return null;
        }
        return list.toArray(new String[0]);
    }
}

