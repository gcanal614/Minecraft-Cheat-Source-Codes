/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.csgo.component;

import club.tifality.gui.csgo.component.Component;

public abstract class ButtonComponent
extends Component {
    public ButtonComponent(Component parent, float x, float y, float width, float height) {
        super(parent, x, y, width, height);
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        if (this.isHovered(mouseX, mouseY)) {
            this.onPress(button);
        }
        super.onMouseClick(mouseX, mouseY, button);
    }

    public abstract void onPress(int var1);
}

