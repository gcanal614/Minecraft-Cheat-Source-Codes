/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.AbstractStubType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.model.StubTypeMarker;
import org.jetbrains.annotations.NotNull;

public final class StubType
extends AbstractStubType
implements StubTypeMarker {
    @Override
    @NotNull
    public AbstractStubType materialize(boolean newNullability) {
        return new StubType(this.getOriginalTypeVariable(), newNullability, this.getConstructor(), this.getMemberScope());
    }

    public StubType(@NotNull TypeConstructor originalTypeVariable, boolean isMarkedNullable, @NotNull TypeConstructor constructor, @NotNull MemberScope memberScope2) {
        Intrinsics.checkNotNullParameter(originalTypeVariable, "originalTypeVariable");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(memberScope2, "memberScope");
        super(originalTypeVariable, isMarkedNullable, constructor, memberScope2);
    }
}

