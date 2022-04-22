package me.module.impl.combat;

import java.util.ArrayList;

import me.Hime;
import me.event.Event;
import me.event.impl.EventReceivePacket;
import me.event.impl.EventSendPacket;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.CombatUtil;
import me.util.PlayerUtils;
import me.util.TimeUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.util.BlockPos;


public class Criticals extends Module {
	public static Criticals instance = new Criticals();
	public Setting delay;
	TimeUtil time = new TimeUtil();
	
    public Criticals() {
        super("Criticals", 0, Category.COMBAT);
    } 
    public Setting mode;
    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<String>();
        options.add("Packet");
        options.add("MiniJump");
        options.add("Legit");
        options.add("NoGround");
        options.add("Watchdog");
        options.add("Watchdog2");
        options.add("Area 51");
        options.add("Redesky");
        Hime.instance.settingsManager.rSetting(mode = new Setting("Criticals Mode", this, "Packet", options));
    	Hime.instance.settingsManager.rSetting(this.delay = new Setting("Crit Delay", this, 350, 0, 1000, true));
    }
    
 
    	
    		
    @Handler
    public void onSendPacket(EventSendPacket event) {
        if(canCrit()) {
            if (event.getPacket() instanceof C02PacketUseEntity) {
                C02PacketUseEntity packet = (C02PacketUseEntity)event.getPacket();
                if(packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                    if(mode.getValString().equalsIgnoreCase("Packet")) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + .1625, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 4.0E-6, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-6, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
                    }
                    if(mode.getValString().equalsIgnoreCase("Watchdog2")) {
                        if(time.hasTimePassed((long) this.delay.getValDouble())) {
                            final double[] watchdogOffsets = {0.056f, 0.016f, 0.003f};
                            for(double i : watchdogOffsets){
                            	  mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + i + (Math.random() * 0.0003F), mc.thePlayer.posZ, false));
                            }
                            time.reset();
                        }
                    }
                    if(mode.getValString().equalsIgnoreCase("Watchdog")) {
                        if(time.hasTimePassed((long) this.delay.getValDouble())) {
                            final double[] watchdogOffsets = {0.056f, 0.016f, 0.003f};
                            for(double i : watchdogOffsets){
                                  mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + i + 0.045, mc.thePlayer.posZ, false));
                            }
                            time.reset();
                        }
                       /* if (mc.thePlayer.hurtResistantTime > 13 && mc.thePlayer.isCollidedVertically && mc.thePlayer.onGround && time.hasTimePassed(350)) {
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.062575, mc.thePlayer.posZ, false));
                            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.015, mc.thePlayer.posZ, false));
                            time.reset();
                        }*/
                    }
                    
                    
                }
            }
            if(mode.getValString().equalsIgnoreCase("MiniJump")) {
                mc.thePlayer.jump();
                mc.thePlayer.motionY -= .30000001192092879;
            }
            if(mode.getValString().equalsIgnoreCase("Legit") && mc.thePlayer.onGround) {
                mc.thePlayer.jump();
            }
        }
    }

  

    @Handler
    public void onMotionEvent(EventUpdate event) {
    	 this.setSuffix(mode.getValString());
       		 if(mode.getValString().equalsIgnoreCase("NoGround")) {
 	                event.setGround(false);
 	            }
       }
                	   
                public boolean isBlockUnder() {
                    for (int i = (int) (mc.thePlayer.posY - 1.0); i > 0; --i) {
                        BlockPos pos = new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ);
                        if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) continue;
                        return true;
                    }
                    return false;
                }
	private boolean canCrit() {
        return !PlayerUtils.isInLiquid() && mc.thePlayer.onGround;
    }
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(!mc.thePlayer.isAirBorne) {
				mc.thePlayer.jump();
			}
		}
		if(mode.getValString().equalsIgnoreCase("Redesky")) {
			
		
		if(e instanceof EventReceivePacket) {
			EventReceivePacket event = (EventReceivePacket)e;
			if(event.getPacket() instanceof C02PacketUseEntity) {
				C02PacketUseEntity packet = ((C02PacketUseEntity)event.getPacket());
				if(!CombatUtil.getTargets().isEmpty() && packet.getAction() == Action.ATTACK) {
					if(CombatUtil.getTargets().get(0) instanceof EntityPlayer) {
						EntityLivingBase target = (EntityLivingBase) CombatUtil.getTargets().get(0);
							mc.thePlayer.onCriticalHit(target);
							mc.thePlayer.fallDistance = 999999f;
							mc.thePlayer.onGround = true;
							
						}
					}
				}
			}
		}
		
	}
}



	

