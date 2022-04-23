/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;

public abstract class ErrorValue
extends ConstantValue<Unit> {
    public static final Companion Companion = new Companion(null);

    @Override
    @NotNull
    public Unit getValue() {
        throw (Throwable)new UnsupportedOperationException();
    }

    public ErrorValue() {
        super(Unit.INSTANCE);
    }

    public static final class ErrorValueWithMessage
    extends ErrorValue {
        @NotNull
        private final String message;

        @Override
        @NotNull
        public SimpleType getType(@NotNull ModuleDescriptor module) {
            Intrinsics.checkNotNullParameter(module, "module");
            SimpleType simpleType2 = ErrorUtils.createErrorType(this.message);
            Intrinsics.checkNotNullExpressionValue(simpleType2, "ErrorUtils.createErrorType(message)");
            return simpleType2;
        }

        @Override
        @NotNull
        public String toString() {
            return this.message;
        }

        public ErrorValueWithMessage(@NotNull String message) {
            Intrinsics.checkNotNullParameter(message, "message");
            this.message = message;
        }
    }

    public static final class Companion {
        @NotNull
        public final ErrorValue create(@NotNull String message) {
            Intrinsics.checkNotNullParameter(message, "message");
            return new ErrorValueWithMessage(message);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

