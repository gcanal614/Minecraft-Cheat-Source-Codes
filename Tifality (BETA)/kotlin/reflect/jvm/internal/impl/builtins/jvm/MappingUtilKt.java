/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;

public final class MappingUtilKt {
    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final TypeConstructorSubstitution createMappedTypeParametersSubstitution(@NotNull ClassDescriptor from, @NotNull ClassDescriptor to) {
        Object object;
        TypeParameterDescriptor p1;
        Iterable iterable;
        Iterable $this$mapTo$iv$iv;
        Iterable $this$map$iv;
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        boolean bl = from.getDeclaredTypeParameters().size() == to.getDeclaredTypeParameters().size();
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean $i$a$-assert-MappingUtilKt$createMappedTypeParametersSubstitution$22 = false;
            String $i$a$-assert-MappingUtilKt$createMappedTypeParametersSubstitution$22 = from + " and " + to + " should have same number of type parameters, " + "but " + from.getDeclaredTypeParameters().size() + " / " + to.getDeclaredTypeParameters().size() + " found";
            throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-MappingUtilKt$createMappedTypeParametersSubstitution$22));
        }
        List<TypeParameterDescriptor> list = from.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(list, "from.declaredTypeParameters");
        Iterable iterable2 = list;
        TypeConstructorSubstitution.Companion companion = TypeConstructorSubstitution.Companion;
        boolean $i$f$map = false;
        void $i$a$-assert-MappingUtilKt$createMappedTypeParametersSubstitution$22 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor)item$iv$iv;
            iterable = destination$iv$iv;
            boolean bl3 = false;
            object = p1.getTypeConstructor();
            iterable.add(object);
        }
        iterable = (List)destination$iv$iv;
        Iterable iterable3 = iterable;
        List<TypeParameterDescriptor> list2 = to.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(list2, "to.declaredTypeParameters");
        $this$map$iv = list2;
        iterable = iterable3;
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            p1 = (TypeParameterDescriptor)item$iv$iv;
            object = destination$iv$iv;
            boolean bl4 = false;
            void v3 = it;
            Intrinsics.checkNotNullExpressionValue(v3, "it");
            SimpleType simpleType2 = v3.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(simpleType2, "it.defaultType");
            TypeProjection typeProjection = TypeUtilsKt.asTypeProjection(simpleType2);
            object.add(typeProjection);
        }
        object = (List)destination$iv$iv;
        return TypeConstructorSubstitution.Companion.createByConstructorsMap$default(companion, MapsKt.toMap(CollectionsKt.zip(iterable, (Iterable)object)), false, 2, null);
    }
}

