/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.Collection;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.builtins.SuspendFunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnhancementKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.InlineClassMappingKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmDescriptorTypeWriter;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmTypeFactory;
import kotlin.reflect.jvm.internal.impl.load.kotlin.TypeMappingConfiguration;
import kotlin.reflect.jvm.internal.impl.load.kotlin.TypeMappingConfigurationImpl;
import kotlin.reflect.jvm.internal.impl.load.kotlin.TypeMappingMode;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.SimpleClassicTypeSystemContext;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.FunctionsKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeSignatureMappingKt {
    private static final <T> T boxTypeIfNeeded(JvmTypeFactory<T> $this$boxTypeIfNeeded, T possiblyPrimitiveType, boolean needBoxedType) {
        return needBoxedType ? $this$boxTypeIfNeeded.boxType(possiblyPrimitiveType) : possiblyPrimitiveType;
    }

    @NotNull
    public static final <T> T mapType(@NotNull KotlinType kotlinType, @NotNull JvmTypeFactory<T> factory, @NotNull TypeMappingMode mode, @NotNull TypeMappingConfiguration<? extends T> typeMappingConfiguration, @Nullable JvmDescriptorTypeWriter<T> descriptorTypeWriter, @NotNull Function3<? super KotlinType, ? super T, ? super TypeMappingMode, Unit> writeGenericType) {
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(typeMappingConfiguration, "typeMappingConfiguration");
        Intrinsics.checkNotNullParameter(writeGenericType, "writeGenericType");
        KotlinType kotlinType2 = typeMappingConfiguration.preprocessType(kotlinType);
        if (kotlinType2 != null) {
            KotlinType kotlinType3 = kotlinType2;
            boolean bl = false;
            boolean bl2 = false;
            KotlinType newType = kotlinType3;
            boolean bl3 = false;
            return TypeSignatureMappingKt.mapType(newType, factory, mode, typeMappingConfiguration, descriptorTypeWriter, writeGenericType);
        }
        if (FunctionTypesKt.isSuspendFunctionType(kotlinType)) {
            return TypeSignatureMappingKt.mapType(SuspendFunctionTypesKt.transformSuspendFunctionToRuntimeFunctionType(kotlinType, typeMappingConfiguration.releaseCoroutines()), factory, mode, typeMappingConfiguration, descriptorTypeWriter, writeGenericType);
        }
        SimpleClassicTypeSystemContext simpleClassicTypeSystemContext = SimpleClassicTypeSystemContext.INSTANCE;
        boolean bl = false;
        boolean bl4 = false;
        SimpleClassicTypeSystemContext $this$with = simpleClassicTypeSystemContext;
        boolean bl5 = false;
        T t = TypeSignatureMappingKt.mapBuiltInType($this$with, kotlinType, factory, mode);
        if (t != null) {
            simpleClassicTypeSystemContext = t;
            bl = false;
            bl4 = false;
            SimpleClassicTypeSystemContext builtInType = simpleClassicTypeSystemContext;
            boolean bl6 = false;
            SimpleClassicTypeSystemContext jvmType = TypeSignatureMappingKt.boxTypeIfNeeded(factory, builtInType, mode.getNeedPrimitiveBoxing());
            writeGenericType.invoke(kotlinType, jvmType, mode);
            return (T)jvmType;
        }
        TypeConstructor constructor = kotlinType.getConstructor();
        if (constructor instanceof IntersectionTypeConstructor) {
            KotlinType kotlinType4 = ((IntersectionTypeConstructor)constructor).getAlternativeType();
            if (kotlinType4 == null) {
                kotlinType4 = typeMappingConfiguration.commonSupertype(((IntersectionTypeConstructor)constructor).getSupertypes());
            }
            KotlinType intersectionType = kotlinType4;
            return TypeSignatureMappingKt.mapType(TypeUtilsKt.replaceArgumentsWithStarProjections(intersectionType), factory, mode, typeMappingConfiguration, descriptorTypeWriter, writeGenericType);
        }
        ClassifierDescriptor classifierDescriptor = constructor.getDeclarationDescriptor();
        if (classifierDescriptor == null) {
            throw (Throwable)new UnsupportedOperationException("no descriptor for type constructor of " + kotlinType);
        }
        Intrinsics.checkNotNullExpressionValue(classifierDescriptor, "constructor.declarationD\u2026structor of $kotlinType\")");
        ClassifierDescriptor descriptor2 = classifierDescriptor;
        if (ErrorUtils.isError(descriptor2)) {
            T jvmType = factory.createObjectType("error/NonExistentClass");
            ClassifierDescriptor classifierDescriptor2 = descriptor2;
            if (classifierDescriptor2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            }
            typeMappingConfiguration.processErrorType(kotlinType, (ClassDescriptor)classifierDescriptor2);
            JvmDescriptorTypeWriter<Object> jvmDescriptorTypeWriter = descriptorTypeWriter;
            if (jvmDescriptorTypeWriter != null) {
                jvmDescriptorTypeWriter.writeClass(jvmType);
            }
            return jvmType;
        }
        if (descriptor2 instanceof ClassDescriptor && KotlinBuiltIns.isArray(kotlinType)) {
            if (kotlinType.getArguments().size() != 1) {
                throw (Throwable)new UnsupportedOperationException("arrays must have one type argument");
            }
            TypeProjection memberProjection = kotlinType.getArguments().get(0);
            KotlinType kotlinType5 = memberProjection.getType();
            Intrinsics.checkNotNullExpressionValue(kotlinType5, "memberProjection.type");
            KotlinType memberType = kotlinType5;
            Object arrayElementType = null;
            if (memberProjection.getProjectionKind() == Variance.IN_VARIANCE) {
                arrayElementType = factory.createObjectType("java/lang/Object");
                JvmDescriptorTypeWriter<Object> jvmDescriptorTypeWriter = descriptorTypeWriter;
                if (jvmDescriptorTypeWriter != null) {
                    JvmDescriptorTypeWriter<Object> jvmType = jvmDescriptorTypeWriter;
                    boolean bl7 = false;
                    boolean bl8 = false;
                    JvmDescriptorTypeWriter<Object> $this$apply = jvmType;
                    boolean bl9 = false;
                    $this$apply.writeArrayType();
                    $this$apply.writeClass(arrayElementType);
                    $this$apply.writeArrayEnd();
                }
            } else {
                JvmDescriptorTypeWriter<Object> jvmDescriptorTypeWriter = descriptorTypeWriter;
                if (jvmDescriptorTypeWriter != null) {
                    jvmDescriptorTypeWriter.writeArrayType();
                }
                Variance variance = memberProjection.getProjectionKind();
                Intrinsics.checkNotNullExpressionValue((Object)variance, "memberProjection.projectionKind");
                arrayElementType = TypeSignatureMappingKt.mapType(memberType, factory, mode.toGenericArgumentMode(variance, true), typeMappingConfiguration, descriptorTypeWriter, writeGenericType);
                JvmDescriptorTypeWriter<Object> jvmDescriptorTypeWriter2 = descriptorTypeWriter;
                if (jvmDescriptorTypeWriter2 != null) {
                    jvmDescriptorTypeWriter2.writeArrayEnd();
                }
            }
            return factory.createFromString("[" + factory.toString(arrayElementType));
        }
        if (descriptor2 instanceof ClassDescriptor) {
            T t2;
            KotlinType expandedType;
            if (((ClassDescriptor)descriptor2).isInline() && !mode.getNeedInlineClassWrapping() && (expandedType = (KotlinType)InlineClassMappingKt.computeExpandedTypeForInlineClass(SimpleClassicTypeSystemContext.INSTANCE, kotlinType)) != null) {
                return TypeSignatureMappingKt.mapType(expandedType, factory, mode.wrapInlineClassesMode(), typeMappingConfiguration, descriptorTypeWriter, writeGenericType);
            }
            if (mode.isForAnnotationParameter() && KotlinBuiltIns.isKClass((ClassDescriptor)descriptor2)) {
                t2 = factory.getJavaLangClassType();
            } else {
                ClassDescriptor classDescriptor = ((ClassDescriptor)descriptor2).getOriginal();
                Intrinsics.checkNotNullExpressionValue(classDescriptor, "descriptor.original");
                t2 = typeMappingConfiguration.getPredefinedTypeForClass(classDescriptor);
                if (t2 == null) {
                    ClassDescriptor classDescriptor2;
                    boolean bl10 = false;
                    boolean bl11 = false;
                    boolean bl12 = false;
                    if (((ClassDescriptor)descriptor2).getKind() == ClassKind.ENUM_ENTRY) {
                        DeclarationDescriptor declarationDescriptor = ((ClassDescriptor)descriptor2).getContainingDeclaration();
                        if (declarationDescriptor == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                        }
                        classDescriptor2 = (ClassDescriptor)declarationDescriptor;
                    } else {
                        classDescriptor2 = (ClassDescriptor)descriptor2;
                    }
                    ClassDescriptor enumClassIfEnumEntry = classDescriptor2;
                    ClassDescriptor classDescriptor3 = enumClassIfEnumEntry.getOriginal();
                    Intrinsics.checkNotNullExpressionValue(classDescriptor3, "enumClassIfEnumEntry.original");
                    t2 = factory.createObjectType(TypeSignatureMappingKt.computeInternalName(classDescriptor3, typeMappingConfiguration));
                }
            }
            T jvmType = t2;
            writeGenericType.invoke(kotlinType, jvmType, mode);
            return jvmType;
        }
        if (descriptor2 instanceof TypeParameterDescriptor) {
            JvmDescriptorTypeWriter<T> jvmDescriptorTypeWriter = null;
            Function3<Object, Object, Object, Unit> function3 = FunctionsKt.getDO_NOTHING_3();
            Object type2 = TypeSignatureMappingKt.mapType(TypeUtilsKt.getRepresentativeUpperBound((TypeParameterDescriptor)descriptor2), factory, mode, typeMappingConfiguration, jvmDescriptorTypeWriter, function3);
            JvmDescriptorTypeWriter<Object> jvmDescriptorTypeWriter3 = descriptorTypeWriter;
            if (jvmDescriptorTypeWriter3 != null) {
                Name name = descriptor2.getName();
                Intrinsics.checkNotNullExpressionValue(name, "descriptor.getName()");
                jvmDescriptorTypeWriter3.writeTypeVariable(name, type2);
            }
            return (T)type2;
        }
        if (descriptor2 instanceof TypeAliasDescriptor && mode.getMapTypeAliases()) {
            return TypeSignatureMappingKt.mapType(((TypeAliasDescriptor)descriptor2).getExpandedType(), factory, mode, typeMappingConfiguration, descriptorTypeWriter, writeGenericType);
        }
        throw (Throwable)new UnsupportedOperationException("Unknown type " + kotlinType);
    }

    public static /* synthetic */ Object mapType$default(KotlinType kotlinType, JvmTypeFactory jvmTypeFactory, TypeMappingMode typeMappingMode, TypeMappingConfiguration typeMappingConfiguration, JvmDescriptorTypeWriter jvmDescriptorTypeWriter, Function3 function3, int n, Object object) {
        if ((n & 0x20) != 0) {
            function3 = FunctionsKt.getDO_NOTHING_3();
        }
        return TypeSignatureMappingKt.mapType(kotlinType, jvmTypeFactory, typeMappingMode, typeMappingConfiguration, jvmDescriptorTypeWriter, function3);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static final boolean hasVoidReturnType(@NotNull CallableDescriptor descriptor2) {
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        if (descriptor2 instanceof ConstructorDescriptor) {
            return true;
        }
        KotlinType kotlinType = descriptor2.getReturnType();
        Intrinsics.checkNotNull(kotlinType);
        if (!KotlinBuiltIns.isUnit(kotlinType)) return false;
        KotlinType kotlinType2 = descriptor2.getReturnType();
        Intrinsics.checkNotNull(kotlinType2);
        if (TypeUtils.isNullableType(kotlinType2)) return false;
        if (descriptor2 instanceof PropertyGetterDescriptor) return false;
        return true;
    }

    @Nullable
    public static final <T> T mapBuiltInType(@NotNull TypeSystemCommonBackendContext $this$mapBuiltInType, @NotNull KotlinTypeMarker type2, @NotNull JvmTypeFactory<T> typeFactory, @NotNull TypeMappingMode mode) {
        Intrinsics.checkNotNullParameter($this$mapBuiltInType, "$this$mapBuiltInType");
        Intrinsics.checkNotNullParameter(type2, "type");
        Intrinsics.checkNotNullParameter(typeFactory, "typeFactory");
        Intrinsics.checkNotNullParameter(mode, "mode");
        TypeConstructorMarker constructor = $this$mapBuiltInType.typeConstructor(type2);
        if (!$this$mapBuiltInType.isClassTypeConstructor(constructor)) {
            return null;
        }
        PrimitiveType primitiveType = $this$mapBuiltInType.getPrimitiveType(constructor);
        if (primitiveType != null) {
            JvmPrimitiveType jvmPrimitiveType = JvmPrimitiveType.get(primitiveType);
            Intrinsics.checkNotNullExpressionValue((Object)jvmPrimitiveType, "JvmPrimitiveType.get(primitiveType)");
            String string = jvmPrimitiveType.getDesc();
            Intrinsics.checkNotNullExpressionValue(string, "JvmPrimitiveType.get(primitiveType).desc");
            T jvmType = typeFactory.createFromString(string);
            boolean isNullableInJava = $this$mapBuiltInType.isNullableType(type2) || TypeEnhancementKt.hasEnhancedNullability($this$mapBuiltInType, type2);
            return TypeSignatureMappingKt.boxTypeIfNeeded(typeFactory, jvmType, isNullableInJava);
        }
        PrimitiveType arrayElementType = $this$mapBuiltInType.getPrimitiveArrayType(constructor);
        if (arrayElementType != null) {
            StringBuilder stringBuilder = new StringBuilder().append("[");
            JvmPrimitiveType jvmPrimitiveType = JvmPrimitiveType.get(arrayElementType);
            Intrinsics.checkNotNullExpressionValue((Object)jvmPrimitiveType, "JvmPrimitiveType.get(arrayElementType)");
            return typeFactory.createFromString(stringBuilder.append(jvmPrimitiveType.getDesc()).toString());
        }
        if ($this$mapBuiltInType.isUnderKotlinPackage(constructor)) {
            ClassId classId;
            ClassId classId2;
            FqNameUnsafe fqNameUnsafe = $this$mapBuiltInType.getClassFqNameUnsafe(constructor);
            if (fqNameUnsafe != null) {
                FqNameUnsafe fqNameUnsafe2 = fqNameUnsafe;
                JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
                boolean bl = false;
                boolean bl2 = false;
                FqNameUnsafe p1 = fqNameUnsafe2;
                boolean bl3 = false;
                classId2 = javaToKotlinClassMap.mapKotlinToJava(p1);
            } else {
                classId2 = classId = null;
            }
            if (classId != null) {
                if (!mode.getKotlinCollectionsToJavaCollections()) {
                    boolean bl;
                    block12: {
                        Iterable $this$any$iv = JavaToKotlinClassMap.INSTANCE.getMutabilityMappings();
                        boolean $i$f$any = false;
                        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                            bl = false;
                        } else {
                            for (Object element$iv : $this$any$iv) {
                                JavaToKotlinClassMap.PlatformMutabilityMapping it = (JavaToKotlinClassMap.PlatformMutabilityMapping)element$iv;
                                boolean bl4 = false;
                                if (!Intrinsics.areEqual(it.getJavaClass(), classId)) continue;
                                bl = true;
                                break block12;
                            }
                            bl = false;
                        }
                    }
                    if (bl) {
                        return null;
                    }
                }
                JvmClassName jvmClassName = JvmClassName.byClassId(classId);
                Intrinsics.checkNotNullExpressionValue(jvmClassName, "JvmClassName.byClassId(classId)");
                String string = jvmClassName.getInternalName();
                Intrinsics.checkNotNullExpressionValue(string, "JvmClassName.byClassId(classId).internalName");
                return typeFactory.createObjectType(string);
            }
        }
        return null;
    }

    @NotNull
    public static final String computeInternalName(@NotNull ClassDescriptor klass, @NotNull TypeMappingConfiguration<?> typeMappingConfiguration) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(typeMappingConfiguration, "typeMappingConfiguration");
        String string = typeMappingConfiguration.getPredefinedFullInternalNameForClass(klass);
        if (string != null) {
            String string2 = string;
            boolean bl = false;
            boolean bl2 = false;
            String it = string2;
            boolean bl3 = false;
            return it;
        }
        DeclarationDescriptor declarationDescriptor = klass.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "klass.containingDeclaration");
        DeclarationDescriptor container = declarationDescriptor;
        Name name = SpecialNames.safeIdentifier(klass.getName());
        Intrinsics.checkNotNullExpressionValue(name, "SpecialNames.safeIdentifier(klass.name)");
        String string3 = name.getIdentifier();
        Intrinsics.checkNotNullExpressionValue(string3, "SpecialNames.safeIdentifier(klass.name).identifier");
        String name2 = string3;
        if (container instanceof PackageFragmentDescriptor) {
            String string4;
            FqName fqName2 = ((PackageFragmentDescriptor)container).getFqName();
            if (fqName2.isRoot()) {
                string4 = name2;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                String string5 = fqName2.asString();
                Intrinsics.checkNotNullExpressionValue(string5, "fqName.asString()");
                string4 = stringBuilder.append(StringsKt.replace$default(string5, '.', '/', false, 4, null)).append('/').append(name2).toString();
            }
            return string4;
        }
        DeclarationDescriptor declarationDescriptor2 = container;
        if (!(declarationDescriptor2 instanceof ClassDescriptor)) {
            declarationDescriptor2 = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor)declarationDescriptor2;
        if (classDescriptor == null) {
            throw (Throwable)new IllegalArgumentException("Unexpected container: " + container + " for " + klass);
        }
        ClassDescriptor containerClass = classDescriptor;
        String string6 = typeMappingConfiguration.getPredefinedInternalNameForClass(containerClass);
        if (string6 == null) {
            string6 = TypeSignatureMappingKt.computeInternalName(containerClass, typeMappingConfiguration);
        }
        String containerInternalName = string6;
        return containerInternalName + '$' + name2;
    }

    public static /* synthetic */ String computeInternalName$default(ClassDescriptor classDescriptor, TypeMappingConfiguration typeMappingConfiguration, int n, Object object) {
        if ((n & 2) != 0) {
            typeMappingConfiguration = TypeMappingConfigurationImpl.INSTANCE;
        }
        return TypeSignatureMappingKt.computeInternalName(classDescriptor, typeMappingConfiguration);
    }
}

