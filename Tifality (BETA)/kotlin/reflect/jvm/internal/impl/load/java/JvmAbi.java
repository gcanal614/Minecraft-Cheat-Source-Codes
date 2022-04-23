/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.reflect.jvm.internal.impl.builtins.CompanionObjectMapping;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FieldDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;
import org.jetbrains.annotations.NotNull;

public final class JvmAbi {
    public static final FqName JVM_FIELD_ANNOTATION_FQ_NAME = new FqName("kotlin.jvm.JvmField");
    public static final ClassId REFLECTION_FACTORY_IMPL = ClassId.topLevel(new FqName("kotlin.reflect.jvm.internal.ReflectionFactoryImpl"));

    public static boolean isGetterName(@NotNull String name) {
        if (name == null) {
            JvmAbi.$$$reportNull$$$0(4);
        }
        return name.startsWith("get") || name.startsWith("is");
    }

    public static boolean isSetterName(@NotNull String name) {
        if (name == null) {
            JvmAbi.$$$reportNull$$$0(5);
        }
        return name.startsWith("set");
    }

    @NotNull
    public static String getterName(@NotNull String propertyName) {
        if (propertyName == null) {
            JvmAbi.$$$reportNull$$$0(6);
        }
        String string = JvmAbi.startsWithIsPrefix(propertyName) ? propertyName : "get" + CapitalizeDecapitalizeKt.capitalizeAsciiOnly(propertyName);
        if (string == null) {
            JvmAbi.$$$reportNull$$$0(7);
        }
        return string;
    }

    @NotNull
    public static String setterName(@NotNull String propertyName) {
        if (propertyName == null) {
            JvmAbi.$$$reportNull$$$0(8);
        }
        String string = "set" + (JvmAbi.startsWithIsPrefix(propertyName) ? propertyName.substring("is".length()) : CapitalizeDecapitalizeKt.capitalizeAsciiOnly(propertyName));
        if (string == null) {
            JvmAbi.$$$reportNull$$$0(9);
        }
        return string;
    }

    public static boolean startsWithIsPrefix(String name) {
        if (!name.startsWith("is")) {
            return false;
        }
        if (name.length() == "is".length()) {
            return false;
        }
        char c = name.charAt("is".length());
        return 'a' > c || c > 'z';
    }

    public static boolean isPropertyWithBackingFieldInOuterClass(@NotNull PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            JvmAbi.$$$reportNull$$$0(10);
        }
        if (propertyDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            return false;
        }
        if (JvmAbi.isClassCompanionObjectWithBackingFieldsInOuter(propertyDescriptor.getContainingDeclaration())) {
            return true;
        }
        return DescriptorUtils.isCompanionObject(propertyDescriptor.getContainingDeclaration()) && JvmAbi.hasJvmFieldAnnotation(propertyDescriptor);
    }

    public static boolean isClassCompanionObjectWithBackingFieldsInOuter(@NotNull DeclarationDescriptor companionObject) {
        if (companionObject == null) {
            JvmAbi.$$$reportNull$$$0(11);
        }
        return DescriptorUtils.isCompanionObject(companionObject) && DescriptorUtils.isClassOrEnumClass(companionObject.getContainingDeclaration()) && !JvmAbi.isMappedIntrinsicCompanionObject((ClassDescriptor)companionObject);
    }

    public static boolean isMappedIntrinsicCompanionObject(@NotNull ClassDescriptor companionObject) {
        if (companionObject == null) {
            JvmAbi.$$$reportNull$$$0(12);
        }
        return CompanionObjectMapping.INSTANCE.isMappedIntrinsicCompanionObject(companionObject);
    }

    public static boolean hasJvmFieldAnnotation(@NotNull CallableMemberDescriptor memberDescriptor) {
        FieldDescriptor field;
        if (memberDescriptor == null) {
            JvmAbi.$$$reportNull$$$0(13);
        }
        if (memberDescriptor instanceof PropertyDescriptor && (field = ((PropertyDescriptor)memberDescriptor).getBackingField()) != null && field.getAnnotations().hasAnnotation(JVM_FIELD_ANNOTATION_FQ_NAME)) {
            return true;
        }
        return memberDescriptor.getAnnotations().hasAnnotation(JVM_FIELD_ANNOTATION_FQ_NAME);
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
            case 7: 
            case 9: {
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
            case 7: 
            case 9: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "baseName";
                break;
            }
            case 1: 
            case 3: 
            case 7: 
            case 9: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/load/java/JvmAbi";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeAliasName";
                break;
            }
            case 4: 
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 6: 
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "propertyName";
                break;
            }
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "propertyDescriptor";
                break;
            }
            case 11: 
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "companionObject";
                break;
            }
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "memberDescriptor";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/load/java/JvmAbi";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[1] = "getSyntheticMethodNameForAnnotatedProperty";
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = "getSyntheticMethodNameForAnnotatedTypeAlias";
                break;
            }
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "getterName";
                break;
            }
            case 9: {
                objectArray = objectArray2;
                objectArray2[1] = "setterName";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "getSyntheticMethodNameForAnnotatedProperty";
                break;
            }
            case 1: 
            case 3: 
            case 7: 
            case 9: {
                break;
            }
            case 2: {
                objectArray = objectArray;
                objectArray[2] = "getSyntheticMethodNameForAnnotatedTypeAlias";
                break;
            }
            case 4: {
                objectArray = objectArray;
                objectArray[2] = "isGetterName";
                break;
            }
            case 5: {
                objectArray = objectArray;
                objectArray[2] = "isSetterName";
                break;
            }
            case 6: {
                objectArray = objectArray;
                objectArray[2] = "getterName";
                break;
            }
            case 8: {
                objectArray = objectArray;
                objectArray[2] = "setterName";
                break;
            }
            case 10: {
                objectArray = objectArray;
                objectArray[2] = "isPropertyWithBackingFieldInOuterClass";
                break;
            }
            case 11: {
                objectArray = objectArray;
                objectArray[2] = "isClassCompanionObjectWithBackingFieldsInOuter";
                break;
            }
            case 12: {
                objectArray = objectArray;
                objectArray[2] = "isMappedIntrinsicCompanionObject";
                break;
            }
            case 13: {
                objectArray = objectArray;
                objectArray[2] = "hasJvmFieldAnnotation";
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
            case 7: 
            case 9: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

