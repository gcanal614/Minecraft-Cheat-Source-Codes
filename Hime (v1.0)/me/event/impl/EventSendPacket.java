package me.event.impl;


import me.event.Event;
import net.minecraft.network.Packet;

public class EventSendPacket extends Event {
	  Packet packet;
	  
	  public EventSendPacket(Packet packet) {
	    this.packet = packet;
	  }
	  
	  public Packet getPacket() {
	    return this.packet;
	  }
	  
	  public void setPacket(Packet packet) {
	    this.packet = packet;
	  }
}