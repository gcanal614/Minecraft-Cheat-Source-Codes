/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;

public final class NameResolverUtilKt {
    @NotNull
    public static final ClassId getClassId(@NotNull NameResolver $this$getClassId, int index) {
        Intrinsics.checkNotNullParameter($this$getClassId, "$this$getClassId");
        ClassId classId = ClassId.fromString($this$getClassId.getQualifiedClassName(index), $this$getClassId.isLocalClassName(index));
        Intrinsics.checkNotNullExpressionValue(classId, "ClassId.fromString(getQu\u2026 isLocalClassName(index))");
        return classId;
    }

    @NotNull
    public static final Name getName(@NotNull NameResolver $this$getName, int index) {
        Intrinsics.checkNotNullParameter($this$getName, "$this$getName");
        Name name = Name.guessByFirstCharacter($this$getName.getString(index));
        Intrinsics.checkNotNullExpressionValue(name, "Name.guessByFirstCharacter(getString(index))");
        return name;
    }
}

