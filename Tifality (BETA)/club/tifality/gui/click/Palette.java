/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.click;

import club.tifality.module.impl.render.hud.Hud;
import club.tifality.utils.render.Colors;
import java.awt.Color;

public enum Palette {
    DEFAULT(new Color(-13619152), new Color(-13619152).darker(), new Color(-13619152), new Color(-14474461).darker(), new Color(-14079703), new Color(Hud.hudColor.get()), new Color(Hud.hudColor.get()), new Color(0xFFFFFF)),
    PINK(new Color(Integer.MIN_VALUE, true), new Color(-2139062144, true), new Color(Integer.MIN_VALUE, true), new Color(-2139062144, true), new Color(Colors.PINK), new Color(Colors.PINK), new Color(-1), new Color(-1));

    private final Color panelBackgroundColor;
    private final Color hoveredBackgroundColor;
    private final Color secondaryBackgroundColor;
    private final Color hoveredSecondaryBackgroundColor;
    private final Color panelHeaderColor;
    private final Color enabledModuleColor;
    private final Color disabledModuleColor;
    private final Color panelHeaderTextColor;

    private Palette(Color panelBackgroundColor, Color hoveredBackgroundColor, Color secondaryBackgroundColor, Color hoveredSecondaryBackgroundColor, Color panelHeaderColor, Color enabledModuleColor, Color disabledModuleColor, Color panelHeaderTextColor) {
        this.panelBackgroundColor = panelBackgroundColor;
        this.hoveredBackgroundColor = hoveredBackgroundColor;
        this.secondaryBackgroundColor = secondaryBackgroundColor;
        this.hoveredSecondaryBackgroundColor = hoveredSecondaryBackgroundColor;
        this.panelHeaderColor = panelHeaderColor;
        this.enabledModuleColor = enabledModuleColor;
        this.disabledModuleColor = disabledModuleColor;
        this.panelHeaderTextColor = panelHeaderTextColor;
    }

    public Color getHoveredSecondaryBackgroundColor() {
        return this.hoveredSecondaryBackgroundColor;
    }

    public Color getHoveredBackgroundColor() {
        return this.hoveredBackgroundColor;
    }

    public Color getSecondaryBackgroundColor() {
        return this.secondaryBackgroundColor;
    }

    public Color getPanelHeaderTextColor() {
        return this.panelHeaderTextColor;
    }

    public Color getPanelBackgroundColor() {
        return this.panelBackgroundColor;
    }

    public Color getPanelHeaderColor() {
        return this.panelHeaderColor;
    }

    public Color getEnabledModuleColor() {
        return this.enabledModuleColor;
    }

    public Color getDisabledModuleColor() {
        return this.disabledModuleColor;
    }
}

