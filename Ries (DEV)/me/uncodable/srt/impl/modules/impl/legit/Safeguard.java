/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.modules.impl.legit;

import me.uncodable.srt.Ries;
import me.uncodable.srt.impl.modules.api.Module;
import me.uncodable.srt.impl.modules.api.ModuleInfo;

@ModuleInfo(internalName="Safeguard", name="Safeguard", desc="Allows you to play legitimately with SRT, without toggling illegitimate modules.", category=Module.Category.LEGIT, legit=true)
public class Safeguard
extends Module {
    public Safeguard(int key, boolean enabled) {
        super(key, enabled);
    }

    @Override
    public void onEnable() {
        Ries.INSTANCE.getModuleManager().getModules().stream().filter(module -> !module.getInfo().legit() && module.isEnabled()).forEach(module -> {
            Ries.INSTANCE.msg(String.format("Module \"%s\" was disabled to provide a fair advantage. You cannot toggle illegitimate modules while \u00a7bsafeguard\u00a77 is enabled.", module.getInfo().name()));
            module.toggle();
        });
    }
}

