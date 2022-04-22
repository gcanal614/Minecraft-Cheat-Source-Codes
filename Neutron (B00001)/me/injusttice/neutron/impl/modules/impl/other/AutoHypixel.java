package me.injusttice.neutron.impl.modules.impl.other;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventReceivePacket;
import me.injusttice.neutron.api.settings.ModuleCategory;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.impl.modules.impl.combat.KillAura;
import me.injusttice.neutron.impl.modules.impl.player.InventoryManager;
import me.injusttice.neutron.impl.modules.impl.player.Stealer;
import net.minecraft.network.play.server.S02PacketChat;

public class AutoHypixel extends Module {

    public ModuleCategory autoThings = new ModuleCategory("To Do...");
    public BooleanSet autoDisable = new BooleanSet("Auto Disable", true);
    public BooleanSet autoPlay = new BooleanSet("Auto Play", true);
    public BooleanSet autoGG = new BooleanSet("Auto GG", false);

    public AutoHypixel() {
        super("AutoHypixel", Category.OTHER);
        addSettings(autoThings);
        autoThings.addCatSettings(autoDisable, autoPlay, autoGG);
    }
    @EventTarget
    public void onReceive(EventReceivePacket event){
        if(event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = (S02PacketChat) event.getPacket();
            String message = packet.getChatComponent().getUnformattedText();
            if(autoDisable.isEnabled()) {
                if (message.contains("You died! Want to play again? Click here!") || message.contains("You won! Want to play again? Click here!")) {
                    if (Stealer.instance().toggled) {
                        Stealer.instance().toggle();
                    }
                    if (InventoryManager.instance().toggled) {
                        InventoryManager.instance().toggle();
                    }
                    if (KillAura.instance().toggled) {
                        KillAura.instance().toggle();
                    }
                }
            }
            if(autoPlay.isEnabled()){
                if (message.contains("You died! Want to play again? Click here!") || message.contains("You won! Want to play again? Click here!")) {
                    mc.thePlayer.sendChatMessage("/play solo_insane");
                }
            }
            if(autoGG.isEnabled()){
                if (message.contains("You died! Want to play again? Click here!") || message.contains("You won! Want to play again? Click here!")) {
                    mc.thePlayer.sendChatMessage("gg");
                }
            }
        }
    }
}
