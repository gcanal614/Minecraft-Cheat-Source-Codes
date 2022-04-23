// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import net.minecraft.client.gui.ScaledResolution;
import bozoware.visual.font.MinecraftFontRenderer;
import bozoware.base.util.player.MovementUtil;
import bozoware.base.util.visual.RenderUtil;
import bozoware.base.BozoWare;
import java.awt.Color;
import bozoware.impl.property.ColorProperty;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.visual.Render2DEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Motion Graph", moduleCategory = ModuleCategory.VISUAL)
public class MotionGraph extends Module
{
    @EventListener
    EventConsumer<Render2DEvent> onRender2DEvent;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    private final ColorProperty crossColor;
    float Motion;
    
    public MotionGraph() {
        this.crossColor = new ColorProperty("Color", new Color(-65536), this);
        final MinecraftFontRenderer BIFR;
        final MinecraftFontRenderer BITFR;
        final MinecraftFontRenderer arrowIcons;
        final MinecraftFontRenderer SCFR;
        final ScaledResolution SR;
        this.onRender2DEvent = (e -> {
            BIFR = BozoWare.getInstance().getFontManager().BasicIcons;
            BITFR = BozoWare.getInstance().getFontManager().BitFontRenderer;
            arrowIcons = BozoWare.getInstance().getFontManager().ArrowIcons;
            SCFR = BozoWare.getInstance().getFontManager().smallCSGORenderer;
            SR = e.getScaledResolution();
            RenderUtil.drawSmoothRoundedRect((float)(SR.getScaledWidth() / 2 - 75), (float)(SR.getScaledHeight() / 2 + 50), (float)(SR.getScaledWidth() / 2 + 75), (float)(SR.getScaledHeight() / 2 + 125), 15.0f, 1610612736);
            RenderUtil.drawSmoothRoundedRect((float)(SR.getScaledWidth() / 2 - 75), (float)(SR.getScaledHeight() / 2 + 50), (float)(SR.getScaledWidth() / 2 + 75), (float)(SR.getScaledHeight() / 2 + 65), 15.0f, 1610612736);
            RenderUtil.glHorizontalGradientQuad(SR.getScaledWidth() / 2 - 75, SR.getScaledHeight() / 2 + 64.5, 75.0, 2.0, 1, -16711936);
            RenderUtil.glHorizontalGradientQuad(SR.getScaledWidth() / 2, SR.getScaledHeight() / 2 + 64.5, 75.0, 2.0, -16711936, 1);
            BITFR.drawStringWithShadow("Motion Graph   " + MovementUtil.getBPS(), SR.getScaledWidth() / 2 - 70, SR.getScaledHeight() / 2 + 57, -1);
            BIFR.drawStringWithShadow(this.getArrowText(), SR.getScaledWidth() / 2 + 55, SR.getScaledHeight() / 2 + 55, -1);
            return;
        });
        this.onUpdatePositionEvent = (e -> this.Motion = Float.parseFloat(MovementUtil.getBPS()));
    }
    
    public String getArrowText() {
        if (this.Motion == 0.0f) {
            return "";
        }
        if (this.Motion > 0.0f) {
            return "b";
        }
        return "";
    }
}
