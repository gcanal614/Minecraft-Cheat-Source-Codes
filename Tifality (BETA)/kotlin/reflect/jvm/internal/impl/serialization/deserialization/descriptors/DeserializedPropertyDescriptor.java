/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.List;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FieldDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
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

public final class DeserializedPropertyDescriptor
extends PropertyDescriptorImpl
implements DeserializedCallableMemberDescriptor {
    @NotNull
    private DeserializedMemberDescriptor.CoroutinesCompatibilityMode coroutinesExperimentalCompatibilityMode;
    @NotNull
    private final ProtoBuf.Property proto;
    @NotNull
    private final NameResolver nameResolver;
    @NotNull
    private final TypeTable typeTable;
    @NotNull
    private final VersionRequirementTable versionRequirementTable;
    @Nullable
    private final DeserializedContainerSource containerSource;

    public final void initialize(@Nullable PropertyGetterDescriptorImpl getter, @Nullable PropertySetterDescriptor setter, @Nullable FieldDescriptor backingField, @Nullable FieldDescriptor delegateField2, @NotNull DeserializedMemberDescriptor.CoroutinesCompatibilityMode isExperimentalCoroutineInReleaseEnvironment) {
        Intrinsics.checkNotNullParameter((Object)isExperimentalCoroutineInReleaseEnvironment, "isExperimentalCoroutineInReleaseEnvironment");
        super.initialize(getter, setter, backingField, delegateField2);
        Unit unit = Unit.INSTANCE;
        boolean bl = false;
        boolean bl2 = false;
        Unit it = unit;
        boolean bl3 = false;
        this.coroutinesExperimentalCompatibilityMode = isExperimentalCoroutineInReleaseEnvironment;
    }

    @Override
    @NotNull
    protected PropertyDescriptorImpl createSubstitutedCopy(@NotNull DeclarationDescriptor newOwner, @NotNull Modality newModality, @NotNull Visibility newVisibility, @Nullable PropertyDescriptor original, @NotNull CallableMemberDescriptor.Kind kind, @NotNull Name newName, @NotNull SourceElement source) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter((Object)newModality, "newModality");
        Intrinsics.checkNotNullParameter(newVisibility, "newVisibility");
        Intrinsics.checkNotNullParameter((Object)kind, "kind");
        Intrinsics.checkNotNullParameter(newName, "newName");
        Intrinsics.checkNotNullParameter(source, "source");
        return new DeserializedPropertyDescriptor(newOwner, original, this.getAnnotations(), newModality, newVisibility, this.isVar(), newName, kind, this.isLateInit(), this.isConst(), this.isExternal(), this.isDelegated(), this.isExpect(), this.getProto(), this.getNameResolver(), this.getTypeTable(), this.getVersionRequirementTable(), this.getContainerSource());
    }

    @Override
    public boolean isExternal() {
        Boolean bl = Flags.IS_EXTERNAL_PROPERTY.get(this.getProto().getFlags());
        Intrinsics.checkNotNullExpressionValue(bl, "Flags.IS_EXTERNAL_PROPERTY.get(proto.flags)");
        return bl;
    }

    @Override
    @NotNull
    public ProtoBuf.Property getProto() {
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

    public DeserializedPropertyDescriptor(@NotNull DeclarationDescriptor containingDeclaration, @Nullable PropertyDescriptor original, @NotNull Annotations annotations2, @NotNull Modality modality, @NotNull Visibility visibility, boolean isVar, @NotNull Name name, @NotNull CallableMemberDescriptor.Kind kind, boolean isLateInit, boolean isConst, boolean isExternal, boolean isDelegated, boolean isExpect, @NotNull ProtoBuf.Property proto, @NotNull NameResolver nameResolver, @NotNull TypeTable typeTable, @NotNull VersionRequirementTable versionRequirementTable, @Nullable DeserializedContainerSource containerSource) {
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        Intrinsics.checkNotNullParameter((Object)modality, "modality");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter((Object)kind, "kind");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        super(containingDeclaration, original, annotations2, modality, visibility, isVar, name, kind, SourceElement.NO_SOURCE, isLateInit, isConst, isExpect, false, isExternal, isDelegated);
        this.proto = proto;
        this.nameResolver = nameResolver;
        this.typeTable = typeTable;
        this.versionRequirementTable = versionRequirementTable;
        this.containerSource = containerSource;
        this.coroutinesExperimentalCompatibilityMode = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
    }

    @Override
    @NotNull
    public List<VersionRequirement> getVersionRequirements() {
        return DeserializedCallableMemberDescriptor.DefaultImpls.getVersionRequirements(this);
    }
}

