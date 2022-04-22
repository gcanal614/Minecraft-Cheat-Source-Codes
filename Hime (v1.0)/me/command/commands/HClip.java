package me.command.commands;

import me.command.Command;
import me.util.MovementUtils;
import net.minecraft.client.Minecraft;

public class HClip extends Command{

	@Override
	public String getName() {
		return "hclip";
	}

	@Override
	public String getDescription() {
		return "teleports forward";
	}

	@Override
	public String getSyntax() {
		return ".hclip <amount>";
	}


	@Override
	public void onCommand(String command, String[] args) throws Exception {
		Minecraft.getMinecraft().thePlayer.setPosition(Minecraft.getMinecraft().thePlayer.posX + MovementUtils.getPosForSetPosX(Integer.valueOf(args[0])), 
				Minecraft.getMinecraft().thePlayer.posY,
				Minecraft.getMinecraft().thePlayer.posZ + MovementUtils.getPosForSetPosZ(Integer.valueOf(args[0])));
		}
	}


