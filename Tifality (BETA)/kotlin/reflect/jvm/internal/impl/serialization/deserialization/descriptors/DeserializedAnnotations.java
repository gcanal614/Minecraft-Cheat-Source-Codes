/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DeserializedAnnotations
implements Annotations {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private final NotNullLazyValue annotations$delegate;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DeserializedAnnotations.class), "annotations", "getAnnotations()Ljava/util/List;"))};
    }

    private final List<AnnotationDescriptor> getAnnotations() {
        return (List)StorageKt.getValue(this.annotations$delegate, (Object)this, $$delegatedProperties[0]);
    }

    @Override
    public boolean isEmpty() {
        return this.getAnnotations().isEmpty();
    }

    @Override
    @NotNull
    public Iterator<AnnotationDescriptor> iterator() {
        return this.getAnnotations().iterator();
    }

    public DeserializedAnnotations(@NotNull StorageManager storageManager, @NotNull Function0<? extends List<? extends AnnotationDescriptor>> compute) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(compute, "compute");
        this.annotations$delegate = storageManager.createLazyValue(compute);
    }

    @Override
    @Nullable
    public AnnotationDescriptor findAnnotation(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return Annotations.DefaultImpls.findAnnotation(this, fqName2);
    }

    @Override
    public boolean hasAnnotation(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return Annotations.DefaultImpls.hasAnnotation(this, fqName2);
    }
}

