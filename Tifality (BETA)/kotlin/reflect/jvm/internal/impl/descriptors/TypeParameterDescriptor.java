/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.model.TypeParameterMarker;
import org.jetbrains.annotations.NotNull;

public interface TypeParameterDescriptor
extends ClassifierDescriptor,
TypeParameterMarker {
    public boolean isReified();

    @NotNull
    public Variance getVariance();

    @NotNull
    public List<KotlinType> getUpperBounds();

    @Override
    @NotNull
    public TypeConstructor getTypeConstructor();

    @Override
    @NotNull
    public TypeParameterDescriptor getOriginal();

    public int getIndex();

    public boolean isCapturedFromOuterDeclaration();

    @NotNull
    public StorageManager getStorageManager();
}

