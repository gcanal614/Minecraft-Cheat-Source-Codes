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
import java.util.Collections;
import java.util.List;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassConstructorDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ClassDescriptorBase;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.ClassTypeConstructorImpl;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MutableClassDescriptor
extends ClassDescriptorBase {
    private final ClassKind kind;
    private final boolean isInner;
    private Modality modality;
    private Visibility visibility;
    private TypeConstructor typeConstructor;
    private List<TypeParameterDescriptor> typeParameters;
    private final Collection<KotlinType> supertypes;
    private final StorageManager storageManager;

    public MutableClassDescriptor(@NotNull DeclarationDescriptor containingDeclaration, @NotNull ClassKind kind, boolean isInner2, boolean isExternal, @NotNull Name name, @NotNull SourceElement source, @NotNull StorageManager storageManager) {
        if (containingDeclaration == null) {
            MutableClassDescriptor.$$$reportNull$$$0(0);
        }
        if (kind == null) {
            MutableClassDescriptor.$$$reportNull$$$0(1);
        }
        if (name == null) {
            MutableClassDescriptor.$$$reportNull$$$0(2);
        }
        if (source == null) {
            MutableClassDescriptor.$$$reportNull$$$0(3);
        }
        if (storageManager == null) {
            MutableClassDescriptor.$$$reportNull$$$0(4);
        }
        super(storageManager, containingDeclaration, name, source, isExternal);
        this.supertypes = new ArrayList<KotlinType>();
        this.storageManager = storageManager;
        assert (kind != ClassKind.OBJECT) : "Fix isCompanionObject()";
        this.kind = kind;
        this.isInner = isInner2;
    }

    @Override
    @Nullable
    public ClassDescriptor getCompanionObjectDescriptor() {
        return null;
    }

    @Override
    @NotNull
    public Annotations getAnnotations() {
        Annotations annotations2 = Annotations.Companion.getEMPTY();
        if (annotations2 == null) {
            MutableClassDescriptor.$$$reportNull$$$0(5);
        }
        return annotations2;
    }

    public void setModality(@NotNull Modality modality) {
        if (modality == null) {
            MutableClassDescriptor.$$$reportNull$$$0(6);
        }
        assert (modality != Modality.SEALED) : "Implement getSealedSubclasses() for this class: " + this.getClass();
        this.modality = modality;
    }

    @Override
    @NotNull
    public Modality getModality() {
        Modality modality = this.modality;
        if (modality == null) {
            MutableClassDescriptor.$$$reportNull$$$0(7);
        }
        return modality;
    }

    @Override
    @NotNull
    public ClassKind getKind() {
        ClassKind classKind = this.kind;
        if (classKind == null) {
            MutableClassDescriptor.$$$reportNull$$$0(8);
        }
        return classKind;
    }

    public void setVisibility(@NotNull Visibility visibility) {
        if (visibility == null) {
            MutableClassDescriptor.$$$reportNull$$$0(9);
        }
        this.visibility = visibility;
    }

    @Override
    @NotNull
    public Visibility getVisibility() {
        Visibility visibility = this.visibility;
        if (visibility == null) {
            MutableClassDescriptor.$$$reportNull$$$0(10);
        }
        return visibility;
    }

    @Override
    public boolean isInner() {
        return this.isInner;
    }

    @Override
    public boolean isData() {
        return false;
    }

    @Override
    public boolean isInline() {
        return false;
    }

    @Override
    public boolean isFun() {
        return false;
    }

    @Override
    public boolean isCompanionObject() {
        return false;
    }

    @Override
    public boolean isExpect() {
        return false;
    }

    @Override
    public boolean isActual() {
        return false;
    }

    @Override
    @NotNull
    public TypeConstructor getTypeConstructor() {
        TypeConstructor typeConstructor2 = this.typeConstructor;
        if (typeConstructor2 == null) {
            MutableClassDescriptor.$$$reportNull$$$0(11);
        }
        return typeConstructor2;
    }

    @NotNull
    public Set<ClassConstructorDescriptor> getConstructors() {
        Set<ClassConstructorDescriptor> set = Collections.emptySet();
        if (set == null) {
            MutableClassDescriptor.$$$reportNull$$$0(13);
        }
        return set;
    }

    @Override
    @Nullable
    public ClassConstructorDescriptor getUnsubstitutedPrimaryConstructor() {
        return null;
    }

    public void setTypeParameterDescriptors(@NotNull List<TypeParameterDescriptor> typeParameters2) {
        if (typeParameters2 == null) {
            MutableClassDescriptor.$$$reportNull$$$0(14);
        }
        if (this.typeParameters != null) {
            throw new IllegalStateException("Type parameters are already set for " + this.getName());
        }
        this.typeParameters = new ArrayList<TypeParameterDescriptor>(typeParameters2);
    }

    @Override
    @NotNull
    public List<TypeParameterDescriptor> getDeclaredTypeParameters() {
        List<TypeParameterDescriptor> list = this.typeParameters;
        if (list == null) {
            MutableClassDescriptor.$$$reportNull$$$0(15);
        }
        return list;
    }

    public void createTypeConstructor() {
        assert (this.typeConstructor == null) : this.typeConstructor;
        this.typeConstructor = new ClassTypeConstructorImpl(this, this.typeParameters, this.supertypes, this.storageManager);
        for (ClassConstructorDescriptor functionDescriptor : this.getConstructors()) {
            ((ClassConstructorDescriptorImpl)functionDescriptor).setReturnType(this.getDefaultType());
        }
    }

    @Override
    @NotNull
    public MemberScope getUnsubstitutedMemberScope(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        if (kotlinTypeRefiner == null) {
            MutableClassDescriptor.$$$reportNull$$$0(16);
        }
        MemberScope.Empty empty = MemberScope.Empty.INSTANCE;
        if (empty == null) {
            MutableClassDescriptor.$$$reportNull$$$0(17);
        }
        return empty;
    }

    @Override
    @NotNull
    public MemberScope getStaticScope() {
        MemberScope.Empty empty = MemberScope.Empty.INSTANCE;
        if (empty == null) {
            MutableClassDescriptor.$$$reportNull$$$0(18);
        }
        return empty;
    }

    @Override
    @NotNull
    public Collection<ClassDescriptor> getSealedSubclasses() {
        List<ClassDescriptor> list = Collections.emptyList();
        if (list == null) {
            MutableClassDescriptor.$$$reportNull$$$0(19);
        }
        return list;
    }

    public String toString() {
        return DeclarationDescriptorImpl.toString(this);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        RuntimeException runtimeException;
        Object[] objectArray;
        Object[] objectArray2;
        int n2;
        String string;
        switch (n) {
            default: {
                string = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            }
            case 5: 
            case 7: 
            case 8: 
            case 10: 
            case 11: 
            case 13: 
            case 15: 
            case 17: 
            case 18: 
            case 19: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 5: 
            case 7: 
            case 8: 
            case 10: 
            case 11: 
            case 13: 
            case 15: 
            case 17: 
            case 18: 
            case 19: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "containingDeclaration";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kind";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "source";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "storageManager";
                break;
            }
            case 5: 
            case 7: 
            case 8: 
            case 10: 
            case 11: 
            case 13: 
            case 15: 
            case 17: 
            case 18: 
            case 19: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/MutableClassDescriptor";
                break;
            }
            case 6: {
                objectArray2 = objectArray3;
                objectArray3[0] = "modality";
                break;
            }
            case 9: {
                objectArray2 = objectArray3;
                objectArray3[0] = "visibility";
                break;
            }
            case 12: {
                objectArray2 = objectArray3;
                objectArray3[0] = "supertype";
                break;
            }
            case 14: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeParameters";
                break;
            }
            case 16: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlinTypeRefiner";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/MutableClassDescriptor";
                break;
            }
            case 5: {
                objectArray = objectArray2;
                objectArray2[1] = "getAnnotations";
                break;
            }
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "getModality";
                break;
            }
            case 8: {
                objectArray = objectArray2;
                objectArray2[1] = "getKind";
                break;
            }
            case 10: {
                objectArray = objectArray2;
                objectArray2[1] = "getVisibility";
                break;
            }
            case 11: {
                objectArray = objectArray2;
                objectArray2[1] = "getTypeConstructor";
                break;
            }
            case 13: {
                objectArray = objectArray2;
                objectArray2[1] = "getConstructors";
                break;
            }
            case 15: {
                objectArray = objectArray2;
                objectArray2[1] = "getDeclaredTypeParameters";
                break;
            }
            case 17: {
                objectArray = objectArray2;
                objectArray2[1] = "getUnsubstitutedMemberScope";
                break;
            }
            case 18: {
                objectArray = objectArray2;
                objectArray2[1] = "getStaticScope";
                break;
            }
            case 19: {
                objectArray = objectArray2;
                objectArray2[1] = "getSealedSubclasses";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 5: 
            case 7: 
            case 8: 
            case 10: 
            case 11: 
            case 13: 
            case 15: 
            case 17: 
            case 18: 
            case 19: {
                break;
            }
            case 6: {
                objectArray = objectArray;
                objectArray[2] = "setModality";
                break;
            }
            case 9: {
                objectArray = objectArray;
                objectArray[2] = "setVisibility";
                break;
            }
            case 12: {
                objectArray = objectArray;
                objectArray[2] = "addSupertype";
                break;
            }
            case 14: {
                objectArray = objectArray;
                objectArray[2] = "setTypeParameterDescriptors";
                break;
            }
            case 16: {
                objectArray = objectArray;
                objectArray[2] = "getUnsubstitutedMemberScope";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 5: 
            case 7: 
            case 8: 
            case 10: 
            case 11: 
            case 13: 
            case 15: 
            case 17: 
            case 18: 
            case 19: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

