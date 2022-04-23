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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorNonRoot;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaAnnotationsKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeAttributes;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolverKt;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaValueParameter;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindExclude;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class LazyJavaScope
extends MemberScopeImpl {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    @NotNull
    private final NotNullLazyValue<Collection<DeclarationDescriptor>> allDescriptors;
    @NotNull
    private final NotNullLazyValue<DeclaredMemberIndex> declaredMemberIndex;
    private final MemoizedFunctionToNotNull<Name, Collection<SimpleFunctionDescriptor>> declaredFunctions;
    private final MemoizedFunctionToNullable<Name, PropertyDescriptor> declaredField;
    private final MemoizedFunctionToNotNull<Name, Collection<SimpleFunctionDescriptor>> functions;
    private final NotNullLazyValue functionNamesLazy$delegate;
    private final NotNullLazyValue propertyNamesLazy$delegate;
    private final NotNullLazyValue classNamesLazy$delegate;
    private final MemoizedFunctionToNotNull<Name, List<PropertyDescriptor>> properties;
    @NotNull
    private final LazyJavaResolverContext c;
    @Nullable
    private final LazyJavaScope mainScope;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaScope.class), "functionNamesLazy", "getFunctionNamesLazy()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaScope.class), "propertyNamesLazy", "getPropertyNamesLazy()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaScope.class), "classNamesLazy", "getClassNamesLazy()Ljava/util/Set;"))};
    }

    @NotNull
    protected abstract DeclarationDescriptor getOwnerDescriptor();

    @NotNull
    protected final NotNullLazyValue<Collection<DeclarationDescriptor>> getAllDescriptors() {
        return this.allDescriptors;
    }

    @NotNull
    protected final NotNullLazyValue<DeclaredMemberIndex> getDeclaredMemberIndex() {
        return this.declaredMemberIndex;
    }

    @NotNull
    protected abstract DeclaredMemberIndex computeMemberIndex();

    protected abstract void computeNonDeclaredFunctions(@NotNull Collection<SimpleFunctionDescriptor> var1, @NotNull Name var2);

    @Nullable
    protected abstract ReceiverParameterDescriptor getDispatchReceiverParameter();

    /*
     * WARNING - void declaration
     */
    private final void retainMostSpecificMethods(Set<SimpleFunctionDescriptor> $this$retainMostSpecificMethods) {
        void $this$groupByTo$iv$iv;
        Iterable $this$groupBy$iv = $this$retainMostSpecificMethods;
        boolean $i$f$groupBy = false;
        Iterable iterable = $this$groupBy$iv;
        Map destination$iv$iv = new LinkedHashMap();
        boolean $i$f$groupByTo = false;
        for (Object element$iv$iv : $this$groupByTo$iv$iv) {
            Object object;
            SimpleFunctionDescriptor it = (SimpleFunctionDescriptor)element$iv$iv;
            boolean bl = false;
            String key$iv$iv = MethodSignatureMappingKt.computeJvmDescriptor$default(it, false, false, 2, null);
            Map $this$getOrPut$iv$iv$iv = destination$iv$iv;
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
        Collection groups2 = destination$iv$iv.values();
        for (List group : groups2) {
            if (group.size() == 1) continue;
            Collection mostSpecificMethods2 = OverridingUtilsKt.selectMostSpecificInEachOverridableGroup(group, retainMostSpecificMethods.mostSpecificMethods.1.INSTANCE);
            $this$retainMostSpecificMethods.removeAll(group);
            $this$retainMostSpecificMethods.addAll(mostSpecificMethods2);
        }
    }

    protected boolean isVisibleAsFunction(@NotNull JavaMethodDescriptor $this$isVisibleAsFunction) {
        Intrinsics.checkNotNullParameter($this$isVisibleAsFunction, "$this$isVisibleAsFunction");
        return true;
    }

    @NotNull
    protected abstract MethodSignatureData resolveMethodSignature(@NotNull JavaMethod var1, @NotNull List<? extends TypeParameterDescriptor> var2, @NotNull KotlinType var3, @NotNull List<? extends ValueParameterDescriptor> var4);

    /*
     * WARNING - void declaration
     */
    @NotNull
    protected final JavaMethodDescriptor resolveMethodToFunctionDescriptor(@NotNull JavaMethod method) {
        DeclarationDescriptorNonRoot declarationDescriptorNonRoot;
        boolean bl;
        Object object;
        DeclarationDescriptorNonRoot declarationDescriptorNonRoot2;
        Object object2;
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(method, "method");
        Annotations annotations2 = LazyJavaAnnotationsKt.resolveAnnotations(this.c, method);
        JavaMethodDescriptor javaMethodDescriptor = JavaMethodDescriptor.createJavaMethod(this.getOwnerDescriptor(), annotations2, method.getName(), this.c.getComponents().getSourceElementFactory().source(method));
        Intrinsics.checkNotNullExpressionValue(javaMethodDescriptor, "JavaMethodDescriptor.cre\u2026.source(method)\n        )");
        JavaMethodDescriptor functionDescriptorImpl = javaMethodDescriptor;
        LazyJavaResolverContext c = ContextKt.childForMethod$default(this.c, functionDescriptorImpl, method, 0, 4, null);
        Iterable $this$map$iv = method.getTypeParameters();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void p;
            JavaTypeParameter javaTypeParameter = (JavaTypeParameter)item$iv$iv;
            object2 = destination$iv$iv;
            boolean bl2 = false;
            Intrinsics.checkNotNull(c.getTypeParameterResolver().resolveTypeParameter((JavaTypeParameter)p));
            object2.add(declarationDescriptorNonRoot2);
        }
        List methodTypeParameters = (List)destination$iv$iv;
        ResolvedValueParameters valueParameters = this.resolveValueParameters(c, functionDescriptorImpl, method.getValueParameters());
        KotlinType returnType = this.computeMethodReturnType(method, c);
        MethodSignatureData effectiveSignature = this.resolveMethodSignature(method, methodTypeParameters, returnType, valueParameters.getDescriptors());
        Object object3 = functionDescriptorImpl;
        KotlinType kotlinType = effectiveSignature.getReceiverType();
        if (kotlinType != null) {
            void it;
            Object item$iv$iv;
            object = kotlinType;
            bl = false;
            boolean bl3 = false;
            item$iv$iv = object;
            object2 = object3;
            boolean bl4 = false;
            declarationDescriptorNonRoot2 = DescriptorFactory.createExtensionReceiverParameterForCallable(functionDescriptorImpl, (KotlinType)it, Annotations.Companion.getEMPTY());
            object3 = object2;
            declarationDescriptorNonRoot = declarationDescriptorNonRoot2;
        } else {
            declarationDescriptorNonRoot = null;
        }
        ((JavaMethodDescriptor)object3).initialize((ReceiverParameterDescriptor)declarationDescriptorNonRoot, this.getDispatchReceiverParameter(), effectiveSignature.getTypeParameters(), effectiveSignature.getValueParameters(), effectiveSignature.getReturnType(), Modality.Companion.convertFromFlags(method.isAbstract(), !method.isFinal()), method.getVisibility(), effectiveSignature.getReceiverType() != null ? MapsKt.mapOf(TuplesKt.to(JavaMethodDescriptor.ORIGINAL_VALUE_PARAMETER_FOR_EXTENSION_RECEIVER, CollectionsKt.first(valueParameters.getDescriptors()))) : MapsKt.emptyMap());
        functionDescriptorImpl.setParameterNamesStatus(effectiveSignature.getHasStableParameterNames(), valueParameters.getHasSynthesizedNames());
        object = effectiveSignature.getErrors();
        bl = false;
        if (!object.isEmpty()) {
            c.getComponents().getSignaturePropagator().reportSignatureErrors(functionDescriptorImpl, effectiveSignature.getErrors());
        }
        return functionDescriptorImpl;
    }

    @NotNull
    protected final KotlinType computeMethodReturnType(@NotNull JavaMethod method, @NotNull LazyJavaResolverContext c) {
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(c, "c");
        boolean annotationMethod = method.getContainingClass().isAnnotationType();
        JavaTypeAttributes returnTypeAttrs = JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, annotationMethod, null, 2, null);
        return c.getTypeResolver().transformJavaType(method.getReturnType(), returnTypeAttrs);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @NotNull
    protected final ResolvedValueParameters resolveValueParameters(@NotNull LazyJavaResolverContext c, @NotNull FunctionDescriptor function, @NotNull List<? extends JavaValueParameter> jValueParameters) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(jValueParameters, "jValueParameters");
        synthesizedNames = false;
        var6_5 = false;
        usedNames = new LinkedHashSet<E>();
        $this$map$iv = CollectionsKt.withIndex((Iterable)jValueParameters);
        $i$f$map = false;
        var9_10 = $this$map$iv;
        destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        for (T item$iv$iv : $this$mapTo$iv$iv) {
            block13: {
                block12: {
                    var14_15 = (IndexedValue)item$iv$iv;
                    var26_29 = destination$iv$iv;
                    $i$a$-map-LazyJavaScope$resolveValueParameters$descriptors$1 = false;
                    var16_17 = $dstr$index$javaParameter.component1();
                    javaParameter = (JavaValueParameter)$dstr$index$javaParameter.component2();
                    annotations = LazyJavaAnnotationsKt.resolveAnnotations(c, javaParameter);
                    typeUsage = JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, false, null, 3, null);
                    v0 = JvmAnnotationNames.PARAMETER_NAME_FQ_NAME;
                    Intrinsics.checkNotNullExpressionValue(v0, "JvmAnnotationNames.PARAMETER_NAME_FQ_NAME");
                    v1 = annotations.findAnnotation(v0);
                    if (v1 == null || (v1 = DescriptorUtilsKt.firstArgument((AnnotationDescriptor)v1)) == null) ** GOTO lbl-1000
                    $this$safeAs$iv = v1;
                    $i$f$safeAs = false;
                    v2 = $this$safeAs$iv;
                    if (!(v2 instanceof StringValue)) {
                        v2 = null;
                    }
                    if ((v1 = (StringValue)v2) != null) {
                        v3 = (String)v1.getValue();
                    } else lbl-1000:
                    // 2 sources

                    {
                        v3 = parameterName = null;
                    }
                    if (javaParameter.isVararg()) {
                        v4 = javaParameter.getType();
                        if (!(v4 instanceof JavaArrayType)) {
                            v4 = null;
                        }
                        if ((JavaArrayType)v4 == null) {
                            throw (Throwable)new AssertionError((Object)("Vararg parameter should be an array: " + javaParameter));
                        }
                        outType = c.getTypeResolver().transformArrayType((JavaArrayType)paramType, typeUsage, true);
                        v5 /* !! */  = TuplesKt.to(outType, c.getModule().getBuiltIns().getArrayElementType(outType));
                    } else {
                        v5 /* !! */  = TuplesKt.to(c.getTypeResolver().transformJavaType(javaParameter.getType(), typeUsage), null);
                    }
                    var25_28 = v5 /* !! */ ;
                    $this$safeAs$iv = var25_28.component1();
                    varargElementType = var25_28.component2();
                    if (!Intrinsics.areEqual(function.getName().asString(), "equals") || jValueParameters.size() != 1 || !Intrinsics.areEqual(c.getModule().getBuiltIns().getNullableAnyType(), outType)) break block12;
                    v6 = Name.identifier("other");
                    break block13;
                }
                if (parameterName == null) ** GOTO lbl-1000
                paramType = parameterName;
                var24_27 = false;
                if (paramType.length() > 0 && usedNames.add(parameterName)) {
                    v6 = Name.identifier(parameterName);
                } else lbl-1000:
                // 2 sources

                {
                    if ((javaName = javaParameter.getName()) == null) {
                        synthesizedNames = true;
                    }
                    if ((v6 = javaName) == null) {
                        v7 = Name.identifier("" + 'p' + (int)index);
                        v6 = v7;
                        Intrinsics.checkNotNullExpressionValue(v7, "Name.identifier(\"p$index\")");
                    }
                }
            }
            Intrinsics.checkNotNullExpressionValue(v6, "if (function.name.asStri\u2026(\"p$index\")\n            }");
            name = v6;
            var27_30 = new ValueParameterDescriptorImpl(function, null, (int)index, annotations, name, (KotlinType)outType, false, false, false, varargElementType, c.getComponents().getSourceElementFactory().source(javaParameter));
            var26_29.add(var27_30);
        }
        descriptors = CollectionsKt.toList((List)destination$iv$iv);
        return new ResolvedValueParameters(descriptors, synthesizedNames);
    }

    private final Set<Name> getFunctionNamesLazy() {
        return (Set)StorageKt.getValue(this.functionNamesLazy$delegate, (Object)this, $$delegatedProperties[0]);
    }

    private final Set<Name> getPropertyNamesLazy() {
        return (Set)StorageKt.getValue(this.propertyNamesLazy$delegate, (Object)this, $$delegatedProperties[1]);
    }

    private final Set<Name> getClassNamesLazy() {
        return (Set)StorageKt.getValue(this.classNamesLazy$delegate, (Object)this, $$delegatedProperties[2]);
    }

    @Override
    @NotNull
    public Set<Name> getFunctionNames() {
        return this.getFunctionNamesLazy();
    }

    @Override
    @NotNull
    public Set<Name> getVariableNames() {
        return this.getPropertyNamesLazy();
    }

    @Override
    @NotNull
    public Set<Name> getClassifierNames() {
        return this.getClassNamesLazy();
    }

    @NotNull
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        if (!this.getFunctionNames().contains(name)) {
            return CollectionsKt.emptyList();
        }
        return (Collection)this.functions.invoke(name);
    }

    @NotNull
    protected abstract Set<Name> computeFunctionNames(@NotNull DescriptorKindFilter var1, @Nullable Function1<? super Name, Boolean> var2);

    protected abstract void computeNonDeclaredProperties(@NotNull Name var1, @NotNull Collection<PropertyDescriptor> var2);

    @NotNull
    protected abstract Set<Name> computePropertyNames(@NotNull DescriptorKindFilter var1, @Nullable Function1<? super Name, Boolean> var2);

    private final PropertyDescriptor resolveProperty(JavaField field) {
        PropertyDescriptorImpl propertyDescriptor = this.createPropertyDescriptor(field);
        propertyDescriptor.initialize(null, null, null, null);
        KotlinType propertyType = this.getPropertyType(field);
        boolean bl = false;
        propertyDescriptor.setType(propertyType, CollectionsKt.emptyList(), this.getDispatchReceiverParameter(), null);
        if (DescriptorUtils.shouldRecordInitializerForProperty(propertyDescriptor, propertyDescriptor.getType())) {
            propertyDescriptor.setCompileTimeInitializer(this.c.getStorageManager().createNullableLazyValue(new Function0<ConstantValue<?>>(this, field, propertyDescriptor){
                final /* synthetic */ LazyJavaScope this$0;
                final /* synthetic */ JavaField $field;
                final /* synthetic */ PropertyDescriptorImpl $propertyDescriptor;

                @Nullable
                public final ConstantValue<?> invoke() {
                    return this.this$0.getC().getComponents().getJavaPropertyInitializerEvaluator().getInitializerConstant(this.$field, this.$propertyDescriptor);
                }
                {
                    this.this$0 = lazyJavaScope;
                    this.$field = javaField;
                    this.$propertyDescriptor = propertyDescriptorImpl;
                    super(0);
                }
            }));
        }
        this.c.getComponents().getJavaResolverCache().recordField(field, propertyDescriptor);
        return propertyDescriptor;
    }

    private final PropertyDescriptorImpl createPropertyDescriptor(JavaField field) {
        boolean isVar = !field.isFinal();
        Annotations annotations2 = LazyJavaAnnotationsKt.resolveAnnotations(this.c, field);
        JavaPropertyDescriptor javaPropertyDescriptor = JavaPropertyDescriptor.create(this.getOwnerDescriptor(), annotations2, Modality.FINAL, field.getVisibility(), isVar, field.getName(), this.c.getComponents().getSourceElementFactory().source(field), this.isFinalStatic(field));
        Intrinsics.checkNotNullExpressionValue(javaPropertyDescriptor, "JavaPropertyDescriptor.c\u2026d.isFinalStatic\n        )");
        return javaPropertyDescriptor;
    }

    private final boolean isFinalStatic(JavaField $this$isFinalStatic) {
        return $this$isFinalStatic.isFinal() && $this$isFinalStatic.isStatic();
    }

    private final KotlinType getPropertyType(JavaField field) {
        boolean isNotNullable;
        KotlinType propertyType = this.c.getTypeResolver().transformJavaType(field.getType(), JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, false, null, 3, null));
        boolean bl = isNotNullable = (KotlinBuiltIns.isPrimitiveType(propertyType) || KotlinBuiltIns.isString(propertyType)) && this.isFinalStatic(field) && field.getHasConstantNotNullInitializer();
        if (isNotNullable) {
            KotlinType kotlinType = TypeUtils.makeNotNullable(propertyType);
            Intrinsics.checkNotNullExpressionValue(kotlinType, "TypeUtils.makeNotNullable(propertyType)");
            return kotlinType;
        }
        return propertyType;
    }

    @NotNull
    public Collection<PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        if (!this.getVariableNames().contains(name)) {
            return CollectionsKt.emptyList();
        }
        return (Collection)this.properties.invoke(name);
    }

    @Override
    @NotNull
    public Collection<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return (Collection)this.allDescriptors.invoke();
    }

    @NotNull
    protected final List<DeclarationDescriptor> computeDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        NoLookupLocation location = NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS;
        LinkedHashSet<CallableMemberDescriptor> result2 = new LinkedHashSet<CallableMemberDescriptor>();
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getCLASSIFIERS_MASK())) {
            for (Name name : this.computeClassNames(kindFilter, nameFilter)) {
                if (!nameFilter.invoke(name).booleanValue()) continue;
                kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull((Collection)result2, this.getContributedClassifier(name, location));
            }
        }
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getFUNCTIONS_MASK()) && !kindFilter.getExcludes().contains(DescriptorKindExclude.NonExtensions.INSTANCE)) {
            for (Name name : this.computeFunctionNames(kindFilter, nameFilter)) {
                if (!nameFilter.invoke(name).booleanValue()) continue;
                result2.addAll(this.getContributedFunctions(name, location));
            }
        }
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getVARIABLES_MASK()) && !kindFilter.getExcludes().contains(DescriptorKindExclude.NonExtensions.INSTANCE)) {
            for (Name name : this.computePropertyNames(kindFilter, nameFilter)) {
                if (!nameFilter.invoke(name).booleanValue()) continue;
                result2.addAll(this.getContributedVariables(name, location));
            }
        }
        return CollectionsKt.toList((Iterable)result2);
    }

    @NotNull
    protected abstract Set<Name> computeClassNames(@NotNull DescriptorKindFilter var1, @Nullable Function1<? super Name, Boolean> var2);

    @NotNull
    public String toString() {
        return "Lazy scope for " + this.getOwnerDescriptor();
    }

    @NotNull
    protected final LazyJavaResolverContext getC() {
        return this.c;
    }

    @Nullable
    protected final LazyJavaScope getMainScope() {
        return this.mainScope;
    }

    public LazyJavaScope(@NotNull LazyJavaResolverContext c, @Nullable LazyJavaScope mainScope) {
        Intrinsics.checkNotNullParameter(c, "c");
        this.c = c;
        this.mainScope = mainScope;
        boolean bl = false;
        this.allDescriptors = this.c.getStorageManager().createRecursionTolerantLazyValue((Function0)new Function0<Collection<? extends DeclarationDescriptor>>(this){
            final /* synthetic */ LazyJavaScope this$0;

            @NotNull
            public final Collection<DeclarationDescriptor> invoke() {
                return this.this$0.computeDescriptors(DescriptorKindFilter.ALL, MemberScope.Companion.getALL_NAME_FILTER());
            }
            {
                this.this$0 = lazyJavaScope;
                super(0);
            }
        }, CollectionsKt.emptyList());
        this.declaredMemberIndex = this.c.getStorageManager().createLazyValue((Function0)new Function0<DeclaredMemberIndex>(this){
            final /* synthetic */ LazyJavaScope this$0;

            @NotNull
            public final DeclaredMemberIndex invoke() {
                return this.this$0.computeMemberIndex();
            }
            {
                this.this$0 = lazyJavaScope;
                super(0);
            }
        });
        this.declaredFunctions = this.c.getStorageManager().createMemoizedFunction((Function1)new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>(this){
            final /* synthetic */ LazyJavaScope this$0;

            @NotNull
            public final Collection<SimpleFunctionDescriptor> invoke(@NotNull Name name) {
                Intrinsics.checkNotNullParameter(name, "name");
                if (this.this$0.getMainScope() != null) {
                    return (Collection)LazyJavaScope.access$getDeclaredFunctions$p(this.this$0.getMainScope()).invoke(name);
                }
                boolean bl = false;
                List result2 = new ArrayList<E>();
                for (JavaMethod method : ((DeclaredMemberIndex)this.this$0.getDeclaredMemberIndex().invoke()).findMethodsByName(name)) {
                    JavaMethodDescriptor descriptor2 = this.this$0.resolveMethodToFunctionDescriptor(method);
                    if (!this.this$0.isVisibleAsFunction(descriptor2)) continue;
                    this.this$0.getC().getComponents().getJavaResolverCache().recordMethod(method, descriptor2);
                    result2.add(descriptor2);
                }
                return result2;
            }
            {
                this.this$0 = lazyJavaScope;
                super(1);
            }
        });
        this.declaredField = this.c.getStorageManager().createMemoizedFunctionWithNullableValues((Function1)new Function1<Name, PropertyDescriptor>(this){
            final /* synthetic */ LazyJavaScope this$0;

            @Nullable
            public final PropertyDescriptor invoke(@NotNull Name name) {
                Intrinsics.checkNotNullParameter(name, "name");
                if (this.this$0.getMainScope() != null) {
                    return (PropertyDescriptor)LazyJavaScope.access$getDeclaredField$p(this.this$0.getMainScope()).invoke(name);
                }
                JavaField field = ((DeclaredMemberIndex)this.this$0.getDeclaredMemberIndex().invoke()).findFieldByName(name);
                return field != null && !field.isEnumEntry() ? LazyJavaScope.access$resolveProperty(this.this$0, field) : null;
            }
            {
                this.this$0 = lazyJavaScope;
                super(1);
            }
        });
        this.functions = this.c.getStorageManager().createMemoizedFunction((Function1)new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>(this){
            final /* synthetic */ LazyJavaScope this$0;

            @NotNull
            public final Collection<SimpleFunctionDescriptor> invoke(@NotNull Name name) {
                Intrinsics.checkNotNullParameter(name, "name");
                LinkedHashSet<E> result2 = new LinkedHashSet<E>((Collection)LazyJavaScope.access$getDeclaredFunctions$p(this.this$0).invoke(name));
                LazyJavaScope.access$retainMostSpecificMethods(this.this$0, result2);
                this.this$0.computeNonDeclaredFunctions((Collection<SimpleFunctionDescriptor>)result2, name);
                return CollectionsKt.toList((Iterable)this.this$0.getC().getComponents().getSignatureEnhancement().enhanceSignatures(this.this$0.getC(), (Collection)result2));
            }
            {
                this.this$0 = lazyJavaScope;
                super(1);
            }
        });
        this.functionNamesLazy$delegate = this.c.getStorageManager().createLazyValue((Function0)new Function0<Set<? extends Name>>(this){
            final /* synthetic */ LazyJavaScope this$0;

            @NotNull
            public final Set<Name> invoke() {
                return this.this$0.computeFunctionNames(DescriptorKindFilter.FUNCTIONS, null);
            }
            {
                this.this$0 = lazyJavaScope;
                super(0);
            }
        });
        this.propertyNamesLazy$delegate = this.c.getStorageManager().createLazyValue((Function0)new Function0<Set<? extends Name>>(this){
            final /* synthetic */ LazyJavaScope this$0;

            @NotNull
            public final Set<Name> invoke() {
                return this.this$0.computePropertyNames(DescriptorKindFilter.VARIABLES, null);
            }
            {
                this.this$0 = lazyJavaScope;
                super(0);
            }
        });
        this.classNamesLazy$delegate = this.c.getStorageManager().createLazyValue((Function0)new Function0<Set<? extends Name>>(this){
            final /* synthetic */ LazyJavaScope this$0;

            @NotNull
            public final Set<Name> invoke() {
                return this.this$0.computeClassNames(DescriptorKindFilter.CLASSIFIERS, null);
            }
            {
                this.this$0 = lazyJavaScope;
                super(0);
            }
        });
        this.properties = this.c.getStorageManager().createMemoizedFunction((Function1)new Function1<Name, List<? extends PropertyDescriptor>>(this){
            final /* synthetic */ LazyJavaScope this$0;

            @NotNull
            public final List<PropertyDescriptor> invoke(@NotNull Name name) {
                Intrinsics.checkNotNullParameter(name, "name");
                ArrayList<E> properties2 = new ArrayList<E>();
                kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull((Collection)properties2, LazyJavaScope.access$getDeclaredField$p(this.this$0).invoke(name));
                this.this$0.computeNonDeclaredProperties(name, (Collection<PropertyDescriptor>)properties2);
                return DescriptorUtils.isAnnotationClass(this.this$0.getOwnerDescriptor()) ? CollectionsKt.toList((Iterable)properties2) : CollectionsKt.toList((Iterable)this.this$0.getC().getComponents().getSignatureEnhancement().enhanceSignatures(this.this$0.getC(), (Collection)properties2));
            }
            {
                this.this$0 = lazyJavaScope;
                super(1);
            }
        });
    }

    public /* synthetic */ LazyJavaScope(LazyJavaResolverContext lazyJavaResolverContext, LazyJavaScope lazyJavaScope, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            lazyJavaScope = null;
        }
        this(lazyJavaResolverContext, lazyJavaScope);
    }

    public static final /* synthetic */ MemoizedFunctionToNotNull access$getDeclaredFunctions$p(LazyJavaScope $this) {
        return $this.declaredFunctions;
    }

    public static final /* synthetic */ MemoizedFunctionToNullable access$getDeclaredField$p(LazyJavaScope $this) {
        return $this.declaredField;
    }

    public static final /* synthetic */ PropertyDescriptor access$resolveProperty(LazyJavaScope $this, JavaField field) {
        return $this.resolveProperty(field);
    }

    public static final /* synthetic */ void access$retainMostSpecificMethods(LazyJavaScope $this, Set $this$access_u24retainMostSpecificMethods) {
        $this.retainMostSpecificMethods($this$access_u24retainMostSpecificMethods);
    }

    protected static final class MethodSignatureData {
        @NotNull
        private final KotlinType returnType;
        @Nullable
        private final KotlinType receiverType;
        @NotNull
        private final List<ValueParameterDescriptor> valueParameters;
        @NotNull
        private final List<TypeParameterDescriptor> typeParameters;
        private final boolean hasStableParameterNames;
        @NotNull
        private final List<String> errors;

        @NotNull
        public final KotlinType getReturnType() {
            return this.returnType;
        }

        @Nullable
        public final KotlinType getReceiverType() {
            return this.receiverType;
        }

        @NotNull
        public final List<ValueParameterDescriptor> getValueParameters() {
            return this.valueParameters;
        }

        @NotNull
        public final List<TypeParameterDescriptor> getTypeParameters() {
            return this.typeParameters;
        }

        public final boolean getHasStableParameterNames() {
            return this.hasStableParameterNames;
        }

        @NotNull
        public final List<String> getErrors() {
            return this.errors;
        }

        public MethodSignatureData(@NotNull KotlinType returnType, @Nullable KotlinType receiverType, @NotNull List<? extends ValueParameterDescriptor> valueParameters, @NotNull List<? extends TypeParameterDescriptor> typeParameters2, boolean hasStableParameterNames, @NotNull List<String> errors) {
            Intrinsics.checkNotNullParameter(returnType, "returnType");
            Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
            Intrinsics.checkNotNullParameter(typeParameters2, "typeParameters");
            Intrinsics.checkNotNullParameter(errors, "errors");
            this.returnType = returnType;
            this.receiverType = receiverType;
            this.valueParameters = valueParameters;
            this.typeParameters = typeParameters2;
            this.hasStableParameterNames = hasStableParameterNames;
            this.errors = errors;
        }

        @NotNull
        public String toString() {
            return "MethodSignatureData(returnType=" + this.returnType + ", receiverType=" + this.receiverType + ", valueParameters=" + this.valueParameters + ", typeParameters=" + this.typeParameters + ", hasStableParameterNames=" + this.hasStableParameterNames + ", errors=" + this.errors + ")";
        }

        public int hashCode() {
            KotlinType kotlinType = this.returnType;
            KotlinType kotlinType2 = this.receiverType;
            List<ValueParameterDescriptor> list = this.valueParameters;
            List<TypeParameterDescriptor> list2 = this.typeParameters;
            int n = ((((kotlinType != null ? ((Object)kotlinType).hashCode() : 0) * 31 + (kotlinType2 != null ? ((Object)kotlinType2).hashCode() : 0)) * 31 + (list != null ? ((Object)list).hashCode() : 0)) * 31 + (list2 != null ? ((Object)list2).hashCode() : 0)) * 31;
            int n2 = this.hasStableParameterNames ? 1 : 0;
            if (n2 != 0) {
                n2 = 1;
            }
            List<String> list3 = this.errors;
            return (n + n2) * 31 + (list3 != null ? ((Object)list3).hashCode() : 0);
        }

        public boolean equals(@Nullable Object object) {
            block3: {
                block2: {
                    if (this == object) break block2;
                    if (!(object instanceof MethodSignatureData)) break block3;
                    MethodSignatureData methodSignatureData = (MethodSignatureData)object;
                    if (!Intrinsics.areEqual(this.returnType, methodSignatureData.returnType) || !Intrinsics.areEqual(this.receiverType, methodSignatureData.receiverType) || !Intrinsics.areEqual(this.valueParameters, methodSignatureData.valueParameters) || !Intrinsics.areEqual(this.typeParameters, methodSignatureData.typeParameters) || this.hasStableParameterNames != methodSignatureData.hasStableParameterNames || !Intrinsics.areEqual(this.errors, methodSignatureData.errors)) break block3;
                }
                return true;
            }
            return false;
        }
    }

    protected static final class ResolvedValueParameters {
        @NotNull
        private final List<ValueParameterDescriptor> descriptors;
        private final boolean hasSynthesizedNames;

        @NotNull
        public final List<ValueParameterDescriptor> getDescriptors() {
            return this.descriptors;
        }

        public final boolean getHasSynthesizedNames() {
            return this.hasSynthesizedNames;
        }

        public ResolvedValueParameters(@NotNull List<? extends ValueParameterDescriptor> descriptors, boolean hasSynthesizedNames) {
            Intrinsics.checkNotNullParameter(descriptors, "descriptors");
            this.descriptors = descriptors;
            this.hasSynthesizedNames = hasSynthesizedNames;
        }
    }
}

