package non.asset.module.impl.movement;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import non.asset.event.bus.Handler;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;

import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Arrays;

public class InvWalk extends Module {
    public InvWalk() {
        super("InvWalk", Category.MOVEMENT);
        setRenderLabel("InvWalk");
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
        if (getMc().currentScreen != null && event.isPre() && !(getMc().currentScreen instanceof GuiChat)) {
            getMc().thePlayer.movementInput.moveForward = 1;
            if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) getMc().thePlayer.rotationPitch += 2f;
            if (Keyboard.isKeyDown(Keyboard.KEY_UP)) getMc().thePlayer.rotationPitch -= 2f;
            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) getMc().thePlayer.rotationYaw += 2f;
            if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) getMc().thePlayer.rotationYaw -= 2f;
            KeyBinding[] keys = {getMc().gameSettings.keyBindForward, getMc().gameSettings.keyBindBack, getMc().gameSettings.keyBindLeft, getMc().gameSettings.keyBindRight,getMc().gameSettings.keyBindJump};
            Arrays.stream(keys).forEach(key -> KeyBinding.setKeyBindState(key.getKeyCode(), Keyboard.isKeyDown(key.getKeyCode())));
        }
    }
}
