// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.util.player;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import java.util.ArrayList;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.opengl.GL11;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.ScaledResolution;
import java.util.Arrays;
import net.minecraft.util.Vector3d;
import javax.vecmath.Vector4d;
import bozoware.impl.module.visual.TargetHUD;
import net.minecraft.entity.EntityLivingBase;
import bozoware.base.util.visual.RenderUtil;
import bozoware.base.util.visual.BloomUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import bozoware.base.util.Wrapper;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.item.ItemBow;
import net.minecraft.client.renderer.culling.Frustum;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import bozoware.base.util.misc.TimerUtil;
import net.minecraft.client.Minecraft;

public class PlayerUtils
{
    private static final Minecraft mc;
    public static TimerUtil timer;
    private static boolean doneBow;
    private static final IntBuffer viewport;
    private static final FloatBuffer modelview;
    private static final FloatBuffer projection;
    private static final FloatBuffer vector;
    private static final Frustum frustrum;
    
    public static void bowSelf() {
        PlayerUtils.timer.reset();
        final int oldSlot = PlayerUtils.mc.thePlayer.inventory.currentItem;
        final Thread thread = new Thread() {
            @Override
            public void run() {
                final int oldSlot = PlayerUtils.mc.thePlayer.inventory.currentItem;
                ItemStack block = PlayerUtils.mc.thePlayer.getCurrentEquippedItem();
                if (block != null) {
                    block = null;
                }
                int slot = PlayerUtils.mc.thePlayer.inventory.currentItem;
                for (short g = 0; g < 9; ++g) {
                    if (PlayerUtils.mc.thePlayer.inventoryContainer.getSlot(g + 36).getHasStack() && PlayerUtils.mc.thePlayer.inventoryContainer.getSlot(g + 36).getStack().getItem() instanceof ItemBow && PlayerUtils.mc.thePlayer.inventoryContainer.getSlot(g + 36).getStack().stackSize != 0 && (block == null || block.getItem() instanceof ItemBow)) {
                        slot = g;
                        block = PlayerUtils.mc.thePlayer.inventoryContainer.getSlot(g + 36).getStack();
                    }
                }
                PlayerUtils.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot));
                PlayerUtils.mc.thePlayer.inventory.currentItem = slot;
                PlayerUtils.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(block));
                Wrapper.sendPacketDirect(new C03PacketPlayer.C05PacketPlayerLook(PlayerUtils.mc.thePlayer.rotationYaw, -90.0f, false));
                try {
                    Thread.sleep(90L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                PlayerUtils.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                try {
                    Thread.sleep(160L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                PlayerUtils.doneBow = true;
                if (PlayerUtils.mc.thePlayer.hurtTime > 0) {
                    PlayerUtils.mc.thePlayer.inventory.currentItem = ThreadLocalRandom.current().nextInt(1, 9);
                }
            }
        };
        thread.start();
        PlayerUtils.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(oldSlot));
        PlayerUtils.mc.thePlayer.inventory.currentItem = oldSlot;
    }
    
    public static double interpolate(final double current, final double old, final double scale) {
        return old + (current - old) * scale;
    }
    
