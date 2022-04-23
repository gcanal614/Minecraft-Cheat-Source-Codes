/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.modules.impl.combat;

import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.events.entity.EventUpdate;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;
import me.uncodable.srt.impl.modules.api.settings.Setting;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(internalName="Regen", name="Regeneration", desc="Allows you to regenerate health unordinarily fast.", category=Module.Category.COMBAT)
public class Regen
extends Module {
    private static final String INTERNAL_PACKET_VALUE = "INTERNAL_PACKET_VALUE";
    private static final String INTERNAL_HEALTH_VALUE = "INTERNAL_HEALTH_VALUE";
    private static final String INTERNAL_GROUND_CHECK = "INTERNAL_GROUND_CHECK";
    private static final String PACKET_VALUE_SETTING_NAME = "Packet Count";
    private static final String HEALTH_VALUE_SETTING_NAME = "Start Health";
    private static final String GROUND_CHECK_SETTING_NAME = "On Ground";

    public Regen(int key, boolean enabled) {
        super(key, enabled);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_PACKET_VALUE, PACKET_VALUE_SETTING_NAME, 100.0, 1.0, 1000.0, true);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_HEALTH_VALUE, HEALTH_VALUE_SETTING_NAME, 19.0, 1.0, 20.0, true);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_GROUND_CHECK, GROUND_CHECK_SETTING_NAME, true);
    }

    @EventTarget(target=EventUpdate.class)
    public void onUpdate(EventUpdate e) {
        if ((Regen.MC.thePlayer.onGround || !Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_GROUND_CHECK, Setting.Type.CHECKBOX).isTicked()) && Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_HEALTH_VALUE, Setting.Type.SLIDER).getCurrentValue() >= (double)Regen.MC.thePlayer.getHealth()) {
            int i = 0;
            while ((double)i < Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_PACKET_VALUE, Setting.Type.SLIDER).getCurrentValue()) {
                Regen.MC.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(Regen.MC.thePlayer.posX, Regen.MC.thePlayer.posY, Regen.MC.thePlayer.posZ, e.getRotationYaw(), e.getRotationPitch(), true));
                ++i;
            }
        }
    }
}

