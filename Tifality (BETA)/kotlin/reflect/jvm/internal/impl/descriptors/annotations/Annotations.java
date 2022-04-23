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
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationsImpl;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Annotations
extends Iterable<AnnotationDescriptor>,
KMappedMarker {
    public static final Companion Companion = kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations$Companion.$$INSTANCE;

    public boolean isEmpty();

    @Nullable
    public AnnotationDescriptor findAnnotation(@NotNull FqName var1);

    public boolean hasAnnotation(@NotNull FqName var1);

    public static final class DefaultImpls {
        @Nullable
        public static AnnotationDescriptor findAnnotation(@NotNull Annotations $this, @NotNull FqName fqName2) {
            Object v0;
            block1: {
                Intrinsics.checkNotNullParameter(fqName2, "fqName");
                Iterable $this$firstOrNull$iv = $this;
                boolean $i$f$firstOrNull = false;
                for (Object element$iv : $this$firstOrNull$iv) {
                    AnnotationDescriptor it = (AnnotationDescriptor)element$iv;
                    boolean bl = false;
                    if (!Intrinsics.areEqual(it.getFqName(), fqName2)) continue;
                    v0 = element$iv;
                    break block1;
                }
                v0 = null;
            }
            return v0;
        }

        public static boolean hasAnnotation(@NotNull Annotations $this, @NotNull FqName fqName2) {
            Intrinsics.checkNotNullParameter(fqName2, "fqName");
            return $this.findAnnotation(fqName2) != null;
        }
    }

    public static final class Companion {
        @NotNull
        private static final Annotations EMPTY;
        static final /* synthetic */ Companion $$INSTANCE;

        @NotNull
        public final Annotations getEMPTY() {
            return EMPTY;
        }

        @NotNull
        public final Annotations create(@NotNull List<? extends AnnotationDescriptor> annotations2) {
            Intrinsics.checkNotNullParameter(annotations2, "annotations");
            return annotations2.isEmpty() ? EMPTY : (Annotations)new AnnotationsImpl(annotations2);
        }

        private Companion() {
        }

        static {
            Companion companion;
            $$INSTANCE = companion = new Companion();
            EMPTY = new Annotations(){

                public boolean isEmpty() {
                    return true;
                }

                @Nullable
                public Void findAnnotation(@NotNull FqName fqName2) {
                    Intrinsics.checkNotNullParameter(fqName2, "fqName");
                    return null;
                }

                @NotNull
                public Iterator<AnnotationDescriptor> iterator() {
                    return CollectionsKt.emptyList().iterator();
                }

                @NotNull
                public String toString() {
                    return "EMPTY";
                }

                public boolean hasAnnotation(@NotNull FqName fqName2) {
                    Intrinsics.checkNotNullParameter(fqName2, "fqName");
                    return DefaultImpls.hasAnnotation(this, fqName2);
                }
            };
        }
    }
}

