package me.injusttice.neutron.impl.modules.impl.combat;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventUpdate;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class FastBow extends Module {

    public ModeSet modes = new ModeSet("Modes", "Vanilla", "Vanilla", "Verus");

    public FastBow() {
        super("FastBow", 0, Category.COMBAT);
        addSettings(modes);
    }

    @EventTarget
    public void onUpdate(EventUpdate e){
        this.setDisplayName("Fast Bow");
        switch (modes.getMode()) {
            case "Vanilla":
                if(Minecraft.getMinecraft().thePlayer.getHealth() > 0
                        &&(Minecraft.getMinecraft().thePlayer.onGround || Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode)
                        && Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() != null
                        && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow
                        && Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed) {
                    Minecraft.getMinecraft().playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem()
                    );
                    Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem().onItemRightClick(mc.thePlayer.inventory.getCurrentItem(), mc.theWorld, mc.thePlayer);

                    for(int i = 0; i < 20; i++)
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
                    Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0,0,0), EnumFacing.DOWN));
                    mc.thePlayer.inventory.getCurrentItem().getItem().onPlayerStoppedUsing(mc.thePlayer.inventory.getCurrentItem(), mc.theWorld, mc.thePlayer, 0);
                }
                break;
            case "Verus":
                break;
        }
    }
}
