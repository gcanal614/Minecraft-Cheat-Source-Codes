package me.injusttice.neutron.impl.commands.impl;

import me.injusttice.neutron.impl.commands.Command;
import me.injusttice.neutron.utils.StringConversions;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class VClip extends Command {

	protected Minecraft mc = Minecraft.getMinecraft();

	public VClip() {
		super("VClip", "VClip", "vclip <distance>", "v");
	}

	@Override
	public void onCommand(String[] args, String command) {
		if (args.length == 1) {
			if(StringConversions.isNumeric(args[0])) {
				mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY + Double.parseDouble(args[0]), mc.thePlayer.posZ);
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§8> §aTeleported " + args[0] + " blocks"));
			}
		}
	}
}
