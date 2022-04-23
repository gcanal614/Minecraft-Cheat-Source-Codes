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

@ModuleInfo(internalName="AntiVoid", name="Anti-Void", desc="Allows you to rubberband back after your dumbass fell into the void.\nUncodable can relate very well... is that a good thing, though?", category=Module.Category.MOVEMENT)
public class AntiVoid
extends Module {
    private static final String COMBO_BOX_SETTING_NAME = "Anti-Void Mode";
    private static final String BASIC = "Basic";

    public AntiVoid(int key, boolean enabled) {
        super(key, enabled);
        Ries.INSTANCE.getSettingManager().addComboBox(this, "INTERNAL_GENERAL_COMBO_BOX", COMBO_BOX_SETTING_NAME, BASIC);
    }

    @EventTarget(target=EventMotionUpdate.class)
    public void onMotion(EventMotionUpdate e) {
        if ((double)AntiVoid.MC.thePlayer.fallDistance > 10.0 && e.getState() == EventMotionUpdate.State.PRE) {
            switch (Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo()) {
                case "Basic": {
                    e.setPosX(AntiVoid.MC.thePlayer.posX + 10.1);
                }
            }
        }
    }
}

