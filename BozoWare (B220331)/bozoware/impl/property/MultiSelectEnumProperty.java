// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;
import bozoware.base.module.Module;
import java.util.List;
import bozoware.base.property.Property;

public class MultiSelectEnumProperty<T extends Enum<T>> extends Property<List<T>>
{
    private final T[] constantEnumValues;
    
    @SafeVarargs
    public MultiSelectEnumProperty(final String propertyLabel, final Module parentModule, final T... propertyValues) {
        super(propertyLabel, Arrays.asList(propertyValues), parentModule);
        this.constantEnumValues = this.getPropertyValue().get(0).getDeclaringClass().getEnumConstants();
    }
    
    public void setValue(final int index, final MultiOption multiOption) {
        final List<T> values = new ArrayList<T>(this.getPropertyValue());
        final T referencedVariant = this.getConstantEnumValues()[index];
        if (multiOption.equals(MultiOption.TOGGLE)) {
            if (!values.contains(referencedVariant)) {
                values.add(referencedVariant);
            }
            else {
                values.remove(referencedVariant);
            }
        }
        else if (multiOption.equals(MultiOption.SELECT)) {
            if (!values.contains(referencedVariant)) {
                values.add(referencedVariant);
            }
        }
        else if (multiOption.equals(MultiOption.UNSELECT)) {
            values.remove(referencedVariant);
        }
        this.setPropertyValue(values);
    }
    
    public List<T> getSelectedValues() {
        return this.getPropertyValue();
    }
    
    public boolean isSelected(final T value) {
        return this.getPropertyValue().contains(value);
    }
    
    public boolean isSelected(final int index) {
        return this.getPropertyValue().contains(this.getConstantEnumValues()[index]);
    }
    
    public T[] getConstantEnumValues() {
        return this.constantEnumValues;
    }
    
    public enum MultiOption
    {
        SELECT, 
        UNSELECT, 
        TOGGLE;
    }
}
