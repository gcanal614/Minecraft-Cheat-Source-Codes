/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.incremental.components;

import kotlin.reflect.jvm.internal.impl.incremental.components.Position;
import org.jetbrains.annotations.NotNull;

public interface LocationInfo {
    @NotNull
    public String getFilePath();

    @NotNull
    public Position getPosition();
}

