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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.components.DescriptorResolverUtils;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.UtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.ClassDeclaredMemberIndex;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticScope;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.utils.DFS;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LazyJavaStaticClassScope
extends LazyJavaStaticScope {
    private final JavaClass jClass;
    @NotNull
    private final LazyJavaClassDescriptor ownerDescriptor;

    @Override
    @NotNull
    protected ClassDeclaredMemberIndex computeMemberIndex() {
        return new ClassDeclaredMemberIndex(this.jClass, computeMemberIndex.1.INSTANCE);
    }

    @Override
    @NotNull
    protected Set<Name> computeFunctionNames(@NotNull DescriptorKindFilter kindFilter, @Nullable Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Set<Name> set = CollectionsKt.toMutableSet((Iterable)((DeclaredMemberIndex)this.getDeclaredMemberIndex().invoke()).getMethodNames());
        boolean bl = false;
        boolean bl2 = false;
        Set<Name> $this$apply = set;
        boolean bl3 = false;
        LazyJavaStaticClassScope lazyJavaStaticClassScope = UtilKt.getParentJavaStaticClassScope(this.getOwnerDescriptor());
        Set<Name> set2 = lazyJavaStaticClassScope != null ? lazyJavaStaticClassScope.getFunctionNames() : null;
        boolean bl4 = false;
        Set<Name> set3 = set2;
        if (set3 == null) {
            set3 = SetsKt.emptySet();
        }
        $this$apply.addAll((Collection)set3);
        if (this.jClass.isEnum()) {
            $this$apply.addAll((Collection<Name>)CollectionsKt.listOf(DescriptorUtils.ENUM_VALUE_OF, DescriptorUtils.ENUM_VALUES));
        }
        return set;
    }

    @Override
    @NotNull
    protected Set<Name> computePropertyNames(@NotNull DescriptorKindFilter kindFilter, @Nullable Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Set<Name> set = CollectionsKt.toMutableSet((Iterable)((DeclaredMemberIndex)this.getDeclaredMemberIndex().invoke()).getFieldNames());
        boolean bl = false;
        boolean bl2 = false;
        Set<Name> $this$apply = set;
        boolean bl3 = false;
        this.flatMapJavaStaticSupertypesScopes(this.getOwnerDescriptor(), $this$apply, computePropertyNames.1.1.INSTANCE);
        return set;
    }

    @Override
    @NotNull
    protected Set<Name> computeClassNames(@NotNull DescriptorKindFilter kindFilter, @Nullable Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        return SetsKt.emptySet();
    }

    @Override
    @Nullable
    public ClassifierDescriptor getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return null;
    }

    @Override
    protected void computeNonDeclaredFunctions(@NotNull Collection<SimpleFunctionDescriptor> result2, @NotNull Name name) {
        block0: {
            Name name2;
            block1: {
                Intrinsics.checkNotNullParameter(result2, "result");
                Intrinsics.checkNotNullParameter(name, "name");
                Set<SimpleFunctionDescriptor> functionsFromSupertypes = this.getStaticFunctionsFromJavaSuperClasses(name, this.getOwnerDescriptor());
                Collection<SimpleFunctionDescriptor> collection = DescriptorResolverUtils.resolveOverridesForStaticMembers(name, (Collection)functionsFromSupertypes, result2, this.getOwnerDescriptor(), this.getC().getComponents().getErrorReporter(), this.getC().getComponents().getKotlinTypeChecker().getOverridingUtil());
                Intrinsics.checkNotNullExpressionValue(collection, "resolveOverridesForStati\u2026.overridingUtil\n        )");
                result2.addAll(collection);
                if (!this.jClass.isEnum()) break block0;
                name2 = name;
                if (!Intrinsics.areEqual(name2, DescriptorUtils.ENUM_VALUE_OF)) break block1;
                SimpleFunctionDescriptor simpleFunctionDescriptor = DescriptorFactory.createEnumValueOfMethod(this.getOwnerDescriptor());
                Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptor, "createEnumValueOfMethod(ownerDescriptor)");
                result2.add(simpleFunctionDescriptor);
                break block0;
            }
            if (!Intrinsics.areEqual(name2, DescriptorUtils.ENUM_VALUES)) break block0;
            SimpleFunctionDescriptor simpleFunctionDescriptor = DescriptorFactory.createEnumValuesMethod(this.getOwnerDescriptor());
            Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptor, "createEnumValuesMethod(ownerDescriptor)");
            result2.add(simpleFunctionDescriptor);
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    protected void computeNonDeclaredProperties(@NotNull Name name, @NotNull Collection<PropertyDescriptor> result2) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(result2, "result");
        boolean bl = false;
        Set propertiesFromSupertypes2 = this.flatMapJavaStaticSupertypesScopes(this.getOwnerDescriptor(), new LinkedHashSet(), (Function1)new Function1<MemberScope, Collection<? extends PropertyDescriptor>>(name){
            final /* synthetic */ Name $name;

            @NotNull
            public final Collection<? extends PropertyDescriptor> invoke(@NotNull MemberScope it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getContributedVariables(this.$name, NoLookupLocation.WHEN_GET_SUPER_MEMBERS);
            }
            {
                this.$name = name;
                super(1);
            }
        });
        Iterable<PropertyDescriptor> iterable = result2;
        boolean bl2 = false;
        if (!iterable.isEmpty()) {
            Collection<PropertyDescriptor> collection = DescriptorResolverUtils.resolveOverridesForStaticMembers(name, propertiesFromSupertypes2, result2, this.getOwnerDescriptor(), this.getC().getComponents().getErrorReporter(), this.getC().getComponents().getKotlinTypeChecker().getOverridingUtil());
            Intrinsics.checkNotNullExpressionValue(collection, "resolveOverridesForStati\u2026ingUtil\n                )");
            result2.addAll(collection);
        } else {
            void $this$flatMapTo$iv$iv;
            void $this$flatMap$iv;
            void $this$groupByTo$iv$iv;
            Map $this$groupBy$iv;
            iterable = propertiesFromSupertypes2;
            Collection<PropertyDescriptor> collection = result2;
            boolean $i$f$groupBy = false;
            void var6_8 = $this$groupBy$iv;
            Object destination$iv$iv = new LinkedHashMap();
            boolean $i$f$groupByTo = false;
            for (Object element$iv$iv : $this$groupByTo$iv$iv) {
                Object object;
                PropertyDescriptor it = (PropertyDescriptor)element$iv$iv;
                boolean bl3 = false;
                PropertyDescriptor key$iv$iv = this.getRealOriginal(it);
                Map $this$getOrPut$iv$iv$iv = destination$iv$iv;
                boolean $i$f$getOrPut = false;
                Object value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
                if (value$iv$iv$iv == null) {
                    boolean bl4 = false;
                    List answer$iv$iv$iv = new ArrayList();
                    $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                    object = answer$iv$iv$iv;
                } else {
                    object = value$iv$iv$iv;
                }
                List list$iv$iv = (List)object;
                list$iv$iv.add(element$iv$iv);
            }
            Object object = destination$iv$iv;
            $this$groupBy$iv = object;
            boolean $i$f$flatMap = false;
            $this$groupByTo$iv$iv = $this$flatMap$iv;
            destination$iv$iv = new ArrayList();
            boolean $i$f$flatMapTo = false;
            Iterator iterator2 = $this$flatMapTo$iv$iv;
            boolean bl5 = false;
            Iterator iterator3 = iterator2.entrySet().iterator();
            while (iterator3.hasNext()) {
                Map.Entry element$iv$iv;
                Map.Entry it = element$iv$iv = iterator3.next();
                boolean bl6 = false;
                Iterable list$iv$iv = DescriptorResolverUtils.resolveOverridesForStaticMembers(name, (Collection)it.getValue(), result2, this.getOwnerDescriptor(), this.getC().getComponents().getErrorReporter(), this.getC().getComponents().getKotlinTypeChecker().getOverridingUtil());
                CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
            }
            object = (List)destination$iv$iv;
            collection.addAll((Collection)object);
        }
    }

    private final Set<SimpleFunctionDescriptor> getStaticFunctionsFromJavaSuperClasses(Name name, ClassDescriptor descriptor2) {
        LazyJavaStaticClassScope lazyJavaStaticClassScope = UtilKt.getParentJavaStaticClassScope(descriptor2);
        if (lazyJavaStaticClassScope == null) {
            return SetsKt.emptySet();
        }
        LazyJavaStaticClassScope staticScope = lazyJavaStaticClassScope;
        return CollectionsKt.toSet((Iterable)staticScope.getContributedFunctions(name, NoLookupLocation.WHEN_GET_SUPER_MEMBERS));
    }

    private final <R> Set<R> flatMapJavaStaticSupertypesScopes(ClassDescriptor root, Set<R> result2, Function1<? super MemberScope, ? extends Collection<? extends R>> onJavaStaticScope) {
        DFS.dfs((Collection)CollectionsKt.listOf(root), flatMapJavaStaticSupertypesScopes.1.INSTANCE, new DFS.AbstractNodeHandler<ClassDescriptor, Unit>(root, result2, onJavaStaticScope){
            final /* synthetic */ ClassDescriptor $root;
            final /* synthetic */ Set $result;
            final /* synthetic */ Function1 $onJavaStaticScope;

            public boolean beforeChildren(@NotNull ClassDescriptor current) {
                Intrinsics.checkNotNullParameter(current, "current");
                if (current == this.$root) {
                    return true;
                }
                MemberScope memberScope2 = current.getStaticScope();
                Intrinsics.checkNotNullExpressionValue(memberScope2, "current.staticScope");
                MemberScope staticScope = memberScope2;
                if (staticScope instanceof LazyJavaStaticScope) {
                    this.$result.addAll((Collection)this.$onJavaStaticScope.invoke(staticScope));
                    return false;
                }
                return true;
            }

            public void result() {
            }
            {
                this.$root = $captured_local_variable$0;
                this.$result = $captured_local_variable$1;
                this.$onJavaStaticScope = $captured_local_variable$2;
            }
        });
        return result2;
    }

    /*
     * WARNING - void declaration
     */
    private final PropertyDescriptor getRealOriginal(PropertyDescriptor $this$realOriginal) {
        void $this$mapTo$iv$iv;
        CallableMemberDescriptor.Kind kind = $this$realOriginal.getKind();
        Intrinsics.checkNotNullExpressionValue((Object)kind, "this.kind");
        if (kind.isReal()) {
            return $this$realOriginal;
        }
        Collection<? extends PropertyDescriptor> collection = $this$realOriginal.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(collection, "this.overriddenDescriptors");
        Iterable $this$map$iv = collection;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor)item$iv$iv;
            Collection collection2 = destination$iv$iv;
            boolean bl = false;
            void v2 = it;
            Intrinsics.checkNotNullExpressionValue(v2, "it");
            PropertyDescriptor propertyDescriptor2 = this.getRealOriginal((PropertyDescriptor)v2);
            collection2.add(propertyDescriptor2);
        }
        return (PropertyDescriptor)CollectionsKt.single(CollectionsKt.distinct((List)destination$iv$iv));
    }

    @Override
    @NotNull
    protected LazyJavaClassDescriptor getOwnerDescriptor() {
        return this.ownerDescriptor;
    }

    public LazyJavaStaticClassScope(@NotNull LazyJavaResolverContext c, @NotNull JavaClass jClass, @NotNull LazyJavaClassDescriptor ownerDescriptor) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        Intrinsics.checkNotNullParameter(ownerDescriptor, "ownerDescriptor");
        super(c);
        this.jClass = jClass;
        this.ownerDescriptor = ownerDescriptor;
    }
}

