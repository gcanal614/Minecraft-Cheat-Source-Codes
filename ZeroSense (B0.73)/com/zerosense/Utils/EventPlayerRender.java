package com.zerosense.Utils;

import com.zerosense.Events.Event;
import net.minecraft.client.entity.AbstractClientPlayer;

public class EventPlayerRender extends Event<EventPlayerRender> {
	
	public EventPlayerRender(AbstractClientPlayer entity) {
		
		this.entity = entity;
		
	}
	
	public AbstractClientPlayer entity;
	
}
