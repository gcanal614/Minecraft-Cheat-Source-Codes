package it.fktcod.ktykshrk.module.mods;

import it.fktcod.ktykshrk.module.HackCategory;
import it.fktcod.ktykshrk.module.Module;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;


/**
 * Created by peanut on 26/07/2021
 */
public class FastBow extends Module {

    public FastBow() {
        super("FastBow", HackCategory.COMBAT);
    }

    @Override
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (mc.thePlayer == null || mc.thePlayer.getCurrentEquippedItem() == null) return;
        if (Mouse.isButtonDown(1) && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow && mc.thePlayer.onGround) {
            for (int power = 20, i = 0; i < power; ++i) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));
            }
            mc.playerController.onStoppedUsingItem(mc.thePlayer);
        }

    }
}
