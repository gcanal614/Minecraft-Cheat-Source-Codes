/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

public abstract class ModuleAwareClassDescriptor
implements ClassDescriptor {
    public static final Companion Companion = new Companion(null);

    @NotNull
    protected abstract MemberScope getUnsubstitutedMemberScope(@NotNull KotlinTypeRefiner var1);

    @NotNull
    protected abstract MemberScope getMemberScope(@NotNull TypeSubstitution var1, @NotNull KotlinTypeRefiner var2);

    public static final class Companion {
        @NotNull
        public final MemberScope getRefinedUnsubstitutedMemberScopeIfPossible$descriptors(@NotNull ClassDescriptor $this$getRefinedUnsubstitutedMemberScopeIfPossible, @NotNull KotlinTypeRefiner kotlinTypeRefiner) {
            Object object;
            Intrinsics.checkNotNullParameter($this$getRefinedUnsubstitutedMemberScopeIfPossible, "$this$getRefinedUnsubstitutedMemberScopeIfPossible");
            Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
            ClassDescriptor classDescriptor = $this$getRefinedUnsubstitutedMemberScopeIfPossible;
            if (!(classDescriptor instanceof ModuleAwareClassDescriptor)) {
                classDescriptor = null;
            }
            if ((object = (ModuleAwareClassDescriptor)classDescriptor) == null || (object = ((ModuleAwareClassDescriptor)object).getUnsubstitutedMemberScope(kotlinTypeRefiner)) == null) {
                MemberScope memberScope2 = $this$getRefinedUnsubstitutedMemberScopeIfPossible.getUnsubstitutedMemberScope();
                object = memberScope2;
                Intrinsics.checkNotNullExpressionValue(memberScope2, "this.unsubstitutedMemberScope");
            }
            return object;
        }

        @NotNull
        public final MemberScope getRefinedMemberScopeIfPossible$descriptors(@NotNull ClassDescriptor $this$getRefinedMemberScopeIfPossible, @NotNull TypeSubstitution typeSubstitution, @NotNull KotlinTypeRefiner kotlinTypeRefiner) {
            Object object;
            Intrinsics.checkNotNullParameter($this$getRefinedMemberScopeIfPossible, "$this$getRefinedMemberScopeIfPossible");
            Intrinsics.checkNotNullParameter(typeSubstitution, "typeSubstitution");
            Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
            ClassDescriptor classDescriptor = $this$getRefinedMemberScopeIfPossible;
            if (!(classDescriptor instanceof ModuleAwareClassDescriptor)) {
                classDescriptor = null;
            }
            if ((object = (ModuleAwareClassDescriptor)classDescriptor) == null || (object = ((ModuleAwareClassDescriptor)object).getMemberScope(typeSubstitution, kotlinTypeRefiner)) == null) {
                MemberScope memberScope2 = $this$getRefinedMemberScopeIfPossible.getMemberScope(typeSubstitution);
                object = memberScope2;
                Intrinsics.checkNotNullExpressionValue(memberScope2, "this.getMemberScope(\n   \u2026ubstitution\n            )");
            }
            return object;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

