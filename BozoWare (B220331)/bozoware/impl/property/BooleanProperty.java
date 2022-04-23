// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.property;

import bozoware.base.module.Module;
import bozoware.base.property.Property;

public class BooleanProperty extends Property<Boolean>
{
    public BooleanProperty(final String propertyLabel, final Boolean propertyValue, final Module parentModule) {
        super(propertyLabel, propertyValue, parentModule);
    }
    
    @Override
    public Boolean getPropertyValue() {
        return super.getPropertyValue();
    }
    
    @Override
    public void setPropertyValue(final Boolean propertyValue) {
        super.setPropertyValue(propertyValue);
        this.onValueChange.run();
    }
}
