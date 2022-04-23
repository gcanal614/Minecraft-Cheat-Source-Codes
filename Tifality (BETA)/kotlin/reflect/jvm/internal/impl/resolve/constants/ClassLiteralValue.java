/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ClassLiteralValue {
    @NotNull
    private final ClassId classId;
    private final int arrayNestedness;

    @NotNull
    public String toString() {
        int it;
        boolean bl = false;
        boolean bl2 = false;
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl3 = false;
        boolean bl4 = false;
        StringBuilder $this$buildString = stringBuilder;
        boolean bl5 = false;
        int n = this.arrayNestedness;
        boolean bl6 = false;
        int n2 = 0;
        n2 = 0;
        int n3 = n;
        while (n2 < n3) {
            it = n2++;
            boolean bl7 = false;
            $this$buildString.append("kotlin/Array<");
        }
        $this$buildString.append(this.classId);
        n = this.arrayNestedness;
        bl6 = false;
        n2 = 0;
        n2 = 0;
        n3 = n;
        while (n2 < n3) {
            it = n2++;
            boolean bl8 = false;
            $this$buildString.append(">");
        }
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    @NotNull
    public final ClassId getClassId() {
        return this.classId;
    }

    public final int getArrayNestedness() {
        return this.arrayNestedness;
    }

    public ClassLiteralValue(@NotNull ClassId classId, int arrayNestedness) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        this.classId = classId;
        this.arrayNestedness = arrayNestedness;
    }

    @NotNull
    public final ClassId component1() {
        return this.classId;
    }

    public final int component2() {
        return this.arrayNestedness;
    }

    public int hashCode() {
        ClassId classId = this.classId;
        return (classId != null ? ((Object)classId).hashCode() : 0) * 31 + this.arrayNestedness;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof ClassLiteralValue)) break block3;
                ClassLiteralValue classLiteralValue = (ClassLiteralValue)object;
                if (!Intrinsics.areEqual(this.classId, classLiteralValue.classId) || this.arrayNestedness != classLiteralValue.arrayNestedness) break block3;
            }
            return true;
        }
        return false;
    }
}

