/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassOrPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ClassDescriptor
extends ClassOrPackageFragmentDescriptor,
ClassifierDescriptorWithTypeParameters {
    @NotNull
    public MemberScope getMemberScope(@NotNull TypeSubstitution var1);

    @NotNull
    public MemberScope getUnsubstitutedMemberScope();

    @NotNull
    public MemberScope getUnsubstitutedInnerClassesScope();

    @NotNull
    public MemberScope getStaticScope();

    @NotNull
    public Collection<ClassConstructorDescriptor> getConstructors();

    @Override
    @NotNull
    public DeclarationDescriptor getContainingDeclaration();

    @Override
    @NotNull
    public SimpleType getDefaultType();

    @Nullable
    public ClassDescriptor getCompanionObjectDescriptor();

    @NotNull
    public ClassKind getKind();

    @Override
    @NotNull
    public Modality getModality();

    @Override
    @NotNull
    public Visibility getVisibility();

    public boolean isCompanionObject();

    public boolean isData();

    public boolean isInline();

    public boolean isFun();

    @NotNull
    public ReceiverParameterDescriptor getThisAsReceiverParameter();

    @Nullable
    public ClassConstructorDescriptor getUnsubstitutedPrimaryConstructor();

    @Override
    @NotNull
    public List<TypeParameterDescriptor> getDeclaredTypeParameters();

    @NotNull
    public Collection<ClassDescriptor> getSealedSubclasses();

    @Override
    @NotNull
    public ClassDescriptor getOriginal();
}

