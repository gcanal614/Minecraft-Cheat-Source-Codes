/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Predicate
 */
package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.IAnimals;

public interface IMob
extends IAnimals {
    public static final Predicate<Entity> mobSelector = p_apply_1_ -> p_apply_1_ instanceof IMob;
    public static final Predicate<Entity> VISIBLE_MOB_SELECTOR = p_apply_1_ -> p_apply_1_ instanceof IMob && !p_apply_1_.isInvisible();
}

