/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.checker.Ref;
import org.jetbrains.annotations.NotNull;

public final class KotlinTypeRefinerKt {
    @NotNull
    private static final ModuleDescriptor.Capability<Ref<KotlinTypeRefiner>> REFINER_CAPABILITY = new ModuleDescriptor.Capability("KotlinTypeRefiner");

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final List<KotlinType> refineTypes(@NotNull KotlinTypeRefiner $this$refineTypes, @NotNull Iterable<? extends KotlinType> types) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$refineTypes, "$this$refineTypes");
        Intrinsics.checkNotNullParameter(types, "types");
        Iterable<? extends KotlinType> $this$map$iv = types;
        boolean $i$f$map = false;
        Iterable<? extends KotlinType> iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            KotlinType kotlinType = (KotlinType)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            KotlinType kotlinType2 = $this$refineTypes.refineType((KotlinType)it);
            collection.add(kotlinType2);
        }
        return (List)destination$iv$iv;
    }

    @NotNull
    public static final ModuleDescriptor.Capability<Ref<KotlinTypeRefiner>> getREFINER_CAPABILITY() {
        return REFINER_CAPABILITY;
    }
}

