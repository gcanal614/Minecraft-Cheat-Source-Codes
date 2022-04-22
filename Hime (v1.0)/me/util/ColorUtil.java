package me.util;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import me.Hime;



public class ColorUtil {
	
	public static float getClickGUIColor(){
	
		return (float) Hime.instance.settingsManager.getSettingByName("Hue").getValDouble() / 255;
	}

	    public static Color setColor(int r, int g, int b, int a) {
	        Color c = new Color(r, g, b, a);
	        return c;
	    }

	    public static Color bw(int bw, int a) {
	        Color c = new Color(bw, bw, bw, a);
	        return c;
	    }

	    public static Color black(int a) {
	        Color c = new Color(0, 0, 0, a);
	        return c;
	    }

	    public static Color white(int a) {
	        Color c = new Color(255, 255, 255, a);
	        return c;
	    }

	    public static void glColor(Color color) {
	        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
	    }

	    public static void glColor(int r, int g, int b, int a) {
	        GL11.glColor4f(r / 255f, g / 255f, b / 255f, a / 255f);
	    }

	    public static void resetglColor() {
	        GL11.glColor4f(1f, 1f, 1f, 1f);
	    }

	    public static int hsbcolor(int h, float s, float b) {
	        return Color.HSBtoRGB(h, s, b);
	    }

	    public static int[] rainbow(int delay, float saturation, float brightness) {
	        double jump = 360f / delay;
	        int[] colors = new int[delay + 1];
	        for (int i = 0; i < colors.length; i++) {
	            colors[i] = Color.HSBtoRGB((float) (i * jump), saturation, brightness);
	        }
	        return colors;
	    }
	    
	    public static Color pulseBrightness(Color color, int index, int count) {
	        float[] hsb = new float[3];
	        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
	        float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0F + (float)index / (float)count * 2.0F) % 2.0F - 1.0F);
	        brightness = 0.5F + 0.5F * brightness;
	        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], brightness % 2.0F));
	     }
	    
	    public static Color pulseSaturation(Color color, int index, int count) {
	        float[] hsb = new float[3];
	        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
	        float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0F + (float)index / (float)count * 2.0F) % 2.0F - 1.0F);
	        brightness = 0.8F * brightness;
	        hsb[1] = brightness % 2.0F;
	        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
	     }
	    

	    public static String textColor(String color) {
	        if (color.equalsIgnoreCase("BLACK")) {
	            return "§0";
	        } else if (color.equalsIgnoreCase("DBLUE")) {
	            return "§1";
	        } else if (color.equalsIgnoreCase("DGREEN")) {
	            return "§2";
	        } else if (color.equalsIgnoreCase("DAQUA")) {
	            return "§3";
	        } else if (color.equalsIgnoreCase("DRED")) {
	            return "§4";
	        } else if (color.equalsIgnoreCase("DPURPLE")) {
	            return "§5";
	        } else if (color.equalsIgnoreCase("GOLD")) {
	            return "§6";
	        } else if (color.equalsIgnoreCase("GRAY")) {
	            return "§7";
	        } else if (color.equalsIgnoreCase("DGRAY")) {
	            return "§8";
	        } else if (color.equalsIgnoreCase("BLUE")) {
	            return "§9";
	        } else if (color.equalsIgnoreCase("GREEN")) {
	            return "§a";
	        } else if (color.equalsIgnoreCase("AQUA")) {
	            return "§b";
	        } else if (color.equalsIgnoreCase("RED")) {
	            return "§c";
	        } else if (color.equalsIgnoreCase("LPURPLE")) {
	            return "§d";
	        } else if (color.equalsIgnoreCase("YELLOW")) {
	            return "§e";
	        } else if (color.equalsIgnoreCase("WHITE")) {
	            return "§f";
	        } else return "";
	    }
}