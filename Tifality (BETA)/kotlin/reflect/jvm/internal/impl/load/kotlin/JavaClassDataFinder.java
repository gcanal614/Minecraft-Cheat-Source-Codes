/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin._Assertions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.kotlin.DeserializedDescriptorResolver;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinderKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassData;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassDataFinder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JavaClassDataFinder
implements ClassDataFinder {
    @NotNull
    private final KotlinClassFinder kotlinClassFinder;
    private final DeserializedDescriptorResolver deserializedDescriptorResolver;

    @Override
    @Nullable
    public ClassData findClassData(@NotNull ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        KotlinJvmBinaryClass kotlinJvmBinaryClass = KotlinClassFinderKt.findKotlinClass(this.kotlinClassFinder, classId);
        if (kotlinJvmBinaryClass == null) {
            return null;
        }
        KotlinJvmBinaryClass kotlinClass2 = kotlinJvmBinaryClass;
        boolean bl = Intrinsics.areEqual(kotlinClass2.getClassId(), classId);
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl3 = false;
            String string = "Class with incorrect id found: expected " + classId + ", actual " + kotlinClass2.getClassId();
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        return this.deserializedDescriptorResolver.readClassData$descriptors_jvm(kotlinClass2);
    }

    public JavaClassDataFinder(@NotNull KotlinClassFinder kotlinClassFinder, @NotNull DeserializedDescriptorResolver deserializedDescriptorResolver) {
        Intrinsics.checkNotNullParameter(kotlinClassFinder, "kotlinClassFinder");
        Intrinsics.checkNotNullParameter(deserializedDescriptorResolver, "deserializedDescriptorResolver");
        this.kotlinClassFinder = kotlinClassFinder;
        this.deserializedDescriptorResolver = deserializedDescriptorResolver;
    }
}

