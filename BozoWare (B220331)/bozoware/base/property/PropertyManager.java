// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.property;

import java.util.Collection;
import bozoware.base.module.Module;
import java.util.ArrayList;

public class PropertyManager
{
    private ArrayList<Property<?>> properties;
    
    public ArrayList<Property<?>> getProperties() {
        return this.properties;
    }
    
    public ArrayList<Property<?>> getPropertiesByModule(final Module module) {
        final ArrayList<Property<?>> properties = new ArrayList<Property<?>>(this.getProperties());
        properties.removeIf(property -> !property.getParentModule().equals(module));
        return properties;
    }
    
    public void addProperty(final Property<?> property) {
        this.properties.add(property);
    }
    
    public PropertyManager() {
        final Runnable onStartTask = () -> this.properties = new ArrayList<Property<?>>();
        onStartTask.run();
    }
}
