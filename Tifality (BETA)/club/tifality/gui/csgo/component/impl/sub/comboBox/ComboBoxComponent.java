/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.csgo.component.impl.sub.comboBox;

import club.tifality.gui.csgo.SkeetUI;
import club.tifality.gui.csgo.component.ButtonComponent;
import club.tifality.gui.csgo.component.Component;
import club.tifality.gui.csgo.component.ExpandableComponent;
import club.tifality.gui.csgo.component.PredicateComponent;
import club.tifality.utils.StringUtils;
import club.tifality.utils.render.LockedResolution;
import club.tifality.utils.render.OGLUtils;
import club.tifality.utils.render.RenderingUtils;
import java.awt.Color;
import java.util.List;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public abstract class ComboBoxComponent
extends ButtonComponent
implements PredicateComponent,
ExpandableComponent {
    private boolean expanded;

    public ComboBoxComponent(Component parent, float x, float y, float width, float height) {
        super(parent, x, y, width, height);
    }

    private String getDisplayString() {
        if (!this.isMultiSelectable()) {
            return StringUtils.upperSnakeCaseToPascal(this.getValue().name());
        }
        List<Enum<?>> values2 = this.getMultiSelectValues();
        int len = values2.size();
        switch (len) {
            case 0: {
                return "";
            }
            case 1: {
                return StringUtils.upperSnakeCaseToPascal(values2.get(0).name());
            }
        }
        StringBuilder sb = new StringBuilder(StringUtils.upperSnakeCaseToPascal(values2.get(0).name())).append(", ");
        for (int i = 1; i < len; ++i) {
            sb.append(StringUtils.upperSnakeCaseToPascal(values2.get(i).name()));
            if (i == len - 1) continue;
            sb.append(", ");
        }
        return sb.toString();
    }

    @Override
    public void drawComponent(LockedResolution lockedResolution, int mouseX, int mouseY) {
        float x = this.getX();
        float y = this.getY();
        float width = this.getWidth();
        float height = this.getHeight();
        Gui.drawRect(x, y, x + width, y + height, new Color(10, 10, 10, (int)SkeetUI.getAlpha()).getRGB());
        boolean hovered = this.isHovered(mouseX, mouseY);
        if (hovered) {
            Gui.drawRect(x + 0.5f, y + 0.5f, x + width - 0.5f, y + height - 0.5f, new Color(90, 90, 90, (int)SkeetUI.getAlpha()).getRGB());
        }
        if (hovered) {
            RenderingUtils.drawGradientRect(x + 1.0f, y + 1.0f, x + width - 1.0f, y + height - 1.0f, false, new Color(31, 31, 31, (int)SkeetUI.getAlpha()).getRGB(), new Color(36, 36, 36, (int)SkeetUI.getAlpha()).getRGB());
        } else {
            RenderingUtils.drawGradientRect(x + 0.5f, y + 0.5f, x + width - 0.5f, y + height - 0.5f, false, new Color(31, 31, 31, (int)SkeetUI.getAlpha()).getRGB(), new Color(36, 36, 36, (int)SkeetUI.getAlpha()).getRGB());
        }
        GL11.glColor4f(0.6f, 0.6f, 0.6f, (float)SkeetUI.getAlpha() / 255.0f);
        RenderingUtils.drawArrow(x + width - 5.0f, y + height / 2.0f - 0.5f, 3.0f, this.isExpanded());
        if (SkeetUI.shouldRenderText()) {
            GL11.glEnable(3089);
            OGLUtils.startScissorBox(lockedResolution, (int)x + 2, (int)y + 1, (int)width - 8, (int)height - 1);
            SkeetUI.FONT_RENDERER.drawString(this.getDisplayString(), x + 2.0f, y + height / 3.0f, SkeetUI.getColor(0x969696));
            GL11.glDisable(3089);
        }
        GL11.glTranslatef(0.0f, 0.0f, 2.0f);
        if (this.expanded) {
            Enum<?>[] enums;
            Enum<?>[] values2 = this.getValues();
            float dropDownHeight = (float)values2.length * height;
            Gui.drawRect(x, y + height, x + width, y + height + dropDownHeight + 0.5f, SkeetUI.getColor(855309));
            float valueBoxHeight = height;
            Enum<?>[] array = enums = this.getValues();
            int length = enums.length;
            for (int i = 0; i < length; ++i) {
                Enum<?> value = array[i];
                boolean valueBoxHovered = (float)mouseX >= x && (float)mouseY >= y + valueBoxHeight && (float)mouseX <= x + width && (float)mouseY < y + valueBoxHeight + height;
                Gui.drawRect(x + 0.5f, y + valueBoxHeight, x + width - 0.5f, y + valueBoxHeight + height, SkeetUI.getColor(valueBoxHovered ? RenderingUtils.darker(0x232323, 0.5f) : 0x232323));
                boolean selected = this.isMultiSelectable() ? this.getMultiSelectValues().contains(value) : value == this.getValue();
                int color = selected ? SkeetUI.getColor() : new Color(190, 190, 190, (int)SkeetUI.getAlpha()).getRGB();
                SkeetUI.GROUP_BOX_HEADER_RENDERER.drawString(StringUtils.upperSnakeCaseToPascal(value.name()), x + 2.0f, y + valueBoxHeight + 4.0f, valueBoxHovered ? new Color(230, 230, 230, (int)SkeetUI.getAlpha()).getRGB() : color, true);
                valueBoxHeight += height;
            }
        }
        GL11.glTranslatef(0.0f, 0.0f, -2.0f);
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        if (this.isHovered(mouseX, mouseY)) {
            this.onPress(button);
        }
        if (this.expanded && button == 0) {
            float x = this.getX();
            float y = this.getY();
            float height = this.getHeight();
            float width = this.getWidth();
            float valueBoxHeight = height;
            for (int i = 0; i < this.getValues().length; ++i) {
                if ((float)mouseX >= x && (float)mouseY >= y + valueBoxHeight && (float)mouseX <= x + width && (float)mouseY < y + valueBoxHeight + height) {
                    this.setValue(i);
                    if (!this.isMultiSelectable()) {
                        this.expandOrClose();
                    }
                    return;
                }
                valueBoxHeight += height;
            }
        }
    }

    private void expandOrClose() {
        this.setExpanded(!this.isExpanded());
    }

    @Override
    public void onPress(int mouseButton) {
        if (mouseButton == 1) {
            this.expandOrClose();
        }
    }

    @Override
    public float getExpandedX() {
        return this.getX();
    }

    @Override
    public float getExpandedY() {
        return this.getY();
    }

    public abstract Enum<?> getValue();

    public abstract void setValue(int var1);

    public abstract List<Enum<?>> getMultiSelectValues();

    public abstract boolean isMultiSelectable();

    public abstract Enum<?>[] getValues();

    @Override
    public boolean isExpanded() {
        return this.expanded;
    }

    @Override
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public float getExpandedWidth() {
        return this.getWidth();
    }

    @Override
    public float getExpandedHeight() {
        float height = this.getHeight();
        return height + (float)this.getValues().length * height + height;
    }
}

