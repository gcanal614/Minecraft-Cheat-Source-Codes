/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.miscellaneous;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventReceivedPacket;
import de.fanta.module.Module;
import java.awt.Color;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotateSet
extends Module {
    public NoRotateSet() {
        super("NoRotateSet", 0, Module.Type.Misc, Color.RED);
    }

    @Override
    public void onEvent(Event event) {
        Packet p;
        if (event instanceof EventReceivedPacket && (p = EventReceivedPacket.INSTANCE.getPacket()) instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook Look = (S08PacketPlayerPosLook)p;
            Look.yaw = NoRotateSet.mc.thePlayer.rotationYaw;
            Look.pitch = NoRotateSet.mc.thePlayer.rotationPitch;
        }
    }
}

