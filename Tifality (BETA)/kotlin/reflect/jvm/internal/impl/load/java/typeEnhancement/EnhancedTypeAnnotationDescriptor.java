/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.Map;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class EnhancedTypeAnnotationDescriptor
implements AnnotationDescriptor {
    public static final EnhancedTypeAnnotationDescriptor INSTANCE;

    private final Void throwError() {
        String string = "No methods should be called on this descriptor. Only its presence matters";
        boolean bl = false;
        throw (Throwable)new IllegalStateException(string.toString());
    }

    @Override
    @NotNull
    public KotlinType getType() {
        Void void_ = this.throwError();
        throw null;
    }

    @Override
    @NotNull
    public Map<Name, ConstantValue<?>> getAllValueArguments() {
        Void void_ = this.throwError();
        throw null;
    }

    @Override
    @NotNull
    public SourceElement getSource() {
        Void void_ = this.throwError();
        throw null;
    }

    @NotNull
    public String toString() {
        return "[EnhancedType]";
    }

    private EnhancedTypeAnnotationDescriptor() {
    }

    static {
        EnhancedTypeAnnotationDescriptor enhancedTypeAnnotationDescriptor;
        INSTANCE = enhancedTypeAnnotationDescriptor = new EnhancedTypeAnnotationDescriptor();
    }

    @Override
    @Nullable
    public FqName getFqName() {
        return AnnotationDescriptor.DefaultImpls.getFqName(this);
    }
}

