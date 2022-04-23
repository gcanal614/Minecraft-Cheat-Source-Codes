/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class EnhancementResult<T> {
    private final T result;
    @Nullable
    private final Annotations enhancementAnnotations;

    public EnhancementResult(T result2, @Nullable Annotations enhancementAnnotations) {
        this.result = result2;
        this.enhancementAnnotations = enhancementAnnotations;
    }

    public final T component1() {
        return this.result;
    }

    @Nullable
    public final Annotations component2() {
        return this.enhancementAnnotations;
    }

    @NotNull
    public String toString() {
        return "EnhancementResult(result=" + this.result + ", enhancementAnnotations=" + this.enhancementAnnotations + ")";
    }

    public int hashCode() {
        T t = this.result;
        Annotations annotations2 = this.enhancementAnnotations;
        return (t != null ? t.hashCode() : 0) * 31 + (annotations2 != null ? annotations2.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof EnhancementResult)) break block3;
                EnhancementResult enhancementResult2 = (EnhancementResult)object;
                if (!Intrinsics.areEqual(this.result, enhancementResult2.result) || !Intrinsics.areEqual(this.enhancementAnnotations, enhancementResult2.enhancementAnnotations)) break block3;
            }
            return true;
        }
        return false;
    }
}

