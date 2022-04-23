/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.NonReportingOverrideStrategy;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.utils.SmartList;
import org.jetbrains.annotations.NotNull;

public abstract class GivenFunctionsMemberScope
extends MemberScopeImpl {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private final NotNullLazyValue allDescriptors$delegate;
    @NotNull
    private final ClassDescriptor containingClass;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(GivenFunctionsMemberScope.class), "allDescriptors", "getAllDescriptors()Ljava/util/List;"))};
    }

    private final List<DeclarationDescriptor> getAllDescriptors() {
        return (List)StorageKt.getValue(this.allDescriptors$delegate, (Object)this, $$delegatedProperties[0]);
    }

    @NotNull
    protected abstract List<FunctionDescriptor> computeDeclaredFunctions();

    @Override
    @NotNull
    public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        if (!kindFilter.acceptsKinds(DescriptorKindFilter.CALLABLES.getKindMask())) {
            boolean bl = false;
            return CollectionsKt.emptyList();
        }
        return this.getAllDescriptors();
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
        void $this$filterIsInstanceAndTo$iv$iv;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        Iterable $this$filterIsInstanceAnd$iv = this.getAllDescriptors();
        boolean $i$f$filterIsInstanceAnd = false;
        Iterable iterable = $this$filterIsInstanceAnd$iv;
        Collection destination$iv$iv = new SmartList();
        boolean $i$f$filterIsInstanceAndTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceAndTo$iv$iv) {
            if (!(element$iv$iv instanceof SimpleFunctionDescriptor)) continue;
            SimpleFunctionDescriptor it = (SimpleFunctionDescriptor)element$iv$iv;
            boolean bl = false;
            if (!Intrinsics.areEqual(it.getName(), name)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public Collection<PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
        void $this$filterIsInstanceAndTo$iv$iv;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        Iterable $this$filterIsInstanceAnd$iv = this.getAllDescriptors();
        boolean $i$f$filterIsInstanceAnd = false;
        Iterable iterable = $this$filterIsInstanceAnd$iv;
        Collection destination$iv$iv = new SmartList();
        boolean $i$f$filterIsInstanceAndTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceAndTo$iv$iv) {
            if (!(element$iv$iv instanceof PropertyDescriptor)) continue;
            PropertyDescriptor it = (PropertyDescriptor)element$iv$iv;
            boolean bl = false;
            if (!Intrinsics.areEqual(it.getName(), name)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    private final List<DeclarationDescriptor> createFakeOverrides(List<? extends FunctionDescriptor> functionsFromCurrent) {
        void $this$filterIsInstanceTo$iv$iv;
        Iterable $this$flatMapTo$iv$iv;
        ArrayList result2 = new ArrayList(3);
        TypeConstructor typeConstructor2 = this.containingClass.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "containingClass.typeConstructor");
        Collection<KotlinType> collection = typeConstructor2.getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collection, "containingClass.typeConstructor.supertypes");
        Iterable $this$flatMap$iv = collection;
        boolean $i$f$flatMap = false;
        Iterable iterable = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean bl = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            KotlinType it = (KotlinType)element$iv$iv;
            boolean bl2 = false;
            Iterable list$iv$iv = ResolutionScope.DefaultImpls.getContributedDescriptors$default(it.getMemberScope(), null, null, 3, null);
            CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
        }
        Iterable $this$filterIsInstance$iv = (List)destination$iv$iv;
        boolean $i$f$filterIsInstance = false;
        $this$flatMapTo$iv$iv = $this$filterIsInstance$iv;
        destination$iv$iv = new ArrayList();
        boolean entry22 = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof CallableMemberDescriptor)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List allSuperDescriptors = (List)destination$iv$iv;
        Object $this$groupBy$iv = allSuperDescriptors;
        boolean $i$f$groupBy = false;
        Iterable iterable2 = $this$groupBy$iv;
        Map destination$iv$iv2 = new LinkedHashMap();
        boolean $i$f$groupByTo = false;
        for (Object element$iv$iv : iterable2) {
            Object object;
            CallableMemberDescriptor it = (CallableMemberDescriptor)element$iv$iv;
            boolean bl3 = false;
            Name key$iv$iv = it.getName();
            Map $this$getOrPut$iv$iv$iv = destination$iv$iv2;
            boolean $i$f$getOrPut = false;
            Object value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
            if (value$iv$iv$iv == null) {
                boolean bl2 = false;
                List answer$iv$iv$iv = new ArrayList();
                $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                object = answer$iv$iv$iv;
            } else {
                object = value$iv$iv$iv;
            }
            List list$iv$iv = (List)object;
            list$iv$iv.add(element$iv$iv);
        }
        $this$groupBy$iv = destination$iv$iv2;
        $i$f$groupBy = false;
        Iterator iterator2 = $this$groupBy$iv.entrySet().iterator();
        while (iterator2.hasNext()) {
            void $this$groupByTo$iv$iv222;
            Map.Entry entry;
            Map.Entry entry2 = entry = iterator2.next();
            boolean bl4 = false;
            $this$groupBy$iv = (Name)entry2.getKey();
            Map.Entry entry3 = entry;
            bl4 = false;
            List group = (List)entry3.getValue();
            Object $this$groupBy$iv222 = group;
            boolean $i$f$groupBy2 = false;
            Iterable element$iv$iv = $this$groupBy$iv222;
            Map destination$iv$iv322 = new LinkedHashMap();
            boolean $i$f$groupByTo222 = false;
            for (Object element$iv$iv2 : $this$groupByTo$iv$iv222) {
                Object object;
                CallableMemberDescriptor it = (CallableMemberDescriptor)element$iv$iv2;
                boolean bl3 = false;
                Boolean key$iv$iv = it instanceof FunctionDescriptor;
                Map $this$getOrPut$iv$iv$iv = destination$iv$iv322;
                boolean $i$f$getOrPut = false;
                Object value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
                if (value$iv$iv$iv == null) {
                    boolean bl42 = false;
                    List answer$iv$iv$iv = new ArrayList();
                    $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                    object = answer$iv$iv$iv;
                } else {
                    object = value$iv$iv$iv;
                }
                List list$iv$iv = (List)object;
                list$iv$iv.add(element$iv$iv2);
            }
            $this$groupBy$iv222 = destination$iv$iv322;
            $i$f$groupBy2 = false;
            for (Map.Entry entry4 : $this$groupBy$iv222.entrySet()) {
                List list;
                void isFunction;
                void name;
                Object $this$groupByTo$iv$iv222 = entry4;
                boolean destination$iv$iv322 = false;
                boolean $this$groupBy$iv222 = (Boolean)$this$groupByTo$iv$iv222.getKey();
                $this$groupByTo$iv$iv222 = entry4;
                destination$iv$iv322 = false;
                List descriptors = (List)$this$groupByTo$iv$iv222.getValue();
                OverridingUtil overridingUtil2 = OverridingUtil.DEFAULT;
                void v5 = name;
                Collection collection2 = descriptors;
                if (isFunction != false) {
                    void $this$filterTo$iv$iv;
                    void $this$filter$iv;
                    $this$groupByTo$iv$iv222 = functionsFromCurrent;
                    Collection collection3 = collection2;
                    void var25_59 = v5;
                    OverridingUtil overridingUtil3 = overridingUtil2;
                    boolean $i$f$filter = false;
                    void $i$f$groupByTo222 = $this$filter$iv;
                    Collection destination$iv$iv4 = new ArrayList();
                    boolean $i$f$filterTo = false;
                    for (Object element$iv$iv3 : $this$filterTo$iv$iv) {
                        FunctionDescriptor it = (FunctionDescriptor)element$iv$iv3;
                        boolean bl5 = false;
                        if (!Intrinsics.areEqual(it.getName(), name)) continue;
                        destination$iv$iv4.add(element$iv$iv3);
                    }
                    List list2 = (List)destination$iv$iv4;
                    overridingUtil2 = overridingUtil3;
                    v5 = var25_59;
                    collection2 = collection3;
                    list = list2;
                } else {
                    boolean bl5 = false;
                    list = CollectionsKt.emptyList();
                }
                overridingUtil2.generateOverridesInFunctionGroup((Name)v5, collection2, list, this.containingClass, new NonReportingOverrideStrategy(this, result2){
                    final /* synthetic */ GivenFunctionsMemberScope this$0;
                    final /* synthetic */ ArrayList $result;

                    public void addFakeOverride(@NotNull CallableMemberDescriptor fakeOverride) {
                        Intrinsics.checkNotNullParameter(fakeOverride, "fakeOverride");
                        OverridingUtil.resolveUnknownVisibilityForMember(fakeOverride, null);
                        this.$result.add(fakeOverride);
                    }

                    protected void conflict(@NotNull CallableMemberDescriptor fromSuper, @NotNull CallableMemberDescriptor fromCurrent) {
                        Intrinsics.checkNotNullParameter(fromSuper, "fromSuper");
                        Intrinsics.checkNotNullParameter(fromCurrent, "fromCurrent");
                        String string = "Conflict in scope of " + this.this$0.getContainingClass() + ": " + fromSuper + " vs " + fromCurrent;
                        boolean bl = false;
                        throw (Throwable)new IllegalStateException(string.toString());
                    }
                    {
                        this.this$0 = this$0;
                        this.$result = $captured_local_variable$1;
                    }
                });
            }
        }
        return kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.compact(result2);
    }

    @NotNull
    protected final ClassDescriptor getContainingClass() {
        return this.containingClass;
    }

    public GivenFunctionsMemberScope(@NotNull StorageManager storageManager, @NotNull ClassDescriptor containingClass) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        this.containingClass = containingClass;
        this.allDescriptors$delegate = storageManager.createLazyValue((Function0)new Function0<List<? extends DeclarationDescriptor>>(this){
            final /* synthetic */ GivenFunctionsMemberScope this$0;

            @NotNull
            public final List<DeclarationDescriptor> invoke() {
                List<FunctionDescriptor> fromCurrent = this.this$0.computeDeclaredFunctions();
                return CollectionsKt.plus((Collection)fromCurrent, (Iterable)GivenFunctionsMemberScope.access$createFakeOverrides(this.this$0, fromCurrent));
            }
            {
                this.this$0 = givenFunctionsMemberScope;
                super(0);
            }
        });
    }

    public static final /* synthetic */ List access$createFakeOverrides(GivenFunctionsMemberScope $this, List functionsFromCurrent) {
        return $this.createFakeOverrides(functionsFromCurrent);
    }
}

