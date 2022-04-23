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

public final class JvmMetadataVersion
extends BinaryVersion {
    private final boolean isStrictSemantics;
    @JvmField
    @NotNull
    public static final JvmMetadataVersion INSTANCE;
    @JvmField
    @NotNull
    public static final JvmMetadataVersion INVALID_VERSION;
    public static final Companion Companion;

    public boolean isCompatible() {
        return (this.getMajor() != 1 || this.getMinor() != 0) && (this.isStrictSemantics ? this.isCompatibleTo(INSTANCE) : this.getMajor() == INSTANCE.getMajor() && this.getMinor() <= INSTANCE.getMinor() + 1);
    }

    public JvmMetadataVersion(@NotNull int[] versionArray, boolean isStrictSemantics) {
        Intrinsics.checkNotNullParameter(versionArray, "versionArray");
        super(Arrays.copyOf(versionArray, versionArray.length));
        this.isStrictSemantics = isStrictSemantics;
    }

    public JvmMetadataVersion(int ... numbers) {
        Intrinsics.checkNotNullParameter(numbers, "numbers");
        this(numbers, false);
    }

    static {
        Companion = new Companion(null);
        INSTANCE = new JvmMetadataVersion(1, 4, 0);
        INVALID_VERSION = new JvmMetadataVersion(new int[0]);
    }

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

