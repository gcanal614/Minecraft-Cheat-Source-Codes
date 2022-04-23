/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AnnotationsImpl
implements Annotations {
    private final List<AnnotationDescriptor> annotations;

    @Override
    public boolean isEmpty() {
        return this.annotations.isEmpty();
    }

    @Override
    @NotNull
    public Iterator<AnnotationDescriptor> iterator() {
        return this.annotations.iterator();
    }

    @NotNull
    public String toString() {
        return this.annotations.toString();
    }

    public AnnotationsImpl(@NotNull List<? extends AnnotationDescriptor> annotations2) {
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        this.annotations = annotations2;
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

