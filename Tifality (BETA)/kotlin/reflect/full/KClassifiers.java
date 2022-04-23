/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.full;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.SinceKotlin;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeProjection;
import kotlin.reflect.full.KClassifiers$WhenMappings;
import kotlin.reflect.jvm.internal.KClassifierImpl;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001b\n\u0000\u001a.\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002\u001a6\u0010\u0012\u001a\u00020\u0001*\u00020\u00022\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000eH\u0007\"\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0015"}, d2={"starProjectedType", "Lkotlin/reflect/KType;", "Lkotlin/reflect/KClassifier;", "getStarProjectedType$annotations", "(Lkotlin/reflect/KClassifier;)V", "getStarProjectedType", "(Lkotlin/reflect/KClassifier;)Lkotlin/reflect/KType;", "createKotlinType", "Lkotlin/reflect/jvm/internal/impl/types/SimpleType;", "typeAnnotations", "Lkotlin/reflect/jvm/internal/impl/descriptors/annotations/Annotations;", "typeConstructor", "Lkotlin/reflect/jvm/internal/impl/types/TypeConstructor;", "arguments", "", "Lkotlin/reflect/KTypeProjection;", "nullable", "", "createType", "annotations", "", "kotlin-reflection"})
@JvmName(name="KClassifiers")
public final class KClassifiers {
    @SinceKotlin(version="1.1")
    @NotNull
    public static final KType createType(@NotNull KClassifier $this$createType, @NotNull List<KTypeProjection> arguments2, boolean nullable, @NotNull List<? extends Annotation> annotations2) {
        Object object;
        Intrinsics.checkNotNullParameter($this$createType, "$this$createType");
        Intrinsics.checkNotNullParameter(arguments2, "arguments");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        KClassifier kClassifier = $this$createType;
        if (!(kClassifier instanceof KClassifierImpl)) {
            kClassifier = null;
        }
        if ((object = (KClassifierImpl)((Object)kClassifier)) == null || (object = object.getDescriptor()) == null) {
            throw (Throwable)new KotlinReflectionInternalError("Cannot create type for an unsupported classifier: " + $this$createType + " (" + $this$createType.getClass() + ')');
        }
        Object descriptor2 = object;
        TypeConstructor typeConstructor2 = descriptor2.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "descriptor.typeConstructor");
        TypeConstructor typeConstructor3 = typeConstructor2;
        List<TypeParameterDescriptor> list = typeConstructor3.getParameters();
        Intrinsics.checkNotNullExpressionValue(list, "typeConstructor.parameters");
        List<TypeParameterDescriptor> parameters2 = list;
        if (parameters2.size() != arguments2.size()) {
            throw (Throwable)new IllegalArgumentException("Class declares " + parameters2.size() + " type parameters, but " + arguments2.size() + " were provided.");
        }
        Annotations typeAnnotations = annotations2.isEmpty() ? Annotations.Companion.getEMPTY() : Annotations.Companion.getEMPTY();
        return new KTypeImpl(KClassifiers.createKotlinType(typeAnnotations, typeConstructor3, arguments2, nullable), null, 2, null);
    }

    public static /* synthetic */ KType createType$default(KClassifier kClassifier, List list, boolean bl, List list2, int n, Object object) {
        if ((n & 1) != 0) {
            list = CollectionsKt.emptyList();
        }
        if ((n & 2) != 0) {
            bl = false;
        }
        if ((n & 4) != 0) {
            list2 = CollectionsKt.emptyList();
        }
        return KClassifiers.createType(kClassifier, list, bl, list2);
    }

    /*
     * Unable to fully structure code
     */
    private static final SimpleType createKotlinType(Annotations typeAnnotations, TypeConstructor typeConstructor, List<KTypeProjection> arguments, boolean nullable) {
        v0 = typeConstructor.getParameters();
        Intrinsics.checkNotNullExpressionValue(v0, "typeConstructor.parameters");
        parameters = v0;
        var5_5 = arguments;
        var20_6 = typeConstructor;
        var19_7 = typeAnnotations;
        $i$f$mapIndexed = false;
        var7_9 = $this$mapIndexed$iv;
        destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
        $i$f$mapIndexedTo = false;
        index$iv$iv = 0;
        for (T item$iv$iv : $this$mapIndexedTo$iv$iv) {
            var13_15 = index$iv$iv++;
            var14_16 = false;
            if (var13_15 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            var15_17 = (KTypeProjection)item$iv$iv;
            var16_18 = var13_15;
            var21_21 = destination$iv$iv;
            $i$a$-mapIndexed-KClassifiers$createKotlinType$1 = false;
            v1 = (KTypeImpl)typeProjection.getType();
            type = v1 != null ? v1.getType() : null;
            v2 = typeProjection.getVariance();
            if (v2 == null) ** GOTO lbl41
            switch (KClassifiers$WhenMappings.$EnumSwitchMapping$0[v2.ordinal()]) {
                case 1: {
                    v3 = type;
                    Intrinsics.checkNotNull(v3);
                    v4 = new TypeProjectionImpl(Variance.INVARIANT, v3);
                    break;
                }
                case 2: {
                    v5 = type;
                    Intrinsics.checkNotNull(v5);
                    v4 = new TypeProjectionImpl(Variance.IN_VARIANCE, v5);
                    break;
                }
                case 3: {
                    v6 = type;
                    Intrinsics.checkNotNull(v6);
                    v4 = new TypeProjectionImpl(Variance.OUT_VARIANCE, v6);
                    break;
                }
lbl41:
                // 1 sources

                v7 = parameters.get((int)index);
                Intrinsics.checkNotNullExpressionValue(v7, "parameters[index]");
                v4 = new StarProjectionImpl(v7);
                break;
                default: {
                    throw new NoWhenBranchMatchedException();
                }
            }
            var22_22 = v4;
            var21_21.add(var22_22);
        }
        var21_21 = (List)destination$iv$iv;
        return KotlinTypeFactory.simpleType$default(var19_7, var20_6, (List)var21_21, nullable, null, 16, null);
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getStarProjectedType$annotations(KClassifier kClassifier) {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final KType getStarProjectedType(@NotNull KClassifier $this$starProjectedType) {
        Collection<KTypeProjection> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Object object;
        Intrinsics.checkNotNullParameter($this$starProjectedType, "$this$starProjectedType");
        KClassifier kClassifier = $this$starProjectedType;
        if (!(kClassifier instanceof KClassifierImpl)) {
            kClassifier = null;
        }
        if ((object = (KClassifierImpl)((Object)kClassifier)) == null || (object = object.getDescriptor()) == null) {
            return KClassifiers.createType$default($this$starProjectedType, null, false, null, 7, null);
        }
        Object descriptor2 = object;
        TypeConstructor typeConstructor2 = descriptor2.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "descriptor.typeConstructor");
        List<TypeParameterDescriptor> list = typeConstructor2.getParameters();
        Intrinsics.checkNotNullExpressionValue(list, "descriptor.typeConstructor.parameters");
        List<TypeParameterDescriptor> typeParameters2 = list;
        if (typeParameters2.isEmpty()) {
            return KClassifiers.createType$default($this$starProjectedType, null, false, null, 7, null);
        }
        Iterable iterable = typeParameters2;
        KClassifier kClassifier2 = $this$starProjectedType;
        boolean $i$f$map = false;
        void var5_6 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            KTypeProjection kTypeProjection = KTypeProjection.Companion.getSTAR();
            collection.add(kTypeProjection);
        }
        collection = (List)destination$iv$iv;
        return KClassifiers.createType$default(kClassifier2, (List)collection, false, null, 6, null);
    }
}

