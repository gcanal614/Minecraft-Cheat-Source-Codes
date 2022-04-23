/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.AbstractNullabilityChecker;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.checker.SimpleClassicTypeSystemContext;
import org.jetbrains.annotations.NotNull;

public final class NullabilityChecker {
    public static final NullabilityChecker INSTANCE;

    public final boolean isSubtypeOfAny(@NotNull UnwrappedType type2) {
        Intrinsics.checkNotNullParameter(type2, "type");
        return AbstractNullabilityChecker.INSTANCE.hasNotNullSupertype(SimpleClassicTypeSystemContext.INSTANCE.newBaseTypeCheckerContext(false, true), FlexibleTypesKt.lowerIfFlexible(type2), AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE);
    }

    private NullabilityChecker() {
    }

    static {
        NullabilityChecker nullabilityChecker;
        INSTANCE = nullabilityChecker = new NullabilityChecker();
    }
}

