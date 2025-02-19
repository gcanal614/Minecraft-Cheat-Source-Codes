package me.injusttice.neutron.impl.commands;

import java.util.*;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.api.events.impl.EventChat;
import me.injusttice.neutron.impl.commands.impl.*;

public class CommandManager {

	public List<Command> commands = new ArrayList<Command>();
	public String prefix = ".";

	public CommandManager() {
		setup();
	}

	public void setup() {
		commands.add(new Hide());
		commands.add(new Help());
		commands.add(new Rename());
		commands.add(new VClip());
		commands.add(new Toggle());
		commands.add(new Bind());
		commands.add(new HClip());
		commands.add(new Name());
		commands.add(new Config());
	}

	public void handleChat(EventChat event) {
		String message = event.getMessage();

		if(!message.startsWith(prefix))
			return;

		event.setCancelled(true);

		message = message.substring(prefix.length());

		boolean foundCommand = false;

		if(message.split(" ").length > 0) {
			String commandName = message.split(" ")[0];

			for(Command c : commands) {
				if(c.aliases.contains(commandName) || c.name.equalsIgnoreCase(commandName)) {
					c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
					foundCommand = true;
					break;
				}
			}
		}

		if(!foundCommand) {
			NeutronMain.addChatMessage("Could find the command.");
		}
	}
}
