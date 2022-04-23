/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.other;

import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.ModuleManager;

@ModuleInfo(label="MemoryFix", category=ModuleCategory.OTHER)
public final class MemoryFix
extends Module {
    public MemoryFix() {
        this.setHidden(true);
    }

    public static boolean cancelGarbageCollection() {
        return ModuleManager.getInstance(MemoryFix.class).isEnabled();
    }
}

