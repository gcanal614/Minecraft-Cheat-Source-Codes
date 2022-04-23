// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.property;

import bozoware.base.module.Module;
import bozoware.base.property.Property;

public class ValueProperty<T extends Number> extends Property<T>
{
    private final T minimumValue;
    private final T maximumValue;
    
    public ValueProperty(final String propertyLabel, final T propertyValue, final T minimumValue, final T maximumValue, final Module parentModule) {
        super(propertyLabel, propertyValue, parentModule);
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
    }
    
    @Override
    public T getPropertyValue() {
        return super.getPropertyValue();
    }
    
    @Override
    public void setPropertyValue(final T propertyValue) {
        super.setPropertyValue(propertyValue);
        this.onValueChange.run();
    }
    
    @Override
    public String getPropertyLabel() {
        return super.getPropertyLabel();
    }
    
    public T getMaximumValue() {
        return this.maximumValue;
    }
    
    public T getMinimumValue() {
        return this.minimumValue;
    }
}
