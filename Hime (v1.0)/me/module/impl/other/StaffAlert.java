package me.module.impl.other;

import me.Hime;
import org.lwjgl.input.Keyboard;

import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;

public class StaffAlert extends Module {
    public StaffAlert() {
        super("StaffAlert", Keyboard.KEY_NONE, Category.MISC);
    }

    @Handler
    public void onUpdate(EventUpdate event){
        PlayerCapabilities cap = new PlayerCapabilities();
        cap.isCreativeMode = true;
        mc.thePlayer.sendQueue.addToSendQueueSilent(new C13PacketPlayerAbilities(cap));
        if (mc.thePlayer.ticksExisted % 20 == 0){
            Hime.addClientChatMessage("Staff Alert");
        }
    }

}