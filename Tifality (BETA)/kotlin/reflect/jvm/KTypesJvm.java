/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm;

import java.util.List;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\"\u001c\u0010\u0000\u001a\u0006\u0012\u0002\b\u00030\u0001*\u00020\u00028@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\"\u0010\u0000\u001a\u0006\u0012\u0002\b\u00030\u0001*\u00020\u00058FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0006\u0010\u0007\u001a\u0004\b\u0003\u0010\b\u00a8\u0006\t"}, d2={"jvmErasure", "Lkotlin/reflect/KClass;", "Lkotlin/reflect/KClassifier;", "getJvmErasure", "(Lkotlin/reflect/KClassifier;)Lkotlin/reflect/KClass;", "Lkotlin/reflect/KType;", "getJvmErasure$annotations", "(Lkotlin/reflect/KType;)V", "(Lkotlin/reflect/KType;)Lkotlin/reflect/KClass;", "kotlin-reflection"})
@JvmName(name="KTypesJvm")
public final class KTypesJvm {
    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getJvmErasure$annotations(KType kType) {
    }

    @NotNull
    public static final KClass<?> getJvmErasure(@NotNull KType $this$jvmErasure) {
        Intrinsics.checkNotNullParameter($this$jvmErasure, "$this$jvmErasure");
        KClass<?> kClass = $this$jvmErasure.getClassifier();
        if (kClass == null || (kClass = KTypesJvm.getJvmErasure(kClass)) == null) {
            throw (Throwable)new KotlinReflectionInternalError("Cannot calculate JVM erasure for type: " + $this$jvmErasure);
        }
        return kClass;
    }

    @NotNull
    public static final KClass<?> getJvmErasure(@NotNull KClassifier $this$jvmErasure) {
        KClass kClass;
        Intrinsics.checkNotNullParameter($this$jvmErasure, "$this$jvmErasure");
        KClassifier kClassifier = $this$jvmErasure;
        if (kClassifier instanceof KClass) {
            kClass = (KClass)$this$jvmErasure;
        } else if (kClassifier instanceof KTypeParameter) {
            KType representativeBound;
            KType kType;
            Object v4;
            List<KType> bounds;
            block9: {
                bounds = ((KTypeParameter)$this$jvmErasure).getUpperBounds();
                Iterable $this$firstOrNull$iv = bounds;
                boolean $i$f$firstOrNull = false;
                for (Object element$iv : $this$firstOrNull$iv) {
                    ClassDescriptor classDescriptor;
                    KType it = (KType)element$iv;
                    boolean bl = false;
                    KType kType2 = it;
                    if (kType2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.reflect.jvm.internal.KTypeImpl");
                    }
                    ClassifierDescriptor classifierDescriptor = ((KTypeImpl)kType2).getType().getConstructor().getDeclarationDescriptor();
                    if (!(classifierDescriptor instanceof ClassDescriptor)) {
                        classifierDescriptor = null;
                    }
                    boolean bl2 = (classDescriptor = (ClassDescriptor)classifierDescriptor) != null && classDescriptor.getKind() != ClassKind.INTERFACE && classDescriptor.getKind() != ClassKind.ANNOTATION_CLASS;
                    if (!bl2) continue;
                    v4 = element$iv;
                    break block9;
                }
                v4 = null;
            }
            if ((kType = (KType)v4) == null) {
                kType = CollectionsKt.firstOrNull(bounds);
            }
            if ((kClass = (representativeBound = kType)) == null || (kClass = KTypesJvm.getJvmErasure((KType)((Object)kClass))) == null) {
                kClass = Reflection.getOrCreateKotlinClass(Object.class);
            }
        } else {
            throw (Throwable)new KotlinReflectionInternalError("Cannot calculate JVM erasure for type: " + $this$jvmErasure);
        }
        return kClass;
    }
}

