/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.Collection;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;

public final class PrimitiveTypeUtilKt {
    @NotNull
    public static final Collection<KotlinType> getAllSignedLiteralTypes(@NotNull ModuleDescriptor $this$allSignedLiteralTypes) {
        Intrinsics.checkNotNullParameter($this$allSignedLiteralTypes, "$this$allSignedLiteralTypes");
        return CollectionsKt.listOf($this$allSignedLiteralTypes.getBuiltIns().getIntType(), $this$allSignedLiteralTypes.getBuiltIns().getLongType(), $this$allSignedLiteralTypes.getBuiltIns().getByteType(), $this$allSignedLiteralTypes.getBuiltIns().getShortType());
    }
}

