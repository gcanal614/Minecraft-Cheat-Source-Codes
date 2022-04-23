/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.util.Arrays;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import org.jetbrains.annotations.NotNull;

public final class JvmBytecodeBinaryVersion
extends BinaryVersion {
    @JvmField
    @NotNull
    public static final JvmBytecodeBinaryVersion INSTANCE;
    @JvmField
    @NotNull
    public static final JvmBytecodeBinaryVersion INVALID_VERSION;
    public static final Companion Companion;

    public JvmBytecodeBinaryVersion(int ... numbers) {
        Intrinsics.checkNotNullParameter(numbers, "numbers");
        super(Arrays.copyOf(numbers, numbers.length));
    }

    static {
        Companion = new Companion(null);
        INSTANCE = new JvmBytecodeBinaryVersion(1, 0, 3);
        INVALID_VERSION = new JvmBytecodeBinaryVersion(new int[0]);
    }

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

