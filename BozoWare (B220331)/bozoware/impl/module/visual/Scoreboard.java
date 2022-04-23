// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import bozoware.base.BozoWare;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.property.BooleanProperty;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Scoreboard", moduleCategory = ModuleCategory.VISUAL)
public class Scoreboard extends Module
{
    public BooleanProperty hiddenBool;
    public ValueProperty<Integer> yPos;
    
    public Scoreboard() {
        this.hiddenBool = new BooleanProperty("Hidden", false, this);
        this.yPos = new ValueProperty<Integer>("Y-Position", 500, 100, 1000, this);
    }
    
    public static Scoreboard getInstance() {
        return BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Scoreboard.class);
    }
}
