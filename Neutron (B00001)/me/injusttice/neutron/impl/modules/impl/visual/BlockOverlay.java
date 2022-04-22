package me.injusttice.neutron.impl.modules.impl.visual;

import me.injusttice.neutron.api.events.impl.EventRender3D;
import me.injusttice.neutron.utils.render.Render2DUtils;
import me.injusttice.neutron.utils.render.RenderUtil;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import me.injusttice.neutron.api.events.EventTarget;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class BlockOverlay extends Module {

    public BlockOverlay() {
        super("BlockOverlay", 0, Category.VISUAL);
    }

    @EventTarget
    public void on3D(EventRender3D e) {
        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos pos = mc.objectMouseOver.getBlockPos();
            Block block = mc.theWorld.getBlockState(pos).getBlock();
            RenderManager renderManager = mc.getRenderManager();
            String s = block.getLocalizedName();
            mc.getRenderManager();
            double x = (double)pos.getX() - renderManager.getRenderPosX();
            mc.getRenderManager();
            double y = (double)pos.getY() - renderManager.getRenderPosY();
            mc.getRenderManager();
            double z = (double)pos.getZ() - renderManager.getRenderPosZ();
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            Color c = new Color(192, 75, 255);
            int r = c.getRed();
            int g = c.getGreen();
            int b = c.getBlue();
            Render2DUtils.glColor(new Color(r, g, b, 50).getRGB());
            double minX = block instanceof BlockStairs || Block.getIdFromBlock((Block)block) == 134 ? 0.0 : block.getBlockBoundsMinX();
            double minY = block instanceof BlockStairs || Block.getIdFromBlock((Block)block) == 134 ? 0.0 : block.getBlockBoundsMinY();
            double minZ = block instanceof BlockStairs || Block.getIdFromBlock((Block)block) == 134 ? 0.0 : block.getBlockBoundsMinZ();
            RenderUtil.drawBoundingBox(new AxisAlignedBB(x + minX, y + minY, z + minZ, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
            Render2DUtils.glColor(new Color(130, 60, 255).getRGB());
            GL11.glLineWidth(0.5f);
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
        }
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }
}