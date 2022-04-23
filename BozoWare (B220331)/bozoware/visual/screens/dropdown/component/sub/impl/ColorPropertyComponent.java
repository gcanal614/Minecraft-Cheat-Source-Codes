// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.visual.screens.dropdown.component.sub.impl;

import bozoware.base.util.visual.RenderUtil;
import bozoware.base.util.visual.ColorUtil;
import org.lwjgl.opengl.GL11;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import bozoware.impl.property.ColorProperty;
import bozoware.visual.screens.dropdown.component.sub.ModuleButtonComponent;
import bozoware.visual.screens.dropdown.component.Component;

public class ColorPropertyComponent extends Component
{
    private int offset;
    private final ModuleButtonComponent parent;
    private final ColorProperty property;
    private boolean active;
    
    public ColorPropertyComponent(final ColorProperty property, final ModuleButtonComponent parent, final int offset) {
        this.property = property;
        this.parent = parent;
        this.offset = offset;
    }
    
    @Override
    public void onDrawScreen(final int mouseX, final int mouseY) {
        final double x = this.parent.getParentFrame().getX();
        final double y = this.parent.getParentFrame().getY() + this.offset;
        Gui.drawRectWithWidth(x, y, 115.0, 14.0, -15395563);
        final float[] hsb = Color.RGBtoHSB(this.property.getPropertyValue().getRed(), this.property.getPropertyValue().getGreen(), this.property.getPropertyValue().getBlue(), null);
        drawHueSlider(x, y);
        Gui.drawRectWithWidth(x + hsb[0] * 115.0f, y, 2.0, 14.0, -16777216);
        Gui.drawRectWithWidth(x + hsb[0] * 115.0f + 0.5, y + 0.5, 1.0, 13.0, -1);
        this.getFontRenderer().drawStringWithShadow(this.property.getPropertyLabel(), x + 3.0, y + 4.0, -1);
        if (!this.isHovering(mouseX, mouseY)) {
            this.active = false;
        }
        if (this.active) {
            final Color newColor = new Color(Color.HSBtoRGB((float)((mouseX - x) / 115.0), hsb[1], hsb[2]));
            final Color settingColor = new Color(newColor.getRed(), newColor.getGreen(), newColor.getBlue(), this.property.getPropertyValue().getAlpha());
            this.property.setPropertyValue(settingColor);
        }
        super.onDrawScreen(mouseX, mouseY);
    }
    
    private static void drawHueSlider(final double x, final double y) {
        GL11.glTranslated(x, y, 0.0);
        final int[] colours = { -65536, -256, -16711936, -16711681, -16776961, -65281, -65536 };
        final double segment = 115.0 / colours.length;
        for (int i = 0; i < colours.length; ++i) {
            final int colour = colours[i];
            final int top = (i != 0) ? ColorUtil.interpolateColors(new Color(colours[i - 1]), new Color(colour), 0.5f).getRGB() : colour;
            final int bottom = (i + 1 < colours.length) ? ColorUtil.interpolateColors(new Color(colour), new Color(colours[i + 1]), 0.5f).getRGB() : colour;
            final double start = segment * i;
            RenderUtil.glHorizontalGradientQuad(start, 0.0, segment, 14.0, top, bottom);
        }
        GL11.glTranslated(-x, -y, 0.0);
    }
    
    private boolean isHovering(final int mouseX, final int mouseY) {
        final double x = this.parent.getParentFrame().getX();
        final double y = this.parent.getParentFrame().getY() + this.offset;
        return mouseX >= x && mouseX <= x + 115.0 && mouseY >= y && mouseY <= y + 14.0;
    }
    
    @Override
    public void onMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isHovering(mouseX, mouseY)) {
            this.active = true;
        }
    }
    
    @Override
    public boolean isHidden() {
        return this.property.isHidden();
    }
    
    @Override
    public double getHeight() {
        return 28.0;
    }
    
    @Override
    public void onMouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        this.active = false;
    }
    
    @Override
    public void setOffset(final int offset) {
        this.offset = offset;
    }
}
