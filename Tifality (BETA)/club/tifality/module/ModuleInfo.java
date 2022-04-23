/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module;

import club.tifality.module.ModuleCategory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    public String label();

    public int key() default 0;

    public ModuleCategory category();

    public String description() default "";
}

