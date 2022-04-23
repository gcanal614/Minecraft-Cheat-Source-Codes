/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceFile;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.IncompatibleVersionErrorData;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class KotlinJvmBinarySourceElement
implements DeserializedContainerSource {
    @NotNull
    private final KotlinJvmBinaryClass binaryClass;
    @Nullable
    private final IncompatibleVersionErrorData<JvmMetadataVersion> incompatibility;
    private final boolean isPreReleaseInvisible;
    private final boolean isInvisibleIrDependency;

    @Override
    @NotNull
    public String getPresentableString() {
        return "Class '" + this.binaryClass.getClassId().asSingleFqName().asString() + '\'';
    }

    @Override
    @NotNull
    public SourceFile getContainingFile() {
        SourceFile sourceFile = SourceFile.NO_SOURCE_FILE;
        Intrinsics.checkNotNullExpressionValue(sourceFile, "SourceFile.NO_SOURCE_FILE");
        return sourceFile;
    }

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName() + ": " + this.binaryClass;
    }

    @NotNull
    public final KotlinJvmBinaryClass getBinaryClass() {
        return this.binaryClass;
    }

    public KotlinJvmBinarySourceElement(@NotNull KotlinJvmBinaryClass binaryClass, @Nullable IncompatibleVersionErrorData<JvmMetadataVersion> incompatibility, boolean isPreReleaseInvisible, boolean isInvisibleIrDependency) {
        Intrinsics.checkNotNullParameter(binaryClass, "binaryClass");
        this.binaryClass = binaryClass;
        this.incompatibility = incompatibility;
        this.isPreReleaseInvisible = isPreReleaseInvisible;
        this.isInvisibleIrDependency = isInvisibleIrDependency;
    }
}

