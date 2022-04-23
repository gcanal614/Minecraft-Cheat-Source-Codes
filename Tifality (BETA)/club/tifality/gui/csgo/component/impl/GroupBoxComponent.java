/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.csgo.component.impl;

import club.tifality.gui.csgo.SkeetUI;
import club.tifality.gui.csgo.component.Component;
import club.tifality.gui.csgo.component.PredicateComponent;
import club.tifality.gui.csgo.component.impl.sub.comboBox.ComboBoxComponent;
import club.tifality.gui.csgo.component.impl.sub.comboBox.ComboBoxTextComponent;
import club.tifality.gui.csgo.component.impl.sub.key.KeyBindComponent;
import club.tifality.utils.render.LockedResolution;
import java.awt.Color;
import net.minecraft.client.gui.Gui;

public final class GroupBoxComponent
extends Component {
    private final String name;

    public GroupBoxComponent(Component parent, String name, float x, float y, float width, float height) {
        super(parent, x, y, width, height);
        this.name = name;
    }

    @Override
    public void drawComponent(LockedResolution resolution, int mouseX, int mouseY) {
        float x = this.getX();
        float y = this.getY();
        float width = this.getWidth();
        float height = this.getHeight();
        float length = SkeetUI.GROUP_BOX_HEADER_RENDERER.getWidth(this.name);
        Gui.drawRect(x, y, x + width, y + height, new Color(10, 10, 10, (int)SkeetUI.getAlpha()).getRGB());
        Gui.drawRect(x + 0.5f, y + 0.5f, x + width - 0.5f, y + height - 0.5f, new Color(48, 48, 48, (int)SkeetUI.getAlpha()).getRGB());
        Gui.drawRect(x + 4.0f, y, x + 4.0f + length + 2.0f, y + 1.0f, new Color(17, 17, 17, (int)SkeetUI.getAlpha()).getRGB());
        Gui.drawRect(x + 1.0f, y + 1.0f, x + width - 1.0f, y + height - 1.0f, SkeetUI.getColor(0x171717));
        if (SkeetUI.shouldRenderText()) {
            SkeetUI.GROUP_BOX_HEADER_RENDERER.drawString(this.name, x + 5.0f, y - 0.5f, SkeetUI.getColor(0xDCDCDC), true);
        }
        float childYLeft = 6.0f;
        float childYRight = 6.0f;
        boolean left = true;
        for (Component component : this.children) {
            PredicateComponent predicateComponent;
            if (!(component instanceof PredicateComponent) ? component instanceof KeyBindComponent : !(predicateComponent = (PredicateComponent)((Object)component)).isVisible()) continue;
            if (component.getWidth() >= 80.333336f) {
                component.setX(3.0f);
                component.setY(childYLeft);
                component.drawComponent(resolution, mouseX, mouseY);
                float yOffset = component.getHeight() + 4.0f;
                childYLeft += yOffset;
                childYRight += yOffset;
                left = true;
                continue;
            }
            component.setX(left ? 3.0f : 49.166668f);
            component.setY(left ? childYLeft : childYRight);
            component.drawComponent(resolution, mouseX, mouseY);
            if (left) {
                childYLeft += component.getHeight() + 4.0f;
            } else {
                childYRight += component.getHeight() + 4.0f;
            }
            left = childYRight >= childYLeft;
        }
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        for (Component child : this.children) {
            ComboBoxTextComponent comboBoxTextComponent;
            ComboBoxComponent comboBox;
            if (!(child instanceof ComboBoxTextComponent) || !(comboBox = (comboBoxTextComponent = (ComboBoxTextComponent)child).getComboBoxComponent()).isExpanded()) continue;
            float x = comboBox.getX();
            float y = comboBoxTextComponent.getY() + comboBoxTextComponent.getHeight();
            if (!((float)mouseX >= x) || !((float)mouseY > y) || !((float)mouseX <= x + comboBox.getWidth()) || !((float)mouseY < y + comboBox.getExpandedHeight())) continue;
            comboBoxTextComponent.onMouseClick(mouseX, mouseY, button);
            return;
        }
        super.onMouseClick(mouseX, mouseY, button);
    }

    @Override
    public float getHeight() {
        float initHeight;
        float heightLeft = initHeight = super.getHeight();
        float heightRight = initHeight;
        boolean left = true;
        for (Component component : this.getChildren()) {
            PredicateComponent predicateComponent;
            if (component instanceof PredicateComponent && !(predicateComponent = (PredicateComponent)((Object)component)).isVisible()) continue;
            if (component.getWidth() >= 80.333336f) {
                float yOffset = component.getHeight() + 4.0f;
                heightLeft += yOffset;
                heightLeft += yOffset;
                left = true;
                continue;
            }
            if (left) {
                heightLeft += component.getHeight() + 4.0f;
            } else {
                heightRight += component.getHeight() + 4.0f;
            }
            left = heightRight >= heightLeft;
        }
        float heightWithComponents = Math.max(heightLeft, heightRight);
        return heightWithComponents - initHeight > initHeight ? heightWithComponents : initHeight;
    }
}

