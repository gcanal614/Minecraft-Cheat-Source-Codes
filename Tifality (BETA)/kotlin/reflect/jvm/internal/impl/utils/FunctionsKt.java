/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.reflect.jvm.internal.impl.utils.FunctionsKt;
import org.jetbrains.annotations.NotNull;

public final class FunctionsKt {
    private static final Function1<Object, Object> IDENTITY = IDENTITY.1.INSTANCE;
    private static final Function1<Object, Boolean> ALWAYS_TRUE = ALWAYS_TRUE.1.INSTANCE;
    private static final Function1<Object, Object> ALWAYS_NULL = ALWAYS_NULL.1.INSTANCE;
    @NotNull
    private static final Function1<Object, Unit> DO_NOTHING = DO_NOTHING.1.INSTANCE;
    @NotNull
    private static final Function2<Object, Object, Unit> DO_NOTHING_2 = DO_NOTHING_2.1.INSTANCE;
    @NotNull
    private static final Function3<Object, Object, Object, Unit> DO_NOTHING_3 = DO_NOTHING_3.1.INSTANCE;

    @NotNull
    public static final <T> Function1<T, Boolean> alwaysTrue() {
        return ALWAYS_TRUE;
    }

    @NotNull
    public static final Function3<Object, Object, Object, Unit> getDO_NOTHING_3() {
        return DO_NOTHING_3;
    }
}

