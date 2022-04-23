/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  store.intent.intentguard.annotation.Native
 */
package me.uncodable.srt.impl.modules.impl.movement;

import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.events.entity.EventMotionUpdate;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;
import me.uncodable.srt.impl.modules.api.settings.Setting;
import store.intent.intentguard.annotation.Native;

@ModuleInfo(internalName="Spider", name="Spider", desc="Allows you to climb walls like a spider.\nYou venomous prick!", category=Module.Category.MOVEMENT)
public class Spider
extends Module {
    private static final String COMBO_BOX_SETTING_NAME = "Spider Mode";
    private static final String KARHU = "Karhu";
    private static final String LEGACY_MATRIX = "Legacy Matrix";

    public Spider(int key, boolean enabled) {
        super(key, enabled);
        Ries.INSTANCE.getSettingManager().addComboBox(this, "INTERNAL_GENERAL_COMBO_BOX", COMBO_BOX_SETTING_NAME, KARHU, LEGACY_MATRIX);
    }

    @EventTarget(target=EventMotionUpdate.class)
    @Native
    public void onMotion(EventMotionUpdate e) {
        if (e.getState() == EventMotionUpdate.State.PRE && Spider.MC.thePlayer.isCollidedHorizontally) {
            switch (Ries.INSTANCE.getSettingManager().getSetting(this, "INTERNAL_GENERAL_COMBO_BOX", Setting.Type.COMBO_BOX).getCurrentCombo()) {
                case "Karhu": {
                    if (Spider.MC.thePlayer.ticksExisted % 3 != 0) break;
                    Spider.MC.thePlayer.jump();
                    break;
                }
                case "Legacy Matrix": {
                    if (Spider.MC.thePlayer.ticksExisted % 4 != 0) break;
                    Spider.MC.thePlayer.motionY = 0.25;
                }
            }
        }
    }
}

