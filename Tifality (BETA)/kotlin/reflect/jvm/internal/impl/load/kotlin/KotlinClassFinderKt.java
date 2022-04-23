/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class KotlinClassFinderKt {
    @Nullable
    public static final KotlinJvmBinaryClass findKotlinClass(@NotNull KotlinClassFinder $this$findKotlinClass, @NotNull ClassId classId) {
        Intrinsics.checkNotNullParameter($this$findKotlinClass, "$this$findKotlinClass");
        Intrinsics.checkNotNullParameter(classId, "classId");
        KotlinClassFinder.Result result2 = $this$findKotlinClass.findKotlinClassOrContent(classId);
        return result2 != null ? result2.toKotlinJvmBinaryClass() : null;
    }

    @Nullable
    public static final KotlinJvmBinaryClass findKotlinClass(@NotNull KotlinClassFinder $this$findKotlinClass, @NotNull JavaClass javaClass) {
        Intrinsics.checkNotNullParameter($this$findKotlinClass, "$this$findKotlinClass");
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        KotlinClassFinder.Result result2 = $this$findKotlinClass.findKotlinClassOrContent(javaClass);
        return result2 != null ? result2.toKotlinJvmBinaryClass() : null;
    }
}

