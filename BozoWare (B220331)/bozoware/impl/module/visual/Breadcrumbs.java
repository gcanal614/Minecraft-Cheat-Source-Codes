// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import java.util.Iterator;
import bozoware.base.util.visual.RenderUtil;
import org.lwjgl.opengl.GL11;
import java.awt.Color;
import bozoware.base.util.misc.TimerUtil;
import java.util.ArrayDeque;
import bozoware.impl.property.ColorProperty;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.property.BooleanProperty;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.visual.EventRender3D;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "BreadCrumbs", moduleCategory = ModuleCategory.VISUAL)
public class Breadcrumbs extends Module
{
    @EventListener
    EventConsumer<EventRender3D> onRender3D;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    public final BooleanProperty cleanEnabled;
    public final BooleanProperty wallHacks;
    private final ValueProperty<Float> thicc;
    private final ColorProperty color;
    private final ArrayDeque<double[]> positions;
    TimerUtil timer;
    
    public Breadcrumbs() {
        this.cleanEnabled = new BooleanProperty("Clear On Enable", true, this);
        this.wallHacks = new BooleanProperty("Through Walls", false, this);
        this.thicc = new ValueProperty<Float>("Line Thickness", 2.0f, 1.0f, 10.0f, this);
        this.color = new ColorProperty("Color", new Color(-65536), this);
        this.positions = new ArrayDeque<double[]>();
        this.timer = new TimerUtil();
        this.onModuleEnabled = (() -> {
            if (this.cleanEnabled.getPropertyValue()) {
                this.positions.clear();
            }
            return;
        });
        this.onUpdatePositionEvent = (e -> {
            synchronized (this.positions) {
                this.positions.add(new double[] { Breadcrumbs.mc.thePlayer.posX, Breadcrumbs.mc.thePlayer.getEntityBoundingBox().minY + 0.01, Breadcrumbs.mc.thePlayer.posZ });
            }
            if (this.timer.hasReached(25L)) {
                this.positions.removeLast();
                this.timer.reset();
            }
            this.timer.reset();
            return;
        });
        final double renderPosX;
        final double renderPosY;
        final double renderPosZ;
        final double x;
        final double y;
        final double z;
        final Iterator<double[]> iterator;
        double[] pos;
        this.onRender3D = (event -> {
            renderPosX = Breadcrumbs.mc.getRenderManager().viewerPosX;
            renderPosY = Breadcrumbs.mc.getRenderManager().viewerPosY;
            renderPosZ = Breadcrumbs.mc.getRenderManager().viewerPosZ;
            x = Breadcrumbs.mc.thePlayer.lastTickPosX + (Breadcrumbs.mc.thePlayer.posX - Breadcrumbs.mc.thePlayer.lastTickPosX) * Breadcrumbs.mc.timer.renderPartialTicks - renderPosX;
            y = Breadcrumbs.mc.thePlayer.lastTickPosY + (Breadcrumbs.mc.thePlayer.posY - Breadcrumbs.mc.thePlayer.lastTickPosY) * Breadcrumbs.mc.timer.renderPartialTicks - renderPosY;
            z = Breadcrumbs.mc.thePlayer.lastTickPosZ + (Breadcrumbs.mc.thePlayer.posZ - Breadcrumbs.mc.thePlayer.lastTickPosZ) * Breadcrumbs.mc.timer.renderPartialTicks - renderPosZ;
            synchronized (this.positions) {
                GL11.glPushMatrix();
                GL11.glDisable(3553);
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(2848);
                GL11.glEnable(3042);
                GL11.glLineWidth((float)this.thicc.getPropertyValue());
                if (!this.wallHacks.getPropertyValue()) {
                    GL11.glEnable(2929);
                }
                else {
                    GL11.glDisable(2929);
                }
                Breadcrumbs.mc.entityRenderer.disableLightmap();
                GL11.glBegin(3);
                RenderUtil.setColor(this.color.getColorRGB());
                this.positions.iterator();
                while (iterator.hasNext()) {
                    pos = iterator.next();
                    GL11.glVertex3d(pos[0] - renderPosX, pos[1] - renderPosY, pos[2] - renderPosZ);
                }
                GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
                GL11.glEnd();
                GL11.glEnable(2929);
                GL11.glDisable(2848);
                GL11.glDisable(3042);
                GL11.glEnable(3553);
                GL11.glPopMatrix();
            }
        });
    }
}
