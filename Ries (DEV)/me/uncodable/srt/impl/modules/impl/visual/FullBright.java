/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.modules.impl.visual;

import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;

@ModuleInfo(internalName="FullBright", name="Full Bright", desc="Allows you to see in darkened areas.", category=Module.Category.VISUAL, legit=true)
public class FullBright
extends Module {
    private float oldBrightness;

    public FullBright(int key, boolean enabled) {
        super(key, enabled);
    }

    @Override
    public void onEnable() {
        this.oldBrightness = FullBright.MC.gameSettings.gammaSetting;
        FullBright.MC.gameSettings.gammaSetting = 1000.0f;
    }

    @Override
    public void onDisable() {
        FullBright.MC.gameSettings.gammaSetting = this.oldBrightness;
    }
}

