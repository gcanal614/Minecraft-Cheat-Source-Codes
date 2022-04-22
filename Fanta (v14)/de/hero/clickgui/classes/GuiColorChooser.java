/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package de.hero.clickgui.classes;

import java.awt.Color;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

public class GuiColorChooser
extends Gui {
    public double x;
    public double y;
    private int width = 100;
    private int height = 80;
    private double hueChooserX;
    private double colorChooserX;
    private double colorChooserY;
    public int color = Color.decode("#FFFFFF").getRGB();
    public float[] hsbValues = new float[3];

    public GuiColorChooser(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = Color.red.getRGB();
    }

    public GuiColorChooser(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
        Color c = new Color(color);
        this.hsbValues = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), this.hsbValues);
        this.setHueChooserByHue(this.hsbValues[0]);
        this.setHueChooserBySB(this.hsbValues[1], this.hsbValues[2]);
    }

    public void draw(int mouseX, int mouseY) {
        int backGroundColor = new Color(38, 38, 38, 150).getRGB();
        GuiColorChooser.drawRect2(this.x, this.y, this.x + (double)this.width, this.y + (double)this.height, backGroundColor);
        int chooserWidth = this.width - 10;
        float i = 0.0f;
        while (i < (float)chooserWidth) {
            float f = 1.0f / (float)chooserWidth * i;
            GuiColorChooser.drawRect2(this.x + 5.0 + (double)i, this.y + (double)this.height - 12.75, this.x + 5.0 + (double)i + 0.5, this.y + (double)this.height - 8.25, Color.HSBtoRGB(f, 1.0f, 1.0f));
            i = (float)((double)i + 0.5);
        }
        int hsbChooserWidth = this.width - 10;
        int hsbChooserHeight = this.height - 25;
        float e = 0.0f;
        while (e < (float)hsbChooserWidth) {
            float f = 0.0f;
            while (f < (float)hsbChooserHeight) {
                float xPos = (float)(this.x + 5.0 + (double)e);
                float yPos = f;
                float satuartion = 1.0f / (float)hsbChooserWidth * e;
                float brightness = 1.0f / (float)hsbChooserHeight * f;
                GuiColorChooser.drawRect2(xPos, this.y + 5.0 + (double)hsbChooserHeight - (double)yPos - 1.0, xPos + 1.0f, this.y + 5.0 + (double)hsbChooserHeight - (double)yPos + 1.0 - 1.0, Color.HSBtoRGB(this.hsbValues[0], satuartion, brightness));
                f += 1.0f;
            }
            e += 1.0f;
        }
        this.drawHollowRect(this.x + 4.5, this.y + 4.5, (double)(this.width - 9), (double)(this.height - 24), 0.5, Color.white.getRGB());
        int max = 255;
        Color onlyHue = new Color(Color.HSBtoRGB(this.hsbValues[0], 1.0f, 1.0f));
        int hueChooserColor = new Color(max - onlyHue.getRed(), max - onlyHue.getGreen(), max - onlyHue.getBlue()).getRGB();
        GuiColorChooser.drawRect2(this.x + 5.0 + this.hueChooserX, this.y + (double)this.height - 12.75, this.x + 5.0 + this.hueChooserX + 0.5, this.y + (double)this.height - 8.25, hueChooserColor);
        Color allColor = new Color(Color.HSBtoRGB(this.hsbValues[0], this.hsbValues[1], this.hsbValues[2]));
        int colorChooserColor = new Color(max - allColor.getRed(), max - allColor.getGreen(), max - allColor.getBlue()).getRGB();
        GuiColorChooser.drawRect2(this.x + 5.0 + this.colorChooserX - 2.5, this.y + 5.0 + this.colorChooserY - 0.25, this.x + 5.0 + this.colorChooserX + 2.5, this.y + 5.0 + this.colorChooserY + 0.25, colorChooserColor);
        GuiColorChooser.drawRect2(this.x + 5.0 + this.colorChooserX - 0.25, this.y + 5.0 + this.colorChooserY - 2.5, this.x + 5.0 + this.colorChooserX + 0.25, this.y + 5.0 + this.colorChooserY + 2.5, colorChooserColor);
        if (Mouse.isButtonDown((int)0)) {
            if ((double)mouseY >= this.y + 5.0 + (double)this.height - 20.0 && (double)mouseY <= this.y + 5.0 + (double)this.height - 10.0) {
                double diff = (double)mouseX - this.x - 5.0;
                if (diff > (double)this.width - 10.5) {
                    diff = (double)this.width - 10.5;
                }
                if (diff < 0.0) {
                    diff = 0.0;
                }
                this.hueChooserX = diff;
                this.setHueChooserByHue((float)((double)(1.0f / (float)(this.width - 10)) * this.hueChooserX));
            }
            if ((double)mouseX >= this.x + 5.0 && (double)mouseX <= this.x + (double)this.width - 5.0 && (double)mouseY >= this.y + 5.0 && (double)mouseY <= this.y + (double)this.height - 20.0) {
                double diffY;
                double diffX = (double)mouseX - this.x - 5.0;
                if (diffX > (double)(this.width - 10)) {
                    diffX = this.width - 10;
                }
                if (diffX < 0.0) {
                    diffX = 0.0;
                }
                if ((diffY = (double)mouseY - this.y - 5.0) > 55.0) {
                    diffY = 55.0;
                }
                if (diffY < 0.25) {
                    diffY = 0.25;
                }
                this.colorChooserX = diffX;
                this.colorChooserY = diffY;
                this.hsbValues[1] = (float)((double)(1.0f / (float)(this.width - 10)) * this.colorChooserX);
                this.hsbValues[2] = 1.0f - (float)(0.0181818176060915 * this.colorChooserY);
            }
        }
        this.color = Color.HSBtoRGB(this.hsbValues[0], this.hsbValues[1], this.hsbValues[2]);
    }

    public void setHue(float hue) {
        if (hue > 1.0f) {
            hue = 1.0f;
        }
        this.hsbValues[0] = hue;
    }

    public void setHueChooserByHue(float hue) {
        this.hueChooserX = (float)(this.width - 10) * hue;
        this.setHue(hue);
    }

    public void setHueChooserBySB(float s, float b) {
        this.colorChooserX = (float)(this.width - 10) * s;
        this.colorChooserY = 55.0f - 55.0f * b;
    }

    public void setSaturation(float sat) {
        if (sat > 1.0f) {
            sat = 1.0f;
        }
        this.hsbValues[1] = sat;
    }

    public void setBrightness(float bright) {
        if (bright > 1.0f) {
            bright = 1.0f;
        }
        this.hsbValues[2] = bright;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

