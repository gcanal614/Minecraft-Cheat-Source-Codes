package me.command.commands;




import me.Hime;
import me.command.Command;
import me.command.friend.sub.Friend;
import net.minecraft.client.Minecraft;

public class FriendCommand extends Command{

	@Override
	public String getName() {
		return "friend";
	}

	@Override
	public String getDescription() {
		return "Allows user to add friends";
	}

	@Override
	public String getSyntax() {
		return ".friend add [Name] [Alias] | .friend remove [Name] | .friend clear";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		if(args[0].equalsIgnoreCase("add")){
			String name = args[1];
			String alias = args[2];
			if(!Hime.instance.friendManager.isFriend(name) && !name.equalsIgnoreCase(Minecraft.getMinecraft().thePlayer.getName())) {
				Hime.instance.friendManager.addFriend(name, alias);
				Hime.addClientChatMessage("Added " + name + " as " + alias);
			}else {
				Hime.addClientChatMessage(name + " is already your friend or is yourself");
			}
		}
		if(args[0].equalsIgnoreCase("remove")) {
			String name = args[1];
			if(Hime.instance.friendManager.isFriend(name)) {
				Hime.instance.friendManager.removeFriend(name);
				Hime.addClientChatMessage("Removed " + name + " from your friends list");
			}else {
				Hime.addClientChatMessage(name + " is not your friend");
			}
		}
		if(args[0].equalsIgnoreCase("list")) {
			
			  if(!Hime.friendManager.getFriends().isEmpty()) {
				  for(Friend friend : Hime.friendManager.getFriends()) {
				    Hime.addClientChatMessage("Friend: " + friend.getName());
				  }
			  }else {
				  Hime.addClientChatMessage("You don't have any friends :(");
			  }
		}
		if(args[0].equalsIgnoreCase("clear")) {
			Hime.instance.friendManager.clearFriends();
			Hime.addClientChatMessage("Cleared friends");
		}
	}

}
