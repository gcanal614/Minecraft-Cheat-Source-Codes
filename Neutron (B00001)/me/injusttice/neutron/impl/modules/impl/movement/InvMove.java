package me.injusttice.neutron.impl.modules.impl.movement;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventUpdate;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;

public class InvMove extends Module {

    public static BooleanSet sneak = new BooleanSet("Sneak", true);
    public static BooleanSet jump = new BooleanSet("Jump", true);

    public InvMove() {
        super("InvMove", 0, Category.PLAYER);
        addSettings(sneak, jump);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if(mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            if(Keyboard.isKeyDown(200)) {
                EntityPlayerSP thePlayer = mc.thePlayer;
                thePlayer.rotationPitch -= 5.0F;
            }
            if(Keyboard.isKeyDown(208)) {
                EntityPlayerSP thePlayer2 = mc.thePlayer;
                thePlayer2.rotationPitch += 5.0F;
            }
            if(Keyboard.isKeyDown(203)) {
                EntityPlayerSP thePlayer3 = mc.thePlayer;
                thePlayer3.rotationYaw -= 7.0F;
            }
            if(Keyboard.isKeyDown(205)) {
                EntityPlayerSP thePlayer4 = mc.thePlayer;
                thePlayer4.rotationYaw += 7.0F;
            }
        }
    }
}