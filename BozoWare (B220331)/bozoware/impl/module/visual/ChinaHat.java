// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import org.lwjgl.util.glu.Cylinder;
import bozoware.base.util.visual.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import java.awt.Color;
import bozoware.impl.property.ColorProperty;
import bozoware.impl.property.ValueProperty;
import bozoware.base.event.EventListener;
import bozoware.impl.event.visual.EventRender3D;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "ChinaHat", moduleCategory = ModuleCategory.VISUAL)
public class ChinaHat extends Module
{
    @EventListener
    EventConsumer<EventRender3D> onEventRender3D;
    private final ValueProperty<Float> Width;
    private final ValueProperty<Float> Height;
    private final ValueProperty<Integer> Sides;
    private final ColorProperty color;
    private final ValueProperty<Integer> alpha;
    
    public ChinaHat() {
        this.Width = new ValueProperty<Float>("Width", 0.86f, 0.0f, 1.0f, this);
        this.Height = new ValueProperty<Float>("Height", 0.3f, 0.0f, 1.0f, this);
        this.Sides = new ValueProperty<Integer>("Sides", 30, 3, 90, this);
        this.color = new ColorProperty("Color", new Color(-65536), this);
        this.alpha = new ValueProperty<Integer>("Opacity", 100, 0, 255, this);
        final Cylinder cylinder;
        this.onEventRender3D = (EventRender3D -> {
            GlStateManager.pushMatrix();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            RenderUtil.setColorWithAlpha(this.color.getColorRGB(), this.alpha.getPropertyValue());
            GlStateManager.disableCull();
            if (!ChinaHat.mc.thePlayer.isSneaking()) {
                GlStateManager.translate(0.0f, ChinaHat.mc.thePlayer.height + 0.3f, 0.0f);
            }
            else {
                GlStateManager.translate(0.0f, ChinaHat.mc.thePlayer.height + 0.1f, 0.0f);
            }
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            cylinder = new Cylinder();
            cylinder.setDrawStyle(100012);
            cylinder.draw(0.0f, (float)this.Width.getPropertyValue(), (float)this.Height.getPropertyValue(), (int)this.Sides.getPropertyValue(), 69);
            GlStateManager.disableColorMaterial();
            GlStateManager.enableTexture2D();
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 255.0f);
            GlStateManager.popMatrix();
        });
    }
}
