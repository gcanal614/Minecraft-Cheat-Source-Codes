/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.util;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.util.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class MemberKindCheck
implements Check {
    @NotNull
    private final String description;

    @Override
    @NotNull
    public String getDescription() {
        return this.description;
    }

    private MemberKindCheck(String description2) {
        this.description = description2;
    }

    @Override
    @Nullable
    public String invoke(@NotNull FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        return Check.DefaultImpls.invoke(this, functionDescriptor);
    }

    public /* synthetic */ MemberKindCheck(String description2, DefaultConstructorMarker $constructor_marker) {
        this(description2);
    }

    public static final class MemberOrExtension
    extends MemberKindCheck {
        public static final MemberOrExtension INSTANCE;

        @Override
        public boolean check(@NotNull FunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            return functionDescriptor.getDispatchReceiverParameter() != null || functionDescriptor.getExtensionReceiverParameter() != null;
        }

        private MemberOrExtension() {
            super("must be a member or an extension function", null);
        }

        static {
            MemberOrExtension memberOrExtension;
            INSTANCE = memberOrExtension = new MemberOrExtension();
        }
    }

    public static final class Member
    extends MemberKindCheck {
        public static final Member INSTANCE;

        @Override
        public boolean check(@NotNull FunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            return functionDescriptor.getDispatchReceiverParameter() != null;
        }

        private Member() {
            super("must be a member function", null);
        }

        static {
            Member member;
            INSTANCE = member = new Member();
        }
    }
}

