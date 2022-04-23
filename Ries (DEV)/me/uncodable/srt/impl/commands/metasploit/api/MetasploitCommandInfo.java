/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.commands.metasploit.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface MetasploitCommandInfo {
    public String name();

    public String desc();
}

