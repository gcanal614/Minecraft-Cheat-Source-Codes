/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.modules.impl.movement;

import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.events.entity.EventMotionUpdate;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;
import me.uncodable.srt.impl.modules.api.settings.Setting;
import me.uncodable.srt.impl.utils.MovementUtils;

@ModuleInfo(internalName="FastLadder", name="Fast Ladder", desc="Allows you to climb ladders like your life depends on it.", category=Module.Category.MOVEMENT)
public class FastLadder
extends Module {
    private static final String COMBO_BOX_SETTING_NAME = "Fast Ladder Mode";
    private static final String INTERNAL_MOTION_VALUE = "INTERNAL_MOTION_VALUE";
    private static final String MOTION_VALUE_SETTING_NAME = "Motion";
    private static final String VANILLA = "Vanilla";
    private boolean wasOnLadder;

    public FastLadder(int key, boolean enabled) {
        super(key, enabled);
        Ries.INSTANCE.getSettingManager().addComboBox(this, "INTERNAL_GENERAL_COMBO_BOX", COMBO_BOX_SETTING_NAME, VANILLA);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_MOTION_VALUE, MOTION_VALUE_SETTING_NAME, 1.0, 0.0, 10.0);
    }

    @Override
    public void onDisable() {
        this.wasOnLadder = false;
    }

    @EventTarget(target=EventMotionUpdate.class)
    public void onMotion(EventMotionUpdate e) {
        if (e.getState() == EventMotionUpdate.State.PRE) {
            if (FastLadder.MC.thePlayer.isOnLadder() && MovementUtils.isMoving2()) {
                double motion = Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_MOTION_VALUE, Setting.Type.SLIDER).getCurrentValue();
                this.wasOnLadder = true;
                switch (Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo()) {
                    case "Vanilla": {
                        FastLadder.MC.thePlayer.motionY = motion;
                    }
                }
            } else if (this.wasOnLadder) {
                FastLadder.MC.thePlayer.motionY = 0.0;
                this.wasOnLadder = false;
            }
        }
    }
}