    public static void draw2DESP(final int color, final Entity entity) {
        final double x = interpolate(entity.posX, entity.lastTickPosX, PlayerUtils.mc.timer.renderPartialTicks);
        final double y = interpolate(entity.posY, entity.lastTickPosY, PlayerUtils.mc.timer.renderPartialTicks);
        final double z = interpolate(entity.posZ, entity.lastTickPosZ, PlayerUtils.mc.timer.renderPartialTicks);
        final double width = entity.width / 1.5;
        final double height = entity.height + (entity.isSneaking() ? -0.3 : 0.2);
        final AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
        final Vector4d pos = get2DVector(aabb);
        if (pos == null) {
            return;
        }
        if (!isInViewFrustrum(aabb)) {
            return;
        }
        PlayerUtils.mc.entityRenderer.setupCameraTransform(PlayerUtils.mc.timer.renderPartialTicks, 0);
        PlayerUtils.mc.entityRenderer.setupOverlayRendering();
        final double posX = pos.x;
        final double posY = pos.y;
        final double endPosX = pos.z;
        final double endPosY = pos.w;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final int size = 20;
        BloomUtil.drawAndBloom(() -> Gui.drawRect((int)posX, (int)posY, (float)endPosX, (float)endPosY, 1073741824));
        BloomUtil.drawAndBloom(() -> RenderUtil.drawBoxOutline((int)posX, (int)posY, (float)endPosX, (float)endPosY, color, 0.5));
        final boolean living = entity instanceof EntityLivingBase;
        if (living) {
            float hp = ((EntityLivingBase)entity).getHealth();
            final float maxHealth = ((EntityLivingBase)entity).getMaxHealth();
            if (hp > maxHealth) {
                hp = maxHealth;
            }
            final double hpPercentage = hp / maxHealth;
            double hpAnimHeight = (endPosY - posY) * hpPercentage;
            final double hpRealHeight = (endPosY - posY) * hpPercentage;
            hpAnimHeight = RenderUtil.animate(hpRealHeight, hpAnimHeight, 0.05);
            Gui.drawRect(posX - 3.5, posY - 0.5, posX - 1.5, endPosY + 0.5, 1073741824);
            if (hp > 0.0f) {
                final int colorrectCode = TargetHUD.getHealthColor(hp, maxHealth).getRGB();
                Gui.drawRect(posX - 3.0, endPosY, posX - 2.0, endPosY - hpAnimHeight, colorrectCode);
            }
        }
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawImageESP(final Entity entity) {
        final double x = interpolate(entity.posX, entity.lastTickPosX, PlayerUtils.mc.timer.renderPartialTicks);
        final double y = interpolate(entity.posY, entity.lastTickPosY, PlayerUtils.mc.timer.renderPartialTicks);
        final double z = interpolate(entity.posZ, entity.lastTickPosZ, PlayerUtils.mc.timer.renderPartialTicks);
        final double width = entity.width / 1.5;
        final double height = entity.height + (entity.isSneaking() ? -0.3 : 0.2);
        final AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
        final Vector4d pos = get2DVector(aabb);
        if (aabb == null) {
            return;
        }
        if (pos == null) {
            return;
        }
        if (!isInViewFrustrum(aabb)) {
            return;
        }
        PlayerUtils.mc.entityRenderer.setupCameraTransform(PlayerUtils.mc.timer.renderPartialTicks, 0);
        PlayerUtils.mc.entityRenderer.setupOverlayRendering();
        final double posX = pos.x;
        final double posY = pos.y;
        final double endPosX = pos.z;
        final double endPosY = pos.w;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final int size = 1;
        final float drawX = (float)(endPosX - posX + size);
        final float drawY = (float)(endPosY - posY + size);
        Gui.drawModalRectWithCustomSizedTexture((float)(int)posX, (float)(int)posY, 0.0f, 0.0f, (float)(int)drawX, (float)((int)drawY - 1), drawX, drawY);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static Vector4d get2DVector(final AxisAlignedBB bb) {
        final List<Vector3d> vectors = Arrays.asList(new Vector3d(bb.minX, bb.minY, bb.minZ), new Vector3d(bb.minX, bb.maxY, bb.minZ), new Vector3d(bb.maxX, bb.minY, bb.minZ), new Vector3d(bb.maxX, bb.maxY, bb.minZ), new Vector3d(bb.minX, bb.minY, bb.maxZ), new Vector3d(bb.minX, bb.maxY, bb.maxZ), new Vector3d(bb.maxX, bb.minY, bb.maxZ), new Vector3d(bb.maxX, bb.maxY, bb.maxZ));
        PlayerUtils.mc.entityRenderer.setupCameraTransform(PlayerUtils.mc.timer.renderPartialTicks, 0);
        Vector4d position = null;
        final int scale = new ScaledResolution(Minecraft.getMinecraft(), PlayerUtils.mc.displayWidth, PlayerUtils.mc.displayHeight).getScaleFactor();
        for (Vector3d vector : vectors) {
            vector = project2D(scale, vector.field_181059_a - PlayerUtils.mc.getRenderManager().viewerPosX, vector.field_181060_b - PlayerUtils.mc.getRenderManager().viewerPosY, vector.field_181061_c - PlayerUtils.mc.getRenderManager().viewerPosZ);
            if (vector != null && vector.field_181061_c >= 0.0 && vector.field_181061_c < 1.0) {
                if (position == null) {
                    position = new Vector4d(vector.field_181059_a, vector.field_181060_b, vector.field_181061_c, 0.0);
                }
                position.x = Math.min(vector.field_181059_a, position.x);
                position.y = Math.min(vector.field_181060_b, position.y);
                position.z = Math.max(vector.field_181059_a, position.z);
                position.w = Math.max(vector.field_181060_b, position.w);
            }
        }
        return position;
    }
    
    public static Vector3d project2D(final int scaleFactor, final double x, final double y, final double z) {
        GL11.glGetFloat(2982, PlayerUtils.modelview);
        GL11.glGetFloat(2983, PlayerUtils.projection);
        GL11.glGetInteger(2978, PlayerUtils.viewport);
        if (GLU.gluProject((float)x, (float)y, (float)z, PlayerUtils.modelview, PlayerUtils.projection, PlayerUtils.viewport, PlayerUtils.vector)) {
            return new Vector3d(PlayerUtils.vector.get(0) / scaleFactor, (Display.getHeight() - PlayerUtils.vector.get(1)) / scaleFactor, PlayerUtils.vector.get(2));
        }
        return null;
    }
    
    public static boolean isOnSameTeam(final Entity entity) {
        if (!(entity instanceof EntityLivingBase)) {
            return false;
        }
        if (((EntityLivingBase)entity).getTeam() != null && PlayerUtils.mc.thePlayer.getTeam() != null) {
            final char c1 = entity.getDisplayName().getFormattedText().charAt(1);
            final char c2 = PlayerUtils.mc.thePlayer.getDisplayName().getFormattedText().charAt(1);
            return c1 == c2;
        }
        return false;
    }
    
    public static void damageVerusAdvanced() {
        PlayerUtils.mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(PlayerUtils.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
        double val1 = 0.0;
        for (int i = 0; i <= 6; ++i) {
            val1 += 0.5;
            PlayerUtils.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtils.mc.thePlayer.posX, PlayerUtils.mc.thePlayer.posY + val1, PlayerUtils.mc.thePlayer.posZ, true));
        }
        double val2 = PlayerUtils.mc.thePlayer.posY + val1;
        final ArrayList<Float> vals = new ArrayList<Float>();
        vals.add(0.0784f);
        vals.add(0.0784f);
        vals.add(0.23052737f);
        vals.add(0.30431682f);
        vals.add(0.37663049f);
        vals.add(0.4474979f);
        vals.add(0.5169479f);
        vals.add(0.585009f);
        vals.add(0.65170884f);
        vals.add(0.15372962f);
        for (final float value : vals) {
            val2 -= value;
        }
        PlayerUtils.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtils.mc.thePlayer.posX, val2, PlayerUtils.mc.thePlayer.posZ, false));
        PlayerUtils.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        PlayerUtils.mc.getNetHandler().getNetworkManager().sendPacket(new C0BPacketEntityAction(PlayerUtils.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
        PlayerUtils.mc.thePlayer.jump();
    }
    
    public static void damageVerusBasic() {
        PlayerUtils.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtils.mc.thePlayer.posX, PlayerUtils.mc.thePlayer.posY + 4.0, PlayerUtils.mc.thePlayer.posZ, false));
        PlayerUtils.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtils.mc.thePlayer.posX, PlayerUtils.mc.thePlayer.posY, PlayerUtils.mc.thePlayer.posZ, false));
        PlayerUtils.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(PlayerUtils.mc.thePlayer.posX, PlayerUtils.mc.thePlayer.posY, PlayerUtils.mc.thePlayer.posZ, true));
    }
    
    public static Entity raycast(final Minecraft mc, final double r3, final Entity entity) {
        if (entity == null) {
            return null;
        }
        final Entity var2 = mc.thePlayer;
        final Vec3 var3 = entity.getPositionVector().add(new Vec3(0.0, entity.getEyeHeight(), 0.0));
        final Vec3 var4 = mc.thePlayer.getPositionVector().add(new Vec3(0.0, mc.thePlayer.getEyeHeight(), 0.0));
        Vec3 var5 = null;
        final float var6 = 1.0f;
        final AxisAlignedBB a = mc.thePlayer.getEntityBoundingBox().addCoord(var3.xCoord - var4.xCoord, var3.yCoord - var4.yCoord, var3.zCoord - var4.zCoord).expand(var6, var6, var6);
        final List var7 = mc.theWorld.getEntitiesWithinAABBExcludingEntity(var2, a);
        double var8 = r3 + 0.5;
        Entity b = null;
        for (int var9 = 0; var9 < var7.size(); ++var9) {
            final Entity var10 = (Entity) var7.get(var9);
            if (var10.canBeCollidedWith()) {
                final float var11 = var10.getCollisionBorderSize();
                final AxisAlignedBB var12 = var10.getEntityBoundingBox().expand(var11, var11, var11);
                final MovingObjectPosition var13 = var12.calculateIntercept(var4, var3);
                if (var12.isVecInside(var4)) {
                    if (0.0 < var8 || var8 == 0.0) {
                        b = var10;
                        var5 = ((var13 == null) ? var4 : var13.hitVec);
                        var8 = 0.0;
                    }
                }
                else if (var13 != null) {
                    final double var14 = var4.distanceTo(var13.hitVec);
                    if (var14 < var8 || var8 == 0.0) {
                        b = var10;
                        var5 = var13.hitVec;
                        var8 = var14;
                    }
                }
            }
        }
        return b;
    }
    
    public static AxisAlignedBB interpolateAxis(final AxisAlignedBB bb) {
        return new AxisAlignedBB(RenderUtil.mc.getRenderManager().viewerPosX - bb.minX, PlayerUtils.mc.getRenderManager().viewerPosY - bb.minY, PlayerUtils.mc.getRenderManager().viewerPosZ - bb.minZ, RenderUtil.mc.getRenderManager().viewerPosX - bb.maxX, RenderUtil.mc.getRenderManager().viewerPosY - bb.maxY, RenderUtil.mc.getRenderManager().viewerPosZ - bb.maxZ);
    }
    
    public static boolean isInViewFrustrum(final AxisAlignedBB bb) {
        final Entity current = PlayerUtils.mc.getRenderViewEntity();
        PlayerUtils.frustrum.setPosition(current.posX, current.posY, current.posZ);
        return PlayerUtils.frustrum.isBoundingBoxInFrustum(bb);
    }
    
    static {
        mc = Minecraft.getMinecraft();
        PlayerUtils.timer = new TimerUtil();
        PlayerUtils.doneBow = false;
        viewport = GLAllocation.createDirectIntBuffer(16);
        modelview = GLAllocation.createDirectFloatBuffer(16);
        projection = GLAllocation.createDirectFloatBuffer(16);
        vector = GLAllocation.createDirectFloatBuffer(4);
        frustrum = new Frustum();
    }
}
