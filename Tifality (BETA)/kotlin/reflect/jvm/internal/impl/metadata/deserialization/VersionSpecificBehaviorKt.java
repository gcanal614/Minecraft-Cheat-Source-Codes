/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import org.jetbrains.annotations.NotNull;

public final class VersionSpecificBehaviorKt {
    public static final boolean isVersionRequirementTableWrittenCorrectly(@NotNull BinaryVersion version) {
        Intrinsics.checkNotNullParameter(version, "version");
        return VersionSpecificBehaviorKt.isKotlin1Dot4OrLater(version);
    }

    public static final boolean isKotlin1Dot4OrLater(@NotNull BinaryVersion version) {
        Intrinsics.checkNotNullParameter(version, "version");
        return version.getMajor() == 1 && version.getMinor() >= 4;
    }
}

