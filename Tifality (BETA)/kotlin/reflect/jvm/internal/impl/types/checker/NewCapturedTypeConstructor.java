/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NewCapturedTypeConstructor
implements CapturedTypeConstructor {
    private final Lazy _supertypes$delegate;
    @NotNull
    private final TypeProjection projection;
    private Function0<? extends List<? extends UnwrappedType>> supertypesComputation;
    private final NewCapturedTypeConstructor original;
    @Nullable
    private final TypeParameterDescriptor typeParameter;

    private final List<UnwrappedType> get_supertypes() {
        Lazy lazy = this._supertypes$delegate;
        NewCapturedTypeConstructor newCapturedTypeConstructor = this;
        Object var3_3 = null;
        boolean bl = false;
        return (List)lazy.getValue();
    }

    public final void initializeSupertypes(@NotNull List<? extends UnwrappedType> supertypes2) {
        Intrinsics.checkNotNullParameter(supertypes2, "supertypes");
        boolean bl = this.supertypesComputation == null;
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl3 = false;
            String string = "Already initialized! oldValue = " + this.supertypesComputation + ", newValue = " + supertypes2;
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        this.supertypesComputation = new Function0<List<? extends UnwrappedType>>(supertypes2){
            final /* synthetic */ List $supertypes;

            @NotNull
            public final List<UnwrappedType> invoke() {
                return this.$supertypes;
            }
            {
                this.$supertypes = list;
                super(0);
            }
        };
    }

    @NotNull
    public List<UnwrappedType> getSupertypes() {
        List<UnwrappedType> list = this.get_supertypes();
        if (list == null) {
            list = CollectionsKt.emptyList();
        }
        return list;
    }

    @Override
    @NotNull
    public List<TypeParameterDescriptor> getParameters() {
        return CollectionsKt.emptyList();
    }

    @Override
    public boolean isDenotable() {
        return false;
    }

    @Override
    @Nullable
    public ClassifierDescriptor getDeclarationDescriptor() {
        return null;
    }

    @Override
    @NotNull
    public KotlinBuiltIns getBuiltIns() {
        KotlinType kotlinType = this.getProjection().getType();
        Intrinsics.checkNotNullExpressionValue(kotlinType, "projection.type");
        return TypeUtilsKt.getBuiltIns(kotlinType);
    }

    @Override
    @NotNull
    public NewCapturedTypeConstructor refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        NewCapturedTypeConstructor newCapturedTypeConstructor;
        Function0 function0;
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        TypeProjection typeProjection = this.getProjection().refine(kotlinTypeRefiner);
        TypeProjection typeProjection2 = typeProjection;
        Intrinsics.checkNotNullExpressionValue(typeProjection, "projection.refine(kotlinTypeRefiner)");
        Function0<? extends List<? extends UnwrappedType>> function02 = this.supertypesComputation;
        if (function02 != null) {
            Function0<? extends List<? extends UnwrappedType>> function03 = function02;
            boolean bl = false;
            boolean bl2 = false;
            Function0<? extends List<? extends UnwrappedType>> function04 = function03;
            TypeProjection typeProjection3 = typeProjection2;
            boolean bl3 = false;
            Function0 function05 = new Function0<List<? extends UnwrappedType>>(this, kotlinTypeRefiner){
                final /* synthetic */ NewCapturedTypeConstructor this$0;
                final /* synthetic */ KotlinTypeRefiner $kotlinTypeRefiner$inlined;
                {
                    this.this$0 = newCapturedTypeConstructor;
                    this.$kotlinTypeRefiner$inlined = kotlinTypeRefiner;
                    super(0);
                }

                /*
                 * WARNING - void declaration
                 */
                @NotNull
                public final List<UnwrappedType> invoke() {
                    void $this$mapTo$iv$iv;
                    Iterable $this$map$iv = this.this$0.getSupertypes();
                    boolean $i$f$map = false;
                    Iterable iterable = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        void it;
                        UnwrappedType unwrappedType = (UnwrappedType)item$iv$iv;
                        Collection collection = destination$iv$iv;
                        boolean bl = false;
                        UnwrappedType unwrappedType2 = it.refine(this.$kotlinTypeRefiner$inlined);
                        collection.add(unwrappedType2);
                    }
                    return (List)destination$iv$iv;
                }
            };
            typeProjection2 = typeProjection3;
            function0 = function05;
        } else {
            function0 = null;
        }
        if ((newCapturedTypeConstructor = this.original) == null) {
            newCapturedTypeConstructor = this;
        }
        TypeParameterDescriptor typeParameterDescriptor = this.typeParameter;
        NewCapturedTypeConstructor newCapturedTypeConstructor2 = newCapturedTypeConstructor;
        Function0 function06 = function0;
        TypeProjection typeProjection4 = typeProjection2;
        return new NewCapturedTypeConstructor(typeProjection4, function06, newCapturedTypeConstructor2, typeParameterDescriptor);
    }

    public boolean equals(@Nullable Object other) {
        NewCapturedTypeConstructor newCapturedTypeConstructor;
        if (this == other) {
            return true;
        }
        Object object = other;
        if (Intrinsics.areEqual(this.getClass(), object != null ? object.getClass() : null) ^ true) {
            return false;
        }
        Object object2 = other;
        if (object2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.checker.NewCapturedTypeConstructor");
        }
        NewCapturedTypeConstructor cfr_ignored_0 = (NewCapturedTypeConstructor)object2;
        NewCapturedTypeConstructor newCapturedTypeConstructor2 = this.original;
        if (newCapturedTypeConstructor2 == null) {
            newCapturedTypeConstructor2 = this;
        }
        if ((newCapturedTypeConstructor = ((NewCapturedTypeConstructor)other).original) == null) {
            newCapturedTypeConstructor = (NewCapturedTypeConstructor)other;
        }
        return newCapturedTypeConstructor2 == newCapturedTypeConstructor;
    }

    public int hashCode() {
        NewCapturedTypeConstructor newCapturedTypeConstructor = this.original;
        return newCapturedTypeConstructor != null ? newCapturedTypeConstructor.hashCode() : super.hashCode();
    }

    @NotNull
    public String toString() {
        return "CapturedType(" + this.getProjection() + ')';
    }

    @Override
    @NotNull
    public TypeProjection getProjection() {
        return this.projection;
    }

    public NewCapturedTypeConstructor(@NotNull TypeProjection projection, @Nullable Function0<? extends List<? extends UnwrappedType>> supertypesComputation, @Nullable NewCapturedTypeConstructor original, @Nullable TypeParameterDescriptor typeParameter) {
        Intrinsics.checkNotNullParameter(projection, "projection");
        this.projection = projection;
        this.supertypesComputation = supertypesComputation;
        this.original = original;
        this.typeParameter = typeParameter;
        this._supertypes$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0)new Function0<List<? extends UnwrappedType>>(this){
            final /* synthetic */ NewCapturedTypeConstructor this$0;

            @Nullable
            public final List<UnwrappedType> invoke() {
                Function0 function0 = NewCapturedTypeConstructor.access$getSupertypesComputation$p(this.this$0);
                return function0 != null ? (List<E>)function0.invoke() : null;
            }
            {
                this.this$0 = newCapturedTypeConstructor;
                super(0);
            }
        });
    }

    public /* synthetic */ NewCapturedTypeConstructor(TypeProjection typeProjection, Function0 function0, NewCapturedTypeConstructor newCapturedTypeConstructor, TypeParameterDescriptor typeParameterDescriptor, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            function0 = null;
        }
        if ((n & 4) != 0) {
            newCapturedTypeConstructor = null;
        }
        if ((n & 8) != 0) {
            typeParameterDescriptor = null;
        }
        this(typeProjection, function0, newCapturedTypeConstructor, typeParameterDescriptor);
    }

    public NewCapturedTypeConstructor(@NotNull TypeProjection projection, final @NotNull List<? extends UnwrappedType> supertypes2, @Nullable NewCapturedTypeConstructor original) {
        Intrinsics.checkNotNullParameter(projection, "projection");
        Intrinsics.checkNotNullParameter(supertypes2, "supertypes");
        this(projection, new Function0<List<? extends UnwrappedType>>(){

            @Override
            @NotNull
            public final List<UnwrappedType> invoke() {
                return supertypes2;
            }
        }, original, null, 8, null);
    }

    public /* synthetic */ NewCapturedTypeConstructor(TypeProjection typeProjection, List list, NewCapturedTypeConstructor newCapturedTypeConstructor, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            newCapturedTypeConstructor = null;
        }
        this(typeProjection, list, newCapturedTypeConstructor);
    }

    public static final /* synthetic */ Function0 access$getSupertypesComputation$p(NewCapturedTypeConstructor $this) {
        return $this.supertypesComputation;
    }
}

