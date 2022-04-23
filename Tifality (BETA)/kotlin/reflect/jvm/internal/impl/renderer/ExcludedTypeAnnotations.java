/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;

public final class ExcludedTypeAnnotations {
    @NotNull
    private static final Set<FqName> internalAnnotationsForResolve;
    public static final ExcludedTypeAnnotations INSTANCE;

    @NotNull
    public final Set<FqName> getInternalAnnotationsForResolve() {
        return internalAnnotationsForResolve;
    }

    private ExcludedTypeAnnotations() {
    }

    static {
        ExcludedTypeAnnotations excludedTypeAnnotations;
        INSTANCE = excludedTypeAnnotations = new ExcludedTypeAnnotations();
        internalAnnotationsForResolve = SetsKt.setOf(new FqName("kotlin.internal.NoInfer"), new FqName("kotlin.internal.Exact"));
    }
}

