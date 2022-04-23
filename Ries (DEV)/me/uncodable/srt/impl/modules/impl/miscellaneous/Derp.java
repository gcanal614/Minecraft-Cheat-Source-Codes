/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.RandomUtils
 */
package me.uncodable.srt.impl.modules.impl.miscellaneous;

import java.util.Random;
import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.events.api.EventTarget;
import me.uncodable.srt.impl.events.events.entity.EventMotionUpdate;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;
import me.uncodable.srt.impl.modules.api.settings.Setting;
import org.apache.commons.lang3.RandomUtils;

@ModuleInfo(internalName="Derp", name="Derp", desc="Allows you to look quite frankly retarded.\nI would add a funny remark, but I've harassed the special education students enough...", category=Module.Category.MISCELLANEOUS)
public class Derp
extends Module {
    private static final String INTERNAL_LOCKVIEW = "INTERNAL_LOCKVIEW";
    private static final String INTERNAL_SPIN = "INTERNAL_SPIN";
    private static final String INTERNAL_HEADLESS = "INTERNAL_HEADLESS";
    private static final String INTERNAL_SPIN_SPEED_VALUE = "INTERNAL_SPIN_SPEED_VALUE";
    private static final String INTERNAL_SPIN_RANDOMIZATION = "INTERNAL_SPIN_RANDOMIZATION";
    private static final String LOCKVIEW_SETTING_NAME = "Use Lock View";
    private static final String SPIN_SETTING_NAME = "Spin";
    private static final String HEADLESS_SETTING_NAME = "Headless";
    private static final String SPIN_SPEED_VALUE_SETTING_NAME = "Spin Speed";
    private static final String SPIN_RANDOMIZATION_SETTING_NAME = "Randomize Spin";
    private float yaw;
    private final Random random = new Random();

    public Derp(int key, boolean enabled) {
        super(key, enabled);
        Ries.INSTANCE.getSettingManager().addSlider(this, INTERNAL_SPIN_SPEED_VALUE, SPIN_SPEED_VALUE_SETTING_NAME, 0.0, 0.0, 100.0, true);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_SPIN, SPIN_SETTING_NAME, true);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_HEADLESS, HEADLESS_SETTING_NAME, true);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_LOCKVIEW, LOCKVIEW_SETTING_NAME);
        Ries.INSTANCE.getSettingManager().addCheckbox(this, INTERNAL_SPIN_RANDOMIZATION, SPIN_RANDOMIZATION_SETTING_NAME, true);
    }

    @Override
    public void onEnable() {
        this.yaw = Derp.MC.thePlayer != null ? Derp.MC.thePlayer.rotationYaw : 0.0f;
    }

    @EventTarget(target=EventMotionUpdate.class)
    public void onMotion(EventMotionUpdate e) {
        if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_LOCKVIEW, Setting.Type.CHECKBOX).isTicked()) {
            if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_HEADLESS, Setting.Type.CHECKBOX).isTicked()) {
                Derp.MC.thePlayer.rotationPitch = -180.0f;
            }
            if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPIN, Setting.Type.CHECKBOX).isTicked()) {
                Derp.MC.thePlayer.rotationYaw = (float)((double)Derp.MC.thePlayer.rotationYaw + Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPIN_SPEED_VALUE, Setting.Type.SLIDER).getCurrentValue());
                if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPIN_RANDOMIZATION, Setting.Type.CHECKBOX).isTicked()) {
                    Derp.MC.thePlayer.rotationYaw = Derp.MC.thePlayer.rotationYaw + (this.random.nextBoolean() ? RandomUtils.nextFloat((float)0.0f, (float)10.0f) : -RandomUtils.nextFloat((float)0.0f, (float)10.0f));
                }
            }
        } else {
            if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_HEADLESS, Setting.Type.CHECKBOX).isTicked()) {
                e.setRotationPitch(-180.0f);
            }
            if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPIN, Setting.Type.CHECKBOX).isTicked()) {
                e.setRotationYaw(this.yaw);
                this.yaw = (float)((double)this.yaw + Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPIN_SPEED_VALUE, Setting.Type.SLIDER).getCurrentValue());
                if (Ries.INSTANCE.getSettingManager().getSetting(this, INTERNAL_SPIN_RANDOMIZATION, Setting.Type.CHECKBOX).isTicked()) {
                    this.yaw += this.random.nextBoolean() ? RandomUtils.nextFloat((float)0.0f, (float)10.0f) : -RandomUtils.nextFloat((float)0.0f, (float)10.0f);
                }
            }
        }
    }
}

