package me.command.commands;



import me.Hime;

import me.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class DataGrabber extends Command{
	protected Minecraft mc = Minecraft.getMinecraft();
	@Override
	public String getName() {
		return "datagrab";
	}

	@Override
	public String getDescription() {
		return "grabs someones mc data";
	}

	@Override
	public String getSyntax() {
		return ".datagrab [PERSON]";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
	   	for(Object e : mc.theWorld.loadedEntityList) {
				if (e instanceof EntityPlayer) {
					if(((EntityPlayer) e).getName().equals(args[0])) {
					EntityPlayer p = (EntityPlayer) e;
					 String cleanUUID = p.getUniqueID().toString().replaceAll("-", "");
					 Hime.addCustomChatMessage("[§7Data Grabber§f]","-----------------------------");
					 Hime.addCustomChatMessage("[§7Data Grabber§f]","Name: " + p.getName());
					 Hime.addCustomChatMessage("[§7Data Grabber§f]","Invis: " + p.isInvisible());
					 Hime.addCustomChatMessage("[§7Data Grabber§f]","NBTTagCompound: " + p.getNBTTagCompound());
					 Hime.addCustomChatMessage("[§7Data Grabber§f]","UUID: " + p.getUniqueID());
					 Hime.addCustomChatMessage("[§7Data Grabber§f]","UUID (Clean): " + cleanUUID);
					 Hime.addCustomChatMessage("[§7Data Grabber§f]","X: " + p.posX + " Y: " + p.posY + " Z: " + p.posZ);
					 Hime.addCustomChatMessage("[§7Data Grabber§f]","Yaw: " + p.rotationYaw + " Pitch: " + p.rotationPitch);
				//	 Hime.addCustomChatMessage("[" + ChatFormatting.GRAY + "Data Grabber" + ChatFormatting.WHITE + "]","IP: " + RandomUtils.nextInt(10, 999) + "." + RandomUtils.nextInt(100, 999) + "." + RandomUtils.nextInt(1, 99) + "." + RandomUtils.nextInt(100, 999)) ;
					 Hime.addCustomChatMessage("[§7Data Grabber§f]","-----------------------------");
					}
				}
	       	}
	}
}
