/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.csgo.component.impl.sub.checkBox;

import club.tifality.gui.csgo.SkeetUI;
import club.tifality.gui.csgo.component.ButtonComponent;
import club.tifality.gui.csgo.component.Component;
import club.tifality.gui.csgo.component.PredicateComponent;
import club.tifality.utils.render.LockedResolution;
import club.tifality.utils.render.RenderingUtils;
import java.awt.Color;
import net.minecraft.client.gui.Gui;

public abstract class CheckBoxComponent
extends ButtonComponent
implements PredicateComponent {
    public CheckBoxComponent(Component parent, float x, float y, float width, float height) {
        super(parent, x, y, width, height);
    }

    @Override
    public void drawComponent(LockedResolution resolution, int mouseX, int mouseY) {
        float x = this.getX();
        float y = this.getY();
        float width = this.getWidth();
        float height = this.getHeight();
        Gui.drawRect(x, y, x + width, y + height, new Color(11, 11, 11, (int)SkeetUI.getAlpha()).getRGB());
        boolean checked = this.isChecked();
        boolean hovered = this.isHovered(mouseX, mouseY);
        RenderingUtils.drawGradientRect(x + 0.5f, y + 0.5f, x + width - 0.5f, y + height - 0.5f, false, checked ? SkeetUI.getColor() : SkeetUI.getColor(hovered ? RenderingUtils.darker(0x494949, 1.4f) : 0x494949), checked ? RenderingUtils.darker(SkeetUI.getColor(), 0.8f) : SkeetUI.getColor(hovered ? RenderingUtils.darker(0x303030, 1.4f) : 0x303030));
    }

    @Override
    public void onPress(int mouseButton) {
        if (mouseButton == 0) {
            this.setChecked(!this.isChecked());
        }
    }

    public abstract boolean isChecked();

    public abstract void setChecked(boolean var1);
}

