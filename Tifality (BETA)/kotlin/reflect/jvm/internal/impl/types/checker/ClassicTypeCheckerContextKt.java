/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.jvm.internal.Reflection;

public final class ClassicTypeCheckerContextKt {
    private static final String errorMessage(Object $this$errorMessage) {
        return "ClassicTypeCheckerContext couldn't handle " + Reflection.getOrCreateKotlinClass($this$errorMessage.getClass()) + ' ' + $this$errorMessage;
    }

    public static final /* synthetic */ String access$errorMessage(Object $this$access_u24errorMessage) {
        return ClassicTypeCheckerContextKt.errorMessage($this$access_u24errorMessage);
    }
}

