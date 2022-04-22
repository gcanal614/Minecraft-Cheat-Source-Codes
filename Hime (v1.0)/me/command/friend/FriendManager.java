package me.command.friend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import me.command.friend.sub.Friend;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;

public class FriendManager {
public static FriendManager instance = new FriendManager();

private ArrayList<Friend> friends = new ArrayList<Friend>();
	
ArrayList<String> toSave = new ArrayList<String>();
private File dir;
private File dataFile;

public void addFriend(String name, String alias) {
	friends.add(new Friend(name, alias));
	//toSave.add("FD:" + name + ":" + alias);
	//this.save();
}
	
	public ArrayList<Friend> getFriends() {
		return this.friends;
	}
	
	public void removeFriend(String name) {
		for(Friend f : this.friends) {
			if(f.getName().equalsIgnoreCase(name)) {
				this.friends.remove(f);
				//this.save();
				break;
			}
		}
		
	
}
	public void clearFriends() {
		this.friends.clear();
		//toSave.clear();
		//this.save();
	}
	
	public boolean isFriend(String name) {
		boolean isFriend = false;
		for (Friend f : this.friends) {
			if (f.getName().equalsIgnoreCase(StringUtils.stripControlCodes(name))) {
				isFriend = true;
				break;
			}
		}
		return isFriend;
	}
	public void saveFriends() {
		dir = new File(Minecraft.getMinecraft().mcDataDir, "Hime");
		if (!dir.exists()) {
			dir.mkdir();
		}
		dataFile = new File(dir, "friends.txt");
		
		if (!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}
		this.load();
		
	}
	
	public void load() {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (String s : lines) {
			String[] args = s.split(":");
			if (s.toLowerCase().startsWith("fd:")) {
				this.addFriend(args[1], args[2]);
			}
		}
	
	}
	
	public void save() {
		   try {
				PrintWriter pw1 = new PrintWriter(this.dataFile);
			
				for (String str1 : toSave) {
					//System.out.println(str);
					pw1.println(str1);
				}
				pw1.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
	
}
