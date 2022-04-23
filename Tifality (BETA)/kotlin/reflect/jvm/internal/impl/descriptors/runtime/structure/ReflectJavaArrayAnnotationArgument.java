/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectJavaArrayAnnotationArgument
extends ReflectJavaAnnotationArgument
implements JavaArrayAnnotationArgument {
    private final Object[] values;

    /*
     * WARNING - void declaration
     */
    @NotNull
    public List<ReflectJavaAnnotationArgument> getElements() {
        void $this$mapTo$iv$iv;
        Object[] $this$map$iv = this.values;
        boolean $i$f$map = false;
        Object[] objectArray = $this$map$iv;
        Collection destination$iv$iv = new ArrayList($this$map$iv.length);
        boolean $i$f$mapTo = false;
        void var6_6 = $this$mapTo$iv$iv;
        int n = ((void)var6_6).length;
        for (int i = 0; i < n; ++i) {
            void it;
            void item$iv$iv;
            void var10_10 = item$iv$iv = var6_6[i];
            Collection collection = destination$iv$iv;
            boolean bl = false;
            void v0 = it;
            Intrinsics.checkNotNull(v0);
            ReflectJavaAnnotationArgument reflectJavaAnnotationArgument = ReflectJavaAnnotationArgument.Factory.create(v0, null);
            collection.add(reflectJavaAnnotationArgument);
        }
        return (List)destination$iv$iv;
    }

    public ReflectJavaArrayAnnotationArgument(@Nullable Name name, @NotNull Object[] values2) {
        Intrinsics.checkNotNullParameter(values2, "values");
        super(name);
        this.values = values2;
    }
}

