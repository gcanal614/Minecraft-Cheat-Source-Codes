/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.click.component.impl.component.property.impl;

import club.tifality.gui.click.ClickGui;
import club.tifality.gui.click.component.Component;
import club.tifality.gui.click.component.impl.ExpandableComponent;
import club.tifality.gui.click.component.impl.component.property.PropertyComponent;
import club.tifality.property.impl.EnumProperty;
import club.tifality.utils.StringUtils;
import club.tifality.utils.Wrapper;
import club.tifality.utils.render.OGLUtils;
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
        String selectedText = StringUtils.upperSnakeCaseToPascal(((Enum)this.property.getValue()).name());
        int dropDownBoxY = y + 10;
        boolean needScissor = Wrapper.getFontRenderer().getWidth(selectedText) > (float)(width - 4);
        int textColor = 0xFFFFFF;
        int bgColor = this.getSecondaryBackgroundColor(this.isHovered(mouseX, mouseY));
        Gui.drawRect(x, y, x + width, y + height, bgColor);
        Wrapper.getFontRenderer().drawStringWithShadow(this.getName(), x + 2, y + 1, textColor);
        Gui.drawRect((double)(x + 2), (double)dropDownBoxY, (double)(x + this.getWidth() - 2), (double)dropDownBoxY + 10.5, -9737365);
        Gui.drawRect((double)x + 2.5, (double)dropDownBoxY + 0.5, (double)(x + this.getWidth()) - 2.5, (double)(dropDownBoxY + 10), -12828863);
        if (needScissor) {
            OGLUtils.startScissorBox(scaledResolution, x + 2, dropDownBoxY + 2, width - 5, 10);
        }
        Wrapper.getFontRenderer().drawStringWithShadow(selectedText, (float)x + 3.5f, (float)dropDownBoxY + 1.5f, textColor);
        if (needScissor) {
            OGLUtils.endScissorBox();
        }
        if (this.isExpanded()) {
            Gui.drawRect(x + 1, y + height, x + width - 1, y + this.getHeightWithExpand(), ClickGui.getInstance().getPalette().getSecondaryBackgroundColor().getRGB());
            this.handleRender(x, y + this.getHeight() + 2, width, textColor);
        }
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        super.onMouseClick(mouseX, mouseY, button);
        if (this.isExpanded()) {
            this.handleClick(mouseX, mouseY, this.getX(), this.getY() + this.getHeight() + 2, this.getWidth());
        }
    }

    private <T extends Enum<T>> void handleRender(int x, int y, int width, int textColor) {
        int enabledColor = ClickGui.getInstance().getPalette().getEnabledModuleColor().getRGB();
        EnumProperty<?> property = this.property;
        for (Enum e : property.getValues()) {
            if (property.isSelected(e)) {
                Gui.drawRect(x + 1, y - 2, x + width - 1, y + 15 - 3 - 2, enabledColor);
            }
            Wrapper.getFontRenderer().drawStringWithShadow(StringUtils.upperSnakeCaseToPascal(e.name()), x + 1 + 2, y, textColor);
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

