// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.world.World;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import bozoware.impl.property.EnumProperty;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.impl.event.block.EventAABB;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Phase", moduleCategory = ModuleCategory.WORLD)
public class Phase extends Module
{
    public static boolean phasing;
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<EventAABB> onEventAABB;
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    private final EnumProperty<Mode> theMode;
    static boolean sneaking;
    private int moveUnder;
    
    public Phase() {
        this.theMode = new EnumProperty<Mode>("Mode", Mode.Aris, this);
        this.moveUnder = 1;
        this.onModuleDisabled = (() -> {});
        this.onPacketReceiveEvent = (e -> {
            switch (this.theMode.getPropertyValue()) {
                case Aris: {
                    if (e.getPacket() instanceof S08PacketPlayerPosLook && this.moveUnder == 2) {
                        this.moveUnder = 1;
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            return;
        });
        double[] array;
        double[] vals;
        int length;
        int j = 0;
        double i;
        float yaw;
        double dist;
        double x;
        double y;
        double z;
        this.onUpdatePositionEvent = (e -> {
            switch (this.theMode.getPropertyValue()) {
                case SetPos: {
                    if (Phase.mc.thePlayer.onGround && Phase.mc.thePlayer.isSneaking() && Phase.mc.thePlayer.isCollidedHorizontally) {
                        vals = (array = new double[] { 0.333, 0.0 });
                        for (length = array.length; j < length; ++j) {
                            i = array[j];
                            yaw = Phase.mc.thePlayer.rotationYaw;
                            dist = 0.25;
                            x = Phase.mc.thePlayer.posX;
                            y = Phase.mc.thePlayer.posY;
                            z = Phase.mc.thePlayer.posZ;
                            Phase.mc.thePlayer.setPosition(x + -Math.sin(Math.toRadians(yaw)) * dist, Phase.mc.thePlayer.posY, z + Math.cos(Math.toRadians(yaw)) * dist);
                        }
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            return;
        });
        this.onEventAABB = (e -> {
            switch (this.theMode.getPropertyValue()) {
                case Aris: {
                    if (this.isInsideBlock()) {
                        e.setBoundingBox(null);
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
        });
    }
    
    private boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(Phase.mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(Phase.mc.thePlayer.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int y = MathHelper.floor_double(Phase.mc.thePlayer.getEntityBoundingBox().minY); y < MathHelper.floor_double(Phase.mc.thePlayer.getEntityBoundingBox().maxY) + 1; ++y) {
                for (int z = MathHelper.floor_double(Phase.mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(Phase.mc.thePlayer.getEntityBoundingBox().maxZ) + 1; ++z) {
                    final Block block = Phase.mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block != null && !(block instanceof BlockAir)) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(Phase.mc.theWorld, new BlockPos(x, y, z), Phase.mc.theWorld.getBlockState(new BlockPos(x, y, z)));
                        if (block instanceof BlockHopper) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }
                        if (boundingBox != null && Phase.mc.thePlayer.getEntityBoundingBox().intersectsWith(boundingBox)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public enum Mode
    {
        Aris, 
        SetPos;
    }
}
