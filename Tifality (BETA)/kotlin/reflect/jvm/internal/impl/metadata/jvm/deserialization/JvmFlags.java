/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;

public final class JvmFlags {
    private static final Flags.BooleanFlagField IS_MOVED_FROM_INTERFACE_COMPANION;
    private static final Flags.BooleanFlagField ARE_INTERFACE_METHOD_BODIES_INSIDE;
    private static final Flags.BooleanFlagField IS_ALL_COMPATIBILITY_MODE;
    public static final JvmFlags INSTANCE;

    public final Flags.BooleanFlagField getIS_MOVED_FROM_INTERFACE_COMPANION() {
        return IS_MOVED_FROM_INTERFACE_COMPANION;
    }

    private JvmFlags() {
    }

    static {
        JvmFlags jvmFlags;
        INSTANCE = jvmFlags = new JvmFlags();
        IS_MOVED_FROM_INTERFACE_COMPANION = Flags.FlagField.booleanFirst();
        ARE_INTERFACE_METHOD_BODIES_INSIDE = Flags.FlagField.booleanFirst();
        IS_ALL_COMPATIBILITY_MODE = Flags.FlagField.booleanAfter(ARE_INTERFACE_METHOD_BODIES_INSIDE);
    }
}

