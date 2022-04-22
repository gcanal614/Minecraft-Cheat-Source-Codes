package me.util;

import java.awt.Color;

public class RainbowUtil {

	 public static int rainbow(int delay) {
	      double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
	      rainbowState %= 360;
	      return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
	 }
	 public static int rainbow(int delay, float brightness, float saturation) {
	      double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
	      rainbowState %= 360;
	      return Color.getHSBColor((float) (rainbowState / 360.0f), saturation, brightness).getRGB();
	  }
	 
	 public static int getRainbow(int speed, int offset) {
	      float hue = (System.currentTimeMillis() + offset) % speed;
	      hue /= speed;
	        return Color.getHSBColor(hue, 1f, 1f).getRGB(); 
	 }
	 public static int getRainbow(int speed, int offset,  float brightness, float saturation) {
	      float hue = (System.currentTimeMillis() + offset) % speed;
	      hue /= speed;
	        return Color.getHSBColor(hue, saturation, brightness).getRGB(); 
	 }
}
