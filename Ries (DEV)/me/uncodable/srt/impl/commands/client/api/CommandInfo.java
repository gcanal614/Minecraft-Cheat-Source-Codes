/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.commands.client.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface CommandInfo {
    public String name();

    public String desc();

    public String usage();

    public boolean legit() default false;
}

