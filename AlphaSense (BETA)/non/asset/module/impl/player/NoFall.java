package non.asset.module.impl.player;

import java.awt.Color;

import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import non.asset.event.bus.Handler;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.utils.OFC.Printer;
import non.asset.utils.value.impl.EnumValue;

public class NoFall extends Module {

    private EnumValue<mode> Mode = new EnumValue<>("Mode", mode.HYPIXEL);
    
    //private BooleanValue hypixel = new BooleanValue("Hypixel", true);

    public NoFall() {
        super("NoFall", Category.PLAYER);
        setRenderLabel("NoFall");
    }
    
    public enum mode {
    	HYPIXEL, VANILLA, REDESKY, GROUNDSPOOF, VERUS
    }
    
    @Handler
    public void onUpdate(UpdateEvent event) {
    	
    	this.setSuffix(Mode.getValue().name().toLowerCase());
    	
    	switch(Mode.getValue()) {
    	case GROUNDSPOOF:
    		if (event.isPre() && getMc().thePlayer.fallDistance >= 3.0) {
                event.setOnGround(true);
            }
    		break;
    	case VANILLA:
    		if (event.isPre() && getMc().thePlayer.fallDistance > 3.0) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
            }
    		break;
		case HYPIXEL:
			if (event.isPre() && getMc().thePlayer.fallDistance >= 3.0 && isBlockUnder()) {
                getMc().thePlayer.fallDistance = 0;
                event.setOnGround(true);
            }
			break;
		case REDESKY:
			if (event.isPre() && getMc().thePlayer.fallDistance >= 3.0 && isBlockUnder()) {
                getMc().thePlayer.fallDistance = 0;
                event.setOnGround(true);
            }
			break;
			
		case VERUS:
			if (event.isPre() && getMc().thePlayer.fallDistance >= 3.0) {
                if (mc.thePlayer.ticksExisted % 2 == 0) {
                	mc.thePlayer.motionY = -0.5;
                }else {
                	mc.thePlayer.motionY = -1;
                }
                if(isBlockUnder()) {
            		event.setOnGround(true);
            	}
            }
			break;
			
		default:
			break;
		
    	
    	}
    }
    private boolean isBlockUnder() {
        for (int i = (int) (getMc().thePlayer.posY - 1.0); i > 0; --i) {
            BlockPos pos = new BlockPos(getMc().thePlayer.posX, i, getMc().thePlayer.posZ);
            if (getMc().theWorld.getBlockState(pos).getBlock() instanceof BlockAir) continue;
            return true;
        }
        return false;
    }
}
