// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.property;

import bozoware.base.BozoWare;
import bozoware.base.module.Module;

public class Property<T>
{
    private final String propertyLabel;
    private T propertyValue;
    private final Module parentModule;
    private boolean hidden;
    public Runnable onValueChange;
    
    public Property(final String propertyLabel, final T propertyValue, final Module parentModule) {
        this.onValueChange = (() -> {});
        this.propertyLabel = propertyLabel;
        this.propertyValue = propertyValue;
        this.parentModule = parentModule;
        BozoWare.getInstance().getPropertyManager().addProperty(this);
    }
    
    public T getPropertyValue() {
        return this.propertyValue;
    }
    
    public void setPropertyValue(final T propertyValue) {
        this.propertyValue = propertyValue;
    }
    
    public String getPropertyLabel() {
        return this.propertyLabel;
    }
    
    public Module getParentModule() {
        return this.parentModule;
    }
    
    public Class<?> getType() {
        return this.propertyValue.getClass();
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }
}
