/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.render;

import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.ModuleManager;
import club.tifality.property.Property;
import club.tifality.utils.render.Colors;

@ModuleInfo(label="EnchantEffect", category=ModuleCategory.RENDER)
public final class EnchantEffect
extends Module {
    private final Property<Integer> itemColorProperty = new Property<Integer>("Item Glint Color", Colors.RED);
    private final Property<Integer> armorModelColorProperty = new Property<Integer>("Armor Glint Color", Colors.RED);

    private static EnchantEffect getInstance() {
        return ModuleManager.getInstance(EnchantEffect.class);
    }

    public static boolean isGlintEnabled() {
        return EnchantEffect.getInstance().isEnabled();
    }

    public static int getItemColor() {
        return EnchantEffect.getInstance().itemColorProperty.getValue();
    }

    public static int getArmorModelColor() {
        return EnchantEffect.getInstance().armorModelColorProperty.getValue();
    }
}

