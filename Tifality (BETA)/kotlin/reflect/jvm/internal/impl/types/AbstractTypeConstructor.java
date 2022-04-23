/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefinerKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractTypeConstructor
implements TypeConstructor {
    private final NotNullLazyValue<Supertypes> supertypes;

    @NotNull
    public List<KotlinType> getSupertypes() {
        return ((Supertypes)this.supertypes.invoke()).getSupertypesWithoutCycles();
    }

    @Override
    @NotNull
    public abstract ClassifierDescriptor getDeclarationDescriptor();

    @Override
    @NotNull
    public TypeConstructor refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return new ModuleViewTypeConstructor(kotlinTypeRefiner);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final Collection<KotlinType> computeNeighbours(TypeConstructor $this$computeNeighbours, boolean useCompanions) {
        Collection collection;
        List<KotlinType> list;
        TypeConstructor typeConstructor2 = $this$computeNeighbours;
        if (!(typeConstructor2 instanceof AbstractTypeConstructor)) {
            typeConstructor2 = null;
        }
        if ((list = (AbstractTypeConstructor)typeConstructor2) != null) {
            AbstractTypeConstructor abstractTypeConstructor = list;
            boolean bl = false;
            boolean bl2 = false;
            AbstractTypeConstructor abstractClassifierDescriptor = abstractTypeConstructor;
            boolean bl3 = false;
            list = CollectionsKt.plus(((Supertypes)abstractClassifierDescriptor.supertypes.invoke()).getAllSupertypes(), (Iterable)abstractClassifierDescriptor.getAdditionalNeighboursInSupertypeGraph(useCompanions));
            if (list != null) {
                collection = list;
                return collection;
            }
        }
        Collection collection2 = $this$computeNeighbours.getSupertypes();
        collection = collection2;
        Intrinsics.checkNotNullExpressionValue(collection2, "supertypes");
        return collection;
    }

    @NotNull
    protected abstract Collection<KotlinType> computeSupertypes();

    @NotNull
    protected abstract SupertypeLoopChecker getSupertypeLoopChecker();

    protected void reportSupertypeLoopError(@NotNull KotlinType type2) {
        Intrinsics.checkNotNullParameter(type2, "type");
    }

    protected void reportScopesLoopError(@NotNull KotlinType type2) {
        Intrinsics.checkNotNullParameter(type2, "type");
    }

    @NotNull
    protected Collection<KotlinType> getAdditionalNeighboursInSupertypeGraph(boolean useCompanions) {
        return CollectionsKt.emptyList();
    }

    @Nullable
    protected KotlinType defaultSupertypeIfEmpty() {
        return null;
    }

    public AbstractTypeConstructor(@NotNull StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.supertypes = storageManager.createLazyValueWithPostCompute((Function0)new Function0<Supertypes>(this){
            final /* synthetic */ AbstractTypeConstructor this$0;

            @NotNull
            public final Supertypes invoke() {
                return new Supertypes(this.this$0.computeSupertypes());
            }
            {
                this.this$0 = abstractTypeConstructor;
                super(0);
            }
        }, supertypes.2.INSTANCE, (Function1)new Function1<Supertypes, Unit>(this){
            final /* synthetic */ AbstractTypeConstructor this$0;

            public final void invoke(@NotNull Supertypes supertypes2) {
                List<T> list;
                Intrinsics.checkNotNullParameter(supertypes2, "supertypes");
                Collection resultWithoutCycles2 = this.this$0.getSupertypeLoopChecker().findLoopsInSupertypesAndDisconnect(this.this$0, supertypes2.getAllSupertypes(), (Function1<? super TypeConstructor, ? extends Iterable<? extends KotlinType>>)new Function1<TypeConstructor, Iterable<? extends KotlinType>>(this){
                    final /* synthetic */ supertypes.3 this$0;

                    @NotNull
                    public final Iterable<KotlinType> invoke(@NotNull TypeConstructor it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return AbstractTypeConstructor.access$computeNeighbours(this.this$0.this$0, it, false);
                    }
                    {
                        this.this$0 = var1_1;
                        super(1);
                    }
                }, (Function1<? super KotlinType, Unit>)new Function1<KotlinType, Unit>(this){
                    final /* synthetic */ supertypes.3 this$0;

                    public final void invoke(@NotNull KotlinType it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        this.this$0.this$0.reportSupertypeLoopError(it);
                    }
                    {
                        this.this$0 = var1_1;
                        super(1);
                    }
                });
                if (resultWithoutCycles2.isEmpty()) {
                    List<KotlinType> list2;
                    boolean bl;
                    KotlinType kotlinType;
                    KotlinType kotlinType2 = this.this$0.defaultSupertypeIfEmpty();
                    if (kotlinType2 != null) {
                        kotlinType = kotlinType2;
                        bl = false;
                        boolean bl2 = false;
                        KotlinType it = kotlinType;
                        boolean bl3 = false;
                        list2 = CollectionsKt.listOf(it);
                    } else {
                        list2 = null;
                    }
                    kotlinType = list2;
                    bl = false;
                    Object object = kotlinType;
                    if (object == null) {
                        object = CollectionsKt.emptyList();
                    }
                    resultWithoutCycles2 = (Collection)object;
                }
                this.this$0.getSupertypeLoopChecker().findLoopsInSupertypesAndDisconnect(this.this$0, resultWithoutCycles2, (Function1<? super TypeConstructor, ? extends Iterable<? extends KotlinType>>)new Function1<TypeConstructor, Iterable<? extends KotlinType>>(this){
                    final /* synthetic */ supertypes.3 this$0;

                    @NotNull
                    public final Iterable<KotlinType> invoke(@NotNull TypeConstructor it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return AbstractTypeConstructor.access$computeNeighbours(this.this$0.this$0, it, true);
                    }
                    {
                        this.this$0 = var1_1;
                        super(1);
                    }
                }, (Function1<? super KotlinType, Unit>)new Function1<KotlinType, Unit>(this){
                    final /* synthetic */ supertypes.3 this$0;

                    public final void invoke(@NotNull KotlinType it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        this.this$0.this$0.reportScopesLoopError(it);
                    }
                    {
                        this.this$0 = var1_1;
                        super(1);
                    }
                });
                Collection collection = resultWithoutCycles2;
                if (!(collection instanceof List)) {
                    collection = null;
                }
                if ((list = (List<T>)collection) == null) {
                    list = CollectionsKt.toList(resultWithoutCycles2);
                }
                supertypes2.setSupertypesWithoutCycles((List<? extends KotlinType>)list);
            }
            {
                this.this$0 = abstractTypeConstructor;
                super(1);
            }
        });
    }

    public static final /* synthetic */ Collection access$computeNeighbours(AbstractTypeConstructor $this, TypeConstructor $this$access_u24computeNeighbours, boolean useCompanions) {
        return $this.computeNeighbours($this$access_u24computeNeighbours, useCompanions);
    }

    private final class ModuleViewTypeConstructor
    implements TypeConstructor {
        private final Lazy refinedSupertypes$delegate;
        private final KotlinTypeRefiner kotlinTypeRefiner;

        private final List<KotlinType> getRefinedSupertypes() {
            Lazy lazy = this.refinedSupertypes$delegate;
            ModuleViewTypeConstructor moduleViewTypeConstructor = this;
            Object var3_3 = null;
            boolean bl = false;
            return (List)lazy.getValue();
        }

        @Override
        @NotNull
        public List<TypeParameterDescriptor> getParameters() {
            List<TypeParameterDescriptor> list = AbstractTypeConstructor.this.getParameters();
            Intrinsics.checkNotNullExpressionValue(list, "this@AbstractTypeConstructor.parameters");
            return list;
        }

        @NotNull
        public List<KotlinType> getSupertypes() {
            return this.getRefinedSupertypes();
        }

        @Override
        public boolean isDenotable() {
            return AbstractTypeConstructor.this.isDenotable();
        }

        @Override
        @NotNull
        public ClassifierDescriptor getDeclarationDescriptor() {
            return AbstractTypeConstructor.this.getDeclarationDescriptor();
        }

        @Override
        @NotNull
        public KotlinBuiltIns getBuiltIns() {
            KotlinBuiltIns kotlinBuiltIns = AbstractTypeConstructor.this.getBuiltIns();
            Intrinsics.checkNotNullExpressionValue(kotlinBuiltIns, "this@AbstractTypeConstructor.builtIns");
            return kotlinBuiltIns;
        }

        @Override
        @NotNull
        public TypeConstructor refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
            Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
            return AbstractTypeConstructor.this.refine(kotlinTypeRefiner);
        }

        public boolean equals(@Nullable Object other) {
            return AbstractTypeConstructor.this.equals(other);
        }

        public int hashCode() {
            return AbstractTypeConstructor.this.hashCode();
        }

        @NotNull
        public String toString() {
            return AbstractTypeConstructor.this.toString();
        }

        public ModuleViewTypeConstructor(KotlinTypeRefiner kotlinTypeRefiner) {
            Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
            this.kotlinTypeRefiner = kotlinTypeRefiner;
            this.refinedSupertypes$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0)new Function0<List<? extends KotlinType>>(this){
                final /* synthetic */ ModuleViewTypeConstructor this$0;

                @NotNull
                public final List<KotlinType> invoke() {
                    return KotlinTypeRefinerKt.refineTypes(ModuleViewTypeConstructor.access$getKotlinTypeRefiner$p(this.this$0), this.this$0.AbstractTypeConstructor.this.getSupertypes());
                }
                {
                    this.this$0 = moduleViewTypeConstructor;
                    super(0);
                }
            });
        }

        public static final /* synthetic */ KotlinTypeRefiner access$getKotlinTypeRefiner$p(ModuleViewTypeConstructor $this) {
            return $this.kotlinTypeRefiner;
        }
    }

    private static final class Supertypes {
        @NotNull
        private List<? extends KotlinType> supertypesWithoutCycles;
        @NotNull
        private final Collection<KotlinType> allSupertypes;

        @NotNull
        public final List<KotlinType> getSupertypesWithoutCycles() {
            return this.supertypesWithoutCycles;
        }

        public final void setSupertypesWithoutCycles(@NotNull List<? extends KotlinType> list) {
            Intrinsics.checkNotNullParameter(list, "<set-?>");
            this.supertypesWithoutCycles = list;
        }

        @NotNull
        public final Collection<KotlinType> getAllSupertypes() {
            return this.allSupertypes;
        }

        public Supertypes(@NotNull Collection<? extends KotlinType> allSupertypes2) {
            Intrinsics.checkNotNullParameter(allSupertypes2, "allSupertypes");
            this.allSupertypes = allSupertypes2;
            this.supertypesWithoutCycles = CollectionsKt.listOf(ErrorUtils.ERROR_TYPE_FOR_LOOP_IN_SUPERTYPES);
        }
    }
}

