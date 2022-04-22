package me.command.commands;

import java.util.Scanner;

import me.command.Command;
import me.notification.Notification;
import me.notification.NotificationManager;
import me.notification.NotificationType;
import me.script.ScriptManager;

public class Script extends Command{

	public static Scanner scanner;
	@Override
	public String getName() {
		return "script";
	}

	@Override
	public String getDescription() {
		return "Allows user to load scripts.";
	}

	@Override
	public String getSyntax() {
		return ".script enable [Filename] | .script disable [Filename] | .script remove [Filename]";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
			if(args[0].equalsIgnoreCase("enable")) {
				ScriptManager.instance.enableScript(args[1]);
			NotificationManager.show(new Notification(NotificationType.WARNING, "Script Alert", args[1] + " Script Was Enabled", 2));
			} if(args[0].equalsIgnoreCase("disable")) {
				ScriptManager.instance.disableScript(args[1]);
			NotificationManager.show(new Notification(NotificationType.WARNING, "Script Alert", args[1] + " Script Was Disabled", 2));
			} else if(args[0].equalsIgnoreCase("remove")) {
				ScriptManager.instance.removeScript(args[1]);
			NotificationManager.show(new Notification(NotificationType.WARNING, "Script Alert", args[1] + " Script Was Removed", 2));
			}
		}	
}
