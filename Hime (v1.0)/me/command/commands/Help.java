package me.command.commands;


import me.Hime;
import me.command.Command;

public class Help extends Command{

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getDescription() {
		return "Gives out all comands";
	}

	@Override
	public String getSyntax() {
		return ".help [PAGE NUM]";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		
	 
	 if(args[0].equalsIgnoreCase("2")){
		Hime.instance.addClientChatMessage("-----------HELP-(Page 2/4)-----------");
		Hime.instance.addClientChatMessage(".nameprotect <text>: Sets the nameprotect name");
		Hime.instance.addClientChatMessage(".spamtext <text>: Sets the text which spammer module spams");
		Hime.instance.addClientChatMessage(".teleport <teleport type> <x> <y> <z>: Teleports to the x/y/z coords or to a player");
		Hime.instance.addClientChatMessage(".serverinfo: Shows a servers info");
		Hime.instance.addClientChatMessage(".say <text>: says something in chat");
		Hime.instance.addClientChatMessage(".datagrab <name>: grabs someones ingame mc data");
		Hime.instance.addClientChatMessage(".dmplayer <name>: sets the player to autodm");
		Hime.instance.addClientChatMessage("-----------HELP-(Page 2/4)-----------");
	 }
	 if(args[0].equalsIgnoreCase("3")){
		Hime.instance.addClientChatMessage("-----------HELP-(Page 3/4)-----------");
		Hime.instance.addClientChatMessage(".config load/save/remove <config name>: Loads, Saves, or Removes a Config");
		Hime.instance.addClientChatMessage(".username: Shows your username");
		Hime.instance.addClientChatMessage(".cape: Enables or disables cape");
		Hime.instance.addClientChatMessage(".clientbrand <Text>: Sets the client brand to whatever you want");
		Hime.instance.addClientChatMessage(".VClip <amount>: Clips vertically");
		Hime.instance.addClientChatMessage(".HClip <amount>: Clips horizontally");
		Hime.instance.addClientChatMessage(".friend add <name> <nick>: Adds a friend, .friend remove <name>: Removes a friend");
		Hime.instance.addClientChatMessage("-----------HELP-(Page 3/4)-----------");
	 }
	 if(args[0].equalsIgnoreCase("4")){
			Hime.instance.addClientChatMessage("-----------HELP-(Page 4/4)-----------");
			Hime.instance.addClientChatMessage(".rename [MODULE] [CUSTOMNAME] | .rename [MODULE] reset | .rename clear: Renames a module's name");
			Hime.instance.addClientChatMessage(".script enable [Filename] | .script disable [Filename] | .script remove [Filename]: Enables/disables a script (you can also bind scripts using .bind)");
			Hime.instance.addClientChatMessage("-----------HELP-(Page 4/4)-----------");
		 }
	 if(args[0].equalsIgnoreCase("")){
		Hime.instance.addClientChatMessage("-----------HELP-(Page 1/4)-----------");
	    Hime.instance.addClientChatMessage(".bind set/del/clear <module>: Binds a module, dels a keybind of a module, or clears all modules binds");
		Hime.instance.addClientChatMessage(".toggle <module>: Toggles a module");
		Hime.instance.addClientChatMessage(".ping: Shows your ping");
		Hime.instance.addClientChatMessage(".clientname <new clientname>: Sets the clientname");
		Hime.instance.addClientChatMessage(".help <page>: Does this");
		Hime.instance.addClientChatMessage(".panic: Turns off all modules");
		Hime.instance.addClientChatMessage(".autoggtext <TEXT>: Sets autogg's text");
		Hime.instance.addClientChatMessage("-----------HELP-(Page 1/4)-----------");
	 }
	}
}
