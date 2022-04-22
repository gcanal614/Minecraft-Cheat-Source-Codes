package me.injusttice.neutron.impl.modules.impl.other;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventReceivePacket;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.impl.modules.impl.combat.KillAura;
import me.injusttice.neutron.impl.modules.impl.exploit.StrafeDisabler;
import net.minecraft.network.play.server.S02PacketChat;

import java.util.concurrent.ThreadLocalRandom;

public class Killsults extends Module {

    public ModeSet mode = new ModeSet("Mode", "BlocksMC", "BlocksMC", "Redesky", "MC-Central", "Mineplex", "Hypixel");


    public Killsults(){
        super("KillSults", 0, Category.OTHER);
        addSettings(mode);
    }

    @EventTarget
    public void onReceive(EventReceivePacket event){
        if(event.getPacket() instanceof S02PacketChat){
            S02PacketChat packet = (S02PacketChat) event.getPacket();
            String message = packet.getChatComponent().getUnformattedText();

            switch (mode.getMode()) {
                case "MC-Central":
                    if(message.contains(mc.thePlayer.getName()) && message.contains("Has Killed")){
                        sendMsg();
                    }
                    break;
                case "BlocksMC":
                    if(message.contains(mc.thePlayer.getName()) && message.contains("was killed")) {
                        sendMsg();
                    }
                    break;
                case "Mineplex":
                    if(message.contains(mc.thePlayer.getName()) && (message.contains("killed by") || message.contains("thrown into the void"))){
                        sendMsg();
                    }
                    break;
                case "Hypixel":
                    if(message.contains("by " + mc.thePlayer.getName())){
                        sendMsg();
                    } else if (message.contains("void by " + mc.thePlayer.getName()) || message.contains("cliff by " + mc.thePlayer.getName())){
                        sendVoidMsg();
                    }
                    break;
            }
        }
    }

    public void sendMsg(){
        String[] niggaSults = {
                "Is Whatchdog Worth it %s?",
                "omfg %s 13 old kid client bai pai hypixel?",
                "Poggy Pog is Whatchdog sleepin %s?",
                "%s Easy One With Neutron!",
                "%s Are u mad? Rage Me InJusttice#8629!"
        };
        int randomIndex = ThreadLocalRandom.current().nextInt(0, niggaSults.length);
        mc.thePlayer.sendChatMessage(String.format(niggaSults[randomIndex], KillAura.target.getName()));
    }

    public void sendVoidMsg(){
        String[] niggaSults = {
                "Is Whatchdog Worth it?",
                "omfg 13 old kid client bai pai hypixel?",
                "Poggy Pog is Whatchdog sleepin?",
                "Easy One With Neutron!",
                "Are u mad? Rage Me InJusttice#8629!"
        };
        int randomIndex = ThreadLocalRandom.current().nextInt(0, niggaSults.length);
        mc.thePlayer.sendChatMessage(niggaSults[randomIndex]);
    }

}
