/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.modules.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import me.uncodable.srt.impl.modules.api.Module;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    public String internalName();

    public String name();

    public String desc();

    public Module.Category category();

    public boolean exp() default false;

    public boolean legit() default false;
}

