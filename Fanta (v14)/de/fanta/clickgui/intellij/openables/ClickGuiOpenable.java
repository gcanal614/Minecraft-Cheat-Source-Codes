/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package de.fanta.clickgui.intellij.openables;

import de.fanta.gui.font.ClientFont;
import de.fanta.gui.font.GlyphPageFontRenderer;
import de.fanta.utils.RenderUtil;
import java.awt.Color;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class ClickGuiOpenable
extends Gui {
    protected float x;
    protected float y;
    protected float baseHeight;
    protected float width;
    protected float height;
    protected List<ClickGuiOpenable> children;
    protected boolean extended = false;
    protected Type type;
    protected String name;
    protected long lastMS = System.currentTimeMillis();
    protected Runnable runnable;
    public static final GlyphPageFontRenderer MENU_FONT = ClientFont.font(19, ClientFont.FontType.ARIAL, true);

    public ClickGuiOpenable(float x, float y, float baseHeight, float width, List<ClickGuiOpenable> children, Type type, String name) {
        this.x = x;
        this.y = y;
        this.baseHeight = baseHeight;
        this.width = width;
        this.height = this.baseHeight;
        this.children = children;
        this.type = type;
        this.name = name;
        this.lastMS = System.currentTimeMillis();
    }

    public void draw(float mouseX, float mouseY) {
        Minecraft mc = Minecraft.getMinecraft();
        boolean hovered = false;
        if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.baseHeight) {
            hovered = true;
        }
        if (hovered && System.currentTimeMillis() - this.lastMS >= 100L) {
            switch (this.type) {
                case FOLDER: {
                    if (Mouse.isButtonDown((int)0) || Mouse.isButtonDown((int)1)) {
                        this.extended = !this.extended;
                    }
                    this.lastMS = System.currentTimeMillis();
                    break;
                }
                case CLASS: {
                    try {
                        Mouse.isButtonDown((int)0);
                        if (Mouse.isButtonDown((int)1)) {
                            this.runnable.run();
                        }
                        this.lastMS = System.currentTimeMillis();
                    }
                    catch (Exception exception) {}
                    break;
                }
            }
        }
        switch (this.type) {
            case FOLDER: {
                int add = 0;
                for (ClickGuiOpenable child : this.children) {
                    child.x = this.x + 10.0f;
                    child.y = this.y + this.baseHeight + (float)add;
                    if (this.extended) {
                        child.draw(mouseX, mouseY);
                    }
                    add = (int)((float)add + child.getHeight());
                }
                float f = this.height = this.extended ? this.baseHeight + (float)add : this.baseHeight;
                if (this.extended) {
                    RenderUtil.draw2dLine(this.x + 3.0f, this.y + 5.0f, this.x + 7.0f, this.y + 9.0f, Color.white);
                    RenderUtil.draw2dLine(this.x + 11.0f, this.y + 5.0f, this.x + 7.0f, this.y + 9.0f, Color.white);
                } else {
                    RenderUtil.draw2dLine(this.x + 5.0f, this.y + 3.0f, this.x + 9.0f, this.y + 7.0f, Color.white);
                    RenderUtil.draw2dLine(this.x + 5.0f, this.y + 11.0f, this.x + 9.0f, this.y + 7.0f, Color.white);
                }
                GlStateManager.color(1.0f, 1.0f, 1.0f);
                mc.getTextureManager().bindTexture(new ResourceLocation("textures/folder.png"));
                ClickGuiOpenable.drawModalRectWithCustomSizedTexture(this.x + 15.0f, this.y + 2.0f, 0.0f, 0.0f, 12.0f, 12.0f, 12.0f, 12.0f);
                MENU_FONT.drawString(this.name, this.x + 28.0f, this.y + 2.0f, Color.white.getRGB());
                this.width = 28 + MENU_FONT.getStringWidth(this.name) + 2;
                break;
            }
            case CLASS: {
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0f, 1.0f, 1.0f);
                mc.getTextureManager().bindTexture(new ResourceLocation("textures/class.png"));
                ClickGuiOpenable.drawModalRectWithCustomSizedTexture(this.x + 3.0f, this.y + 2.0f, 0.0f, 0.0f, 12.0f, 12.0f, 12.0f, 12.0f);
                GlStateManager.popMatrix();
                MENU_FONT.drawString(this.name, this.x + 16.0f, this.y + 2.0f, Color.white.getRGB());
                this.width = 16 + MENU_FONT.getStringWidth(this.name) + 2;
                break;
            }
        }
    }

    public void setRunnable(Runnable runner) {
        this.runnable = runner;
    }

    public float getBaseHeight() {
        return this.baseHeight;
    }

    public List<ClickGuiOpenable> getChildren() {
        return this.children;
    }

    public float getHeight() {
        return this.height;
    }

    public long getLastMS() {
        return this.lastMS;
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return this.type;
    }

    public float getWidth() {
        return this.width;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public static enum Type {
        FOLDER,
        CLASS;

    }
}

