/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;

public final class VersionRequirement$Companion$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        $EnumSwitchMapping$0 = new int[ProtoBuf.VersionRequirement.Level.values().length];
        VersionRequirement$Companion$WhenMappings.$EnumSwitchMapping$0[ProtoBuf.VersionRequirement.Level.WARNING.ordinal()] = 1;
        VersionRequirement$Companion$WhenMappings.$EnumSwitchMapping$0[ProtoBuf.VersionRequirement.Level.ERROR.ordinal()] = 2;
        VersionRequirement$Companion$WhenMappings.$EnumSwitchMapping$0[ProtoBuf.VersionRequirement.Level.HIDDEN.ordinal()] = 3;
    }
}

