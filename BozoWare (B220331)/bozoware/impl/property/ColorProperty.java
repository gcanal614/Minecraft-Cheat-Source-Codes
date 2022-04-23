// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.property;

import bozoware.base.module.Module;
import java.awt.Color;
import bozoware.base.property.Property;

public class ColorProperty extends Property<Color>
{
    private Color color;
    
    public ColorProperty(final String propertyLabel, final Color propertyValue, final Module parentModule) {
        super(propertyLabel, propertyValue, parentModule);
    }
    
    @Override
    public Color getPropertyValue() {
        return super.getPropertyValue();
    }
    
    public int getColorRGB() {
        return super.getPropertyValue().getRGB();
    }
    
    @Override
    public void setPropertyValue(final Color color) {
        super.setPropertyValue(color);
    }
    
    public void setPropertyValue(final int color) {
        super.setPropertyValue(new Color(color));
    }
}
