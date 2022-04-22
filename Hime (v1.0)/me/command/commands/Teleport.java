package me.command.commands;



import me.Hime;
import me.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Teleport extends Command{

	@Override
	public String getName() {
		return "teleport";
	}

	@Override
	public String getDescription() {
		return "teleports you somewhere";
	}

	@Override
	public String getSyntax() {
		return ".teleport [type] [X] [Y] [Z]/[player name]";
	}


	@Override
	public void onCommand(String command, String[] args) throws Exception {
	if(args[0].equalsIgnoreCase("Coords")) {
		try {
			Minecraft.getMinecraft().thePlayer.setPosition(Integer.valueOf(args[1]), Integer.valueOf(args[2]), Integer.valueOf(args[3]));
			  mc.thePlayer.motionX = 0;
				mc.thePlayer.motionY = 0;
				mc.thePlayer.motionZ = 0;
				for (int i = 0; i < 5; i++) {
					mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Integer.valueOf(args[1]), Integer.valueOf(args[2]) + 99, Integer.valueOf(args[3]), true));
					//mc.thePlayer.setPosition(p.getVec3());
					mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Integer.valueOf(args[1]), Integer.valueOf(args[2]) + 1, Integer.valueOf(args[3]), true));
					mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
					mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
					mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
					mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
					mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
					mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
					
				}
				  Hime.addCustomChatMessage("[§2Teleport§f]", "Teleport Complete");
		}catch(Exception e) {
			e.printStackTrace();
		}
    }else if(args[0].equalsIgnoreCase("Player")) {
       	for(Object e : mc.theWorld.loadedEntityList) {
			if (e instanceof EntityPlayer) {
				if(((EntityPlayer) e).getName().equals(args[1])) {
				EntityPlayer p = (EntityPlayer) e;
			  
			      mc.thePlayer.motionX = 0;
					mc.thePlayer.motionY = 0;
					mc.thePlayer.motionZ = 0;
					for (int i = 0; i < 5; i++) {
						mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(p.posX, p.posY + 99, p.posZ, true));
						//mc.thePlayer.setPosition(p.getVec3());
						mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(p.posX, p.posY + 1, p.posZ, true));
						mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
						mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
						mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
						mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
						mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
						mc.getNetHandler().addToSendQueue(new C03PacketPlayer());
						
					}
				    Hime.addCustomChatMessage("[§2Teleport§f]", "Teleport Complete");
				}
			}
       	}
    	
     }
	}
}


