/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.modules.impl.combat;

import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.events.packet.EventPacket;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;
import me.uncodable.srt.impl.utils.PacketUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

@ModuleInfo(internalName="ReverseKnockback", name="Reverse Knock-Back", desc="Allows you to hit any entity towards you.\nGreat for combo-mode.", category=Module.Category.COMBAT)
public class ReverseKnockback
extends Module {
    public ReverseKnockback(int key, boolean enabled) {
        super(key, enabled);
    }

    @EventTarget(target=EventPacket.class)
    public void onPacket(EventPacket e) {
        C02PacketUseEntity packet;
        if (e.getPacket() instanceof C02PacketUseEntity && (packet = (C02PacketUseEntity)PacketUtils.getPacket(e.getPacket())).getAction() == C02PacketUseEntity.Action.ATTACK) {
            Entity entity = packet.getEntityFromWorld(ReverseKnockback.MC.theWorld);
            double xDiff = entity.posX - ReverseKnockback.MC.thePlayer.posX;
            double zDiff = entity.posZ - ReverseKnockback.MC.thePlayer.posZ;
            ReverseKnockback.MC.thePlayer.sendQueue.packetNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(entity.posX + MathHelper.clamp_double(xDiff, -1.0, 1.0), entity.posY, entity.posZ + MathHelper.clamp_double(zDiff, -1.0, 1.0), ReverseKnockback.MC.thePlayer.rotationYaw + 180.0f, ReverseKnockback.MC.thePlayer.rotationPitch, entity.onGround));
        }
    }
}

