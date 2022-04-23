/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.CloneableClassScope;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.FallbackBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings$WhenMappings;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.MappingUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModalityKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilterKt;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PackageFragmentDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaResolverCache;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaClassMemberScope;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.NameResolverUtilKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import kotlin.reflect.jvm.internal.impl.storage.CacheWithNotNullValues;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.LazyWrappedType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.utils.DFS;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;
import org.jetbrains.annotations.NotNull;

public class JvmBuiltInsSettings
implements AdditionalClassPartsProvider,
PlatformDependentDeclarationFilter {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private final JavaToKotlinClassMap j2kClassMap;
    private final Lazy ownerModuleDescriptor$delegate;
    private final Lazy isAdditionalBuiltInsFeatureSupported$delegate;
    private final KotlinType mockSerializableType;
    private final NotNullLazyValue cloneableType$delegate;
    private final CacheWithNotNullValues<FqName, ClassDescriptor> javaAnalogueClassesWithCustomSupertypeCache;
    private final NotNullLazyValue notConsideredDeprecation$delegate;
    private final ModuleDescriptor moduleDescriptor;
    @NotNull
    private static final Set<String> DROP_LIST_METHOD_SIGNATURES;
    @NotNull
    private static final Set<String> BLACK_LIST_METHOD_SIGNATURES;
    @NotNull
    private static final Set<String> WHITE_LIST_METHOD_SIGNATURES;
    @NotNull
    private static final Set<String> MUTABLE_METHOD_SIGNATURES;
    @NotNull
    private static final Set<String> BLACK_LIST_CONSTRUCTOR_SIGNATURES;
    @NotNull
    private static final Set<String> WHITE_LIST_CONSTRUCTOR_SIGNATURES;
    public static final Companion Companion;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBuiltInsSettings.class), "cloneableType", "getCloneableType()Lorg/jetbrains/kotlin/types/SimpleType;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBuiltInsSettings.class), "notConsideredDeprecation", "getNotConsideredDeprecation()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;"))};
        Companion = new Companion(null);
        DROP_LIST_METHOD_SIGNATURES = SetsKt.plus((Set)SignatureBuildingComponents.INSTANCE.inJavaUtil("Collection", "toArray()[Ljava/lang/Object;", "toArray([Ljava/lang/Object;)[Ljava/lang/Object;"), "java/lang/annotation/Annotation.annotationType()Ljava/lang/Class;");
        boolean $i$f$signatures = false;
        SignatureBuildingComponents signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
        boolean bl = false;
        boolean bl2 = false;
        SignatureBuildingComponents $this$signatures = signatureBuildingComponents;
        boolean bl3 = false;
        BLACK_LIST_METHOD_SIGNATURES = SetsKt.plus(SetsKt.plus(SetsKt.plus(SetsKt.plus(SetsKt.plus(JvmBuiltInsSettings.Companion.buildPrimitiveValueMethodsSet(), (Iterable)$this$signatures.inJavaUtil("List", "sort(Ljava/util/Comparator;)V")), (Iterable)$this$signatures.inJavaLang("String", "codePointAt(I)I", "codePointBefore(I)I", "codePointCount(II)I", "compareToIgnoreCase(Ljava/lang/String;)I", "concat(Ljava/lang/String;)Ljava/lang/String;", "contains(Ljava/lang/CharSequence;)Z", "contentEquals(Ljava/lang/CharSequence;)Z", "contentEquals(Ljava/lang/StringBuffer;)Z", "endsWith(Ljava/lang/String;)Z", "equalsIgnoreCase(Ljava/lang/String;)Z", "getBytes()[B", "getBytes(II[BI)V", "getBytes(Ljava/lang/String;)[B", "getBytes(Ljava/nio/charset/Charset;)[B", "getChars(II[CI)V", "indexOf(I)I", "indexOf(II)I", "indexOf(Ljava/lang/String;)I", "indexOf(Ljava/lang/String;I)I", "intern()Ljava/lang/String;", "isEmpty()Z", "lastIndexOf(I)I", "lastIndexOf(II)I", "lastIndexOf(Ljava/lang/String;)I", "lastIndexOf(Ljava/lang/String;I)I", "matches(Ljava/lang/String;)Z", "offsetByCodePoints(II)I", "regionMatches(ILjava/lang/String;II)Z", "regionMatches(ZILjava/lang/String;II)Z", "replaceAll(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", "replace(CC)Ljava/lang/String;", "replaceFirst(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", "replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;", "split(Ljava/lang/String;I)[Ljava/lang/String;", "split(Ljava/lang/String;)[Ljava/lang/String;", "startsWith(Ljava/lang/String;I)Z", "startsWith(Ljava/lang/String;)Z", "substring(II)Ljava/lang/String;", "substring(I)Ljava/lang/String;", "toCharArray()[C", "toLowerCase()Ljava/lang/String;", "toLowerCase(Ljava/util/Locale;)Ljava/lang/String;", "toUpperCase()Ljava/lang/String;", "toUpperCase(Ljava/util/Locale;)Ljava/lang/String;", "trim()Ljava/lang/String;", "isBlank()Z", "lines()Ljava/util/stream/Stream;", "repeat(I)Ljava/lang/String;")), (Iterable)$this$signatures.inJavaLang("Double", "isInfinite()Z", "isNaN()Z")), (Iterable)$this$signatures.inJavaLang("Float", "isInfinite()Z", "isNaN()Z")), (Iterable)$this$signatures.inJavaLang("Enum", "getDeclaringClass()Ljava/lang/Class;", "finalize()V"));
        $i$f$signatures = false;
        signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
        bl = false;
        bl2 = false;
        $this$signatures = signatureBuildingComponents;
        boolean bl4 = false;
        WHITE_LIST_METHOD_SIGNATURES = SetsKt.plus(SetsKt.plus(SetsKt.plus(SetsKt.plus(SetsKt.plus(SetsKt.plus((Set)$this$signatures.inJavaLang("CharSequence", "codePoints()Ljava/util/stream/IntStream;", "chars()Ljava/util/stream/IntStream;"), (Iterable)$this$signatures.inJavaUtil("Iterator", "forEachRemaining(Ljava/util/function/Consumer;)V")), (Iterable)$this$signatures.inJavaLang("Iterable", "forEach(Ljava/util/function/Consumer;)V", "spliterator()Ljava/util/Spliterator;")), (Iterable)$this$signatures.inJavaLang("Throwable", "setStackTrace([Ljava/lang/StackTraceElement;)V", "fillInStackTrace()Ljava/lang/Throwable;", "getLocalizedMessage()Ljava/lang/String;", "printStackTrace()V", "printStackTrace(Ljava/io/PrintStream;)V", "printStackTrace(Ljava/io/PrintWriter;)V", "getStackTrace()[Ljava/lang/StackTraceElement;", "initCause(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "getSuppressed()[Ljava/lang/Throwable;", "addSuppressed(Ljava/lang/Throwable;)V")), (Iterable)$this$signatures.inJavaUtil("Collection", "spliterator()Ljava/util/Spliterator;", "parallelStream()Ljava/util/stream/Stream;", "stream()Ljava/util/stream/Stream;", "removeIf(Ljava/util/function/Predicate;)Z")), (Iterable)$this$signatures.inJavaUtil("List", "replaceAll(Ljava/util/function/UnaryOperator;)V")), (Iterable)$this$signatures.inJavaUtil("Map", "getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "forEach(Ljava/util/function/BiConsumer;)V", "replaceAll(Ljava/util/function/BiFunction;)V", "merge(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", "computeIfPresent(Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", "putIfAbsent(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "replace(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Z", "replace(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;", "compute(Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;"));
        $i$f$signatures = false;
        signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
        bl = false;
        bl2 = false;
        $this$signatures = signatureBuildingComponents;
        boolean bl5 = false;
        MUTABLE_METHOD_SIGNATURES = SetsKt.plus(SetsKt.plus((Set)$this$signatures.inJavaUtil("Collection", "removeIf(Ljava/util/function/Predicate;)Z"), (Iterable)$this$signatures.inJavaUtil("List", "replaceAll(Ljava/util/function/UnaryOperator;)V", "sort(Ljava/util/Comparator;)V")), (Iterable)$this$signatures.inJavaUtil("Map", "computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;", "computeIfPresent(Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", "compute(Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", "merge(Ljava/lang/Object;Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;", "putIfAbsent(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "remove(Ljava/lang/Object;Ljava/lang/Object;)Z", "replaceAll(Ljava/util/function/BiFunction;)V", "replace(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "replace(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Z"));
        $i$f$signatures = false;
        signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
        bl = false;
        bl2 = false;
        $this$signatures = signatureBuildingComponents;
        boolean bl6 = false;
        String[] stringArray = $this$signatures.constructors("D");
        String[] stringArray2 = $this$signatures.constructors("[C", "[CII", "[III", "[BIILjava/lang/String;", "[BIILjava/nio/charset/Charset;", "[BLjava/lang/String;", "[BLjava/nio/charset/Charset;", "[BII", "[B", "Ljava/lang/StringBuffer;", "Ljava/lang/StringBuilder;");
        BLACK_LIST_CONSTRUCTOR_SIGNATURES = SetsKt.plus(SetsKt.plus(JvmBuiltInsSettings.Companion.buildPrimitiveStringConstructorsSet(), (Iterable)$this$signatures.inJavaLang("Float", Arrays.copyOf(stringArray, stringArray.length))), (Iterable)$this$signatures.inJavaLang("String", Arrays.copyOf(stringArray2, stringArray2.length)));
        $i$f$signatures = false;
        signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
        bl = false;
        bl2 = false;
        $this$signatures = signatureBuildingComponents;
        boolean bl7 = false;
        String[] stringArray3 = $this$signatures.constructors("Ljava/lang/String;Ljava/lang/Throwable;ZZ");
        WHITE_LIST_CONSTRUCTOR_SIGNATURES = $this$signatures.inJavaLang("Throwable", Arrays.copyOf(stringArray3, stringArray3.length));
    }

    private final ModuleDescriptor getOwnerModuleDescriptor() {
        Lazy lazy = this.ownerModuleDescriptor$delegate;
        JvmBuiltInsSettings jvmBuiltInsSettings = this;
        Object var3_3 = null;
        boolean bl = false;
        return (ModuleDescriptor)lazy.getValue();
    }

    private final boolean isAdditionalBuiltInsFeatureSupported() {
        Lazy lazy = this.isAdditionalBuiltInsFeatureSupported$delegate;
        JvmBuiltInsSettings jvmBuiltInsSettings = this;
        Object var3_3 = null;
        boolean bl = false;
        return (Boolean)lazy.getValue();
    }

    private final SimpleType getCloneableType() {
        return (SimpleType)StorageKt.getValue(this.cloneableType$delegate, (Object)this, $$delegatedProperties[0]);
    }

    private final Annotations getNotConsideredDeprecation() {
        return (Annotations)StorageKt.getValue(this.notConsideredDeprecation$delegate, (Object)this, $$delegatedProperties[1]);
    }

    private final KotlinType createMockJavaIoSerializableType(StorageManager $this$createMockJavaIoSerializableType) {
        PackageFragmentDescriptorImpl mockJavaIoPackageFragment2 = new PackageFragmentDescriptorImpl(this, this.moduleDescriptor, new FqName("java.io")){
            final /* synthetic */ JvmBuiltInsSettings this$0;

            @NotNull
            public MemberScope.Empty getMemberScope() {
                return MemberScope.Empty.INSTANCE;
            }
            {
                this.this$0 = this$0;
                super($super_call_param$1, $super_call_param$2);
            }
        };
        List<LazyWrappedType> superTypes2 = CollectionsKt.listOf(new LazyWrappedType($this$createMockJavaIoSerializableType, (Function0<? extends KotlinType>)new Function0<KotlinType>(this){
            final /* synthetic */ JvmBuiltInsSettings this$0;

            @NotNull
            public final KotlinType invoke() {
                SimpleType simpleType2 = JvmBuiltInsSettings.access$getModuleDescriptor$p(this.this$0).getBuiltIns().getAnyType();
                Intrinsics.checkNotNullExpressionValue(simpleType2, "moduleDescriptor.builtIns.anyType");
                return simpleType2;
            }
            {
                this.this$0 = jvmBuiltInsSettings;
                super(0);
            }
        }));
        ClassDescriptorImpl mockSerializableClass = new ClassDescriptorImpl(mockJavaIoPackageFragment2, Name.identifier("Serializable"), Modality.ABSTRACT, ClassKind.INTERFACE, (Collection<KotlinType>)superTypes2, SourceElement.NO_SOURCE, false, $this$createMockJavaIoSerializableType);
        mockSerializableClass.initialize(MemberScope.Empty.INSTANCE, SetsKt.<ClassConstructorDescriptor>emptySet(), null);
        SimpleType simpleType2 = mockSerializableClass.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(simpleType2, "mockSerializableClass.defaultType");
        return simpleType2;
    }

    @Override
    @NotNull
    public Collection<KotlinType> getSupertypes(@NotNull ClassDescriptor classDescriptor) {
        List<KotlinType> list;
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        FqNameUnsafe fqName2 = DescriptorUtilsKt.getFqNameUnsafe(classDescriptor);
        if (JvmBuiltInsSettings.Companion.isArrayOrPrimitiveArray(fqName2)) {
            KotlinType[] kotlinTypeArray = new KotlinType[2];
            SimpleType simpleType2 = this.getCloneableType();
            Intrinsics.checkNotNullExpressionValue(simpleType2, "cloneableType");
            kotlinTypeArray[0] = simpleType2;
            kotlinTypeArray[1] = this.mockSerializableType;
            list = CollectionsKt.listOf(kotlinTypeArray);
        } else if (Companion.isSerializableInJava(fqName2)) {
            list = CollectionsKt.listOf(this.mockSerializableType);
        } else {
            boolean bl = false;
            list = CollectionsKt.emptyList();
        }
        return list;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    @NotNull
    public Collection<SimpleFunctionDescriptor> getFunctions(@NotNull Name name, @NotNull ClassDescriptor classDescriptor) {
        void $this$mapNotNullTo$iv$iv;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        if (Intrinsics.areEqual(name, CloneableClassScope.Companion.getCLONE_NAME()) && classDescriptor instanceof DeserializedClassDescriptor && KotlinBuiltIns.isArrayOrPrimitiveArray(classDescriptor)) {
            boolean bl;
            block18: {
                List<ProtoBuf.Function> list = ((DeserializedClassDescriptor)classDescriptor).getClassProto().getFunctionList();
                Intrinsics.checkNotNullExpressionValue(list, "classDescriptor.classProto.functionList");
                Iterable $this$any$iv = list;
                boolean $i$f$any = false;
                if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                    bl = false;
                } else {
                    for (Object element$iv : $this$any$iv) {
                        ProtoBuf.Function functionProto = (ProtoBuf.Function)element$iv;
                        boolean bl2 = false;
                        NameResolver nameResolver = ((DeserializedClassDescriptor)classDescriptor).getC().getNameResolver();
                        ProtoBuf.Function function = functionProto;
                        Intrinsics.checkNotNullExpressionValue(function, "functionProto");
                        if (!Intrinsics.areEqual(NameResolverUtilKt.getName(nameResolver, function.getName()), CloneableClassScope.Companion.getCLONE_NAME())) continue;
                        bl = true;
                        break block18;
                    }
                    bl = false;
                }
            }
            if (bl) {
                return CollectionsKt.emptyList();
            }
            return CollectionsKt.listOf(this.createCloneForArray((DeserializedClassDescriptor)classDescriptor, (SimpleFunctionDescriptor)CollectionsKt.single((Iterable)this.getCloneableType().getMemberScope().getContributedFunctions(name, NoLookupLocation.FROM_BUILTINS))));
        }
        if (!this.isAdditionalBuiltInsFeatureSupported()) {
            return CollectionsKt.emptyList();
        }
        Iterable $this$mapNotNull$iv = this.getAdditionalFunctions(classDescriptor, (Function1<? super MemberScope, ? extends Collection<? extends SimpleFunctionDescriptor>>)new Function1<MemberScope, Collection<? extends SimpleFunctionDescriptor>>(name){
            final /* synthetic */ Name $name;

            @NotNull
            public final Collection<SimpleFunctionDescriptor> invoke(@NotNull MemberScope it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getContributedFunctions(this.$name, NoLookupLocation.FROM_BUILTINS);
            }
            {
                this.$name = name;
                super(1);
            }
        });
        boolean $i$f$mapNotNull = false;
        Iterable iterable = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
        while (true) {
            SimpleFunctionDescriptor simpleFunctionDescriptor;
            block19: {
                Object element$iv$iv$iv;
                if (!iterator2.hasNext()) {
                    return (List)destination$iv$iv;
                }
                Object element$iv$iv = element$iv$iv$iv = iterator2.next();
                boolean bl = false;
                SimpleFunctionDescriptor additionalMember = (SimpleFunctionDescriptor)element$iv$iv;
                boolean bl3 = false;
                DeclarationDescriptor declarationDescriptor = additionalMember.getContainingDeclaration();
                if (declarationDescriptor == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                }
                FunctionDescriptor functionDescriptor = additionalMember.substitute(MappingUtilKt.createMappedTypeParametersSubstitution((ClassDescriptor)declarationDescriptor, classDescriptor).buildSubstitutor());
                if (functionDescriptor == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor");
                }
                SimpleFunctionDescriptor substitutedWithKotlinTypeParameters = (SimpleFunctionDescriptor)functionDescriptor;
                FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> copyBuilder = substitutedWithKotlinTypeParameters.newCopyBuilder();
                boolean bl4 = false;
                boolean bl5 = false;
                FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> $this$apply = copyBuilder;
                boolean bl6 = false;
                $this$apply.setOwner(classDescriptor);
                $this$apply.setDispatchReceiverParameter(classDescriptor.getThisAsReceiverParameter());
                $this$apply.setPreserveSourceElement();
                JDKMemberStatus memberStatus = this.getJdkMethodStatus(additionalMember);
                switch (JvmBuiltInsSettings$WhenMappings.$EnumSwitchMapping$0[memberStatus.ordinal()]) {
                    case 1: {
                        if (ModalityKt.isFinalClass(classDescriptor)) {
                            simpleFunctionDescriptor = null;
                            break block19;
                        } else {
                            Intrinsics.checkNotNullExpressionValue($this$apply.setHiddenForResolutionEverywhereBesideSupercalls(), "setHiddenForResolutionEverywhereBesideSupercalls()");
                            break;
                        }
                    }
                    case 2: {
                        Intrinsics.checkNotNullExpressionValue($this$apply.setAdditionalAnnotations(this.getNotConsideredDeprecation()), "setAdditionalAnnotations(notConsideredDeprecation)");
                        break;
                    }
                    case 3: {
                        simpleFunctionDescriptor = null;
                        break block19;
                    }
                    case 4: {
                    }
                }
                SimpleFunctionDescriptor simpleFunctionDescriptor2 = copyBuilder.build();
                Intrinsics.checkNotNull(simpleFunctionDescriptor2);
                simpleFunctionDescriptor = simpleFunctionDescriptor2;
            }
            if (simpleFunctionDescriptor == null) continue;
            SimpleFunctionDescriptor simpleFunctionDescriptor3 = simpleFunctionDescriptor;
            boolean bl = false;
            boolean bl7 = false;
            SimpleFunctionDescriptor it$iv$iv = simpleFunctionDescriptor3;
            boolean bl8 = false;
            destination$iv$iv.add(it$iv$iv);
        }
    }

    @NotNull
    public Set<Name> getFunctionsNames(@NotNull ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        if (!this.isAdditionalBuiltInsFeatureSupported()) {
            return SetsKt.emptySet();
        }
        Object object = this.getJavaAnalogue(classDescriptor);
        if (object == null || (object = ((LazyJavaClassDescriptor)object).getUnsubstitutedMemberScope()) == null || (object = ((LazyJavaScope)object).getFunctionNames()) == null) {
            object = SetsKt.emptySet();
        }
        return object;
    }

    /*
     * WARNING - void declaration
     */
    private final Collection<SimpleFunctionDescriptor> getAdditionalFunctions(ClassDescriptor classDescriptor, Function1<? super MemberScope, ? extends Collection<? extends SimpleFunctionDescriptor>> functionsByScope) {
        void $this$filterTo$iv$iv;
        Collection<FqName> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        LazyJavaClassDescriptor lazyJavaClassDescriptor = this.getJavaAnalogue(classDescriptor);
        if (lazyJavaClassDescriptor == null) {
            return CollectionsKt.emptyList();
        }
        LazyJavaClassDescriptor javaAnalogueDescriptor = lazyJavaClassDescriptor;
        Collection<ClassDescriptor> kotlinClassDescriptors = this.j2kClassMap.mapPlatformClass(DescriptorUtilsKt.getFqNameSafe(javaAnalogueDescriptor), FallbackBuiltIns.Companion.getInstance());
        ClassDescriptor classDescriptor2 = (ClassDescriptor)CollectionsKt.lastOrNull((Iterable)kotlinClassDescriptors);
        if (classDescriptor2 == null) {
            return CollectionsKt.emptyList();
        }
        ClassDescriptor kotlinMutableClassIfContainer = classDescriptor2;
        Iterable iterable = kotlinClassDescriptors;
        SmartSet.Companion companion = SmartSet.Companion;
        boolean $i$f$map = false;
        void var9_11 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ClassDescriptor classDescriptor3 = (ClassDescriptor)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            FqName fqName2 = DescriptorUtilsKt.getFqNameSafe((DeclarationDescriptor)it);
            collection.add(fqName2);
        }
        collection = (List)destination$iv$iv;
        SmartSet kotlinVersions = companion.create((Collection)collection);
        boolean isMutable = this.j2kClassMap.isMutable(classDescriptor);
        ClassDescriptor fakeJavaClassDescriptor2 = this.javaAnalogueClassesWithCustomSupertypeCache.computeIfAbsent(DescriptorUtilsKt.getFqNameSafe(javaAnalogueDescriptor), new Function0<ClassDescriptor>(javaAnalogueDescriptor, kotlinMutableClassIfContainer){
            final /* synthetic */ LazyJavaClassDescriptor $javaAnalogueDescriptor;
            final /* synthetic */ ClassDescriptor $kotlinMutableClassIfContainer;

            @NotNull
            public final ClassDescriptor invoke() {
                JavaResolverCache javaResolverCache = JavaResolverCache.EMPTY;
                Intrinsics.checkNotNullExpressionValue(javaResolverCache, "JavaResolverCache.EMPTY");
                return this.$javaAnalogueDescriptor.copy$descriptors_jvm(javaResolverCache, this.$kotlinMutableClassIfContainer);
            }
            {
                this.$javaAnalogueDescriptor = lazyJavaClassDescriptor;
                this.$kotlinMutableClassIfContainer = classDescriptor;
                super(0);
            }
        });
        MemberScope memberScope2 = fakeJavaClassDescriptor2.getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(memberScope2, "fakeJavaClassDescriptor.unsubstitutedMemberScope");
        MemberScope scope2 = memberScope2;
        Iterable $this$filter$iv = functionsByScope.invoke(scope2);
        boolean $i$f$filter = false;
        Iterable iterable2 = $this$filter$iv;
        Collection destination$iv$iv2 = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            boolean bl;
            SimpleFunctionDescriptor analogueMember = (SimpleFunctionDescriptor)element$iv$iv;
            boolean bl2 = false;
            if (analogueMember.getKind() != CallableMemberDescriptor.Kind.DECLARATION) {
                bl = false;
            } else if (!analogueMember.getVisibility().isPublicAPI()) {
                bl = false;
            } else if (KotlinBuiltIns.isDeprecated(analogueMember)) {
                bl = false;
            } else {
                boolean bl3;
                block13: {
                    Collection<? extends FunctionDescriptor> collection2 = analogueMember.getOverriddenDescriptors();
                    Intrinsics.checkNotNullExpressionValue(collection2, "analogueMember.overriddenDescriptors");
                    Iterable $this$any$iv = collection2;
                    boolean $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl3 = false;
                    } else {
                        for (Object element$iv : $this$any$iv) {
                            FunctionDescriptor it = (FunctionDescriptor)element$iv;
                            boolean bl4 = false;
                            FunctionDescriptor functionDescriptor = it;
                            Intrinsics.checkNotNullExpressionValue(functionDescriptor, "it");
                            DeclarationDescriptor declarationDescriptor = functionDescriptor.getContainingDeclaration();
                            Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "it.containingDeclaration");
                            if (!kotlinVersions.contains(DescriptorUtilsKt.getFqNameSafe(declarationDescriptor))) continue;
                            bl3 = true;
                            break block13;
                        }
                        bl3 = false;
                    }
                }
                bl = bl3 ? false : !this.isMutabilityViolation(analogueMember, isMutable);
            }
            if (!bl) continue;
            destination$iv$iv2.add(element$iv$iv);
        }
        return (List)destination$iv$iv2;
    }

    private final SimpleFunctionDescriptor createCloneForArray(DeserializedClassDescriptor arrayClassDescriptor, SimpleFunctionDescriptor cloneFromCloneable) {
        FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> copyBuilder = cloneFromCloneable.newCopyBuilder();
        boolean bl = false;
        boolean bl2 = false;
        FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> $this$apply = copyBuilder;
        boolean bl3 = false;
        $this$apply.setOwner(arrayClassDescriptor);
        $this$apply.setVisibility(Visibilities.PUBLIC);
        $this$apply.setReturnType(arrayClassDescriptor.getDefaultType());
        $this$apply.setDispatchReceiverParameter(arrayClassDescriptor.getThisAsReceiverParameter());
        SimpleFunctionDescriptor simpleFunctionDescriptor = copyBuilder.build();
        Intrinsics.checkNotNull(simpleFunctionDescriptor);
        return simpleFunctionDescriptor;
    }

    private final boolean isMutabilityViolation(SimpleFunctionDescriptor $this$isMutabilityViolation, boolean isMutable) {
        DeclarationDescriptor declarationDescriptor = $this$isMutabilityViolation.getContainingDeclaration();
        if (declarationDescriptor == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        }
        ClassDescriptor owner = (ClassDescriptor)declarationDescriptor;
        String jvmDescriptor2 = MethodSignatureMappingKt.computeJvmDescriptor$default($this$isMutabilityViolation, false, false, 3, null);
        if (MUTABLE_METHOD_SIGNATURES.contains(SignatureBuildingComponents.INSTANCE.signature(owner, jvmDescriptor2)) ^ isMutable) {
            return true;
        }
        Boolean bl = DFS.ifAny((Collection)CollectionsKt.listOf($this$isMutabilityViolation), isMutabilityViolation.1.INSTANCE, (Function1)new Function1<CallableMemberDescriptor, Boolean>(this){
            final /* synthetic */ JvmBuiltInsSettings this$0;

            /*
             * Enabled aggressive block sorting
             */
            public final Boolean invoke(CallableMemberDescriptor overridden) {
                boolean bl;
                CallableMemberDescriptor callableMemberDescriptor = overridden;
                Intrinsics.checkNotNullExpressionValue(callableMemberDescriptor, "overridden");
                if (callableMemberDescriptor.getKind() == CallableMemberDescriptor.Kind.DECLARATION) {
                    DeclarationDescriptor declarationDescriptor = overridden.getContainingDeclaration();
                    if (declarationDescriptor == null) {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    }
                    if (JvmBuiltInsSettings.access$getJ2kClassMap$p(this.this$0).isMutable((ClassDescriptor)declarationDescriptor)) {
                        bl = true;
                        return bl;
                    }
                }
                bl = false;
                return bl;
            }
            {
                this.this$0 = jvmBuiltInsSettings;
                super(1);
            }
        });
        Intrinsics.checkNotNullExpressionValue(bl, "DFS.ifAny<CallableMember\u2026lassDescriptor)\n        }");
        return bl;
    }

    /*
     * WARNING - void declaration
     */
    private final JDKMemberStatus getJdkMethodStatus(FunctionDescriptor $this$getJdkMethodStatus) {
        void result2;
        DeclarationDescriptor declarationDescriptor = $this$getJdkMethodStatus.getContainingDeclaration();
        if (declarationDescriptor == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        }
        ClassDescriptor owner = (ClassDescriptor)declarationDescriptor;
        String jvmDescriptor2 = MethodSignatureMappingKt.computeJvmDescriptor$default($this$getJdkMethodStatus, false, false, 3, null);
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = null;
        Object r = DFS.dfs((Collection)CollectionsKt.listOf(owner), new DFS.Neighbors<ClassDescriptor>(this){
            final /* synthetic */ JvmBuiltInsSettings this$0;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final Iterable<ClassDescriptor> getNeighbors(ClassDescriptor it) {
                void $this$mapNotNullTo$iv$iv;
                ClassDescriptor classDescriptor = it;
                Intrinsics.checkNotNullExpressionValue(classDescriptor, "it");
                TypeConstructor typeConstructor2 = classDescriptor.getTypeConstructor();
                Intrinsics.checkNotNullExpressionValue(typeConstructor2, "it.typeConstructor");
                Collection<KotlinType> collection = typeConstructor2.getSupertypes();
                Intrinsics.checkNotNullExpressionValue(collection, "it.typeConstructor.supertypes");
                Iterable $this$mapNotNull$iv = collection;
                boolean $i$f$mapNotNull = false;
                Iterable iterable = $this$mapNotNull$iv;
                Collection destination$iv$iv = new ArrayList<E>();
                boolean $i$f$mapNotNullTo = false;
                void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                boolean $i$f$forEach = false;
                Iterator<T> iterator2 = $this$forEach$iv$iv$iv.iterator();
                while (iterator2.hasNext()) {
                    LazyJavaClassDescriptor lazyJavaClassDescriptor;
                    T element$iv$iv$iv;
                    T element$iv$iv = element$iv$iv$iv = iterator2.next();
                    boolean bl = false;
                    KotlinType it2 = (KotlinType)element$iv$iv;
                    boolean bl2 = false;
                    ClassifierDescriptor classifierDescriptor = it2.getConstructor().getDeclarationDescriptor();
                    ClassifierDescriptor classifierDescriptor2 = classifierDescriptor != null ? classifierDescriptor.getOriginal() : null;
                    if (!(classifierDescriptor2 instanceof ClassDescriptor)) {
                        classifierDescriptor2 = null;
                    }
                    ClassDescriptor classDescriptor2 = (ClassDescriptor)classifierDescriptor2;
                    if ((classDescriptor2 != null ? JvmBuiltInsSettings.access$getJavaAnalogue(this.this$0, classDescriptor2) : null) == null) continue;
                    lazyJavaClassDescriptor = lazyJavaClassDescriptor;
                    boolean bl3 = false;
                    boolean bl4 = false;
                    LazyJavaClassDescriptor it$iv$iv = lazyJavaClassDescriptor;
                    boolean bl5 = false;
                    destination$iv$iv.add(it$iv$iv);
                }
                return (List)destination$iv$iv;
            }
            {
                this.this$0 = jvmBuiltInsSettings;
            }
        }, new DFS.AbstractNodeHandler<ClassDescriptor, JDKMemberStatus>(jvmDescriptor2, (Ref.ObjectRef)result2){
            final /* synthetic */ String $jvmDescriptor;
            final /* synthetic */ Ref.ObjectRef $result;

            public boolean beforeChildren(@NotNull ClassDescriptor javaClassDescriptor) {
                String signature2;
                Intrinsics.checkNotNullParameter(javaClassDescriptor, "javaClassDescriptor");
                String string = signature2 = SignatureBuildingComponents.INSTANCE.signature(javaClassDescriptor, this.$jvmDescriptor);
                if (JvmBuiltInsSettings.Companion.getBLACK_LIST_METHOD_SIGNATURES().contains(string)) {
                    this.$result.element = JDKMemberStatus.BLACK_LIST;
                } else if (JvmBuiltInsSettings.Companion.getWHITE_LIST_METHOD_SIGNATURES().contains(string)) {
                    this.$result.element = JDKMemberStatus.WHITE_LIST;
                } else if (JvmBuiltInsSettings.Companion.getDROP_LIST_METHOD_SIGNATURES().contains(string)) {
                    this.$result.element = JDKMemberStatus.DROP;
                }
                return (JDKMemberStatus)((Object)this.$result.element) == null;
            }

            @NotNull
            public JDKMemberStatus result() {
                JDKMemberStatus jDKMemberStatus = (JDKMemberStatus)((Object)this.$result.element);
                if (jDKMemberStatus == null) {
                    jDKMemberStatus = JDKMemberStatus.NOT_CONSIDERED;
                }
                return jDKMemberStatus;
            }
            {
                this.$jvmDescriptor = $captured_local_variable$0;
                this.$result = $captured_local_variable$1;
            }
        });
        Intrinsics.checkNotNullExpressionValue(r, "DFS.dfs<ClassDescriptor,\u2026CONSIDERED\n            })");
        return (JDKMemberStatus)((Object)r);
    }

    private final LazyJavaClassDescriptor getJavaAnalogue(ClassDescriptor $this$getJavaAnalogue) {
        if (KotlinBuiltIns.isAny($this$getJavaAnalogue)) {
            return null;
        }
        if (!KotlinBuiltIns.isUnderKotlinPackage($this$getJavaAnalogue)) {
            return null;
        }
        FqNameUnsafe fqName2 = DescriptorUtilsKt.getFqNameUnsafe($this$getJavaAnalogue);
        if (!fqName2.isSafe()) {
            return null;
        }
        Object object = this.j2kClassMap.mapKotlinToJava(fqName2);
        if (object == null || (object = ((ClassId)object).asSingleFqName()) == null) {
            return null;
        }
        Intrinsics.checkNotNullExpressionValue(object, "j2kClassMap.mapKotlinToJ\u2026leFqName() ?: return null");
        Object javaAnalogueFqName = object;
        ClassDescriptor classDescriptor = DescriptorUtilKt.resolveClassByFqName(this.getOwnerModuleDescriptor(), (FqName)javaAnalogueFqName, NoLookupLocation.FROM_BUILTINS);
        if (!(classDescriptor instanceof LazyJavaClassDescriptor)) {
            classDescriptor = null;
        }
        return (LazyJavaClassDescriptor)classDescriptor;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    @NotNull
    public Collection<ClassConstructorDescriptor> getConstructors(@NotNull ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        if (classDescriptor.getKind() != ClassKind.CLASS || !this.isAdditionalBuiltInsFeatureSupported()) {
            return CollectionsKt.emptyList();
        }
        v0 = this.getJavaAnalogue(classDescriptor);
        if (v0 == null) {
            return CollectionsKt.emptyList();
        }
        javaAnalogueDescriptor = v0;
        v1 = JavaToKotlinClassMap.mapJavaToKotlin$default(this.j2kClassMap, DescriptorUtilsKt.getFqNameSafe(javaAnalogueDescriptor), FallbackBuiltIns.Companion.getInstance(), null, 4, null);
        if (v1 == null) {
            return CollectionsKt.emptyList();
        }
        defaultKotlinVersion = v1;
        substitutor = MappingUtilKt.createMappedTypeParametersSubstitution(defaultKotlinVersion, javaAnalogueDescriptor).buildSubstitutor();
        $fun$isEffectivelyTheSameAs$1 = new Function2<ConstructorDescriptor, ConstructorDescriptor, Boolean>(substitutor){
            final /* synthetic */ TypeSubstitutor $substitutor;

            public final boolean invoke(@NotNull ConstructorDescriptor $this$isEffectivelyTheSameAs, @NotNull ConstructorDescriptor javaConstructor) {
                Intrinsics.checkNotNullParameter($this$isEffectivelyTheSameAs, "$this$isEffectivelyTheSameAs");
                Intrinsics.checkNotNullParameter(javaConstructor, "javaConstructor");
                return OverridingUtil.getBothWaysOverridability($this$isEffectivelyTheSameAs, javaConstructor.substitute(this.$substitutor)) == OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE;
            }
            {
                this.$substitutor = typeSubstitutor2;
                super(2);
            }
        };
        $this$filter$iv = javaAnalogueDescriptor.getConstructors();
        $i$f$filter = false;
        var8_8 = $this$filter$iv;
        destination$iv$iv = new ArrayList<E>();
        $i$f$filterTo = false;
        for (T element$iv$iv : $this$filterTo$iv$iv) {
            block12: {
                javaConstructor = (ClassConstructorDescriptor)element$iv$iv;
                $i$a$-filter-JvmBuiltInsSettings$getConstructors$2 = false;
                if (!javaConstructor.getVisibility().isPublicAPI()) ** GOTO lbl-1000
                v2 = defaultKotlinVersion.getConstructors();
                Intrinsics.checkNotNullExpressionValue(v2, "defaultKotlinVersion.constructors");
                $this$none$iv = v2;
                $i$f$none = false;
                if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                    v3 = true;
                } else {
                    for (T element$iv : $this$none$iv) {
                        it = (ClassConstructorDescriptor)element$iv;
                        $i$a$-none-JvmBuiltInsSettings$getConstructors$2$1 = false;
                        v4 = it;
                        Intrinsics.checkNotNullExpressionValue(v4, "it");
                        if (!$fun$isEffectivelyTheSameAs$1.invoke((ConstructorDescriptor)v4, (ConstructorDescriptor)javaConstructor)) continue;
                        v3 = false;
                        break block12;
                    }
                    v3 = true;
                }
            }
            if (v3 && !this.isTrivialCopyConstructorFor(javaConstructor, classDescriptor) && !KotlinBuiltIns.isDeprecated(javaConstructor) && !JvmBuiltInsSettings.BLACK_LIST_CONSTRUCTOR_SIGNATURES.contains(SignatureBuildingComponents.INSTANCE.signature(javaAnalogueDescriptor, MethodSignatureMappingKt.computeJvmDescriptor$default(javaConstructor, false, false, 3, null)))) {
                v5 = true;
            } else lbl-1000:
            // 2 sources

            {
                v5 = false;
            }
            if (!v5) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$map$iv = (List)destination$iv$iv;
        $i$f$map = false;
        $this$filterTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        for (T item$iv$iv : $this$mapTo$iv$iv) {
            javaConstructor = (ClassConstructorDescriptor)item$iv$iv;
            var21_23 = destination$iv$iv;
            $i$a$-map-JvmBuiltInsSettings$getConstructors$3 = false;
            var15_15 = javaConstructor.newCopyBuilder();
            var16_16 = false;
            var17_18 = false;
            $this$apply = var15_15;
            $i$a$-apply-JvmBuiltInsSettings$getConstructors$3$1 = false;
            $this$apply.setOwner(classDescriptor);
            $this$apply.setReturnType(classDescriptor.getDefaultType());
            $this$apply.setPreserveSourceElement();
            $this$apply.setSubstitution(substitutor.getSubstitution());
            if (!JvmBuiltInsSettings.WHITE_LIST_CONSTRUCTOR_SIGNATURES.contains(SignatureBuildingComponents.INSTANCE.signature(javaAnalogueDescriptor, MethodSignatureMappingKt.computeJvmDescriptor$default(javaConstructor, false, false, 3, null)))) {
                $this$apply.setAdditionalAnnotations(this.getNotConsideredDeprecation());
            }
            v6 = var15_15.build();
            if (v6 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor");
            }
            var22_24 = (ClassConstructorDescriptor)v6;
            var21_23.add(var22_24);
        }
        return (List)destination$iv$iv;
    }

    @Override
    public boolean isFunctionAvailable(@NotNull ClassDescriptor classDescriptor, @NotNull SimpleFunctionDescriptor functionDescriptor) {
        boolean bl;
        block6: {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            LazyJavaClassDescriptor lazyJavaClassDescriptor = this.getJavaAnalogue(classDescriptor);
            if (lazyJavaClassDescriptor == null) {
                return true;
            }
            LazyJavaClassDescriptor javaAnalogueClassDescriptor = lazyJavaClassDescriptor;
            if (!functionDescriptor.getAnnotations().hasAnnotation(PlatformDependentDeclarationFilterKt.getPLATFORM_DEPENDENT_ANNOTATION_FQ_NAME())) {
                return true;
            }
            if (!this.isAdditionalBuiltInsFeatureSupported()) {
                return false;
            }
            String jvmDescriptor2 = MethodSignatureMappingKt.computeJvmDescriptor$default(functionDescriptor, false, false, 3, null);
            LazyJavaClassMemberScope lazyJavaClassMemberScope = javaAnalogueClassDescriptor.getUnsubstitutedMemberScope();
            Name name = functionDescriptor.getName();
            Intrinsics.checkNotNullExpressionValue(name, "functionDescriptor.name");
            Iterable $this$any$iv = lazyJavaClassMemberScope.getContributedFunctions(name, NoLookupLocation.FROM_BUILTINS);
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    SimpleFunctionDescriptor it = (SimpleFunctionDescriptor)element$iv;
                    boolean bl2 = false;
                    if (!Intrinsics.areEqual(MethodSignatureMappingKt.computeJvmDescriptor$default(it, false, false, 3, null), jvmDescriptor2)) continue;
                    bl = true;
                    break block6;
                }
                bl = false;
            }
        }
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final boolean isTrivialCopyConstructorFor(ConstructorDescriptor $this$isTrivialCopyConstructorFor, ClassDescriptor classDescriptor) {
        if ($this$isTrivialCopyConstructorFor.getValueParameters().size() != 1) return false;
        List<ValueParameterDescriptor> list = $this$isTrivialCopyConstructorFor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(list, "valueParameters");
        ValueParameterDescriptor valueParameterDescriptor = CollectionsKt.single(list);
        Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "valueParameters.single()");
        ClassifierDescriptor classifierDescriptor = valueParameterDescriptor.getType().getConstructor().getDeclarationDescriptor();
        if (!Intrinsics.areEqual(classifierDescriptor != null ? DescriptorUtilsKt.getFqNameUnsafe(classifierDescriptor) : null, DescriptorUtilsKt.getFqNameUnsafe(classDescriptor))) return false;
        return true;
    }

    public JvmBuiltInsSettings(@NotNull ModuleDescriptor moduleDescriptor, @NotNull StorageManager storageManager, @NotNull Function0<? extends ModuleDescriptor> deferredOwnerModuleDescriptor, @NotNull Function0<Boolean> isAdditionalBuiltInsFeatureSupported) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(deferredOwnerModuleDescriptor, "deferredOwnerModuleDescriptor");
        Intrinsics.checkNotNullParameter(isAdditionalBuiltInsFeatureSupported, "isAdditionalBuiltInsFeatureSupported");
        this.moduleDescriptor = moduleDescriptor;
        this.j2kClassMap = JavaToKotlinClassMap.INSTANCE;
        this.ownerModuleDescriptor$delegate = LazyKt.lazy(deferredOwnerModuleDescriptor);
        this.isAdditionalBuiltInsFeatureSupported$delegate = LazyKt.lazy(isAdditionalBuiltInsFeatureSupported);
        this.mockSerializableType = this.createMockJavaIoSerializableType(storageManager);
        this.cloneableType$delegate = storageManager.createLazyValue((Function0)new Function0<SimpleType>(this, storageManager){
            final /* synthetic */ JvmBuiltInsSettings this$0;
            final /* synthetic */ StorageManager $storageManager;

            @NotNull
            public final SimpleType invoke() {
                return FindClassInModuleKt.findNonGenericClassAcrossDependencies(JvmBuiltInsSettings.access$getOwnerModuleDescriptor$p(this.this$0), JvmBuiltInClassDescriptorFactory.Companion.getCLONEABLE_CLASS_ID(), new NotFoundClasses(this.$storageManager, JvmBuiltInsSettings.access$getOwnerModuleDescriptor$p(this.this$0))).getDefaultType();
            }
            {
                this.this$0 = jvmBuiltInsSettings;
                this.$storageManager = storageManager;
                super(0);
            }
        });
        this.javaAnalogueClassesWithCustomSupertypeCache = storageManager.createCacheWithNotNullValues();
        this.notConsideredDeprecation$delegate = storageManager.createLazyValue((Function0)new Function0<Annotations>(this){
            final /* synthetic */ JvmBuiltInsSettings this$0;

            @NotNull
            public final Annotations invoke() {
                AnnotationDescriptor annotation = AnnotationUtilKt.createDeprecatedAnnotation$default(JvmBuiltInsSettings.access$getModuleDescriptor$p(this.this$0).getBuiltIns(), "This member is not fully supported by Kotlin compiler, so it may be absent or have different signature in next major version", null, null, 6, null);
                return Annotations.Companion.create(CollectionsKt.listOf(annotation));
            }
            {
                this.this$0 = jvmBuiltInsSettings;
                super(0);
            }
        });
    }

    public static final /* synthetic */ ModuleDescriptor access$getModuleDescriptor$p(JvmBuiltInsSettings $this) {
        return $this.moduleDescriptor;
    }

    public static final /* synthetic */ JavaToKotlinClassMap access$getJ2kClassMap$p(JvmBuiltInsSettings $this) {
        return $this.j2kClassMap;
    }

    public static final /* synthetic */ LazyJavaClassDescriptor access$getJavaAnalogue(JvmBuiltInsSettings $this, ClassDescriptor $this$access_u24getJavaAnalogue) {
        return $this.getJavaAnalogue($this$access_u24getJavaAnalogue);
    }

    public static final /* synthetic */ ModuleDescriptor access$getOwnerModuleDescriptor$p(JvmBuiltInsSettings $this) {
        return $this.getOwnerModuleDescriptor();
    }

    private static final class JDKMemberStatus
    extends Enum<JDKMemberStatus> {
        public static final /* enum */ JDKMemberStatus BLACK_LIST;
        public static final /* enum */ JDKMemberStatus WHITE_LIST;
        public static final /* enum */ JDKMemberStatus NOT_CONSIDERED;
        public static final /* enum */ JDKMemberStatus DROP;
        private static final /* synthetic */ JDKMemberStatus[] $VALUES;

        static {
            JDKMemberStatus[] jDKMemberStatusArray = new JDKMemberStatus[4];
            JDKMemberStatus[] jDKMemberStatusArray2 = jDKMemberStatusArray;
            jDKMemberStatusArray[0] = BLACK_LIST = new JDKMemberStatus();
            jDKMemberStatusArray[1] = WHITE_LIST = new JDKMemberStatus();
            jDKMemberStatusArray[2] = NOT_CONSIDERED = new JDKMemberStatus();
            jDKMemberStatusArray[3] = DROP = new JDKMemberStatus();
            $VALUES = jDKMemberStatusArray;
        }

        public static JDKMemberStatus[] values() {
            return (JDKMemberStatus[])$VALUES.clone();
        }

        public static JDKMemberStatus valueOf(String string) {
            return Enum.valueOf(JDKMemberStatus.class, string);
        }
    }

    public static final class Companion {
        public final boolean isSerializableInJava(@NotNull FqNameUnsafe fqName2) {
            Class<?> clazz;
            Intrinsics.checkNotNullParameter(fqName2, "fqName");
            if (this.isArrayOrPrimitiveArray(fqName2)) {
                return true;
            }
            ClassId classId = JavaToKotlinClassMap.INSTANCE.mapKotlinToJava(fqName2);
            if (classId == null) {
                return false;
            }
            ClassId javaClassId = classId;
            try {
                Class<?> clazz2 = Class.forName(javaClassId.asSingleFqName().asString());
                Intrinsics.checkNotNullExpressionValue(clazz2, "Class.forName(javaClassI\u2026ingleFqName().asString())");
                clazz = clazz2;
            }
            catch (ClassNotFoundException e) {
                return false;
            }
            Class<?> classViaReflection = clazz;
            return Serializable.class.isAssignableFrom(classViaReflection);
        }

        private final boolean isArrayOrPrimitiveArray(FqNameUnsafe fqName2) {
            return Intrinsics.areEqual(fqName2, KotlinBuiltIns.FQ_NAMES.array) || KotlinBuiltIns.isPrimitiveArray(fqName2);
        }

        @NotNull
        public final Set<String> getDROP_LIST_METHOD_SIGNATURES() {
            return DROP_LIST_METHOD_SIGNATURES;
        }

        @NotNull
        public final Set<String> getBLACK_LIST_METHOD_SIGNATURES() {
            return BLACK_LIST_METHOD_SIGNATURES;
        }

        /*
         * WARNING - void declaration
         */
        private final Set<String> buildPrimitiveValueMethodsSet() {
            void $this$flatMapTo$iv;
            boolean $i$f$signatures = false;
            SignatureBuildingComponents signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
            boolean bl = false;
            boolean bl2 = false;
            SignatureBuildingComponents $this$signatures = signatureBuildingComponents;
            boolean bl3 = false;
            Iterable iterable = CollectionsKt.listOf(JvmPrimitiveType.BOOLEAN, JvmPrimitiveType.CHAR);
            Collection destination$iv = new LinkedHashSet();
            boolean $i$f$flatMapTo = false;
            for (Object element$iv : $this$flatMapTo$iv) {
                JvmPrimitiveType it = (JvmPrimitiveType)((Object)element$iv);
                boolean bl4 = false;
                String string = it.getWrapperFqName().shortName().asString();
                Intrinsics.checkNotNullExpressionValue(string, "it.wrapperFqName.shortName().asString()");
                Iterable list$iv = $this$signatures.inJavaLang(string, it.getJavaKeywordName() + "Value()" + it.getDesc());
                CollectionsKt.addAll(destination$iv, list$iv);
            }
            return (LinkedHashSet)destination$iv;
        }

        @NotNull
        public final Set<String> getWHITE_LIST_METHOD_SIGNATURES() {
            return WHITE_LIST_METHOD_SIGNATURES;
        }

        /*
         * WARNING - void declaration
         */
        private final Set<String> buildPrimitiveStringConstructorsSet() {
            void $this$flatMapTo$iv;
            boolean $i$f$signatures = false;
            SignatureBuildingComponents signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
            boolean bl = false;
            boolean bl2 = false;
            SignatureBuildingComponents $this$signatures = signatureBuildingComponents;
            boolean bl3 = false;
            Iterable iterable = CollectionsKt.listOf(JvmPrimitiveType.BOOLEAN, JvmPrimitiveType.BYTE, JvmPrimitiveType.DOUBLE, JvmPrimitiveType.FLOAT, JvmPrimitiveType.BYTE, JvmPrimitiveType.INT, JvmPrimitiveType.LONG, JvmPrimitiveType.SHORT);
            Collection destination$iv = new LinkedHashSet();
            boolean $i$f$flatMapTo = false;
            for (Object element$iv : $this$flatMapTo$iv) {
                JvmPrimitiveType it = (JvmPrimitiveType)((Object)element$iv);
                boolean bl4 = false;
                String string = it.getWrapperFqName().shortName().asString();
                Intrinsics.checkNotNullExpressionValue(string, "it.wrapperFqName.shortName().asString()");
                String[] stringArray = $this$signatures.constructors("Ljava/lang/String;");
                Iterable list$iv = $this$signatures.inJavaLang(string, Arrays.copyOf(stringArray, stringArray.length));
                CollectionsKt.addAll(destination$iv, list$iv);
            }
            return (LinkedHashSet)destination$iv;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

