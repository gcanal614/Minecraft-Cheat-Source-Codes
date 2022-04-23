/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;

final class CapturedTypeParameterDescriptor
implements TypeParameterDescriptor {
    private final TypeParameterDescriptor originalDescriptor;
    private final DeclarationDescriptor declarationDescriptor;
    private final int declaredTypeParametersCount;

    @Override
    public boolean isCapturedFromOuterDeclaration() {
        return true;
    }

    @Override
    @NotNull
    public TypeParameterDescriptor getOriginal() {
        TypeParameterDescriptor typeParameterDescriptor = this.originalDescriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "originalDescriptor.original");
        return typeParameterDescriptor;
    }

    @Override
    @NotNull
    public DeclarationDescriptor getContainingDeclaration() {
        return this.declarationDescriptor;
    }

    @Override
    public int getIndex() {
        return this.declaredTypeParametersCount + this.originalDescriptor.getIndex();
    }

    @NotNull
    public String toString() {
        return this.originalDescriptor + "[inner-copy]";
    }

    public CapturedTypeParameterDescriptor(@NotNull TypeParameterDescriptor originalDescriptor, @NotNull DeclarationDescriptor declarationDescriptor, int declaredTypeParametersCount) {
        Intrinsics.checkNotNullParameter(originalDescriptor, "originalDescriptor");
        Intrinsics.checkNotNullParameter(declarationDescriptor, "declarationDescriptor");
        this.originalDescriptor = originalDescriptor;
        this.declarationDescriptor = declarationDescriptor;
        this.declaredTypeParametersCount = declaredTypeParametersCount;
    }

    @Override
    @NotNull
    public Annotations getAnnotations() {
        return this.originalDescriptor.getAnnotations();
    }

    @Override
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor2, D data2) {
        return this.originalDescriptor.accept(visitor2, data2);
    }

    @Override
    @NotNull
    public SimpleType getDefaultType() {
        return this.originalDescriptor.getDefaultType();
    }

    @Override
    @NotNull
    public Name getName() {
        return this.originalDescriptor.getName();
    }

    @Override
    @NotNull
    public SourceElement getSource() {
        return this.originalDescriptor.getSource();
    }

    @Override
    @NotNull
    public StorageManager getStorageManager() {
        return this.originalDescriptor.getStorageManager();
    }

    @Override
    @NotNull
    public TypeConstructor getTypeConstructor() {
        return this.originalDescriptor.getTypeConstructor();
    }

    @Override
    @NotNull
    public List<KotlinType> getUpperBounds() {
        return this.originalDescriptor.getUpperBounds();
    }

    @Override
    @NotNull
    public Variance getVariance() {
        return this.originalDescriptor.getVariance();
    }

    @Override
    public boolean isReified() {
        return this.originalDescriptor.isReified();
    }
}

