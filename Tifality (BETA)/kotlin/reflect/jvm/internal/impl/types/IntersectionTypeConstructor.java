/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.TypeIntersectionScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class IntersectionTypeConstructor
implements TypeConstructor {
    private KotlinType alternative;
    private final LinkedHashSet<KotlinType> intersectedTypes;
    private final int hashCode;

    @Override
    @NotNull
    public List<TypeParameterDescriptor> getParameters() {
        return CollectionsKt.emptyList();
    }

    @Override
    @NotNull
    public Collection<KotlinType> getSupertypes() {
        return this.intersectedTypes;
    }

    @NotNull
    public final MemberScope createScopeForKotlinType() {
        return TypeIntersectionScope.Companion.create("member scope for intersection type", (Collection<? extends KotlinType>)this.intersectedTypes);
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
        KotlinBuiltIns kotlinBuiltIns = ((KotlinType)this.intersectedTypes.iterator().next()).getConstructor().getBuiltIns();
        Intrinsics.checkNotNullExpressionValue(kotlinBuiltIns, "intersectedTypes.iterato\u2026xt().constructor.builtIns");
        return kotlinBuiltIns;
    }

    @NotNull
    public String toString() {
        return this.makeDebugNameForIntersectionType((Iterable<? extends KotlinType>)this.intersectedTypes);
    }

    private final String makeDebugNameForIntersectionType(Iterable<? extends KotlinType> resultingTypes) {
        Iterable<? extends KotlinType> $this$sortedBy$iv = resultingTypes;
        boolean $i$f$sortedBy = false;
        boolean bl = false;
        return CollectionsKt.joinToString$default(CollectionsKt.sortedWith($this$sortedBy$iv, new Comparator<T>(){

            public final int compare(T a2, T b2) {
                boolean bl = false;
                KotlinType it = (KotlinType)a2;
                boolean bl2 = false;
                Comparable comparable = (Comparable)((Object)it.toString());
                it = (KotlinType)b2;
                Comparable comparable2 = comparable;
                bl2 = false;
                String string = it.toString();
                return ComparisonsKt.compareValues(comparable2, (Comparable)((Object)string));
            }
        }), " & ", "{", "}", 0, null, null, 56, null);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IntersectionTypeConstructor)) {
            return false;
        }
        return Intrinsics.areEqual(this.intersectedTypes, ((IntersectionTypeConstructor)other).intersectedTypes);
    }

    @NotNull
    public final SimpleType createType() {
        boolean bl = false;
        return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(Annotations.Companion.getEMPTY(), this, CollectionsKt.emptyList(), false, this.createScopeForKotlinType(), (Function1<? super KotlinTypeRefiner, ? extends SimpleType>)new Function1<KotlinTypeRefiner, SimpleType>(this){
            final /* synthetic */ IntersectionTypeConstructor this$0;

            @Nullable
            public final SimpleType invoke(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
                Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
                return this.this$0.refine(kotlinTypeRefiner).createType();
            }
            {
                this.this$0 = intersectionTypeConstructor;
                super(1);
            }
        });
    }

    public int hashCode() {
        return this.hashCode;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public IntersectionTypeConstructor refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        IntersectionTypeConstructor intersectionTypeConstructor;
        IntersectionTypeConstructor intersectionTypeConstructor2;
        void $this$mapTo$iv$iv$iv;
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        IntersectionTypeConstructor $this$transformComponents$iv = this;
        boolean $i$f$transformComponents = false;
        boolean changed$iv = false;
        Iterable $this$map$iv$iv = $this$transformComponents$iv.getSupertypes();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv$iv;
        Collection destination$iv$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv$iv : $this$mapTo$iv$iv$iv) {
            KotlinType kotlinType;
            void it$iv;
            KotlinType kotlinType2 = (KotlinType)item$iv$iv$iv;
            Collection collection = destination$iv$iv$iv;
            boolean bl = false;
            void it = it$iv;
            if (true) {
                changed$iv = true;
                it = it$iv;
                boolean bl2 = false;
                kotlinType = it.refine(kotlinTypeRefiner);
            } else {
                kotlinType = it$iv;
            }
            void var16_20 = kotlinType;
            collection.add(var16_20);
        }
        List newSupertypes$iv = (List)destination$iv$iv$iv;
        if (!changed$iv) {
            intersectionTypeConstructor2 = null;
        } else {
            KotlinType kotlinType;
            KotlinType kotlinType3 = $this$transformComponents$iv.getAlternativeType();
            if (kotlinType3 != null) {
                KotlinType kotlinType4 = kotlinType3;
                boolean bl = false;
                boolean bl3 = false;
                KotlinType alternative$iv = kotlinType4;
                boolean bl4 = false;
                KotlinType it = alternative$iv;
                if (true) {
                    it = alternative$iv;
                    boolean bl5 = false;
                    kotlinType = it.refine(kotlinTypeRefiner);
                } else {
                    kotlinType = alternative$iv;
                }
            } else {
                kotlinType = null;
            }
            KotlinType updatedAlternative$iv = kotlinType;
            intersectionTypeConstructor2 = intersectionTypeConstructor = new IntersectionTypeConstructor(newSupertypes$iv).setAlternative(updatedAlternative$iv);
        }
        if (intersectionTypeConstructor2 == null) {
            intersectionTypeConstructor = this;
        }
        return intersectionTypeConstructor;
    }

    @NotNull
    public final IntersectionTypeConstructor setAlternative(@Nullable KotlinType alternative) {
        return new IntersectionTypeConstructor((Collection<? extends KotlinType>)this.intersectedTypes, alternative);
    }

    @Nullable
    public final KotlinType getAlternativeType() {
        return this.alternative;
    }

    public IntersectionTypeConstructor(@NotNull Collection<? extends KotlinType> typesToIntersect) {
        Intrinsics.checkNotNullParameter(typesToIntersect, "typesToIntersect");
        boolean bl = !typesToIntersect.isEmpty();
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl3 = false;
            String string = "Attempt to create an empty intersection";
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        this.intersectedTypes = new LinkedHashSet<KotlinType>(typesToIntersect);
        this.hashCode = this.intersectedTypes.hashCode();
    }

    private IntersectionTypeConstructor(Collection<? extends KotlinType> typesToIntersect, KotlinType alternative) {
        this(typesToIntersect);
        this.alternative = alternative;
    }
}

