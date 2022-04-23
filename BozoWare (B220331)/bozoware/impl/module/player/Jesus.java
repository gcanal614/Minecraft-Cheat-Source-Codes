// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.BlockLiquid;
import bozoware.impl.event.block.EventAABB;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Jesus", moduleCategory = ModuleCategory.PLAYER)
public class Jesus extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<EventAABB> onEventAABB;
    static boolean wasWater;
    
    public Jesus() {
        this.onUpdatePositionEvent = (event -> {
            if (event.isPre()) {
                if (isOnLiquid()) {
                    event.setOnGround(false);
                }
                if (event.isPre() && (!Jesus.mc.thePlayer.isBurning() || !this.isOnWater())) {
                    if (isInLiquid() && !Jesus.mc.gameSettings.keyBindSneak.isKeyDown() && !Jesus.mc.gameSettings.keyBindJump.isKeyDown() && Jesus.mc.thePlayer.fallDistance < 3.0f) {
                        Jesus.mc.thePlayer.motionY = 0.1;
                    }
                }
            }
            return;
        });
        final Block block;
        this.onEventAABB = (event -> {
            block = Jesus.mc.theWorld.getBlockState(event.getPos()).getBlock();
            if (Jesus.mc.theWorld != null && Jesus.mc.thePlayer.fallDistance <= 3.0f && (!Jesus.mc.thePlayer.isBurning() || !this.isOnWater())) {
                if (block instanceof BlockLiquid && !isInLiquid() && !Jesus.mc.thePlayer.isSneaking()) {
                    event.setBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0).contract(0.0, 0.0, 0.0).offset(event.getPos().getX(), event.getPos().getY(), event.getPos().getZ()));
                }
            }
        });
    }
    
    public static boolean isOnLiquid() {
        final double y = Jesus.mc.thePlayer.posY - 0.03;
        for (int x = MathHelper.floor_double(Jesus.mc.thePlayer.posX); x < MathHelper.ceiling_double_int(Jesus.mc.thePlayer.posX); ++x) {
            for (int z = MathHelper.floor_double(Jesus.mc.thePlayer.posZ); z < MathHelper.ceiling_double_int(Jesus.mc.thePlayer.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, MathHelper.floor_double(y), z);
                if (Jesus.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isInLiquid() {
        final double y = Jesus.mc.thePlayer.posY + 0.01;
        for (int x = MathHelper.floor_double(Jesus.mc.thePlayer.posX); x < MathHelper.ceiling_double_int(Jesus.mc.thePlayer.posX); ++x) {
            for (int z = MathHelper.floor_double(Jesus.mc.thePlayer.posZ); z < MathHelper.ceiling_double_int(Jesus.mc.thePlayer.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, (int)y, z);
                if (Jesus.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isOnWater() {
        final double y = Jesus.mc.thePlayer.posY - 0.03;
        for (int x = MathHelper.floor_double(Jesus.mc.thePlayer.posX); x < MathHelper.ceiling_double_int(Jesus.mc.thePlayer.posX); ++x) {
            for (int z = MathHelper.floor_double(Jesus.mc.thePlayer.posZ); z < MathHelper.ceiling_double_int(Jesus.mc.thePlayer.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, MathHelper.floor_double(y), z);
                if (Jesus.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid && Jesus.mc.theWorld.getBlockState(pos).getBlock().getMaterial() == Material.water) {
                    return true;
                }
            }
        }
        return false;
    }
}
