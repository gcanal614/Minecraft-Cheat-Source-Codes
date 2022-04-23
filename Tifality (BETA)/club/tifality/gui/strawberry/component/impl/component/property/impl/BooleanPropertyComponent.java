/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.strawberry.component.impl.component.property.impl;

import club.tifality.gui.strawberry.component.Component;
import club.tifality.gui.strawberry.component.impl.component.property.PropertyComponent;
import club.tifality.module.impl.render.hud.Hud;
import club.tifality.property.Property;
import club.tifality.utils.render.OGLUtils;
import club.tifality.utils.render.RenderingUtils;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public final class BooleanPropertyComponent
extends Component
implements PropertyComponent {
    private final Property<Boolean> booleanProperty;
    private int buttonLeft;
    private int buttonTop;
    private int buttonRight;
    private int buttonBottom;

    public BooleanPropertyComponent(Component parent, Property<Boolean> booleanProperty, int x, int y, int width, int height) {
        super(parent, booleanProperty.getLabel(), x, y, width, height);
        this.booleanProperty = booleanProperty;
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        int x = this.getX();
        int y = this.getY();
        int width = this.getWidth();
        int middleHeight = this.getHeight() / 2;
        int btnRight = x + width - 1;
        float maxWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.getName()) + middleHeight + 6;
        boolean hovered = this.isHovered(mouseX, mouseY);
        boolean tooWide = maxWidth > (float)this.getWidth();
        boolean needScissorBox = tooWide && !hovered;
        Gui.drawRect(x, y, (float)x + (tooWide && hovered ? maxWidth : (float)this.getWidth()) + 1.0f, y + this.getHeight(), this.getSecondaryBackgroundColor(hovered));
        Gui.drawRect(x - 2, y, x, y + this.getHeight(), (int)Hud.hudColor.get());
        if (this.booleanProperty.getValue().booleanValue()) {
            this.buttonLeft = x + width - 9;
            this.buttonTop = y + middleHeight - middleHeight / 2;
            this.buttonRight = btnRight;
            this.buttonBottom = y + middleHeight + middleHeight / 2 + 2;
            Gui.drawRect(this.buttonLeft, this.buttonTop, this.buttonRight, this.buttonBottom, (int)Hud.hudColor.get());
            Gui.drawRect((double)this.buttonLeft + 0.5, (double)this.buttonTop + 0.5, (double)this.buttonRight - 0.5, (double)this.buttonBottom - 0.5, (int)Hud.hudColor.get());
        } else {
            this.buttonLeft = x + width - 9;
            this.buttonTop = y + middleHeight - middleHeight / 2;
            this.buttonRight = btnRight;
            this.buttonBottom = y + middleHeight + middleHeight / 2 + 2;
            Gui.drawRect(this.buttonLeft, this.buttonTop, this.buttonRight, this.buttonBottom, new Color(255, 255, 255).getRGB());
            Gui.drawRect((double)this.buttonLeft + 0.5, (double)this.buttonTop + 0.5, (double)this.buttonRight - 0.5, (double)this.buttonBottom - 0.5, new Color(255, 255, 255).getRGB());
        }
        if (needScissorBox) {
            OGLUtils.startScissorBox(RenderingUtils.getScaledResolution(), x, y, this.getWidth(), this.getHeight());
        }
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.getName(), x + 2, y + middleHeight - 3, 0xFFFFFF);
        if (needScissorBox) {
            OGLUtils.endScissorBox();
        }
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        if (button == 0 && mouseX > this.buttonLeft && mouseY > this.buttonTop && mouseX < this.buttonRight && mouseY < this.buttonBottom) {
            this.booleanProperty.setValue(this.booleanProperty.getValue() == false);
        }
    }

    @Override
    public Property<?> getProperty() {
        return this.booleanProperty;
    }
}

