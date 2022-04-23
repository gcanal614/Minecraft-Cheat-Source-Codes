/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.util;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.util.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ReturnsCheck
implements Check {
    @NotNull
    private final String description;
    @NotNull
    private final String name;
    @NotNull
    private final Function1<KotlinBuiltIns, KotlinType> type;

    @Override
    @NotNull
    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean check(@NotNull FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        return Intrinsics.areEqual(functionDescriptor.getReturnType(), this.type.invoke(DescriptorUtilsKt.getBuiltIns(functionDescriptor)));
    }

    private ReturnsCheck(String name, Function1<? super KotlinBuiltIns, ? extends KotlinType> type2) {
        this.name = name;
        this.type = type2;
        this.description = "must return " + this.name;
    }

    @Override
    @Nullable
    public String invoke(@NotNull FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        return Check.DefaultImpls.invoke(this, functionDescriptor);
    }

    public /* synthetic */ ReturnsCheck(String name, Function1 type2, DefaultConstructorMarker $constructor_marker) {
        this(name, type2);
    }

    public static final class ReturnsBoolean
    extends ReturnsCheck {
        public static final ReturnsBoolean INSTANCE;

        private ReturnsBoolean() {
            super("Boolean", 1.INSTANCE, null);
        }

        static {
            ReturnsBoolean returnsBoolean;
            INSTANCE = returnsBoolean = new ReturnsBoolean();
        }
    }

    public static final class ReturnsInt
    extends ReturnsCheck {
        public static final ReturnsInt INSTANCE;

        private ReturnsInt() {
            super("Int", 1.INSTANCE, null);
        }

        static {
            ReturnsInt returnsInt;
            INSTANCE = returnsInt = new ReturnsInt();
        }
    }

    public static final class ReturnsUnit
    extends ReturnsCheck {
        public static final ReturnsUnit INSTANCE;

        private ReturnsUnit() {
            super("Unit", 1.INSTANCE, null);
        }

        static {
            ReturnsUnit returnsUnit;
            INSTANCE = returnsUnit = new ReturnsUnit();
        }
    }
}

