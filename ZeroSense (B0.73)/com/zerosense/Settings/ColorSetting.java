package com.zerosense.Settings;

import com.zerosense.Utils.JColor;
import com.zerosense.mods.Module;

import java.awt.Color;

public class ColorSetting extends Setting {
  private JColor value;
  
  private Module parent;
  
  private boolean rainbow;
  
  public void setValue(Color paramColor) {
    setValue(getRainbow(), new JColor(paramColor));
  }
  
  public Color getColor() {
    return (Color)getColor();
  }
  
  public Color getValue() {
    return (Color)getValue();
  }
  
  public void setRainbow(boolean paramBoolean) {
    this.rainbow = paramBoolean;
  }
  
  public long toInteger() {
    return (this.value.getRGB() & 0xFFFFFFFF);
  }
  
  public static int rainbow(int paramInt) {
    double d = Math.ceil((System.currentTimeMillis() + paramInt) / 20.0D);
    d %= 360.0D;
    return Color.getHSBColor((float)(d / 360.0D), 0.5F, 1.0F).getRGB();
  }
  
  public boolean getRainbow() {
    return this.rainbow;
  }
  
  public void fromInteger(long paramLong) {
    this.value = new JColor(Math.toIntExact(paramLong & 0xFFFFFFFFFFFFFFFFL), true);
  }
  
  public void setValue(boolean paramBoolean, JColor paramJColor) {
    this.rainbow = paramBoolean;
    this.value = paramJColor;
  }
  
  public ColorSetting(String paramString, JColor paramJColor) {
    super(paramString);
    this.name = paramString;
    this.value = paramJColor;
  }
}
