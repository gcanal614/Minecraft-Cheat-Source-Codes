/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.events.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import me.uncodable.srt.impl.events.api.Event;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface EventTarget {
    public Class<? extends Event> target();
}

