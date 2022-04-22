package me.module.impl.combat.ghost;

import me.event.impl.EventSendPacket;
import me.event.impl.EventUpdate;
import me.module.Module;
import me.ui.clickgui2.component.components.sub.Keybind;
import me.util.TimeUtil;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import org.lwjgl.input.Keyboard;

public class WTap extends Module {
    TimeUtil time = new TimeUtil();

    public WTap() {super("Wtap", Keyboard.KEY_NONE, Category.COMBAT);}

    public void onSendPacket(EventSendPacket e){
        if(e.getPacket() instanceof C02PacketUseEntity) {
            C02PacketUseEntity packet = (C02PacketUseEntity)e.getPacket();
            if(packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                mc.thePlayer.sendQueue.addToSendQueueSilent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));

            }
        }
    }

}
