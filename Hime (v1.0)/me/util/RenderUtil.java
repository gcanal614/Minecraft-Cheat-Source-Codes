package me.util;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL14;

import static org.lwjgl.opengl.GL11.*;

public enum RenderUtil {

	instance;

	protected Minecraft mc = Minecraft.getMinecraft();
	final boolean restore = glEnableBlend();

	public void draw2DImage(ResourceLocation image, int x, int y, int width, int height, Color c) {
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glDepthMask(false);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha());
		this.mc.getTextureManager().bindTexture(image);
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
		glDepthMask(true);
		glDisable(GL_BLEND);
		glEnable(GL_DEPTH_TEST);
		glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public void drawImage(GuiIngame gui, ResourceLocation fileLocation, int x, int y, int w, int h, float fw, float fh, float u, float v) {
		 glDisable(GL_DEPTH_TEST);
		 glEnable(GL_BLEND);
		 glDepthMask(false);
		 OpenGlHelper.glBlendFunc(770, 771, 1, 0);
	     GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	     mc.getTextureManager().bindTexture(fileLocation);
	     Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, w, h, fw, fh);
	     glDepthMask(true);
		 glDisable(GL_BLEND);
		 glEnable(GL_DEPTH_TEST);
		 glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	 }

	public void drawImageOnScreen(ResourceLocation image, int x, int y, int width, int height) {
		this.mc.getTextureManager().bindTexture(image);
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, width, height);
	}


	public static void glColor(int hex) {
		float alpha = (hex >> 24 & 0xFF) / 255.0F;
		float red = (hex >> 16 & 0xFF) / 255.0F;
		float green = (hex >> 8 & 0xFF) / 255.0F;
		float blue = (hex & 0xFF) / 255.0F;
		GL11.glColor4f(red, green, blue, alpha);
	}


	public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
		float f = (col1 >> 24 & 255) / 255.0f;
		float f1 = (col1 >> 16 & 255) / 255.0f;
		float f2 = (col1 >> 8 & 255) / 255.0f;
		float f3 = (col1 & 255) / 255.0f;
		float f4 = (col2 >> 24 & 255) / 255.0f;
		float f5 = (col2 >> 16 & 255) / 255.0f;
		float f6 = (col2 >> 8 & 255) / 255.0f;
		float f7 = (col2 & 255) / 255.0f;
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glShadeModel(7425);
		GL11.glPushMatrix();
		GL11.glBegin(7);
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glVertex2d(left, top);
		GL11.glVertex2d(left, bottom);
		GL11.glColor4f(f5, f6, f7, f4);
		GL11.glVertex2d(right, bottom);
		GL11.glVertex2d(right, top);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
	}

	public long time = System.currentTimeMillis();

	/**
	 * Credit to LaVache
	 */
	public void drawGif(ResourceLocation img, int x, int y, int width, int height, int numberImage, int delay) {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDepthMask(false);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(img);
		Gui.drawModalRectWithCustomSizedTexture(x, y,
				width * ((System.currentTimeMillis() - time) / delay % numberImage), 0, width, height,
				width * numberImage, height);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	public boolean isHovered(double x, double y, double width, double height, int mouseX, int mouseY) {
		return mouseX > x && mouseY > y && mouseX < width && mouseY < height;
	}


	  public static void drawRoundedRect(float left, float top, float right, float bottom, int smooth, Color color) {
	    Gui.drawRect((left + smooth), top, (right - smooth), bottom, color.getRGB());
	    Gui.drawRect(left, (top + smooth), right, (bottom - smooth), color.getRGB());
	    drawFilledCircle((int)left + smooth, (int)top + smooth, smooth, color);
	    drawFilledCircle((int)right - smooth, (int)top + smooth, smooth, color);
	    drawFilledCircle((int)right - smooth, (int)bottom - smooth, smooth, color);
	    drawFilledCircle((int)left + smooth, (int)bottom - smooth, smooth, color);
	  }
	  
	  public static void drawBorderRoundedRect(float left, float top, float right, float bottom, int smooth, Color color, Color borderColor) {
	    drawRoundedRect(left - 1.0F, top - 1.0F, right + 1.0F, bottom + 1.0F, smooth + 1, borderColor);
	    drawRoundedRect(left, top, right, bottom, smooth, color);
	  }
	  
	  public static void drawFilledCircle(int xx, int yy, float radius, Color color) {
	    int sections = 50;
	    double dAngle = 6.283185307179586D / sections;
	    GL11.glPushAttrib(8192);
	    GL11.glEnable(3042);
	    GL11.glDisable(3553);
	    GL11.glBlendFunc(770, 771);
	    GL11.glEnable(2848);
	    GL11.glBegin(6);
	    for (int i = 0; i < sections; i++) {
	      float x = (float)(radius * Math.sin(i * dAngle));
	      float y = (float)(radius * Math.cos(i * dAngle));
	      GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
	      GL11.glVertex2f(xx + x, yy + y);
	    } 
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    GL11.glEnd();
	    GL11.glPopAttrib();
	  }

	  public static void rectangle(double left, double top, double right, double bottom, int color) {
		    Gui.drawRect(left, top, right, bottom, color);
		  }
	 public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
		    rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
		    rectangle(x + width, y, x1 - width, y + width, borderColor);
		    rectangle(x, y, x + width, y1, borderColor);
		    rectangle(x1 - width, y, x1, y1, borderColor);
		    rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
		  }
	 
	 public static void drawRoundedRect2(int x, int y, int width, int height, int cornerRadius, Color color) {
	        Gui.drawRect(x, y + cornerRadius, x + cornerRadius, y + height - cornerRadius, color.getRGB());
	        Gui.drawRect(x + cornerRadius, y, x + width - cornerRadius, y + height, color.getRGB());
	        Gui.drawRect(x + width - cornerRadius, y + cornerRadius, x + width, y + height - cornerRadius, color.getRGB());
	        
	        drawArc(x + cornerRadius, y + cornerRadius, cornerRadius, 0, 90, color);
	        drawArc(x + width - cornerRadius, y + cornerRadius, cornerRadius, 270, 360, color);
	        drawArc(x + width - cornerRadius, y + height - cornerRadius, cornerRadius, 180, 270, color);
	        drawArc(x + cornerRadius, y + height - cornerRadius, cornerRadius, 90, 180, color);
	    }
	    
	    public static void drawArc(int x, int y, int radius, int startAngle, int endAngle, Color color) {
	        
	        GL11.glPushMatrix();
	        GL11.glEnable(3042);
	        GL11.glDisable(3553);
	        GL11.glBlendFunc(770, 771);
	        GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, (float) color.getAlpha() / 255);
	        
	        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();

	        worldRenderer.func_181668_a(6, DefaultVertexFormats.field_181705_e);
	        worldRenderer.func_181662_b(x, y, 0).func_181675_d();

	        for (int i = (int) (startAngle / 360.0 * 100); i <= (int) (endAngle / 360.0 * 100); i++) {
	            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
	            worldRenderer.func_181662_b(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0).func_181675_d();
	        }
	        
	        Tessellator.getInstance().draw();
	        
	        GL11.glEnable(3553);
	        GL11.glDisable(3042);
	        GL11.glPopMatrix();
	    }
	 
	 public static void drawRect(double left, double top, double right, double bottom, int color) {
	        if (left < right) {
	            double i = left;
	            left = right;
	            right = i;
	        }

	        if (top < bottom) {
	            double j = top;
	            top = bottom;
	            bottom = j;
	        }

	        float f3 = (float) (color >> 24 & 255) / 255.0F;
	        float f = (float) (color >> 16 & 255) / 255.0F;
	        float f1 = (float) (color >> 8 & 255) / 255.0F;
	        float f2 = (float) (color & 255) / 255.0F;
	        Tessellator tessellator = Tessellator.getInstance();
	        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
	        GlStateManager.enableBlend();
	        GlStateManager.disableTexture2D();
	        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
	        GlStateManager.color(f, f1, f2, f3);
	        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
	        worldrenderer.func_181662_b((double) left, (double) bottom, 0.0D).func_181675_d();
	        worldrenderer.func_181662_b((double) right, (double) bottom, 0.0D).func_181675_d();
	        worldrenderer.func_181662_b((double) right, (double) top, 0.0D).func_181675_d();
	        worldrenderer.func_181662_b((double) left, (double) top, 0.0D).func_181675_d();
	        tessellator.draw();
	        GlStateManager.enableTexture2D();
	        GlStateManager.disableBlend();
	    }
	 
	 public void drawGradientRect(double d, int top, double e, int bottom, int startColor, int endColor)
	 {
	        float f = (float)(startColor >> 24 & 255) / 255.0F;
	        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
	        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
	        float f3 = (float)(startColor & 255) / 255.0F;
	        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
	        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
	        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
	        float f7 = (float)(endColor & 255) / 255.0F;
	        GlStateManager.disableTexture2D();
	        GlStateManager.enableBlend();
	        GlStateManager.disableAlpha();
	        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
	        GlStateManager.shadeModel(7425);
	        Tessellator tessellator = Tessellator.getInstance();
	        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
	        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
	        worldrenderer.func_181662_b((double)e, (double)top, (double)0).func_181666_a(f1, f2, f3, f).func_181675_d();
	        worldrenderer.func_181662_b((double)d, (double)top, (double)0).func_181666_a(f1, f2, f3, f).func_181675_d();
	        worldrenderer.func_181662_b((double)d, (double)bottom, (double)0).func_181666_a(f5, f6, f7, f4).func_181675_d();
	        worldrenderer.func_181662_b((double)e, (double)bottom, (double)0).func_181666_a(f5, f6, f7, f4).func_181675_d();
	        tessellator.draw();
	        GlStateManager.shadeModel(7424);
	        GlStateManager.disableBlend();
	        GlStateManager.enableAlpha();
	        GlStateManager.enableTexture2D();
	    }

	public static void drawBorderedRect(double left, double top, double right, double bottom, double borderWidth, int insideColor, int borderColor, boolean borderIncludedInBounds) {
        drawRect(left - (!borderIncludedInBounds ? borderWidth : 0), top - (!borderIncludedInBounds ? borderWidth : 0), right + (!borderIncludedInBounds ? borderWidth : 0), bottom + (!borderIncludedInBounds ? borderWidth : 0), borderColor);
        drawRect(left + (borderIncludedInBounds ? borderWidth : 0), top + (borderIncludedInBounds ? borderWidth : 0), right - ((borderIncludedInBounds ? borderWidth : 0)), bottom - ((borderIncludedInBounds ? borderWidth : 0)), insideColor);
    }

	public void draw2DImage(ResourceLocation image, double x, double y, int width, int height, Color c) {
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glDepthMask(false);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		glColor4f(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha());
		this.mc.getTextureManager().bindTexture(image);
		Gui.drawModalRectWithCustomSizedTexture2(x, y, 0.0F, 0.0F, width, height, width, height);
		glDepthMask(true);
		glDisable(GL_BLEND);
		glEnable(GL_DEPTH_TEST);
		glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static void glDrawRoundedRectEllipse(final double x,
												final double y,
												final double width,
												final double height,
												final RoundingMode roundingMode,
												final int roundingDef,
												final double roundingLevel,
												final int colour) {
		boolean bLeft = false;
		boolean tLeft = false;
		boolean bRight = false;
		boolean tRight = false;

		switch (roundingMode) {
			case TOP:
				tLeft = true;
				tRight = true;
				break;
			case BOTTOM:
				bLeft = true;
				bRight = true;
				break;
			case FULL:
				tLeft = true;
				tRight = true;
				bLeft = true;
				bRight = true;
				break;
			case LEFT:
				bLeft = true;
				tLeft = true;
				break;
			case RIGHT:
				bRight = true;
				tRight = true;
				break;
			case TOP_LEFT:
				tLeft = true;
				break;
			case TOP_RIGHT:
				tRight = true;
				break;
			case BOTTOM_LEFT:
				bLeft = true;
				break;
			case BOTTOM_RIGHT:
				bRight = true;
				break;
		}

		// Translate matrix to top-left of rect
		glTranslated(x, y, 0);
		// Enable triangle anti-aliasing
		glEnable(GL_POLYGON_SMOOTH);
		glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
		// Enable blending
		final boolean restore = glEnableBlend();

		if (tLeft) {
			// Top left
			glDrawFilledEllipse(roundingLevel, roundingLevel, roundingLevel,
					(int) (roundingDef * 0.5), (int) (roundingDef * 0.75),
					roundingDef, false, colour);
		}

		if (tRight) {
			// Top right
			glDrawFilledEllipse(width - roundingLevel, roundingLevel, roundingLevel,
					(int) (roundingDef * 0.75), roundingDef,
					roundingDef, false, colour);
		}

		if (bLeft) {
			// Bottom left
			glDrawFilledEllipse(roundingLevel, height - roundingLevel, roundingLevel,
					(int) (roundingDef * 0.25), (int) (roundingDef * 0.5),
					roundingDef, false, colour);
		}

		if (bRight) {
			// Bottom right
			glDrawFilledEllipse(width - roundingLevel, height - roundingLevel, roundingLevel,
					0, (int) (roundingDef * 0.25),
					roundingDef, false, colour);
		}


		// Enable triangle anti-aliasing (to save performance on next poly draw)
		glDisable(GL_POLYGON_SMOOTH);
		glHint(GL_LINE_SMOOTH_HINT, GL_DONT_CARE);

		// Disable texture drawing
		glDisable(GL_TEXTURE_2D);
		// Set colour
		glColor(colour);

		// Begin polygon
		glBegin(GL_POLYGON);
		{
			if (tLeft) {
				glVertex2d(roundingLevel, roundingLevel);
				glVertex2d(0, roundingLevel);
			} else {
				glVertex2d(0, 0);
			}

			if (bLeft) {
				glVertex2d(0, height - roundingLevel);
				glVertex2d(roundingLevel, height - roundingLevel);
				glVertex2d(roundingLevel, height);
			} else {
				glVertex2d(0, height);
			}

			if (bRight) {
				glVertex2d(width - roundingLevel, height);
				glVertex2d(width - roundingLevel, height - roundingLevel);
				glVertex2d(width, height - roundingLevel);
			} else {
				glVertex2d(width, height);
			}

			if (tRight) {
				glVertex2d(width, roundingLevel);
				glVertex2d(width - roundingLevel, roundingLevel);
				glVertex2d(width - roundingLevel, 0);
			} else {
				glVertex2d(width, 0);
			}

			if (tLeft) {
				glVertex2d(roundingLevel, 0);
			}
		}
		// Draw polygon
		glEnd();

		// Disable blending
		glRestoreBlend(restore);
		// Translate matrix back (instead of creating a new matrix with glPush/glPop)
		glTranslated(-x, -y, 0);
		// Re-enable texture drawing
		glEnable(GL_TEXTURE_2D);
	}

	public static void glDrawFilledEllipse(final double x,
										   final double y,
										   final double radius,
										   final int startIndex,
										   final int endIndex,
										   final int polygons,
										   final boolean smooth,
										   final int colour) {
		// Enable blending
		final boolean restore = glEnableBlend();

		if (smooth) {
			// Enable anti-aliasing
			glEnable(GL_POLYGON_SMOOTH);
			glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
		}
		// Disable texture drawing
		glDisable(GL_TEXTURE_2D);
		// Set color
		glColor(colour);
		// Required because of minecraft optimizations
		glDisable(GL_CULL_FACE);

		// Begin triangle fan
		glBegin(GL_POLYGON);
		{
			// Specify center vertex
			glVertex2d(x, y);

			for (double i = startIndex; i <= endIndex; i++) {
				final double theta = 2.0 * Math.PI * i / polygons;
				// Specify triangle fan vertices in a circle (size=radius) around x & y
				glVertex2d(x + radius * Math.cos(theta), y + radius * Math.sin(theta));
			}
		}
		// Draw the triangle fan
		glEnd();

		// Disable blending
		glRestoreBlend(restore);

		if (smooth) {
			// Disable anti-aliasing
			glDisable(GL_POLYGON_SMOOTH);
			glHint(GL_POLYGON_SMOOTH_HINT, GL_DONT_CARE);
		}
		// See above
		glEnable(GL_CULL_FACE);
		// Re-enable texture drawing
		glEnable(GL_TEXTURE_2D);
	}

	public static void glRestoreBlend(final boolean wasEnabled) {
		if (!wasEnabled) {
			glDisable(GL_BLEND);
		}
	}

	public enum RoundingMode {
		TOP_LEFT,
		BOTTOM_LEFT,
		TOP_RIGHT,
		BOTTOM_RIGHT,

		LEFT,
		RIGHT,

		TOP,
		BOTTOM,

		FULL
	}

	public static boolean glEnableBlend() {
		final boolean wasEnabled = glIsEnabled(GL_BLEND);

		if (!wasEnabled) {
			glEnable(GL_BLEND);
			GL14.glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		}

		return wasEnabled;
	}

}