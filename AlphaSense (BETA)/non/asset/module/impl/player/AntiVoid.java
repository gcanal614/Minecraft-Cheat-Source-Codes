package non.asset.module.impl.player;


import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.utils.OFC.TimerUtil;
import non.asset.utils.value.impl.BooleanValue;
import non.asset.utils.value.impl.EnumValue;

public class AntiVoid extends Module {
	
	private double lastX = 0;
    private double lastY = 0;
    private double lastZ = 0;
    TimerUtil aaa = new TimerUtil();
    
    private boolean falling = false;
    
    public static EnumValue<mode> Mode = new EnumValue<>("Mode", mode.TPBACK);


    private BooleanValue advanced = new BooleanValue("Advanced", false);
    
    private BooleanValue blink = new BooleanValue("Blink", false, advanced, "true");
    
    public AntiVoid() {
        super("AntiVoid", Category.PLAYER);
        setDescription("Tp you back when falling into the void");
        setRenderLabel("AntiVoid");
    }
	

    @Override
    public void onEnable() {
    	lastX = 0;
        lastY = 0;
        lastZ = 0;
        falling = false;
        aaa.reset();
    }
    @Override
    public void onDisable() {
    	
    }
    
    public enum mode {
    	TPBACK, PACKET, STATICAL
    }

    @Handler
    public void onPacket(PacketEvent event) {
    	if (getMc().theWorld == null 
    	|| getMc().thePlayer == null) return;
    	
    	for (int i = (int) Math.ceil(getMc().thePlayer.posY); i >= 0; i--) {
            if (getMc().theWorld.getBlockState(new BlockPos(getMc().thePlayer.posX, i, getMc().thePlayer.posZ)).getBlock() != Blocks.air) {
                return;
            }
        }
    	
    	if(blink.isEnabled()) {
	    	if (getMc().thePlayer.fallDistance > 3) {
	        	if(aaa.reach(400)) {
	        		if(event.getPacket() instanceof C03PacketPlayer) {
		        		event.setCanceled(true);
		        		aaa.reset();
	        		}
	        	}
	    	}
    	}

    }
    
    @Handler
    public void onUpdate(UpdateEvent event) {
        if(event.isPre()) {
        	
        	for (int i = (int) Math.ceil(getMc().thePlayer.posY); i >= 0; i--) {
                if (getMc().theWorld.getBlockState(new BlockPos(getMc().thePlayer.posX, i, getMc().thePlayer.posZ)).getBlock() != Blocks.air) {
                    return;
                }
            }

            if (getMc().thePlayer.fallDistance < 2) {
            	if(mc.thePlayer.onGround) {
	                lastX = mc.thePlayer.prevPosX;
	                lastY = mc.thePlayer.prevPosY + 0.006;
	                lastZ = mc.thePlayer.prevPosZ;
            	}
            }
        	
            if (getMc().thePlayer.fallDistance > 3) {
            	
            	if(Mode.getValue() == mode.TPBACK) {
	            	if(!mc.thePlayer.onGround) {
	            		mc.thePlayer.setPosition(lastX, lastY, lastZ);
	            	}
            	}
            	
                if (getMc().thePlayer.posY < 0) {

                	if(Mode.getValue() == mode.PACKET) {
                		event.setY(event.getY() + 4.42f);
                	}
                	
                } else {
                    for (int i = (int) Math.ceil(getMc().thePlayer.posY); i >= 0; i--) {
                        if (getMc().theWorld.getBlockState(new BlockPos(getMc().thePlayer.posX, i, getMc().thePlayer.posZ)).getBlock() != Blocks.air) {
                            return;
                        }
                    }
                    

                	if(Mode.getValue() == mode.STATICAL) {
                		mc.thePlayer.motionY = mc.thePlayer.motionX = mc.thePlayer.motionZ *= 0;
                	}
                	
                	if(Mode.getValue() == mode.PACKET) {
                		event.setY(event.getY() + 4.42f);
                	}
                    
                }
            }
    	
        	
        }
    }
}
