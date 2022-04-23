/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.FunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.SimpleFunctionDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedCallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DeserializedSimpleFunctionDescriptor
extends SimpleFunctionDescriptorImpl
implements DeserializedCallableMemberDescriptor {
    @NotNull
    private DeserializedMemberDescriptor.CoroutinesCompatibilityMode coroutinesExperimentalCompatibilityMode;
    @NotNull
    private final ProtoBuf.Function proto;
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

    @NotNull
    public final SimpleFunctionDescriptorImpl initialize(@Nullable ReceiverParameterDescriptor extensionReceiverParameter, @Nullable ReceiverParameterDescriptor dispatchReceiverParameter, @NotNull List<? extends TypeParameterDescriptor> typeParameters2, @NotNull List<? extends ValueParameterDescriptor> unsubstitutedValueParameters, @Nullable KotlinType unsubstitutedReturnType, @Nullable Modality modality, @NotNull Visibility visibility, @NotNull Map<? extends CallableDescriptor.UserDataKey<?>, ?> userDataMap, @NotNull DeserializedMemberDescriptor.CoroutinesCompatibilityMode isExperimentalCoroutineInReleaseEnvironment) {
        Intrinsics.checkNotNullParameter(typeParameters2, "typeParameters");
        Intrinsics.checkNotNullParameter(unsubstitutedValueParameters, "unsubstitutedValueParameters");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(userDataMap, "userDataMap");
        Intrinsics.checkNotNullParameter((Object)isExperimentalCoroutineInReleaseEnvironment, "isExperimentalCoroutineInReleaseEnvironment");
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImpl = super.initialize(extensionReceiverParameter, dispatchReceiverParameter, typeParameters2, unsubstitutedValueParameters, unsubstitutedReturnType, modality, visibility, userDataMap);
        Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptorImpl, "super.initialize(\n      \u2026    userDataMap\n        )");
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImpl2 = simpleFunctionDescriptorImpl;
        boolean bl = false;
        boolean bl2 = false;
        SimpleFunctionDescriptorImpl it = simpleFunctionDescriptorImpl2;
        boolean bl3 = false;
        this.coroutinesExperimentalCompatibilityMode = isExperimentalCoroutineInReleaseEnvironment;
        return simpleFunctionDescriptorImpl2;
    }

    @Override
    @NotNull
    protected FunctionDescriptorImpl createSubstitutedCopy(@NotNull DeclarationDescriptor newOwner, @Nullable FunctionDescriptor original, @NotNull CallableMemberDescriptor.Kind kind, @Nullable Name newName, @NotNull Annotations annotations2, @NotNull SourceElement source) {
        Intrinsics.checkNotNullParameter(newOwner, "newOwner");
        Intrinsics.checkNotNullParameter((Object)kind, "kind");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        Intrinsics.checkNotNullParameter(source, "source");
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor)original;
        Name name = newName;
        if (name == null) {
            Name name2 = this.getName();
            name = name2;
            Intrinsics.checkNotNullExpressionValue(name2, "name");
        }
        DeserializedSimpleFunctionDescriptor deserializedSimpleFunctionDescriptor = new DeserializedSimpleFunctionDescriptor(newOwner, simpleFunctionDescriptor, annotations2, name, kind, this.getProto(), this.getNameResolver(), this.getTypeTable(), this.getVersionRequirementTable(), this.getContainerSource(), source);
        boolean bl = false;
        boolean bl2 = false;
        DeserializedSimpleFunctionDescriptor it = deserializedSimpleFunctionDescriptor;
        boolean bl3 = false;
        it.coroutinesExperimentalCompatibilityMode = this.getCoroutinesExperimentalCompatibilityMode();
        return deserializedSimpleFunctionDescriptor;
    }

    @Override
    @NotNull
    public ProtoBuf.Function getProto() {
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

    public DeserializedSimpleFunctionDescriptor(@NotNull DeclarationDescriptor containingDeclaration, @Nullable SimpleFunctionDescriptor original, @NotNull Annotations annotations2, @NotNull Name name, @NotNull CallableMemberDescriptor.Kind kind, @NotNull ProtoBuf.Function proto, @NotNull NameResolver nameResolver, @NotNull TypeTable typeTable, @NotNull VersionRequirementTable versionRequirementTable, @Nullable DeserializedContainerSource containerSource, @Nullable SourceElement source) {
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter((Object)kind, "kind");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        SourceElement sourceElement = source;
        if (sourceElement == null) {
            sourceElement = SourceElement.NO_SOURCE;
        }
        super(containingDeclaration, original, annotations2, name, kind, sourceElement);
        this.proto = proto;
        this.nameResolver = nameResolver;
        this.typeTable = typeTable;
        this.versionRequirementTable = versionRequirementTable;
        this.containerSource = containerSource;
        this.coroutinesExperimentalCompatibilityMode = DeserializedMemberDescriptor.CoroutinesCompatibilityMode.COMPATIBLE;
    }

    public /* synthetic */ DeserializedSimpleFunctionDescriptor(DeclarationDescriptor declarationDescriptor, SimpleFunctionDescriptor simpleFunctionDescriptor, Annotations annotations2, Name name, CallableMemberDescriptor.Kind kind, ProtoBuf.Function function, NameResolver nameResolver, TypeTable typeTable, VersionRequirementTable versionRequirementTable, DeserializedContainerSource deserializedContainerSource, SourceElement sourceElement, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 0x400) != 0) {
            sourceElement = null;
        }
        this(declarationDescriptor, simpleFunctionDescriptor, annotations2, name, kind, function, nameResolver, typeTable, versionRequirementTable, deserializedContainerSource, sourceElement);
    }

    @Override
    @NotNull
    public List<VersionRequirement> getVersionRequirements() {
        return DeserializedCallableMemberDescriptor.DefaultImpls.getVersionRequirements(this);
    }
}

