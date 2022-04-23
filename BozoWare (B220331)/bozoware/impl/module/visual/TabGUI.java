// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import bozoware.visual.font.MinecraftFontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import bozoware.base.BozoWare;
import bozoware.base.event.EventListener;
import bozoware.impl.event.visual.Render2DEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "TabGUI", moduleCategory = ModuleCategory.VISUAL)
public class TabGUI extends Module
{
    @EventListener
    EventConsumer<Render2DEvent> onRender2DEvent;
    public static int moduleNum;
    
    public TabGUI() {
        this.onModuleEnabled = (() -> TabGUI.moduleNum = 1);
        final MinecraftFontRenderer MFR;
        final double top;
        final double bottom;
        final double left;
        final double right;
        this.onRender2DEvent = (e -> {
            MFR = BozoWare.getInstance().getFontManager().mediumFontRenderer;
            if (Keyboard.isKeyDown(208)) {
                ++TabGUI.moduleNum;
            }
            if (Keyboard.isKeyDown(200) && TabGUI.moduleNum != 1) {
                --TabGUI.moduleNum;
            }
            Gui.drawRect(5.0, 18.0, 60.0, 80.0, -1728053248);
            top = TabGUI.moduleNum * 18;
            bottom = TabGUI.moduleNum * 30;
            HUD.getInstance();
            Gui.drawRect(left, top, right, bottom, HUD.bozoColor2);
            MFR.drawStringWithShadow("COMBAT", 8.0, 21.0, -1);
        });
    }
    
    static {
        TabGUI.moduleNum = 1;
    }
}
