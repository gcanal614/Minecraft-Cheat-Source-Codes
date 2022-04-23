/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class IncompatibleVersionErrorData<T extends BinaryVersion> {
    @NotNull
    private final T actualVersion;
    @NotNull
    private final T expectedVersion;
    @NotNull
    private final String filePath;
    @NotNull
    private final ClassId classId;

    public IncompatibleVersionErrorData(@NotNull T actualVersion, @NotNull T expectedVersion, @NotNull String filePath, @NotNull ClassId classId) {
        Intrinsics.checkNotNullParameter(actualVersion, "actualVersion");
        Intrinsics.checkNotNullParameter(expectedVersion, "expectedVersion");
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(classId, "classId");
        this.actualVersion = actualVersion;
        this.expectedVersion = expectedVersion;
        this.filePath = filePath;
        this.classId = classId;
    }

    @NotNull
    public String toString() {
        return "IncompatibleVersionErrorData(actualVersion=" + this.actualVersion + ", expectedVersion=" + this.expectedVersion + ", filePath=" + this.filePath + ", classId=" + this.classId + ")";
    }

    public int hashCode() {
        T t = this.actualVersion;
        T t2 = this.expectedVersion;
        String string = this.filePath;
        ClassId classId = this.classId;
        return (((t != null ? t.hashCode() : 0) * 31 + (t2 != null ? t2.hashCode() : 0)) * 31 + (string != null ? string.hashCode() : 0)) * 31 + (classId != null ? ((Object)classId).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof IncompatibleVersionErrorData)) break block3;
                IncompatibleVersionErrorData incompatibleVersionErrorData = (IncompatibleVersionErrorData)object;
                if (!Intrinsics.areEqual(this.actualVersion, incompatibleVersionErrorData.actualVersion) || !Intrinsics.areEqual(this.expectedVersion, incompatibleVersionErrorData.expectedVersion) || !Intrinsics.areEqual(this.filePath, incompatibleVersionErrorData.filePath) || !Intrinsics.areEqual(this.classId, incompatibleVersionErrorData.classId)) break block3;
            }
            return true;
        }
        return false;
    }
}

