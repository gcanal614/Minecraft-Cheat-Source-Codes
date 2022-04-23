/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Collection;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorEquivalenceForOverrides;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DescriptorEquivalenceForOverrides {
    public static final DescriptorEquivalenceForOverrides INSTANCE;

    public final boolean areEquivalent(@Nullable DeclarationDescriptor a2, @Nullable DeclarationDescriptor b2, boolean allowCopiesFromTheSameDeclaration, boolean distinguishExpectsAndNonExpects) {
        return a2 instanceof ClassDescriptor && b2 instanceof ClassDescriptor ? this.areClassesEquivalent((ClassDescriptor)a2, (ClassDescriptor)b2) : (a2 instanceof TypeParameterDescriptor && b2 instanceof TypeParameterDescriptor ? DescriptorEquivalenceForOverrides.areTypeParametersEquivalent$default(this, (TypeParameterDescriptor)a2, (TypeParameterDescriptor)b2, allowCopiesFromTheSameDeclaration, null, 8, null) : (a2 instanceof CallableDescriptor && b2 instanceof CallableDescriptor ? DescriptorEquivalenceForOverrides.areCallableDescriptorsEquivalent$default(this, (CallableDescriptor)a2, (CallableDescriptor)b2, allowCopiesFromTheSameDeclaration, distinguishExpectsAndNonExpects, false, KotlinTypeRefiner.Default.INSTANCE, 16, null) : (a2 instanceof PackageFragmentDescriptor && b2 instanceof PackageFragmentDescriptor ? Intrinsics.areEqual(((PackageFragmentDescriptor)a2).getFqName(), ((PackageFragmentDescriptor)b2).getFqName()) : Intrinsics.areEqual(a2, b2))));
    }

    public static /* synthetic */ boolean areEquivalent$default(DescriptorEquivalenceForOverrides descriptorEquivalenceForOverrides, DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2, boolean bl, boolean bl2, int n, Object object) {
        if ((n & 8) != 0) {
            bl2 = true;
        }
        return descriptorEquivalenceForOverrides.areEquivalent(declarationDescriptor, declarationDescriptor2, bl, bl2);
    }

    private final boolean areClassesEquivalent(ClassDescriptor a2, ClassDescriptor b2) {
        return Intrinsics.areEqual(a2.getTypeConstructor(), b2.getTypeConstructor());
    }

    private final boolean areTypeParametersEquivalent(TypeParameterDescriptor a2, TypeParameterDescriptor b2, boolean allowCopiesFromTheSameDeclaration, Function2<? super DeclarationDescriptor, ? super DeclarationDescriptor, Boolean> equivalentCallables) {
        if (Intrinsics.areEqual(a2, b2)) {
            return true;
        }
        if (Intrinsics.areEqual(a2.getContainingDeclaration(), b2.getContainingDeclaration())) {
            return false;
        }
        if (!this.ownersEquivalent(a2, b2, equivalentCallables, allowCopiesFromTheSameDeclaration)) {
            return false;
        }
        return a2.getIndex() == b2.getIndex();
    }

    static /* synthetic */ boolean areTypeParametersEquivalent$default(DescriptorEquivalenceForOverrides descriptorEquivalenceForOverrides, TypeParameterDescriptor typeParameterDescriptor, TypeParameterDescriptor typeParameterDescriptor2, boolean bl, Function2 function2, int n, Object object) {
        if ((n & 8) != 0) {
            function2 = areTypeParametersEquivalent.1.INSTANCE;
        }
        return descriptorEquivalenceForOverrides.areTypeParametersEquivalent(typeParameterDescriptor, typeParameterDescriptor2, bl, function2);
    }

    private final SourceElement singleSource(CallableDescriptor $this$singleSource) {
        while (true) {
            if (!($this$singleSource instanceof CallableMemberDescriptor) || ((CallableMemberDescriptor)$this$singleSource).getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                return $this$singleSource.getSource();
            }
            Collection<? extends CallableMemberDescriptor> collection = ((CallableMemberDescriptor)$this$singleSource).getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(collection, "overriddenDescriptors");
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor)CollectionsKt.singleOrNull((Iterable)collection);
            if (callableMemberDescriptor == null) break;
            $this$singleSource = callableMemberDescriptor;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean areCallableDescriptorsEquivalent(@NotNull CallableDescriptor a2, @NotNull CallableDescriptor b2, boolean allowCopiesFromTheSameDeclaration, boolean distinguishExpectsAndNonExpects, boolean ignoreReturnType, @NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(a2, "a");
        Intrinsics.checkNotNullParameter(b2, "b");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        if (Intrinsics.areEqual(a2, b2)) {
            return true;
        }
        if (Intrinsics.areEqual(a2.getName(), b2.getName()) ^ true) {
            return false;
        }
        if (distinguishExpectsAndNonExpects && a2 instanceof MemberDescriptor && b2 instanceof MemberDescriptor && ((MemberDescriptor)((Object)a2)).isExpect() != ((MemberDescriptor)((Object)b2)).isExpect()) {
            return false;
        }
        if (Intrinsics.areEqual(a2.getContainingDeclaration(), b2.getContainingDeclaration())) {
            if (!allowCopiesFromTheSameDeclaration) {
                return false;
            }
            if (Intrinsics.areEqual(this.singleSource(a2), this.singleSource(b2)) ^ true) {
                return false;
            }
        }
        if (DescriptorUtils.isLocal(a2)) return false;
        if (DescriptorUtils.isLocal(b2)) {
            return false;
        }
        if (!this.ownersEquivalent(a2, b2, areCallableDescriptorsEquivalent.1.INSTANCE, allowCopiesFromTheSameDeclaration)) {
            return false;
        }
        OverridingUtil overridingUtil2 = OverridingUtil.create(kotlinTypeRefiner, new KotlinTypeChecker.TypeConstructorEquality(allowCopiesFromTheSameDeclaration, a2, b2){
            final /* synthetic */ boolean $allowCopiesFromTheSameDeclaration;
            final /* synthetic */ CallableDescriptor $a;
            final /* synthetic */ CallableDescriptor $b;

            public final boolean invoke(@NotNull TypeConstructor c1, @NotNull TypeConstructor c2) {
                Intrinsics.checkNotNullParameter(c1, "c1");
                Intrinsics.checkNotNullParameter(c2, "c2");
                if (Intrinsics.areEqual(c1, c2)) {
                    return true;
                }
                ClassifierDescriptor d1 = c1.getDeclarationDescriptor();
                ClassifierDescriptor d2 = c2.getDeclarationDescriptor();
                if (!(d1 instanceof TypeParameterDescriptor) || !(d2 instanceof TypeParameterDescriptor)) {
                    return false;
                }
                return DescriptorEquivalenceForOverrides.access$areTypeParametersEquivalent(DescriptorEquivalenceForOverrides.INSTANCE, (TypeParameterDescriptor)d1, (TypeParameterDescriptor)d2, this.$allowCopiesFromTheSameDeclaration, new Function2<DeclarationDescriptor, DeclarationDescriptor, Boolean>(this){
                    final /* synthetic */ areCallableDescriptorsEquivalent.overridingUtil.1 this$0;

                    public final boolean invoke(@Nullable DeclarationDescriptor x, @Nullable DeclarationDescriptor y) {
                        return Intrinsics.areEqual(x, this.this$0.$a) && Intrinsics.areEqual(y, this.this$0.$b);
                    }
                    {
                        this.this$0 = var1_1;
                        super(2);
                    }
                });
            }
            {
                this.$allowCopiesFromTheSameDeclaration = bl;
                this.$a = callableDescriptor;
                this.$b = callableDescriptor2;
            }
        });
        Intrinsics.checkNotNullExpressionValue(overridingUtil2, "OverridingUtil.create(ko\u2026= a && y == b }\n        }");
        OverridingUtil overridingUtil3 = overridingUtil2;
        OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfo = overridingUtil3.isOverridableBy(a2, b2, null, !ignoreReturnType);
        Intrinsics.checkNotNullExpressionValue(overrideCompatibilityInfo, "overridingUtil.isOverrid\u2026 null, !ignoreReturnType)");
        if (overrideCompatibilityInfo.getResult() != OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE) return false;
        OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfo2 = overridingUtil3.isOverridableBy(b2, a2, null, !ignoreReturnType);
        Intrinsics.checkNotNullExpressionValue(overrideCompatibilityInfo2, "overridingUtil.isOverrid\u2026 null, !ignoreReturnType)");
        if (overrideCompatibilityInfo2.getResult() != OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE) return false;
        return true;
    }

    public static /* synthetic */ boolean areCallableDescriptorsEquivalent$default(DescriptorEquivalenceForOverrides descriptorEquivalenceForOverrides, CallableDescriptor callableDescriptor, CallableDescriptor callableDescriptor2, boolean bl, boolean bl2, boolean bl3, KotlinTypeRefiner kotlinTypeRefiner, int n, Object object) {
        if ((n & 8) != 0) {
            bl2 = true;
        }
        if ((n & 0x10) != 0) {
            bl3 = false;
        }
        return descriptorEquivalenceForOverrides.areCallableDescriptorsEquivalent(callableDescriptor, callableDescriptor2, bl, bl2, bl3, kotlinTypeRefiner);
    }

    private final boolean ownersEquivalent(DeclarationDescriptor a2, DeclarationDescriptor b2, Function2<? super DeclarationDescriptor, ? super DeclarationDescriptor, Boolean> equivalentCallables, boolean allowCopiesFromTheSameDeclaration) {
        DeclarationDescriptor aOwner = a2.getContainingDeclaration();
        DeclarationDescriptor bOwner = b2.getContainingDeclaration();
        return aOwner instanceof CallableMemberDescriptor || bOwner instanceof CallableMemberDescriptor ? equivalentCallables.invoke(aOwner, bOwner) : DescriptorEquivalenceForOverrides.areEquivalent$default(this, aOwner, bOwner, allowCopiesFromTheSameDeclaration, false, 8, null);
    }

    private DescriptorEquivalenceForOverrides() {
    }

    static {
        DescriptorEquivalenceForOverrides descriptorEquivalenceForOverrides;
        INSTANCE = descriptorEquivalenceForOverrides = new DescriptorEquivalenceForOverrides();
    }

    public static final /* synthetic */ boolean access$areTypeParametersEquivalent(DescriptorEquivalenceForOverrides $this, TypeParameterDescriptor a2, TypeParameterDescriptor b2, boolean allowCopiesFromTheSameDeclaration, Function2 equivalentCallables) {
        return $this.areTypeParametersEquivalent(a2, b2, allowCopiesFromTheSameDeclaration, equivalentCallables);
    }
}

