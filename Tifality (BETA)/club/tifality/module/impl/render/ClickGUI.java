/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.render;

import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.impl.EnumProperty;

@ModuleInfo(label="ClickGui", key=54, category=ModuleCategory.RENDER)
public final class ClickGUI
extends Module {
    public static final EnumProperty<ClickGuiMode> clickGuiMode = new EnumProperty<ClickGuiMode>("Mode", ClickGuiMode.SKEET);

    public ClickGUI() {
        this.setHidden(true);
    }

    public static enum ClickGuiMode {
        SKEET,
        DROPDOWN,
        StrawBerry;

    }
}

