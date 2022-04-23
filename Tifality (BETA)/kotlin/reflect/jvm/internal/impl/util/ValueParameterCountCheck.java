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

public abstract class ValueParameterCountCheck
implements Check {
    @NotNull
    private final String description;

    @Override
    @NotNull
    public String getDescription() {
        return this.description;
    }

    private ValueParameterCountCheck(String description2) {
        this.description = description2;
    }

    @Override
    @Nullable
    public String invoke(@NotNull FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        return Check.DefaultImpls.invoke(this, functionDescriptor);
    }

    public /* synthetic */ ValueParameterCountCheck(String description2, DefaultConstructorMarker $constructor_marker) {
        this(description2);
    }

    public static final class NoValueParameters
    extends ValueParameterCountCheck {
        public static final NoValueParameters INSTANCE;

        @Override
        public boolean check(@NotNull FunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            return functionDescriptor.getValueParameters().isEmpty();
        }

        private NoValueParameters() {
            super("must have no value parameters", null);
        }

        static {
            NoValueParameters noValueParameters;
            INSTANCE = noValueParameters = new NoValueParameters();
        }
    }

    public static final class SingleValueParameter
    extends ValueParameterCountCheck {
        public static final SingleValueParameter INSTANCE;

        @Override
        public boolean check(@NotNull FunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            return functionDescriptor.getValueParameters().size() == 1;
        }

        private SingleValueParameter() {
            super("must have a single value parameter", null);
        }

        static {
            SingleValueParameter singleValueParameter;
            INSTANCE = singleValueParameter = new SingleValueParameter();
        }
    }

    public static final class AtLeast
    extends ValueParameterCountCheck {
        private final int n;

        @Override
        public boolean check(@NotNull FunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            return functionDescriptor.getValueParameters().size() >= this.n;
        }

        public AtLeast(int n) {
            super("must have at least " + n + " value parameter" + (n > 1 ? "s" : ""), null);
            this.n = n;
        }
    }

    public static final class Equals
    extends ValueParameterCountCheck {
        private final int n;

        @Override
        public boolean check(@NotNull FunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            return functionDescriptor.getValueParameters().size() == this.n;
        }

        public Equals(int n) {
            super("must have exactly " + n + " value parameters", null);
            this.n = n;
        }
    }
}

