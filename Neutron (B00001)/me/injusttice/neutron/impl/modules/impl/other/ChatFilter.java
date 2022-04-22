package me.injusttice.neutron.impl.modules.impl.other;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventSendPacket;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import net.minecraft.network.play.client.C01PacketChatMessage;

import java.util.ArrayList;

public class ChatFilter extends Module {

    public ModeSet mode = new ModeSet("Mode", "Bypass", "Bypass", "Chinese");

    public ChatFilter(){
        super("ChatFilter", 0, Category.OTHER);
        addSettings(mode);
    }

    @EventTarget
    public void onSend(EventSendPacket event) {
        this.setDisplayName("Chat Filter §7" + mode.getMode());
        switch (mode.getMode()) {
            case "Bypass":
                if (event.getPacket() instanceof C01PacketChatMessage) {
                    C01PacketChatMessage p = (C01PacketChatMessage) event.getPacket();
                    String finalmsg = "";
                    ArrayList<String> splitshit = new ArrayList<>();
                    String[] msg = p.getMessage().split(" ");
                    for (int i = 0; i < msg.length; i++) {
                        char[] characters = msg[i].toCharArray();
                        for (int i2 = 0; i2 < characters.length; i2++) {
                            splitshit.add(characters[i2] + "\u061C");
                        }
                        splitshit.add(" ");
                    }
                    for (int i = 0; i < splitshit.size(); i++) {
                        finalmsg += splitshit.get(i);
                    }
                    p.setMessage(finalmsg.replaceFirst("%", ""));
                    splitshit.clear();
                }
                break;
        }
    }
}
