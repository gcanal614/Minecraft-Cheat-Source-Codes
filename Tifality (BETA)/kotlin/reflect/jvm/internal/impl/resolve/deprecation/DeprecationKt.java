/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve.deprecation;

import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import org.jetbrains.annotations.NotNull;

public final class DeprecationKt {
    @NotNull
    private static final CallableDescriptor.UserDataKey<Object> DEPRECATED_FUNCTION_KEY = new CallableDescriptor.UserDataKey<Object>(){};

    @NotNull
    public static final CallableDescriptor.UserDataKey<Object> getDEPRECATED_FUNCTION_KEY() {
        return DEPRECATED_FUNCTION_KEY;
    }
}

