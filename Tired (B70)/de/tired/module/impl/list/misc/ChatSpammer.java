package de.tired.module.impl.list.misc;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.event.EventTarget;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;

import java.util.Random;

@ModuleAnnotation(name = "ChatSpammer", category = ModuleCategory.MISC, clickG = "Spams a text in the chat")
public class ChatSpammer extends Module {

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 5;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        if (MC.thePlayer.ticksExisted % 27 == 0) {
            MC.thePlayer.sendChatMessage("@a " +generatedString + " Go And play with tired-client.de (BEST FREE CHEAT) " + generatedString);
        }
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
