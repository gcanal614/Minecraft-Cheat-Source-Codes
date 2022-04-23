// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.impl.module.combat;

import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Mouse;
import bozoware.visual.screens.dropdown.GuiDropDown;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemBow;
import bozoware.impl.property.ValueProperty;
import bozoware.impl.property.EnumProperty;
import bozoware.impl.event.network.PacketReceiveEvent;
import bozoware.base.event.EventListener;
import bozoware.impl.event.player.UpdatePositionEvent;
import bozoware.base.event.EventConsumer;
import bozoware.base.module.ModuleCategory;
import bozoware.base.module.ModuleData;
import bozoware.base.module.Module;

@ModuleData(moduleName = "FastBow", moduleCategory = ModuleCategory.COMBAT)
public class FastBow extends Module
{
    @EventListener
    EventConsumer<UpdatePositionEvent> onUpdatePositionEvent;
    @EventListener
    EventConsumer<PacketReceiveEvent> onPacketReceiveEvent;
    private final EnumProperty<Modes> Mode;
    private final ValueProperty<Integer> Packets;
    
    public FastBow() {
        this.Mode = new EnumProperty<Modes>("Mode", Modes.Basic, this);
        this.Packets = new ValueProperty<Integer>("Packets", 25, 10, 100, this);
        this.onModuleEnabled = (() -> {});
        int i;
        int j;
        this.onUpdatePositionEvent = (e -> {
            switch (this.Mode.getPropertyValue()) {
                case Basic: {
                    if (FastBow.mc.thePlayer.getCurrentEquippedItem() == null) {
                        return;
                    }
                    else if (!(FastBow.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow)) {
                        return;
                    }
                    else if ((!(FastBow.mc.currentScreen instanceof GuiInventory) || !(FastBow.mc.currentScreen instanceof GuiDropDown)) && Mouse.isButtonDown(1)) {
                        if (FastBow.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow && FastBow.mc.thePlayer.getCurrentEquippedItem().getItem() != null) {
                            FastBow.mc.rightClickDelayTimer = 0;
                            for (i = 0; i < this.Packets.getPropertyValue() * 10; ++i) {
                                FastBow.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                            }
                            FastBow.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                            FastBow.mc.thePlayer.stopUsingItem();
                            break;
                        }
                        else {
                            return;
                        }
                    }
                    else {
                        break;
                    }
                    break;
                }
                case SPEED: {
                    if (FastBow.mc.thePlayer.getCurrentEquippedItem() == null) {
                        return;
                    }
                    else if (!(FastBow.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow)) {
                        return;
                    }
                    else if (e.isPre() && FastBow.mc.thePlayer.onGround && FastBow.mc.gameSettings.keyBindUseItem.pressed) {
                        FastBow.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(FastBow.mc.thePlayer.inventory.getCurrentItem()));
                        for (j = 0; j < 20; ++j) {
                            FastBow.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(FastBow.mc.thePlayer.posX, FastBow.mc.thePlayer.posY + 1.0E-9, FastBow.mc.thePlayer.posZ, true));
                        }
                        FastBow.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
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
        S18PacketEntityTeleport packet;
        this.onPacketReceiveEvent = (e -> {
            if (e.getPacket() instanceof S18PacketEntityTeleport) {
                packet = (S18PacketEntityTeleport)e.getPacket();
                if (FastBow.mc.thePlayer != null) {
                    packet.setYaw((byte)FastBow.mc.thePlayer.rotationYaw);
                }
                packet.setPitch((byte)FastBow.mc.thePlayer.rotationPitch);
            }
        });
    }
    
    private enum Modes
    {
        Basic, 
        SPEED;
    }
}
