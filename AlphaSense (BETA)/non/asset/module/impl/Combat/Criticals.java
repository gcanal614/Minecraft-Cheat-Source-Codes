package non.asset.module.impl.Combat;

import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.utils.OFC.TimerUtil;

public class Criticals extends Module {

    private float FallStack;
    
    private boolean swinging = false;

    private boolean check1 = false;

    private boolean check2 = false;

    private boolean check3 = false;

    private int vl = 0;

    private final double[] watchdogOffsets = {0.056f, 0.016f, 0.003f};
    private final TimerUtil timer = new TimerUtil();
    private int groundTicks;
    
    public Criticals() {
        super("Criticals", Category.COMBAT);
    }
    

    @Handler
    public void onPacket(PacketEvent event) {  
    	 if (event.getPacket() instanceof C0APacketAnimation) {
    		 if(!mc.thePlayer.isOnLiquid() && !mc.thePlayer.isOnLadder() && mc.thePlayer.onGround && !mc.thePlayer.isDead) {
    			 mc.getNetHandler().addToSendQueueNoEvents(new C03PacketPlayer.C04PacketPlayerPosition(
    				mc.thePlayer.posX, mc.thePlayer.posY + 0.03, mc.thePlayer.posZ, false));
    		 }
    	 }
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
        groundTicks = mc.thePlayer.onGround ? groundTicks + 1 : 0;
    }


    @Override
    public void onEnable() {
        if (getMc().thePlayer == null || getMc().theWorld == null) return;
        FallStack = 0;
        swinging = false;
        check1 = false;
        check2 = false;
        check3 = false;
        vl = 0;
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
