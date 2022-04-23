/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve.calls.inference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedType;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructor;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.DelegatedTypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.IndexedParametersSubstitution;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.LazyWrappedType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CapturedTypeConstructorKt {
    @NotNull
    public static final KotlinType createCapturedType(@NotNull TypeProjection typeProjection) {
        Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
        return new CapturedType(typeProjection, null, false, null, 14, null);
    }

    public static final boolean isCaptured(@NotNull KotlinType $this$isCaptured) {
        Intrinsics.checkNotNullParameter($this$isCaptured, "$this$isCaptured");
        return $this$isCaptured.getConstructor() instanceof CapturedTypeConstructor;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final TypeSubstitution wrapWithCapturingSubstitution(@NotNull TypeSubstitution $this$wrapWithCapturingSubstitution, boolean needApproximation) {
        TypeSubstitution typeSubstitution;
        Intrinsics.checkNotNullParameter($this$wrapWithCapturingSubstitution, "$this$wrapWithCapturingSubstitution");
        if ($this$wrapWithCapturingSubstitution instanceof IndexedParametersSubstitution) {
            Collection<TypeProjection> collection;
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            Iterable iterable = ArraysKt.zip(((IndexedParametersSubstitution)$this$wrapWithCapturingSubstitution).getArguments(), ((IndexedParametersSubstitution)$this$wrapWithCapturingSubstitution).getParameters());
            TypeParameterDescriptor[] typeParameterDescriptorArray = ((IndexedParametersSubstitution)$this$wrapWithCapturingSubstitution).getParameters();
            boolean $i$f$map = false;
            void var4_5 = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                Pair pair = (Pair)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl = false;
                TypeProjection typeProjection = CapturedTypeConstructorKt.createCapturedIfNeeded((TypeProjection)it.getFirst(), (TypeParameterDescriptor)it.getSecond());
                collection.add(typeProjection);
            }
            collection = (List)destination$iv$iv;
            Collection $this$toTypedArray$iv = collection;
            boolean $i$f$toTypedArray = false;
            Collection thisCollection$iv = $this$toTypedArray$iv;
            TypeProjection[] typeProjectionArray = thisCollection$iv.toArray(new TypeProjection[0]);
            if (typeProjectionArray == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            boolean bl = needApproximation;
            TypeProjection[] typeProjectionArray2 = typeProjectionArray;
            TypeParameterDescriptor[] typeParameterDescriptorArray2 = typeParameterDescriptorArray;
            typeSubstitution = new IndexedParametersSubstitution(typeParameterDescriptorArray2, typeProjectionArray2, bl);
        } else {
            typeSubstitution = new DelegatedTypeSubstitution($this$wrapWithCapturingSubstitution, needApproximation, $this$wrapWithCapturingSubstitution){
                final /* synthetic */ TypeSubstitution $this_wrapWithCapturingSubstitution;
                final /* synthetic */ boolean $needApproximation;

                public boolean approximateContravariantCapturedTypes() {
                    return this.$needApproximation;
                }

                @Nullable
                public TypeProjection get(@NotNull KotlinType key) {
                    TypeProjection typeProjection;
                    Intrinsics.checkNotNullParameter(key, "key");
                    TypeProjection typeProjection2 = super.get(key);
                    if (typeProjection2 != null) {
                        ClassifierDescriptor classifierDescriptor = key.getConstructor().getDeclarationDescriptor();
                        if (!(classifierDescriptor instanceof TypeParameterDescriptor)) {
                            classifierDescriptor = null;
                        }
                        typeProjection = CapturedTypeConstructorKt.access$createCapturedIfNeeded(typeProjection2, (TypeParameterDescriptor)classifierDescriptor);
                    } else {
                        typeProjection = null;
                    }
                    return typeProjection;
                }
                {
                    this.$this_wrapWithCapturingSubstitution = $receiver;
                    this.$needApproximation = $captured_local_variable$1;
                    super($super_call_param$2);
                }
            };
        }
        return typeSubstitution;
    }

    public static /* synthetic */ TypeSubstitution wrapWithCapturingSubstitution$default(TypeSubstitution typeSubstitution, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            bl = true;
        }
        return CapturedTypeConstructorKt.wrapWithCapturingSubstitution(typeSubstitution, bl);
    }

    private static final TypeProjection createCapturedIfNeeded(TypeProjection $this$createCapturedIfNeeded, TypeParameterDescriptor typeParameterDescriptor) {
        if (typeParameterDescriptor == null || $this$createCapturedIfNeeded.getProjectionKind() == Variance.INVARIANT) {
            return $this$createCapturedIfNeeded;
        }
        if (typeParameterDescriptor.getVariance() == $this$createCapturedIfNeeded.getProjectionKind()) {
            TypeProjectionImpl typeProjectionImpl;
            if ($this$createCapturedIfNeeded.isStarProjection()) {
                StorageManager storageManager = LockBasedStorageManager.NO_LOCKS;
                Intrinsics.checkNotNullExpressionValue(storageManager, "LockBasedStorageManager.NO_LOCKS");
                typeProjectionImpl = new TypeProjectionImpl(new LazyWrappedType(storageManager, (Function0<? extends KotlinType>)new Function0<KotlinType>($this$createCapturedIfNeeded){
                    final /* synthetic */ TypeProjection $this_createCapturedIfNeeded;

                    @NotNull
                    public final KotlinType invoke() {
                        KotlinType kotlinType = this.$this_createCapturedIfNeeded.getType();
                        Intrinsics.checkNotNullExpressionValue(kotlinType, "this@createCapturedIfNeeded.type");
                        return kotlinType;
                    }
                    {
                        this.$this_createCapturedIfNeeded = typeProjection;
                        super(0);
                    }
                }));
            } else {
                typeProjectionImpl = new TypeProjectionImpl($this$createCapturedIfNeeded.getType());
            }
            return typeProjectionImpl;
        }
        return new TypeProjectionImpl(CapturedTypeConstructorKt.createCapturedType($this$createCapturedIfNeeded));
    }

    public static final /* synthetic */ TypeProjection access$createCapturedIfNeeded(TypeProjection $this$access_u24createCapturedIfNeeded, TypeParameterDescriptor typeParameterDescriptor) {
        return CapturedTypeConstructorKt.createCapturedIfNeeded($this$access_u24createCapturedIfNeeded, typeParameterDescriptor);
    }
}

