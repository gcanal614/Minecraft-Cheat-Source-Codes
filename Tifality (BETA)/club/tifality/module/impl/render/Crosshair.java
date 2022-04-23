/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.render;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.render.Render2DEvent;
import club.tifality.manager.event.impl.render.RenderCrosshairEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.utils.Wrapper;
import club.tifality.utils.movement.PlayerInfoCache;
import club.tifality.utils.render.Colors;
import club.tifality.utils.render.LockedResolution;
import club.tifality.utils.render.RenderingUtils;
import net.minecraft.client.gui.Gui;

@ModuleInfo(label="Crosshair", category=ModuleCategory.RENDER)
public final class Crosshair
extends Module {
    private final DoubleProperty gapProperty = new DoubleProperty("Gap", 1.0, 0.0, 10.0, 0.5);
    private final DoubleProperty lengthProperty = new DoubleProperty("Length", 3.0, 0.0, 10.0, 0.5);
    private final DoubleProperty widthProperty = new DoubleProperty("Width", 1.0, 0.0, 5.0, 0.5);
    private final Property<Boolean> tShapeProperty = new Property<Boolean>("T Shape", Boolean.FALSE);
    private final Property<Boolean> dotProperty = new Property<Boolean>("Dot", Boolean.FALSE);
    private final Property<Boolean> dynamicProperty = new Property<Boolean>("Dynamic", Boolean.TRUE);
    private final Property<Boolean> outlineProperty = new Property<Boolean>("Outline", Boolean.TRUE);
    private final DoubleProperty outlineWidthProperty = new DoubleProperty("Outline Width", 0.5, this.outlineProperty::getValue, 0.5, 5.0, 0.5);
    private final Property<Integer> colorProperty = new Property<Integer>("Color", Colors.GREEN);

    @Listener
    public void onRenderCrosshairEvent(RenderCrosshairEvent event) {
        event.setCancelled();
    }

    @Listener
    public void onRender2D(Render2DEvent event) {
        double width = (Double)this.widthProperty.getValue();
        double halfWidth = width / 2.0;
        double gap = (Double)this.gapProperty.getValue();
        if (this.dynamicProperty.getValue().booleanValue()) {
            // empty if block
        }
        gap *= Math.max(Wrapper.getPlayer().isSneaking() ? 0.5 : 1.0, RenderingUtils.interpolate(PlayerInfoCache.getPrevLastDist(), PlayerInfoCache.getLastDist(), event.getPartialTicks()) * 10.0);
        double length = (Double)this.lengthProperty.getValue();
        int color = this.colorProperty.getValue();
        double outlineWidth = (Double)this.outlineWidthProperty.getValue();
        boolean outline = this.outlineProperty.getValue();
        boolean tShape = this.tShapeProperty.getValue();
        LockedResolution lr = event.getResolution();
        double thick = (Double)this.outlineWidthProperty.get();
        double middleX = (double)lr.getWidth() / 2.0;
        double middleY = (double)lr.getHeight() / 2.0;
        if (outline) {
            Gui.drawRect(middleX - gap - length - outlineWidth, middleY - halfWidth - outlineWidth, middleX - gap + outlineWidth, middleY + halfWidth + outlineWidth, -1778384896);
            Gui.drawRect(middleX + gap - outlineWidth, middleY - halfWidth - outlineWidth, middleX + gap + length + outlineWidth, middleY + halfWidth + outlineWidth, -1778384896);
            Gui.drawRect(middleX - halfWidth - outlineWidth, middleY + gap - outlineWidth, middleX + halfWidth + outlineWidth, middleY + gap + length + outlineWidth, -1778384896);
            if (!tShape) {
                Gui.drawRect(middleX - halfWidth - outlineWidth, middleY - gap - length - outlineWidth, middleX + halfWidth + outlineWidth, middleY - gap + outlineWidth, -1778384896);
            }
        }
        if (this.dotProperty.get().booleanValue()) {
            Gui.drawRect(middleX - thick - (Double)this.outlineWidthProperty.get(), middleY - thick - (Double)this.outlineWidthProperty.get(), middleX + thick + (Double)this.outlineWidthProperty.get(), middleY + thick + (Double)this.outlineWidthProperty.get(), -1778384896);
            Gui.drawRect(middleX - thick, middleY - thick, middleX + thick, middleY + thick, color);
        }
        Gui.drawRect(middleX - gap - length, middleY - halfWidth, middleX - gap, middleY + halfWidth, color);
        Gui.drawRect(middleX + gap, middleY - halfWidth, middleX + gap + length, middleY + halfWidth, color);
        Gui.drawRect(middleX - halfWidth, middleY + gap, middleX + halfWidth, middleY + gap + length, color);
        if (!tShape) {
            Gui.drawRect(middleX - halfWidth, middleY - gap - length, middleX + halfWidth, middleY - gap, color);
        }
    }
}

