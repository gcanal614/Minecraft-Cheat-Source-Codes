package me.util;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class Render2D {
	
	private static Minecraft mc = Minecraft.getMinecraft();
	
	public final static void enable(int glTarget) {
		GL11.glEnable(glTarget);
	}
	
	public final static void disable(int glTarget) {
		GL11.glDisable(glTarget);
	}
	
	public final static void start() {
		enable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		disable(GL11.GL_TEXTURE_2D);
		disable(GL11.GL_CULL_FACE);
		GlStateManager.disableAlpha();
	}
	
    public final static void stop() {
    	GlStateManager.enableAlpha();
        enable(GL11.GL_CULL_FACE);
        enable(GL11.GL_TEXTURE_2D);
        disable(GL11.GL_BLEND);
        color(Color.white);
    }
    
    public final static void begin(int glMode) {
    	GL11.glBegin(glMode);
    }
    
    public final static void end() {
    	GL11.glEnd();
    }
    
    public final static void vertex(double x, double y) {
    	GL11.glVertex2d(x, y);
    }
    
    public final static void color(double red, double green, double blue, double alpha) {
    	GL11.glColor4d(red, green, blue, alpha);
    }
    
    
    public final static void color(Color color) {
    	if (color == null)
    		color = Color.white;
        color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
    }
    
    
    public final static void rect(double x, double y, double width, double height, boolean filled, Color color) {
    	start();
    	if (color != null)
    		color(color);
    	begin(filled ? GL11.GL_TRIANGLE_FAN : GL11.GL_LINES);
    	{
    		vertex(x, y);
    		vertex(x + width, y);
    		vertex(x + width, y + height);
    		vertex(x, y + height);
    		if (!filled)  {
    			vertex(x, y);
    			vertex(x, y + height);
    			vertex(x + width, y);
    			vertex(x + width, y + height);
    		}
    	}
    	end();
    	stop();
    }
    
    public final static void rect(double x, double y, double width, double height, boolean filled) {
    	rect(x, y, width, height, filled, null);
    }
    
    public final static void rect(double x, double y, double width, double height, Color color) {
    	rect(x, y, width, height, true, color);
    }
    
    public final static void rect(double x, double y, double width, double height) {
    	rect(x, y, width, height, true, null);
    }
    
    public static void polygon(double x, double y, double sideLength, double amountOfSides, boolean filled, Color color) {
    	sideLength /= 2;
    	start();
    	if (color != null)
    		color(color);
    	if (!filled) GL11.glLineWidth(1);
    	GL11.glEnable(GL11.GL_LINE_SMOOTH);
    	begin(filled ? GL11.GL_TRIANGLE_FAN : GL11.GL_LINE_STRIP);
    	{
    		for (double i = 0; i <= amountOfSides; i++) {
    			double angle = i * (Math.PI * 2) / amountOfSides;
    			vertex(x + (sideLength * Math.cos(angle)) + sideLength, y + (sideLength * Math.sin(angle)) + sideLength);
    		}
    	}
    	end();
    	GL11.glDisable(GL11.GL_LINE_SMOOTH);
    	stop();
    }
    
    public static void triangle(double x, double y, double sideLength, boolean filled, Color color) {
    	polygon(x, y, sideLength, 3, filled, color);
    }
    
}
