/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassConstructorDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedCallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DeserializedClassConstructorDescriptor
extends ClassConstructorDescriptorImpl
implements DeserializedCallableMemberDescriptor {
    @NotNull
    private DeserializedMemberDescriptor.CoroutinesCompatibilityMode coroutinesExperimentalCompatibilityMode;
    @NotNull
    private final ProtoBuf.Constructor proto;
    @NotNull
    private final NameResolver nameResolver;
    @NotNull
    private final TypeTable typeTable;
    @NotNull
    private final VersionRequirementTable versionRequirementTable;
    @Nullable
    private final DeserializedContainerSource containerSource;

    @NotNull
    public DeserializedMemberDescriptor.CoroutinesCompatibilityMode getCoroutinesExperimentalCompatibilityMode() {
        return this.coroutinesExperimentalCompatibilityMode;
    }

    public void setCoroutinesExperimentalCompatibilityMode$deserialization(@NotNull DeserializedMemberDescriptor.CoroutinesCompatibilityMode coroutinesCompatibilityMode) {
        Intrinsics.checkNotNullParameter((Object)coroutinesCompatibilityMode, "<set-?>");
        this.coroutinesExperimentalCompatibilityMode = coroutinesCompatibilityMode;
    }

    @Override
    @NotNull
    protected DeserializedClassConstructorDescriptor createSubstitutedCopy(@NotNull DeclarationDescriptor newOwner, @Nullable FunctionDescriptor original, @NotNull CallableMemberDescriptor.Kind kind, @Nullable Name newName, @NotNull Annotations annotations2, @NotNull SourceElement source) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter((Object)kind, "kind");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        Intrinsics.checkNotNullParameter(source, "source");
        DeserializedClassConstructorDescriptor deserializedClassConstructorDescriptor = new DeserializedClassConstructorDescriptor((ClassDescriptor)newOwner, (ConstructorDescriptor)original, annotations2, this.isPrimary, kind, this.getProto(), this.getNameResolver(), this.getTypeTable(), this.getVersionRequirementTable(), this.getContainerSource(), source);
        boolean bl = false;
        boolean bl2 = false;
        DeserializedClassConstructorDescriptor it = deserializedClassConstructorDescriptor;
        boolean bl3 = false;
        it.setCoroutinesExperimentalCompatibilityMode$deserialization(this.getCoroutinesExperimentalCompatibilityMode());
        return deserializedClassConstructorDescriptor;
    }

    @Override
    public boolean isExternal() {
        return false;
    }

    @Override
    public boolean isInline() {
        return false;
    }

    @Override
    public boolean isTailrec() {
        return false;
    }

    @Override
    public boolean isSuspend() {
        return false;
    }

    @Override
    @NotNull
    public ProtoBuf.Constructor getProto() {
        return this.proto;
    }

    @Override
    @NotNull
    public NameResolver getNameResolver() {
        return this.nameResolver;
    }

    @Override
    @NotNull
    public TypeTable getTypeTable() {
        return this.typeTable;
    }

    @Override
    @NotNull
    public VersionRequirementTable getVersionRequirementTable() {
        return this.versionRequirementTable;
    }

    @Override
    @Nullable
    public DeserializedContainerSource getContainerSource() {
        return this.containerSource;
    }

    public DeserializedClassConstructorDescriptor(@NotNull ClassDescriptor containingDeclaration, @Nullable ConstructorDescriptor original, @NotNull Annotations annotations2, boolean isPrimary, @NotNull CallableMemberDescriptor.Kind kind, @NotNull ProtoBuf.Constructor proto, @NotNull NameResolver nameResolver, @NotNull TypeTable typeTable, @NotNull VersionRequirementTable versionRequirementTable, @Nullable DeserializedContainerSource containerSource, @Nullable SourceElement source) {
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        Intrinsics.checkNotNullParameter((Object)kind, "kind");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        SourceElement sourceElement = source;
        if (sourceElement == null) {
            sourceElement = SourceElement.NO_SOURCE;
        }
        super(containingDeclaration, original, annotations2, isPrimary, kind, sourceElement);
        this.proto = proto;
        this.nameResolver = nameResolver;
        this.typeTable = typeTable;
        this.versionRequirementTable = versionRequirementTable;
        this.containerSource = containerSource;
        this.coroutinesExperimentalCompatibilityMode = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
    }

    public /* synthetic */ DeserializedClassConstructorDescriptor(ClassDescriptor classDescriptor, ConstructorDescriptor constructorDescriptor, Annotations annotations2, boolean bl, CallableMemberDescriptor.Kind kind, ProtoBuf.Constructor constructor, NameResolver nameResolver, TypeTable typeTable, VersionRequirementTable versionRequirementTable, DeserializedContainerSource deserializedContainerSource, SourceElement sourceElement, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 0x400) != 0) {
            sourceElement = null;
        }
        this(classDescriptor, constructorDescriptor, annotations2, bl, kind, constructor, nameResolver, typeTable, versionRequirementTable, deserializedContainerSource, sourceElement);
    }

    @Override
    @NotNull
    public List<VersionRequirement> getVersionRequirements() {
        return DeserializedCallableMemberDescriptor.DefaultImpls.getVersionRequirements(this);
    }
}

