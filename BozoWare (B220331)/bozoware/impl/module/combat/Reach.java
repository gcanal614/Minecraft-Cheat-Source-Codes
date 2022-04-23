// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.combat;

import bozoware.base.BozoWare;
import bozoware.impl.property.ValueProperty;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Reach", moduleCategory = ModuleCategory.COMBAT)
public class Reach extends Module
{
    private final ValueProperty<Double> rangeValue;
    
    public Reach() {
        this.rangeValue = new ValueProperty<Double>("Range", 3.5, Double.valueOf(Integer.valueOf(3)), Double.valueOf(Integer.valueOf(6)), this);
    }
    
    private static Reach getInstance() {
        return BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Reach.class);
    }
    
    public static float getReachValue() {
        return getInstance().rangeValue.getPropertyValue().floatValue();
    }
}
