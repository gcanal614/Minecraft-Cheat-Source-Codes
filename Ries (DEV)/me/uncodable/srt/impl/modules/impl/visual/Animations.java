/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package me.uncodable.srt.impl.modules.impl.visual;

import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.events.entity.EventUpdate;
import me.uncodable.srt.impl.events.events.packet.EventPacket;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;
import me.uncodable.srt.impl.modules.api.settings.Setting;
import me.uncodable.srt.impl.utils.PacketUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

@ModuleInfo(internalName="Animations", name="Animations", desc="Allows you to display animations that do not come stock in vanilla 1.8.x.\nMojang can never do anything right.", category=Module.Category.VISUAL, legit=true, exp=true)
public class Animations
extends Module {
    private static final String INTERNAL_ONE_POINT_SEVEN = "INTERNAL_1_7";
    private static final String INTERNAL_F5_ANIMATION = "INTERNAL_F5_ANIMATION";
    private static final String ONE_POINT_SEVEN_SETTING_NAME = "1.7 Swing";
    private static final String F5_ANIMATION_SETTING_NAME = "F5 Rotations";
    private float prevYaw;

    public Animations(int key, boolean enabled) {
        super(key, enabled);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_ONE_POINT_SEVEN, ONE_POINT_SEVEN_SETTING_NAME, true);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_F5_ANIMATION, F5_ANIMATION_SETTING_NAME, true);
    }

    @Override
    public void onEnable() {
        if (Animations.MC.thePlayer != null) {
            this.prevYaw = Animations.MC.thePlayer.rotationYaw;
        }
    }

    @EventTarget(target=EventUpdate.class)
    public void onUpdate(EventUpdate e) {
        if (e.getState() == EventUpdate.State.PRE && Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_ONE_POINT_SEVEN, Setting.Type.CHECKBOX).isTicked() && Animations.MC.objectMouseOver != null && Animations.MC.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && Animations.MC.thePlayer.isUsingItem() && Mouse.isButtonDown((int)0) && Animations.MC.currentScreen == null) {
            Animations.MC.thePlayer.swingItem();
        }
    }

    @EventTarget(target=EventPacket.class)
    public void onPacket(EventPacket e) {
        if ((e.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook || e.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook) && Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_F5_ANIMATION, Setting.Type.CHECKBOX).isTicked()) {
            C03PacketPlayer packet = (C03PacketPlayer)PacketUtils.getPacket(e.getPacket());
            Animations.MC.thePlayer.rotationYawHead = packet.getYaw();
            Animations.MC.thePlayer.renderYawOffset = packet.getYaw();
        }
    }
}

