// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import bozoware.base.BozoWare;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "WorldColor", moduleCategory = ModuleCategory.VISUAL)
public class WorldColor extends Module
{
    public static WorldColor getInstance() {
        return BozoWare.getInstance().getModuleManager().getModuleByClass.apply(WorldColor.class);
    }
}
