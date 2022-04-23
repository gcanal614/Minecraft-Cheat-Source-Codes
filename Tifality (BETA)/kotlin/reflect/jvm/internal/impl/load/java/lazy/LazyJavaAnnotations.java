/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.Iterator;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaAnnotationMapper;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationOwner;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LazyJavaAnnotations
implements Annotations {
    private final MemoizedFunctionToNullable<JavaAnnotation, AnnotationDescriptor> annotationDescriptors;
    private final LazyJavaResolverContext c;
    private final JavaAnnotationOwner annotationOwner;

    @Override
    @Nullable
    public AnnotationDescriptor findAnnotation(@NotNull FqName fqName2) {
        Object object;
        block3: {
            block2: {
                Intrinsics.checkNotNullParameter(fqName2, "fqName");
                object = this.annotationOwner.findAnnotation(fqName2);
                if (object == null) break block2;
                JavaAnnotation javaAnnotation = object;
                Function1 function1 = this.annotationDescriptors;
                boolean bl = false;
                boolean bl2 = false;
                object = (AnnotationDescriptor)function1.invoke(javaAnnotation);
                if (object != null) break block3;
            }
            object = JavaAnnotationMapper.INSTANCE.findMappedJavaAnnotation(fqName2, this.annotationOwner, this.c);
        }
        return object;
    }

    @Override
    @NotNull
    public Iterator<AnnotationDescriptor> iterator() {
        Sequence sequence = SequencesKt.map(CollectionsKt.asSequence((Iterable)this.annotationOwner.getAnnotations()), (Function1)this.annotationDescriptors);
        FqName fqName2 = KotlinBuiltIns.FQ_NAMES.deprecated;
        Intrinsics.checkNotNullExpressionValue(fqName2, "KotlinBuiltIns.FQ_NAMES.deprecated");
        return SequencesKt.filterNotNull(SequencesKt.plus(sequence, JavaAnnotationMapper.INSTANCE.findMappedJavaAnnotation(fqName2, this.annotationOwner, this.c))).iterator();
    }

    @Override
    public boolean isEmpty() {
        return this.annotationOwner.getAnnotations().isEmpty() && !this.annotationOwner.isDeprecatedInJavaDoc();
    }

    public LazyJavaAnnotations(@NotNull LazyJavaResolverContext c, @NotNull JavaAnnotationOwner annotationOwner) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(annotationOwner, "annotationOwner");
        this.c = c;
        this.annotationOwner = annotationOwner;
        this.annotationDescriptors = this.c.getComponents().getStorageManager().createMemoizedFunctionWithNullableValues((Function1)new Function1<JavaAnnotation, AnnotationDescriptor>(this){
            final /* synthetic */ LazyJavaAnnotations this$0;

            @Nullable
            public final AnnotationDescriptor invoke(@NotNull JavaAnnotation annotation) {
                Intrinsics.checkNotNullParameter(annotation, "annotation");
                return JavaAnnotationMapper.INSTANCE.mapOrResolveJavaAnnotation(annotation, LazyJavaAnnotations.access$getC$p(this.this$0));
            }
            {
                this.this$0 = lazyJavaAnnotations;
                super(1);
            }
        });
    }

    @Override
    public boolean hasAnnotation(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return Annotations.DefaultImpls.hasAnnotation(this, fqName2);
    }

    public static final /* synthetic */ LazyJavaResolverContext access$getC$p(LazyJavaAnnotations $this) {
        return $this.c;
    }
}

