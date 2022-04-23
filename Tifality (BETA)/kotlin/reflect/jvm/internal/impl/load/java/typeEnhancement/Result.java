/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class Result {
    @NotNull
    private final KotlinType type;
    private final int subtreeSize;
    private final boolean wereChanges;

    @Nullable
    public final KotlinType getTypeIfChanged() {
        KotlinType kotlinType = this.getType();
        boolean bl = false;
        boolean bl2 = false;
        KotlinType it = kotlinType;
        boolean bl3 = false;
        return this.wereChanges ? kotlinType : null;
    }

    @NotNull
    public KotlinType getType() {
        return this.type;
    }

    public final int getSubtreeSize() {
        return this.subtreeSize;
    }

    public final boolean getWereChanges() {
        return this.wereChanges;
    }

    public Result(@NotNull KotlinType type2, int subtreeSize, boolean wereChanges) {
        Intrinsics.checkNotNullParameter(type2, "type");
        this.type = type2;
        this.subtreeSize = subtreeSize;
        this.wereChanges = wereChanges;
    }
}

