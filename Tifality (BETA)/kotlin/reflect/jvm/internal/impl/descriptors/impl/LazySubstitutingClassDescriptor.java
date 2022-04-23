/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleAwareClassDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.SubstitutingScope;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.types.ClassTypeConstructorImpl;
import kotlin.reflect.jvm.internal.impl.types.DescriptorSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LazySubstitutingClassDescriptor
extends ModuleAwareClassDescriptor {
    private final ModuleAwareClassDescriptor original;
    private final TypeSubstitutor originalSubstitutor;
    private TypeSubstitutor newSubstitutor;
    private List<TypeParameterDescriptor> typeConstructorParameters;
    private List<TypeParameterDescriptor> declaredTypeParameters;
    private TypeConstructor typeConstructor;

    public LazySubstitutingClassDescriptor(ModuleAwareClassDescriptor descriptor2, TypeSubstitutor substitutor) {
        this.original = descriptor2;
        this.originalSubstitutor = substitutor;
    }

    private TypeSubstitutor getSubstitutor() {
        if (this.newSubstitutor == null) {
            if (this.originalSubstitutor.isEmpty()) {
                this.newSubstitutor = this.originalSubstitutor;
            } else {
                List<TypeParameterDescriptor> originalTypeParameters = this.original.getTypeConstructor().getParameters();
                this.typeConstructorParameters = new ArrayList<TypeParameterDescriptor>(originalTypeParameters.size());
                this.newSubstitutor = DescriptorSubstitutor.substituteTypeParameters(originalTypeParameters, this.originalSubstitutor.getSubstitution(), this, this.typeConstructorParameters);
                this.declaredTypeParameters = CollectionsKt.filter(this.typeConstructorParameters, new Function1<TypeParameterDescriptor, Boolean>(){

                    @Override
                    public Boolean invoke(TypeParameterDescriptor descriptor2) {
                        return !descriptor2.isCapturedFromOuterDeclaration();
                    }
                });
            }
        }
        return this.newSubstitutor;
    }

    @Override
    @NotNull
    public TypeConstructor getTypeConstructor() {
        TypeConstructor originalTypeConstructor = this.original.getTypeConstructor();
        if (this.originalSubstitutor.isEmpty()) {
            TypeConstructor typeConstructor2 = originalTypeConstructor;
            if (typeConstructor2 == null) {
                LazySubstitutingClassDescriptor.$$$reportNull$$$0(0);
            }
            return typeConstructor2;
        }
        if (this.typeConstructor == null) {
            TypeSubstitutor substitutor = this.getSubstitutor();
            Collection<KotlinType> originalSupertypes = originalTypeConstructor.getSupertypes();
            ArrayList<KotlinType> supertypes2 = new ArrayList<KotlinType>(originalSupertypes.size());
            for (KotlinType supertype : originalSupertypes) {
                supertypes2.add(substitutor.substitute(supertype, Variance.INVARIANT));
            }
            this.typeConstructor = new ClassTypeConstructorImpl(this, this.typeConstructorParameters, supertypes2, LockBasedStorageManager.NO_LOCKS);
        }
        TypeConstructor typeConstructor3 = this.typeConstructor;
        if (typeConstructor3 == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(1);
        }
        return typeConstructor3;
    }

    @Override
    @NotNull
    public MemberScope getMemberScope(@NotNull TypeSubstitution typeSubstitution, @NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        if (typeSubstitution == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(5);
        }
        if (kotlinTypeRefiner == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(6);
        }
        MemberScope memberScope2 = this.original.getMemberScope(typeSubstitution, kotlinTypeRefiner);
        if (this.originalSubstitutor.isEmpty()) {
            MemberScope memberScope3 = memberScope2;
            if (memberScope3 == null) {
                LazySubstitutingClassDescriptor.$$$reportNull$$$0(7);
            }
            return memberScope3;
        }
        return new SubstitutingScope(memberScope2, this.getSubstitutor());
    }

    @Override
    @NotNull
    public MemberScope getMemberScope(@NotNull TypeSubstitution typeSubstitution) {
        if (typeSubstitution == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(10);
        }
        MemberScope memberScope2 = this.getMemberScope(typeSubstitution, DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtils.getContainingModule(this)));
        if (memberScope2 == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(11);
        }
        return memberScope2;
    }

    @Override
    @NotNull
    public MemberScope getUnsubstitutedMemberScope() {
        MemberScope memberScope2 = this.getUnsubstitutedMemberScope(DescriptorUtilsKt.getKotlinTypeRefiner(DescriptorUtils.getContainingModule(this.original)));
        if (memberScope2 == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(12);
        }
        return memberScope2;
    }

    @Override
    @NotNull
    public MemberScope getUnsubstitutedMemberScope(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        if (kotlinTypeRefiner == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(13);
        }
        MemberScope memberScope2 = this.original.getUnsubstitutedMemberScope(kotlinTypeRefiner);
        if (this.originalSubstitutor.isEmpty()) {
            MemberScope memberScope3 = memberScope2;
            if (memberScope3 == null) {
                LazySubstitutingClassDescriptor.$$$reportNull$$$0(14);
            }
            return memberScope3;
        }
        return new SubstitutingScope(memberScope2, this.getSubstitutor());
    }

    @Override
    @NotNull
    public MemberScope getStaticScope() {
        MemberScope memberScope2 = this.original.getStaticScope();
        if (memberScope2 == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(15);
        }
        return memberScope2;
    }

    @Override
    @NotNull
    public SimpleType getDefaultType() {
        List<TypeProjection> typeProjections = TypeUtils.getDefaultTypeProjections(this.getTypeConstructor().getParameters());
        SimpleType simpleType2 = KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(this.getAnnotations(), this.getTypeConstructor(), typeProjections, false, this.getUnsubstitutedMemberScope());
        if (simpleType2 == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(16);
        }
        return simpleType2;
    }

    @Override
    @NotNull
    public ReceiverParameterDescriptor getThisAsReceiverParameter() {
        throw new UnsupportedOperationException();
    }

    @Override
    @NotNull
    public Collection<ClassConstructorDescriptor> getConstructors() {
        Collection<ClassConstructorDescriptor> originalConstructors = this.original.getConstructors();
        ArrayList<ClassConstructorDescriptor> result2 = new ArrayList<ClassConstructorDescriptor>(originalConstructors.size());
        for (ClassConstructorDescriptor constructor : originalConstructors) {
            ClassConstructorDescriptor copy2 = (ClassConstructorDescriptor)constructor.newCopyBuilder().setOriginal(constructor.getOriginal()).setModality(constructor.getModality()).setVisibility(constructor.getVisibility()).setKind(constructor.getKind()).setCopyOverrides(false).build();
            result2.add(copy2.substitute(this.getSubstitutor()));
        }
        ArrayList<ClassConstructorDescriptor> arrayList = result2;
        if (arrayList == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(17);
        }
        return arrayList;
    }

    @Override
    @NotNull
    public Annotations getAnnotations() {
        Annotations annotations2 = this.original.getAnnotations();
        if (annotations2 == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(18);
        }
        return annotations2;
    }

    @Override
    @NotNull
    public Name getName() {
        Name name = this.original.getName();
        if (name == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(19);
        }
        return name;
    }

    @Override
    @NotNull
    public ClassDescriptor getOriginal() {
        ClassDescriptor classDescriptor = this.original.getOriginal();
        if (classDescriptor == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(20);
        }
        return classDescriptor;
    }

    @Override
    @NotNull
    public DeclarationDescriptor getContainingDeclaration() {
        DeclarationDescriptor declarationDescriptor = this.original.getContainingDeclaration();
        if (declarationDescriptor == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(21);
        }
        return declarationDescriptor;
    }

    @Override
    @NotNull
    public ClassDescriptor substitute(@NotNull TypeSubstitutor substitutor) {
        if (substitutor == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(22);
        }
        if (substitutor.isEmpty()) {
            LazySubstitutingClassDescriptor lazySubstitutingClassDescriptor = this;
            if (lazySubstitutingClassDescriptor == null) {
                LazySubstitutingClassDescriptor.$$$reportNull$$$0(23);
            }
            return lazySubstitutingClassDescriptor;
        }
        return new LazySubstitutingClassDescriptor(this, TypeSubstitutor.createChainedSubstitutor(substitutor.getSubstitution(), this.getSubstitutor().getSubstitution()));
    }

    @Override
    public ClassDescriptor getCompanionObjectDescriptor() {
        return this.original.getCompanionObjectDescriptor();
    }

    @Override
    @NotNull
    public ClassKind getKind() {
        ClassKind classKind = this.original.getKind();
        if (classKind == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(24);
        }
        return classKind;
    }

    @Override
    @NotNull
    public Modality getModality() {
        Modality modality = this.original.getModality();
        if (modality == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(25);
        }
        return modality;
    }

    @Override
    @NotNull
    public Visibility getVisibility() {
        Visibility visibility = this.original.getVisibility();
        if (visibility == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(26);
        }
        return visibility;
    }

    @Override
    public boolean isInner() {
        return this.original.isInner();
    }

    @Override
    public boolean isData() {
        return this.original.isData();
    }

    @Override
    public boolean isInline() {
        return this.original.isInline();
    }

    @Override
    public boolean isFun() {
        return this.original.isFun();
    }

    @Override
    public boolean isExternal() {
        return this.original.isExternal();
    }

    @Override
    public boolean isCompanionObject() {
        return this.original.isCompanionObject();
    }

    @Override
    public boolean isExpect() {
        return this.original.isExpect();
    }

    @Override
    public boolean isActual() {
        return this.original.isActual();
    }

    @Override
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor2, D data2) {
        return visitor2.visitClassDescriptor(this, data2);
    }

    @Override
    @NotNull
    public MemberScope getUnsubstitutedInnerClassesScope() {
        MemberScope memberScope2 = this.original.getUnsubstitutedInnerClassesScope();
        if (memberScope2 == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(27);
        }
        return memberScope2;
    }

    @Override
    @Nullable
    public ClassConstructorDescriptor getUnsubstitutedPrimaryConstructor() {
        return this.original.getUnsubstitutedPrimaryConstructor();
    }

    @Override
    @NotNull
    public SourceElement getSource() {
        SourceElement sourceElement = SourceElement.NO_SOURCE;
        if (sourceElement == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(28);
        }
        return sourceElement;
    }

    @Override
    @NotNull
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        this.getSubstitutor();
        List<TypeParameterDescriptor> list = this.declaredTypeParameters;
        if (list == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(29);
        }
        return list;
    }

    @Override
    @NotNull
    public Collection<ClassDescriptor> getSealedSubclasses() {
        Collection<ClassDescriptor> collection = this.original.getSealedSubclasses();
        if (collection == null) {
            LazySubstitutingClassDescriptor.$$$reportNull$$$0(30);
        }
        return collection;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        RuntimeException runtimeException;
        Object[] objectArray;
        Object[] objectArray2;
        int n2;
        String string;
        switch (n) {
            default: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
            case 2: 
            case 3: 
            case 5: 
            case 6: 
            case 8: 
            case 10: 
            case 13: 
            case 22: {
                string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 2;
                break;
            }
            case 2: 
            case 3: 
            case 5: 
            case 6: 
            case 8: 
            case 10: 
            case 13: 
            case 22: {
                n2 = 3;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/LazySubstitutingClassDescriptor";
                break;
            }
            case 2: 
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeArguments";
                break;
            }
            case 3: 
            case 6: 
            case 13: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlinTypeRefiner";
                break;
            }
            case 5: 
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeSubstitution";
                break;
            }
            case 22: {
                objectArray2 = objectArray3;
                objectArray3[0] = "substitutor";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "getTypeConstructor";
                break;
            }
            case 2: 
            case 3: 
            case 5: 
            case 6: 
            case 8: 
            case 10: 
            case 13: 
            case 22: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/LazySubstitutingClassDescriptor";
                break;
            }
            case 4: 
            case 7: 
            case 9: 
            case 11: {
                objectArray = objectArray2;
                objectArray2[1] = "getMemberScope";
                break;
            }
            case 12: 
            case 14: {
                objectArray = objectArray2;
                objectArray2[1] = "getUnsubstitutedMemberScope";
                break;
            }
            case 15: {
                objectArray = objectArray2;
                objectArray2[1] = "getStaticScope";
                break;
            }
            case 16: {
                objectArray = objectArray2;
                objectArray2[1] = "getDefaultType";
                break;
            }
            case 17: {
                objectArray = objectArray2;
                objectArray2[1] = "getConstructors";
                break;
            }
            case 18: {
                objectArray = objectArray2;
                objectArray2[1] = "getAnnotations";
                break;
            }
            case 19: {
                objectArray = objectArray2;
                objectArray2[1] = "getName";
                break;
            }
            case 20: {
                objectArray = objectArray2;
                objectArray2[1] = "getOriginal";
                break;
            }
            case 21: {
                objectArray = objectArray2;
                objectArray2[1] = "getContainingDeclaration";
                break;
            }
            case 23: {
                objectArray = objectArray2;
                objectArray2[1] = "substitute";
                break;
            }
            case 24: {
                objectArray = objectArray2;
                objectArray2[1] = "getKind";
                break;
            }
            case 25: {
                objectArray = objectArray2;
                objectArray2[1] = "getModality";
                break;
            }
            case 26: {
                objectArray = objectArray2;
                objectArray2[1] = "getVisibility";
                break;
            }
            case 27: {
                objectArray = objectArray2;
                objectArray2[1] = "getUnsubstitutedInnerClassesScope";
                break;
            }
            case 28: {
                objectArray = objectArray2;
                objectArray2[1] = "getSource";
                break;
            }
            case 29: {
                objectArray = objectArray2;
                objectArray2[1] = "getDeclaredTypeParameters";
                break;
            }
            case 30: {
                objectArray = objectArray2;
                objectArray2[1] = "getSealedSubclasses";
                break;
            }
        }
        switch (n) {
            default: {
                break;
            }
            case 2: 
            case 3: 
            case 5: 
            case 6: 
            case 8: 
            case 10: {
                objectArray = objectArray;
                objectArray[2] = "getMemberScope";
                break;
            }
            case 13: {
                objectArray = objectArray;
                objectArray[2] = "getUnsubstitutedMemberScope";
                break;
            }
            case 22: {
                objectArray = objectArray;
                objectArray[2] = "substitute";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
            case 2: 
            case 3: 
            case 5: 
            case 6: 
            case 8: 
            case 10: 
            case 13: 
            case 22: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

