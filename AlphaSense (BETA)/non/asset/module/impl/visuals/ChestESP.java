package non.asset.module.impl.visuals;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.AxisAlignedBB;
import non.asset.event.bus.Handler;
import non.asset.event.impl.render.Render3DEvent;
import non.asset.module.Module;
import non.asset.utils.OFC.RenderUtil;
import non.asset.utils.value.impl.BooleanValue;
import non.asset.utils.value.impl.NumberValue;

public class ChestESP extends Module {

    private NumberValue<Float> red = new NumberValue<>("Red", 255f, 1f, 255f, 1f);
    private NumberValue<Float> green = new NumberValue<>("Green", 255f, 1f, 255f, 1f);
    private NumberValue<Float> blue = new NumberValue<>("Blue", 255f, 1f, 255f, 1f);
    private NumberValue<Float> alpha = new NumberValue<>("Alpha", 255f, 1f, 255f, 1f);

    private BooleanValue enderchest = new BooleanValue("Ender Chests", true);
    
    private NumberValue<Float> ered = new NumberValue<>("Ender Red", 95f, 1f, 255f, 1f, enderchest, "true");
    private NumberValue<Float> egreen = new NumberValue<>("Ender Green", 115f, 1f, 255f, 1f, enderchest, "true");
    private NumberValue<Float> eblue = new NumberValue<>("Ender Blue", 215f, 1f, 255f, 1f, enderchest, "true");
    private NumberValue<Float> ealpha = new NumberValue<>("Ender Alpha", 255f, 1f, 255f, 1f, enderchest, "true");
    
    public ChestESP() {
        super("ChestESP", Category.VISUALS);
    }

    @Handler
    public void render3d(Render3DEvent event) {
        for (TileEntity tile : getMc().theWorld.loadedTileEntityList) {
            double posX = tile.getPos().getX() - getMc().getRenderManager().getRenderPosX();
            double posY = tile.getPos().getY() -getMc().getRenderManager().getRenderPosY();
            double posZ = tile.getPos().getZ() - getMc().getRenderManager().getRenderPosZ();
            if (tile instanceof TileEntityChest) {

                AxisAlignedBB bb = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(posX, posY, posZ);
                TileEntityChest adjacent = null;
                if (((TileEntityChest) tile).adjacentChestXNeg != null)
                    adjacent = ((TileEntityChest) tile).adjacentChestXNeg;
                if (((TileEntityChest) tile).adjacentChestXPos != null)
                    adjacent = ((TileEntityChest) tile).adjacentChestXPos;
                if (((TileEntityChest) tile).adjacentChestZNeg != null)
                    adjacent = ((TileEntityChest) tile).adjacentChestZNeg;
                if (((TileEntityChest) tile).adjacentChestZPos != null)
                    adjacent = ((TileEntityChest) tile).adjacentChestZPos;
                if (adjacent != null)
                    bb = bb.union(new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(adjacent.getPos().getX() - getMc().getRenderManager().getRenderPosX(), adjacent.getPos().getY() - getMc().getRenderManager().getRenderPosY(), adjacent.getPos().getZ() - getMc().getRenderManager().getRenderPosZ()));

                if (((TileEntityChest) tile).getChestType() == 1) {


                    drawBlockESP(bb, 255, 0, 0, 255,1f);
                } else {

                	drawBlockESP(bb, red.getValue(), green.getValue(), blue.getValue(), alpha.getValue(),1f);

                }

            }

            AxisAlignedBB bb = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(posX, posY, posZ);
            TileEntityChest adjacent = null;
            
            if (tile instanceof TileEntityEnderChest) {

            	if(enderchest.isEnabled()) {
            		drawBlockESP(bb, ered.getValue(), egreen.getValue(), eblue.getValue(), ealpha.getValue(),1f);
            	}
            }
        }
    }
    private void drawBlockESP(AxisAlignedBB bb, float red, float green, float blue, float alpha,float width) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red / 255f, green / 255f, blue / 255f, 0.2f);
        RenderUtil.drawBoundingBox(bb);
        GL11.glLineWidth(width);
        GL11.glColor4f(red / 255f, green / 255f, blue / 255f, alpha / 255f);
        RenderUtil.drawOutlinedBoundingBox(bb);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }
}