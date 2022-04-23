/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaMember;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;
import org.jetbrains.annotations.NotNull;

public final class ReflectJavaField
extends ReflectJavaMember
implements JavaField {
    @NotNull
    private final Field member;

    @Override
    public boolean isEnumEntry() {
        return this.getMember().isEnumConstant();
    }

    @Override
    @NotNull
    public ReflectJavaType getType() {
        Type type2 = this.getMember().getGenericType();
        Intrinsics.checkNotNullExpressionValue(type2, "member.genericType");
        return ReflectJavaType.Factory.create(type2);
    }

    @Override
    public boolean getHasConstantNotNullInitializer() {
        return false;
    }

    @Override
    @NotNull
    public Field getMember() {
        return this.member;
    }

    public ReflectJavaField(@NotNull Field member) {
        Intrinsics.checkNotNullParameter(member, "member");
        this.member = member;
    }
}

