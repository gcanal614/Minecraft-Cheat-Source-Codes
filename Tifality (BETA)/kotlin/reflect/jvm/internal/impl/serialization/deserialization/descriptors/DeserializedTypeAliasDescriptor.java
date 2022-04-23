/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterUtilsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractTypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeAliasConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutionKt;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DeserializedTypeAliasDescriptor
extends AbstractTypeAliasDescriptor
implements DeserializedMemberDescriptor {
    @NotNull
    private Collection<? extends TypeAliasConstructorDescriptor> constructors;
    @NotNull
    private SimpleType underlyingType;
    @NotNull
    private SimpleType expandedType;
    private List<? extends TypeParameterDescriptor> typeConstructorParameters;
    private SimpleType defaultTypeImpl;
    @NotNull
    private DeserializedMemberDescriptor.CoroutinesCompatibilityMode coroutinesExperimentalCompatibilityMode;
    @NotNull
    private final StorageManager storageManager;
    @NotNull
    private final ProtoBuf.TypeAlias proto;
    @NotNull
    private final NameResolver nameResolver;
    @NotNull
    private final TypeTable typeTable;
    @NotNull
    private final VersionRequirementTable versionRequirementTable;
    @Nullable
    private final DeserializedContainerSource containerSource;

    @Override
    @NotNull
    public SimpleType getUnderlyingType() {
        SimpleType simpleType2 = this.underlyingType;
        if (simpleType2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("underlyingType");
        }
        return simpleType2;
    }

    @Override
    @NotNull
    public SimpleType getExpandedType() {
        SimpleType simpleType2 = this.expandedType;
        if (simpleType2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("expandedType");
        }
        return simpleType2;
    }

    @NotNull
    public DeserializedMemberDescriptor.CoroutinesCompatibilityMode getCoroutinesExperimentalCompatibilityMode() {
        return this.coroutinesExperimentalCompatibilityMode;
    }

    public final void initialize(@NotNull List<? extends TypeParameterDescriptor> declaredTypeParameters, @NotNull SimpleType underlyingType, @NotNull SimpleType expandedType, @NotNull DeserializedMemberDescriptor.CoroutinesCompatibilityMode isExperimentalCoroutineInReleaseEnvironment) {
        Intrinsics.checkNotNullParameter(declaredTypeParameters, "declaredTypeParameters");
        Intrinsics.checkNotNullParameter(underlyingType, "underlyingType");
        Intrinsics.checkNotNullParameter(expandedType, "expandedType");
        Intrinsics.checkNotNullParameter((Object)isExperimentalCoroutineInReleaseEnvironment, "isExperimentalCoroutineInReleaseEnvironment");
        this.initialize(declaredTypeParameters);
        this.underlyingType = underlyingType;
        this.expandedType = expandedType;
        this.typeConstructorParameters = TypeParameterUtilsKt.computeConstructorTypeParameters(this);
        this.defaultTypeImpl = this.computeDefaultType();
        this.constructors = this.getTypeAliasConstructors();
        this.coroutinesExperimentalCompatibilityMode = isExperimentalCoroutineInReleaseEnvironment;
    }

    @Override
    @Nullable
    public ClassDescriptor getClassDescriptor() {
        ClassDescriptor classDescriptor;
        if (KotlinTypeKt.isError(this.getExpandedType())) {
            classDescriptor = null;
        } else {
            ClassifierDescriptor classifierDescriptor = this.getExpandedType().getConstructor().getDeclarationDescriptor();
            if (!(classifierDescriptor instanceof ClassDescriptor)) {
                classifierDescriptor = null;
            }
            classDescriptor = (ClassDescriptor)classifierDescriptor;
        }
        return classDescriptor;
    }

    @Override
    @NotNull
    public SimpleType getDefaultType() {
        SimpleType simpleType2 = this.defaultTypeImpl;
        if (simpleType2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("defaultTypeImpl");
        }
        return simpleType2;
    }

    @Override
    @NotNull
    public TypeAliasDescriptor substitute(@NotNull TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        if (substitutor.isEmpty()) {
            return this;
        }
        StorageManager storageManager = this.getStorageManager();
        DeclarationDescriptor declarationDescriptor = this.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "containingDeclaration");
        Annotations annotations2 = this.getAnnotations();
        Intrinsics.checkNotNullExpressionValue(annotations2, "annotations");
        Name name = this.getName();
        Intrinsics.checkNotNullExpressionValue(name, "name");
        DeserializedTypeAliasDescriptor substituted = new DeserializedTypeAliasDescriptor(storageManager, declarationDescriptor, annotations2, name, this.getVisibility(), this.getProto(), this.getNameResolver(), this.getTypeTable(), this.getVersionRequirementTable(), this.getContainerSource());
        List<TypeParameterDescriptor> list = this.getDeclaredTypeParameters();
        KotlinType kotlinType = substitutor.safeSubstitute(this.getUnderlyingType(), Variance.INVARIANT);
        Intrinsics.checkNotNullExpressionValue(kotlinType, "substitutor.safeSubstitu\u2026Type, Variance.INVARIANT)");
        SimpleType simpleType2 = TypeSubstitutionKt.asSimpleType(kotlinType);
        KotlinType kotlinType2 = substitutor.safeSubstitute(this.getExpandedType(), Variance.INVARIANT);
        Intrinsics.checkNotNullExpressionValue(kotlinType2, "substitutor.safeSubstitu\u2026Type, Variance.INVARIANT)");
        substituted.initialize(list, simpleType2, TypeSubstitutionKt.asSimpleType(kotlinType2), this.getCoroutinesExperimentalCompatibilityMode());
        return substituted;
    }

    @Override
    @NotNull
    protected List<TypeParameterDescriptor> getTypeConstructorTypeParameters() {
        List<TypeParameterDescriptor> list = this.typeConstructorParameters;
        if (list == null) {
            Intrinsics.throwUninitializedPropertyAccessException("typeConstructorParameters");
        }
        return list;
    }

    @Override
    @NotNull
    protected StorageManager getStorageManager() {
        return this.storageManager;
    }

    @Override
    @NotNull
    public ProtoBuf.TypeAlias getProto() {
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

    public DeserializedTypeAliasDescriptor(@NotNull StorageManager storageManager, @NotNull DeclarationDescriptor containingDeclaration, @NotNull Annotations annotations2, @NotNull Name name, @NotNull Visibility visibility, @NotNull ProtoBuf.TypeAlias proto, @NotNull NameResolver nameResolver, @NotNull TypeTable typeTable, @NotNull VersionRequirementTable versionRequirementTable, @Nullable DeserializedContainerSource containerSource) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        SourceElement sourceElement = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(sourceElement, "SourceElement.NO_SOURCE");
        super(containingDeclaration, annotations2, name, sourceElement, visibility);
        this.storageManager = storageManager;
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
        return DeserializedMemberDescriptor.DefaultImpls.getVersionRequirements(this);
    }
}

