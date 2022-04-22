package me.injusttice.neutron.impl.modules.impl.combat;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import net.minecraft.item.ItemSoup;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C03PacketPlayer;

public class AutoSoup extends Module {
    
    public DoubleSet health = new DoubleSet("Health", 7.0, 2.0, 9.5, 0.5);
    
    public AutoSoup() {
        super("AutoSoup", 0, Category.COMBAT);
        addSettings(health);
    }
    
    @EventTarget
    public void onMotion(EventMotion e) {
        EntityPlayerSP thePlayer;
        EntityPlayerSP player = thePlayer = mc.thePlayer;
        thePlayer.rotationPitch += 1.0E-4f;
        if (player.getHealth() != player.getMaxHealth() && player.getHealth() < health.getValue() * 2.0 && doesNextSlotHaveSoup() && player.hurtTime >= 9) {
            player.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(player.posX, player.posY, player.posZ, player.rotationYawHead, 90.0f, player.onGround));
            player.sendQueue.addToSendQueue(new C09PacketHeldItemChange(getSlotWithSoup()));
            mc.playerController.sendUseItem(player, mc.theWorld, player.inventory.getStackInSlot(getSlotWithSoup()));
            new BlockPos(0, 0, 0);
            player.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
            player.sendQueue.addToSendQueue(new C09PacketHeldItemChange(player.inventory.currentItem));
            mc.playerController.onStoppedUsingItem(player);
            NeutronMain.addChatMessage("Consumed Soup");
        }
    }
    
    public boolean doesNextSlotHaveSoup() {
        EntityPlayerSP player = mc.thePlayer;
        for (int i = 0; i < 9; ++i) {
            if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() instanceof ItemSoup) {
                return true;
            }
        }
        return false;
    }
    
    public int getSlotWithSoup() {
        EntityPlayerSP player = mc.thePlayer;
        for (int i = 0; i < 9; ++i) {
            if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() instanceof ItemSoup) {
                return i;
            }
        }
        return 0;
    }
}
