package net.minecraft.client.gui;

import java.awt.Color;
import java.awt.Font;

import me.Hime;
import me.font.TTFFontRenderer;
import me.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButton extends Gui
{
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");

    /** Button width in pixels */
    protected int width;
    public static TTFFontRenderer ttfr = new TTFFontRenderer(new Font("Verdana", 100, 14));
    public static TTFFontRenderer ttfrb = new TTFFontRenderer(new Font("Verdana", 100, 50));
    public static TTFFontRenderer ttfrm = new TTFFontRenderer(new Font("Verdana", 100, 30));
    public static TTFFontRenderer ttfrs = new TTFFontRenderer(new Font("Verdana", 100, 20));
    public static TTFFontRenderer sigmab = new TTFFontRenderer(new Font("Roboto-Medium", 100, 30));
    public static TTFFontRenderer sigmas = new TTFFontRenderer(new Font("Roboto-Medium", 100, 20));
    
    public static TTFFontRenderer astolfo2 = new TTFFontRenderer(new Font("Roboto-Medium", 100, 15));
    public static TTFFontRenderer astolfo1 = new TTFFontRenderer(new Font("Roboto-Regular", 100, 16));
    
    public static TTFFontRenderer eeee = new TTFFontRenderer(new Font("Roboto-Regular", 100, 18));
    
    public static TTFFontRenderer aaaa = new TTFFontRenderer(new Font("Comfortaa-Light", 100, 18));
    public static TTFFontRenderer ssss = new TTFFontRenderer(new Font("Comfortaa-Bold", 100, 14));
    public static TTFFontRenderer moon2 = new TTFFontRenderer(new Font("Comfortaa-Bold", 100, 50));
    public static TTFFontRenderer moon = new TTFFontRenderer(new Font("Comfortaa-Light", 100, 30));
    
    public static TTFFontRenderer jello1 = new TTFFontRenderer(new Font("jellolight", 100, 35));
    public static TTFFontRenderer jello2 = new TTFFontRenderer(new Font("jellolight", 100, 20));
    
    public static TTFFontRenderer esp = new TTFFontRenderer(new Font("Roboto-Light", 100, 24));
    public static TTFFontRenderer jellowork = new TTFFontRenderer(new Font("HelveticaLight", 100, 40));
    public static TTFFontRenderer sgsmall = new TTFFontRenderer(new Font("Segoe UI", 100, 18));
    
    public static TTFFontRenderer appleBig = new TTFFontRenderer(new Font("Apple", 100, 25));
    public static TTFFontRenderer appleSmall = new TTFFontRenderer(new Font("Apple", 100, 18));
    
    public static TTFFontRenderer notification = new TTFFontRenderer(new Font("notification", 100, 63));

    /** Button height in pixels */
    protected int height;

    /** The x position of this control. */
    public int xPosition;

    /** The y position of this control. */
    public int yPosition;

    /** The string displayed on this control. */
    public String displayString;
    public int id;

    /** True if this control is enabled, false to disable. */
    public boolean enabled;

    /** Hides the button completely if false. */
    public boolean visible;
    protected boolean hovered;

    public GuiButton(int buttonId, int x, int y, String buttonText)
    {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
    {
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

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean mouseOver)
    {
        int i = 1;

        if (!this.enabled)
        {
            i = 0;
        }
        else if (mouseOver)
        {
            i = 2;
        }

        return i;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRendererObj;
            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = this.getHoverState(this.hovered);
            //GlStateManager.enableBlend();
            //GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            //GlStateManager.blendFunc(770, 771);
            RenderUtil.drawRoundedRect2(xPosition, yPosition, width, height, 5, new Color(0, 0, 0, 200));
            this.mouseDragged(mc, mouseX, mouseY);

            if (this.hovered)
            {
            	Hime.instance.cfrs.drawCenteredString(displayString, xPosition + width / 2, yPosition + (this.height - 6) / 2, Color.gray.darker().getRGB());
            }else {
            	Hime.instance.cfrs.drawCenteredString(displayString, xPosition + width / 2, yPosition + (this.height - 6) / 2,  -1);
            }
      }
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
    {
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY)
    {
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    /**
     * Whether the mouse cursor is currently over the button.
     */
    public boolean isMouseOver()
    {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY)
    {
    }

    public void playPressSound(SoundHandler soundHandlerIn)
    {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

    public int getButtonWidth()
    {
        return this.width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }
}
