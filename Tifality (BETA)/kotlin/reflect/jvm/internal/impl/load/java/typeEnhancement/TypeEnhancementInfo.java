/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.Map;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers;
import org.jetbrains.annotations.NotNull;

public final class TypeEnhancementInfo {
    @NotNull
    private final Map<Integer, JavaTypeQualifiers> map;

    @NotNull
    public final Map<Integer, JavaTypeQualifiers> getMap() {
        return this.map;
    }

    public TypeEnhancementInfo(@NotNull Map<Integer, JavaTypeQualifiers> map2) {
        Intrinsics.checkNotNullParameter(map2, "map");
        this.map = map2;
    }
}

