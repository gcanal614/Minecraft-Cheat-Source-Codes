package net.minecraft.client.gui;

import de.tired.api.util.font.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class GuiButton2 extends Gui {
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");

    /**
     * Button width in pixels
     */
    protected int width;

    /**
     * Button height in pixels
     */
    protected int height;

    /**
     * The x position of this control.
     */
    public int xPosition;

    /**
     * The y position of this control.
     */
    public int yPosition;

    /**
     * The string displayed on this control.
     */
    public String displayString;
    public int id;

    /**
     * True if this control is enabled, false to disable.
     */
    public boolean enabled;

    /**
     * Hides the button completely if false.
     */
    public double radius = 2.5;
    public boolean visible;
    protected boolean hovered;

    public boolean alternativRender = false;

    public GuiButton2(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public GuiButton2(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
    }

    public boolean isAlternativRender() {
        return alternativRender;
    }

    public void setAlternativRender(boolean alternativRender) {
        this.alternativRender = alternativRender;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean mouseOver) {
        int i = 1;

        if (!this.enabled) {
            i = 0;
        } else if (mouseOver) {
            i = 2;
        }

        return i;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY, boolean text, boolean... other) {

        double MAX_VALUE = 2.7;
        if (hovered) {
            if (radius != MAX_VALUE) {
                radius += .8;
            }
        }
        if (radius > MAX_VALUE) {
            radius = MAX_VALUE;
        }

        if (!hovered && radius > 2) {
            radius -= 0.2;
        }

            FontRenderer fontrenderer = mc.fontRendererObj;
            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            //   BlurUtil.BLUR.renderBlurScissor(xPosition, yPosition, width, height);



            //   this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i * 20, this.width / 2, this.height);
            //   this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
            this.mouseDragged(mc, mouseX, mouseY);

            if (hovered) {
                //  Gui.drawRect(xPosition, yPosition, xPosition + width, yPosition + height, Integer.MIN_VALUE);
            }

            FontManager.confortaa.drawCenteredString(this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 2) / 2 + 2, new Color(20, 20, 20).getRGB());
            FontManager.confortaa.drawCenteredString(this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 2) / 2 + 1, new Color(30, 30, 30).getRGB());
            FontManager.confortaa.drawCenteredString(this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 2) / 2, -1);

            drawOtherShit();

    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
    }

    protected void drawOtherShit() {

    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY) {
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    /**
     * Whether the mouse cursor is currently over the button.
     */
    public boolean isMouseOver() {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY) {
    }

    public void playPressSound(SoundHandler soundHandlerIn) {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

    public int getButtonWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
