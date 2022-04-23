// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import bozoware.base.BozoWare;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.property.EnumProperty;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Animations", moduleCategory = ModuleCategory.VISUAL)
public class Animations extends Module
{
    public final EnumProperty<AnimationModes> blockMode;
    public final ValueProperty<Float> Xpos;
    public final ValueProperty<Float> Ypos;
    public final ValueProperty<Float> Zpos;
    public final ValueProperty<Float> Size;
    
    public Animations() {
        this.blockMode = new EnumProperty<AnimationModes>("Mode", AnimationModes.Swing, this);
        this.Xpos = new ValueProperty<Float>("X", 0.0f, -1.0f, 1.0f, this);
        this.Ypos = new ValueProperty<Float>("Y", 0.0f, -1.0f, 1.0f, this);
        this.Zpos = new ValueProperty<Float>("Z", 0.0f, -1.0f, 1.0f, this);
        this.Size = new ValueProperty<Float>("Size", 1.0f, 0.1f, 2.0f, this);
        this.blockMode.onValueChange = (() -> this.setModuleSuffix(this.blockMode.getPropertyValue().name()));
    }
    
    public static Animations getInstance() {
        return BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Animations.class);
    }
    
    public enum AnimationModes
    {
        Swing, 
        Swong, 
        Slide;
    }
}
