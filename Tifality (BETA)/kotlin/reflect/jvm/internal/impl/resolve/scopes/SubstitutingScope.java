/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Substitutable;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SubstitutingScope
implements MemberScope {
    private final TypeSubstitutor substitutor;
    private Map<DeclarationDescriptor, DeclarationDescriptor> substitutedDescriptors;
    private final Lazy _allDescriptors$delegate;
    private final MemberScope workerScope;

    private final Collection<DeclarationDescriptor> get_allDescriptors() {
        Lazy lazy = this._allDescriptors$delegate;
        SubstitutingScope substitutingScope = this;
        Object var3_3 = null;
        boolean bl = false;
        return (Collection)lazy.getValue();
    }

    private final <D extends DeclarationDescriptor> D substitute(D descriptor2) {
        DeclarationDescriptor substituted;
        DeclarationDescriptor declarationDescriptor;
        if (this.substitutor.isEmpty()) {
            return descriptor2;
        }
        if (this.substitutedDescriptors == null) {
            this.substitutedDescriptors = new HashMap();
        }
        Map<DeclarationDescriptor, DeclarationDescriptor> map2 = this.substitutedDescriptors;
        Intrinsics.checkNotNull(map2);
        Map<DeclarationDescriptor, DeclarationDescriptor> $this$getOrPut$iv = map2;
        boolean $i$f$getOrPut = false;
        DeclarationDescriptor value$iv = $this$getOrPut$iv.get(descriptor2);
        if (value$iv == null) {
            Object t;
            boolean bl = false;
            D d = descriptor2;
            if (d instanceof Substitutable) {
                Object $this$sure$iv = ((Substitutable)((Object)descriptor2)).substitute(this.substitutor);
                boolean $i$f$sure = false;
                t = $this$sure$iv;
                if (t == null) {
                    String string;
                    boolean bl2 = false;
                    String string2 = string = "We expect that no conflict should happen while substitution is guaranteed to generate invariant projection, " + "but " + descriptor2 + " substitution fails";
                    throw (Throwable)((Object)new AssertionError((Object)string2));
                }
            } else {
                String string = "Unknown descriptor in scope: " + descriptor2;
                boolean bl3 = false;
                throw (Throwable)new IllegalStateException(string.toString());
            }
            DeclarationDescriptor answer$iv = (DeclarationDescriptor)t;
            $this$getOrPut$iv.put(descriptor2, answer$iv);
            declarationDescriptor = answer$iv;
        } else {
            declarationDescriptor = value$iv;
        }
        DeclarationDescriptor declarationDescriptor2 = substituted = declarationDescriptor;
        if (declarationDescriptor2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type D");
        }
        return (D)declarationDescriptor2;
    }

    private final <D extends DeclarationDescriptor> Collection<D> substitute(Collection<? extends D> descriptors) {
        if (this.substitutor.isEmpty()) {
            return descriptors;
        }
        if (descriptors.isEmpty()) {
            return descriptors;
        }
        LinkedHashSet<DeclarationDescriptor> result2 = CollectionsKt.newLinkedHashSetWithExpectedSize(descriptors.size());
        for (DeclarationDescriptor descriptor2 : descriptors) {
            DeclarationDescriptor substitute = this.substitute(descriptor2);
            result2.add(substitute);
        }
        return result2;
    }

    @Override
    @NotNull
    public Collection<? extends PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return this.substitute(this.workerScope.getContributedVariables(name, location));
    }

    @Override
    @Nullable
    public ClassifierDescriptor getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        ClassifierDescriptor classifierDescriptor;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        ClassifierDescriptor classifierDescriptor2 = this.workerScope.getContributedClassifier(name, location);
        if (classifierDescriptor2 != null) {
            ClassifierDescriptor classifierDescriptor3 = classifierDescriptor2;
            boolean bl = false;
            boolean bl2 = false;
            ClassifierDescriptor it = classifierDescriptor3;
            boolean bl3 = false;
            classifierDescriptor = (ClassifierDescriptor)this.substitute((DeclarationDescriptor)it);
        } else {
            classifierDescriptor = null;
        }
        return classifierDescriptor;
    }

    @Override
    @NotNull
    public Collection<? extends SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return this.substitute(this.workerScope.getContributedFunctions(name, location));
    }

    @Override
    @NotNull
    public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return this.get_allDescriptors();
    }

    @Override
    @NotNull
    public Set<Name> getFunctionNames() {
        return this.workerScope.getFunctionNames();
    }

    @Override
    @NotNull
    public Set<Name> getVariableNames() {
        return this.workerScope.getVariableNames();
    }

    @Override
    @Nullable
    public Set<Name> getClassifierNames() {
        return this.workerScope.getClassifierNames();
    }

    public SubstitutingScope(@NotNull MemberScope workerScope, @NotNull TypeSubstitutor givenSubstitutor) {
        Intrinsics.checkNotNullParameter(workerScope, "workerScope");
        Intrinsics.checkNotNullParameter(givenSubstitutor, "givenSubstitutor");
        this.workerScope = workerScope;
        TypeSubstitution typeSubstitution = givenSubstitutor.getSubstitution();
        Intrinsics.checkNotNullExpressionValue(typeSubstitution, "givenSubstitutor.substitution");
        this.substitutor = CapturedTypeConstructorKt.wrapWithCapturingSubstitution$default(typeSubstitution, false, 1, null).buildSubstitutor();
        this._allDescriptors$delegate = LazyKt.lazy((Function0)new Function0<Collection<? extends DeclarationDescriptor>>(this){
            final /* synthetic */ SubstitutingScope this$0;

            @NotNull
            public final Collection<DeclarationDescriptor> invoke() {
                return SubstitutingScope.access$substitute(this.this$0, ResolutionScope.DefaultImpls.getContributedDescriptors$default(SubstitutingScope.access$getWorkerScope$p(this.this$0), null, null, 3, null));
            }
            {
                this.this$0 = substitutingScope;
                super(0);
            }
        });
    }

    @Override
    public void recordLookup(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        MemberScope.DefaultImpls.recordLookup(this, name, location);
    }

    public static final /* synthetic */ Collection access$substitute(SubstitutingScope $this, Collection descriptors) {
        return $this.substitute(descriptors);
    }

    public static final /* synthetic */ MemberScope access$getWorkerScope$p(SubstitutingScope $this) {
        return $this.workerScope;
    }
}

