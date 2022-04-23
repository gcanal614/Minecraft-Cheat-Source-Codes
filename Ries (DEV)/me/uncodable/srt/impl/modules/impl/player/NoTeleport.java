/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.modules.impl.player;

import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.events.packet.EventPacket;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;
import me.uncodable.srt.impl.utils.PacketUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

@ModuleInfo(internalName="NoTeleport", name="No Teleport", desc="Prevents the server from teleporting you back.\nCan be used effectively in conjunction with an anti-cheat disabler.", category=Module.Category.PLAYER)
public class NoTeleport
extends Module {
    public boolean received;
    public double x;
    public double y;
    public double z;

    public NoTeleport(int key, boolean enabled) {
        super(key, enabled);
    }

    @Override
    public void onDisable() {
        this.received = false;
        this.z = 0.0;
        this.y = 0.0;
        this.x = 0.0;
    }

    @EventTarget(target=EventPacket.class)
    public void onPacket(EventPacket e) {
        Packet<INetHandlerPlayClient> packet;
        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            packet = (S08PacketPlayerPosLook)PacketUtils.getPacket(e.getPacket());
            this.x = ((S08PacketPlayerPosLook)packet).getX();
            this.y = ((S08PacketPlayerPosLook)packet).getY();
            this.z = ((S08PacketPlayerPosLook)packet).getZ();
            this.received = true;
            e.setCancelled(true);
        }
        if (e.getPacket() instanceof C03PacketPlayer && this.received) {
            packet = (C03PacketPlayer)PacketUtils.getPacket(e.getPacket());
            ((C03PacketPlayer)packet).setX(this.x);
            ((C03PacketPlayer)packet).setY(this.y);
            ((C03PacketPlayer)packet).setZ(this.z);
            this.received = false;
        }
    }
}

