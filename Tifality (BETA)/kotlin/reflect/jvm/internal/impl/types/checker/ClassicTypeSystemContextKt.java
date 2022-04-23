/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.ClassicTypeSystemContextKt$WhenMappings;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;
import org.jetbrains.annotations.NotNull;

public final class ClassicTypeSystemContextKt {
    @NotNull
    public static final TypeVariance convertVariance(@NotNull Variance $this$convertVariance) {
        TypeVariance typeVariance;
        Intrinsics.checkNotNullParameter((Object)$this$convertVariance, "$this$convertVariance");
        switch (ClassicTypeSystemContextKt$WhenMappings.$EnumSwitchMapping$1[$this$convertVariance.ordinal()]) {
            case 1: {
                typeVariance = TypeVariance.INV;
                break;
            }
            case 2: {
                typeVariance = TypeVariance.IN;
                break;
            }
            case 3: {
                typeVariance = TypeVariance.OUT;
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return typeVariance;
    }
}

