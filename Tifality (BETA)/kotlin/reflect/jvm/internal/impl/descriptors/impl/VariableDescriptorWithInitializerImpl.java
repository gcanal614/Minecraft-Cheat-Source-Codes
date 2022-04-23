/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.VariableDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.storage.NullableLazyValue;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class VariableDescriptorWithInitializerImpl
extends VariableDescriptorImpl {
    private final boolean isVar;
    protected NullableLazyValue<ConstantValue<?>> compileTimeInitializer;

    public VariableDescriptorWithInitializerImpl(@NotNull DeclarationDescriptor containingDeclaration, @NotNull Annotations annotations2, @NotNull Name name, @Nullable KotlinType outType, boolean isVar, @NotNull SourceElement source) {
        if (containingDeclaration == null) {
            VariableDescriptorWithInitializerImpl.$$$reportNull$$$0(0);
        }
        if (annotations2 == null) {
            VariableDescriptorWithInitializerImpl.$$$reportNull$$$0(1);
        }
        if (name == null) {
            VariableDescriptorWithInitializerImpl.$$$reportNull$$$0(2);
        }
        if (source == null) {
            VariableDescriptorWithInitializerImpl.$$$reportNull$$$0(3);
        }
        super(containingDeclaration, annotations2, name, outType, source);
        this.isVar = isVar;
    }

    @Override
    public boolean isVar() {
        return this.isVar;
    }

    @Override
    @Nullable
    public ConstantValue<?> getCompileTimeInitializer() {
        if (this.compileTimeInitializer != null) {
            return (ConstantValue)this.compileTimeInitializer.invoke();
        }
        return null;
    }

    public void setCompileTimeInitializer(@NotNull NullableLazyValue<ConstantValue<?>> compileTimeInitializer) {
        if (compileTimeInitializer == null) {
            VariableDescriptorWithInitializerImpl.$$$reportNull$$$0(4);
        }
        assert (!this.isVar()) : "Constant value for variable initializer should be recorded only for final variables: " + this.getName();
        this.compileTimeInitializer = compileTimeInitializer;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[3];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "containingDeclaration";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "annotations";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "source";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "compileTimeInitializer";
                break;
            }
        }
        objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/VariableDescriptorWithInitializerImpl";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = "<init>";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[2] = "setCompileTimeInitializer";
                break;
            }
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }
}

