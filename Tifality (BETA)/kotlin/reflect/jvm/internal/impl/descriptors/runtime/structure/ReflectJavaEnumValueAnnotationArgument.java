/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaEnumValueAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectJavaEnumValueAnnotationArgument
extends ReflectJavaAnnotationArgument
implements JavaEnumValueAnnotationArgument {
    private final Enum<?> value;

    @Override
    @Nullable
    public ClassId getEnumClassId() {
        Class<?> clazz;
        Class<?> clazz2 = this.value.getClass();
        if (clazz2.isEnum()) {
            clazz = clazz2;
        } else {
            Class<?> clazz3 = clazz2.getEnclosingClass();
            clazz = clazz3;
            Intrinsics.checkNotNullExpressionValue(clazz3, "clazz.enclosingClass");
        }
        Class<?> enumClass = clazz;
        return ReflectClassUtilKt.getClassId(enumClass);
    }

    @Override
    @Nullable
    public Name getEntryName() {
        return Name.identifier(this.value.name());
    }

    public ReflectJavaEnumValueAnnotationArgument(@Nullable Name name, @NotNull Enum<?> value) {
        Intrinsics.checkNotNullParameter(value, "value");
        super(name);
        this.value = value;
    }
}

