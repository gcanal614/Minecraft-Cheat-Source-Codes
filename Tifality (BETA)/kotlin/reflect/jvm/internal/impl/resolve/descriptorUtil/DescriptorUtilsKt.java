/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil;

import java.util.Collection;
import java.util.LinkedHashSet;
import kotlin.Unit;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.ResolutionScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefinerKt;
import kotlin.reflect.jvm.internal.impl.types.checker.Ref;
import kotlin.reflect.jvm.internal.impl.utils.DFS;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DescriptorUtilsKt {
    private static final Name RETENTION_PARAMETER_NAME;

    @NotNull
    public static final FqNameUnsafe getFqNameUnsafe(@NotNull DeclarationDescriptor $this$fqNameUnsafe) {
        Intrinsics.checkNotNullParameter($this$fqNameUnsafe, "$this$fqNameUnsafe");
        FqNameUnsafe fqNameUnsafe = DescriptorUtils.getFqName($this$fqNameUnsafe);
        Intrinsics.checkNotNullExpressionValue(fqNameUnsafe, "DescriptorUtils.getFqName(this)");
        return fqNameUnsafe;
    }

    @NotNull
    public static final FqName getFqNameSafe(@NotNull DeclarationDescriptor $this$fqNameSafe) {
        Intrinsics.checkNotNullParameter($this$fqNameSafe, "$this$fqNameSafe");
        FqName fqName2 = DescriptorUtils.getFqNameSafe($this$fqNameSafe);
        Intrinsics.checkNotNullExpressionValue(fqName2, "DescriptorUtils.getFqNameSafe(this)");
        return fqName2;
    }

    @NotNull
    public static final ModuleDescriptor getModule(@NotNull DeclarationDescriptor $this$module) {
        Intrinsics.checkNotNullParameter($this$module, "$this$module");
        ModuleDescriptor moduleDescriptor = DescriptorUtils.getContainingModule($this$module);
        Intrinsics.checkNotNullExpressionValue(moduleDescriptor, "DescriptorUtils.getContainingModule(this)");
        return moduleDescriptor;
    }

    @Nullable
    public static final ClassDescriptor resolveTopLevelClass(@NotNull ModuleDescriptor $this$resolveTopLevelClass, @NotNull FqName topLevelClassFqName, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter($this$resolveTopLevelClass, "$this$resolveTopLevelClass");
        Intrinsics.checkNotNullParameter(topLevelClassFqName, "topLevelClassFqName");
        Intrinsics.checkNotNullParameter(location, "location");
        boolean bl = !topLevelClassFqName.isRoot();
        boolean bl2 = false;
        boolean bl3 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl4 = false;
            String string = "Assertion failed";
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        FqName fqName2 = topLevelClassFqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqName2, "topLevelClassFqName.parent()");
        MemberScope memberScope2 = $this$resolveTopLevelClass.getPackage(fqName2).getMemberScope();
        Name name = topLevelClassFqName.shortName();
        Intrinsics.checkNotNullExpressionValue(name, "topLevelClassFqName.shortName()");
        ClassifierDescriptor classifierDescriptor = memberScope2.getContributedClassifier(name, location);
        if (!(classifierDescriptor instanceof ClassDescriptor)) {
            classifierDescriptor = null;
        }
        return (ClassDescriptor)classifierDescriptor;
    }

    @Nullable
    public static final ClassId getClassId(@Nullable ClassifierDescriptor $this$classId) {
        ClassId classId;
        DeclarationDescriptor declarationDescriptor = $this$classId;
        if (declarationDescriptor != null && (declarationDescriptor = declarationDescriptor.getContainingDeclaration()) != null) {
            DeclarationDescriptor declarationDescriptor2 = declarationDescriptor;
            boolean bl = false;
            boolean bl2 = false;
            DeclarationDescriptor owner = declarationDescriptor2;
            boolean bl3 = false;
            DeclarationDescriptor declarationDescriptor3 = owner;
            if (declarationDescriptor3 instanceof PackageFragmentDescriptor) {
                classId = new ClassId(((PackageFragmentDescriptor)owner).getFqName(), $this$classId.getName());
            } else if (declarationDescriptor3 instanceof ClassifierDescriptorWithTypeParameters) {
                ClassId classId2 = DescriptorUtilsKt.getClassId(owner);
                classId = classId2 != null ? classId2.createNestedClassId($this$classId.getName()) : null;
            } else {
                classId = null;
            }
        } else {
            classId = null;
        }
        return classId;
    }

    @Nullable
    public static final ClassDescriptor getSuperClassNotAny(@NotNull ClassDescriptor $this$getSuperClassNotAny) {
        Intrinsics.checkNotNullParameter($this$getSuperClassNotAny, "$this$getSuperClassNotAny");
        for (KotlinType supertype : $this$getSuperClassNotAny.getDefaultType().getConstructor().getSupertypes()) {
            ClassifierDescriptor superClassifier;
            if (KotlinBuiltIns.isAnyOrNullableAny(supertype) || !DescriptorUtils.isClassOrEnumClass(superClassifier = supertype.getConstructor().getDeclarationDescriptor())) continue;
            ClassifierDescriptor classifierDescriptor = superClassifier;
            if (classifierDescriptor == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            }
            return (ClassDescriptor)classifierDescriptor;
        }
        return null;
    }

    @NotNull
    public static final KotlinBuiltIns getBuiltIns(@NotNull DeclarationDescriptor $this$builtIns) {
        Intrinsics.checkNotNullParameter($this$builtIns, "$this$builtIns");
        return DescriptorUtilsKt.getModule($this$builtIns).getBuiltIns();
    }

    public static final boolean declaresOrInheritsDefaultValue(@NotNull ValueParameterDescriptor $this$declaresOrInheritsDefaultValue) {
        Intrinsics.checkNotNullParameter($this$declaresOrInheritsDefaultValue, "$this$declaresOrInheritsDefaultValue");
        Boolean bl = DFS.ifAny((Collection)CollectionsKt.listOf($this$declaresOrInheritsDefaultValue), declaresOrInheritsDefaultValue.1.INSTANCE, declaresOrInheritsDefaultValue.2.INSTANCE);
        Intrinsics.checkNotNullExpressionValue(bl, "DFS.ifAny(\n        listO\u2026eclaresDefaultValue\n    )");
        return bl;
    }

    @NotNull
    public static final Sequence<DeclarationDescriptor> getParentsWithSelf(@NotNull DeclarationDescriptor $this$parentsWithSelf) {
        Intrinsics.checkNotNullParameter($this$parentsWithSelf, "$this$parentsWithSelf");
        return SequencesKt.generateSequence($this$parentsWithSelf, (Function1)parentsWithSelf.1.INSTANCE);
    }

    @NotNull
    public static final Sequence<DeclarationDescriptor> getParents(@NotNull DeclarationDescriptor $this$parents) {
        Intrinsics.checkNotNullParameter($this$parents, "$this$parents");
        return SequencesKt.drop(DescriptorUtilsKt.getParentsWithSelf($this$parents), 1);
    }

    @NotNull
    public static final CallableMemberDescriptor getPropertyIfAccessor(@NotNull CallableMemberDescriptor $this$propertyIfAccessor) {
        CallableMemberDescriptor callableMemberDescriptor;
        Intrinsics.checkNotNullParameter($this$propertyIfAccessor, "$this$propertyIfAccessor");
        if ($this$propertyIfAccessor instanceof PropertyAccessorDescriptor) {
            PropertyDescriptor propertyDescriptor = ((PropertyAccessorDescriptor)$this$propertyIfAccessor).getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(propertyDescriptor, "correspondingProperty");
            callableMemberDescriptor = propertyDescriptor;
        } else {
            callableMemberDescriptor = $this$propertyIfAccessor;
        }
        return callableMemberDescriptor;
    }

    @Nullable
    public static final FqName fqNameOrNull(@NotNull DeclarationDescriptor $this$fqNameOrNull) {
        Intrinsics.checkNotNullParameter($this$fqNameOrNull, "$this$fqNameOrNull");
        FqNameUnsafe fqNameUnsafe = DescriptorUtilsKt.getFqNameUnsafe($this$fqNameOrNull);
        boolean bl = false;
        boolean bl2 = false;
        FqNameUnsafe it = fqNameUnsafe;
        boolean bl3 = false;
        FqNameUnsafe fqNameUnsafe2 = it.isSafe() ? fqNameUnsafe : null;
        return fqNameUnsafe2 != null ? fqNameUnsafe2.toSafe() : null;
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public static final CallableMemberDescriptor firstOverridden(@NotNull CallableMemberDescriptor $this$firstOverridden, boolean useOriginal, @NotNull Function1<? super CallableMemberDescriptor, Boolean> predicate) {
        void result2;
        Intrinsics.checkNotNullParameter($this$firstOverridden, "$this$firstOverridden");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = null;
        return (CallableMemberDescriptor)DFS.dfs((Collection)CollectionsKt.listOf($this$firstOverridden), new DFS.Neighbors<CallableMemberDescriptor>(useOriginal){
            final /* synthetic */ boolean $useOriginal;

            @NotNull
            public final Iterable<CallableMemberDescriptor> getNeighbors(CallableMemberDescriptor current) {
                CallableMemberDescriptor descriptor2;
                Object object;
                CallableMemberDescriptor callableMemberDescriptor;
                if (this.$useOriginal) {
                    CallableMemberDescriptor callableMemberDescriptor2 = current;
                    callableMemberDescriptor = callableMemberDescriptor2 != null ? callableMemberDescriptor2.getOriginal() : null;
                } else {
                    callableMemberDescriptor = current;
                }
                return (object = (descriptor2 = callableMemberDescriptor)) != null && (object = object.getOverriddenDescriptors()) != null ? (Iterable)object : (Iterable)CollectionsKt.emptyList();
            }
            {
                this.$useOriginal = bl;
            }
        }, new DFS.AbstractNodeHandler<CallableMemberDescriptor, CallableMemberDescriptor>((Ref.ObjectRef)result2, predicate){
            final /* synthetic */ Ref.ObjectRef $result;
            final /* synthetic */ Function1 $predicate;

            public boolean beforeChildren(@NotNull CallableMemberDescriptor current) {
                Intrinsics.checkNotNullParameter(current, "current");
                return (CallableMemberDescriptor)this.$result.element == null;
            }

            public void afterChildren(@NotNull CallableMemberDescriptor current) {
                Intrinsics.checkNotNullParameter(current, "current");
                if ((CallableMemberDescriptor)this.$result.element == null && ((Boolean)this.$predicate.invoke(current)).booleanValue()) {
                    this.$result.element = current;
                }
            }

            @Nullable
            public CallableMemberDescriptor result() {
                return (CallableMemberDescriptor)this.$result.element;
            }
            {
                this.$result = $captured_local_variable$0;
                this.$predicate = $captured_local_variable$1;
            }
        });
    }

    public static /* synthetic */ CallableMemberDescriptor firstOverridden$default(CallableMemberDescriptor callableMemberDescriptor, boolean bl, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        return DescriptorUtilsKt.firstOverridden(callableMemberDescriptor, bl, function1);
    }

    @NotNull
    public static final Collection<ClassDescriptor> computeSealedSubclasses(@NotNull ClassDescriptor sealedClass) {
        Intrinsics.checkNotNullParameter(sealedClass, "sealedClass");
        if (sealedClass.getModality() != Modality.SEALED) {
            return CollectionsKt.emptyList();
        }
        boolean bl = false;
        LinkedHashSet result2 = new LinkedHashSet();
        Function2<MemberScope, Boolean, Unit> $fun$collectSubclasses$1 = new Function2<MemberScope, Boolean, Unit>(sealedClass, result2){
            final /* synthetic */ ClassDescriptor $sealedClass;
            final /* synthetic */ LinkedHashSet $result;

            public final void invoke(@NotNull MemberScope scope2, boolean collectNested) {
                Intrinsics.checkNotNullParameter(scope2, "scope");
                for (DeclarationDescriptor descriptor2 : ResolutionScope.DefaultImpls.getContributedDescriptors$default(scope2, DescriptorKindFilter.CLASSIFIERS, null, 2, null)) {
                    if (!(descriptor2 instanceof ClassDescriptor)) continue;
                    if (DescriptorUtils.isDirectSubclass((ClassDescriptor)descriptor2, this.$sealedClass)) {
                        this.$result.add(descriptor2);
                    }
                    if (!collectNested) continue;
                    MemberScope memberScope2 = ((ClassDescriptor)descriptor2).getUnsubstitutedInnerClassesScope();
                    Intrinsics.checkNotNullExpressionValue(memberScope2, "descriptor.unsubstitutedInnerClassesScope");
                    this.invoke(memberScope2, collectNested);
                }
            }
            {
                this.$sealedClass = classDescriptor;
                this.$result = linkedHashSet;
                super(2);
            }
        };
        DeclarationDescriptor declarationDescriptor = sealedClass.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "sealedClass.containingDeclaration");
        DeclarationDescriptor container = declarationDescriptor;
        if (container instanceof PackageFragmentDescriptor) {
            $fun$collectSubclasses$1.invoke(((PackageFragmentDescriptor)container).getMemberScope(), false);
        }
        MemberScope memberScope2 = sealedClass.getUnsubstitutedInnerClassesScope();
        Intrinsics.checkNotNullExpressionValue(memberScope2, "sealedClass.unsubstitutedInnerClassesScope");
        $fun$collectSubclasses$1.invoke(memberScope2, true);
        return result2;
    }

    @Nullable
    public static final ClassDescriptor getAnnotationClass(@NotNull AnnotationDescriptor $this$annotationClass) {
        Intrinsics.checkNotNullParameter($this$annotationClass, "$this$annotationClass");
        ClassifierDescriptor classifierDescriptor = $this$annotationClass.getType().getConstructor().getDeclarationDescriptor();
        if (!(classifierDescriptor instanceof ClassDescriptor)) {
            classifierDescriptor = null;
        }
        return (ClassDescriptor)classifierDescriptor;
    }

    @Nullable
    public static final ConstantValue<?> firstArgument(@NotNull AnnotationDescriptor $this$firstArgument) {
        Intrinsics.checkNotNullParameter($this$firstArgument, "$this$firstArgument");
        return (ConstantValue)CollectionsKt.firstOrNull((Iterable)$this$firstArgument.getAllValueArguments().values());
    }

    @NotNull
    public static final KotlinTypeRefiner getKotlinTypeRefiner(@NotNull ModuleDescriptor $this$getKotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter($this$getKotlinTypeRefiner, "$this$getKotlinTypeRefiner");
        Ref<KotlinTypeRefiner> ref = $this$getKotlinTypeRefiner.getCapability(KotlinTypeRefinerKt.getREFINER_CAPABILITY());
        if (ref == null || (ref = ref.getValue()) == null) {
            ref = KotlinTypeRefiner.Default.INSTANCE;
        }
        return ref;
    }

    public static final boolean isTypeRefinementEnabled(@NotNull ModuleDescriptor $this$isTypeRefinementEnabled) {
        Intrinsics.checkNotNullParameter($this$isTypeRefinementEnabled, "$this$isTypeRefinementEnabled");
        Ref<KotlinTypeRefiner> ref = $this$isTypeRefinementEnabled.getCapability(KotlinTypeRefinerKt.getREFINER_CAPABILITY());
        return (ref != null ? ref.getValue() : null) != null;
    }

    static {
        Name name = Name.identifier("value");
        Intrinsics.checkNotNullExpressionValue(name, "Name.identifier(\"value\")");
        RETENTION_PARAMETER_NAME = name;
    }
}

