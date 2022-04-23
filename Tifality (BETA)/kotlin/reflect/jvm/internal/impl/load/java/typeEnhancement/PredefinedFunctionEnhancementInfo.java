/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnhancementInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PredefinedFunctionEnhancementInfo {
    @Nullable
    private final TypeEnhancementInfo returnTypeInfo;
    @NotNull
    private final List<TypeEnhancementInfo> parametersInfo;

    @Nullable
    public final TypeEnhancementInfo getReturnTypeInfo() {
        return this.returnTypeInfo;
    }

    @NotNull
    public final List<TypeEnhancementInfo> getParametersInfo() {
        return this.parametersInfo;
    }

    public PredefinedFunctionEnhancementInfo(@Nullable TypeEnhancementInfo returnTypeInfo, @NotNull List<TypeEnhancementInfo> parametersInfo) {
        Intrinsics.checkNotNullParameter(parametersInfo, "parametersInfo");
        this.returnTypeInfo = returnTypeInfo;
        this.parametersInfo = parametersInfo;
    }

    public /* synthetic */ PredefinedFunctionEnhancementInfo(TypeEnhancementInfo typeEnhancementInfo, List list, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            typeEnhancementInfo = null;
        }
        if ((n & 2) != 0) {
            list = CollectionsKt.emptyList();
        }
        this(typeEnhancementInfo, list);
    }

    public PredefinedFunctionEnhancementInfo() {
        this(null, null, 3, null);
    }
}

