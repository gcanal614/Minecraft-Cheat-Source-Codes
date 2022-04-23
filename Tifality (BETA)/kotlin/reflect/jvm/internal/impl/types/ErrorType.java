/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

public class ErrorType
extends SimpleType {
    @NotNull
    private final TypeConstructor constructor;
    @NotNull
    private final MemberScope memberScope;
    @NotNull
    private final List<TypeProjection> arguments;
    private final boolean isMarkedNullable;
    @NotNull
    private final String presentableName;

    @Override
    @NotNull
    public Annotations getAnnotations() {
        return Annotations.Companion.getEMPTY();
    }

    @Override
    @NotNull
    public String toString() {
        return this.getConstructor().toString() + (this.getArguments().isEmpty() ? "" : CollectionsKt.joinToString((Iterable)this.getArguments(), ", ", "<", ">", -1, "...", null));
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
        return new ErrorType(this.getConstructor(), this.getMemberScope(), this.getArguments(), newNullability, null, 16, null);
    }

    @Override
    @NotNull
    public ErrorType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
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

    @Override
    @NotNull
    public List<TypeProjection> getArguments() {
        return this.arguments;
    }

    @Override
    public boolean isMarkedNullable() {
        return this.isMarkedNullable;
    }

    @NotNull
    public String getPresentableName() {
        return this.presentableName;
    }

    @JvmOverloads
    public ErrorType(@NotNull TypeConstructor constructor, @NotNull MemberScope memberScope2, @NotNull List<? extends TypeProjection> arguments2, boolean isMarkedNullable, @NotNull String presentableName) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(memberScope2, "memberScope");
        Intrinsics.checkNotNullParameter(arguments2, "arguments");
        Intrinsics.checkNotNullParameter(presentableName, "presentableName");
        this.constructor = constructor;
        this.memberScope = memberScope2;
        this.arguments = arguments2;
        this.isMarkedNullable = isMarkedNullable;
        this.presentableName = presentableName;
    }

    public /* synthetic */ ErrorType(TypeConstructor typeConstructor2, MemberScope memberScope2, List list, boolean bl, String string, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            list = CollectionsKt.emptyList();
        }
        if ((n & 8) != 0) {
            bl = false;
        }
        if ((n & 0x10) != 0) {
            string = "???";
        }
        this(typeConstructor2, memberScope2, list, bl, string);
    }

    @JvmOverloads
    public ErrorType(@NotNull TypeConstructor constructor, @NotNull MemberScope memberScope2, @NotNull List<? extends TypeProjection> arguments2, boolean isMarkedNullable) {
        this(constructor, memberScope2, arguments2, isMarkedNullable, null, 16, null);
    }

    @JvmOverloads
    public ErrorType(@NotNull TypeConstructor constructor, @NotNull MemberScope memberScope2) {
        this(constructor, memberScope2, null, false, null, 28, null);
    }
}

