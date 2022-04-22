package me.command;


import java.util.ArrayList;

import me.Hime;
import me.command.commands.*;


public class CommandManager {
	public static CommandManager instance;
	private ArrayList<Command> commands;
	
	public CommandManager(){
		commands = new ArrayList();
	    commands.add(new Bind());
	    commands.add(new Toggle());
	    commands.add(new Help());
	    commands.add(new Ping());
	    commands.add(new HClip());
	    commands.add(new VClip());
	    commands.add(new ServerInfo());
	    commands.add(new Username());
	    commands.add(new FriendCommand());
		commands.add(new Config());
		commands.add(new ClientName());
		commands.add(new Say());
		commands.add(new NameProtect());
		commands.add(new Teleport());
		commands.add(new Spammer());
		commands.add(new Panic());
		commands.add(new DataGrabber());
		commands.add(new ClientBrand());
		commands.add(new Cape());
		commands.add(new DmUser());
		commands.add(new AutoGGCommand());
		commands.add(new Rename());
		commands.add(new Script());
	}
	
	public ArrayList<Command> getCommands(){
		return commands;
	}
	
	public void callCommand(String text){
		String[] split = text.split(" ");
		String command = split[0];
		String args = text.substring(command.length()).trim();
		for(Command c: getCommands()){
			if(c.getName().equalsIgnoreCase(command)){
				try{
					c.onCommand(args, args.split(" "));
					c.onToggle();
				}catch(Exception e){
					Hime.addClientChatMessage("Invalid Command Usage!");
					Hime.addClientChatMessage(c.getSyntax());
				}
				return;
			}
		}
		Hime.addClientChatMessage("Command not found!");
	}

}
