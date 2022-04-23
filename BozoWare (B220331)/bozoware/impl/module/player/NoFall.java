// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.player;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;
import net.minecraft.util.AxisAlignedBB;
import bozoware.impl.module.movement.Flight;
import bozoware.base.BozoWare;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import bozoware.base.util.Wrapper;
import bozoware.impl.property.EnumProperty;
import bozoware.impl.event.block.EventAABB;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "No Fall", moduleCategory = ModuleCategory.PLAYER)
public class NoFall extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<EventAABB> onEventAABB;
    private final EnumProperty<NoFallModes> noFallModesProperty;
    
    public NoFall() {
        this.noFallModesProperty = new EnumProperty<NoFallModes>("Mode", NoFallModes.Edit, this);
        BlockPos blockpos;
        this.onUpdatePositionEvent = (updatePositionEvent -> {
            if (updatePositionEvent.isPre()) {
                this.setModuleSuffix(this.noFallModesProperty.getPropertyValue().name());
                switch (this.noFallModesProperty.getPropertyValue()) {
                    case TP: {
                        if (NoFall.mc.thePlayer.fallDistance >= 3.0f && this.isBlockUnder()) {
                            blockpos = this.getClosestBlockUnder();
                            NoFall.mc.thePlayer.fallDistance = 0.0f;
                            NoFall.mc.thePlayer.getEntityBoundingBox().offsetAndUpdate(0.0, -(NoFall.mc.thePlayer.posY - blockpos.getY() - 1.0), 0.0);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Edit: {
                        if (Wrapper.getPlayer().fallDistance > 3.0f) {
                            updatePositionEvent.setOnGround(true);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Watchdog: {
                        if (NoFall.mc.thePlayer.fallDistance > 2.75 && updatePositionEvent.isPre()) {
                            Wrapper.sendPacketDirect(new C03PacketPlayer(true));
                            NoFall.mc.thePlayer.fallDistance = 0.0f;
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Edit2: {
                        if (Wrapper.getPlayer().fallDistance >= 3.0f) {
                            Wrapper.sendPacketDirect(new C03PacketPlayer(false));
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                }
            }
            return;
        });
        AxisAlignedBB blockBB;
        this.onEventAABB = (e -> {
            switch (this.noFallModesProperty.getPropertyValue()) {
                case Verus: {
                    if (BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Flight.class).isModuleToggled()) {
                        return;
                    }
                    else if (NoFall.mc.thePlayer.fallDistance >= 2.0f && e.getPos().getY() < NoFall.mc.thePlayer.posY && !NoFall.mc.gameSettings.keyBindSneak.pressed) {
                        blockBB = AxisAlignedBB.fromBounds(-5000.0, -1.0, -5000.0, 5000.0, 1.0, 5000.0).offset(e.getPos().getX(), e.getPos().getY(), e.getPos().getZ());
                        e.setBoundingBox(blockBB);
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
    
    private boolean isBlockUnder() {
        for (int i = (int)(NoFall.mc.thePlayer.posY - 1.0); i > 0; --i) {
            final BlockPos pos = new BlockPos(NoFall.mc.thePlayer.posX, i, NoFall.mc.thePlayer.posZ);
            if (!(NoFall.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir)) {
                return true;
            }
        }
        return false;
    }
    
    private BlockPos getClosestBlockUnder() {
        int i = (int)NoFall.mc.thePlayer.posY - 1;
        while (i > 0) {
            final BlockPos pos = new BlockPos(NoFall.mc.thePlayer.posX, i, NoFall.mc.thePlayer.posZ);
            if (NoFall.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
                --i;
            }
            else {
                if (NoFall.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
                }
                return pos;
            }
        }
        return NoFall.mc.thePlayer.getPosition();
    }
    
    private enum NoFallModes
    {
        Edit, 
        Edit2, 
        Watchdog, 
        TP, 
        Verus;
    }
}
