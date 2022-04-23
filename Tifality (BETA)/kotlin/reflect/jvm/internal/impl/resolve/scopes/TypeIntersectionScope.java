/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.AbstractScopeAdapter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.ChainedMemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.TypeIntersectionScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.util.collectionUtils.ScopeUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.SmartList;
import org.jetbrains.annotations.NotNull;

public final class TypeIntersectionScope
extends AbstractScopeAdapter {
    private final String debugName;
    @NotNull
    private final MemberScope workerScope;
    public static final Companion Companion = new Companion(null);

    @Override
    @NotNull
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return OverridingUtilsKt.selectMostSpecificInEachOverridableGroup(super.getContributedFunctions(name, location), getContributedFunctions.1.INSTANCE);
    }

    @Override
    @NotNull
    public Collection<PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return OverridingUtilsKt.selectMostSpecificInEachOverridableGroup(super.getContributedVariables(name, location), getContributedVariables.1.INSTANCE);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        void callables;
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        Iterable $this$partition$iv = super.getContributedDescriptors(kindFilter, nameFilter);
        boolean $i$f$partition = false;
        ArrayList first$iv = new ArrayList();
        ArrayList second$iv = new ArrayList();
        for (Object element$iv : $this$partition$iv) {
            DeclarationDescriptor it = (DeclarationDescriptor)element$iv;
            boolean bl = false;
            if (it instanceof CallableDescriptor) {
                first$iv.add(element$iv);
                continue;
            }
            second$iv.add(element$iv);
        }
        Pair pair = new Pair(first$iv, second$iv);
        List list = pair.component1();
        List other = pair.component2();
        void v0 = callables;
        if (v0 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Collection<org.jetbrains.kotlin.descriptors.CallableDescriptor>");
        }
        return CollectionsKt.plus(OverridingUtilsKt.selectMostSpecificInEachOverridableGroup((Collection)v0, getContributedDescriptors.2.INSTANCE), (Iterable)other);
    }

    @Override
    @NotNull
    protected MemberScope getWorkerScope() {
        return this.workerScope;
    }

    private TypeIntersectionScope(String debugName, MemberScope workerScope) {
        this.debugName = debugName;
        this.workerScope = workerScope;
    }

    public /* synthetic */ TypeIntersectionScope(String debugName, MemberScope workerScope, DefaultConstructorMarker $constructor_marker) {
        this(debugName, workerScope);
    }

    @JvmStatic
    @NotNull
    public static final MemberScope create(@NotNull String message, @NotNull Collection<? extends KotlinType> types) {
        return Companion.create(message, types);
    }

    public static final class Companion {
        /*
         * WARNING - void declaration
         */
        @JvmStatic
        @NotNull
        public final MemberScope create(@NotNull String message, @NotNull Collection<? extends KotlinType> types) {
            void $this$mapTo$iv$iv;
            Intrinsics.checkNotNullParameter(message, "message");
            Intrinsics.checkNotNullParameter(types, "types");
            Iterable $this$map$iv = types;
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                KotlinType kotlinType = (KotlinType)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                MemberScope memberScope2 = it.getMemberScope();
                collection.add(memberScope2);
            }
            SmartList<MemberScope> nonEmptyScopes = ScopeUtilsKt.listOfNonEmptyScopes((List)destination$iv$iv);
            MemberScope chainedOrSingle = ChainedMemberScope.Companion.createOrSingle$descriptors(message, (List<? extends MemberScope>)nonEmptyScopes);
            if (nonEmptyScopes.size() <= 1) {
                return chainedOrSingle;
            }
            return new TypeIntersectionScope(message, chainedOrSingle, null);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

