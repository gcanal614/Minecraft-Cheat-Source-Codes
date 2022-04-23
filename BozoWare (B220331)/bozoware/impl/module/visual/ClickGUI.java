// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import bozoware.base.BozoWare;
import bozoware.impl.property.EnumProperty;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "ClickGUI", moduleCategory = ModuleCategory.VISUAL)
public class ClickGUI extends Module
{
    public final EnumProperty<clickGuiModes> clickGuiMode;
    public final EnumProperty<loliModes> loliMode;
    
    public ClickGUI() {
        this.clickGuiMode = new EnumProperty<clickGuiModes>("Mode", clickGuiModes.Dropdown, this);
        this.loliMode = new EnumProperty<loliModes>("Woman", loliModes.Uzaki, this);
        this.setModuleToggled(false);
        this.loliMode.setHidden(false);
        this.clickGuiMode.onValueChange = (() -> {
            if (this.clickGuiMode.getPropertyValue().equals(clickGuiModes.Dropdown)) {
                this.loliMode.setHidden(false);
            }
            else {
                this.loliMode.setHidden(true);
            }
            return;
        });
        this.onModuleEnabled = (() -> this.setModuleToggled(false));
    }
    
    public static ClickGUI getInstance() {
        return BozoWare.getInstance().getModuleManager().getModuleByClass.apply(ClickGUI.class);
    }
    
    public enum clickGuiModes
    {
        Dropdown, 
        Onetap;
    }
    
    public enum loliModes
    {
        Uzaki, 
        ZeroTwo, 
        Rias, 
        None, 
        Kanna;
    }
}
