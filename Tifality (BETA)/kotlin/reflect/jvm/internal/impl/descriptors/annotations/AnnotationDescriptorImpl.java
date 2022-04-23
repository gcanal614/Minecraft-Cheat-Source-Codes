/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Map;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AnnotationDescriptorImpl
implements AnnotationDescriptor {
    private final KotlinType annotationType;
    private final Map<Name, ConstantValue<?>> valueArguments;
    private final SourceElement source;

    public AnnotationDescriptorImpl(@NotNull KotlinType annotationType, @NotNull Map<Name, ConstantValue<?>> valueArguments, @NotNull SourceElement source) {
        if (annotationType == null) {
            AnnotationDescriptorImpl.$$$reportNull$$$0(0);
        }
        if (valueArguments == null) {
            AnnotationDescriptorImpl.$$$reportNull$$$0(1);
        }
        if (source == null) {
            AnnotationDescriptorImpl.$$$reportNull$$$0(2);
        }
        this.annotationType = annotationType;
        this.valueArguments = valueArguments;
        this.source = source;
    }

    @Override
    @NotNull
    public KotlinType getType() {
        KotlinType kotlinType = this.annotationType;
        if (kotlinType == null) {
            AnnotationDescriptorImpl.$$$reportNull$$$0(3);
        }
        return kotlinType;
    }

    @Override
    @Nullable
    public FqName getFqName() {
        return AnnotationDescriptor.DefaultImpls.getFqName(this);
    }

    @Override
    @NotNull
    public Map<Name, ConstantValue<?>> getAllValueArguments() {
        Map<Name, ConstantValue<?>> map2 = this.valueArguments;
        if (map2 == null) {
            AnnotationDescriptorImpl.$$$reportNull$$$0(4);
        }
        return map2;
    }

    @Override
    @NotNull
    public SourceElement getSource() {
        SourceElement sourceElement = this.source;
        if (sourceElement == null) {
            AnnotationDescriptorImpl.$$$reportNull$$$0(5);
        }
        return sourceElement;
    }

    public String toString() {
        return DescriptorRenderer.FQ_NAMES_IN_TYPES.renderAnnotation(this, null);
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
            case 3: 
            case 4: 
            case 5: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 3: 
            case 4: 
            case 5: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "annotationType";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "valueArguments";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "source";
                break;
            }
            case 3: 
            case 4: 
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/annotations/AnnotationDescriptorImpl";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/annotations/AnnotationDescriptorImpl";
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = "getType";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "getAllValueArguments";
                break;
            }
            case 5: {
                objectArray = objectArray2;
                objectArray2[1] = "getSource";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 3: 
            case 4: 
            case 5: {
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 3: 
            case 4: 
            case 5: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

