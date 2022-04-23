/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.csgo.component;

import club.tifality.gui.csgo.SkeetUI;
import club.tifality.gui.csgo.component.Component;
import club.tifality.utils.render.LockedResolution;

public abstract class TabComponent
extends Component {
    private final String name;

    public TabComponent(Component parent, String name, float x, float y, float width, float height) {
        super(parent, x, y, width, height);
        this.setupChildren();
        this.name = name;
    }

    public abstract void setupChildren();

    @Override
    public void drawComponent(LockedResolution resolution, int mouseX, int mouseY) {
        SkeetUI.FONT_RENDERER.drawStringWithShadow(this.name, this.getX() + 8.0f, this.getY() + 8.0f - 3.0f, SkeetUI.getColor(0xFFFFFF));
        float x = 8.0f;
        for (int i = 0; i < this.children.size(); ++i) {
            Component child = (Component)this.children.get(i);
            child.setX(x);
            if (i < 3) {
                child.setY(14.0f);
            }
            child.drawComponent(resolution, mouseX, mouseY);
            x += 102.333336f;
            if (x + 8.0f + 94.333336f > 315.0f) {
                x = 8.0f;
            }
            if (i <= 2) continue;
            int above = i - 3;
            int totalY = 14;
            do {
                Component componentAbove = this.getChildren().get(above);
                totalY += (int)(componentAbove.getHeight() + 8.0f);
            } while ((above -= 3) >= 0);
            child.setY(totalY);
        }
    }
}

