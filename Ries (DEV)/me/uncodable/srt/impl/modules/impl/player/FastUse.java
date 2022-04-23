/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.modules.impl.player;

import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.events.entity.EventUpdate;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;
import me.uncodable.srt.impl.modules.api.settings.Setting;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(internalName="FastUse", name="Fast Use", desc="Allows you to eat like you're a Discord moderator.", category=Module.Category.PLAYER)
public class FastUse
extends Module {
    private static final String INTERNAL_PACKET_VALUE = "INTERNAL_PACKET_VALUE";
    private static final String INTERNAL_GROUND_CHECK = "INTERNAL_GROUND_CHECK";
    private static final String PACKET_VALUE_SETTING_NAME = "Packet Count";
    private static final String GROUND_CHECK_SETTING_NAME = "On Ground";

    public FastUse(int key, boolean enabled) {
        super(key, enabled);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_PACKET_VALUE, PACKET_VALUE_SETTING_NAME, 100.0, 1.0, 1000.0, true);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_GROUND_CHECK, GROUND_CHECK_SETTING_NAME, true);
    }

    @EventTarget(target=EventUpdate.class)
    public void onUpdate(EventUpdate e) {
        if (!(!FastUse.MC.thePlayer.onGround && Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_GROUND_CHECK, Setting.Type.CHECKBOX).isTicked() || !FastUse.MC.thePlayer.isEating() || FastUse.MC.thePlayer.getHeldItem().getItem() instanceof ItemSword || FastUse.MC.thePlayer.getHeldItem().getItem() instanceof ItemBow)) {
            int i = 0;
            while ((double)i < Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_PACKET_VALUE, Setting.Type.SLIDER).getCurrentValue()) {
                FastUse.MC.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(FastUse.MC.thePlayer.posX, FastUse.MC.thePlayer.posY, FastUse.MC.thePlayer.posZ, e.getRotationYaw(), e.getRotationPitch(), true));
                ++i;
            }
        }
    }
}

