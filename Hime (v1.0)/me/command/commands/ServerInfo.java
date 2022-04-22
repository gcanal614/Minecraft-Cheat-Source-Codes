package me.command.commands;




import me.Hime;
import me.command.Command;
import net.minecraft.client.Minecraft;

public class ServerInfo extends Command{

	@Override
	public String getName() {
		return "serverinfo";
	}

	@Override
	public String getDescription() {
		return "gets info for current server";
	}

	@Override
	public String getSyntax() {
		return ".serverinfo";
	}


		

		
	@Override
	public void onCommand(String command, String[] args) throws Exception {
	  long ping = Minecraft.getMinecraft().currentServerData.pingToServer;
	  String name = Minecraft.getMinecraft().currentServerData.serverName;
	  String ver = Minecraft.getMinecraft().currentServerData.gameVersion;
	  String ip = Minecraft.getMinecraft().currentServerData.serverIP;
	  String players = Minecraft.getMinecraft().currentServerData.populationInfo;
	
      Hime.addClientChatMessage("Info: ");
      Hime.addClientChatMessage("Ping: " + ping);
      Hime.addClientChatMessage("Name: " + name);
      Hime.addClientChatMessage("IP: " + ip);
      Hime.addClientChatMessage("Players: " + players);
      Hime.addClientChatMessage("Version: " + ver);
	}
}


