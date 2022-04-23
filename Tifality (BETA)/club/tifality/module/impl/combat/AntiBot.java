/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.combat;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.packet.PacketReceiveEvent;
import club.tifality.manager.event.impl.player.DamageEntityEvent;
import club.tifality.manager.event.impl.world.WorldLoadEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.utils.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;

@ModuleInfo(label="AntiBot", category=ModuleCategory.COMBAT)
public final class AntiBot
extends Module {
    public static final List<EntityPlayer> BOTS = new ArrayList<EntityPlayer>();

    @Listener
    public void onPacketReceiveEvent(PacketReceiveEvent event) {
        NetworkPlayerInfo info;
        S0CPacketSpawnPlayer packet;
        UUID uuid;
        if (event.getPacket() instanceof S0CPacketSpawnPlayer && (uuid = (packet = (S0CPacketSpawnPlayer)event.getPacket()).getPlayer()) != Wrapper.getPlayer().getUniqueID() && ((info = Wrapper.getNetHandler().getPlayerInfo(uuid)) == null || info.getResponseTime() != 1)) {
            BOTS.add(Wrapper.getWorld().getPlayerEntityByUUID(uuid));
        }
    }

    @Listener
    public void onWorldChange(WorldLoadEvent event) {
        BOTS.clear();
    }

    @Listener
    public void onDamageEntityEvent(DamageEntityEvent event) {
        if (event.getEntity() instanceof EntityOtherPlayerMP) {
            BOTS.remove(event.getEntity());
        }
    }
}

