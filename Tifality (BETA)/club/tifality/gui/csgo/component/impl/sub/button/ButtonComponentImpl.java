/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.csgo.component.impl.sub.button;

import club.tifality.gui.csgo.SkeetUI;
import club.tifality.gui.csgo.component.ButtonComponent;
import club.tifality.gui.csgo.component.Component;
import club.tifality.utils.render.LockedResolution;
import club.tifality.utils.render.RenderingUtils;
import java.util.function.Consumer;
import net.minecraft.client.gui.Gui;

public final class ButtonComponentImpl
extends ButtonComponent {
    private final String text;
    private final Consumer<Integer> onPress;

    public ButtonComponentImpl(Component parent, String text, Consumer<Integer> onPress, float width, float height) {
        super(parent, 0.0f, 0.0f, width, height);
        this.text = text;
        this.onPress = onPress;
    }

    @Override
    public void drawComponent(LockedResolution lockedResolution, int mouseX, int mouseY) {
        float x = this.getX();
        float y = this.getY();
        float width = this.getWidth();
        float height = this.getHeight();
        boolean hovered = this.isHovered(mouseX, mouseY);
        Gui.drawRect(x, y, x + width, y + height, SkeetUI.getColor(0x111111));
        Gui.drawRect(x + 0.5f, y + 0.5f, x + width - 0.5f, y + height - 0.5f, SkeetUI.getColor(0x262626));
        RenderingUtils.drawGradientRect(x + 1.0f, y + 1.0f, x + width - 1.0f, y + height - 1.0f, false, SkeetUI.getColor(hovered ? RenderingUtils.darker(0x222222, 1.2f) : 0x222222), SkeetUI.getColor(hovered ? RenderingUtils.darker(0x1E1E1E, 1.2f) : 0x1E1E1E));
        if (SkeetUI.shouldRenderText()) {
            RenderingUtils.drawOutlinedString(SkeetUI.FONT_RENDERER, this.text, x + width / 2.0f - SkeetUI.FONT_RENDERER.getWidth(this.text) / 2.0f, y + height / 2.0f - 1.0f, SkeetUI.getColor(0xFFFFFF), SkeetUI.getColor(0));
        }
    }

    @Override
    public void onPress(int mouseButton) {
        this.onPress.accept(mouseButton);
    }
}

