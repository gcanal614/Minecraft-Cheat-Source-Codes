/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.incremental.components;

import kotlin.reflect.jvm.internal.impl.incremental.components.LocationInfo;
import org.jetbrains.annotations.Nullable;

public interface LookupLocation {
    @Nullable
    public LocationInfo getLocation();
}

