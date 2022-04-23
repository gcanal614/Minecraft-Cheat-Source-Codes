/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.Iterator;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.EnhancedTypeAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class EnhancedTypeAnnotations
implements Annotations {
    private final FqName fqNameToMatch;

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    @Nullable
    public EnhancedTypeAnnotationDescriptor findAnnotation(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        FqName fqName3 = fqName2;
        return Intrinsics.areEqual(fqName3, this.fqNameToMatch) ? EnhancedTypeAnnotationDescriptor.INSTANCE : null;
    }

    @Override
    @NotNull
    public Iterator<AnnotationDescriptor> iterator() {
        return CollectionsKt.emptyList().iterator();
    }

    public EnhancedTypeAnnotations(@NotNull FqName fqNameToMatch) {
        Intrinsics.checkNotNullParameter(fqNameToMatch, "fqNameToMatch");
        this.fqNameToMatch = fqNameToMatch;
    }

    @Override
    public boolean hasAnnotation(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return Annotations.DefaultImpls.hasAnnotation(this, fqName2);
    }
}

