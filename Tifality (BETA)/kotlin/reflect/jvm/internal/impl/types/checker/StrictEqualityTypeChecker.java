/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.AbstractStrictEqualityTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.checker.SimpleClassicTypeSystemContext;
import org.jetbrains.annotations.NotNull;

public final class StrictEqualityTypeChecker {
    public static final StrictEqualityTypeChecker INSTANCE;

    public final boolean strictEqualTypes(@NotNull UnwrappedType a2, @NotNull UnwrappedType b2) {
        Intrinsics.checkNotNullParameter(a2, "a");
        Intrinsics.checkNotNullParameter(b2, "b");
        return AbstractStrictEqualityTypeChecker.INSTANCE.strictEqualTypes(SimpleClassicTypeSystemContext.INSTANCE, a2, b2);
    }

    private StrictEqualityTypeChecker() {
    }

    static {
        StrictEqualityTypeChecker strictEqualityTypeChecker;
        INSTANCE = strictEqualityTypeChecker = new StrictEqualityTypeChecker();
    }
}

