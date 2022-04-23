/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.gui.csgo.component.impl.sub.color;

import club.tifality.gui.csgo.SkeetUI;
import club.tifality.gui.csgo.component.ButtonComponent;
import club.tifality.gui.csgo.component.Component;
import club.tifality.gui.csgo.component.ExpandableComponent;
import club.tifality.gui.csgo.component.PredicateComponent;
import club.tifality.property.ValueChangeListener;
import club.tifality.utils.render.LockedResolution;
import club.tifality.utils.render.RenderingUtils;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public abstract class ColorPickerComponent
extends ButtonComponent
implements PredicateComponent,
ExpandableComponent {
    private boolean expanded;
    private float hue;
    private float saturation;
    private float brightness;
    private float alpha;
    private boolean colorSelectorDragging;
    private boolean hueSelectorDragging;
    private boolean alphaSelectorDragging;

    public ColorPickerComponent(Component parent, float x, float y, float width, float height) {
        super(parent, x, y, width, height);
    }

    private static void drawCheckeredBackground(float x, float y, float x2, float y2) {
        Gui.drawRect(x, y, x2, y2, SkeetUI.getColor(0xFFFFFF));
        boolean offset = false;
        while (y < y2) {
            offset = !offset;
            for (float x1 = x + (float)(offset ? true : false); x1 < x2; x1 += 2.0f) {
                if (x1 > x2 - 1.0f) continue;
                Gui.drawRect(x1, y, x1 + 1.0f, y + 1.0f, SkeetUI.getColor(0x808080));
            }
            y += 1.0f;
        }
    }

    @Override
    public void drawComponent(LockedResolution resolution, int mouseX, int mouseY) {
        float x = this.getX();
        float y = this.getY();
        float width = this.getWidth();
        float height = this.getHeight();
        int black = SkeetUI.getColor(0);
        Gui.drawRect((double)x - 0.5, (double)y - 0.5, (double)(x + width) + 0.5, (double)(y + height) + 0.5, black);
        int guiAlpha = (int)SkeetUI.getAlpha();
        int color = this.getColor();
        int colorAlpha = color >> 24 & 0xFF;
        int minAlpha = Math.min(guiAlpha, colorAlpha);
        if (colorAlpha < 255) {
            ColorPickerComponent.drawCheckeredBackground(x, y, x + width, y + height);
        }
        int newColor = new Color(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, minAlpha).getRGB();
        Gui.drawGradientRect(x, y, x + width, y + height, newColor, RenderingUtils.darker(newColor, 0.6f));
        if (this.isExpanded()) {
            float hueSelectorY;
            float hueSliderYDif;
            float alphaSliderBottom;
            float hueSliderRight;
            GL11.glTranslated(0.0, 0.0, 3.0);
            float expandedX = this.getExpandedX();
            float expandedY = this.getExpandedY();
            float expandedWidth = this.getExpandedWidth();
            float expandedHeight = this.getExpandedHeight();
            Gui.drawRect(expandedX, expandedY, expandedX + expandedWidth, expandedY + expandedHeight, black);
            Gui.drawRect((double)expandedX + 0.5, (double)expandedY + 0.5, (double)(expandedX + expandedWidth) - 0.5, (double)(expandedY + expandedHeight) - 0.5, SkeetUI.getColor(0x39393B));
            Gui.drawRect(expandedX + 1.0f, expandedY + 1.0f, expandedX + expandedWidth - 1.0f, expandedY + expandedHeight - 1.0f, SkeetUI.getColor(0x232323));
            float colorPickerSize = expandedWidth - 9.0f - 8.0f;
            float colorPickerLeft = expandedX + 3.0f;
            float colorPickerTop = expandedY + 3.0f;
            float colorPickerRight = colorPickerLeft + colorPickerSize;
            float colorPickerBottom = colorPickerTop + colorPickerSize;
            int selectorWhiteOverlayColor = new Color(255, 255, 255, Math.min(guiAlpha, 180)).getRGB();
            if ((float)mouseX <= colorPickerLeft || (float)mouseY <= colorPickerTop || (float)mouseX >= colorPickerRight || (float)mouseY >= colorPickerBottom) {
                this.colorSelectorDragging = false;
            }
            Gui.drawRect((double)colorPickerLeft - 0.5, (double)colorPickerTop - 0.5, (double)colorPickerRight + 0.5, (double)colorPickerBottom + 0.5, SkeetUI.getColor(0));
            this.drawColorPickerRect(colorPickerLeft, colorPickerTop, colorPickerRight, colorPickerBottom);
            float hueSliderLeft = this.saturation * (colorPickerRight - colorPickerLeft);
            float alphaSliderTop = (1.0f - this.brightness) * (colorPickerBottom - colorPickerTop);
            if (this.colorSelectorDragging) {
                hueSliderRight = colorPickerRight - colorPickerLeft;
                alphaSliderBottom = (float)mouseX - colorPickerLeft;
                this.saturation = alphaSliderBottom / hueSliderRight;
                hueSliderLeft = alphaSliderBottom;
                hueSliderYDif = colorPickerBottom - colorPickerTop;
                hueSelectorY = (float)mouseY - colorPickerTop;
                this.brightness = 1.0f - hueSelectorY / hueSliderYDif;
                alphaSliderTop = hueSelectorY;
                this.updateColor(Color.HSBtoRGB(this.hue, this.saturation, this.brightness), false);
            }
            hueSliderRight = colorPickerLeft + hueSliderLeft - 0.5f;
            alphaSliderBottom = colorPickerTop + alphaSliderTop - 0.5f;
            hueSliderYDif = colorPickerLeft + hueSliderLeft + 0.5f;
            hueSelectorY = colorPickerTop + alphaSliderTop + 0.5f;
            Gui.drawRect(hueSliderRight - 0.5f, alphaSliderBottom - 0.5f, hueSliderRight, hueSelectorY + 0.5f, black);
            Gui.drawRect(hueSliderYDif, alphaSliderBottom - 0.5f, hueSliderYDif + 0.5f, hueSelectorY + 0.5f, black);
            Gui.drawRect(hueSliderRight, alphaSliderBottom - 0.5f, hueSliderYDif, alphaSliderBottom, black);
            Gui.drawRect(hueSliderRight, hueSelectorY, hueSliderYDif, hueSelectorY + 0.5f, black);
            Gui.drawRect(hueSliderRight, alphaSliderBottom, hueSliderYDif, hueSelectorY, selectorWhiteOverlayColor);
            hueSliderLeft = colorPickerRight + 3.0f;
            hueSliderRight = hueSliderLeft + 8.0f;
            if ((float)mouseX <= hueSliderLeft || (float)mouseY <= colorPickerTop || (float)mouseX >= hueSliderRight || (float)mouseY >= colorPickerBottom) {
                this.hueSelectorDragging = false;
            }
            hueSliderYDif = colorPickerBottom - colorPickerTop;
            hueSelectorY = (1.0f - this.hue) * hueSliderYDif;
            if (this.hueSelectorDragging) {
                float inc = (float)mouseY - colorPickerTop;
                this.hue = 1.0f - inc / hueSliderYDif;
                hueSelectorY = inc;
                this.updateColor(Color.HSBtoRGB(this.hue, this.saturation, this.brightness), false);
            }
            Gui.drawRect((double)hueSliderLeft - 0.5, (double)colorPickerTop - 0.5, (double)hueSliderRight + 0.5, (double)colorPickerBottom + 0.5, black);
            float hsHeight = colorPickerBottom - colorPickerTop;
            float alphaSelectorX = hsHeight / 5.0f;
            float asLeft = colorPickerTop;
            int i = 0;
            while ((float)i < 5.0f) {
                boolean last = (float)i == 4.0f;
                Gui.drawGradientRect(hueSliderLeft, asLeft, hueSliderRight, asLeft + alphaSelectorX, SkeetUI.getColor(Color.HSBtoRGB(1.0f - 0.2f * (float)i, 1.0f, 1.0f)), SkeetUI.getColor(Color.HSBtoRGB(1.0f - 0.2f * (float)(i + 1), 1.0f, 1.0f)));
                if (!last) {
                    asLeft += alphaSelectorX;
                }
                ++i;
            }
            float hsTop = colorPickerTop + hueSelectorY - 0.5f;
            float asRight = colorPickerTop + hueSelectorY + 0.5f;
            Gui.drawRect(hueSliderLeft - 0.5f, hsTop - 0.5f, hueSliderLeft, asRight + 0.5f, black);
            Gui.drawRect(hueSliderRight, hsTop - 0.5f, hueSliderRight + 0.5f, asRight + 0.5f, black);
            Gui.drawRect(hueSliderLeft, hsTop - 0.5f, hueSliderRight, hsTop, black);
            Gui.drawRect(hueSliderLeft, asRight, hueSliderRight, asRight + 0.5f, black);
            Gui.drawRect(hueSliderLeft, hsTop, hueSliderRight, asRight, selectorWhiteOverlayColor);
            alphaSliderTop = colorPickerBottom + 3.0f;
            alphaSliderBottom = alphaSliderTop + 8.0f;
            if ((float)mouseX <= colorPickerLeft || (float)mouseY <= alphaSliderTop || (float)mouseX >= colorPickerRight || (float)mouseY >= alphaSliderBottom) {
                this.alphaSelectorDragging = false;
            }
            int z = Color.HSBtoRGB(this.hue, this.saturation, this.brightness);
            int r = z >> 16 & 0xFF;
            int g = z >> 8 & 0xFF;
            int b = z & 0xFF;
            hsHeight = colorPickerRight - colorPickerLeft;
            alphaSelectorX = this.alpha * hsHeight;
            if (this.alphaSelectorDragging) {
                asLeft = (float)mouseX - colorPickerLeft;
                this.alpha = asLeft / hsHeight;
                alphaSelectorX = asLeft;
                this.updateColor(new Color(r, g, b, (int)(this.alpha * 255.0f)).getRGB(), true);
            }
            Gui.drawRect((double)colorPickerLeft - 0.5, (double)alphaSliderTop - 0.5, (double)colorPickerRight + 0.5, (double)alphaSliderBottom + 0.5, black);
            ColorPickerComponent.drawCheckeredBackground(colorPickerLeft, alphaSliderTop, colorPickerRight, alphaSliderBottom);
            RenderingUtils.drawGradientRect(colorPickerLeft, alphaSliderTop, colorPickerRight, alphaSliderBottom, true, new Color(r, g, b, 0).getRGB(), new Color(r, g, b, Math.min(guiAlpha, 255)).getRGB());
            asLeft = colorPickerLeft + alphaSelectorX - 0.5f;
            asRight = colorPickerLeft + alphaSelectorX + 0.5f;
            Gui.drawRect(asLeft - 0.5f, alphaSliderTop, asRight + 0.5f, alphaSliderBottom, black);
            Gui.drawRect(asLeft, alphaSliderTop, asRight, alphaSliderBottom, selectorWhiteOverlayColor);
            GL11.glTranslated(0.0, 0.0, -3.0);
        }
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        super.onMouseClick(mouseX, mouseY, button);
        if (this.isExpanded() && button == 0) {
            float expandedX = this.getExpandedX();
            float expandedY = this.getExpandedY();
            float expandedWidth = this.getExpandedWidth();
            float expandedHeight = this.getExpandedHeight();
            float colorPickerSize = expandedWidth - 9.0f - 8.0f;
            float colorPickerLeft = expandedX + 3.0f;
            float colorPickerTop = expandedY + 3.0f;
            float colorPickerRight = colorPickerLeft + colorPickerSize;
            float colorPickerBottom = colorPickerTop + colorPickerSize;
            float alphaSliderTop = colorPickerBottom + 3.0f;
            float alphaSliderBottom = alphaSliderTop + 8.0f;
            float hueSliderLeft = colorPickerRight + 3.0f;
            float hueSliderRight = hueSliderLeft + 8.0f;
            this.colorSelectorDragging = !this.colorSelectorDragging && (float)mouseX > colorPickerLeft && (float)mouseY > colorPickerTop && (float)mouseX < colorPickerRight && (float)mouseY < colorPickerBottom;
            this.alphaSelectorDragging = !this.alphaSelectorDragging && (float)mouseX > colorPickerLeft && (float)mouseY > alphaSliderTop && (float)mouseX < colorPickerRight && (float)mouseY < alphaSliderBottom;
            this.hueSelectorDragging = !this.hueSelectorDragging && (float)mouseX > hueSliderLeft && (float)mouseY > colorPickerTop && (float)mouseX < hueSliderRight && (float)mouseY < colorPickerBottom;
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

    private void updateColor(int hex, boolean hasAlpha) {
        if (hasAlpha) {
            this.setColor(hex);
        } else {
            this.setColor(new Color(hex >> 16 & 0xFF, hex >> 8 & 0xFF, hex & 0xFF, (int)(this.alpha * 255.0f)).getRGB());
        }
    }

    public abstract int getColor();

    public abstract void setColor(int var1);

    public abstract void addValueChangeListener(ValueChangeListener<Integer> var1);

    public void onValueChange(int oldValue, int value) {
        float[] hsb = this.getHSBFromColor(value);
        this.hue = hsb[0];
        this.saturation = hsb[1];
        this.brightness = hsb[2];
        this.alpha = (float)(value >> 24 & 0xFF) / 255.0f;
    }

    private float[] getHSBFromColor(int hex) {
        int r = hex >> 16 & 0xFF;
        int g = hex >> 8 & 0xFF;
        int b = hex & 0xFF;
        return Color.RGBtoHSB(r, g, b, null);
    }

    private void drawColorPickerRect(float left, float top, float right, float bottom) {
        int hueBasedColor = SkeetUI.getColor(Color.HSBtoRGB(this.hue, 1.0f, 1.0f));
        RenderingUtils.drawGradientRect(left, top, right, bottom, true, SkeetUI.getColor(0xFFFFFF), hueBasedColor);
        Gui.drawGradientRect(left, top, right, bottom, 0, SkeetUI.getColor(0));
    }

    @Override
    public float getExpandedX() {
        return this.getX() + this.getWidth() - 80.333336f;
    }

    @Override
    public float getExpandedY() {
        return this.getY() + this.getHeight();
    }

    @Override
    public boolean isExpanded() {
        return this.expanded;
    }

    @Override
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public void onPress(int mouseButton) {
        if (mouseButton == 1) {
            this.setExpanded(!this.isExpanded());
        }
    }

    @Override
    public float getExpandedWidth() {
        float right = this.getX() + this.getWidth();
        return right - this.getExpandedX();
    }

    @Override
    public float getExpandedHeight() {
        return this.getExpandedWidth();
    }
}

