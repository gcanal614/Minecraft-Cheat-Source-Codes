/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.BuiltinMethodsWithDifferentJvmName;
import kotlin.reflect.jvm.internal.impl.load.java.BuiltinMethodsWithSpecialGenericSignature;
import kotlin.reflect.jvm.internal.impl.load.java.BuiltinSpecialProperties;
import kotlin.reflect.jvm.internal.impl.load.java.NameAndSignature;
import kotlin.reflect.jvm.internal.impl.load.java.SpecialBuiltinMembers;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckingProcedure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@JvmName(name="SpecialBuiltinMembers")
public final class SpecialBuiltinMembers {
    private static final FqName child(FqName $this$child, String name) {
        FqName fqName2 = $this$child.child(Name.identifier(name));
        Intrinsics.checkNotNullExpressionValue(fqName2, "child(Name.identifier(name))");
        return fqName2;
    }

    private static final FqName childSafe(FqNameUnsafe $this$childSafe, String name) {
        FqName fqName2 = $this$childSafe.child(Name.identifier(name)).toSafe();
        Intrinsics.checkNotNullExpressionValue(fqName2, "child(Name.identifier(name)).toSafe()");
        return fqName2;
    }

    private static final NameAndSignature method(String $this$method, String name, String parameters2, String returnType) {
        Name name2 = Name.identifier(name);
        Intrinsics.checkNotNullExpressionValue(name2, "Name.identifier(name)");
        return new NameAndSignature(name2, SignatureBuildingComponents.INSTANCE.signature($this$method, name + '(' + parameters2 + ')' + returnType));
    }

    @Nullable
    public static final <T extends CallableMemberDescriptor> T getOverriddenBuiltinWithDifferentJvmName(@NotNull T $this$getOverriddenBuiltinWithDifferentJvmName) {
        Intrinsics.checkNotNullParameter($this$getOverriddenBuiltinWithDifferentJvmName, "$this$getOverriddenBuiltinWithDifferentJvmName");
        if (!BuiltinMethodsWithDifferentJvmName.INSTANCE.getORIGINAL_SHORT_NAMES().contains($this$getOverriddenBuiltinWithDifferentJvmName.getName()) && !BuiltinSpecialProperties.INSTANCE.getSPECIAL_SHORT_NAMES$descriptors_jvm().contains(DescriptorUtilsKt.getPropertyIfAccessor($this$getOverriddenBuiltinWithDifferentJvmName).getName())) {
            return null;
        }
        T t = $this$getOverriddenBuiltinWithDifferentJvmName;
        return (T)(t instanceof PropertyDescriptor || t instanceof PropertyAccessorDescriptor ? DescriptorUtilsKt.firstOverridden$default($this$getOverriddenBuiltinWithDifferentJvmName, false, getOverriddenBuiltinWithDifferentJvmName.1.INSTANCE, 1, null) : (t instanceof SimpleFunctionDescriptor ? DescriptorUtilsKt.firstOverridden$default($this$getOverriddenBuiltinWithDifferentJvmName, false, getOverriddenBuiltinWithDifferentJvmName.2.INSTANCE, 1, null) : null));
    }

    public static final boolean doesOverrideBuiltinWithDifferentJvmName(@NotNull CallableMemberDescriptor $this$doesOverrideBuiltinWithDifferentJvmName) {
        Intrinsics.checkNotNullParameter($this$doesOverrideBuiltinWithDifferentJvmName, "$this$doesOverrideBuiltinWithDifferentJvmName");
        return SpecialBuiltinMembers.getOverriddenBuiltinWithDifferentJvmName($this$doesOverrideBuiltinWithDifferentJvmName) != null;
    }

    @Nullable
    public static final <T extends CallableMemberDescriptor> T getOverriddenSpecialBuiltin(@NotNull T $this$getOverriddenSpecialBuiltin) {
        Intrinsics.checkNotNullParameter($this$getOverriddenSpecialBuiltin, "$this$getOverriddenSpecialBuiltin");
        T t = SpecialBuiltinMembers.getOverriddenBuiltinWithDifferentJvmName($this$getOverriddenSpecialBuiltin);
        if (t != null) {
            T t2 = t;
            boolean bl = false;
            boolean bl2 = false;
            T it = t2;
            boolean bl3 = false;
            return it;
        }
        Name name = $this$getOverriddenSpecialBuiltin.getName();
        Intrinsics.checkNotNullExpressionValue(name, "name");
        if (!BuiltinMethodsWithSpecialGenericSignature.INSTANCE.getSameAsBuiltinMethodWithErasedValueParameters(name)) {
            return null;
        }
        return (T)DescriptorUtilsKt.firstOverridden$default($this$getOverriddenSpecialBuiltin, false, getOverriddenSpecialBuiltin.2.INSTANCE, 1, null);
    }

