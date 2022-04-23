// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.movement;

import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import java.util.Arrays;
import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.util.BlockPos;
import java.util.ArrayList;
import bozoware.impl.module.player.BlockFly;
import bozoware.base.BozoWare;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.property.EnumProperty;
import bozoware.impl.event.player.PlayerStepEvent;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "Step", moduleCategory = ModuleCategory.MOVEMENT)
public class Step extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    @EventListener
    EventConsumer<PlayerStepEvent> onStepEvent;
    private final EnumProperty<Modes> Mode;
    private final ValueProperty<Float> Height;
    double preY;
    private final double[][] offsets;
    private double stepTimer;
    
    public Step() {
        this.Mode = new EnumProperty<Modes>("Mode", Modes.Vanilla, this);
        this.Height = new ValueProperty<Float>("Step Height", 1.0f, 1.0f, 3.0f, this);
        this.offsets = new double[][] { { 0.41999998688698, 0.7531999805212 } };
        this.setModuleSuffix(this.Mode.getPropertyValue().toString());
        this.onModuleDisabled = (() -> {
            Step.mc.timer.timerSpeed = 1.0f;
            Step.mc.thePlayer.stepHeight = 0.5f;
            return;
        });
        this.onModuleEnabled = (() -> {});
        final double height;
        final boolean canStep;
        double[][] offsets;
        int length;
        int i = 0;
        double[] offset;
        this.onStepEvent = (e -> {
            switch (this.Mode.getPropertyValue()) {
                case NCP: {
                    height = Step.mc.thePlayer.getEntityBoundingBox().minY - Step.mc.thePlayer.posY;
                    canStep = (height >= 0.625 && Step.mc.thePlayer.onGround && Step.mc.thePlayer.isCollidedVertically && !BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Speed.class).isModuleToggled() && !BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Flight.class).isModuleToggled() && !BozoWare.getInstance().getModuleManager().getModuleByClass.apply(BlockFly.class).isModuleToggled());
                    Step.mc.thePlayer.stepHeight = this.Height.getPropertyValue();
                    if (!Step.mc.thePlayer.isInWater() && !Step.mc.thePlayer.isInLava() && !Step.mc.thePlayer.isOnLadder() && canStep) {
                        if (e.isPre()) {
                            e.setStepHeight(this.Height.getPropertyValue());
                            break;
                        }
                        else if (e.getStepHeight() >= this.Height.getPropertyValue() && canStep) {
                            offsets = this.offsets;
                            for (length = offsets.length; i < length; ++i) {
                                offset = offsets[i];
                                Step.mc.thePlayer.yC04(offset[0], false);
                                Step.mc.thePlayer.yC04(offset[1], false);
                            }
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            return;
        });
        this.onPacketReceiveEvent = (xd -> {});
        this.onUpdatePositionEvent = (e -> {
            if (this.Mode.getPropertyValue().equals(Modes.NCP)) {
                if (BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Speed.class).isModuleToggled() || BozoWare.getInstance().getModuleManager().getModuleByClass.apply(Flight.class).isModuleToggled() || BozoWare.getInstance().getModuleManager().getModuleByClass.apply(BlockFly.class).isModuleToggled()) {
                    Step.mc.thePlayer.stepHeight = 0.5f;
                }
                if (!Step.mc.thePlayer.onGround || !Step.mc.thePlayer.isCollidedVertically) {
                    Step.mc.thePlayer.stepHeight = 0.5f;
                }
            }
            if (e.isPre()) {
                this.preY = Step.mc.thePlayer.posY;
            }
            if (this.Mode.getPropertyValue().equals(Modes.Vanilla)) {
                Step.mc.thePlayer.stepHeight = this.Height.getPropertyValue();
                if (!Step.mc.thePlayer.isCollidedHorizontally || !Step.mc.thePlayer.onGround) {}
            }
            return;
        });
        this.Mode.onValueChange = (() -> this.setModuleSuffix(this.Mode.getPropertyValue().name()));
    }
    
    private boolean canStep() {
        final ArrayList<BlockPos> collisionBlocks = new ArrayList<BlockPos>();
        final Entity player = Step.mc.thePlayer;
        final BlockPos pos1 = new BlockPos(player.getEntityBoundingBox().minX - 0.001, player.getEntityBoundingBox().minY - 0.001, player.getEntityBoundingBox().minZ - 0.001);
        final BlockPos pos2 = new BlockPos(player.getEntityBoundingBox().maxX + 0.001, player.getEntityBoundingBox().maxY + 0.001, player.getEntityBoundingBox().maxZ + 0.001);
        if (player.worldObj.isAreaLoaded(pos1, pos2)) {
            for (int x = pos1.getX(); x <= pos2.getX(); ++x) {
                for (int y = pos1.getY(); y <= pos2.getY(); ++y) {
                    for (int z = pos1.getZ(); z <= pos2.getZ(); ++z) {
                        if (y > player.posY - 1.0 && y <= player.posY) {
                            collisionBlocks.add(new BlockPos(x, y, z));
                        }
                    }
                }
            }
        }
        final BlockPos belowPlayerPos = new BlockPos(player.posX, player.posY - 1.0, player.posZ);
        for (final BlockPos collisionBlock : collisionBlocks) {
            if (!(player.worldObj.getBlockState(collisionBlock.add(0, 1, 0)).getBlock() instanceof BlockFenceGate) && player.worldObj.getBlockState(collisionBlock.add(0, 1, 0)).getBlock().getCollisionBoundingBox(Step.mc.theWorld, belowPlayerPos, Step.mc.theWorld.getBlockState(collisionBlock)) != null) {
                return true;
            }
        }
        return true;
    }
    
    private void ncpStep(final double height) {
        final List<Double> offset = Arrays.asList(0.42, 0.333, 0.248, 0.083, -0.078);
        final double posX = Step.mc.thePlayer.posX;
        final double posZ = Step.mc.thePlayer.posZ;
        double y = Step.mc.thePlayer.posY;
        if (height < 1.1) {
            double first = 0.42;
            double second = 0.75;
            if (height != 1.0) {
                first *= height;
                second *= height;
                if (first > 0.425) {
                    first = 0.425;
                }
                if (second > 0.78) {
                    second = 0.78;
                }
                if (second < 0.49) {
                    second = 0.49;
                }
            }
            Step.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + first, posZ, false));
            if (y + second < y + height) {
                Step.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + second, posZ, false));
            }
            return;
        }
        if (height < 1.6) {
            for (int i = 0; i < offset.size(); ++i) {
                final double off = offset.get(i);
                y += off;
                Step.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(posX, y, posZ, false));
            }
        }
        else if (height < 2.1) {
            final double[] array;
            final double[] heights = array = new double[] { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869 };
            for (final double off2 : array) {
                Step.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + off2, posZ, false));
            }
        }
        else {
            final double[] array2;
            final double[] heights = array2 = new double[] { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907 };
            for (final double off2 : array2) {
                Step.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + off2, posZ, false));
            }
        }
    }
    
    private enum Modes
    {
        Vanilla, 
        NCP, 
        Verus;
    }
}
