/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleAwareClassDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

public final class ModuleAwareClassDescriptorKt {
    @NotNull
    public static final MemberScope getRefinedUnsubstitutedMemberScopeIfPossible(@NotNull ClassDescriptor $this$getRefinedUnsubstitutedMemberScopeIfPossible, @NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter($this$getRefinedUnsubstitutedMemberScopeIfPossible, "$this$getRefinedUnsubstitutedMemberScopeIfPossible");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return ModuleAwareClassDescriptor.Companion.getRefinedUnsubstitutedMemberScopeIfPossible$descriptors($this$getRefinedUnsubstitutedMemberScopeIfPossible, kotlinTypeRefiner);
    }

    @NotNull
    public static final MemberScope getRefinedMemberScopeIfPossible(@NotNull ClassDescriptor $this$getRefinedMemberScopeIfPossible, @NotNull TypeSubstitution typeSubstitution, @NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter($this$getRefinedMemberScopeIfPossible, "$this$getRefinedMemberScopeIfPossible");
        Intrinsics.checkNotNullParameter(typeSubstitution, "typeSubstitution");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return ModuleAwareClassDescriptor.Companion.getRefinedMemberScopeIfPossible$descriptors($this$getRefinedMemberScopeIfPossible, typeSubstitution, kotlinTypeRefiner);
    }
}