    @Nullable
    public static final String getJvmMethodNameIfSpecial(@NotNull CallableMemberDescriptor callableMemberDescriptor) {
        String string;
        CallableMemberDescriptor overriddenBuiltin;
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "callableMemberDescriptor");
        CallableMemberDescriptor callableMemberDescriptor2 = SpecialBuiltinMembers.getOverriddenBuiltinThatAffectsJvmName(callableMemberDescriptor);
        if (callableMemberDescriptor2 == null || (callableMemberDescriptor2 = DescriptorUtilsKt.getPropertyIfAccessor(callableMemberDescriptor2)) == null) {
            return null;
        }
        CallableMemberDescriptor callableMemberDescriptor3 = overriddenBuiltin = callableMemberDescriptor2;
        if (callableMemberDescriptor3 instanceof PropertyDescriptor) {
            string = BuiltinSpecialProperties.INSTANCE.getBuiltinSpecialPropertyGetterName(overriddenBuiltin);
        } else if (callableMemberDescriptor3 instanceof SimpleFunctionDescriptor) {
            Name name = BuiltinMethodsWithDifferentJvmName.INSTANCE.getJvmName((SimpleFunctionDescriptor)overriddenBuiltin);
            string = name != null ? name.asString() : null;
        } else {
            string = null;
        }
        return string;
    }

    private static final CallableMemberDescriptor getOverriddenBuiltinThatAffectsJvmName(CallableMemberDescriptor callableMemberDescriptor) {
        return KotlinBuiltIns.isBuiltIn(callableMemberDescriptor) ? SpecialBuiltinMembers.getOverriddenBuiltinWithDifferentJvmName(callableMemberDescriptor) : null;
    }

    public static final boolean hasRealKotlinSuperClassWithOverrideOf(@NotNull ClassDescriptor $this$hasRealKotlinSuperClassWithOverrideOf, @NotNull CallableDescriptor specialCallableDescriptor) {
        Intrinsics.checkNotNullParameter($this$hasRealKotlinSuperClassWithOverrideOf, "$this$hasRealKotlinSuperClassWithOverrideOf");
        Intrinsics.checkNotNullParameter(specialCallableDescriptor, "specialCallableDescriptor");
        DeclarationDescriptor declarationDescriptor = specialCallableDescriptor.getContainingDeclaration();
        if (declarationDescriptor == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        }
        SimpleType simpleType2 = ((ClassDescriptor)declarationDescriptor).getDefaultType();
        Intrinsics.checkNotNullExpressionValue(simpleType2, "(specialCallableDescript\u2026ssDescriptor).defaultType");
        SimpleType builtinContainerDefaultType = simpleType2;
        ClassDescriptor superClassDescriptor = DescriptorUtils.getSuperClassDescriptor($this$hasRealKotlinSuperClassWithOverrideOf);
        while (superClassDescriptor != null) {
            if (!(superClassDescriptor instanceof JavaClassDescriptor)) {
                boolean doesOverrideBuiltinDeclaration;
                boolean bl = doesOverrideBuiltinDeclaration = TypeCheckingProcedure.findCorrespondingSupertype(superClassDescriptor.getDefaultType(), builtinContainerDefaultType) != null;
                if (doesOverrideBuiltinDeclaration) {
                    return !KotlinBuiltIns.isBuiltIn(superClassDescriptor);
                }
            }
            superClassDescriptor = DescriptorUtils.getSuperClassDescriptor(superClassDescriptor);
        }
        return false;
    }

    public static final boolean isFromJava(@NotNull CallableMemberDescriptor $this$isFromJava) {
        Intrinsics.checkNotNullParameter($this$isFromJava, "$this$isFromJava");
        CallableMemberDescriptor descriptor2 = DescriptorUtilsKt.getPropertyIfAccessor($this$isFromJava);
        return descriptor2.getContainingDeclaration() instanceof JavaClassDescriptor;
    }

    public static final boolean isFromJavaOrBuiltins(@NotNull CallableMemberDescriptor $this$isFromJavaOrBuiltins) {
        Intrinsics.checkNotNullParameter($this$isFromJavaOrBuiltins, "$this$isFromJavaOrBuiltins");
        return SpecialBuiltinMembers.isFromJava($this$isFromJavaOrBuiltins) || KotlinBuiltIns.isBuiltIn($this$isFromJavaOrBuiltins);
    }

    public static final /* synthetic */ FqName access$childSafe(FqNameUnsafe $this$access_u24childSafe, String name) {
        return SpecialBuiltinMembers.childSafe($this$access_u24childSafe, name);
    }

    public static final /* synthetic */ FqName access$child(FqName $this$access_u24child, String name) {
        return SpecialBuiltinMembers.child($this$access_u24child, name);
    }

    public static final /* synthetic */ NameAndSignature access$method(String $this$access_u24method, String name, String parameters2, String returnType) {
        return SpecialBuiltinMembers.method($this$access_u24method, name, parameters2, returnType);
    }
}

