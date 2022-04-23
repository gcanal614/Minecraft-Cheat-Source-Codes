/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.model.FlexibleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext;
import org.jetbrains.annotations.NotNull;

public final class AbstractStrictEqualityTypeChecker {
    public static final AbstractStrictEqualityTypeChecker INSTANCE;

    public final boolean strictEqualTypes(@NotNull TypeSystemContext context, @NotNull KotlinTypeMarker a2, @NotNull KotlinTypeMarker b2) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(a2, "a");
        Intrinsics.checkNotNullParameter(b2, "b");
        return this.strictEqualTypesInternal(context, a2, b2);
    }

    private final boolean strictEqualTypesInternal(TypeSystemContext $this$strictEqualTypesInternal, KotlinTypeMarker a2, KotlinTypeMarker b2) {
        if (a2 == b2) {
            return true;
        }
        SimpleTypeMarker simpleA = $this$strictEqualTypesInternal.asSimpleType(a2);
        SimpleTypeMarker simpleB = $this$strictEqualTypesInternal.asSimpleType(b2);
        if (simpleA != null && simpleB != null) {
            return this.strictEqualSimpleTypes($this$strictEqualTypesInternal, simpleA, simpleB);
        }
        FlexibleTypeMarker flexibleA = $this$strictEqualTypesInternal.asFlexibleType(a2);
        FlexibleTypeMarker flexibleB = $this$strictEqualTypesInternal.asFlexibleType(b2);
        if (flexibleA != null && flexibleB != null) {
            return this.strictEqualSimpleTypes($this$strictEqualTypesInternal, $this$strictEqualTypesInternal.lowerBound(flexibleA), $this$strictEqualTypesInternal.lowerBound(flexibleB)) && this.strictEqualSimpleTypes($this$strictEqualTypesInternal, $this$strictEqualTypesInternal.upperBound(flexibleA), $this$strictEqualTypesInternal.upperBound(flexibleB));
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    private final boolean strictEqualSimpleTypes(TypeSystemContext $this$strictEqualSimpleTypes, SimpleTypeMarker a2, SimpleTypeMarker b2) {
        if ($this$strictEqualSimpleTypes.argumentsCount(a2) != $this$strictEqualSimpleTypes.argumentsCount(b2) || $this$strictEqualSimpleTypes.isMarkedNullable(a2) != $this$strictEqualSimpleTypes.isMarkedNullable(b2) || $this$strictEqualSimpleTypes.asDefinitelyNotNullType(a2) == null != ($this$strictEqualSimpleTypes.asDefinitelyNotNullType(b2) == null) || !$this$strictEqualSimpleTypes.isEqualTypeConstructors($this$strictEqualSimpleTypes.typeConstructor(a2), $this$strictEqualSimpleTypes.typeConstructor(b2))) {
            return false;
        }
        if ($this$strictEqualSimpleTypes.identicalArguments(a2, b2)) {
            return true;
        }
        int n = 0;
        int n2 = $this$strictEqualSimpleTypes.argumentsCount(a2);
        while (n < n2) {
            void i;
            TypeArgumentMarker aArg = $this$strictEqualSimpleTypes.getArgument(a2, (int)i);
            TypeArgumentMarker bArg = $this$strictEqualSimpleTypes.getArgument(b2, (int)i);
            if ($this$strictEqualSimpleTypes.isStarProjection(aArg) != $this$strictEqualSimpleTypes.isStarProjection(bArg)) {
                return false;
            }
            if (!$this$strictEqualSimpleTypes.isStarProjection(aArg)) {
                if ($this$strictEqualSimpleTypes.getVariance(aArg) != $this$strictEqualSimpleTypes.getVariance(bArg)) {
                    return false;
                }
                if (!this.strictEqualTypesInternal($this$strictEqualSimpleTypes, $this$strictEqualSimpleTypes.getType(aArg), $this$strictEqualSimpleTypes.getType(bArg))) {
                    return false;
                }
            }
            ++i;
        }
        return true;
    }

    private AbstractStrictEqualityTypeChecker() {
    }

    static {
        AbstractStrictEqualityTypeChecker abstractStrictEqualityTypeChecker;
        INSTANCE = abstractStrictEqualityTypeChecker = new AbstractStrictEqualityTypeChecker();
    }
}

