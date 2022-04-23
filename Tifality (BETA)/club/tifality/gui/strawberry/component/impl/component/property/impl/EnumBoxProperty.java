/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.strawberry.component.impl.component.property.impl;

import club.tifality.gui.strawberry.ClickGui;
import club.tifality.gui.strawberry.component.Component;
import club.tifality.gui.strawberry.component.impl.ExpandableComponent;
import club.tifality.gui.strawberry.component.impl.component.property.PropertyComponent;
import club.tifality.module.impl.render.hud.Hud;
import club.tifality.property.impl.EnumProperty;
import club.tifality.utils.StringUtils;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public final class EnumBoxProperty
extends ExpandableComponent
implements PropertyComponent {
    private final EnumProperty<?> property;

    public EnumBoxProperty(Component parent, EnumProperty<?> property, int x, int y, int width, int height) {
        super(parent, property.getLabel(), x, y, width, height);
        this.property = property;
    }

    public EnumProperty<?> getProperty() {
        return this.property;
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        super.drawComponent(scaledResolution, mouseX, mouseY);
        int x = this.getX();
        int y = this.getY();
        int width = this.getWidth();
        int height = this.getHeight();
        int textColor = 0xFFFFFF;
        int bgColor = this.getSecondaryBackgroundColor(this.isHovered(mouseX, mouseY));
        Gui.drawRect(x, y, x + width + 1, y + height, bgColor);
        Gui.drawRect(x - 2, y, x, y + height, (int)Hud.hudColor.get());
        if (this.isExpanded()) {
            Gui.drawRect(x - 2, y + height, x + 1, y + this.getHeightWithExpand(), (int)Hud.hudColor.get());
            Gui.drawRect(x + 1, y + height, x + width - 1 + 2, y + this.getHeightWithExpand(), ClickGui.getInstance().getPalette().getSecondaryBackgroundColor().getRGB());
            this.handleRender(x, y + this.getHeight() + 2, width);
        }
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.getName(), x + 2, (float)y + (float)this.getHeight() / 2.0f - 3.0f, textColor);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.isExpanded() ? "<" : ">", x + width - 6, (float)y + (float)height / 2.0f - 4.0f, new Color(255, 255, 255).getRGB());
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        super.onMouseClick(mouseX, mouseY, button);
        if (this.isExpanded()) {
            this.handleClick(mouseX, mouseY, this.getX(), this.getY() + this.getHeight() + 2, this.getWidth());
        }
    }

    private <T extends Enum<T>> void handleRender(int x, int y, int width) {
        EnumProperty<?> property = this.property;
        for (Enum e : property.getValues()) {
            if (property.isSelected(e)) {
                Gui.drawRect(x + 1, y - 2, x + width - 1 + 2, y + 15 - 3 - 2, new Color(0, 0, 0, 95).getRGB());
            }
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(StringUtils.upperSnakeCaseToPascal(e.name()), x + 1 + 2, y, property.isSelected(e) ? Hud.hudColor.get().intValue() : new Color(255, 255, 255).getRGB());
            y += 12;
        }
    }

    private <T extends Enum<T>> void handleClick(int mouseX, int mouseY, int x, int y, int width) {
        EnumProperty<?> property = this.property;
        for (Enum e : property.getValues()) {
            if (mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + 15 - 3) {
                property.setValue(e);
            }
            y += 12;
        }
    }

    @Override
    public int getHeightWithExpand() {
        return this.getHeight() + this.property.getValues().length * 12;
    }

    @Override
    public void onPress(int mouseX, int mouseY, int button) {
    }

    @Override
    public boolean canExpand() {
        return this.property.getValues().length > 1;
    }
}

