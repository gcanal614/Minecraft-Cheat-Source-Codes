/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.full;

import java.lang.annotation.Annotation;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KAnnotatedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u0000\u0016\n\u0002\b\u0002\n\u0002\u0010\u001b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u001a \u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u0003H\u0087\b\u00a2\u0006\u0002\u0010\u0004\u001a\u0019\u0010\u0005\u001a\u00020\u0006\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u0003H\u0087\b\u00a8\u0006\u0007"}, d2={"findAnnotation", "T", "", "Lkotlin/reflect/KAnnotatedElement;", "(Lkotlin/reflect/KAnnotatedElement;)Ljava/lang/annotation/Annotation;", "hasAnnotation", "", "kotlin-reflection"})
@JvmName(name="KAnnotatedElements")
public final class KAnnotatedElements {
    @SinceKotlin(version="1.1")
    @Nullable
    public static final /* synthetic */ <T extends Annotation> T findAnnotation(@NotNull KAnnotatedElement $this$findAnnotation) {
        Object v0;
        block1: {
            int $i$f$findAnnotation = 0;
            Intrinsics.checkNotNullParameter($this$findAnnotation, "$this$findAnnotation");
            Iterable $this$firstOrNull$iv = $this$findAnnotation.getAnnotations();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                Annotation it = (Annotation)element$iv;
                boolean bl = false;
                Intrinsics.reifiedOperationMarker(3, "T");
                if (!(it instanceof Annotation)) continue;
                v0 = element$iv;
                break block1;
            }
            v0 = null;
        }
        Intrinsics.reifiedOperationMarker(1, "T?");
        return (T)((Annotation)v0);
    }

    @SinceKotlin(version="1.4")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static final /* synthetic */ <T extends Annotation> boolean hasAnnotation(@NotNull KAnnotatedElement $this$hasAnnotation) {
        Object v0;
        block1: {
            int $i$f$hasAnnotation = 0;
            Intrinsics.checkNotNullParameter($this$hasAnnotation, "$this$hasAnnotation");
            KAnnotatedElement $this$findAnnotation$iv = $this$hasAnnotation;
            boolean $i$f$findAnnotation = false;
            Iterable $this$firstOrNull$iv$iv = $this$findAnnotation$iv.getAnnotations();
            boolean $i$f$firstOrNull = false;
            for (Object element$iv$iv : $this$firstOrNull$iv$iv) {
                Annotation it$iv = (Annotation)element$iv$iv;
                boolean bl = false;
                Intrinsics.reifiedOperationMarker(3, "T");
                if (!(it$iv instanceof Annotation)) continue;
                v0 = element$iv$iv;
                break block1;
            }
            v0 = null;
        }
        Intrinsics.reifiedOperationMarker(1, "T?");
        return (Annotation)v0 != null;
    }
}

