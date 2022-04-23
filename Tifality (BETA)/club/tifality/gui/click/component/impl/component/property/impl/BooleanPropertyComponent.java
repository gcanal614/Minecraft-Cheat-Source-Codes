/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.click.component.impl.component.property.impl;

import club.tifality.gui.click.component.Component;
import club.tifality.gui.click.component.impl.component.property.PropertyComponent;
import club.tifality.property.Property;
import club.tifality.utils.Wrapper;
import club.tifality.utils.render.OGLUtils;
import club.tifality.utils.render.RenderingUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

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
        int middleHeight = this.getHeight() / 2;
        int btnRight = x + 3 + middleHeight;
        float maxWidth = Wrapper.getFontRenderer().getWidth(this.getName()) + (float)middleHeight + 6.0f;
        boolean hovered = this.isHovered(mouseX, mouseY);
        boolean tooWide = maxWidth > (float)this.getWidth();
        boolean needScissorBox = tooWide && !hovered;
        Gui.drawRect(x, y, (float)x + (tooWide && hovered ? maxWidth : (float)this.getWidth()), y + this.getHeight(), this.getSecondaryBackgroundColor(hovered));
        if (needScissorBox) {
            OGLUtils.startScissorBox(RenderingUtils.getScaledResolution(), x, y, this.getWidth(), this.getHeight());
        }
        Wrapper.getFontRenderer().drawStringWithShadow(this.getName(), btnRight + 3, y + middleHeight - 3, 0xFFFFFF);
        if (needScissorBox) {
            OGLUtils.endScissorBox();
        }
        this.buttonLeft = x + 2;
        this.buttonTop = y + middleHeight - middleHeight / 2;
        this.buttonRight = btnRight;
        this.buttonBottom = y + middleHeight + middleHeight / 2 + 2;
        Gui.drawRect(this.buttonLeft, this.buttonTop, this.buttonRight, this.buttonBottom, -9737365);
        Gui.drawRect((double)this.buttonLeft + 0.5, (double)this.buttonTop + 0.5, (double)this.buttonRight - 0.5, (double)this.buttonBottom - 0.5, -12828863);
        if (this.booleanProperty.getValue().booleanValue()) {
            GL11.glPushMatrix();
            GL11.glTranslatef(this.buttonLeft + 1, this.buttonTop + 4, 1.0f);
            OGLUtils.startBlending();
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glLineWidth(1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(3553);
            GL11.glBegin(3);
            GL11.glVertex2d(0.0, 0.0);
            GL11.glVertex2d(2.0, 2.0);
            GL11.glVertex2d(6.0, -2.0);
            GL11.glEnd();
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            OGLUtils.endBlending();
            GL11.glPopMatrix();
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

