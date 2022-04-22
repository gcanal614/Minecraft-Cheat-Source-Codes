package non.asset.module.impl.other;

import java.awt.Color;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import non.asset.Clarinet;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.module.Module;
import non.asset.module.impl.Combat.Aura;
import non.asset.module.impl.exploit.Disabler;
import non.asset.module.impl.movement.Fly;
import non.asset.utils.OFC.MoveUtil;
import non.asset.utils.OFC.Printer;
import non.asset.utils.OFC.TimerUtil;
import non.asset.utils.value.impl.BooleanValue;

public class AutoGame extends Module {
	
	private TimerUtil timer = new TimerUtil();
	
	private boolean nextGameSendDelay = false;
	private boolean flagged = false;
    
	private String[] nigga = new String[]{"1st Killer - ", "1st Place - ", "You died! Want to play again? Click here!", "Winner: ", " - Damage Dealt - ", "1st - ", "Winning Team - ", "Winners: ", "Winner: ", "Winning Team: ", " win the game!", "Top Seeker: ", "1st Place: ", "Last team standing!", "Winner #1 (", "Top Survivors", "Winners - ", "[SkyWars] Stopping in 10 seconds"};
	
	private String[] deletefdpplss = new String[]{"won the game", "is the winner", "venceu", "won"};
	
	public BooleanValue fucker = new BooleanValue("Extra Bypass", false);

    private boolean allowedToSend = false;
	public String[] dieTitles = new String[] {"Died","Killed","Wasted","Morreu"};
	
    public AutoGame() {
        super("Patcher", Category.OTHER);
        setDescription("Solve some bypass issues");
    	setHidden(false);
    }

    @Handler
    public void onPacket(PacketEvent event) {

        if (getMc().thePlayer == null) return;
        
    	if (getMc().theWorld == null) {
            allowedToSend = false;
			timer.reset();
            return;
        }
    	if(allowedToSend == true) {
    		if(timer.reach(2000)) {
    			allowedToSend = false;
    		}
    	}
    	
        if (!event.isSending() && !allowedToSend) {
            if (event.getPacket() instanceof S45PacketTitle) {
            	
            	deletefdpplss = new String[]{"won the game", "is the winner", "venceu", "won"};
            	
            	S45PacketTitle packet = (S45PacketTitle) event.getPacket();
            	
            	for (String str : deletefdpplss) {
            		if(packet.getMessage().getUnformattedText().contains(str) && packet.getMessage().getUnformattedText().contains(mc.thePlayer.getName())) {
            			Clarinet.INSTANCE.getNotificationManager().addNotification("GG", 3000);
                		timer.reset();
                        allowedToSend = true;
            		}
            	}
            }
            
            if (event.getPacket() instanceof S02PacketChat) {
                S02PacketChat packet = (S02PacketChat) event.getPacket();
                
                for (String str : nigga) {
                    if (packet.getChatComponent().getUnformattedText().contains(str) && GuiIngame.isInSkywars) {
                        Clarinet.INSTANCE.getNotificationManager().addNotification("The game ends!", 3500);
                        allowedToSend = true;
                		timer.reset();
                    }
                }
            }
        }

    	if(fucker.isEnabled()) {
	    	if (event.isSending() && event.getPacket() instanceof C17PacketCustomPayload) {
	        	setSuffix("C17");
				timer.reset();
	            event.setCanceled(true);
	        }    	
    	}
    	if (!event.isSending() && event.getPacket() instanceof S08PacketPlayerPosLook) {
    		S08PacketPlayerPosLook a = new S08PacketPlayerPosLook();
    		setSuffix("S08");
    		a.setPitch(mc.thePlayer.rotationPitch);
    		a.setYaw(mc.thePlayer.rotationYaw);
			timer.reset();
        }
    	if(event.getPacket() instanceof S09PacketHeldItemChange) {
    		setSuffix("S09");
    		if(!mc.playerController.syncItem()) {
    			mc.playerController.syncCurrentPlayItem();
    		}
			timer.reset();
    	}
    	if(fucker.isEnabled()) {
	    	if(event.getPacket() instanceof S08PacketPlayerPosLook) {
	    		if(event.getPacket() instanceof C03PacketPlayer) {
	    			setSuffix("S08");
	    			timer.reset();
	        		event.setCanceled(true);
	        	}
	    	}
	    	if(event.getPacket() instanceof S27PacketExplosion) {
	    		if(event.getPacket() instanceof C03PacketPlayer) {
	    			setSuffix("S27");
	    			timer.reset();
	        		event.setCanceled(true);
	        	}
	    	}
    	}
    	if(timer.reach(2000)) {
        	setSuffix("NON");	
    	}
    }

    @Override
    public void onEnable() {
        if (getMc().theWorld == null) return;

    	if(!MoveUtil.sigmaHatar) {
    		Clarinet.INSTANCE.getNotificationManager().addNotification("Only" + " be" + "ta", 1900);
    		toggle();
    	}

        nextGameSendDelay = false;
        flagged = false;
        
    }

    @Override
    public void onDisable() {
        if (getMc().theWorld == null) return;

		allowedToSend = false;
    }

    private boolean hasMoved() {
        return getMc().thePlayer.posX != getMc().thePlayer.prevPosX || getMc().thePlayer.posY != getMc().thePlayer.prevPosY || getMc().thePlayer.posZ != getMc().thePlayer.prevPosZ;
    }
}