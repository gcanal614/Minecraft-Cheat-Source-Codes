/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.components;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifier;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifierType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMember;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaValueParameter;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.NonReportingOverrideStrategy;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ErrorReporter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DescriptorResolverUtils {
    @NotNull
    public static <D extends CallableMemberDescriptor> Collection<D> resolveOverridesForNonStaticMembers(@NotNull Name name, @NotNull Collection<D> membersFromSupertypes, @NotNull Collection<D> membersFromCurrent, @NotNull ClassDescriptor classDescriptor, @NotNull ErrorReporter errorReporter, @NotNull OverridingUtil overridingUtil2) {
        if (name == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(0);
        }
        if (membersFromSupertypes == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(1);
        }
        if (membersFromCurrent == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(2);
        }
        if (classDescriptor == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(3);
        }
        if (errorReporter == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(4);
        }
        if (overridingUtil2 == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(5);
        }
        return DescriptorResolverUtils.resolveOverrides(name, membersFromSupertypes, membersFromCurrent, classDescriptor, errorReporter, overridingUtil2, false);
    }

    @NotNull
    public static <D extends CallableMemberDescriptor> Collection<D> resolveOverridesForStaticMembers(@NotNull Name name, @NotNull Collection<D> membersFromSupertypes, @NotNull Collection<D> membersFromCurrent, @NotNull ClassDescriptor classDescriptor, @NotNull ErrorReporter errorReporter, @NotNull OverridingUtil overridingUtil2) {
        if (name == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(6);
        }
        if (membersFromSupertypes == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(7);
        }
        if (membersFromCurrent == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(8);
        }
        if (classDescriptor == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(9);
        }
        if (errorReporter == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(10);
        }
        if (overridingUtil2 == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(11);
        }
        return DescriptorResolverUtils.resolveOverrides(name, membersFromSupertypes, membersFromCurrent, classDescriptor, errorReporter, overridingUtil2, true);
    }

    @NotNull
    private static <D extends CallableMemberDescriptor> Collection<D> resolveOverrides(@NotNull Name name, @NotNull Collection<D> membersFromSupertypes, @NotNull Collection<D> membersFromCurrent, @NotNull ClassDescriptor classDescriptor, final @NotNull ErrorReporter errorReporter, @NotNull OverridingUtil overridingUtil2, final boolean isStaticContext) {
        if (name == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(12);
        }
        if (membersFromSupertypes == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(13);
        }
        if (membersFromCurrent == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(14);
        }
        if (classDescriptor == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(15);
        }
        if (errorReporter == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(16);
        }
        if (overridingUtil2 == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(17);
        }
        final LinkedHashSet result2 = new LinkedHashSet();
        overridingUtil2.generateOverridesInFunctionGroup(name, membersFromSupertypes, membersFromCurrent, classDescriptor, new NonReportingOverrideStrategy(){

            @Override
            public void addFakeOverride(@NotNull CallableMemberDescriptor fakeOverride) {
                if (fakeOverride == null) {
                    1.$$$reportNull$$$0(0);
                }
                OverridingUtil.resolveUnknownVisibilityForMember(fakeOverride, new Function1<CallableMemberDescriptor, Unit>(){

                    @Override
                    public Unit invoke(@NotNull CallableMemberDescriptor descriptor2) {
                        if (descriptor2 == null) {
                            1.$$$reportNull$$$0(0);
                        }
                        errorReporter.reportCannotInferVisibility(descriptor2);
                        return Unit.INSTANCE;
                    }

                    private static /* synthetic */ void $$$reportNull$$$0(int n) {
                        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "descriptor", "kotlin/reflect/jvm/internal/impl/load/java/components/DescriptorResolverUtils$1$1", "invoke"));
                    }
                });
                result2.add(fakeOverride);
            }

            @Override
            public void conflict(@NotNull CallableMemberDescriptor fromSuper, @NotNull CallableMemberDescriptor fromCurrent) {
                if (fromSuper == null) {
                    1.$$$reportNull$$$0(1);
                }
                if (fromCurrent == null) {
                    1.$$$reportNull$$$0(2);
                }
            }

            @Override
            public void setOverriddenDescriptors(@NotNull CallableMemberDescriptor member, @NotNull Collection<? extends CallableMemberDescriptor> overridden) {
                if (member == null) {
                    1.$$$reportNull$$$0(3);
                }
                if (overridden == null) {
                    1.$$$reportNull$$$0(4);
                }
                if (isStaticContext && member.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                    return;
                }
                super.setOverriddenDescriptors(member, overridden);
            }

            private static /* synthetic */ void $$$reportNull$$$0(int n) {
                Object[] objectArray;
                Object[] objectArray2;
                Object[] objectArray3 = new Object[3];
                switch (n) {
                    default: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "fakeOverride";
                        break;
                    }
                    case 1: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "fromSuper";
                        break;
                    }
                    case 2: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "fromCurrent";
                        break;
                    }
                    case 3: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "member";
                        break;
                    }
                    case 4: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "overridden";
                        break;
                    }
                }
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/load/java/components/DescriptorResolverUtils$1";
                switch (n) {
                    default: {
                        objectArray = objectArray2;
                        objectArray2[2] = "addFakeOverride";
                        break;
                    }
                    case 1: 
                    case 2: {
                        objectArray = objectArray2;
                        objectArray2[2] = "conflict";
                        break;
                    }
                    case 3: 
                    case 4: {
                        objectArray = objectArray2;
                        objectArray2[2] = "setOverriddenDescriptors";
                        break;
                    }
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
            }
        });
        LinkedHashSet linkedHashSet = result2;
        if (linkedHashSet == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(18);
        }
        return linkedHashSet;
    }

    @Nullable
    public static ValueParameterDescriptor getAnnotationParameterByName(@NotNull Name name, @NotNull ClassDescriptor annotationClass) {
        Collection<ClassConstructorDescriptor> constructors2;
        if (name == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(19);
        }
        if (annotationClass == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(20);
        }
        if ((constructors2 = annotationClass.getConstructors()).size() != 1) {
            return null;
        }
        for (ValueParameterDescriptor parameter : constructors2.iterator().next().getValueParameters()) {
            if (!parameter.getName().equals(name)) continue;
            return parameter;
        }
        return null;
    }

    public static boolean isObjectMethodInInterface(@NotNull JavaMember member) {
        if (member == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(21);
        }
        return member.getContainingClass().isInterface() && member instanceof JavaMethod && DescriptorResolverUtils.isObjectMethod((JavaMethod)member);
    }

    private static boolean isObjectMethod(@NotNull JavaMethod method) {
        String name;
        if (method == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(22);
        }
        if ((name = method.getName().asString()).equals("toString") || name.equals("hashCode")) {
            return method.getValueParameters().isEmpty();
        }
        if (name.equals("equals")) {
            return DescriptorResolverUtils.isMethodWithOneObjectParameter(method);
        }
        return false;
    }

    private static boolean isMethodWithOneObjectParameter(@NotNull JavaMethod method) {
        JavaClassifier classifier2;
        JavaType type2;
        List<JavaValueParameter> parameters2;
        if (method == null) {
            DescriptorResolverUtils.$$$reportNull$$$0(23);
        }
        if ((parameters2 = method.getValueParameters()).size() == 1 && (type2 = parameters2.get(0).getType()) instanceof JavaClassifierType && (classifier2 = ((JavaClassifierType)type2).getClassifier()) instanceof JavaClass) {
            FqName classFqName = ((JavaClass)classifier2).getFqName();
            return classFqName != null && classFqName.asString().equals("java.lang.Object");
        }
        return false;
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
            case 18: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 18: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 1: 
            case 7: 
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "membersFromSupertypes";
                break;
            }
            case 2: 
            case 8: 
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "membersFromCurrent";
                break;
            }
            case 3: 
            case 9: 
            case 15: {
                objectArray2 = objectArray3;
                objectArray3[0] = "classDescriptor";
                break;
            }
            case 4: 
            case 10: 
            case 16: {
                objectArray2 = objectArray3;
                objectArray3[0] = "errorReporter";
                break;
            }
            case 5: 
            case 11: 
            case 17: {
                objectArray2 = objectArray3;
                objectArray3[0] = "overridingUtil";
                break;
            }
            case 18: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/load/java/components/DescriptorResolverUtils";
                break;
            }
            case 20: {
                objectArray2 = objectArray3;
                objectArray3[0] = "annotationClass";
                break;
            }
            case 21: {
                objectArray2 = objectArray3;
                objectArray3[0] = "member";
                break;
            }
            case 22: 
            case 23: {
                objectArray2 = objectArray3;
                objectArray3[0] = "method";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/load/java/components/DescriptorResolverUtils";
                break;
            }
            case 18: {
                objectArray = objectArray2;
                objectArray2[1] = "resolveOverrides";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "resolveOverridesForNonStaticMembers";
                break;
            }
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: {
                objectArray = objectArray;
                objectArray[2] = "resolveOverridesForStaticMembers";
                break;
            }
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 17: {
                objectArray = objectArray;
                objectArray[2] = "resolveOverrides";
                break;
            }
            case 18: {
                break;
            }
            case 19: 
            case 20: {
                objectArray = objectArray;
                objectArray[2] = "getAnnotationParameterByName";
                break;
            }
            case 21: {
                objectArray = objectArray;
                objectArray[2] = "isObjectMethodInInterface";
                break;
            }
            case 22: {
                objectArray = objectArray;
                objectArray[2] = "isObjectMethod";
                break;
            }
            case 23: {
                objectArray = objectArray;
                objectArray[2] = "isMethodWithOneObjectParameter";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 18: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

