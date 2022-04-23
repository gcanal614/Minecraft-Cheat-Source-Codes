/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.UnsignedTypes;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithSource;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorWithVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceFile;
import kotlin.reflect.jvm.internal.impl.descriptors.VariableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DescriptorUtils {
    public static final Name ENUM_VALUES = Name.identifier("values");
    public static final Name ENUM_VALUE_OF = Name.identifier("valueOf");
    public static final FqName COROUTINES_PACKAGE_FQ_NAME_RELEASE = new FqName("kotlin.coroutines");
    public static final FqName COROUTINES_PACKAGE_FQ_NAME_EXPERIMENTAL = COROUTINES_PACKAGE_FQ_NAME_RELEASE.child(Name.identifier("experimental"));
    public static final FqName COROUTINES_INTRINSICS_PACKAGE_FQ_NAME_EXPERIMENTAL = COROUTINES_PACKAGE_FQ_NAME_EXPERIMENTAL.child(Name.identifier("intrinsics"));
    public static final FqName CONTINUATION_INTERFACE_FQ_NAME_EXPERIMENTAL = COROUTINES_PACKAGE_FQ_NAME_EXPERIMENTAL.child(Name.identifier("Continuation"));
    public static final FqName CONTINUATION_INTERFACE_FQ_NAME_RELEASE = COROUTINES_PACKAGE_FQ_NAME_RELEASE.child(Name.identifier("Continuation"));
    public static final FqName RESULT_FQ_NAME = new FqName("kotlin.Result");
    public static final FqName JVM_NAME = new FqName("kotlin.jvm.JvmName");

    private DescriptorUtils() {
    }

    @Nullable
    public static ReceiverParameterDescriptor getDispatchReceiverParameterIfNeeded(@NotNull DeclarationDescriptor containingDeclaration) {
        if (containingDeclaration == null) {
            DescriptorUtils.$$$reportNull$$$0(0);
        }
        if (containingDeclaration instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor = (ClassDescriptor)containingDeclaration;
            return classDescriptor.getThisAsReceiverParameter();
        }
        return null;
    }

    public static boolean isLocal(@NotNull DeclarationDescriptor descriptor2) {
        if (descriptor2 == null) {
            DescriptorUtils.$$$reportNull$$$0(1);
        }
        for (DeclarationDescriptor current = descriptor2; current != null; current = current.getContainingDeclaration()) {
            if (!DescriptorUtils.isAnonymousObject(current) && !DescriptorUtils.isDescriptorWithLocalVisibility(current)) continue;
            return true;
        }
        return false;
    }

    public static boolean isDescriptorWithLocalVisibility(DeclarationDescriptor current) {
        return current instanceof DeclarationDescriptorWithVisibility && ((DeclarationDescriptorWithVisibility)current).getVisibility() == Visibilities.LOCAL;
    }

    @NotNull
    public static FqNameUnsafe getFqName(@NotNull DeclarationDescriptor descriptor2) {
        FqName safe;
        if (descriptor2 == null) {
            DescriptorUtils.$$$reportNull$$$0(2);
        }
        return (safe = DescriptorUtils.getFqNameSafeIfPossible(descriptor2)) != null ? safe.toUnsafe() : DescriptorUtils.getFqNameUnsafe(descriptor2);
    }

    @NotNull
    public static FqName getFqNameSafe(@NotNull DeclarationDescriptor descriptor2) {
        FqName safe;
        if (descriptor2 == null) {
            DescriptorUtils.$$$reportNull$$$0(3);
        }
        FqName fqName2 = (safe = DescriptorUtils.getFqNameSafeIfPossible(descriptor2)) != null ? safe : DescriptorUtils.getFqNameUnsafe(descriptor2).toSafe();
        if (fqName2 == null) {
            DescriptorUtils.$$$reportNull$$$0(4);
        }
        return fqName2;
    }

    @Nullable
    private static FqName getFqNameSafeIfPossible(@NotNull DeclarationDescriptor descriptor2) {
        if (descriptor2 == null) {
            DescriptorUtils.$$$reportNull$$$0(5);
        }
        if (descriptor2 instanceof ModuleDescriptor || ErrorUtils.isError(descriptor2)) {
            return FqName.ROOT;
        }
        if (descriptor2 instanceof PackageViewDescriptor) {
            return ((PackageViewDescriptor)descriptor2).getFqName();
        }
        if (descriptor2 instanceof PackageFragmentDescriptor) {
            return ((PackageFragmentDescriptor)descriptor2).getFqName();
        }
        return null;
    }

    @NotNull
    private static FqNameUnsafe getFqNameUnsafe(@NotNull DeclarationDescriptor descriptor2) {
        if (descriptor2 == null) {
            DescriptorUtils.$$$reportNull$$$0(6);
        }
        DeclarationDescriptor containingDeclaration = descriptor2.getContainingDeclaration();
        assert (containingDeclaration != null) : "Not package/module descriptor doesn't have containing declaration: " + descriptor2;
        FqNameUnsafe fqNameUnsafe = DescriptorUtils.getFqName(containingDeclaration).child(descriptor2.getName());
        if (fqNameUnsafe == null) {
            DescriptorUtils.$$$reportNull$$$0(7);
        }
        return fqNameUnsafe;
    }

    public static boolean isTopLevelDeclaration(@Nullable DeclarationDescriptor descriptor2) {
        return descriptor2 != null && descriptor2.getContainingDeclaration() instanceof PackageFragmentDescriptor;
    }

    public static boolean areInSameModule(@NotNull DeclarationDescriptor first, @NotNull DeclarationDescriptor second) {
        if (first == null) {
            DescriptorUtils.$$$reportNull$$$0(14);
        }
        if (second == null) {
            DescriptorUtils.$$$reportNull$$$0(15);
        }
        return DescriptorUtils.getContainingModule(first).equals(DescriptorUtils.getContainingModule(second));
    }

    @Nullable
    public static <D extends DeclarationDescriptor> D getParentOfType(@Nullable DeclarationDescriptor descriptor2, @NotNull Class<D> aClass) {
        if (aClass == null) {
            DescriptorUtils.$$$reportNull$$$0(16);
        }
        return DescriptorUtils.getParentOfType(descriptor2, aClass, true);
    }

    @Nullable
    public static <D extends DeclarationDescriptor> D getParentOfType(@Nullable DeclarationDescriptor descriptor2, @NotNull Class<D> aClass, boolean strict) {
        if (aClass == null) {
            DescriptorUtils.$$$reportNull$$$0(17);
        }
        if (descriptor2 == null) {
            return null;
        }
        if (strict) {
            descriptor2 = descriptor2.getContainingDeclaration();
        }
        while (descriptor2 != null) {
            if (aClass.isInstance(descriptor2)) {
                return (D)descriptor2;
            }
            descriptor2 = descriptor2.getContainingDeclaration();
        }
        return null;
    }

    @Nullable
    public static ModuleDescriptor getContainingModuleOrNull(@NotNull KotlinType kotlinType) {
        ClassifierDescriptor descriptor2;
        if (kotlinType == null) {
            DescriptorUtils.$$$reportNull$$$0(18);
        }
        if ((descriptor2 = kotlinType.getConstructor().getDeclarationDescriptor()) == null) {
            return null;
        }
        return DescriptorUtils.getContainingModuleOrNull(descriptor2);
    }

    @NotNull
    public static ModuleDescriptor getContainingModule(@NotNull DeclarationDescriptor descriptor2) {
        if (descriptor2 == null) {
            DescriptorUtils.$$$reportNull$$$0(19);
        }
        ModuleDescriptor module = DescriptorUtils.getContainingModuleOrNull(descriptor2);
        assert (module != null) : "Descriptor without a containing module: " + descriptor2;
        ModuleDescriptor moduleDescriptor = module;
        if (moduleDescriptor == null) {
            DescriptorUtils.$$$reportNull$$$0(20);
        }
        return moduleDescriptor;
    }

    @Nullable
    public static ModuleDescriptor getContainingModuleOrNull(@NotNull DeclarationDescriptor descriptor2) {
        if (descriptor2 == null) {
            DescriptorUtils.$$$reportNull$$$0(21);
        }
        while (descriptor2 != null) {
            if (descriptor2 instanceof ModuleDescriptor) {
                return (ModuleDescriptor)descriptor2;
            }
            if (descriptor2 instanceof PackageViewDescriptor) {
                return ((PackageViewDescriptor)descriptor2).getModule();
            }
            descriptor2 = descriptor2.getContainingDeclaration();
        }
        return null;
    }

    public static boolean isDirectSubclass(@NotNull ClassDescriptor subClass, @NotNull ClassDescriptor superClass) {
        if (subClass == null) {
            DescriptorUtils.$$$reportNull$$$0(24);
        }
        if (superClass == null) {
            DescriptorUtils.$$$reportNull$$$0(25);
        }
        for (KotlinType superType : subClass.getTypeConstructor().getSupertypes()) {
            if (!DescriptorUtils.isSameClass(superType, superClass.getOriginal())) continue;
            return true;
        }
        return false;
    }

    public static boolean isSubclass(@NotNull ClassDescriptor subClass, @NotNull ClassDescriptor superClass) {
        if (subClass == null) {
            DescriptorUtils.$$$reportNull$$$0(26);
        }
        if (superClass == null) {
            DescriptorUtils.$$$reportNull$$$0(27);
        }
        return DescriptorUtils.isSubtypeOfClass(subClass.getDefaultType(), superClass.getOriginal());
    }

    private static boolean isSameClass(@NotNull KotlinType type2, @NotNull DeclarationDescriptor other) {
        DeclarationDescriptor originalDescriptor;
        ClassifierDescriptor descriptor2;
        if (type2 == null) {
            DescriptorUtils.$$$reportNull$$$0(28);
        }
        if (other == null) {
            DescriptorUtils.$$$reportNull$$$0(29);
        }
        return (descriptor2 = type2.getConstructor().getDeclarationDescriptor()) != null && (originalDescriptor = descriptor2.getOriginal()) instanceof ClassifierDescriptor && other instanceof ClassifierDescriptor && ((ClassifierDescriptor)other).getTypeConstructor().equals(((ClassifierDescriptor)originalDescriptor).getTypeConstructor());
    }

    public static boolean isSubtypeOfClass(@NotNull KotlinType type2, @NotNull DeclarationDescriptor superClass) {
        if (type2 == null) {
            DescriptorUtils.$$$reportNull$$$0(30);
        }
        if (superClass == null) {
            DescriptorUtils.$$$reportNull$$$0(31);
        }
        if (DescriptorUtils.isSameClass(type2, superClass)) {
            return true;
        }
        for (KotlinType superType : type2.getConstructor().getSupertypes()) {
            if (!DescriptorUtils.isSubtypeOfClass(superType, superClass)) continue;
            return true;
        }
        return false;
    }

    public static boolean isCompanionObject(@Nullable DeclarationDescriptor descriptor2) {
        return DescriptorUtils.isKindOf(descriptor2, ClassKind.OBJECT) && ((ClassDescriptor)descriptor2).isCompanionObject();
    }

    public static boolean isSealedClass(@Nullable DeclarationDescriptor descriptor2) {
        return DescriptorUtils.isKindOf(descriptor2, ClassKind.CLASS) && ((ClassDescriptor)descriptor2).getModality() == Modality.SEALED;
    }

    public static boolean isAnonymousObject(@NotNull DeclarationDescriptor descriptor2) {
        if (descriptor2 == null) {
            DescriptorUtils.$$$reportNull$$$0(32);
        }
        return DescriptorUtils.isClass(descriptor2) && descriptor2.getName().equals(SpecialNames.NO_NAME_PROVIDED);
    }

    public static boolean isEnumEntry(@NotNull DeclarationDescriptor descriptor2) {
        if (descriptor2 == null) {
            DescriptorUtils.$$$reportNull$$$0(34);
        }
        return DescriptorUtils.isKindOf(descriptor2, ClassKind.ENUM_ENTRY);
    }

    public static boolean isEnumClass(@Nullable DeclarationDescriptor descriptor2) {
        return DescriptorUtils.isKindOf(descriptor2, ClassKind.ENUM_CLASS);
    }

    public static boolean isAnnotationClass(@Nullable DeclarationDescriptor descriptor2) {
        return DescriptorUtils.isKindOf(descriptor2, ClassKind.ANNOTATION_CLASS);
    }

    public static boolean isInterface(@Nullable DeclarationDescriptor descriptor2) {
        return DescriptorUtils.isKindOf(descriptor2, ClassKind.INTERFACE);
    }

    public static boolean isClass(@Nullable DeclarationDescriptor descriptor2) {
        return DescriptorUtils.isKindOf(descriptor2, ClassKind.CLASS);
    }

    public static boolean isClassOrEnumClass(@Nullable DeclarationDescriptor descriptor2) {
        return DescriptorUtils.isClass(descriptor2) || DescriptorUtils.isEnumClass(descriptor2);
    }

    private static boolean isKindOf(@Nullable DeclarationDescriptor descriptor2, @NotNull ClassKind classKind) {
        if (classKind == null) {
            DescriptorUtils.$$$reportNull$$$0(35);
        }
        return descriptor2 instanceof ClassDescriptor && ((ClassDescriptor)descriptor2).getKind() == classKind;
    }

    @Nullable
    public static ClassDescriptor getSuperClassDescriptor(@NotNull ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            DescriptorUtils.$$$reportNull$$$0(42);
        }
        Collection<KotlinType> superclassTypes = classDescriptor.getTypeConstructor().getSupertypes();
        for (KotlinType type2 : superclassTypes) {
            ClassDescriptor superClassDescriptor = DescriptorUtils.getClassDescriptorForType(type2);
            if (superClassDescriptor.getKind() == ClassKind.INTERFACE) continue;
            return superClassDescriptor;
        }
        return null;
    }

    @NotNull
    public static ClassDescriptor getClassDescriptorForType(@NotNull KotlinType type2) {
        if (type2 == null) {
            DescriptorUtils.$$$reportNull$$$0(43);
        }
        return DescriptorUtils.getClassDescriptorForTypeConstructor(type2.getConstructor());
    }

    @NotNull
    public static ClassDescriptor getClassDescriptorForTypeConstructor(@NotNull TypeConstructor typeConstructor2) {
        if (typeConstructor2 == null) {
            DescriptorUtils.$$$reportNull$$$0(44);
        }
        ClassifierDescriptor descriptor2 = typeConstructor2.getDeclarationDescriptor();
        assert (descriptor2 instanceof ClassDescriptor) : "Classifier descriptor of a type should be of type ClassDescriptor: " + typeConstructor2;
        ClassDescriptor classDescriptor = (ClassDescriptor)descriptor2;
        if (classDescriptor == null) {
            DescriptorUtils.$$$reportNull$$$0(45);
        }
        return classDescriptor;
    }

    @NotNull
    public static Visibility getDefaultConstructorVisibility(@NotNull ClassDescriptor classDescriptor) {
        ClassKind classKind;
        if (classDescriptor == null) {
            DescriptorUtils.$$$reportNull$$$0(46);
        }
        if ((classKind = classDescriptor.getKind()) == ClassKind.ENUM_CLASS || classKind.isSingleton() || DescriptorUtils.isSealedClass(classDescriptor)) {
            Visibility visibility = Visibilities.PRIVATE;
            if (visibility == null) {
                DescriptorUtils.$$$reportNull$$$0(47);
            }
            return visibility;
        }
        if (DescriptorUtils.isAnonymousObject(classDescriptor)) {
            Visibility visibility = Visibilities.DEFAULT_VISIBILITY;
            if (visibility == null) {
                DescriptorUtils.$$$reportNull$$$0(48);
            }
            return visibility;
        }
        assert (classKind == ClassKind.CLASS || classKind == ClassKind.INTERFACE || classKind == ClassKind.ANNOTATION_CLASS);
        Visibility visibility = Visibilities.PUBLIC;
        if (visibility == null) {
            DescriptorUtils.$$$reportNull$$$0(49);
        }
        return visibility;
    }

    @NotNull
    public static <D extends CallableMemberDescriptor> D unwrapFakeOverride(@NotNull D descriptor2) {
        if (descriptor2 == null) {
            DescriptorUtils.$$$reportNull$$$0(55);
        }
        while (descriptor2.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            Collection<? extends CallableMemberDescriptor> overridden = descriptor2.getOverriddenDescriptors();
            if (overridden.isEmpty()) {
                throw new IllegalStateException("Fake override should have at least one overridden descriptor: " + descriptor2);
            }
            descriptor2 = overridden.iterator().next();
        }
        D d = descriptor2;
        if (d == null) {
            DescriptorUtils.$$$reportNull$$$0(56);
        }
        return d;
    }

    @NotNull
    public static <D extends DeclarationDescriptorWithVisibility> D unwrapFakeOverrideToAnyDeclaration(@NotNull D descriptor2) {
        if (descriptor2 == null) {
            DescriptorUtils.$$$reportNull$$$0(57);
        }
        if (descriptor2 instanceof CallableMemberDescriptor) {
            return (D)DescriptorUtils.unwrapFakeOverride((CallableMemberDescriptor)descriptor2);
        }
        D d = descriptor2;
        if (d == null) {
            DescriptorUtils.$$$reportNull$$$0(58);
        }
        return d;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean shouldRecordInitializerForProperty(@NotNull VariableDescriptor variable, @NotNull KotlinType type2) {
        if (variable == null) {
            DescriptorUtils.$$$reportNull$$$0(59);
        }
        if (type2 == null) {
            DescriptorUtils.$$$reportNull$$$0(60);
        }
        if (variable.isVar()) return false;
        if (KotlinTypeKt.isError(type2)) {
            return false;
        }
        if (TypeUtils.acceptsNullable(type2)) {
            return true;
        }
        KotlinBuiltIns builtIns = DescriptorUtilsKt.getBuiltIns(variable);
        if (KotlinBuiltIns.isPrimitiveType(type2)) return true;
        if (KotlinTypeChecker.DEFAULT.equalTypes(builtIns.getStringType(), type2)) return true;
        if (KotlinTypeChecker.DEFAULT.equalTypes(builtIns.getNumber().getDefaultType(), type2)) return true;
        if (KotlinTypeChecker.DEFAULT.equalTypes(builtIns.getAnyType(), type2)) return true;
        if (!UnsignedTypes.isUnsignedType(type2)) return false;
        return true;
    }

    @NotNull
    public static <D extends CallableDescriptor> Set<D> getAllOverriddenDescriptors(@NotNull D f2) {
        if (f2 == null) {
            DescriptorUtils.$$$reportNull$$$0(64);
        }
        LinkedHashSet result2 = new LinkedHashSet();
        DescriptorUtils.collectAllOverriddenDescriptors(f2.getOriginal(), result2);
        LinkedHashSet linkedHashSet = result2;
        if (linkedHashSet == null) {
            DescriptorUtils.$$$reportNull$$$0(65);
        }
        return linkedHashSet;
    }

    private static <D extends CallableDescriptor> void collectAllOverriddenDescriptors(@NotNull D current, @NotNull Set<D> result2) {
        if (current == null) {
            DescriptorUtils.$$$reportNull$$$0(66);
        }
        if (result2 == null) {
            DescriptorUtils.$$$reportNull$$$0(67);
        }
        if (result2.contains(current)) {
            return;
        }
        for (CallableDescriptor callableDescriptor : current.getOriginal().getOverriddenDescriptors()) {
            CallableDescriptor descriptor2 = callableDescriptor.getOriginal();
            DescriptorUtils.collectAllOverriddenDescriptors(descriptor2, result2);
            result2.add(descriptor2);
        }
    }

    @NotNull
    public static SourceFile getContainingSourceFile(@NotNull DeclarationDescriptor descriptor2) {
        if (descriptor2 == null) {
            DescriptorUtils.$$$reportNull$$$0(75);
        }
        if (descriptor2 instanceof PropertySetterDescriptor) {
            descriptor2 = ((PropertySetterDescriptor)descriptor2).getCorrespondingProperty();
        }
        if (descriptor2 instanceof DeclarationDescriptorWithSource) {
            SourceFile sourceFile = ((DeclarationDescriptorWithSource)descriptor2).getSource().getContainingFile();
            if (sourceFile == null) {
                DescriptorUtils.$$$reportNull$$$0(76);
            }
            return sourceFile;
        }
        SourceFile sourceFile = SourceFile.NO_SOURCE_FILE;
        if (sourceFile == null) {
            DescriptorUtils.$$$reportNull$$$0(77);
        }
        return sourceFile;
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
            case 4: 
            case 7: 
            case 9: 
            case 10: 
            case 20: 
            case 38: 
            case 40: 
            case 41: 
            case 45: 
            case 47: 
            case 48: 
            case 49: 
            case 56: 
            case 58: 
            case 65: 
            case 69: 
            case 76: 
            case 77: 
            case 79: 
            case 82: 
            case 87: 
            case 89: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 4: 
            case 7: 
            case 9: 
            case 10: 
            case 20: 
            case 38: 
            case 40: 
            case 41: 
            case 45: 
            case 47: 
            case 48: 
            case 49: 
            case 56: 
            case 58: 
            case 65: 
            case 69: 
            case 76: 
            case 77: 
            case 79: 
            case 82: 
            case 87: 
            case 89: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "containingDeclaration";
                break;
            }
            case 1: 
            case 2: 
            case 3: 
            case 5: 
            case 6: 
            case 8: 
            case 11: 
            case 12: 
            case 13: 
            case 19: 
            case 21: 
            case 22: 
            case 32: 
            case 33: 
            case 34: 
            case 53: 
            case 54: 
            case 55: 
            case 57: 
            case 75: 
            case 88: 
            case 90: {
                objectArray2 = objectArray3;
                objectArray3[0] = "descriptor";
                break;
            }
            case 4: 
            case 7: 
            case 9: 
            case 10: 
            case 20: 
            case 38: 
            case 40: 
            case 41: 
            case 45: 
            case 47: 
            case 48: 
            case 49: 
            case 56: 
            case 58: 
            case 65: 
            case 69: 
            case 76: 
            case 77: 
            case 79: 
            case 82: 
            case 87: 
            case 89: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/resolve/DescriptorUtils";
                break;
            }
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "first";
                break;
            }
            case 15: {
                objectArray2 = objectArray3;
                objectArray3[0] = "second";
                break;
            }
            case 16: 
            case 17: {
                objectArray2 = objectArray3;
                objectArray3[0] = "aClass";
                break;
            }
            case 18: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlinType";
                break;
            }
            case 23: {
                objectArray2 = objectArray3;
                objectArray3[0] = "declarationDescriptor";
                break;
            }
            case 24: 
            case 26: {
                objectArray2 = objectArray3;
                objectArray3[0] = "subClass";
                break;
            }
            case 25: 
            case 27: 
            case 31: {
                objectArray2 = objectArray3;
                objectArray3[0] = "superClass";
                break;
            }
            case 28: 
            case 30: 
            case 43: 
            case 60: {
                objectArray2 = objectArray3;
                objectArray3[0] = "type";
                break;
            }
            case 29: {
                objectArray2 = objectArray3;
                objectArray3[0] = "other";
                break;
            }
            case 35: {
                objectArray2 = objectArray3;
                objectArray3[0] = "classKind";
                break;
            }
            case 36: 
            case 37: 
            case 39: 
            case 42: 
            case 46: 
            case 50: 
            case 61: 
            case 62: 
            case 63: 
            case 70: 
            case 71: {
                objectArray2 = objectArray3;
                objectArray3[0] = "classDescriptor";
                break;
            }
            case 44: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeConstructor";
                break;
            }
            case 51: {
                objectArray2 = objectArray3;
                objectArray3[0] = "innerClassName";
                break;
            }
            case 52: {
                objectArray2 = objectArray3;
                objectArray3[0] = "location";
                break;
            }
            case 59: {
                objectArray2 = objectArray3;
                objectArray3[0] = "variable";
                break;
            }
            case 64: {
                objectArray2 = objectArray3;
                objectArray3[0] = "f";
                break;
            }
            case 66: {
                objectArray2 = objectArray3;
                objectArray3[0] = "current";
                break;
            }
            case 67: {
                objectArray2 = objectArray3;
                objectArray3[0] = "result";
                break;
            }
            case 68: {
                objectArray2 = objectArray3;
                objectArray3[0] = "memberDescriptor";
                break;
            }
            case 72: 
            case 73: 
            case 74: {
                objectArray2 = objectArray3;
                objectArray3[0] = "annotated";
                break;
            }
            case 78: 
            case 80: 
            case 83: 
            case 85: {
                objectArray2 = objectArray3;
                objectArray3[0] = "scope";
                break;
            }
            case 81: 
            case 84: 
            case 86: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/resolve/DescriptorUtils";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "getFqNameSafe";
                break;
            }
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "getFqNameUnsafe";
                break;
            }
            case 9: 
            case 10: {
                objectArray = objectArray2;
                objectArray2[1] = "getFqNameFromTopLevelClass";
                break;
            }
            case 20: {
                objectArray = objectArray2;
                objectArray2[1] = "getContainingModule";
                break;
            }
            case 38: {
                objectArray = objectArray2;
                objectArray2[1] = "getSuperclassDescriptors";
                break;
            }
            case 40: 
            case 41: {
                objectArray = objectArray2;
                objectArray2[1] = "getSuperClassType";
                break;
            }
            case 45: {
                objectArray = objectArray2;
                objectArray2[1] = "getClassDescriptorForTypeConstructor";
                break;
            }
            case 47: 
            case 48: 
            case 49: {
                objectArray = objectArray2;
                objectArray2[1] = "getDefaultConstructorVisibility";
                break;
            }
            case 56: {
                objectArray = objectArray2;
                objectArray2[1] = "unwrapFakeOverride";
                break;
            }
            case 58: {
                objectArray = objectArray2;
                objectArray2[1] = "unwrapFakeOverrideToAnyDeclaration";
                break;
            }
            case 65: {
                objectArray = objectArray2;
                objectArray2[1] = "getAllOverriddenDescriptors";
                break;
            }
            case 69: {
                objectArray = objectArray2;
                objectArray2[1] = "getAllOverriddenDeclarations";
                break;
            }
            case 76: 
            case 77: {
                objectArray = objectArray2;
                objectArray2[1] = "getContainingSourceFile";
                break;
            }
            case 79: {
                objectArray = objectArray2;
                objectArray2[1] = "getAllDescriptors";
                break;
            }
            case 82: {
                objectArray = objectArray2;
                objectArray2[1] = "getFunctionByName";
                break;
            }
            case 87: {
                objectArray = objectArray2;
                objectArray2[1] = "getPropertyByName";
                break;
            }
            case 89: {
                objectArray = objectArray2;
                objectArray2[1] = "getDirectMember";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "getDispatchReceiverParameterIfNeeded";
                break;
            }
            case 1: {
                objectArray = objectArray;
                objectArray[2] = "isLocal";
                break;
            }
            case 2: {
                objectArray = objectArray;
                objectArray[2] = "getFqName";
                break;
            }
            case 3: {
                objectArray = objectArray;
                objectArray[2] = "getFqNameSafe";
                break;
            }
            case 4: 
            case 7: 
            case 9: 
            case 10: 
            case 20: 
            case 38: 
            case 40: 
            case 41: 
            case 45: 
            case 47: 
            case 48: 
            case 49: 
            case 56: 
            case 58: 
            case 65: 
            case 69: 
            case 76: 
            case 77: 
            case 79: 
            case 82: 
            case 87: 
            case 89: {
                break;
            }
            case 5: {
                objectArray = objectArray;
                objectArray[2] = "getFqNameSafeIfPossible";
                break;
            }
            case 6: {
                objectArray = objectArray;
                objectArray[2] = "getFqNameUnsafe";
                break;
            }
            case 8: {
                objectArray = objectArray;
                objectArray[2] = "getFqNameFromTopLevelClass";
                break;
            }
            case 11: {
                objectArray = objectArray;
                objectArray[2] = "isExtension";
                break;
            }
            case 12: {
                objectArray = objectArray;
                objectArray[2] = "isOverride";
                break;
            }
            case 13: {
                objectArray = objectArray;
                objectArray[2] = "isStaticDeclaration";
                break;
            }
            case 14: 
            case 15: {
                objectArray = objectArray;
                objectArray[2] = "areInSameModule";
                break;
            }
            case 16: 
            case 17: {
                objectArray = objectArray;
                objectArray[2] = "getParentOfType";
                break;
            }
            case 18: 
            case 21: {
                objectArray = objectArray;
                objectArray[2] = "getContainingModuleOrNull";
                break;
            }
            case 19: {
                objectArray = objectArray;
                objectArray[2] = "getContainingModule";
                break;
            }
            case 22: {
                objectArray = objectArray;
                objectArray[2] = "getContainingClass";
                break;
            }
            case 23: {
                objectArray = objectArray;
                objectArray[2] = "isAncestor";
                break;
            }
            case 24: 
            case 25: {
                objectArray = objectArray;
                objectArray[2] = "isDirectSubclass";
                break;
            }
            case 26: 
            case 27: {
                objectArray = objectArray;
                objectArray[2] = "isSubclass";
                break;
            }
            case 28: 
            case 29: {
                objectArray = objectArray;
                objectArray[2] = "isSameClass";
                break;
            }
            case 30: 
            case 31: {
                objectArray = objectArray;
                objectArray[2] = "isSubtypeOfClass";
                break;
            }
            case 32: {
                objectArray = objectArray;
                objectArray[2] = "isAnonymousObject";
                break;
            }
            case 33: {
                objectArray = objectArray;
                objectArray[2] = "isAnonymousFunction";
                break;
            }
            case 34: {
                objectArray = objectArray;
                objectArray[2] = "isEnumEntry";
                break;
            }
            case 35: {
                objectArray = objectArray;
                objectArray[2] = "isKindOf";
                break;
            }
            case 36: {
                objectArray = objectArray;
                objectArray[2] = "hasAbstractMembers";
                break;
            }
            case 37: {
                objectArray = objectArray;
                objectArray[2] = "getSuperclassDescriptors";
                break;
            }
            case 39: {
                objectArray = objectArray;
                objectArray[2] = "getSuperClassType";
                break;
            }
            case 42: {
                objectArray = objectArray;
                objectArray[2] = "getSuperClassDescriptor";
                break;
            }
            case 43: {
                objectArray = objectArray;
                objectArray[2] = "getClassDescriptorForType";
                break;
            }
            case 44: {
                objectArray = objectArray;
                objectArray[2] = "getClassDescriptorForTypeConstructor";
                break;
            }
            case 46: {
                objectArray = objectArray;
                objectArray[2] = "getDefaultConstructorVisibility";
                break;
            }
            case 50: 
            case 51: 
            case 52: {
                objectArray = objectArray;
                objectArray[2] = "getInnerClassByName";
                break;
            }
            case 53: {
                objectArray = objectArray;
                objectArray[2] = "isStaticNestedClass";
                break;
            }
            case 54: {
                objectArray = objectArray;
                objectArray[2] = "isTopLevelOrInnerClass";
                break;
            }
            case 55: {
                objectArray = objectArray;
                objectArray[2] = "unwrapFakeOverride";
                break;
            }
            case 57: {
                objectArray = objectArray;
                objectArray[2] = "unwrapFakeOverrideToAnyDeclaration";
                break;
            }
            case 59: 
            case 60: {
                objectArray = objectArray;
                objectArray[2] = "shouldRecordInitializerForProperty";
                break;
            }
            case 61: {
                objectArray = objectArray;
                objectArray[2] = "classCanHaveAbstractFakeOverride";
                break;
            }
            case 62: {
                objectArray = objectArray;
                objectArray[2] = "classCanHaveAbstractDeclaration";
                break;
            }
            case 63: {
                objectArray = objectArray;
                objectArray[2] = "classCanHaveOpenMembers";
                break;
            }
            case 64: {
                objectArray = objectArray;
                objectArray[2] = "getAllOverriddenDescriptors";
                break;
            }
            case 66: 
            case 67: {
                objectArray = objectArray;
                objectArray[2] = "collectAllOverriddenDescriptors";
                break;
            }
            case 68: {
                objectArray = objectArray;
                objectArray[2] = "getAllOverriddenDeclarations";
                break;
            }
            case 70: {
                objectArray = objectArray;
                objectArray[2] = "isSingletonOrAnonymousObject";
                break;
            }
            case 71: {
                objectArray = objectArray;
                objectArray[2] = "canHaveDeclaredConstructors";
                break;
            }
            case 72: {
                objectArray = objectArray;
                objectArray[2] = "getJvmName";
                break;
            }
            case 73: {
                objectArray = objectArray;
                objectArray[2] = "findJvmNameAnnotation";
                break;
            }
            case 74: {
                objectArray = objectArray;
                objectArray[2] = "hasJvmNameAnnotation";
                break;
            }
            case 75: {
                objectArray = objectArray;
                objectArray[2] = "getContainingSourceFile";
                break;
            }
            case 78: {
                objectArray = objectArray;
                objectArray[2] = "getAllDescriptors";
                break;
            }
            case 80: 
            case 81: {
                objectArray = objectArray;
                objectArray[2] = "getFunctionByName";
                break;
            }
            case 83: 
            case 84: {
                objectArray = objectArray;
                objectArray[2] = "getFunctionByNameOrNull";
                break;
            }
            case 85: 
            case 86: {
                objectArray = objectArray;
                objectArray[2] = "getPropertyByName";
                break;
            }
            case 88: {
                objectArray = objectArray;
                objectArray[2] = "getDirectMember";
                break;
            }
            case 90: {
                objectArray = objectArray;
                objectArray[2] = "isMethodOfAny";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 4: 
            case 7: 
            case 9: 
            case 10: 
            case 20: 
            case 38: 
            case 40: 
            case 41: 
            case 45: 
            case 47: 
            case 48: 
            case 49: 
            case 56: 
            case 58: 
            case 65: 
            case 69: 
            case 76: 
            case 77: 
            case 79: 
            case 82: 
            case 87: 
            case 89: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

