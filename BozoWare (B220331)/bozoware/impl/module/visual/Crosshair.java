// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import net.minecraft.client.gui.ScaledResolution;
import bozoware.visual.font.MinecraftFontRenderer;
import bozoware.base.util.visual.RenderUtil;
import bozoware.base.util.player.MovementUtil;
import bozoware.base.BozoWare;
import java.awt.Color;
import bozoware.impl.property.ColorProperty;
import bozoware.impl.property.BooleanProperty;
import bozoware.base.event.EventListener;
import bozoware.impl.event.visual.Render2DEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Crosshair", moduleCategory = ModuleCategory.VISUAL)
public class Crosshair extends Module
{
    @EventListener
    EventConsumer<Render2DEvent> onRender2DEvent;
    private final BooleanProperty dynamicBool;
    private final BooleanProperty renderDot;
    private final ColorProperty crossColor;
    
    public Crosshair() {
        this.dynamicBool = new BooleanProperty("Dynamic", true, this);
        this.renderDot = new BooleanProperty("Dot", true, this);
        this.crossColor = new ColorProperty("Color", new Color(-65536), this);
        final MinecraftFontRenderer FR;
        final MinecraftFontRenderer arrowIcons;
        final ScaledResolution SR;
        this.onRender2DEvent = (e -> {
            FR = BozoWare.getInstance().getFontManager().largeFontRenderer;
            arrowIcons = BozoWare.getInstance().getFontManager().ArrowIcons;
            SR = e.getScaledResolution();
            if (this.dynamicBool.getPropertyValue()) {
                if (!MovementUtil.isMoving()) {
                    FR.drawString(".", SR.getScaledWidth() / 2 - 1.25, SR.getScaledHeight() / 2 - 7, this.crossColor.getColorRGB());
                    RenderUtil.drawSmoothRoundedRect((float)(SR.getScaledWidth() / 2 - 20), SR.getScaledHeight() / 2 - 0.3f, (float)(SR.getScaledWidth() / 2 - 5), SR.getScaledHeight() / 2 + 0.5f, 0.3f, this.crossColor.getColorRGB());
                    RenderUtil.drawSmoothRoundedRect((float)(SR.getScaledWidth() / 2 + 5), SR.getScaledHeight() / 2 - 0.3f, (float)(SR.getScaledWidth() / 2 + 20), SR.getScaledHeight() / 2 + 0.5f, 0.3f, this.crossColor.getColorRGB());
                    RenderUtil.drawSmoothRoundedRect(SR.getScaledWidth() / 2 - 0.3f, (float)(SR.getScaledHeight() / 2 - 20), SR.getScaledWidth() / 2 + 0.5f, (float)(SR.getScaledHeight() / 2 - 5), 0.3f, this.crossColor.getColorRGB());
                    RenderUtil.drawSmoothRoundedRect(SR.getScaledWidth() / 2 - 0.3f, (float)(SR.getScaledHeight() / 2 + 5), SR.getScaledWidth() / 2 + 0.5f, (float)(SR.getScaledHeight() / 2 + 20), 0.3f, this.crossColor.getColorRGB());
                }
                else {
                    FR.drawString(".", SR.getScaledWidth() / 2 - 1.25, SR.getScaledHeight() / 2 - 7, this.crossColor.getColorRGB());
                    RenderUtil.drawSmoothRoundedRect((float)(SR.getScaledWidth() / 2 - 25), SR.getScaledHeight() / 2 - 0.3f, (float)(SR.getScaledWidth() / 2 - 10), SR.getScaledHeight() / 2 + 0.5f, 0.3f, this.crossColor.getColorRGB());
                    RenderUtil.drawSmoothRoundedRect((float)(SR.getScaledWidth() / 2 + 10), SR.getScaledHeight() / 2 - 0.3f, (float)(SR.getScaledWidth() / 2 + 25), SR.getScaledHeight() / 2 + 0.5f, 0.3f, this.crossColor.getColorRGB());
                    RenderUtil.drawSmoothRoundedRect(SR.getScaledWidth() / 2 - 0.3f, (float)(SR.getScaledHeight() / 2 - 25), SR.getScaledWidth() / 2 + 0.5f, (float)(SR.getScaledHeight() / 2 - 10), 0.3f, this.crossColor.getColorRGB());
                    RenderUtil.drawSmoothRoundedRect(SR.getScaledWidth() / 2 - 0.3f, (float)(SR.getScaledHeight() / 2 + 10), SR.getScaledWidth() / 2 + 0.5f, (float)(SR.getScaledHeight() / 2 + 25), 0.3f, this.crossColor.getColorRGB());
                }
            }
            else {
                FR.drawString(".", SR.getScaledWidth() / 2 - 1.25, SR.getScaledHeight() / 2 - 7, this.crossColor.getColorRGB());
                RenderUtil.drawSmoothRoundedRect((float)(SR.getScaledWidth() / 2 - 20), SR.getScaledHeight() / 2 - 0.3f, (float)(SR.getScaledWidth() / 2 - 5), SR.getScaledHeight() / 2 + 0.5f, 0.3f, this.crossColor.getColorRGB());
                RenderUtil.drawSmoothRoundedRect((float)(SR.getScaledWidth() / 2 + 5), SR.getScaledHeight() / 2 - 0.3f, (float)(SR.getScaledWidth() / 2 + 20), SR.getScaledHeight() / 2 + 0.5f, 0.3f, this.crossColor.getColorRGB());
                RenderUtil.drawSmoothRoundedRect(SR.getScaledWidth() / 2 - 0.3f, (float)(SR.getScaledHeight() / 2 - 20), SR.getScaledWidth() / 2 + 0.5f, (float)(SR.getScaledHeight() / 2 - 5), 0.3f, this.crossColor.getColorRGB());
                RenderUtil.drawSmoothRoundedRect(SR.getScaledWidth() / 2 - 0.3f, (float)(SR.getScaledHeight() / 2 + 5), SR.getScaledWidth() / 2 + 0.5f, (float)(SR.getScaledHeight() / 2 + 20), 0.3f, this.crossColor.getColorRGB());
            }
        });
    }
}
