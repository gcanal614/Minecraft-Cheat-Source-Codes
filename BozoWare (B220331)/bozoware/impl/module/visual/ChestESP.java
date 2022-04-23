// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.visual;

import bozoware.base.util.visual.RenderUtil;
import org.lwjgl.opengl.GL11;
import java.awt.Color;
import net.minecraft.tileentity.TileEntityChest;
import java.util.Iterator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import bozoware.base.event.EventListener;
import bozoware.impl.event.visual.EventRender3D;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Chest ESP", moduleCategory = ModuleCategory.WORLD)
public class ChestESP extends Module
{
    @EventListener
    EventConsumer<EventRender3D> onRender3D;
    
    public ChestESP() {
        final Iterator<TileEntity> iterator;
        TileEntity TE;
        double posX;
        double posY;
        double posZ;
        AxisAlignedBB bb;
        this.onRender3D = (r3D -> {
            ChestESP.mc.theWorld.loadedTileEntityList.iterator();
            while (iterator.hasNext()) {
                TE = iterator.next();
                if (this.canRender(TE)) {
                    posX = TE.getPos().getX() - RenderManager.renderPosX;
                    posY = TE.getPos().getY() - RenderManager.renderPosY;
                    posZ = TE.getPos().getZ() - RenderManager.renderPosZ;
                    bb = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(posX, posY, posZ);
                    this.drawBlockESP(bb, HUD.getInstance().bozoColorColor, 255, 5.0f);
                }
            }
        });
    }
    
    public boolean canRender(final TileEntity e) {
        return e instanceof TileEntityChest && ChestESP.mc.thePlayer != null;
    }
    
    private void drawBlockESP(final AxisAlignedBB bb, final Color color, final int alpha, final float width) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        RenderUtil.setColorWithAlpha(color.getRGB(), alpha);
        GL11.glLineWidth(width);
        RenderUtil.setColorWithAlpha(color.getRGB(), alpha);
        RenderUtil.drawOutlinedBox(bb);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
