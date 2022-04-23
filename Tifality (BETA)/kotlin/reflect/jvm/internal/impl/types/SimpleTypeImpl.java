/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.AnnotatedSimpleType;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.NotNullSimpleType;
import kotlin.reflect.jvm.internal.impl.types.NullableSimpleType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

final class SimpleTypeImpl
extends SimpleType {
    @NotNull
    private final TypeConstructor constructor;
    @NotNull
    private final List<TypeProjection> arguments;
    private final boolean isMarkedNullable;
    @NotNull
    private final MemberScope memberScope;
    private final Function1<KotlinTypeRefiner, SimpleType> refinedTypeFactory;

    @Override
    @NotNull
    public Annotations getAnnotations() {
        return Annotations.Companion.getEMPTY();
    }

    @Override
    @NotNull
    public SimpleType replaceAnnotations(@NotNull Annotations newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        return newAnnotations.isEmpty() ? (SimpleType)this : (SimpleType)new AnnotatedSimpleType(this, newAnnotations);
    }

    @Override
    @NotNull
    public SimpleType makeNullableAsSpecified(boolean newNullability) {
        return newNullability == this.isMarkedNullable() ? (SimpleType)this : (newNullability ? (SimpleType)new NullableSimpleType(this) : (SimpleType)new NotNullSimpleType(this));
    }

    @Override
    @NotNull
    public SimpleType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        SimpleType simpleType2 = this.refinedTypeFactory.invoke(kotlinTypeRefiner);
        if (simpleType2 == null) {
            simpleType2 = this;
        }
        return simpleType2;
    }

    @Override
    @NotNull
    public TypeConstructor getConstructor() {
        return this.constructor;
    }

    @Override
    @NotNull
    public List<TypeProjection> getArguments() {
        return this.arguments;
    }

    @Override
    public boolean isMarkedNullable() {
        return this.isMarkedNullable;
    }

    @Override
    @NotNull
    public MemberScope getMemberScope() {
        return this.memberScope;
    }

    public SimpleTypeImpl(@NotNull TypeConstructor constructor, @NotNull List<? extends TypeProjection> arguments2, boolean isMarkedNullable, @NotNull MemberScope memberScope2, @NotNull Function1<? super KotlinTypeRefiner, ? extends SimpleType> refinedTypeFactory) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments2, "arguments");
        Intrinsics.checkNotNullParameter(memberScope2, "memberScope");
        Intrinsics.checkNotNullParameter(refinedTypeFactory, "refinedTypeFactory");
        this.constructor = constructor;
        this.arguments = arguments2;
        this.isMarkedNullable = isMarkedNullable;
        this.memberScope = memberScope2;
        this.refinedTypeFactory = refinedTypeFactory;
        if (this.getMemberScope() instanceof ErrorUtils.ErrorScope) {
            throw (Throwable)new IllegalStateException("SimpleTypeImpl should not be created for error type: " + this.getMemberScope() + '\n' + this.getConstructor());
        }
    }
}

