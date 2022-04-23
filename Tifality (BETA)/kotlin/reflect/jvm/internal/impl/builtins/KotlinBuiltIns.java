/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoader;
import kotlin.reflect.jvm.internal.impl.builtins.BuiltInsPackageFragment;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.builtins.ReflectionTypesKt;
import kotlin.reflect.jvm.internal.impl.builtins.UnsignedTypes;
import kotlin.reflect.jvm.internal.impl.builtins.functions.BuiltInFictitiousFunctionClassFactory;
import kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class KotlinBuiltIns {
    public static final Name BUILT_INS_PACKAGE_NAME = Name.identifier("kotlin");
    public static final FqName BUILT_INS_PACKAGE_FQ_NAME = FqName.topLevel(BUILT_INS_PACKAGE_NAME);
    private static final FqName ANNOTATION_PACKAGE_FQ_NAME = BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("annotation"));
    public static final FqName COLLECTIONS_PACKAGE_FQ_NAME = BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("collections"));
    public static final FqName RANGES_PACKAGE_FQ_NAME = BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("ranges"));
    public static final FqName TEXT_PACKAGE_FQ_NAME = BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("text"));
    public static final Set<FqName> BUILT_INS_PACKAGE_FQ_NAMES = SetsKt.setOf(BUILT_INS_PACKAGE_FQ_NAME, COLLECTIONS_PACKAGE_FQ_NAME, RANGES_PACKAGE_FQ_NAME, ANNOTATION_PACKAGE_FQ_NAME, ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME(), BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("internal")), DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE);
    private ModuleDescriptorImpl builtInsModule;
    private final NotNullLazyValue<Primitives> primitives;
    private final NotNullLazyValue<Collection<PackageViewDescriptor>> builtInPackagesImportedByDefault;
    private final MemoizedFunctionToNotNull<Name, ClassDescriptor> builtInClassesByName;
    private final StorageManager storageManager;
    public static final FqNames FQ_NAMES = new FqNames();
    public static final Name BUILTINS_MODULE_NAME = Name.special("<built-ins module>");

    protected KotlinBuiltIns(@NotNull StorageManager storageManager) {
        if (storageManager == null) {
            KotlinBuiltIns.$$$reportNull$$$0(0);
        }
        this.storageManager = storageManager;
        this.builtInPackagesImportedByDefault = storageManager.createLazyValue(new Function0<Collection<PackageViewDescriptor>>(){

            @Override
            public Collection<PackageViewDescriptor> invoke() {
                return Arrays.asList(KotlinBuiltIns.this.builtInsModule.getPackage(BUILT_INS_PACKAGE_FQ_NAME), KotlinBuiltIns.this.builtInsModule.getPackage(COLLECTIONS_PACKAGE_FQ_NAME), KotlinBuiltIns.this.builtInsModule.getPackage(RANGES_PACKAGE_FQ_NAME), KotlinBuiltIns.this.builtInsModule.getPackage(ANNOTATION_PACKAGE_FQ_NAME));
            }
        });
        this.primitives = storageManager.createLazyValue(new Function0<Primitives>(){

            @Override
            public Primitives invoke() {
                EnumMap<PrimitiveType, SimpleType> primitiveTypeToArrayKotlinType = new EnumMap<PrimitiveType, SimpleType>(PrimitiveType.class);
                HashMap<SimpleType, SimpleType> primitiveKotlinTypeToKotlinArrayType = new HashMap<SimpleType, SimpleType>();
                HashMap<SimpleType, SimpleType> kotlinArrayTypeToPrimitiveKotlinType = new HashMap<SimpleType, SimpleType>();
                for (PrimitiveType primitive : PrimitiveType.values()) {
                    SimpleType type2 = KotlinBuiltIns.this.getBuiltInTypeByClassName(primitive.getTypeName().asString());
                    SimpleType arrayType = KotlinBuiltIns.this.getBuiltInTypeByClassName(primitive.getArrayTypeName().asString());
                    primitiveTypeToArrayKotlinType.put(primitive, arrayType);
                    primitiveKotlinTypeToKotlinArrayType.put(type2, arrayType);
                    kotlinArrayTypeToPrimitiveKotlinType.put(arrayType, type2);
                }
                return new Primitives(primitiveTypeToArrayKotlinType, primitiveKotlinTypeToKotlinArrayType, kotlinArrayTypeToPrimitiveKotlinType);
            }
        });
        this.builtInClassesByName = storageManager.createMemoizedFunction(new Function1<Name, ClassDescriptor>(){

            @Override
            public ClassDescriptor invoke(Name name) {
                ClassifierDescriptor classifier2 = KotlinBuiltIns.this.getBuiltInsPackageScope().getContributedClassifier(name, NoLookupLocation.FROM_BUILTINS);
                if (classifier2 == null) {
                    throw new AssertionError((Object)("Built-in class " + BUILT_INS_PACKAGE_FQ_NAME.child(name) + " is not found"));
                }
                if (!(classifier2 instanceof ClassDescriptor)) {
                    throw new AssertionError((Object)("Must be a class descriptor " + name + ", but was " + classifier2));
                }
                return (ClassDescriptor)classifier2;
            }
        });
    }

    protected void createBuiltInsModule(boolean isFallback) {
        this.builtInsModule = new ModuleDescriptorImpl(BUILTINS_MODULE_NAME, this.storageManager, this, null);
        this.builtInsModule.initialize(BuiltInsLoader.Companion.getInstance().createPackageFragmentProvider(this.storageManager, this.builtInsModule, this.getClassDescriptorFactories(), this.getPlatformDependentDeclarationFilter(), this.getAdditionalClassPartsProvider(), isFallback));
        this.builtInsModule.setDependencies(this.builtInsModule);
    }

    public void setBuiltInsModule(final @NotNull ModuleDescriptorImpl module) {
        if (module == null) {
            KotlinBuiltIns.$$$reportNull$$$0(1);
        }
        this.storageManager.compute(new Function0<Void>(){

            @Override
            public Void invoke() {
                if (KotlinBuiltIns.this.builtInsModule != null) {
                    throw new AssertionError((Object)("Built-ins module is already set: " + KotlinBuiltIns.this.builtInsModule + " (attempting to reset to " + module + ")"));
                }
                KotlinBuiltIns.this.builtInsModule = module;
                return null;
            }
        });
    }

    @NotNull
    protected AdditionalClassPartsProvider getAdditionalClassPartsProvider() {
        AdditionalClassPartsProvider.None none = AdditionalClassPartsProvider.None.INSTANCE;
        if (none == null) {
            KotlinBuiltIns.$$$reportNull$$$0(2);
        }
        return none;
    }

    @NotNull
    protected PlatformDependentDeclarationFilter getPlatformDependentDeclarationFilter() {
        PlatformDependentDeclarationFilter.NoPlatformDependent noPlatformDependent = PlatformDependentDeclarationFilter.NoPlatformDependent.INSTANCE;
        if (noPlatformDependent == null) {
            KotlinBuiltIns.$$$reportNull$$$0(3);
        }
        return noPlatformDependent;
    }

    @NotNull
    protected Iterable<ClassDescriptorFactory> getClassDescriptorFactories() {
        List<ClassDescriptorFactory> list = Collections.singletonList(new BuiltInFictitiousFunctionClassFactory(this.storageManager, this.builtInsModule));
        if (list == null) {
            KotlinBuiltIns.$$$reportNull$$$0(4);
        }
        return list;
    }

    @NotNull
    protected StorageManager getStorageManager() {
        StorageManager storageManager = this.storageManager;
        if (storageManager == null) {
            KotlinBuiltIns.$$$reportNull$$$0(5);
        }
        return storageManager;
    }

    @NotNull
    public ModuleDescriptorImpl getBuiltInsModule() {
        ModuleDescriptorImpl moduleDescriptorImpl = this.builtInsModule;
        if (moduleDescriptorImpl == null) {
            KotlinBuiltIns.$$$reportNull$$$0(6);
        }
        return moduleDescriptorImpl;
    }

    public static boolean isBuiltIn(@NotNull DeclarationDescriptor descriptor2) {
        if (descriptor2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(8);
        }
        return DescriptorUtils.getParentOfType(descriptor2, BuiltInsPackageFragment.class, false) != null;
    }

    public static boolean isUnderKotlinPackage(@NotNull DeclarationDescriptor descriptor2) {
        if (descriptor2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(9);
        }
        for (DeclarationDescriptor current = descriptor2; current != null; current = current.getContainingDeclaration()) {
            if (!(current instanceof PackageFragmentDescriptor)) continue;
            return ((PackageFragmentDescriptor)current).getFqName().startsWith(BUILT_INS_PACKAGE_NAME);
        }
        return false;
    }

    @NotNull
    public MemberScope getBuiltInsPackageScope() {
        MemberScope memberScope2 = this.builtInsModule.getPackage(BUILT_INS_PACKAGE_FQ_NAME).getMemberScope();
        if (memberScope2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(10);
        }
        return memberScope2;
    }

    @NotNull
    public ClassDescriptor getBuiltInClassByFqName(@NotNull FqName fqName2) {
        if (fqName2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(11);
        }
        ClassDescriptor descriptor2 = DescriptorUtilKt.resolveClassByFqName(this.builtInsModule, fqName2, NoLookupLocation.FROM_BUILTINS);
        assert (descriptor2 != null) : "Can't find built-in class " + fqName2;
        ClassDescriptor classDescriptor = descriptor2;
        if (classDescriptor == null) {
            KotlinBuiltIns.$$$reportNull$$$0(12);
        }
        return classDescriptor;
    }

    @NotNull
    private ClassDescriptor getBuiltInClassByName(@NotNull String simpleName2) {
        if (simpleName2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(13);
        }
        ClassDescriptor classDescriptor = (ClassDescriptor)this.builtInClassesByName.invoke(Name.identifier(simpleName2));
        if (classDescriptor == null) {
            KotlinBuiltIns.$$$reportNull$$$0(14);
        }
        return classDescriptor;
    }

    @NotNull
    public ClassDescriptor getAny() {
        return this.getBuiltInClassByName("Any");
    }

    @NotNull
    public ClassDescriptor getNothing() {
        return this.getBuiltInClassByName("Nothing");
    }

    @NotNull
    private ClassDescriptor getPrimitiveClassDescriptor(@NotNull PrimitiveType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(15);
        }
        return this.getBuiltInClassByName(type2.getTypeName().asString());
    }

    @NotNull
    public ClassDescriptor getArray() {
        return this.getBuiltInClassByName("Array");
    }

    @NotNull
    public ClassDescriptor getNumber() {
        return this.getBuiltInClassByName("Number");
    }

    @NotNull
    public ClassDescriptor getUnit() {
        return this.getBuiltInClassByName("Unit");
    }

    @NotNull
    public static String getFunctionName(int parameterCount) {
        String string = "Function" + parameterCount;
        if (string == null) {
            KotlinBuiltIns.$$$reportNull$$$0(17);
        }
        return string;
    }

    @NotNull
    public static ClassId getFunctionClassId(int parameterCount) {
        return new ClassId(BUILT_INS_PACKAGE_FQ_NAME, Name.identifier(KotlinBuiltIns.getFunctionName(parameterCount)));
    }

    @NotNull
    public ClassDescriptor getFunction(int parameterCount) {
        return this.getBuiltInClassByName(KotlinBuiltIns.getFunctionName(parameterCount));
    }

    @NotNull
    public static String getSuspendFunctionName(int parameterCount) {
        String string = FunctionClassDescriptor.Kind.SuspendFunction.getClassNamePrefix() + parameterCount;
        if (string == null) {
            KotlinBuiltIns.$$$reportNull$$$0(19);
        }
        return string;
    }

    @NotNull
    public ClassDescriptor getSuspendFunction(int parameterCount) {
        ClassDescriptor classDescriptor = this.getBuiltInClassByFqName(DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE.child(Name.identifier(KotlinBuiltIns.getSuspendFunctionName(parameterCount))));
        if (classDescriptor == null) {
            KotlinBuiltIns.$$$reportNull$$$0(20);
        }
        return classDescriptor;
    }

    @NotNull
    public ClassDescriptor getString() {
        return this.getBuiltInClassByName("String");
    }

    @NotNull
    public ClassDescriptor getComparable() {
        return this.getBuiltInClassByName("Comparable");
    }

    @NotNull
    public ClassDescriptor getKClass() {
        ClassDescriptor classDescriptor = this.getBuiltInClassByFqName(KotlinBuiltIns.FQ_NAMES.kClass.toSafe());
        if (classDescriptor == null) {
            KotlinBuiltIns.$$$reportNull$$$0(23);
        }
        return classDescriptor;
    }

    @NotNull
    public ClassDescriptor getCollection() {
        ClassDescriptor classDescriptor = this.getBuiltInClassByFqName(KotlinBuiltIns.FQ_NAMES.collection);
        if (classDescriptor == null) {
            KotlinBuiltIns.$$$reportNull$$$0(36);
        }
        return classDescriptor;
    }

    @NotNull
    private SimpleType getBuiltInTypeByClassName(@NotNull String classSimpleName) {
        if (classSimpleName == null) {
            KotlinBuiltIns.$$$reportNull$$$0(48);
        }
        SimpleType simpleType2 = this.getBuiltInClassByName(classSimpleName).getDefaultType();
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(49);
        }
        return simpleType2;
    }

    @NotNull
    public SimpleType getNothingType() {
        SimpleType simpleType2 = this.getNothing().getDefaultType();
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(50);
        }
        return simpleType2;
    }

    @NotNull
    public SimpleType getNullableNothingType() {
        SimpleType simpleType2 = this.getNothingType().makeNullableAsSpecified(true);
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(51);
        }
        return simpleType2;
    }

    @NotNull
    public SimpleType getAnyType() {
        SimpleType simpleType2 = this.getAny().getDefaultType();
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(52);
        }
        return simpleType2;
    }

    @NotNull
    public SimpleType getNullableAnyType() {
        SimpleType simpleType2 = this.getAnyType().makeNullableAsSpecified(true);
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(53);
        }
        return simpleType2;
    }

    @NotNull
    public SimpleType getDefaultBound() {
        SimpleType simpleType2 = this.getNullableAnyType();
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(54);
        }
        return simpleType2;
    }

    @NotNull
    public SimpleType getPrimitiveKotlinType(@NotNull PrimitiveType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(55);
        }
        SimpleType simpleType2 = this.getPrimitiveClassDescriptor(type2).getDefaultType();
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(56);
        }
        return simpleType2;
    }

    @NotNull
    public SimpleType getNumberType() {
        SimpleType simpleType2 = this.getNumber().getDefaultType();
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(57);
        }
        return simpleType2;
    }

    @NotNull
    public SimpleType getByteType() {
        SimpleType simpleType2 = this.getPrimitiveKotlinType(PrimitiveType.BYTE);
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(58);
        }
        return simpleType2;
    }

    @NotNull
    public SimpleType getShortType() {
        SimpleType simpleType2 = this.getPrimitiveKotlinType(PrimitiveType.SHORT);
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(59);
        }
        return simpleType2;
    }

    @NotNull
    public SimpleType getIntType() {
        SimpleType simpleType2 = this.getPrimitiveKotlinType(PrimitiveType.INT);
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(60);
        }
        return simpleType2;
    }

    @NotNull
    public SimpleType getLongType() {
        SimpleType simpleType2 = this.getPrimitiveKotlinType(PrimitiveType.LONG);
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(61);
        }
        return simpleType2;
    }

    @NotNull
    public SimpleType getFloatType() {
        SimpleType simpleType2 = this.getPrimitiveKotlinType(PrimitiveType.FLOAT);
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(62);
        }
        return simpleType2;
    }

    @NotNull
    public SimpleType getDoubleType() {
        SimpleType simpleType2 = this.getPrimitiveKotlinType(PrimitiveType.DOUBLE);
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(63);
        }
        return simpleType2;
    }

    @NotNull
    public SimpleType getCharType() {
        SimpleType simpleType2 = this.getPrimitiveKotlinType(PrimitiveType.CHAR);
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(64);
        }
        return simpleType2;
    }

    @NotNull
    public SimpleType getBooleanType() {
        SimpleType simpleType2 = this.getPrimitiveKotlinType(PrimitiveType.BOOLEAN);
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(65);
        }
        return simpleType2;
    }

    @NotNull
    public SimpleType getUnitType() {
        SimpleType simpleType2 = this.getUnit().getDefaultType();
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(66);
        }
        return simpleType2;
    }

    @NotNull
    public SimpleType getStringType() {
        SimpleType simpleType2 = this.getString().getDefaultType();
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(67);
        }
        return simpleType2;
    }

    @NotNull
    public KotlinType getArrayElementType(@NotNull KotlinType arrayType) {
        KotlinType unsignedType;
        if (arrayType == null) {
            KotlinBuiltIns.$$$reportNull$$$0(69);
        }
        if (KotlinBuiltIns.isArray(arrayType)) {
            if (arrayType.getArguments().size() != 1) {
                throw new IllegalStateException();
            }
            KotlinType kotlinType = arrayType.getArguments().get(0).getType();
            if (kotlinType == null) {
                KotlinBuiltIns.$$$reportNull$$$0(70);
            }
            return kotlinType;
        }
        KotlinType notNullArrayType = TypeUtils.makeNotNullable(arrayType);
        KotlinType primitiveType = ((Primitives)this.primitives.invoke()).kotlinArrayTypeToPrimitiveKotlinType.get(notNullArrayType);
        if (primitiveType != null) {
            KotlinType kotlinType = primitiveType;
            if (kotlinType == null) {
                KotlinBuiltIns.$$$reportNull$$$0(71);
            }
            return kotlinType;
        }
        ModuleDescriptor module = DescriptorUtils.getContainingModuleOrNull(notNullArrayType);
        if (module != null && (unsignedType = KotlinBuiltIns.getElementTypeForUnsignedArray(notNullArrayType, module)) != null) {
            KotlinType kotlinType = unsignedType;
            if (kotlinType == null) {
                KotlinBuiltIns.$$$reportNull$$$0(72);
            }
            return kotlinType;
        }
        throw new IllegalStateException("not array: " + arrayType);
    }

    @Nullable
    private static KotlinType getElementTypeForUnsignedArray(@NotNull KotlinType notNullArrayType, @NotNull ModuleDescriptor module) {
        ClassifierDescriptor descriptor2;
        if (notNullArrayType == null) {
            KotlinBuiltIns.$$$reportNull$$$0(73);
        }
        if (module == null) {
            KotlinBuiltIns.$$$reportNull$$$0(74);
        }
        if ((descriptor2 = notNullArrayType.getConstructor().getDeclarationDescriptor()) == null) {
            return null;
        }
        if (!UnsignedTypes.INSTANCE.isShortNameOfUnsignedArray(descriptor2.getName())) {
            return null;
        }
        ClassId arrayClassId = DescriptorUtilsKt.getClassId(descriptor2);
        if (arrayClassId == null) {
            return null;
        }
        ClassId elementClassId = UnsignedTypes.INSTANCE.getUnsignedClassIdByArrayClassId(arrayClassId);
        if (elementClassId == null) {
            return null;
        }
        ClassDescriptor elementClassDescriptor = FindClassInModuleKt.findClassAcrossModuleDependencies(module, elementClassId);
        if (elementClassDescriptor == null) {
            return null;
        }
        return elementClassDescriptor.getDefaultType();
    }

    @NotNull
    public SimpleType getPrimitiveArrayKotlinType(@NotNull PrimitiveType primitiveType) {
        if (primitiveType == null) {
            KotlinBuiltIns.$$$reportNull$$$0(75);
        }
        SimpleType simpleType2 = ((Primitives)this.primitives.invoke()).primitiveTypeToArrayKotlinType.get((Object)primitiveType);
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(76);
        }
        return simpleType2;
    }

    public static boolean isPrimitiveArray(@NotNull FqNameUnsafe arrayFqName) {
        if (arrayFqName == null) {
            KotlinBuiltIns.$$$reportNull$$$0(78);
        }
        return KotlinBuiltIns.FQ_NAMES.arrayClassFqNameToPrimitiveType.get(arrayFqName) != null;
    }

    @Nullable
    public static PrimitiveType getPrimitiveType(@NotNull DeclarationDescriptor descriptor2) {
        if (descriptor2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(79);
        }
        return KotlinBuiltIns.FQ_NAMES.primitiveTypeShortNames.contains(descriptor2.getName()) ? KotlinBuiltIns.FQ_NAMES.fqNameToPrimitiveType.get(DescriptorUtils.getFqName(descriptor2)) : null;
    }

    @Nullable
    public static PrimitiveType getPrimitiveArrayType(@NotNull DeclarationDescriptor descriptor2) {
        if (descriptor2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(80);
        }
        return KotlinBuiltIns.FQ_NAMES.primitiveArrayTypeShortNames.contains(descriptor2.getName()) ? KotlinBuiltIns.FQ_NAMES.arrayClassFqNameToPrimitiveType.get(DescriptorUtils.getFqName(descriptor2)) : null;
    }

    @NotNull
    public SimpleType getArrayType(@NotNull Variance projectionType, @NotNull KotlinType argument) {
        if (projectionType == null) {
            KotlinBuiltIns.$$$reportNull$$$0(81);
        }
        if (argument == null) {
            KotlinBuiltIns.$$$reportNull$$$0(82);
        }
        List<TypeProjectionImpl> types = Collections.singletonList(new TypeProjectionImpl(projectionType, argument));
        SimpleType simpleType2 = KotlinTypeFactory.simpleNotNullType(Annotations.Companion.getEMPTY(), this.getArray(), types);
        if (simpleType2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(83);
        }
        return simpleType2;
    }

    public static boolean isArray(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(87);
        }
        return KotlinBuiltIns.isConstructedFromGivenClass(type2, KotlinBuiltIns.FQ_NAMES.array);
    }

    public static boolean isArrayOrPrimitiveArray(@NotNull ClassDescriptor descriptor2) {
        if (descriptor2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(88);
        }
        return KotlinBuiltIns.classFqNameEquals(descriptor2, KotlinBuiltIns.FQ_NAMES.array) || KotlinBuiltIns.getPrimitiveArrayType(descriptor2) != null;
    }

    public static boolean isPrimitiveArray(@NotNull KotlinType type2) {
        ClassifierDescriptor descriptor2;
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(90);
        }
        return (descriptor2 = type2.getConstructor().getDeclarationDescriptor()) != null && KotlinBuiltIns.getPrimitiveArrayType(descriptor2) != null;
    }

    public static boolean isPrimitiveType(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(93);
        }
        return !type2.isMarkedNullable() && KotlinBuiltIns.isPrimitiveTypeOrNullablePrimitiveType(type2);
    }

    public static boolean isPrimitiveTypeOrNullablePrimitiveType(@NotNull KotlinType type2) {
        ClassifierDescriptor descriptor2;
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(94);
        }
        return (descriptor2 = type2.getConstructor().getDeclarationDescriptor()) instanceof ClassDescriptor && KotlinBuiltIns.isPrimitiveClass((ClassDescriptor)descriptor2);
    }

    public static boolean isPrimitiveClass(@NotNull ClassDescriptor descriptor2) {
        if (descriptor2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(95);
        }
        return KotlinBuiltIns.getPrimitiveType(descriptor2) != null;
    }

    private static boolean isConstructedFromGivenClass(@NotNull KotlinType type2, @NotNull FqNameUnsafe fqName2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(96);
        }
        if (fqName2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(97);
        }
        return KotlinBuiltIns.isTypeConstructorForGivenClass(type2.getConstructor(), fqName2);
    }

    public static boolean isTypeConstructorForGivenClass(@NotNull TypeConstructor typeConstructor2, @NotNull FqNameUnsafe fqName2) {
        ClassifierDescriptor descriptor2;
        if (typeConstructor2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(100);
        }
        if (fqName2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(101);
        }
        return (descriptor2 = typeConstructor2.getDeclarationDescriptor()) instanceof ClassDescriptor && KotlinBuiltIns.classFqNameEquals(descriptor2, fqName2);
    }

    private static boolean classFqNameEquals(@NotNull ClassifierDescriptor descriptor2, @NotNull FqNameUnsafe fqName2) {
        if (descriptor2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(102);
        }
        if (fqName2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(103);
        }
        return descriptor2.getName().equals(fqName2.shortName()) && fqName2.equals(DescriptorUtils.getFqName(descriptor2));
    }

    private static boolean isNotNullConstructedFromGivenClass(@NotNull KotlinType type2, @NotNull FqNameUnsafe fqName2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(104);
        }
        if (fqName2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(105);
        }
        return !type2.isMarkedNullable() && KotlinBuiltIns.isConstructedFromGivenClass(type2, fqName2);
    }

    public static boolean isSpecialClassWithNoSupertypes(@NotNull ClassDescriptor descriptor2) {
        if (descriptor2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(106);
        }
        return KotlinBuiltIns.classFqNameEquals(descriptor2, KotlinBuiltIns.FQ_NAMES.any) || KotlinBuiltIns.classFqNameEquals(descriptor2, KotlinBuiltIns.FQ_NAMES.nothing);
    }

    public static boolean isAny(@NotNull ClassDescriptor descriptor2) {
        if (descriptor2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(107);
        }
        return KotlinBuiltIns.classFqNameEquals(descriptor2, KotlinBuiltIns.FQ_NAMES.any);
    }

    public static boolean isBoolean(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(109);
        }
        return KotlinBuiltIns.isConstructedFromGivenClassAndNotNullable(type2, KotlinBuiltIns.FQ_NAMES._boolean);
    }

    public static boolean isChar(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(113);
        }
        return KotlinBuiltIns.isConstructedFromGivenClassAndNotNullable(type2, KotlinBuiltIns.FQ_NAMES._char);
    }

    public static boolean isInt(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(115);
        }
        return KotlinBuiltIns.isConstructedFromGivenClassAndNotNullable(type2, KotlinBuiltIns.FQ_NAMES._int);
    }

    public static boolean isByte(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(116);
        }
        return KotlinBuiltIns.isConstructedFromGivenClassAndNotNullable(type2, KotlinBuiltIns.FQ_NAMES._byte);
    }

    public static boolean isLong(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(117);
        }
        return KotlinBuiltIns.isConstructedFromGivenClassAndNotNullable(type2, KotlinBuiltIns.FQ_NAMES._long);
    }

    public static boolean isShort(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(119);
        }
        return KotlinBuiltIns.isConstructedFromGivenClassAndNotNullable(type2, KotlinBuiltIns.FQ_NAMES._short);
    }

    public static boolean isFloat(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(120);
        }
        return KotlinBuiltIns.isFloatOrNullableFloat(type2) && !type2.isMarkedNullable();
    }

    public static boolean isFloatOrNullableFloat(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(121);
        }
        return KotlinBuiltIns.isConstructedFromGivenClass(type2, KotlinBuiltIns.FQ_NAMES._float);
    }

    public static boolean isDouble(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(122);
        }
        return KotlinBuiltIns.isDoubleOrNullableDouble(type2) && !type2.isMarkedNullable();
    }

    public static boolean isDoubleOrNullableDouble(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(127);
        }
        return KotlinBuiltIns.isConstructedFromGivenClass(type2, KotlinBuiltIns.FQ_NAMES._double);
    }

    private static boolean isConstructedFromGivenClassAndNotNullable(@NotNull KotlinType type2, @NotNull FqNameUnsafe fqName2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(128);
        }
        if (fqName2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(129);
        }
        return KotlinBuiltIns.isConstructedFromGivenClass(type2, fqName2) && !type2.isMarkedNullable();
    }

    public static boolean isNothing(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(130);
        }
        return KotlinBuiltIns.isNothingOrNullableNothing(type2) && !TypeUtils.isNullableType(type2);
    }

    public static boolean isNothingOrNullableNothing(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(132);
        }
        return KotlinBuiltIns.isConstructedFromGivenClass(type2, KotlinBuiltIns.FQ_NAMES.nothing);
    }

    public static boolean isAnyOrNullableAny(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(133);
        }
        return KotlinBuiltIns.isConstructedFromGivenClass(type2, KotlinBuiltIns.FQ_NAMES.any);
    }

    public static boolean isNullableAny(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(134);
        }
        return KotlinBuiltIns.isAnyOrNullableAny(type2) && type2.isMarkedNullable();
    }

    public static boolean isDefaultBound(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(135);
        }
        return KotlinBuiltIns.isNullableAny(type2);
    }

    public static boolean isUnit(@NotNull KotlinType type2) {
        if (type2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(136);
        }
        return KotlinBuiltIns.isNotNullConstructedFromGivenClass(type2, KotlinBuiltIns.FQ_NAMES.unit);
    }

    public static boolean isString(@Nullable KotlinType type2) {
        return type2 != null && KotlinBuiltIns.isNotNullConstructedFromGivenClass(type2, KotlinBuiltIns.FQ_NAMES.string);
    }

    public static boolean isKClass(@NotNull ClassDescriptor descriptor2) {
        if (descriptor2 == null) {
            KotlinBuiltIns.$$$reportNull$$$0(150);
        }
        return KotlinBuiltIns.classFqNameEquals(descriptor2, KotlinBuiltIns.FQ_NAMES.kClass);
    }

    public static boolean isDeprecated(@NotNull DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            KotlinBuiltIns.$$$reportNull$$$0(153);
        }
        if (declarationDescriptor.getOriginal().getAnnotations().hasAnnotation(KotlinBuiltIns.FQ_NAMES.deprecated)) {
            return true;
        }
        if (declarationDescriptor instanceof PropertyDescriptor) {
            boolean isVar = ((PropertyDescriptor)declarationDescriptor).isVar();
            PropertyGetterDescriptor getter = ((PropertyDescriptor)declarationDescriptor).getGetter();
            PropertySetterDescriptor setter = ((PropertyDescriptor)declarationDescriptor).getSetter();
            return getter != null && KotlinBuiltIns.isDeprecated(getter) && (!isVar || setter != null && KotlinBuiltIns.isDeprecated(setter));
        }
        return false;
    }

    public static FqName getPrimitiveFqName(@NotNull PrimitiveType primitiveType) {
        if (primitiveType == null) {
            KotlinBuiltIns.$$$reportNull$$$0(155);
        }
        return BUILT_INS_PACKAGE_FQ_NAME.child(primitiveType.getTypeName());
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        RuntimeException runtimeException;
        Object[] objectArray;
        Object[] objectArray2;
        int n2;
        String string;
        switch (n) {
            default: {
                string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 10: 
            case 12: 
            case 14: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: 
            case 35: 
            case 36: 
            case 37: 
            case 38: 
            case 39: 
            case 40: 
            case 41: 
            case 42: 
            case 43: 
            case 44: 
            case 45: 
            case 46: 
            case 47: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 56: 
            case 57: 
            case 58: 
            case 59: 
            case 60: 
            case 61: 
            case 62: 
            case 63: 
            case 64: 
            case 65: 
            case 66: 
            case 67: 
            case 68: 
            case 70: 
            case 71: 
            case 72: 
            case 76: 
            case 83: 
            case 85: 
            case 86: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 10: 
            case 12: 
            case 14: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: 
            case 35: 
            case 36: 
            case 37: 
            case 38: 
            case 39: 
            case 40: 
            case 41: 
            case 42: 
            case 43: 
            case 44: 
            case 45: 
            case 46: 
            case 47: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 56: 
            case 57: 
            case 58: 
            case 59: 
            case 60: 
            case 61: 
            case 62: 
            case 63: 
            case 64: 
            case 65: 
            case 66: 
            case 67: 
            case 68: 
            case 70: 
            case 71: 
            case 72: 
            case 76: 
            case 83: 
            case 85: 
            case 86: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "storageManager";
                break;
            }
            case 1: 
            case 74: {
                objectArray2 = objectArray3;
                objectArray3[0] = "module";
                break;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 10: 
            case 12: 
            case 14: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: 
            case 35: 
            case 36: 
            case 37: 
            case 38: 
            case 39: 
            case 40: 
            case 41: 
            case 42: 
            case 43: 
            case 44: 
            case 45: 
            case 46: 
            case 47: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 56: 
            case 57: 
            case 58: 
            case 59: 
            case 60: 
            case 61: 
            case 62: 
            case 63: 
            case 64: 
            case 65: 
            case 66: 
            case 67: 
            case 68: 
            case 70: 
            case 71: 
            case 72: 
            case 76: 
            case 83: 
            case 85: 
            case 86: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/builtins/KotlinBuiltIns";
                break;
            }
            case 8: 
            case 9: 
            case 79: 
            case 80: 
            case 88: 
            case 95: 
            case 102: 
            case 106: 
            case 107: 
            case 139: 
            case 140: 
            case 142: 
            case 150: 
            case 151: 
            case 152: {
                objectArray2 = objectArray3;
                objectArray3[0] = "descriptor";
                break;
            }
            case 11: 
            case 97: 
            case 99: 
            case 101: 
            case 103: 
            case 105: 
            case 129: {
                objectArray2 = objectArray3;
                objectArray3[0] = "fqName";
                break;
            }
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "simpleName";
                break;
            }
            case 15: 
            case 16: 
            case 55: 
            case 87: 
            case 89: 
            case 90: 
            case 91: 
            case 92: 
            case 93: 
            case 94: 
            case 96: 
            case 98: 
            case 104: 
            case 108: 
            case 109: 
            case 110: 
            case 112: 
            case 113: 
            case 114: 
            case 115: 
            case 116: 
            case 117: 
            case 118: 
            case 119: 
            case 120: 
            case 121: 
            case 122: 
            case 123: 
            case 124: 
            case 125: 
            case 126: 
            case 127: 
            case 128: 
            case 130: 
            case 131: 
            case 132: 
            case 133: 
            case 134: 
            case 135: 
            case 136: 
            case 137: 
            case 138: 
            case 141: 
            case 143: 
            case 144: 
            case 145: 
            case 146: 
            case 147: 
            case 148: 
            case 149: 
            case 154: {
                objectArray2 = objectArray3;
                objectArray3[0] = "type";
                break;
            }
            case 48: {
                objectArray2 = objectArray3;
                objectArray3[0] = "classSimpleName";
                break;
            }
            case 69: {
                objectArray2 = objectArray3;
                objectArray3[0] = "arrayType";
                break;
            }
            case 73: {
                objectArray2 = objectArray3;
                objectArray3[0] = "notNullArrayType";
                break;
            }
            case 75: 
            case 155: {
                objectArray2 = objectArray3;
                objectArray3[0] = "primitiveType";
                break;
            }
            case 77: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlinType";
                break;
            }
            case 78: {
                objectArray2 = objectArray3;
                objectArray3[0] = "arrayFqName";
                break;
            }
            case 81: {
                objectArray2 = objectArray3;
                objectArray3[0] = "projectionType";
                break;
            }
            case 82: 
            case 84: {
                objectArray2 = objectArray3;
                objectArray3[0] = "argument";
                break;
            }
            case 100: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeConstructor";
                break;
            }
            case 111: {
                objectArray2 = objectArray3;
                objectArray3[0] = "classDescriptor";
                break;
            }
            case 153: {
                objectArray2 = objectArray3;
                objectArray3[0] = "declarationDescriptor";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/builtins/KotlinBuiltIns";
                break;
            }
            case 2: {
                objectArray = objectArray2;
                objectArray2[1] = "getAdditionalClassPartsProvider";
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = "getPlatformDependentDeclarationFilter";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "getClassDescriptorFactories";
                break;
            }
            case 5: {
                objectArray = objectArray2;
                objectArray2[1] = "getStorageManager";
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "getBuiltInsModule";
                break;
            }
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "getBuiltInPackagesImportedByDefault";
                break;
            }
            case 10: {
                objectArray = objectArray2;
                objectArray2[1] = "getBuiltInsPackageScope";
                break;
            }
            case 12: {
                objectArray = objectArray2;
                objectArray2[1] = "getBuiltInClassByFqName";
                break;
            }
            case 14: {
                objectArray = objectArray2;
                objectArray2[1] = "getBuiltInClassByName";
                break;
            }
            case 17: {
                objectArray = objectArray2;
                objectArray2[1] = "getFunctionName";
                break;
            }
            case 18: {
                objectArray = objectArray2;
                objectArray2[1] = "getKFunctionFqName";
                break;
            }
            case 19: {
                objectArray = objectArray2;
                objectArray2[1] = "getSuspendFunctionName";
                break;
            }
            case 20: {
                objectArray = objectArray2;
                objectArray2[1] = "getSuspendFunction";
                break;
            }
            case 21: {
                objectArray = objectArray2;
                objectArray2[1] = "getKFunction";
                break;
            }
            case 22: {
                objectArray = objectArray2;
                objectArray2[1] = "getKSuspendFunction";
                break;
            }
            case 23: {
                objectArray = objectArray2;
                objectArray2[1] = "getKClass";
                break;
            }
            case 24: {
                objectArray = objectArray2;
                objectArray2[1] = "getKCallable";
                break;
            }
            case 25: {
                objectArray = objectArray2;
                objectArray2[1] = "getKProperty";
                break;
            }
            case 26: {
                objectArray = objectArray2;
                objectArray2[1] = "getKProperty0";
                break;
            }
            case 27: {
                objectArray = objectArray2;
                objectArray2[1] = "getKProperty1";
                break;
            }
            case 28: {
                objectArray = objectArray2;
                objectArray2[1] = "getKProperty2";
                break;
            }
            case 29: {
                objectArray = objectArray2;
                objectArray2[1] = "getKMutableProperty0";
                break;
            }
            case 30: {
                objectArray = objectArray2;
                objectArray2[1] = "getKMutableProperty1";
                break;
            }
            case 31: {
                objectArray = objectArray2;
                objectArray2[1] = "getKMutableProperty2";
                break;
            }
            case 32: {
                objectArray = objectArray2;
                objectArray2[1] = "getIterator";
                break;
            }
            case 33: {
                objectArray = objectArray2;
                objectArray2[1] = "getIterable";
                break;
            }
            case 34: {
                objectArray = objectArray2;
                objectArray2[1] = "getMutableIterable";
                break;
            }
            case 35: {
                objectArray = objectArray2;
                objectArray2[1] = "getMutableIterator";
                break;
            }
            case 36: {
                objectArray = objectArray2;
                objectArray2[1] = "getCollection";
                break;
            }
            case 37: {
                objectArray = objectArray2;
                objectArray2[1] = "getMutableCollection";
                break;
            }
            case 38: {
                objectArray = objectArray2;
                objectArray2[1] = "getList";
                break;
            }
            case 39: {
                objectArray = objectArray2;
                objectArray2[1] = "getMutableList";
                break;
            }
            case 40: {
                objectArray = objectArray2;
                objectArray2[1] = "getSet";
                break;
            }
            case 41: {
                objectArray = objectArray2;
                objectArray2[1] = "getMutableSet";
                break;
            }
            case 42: {
                objectArray = objectArray2;
                objectArray2[1] = "getMap";
                break;
            }
            case 43: {
                objectArray = objectArray2;
                objectArray2[1] = "getMutableMap";
                break;
            }
            case 44: {
                objectArray = objectArray2;
                objectArray2[1] = "getMapEntry";
                break;
            }
            case 45: {
                objectArray = objectArray2;
                objectArray2[1] = "getMutableMapEntry";
                break;
            }
            case 46: {
                objectArray = objectArray2;
                objectArray2[1] = "getListIterator";
                break;
            }
            case 47: {
                objectArray = objectArray2;
                objectArray2[1] = "getMutableListIterator";
                break;
            }
            case 49: {
                objectArray = objectArray2;
                objectArray2[1] = "getBuiltInTypeByClassName";
                break;
            }
            case 50: {
                objectArray = objectArray2;
                objectArray2[1] = "getNothingType";
                break;
            }
            case 51: {
                objectArray = objectArray2;
                objectArray2[1] = "getNullableNothingType";
                break;
            }
            case 52: {
                objectArray = objectArray2;
                objectArray2[1] = "getAnyType";
                break;
            }
            case 53: {
                objectArray = objectArray2;
                objectArray2[1] = "getNullableAnyType";
                break;
            }
            case 54: {
                objectArray = objectArray2;
                objectArray2[1] = "getDefaultBound";
                break;
            }
            case 56: {
                objectArray = objectArray2;
                objectArray2[1] = "getPrimitiveKotlinType";
                break;
            }
            case 57: {
                objectArray = objectArray2;
                objectArray2[1] = "getNumberType";
                break;
            }
            case 58: {
                objectArray = objectArray2;
                objectArray2[1] = "getByteType";
                break;
            }
            case 59: {
                objectArray = objectArray2;
                objectArray2[1] = "getShortType";
                break;
            }
            case 60: {
                objectArray = objectArray2;
                objectArray2[1] = "getIntType";
                break;
            }
            case 61: {
                objectArray = objectArray2;
                objectArray2[1] = "getLongType";
                break;
            }
            case 62: {
                objectArray = objectArray2;
                objectArray2[1] = "getFloatType";
                break;
            }
            case 63: {
                objectArray = objectArray2;
                objectArray2[1] = "getDoubleType";
                break;
            }
            case 64: {
                objectArray = objectArray2;
                objectArray2[1] = "getCharType";
                break;
            }
            case 65: {
                objectArray = objectArray2;
                objectArray2[1] = "getBooleanType";
                break;
            }
            case 66: {
                objectArray = objectArray2;
                objectArray2[1] = "getUnitType";
                break;
            }
            case 67: {
                objectArray = objectArray2;
                objectArray2[1] = "getStringType";
                break;
            }
            case 68: {
                objectArray = objectArray2;
                objectArray2[1] = "getIterableType";
                break;
            }
            case 70: 
            case 71: 
            case 72: {
                objectArray = objectArray2;
                objectArray2[1] = "getArrayElementType";
                break;
            }
            case 76: {
                objectArray = objectArray2;
                objectArray2[1] = "getPrimitiveArrayKotlinType";
                break;
            }
            case 83: {
                objectArray = objectArray2;
                objectArray2[1] = "getArrayType";
                break;
            }
            case 85: {
                objectArray = objectArray2;
                objectArray2[1] = "getEnumType";
                break;
            }
            case 86: {
                objectArray = objectArray2;
                objectArray2[1] = "getAnnotationType";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 1: {
                objectArray = objectArray;
                objectArray[2] = "setBuiltInsModule";
                break;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 10: 
            case 12: 
            case 14: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: 
            case 35: 
            case 36: 
            case 37: 
            case 38: 
            case 39: 
            case 40: 
            case 41: 
            case 42: 
            case 43: 
            case 44: 
            case 45: 
            case 46: 
            case 47: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 56: 
            case 57: 
            case 58: 
            case 59: 
            case 60: 
            case 61: 
            case 62: 
            case 63: 
            case 64: 
            case 65: 
            case 66: 
            case 67: 
            case 68: 
            case 70: 
            case 71: 
            case 72: 
            case 76: 
            case 83: 
            case 85: 
            case 86: {
                break;
            }
            case 8: {
                objectArray = objectArray;
                objectArray[2] = "isBuiltIn";
                break;
            }
            case 9: {
                objectArray = objectArray;
                objectArray[2] = "isUnderKotlinPackage";
                break;
            }
            case 11: {
                objectArray = objectArray;
                objectArray[2] = "getBuiltInClassByFqName";
                break;
            }
            case 13: {
                objectArray = objectArray;
                objectArray[2] = "getBuiltInClassByName";
                break;
            }
            case 15: {
                objectArray = objectArray;
                objectArray[2] = "getPrimitiveClassDescriptor";
                break;
            }
            case 16: {
                objectArray = objectArray;
                objectArray[2] = "getPrimitiveArrayClassDescriptor";
                break;
            }
            case 48: {
                objectArray = objectArray;
                objectArray[2] = "getBuiltInTypeByClassName";
                break;
            }
            case 55: {
                objectArray = objectArray;
                objectArray[2] = "getPrimitiveKotlinType";
                break;
            }
            case 69: {
                objectArray = objectArray;
                objectArray[2] = "getArrayElementType";
                break;
            }
            case 73: 
            case 74: {
                objectArray = objectArray;
                objectArray[2] = "getElementTypeForUnsignedArray";
                break;
            }
            case 75: {
                objectArray = objectArray;
                objectArray[2] = "getPrimitiveArrayKotlinType";
                break;
            }
            case 77: {
                objectArray = objectArray;
                objectArray[2] = "getPrimitiveArrayKotlinTypeByPrimitiveKotlinType";
                break;
            }
            case 78: 
            case 90: {
                objectArray = objectArray;
                objectArray[2] = "isPrimitiveArray";
                break;
            }
            case 79: 
            case 92: {
                objectArray = objectArray;
                objectArray[2] = "getPrimitiveType";
                break;
            }
            case 80: {
                objectArray = objectArray;
                objectArray[2] = "getPrimitiveArrayType";
                break;
            }
            case 81: 
            case 82: {
                objectArray = objectArray;
                objectArray[2] = "getArrayType";
                break;
            }
            case 84: {
                objectArray = objectArray;
                objectArray[2] = "getEnumType";
                break;
            }
            case 87: {
                objectArray = objectArray;
                objectArray[2] = "isArray";
                break;
            }
            case 88: 
            case 89: {
                objectArray = objectArray;
                objectArray[2] = "isArrayOrPrimitiveArray";
                break;
            }
            case 91: {
                objectArray = objectArray;
                objectArray[2] = "getPrimitiveArrayElementType";
                break;
            }
            case 93: {
                objectArray = objectArray;
                objectArray[2] = "isPrimitiveType";
                break;
            }
            case 94: {
                objectArray = objectArray;
                objectArray[2] = "isPrimitiveTypeOrNullablePrimitiveType";
                break;
            }
            case 95: {
                objectArray = objectArray;
                objectArray[2] = "isPrimitiveClass";
                break;
            }
            case 96: 
            case 97: 
            case 98: 
            case 99: {
                objectArray = objectArray;
                objectArray[2] = "isConstructedFromGivenClass";
                break;
            }
            case 100: 
            case 101: {
                objectArray = objectArray;
                objectArray[2] = "isTypeConstructorForGivenClass";
                break;
            }
            case 102: 
            case 103: {
                objectArray = objectArray;
                objectArray[2] = "classFqNameEquals";
                break;
            }
            case 104: 
            case 105: {
                objectArray = objectArray;
                objectArray[2] = "isNotNullConstructedFromGivenClass";
                break;
            }
            case 106: {
                objectArray = objectArray;
                objectArray[2] = "isSpecialClassWithNoSupertypes";
                break;
            }
            case 107: 
            case 108: {
                objectArray = objectArray;
                objectArray[2] = "isAny";
                break;
            }
            case 109: 
            case 111: {
                objectArray = objectArray;
                objectArray[2] = "isBoolean";
                break;
            }
            case 110: {
                objectArray = objectArray;
                objectArray[2] = "isBooleanOrNullableBoolean";
                break;
            }
            case 112: {
                objectArray = objectArray;
                objectArray[2] = "isNumber";
                break;
            }
            case 113: {
                objectArray = objectArray;
                objectArray[2] = "isChar";
                break;
            }
            case 114: {
                objectArray = objectArray;
                objectArray[2] = "isCharOrNullableChar";
                break;
            }
            case 115: {
                objectArray = objectArray;
                objectArray[2] = "isInt";
                break;
            }
            case 116: {
                objectArray = objectArray;
                objectArray[2] = "isByte";
                break;
            }
            case 117: {
                objectArray = objectArray;
                objectArray[2] = "isLong";
                break;
            }
            case 118: {
                objectArray = objectArray;
                objectArray[2] = "isLongOrNullableLong";
                break;
            }
            case 119: {
                objectArray = objectArray;
                objectArray[2] = "isShort";
                break;
            }
            case 120: {
                objectArray = objectArray;
                objectArray[2] = "isFloat";
                break;
            }
            case 121: {
                objectArray = objectArray;
                objectArray[2] = "isFloatOrNullableFloat";
                break;
            }
            case 122: {
                objectArray = objectArray;
                objectArray[2] = "isDouble";
                break;
            }
            case 123: {
                objectArray = objectArray;
                objectArray[2] = "isUByte";
                break;
            }
            case 124: {
                objectArray = objectArray;
                objectArray[2] = "isUShort";
                break;
            }
            case 125: {
                objectArray = objectArray;
                objectArray[2] = "isUInt";
                break;
            }
            case 126: {
                objectArray = objectArray;
                objectArray[2] = "isULong";
                break;
            }
            case 127: {
                objectArray = objectArray;
                objectArray[2] = "isDoubleOrNullableDouble";
                break;
            }
            case 128: 
            case 129: {
                objectArray = objectArray;
                objectArray[2] = "isConstructedFromGivenClassAndNotNullable";
                break;
            }
            case 130: {
                objectArray = objectArray;
                objectArray[2] = "isNothing";
                break;
            }
            case 131: {
                objectArray = objectArray;
                objectArray[2] = "isNullableNothing";
                break;
            }
            case 132: {
                objectArray = objectArray;
                objectArray[2] = "isNothingOrNullableNothing";
                break;
            }
            case 133: {
                objectArray = objectArray;
                objectArray[2] = "isAnyOrNullableAny";
                break;
            }
            case 134: {
                objectArray = objectArray;
                objectArray[2] = "isNullableAny";
                break;
            }
            case 135: {
                objectArray = objectArray;
                objectArray[2] = "isDefaultBound";
                break;
            }
            case 136: {
                objectArray = objectArray;
                objectArray[2] = "isUnit";
                break;
            }
            case 137: {
                objectArray = objectArray;
                objectArray[2] = "isUnitOrNullableUnit";
                break;
            }
            case 138: {
                objectArray = objectArray;
                objectArray[2] = "isBooleanOrSubtype";
                break;
            }
            case 139: {
                objectArray = objectArray;
                objectArray[2] = "isMemberOfAny";
                break;
            }
            case 140: 
            case 141: {
                objectArray = objectArray;
                objectArray[2] = "isEnum";
                break;
            }
            case 142: 
            case 143: {
                objectArray = objectArray;
                objectArray[2] = "isComparable";
                break;
            }
            case 144: {
                objectArray = objectArray;
                objectArray[2] = "isCollectionOrNullableCollection";
                break;
            }
            case 145: {
                objectArray = objectArray;
                objectArray[2] = "isListOrNullableList";
                break;
            }
            case 146: {
                objectArray = objectArray;
                objectArray[2] = "isSetOrNullableSet";
                break;
            }
            case 147: {
                objectArray = objectArray;
                objectArray[2] = "isMapOrNullableMap";
                break;
            }
            case 148: {
                objectArray = objectArray;
                objectArray[2] = "isIterableOrNullableIterable";
                break;
            }
            case 149: {
                objectArray = objectArray;
                objectArray[2] = "isThrowableOrNullableThrowable";
                break;
            }
            case 150: {
                objectArray = objectArray;
                objectArray[2] = "isKClass";
                break;
            }
            case 151: {
                objectArray = objectArray;
                objectArray[2] = "isNonPrimitiveArray";
                break;
            }
            case 152: {
                objectArray = objectArray;
                objectArray[2] = "isCloneable";
                break;
            }
            case 153: {
                objectArray = objectArray;
                objectArray[2] = "isDeprecated";
                break;
            }
            case 154: {
                objectArray = objectArray;
                objectArray[2] = "isNotNullOrNullableFunctionSupertype";
                break;
            }
            case 155: {
                objectArray = objectArray;
                objectArray[2] = "getPrimitiveFqName";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 10: 
            case 12: 
            case 14: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: 
            case 35: 
            case 36: 
            case 37: 
            case 38: 
            case 39: 
            case 40: 
            case 41: 
            case 42: 
            case 43: 
            case 44: 
            case 45: 
            case 46: 
            case 47: 
            case 49: 
            case 50: 
            case 51: 
            case 52: 
            case 53: 
            case 54: 
            case 56: 
            case 57: 
            case 58: 
            case 59: 
            case 60: 
            case 61: 
            case 62: 
            case 63: 
            case 64: 
            case 65: 
            case 66: 
            case 67: 
            case 68: 
            case 70: 
            case 71: 
            case 72: 
            case 76: 
            case 83: 
            case 85: 
            case 86: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }

    public static class FqNames {
        public final FqNameUnsafe any = FqNames.fqNameUnsafe("Any");
        public final FqNameUnsafe nothing = FqNames.fqNameUnsafe("Nothing");
        public final FqNameUnsafe cloneable = FqNames.fqNameUnsafe("Cloneable");
        public final FqName suppress = FqNames.fqName("Suppress");
        public final FqNameUnsafe unit = FqNames.fqNameUnsafe("Unit");
        public final FqNameUnsafe charSequence = FqNames.fqNameUnsafe("CharSequence");
        public final FqNameUnsafe string = FqNames.fqNameUnsafe("String");
        public final FqNameUnsafe array = FqNames.fqNameUnsafe("Array");
        public final FqNameUnsafe _boolean = FqNames.fqNameUnsafe("Boolean");
        public final FqNameUnsafe _char = FqNames.fqNameUnsafe("Char");
        public final FqNameUnsafe _byte = FqNames.fqNameUnsafe("Byte");
        public final FqNameUnsafe _short = FqNames.fqNameUnsafe("Short");
        public final FqNameUnsafe _int = FqNames.fqNameUnsafe("Int");
        public final FqNameUnsafe _long = FqNames.fqNameUnsafe("Long");
        public final FqNameUnsafe _float = FqNames.fqNameUnsafe("Float");
        public final FqNameUnsafe _double = FqNames.fqNameUnsafe("Double");
        public final FqNameUnsafe number = FqNames.fqNameUnsafe("Number");
        public final FqNameUnsafe _enum = FqNames.fqNameUnsafe("Enum");
        public final FqNameUnsafe functionSupertype = FqNames.fqNameUnsafe("Function");
        public final FqName throwable = FqNames.fqName("Throwable");
        public final FqName comparable = FqNames.fqName("Comparable");
        public final FqNameUnsafe intRange = FqNames.rangesFqName("IntRange");
        public final FqNameUnsafe longRange = FqNames.rangesFqName("LongRange");
        public final FqName deprecated = FqNames.fqName("Deprecated");
        public final FqName deprecatedSinceKotlin = FqNames.fqName("DeprecatedSinceKotlin");
        public final FqName deprecationLevel = FqNames.fqName("DeprecationLevel");
        public final FqName replaceWith = FqNames.fqName("ReplaceWith");
        public final FqName extensionFunctionType = FqNames.fqName("ExtensionFunctionType");
        public final FqName parameterName = FqNames.fqName("ParameterName");
        public final FqName annotation = FqNames.fqName("Annotation");
        public final FqName target = FqNames.annotationName("Target");
        public final FqName annotationTarget = FqNames.annotationName("AnnotationTarget");
        public final FqName annotationRetention = FqNames.annotationName("AnnotationRetention");
        public final FqName retention = FqNames.annotationName("Retention");
        public final FqName repeatable = FqNames.annotationName("Repeatable");
        public final FqName mustBeDocumented = FqNames.annotationName("MustBeDocumented");
        public final FqName unsafeVariance = FqNames.fqName("UnsafeVariance");
        public final FqName publishedApi = FqNames.fqName("PublishedApi");
        public final FqName iterator = FqNames.collectionsFqName("Iterator");
        public final FqName iterable = FqNames.collectionsFqName("Iterable");
        public final FqName collection = FqNames.collectionsFqName("Collection");
        public final FqName list = FqNames.collectionsFqName("List");
        public final FqName listIterator = FqNames.collectionsFqName("ListIterator");
        public final FqName set = FqNames.collectionsFqName("Set");
        public final FqName map = FqNames.collectionsFqName("Map");
        public final FqName mapEntry = this.map.child(Name.identifier("Entry"));
        public final FqName mutableIterator = FqNames.collectionsFqName("MutableIterator");
        public final FqName mutableIterable = FqNames.collectionsFqName("MutableIterable");
        public final FqName mutableCollection = FqNames.collectionsFqName("MutableCollection");
        public final FqName mutableList = FqNames.collectionsFqName("MutableList");
        public final FqName mutableListIterator = FqNames.collectionsFqName("MutableListIterator");
        public final FqName mutableSet = FqNames.collectionsFqName("MutableSet");
        public final FqName mutableMap = FqNames.collectionsFqName("MutableMap");
        public final FqName mutableMapEntry = this.mutableMap.child(Name.identifier("MutableEntry"));
        public final FqNameUnsafe kClass = FqNames.reflect("KClass");
        public final FqNameUnsafe kCallable = FqNames.reflect("KCallable");
        public final FqNameUnsafe kProperty0 = FqNames.reflect("KProperty0");
        public final FqNameUnsafe kProperty1 = FqNames.reflect("KProperty1");
        public final FqNameUnsafe kProperty2 = FqNames.reflect("KProperty2");
        public final FqNameUnsafe kMutableProperty0 = FqNames.reflect("KMutableProperty0");
        public final FqNameUnsafe kMutableProperty1 = FqNames.reflect("KMutableProperty1");
        public final FqNameUnsafe kMutableProperty2 = FqNames.reflect("KMutableProperty2");
        public final FqNameUnsafe kPropertyFqName = FqNames.reflect("KProperty");
        public final FqNameUnsafe kMutablePropertyFqName = FqNames.reflect("KMutableProperty");
        public final ClassId kProperty = ClassId.topLevel(this.kPropertyFqName.toSafe());
        public final FqNameUnsafe kDeclarationContainer = FqNames.reflect("KDeclarationContainer");
        public final FqName uByteFqName = FqNames.fqName("UByte");
        public final FqName uShortFqName = FqNames.fqName("UShort");
        public final FqName uIntFqName = FqNames.fqName("UInt");
        public final FqName uLongFqName = FqNames.fqName("ULong");
        public final ClassId uByte = ClassId.topLevel(this.uByteFqName);
        public final ClassId uShort = ClassId.topLevel(this.uShortFqName);
        public final ClassId uInt = ClassId.topLevel(this.uIntFqName);
        public final ClassId uLong = ClassId.topLevel(this.uLongFqName);
        public final Set<Name> primitiveTypeShortNames = CollectionsKt.newHashSetWithExpectedSize(PrimitiveType.values().length);
        public final Set<Name> primitiveArrayTypeShortNames = CollectionsKt.newHashSetWithExpectedSize(PrimitiveType.values().length);
        public final Map<FqNameUnsafe, PrimitiveType> fqNameToPrimitiveType = CollectionsKt.newHashMapWithExpectedSize(PrimitiveType.values().length);
        public final Map<FqNameUnsafe, PrimitiveType> arrayClassFqNameToPrimitiveType = CollectionsKt.newHashMapWithExpectedSize(PrimitiveType.values().length);

        public FqNames() {
            for (PrimitiveType primitiveType : PrimitiveType.values()) {
                this.primitiveTypeShortNames.add(primitiveType.getTypeName());
                this.primitiveArrayTypeShortNames.add(primitiveType.getArrayTypeName());
                this.fqNameToPrimitiveType.put(FqNames.fqNameUnsafe(primitiveType.getTypeName().asString()), primitiveType);
                this.arrayClassFqNameToPrimitiveType.put(FqNames.fqNameUnsafe(primitiveType.getArrayTypeName().asString()), primitiveType);
            }
        }

        @NotNull
        private static FqNameUnsafe fqNameUnsafe(@NotNull String simpleName2) {
            if (simpleName2 == null) {
                FqNames.$$$reportNull$$$0(0);
            }
            FqNameUnsafe fqNameUnsafe = FqNames.fqName(simpleName2).toUnsafe();
            if (fqNameUnsafe == null) {
                FqNames.$$$reportNull$$$0(1);
            }
            return fqNameUnsafe;
        }

        @NotNull
        private static FqName fqName(@NotNull String simpleName2) {
            if (simpleName2 == null) {
                FqNames.$$$reportNull$$$0(2);
            }
            FqName fqName2 = BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier(simpleName2));
            if (fqName2 == null) {
                FqNames.$$$reportNull$$$0(3);
            }
            return fqName2;
        }

        @NotNull
        private static FqName collectionsFqName(@NotNull String simpleName2) {
            if (simpleName2 == null) {
                FqNames.$$$reportNull$$$0(4);
            }
            FqName fqName2 = COLLECTIONS_PACKAGE_FQ_NAME.child(Name.identifier(simpleName2));
            if (fqName2 == null) {
                FqNames.$$$reportNull$$$0(5);
            }
            return fqName2;
        }

        @NotNull
        private static FqNameUnsafe rangesFqName(@NotNull String simpleName2) {
            if (simpleName2 == null) {
                FqNames.$$$reportNull$$$0(6);
            }
            FqNameUnsafe fqNameUnsafe = RANGES_PACKAGE_FQ_NAME.child(Name.identifier(simpleName2)).toUnsafe();
            if (fqNameUnsafe == null) {
                FqNames.$$$reportNull$$$0(7);
            }
            return fqNameUnsafe;
        }

        @NotNull
        private static FqNameUnsafe reflect(@NotNull String simpleName2) {
            if (simpleName2 == null) {
                FqNames.$$$reportNull$$$0(8);
            }
            FqNameUnsafe fqNameUnsafe = ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME().child(Name.identifier(simpleName2)).toUnsafe();
            if (fqNameUnsafe == null) {
                FqNames.$$$reportNull$$$0(9);
            }
            return fqNameUnsafe;
        }

        @NotNull
        private static FqName annotationName(@NotNull String simpleName2) {
            if (simpleName2 == null) {
                FqNames.$$$reportNull$$$0(10);
            }
            FqName fqName2 = ANNOTATION_PACKAGE_FQ_NAME.child(Name.identifier(simpleName2));
            if (fqName2 == null) {
                FqNames.$$$reportNull$$$0(11);
            }
            return fqName2;
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            RuntimeException runtimeException;
            Object[] objectArray;
            Object[] objectArray2;
            int n2;
            String string;
            switch (n) {
                default: {
                    string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                }
                case 1: 
                case 3: 
                case 5: 
                case 7: 
                case 9: 
                case 11: {
                    string = "@NotNull method %s.%s must not return null";
                    break;
                }
            }
            switch (n) {
                default: {
                    n2 = 3;
                    break;
                }
                case 1: 
                case 3: 
                case 5: 
                case 7: 
                case 9: 
                case 11: {
                    n2 = 2;
                    break;
                }
            }
            Object[] objectArray3 = new Object[n2];
            switch (n) {
                default: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "simpleName";
                    break;
                }
                case 1: 
                case 3: 
                case 5: 
                case 7: 
                case 9: 
                case 11: {
                    objectArray2 = objectArray3;
                    objectArray3[0] = "kotlin/reflect/jvm/internal/impl/builtins/KotlinBuiltIns$FqNames";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[1] = "kotlin/reflect/jvm/internal/impl/builtins/KotlinBuiltIns$FqNames";
                    break;
                }
                case 1: {
                    objectArray = objectArray2;
                    objectArray2[1] = "fqNameUnsafe";
                    break;
                }
                case 3: {
                    objectArray = objectArray2;
                    objectArray2[1] = "fqName";
                    break;
                }
                case 5: {
                    objectArray = objectArray2;
                    objectArray2[1] = "collectionsFqName";
                    break;
                }
                case 7: {
                    objectArray = objectArray2;
                    objectArray2[1] = "rangesFqName";
                    break;
                }
                case 9: {
                    objectArray = objectArray2;
                    objectArray2[1] = "reflect";
                    break;
                }
                case 11: {
                    objectArray = objectArray2;
                    objectArray2[1] = "annotationName";
                    break;
                }
            }
            switch (n) {
                default: {
                    objectArray = objectArray;
                    objectArray[2] = "fqNameUnsafe";
                    break;
                }
                case 1: 
                case 3: 
                case 5: 
                case 7: 
                case 9: 
                case 11: {
                    break;
                }
                case 2: {
                    objectArray = objectArray;
                    objectArray[2] = "fqName";
                    break;
                }
                case 4: {
                    objectArray = objectArray;
                    objectArray[2] = "collectionsFqName";
                    break;
                }
                case 6: {
                    objectArray = objectArray;
                    objectArray[2] = "rangesFqName";
                    break;
                }
                case 8: {
                    objectArray = objectArray;
                    objectArray[2] = "reflect";
                    break;
                }
                case 10: {
                    objectArray = objectArray;
                    objectArray[2] = "annotationName";
                    break;
                }
            }
            String string2 = String.format(string, objectArray);
            switch (n) {
                default: {
                    runtimeException = new IllegalArgumentException(string2);
                    break;
                }
                case 1: 
                case 3: 
                case 5: 
                case 7: 
                case 9: 
                case 11: {
                    runtimeException = new IllegalStateException(string2);
                    break;
                }
            }
            throw runtimeException;
        }
    }

    private static class Primitives {
        public final Map<PrimitiveType, SimpleType> primitiveTypeToArrayKotlinType;
        public final Map<KotlinType, SimpleType> primitiveKotlinTypeToKotlinArrayType;
        public final Map<SimpleType, SimpleType> kotlinArrayTypeToPrimitiveKotlinType;

        private Primitives(@NotNull Map<PrimitiveType, SimpleType> primitiveTypeToArrayKotlinType, @NotNull Map<KotlinType, SimpleType> primitiveKotlinTypeToKotlinArrayType, @NotNull Map<SimpleType, SimpleType> kotlinArrayTypeToPrimitiveKotlinType) {
            if (primitiveTypeToArrayKotlinType == null) {
                Primitives.$$$reportNull$$$0(0);
            }
            if (primitiveKotlinTypeToKotlinArrayType == null) {
                Primitives.$$$reportNull$$$0(1);
            }
            if (kotlinArrayTypeToPrimitiveKotlinType == null) {
                Primitives.$$$reportNull$$$0(2);
            }
            this.primitiveTypeToArrayKotlinType = primitiveTypeToArrayKotlinType;
            this.primitiveKotlinTypeToKotlinArrayType = primitiveKotlinTypeToKotlinArrayType;
            this.kotlinArrayTypeToPrimitiveKotlinType = kotlinArrayTypeToPrimitiveKotlinType;
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            Object[] objectArray;
            Object[] objectArray2 = new Object[3];
            switch (n) {
                default: {
                    objectArray = objectArray2;
                    objectArray2[0] = "primitiveTypeToArrayKotlinType";
                    break;
                }
                case 1: {
                    objectArray = objectArray2;
                    objectArray2[0] = "primitiveKotlinTypeToKotlinArrayType";
                    break;
                }
                case 2: {
                    objectArray = objectArray2;
                    objectArray2[0] = "kotlinArrayTypeToPrimitiveKotlinType";
                    break;
                }
            }
            objectArray[1] = "kotlin/reflect/jvm/internal/impl/builtins/KotlinBuiltIns$Primitives";
            objectArray[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
        }
    }
}

