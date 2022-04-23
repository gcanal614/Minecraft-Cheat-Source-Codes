/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import org.jetbrains.annotations.NotNull;

public interface NameResolver {
    @NotNull
    public String getString(int var1);

    @NotNull
    public String getQualifiedClassName(int var1);

    public boolean isLocalClassName(int var1);
}

