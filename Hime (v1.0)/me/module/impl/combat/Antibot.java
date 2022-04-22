package me.module.impl.combat;

 
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.Hime;
import org.lwjgl.input.Keyboard;

import com.google.common.collect.Ordering;

import me.event.impl.EventReceivePacket;
import me.event.impl.EventUpdate;
import me.hippo.api.lwjeb.annotation.Handler;
import me.module.Module;
import me.settings.Setting;
import me.util.TimeUtil;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.util.MathHelper;
 
public class Antibot extends Module {
	public static Antibot instance = new Antibot();
TimeUtil time = new TimeUtil();

	private Object EntityArmorStand;
    public Antibot() {
        super("Antibot", Keyboard.KEY_NONE, Category.COMBAT);
    }
    public Setting mode;
    
    public static List<EntityLivingBase> bots = new ArrayList<>();

    int currentEntity;
    int playerList;
    int index;
    int next;
    int oldPos;
    public Setting packet;
    public Setting chat;
    public Setting sensitivity;
    
    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<String>();
        options.add("Advanced");
        options.add("Invisible");
        options.add("Tab List");
        options.add("Watchdog");
        options.add("WatchdogTest");
        options.add("Mineplex");
        options.add("Cubecraft Chest");
        Hime.instance.settingsManager.rSetting(mode = new Setting("AntiBot Mode", this, "Mineplex", options));
    	Hime.instance.settingsManager.rSetting(this.sensitivity = new Setting("Sensitivity", this, 8, 0, 10, false));
        Hime.instance.settingsManager.rSetting(packet = new Setting("Mineplex Packet Check", this, false));
        Hime.instance.settingsManager.rSetting(chat = new Setting("Show Removed Bots in Chat", this, false));
    }

    @Handler
    public void onReceivePacket(EventReceivePacket event) {
        if(mode.getValString().equalsIgnoreCase("Advanced") && event.getPacket() instanceof S0CPacketSpawnPlayer) {
            S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer)event.getPacket();
            double posX = packet.getX() / 32D;
            double posY = packet.getY() / 32D;
            double posZ = packet.getZ() / 32D;

            double diffX = mc.thePlayer.posX - posX;
            double diffY = mc.thePlayer.posY - posY;
            double diffZ = mc.thePlayer.posZ - posZ;

            double dist = MathHelper.sqrt_double(diffX * diffX + diffY * diffY + diffZ * diffZ);

            if (dist <= 17 && posY > mc.thePlayer.posY + 1 && (posX != mc.thePlayer.posX && posY != mc.thePlayer.posY && posZ != mc.thePlayer.posZ)) {
                event.cancel();
            }
        }
        if(this.packet.getValBoolean()) {
         if(mode.getValString().equalsIgnoreCase("Mineplex") && event.getPacket() instanceof S0CPacketSpawnPlayer) {
        	 S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer)event.getPacket();
        	 Entity en = mc.theWorld.getEntityByID(packet.getEntityID());
        	 if (en != null && en instanceof EntityPlayer) {
        		 this.bots.add((EntityLivingBase) en);
        	 }
         }
         if(mode.getValString().equalsIgnoreCase("Mineplex") && event.getPacket() instanceof S18PacketEntityTeleport) {
        	 S18PacketEntityTeleport packet = (S18PacketEntityTeleport)event.getPacket();
        	 Entity en = mc.theWorld.getEntityByID(packet.getEntityId());
        	 if (en != null && en instanceof EntityPlayer) {
              if (en.isInvisible()) {
        		 this.bots.add((EntityLivingBase)en);
        	   }
        	 }
         }
        }
    }
    private boolean isOnTab(Entity entity) {
        Iterator var2 = mc.getNetHandler().getPlayerInfoMap().iterator();

        NetworkPlayerInfo info;
        do {
           if (!var2.hasNext()) {
              return false;
           }

           info = (NetworkPlayerInfo)var2.next();
        } while(!info.getGameProfile().getName().equals(entity.getName()));

        return true;
     }
 
    public void onDisable() {
     	bots.clear();
        super.onDisable();
      }
    private boolean isEntityBot(Entity entity) {
        double distance = entity.getDistanceSqToEntity(mc.thePlayer);
        if (!(entity instanceof EntityPlayer)) {
           return false;
        } else if (mc.getCurrentServerData() == null) {
           return false;
        } else {
           return mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel") && entity.getDisplayName().getFormattedText().startsWith("à¸¢à¸‡") || !this.isOnTab(entity) && mc.thePlayer.ticksExisted > 100;
        }
    }
    

    public  boolean isWatchdogBot(EntityPlayer e) {
        if (e.getGameProfile() == null) {
            return true;
        }
        NetworkPlayerInfo npi = mc.getNetHandler().getPlayerInfo(e.getGameProfile().getId());
        return (npi == null || npi.getGameProfile() == null || e.ticksExisted < this.sensitivity.getValInt() || npi.getResponseTime() != 1);
    }
    
        @Handler
        public void onUpdate(EventUpdate event) {
        	this.setSuffix(mode.getValString());
        	if(mode.getValString().equalsIgnoreCase("Cubecraft Chest")){
    			for (Object entity : mc.theWorld.loadedEntityList)
    				if (((Entity) entity).isInvisible() || entity instanceof EntityArmorStand) {
    					this.bots.add((EntityLivingBase)entity);
    				}else {
    					this.bots.remove(entity);
    				}
    		}
            if(mode.getValString().equalsIgnoreCase("Mineplex")) {
            	for (Object o : this.mc.theWorld.loadedEntityList) {
  					if (((o instanceof EntityPlayer)) && (o != this.mc.thePlayer)) {
  					EntityPlayer e = (EntityPlayer) o;
  					 if (e.getDisplayName().getFormattedText().contains("§r") && !this.getPlayerTabList().contains(e) && e.isInvisible()) {
  						bots.add(e);
  						 if(this.chat.getValBoolean()) {
  	                          Hime.instance.addClientChatMessage("Removed Bot: " + e.getName());
  	                        }
                        }
  					}
              	}
            }
            if(mode.getValString().equalsIgnoreCase("Invisible")) {
            	for (Object o : this.mc.theWorld.loadedEntityList) {
					if (((o instanceof EntityPlayer)) && (o != this.mc.thePlayer)) {
					EntityPlayer e = (EntityPlayer) o;
					if(e.isInvisible()) {
						bots.add(e);
					 }
					}
            	}
            }
            if(mode.getValString().equalsIgnoreCase("Tab List")) {
            	for (Object o : this.mc.theWorld.loadedEntityList) {
					if (((o instanceof EntityPlayer)) && (o != this.mc.thePlayer)) {
					EntityPlayer e = (EntityPlayer) o;
				    if(!this.isOnTab(e))
				        bots.add(e);
					}
            	}
            }
            if(mode.getValString().equalsIgnoreCase("WatchdogTest")) {
            	this.setSuffix("Watchdog");
                  	for (Object o : this.mc.theWorld.loadedEntityList) {
      					if (((o instanceof EntityPlayer)) && (o != this.mc.thePlayer)) {
      					EntityPlayer e = (EntityPlayer) o;
      					 if (e.getDisplayName().getFormattedText().contains("§r") && !this.getPlayerTabList().contains(e) && e.isInvisible()) {
      						bots.add(e);
      						 if(this.chat.getValBoolean()) {
      	                          Hime.instance.addClientChatMessage("Removed Bot: " + e.getName());
      	                        }
      					 }
      					}
                  	}
            }
            if(mode.getValString().equalsIgnoreCase("Watchdog")) {
            	for (Object o : this.mc.theWorld.loadedEntityList) {
					if (((o instanceof EntityPlayer)) && (o != this.mc.thePlayer)) {
					EntityPlayer e = (EntityPlayer) o;
                      if ((!e.getName().startsWith("\u00a7") ||
                        		!e.getName().contains((CharSequence)"\u00a7c")) 
                        		&& (!this.isEntityBot((Entity)e))) continue;
                            bots.add((EntityLivingBase)e);
                        if(this.chat.getValBoolean()) {
                          Hime.instance.addClientChatMessage("Removed Bot: " + e.getName());
                        }
                    }
            	}
            	for (Object o : this.mc.theWorld.loadedEntityList) {
					if (((o instanceof EntityPlayer)) && (o != this.mc.thePlayer)) {
					 EntityPlayer e = (EntityPlayer) o;
                    }
            	}
            }
        }
 
    
    public Ordering<NetworkPlayerInfo> field_175252_a() {
      try {
        Class<GuiPlayerTabOverlay> c = GuiPlayerTabOverlay.class;
        Field f = c.getField("field_175252_a");
        f.setAccessible(true);
        return (Ordering<NetworkPlayerInfo>)f.get(GuiPlayerTabOverlay.class);
      } catch (Exception e) {
        return null;
      } 
    }
    
    private List<EntityPlayer> getPlayerTabList() {
      List<EntityPlayer> list;
      (list = new ArrayList<>()).clear();
      Ordering<NetworkPlayerInfo> field_175252_a = field_175252_a();
      if (field_175252_a == null)
        return list; 
      List players = field_175252_a.sortedCopy(this.mc.thePlayer.sendQueue.getPlayerInfoMap());
      for (Object o : players) {
        NetworkPlayerInfo info = (NetworkPlayerInfo)o;
        if (info == null)
          continue; 
        list.add(this.mc.theWorld.getPlayerEntityByName(info.getGameProfile().getName()));
      } 
      return list;
    }
	  public static boolean isBot(EntityPlayer ep) {
    return bots.contains(ep);
  }
   
}
