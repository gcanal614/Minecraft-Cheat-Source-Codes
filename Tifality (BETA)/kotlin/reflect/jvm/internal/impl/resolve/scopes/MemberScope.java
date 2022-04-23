/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.Collection;
import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MemberScope
extends ResolutionScope {
    public static final Companion Companion = kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope$Companion.$$INSTANCE;

    @NotNull
    public Collection<? extends PropertyDescriptor> getContributedVariables(@NotNull Name var1, @NotNull LookupLocation var2);

    @NotNull
    public Collection<? extends SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name var1, @NotNull LookupLocation var2);

    @NotNull
    public Set<Name> getFunctionNames();

    @NotNull
    public Set<Name> getVariableNames();

    @Nullable
    public Set<Name> getClassifierNames();

    public static final class Empty
    extends MemberScopeImpl {
        public static final Empty INSTANCE;

        @Override
        @NotNull
        public Set<Name> getFunctionNames() {
            return SetsKt.emptySet();
        }

        @Override
        @NotNull
        public Set<Name> getVariableNames() {
            return SetsKt.emptySet();
        }

        @Override
        @NotNull
        public Set<Name> getClassifierNames() {
            return SetsKt.emptySet();
        }

        private Empty() {
        }

        static {
            Empty empty;
            INSTANCE = empty = new Empty();
        }
    }

    public static final class DefaultImpls {
        public static void recordLookup(@NotNull MemberScope $this, @NotNull Name name, @NotNull LookupLocation location) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            ResolutionScope.DefaultImpls.recordLookup($this, name, location);
        }
    }

    public static final class Companion {
        @NotNull
        private static final Function1<Name, Boolean> ALL_NAME_FILTER;
        static final /* synthetic */ Companion $$INSTANCE;

        @NotNull
        public final Function1<Name, Boolean> getALL_NAME_FILTER() {
            return ALL_NAME_FILTER;
        }

        private Companion() {
        }

        static {
            Companion companion;
            $$INSTANCE = companion = new Companion();
            ALL_NAME_FILTER = ALL_NAME_FILTER.1.INSTANCE;
        }
    }
}

