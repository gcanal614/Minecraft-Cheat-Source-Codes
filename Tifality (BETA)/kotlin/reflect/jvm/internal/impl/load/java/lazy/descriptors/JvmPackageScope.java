/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.UtilsKt;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaPackageFragment;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaPackageScope;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPackage;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeKt;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.util.collectionUtils.ScopeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JvmPackageScope
implements MemberScope {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    @NotNull
    private final LazyJavaPackageScope javaScope;
    private final NotNullLazyValue kotlinScopes$delegate;
    private final LazyJavaResolverContext c;
    private final LazyJavaPackageFragment packageFragment;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmPackageScope.class), "kotlinScopes", "getKotlinScopes()[Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;"))};
    }

    @NotNull
    public final LazyJavaPackageScope getJavaScope$descriptors_jvm() {
        return this.javaScope;
    }

    private final MemberScope[] getKotlinScopes() {
        return (MemberScope[])StorageKt.getValue(this.kotlinScopes$delegate, (Object)this, $$delegatedProperties[0]);
    }

    @Override
    @Nullable
    public ClassifierDescriptor getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        ClassifierDescriptor classifierDescriptor;
        block3: {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            this.recordLookup(name, location);
            ClassDescriptor javaClassifier = this.javaScope.getContributedClassifier(name, location);
            if (javaClassifier != null) {
                return javaClassifier;
            }
            MemberScope[] scopes$iv = this.getKotlinScopes();
            boolean $i$f$getFirstClassifierDiscriminateHeaders = false;
            ClassifierDescriptor result$iv = null;
            MemberScope[] memberScopeArray = scopes$iv;
            int n = memberScopeArray.length;
            for (int i = 0; i < n; ++i) {
                MemberScope scope$iv;
                MemberScope it = scope$iv = memberScopeArray[i];
                boolean bl = false;
                ClassifierDescriptor newResult$iv = it.getContributedClassifier(name, location);
                if (newResult$iv == null) continue;
                if (newResult$iv instanceof ClassifierDescriptorWithTypeParameters && ((ClassifierDescriptorWithTypeParameters)newResult$iv).isExpect()) {
                    if (result$iv != null) continue;
                    result$iv = newResult$iv;
                    continue;
                }
                classifierDescriptor = newResult$iv;
                break block3;
            }
            classifierDescriptor = result$iv;
        }
        return classifierDescriptor;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public Collection<PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
        void firstScope$iv;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        this.recordLookup(name, location);
        LazyJavaPackageScope lazyJavaPackageScope = this.javaScope;
        MemberScope[] restScopes$iv = this.getKotlinScopes();
        boolean $i$f$getFromAllScopes = false;
        MemberScope it = (MemberScope)firstScope$iv;
        boolean bl = false;
        Collection<? extends PropertyDescriptor> result$iv = it.getContributedVariables(name, location);
        MemberScope[] memberScopeArray = restScopes$iv;
        int n = memberScopeArray.length;
        for (int i = 0; i < n; ++i) {
            void it2;
            MemberScope scope$iv;
            MemberScope memberScope2 = scope$iv = memberScopeArray[i];
            Collection<? extends PropertyDescriptor> collection = result$iv;
            $i$a$-getFromAllScopes-JvmPackageScope$getContributedVariables$1 = false;
            Collection<? extends PropertyDescriptor> collection2 = it2.getContributedVariables(name, location);
            result$iv = ScopeUtilsKt.concat(collection, collection2);
        }
        Collection<? extends PropertyDescriptor> collection = result$iv;
        if (collection == null) {
            collection = SetsKt.emptySet();
        }
        return collection;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
        void firstScope$iv;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        this.recordLookup(name, location);
        LazyJavaPackageScope lazyJavaPackageScope = this.javaScope;
        MemberScope[] restScopes$iv = this.getKotlinScopes();
        boolean $i$f$getFromAllScopes = false;
        MemberScope it = (MemberScope)firstScope$iv;
        boolean bl = false;
        Collection<? extends SimpleFunctionDescriptor> result$iv = it.getContributedFunctions(name, location);
        MemberScope[] memberScopeArray = restScopes$iv;
        int n = memberScopeArray.length;
        for (int i = 0; i < n; ++i) {
            void it2;
            MemberScope scope$iv;
            MemberScope memberScope2 = scope$iv = memberScopeArray[i];
            Collection<? extends SimpleFunctionDescriptor> collection = result$iv;
            $i$a$-getFromAllScopes-JvmPackageScope$getContributedFunctions$1 = false;
            Collection<? extends SimpleFunctionDescriptor> collection2 = it2.getContributedFunctions(name, location);
            result$iv = ScopeUtilsKt.concat(collection, collection2);
        }
        Collection<? extends SimpleFunctionDescriptor> collection = result$iv;
        if (collection == null) {
            collection = SetsKt.emptySet();
        }
        return collection;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        void firstScope$iv;
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        LazyJavaPackageScope lazyJavaPackageScope = this.javaScope;
        MemberScope[] restScopes$iv = this.getKotlinScopes();
        boolean $i$f$getFromAllScopes = false;
        MemberScope it = (MemberScope)firstScope$iv;
        boolean bl = false;
        Collection<DeclarationDescriptor> result$iv = it.getContributedDescriptors(kindFilter, nameFilter);
        MemberScope[] memberScopeArray = restScopes$iv;
        int n = memberScopeArray.length;
        for (int i = 0; i < n; ++i) {
            void it2;
            MemberScope scope$iv;
            MemberScope memberScope2 = scope$iv = memberScopeArray[i];
            Collection<DeclarationDescriptor> collection = result$iv;
            $i$a$-getFromAllScopes-JvmPackageScope$getContributedDescriptors$1 = false;
            Collection<DeclarationDescriptor> collection2 = it2.getContributedDescriptors(kindFilter, nameFilter);
            result$iv = ScopeUtilsKt.concat(collection, collection2);
        }
        Collection<DeclarationDescriptor> collection = result$iv;
        if (collection == null) {
            collection = SetsKt.emptySet();
        }
        return collection;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Set<Name> getFunctionNames() {
        void var2_3;
        void $this$flatMapTo$iv;
        MemberScope[] memberScopeArray = this.getKotlinScopes();
        boolean bl = false;
        Collection destination$iv = new LinkedHashSet();
        boolean $i$f$flatMapTo = false;
        void var4_6 = $this$flatMapTo$iv;
        int n = ((void)var4_6).length;
        for (int i = 0; i < n; ++i) {
            void element$iv;
            void it = element$iv = var4_6[i];
            boolean bl2 = false;
            Iterable list$iv = it.getFunctionNames();
            CollectionsKt.addAll(destination$iv, list$iv);
        }
        memberScopeArray = var2_3;
        boolean bl3 = false;
        boolean bl4 = false;
        Set $this$apply = (Set)memberScopeArray;
        boolean bl5 = false;
        $this$apply.addAll((Collection)this.javaScope.getFunctionNames());
        return (Set)memberScopeArray;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Set<Name> getVariableNames() {
        void var2_3;
        void $this$flatMapTo$iv;
        MemberScope[] memberScopeArray = this.getKotlinScopes();
        boolean bl = false;
        Collection destination$iv = new LinkedHashSet();
        boolean $i$f$flatMapTo = false;
        void var4_6 = $this$flatMapTo$iv;
        int n = ((void)var4_6).length;
        for (int i = 0; i < n; ++i) {
            void element$iv;
            void it = element$iv = var4_6[i];
            boolean bl2 = false;
            Iterable list$iv = it.getVariableNames();
            CollectionsKt.addAll(destination$iv, list$iv);
        }
        memberScopeArray = var2_3;
        boolean bl3 = false;
        boolean bl4 = false;
        Set $this$apply = (Set)memberScopeArray;
        boolean bl5 = false;
        $this$apply.addAll((Collection)this.javaScope.getVariableNames());
        return (Set)memberScopeArray;
    }

    @Override
    @Nullable
    public Set<Name> getClassifierNames() {
        Set<Name> set;
        Set<Name> set2 = MemberScopeKt.flatMapClassifierNamesOrNull(ArraysKt.asIterable(this.getKotlinScopes()));
        if (set2 != null) {
            Set<Name> set3 = set2;
            boolean bl = false;
            boolean bl2 = false;
            Set<Name> $this$apply = set3;
            boolean bl3 = false;
            $this$apply.addAll((Collection<Name>)this.javaScope.getClassifierNames());
            set = set3;
        } else {
            set = null;
        }
        return set;
    }

    @Override
    public void recordLookup(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        UtilsKt.record(this.c.getComponents().getLookupTracker(), location, this.packageFragment, name);
    }

    public JvmPackageScope(@NotNull LazyJavaResolverContext c, @NotNull JavaPackage jPackage, @NotNull LazyJavaPackageFragment packageFragment) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(jPackage, "jPackage");
        Intrinsics.checkNotNullParameter(packageFragment, "packageFragment");
        this.c = c;
        this.packageFragment = packageFragment;
        this.javaScope = new LazyJavaPackageScope(this.c, jPackage, this.packageFragment);
        this.kotlinScopes$delegate = this.c.getStorageManager().createLazyValue((Function0)new Function0<MemberScope[]>(this){
            final /* synthetic */ JvmPackageScope this$0;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final MemberScope[] invoke() {
                void $this$mapNotNullTo$iv$iv;
                Iterable $this$mapNotNull$iv = JvmPackageScope.access$getPackageFragment$p(this.this$0).getBinaryClasses$descriptors_jvm().values();
                boolean $i$f$mapNotNull = false;
                Iterable iterable = $this$mapNotNull$iv;
                Collection destination$iv$iv = new ArrayList<E>();
                boolean $i$f$mapNotNullTo = false;
                void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                boolean $i$f$forEach = false;
                Iterator<T> iterator2 = $this$forEach$iv$iv$iv.iterator();
                while (iterator2.hasNext()) {
                    MemberScope memberScope2;
                    T element$iv$iv$iv;
                    T element$iv$iv = element$iv$iv$iv = iterator2.next();
                    boolean bl = false;
                    KotlinJvmBinaryClass partClass = (KotlinJvmBinaryClass)element$iv$iv;
                    boolean bl2 = false;
                    if (JvmPackageScope.access$getC$p(this.this$0).getComponents().getDeserializedDescriptorResolver().createKotlinPackagePartScope(JvmPackageScope.access$getPackageFragment$p(this.this$0), partClass) == null) continue;
                    boolean bl3 = false;
                    boolean bl4 = false;
                    MemberScope it$iv$iv = memberScope2;
                    boolean bl5 = false;
                    destination$iv$iv.add(it$iv$iv);
                }
                Collection $this$toTypedArray$iv = ScopeUtilsKt.listOfNonEmptyScopes((List)destination$iv$iv);
                boolean $i$f$toTypedArray = false;
                Collection thisCollection$iv = $this$toTypedArray$iv;
                MemberScope[] memberScopeArray = thisCollection$iv.toArray(new MemberScope[0]);
                if (memberScopeArray == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
                }
                return memberScopeArray;
            }
            {
                this.this$0 = jvmPackageScope;
                super(0);
            }
        });
    }

    public static final /* synthetic */ LazyJavaPackageFragment access$getPackageFragment$p(JvmPackageScope $this) {
        return $this.packageFragment;
    }

    public static final /* synthetic */ LazyJavaResolverContext access$getC$p(JvmPackageScope $this) {
        return $this.c;
    }
}

