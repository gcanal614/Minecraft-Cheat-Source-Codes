package me.command.commands;




import me.command.Command;
import net.minecraft.client.Minecraft;

public class VClip extends Command{

	@Override
	public String getName() {
		return "vclip";
	}

	@Override
	public String getDescription() {
		return "clips blocks";
	}

	@Override
	public String getSyntax() {
		return ".vclip <amount>";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		try {		
			Minecraft.getMinecraft().thePlayer.setPosition(Minecraft.getMinecraft().thePlayer.posX, 
					Minecraft.getMinecraft().thePlayer.posY + Integer.valueOf(args[0]),
					Minecraft.getMinecraft().thePlayer.posZ);
		
		}catch(Exception e) {
			e.printStackTrace();
		}

		
	}
	}


