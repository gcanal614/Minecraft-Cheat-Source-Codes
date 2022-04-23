/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.click.component.impl.component.property.impl;

import club.tifality.gui.click.ClickGui;
import club.tifality.gui.click.component.Component;
import club.tifality.gui.click.component.impl.ExpandableComponent;
import club.tifality.gui.click.component.impl.component.property.PropertyComponent;
import club.tifality.property.Property;
import club.tifality.utils.Wrapper;
import club.tifality.utils.render.OGLUtils;
import club.tifality.utils.render.RenderingUtils;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class ColorPropertyComponent
extends ExpandableComponent
implements PropertyComponent {
    private static final int COLOR_PICKER_HEIGHT = 80;
    public static Tessellator tessellator = Tessellator.getInstance();
    public static WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    private final Property<Integer> colorProperty;
    private float hue;
    private float saturation;
    private float brightness;
    private float alpha;
    private boolean colorSelectorDragging;
    private boolean hueSelectorDragging;
    private boolean alphaSelectorDragging;

    public ColorPropertyComponent(Component parent, Property<Integer> colorProperty, int x, int y, int width, int height) {
        super(parent, colorProperty.getLabel(), x, y, width, height);
        this.colorProperty = colorProperty;
        this.colorProperty.addValueChangeListener((oldValue, value) -> {
            float[] hsb = this.getHSBFromColor((int)value);
            this.hue = hsb[0];
            this.saturation = hsb[1];
            this.brightness = hsb[2];
            this.alpha = (float)(value >> 24 & 0xFF) / 255.0f;
        });
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
        boolean hovered = this.isHovered(mouseX, mouseY);
        Gui.drawRect(x, y, x + width, y + height, bgColor);
        Wrapper.getFontRenderer().drawStringWithShadow(this.getName(), x + 2, (float)y + (float)height / 2.0f - 3.0f, textColor);
        float left = x + width - 13;
        float top = (float)y + (float)height / 2.0f - 2.0f;
        float right = x + width - 2;
        float bottom = (float)y + (float)height / 2.0f + 2.0f;
        Gui.drawRect(left - 0.5f, top - 0.5f, right + 0.5f, bottom + 0.5f, -16777216);
        this.drawCheckeredBackground(left, top, right, bottom);
        Gui.drawRect(left, top, right, bottom, (int)this.colorProperty.getValue());
        if (this.isExpanded()) {
            Gui.drawRect(x + 1, y + height, x + width - 1, y + this.getHeightWithExpand(), ClickGui.getInstance().getPalette().getSecondaryBackgroundColor().getRGB());
            float cpLeft = x + 2;
            float cpTop = y + height + 2;
            float cpRight = x + 80 - 2;
            float cpBottom = y + height + 80 - 2;
            if ((float)mouseX <= cpLeft || (float)mouseY <= cpTop || (float)mouseX >= cpRight || (float)mouseY >= cpBottom) {
                this.colorSelectorDragging = false;
            }
            float colorSelectorX = this.saturation * (cpRight - cpLeft);
            float colorSelectorY = (1.0f - this.brightness) * (cpBottom - cpTop);
            if (this.colorSelectorDragging) {
                float wWidth = cpRight - cpLeft;
                float xDif = (float)mouseX - cpLeft;
                this.saturation = xDif / wWidth;
                colorSelectorX = xDif;
                float hHeight = cpBottom - cpTop;
                float yDif = (float)mouseY - cpTop;
                this.brightness = 1.0f - yDif / hHeight;
                colorSelectorY = yDif;
                this.updateColor(Color.HSBtoRGB(this.hue, this.saturation, this.brightness), false);
            }
            Gui.drawRect(cpLeft, cpTop, cpRight, cpBottom, -16777216);
            this.drawColorPickerRect(cpLeft + 0.5f, cpTop + 0.5f, cpRight - 0.5f, cpBottom - 0.5f);
            float selectorWidth = 2.0f;
            float outlineWidth = 0.5f;
            float half = selectorWidth / 2.0f;
            float csLeft = cpLeft + colorSelectorX - half;
            float csTop = cpTop + colorSelectorY - half;
            float csRight = cpLeft + colorSelectorX + half;
            float csBottom = cpTop + colorSelectorY + half;
            Gui.drawRect(csLeft - outlineWidth, csTop - outlineWidth, csRight + outlineWidth, csBottom + outlineWidth, -16777216);
            Gui.drawRect(csLeft, csTop, csRight, csBottom, Color.HSBtoRGB(this.hue, this.saturation, this.brightness));
            float sLeft = x + 80 - 1;
            float sTop = y + height + 2;
            float sRight = sLeft + 5.0f;
            float sBottom = y + height + 80 - 2;
            if ((float)mouseX <= sLeft || (float)mouseY <= sTop || (float)mouseX >= sRight || (float)mouseY >= sBottom) {
                this.hueSelectorDragging = false;
            }
            float hueSelectorY = this.hue * (sBottom - sTop);
            if (this.hueSelectorDragging) {
                float hsHeight = sBottom - sTop;
                float yDif = (float)mouseY - sTop;
                this.hue = yDif / hsHeight;
                hueSelectorY = yDif;
                this.updateColor(Color.HSBtoRGB(this.hue, this.saturation, this.brightness), false);
            }
            Gui.drawRect(sLeft, sTop, sRight, sBottom, -16777216);
            float inc = 0.2f;
            float times = 1.0f / inc;
            float sHeight = sBottom - sTop;
            float sY = sTop + 0.5f;
            float size = sHeight / times;
            int i = 0;
            while ((float)i < times) {
                boolean last;
                boolean bl = last = (float)i == times - 1.0f;
                if (last) {
                    size -= 1.0f;
                }
                Gui.drawGradientRect(sLeft + 0.5f, sY, sRight - 0.5f, sY + size, Color.HSBtoRGB(inc * (float)i, 1.0f, 1.0f), Color.HSBtoRGB(inc * (float)(i + 1), 1.0f, 1.0f));
                if (!last) {
                    sY += size;
                }
                ++i;
            }
            float selectorHeight = 2.0f;
            float outlineWidth2 = 0.5f;
            float half2 = selectorHeight / 2.0f;
            float csTop2 = sTop + hueSelectorY - half2;
            float csBottom2 = sTop + hueSelectorY + half2;
            Gui.drawRect(sLeft - outlineWidth2, csTop2 - outlineWidth2, sRight + outlineWidth2, csBottom2 + outlineWidth2, -16777216);
            Gui.drawRect(sLeft, csTop2, sRight, csBottom2, Color.HSBtoRGB(this.hue, 1.0f, 1.0f));
            sLeft = x + 80 + 6;
            sTop = y + height + 2;
            sRight = sLeft + 5.0f;
            sBottom = y + height + 80 - 2;
            if ((float)mouseX <= sLeft || (float)mouseY <= sTop || (float)mouseX >= sRight || (float)mouseY >= sBottom) {
                this.alphaSelectorDragging = false;
            }
            int color = Color.HSBtoRGB(this.hue, this.saturation, this.brightness);
            int r = color >> 16 & 0xFF;
            int g = color >> 8 & 0xFF;
            int b = color & 0xFF;
            float alphaSelectorY = this.alpha * (sBottom - sTop);
            if (this.alphaSelectorDragging) {
                float hsHeight = sBottom - sTop;
                float yDif = (float)mouseY - sTop;
                this.alpha = yDif / hsHeight;
                alphaSelectorY = yDif;
                this.updateColor(new Color(r, g, b, (int)(this.alpha * 255.0f)).getRGB(), true);
            }
            Gui.drawRect(sLeft, sTop, sRight, sBottom, -16777216);
            this.drawCheckeredBackground(sLeft + 0.5f, sTop + 0.5f, sRight - 0.5f, sBottom - 0.5f);
            Gui.drawGradientRect(sLeft + 0.5f, sTop + 0.5f, sRight - 0.5f, sBottom - 0.5f, new Color(r, g, b, 0).getRGB(), new Color(r, g, b, 255).getRGB());
            float selectorHeight2 = 2.0f;
            float outlineWidth3 = 0.5f;
            float half3 = selectorHeight2 / 2.0f;
            float csTop3 = sTop + alphaSelectorY - half3;
            float csBottom3 = sTop + alphaSelectorY + half3;
            float bx = sRight + outlineWidth3;
            float ay = csTop3 - outlineWidth3;
            float by = csBottom3 + outlineWidth3;
            GL11.glDisable(3553);
            RenderingUtils.color(-16777216);
            GL11.glBegin(2);
            GL11.glVertex2f(sLeft, ay);
            GL11.glVertex2f(sLeft, by);
            GL11.glVertex2f(bx, by);
            GL11.glVertex2f(bx, ay);
            GL11.glEnd();
            GL11.glEnable(3553);
            float xOff = 93.0f;
            float sLeft2 = (float)x + xOff;
            float sTop2 = y + height + 2;
            float sRight2 = sLeft2 + (float)width - xOff - 3.0f;
            float sBottom2 = (float)(y + height) + 40.0f + 8.0f;
            Gui.drawRect(sLeft2, sTop2, sRight2, sBottom2, -16777216);
            this.drawCheckeredBackground(sLeft2 + 0.5f, sTop2 + 0.5f, sRight2 - 0.5f, sBottom2 - 0.5f);
            Gui.drawRect(sLeft2 + 4.0f, sTop2 + 4.0f, sRight2 - 4.0f, sBottom2 - 4.0f, (int)this.colorProperty.getValue());
        }
    }

    private void drawCheckeredBackground(float x, float y, float x2, float y2) {
        Gui.drawRect(x, y, x2, y2, -1);
        boolean off = false;
        while (y < y2) {
            off = !off;
            for (float x1 = x + (float)(off ? true : false); x1 < x2; x1 += 2.0f) {
                Gui.drawRect(x1, y, x1 + 1.0f, y + 1.0f, -8355712);
            }
            y += 1.0f;
        }
    }

    private void updateColor(int hex, boolean hasAlpha) {
        if (hasAlpha) {
            this.colorProperty.setValue(hex);
        } else {
            this.colorProperty.setValue(new Color(hex >> 16 & 0xFF, hex >> 8 & 0xFF, hex & 0xFF, (int)(this.alpha * 255.0f)).getRGB());
        }
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        super.onMouseClick(mouseX, mouseY, button);
        if (this.isExpanded() && button == 0) {
            int x = this.getX();
            int y = this.getY();
            float cpLeft = x + 2;
            float cpTop = y + this.getHeight() + 2;
            float cpRight = x + 80 - 2;
            float cpBottom = y + this.getHeight() + 80 - 2;
            float sLeft = x + 80 - 1;
            float sTop = y + this.getHeight() + 2;
            float sRight = sLeft + 5.0f;
            float sBottom = y + this.getHeight() + 80 - 2;
            float asLeft = x + 80 + 6;
            float asTop = y + this.getHeight() + 2;
            float asRight = asLeft + 5.0f;
            float asBottom = y + this.getHeight() + 80 - 2;
            this.colorSelectorDragging = !this.colorSelectorDragging && (float)mouseX > cpLeft && (float)mouseY > cpTop && (float)mouseX < cpRight && (float)mouseY < cpBottom;
            this.hueSelectorDragging = !this.hueSelectorDragging && (float)mouseX > sLeft && (float)mouseY > sTop && (float)mouseX < sRight && (float)mouseY < sBottom;
            this.alphaSelectorDragging = !this.alphaSelectorDragging && (float)mouseX > asLeft && (float)mouseY > asTop && (float)mouseX < asRight && (float)mouseY < asBottom;
        }
    }

    @Override
    public void onMouseRelease(int button) {
        if (this.hueSelectorDragging) {
            this.hueSelectorDragging = false;
        } else if (this.colorSelectorDragging) {
            this.colorSelectorDragging = false;
        } else if (this.alphaSelectorDragging) {
            this.alphaSelectorDragging = false;
        }
    }

    private float[] getHSBFromColor(int hex) {
        int r = hex >> 16 & 0xFF;
        int g = hex >> 8 & 0xFF;
        int b = hex & 0xFF;
        return Color.RGBtoHSB(r, g, b, null);
    }

    public void drawColorPickerRect(float left, float top, float right, float bottom) {
        int hueBasedColor = Color.HSBtoRGB(this.hue, 1.0f, 1.0f);
        GL11.glDisable(3553);
        OGLUtils.startBlending();
        GL11.glShadeModel(7425);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(right, top, 0.0).color(hueBasedColor).endVertex();
        worldrenderer.pos(left, top, 0.0).color(-1).endVertex();
        worldrenderer.pos(left, bottom, 0.0).color(-1).endVertex();
        worldrenderer.pos(right, bottom, 0.0).color(hueBasedColor).endVertex();
        tessellator.draw();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(right, top, 0.0).color(0x18000000).endVertex();
        worldrenderer.pos(left, top, 0.0).color(0x18000000).endVertex();
        worldrenderer.pos(left, bottom, 0.0).color(-16777216).endVertex();
        worldrenderer.pos(right, bottom, 0.0).color(-16777216).endVertex();
        tessellator.draw();
        OGLUtils.endBlending();
        GL11.glShadeModel(7424);
        GL11.glEnable(3553);
    }

    @Override
    public boolean canExpand() {
        return true;
    }

    @Override
    public int getHeightWithExpand() {
        return this.getHeight() + 80;
    }

    @Override
    public void onPress(int mouseX, int mouseY, int button) {
    }

    public Property<Integer> getProperty() {
        return this.colorProperty;
    }
}

