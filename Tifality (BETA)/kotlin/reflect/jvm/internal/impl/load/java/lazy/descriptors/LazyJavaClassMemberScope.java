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
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.SuspendFunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassConstructorDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorBase;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.EnumEntrySyntheticClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertySetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.incremental.UtilsKt;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.BuiltinMethodsWithDifferentJvmName;
import kotlin.reflect.jvm.internal.impl.load.java.BuiltinMethodsWithSpecialGenericSignature;
import kotlin.reflect.jvm.internal.impl.load.java.BuiltinSpecialProperties;
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder;
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassesTracker;
import kotlin.reflect.jvm.internal.impl.load.java.JavaIncompatibilityRulesOverridabilityCondition;
import kotlin.reflect.jvm.internal.impl.load.java.JavaVisibilities;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAbi;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.load.java.PropertiesConventionUtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.SpecialBuiltinMembers;
import kotlin.reflect.jvm.internal.impl.load.java.components.DescriptorResolverUtils;
import kotlin.reflect.jvm.internal.impl.load.java.components.SignaturePropagator;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaForKotlinOverridePropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.UtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.ValueParameterData;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaAnnotationsKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.ClassDeclaredMemberIndex;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.JavaDescriptorUtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassMemberScope;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeAttributes;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolverKt;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaConstructor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ErrorReporter;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LazyJavaClassMemberScope
extends LazyJavaScope {
    @NotNull
    private final NotNullLazyValue<List<ClassConstructorDescriptor>> constructors;
    private final NotNullLazyValue<Set<Name>> nestedClassIndex;
    private final NotNullLazyValue<Map<Name, JavaField>> enumEntryIndex;
    private final MemoizedFunctionToNullable<Name, ClassDescriptorBase> nestedClasses;
    @NotNull
    private final ClassDescriptor ownerDescriptor;
    private final JavaClass jClass;
    private final boolean skipRefinement;

    @Override
    @NotNull
    protected ClassDeclaredMemberIndex computeMemberIndex() {
        return new ClassDeclaredMemberIndex(this.jClass, computeMemberIndex.1.INSTANCE);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    protected LinkedHashSet<Name> computeFunctionNames(@NotNull DescriptorKindFilter kindFilter, @Nullable Function1<? super Name, Boolean> nameFilter) {
        void $this$flatMapTo$iv;
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        TypeConstructor typeConstructor2 = this.getOwnerDescriptor().getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "ownerDescriptor.typeConstructor");
        Collection<KotlinType> collection = typeConstructor2.getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collection, "ownerDescriptor.typeConstructor.supertypes");
        Iterable iterable = collection;
        boolean bl = false;
        Collection destination$iv = new LinkedHashSet();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv : $this$flatMapTo$iv) {
            KotlinType it = (KotlinType)element$iv;
            boolean bl2 = false;
            Iterable list$iv = it.getMemberScope().getFunctionNames();
            CollectionsKt.addAll(destination$iv, list$iv);
        }
        iterable = destination$iv;
        boolean bl3 = false;
        boolean bl4 = false;
        LinkedHashSet $this$apply = (LinkedHashSet)iterable;
        boolean bl5 = false;
        $this$apply.addAll((Collection)((DeclaredMemberIndex)this.getDeclaredMemberIndex().invoke()).getMethodNames());
        $this$apply.addAll((Collection)this.computeClassNames(kindFilter, nameFilter));
        return (LinkedHashSet)iterable;
    }

    @NotNull
    public final NotNullLazyValue<List<ClassConstructorDescriptor>> getConstructors$descriptors_jvm() {
        return this.constructors;
    }

    @Override
    protected boolean isVisibleAsFunction(@NotNull JavaMethodDescriptor $this$isVisibleAsFunction) {
        Intrinsics.checkNotNullParameter($this$isVisibleAsFunction, "$this$isVisibleAsFunction");
        if (this.jClass.isAnnotationType()) {
            return false;
        }
        return this.isVisibleAsFunctionInCurrentClass($this$isVisibleAsFunction);
    }

    private final boolean isVisibleAsFunctionInCurrentClass(SimpleFunctionDescriptor function) {
        boolean bl;
        block8: {
            Name name = function.getName();
            Intrinsics.checkNotNullExpressionValue(name, "function.name");
            Iterable $this$any$iv = PropertiesConventionUtilKt.getPropertyNamesCandidatesByAccessorName(name);
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    boolean bl2;
                    block7: {
                        Name propertyName = (Name)element$iv;
                        boolean bl3 = false;
                        Iterable $this$any$iv2 = this.getPropertiesFromSupertypes(propertyName);
                        boolean $i$f$any2 = false;
                        if ($this$any$iv2 instanceof Collection && ((Collection)$this$any$iv2).isEmpty()) {
                            bl2 = false;
                        } else {
                            for (Object element$iv2 : $this$any$iv2) {
                                PropertyDescriptor property = (PropertyDescriptor)element$iv2;
                                boolean bl4 = false;
                                if (!(this.doesClassOverridesProperty(property, (Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>>)new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>(this, function){
                                    final /* synthetic */ LazyJavaClassMemberScope this$0;
                                    final /* synthetic */ SimpleFunctionDescriptor $function$inlined;
                                    {
                                        this.this$0 = lazyJavaClassMemberScope;
                                        this.$function$inlined = simpleFunctionDescriptor;
                                        super(1);
                                    }

                                    @NotNull
                                    public final Collection<SimpleFunctionDescriptor> invoke(@NotNull Name accessorName) {
                                        Intrinsics.checkNotNullParameter(accessorName, "accessorName");
                                        return Intrinsics.areEqual(this.$function$inlined.getName(), accessorName) ? CollectionsKt.listOf(this.$function$inlined) : CollectionsKt.plus(LazyJavaClassMemberScope.access$searchMethodsByNameWithoutBuiltinMagic(this.this$0, accessorName), (Iterable)LazyJavaClassMemberScope.access$searchMethodsInSupertypesWithoutBuiltinMagic(this.this$0, accessorName));
                                    }
                                }) && (property.isVar() || !JvmAbi.isSetterName(function.getName().asString())))) continue;
                                bl2 = true;
                                break block7;
                            }
                            bl2 = false;
                        }
                    }
                    if (!bl2) continue;
                    bl = true;
                    break block8;
                }
                bl = false;
            }
        }
        if (bl) {
            return false;
        }
        return !this.doesOverrideRenamedBuiltins(function) && !this.shouldBeVisibleAsOverrideOfBuiltInWithErasedValueParameters(function) && !this.doesOverrideSuspendFunction(function);
    }

    /*
     * WARNING - void declaration
     */
    private final boolean shouldBeVisibleAsOverrideOfBuiltInWithErasedValueParameters(SimpleFunctionDescriptor $this$shouldBeVisibleAsOverrideOfBuiltInWithErasedValueParameters) {
        boolean bl;
        block5: {
            void $this$mapNotNullTo$iv$iv;
            Name name = $this$shouldBeVisibleAsOverrideOfBuiltInWithErasedValueParameters.getName();
            Intrinsics.checkNotNullExpressionValue(name, "name");
            if (!BuiltinMethodsWithSpecialGenericSignature.INSTANCE.getSameAsBuiltinMethodWithErasedValueParameters(name)) {
                return false;
            }
            Name name2 = $this$shouldBeVisibleAsOverrideOfBuiltInWithErasedValueParameters.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "name");
            Iterable $this$mapNotNull$iv = this.getFunctionsFromSupertypes(name2);
            boolean $i$f$mapNotNull = false;
            Iterable iterable = $this$mapNotNull$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
            while (iterator2.hasNext()) {
                FunctionDescriptor functionDescriptor;
                Object element$iv$iv$iv;
                Object element$iv$iv = element$iv$iv$iv = iterator2.next();
                boolean bl2 = false;
                SimpleFunctionDescriptor it = (SimpleFunctionDescriptor)element$iv$iv;
                boolean bl3 = false;
                if (BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava(it) == null) continue;
                boolean bl4 = false;
                boolean bl5 = false;
                FunctionDescriptor it$iv$iv = functionDescriptor;
                boolean bl6 = false;
                destination$iv$iv.add(it$iv$iv);
            }
            List candidatesToOverride = (List)destination$iv$iv;
            Iterable $this$any$iv = candidatesToOverride;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    FunctionDescriptor candidate = (FunctionDescriptor)element$iv;
                    boolean bl7 = false;
                    if (!this.hasSameJvmDescriptorButDoesNotOverride($this$shouldBeVisibleAsOverrideOfBuiltInWithErasedValueParameters, candidate)) continue;
                    bl = true;
                    break block5;
                }
                bl = false;
            }
        }
        return bl;
    }

    /*
     * WARNING - void declaration
     */
    private final Collection<SimpleFunctionDescriptor> searchMethodsByNameWithoutBuiltinMagic(Name name) {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = ((DeclaredMemberIndex)this.getDeclaredMemberIndex().invoke()).findMethodsByName(name);
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            JavaMethod javaMethod = (JavaMethod)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            JavaMethodDescriptor javaMethodDescriptor = this.resolveMethodToFunctionDescriptor((JavaMethod)it);
            collection.add(javaMethodDescriptor);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    private final Collection<SimpleFunctionDescriptor> searchMethodsInSupertypesWithoutBuiltinMagic(Name name) {
        void $this$filterNotTo$iv$iv;
        Iterable $this$filterNot$iv = this.getFunctionsFromSupertypes(name);
        boolean $i$f$filterNot = false;
        Iterable iterable = $this$filterNot$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterNotTo = false;
        for (Object element$iv$iv : $this$filterNotTo$iv$iv) {
            SimpleFunctionDescriptor it = (SimpleFunctionDescriptor)element$iv$iv;
            boolean bl = false;
            if (SpecialBuiltinMembers.doesOverrideBuiltinWithDifferentJvmName(it) || BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava(it) != null) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    private final boolean doesOverrideRenamedBuiltins(SimpleFunctionDescriptor $this$doesOverrideRenamedBuiltins) {
        boolean bl;
        block10: {
            Name name = $this$doesOverrideRenamedBuiltins.getName();
            Intrinsics.checkNotNullExpressionValue(name, "name");
            Iterable $this$any$iv = BuiltinMethodsWithDifferentJvmName.INSTANCE.getBuiltinFunctionNamesByJvmName(name);
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    boolean bl2;
                    block9: {
                        void $this$filterTo$iv$iv;
                        Name builtinName = (Name)element$iv;
                        boolean bl3 = false;
                        Iterable $this$filter$iv = this.getFunctionsFromSupertypes(builtinName);
                        boolean $i$f$filter = false;
                        Iterable iterable = $this$filter$iv;
                        Collection destination$iv$iv = new ArrayList();
                        boolean $i$f$filterTo = false;
                        for (Object element$iv$iv : $this$filterTo$iv$iv) {
                            SimpleFunctionDescriptor it = (SimpleFunctionDescriptor)element$iv$iv;
                            boolean bl4 = false;
                            if (!SpecialBuiltinMembers.doesOverrideBuiltinWithDifferentJvmName(it)) continue;
                            destination$iv$iv.add(element$iv$iv);
                        }
                        List builtinSpecialFromSuperTypes = (List)destination$iv$iv;
                        if (builtinSpecialFromSuperTypes.isEmpty()) {
                            bl2 = false;
                        } else {
                            SimpleFunctionDescriptor methodDescriptor = this.createRenamedCopy($this$doesOverrideRenamedBuiltins, builtinName);
                            Iterable $this$any$iv2 = builtinSpecialFromSuperTypes;
                            boolean $i$f$any2 = false;
                            if ($this$any$iv2 instanceof Collection && ((Collection)$this$any$iv2).isEmpty()) {
                                bl2 = false;
                            } else {
                                for (Object element$iv2 : $this$any$iv2) {
                                    SimpleFunctionDescriptor it = (SimpleFunctionDescriptor)element$iv2;
                                    boolean bl5 = false;
                                    if (!this.doesOverrideRenamedDescriptor(it, methodDescriptor)) continue;
                                    bl2 = true;
                                    break block9;
                                }
                                bl2 = false;
                            }
                        }
                    }
                    if (!bl2) continue;
                    bl = true;
                    break block10;
                }
                bl = false;
            }
        }
        return bl;
    }

    private final boolean doesOverrideSuspendFunction(SimpleFunctionDescriptor $this$doesOverrideSuspendFunction) {
        boolean bl;
        block4: {
            SimpleFunctionDescriptor simpleFunctionDescriptor = this.createSuspendView($this$doesOverrideSuspendFunction);
            if (simpleFunctionDescriptor == null) {
                return false;
            }
            SimpleFunctionDescriptor suspendView = simpleFunctionDescriptor;
            Name name = $this$doesOverrideSuspendFunction.getName();
            Intrinsics.checkNotNullExpressionValue(name, "name");
            Iterable $this$any$iv = this.getFunctionsFromSupertypes(name);
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    SimpleFunctionDescriptor overriddenCandidate = (SimpleFunctionDescriptor)element$iv;
                    boolean bl2 = false;
                    if (!(overriddenCandidate.isSuspend() && this.doesOverride(suspendView, overriddenCandidate))) continue;
                    bl = true;
                    break block4;
                }
                bl = false;
            }
        }
        return bl;
    }

    /*
     * Unable to fully structure code
     */
    private final SimpleFunctionDescriptor createSuspendView(SimpleFunctionDescriptor $this$createSuspendView) {
        block7: {
            block6: {
                block5: {
                    v0 = $this$createSuspendView.getValueParameters();
                    Intrinsics.checkNotNullExpressionValue(v0, "valueParameters");
                    v1 = CollectionsKt.lastOrNull(v0);
                    if (v1 == null) break block5;
                    var3_2 = v1;
                    var4_3 = false;
                    var5_4 = false;
                    it = var3_2;
                    $i$a$-takeIf-LazyJavaClassMemberScope$createSuspendView$continuationParameter$1 = false;
                    v2 = it.getType().getConstructor().getDeclarationDescriptor();
                    if (v2 == null || (v2 = DescriptorUtilsKt.getFqNameUnsafe((DeclarationDescriptor)v2)) == null) ** GOTO lbl-1000
                    var8_7 = v2;
                    var9_8 = false;
                    var10_9 = false;
                    p1 = var8_7;
                    $i$a$-unknown-LazyJavaClassMemberScope$createSuspendView$continuationParameter$1$1 = false;
                    v2 = p1.isSafe() != false ? var8_7 : null;
                    if (v2 != null) {
                        v3 = v2.toSafe();
                    } else lbl-1000:
                    // 2 sources

                    {
                        v3 = null;
                    }
                    if ((v1 = SuspendFunctionTypesKt.isContinuation(v3, this.getC().getComponents().getSettings().isReleaseCoroutines()) != false ? var3_2 : null) != null) break block6;
                }
                return null;
            }
            continuationParameter = v1;
            v4 = $this$createSuspendView.newCopyBuilder();
            v5 = $this$createSuspendView.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(v5, "valueParameters");
            functionDescriptor = v4.setValueParameters(CollectionsKt.dropLast(v5, 1)).setReturnType(continuationParameter.getType().getArguments().get(0).getType()).build();
            v6 = (SimpleFunctionDescriptorImpl)functionDescriptor;
            if (v6 == null) break block7;
            v6.setSuspend(true);
        }
        return functionDescriptor;
    }

    private final SimpleFunctionDescriptor createRenamedCopy(SimpleFunctionDescriptor $this$createRenamedCopy, Name builtinName) {
        FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> copyBuilder = $this$createRenamedCopy.newCopyBuilder();
        boolean bl = false;
        boolean bl2 = false;
        FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> $this$apply = copyBuilder;
        boolean bl3 = false;
        $this$apply.setName(builtinName);
        $this$apply.setSignatureChange();
        $this$apply.setPreserveSourceElement();
        SimpleFunctionDescriptor simpleFunctionDescriptor = copyBuilder.build();
        Intrinsics.checkNotNull(simpleFunctionDescriptor);
        return simpleFunctionDescriptor;
    }

    private final boolean doesOverrideRenamedDescriptor(SimpleFunctionDescriptor superDescriptor, FunctionDescriptor subDescriptor) {
        FunctionDescriptor functionDescriptor = BuiltinMethodsWithDifferentJvmName.INSTANCE.isRemoveAtByIndex(superDescriptor) ? subDescriptor.getOriginal() : subDescriptor;
        Intrinsics.checkNotNullExpressionValue(functionDescriptor, "if (superDescriptor.isRe\u2026iginal else subDescriptor");
        FunctionDescriptor subDescriptorToCheck = functionDescriptor;
        return this.doesOverride(subDescriptorToCheck, superDescriptor);
    }

    private final boolean doesOverride(CallableDescriptor $this$doesOverride, CallableDescriptor superDescriptor) {
        OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfo = OverridingUtil.DEFAULT.isOverridableByWithoutExternalConditions(superDescriptor, $this$doesOverride, true);
        Intrinsics.checkNotNullExpressionValue(overrideCompatibilityInfo, "OverridingUtil.DEFAULT.i\u2026erDescriptor, this, true)");
        OverridingUtil.OverrideCompatibilityInfo.Result result2 = overrideCompatibilityInfo.getResult();
        Intrinsics.checkNotNullExpressionValue((Object)result2, "OverridingUtil.DEFAULT.i\u2026iptor, this, true).result");
        OverridingUtil.OverrideCompatibilityInfo.Result commonOverridabilityResult = result2;
        return commonOverridabilityResult == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE && !JavaIncompatibilityRulesOverridabilityCondition.Companion.doesJavaOverrideHaveIncompatibleValueParameterKinds(superDescriptor, $this$doesOverride);
    }

    private final SimpleFunctionDescriptor findGetterOverride(PropertyDescriptor $this$findGetterOverride, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> functions2) {
        String specialGetterName;
        PropertyGetterDescriptor overriddenBuiltinProperty;
        PropertyGetterDescriptor propertyGetterDescriptor = $this$findGetterOverride.getGetter();
        PropertyGetterDescriptor propertyGetterDescriptor2 = overriddenBuiltinProperty = propertyGetterDescriptor != null ? (PropertyGetterDescriptor)SpecialBuiltinMembers.getOverriddenBuiltinWithDifferentJvmName((CallableMemberDescriptor)propertyGetterDescriptor) : null;
        String string = specialGetterName = propertyGetterDescriptor2 != null ? BuiltinSpecialProperties.INSTANCE.getBuiltinSpecialPropertyGetterName(propertyGetterDescriptor2) : null;
        if (specialGetterName != null && !SpecialBuiltinMembers.hasRealKotlinSuperClassWithOverrideOf(this.getOwnerDescriptor(), overriddenBuiltinProperty)) {
            return this.findGetterByName($this$findGetterOverride, specialGetterName, functions2);
        }
        String string2 = JvmAbi.getterName($this$findGetterOverride.getName().asString());
        Intrinsics.checkNotNullExpressionValue(string2, "JvmAbi.getterName(name.asString())");
        return this.findGetterByName($this$findGetterOverride, string2, functions2);
    }

    private final SimpleFunctionDescriptor findGetterByName(PropertyDescriptor $this$findGetterByName, String getterName, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> functions2) {
        SimpleFunctionDescriptor simpleFunctionDescriptor;
        block3: {
            Name name = Name.identifier(getterName);
            Intrinsics.checkNotNullExpressionValue(name, "Name.identifier(getterName)");
            Iterable $this$firstNotNullResult$iv = functions2.invoke(name);
            boolean $i$f$firstNotNullResult = false;
            for (Object element$iv : $this$firstNotNullResult$iv) {
                SimpleFunctionDescriptor result$iv;
                SimpleFunctionDescriptor simpleFunctionDescriptor2;
                SimpleFunctionDescriptor descriptor2 = (SimpleFunctionDescriptor)element$iv;
                boolean bl = false;
                if (descriptor2.getValueParameters().size() != 0) {
                    simpleFunctionDescriptor2 = null;
                } else {
                    boolean bl2;
                    SimpleFunctionDescriptor simpleFunctionDescriptor3 = descriptor2;
                    boolean bl3 = false;
                    boolean bl4 = false;
                    SimpleFunctionDescriptor it = simpleFunctionDescriptor3;
                    boolean bl5 = false;
                    KotlinType kotlinType = descriptor2.getReturnType();
                    simpleFunctionDescriptor2 = (kotlinType == null ? (bl2 = false) : KotlinTypeChecker.DEFAULT.isSubtypeOf(kotlinType, $this$findGetterByName.getType())) ? simpleFunctionDescriptor3 : null;
                }
                if ((result$iv = simpleFunctionDescriptor2) == null) continue;
                simpleFunctionDescriptor = result$iv;
                break block3;
            }
            simpleFunctionDescriptor = null;
        }
        return simpleFunctionDescriptor;
    }

    private final SimpleFunctionDescriptor findSetterOverride(PropertyDescriptor $this$findSetterOverride, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> functions2) {
        SimpleFunctionDescriptor simpleFunctionDescriptor;
        block7: {
            Name name = Name.identifier(JvmAbi.setterName($this$findSetterOverride.getName().asString()));
            Intrinsics.checkNotNullExpressionValue(name, "Name.identifier(JvmAbi.s\u2026terName(name.asString()))");
            Iterable $this$firstNotNullResult$iv = functions2.invoke(name);
            boolean $i$f$firstNotNullResult = false;
            for (Object element$iv : $this$firstNotNullResult$iv) {
                SimpleFunctionDescriptor result$iv;
                SimpleFunctionDescriptor simpleFunctionDescriptor2;
                SimpleFunctionDescriptor descriptor2 = (SimpleFunctionDescriptor)element$iv;
                boolean bl = false;
                if (descriptor2.getValueParameters().size() != 1) {
                    simpleFunctionDescriptor2 = null;
                } else {
                    KotlinType kotlinType = descriptor2.getReturnType();
                    if (kotlinType == null) {
                        simpleFunctionDescriptor2 = null;
                    } else if (!KotlinBuiltIns.isUnit(kotlinType)) {
                        simpleFunctionDescriptor2 = null;
                    } else {
                        SimpleFunctionDescriptor simpleFunctionDescriptor3 = descriptor2;
                        boolean bl2 = false;
                        boolean bl3 = false;
                        SimpleFunctionDescriptor it = simpleFunctionDescriptor3;
                        boolean bl4 = false;
                        List<ValueParameterDescriptor> list = descriptor2.getValueParameters();
                        Intrinsics.checkNotNullExpressionValue(list, "descriptor.valueParameters");
                        ValueParameterDescriptor valueParameterDescriptor = CollectionsKt.single(list);
                        Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "descriptor.valueParameters.single()");
                        simpleFunctionDescriptor2 = KotlinTypeChecker.DEFAULT.equalTypes(valueParameterDescriptor.getType(), $this$findSetterOverride.getType()) ? simpleFunctionDescriptor3 : null;
                    }
                }
                if ((result$iv = simpleFunctionDescriptor2) == null) continue;
                simpleFunctionDescriptor = result$iv;
                break block7;
            }
            simpleFunctionDescriptor = null;
        }
        return simpleFunctionDescriptor;
    }

    private final boolean doesClassOverridesProperty(PropertyDescriptor property, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> functions2) {
        if (JavaDescriptorUtilKt.isJavaField(property)) {
            return false;
        }
        SimpleFunctionDescriptor getter = this.findGetterOverride(property, functions2);
        SimpleFunctionDescriptor setter = this.findSetterOverride(property, functions2);
        if (getter == null) {
            return false;
        }
        if (!property.isVar()) {
            return true;
        }
        return setter != null && setter.getModality() == getter.getModality();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    protected void computeNonDeclaredFunctions(@NotNull Collection<SimpleFunctionDescriptor> result2, @NotNull Name name) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter(result2, "result");
        Intrinsics.checkNotNullParameter(name, "name");
        Set<SimpleFunctionDescriptor> functionsFromSupertypes = this.getFunctionsFromSupertypes(name);
        if (!BuiltinMethodsWithDifferentJvmName.INSTANCE.getSameAsRenamedInJvmBuiltin(name) && !BuiltinMethodsWithSpecialGenericSignature.INSTANCE.getSameAsBuiltinMethodWithErasedValueParameters(name)) {
            boolean bl;
            Iterable $this$none$iv;
            block7: {
                $this$none$iv = functionsFromSupertypes;
                boolean $i$f$none = false;
                if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                    bl = true;
                } else {
                    for (Object element$iv : $this$none$iv) {
                        FunctionDescriptor p1 = (FunctionDescriptor)element$iv;
                        boolean bl2 = false;
                        if (!p1.isSuspend()) continue;
                        bl = false;
                        break block7;
                    }
                    bl = true;
                }
            }
            if (bl) {
                void $this$filterTo$iv$iv2;
                void $this$filter$iv;
                $this$none$iv = functionsFromSupertypes;
                Name name2 = name;
                Collection<SimpleFunctionDescriptor> collection = result2;
                LazyJavaClassMemberScope lazyJavaClassMemberScope = this;
                boolean $i$f$filter = false;
                Iterator iterator2 = $this$filter$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$filterTo = false;
                for (Object element$iv$iv : $this$filterTo$iv$iv2) {
                    SimpleFunctionDescriptor it = (SimpleFunctionDescriptor)element$iv$iv;
                    boolean bl3 = false;
                    if (!this.isVisibleAsFunctionInCurrentClass(it)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                List list = (List)destination$iv$iv;
                lazyJavaClassMemberScope.addFunctionFromSupertypes(collection, name2, list, false);
                return;
            }
        }
        SmartSet specialBuiltinsFromSuperTypes = SmartSet.Companion.create();
        Collection collection = DescriptorResolverUtils.resolveOverridesForNonStaticMembers(name, (Collection)functionsFromSupertypes, CollectionsKt.emptyList(), this.getOwnerDescriptor(), ErrorReporter.DO_NOTHING, this.getC().getComponents().getKotlinTypeChecker().getOverridingUtil());
        Intrinsics.checkNotNullExpressionValue(collection, "resolveOverridesForNonSt\u2026.overridingUtil\n        )");
        Collection mergedFunctionFromSuperTypes = collection;
        this.addOverriddenSpecialMethods(name, result2, mergedFunctionFromSuperTypes, result2, (Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>>)new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>(this){

            @NotNull
            public final Collection<SimpleFunctionDescriptor> invoke(@NotNull Name p1) {
                Intrinsics.checkNotNullParameter(p1, "p1");
                return LazyJavaClassMemberScope.access$searchMethodsByNameWithoutBuiltinMagic((LazyJavaClassMemberScope)this.receiver, p1);
            }

            public final KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(LazyJavaClassMemberScope.class);
            }

            public final String getName() {
                return "searchMethodsByNameWithoutBuiltinMagic";
            }

            public final String getSignature() {
                return "searchMethodsByNameWithoutBuiltinMagic(Lorg/jetbrains/kotlin/name/Name;)Ljava/util/Collection;";
            }
        });
        this.addOverriddenSpecialMethods(name, result2, mergedFunctionFromSuperTypes, specialBuiltinsFromSuperTypes, (Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>>)new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>(this){

            @NotNull
            public final Collection<SimpleFunctionDescriptor> invoke(@NotNull Name p1) {
                Intrinsics.checkNotNullParameter(p1, "p1");
                return LazyJavaClassMemberScope.access$searchMethodsInSupertypesWithoutBuiltinMagic((LazyJavaClassMemberScope)this.receiver, p1);
            }

            public final KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(LazyJavaClassMemberScope.class);
            }

            public final String getName() {
                return "searchMethodsInSupertypesWithoutBuiltinMagic";
            }

            public final String getSignature() {
                return "searchMethodsInSupertypesWithoutBuiltinMagic(Lorg/jetbrains/kotlin/name/Name;)Ljava/util/Collection;";
            }
        });
        Iterable $this$filter$iv = functionsFromSupertypes;
        boolean $i$f$filter = false;
        Iterable bl2 = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            SimpleFunctionDescriptor it = (SimpleFunctionDescriptor)element$iv$iv;
            boolean bl = false;
            if (!this.isVisibleAsFunctionInCurrentClass(it)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List visibleFunctionsFromSupertypes = CollectionsKt.plus((Collection)((List)destination$iv$iv), (Iterable)specialBuiltinsFromSuperTypes);
        this.addFunctionFromSupertypes(result2, name, visibleFunctionsFromSupertypes, true);
    }

    /*
     * WARNING - void declaration
     */
    private final void addFunctionFromSupertypes(Collection<SimpleFunctionDescriptor> result2, Name name, Collection<? extends SimpleFunctionDescriptor> functionsFromSupertypes, boolean isSpecialBuiltinName) {
        Collection<SimpleFunctionDescriptor> collection = DescriptorResolverUtils.resolveOverridesForNonStaticMembers(name, functionsFromSupertypes, result2, this.getOwnerDescriptor(), this.getC().getComponents().getErrorReporter(), this.getC().getComponents().getKotlinTypeChecker().getOverridingUtil());
        Intrinsics.checkNotNullExpressionValue(collection, "resolveOverridesForNonSt\u2026.overridingUtil\n        )");
        Collection<SimpleFunctionDescriptor> additionalOverrides = collection;
        if (!isSpecialBuiltinName) {
            result2.addAll(additionalOverrides);
        } else {
            Collection<void> collection2;
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            List<SimpleFunctionDescriptor> allDescriptors2 = CollectionsKt.plus(result2, (Iterable)additionalOverrides);
            Iterable iterable = additionalOverrides;
            Collection<SimpleFunctionDescriptor> collection3 = result2;
            boolean $i$f$map = false;
            void var9_10 = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                SimpleFunctionDescriptor simpleFunctionDescriptor;
                void resolvedOverride;
                SimpleFunctionDescriptor simpleFunctionDescriptor2 = (SimpleFunctionDescriptor)item$iv$iv;
                collection2 = destination$iv$iv;
                boolean bl = false;
                if ((SimpleFunctionDescriptor)SpecialBuiltinMembers.getOverriddenSpecialBuiltin((CallableMemberDescriptor)resolvedOverride) == null) {
                    void v1 = resolvedOverride;
                    simpleFunctionDescriptor = v1;
                    Intrinsics.checkNotNullExpressionValue(v1, "resolvedOverride");
                } else {
                    SimpleFunctionDescriptor overriddenBuiltin;
                    void v3 = resolvedOverride;
                    Intrinsics.checkNotNullExpressionValue(v3, "resolvedOverride");
                    simpleFunctionDescriptor = this.createHiddenCopyIfBuiltinAlreadyAccidentallyOverridden((SimpleFunctionDescriptor)v3, overriddenBuiltin, (Collection<? extends SimpleFunctionDescriptor>)allDescriptors2);
                }
                void var19_19 = simpleFunctionDescriptor;
                collection2.add(var19_19);
            }
            collection2 = (List)destination$iv$iv;
            collection3.addAll((Collection<SimpleFunctionDescriptor>)collection2);
        }
    }

    private final void addOverriddenSpecialMethods(Name name, Collection<? extends SimpleFunctionDescriptor> alreadyDeclaredFunctions, Collection<? extends SimpleFunctionDescriptor> candidatesForOverride, Collection<SimpleFunctionDescriptor> result2, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> functions2) {
        for (SimpleFunctionDescriptor simpleFunctionDescriptor : candidatesForOverride) {
            kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(result2, this.obtainOverrideForBuiltinWithDifferentJvmName(simpleFunctionDescriptor, functions2, name, alreadyDeclaredFunctions));
            kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(result2, this.obtainOverrideForBuiltInWithErasedValueParametersInJava(simpleFunctionDescriptor, functions2, alreadyDeclaredFunctions));
            kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(result2, this.obtainOverrideForSuspend(simpleFunctionDescriptor, functions2));
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final SimpleFunctionDescriptor obtainOverrideForBuiltInWithErasedValueParametersInJava(SimpleFunctionDescriptor descriptor2, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> functions2, Collection<? extends SimpleFunctionDescriptor> alreadyDeclaredFunctions) {
        FunctionDescriptor functionDescriptor = BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava(descriptor2);
        if (functionDescriptor == null) return null;
        FunctionDescriptor overriddenBuiltin = functionDescriptor;
        SimpleFunctionDescriptor simpleFunctionDescriptor = this.createOverrideForBuiltinFunctionWithErasedParameterIfNeeded(overriddenBuiltin, functions2);
        SimpleFunctionDescriptor simpleFunctionDescriptor2 = simpleFunctionDescriptor;
        if (simpleFunctionDescriptor == null) return null;
        SimpleFunctionDescriptor simpleFunctionDescriptor3 = simpleFunctionDescriptor2;
        LazyJavaClassMemberScope lazyJavaClassMemberScope = this;
        boolean bl = false;
        boolean bl2 = false;
        SimpleFunctionDescriptor p1 = simpleFunctionDescriptor3;
        boolean bl3 = false;
        if (!lazyJavaClassMemberScope.isVisibleAsFunctionInCurrentClass(p1)) return null;
        SimpleFunctionDescriptor simpleFunctionDescriptor4 = simpleFunctionDescriptor3;
        simpleFunctionDescriptor2 = simpleFunctionDescriptor4;
        if (simpleFunctionDescriptor4 == null) return null;
        SimpleFunctionDescriptor simpleFunctionDescriptor5 = this.createHiddenCopyIfBuiltinAlreadyAccidentallyOverridden(simpleFunctionDescriptor2, overriddenBuiltin, alreadyDeclaredFunctions);
        return simpleFunctionDescriptor5;
    }

    private final SimpleFunctionDescriptor obtainOverrideForBuiltinWithDifferentJvmName(SimpleFunctionDescriptor descriptor2, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> functions2, Name name, Collection<? extends SimpleFunctionDescriptor> alreadyDeclaredFunctions) {
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor)SpecialBuiltinMembers.getOverriddenBuiltinWithDifferentJvmName((CallableMemberDescriptor)descriptor2);
        if (simpleFunctionDescriptor == null) {
            return null;
        }
        SimpleFunctionDescriptor overriddenBuiltin = simpleFunctionDescriptor;
        String string = SpecialBuiltinMembers.getJvmMethodNameIfSpecial(overriddenBuiltin);
        Intrinsics.checkNotNull(string);
        String nameInJava = string;
        Name name2 = Name.identifier(nameInJava);
        Intrinsics.checkNotNullExpressionValue(name2, "Name.identifier(nameInJava)");
        for (SimpleFunctionDescriptor simpleFunctionDescriptor2 : functions2.invoke(name2)) {
            SimpleFunctionDescriptor renamedCopy = this.createRenamedCopy(simpleFunctionDescriptor2, name);
            if (!this.doesOverrideRenamedDescriptor(overriddenBuiltin, renamedCopy)) continue;
            return this.createHiddenCopyIfBuiltinAlreadyAccidentallyOverridden(renamedCopy, overriddenBuiltin, alreadyDeclaredFunctions);
        }
        return null;
    }

    private final SimpleFunctionDescriptor obtainOverrideForSuspend(SimpleFunctionDescriptor descriptor2, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> functions2) {
        SimpleFunctionDescriptor simpleFunctionDescriptor;
        block4: {
            if (!descriptor2.isSuspend()) {
                return null;
            }
            Name name = descriptor2.getName();
            Intrinsics.checkNotNullExpressionValue(name, "descriptor.name");
            Iterable $this$firstNotNullResult$iv = functions2.invoke(name);
            boolean $i$f$firstNotNullResult = false;
            for (Object element$iv : $this$firstNotNullResult$iv) {
                SimpleFunctionDescriptor result$iv;
                SimpleFunctionDescriptor simpleFunctionDescriptor2;
                SimpleFunctionDescriptor overrideCandidate = (SimpleFunctionDescriptor)element$iv;
                boolean bl = false;
                if (this.createSuspendView(overrideCandidate) != null) {
                    SimpleFunctionDescriptor simpleFunctionDescriptor3;
                    boolean bl2 = false;
                    boolean bl3 = false;
                    SimpleFunctionDescriptor suspendView = simpleFunctionDescriptor3;
                    boolean bl4 = false;
                    simpleFunctionDescriptor2 = this.doesOverride(suspendView, descriptor2) ? simpleFunctionDescriptor3 : null;
                } else {
                    simpleFunctionDescriptor2 = null;
                }
                if ((result$iv = simpleFunctionDescriptor2) == null) continue;
                simpleFunctionDescriptor = result$iv;
                break block4;
            }
            simpleFunctionDescriptor = null;
        }
        return simpleFunctionDescriptor;
    }

    private final SimpleFunctionDescriptor createHiddenCopyIfBuiltinAlreadyAccidentallyOverridden(SimpleFunctionDescriptor $this$createHiddenCopyIfBuiltinAlreadyAccidentallyOverridden, CallableDescriptor specialBuiltin, Collection<? extends SimpleFunctionDescriptor> alreadyDeclaredFunctions) {
        SimpleFunctionDescriptor simpleFunctionDescriptor;
        boolean bl;
        block5: {
            Iterable $this$none$iv = alreadyDeclaredFunctions;
            boolean $i$f$none = false;
            if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                bl = true;
            } else {
                for (Object element$iv : $this$none$iv) {
                    SimpleFunctionDescriptor it = (SimpleFunctionDescriptor)element$iv;
                    boolean bl2 = false;
                    if (!(Intrinsics.areEqual($this$createHiddenCopyIfBuiltinAlreadyAccidentallyOverridden, it) ^ true && it.getInitialSignatureDescriptor() == null && this.doesOverride(it, specialBuiltin))) continue;
                    bl = false;
                    break block5;
                }
                bl = true;
            }
        }
        if (bl) {
            simpleFunctionDescriptor = $this$createHiddenCopyIfBuiltinAlreadyAccidentallyOverridden;
        } else {
            SimpleFunctionDescriptor simpleFunctionDescriptor2 = $this$createHiddenCopyIfBuiltinAlreadyAccidentallyOverridden.newCopyBuilder().setHiddenToOvercomeSignatureClash().build();
            Intrinsics.checkNotNull(simpleFunctionDescriptor2);
            simpleFunctionDescriptor = simpleFunctionDescriptor2;
        }
        return simpleFunctionDescriptor;
    }

    /*
     * WARNING - void declaration
     */
    private final SimpleFunctionDescriptor createOverrideForBuiltinFunctionWithErasedParameterIfNeeded(FunctionDescriptor overridden, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> functions2) {
        SimpleFunctionDescriptor simpleFunctionDescriptor;
        Object v1;
        block4: {
            Name name = overridden.getName();
            Intrinsics.checkNotNullExpressionValue(name, "overridden.name");
            Iterable $this$firstOrNull$iv = functions2.invoke(name);
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                SimpleFunctionDescriptor it = (SimpleFunctionDescriptor)element$iv;
                boolean bl = false;
                if (!this.hasSameJvmDescriptorButDoesNotOverride(it, overridden)) continue;
                v1 = element$iv;
                break block4;
            }
            v1 = null;
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor2 = v1;
        if (simpleFunctionDescriptor2 != null) {
            Collection<ValueParameterData> collection;
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            SimpleFunctionDescriptor simpleFunctionDescriptor3 = simpleFunctionDescriptor2;
            boolean bl = false;
            boolean bl2 = false;
            SimpleFunctionDescriptor override = simpleFunctionDescriptor3;
            boolean bl3 = false;
            FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> copyBuilder = override.newCopyBuilder();
            boolean bl4 = false;
            boolean bl5 = false;
            FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> $this$apply = copyBuilder;
            boolean bl6 = false;
            List<ValueParameterDescriptor> list = overridden.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(list, "overridden.valueParameters");
            Iterable iterable = list;
            FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> copyBuilder2 = $this$apply;
            boolean $i$f$map = false;
            void var16_19 = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl7 = false;
                void v4 = it;
                Intrinsics.checkNotNullExpressionValue(v4, "it");
                KotlinType kotlinType = v4.getType();
                Intrinsics.checkNotNullExpressionValue(kotlinType, "it.type");
                ValueParameterData valueParameterData = new ValueParameterData(kotlinType, it.declaresDefaultValue());
                collection.add(valueParameterData);
            }
            collection = (List)destination$iv$iv;
            Collection collection2 = collection;
            List<ValueParameterDescriptor> list2 = override.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(list2, "override.valueParameters");
            copyBuilder2.setValueParameters(UtilKt.copyValueParameters(collection2, (Collection<? extends ValueParameterDescriptor>)list2, overridden));
            $this$apply.setSignatureChange();
            $this$apply.setPreserveSourceElement();
            simpleFunctionDescriptor = copyBuilder.build();
        } else {
            simpleFunctionDescriptor = null;
        }
        return simpleFunctionDescriptor;
    }

    /*
     * WARNING - void declaration
     */
    private final Set<SimpleFunctionDescriptor> getFunctionsFromSupertypes(Name name) {
        void var3_3;
        void $this$flatMapTo$iv;
        Iterable iterable = this.computeSupertypes();
        Collection destination$iv = new LinkedHashSet();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv : $this$flatMapTo$iv) {
            KotlinType it = (KotlinType)element$iv;
            boolean bl = false;
            Iterable list$iv = it.getMemberScope().getContributedFunctions(name, NoLookupLocation.WHEN_GET_SUPER_MEMBERS);
            CollectionsKt.addAll(destination$iv, list$iv);
        }
        return (Set)var3_3;
    }

    @Override
    protected void computeNonDeclaredProperties(@NotNull Name name, @NotNull Collection<PropertyDescriptor> result2) {
        Set<PropertyDescriptor> propertiesFromSupertypes2;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(result2, "result");
        if (this.jClass.isAnnotationType()) {
            this.computeAnnotationProperties(name, result2);
        }
        if ((propertiesFromSupertypes2 = this.getPropertiesFromSupertypes(name)).isEmpty()) {
            return;
        }
        SmartSet handledProperties = SmartSet.Companion.create();
        SmartSet propertiesOverridesFromSuperTypes = SmartSet.Companion.create();
        this.addPropertyOverrideByMethod(propertiesFromSupertypes2, result2, handledProperties, (Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>>)new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>(this){
            final /* synthetic */ LazyJavaClassMemberScope this$0;

            @NotNull
            public final Collection<SimpleFunctionDescriptor> invoke(@NotNull Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return LazyJavaClassMemberScope.access$searchMethodsByNameWithoutBuiltinMagic(this.this$0, it);
            }
            {
                this.this$0 = lazyJavaClassMemberScope;
                super(1);
            }
        });
        this.addPropertyOverrideByMethod(SetsKt.minus(propertiesFromSupertypes2, handledProperties), propertiesOverridesFromSuperTypes, null, (Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>>)new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>(this){
            final /* synthetic */ LazyJavaClassMemberScope this$0;

            @NotNull
            public final Collection<SimpleFunctionDescriptor> invoke(@NotNull Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return LazyJavaClassMemberScope.access$searchMethodsInSupertypesWithoutBuiltinMagic(this.this$0, it);
            }
            {
                this.this$0 = lazyJavaClassMemberScope;
                super(1);
            }
        });
        Collection<PropertyDescriptor> collection = DescriptorResolverUtils.resolveOverridesForNonStaticMembers(name, (Collection)SetsKt.plus(propertiesFromSupertypes2, propertiesOverridesFromSuperTypes), result2, this.getOwnerDescriptor(), this.getC().getComponents().getErrorReporter(), this.getC().getComponents().getKotlinTypeChecker().getOverridingUtil());
        Intrinsics.checkNotNullExpressionValue(collection, "resolveOverridesForNonSt\u2026rridingUtil\n            )");
        result2.addAll(collection);
    }

    private final void addPropertyOverrideByMethod(Set<? extends PropertyDescriptor> propertiesFromSupertypes2, Collection<PropertyDescriptor> result2, Set<PropertyDescriptor> handledProperties, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> functions2) {
        for (PropertyDescriptor propertyDescriptor : propertiesFromSupertypes2) {
            JavaPropertyDescriptor newProperty = this.createPropertyDescriptorByMethods(propertyDescriptor, functions2);
            if (newProperty == null) continue;
            result2.add(newProperty);
            Set<PropertyDescriptor> set = handledProperties;
            if (set == null) break;
            set.add(propertyDescriptor);
            break;
        }
    }

    private final void computeAnnotationProperties(Name name, Collection<PropertyDescriptor> result2) {
        JavaMethod javaMethod = (JavaMethod)CollectionsKt.singleOrNull((Iterable)((DeclaredMemberIndex)this.getDeclaredMemberIndex().invoke()).findMethodsByName(name));
        if (javaMethod == null) {
            return;
        }
        JavaMethod method = javaMethod;
        result2.add(LazyJavaClassMemberScope.createPropertyDescriptorWithDefaultGetter$default(this, method, null, Modality.FINAL, 2, null));
    }

    private final JavaPropertyDescriptor createPropertyDescriptorWithDefaultGetter(JavaMethod method, KotlinType givenType, Modality modality) {
        Annotations annotations2 = LazyJavaAnnotationsKt.resolveAnnotations(this.getC(), method);
        JavaPropertyDescriptor javaPropertyDescriptor = JavaPropertyDescriptor.create(this.getOwnerDescriptor(), annotations2, modality, method.getVisibility(), false, method.getName(), this.getC().getComponents().getSourceElementFactory().source(method), false);
        Intrinsics.checkNotNullExpressionValue(javaPropertyDescriptor, "JavaPropertyDescriptor.c\u2026inal = */ false\n        )");
        JavaPropertyDescriptor propertyDescriptor = javaPropertyDescriptor;
        PropertyGetterDescriptorImpl propertyGetterDescriptorImpl = DescriptorFactory.createDefaultGetter(propertyDescriptor, Annotations.Companion.getEMPTY());
        Intrinsics.checkNotNullExpressionValue(propertyGetterDescriptorImpl, "DescriptorFactory.create\u2026iptor, Annotations.EMPTY)");
        PropertyGetterDescriptorImpl getter = propertyGetterDescriptorImpl;
        propertyDescriptor.initialize(getter, null);
        KotlinType kotlinType = givenType;
        if (kotlinType == null) {
            kotlinType = this.computeMethodReturnType(method, ContextKt.childForMethod$default(this.getC(), propertyDescriptor, method, 0, 4, null));
        }
        KotlinType returnType = kotlinType;
        boolean bl = false;
        propertyDescriptor.setType(returnType, CollectionsKt.emptyList(), this.getDispatchReceiverParameter(), null);
        getter.initialize(returnType);
        return propertyDescriptor;
    }

    static /* synthetic */ JavaPropertyDescriptor createPropertyDescriptorWithDefaultGetter$default(LazyJavaClassMemberScope lazyJavaClassMemberScope, JavaMethod javaMethod, KotlinType kotlinType, Modality modality, int n, Object object) {
        if ((n & 2) != 0) {
            kotlinType = null;
        }
        return lazyJavaClassMemberScope.createPropertyDescriptorWithDefaultGetter(javaMethod, kotlinType, modality);
    }

    private final JavaPropertyDescriptor createPropertyDescriptorByMethods(PropertyDescriptor overriddenProperty, Function1<? super Name, ? extends Collection<? extends SimpleFunctionDescriptor>> functions2) {
        PropertySetterDescriptorImpl propertySetterDescriptorImpl;
        boolean bl;
        boolean bl2;
        SimpleFunctionDescriptor setterMethod;
        SimpleFunctionDescriptor simpleFunctionDescriptor;
        if (!this.doesClassOverridesProperty(overriddenProperty, functions2)) {
            return null;
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor2 = this.findGetterOverride(overriddenProperty, functions2);
        Intrinsics.checkNotNull(simpleFunctionDescriptor2);
        SimpleFunctionDescriptor getterMethod = simpleFunctionDescriptor2;
        if (overriddenProperty.isVar()) {
            SimpleFunctionDescriptor simpleFunctionDescriptor3 = this.findSetterOverride(overriddenProperty, functions2);
            simpleFunctionDescriptor = simpleFunctionDescriptor3;
            Intrinsics.checkNotNull(simpleFunctionDescriptor3);
        } else {
            simpleFunctionDescriptor = null;
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor4 = setterMethod = simpleFunctionDescriptor;
        if (simpleFunctionDescriptor4 != null) {
            SimpleFunctionDescriptor simpleFunctionDescriptor5 = simpleFunctionDescriptor4;
            bl2 = false;
            boolean bl3 = false;
            SimpleFunctionDescriptor it = simpleFunctionDescriptor5;
            boolean bl4 = false;
            bl = it.getModality() == getterMethod.getModality();
        } else {
            bl = true;
        }
        boolean bl5 = bl;
        bl2 = false;
        if (_Assertions.ENABLED && !bl5) {
            boolean $i$a$-assert-LazyJavaClassMemberScope$createPropertyDescriptorByMethods$32 = false;
            SimpleFunctionDescriptor simpleFunctionDescriptor6 = setterMethod;
            String $i$a$-assert-LazyJavaClassMemberScope$createPropertyDescriptorByMethods$32 = "Different accessors modalities when creating overrides for " + overriddenProperty + " in " + this.getOwnerDescriptor() + "for getter is " + (Object)((Object)getterMethod.getModality()) + ", but for setter is " + (Object)((Object)(simpleFunctionDescriptor6 != null ? simpleFunctionDescriptor6.getModality() : null));
            throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-LazyJavaClassMemberScope$createPropertyDescriptorByMethods$32));
        }
        JavaForKotlinOverridePropertyDescriptor propertyDescriptor = new JavaForKotlinOverridePropertyDescriptor(this.getOwnerDescriptor(), getterMethod, setterMethod, overriddenProperty);
        KotlinType kotlinType = getterMethod.getReturnType();
        Intrinsics.checkNotNull(kotlinType);
        bl2 = false;
        propertyDescriptor.setType(kotlinType, CollectionsKt.emptyList(), this.getDispatchReceiverParameter(), null);
        PropertyGetterDescriptorImpl $i$a$-assert-LazyJavaClassMemberScope$createPropertyDescriptorByMethods$32 = DescriptorFactory.createGetter(propertyDescriptor, getterMethod.getAnnotations(), false, false, false, getterMethod.getSource());
        boolean it = false;
        boolean bl6 = false;
        PropertyGetterDescriptorImpl $this$apply = $i$a$-assert-LazyJavaClassMemberScope$createPropertyDescriptorByMethods$32;
        boolean bl7 = false;
        $this$apply.setInitialSignatureDescriptor(getterMethod);
        $this$apply.initialize(propertyDescriptor.getType());
        PropertyGetterDescriptorImpl propertyGetterDescriptorImpl = $i$a$-assert-LazyJavaClassMemberScope$createPropertyDescriptorByMethods$32;
        Intrinsics.checkNotNullExpressionValue(propertyGetterDescriptorImpl, "DescriptorFactory.create\u2026escriptor.type)\n        }");
        PropertyGetterDescriptorImpl getter = propertyGetterDescriptorImpl;
        if (setterMethod != null) {
            List<ValueParameterDescriptor> list = setterMethod.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(list, "setterMethod.valueParameters");
            ValueParameterDescriptor valueParameterDescriptor = CollectionsKt.firstOrNull(list);
            if (valueParameterDescriptor == null) {
                throw (Throwable)((Object)new AssertionError((Object)("No parameter found for " + setterMethod)));
            }
            ValueParameterDescriptor parameter = valueParameterDescriptor;
            PropertySetterDescriptorImpl propertySetterDescriptorImpl2 = DescriptorFactory.createSetter(propertyDescriptor, setterMethod.getAnnotations(), parameter.getAnnotations(), false, false, false, setterMethod.getVisibility(), setterMethod.getSource());
            boolean bl8 = false;
            bl7 = false;
            PropertySetterDescriptorImpl $this$apply2 = propertySetterDescriptorImpl2;
            boolean bl9 = false;
            $this$apply2.setInitialSignatureDescriptor(setterMethod);
            propertySetterDescriptorImpl = propertySetterDescriptorImpl2;
        } else {
            propertySetterDescriptorImpl = null;
        }
        PropertySetterDescriptorImpl setter = propertySetterDescriptorImpl;
        JavaForKotlinOverridePropertyDescriptor javaForKotlinOverridePropertyDescriptor = propertyDescriptor;
        boolean bl10 = false;
        boolean bl11 = false;
        JavaForKotlinOverridePropertyDescriptor $this$apply3 = javaForKotlinOverridePropertyDescriptor;
        boolean bl12 = false;
        $this$apply3.initialize(getter, setter);
        return javaForKotlinOverridePropertyDescriptor;
    }

    /*
     * WARNING - void declaration
     */
    private final Set<PropertyDescriptor> getPropertiesFromSupertypes(Name name) {
        void $this$flatMapTo$iv$iv;
        Iterable $this$flatMap$iv = this.computeSupertypes();
        boolean $i$f$flatMap = false;
        Iterable iterable = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            void $this$mapTo$iv$iv;
            KotlinType it = (KotlinType)element$iv$iv;
            boolean bl = false;
            Iterable $this$map$iv = it.getMemberScope().getContributedVariables(name, NoLookupLocation.WHEN_GET_SUPER_MEMBERS);
            boolean $i$f$map = false;
            Iterable iterable2 = $this$map$iv;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void p;
                PropertyDescriptor propertyDescriptor = (PropertyDescriptor)item$iv$iv;
                Collection collection = destination$iv$iv2;
                boolean bl2 = false;
                void var21_21 = p;
                collection.add(var21_21);
            }
            Iterable list$iv$iv = (List)destination$iv$iv2;
            CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
        }
        return CollectionsKt.toSet((List)destination$iv$iv);
    }

    private final Collection<KotlinType> computeSupertypes() {
        if (this.skipRefinement) {
            TypeConstructor typeConstructor2 = this.getOwnerDescriptor().getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor2, "ownerDescriptor.typeConstructor");
            Collection<KotlinType> collection = typeConstructor2.getSupertypes();
            Intrinsics.checkNotNullExpressionValue(collection, "ownerDescriptor.typeConstructor.supertypes");
            return collection;
        }
        return this.getC().getComponents().getKotlinTypeChecker().getKotlinTypeRefiner().refineSupertypes(this.getOwnerDescriptor());
    }

    @Override
    @NotNull
    protected LazyJavaScope.MethodSignatureData resolveMethodSignature(@NotNull JavaMethod method, @NotNull List<? extends TypeParameterDescriptor> methodTypeParameters, @NotNull KotlinType returnType, @NotNull List<? extends ValueParameterDescriptor> valueParameters) {
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(methodTypeParameters, "methodTypeParameters");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        SignaturePropagator.PropagatedSignature propagatedSignature = this.getC().getComponents().getSignaturePropagator().resolvePropagatedSignature(method, this.getOwnerDescriptor(), returnType, null, valueParameters, methodTypeParameters);
        Intrinsics.checkNotNullExpressionValue(propagatedSignature, "c.components.signaturePr\u2026dTypeParameters\n        )");
        SignaturePropagator.PropagatedSignature propagated = propagatedSignature;
        KotlinType kotlinType = propagated.getReturnType();
        Intrinsics.checkNotNullExpressionValue(kotlinType, "propagated.returnType");
        KotlinType kotlinType2 = propagated.getReceiverType();
        List<ValueParameterDescriptor> list = propagated.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(list, "propagated.valueParameters");
        List<TypeParameterDescriptor> list2 = propagated.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(list2, "propagated.typeParameters");
        boolean bl = propagated.hasStableParameterNames();
        List<String> list3 = propagated.getErrors();
        Intrinsics.checkNotNullExpressionValue(list3, "propagated.errors");
        return new LazyJavaScope.MethodSignatureData(kotlinType, kotlinType2, list, list2, bl, list3);
    }

    private final boolean hasSameJvmDescriptorButDoesNotOverride(SimpleFunctionDescriptor $this$hasSameJvmDescriptorButDoesNotOverride, FunctionDescriptor builtinWithErasedParameters) {
        String string = MethodSignatureMappingKt.computeJvmDescriptor$default($this$hasSameJvmDescriptorButDoesNotOverride, false, false, 2, null);
        FunctionDescriptor functionDescriptor = builtinWithErasedParameters.getOriginal();
        Intrinsics.checkNotNullExpressionValue(functionDescriptor, "builtinWithErasedParameters.original");
        return Intrinsics.areEqual(string, MethodSignatureMappingKt.computeJvmDescriptor$default(functionDescriptor, false, false, 2, null)) && !this.doesOverride($this$hasSameJvmDescriptorButDoesNotOverride, builtinWithErasedParameters);
    }

    /*
     * WARNING - void declaration
     */
    private final JavaClassConstructorDescriptor resolveConstructor(JavaConstructor constructor) {
        Collection<TypeParameterDescriptor> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        ClassDescriptor classDescriptor = this.getOwnerDescriptor();
        JavaClassConstructorDescriptor javaClassConstructorDescriptor = JavaClassConstructorDescriptor.createJavaConstructor(classDescriptor, LazyJavaAnnotationsKt.resolveAnnotations(this.getC(), constructor), false, this.getC().getComponents().getSourceElementFactory().source(constructor));
        Intrinsics.checkNotNullExpressionValue(javaClassConstructorDescriptor, "JavaClassConstructorDesc\u2026ce(constructor)\n        )");
        JavaClassConstructorDescriptor constructorDescriptor = javaClassConstructorDescriptor;
        LazyJavaResolverContext c = ContextKt.childForMethod(this.getC(), constructorDescriptor, constructor, classDescriptor.getDeclaredTypeParameters().size());
        LazyJavaScope.ResolvedValueParameters valueParameters = this.resolveValueParameters(c, constructorDescriptor, constructor.getValueParameters());
        List<TypeParameterDescriptor> list = classDescriptor.getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(list, "classDescriptor.declaredTypeParameters");
        Iterable iterable = constructor.getTypeParameters();
        Collection collection2 = list;
        boolean $i$f$map = false;
        void var9_9 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            TypeParameterDescriptor typeParameterDescriptor;
            void p;
            JavaTypeParameter javaTypeParameter = (JavaTypeParameter)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            Intrinsics.checkNotNull(c.getTypeParameterResolver().resolveTypeParameter((JavaTypeParameter)p));
            collection.add(typeParameterDescriptor);
        }
        collection = (List)destination$iv$iv;
        List<TypeParameterDescriptor> constructorTypeParameters = CollectionsKt.plus(collection2, (Iterable)collection);
        constructorDescriptor.initialize(valueParameters.getDescriptors(), constructor.getVisibility(), constructorTypeParameters);
        constructorDescriptor.setHasStableParameterNames(false);
        constructorDescriptor.setHasSynthesizedParameterNames(valueParameters.getHasSynthesizedNames());
        constructorDescriptor.setReturnType(classDescriptor.getDefaultType());
        c.getComponents().getJavaResolverCache().recordConstructor(constructor, constructorDescriptor);
        return constructorDescriptor;
    }

    private final ClassConstructorDescriptor createDefaultConstructor() {
        boolean isAnnotation = this.jClass.isAnnotationType();
        if (!(!this.jClass.isInterface() && this.jClass.hasDefaultConstructor() || isAnnotation)) {
            return null;
        }
        ClassDescriptor classDescriptor = this.getOwnerDescriptor();
        JavaClassConstructorDescriptor javaClassConstructorDescriptor = JavaClassConstructorDescriptor.createJavaConstructor(classDescriptor, Annotations.Companion.getEMPTY(), true, this.getC().getComponents().getSourceElementFactory().source(this.jClass));
        Intrinsics.checkNotNullExpressionValue(javaClassConstructorDescriptor, "JavaClassConstructorDesc\u2026.source(jClass)\n        )");
        JavaClassConstructorDescriptor constructorDescriptor = javaClassConstructorDescriptor;
        List<ValueParameterDescriptor> valueParameters = isAnnotation ? this.createAnnotationConstructorParameters(constructorDescriptor) : Collections.emptyList();
        constructorDescriptor.setHasSynthesizedParameterNames(false);
        constructorDescriptor.initialize(valueParameters, this.getConstructorVisibility(classDescriptor));
        constructorDescriptor.setHasStableParameterNames(true);
        constructorDescriptor.setReturnType(classDescriptor.getDefaultType());
        this.getC().getComponents().getJavaResolverCache().recordConstructor(this.jClass, constructorDescriptor);
        return constructorDescriptor;
    }

    private final Visibility getConstructorVisibility(ClassDescriptor classDescriptor) {
        Visibility visibility = classDescriptor.getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "classDescriptor.visibility");
        Visibility visibility2 = visibility;
        if (Intrinsics.areEqual(visibility2, JavaVisibilities.PROTECTED_STATIC_VISIBILITY)) {
            Visibility visibility3 = JavaVisibilities.PROTECTED_AND_PACKAGE;
            Intrinsics.checkNotNullExpressionValue(visibility3, "JavaVisibilities.PROTECTED_AND_PACKAGE");
            return visibility3;
        }
        return visibility2;
    }

    /*
     * WARNING - void declaration
     */
    private final List<ValueParameterDescriptor> createAnnotationConstructorParameters(ClassConstructorDescriptorImpl constructor) {
        void methodsNamedValue;
        Collection<JavaMethod> methods2 = this.jClass.getMethods();
        ArrayList result2 = new ArrayList(methods2.size());
        JavaTypeAttributes attr = JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, true, null, 2, null);
        Iterable $this$partition$iv22 = methods2;
        boolean $i$f$partition = false;
        ArrayList first$iv = new ArrayList();
        ArrayList second$iv = new ArrayList();
        for (Object element$iv : $this$partition$iv22) {
            JavaMethod it = (JavaMethod)element$iv;
            boolean bl = false;
            if (Intrinsics.areEqual(it.getName(), JvmAnnotationNames.DEFAULT_ANNOTATION_MEMBER_NAME)) {
                first$iv.add(element$iv);
                continue;
            }
            second$iv.add(element$iv);
        }
        Pair pair = new Pair(first$iv, second$iv);
        List list = pair.component1();
        List otherMethods = pair.component2();
        boolean bl = methodsNamedValue.size() <= 1;
        boolean $this$partition$iv22 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean $i$a$-assert-LazyJavaClassMemberScope$createAnnotationConstructorParameters$32 = false;
            String $i$a$-assert-LazyJavaClassMemberScope$createAnnotationConstructorParameters$32 = "There can't be more than one method named 'value' in annotation class: " + this.jClass;
            throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-LazyJavaClassMemberScope$createAnnotationConstructorParameters$32));
        }
        JavaMethod methodNamedValue = (JavaMethod)CollectionsKt.firstOrNull(methodsNamedValue);
        if (methodNamedValue != null) {
            void parameterType;
            JavaType parameterNamedValueJavaType = methodNamedValue.getReturnType();
            Pair<KotlinType, KotlinType> pair2 = parameterNamedValueJavaType instanceof JavaArrayType ? new Pair<KotlinType, KotlinType>(this.getC().getTypeResolver().transformArrayType((JavaArrayType)parameterNamedValueJavaType, attr, true), this.getC().getTypeResolver().transformJavaType(((JavaArrayType)parameterNamedValueJavaType).getComponentType(), attr)) : new Pair<KotlinType, Object>(this.getC().getTypeResolver().transformJavaType(parameterNamedValueJavaType, attr), null);
            KotlinType $i$a$-assert-LazyJavaClassMemberScope$createAnnotationConstructorParameters$32 = (KotlinType)pair2.component1();
            KotlinType varargType = (KotlinType)pair2.component2();
            this.addAnnotationValueParameter(result2, constructor, 0, methodNamedValue, (KotlinType)parameterType, varargType);
        }
        boolean startIndex = methodNamedValue != null;
        boolean parameterType = false;
        for (JavaMethod method : (Iterable)otherMethods) {
            void index;
            KotlinType parameterType2 = this.getC().getTypeResolver().transformJavaType(method.getReturnType(), attr);
            this.addAnnotationValueParameter(result2, constructor, (int)(index + startIndex), method, parameterType2, null);
            ++index;
        }
        return result2;
    }

    /*
     * WARNING - void declaration
     */
    private final void addAnnotationValueParameter(List<ValueParameterDescriptor> $this$addAnnotationValueParameter, ConstructorDescriptor constructor, int index, JavaMethod method, KotlinType returnType, KotlinType varargElementType) {
        KotlinType kotlinType;
        List<ValueParameterDescriptor> list = $this$addAnnotationValueParameter;
        CallableDescriptor callableDescriptor = constructor;
        ValueParameterDescriptor valueParameterDescriptor = null;
        int n = index;
        Annotations annotations2 = Annotations.Companion.getEMPTY();
        Name name = method.getName();
        KotlinType kotlinType2 = TypeUtils.makeNotNullable(returnType);
        KotlinType kotlinType3 = kotlinType2;
        Intrinsics.checkNotNullExpressionValue(kotlinType2, "TypeUtils.makeNotNullable(returnType)");
        boolean bl = method.getHasAnnotationParameterDefaultValue();
        boolean bl2 = false;
        boolean bl3 = false;
        KotlinType kotlinType4 = varargElementType;
        if (kotlinType4 != null) {
            void it;
            KotlinType kotlinType5 = kotlinType4;
            boolean bl4 = false;
            boolean bl5 = false;
            KotlinType kotlinType6 = kotlinType5;
            boolean bl6 = bl3;
            boolean bl7 = bl2;
            boolean bl8 = bl;
            KotlinType kotlinType7 = kotlinType3;
            Name name2 = name;
            Annotations annotations3 = annotations2;
            int n2 = n;
            ValueParameterDescriptor valueParameterDescriptor2 = valueParameterDescriptor;
            CallableDescriptor callableDescriptor2 = callableDescriptor;
            List<ValueParameterDescriptor> list2 = list;
            boolean bl9 = false;
            KotlinType kotlinType8 = TypeUtils.makeNotNullable((KotlinType)it);
            list = list2;
            callableDescriptor = callableDescriptor2;
            valueParameterDescriptor = valueParameterDescriptor2;
            n = n2;
            annotations2 = annotations3;
            name = name2;
            kotlinType3 = kotlinType7;
            bl = bl8;
            bl2 = bl7;
            bl3 = bl6;
            kotlinType = kotlinType8;
        } else {
            kotlinType = null;
        }
        SourceElement sourceElement = this.getC().getComponents().getSourceElementFactory().source(method);
        KotlinType kotlinType9 = kotlinType;
        boolean bl10 = bl3;
        boolean bl11 = bl2;
        boolean bl12 = bl;
        KotlinType kotlinType10 = kotlinType3;
        Name name3 = name;
        Annotations annotations4 = annotations2;
        int n3 = n;
        ValueParameterDescriptor valueParameterDescriptor3 = valueParameterDescriptor;
        CallableDescriptor callableDescriptor3 = callableDescriptor;
        list.add(new ValueParameterDescriptorImpl(callableDescriptor3, valueParameterDescriptor3, n3, annotations4, name3, kotlinType10, bl12, bl11, bl10, kotlinType9, sourceElement));
    }

    @Override
    @Nullable
    protected ReceiverParameterDescriptor getDispatchReceiverParameter() {
        return DescriptorUtils.getDispatchReceiverParameterIfNeeded(this.getOwnerDescriptor());
    }

    @Override
    @Nullable
    public ClassifierDescriptor getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        this.recordLookup(name, location);
        Object object = (LazyJavaClassMemberScope)this.getMainScope();
        return object != null && (object = ((LazyJavaClassMemberScope)object).nestedClasses) != null && (object = (ClassDescriptorBase)object.invoke(name)) != null ? (ClassifierDescriptor)object : (ClassifierDescriptor)this.nestedClasses.invoke(name);
    }

    @Override
    @NotNull
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        this.recordLookup(name, location);
        return super.getContributedFunctions(name, location);
    }

    @Override
    @NotNull
    public Collection<PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        this.recordLookup(name, location);
        return super.getContributedVariables(name, location);
    }

    @Override
    @NotNull
    protected Set<Name> computeClassNames(@NotNull DescriptorKindFilter kindFilter, @Nullable Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        return SetsKt.plus((Set)this.nestedClassIndex.invoke(), ((Map)this.enumEntryIndex.invoke()).keySet());
    }

    @Override
    @NotNull
    protected Set<Name> computePropertyNames(@NotNull DescriptorKindFilter kindFilter, @Nullable Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        if (this.jClass.isAnnotationType()) {
            return this.getFunctionNames();
        }
        LinkedHashSet result2 = new LinkedHashSet(((DeclaredMemberIndex)this.getDeclaredMemberIndex().invoke()).getFieldNames());
        TypeConstructor typeConstructor2 = this.getOwnerDescriptor().getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "ownerDescriptor.typeConstructor");
        Collection<KotlinType> collection = typeConstructor2.getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collection, "ownerDescriptor.typeConstructor.supertypes");
        Iterable $this$flatMapTo$iv = collection;
        boolean $i$f$flatMapTo = false;
        for (Object element$iv : $this$flatMapTo$iv) {
            KotlinType supertype = (KotlinType)element$iv;
            boolean bl = false;
            Iterable list$iv = supertype.getMemberScope().getVariableNames();
            CollectionsKt.addAll((Collection)result2, list$iv);
        }
        return (Set)((Collection)result2);
    }

    @Override
    public void recordLookup(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        UtilsKt.record(this.getC().getComponents().getLookupTracker(), location, this.getOwnerDescriptor(), name);
    }

    @Override
    @NotNull
    public String toString() {
        return "Lazy Java member scope for " + this.jClass.getFqName();
    }

    @Override
    @NotNull
    protected ClassDescriptor getOwnerDescriptor() {
        return this.ownerDescriptor;
    }

    public LazyJavaClassMemberScope(@NotNull LazyJavaResolverContext c, @NotNull ClassDescriptor ownerDescriptor, @NotNull JavaClass jClass, boolean skipRefinement, @Nullable LazyJavaClassMemberScope mainScope) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(ownerDescriptor, "ownerDescriptor");
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        super(c, mainScope);
        this.ownerDescriptor = ownerDescriptor;
        this.jClass = jClass;
        this.skipRefinement = skipRefinement;
        this.constructors = c.getStorageManager().createLazyValue((Function0)new Function0<List<? extends ClassConstructorDescriptor>>(this, c){
            final /* synthetic */ LazyJavaClassMemberScope this$0;
            final /* synthetic */ LazyJavaResolverContext $c;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final List<ClassConstructorDescriptor> invoke() {
                Collection collection;
                Collection<JavaConstructor> constructors2 = LazyJavaClassMemberScope.access$getJClass$p(this.this$0).getConstructors();
                ArrayList<JavaClassConstructorDescriptor> result2 = new ArrayList<JavaClassConstructorDescriptor>(constructors2.size());
                for (JavaConstructor constructor : constructors2) {
                    JavaClassConstructorDescriptor descriptor2 = LazyJavaClassMemberScope.access$resolveConstructor(this.this$0, constructor);
                    result2.add(descriptor2);
                }
                SignatureEnhancement signatureEnhancement = this.$c.getComponents().getSignatureEnhancement();
                LazyJavaResolverContext lazyJavaResolverContext = this.$c;
                Collection $this$ifEmpty$iv = result2;
                boolean $i$f$ifEmpty = false;
                if ($this$ifEmpty$iv.isEmpty()) {
                    LazyJavaResolverContext lazyJavaResolverContext2 = lazyJavaResolverContext;
                    SignatureEnhancement signatureEnhancement2 = signatureEnhancement;
                    boolean bl = false;
                    List<ClassConstructorDescriptor> list = CollectionsKt.listOfNotNull(LazyJavaClassMemberScope.access$createDefaultConstructor(this.this$0));
                    signatureEnhancement = signatureEnhancement2;
                    lazyJavaResolverContext = lazyJavaResolverContext2;
                    collection = list;
                } else {
                    void var3_5;
                    collection = var3_5;
                }
                return CollectionsKt.toList((Iterable)signatureEnhancement.enhanceSignatures(lazyJavaResolverContext, collection));
            }
            {
                this.this$0 = lazyJavaClassMemberScope;
                this.$c = lazyJavaResolverContext;
                super(0);
            }
        });
        this.nestedClassIndex = c.getStorageManager().createLazyValue((Function0)new Function0<Set<? extends Name>>(this){
            final /* synthetic */ LazyJavaClassMemberScope this$0;

            @NotNull
            public final Set<Name> invoke() {
                return CollectionsKt.toSet((Iterable)LazyJavaClassMemberScope.access$getJClass$p(this.this$0).getInnerClassNames());
            }
            {
                this.this$0 = lazyJavaClassMemberScope;
                super(0);
            }
        });
        this.enumEntryIndex = c.getStorageManager().createLazyValue((Function0)new Function0<Map<Name, ? extends JavaField>>(this){
            final /* synthetic */ LazyJavaClassMemberScope this$0;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final Map<Name, JavaField> invoke() {
                void $this$associateByTo$iv$iv;
                void $this$filterTo$iv$iv;
                Iterable $this$filter$iv = LazyJavaClassMemberScope.access$getJClass$p(this.this$0).getFields();
                boolean $i$f$filter = false;
                Iterable iterable = $this$filter$iv;
                Iterable<E> destination$iv$iv = new ArrayList<E>();
                boolean $i$f$filterTo = false;
                for (T element$iv$iv : $this$filterTo$iv$iv) {
                    JavaField it = (JavaField)element$iv$iv;
                    boolean bl = false;
                    if (!it.isEnumEntry()) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                Iterable $this$associateBy$iv = (List)destination$iv$iv;
                boolean $i$f$associateBy = false;
                int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associateBy$iv, 10)), 16);
                destination$iv$iv = $this$associateBy$iv;
                Map destination$iv$iv2 = new LinkedHashMap<K, V>(capacity$iv);
                boolean $i$f$associateByTo = false;
                for (T element$iv$iv : $this$associateByTo$iv$iv) {
                    void f2;
                    JavaField bl = (JavaField)element$iv$iv;
                    Map map2 = destination$iv$iv2;
                    boolean bl2 = false;
                    Name name = f2.getName();
                    map2.put(name, element$iv$iv);
                }
                return destination$iv$iv2;
            }
            {
                this.this$0 = lazyJavaClassMemberScope;
                super(0);
            }
        });
        this.nestedClasses = c.getStorageManager().createMemoizedFunctionWithNullableValues((Function1)new Function1<Name, ClassDescriptorBase>(this, c){
            final /* synthetic */ LazyJavaClassMemberScope this$0;
            final /* synthetic */ LazyJavaResolverContext $c;

            @Nullable
            public final ClassDescriptorBase invoke(@NotNull Name name) {
                ClassDescriptorBase classDescriptorBase;
                Intrinsics.checkNotNullParameter(name, "name");
                if (!((Set)LazyJavaClassMemberScope.access$getNestedClassIndex$p(this.this$0).invoke()).contains(name)) {
                    EnumEntrySyntheticClassDescriptor enumEntrySyntheticClassDescriptor;
                    JavaField field = (JavaField)((Map)LazyJavaClassMemberScope.access$getEnumEntryIndex$p(this.this$0).invoke()).get(name);
                    if (field != null) {
                        NotNullLazyValue<Set<Name>> enumMemberNames2 = this.$c.getStorageManager().createLazyValue((Function0)new Function0<Set<? extends Name>>(this){
                            final /* synthetic */ nestedClasses.1 this$0;

                            @NotNull
                            public final Set<Name> invoke() {
                                return SetsKt.plus(this.this$0.this$0.getFunctionNames(), (Iterable)this.this$0.this$0.getVariableNames());
                            }
                            {
                                this.this$0 = var1_1;
                                super(0);
                            }
                        });
                        enumEntrySyntheticClassDescriptor = EnumEntrySyntheticClassDescriptor.create(this.$c.getStorageManager(), this.this$0.getOwnerDescriptor(), name, enumMemberNames2, LazyJavaAnnotationsKt.resolveAnnotations(this.$c, field), this.$c.getComponents().getSourceElementFactory().source(field));
                    } else {
                        enumEntrySyntheticClassDescriptor = null;
                    }
                    classDescriptorBase = enumEntrySyntheticClassDescriptor;
                } else {
                    LazyJavaClassDescriptor lazyJavaClassDescriptor;
                    JavaClassFinder javaClassFinder = this.$c.getComponents().getFinder();
                    ClassId classId = DescriptorUtilsKt.getClassId(this.this$0.getOwnerDescriptor());
                    Intrinsics.checkNotNull(classId);
                    ClassId classId2 = classId.createNestedClassId(name);
                    Intrinsics.checkNotNullExpressionValue(classId2, "ownerDescriptor.classId!\u2026createNestedClassId(name)");
                    JavaClass javaClass = javaClassFinder.findClass(new JavaClassFinder.Request(classId2, null, LazyJavaClassMemberScope.access$getJClass$p(this.this$0), 2, null));
                    if (javaClass != null) {
                        JavaClass javaClass2 = javaClass;
                        boolean bl = false;
                        boolean bl2 = false;
                        JavaClass it = javaClass2;
                        boolean bl3 = false;
                        LazyJavaClassDescriptor lazyJavaClassDescriptor2 = new LazyJavaClassDescriptor(this.$c, this.this$0.getOwnerDescriptor(), it, null, 8, null);
                        JavaClassesTracker javaClassesTracker = this.$c.getComponents().getJavaClassesTracker();
                        boolean bl4 = false;
                        boolean bl5 = false;
                        JavaClassDescriptor p1 = lazyJavaClassDescriptor2;
                        boolean bl6 = false;
                        javaClassesTracker.reportClass(p1);
                        lazyJavaClassDescriptor = lazyJavaClassDescriptor2;
                    } else {
                        lazyJavaClassDescriptor = null;
                    }
                    classDescriptorBase = lazyJavaClassDescriptor;
                }
                return classDescriptorBase;
            }
            {
                this.this$0 = lazyJavaClassMemberScope;
                this.$c = lazyJavaResolverContext;
                super(1);
            }
        });
    }

    public /* synthetic */ LazyJavaClassMemberScope(LazyJavaResolverContext lazyJavaResolverContext, ClassDescriptor classDescriptor, JavaClass javaClass, boolean bl, LazyJavaClassMemberScope lazyJavaClassMemberScope, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 0x10) != 0) {
            lazyJavaClassMemberScope = null;
        }
        this(lazyJavaResolverContext, classDescriptor, javaClass, bl, lazyJavaClassMemberScope);
    }

    public static final /* synthetic */ Collection access$searchMethodsByNameWithoutBuiltinMagic(LazyJavaClassMemberScope $this, Name name) {
        return $this.searchMethodsByNameWithoutBuiltinMagic(name);
    }

    public static final /* synthetic */ Collection access$searchMethodsInSupertypesWithoutBuiltinMagic(LazyJavaClassMemberScope $this, Name name) {
        return $this.searchMethodsInSupertypesWithoutBuiltinMagic(name);
    }

    public static final /* synthetic */ JavaClass access$getJClass$p(LazyJavaClassMemberScope $this) {
        return $this.jClass;
    }

    public static final /* synthetic */ JavaClassConstructorDescriptor access$resolveConstructor(LazyJavaClassMemberScope $this, JavaConstructor constructor) {
        return $this.resolveConstructor(constructor);
    }

    public static final /* synthetic */ ClassConstructorDescriptor access$createDefaultConstructor(LazyJavaClassMemberScope $this) {
        return $this.createDefaultConstructor();
    }

    public static final /* synthetic */ NotNullLazyValue access$getNestedClassIndex$p(LazyJavaClassMemberScope $this) {
        return $this.nestedClassIndex;
    }

    public static final /* synthetic */ NotNullLazyValue access$getEnumEntryIndex$p(LazyJavaClassMemberScope $this) {
        return $this.enumEntryIndex;
    }
}

