/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import java.util.List;
import kotlin.Pair;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstUtil;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertySetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaCallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.ValueParameterData;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnhancementKt;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavaPropertyDescriptor
extends PropertyDescriptorImpl
implements JavaCallableMemberDescriptor {
    private final boolean isStaticFinal;
    @Nullable
    private final Pair<CallableDescriptor.UserDataKey<?>, ?> singleUserData;

    protected JavaPropertyDescriptor(@NotNull DeclarationDescriptor containingDeclaration, @NotNull Annotations annotations2, @NotNull Modality modality, @NotNull Visibility visibility, boolean isVar, @NotNull Name name, @NotNull SourceElement source, @Nullable PropertyDescriptor original, @NotNull CallableMemberDescriptor.Kind kind, boolean isStaticFinal, @Nullable Pair<CallableDescriptor.UserDataKey<?>, ?> singleUserData) {
        if (containingDeclaration == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(0);
        }
        if (annotations2 == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(1);
        }
        if (modality == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(2);
        }
        if (visibility == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(3);
        }
        if (name == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(4);
        }
        if (source == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(5);
        }
        if (kind == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(6);
        }
        super(containingDeclaration, original, annotations2, modality, visibility, isVar, name, kind, source, false, false, false, false, false, false);
        this.isStaticFinal = isStaticFinal;
        this.singleUserData = singleUserData;
    }

    @NotNull
    public static JavaPropertyDescriptor create(@NotNull DeclarationDescriptor containingDeclaration, @NotNull Annotations annotations2, @NotNull Modality modality, @NotNull Visibility visibility, boolean isVar, @NotNull Name name, @NotNull SourceElement source, boolean isStaticFinal) {
        if (containingDeclaration == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(7);
        }
        if (annotations2 == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(8);
        }
        if (modality == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(9);
        }
        if (visibility == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(10);
        }
        if (name == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(11);
        }
        if (source == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(12);
        }
        return new JavaPropertyDescriptor(containingDeclaration, annotations2, modality, visibility, isVar, name, source, null, CallableMemberDescriptor.Kind.DECLARATION, isStaticFinal, null);
    }

    @Override
    @NotNull
    protected PropertyDescriptorImpl createSubstitutedCopy(@NotNull DeclarationDescriptor newOwner, @NotNull Modality newModality, @NotNull Visibility newVisibility, @Nullable PropertyDescriptor original, @NotNull CallableMemberDescriptor.Kind kind, @NotNull Name newName, @NotNull SourceElement source) {
        if (newOwner == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(13);
        }
        if (newModality == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(14);
        }
        if (newVisibility == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(15);
        }
        if (kind == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(16);
        }
        if (newName == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(17);
        }
        if (source == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(18);
        }
        return new JavaPropertyDescriptor(newOwner, this.getAnnotations(), newModality, newVisibility, this.isVar(), newName, source, original, kind, this.isStaticFinal, this.singleUserData);
    }

    @Override
    public boolean hasSynthesizedParameterNames() {
        return false;
    }

    @Override
    @NotNull
    public JavaCallableMemberDescriptor enhance(@Nullable KotlinType enhancedReceiverType, @NotNull List<ValueParameterData> enhancedValueParametersData, @NotNull KotlinType enhancedReturnType, @Nullable Pair<CallableDescriptor.UserDataKey<?>, ?> additionalUserData) {
        if (enhancedValueParametersData == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(19);
        }
        if (enhancedReturnType == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(20);
        }
        PropertyDescriptor enhancedOriginal = this.getOriginal() == this ? null : this.getOriginal();
        JavaPropertyDescriptor enhanced = new JavaPropertyDescriptor(this.getContainingDeclaration(), this.getAnnotations(), this.getModality(), this.getVisibility(), this.isVar(), this.getName(), this.getSource(), enhancedOriginal, this.getKind(), this.isStaticFinal, additionalUserData);
        PropertyGetterDescriptorImpl newGetter = null;
        PropertyGetterDescriptorImpl getter = this.getGetter();
        if (getter != null) {
            newGetter = new PropertyGetterDescriptorImpl(enhanced, getter.getAnnotations(), getter.getModality(), getter.getVisibility(), getter.isDefault(), getter.isExternal(), getter.isInline(), this.getKind(), enhancedOriginal == null ? null : enhancedOriginal.getGetter(), getter.getSource());
            newGetter.setInitialSignatureDescriptor(getter.getInitialSignatureDescriptor());
            newGetter.initialize(enhancedReturnType);
        }
        PropertySetterDescriptorImpl newSetter = null;
        PropertySetterDescriptor setter = this.getSetter();
        if (setter != null) {
            newSetter = new PropertySetterDescriptorImpl(enhanced, setter.getAnnotations(), setter.getModality(), setter.getVisibility(), setter.isDefault(), setter.isExternal(), setter.isInline(), this.getKind(), enhancedOriginal == null ? null : enhancedOriginal.getSetter(), setter.getSource());
            newSetter.setInitialSignatureDescriptor(newSetter.getInitialSignatureDescriptor());
            newSetter.initialize(setter.getValueParameters().get(0));
        }
        enhanced.initialize(newGetter, newSetter, this.getBackingField(), this.getDelegateField());
        enhanced.setSetterProjectedOut(this.isSetterProjectedOut());
        if (this.compileTimeInitializer != null) {
            enhanced.setCompileTimeInitializer(this.compileTimeInitializer);
        }
        enhanced.setOverriddenDescriptors(this.getOverriddenDescriptors());
        ReceiverParameterDescriptor enhancedReceiver = enhancedReceiverType == null ? null : DescriptorFactory.createExtensionReceiverParameterForCallable(this, enhancedReceiverType, Annotations.Companion.getEMPTY());
        enhanced.setType(enhancedReturnType, this.getTypeParameters(), this.getDispatchReceiverParameter(), enhancedReceiver);
        JavaPropertyDescriptor javaPropertyDescriptor = enhanced;
        if (javaPropertyDescriptor == null) {
            JavaPropertyDescriptor.$$$reportNull$$$0(21);
        }
        return javaPropertyDescriptor;
    }

    @Override
    public boolean isConst() {
        KotlinType type2 = this.getType();
        return this.isStaticFinal && ConstUtil.canBeUsedForConstVal(type2) && (!TypeEnhancementKt.hasEnhancedNullability(type2) || KotlinBuiltIns.isString(type2));
    }

    @Override
    @Nullable
    public <V> V getUserData(CallableDescriptor.UserDataKey<V> key) {
        if (this.singleUserData != null && this.singleUserData.getFirst().equals(key)) {
            return (V)this.singleUserData.getSecond();
        }
        return null;
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
            case 21: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 21: {
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
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "annotations";
                break;
            }
            case 2: 
            case 9: {
                objectArray2 = objectArray3;
                objectArray3[0] = "modality";
                break;
            }
            case 3: 
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "visibility";
                break;
            }
            case 4: 
            case 11: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 5: 
            case 12: 
            case 18: {
                objectArray2 = objectArray3;
                objectArray3[0] = "source";
                break;
            }
            case 6: 
            case 16: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kind";
                break;
            }
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "newOwner";
                break;
            }
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "newModality";
                break;
            }
            case 15: {
                objectArray2 = objectArray3;
                objectArray3[0] = "newVisibility";
                break;
            }
            case 17: {
                objectArray2 = objectArray3;
                objectArray3[0] = "newName";
                break;
            }
            case 19: {
                objectArray2 = objectArray3;
                objectArray3[0] = "enhancedValueParametersData";
                break;
            }
            case 20: {
                objectArray2 = objectArray3;
                objectArray3[0] = "enhancedReturnType";
                break;
            }
            case 21: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/load/java/descriptors/JavaPropertyDescriptor";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/load/java/descriptors/JavaPropertyDescriptor";
                break;
            }
            case 21: {
                objectArray = objectArray2;
                objectArray2[1] = "enhance";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: {
                objectArray = objectArray;
                objectArray[2] = "create";
                break;
            }
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: {
                objectArray = objectArray;
                objectArray[2] = "createSubstitutedCopy";
                break;
            }
            case 19: 
            case 20: {
                objectArray = objectArray;
                objectArray[2] = "enhance";
                break;
            }
            case 21: {
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 21: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

