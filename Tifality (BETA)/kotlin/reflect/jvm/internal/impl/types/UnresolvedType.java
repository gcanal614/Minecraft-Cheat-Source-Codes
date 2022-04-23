/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.ErrorType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

public final class UnresolvedType
extends ErrorType {
    @NotNull
    private final String presentableName;

    @Override
    @NotNull
    public SimpleType makeNullableAsSpecified(boolean newNullability) {
        return new UnresolvedType(this.getPresentableName(), this.getConstructor(), this.getMemberScope(), this.getArguments(), newNullability);
    }

    @Override
    @NotNull
    public UnresolvedType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    @Override
    @NotNull
    public String getPresentableName() {
        return this.presentableName;
    }

    public UnresolvedType(@NotNull String presentableName, @NotNull TypeConstructor constructor, @NotNull MemberScope memberScope2, @NotNull List<? extends TypeProjection> arguments2, boolean isMarkedNullable) {
        Intrinsics.checkNotNullParameter(presentableName, "presentableName");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(memberScope2, "memberScope");
        Intrinsics.checkNotNullParameter(arguments2, "arguments");
        super(constructor, memberScope2, arguments2, isMarkedNullable, null, 16, null);
        this.presentableName = presentableName;
    }
}

