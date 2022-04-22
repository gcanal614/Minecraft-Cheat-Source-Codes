package com.zerosense.Events;

import com.zerosense.Events.impl.EventNoSlow;
import net.minecraft.network.Packet;

public class Event<T> {

    private boolean cancelled;
    public EventType type;
    public EventDirection direction;
    private Object y;
    public float yaw, pitch;
    public static Packet packet;
    
    public Event call(Event<T> event) {
    	this.cancelled = false;
    	this.call(this);
    	return this;
    }

    public boolean isCancelled()
    {
        return cancelled;
    }

    public void setCancelled(boolean cancelled)
    {
    	this.cancelled = cancelled;
    }



    public void setType(EventType type)
    {
        this.type = type;
    }

    public EventDirection getDirection()
    {
        return direction;
    }

    public void setDirection(EventDirection direction)
    {
        this.direction = direction;
    }

    public boolean isPre()
    {
        if(type == null)
            return false;

        return type == EventType.PRE;
    }

    public boolean isPost()
    {
        if(type == null)
            return false;

        return type == EventType.POST;
    }

    public boolean isIncoming()
    {
        if(direction == null)
            return false;

        return direction == EventDirection.INCOMING;
    }

    public boolean isOutgoing()
    {
        if(direction == null)
            return false;

        return direction == EventDirection.OUTGOING;
    }

    public static <T extends Packet> T getPacket() {
        return (T) packet;
    }

    public enum getState {
        PRE, POST;
    }


    public void setYaw(float yaw) {
        yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        pitch = pitch;
    }

    public Object getY2() {
        return y;
    }

    public void setY2(Object y) {
        this.y = y;
    }

    public void setY2(int i) {
    }

    public float getYaw() {
        return yaw;
    }

    public void setY(Object y) {
        this.y = y;
    }

    public enum State {
        PRE, POST;
    }
}
