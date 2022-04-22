package non.asset.module.impl.Combat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.utils.OFC.TimerUtil;
import non.asset.utils.value.impl.BooleanValue;
import non.asset.utils.value.impl.EnumValue;

public class AntiBot extends Module {
    private static ArrayList<Entity> bots = new ArrayList<>();
    private EnumValue<Mode> mode = new EnumValue<>("Mode", Mode.WATCHDOG);
    private BooleanValue remove = new BooleanValue("Remove Bots", "Remove Bots From World", false);
    private Map<Integer, Double> distanceMap = new HashMap<>();
    private Set<Integer> swingSet = new HashSet<>();
    private TimerUtil timerUtil = new TimerUtil();

    public AntiBot() {
        super("AntiBot", Category.COMBAT);
        setDescription("Remove anticheat bots");
        setRenderLabel("AntiBot");
    }
    
    @Handler
    public void onUpdate(UpdateEvent event) {
        if (getMc().theWorld == null) return;
        setSuffix(StringUtils.capitalize(mode.getValue().name().toLowerCase()));
        switch (mode.getValue()) {
        
        	case INVISIBLE:

        		Iterator var3 = mc.theWorld.loadedEntityList.iterator();
         	  	while(var3.hasNext()) {
         	  		Object entity = var3.next();
	       		   	if(((Entity)entity).isInvisible() && entity != mc.thePlayer) {
	       		   		if (isEntityBot((Entity) entity)) {

                            System.out.println("Detected a Invisible Bot " + ((Entity) entity).getName());
                            
	       		   			if (remove.isEnabled()) {
	       		   				getMc().theWorld.removeEntity((Entity) entity);
	       		   			}else {
	    	       		   		bots.add((Entity) entity);
	       		   			}
                        }else {
		       		   		bots.remove(entity);
                        }
	       		   	}
         	  	}
         	  	break;
         	   
            case WATCHDOG:
                if (event.isPre()) {
                    if (getMc().thePlayer.ticksExisted % 600 == 0) {
                        bots.clear();
                    }
                }
                for (Entity entity : getMc().theWorld.getLoadedEntityList()) {
                    if (entity instanceof EntityPlayer) {
                        if (entity != getMc().thePlayer) {
                            if (isEntityBot(entity)) {
                                if (remove.isEnabled()) getMc().theWorld.removeEntity(entity);
                                bots.add(entity);
                                System.out.println("Detected a Hypixel Bot " + entity.getName());
                            } else bots.remove(entity);
                        }
                    }
                }
                break;
                
            case MINEPLEX:
            	
                for (Entity e : getMc().theWorld.getLoadedEntityList()) {
                    if (e instanceof EntityPlayer) {
                        if (e.ticksExisted < 2 && ((EntityPlayer) e).getHealth() < 20 && ((EntityPlayer) e).getHealth() > 0 && e != getMc().thePlayer) {
                            if (remove.isEnabled()) { 
	                            getMc().theWorld.removeEntity(e);
	                            System.out.println("Detected a Mineplex Bot " + ((EntityPlayer) e).getName());
	                            bots.add(e);
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    @Handler
    public void onPacket(PacketEvent event) {
        if (getMc().theWorld == null) return;
        if (event.getPacket() instanceof S0CPacketSpawnPlayer) {
            S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer) event.getPacket();
            double x = packet.getX();
            double y = packet.getY();
            double z = packet.getZ();
            double d = getMc().thePlayer.getDistance(x, y, z);

            distanceMap.put(packet.getEntityID(), d);
        }

        if (event.getPacket() instanceof S0BPacketAnimation) {
            S0BPacketAnimation packet = (S0BPacketAnimation) event.getPacket();

            if (packet.getAnimationType() != 0) return;

            swingSet.add(packet.getEntityID());
        }

    }

    private boolean isEntityBot(Entity entity) {
        if (!distanceMap.containsKey(entity.getEntityId())) return false;
        double distance = distanceMap.get(entity.getEntityId());
        if (getMc().getCurrentServerData() == null || !swingSet.contains(entity.getEntityId()) || !(entity instanceof EntityPlayer))
            return false;
        return getMc().getCurrentServerData().serverIP.toLowerCase().contains("hypixel") && ((distance > 14.5 && distance < 17) || entity.getName().startsWith("\247") || entity.getDisplayName().getFormattedText().startsWith("ยง") || entity.getDisplayName().getFormattedText().toLowerCase().startsWith("npc") || !isOnTab(entity)) && getMc().thePlayer.ticksExisted > 100;
    }

    static boolean isOnTab(Entity entity) {
        return Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap().stream().anyMatch(info -> info.getGameProfile().getName().equalsIgnoreCase(entity.getName()));
    }

    private enum Mode {
    	WATCHDOG, MINEPLEX, INVISIBLE
    }

    @Override
    public void onEnable() {
        bots.clear();
    }

    @Override
    public void onDisable() {
        bots.clear();
    }

    public static List<Entity> getBots() {
        return bots;
    }
}
