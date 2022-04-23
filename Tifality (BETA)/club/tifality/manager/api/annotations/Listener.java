/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.manager.api.annotations;

import club.tifality.manager.api.annotations.Priority;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface Listener {
    public Priority value() default Priority.MEDIUM;
}

