/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ClassData {
    @NotNull
    private final NameResolver nameResolver;
    @NotNull
    private final ProtoBuf.Class classProto;
    @NotNull
    private final BinaryVersion metadataVersion;
    @NotNull
    private final SourceElement sourceElement;

    public ClassData(@NotNull NameResolver nameResolver, @NotNull ProtoBuf.Class classProto, @NotNull BinaryVersion metadataVersion, @NotNull SourceElement sourceElement) {
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(classProto, "classProto");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(sourceElement, "sourceElement");
        this.nameResolver = nameResolver;
        this.classProto = classProto;
        this.metadataVersion = metadataVersion;
        this.sourceElement = sourceElement;
    }

    @NotNull
    public final NameResolver component1() {
        return this.nameResolver;
    }

    @NotNull
    public final ProtoBuf.Class component2() {
        return this.classProto;
    }

    @NotNull
    public final BinaryVersion component3() {
        return this.metadataVersion;
    }

    @NotNull
    public final SourceElement component4() {
        return this.sourceElement;
    }

    @NotNull
    public String toString() {
        return "ClassData(nameResolver=" + this.nameResolver + ", classProto=" + this.classProto + ", metadataVersion=" + this.metadataVersion + ", sourceElement=" + this.sourceElement + ")";
    }

    public int hashCode() {
        NameResolver nameResolver = this.nameResolver;
        ProtoBuf.Class clazz = this.classProto;
        BinaryVersion binaryVersion = this.metadataVersion;
        SourceElement sourceElement = this.sourceElement;
        return (((nameResolver != null ? nameResolver.hashCode() : 0) * 31 + (clazz != null ? clazz.hashCode() : 0)) * 31 + (binaryVersion != null ? ((Object)binaryVersion).hashCode() : 0)) * 31 + (sourceElement != null ? sourceElement.hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof ClassData)) break block3;
                ClassData classData = (ClassData)object;
                if (!Intrinsics.areEqual(this.nameResolver, classData.nameResolver) || !Intrinsics.areEqual(this.classProto, classData.classProto) || !Intrinsics.areEqual(this.metadataVersion, classData.metadataVersion) || !Intrinsics.areEqual(this.sourceElement, classData.sourceElement)) break block3;
            }
            return true;
        }
        return false;
    }
}

