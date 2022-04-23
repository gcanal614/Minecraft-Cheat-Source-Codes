// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import net.minecraft.util.MathHelper;
import bozoware.base.util.Wrapper;
import net.minecraft.network.play.client.C03PacketPlayer;
import bozoware.base.util.misc.TimerUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.BlockBed;
import net.minecraft.util.BlockPos;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Bed Breaker", moduleCategory = ModuleCategory.PLAYER)
public class BedBreaker extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    
    public BedBreaker() {
        int x;
        int y;
        int z;
        BlockPos pos;
        float[] rot;
        TimerUtil timer;
        this.onUpdatePositionEvent = (e -> {
            if (e.isPre()) {
                for (x = -3; x < 3; ++x) {
                    for (y = -3; y < 3; ++y) {
                        for (z = -3; z < 3; ++z) {
                            pos = new BlockPos(BedBreaker.mc.thePlayer.posX + x, BedBreaker.mc.thePlayer.posY + y, BedBreaker.mc.thePlayer.posZ + z);
                            if (pos.getBlock() instanceof BlockBed) {
                                rot = this.getRotationsToBlock(pos, EnumFacing.NORTH);
                                e.setYaw(rot[0]);
                                e.setPitch(rot[1]);
                                BedBreaker.mc.getNetHandler().getNetworkManager().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.NORTH));
                                timer = new TimerUtil();
                                if (timer.hasReached(250L) && !BedBreaker.mc.isSingleplayer() && BedBreaker.mc.getCurrentServerData().serverIP.contains("hypixel.net")) {
                                    Wrapper.sendPacketDirect(new C03PacketPlayer.C04PacketPlayerPosition(pos.getX(), pos.getY() + 1, pos.getZ(), true));
                                    timer.reset();
                                }
                                BedBreaker.mc.getNetHandler().getNetworkManager().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.NORTH));
                                BedBreaker.mc.thePlayer.swingItem();
                            }
                        }
                    }
                }
            }
        });
    }
    
    public float[] getRotationsToBlock(final BlockPos paramBlockPos, final EnumFacing paramEnumFacing) {
        final double d1 = paramBlockPos.getX() + 0.5 - BedBreaker.mc.thePlayer.posX + paramEnumFacing.getFrontOffsetX() / 2.0;
        final double d2 = paramBlockPos.getZ() + 0.5 - BedBreaker.mc.thePlayer.posZ + paramEnumFacing.getFrontOffsetZ() / 2.0;
        final double d3 = BedBreaker.mc.thePlayer.posY + BedBreaker.mc.thePlayer.getEyeHeight() - (paramBlockPos.getY() + 0.5);
        final double d4 = MathHelper.sqrt_double(d1 * d1 + d2 * d2);
        float f1 = (float)(Math.atan2(d2, d1) * 180.0 / 3.141592653589793) - 90.0f;
        final float f2 = (float)(Math.atan2(d3, d4) * 180.0 / 3.141592653589793);
        if (f1 < 0.0f) {
            f1 += 360.0f;
        }
        return new float[] { f1, f2 };
    }
}
