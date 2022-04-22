package me.command;

import net.minecraft.client.Minecraft;

public abstract class Command {
	protected Minecraft mc = Minecraft.getMinecraft();
	public abstract String getName();
	public abstract String getDescription();
	public abstract String getSyntax();
	public abstract void onCommand(String command, String[] args) throws Exception;
	public void onToggle() {};

}
