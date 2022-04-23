/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionSpecificBehaviorKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DeserializationContext {
    @NotNull
    private final TypeDeserializer typeDeserializer;
    @NotNull
    private final MemberDeserializer memberDeserializer;
    @NotNull
    private final DeserializationComponents components;
    @NotNull
    private final NameResolver nameResolver;
    @NotNull
    private final DeclarationDescriptor containingDeclaration;
    @NotNull
    private final TypeTable typeTable;
    @NotNull
    private final VersionRequirementTable versionRequirementTable;
    @NotNull
    private final BinaryVersion metadataVersion;
    @Nullable
    private final DeserializedContainerSource containerSource;

    @NotNull
    public final TypeDeserializer getTypeDeserializer() {
        return this.typeDeserializer;
    }

    @NotNull
    public final MemberDeserializer getMemberDeserializer() {
        return this.memberDeserializer;
    }

    @NotNull
    public final StorageManager getStorageManager() {
        return this.components.getStorageManager();
    }

    @NotNull
    public final DeserializationContext childContext(@NotNull DeclarationDescriptor descriptor2, @NotNull List<ProtoBuf.TypeParameter> typeParameterProtos, @NotNull NameResolver nameResolver, @NotNull TypeTable typeTable, @NotNull VersionRequirementTable versionRequirementTable, @NotNull BinaryVersion metadataVersion) {
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        Intrinsics.checkNotNullParameter(typeParameterProtos, "typeParameterProtos");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        return new DeserializationContext(this.components, nameResolver, descriptor2, typeTable, VersionSpecificBehaviorKt.isVersionRequirementTableWrittenCorrectly(metadataVersion) ? versionRequirementTable : this.versionRequirementTable, metadataVersion, this.containerSource, this.typeDeserializer, typeParameterProtos);
    }

    public static /* synthetic */ DeserializationContext childContext$default(DeserializationContext deserializationContext, DeclarationDescriptor declarationDescriptor, List list, NameResolver nameResolver, TypeTable typeTable, VersionRequirementTable versionRequirementTable, BinaryVersion binaryVersion, int n, Object object) {
        if ((n & 4) != 0) {
            nameResolver = deserializationContext.nameResolver;
        }
        if ((n & 8) != 0) {
            typeTable = deserializationContext.typeTable;
        }
        if ((n & 0x10) != 0) {
            versionRequirementTable = deserializationContext.versionRequirementTable;
        }
        if ((n & 0x20) != 0) {
            binaryVersion = deserializationContext.metadataVersion;
        }
        return deserializationContext.childContext(declarationDescriptor, list, nameResolver, typeTable, versionRequirementTable, binaryVersion);
    }

    @NotNull
    public final DeserializationComponents getComponents() {
        return this.components;
    }

    @NotNull
    public final NameResolver getNameResolver() {
        return this.nameResolver;
    }

    @NotNull
    public final DeclarationDescriptor getContainingDeclaration() {
        return this.containingDeclaration;
    }

    @NotNull
    public final TypeTable getTypeTable() {
        return this.typeTable;
    }

    @NotNull
    public final VersionRequirementTable getVersionRequirementTable() {
        return this.versionRequirementTable;
    }

    @Nullable
    public final DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    public DeserializationContext(@NotNull DeserializationComponents components, @NotNull NameResolver nameResolver, @NotNull DeclarationDescriptor containingDeclaration, @NotNull TypeTable typeTable, @NotNull VersionRequirementTable versionRequirementTable, @NotNull BinaryVersion metadataVersion, @Nullable DeserializedContainerSource containerSource, @Nullable TypeDeserializer parentTypeDeserializer, @NotNull List<ProtoBuf.TypeParameter> typeParameters2) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(typeParameters2, "typeParameters");
        this.components = components;
        this.nameResolver = nameResolver;
        this.containingDeclaration = containingDeclaration;
        this.typeTable = typeTable;
        this.versionRequirementTable = versionRequirementTable;
        this.metadataVersion = metadataVersion;
        this.containerSource = containerSource;
        String string = "Deserializer for \"" + this.containingDeclaration.getName() + '\"';
        Object object = this.containerSource;
        if (object == null || (object = object.getPresentableString()) == null) {
            object = "[container not found]";
        }
        this.typeDeserializer = new TypeDeserializer(this, parentTypeDeserializer, typeParameters2, string, (String)object, false, 32, null);
        this.memberDeserializer = new MemberDeserializer(this);
    }
}

