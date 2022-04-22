package non.asset.module.impl.movement;

import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.utils.OFC.Printer;
import non.asset.utils.OFC.TimerUtil;

import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", Category.MOVEMENT);
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
        if (getMc().thePlayer == null) return;
        
        getMc().thePlayer.setSprinting(canSprint());
        
    }

     private boolean canSprint() {
        return getMc().thePlayer.getFoodStats().getFoodLevel() > 7 && (getMc().gameSettings.keyBindForward.isKeyDown() | getMc().gameSettings.keyBindBack.isKeyDown() || getMc().gameSettings.keyBindLeft.isKeyDown() || getMc().gameSettings.keyBindRight.isKeyDown() && !mc.thePlayer.isCollidedHorizontally);
    }
}
