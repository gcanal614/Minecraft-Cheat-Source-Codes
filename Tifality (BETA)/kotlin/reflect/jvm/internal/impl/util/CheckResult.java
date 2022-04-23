/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.util;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public abstract class CheckResult {
    private final boolean isSuccess;

    public final boolean isSuccess() {
        return this.isSuccess;
    }

    private CheckResult(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public /* synthetic */ CheckResult(boolean isSuccess, DefaultConstructorMarker $constructor_marker) {
        this(isSuccess);
    }

    public static final class IllegalSignature
    extends CheckResult {
        @NotNull
        private final String error;

        public IllegalSignature(@NotNull String error) {
            Intrinsics.checkNotNullParameter(error, "error");
            super(false, null);
            this.error = error;
        }
    }

    public static final class IllegalFunctionName
    extends CheckResult {
        public static final IllegalFunctionName INSTANCE;

        private IllegalFunctionName() {
            super(false, null);
        }

        static {
            IllegalFunctionName illegalFunctionName;
            INSTANCE = illegalFunctionName = new IllegalFunctionName();
        }
    }

    public static final class SuccessCheck
    extends CheckResult {
        public static final SuccessCheck INSTANCE;

        private SuccessCheck() {
            super(true, null);
        }

        static {
            SuccessCheck successCheck;
            INSTANCE = successCheck = new SuccessCheck();
        }
    }
}

