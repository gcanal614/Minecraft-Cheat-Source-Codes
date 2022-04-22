package non.asset.module.impl.movement;

import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import non.asset.event.bus.Handler;
import non.asset.event.impl.player.SlowdownEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.utils.value.impl.BooleanValue;

import java.awt.*;

public class NoSlow extends Module {
    private BooleanValue vanilla = new BooleanValue("Vanilla", false);
    private BooleanValue items = new BooleanValue("Items", true);
    private BooleanValue sprint = new BooleanValue("Sprint", true);
    private BooleanValue soulsand = new BooleanValue("SoulSand", true);
    
    public NoSlow() {
        super("NoSlow", Category.MOVEMENT);
        setRenderLabel("NoSlow");
    }

    @Handler
    public void onUpdate(UpdateEvent event) {

        setRenderLabel("NoSlow");
        
        if (vanilla.getValue()) return;
        if (event.isPre()) {
            if (getMc().thePlayer.getHeldItem() != null && getMc().thePlayer.isBlocking() && getMc().thePlayer.onGround && (getMc().gameSettings.keyBindForward.isKeyDown() | getMc().gameSettings.keyBindBack.isKeyDown() || getMc().gameSettings.keyBindLeft.isKeyDown() || getMc().gameSettings.keyBindRight.isKeyDown())) {
                getMc().thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
        } else {
            if (getMc().thePlayer.getHeldItem() != null && getMc().thePlayer.isBlocking() && getMc().thePlayer.onGround && (getMc().gameSettings.keyBindForward.isKeyDown() | getMc().gameSettings.keyBindBack.isKeyDown() || getMc().gameSettings.keyBindLeft.isKeyDown() || getMc().gameSettings.keyBindRight.isKeyDown())) {
                getMc().thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(getMc().thePlayer.getHeldItem()));
            }
        }
    }

    @Handler
    public void onSlowDown(SlowdownEvent event) {
        switch (event.getType()) {
            case Item:
                if (items.getValue()) event.setCanceled(true);
                if (getMc().gameSettings.keyBindSprint.isKeyDown()) getMc().thePlayer.setSprinting(true);
                break;
            case Sprinting:
                if (sprint.getValue()) event.setCanceled(true);
                break;
            case SoulSand:
                if (soulsand.getValue()) event.setCanceled(true);
                break;
		default:
			break;
        }
    }
}
