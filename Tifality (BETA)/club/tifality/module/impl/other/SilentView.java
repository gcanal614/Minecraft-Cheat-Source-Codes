/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.other;

import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.Property;
import java.awt.Color;

@ModuleInfo(label="SilentView", category=ModuleCategory.OTHER)
public final class SilentView
extends Module {
    public static final Property<Boolean> ghostSilentView = new Property<Boolean>("Ghost", false);
    public static final Property<Integer> color = new Property<Integer>("Color", new Color(255, 0, 0).getRGB(), ghostSilentView::get);
}

