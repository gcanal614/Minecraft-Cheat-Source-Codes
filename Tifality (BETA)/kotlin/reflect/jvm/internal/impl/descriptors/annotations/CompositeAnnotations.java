/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.CompositeAnnotations;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CompositeAnnotations
implements Annotations {
    private final List<Annotations> delegates;

    @Override
    public boolean isEmpty() {
        boolean bl;
        block3: {
            Iterable $this$all$iv = this.delegates;
            boolean $i$f$all = false;
            if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                bl = true;
            } else {
                for (Object element$iv : $this$all$iv) {
                    Annotations it = (Annotations)element$iv;
                    boolean bl2 = false;
                    if (it.isEmpty()) continue;
                    bl = false;
                    break block3;
                }
                bl = true;
            }
        }
        return bl;
    }

    @Override
    public boolean hasAnnotation(@NotNull FqName fqName2) {
        boolean bl;
        block1: {
            Intrinsics.checkNotNullParameter(fqName2, "fqName");
            Sequence $this$any$iv = CollectionsKt.asSequence((Iterable)this.delegates);
            boolean $i$f$any = false;
            Iterator iterator2 = $this$any$iv.iterator();
            while (iterator2.hasNext()) {
                Object element$iv = iterator2.next();
                Annotations it = (Annotations)element$iv;
                boolean bl2 = false;
                if (!it.hasAnnotation(fqName2)) continue;
                bl = true;
                break block1;
            }
            bl = false;
        }
        return bl;
    }

    @Override
    @Nullable
    public AnnotationDescriptor findAnnotation(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return (AnnotationDescriptor)SequencesKt.firstOrNull(SequencesKt.mapNotNull(CollectionsKt.asSequence((Iterable)this.delegates), (Function1)new Function1<Annotations, AnnotationDescriptor>(fqName2){
            final /* synthetic */ FqName $fqName;

            @Nullable
            public final AnnotationDescriptor invoke(@NotNull Annotations it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.findAnnotation(this.$fqName);
            }
            {
                this.$fqName = fqName2;
                super(1);
            }
        }));
    }

    @Override
    @NotNull
    public Iterator<AnnotationDescriptor> iterator() {
        return SequencesKt.flatMap(CollectionsKt.asSequence((Iterable)this.delegates), iterator.1.INSTANCE).iterator();
    }

    public CompositeAnnotations(@NotNull List<? extends Annotations> delegates) {
        Intrinsics.checkNotNullParameter(delegates, "delegates");
        this.delegates = delegates;
    }

    public CompositeAnnotations(Annotations ... delegates) {
        Intrinsics.checkNotNullParameter(delegates, "delegates");
        this(ArraysKt.toList(delegates));
    }
}

