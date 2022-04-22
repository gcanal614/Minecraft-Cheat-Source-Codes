package me.event.impl;

import me.event.Event;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;

public class EventOpenChest extends Event{
	ContainerChest chest;

	public EventOpenChest(ContainerChest chest) {
		this.chest = chest;
	}

	public ContainerChest getChest() {
		return chest;
	}

	public void setChest(ContainerChest chest) {
		this.chest = chest;
	}
	
}
