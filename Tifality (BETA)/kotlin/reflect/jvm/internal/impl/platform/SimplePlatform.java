/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.platform;

import kotlin.reflect.jvm.internal.impl.platform.TargetPlatformVersion;
import org.jetbrains.annotations.NotNull;

public abstract class SimplePlatform {
    @NotNull
    private final TargetPlatformVersion targetPlatformVersion;
    @NotNull
    private final String platformName;

    @NotNull
    public String toString() {
        String targetName = this.getTargetName();
        CharSequence charSequence = targetName;
        boolean bl = false;
        return charSequence.length() > 0 ? this.platformName + " (" + targetName + ')' : this.platformName;
    }

    @NotNull
    public String getTargetName() {
        return this.getTargetPlatformVersion().getDescription();
    }

    @NotNull
    public TargetPlatformVersion getTargetPlatformVersion() {
        return this.targetPlatformVersion;
    }
}

