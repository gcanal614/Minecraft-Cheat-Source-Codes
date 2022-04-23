// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import bozoware.visual.font.MinecraftFontRenderer;
import bozoware.base.BozoWare;
import bozoware.base.event.EventListener;
import bozoware.impl.event.visual.Render2DEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Test Blur", moduleCategory = ModuleCategory.VISUAL)
public class TestBlur extends Module
{
    @EventListener
    EventConsumer<Render2DEvent> onRender2DEvent;
    
    public TestBlur() {
        final MinecraftFontRenderer MCFR;
        this.onRender2DEvent = (e -> {
            MCFR = BozoWare.getInstance().getFontManager().McFontRenderer;
            MCFR.drawStringWithShadow("Hell obozo", e.getScaledResolution().getScaledWidth() / 2, e.getScaledResolution().getScaledHeight() / 2, -1);
        });
    }
}
