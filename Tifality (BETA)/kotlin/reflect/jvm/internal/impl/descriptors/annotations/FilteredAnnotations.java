/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FilteredAnnotations
implements Annotations {
    private final Annotations delegate;
    private final boolean isDefinitelyNewInference;
    private final Function1<FqName, Boolean> fqNameFilter;

    @Override
    public boolean hasAnnotation(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return this.fqNameFilter.invoke(fqName2) != false ? this.delegate.hasAnnotation(fqName2) : false;
    }

    @Override
    @Nullable
    public AnnotationDescriptor findAnnotation(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return this.fqNameFilter.invoke(fqName2) != false ? this.delegate.findAnnotation(fqName2) : null;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Iterator<AnnotationDescriptor> iterator() {
        void $this$filterTo$iv$iv;
        void $this$filter$iv;
        Iterable iterable = this.delegate;
        FilteredAnnotations filteredAnnotations = this;
        boolean $i$f$filter = false;
        void var4_4 = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            AnnotationDescriptor p1 = (AnnotationDescriptor)element$iv$iv;
            boolean bl = false;
            if (!filteredAnnotations.shouldBeReturned(p1)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return ((List)destination$iv$iv).iterator();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public boolean isEmpty() {
        boolean condition;
        block3: {
            boolean bl;
            void $this$any$iv;
            Iterable iterable = this.delegate;
            FilteredAnnotations filteredAnnotations = this;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    AnnotationDescriptor p1 = (AnnotationDescriptor)element$iv;
                    boolean bl2 = false;
                    if (!filteredAnnotations.shouldBeReturned(p1)) continue;
                    bl = true;
                    break block3;
                }
                bl = condition = false;
            }
        }
        return this.isDefinitelyNewInference ? !condition : condition;
    }

    private final boolean shouldBeReturned(AnnotationDescriptor annotation) {
        FqName fqName2 = annotation.getFqName();
        boolean bl = false;
        boolean bl2 = false;
        FqName fqName3 = fqName2;
        boolean bl3 = false;
        return fqName3 != null && this.fqNameFilter.invoke(fqName3).booleanValue();
    }

    public FilteredAnnotations(@NotNull Annotations delegate, boolean isDefinitelyNewInference, @NotNull Function1<? super FqName, Boolean> fqNameFilter) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(fqNameFilter, "fqNameFilter");
        this.delegate = delegate;
        this.isDefinitelyNewInference = isDefinitelyNewInference;
        this.fqNameFilter = fqNameFilter;
    }

    public FilteredAnnotations(@NotNull Annotations delegate, @NotNull Function1<? super FqName, Boolean> fqNameFilter) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(fqNameFilter, "fqNameFilter");
        this(delegate, false, fqNameFilter);
    }
}

