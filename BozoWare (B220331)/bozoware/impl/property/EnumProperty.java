// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.property;

import java.util.Arrays;
import bozoware.base.module.Module;
import bozoware.base.property.Property;

public class EnumProperty<T extends Enum<T>> extends Property<T>
{
    private final T[] enumValues;
    
    public EnumProperty(final String propertyLabel, final T propertyValue, final Module parentModule) {
        super(propertyLabel, propertyValue, parentModule);
        this.enumValues = propertyValue.getDeclaringClass().getEnumConstants();
    }
    
    public void increment() {
        final T currentValue = this.getPropertyValue();
        for (final T constant : this.getEnumValues()) {
            if (constant == currentValue) {
                final int ordinal = Arrays.asList(this.getEnumValues()).indexOf(constant);
                T newValue;
                if (ordinal == this.getEnumValues().length - 1) {
                    newValue = this.getEnumValues()[0];
                }
                else {
                    newValue = this.getEnumValues()[ordinal + 1];
                }
                this.setPropertyValue(newValue);
                return;
            }
        }
        this.onValueChange.run();
    }
    
    public void decrement() {
        final T currentValue = this.getPropertyValue();
        for (final T constant : this.getEnumValues()) {
            if (constant == currentValue) {
                final int ordinal = Arrays.asList(this.getEnumValues()).indexOf(constant);
                T newValue;
                if (ordinal == 0) {
                    newValue = this.getEnumValues()[this.getEnumValues().length - 1];
                }
                else {
                    newValue = this.getEnumValues()[ordinal - 1];
                }
                this.setPropertyValue(newValue);
                return;
            }
        }
        this.onValueChange.run();
    }
    
    @Override
    public void setPropertyValue(final T propertyValue) {
        super.setPropertyValue(propertyValue);
        this.onValueChange.run();
    }
    
    @Override
    public T getPropertyValue() {
        return super.getPropertyValue();
    }
    
    public T[] getEnumValues() {
        return this.enumValues;
    }
}
