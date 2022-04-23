/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractStubType
extends SimpleType {
    @NotNull
    private final TypeConstructor originalTypeVariable;
    private final boolean isMarkedNullable;
    @NotNull
    private final TypeConstructor constructor;
    @NotNull
    private final MemberScope memberScope;

    @Override
    @NotNull
    public List<TypeProjection> getArguments() {
        return CollectionsKt.emptyList();
    }

    @Override
    @NotNull
    public Annotations getAnnotations() {
        return Annotations.Companion.getEMPTY();
    }

    @Override
    @NotNull
    public SimpleType replaceAnnotations(@NotNull Annotations newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        return this;
    }

    @Override
    @NotNull
    public SimpleType makeNullableAsSpecified(boolean newNullability) {
        return newNullability == this.isMarkedNullable() ? this : this.materialize(newNullability);
    }

    @Override
    @NotNull
    public String toString() {
        return "NonFixed: " + this.originalTypeVariable;
    }

    @Override
    @NotNull
    public AbstractStubType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    @NotNull
    public abstract AbstractStubType materialize(boolean var1);

    @NotNull
    protected final TypeConstructor getOriginalTypeVariable() {
        return this.originalTypeVariable;
    }

    @Override
    public boolean isMarkedNullable() {
        return this.isMarkedNullable;
    }

    @Override
    @NotNull
    public TypeConstructor getConstructor() {
        return this.constructor;
    }

    @Override
    @NotNull
    public MemberScope getMemberScope() {
        return this.memberScope;
    }

    public AbstractStubType(@NotNull TypeConstructor originalTypeVariable, boolean isMarkedNullable, @NotNull TypeConstructor constructor, @NotNull MemberScope memberScope2) {
        Intrinsics.checkNotNullParameter(originalTypeVariable, "originalTypeVariable");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(memberScope2, "memberScope");
        this.originalTypeVariable = originalTypeVariable;
        this.isMarkedNullable = isMarkedNullable;
        this.constructor = constructor;
        this.memberScope = memberScope2;
    }
}

