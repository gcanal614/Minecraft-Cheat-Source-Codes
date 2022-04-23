// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import bozoware.base.util.Wrapper;
import net.minecraft.util.BlockPos;
import net.minecraft.init.Blocks;
import bozoware.impl.property.EnumProperty;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.PlayerMoveEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "IceSpeed", moduleCategory = ModuleCategory.MOVEMENT)
public class IceSpeed extends Module
{
    @EventListener
    EventConsumer<PlayerMoveEvent> playerMoveEvent;
    private final EnumProperty<Modes> Mode;
    
    public IceSpeed() {
        this.Mode = new EnumProperty<Modes>("Mode", Modes.NCP, this);
        this.setModuleSuffix(this.Mode.getPropertyValue().toString());
        this.setModuleBind(0);
        this.onModuleEnabled = (() -> {});
        this.onModuleDisabled = (() -> {
            Blocks.ice.slipperiness = 0.98f;
            Blocks.packed_ice.slipperiness = 0.98f;
            return;
        });
        final Material material;
        Block upBlock;
        EntityPlayerSP thePlayer;
        EntityPlayerSP thePlayer2;
        EntityPlayerSP thePlayer3;
        EntityPlayerSP thePlayer4;
        this.playerMoveEvent = (e -> {
            switch (this.Mode.getPropertyValue()) {
                case Vanilla: {
                    if (Wrapper.getBlock(new BlockPos(IceSpeed.mc.thePlayer.posX, IceSpeed.mc.thePlayer.posY - 0.001, IceSpeed.mc.thePlayer.posZ)).getMaterial() != Material.ice) {
                        if (Wrapper.getBlock(new BlockPos(IceSpeed.mc.thePlayer.posX, IceSpeed.mc.thePlayer.posY - 0.001, IceSpeed.mc.thePlayer.posZ)).getMaterial() != Material.packedIce) {
                            break;
                        }
                    }
                    IceSpeed.mc.thePlayer.motionY = -1.0;
                    IceSpeed.mc.thePlayer.jump();
                    break;
                }
                case Spartan: {
                    material = Wrapper.getBlock(new BlockPos(IceSpeed.mc.thePlayer.posX, IceSpeed.mc.thePlayer.posY - 1.0, IceSpeed.mc.thePlayer.posZ)).getMaterial();
                    if (material == Material.ice || material == Material.packedIce) {
                        upBlock = Wrapper.getBlock(new BlockPos(IceSpeed.mc.thePlayer.posX, IceSpeed.mc.thePlayer.posY + 2.0, IceSpeed.mc.thePlayer.posZ));
                        if (!(upBlock instanceof BlockAir)) {
                            thePlayer = IceSpeed.mc.thePlayer;
                            thePlayer.motionX *= 1.342;
                            thePlayer2 = IceSpeed.mc.thePlayer;
                            thePlayer2.motionZ *= 1.342;
                        }
                        else {
                            thePlayer3 = IceSpeed.mc.thePlayer;
                            thePlayer3.motionX *= 1.18;
                            thePlayer4 = IceSpeed.mc.thePlayer;
                            thePlayer4.motionZ *= 1.18;
                        }
                        Blocks.ice.slipperiness = 0.6f;
                        Blocks.packed_ice.slipperiness = 0.6f;
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
                case NCP: {
                    Blocks.ice.slipperiness = 0.39f;
                    Blocks.packed_ice.slipperiness = 0.39f;
                    break;
                }
            }
        });
    }
    
    private enum Modes
    {
        NCP, 
        Spartan, 
        Vanilla;
    }
}
