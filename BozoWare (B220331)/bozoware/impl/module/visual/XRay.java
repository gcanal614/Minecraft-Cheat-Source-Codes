// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import java.util.ArrayList;
import java.util.List;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Xray", moduleCategory = ModuleCategory.VISUAL)
public class XRay extends Module
{
    public List<Integer> blocks;
    
    public XRay() {
        (this.blocks = new ArrayList<Integer>()).add(14);
        this.blocks.add(15);
        this.blocks.add(16);
        this.blocks.add(21);
        this.blocks.add(56);
        this.blocks.add(73);
        this.blocks.add(129);
        this.blocks.add(133);
        this.blocks.add(57);
        this.blocks.add(41);
        this.blocks.add(42);
        this.blocks.add(173);
        this.blocks.add(152);
        this.onModuleEnabled = (() -> {
            if (XRay.mc.theWorld != null) {
                XRay.mc.renderGlobal.loadRenderers();
            }
            return;
        });
        this.onModuleDisabled = (() -> {
            if (XRay.mc.theWorld != null) {
                XRay.mc.renderGlobal.loadRenderers();
            }
        });
    }
    
    public List<Integer> getBlocks() {
        return this.blocks;
    }
}
