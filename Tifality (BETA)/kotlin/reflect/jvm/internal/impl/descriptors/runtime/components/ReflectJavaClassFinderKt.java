/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectJavaClassFinderKt {
    @Nullable
    public static final Class<?> tryLoadClass(@NotNull ClassLoader $this$tryLoadClass, @NotNull String fqName2) {
        Class<?> clazz;
        Intrinsics.checkNotNullParameter($this$tryLoadClass, "$this$tryLoadClass");
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        try {
            clazz = Class.forName(fqName2, false, $this$tryLoadClass);
        }
        catch (ClassNotFoundException e) {
            clazz = null;
        }
        return clazz;
    }
}

