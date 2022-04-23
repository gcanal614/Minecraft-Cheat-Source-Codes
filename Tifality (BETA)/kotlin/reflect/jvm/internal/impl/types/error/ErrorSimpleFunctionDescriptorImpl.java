/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types.error;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
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
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ErrorSimpleFunctionDescriptorImpl
extends SimpleFunctionDescriptorImpl {
    private final ErrorUtils.ErrorScope ownerScope;

    public ErrorSimpleFunctionDescriptorImpl(@NotNull ClassDescriptor containingDeclaration, @NotNull ErrorUtils.ErrorScope ownerScope) {
        if (containingDeclaration == null) {
            ErrorSimpleFunctionDescriptorImpl.$$$reportNull$$$0(0);
        }
        if (ownerScope == null) {
            ErrorSimpleFunctionDescriptorImpl.$$$reportNull$$$0(1);
        }
        super((DeclarationDescriptor)containingDeclaration, null, Annotations.Companion.getEMPTY(), Name.special("<ERROR FUNCTION>"), CallableMemberDescriptor.Kind.DECLARATION, SourceElement.NO_SOURCE);
        this.ownerScope = ownerScope;
    }

    @Override
    @NotNull
    protected FunctionDescriptorImpl createSubstitutedCopy(@NotNull DeclarationDescriptor newOwner, @Nullable FunctionDescriptor original, @NotNull CallableMemberDescriptor.Kind kind, @Nullable Name newName, @NotNull Annotations annotations2, @NotNull SourceElement source) {
        if (newOwner == null) {
            ErrorSimpleFunctionDescriptorImpl.$$$reportNull$$$0(2);
        }
        if (kind == null) {
            ErrorSimpleFunctionDescriptorImpl.$$$reportNull$$$0(3);
        }
        if (annotations2 == null) {
            ErrorSimpleFunctionDescriptorImpl.$$$reportNull$$$0(4);
        }
        if (source == null) {
            ErrorSimpleFunctionDescriptorImpl.$$$reportNull$$$0(5);
        }
        ErrorSimpleFunctionDescriptorImpl errorSimpleFunctionDescriptorImpl = this;
        if (errorSimpleFunctionDescriptorImpl == null) {
            ErrorSimpleFunctionDescriptorImpl.$$$reportNull$$$0(6);
        }
        return errorSimpleFunctionDescriptorImpl;
    }

    @Override
    @NotNull
    public SimpleFunctionDescriptor copy(DeclarationDescriptor newOwner, Modality modality, Visibility visibility, CallableMemberDescriptor.Kind kind, boolean copyOverrides) {
        ErrorSimpleFunctionDescriptorImpl errorSimpleFunctionDescriptorImpl = this;
        if (errorSimpleFunctionDescriptorImpl == null) {
            ErrorSimpleFunctionDescriptorImpl.$$$reportNull$$$0(7);
        }
        return errorSimpleFunctionDescriptorImpl;
    }

    @Override
    @NotNull
    public FunctionDescriptor.CopyBuilder<? extends SimpleFunctionDescriptor> newCopyBuilder() {
        return new FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor>(){

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setOwner(@NotNull DeclarationDescriptor owner) {
                if (owner == null) {
                    1.$$$reportNull$$$0(0);
                }
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(1);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setModality(@NotNull Modality modality) {
                if (modality == null) {
                    1.$$$reportNull$$$0(2);
                }
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(3);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setVisibility(@NotNull Visibility visibility) {
                if (visibility == null) {
                    1.$$$reportNull$$$0(4);
                }
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(5);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setKind(@NotNull CallableMemberDescriptor.Kind kind) {
                if (kind == null) {
                    1.$$$reportNull$$$0(6);
                }
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(7);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setCopyOverrides(boolean copyOverrides) {
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(8);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setName(@NotNull Name name) {
                if (name == null) {
                    1.$$$reportNull$$$0(9);
                }
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(10);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setValueParameters(@NotNull List<ValueParameterDescriptor> parameters2) {
                if (parameters2 == null) {
                    1.$$$reportNull$$$0(11);
                }
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(12);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setSubstitution(@NotNull TypeSubstitution substitution) {
                if (substitution == null) {
                    1.$$$reportNull$$$0(13);
                }
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(14);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setTypeParameters(@NotNull List<TypeParameterDescriptor> parameters2) {
                if (parameters2 == null) {
                    1.$$$reportNull$$$0(17);
                }
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(18);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setReturnType(@NotNull KotlinType type2) {
                if (type2 == null) {
                    1.$$$reportNull$$$0(19);
                }
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(20);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setExtensionReceiverParameter(@Nullable ReceiverParameterDescriptor extensionReceiverParameter) {
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(21);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setDispatchReceiverParameter(@Nullable ReceiverParameterDescriptor dispatchReceiverParameter) {
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(22);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setOriginal(@Nullable CallableMemberDescriptor original) {
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(23);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setSignatureChange() {
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(24);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setPreserveSourceElement() {
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(25);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setDropOriginalInContainingParts() {
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(26);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setHiddenToOvercomeSignatureClash() {
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(27);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setHiddenForResolutionEverywhereBesideSupercalls() {
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(28);
                }
                return v0;
            }

            @Override
            @NotNull
            public FunctionDescriptor.CopyBuilder<SimpleFunctionDescriptor> setAdditionalAnnotations(@NotNull Annotations additionalAnnotations) {
                if (additionalAnnotations == null) {
                    1.$$$reportNull$$$0(29);
                }
                1 v0 = this;
                if (v0 == null) {
                    1.$$$reportNull$$$0(30);
                }
                return v0;
            }

            @Override
            @Nullable
            public SimpleFunctionDescriptor build() {
                return ErrorSimpleFunctionDescriptorImpl.this;
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
                    case 1: 
                    case 3: 
                    case 5: 
                    case 7: 
                    case 8: 
                    case 10: 
                    case 12: 
                    case 14: 
                    case 16: 
                    case 18: 
                    case 20: 
                    case 21: 
                    case 22: 
                    case 23: 
                    case 24: 
                    case 25: 
                    case 26: 
                    case 27: 
                    case 28: 
                    case 30: {
                        string = "@NotNull method %s.%s must not return null";
                        break;
                    }
                }
                switch (n) {
                    default: {
                        n2 = 3;
                        break;
                    }
                    case 1: 
                    case 3: 
                    case 5: 
                    case 7: 
                    case 8: 
                    case 10: 
                    case 12: 
                    case 14: 
                    case 16: 
                    case 18: 
                    case 20: 
                    case 21: 
                    case 22: 
                    case 23: 
                    case 24: 
                    case 25: 
                    case 26: 
                    case 27: 
                    case 28: 
                    case 30: {
                        n2 = 2;
                        break;
                    }
                }
                Object[] objectArray3 = new Object[n2];
                switch (n) {
                    default: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "owner";
                        break;
                    }
                    case 1: 
                    case 3: 
                    case 5: 
                    case 7: 
                    case 8: 
                    case 10: 
                    case 12: 
                    case 14: 
                    case 16: 
                    case 18: 
                    case 20: 
                    case 21: 
                    case 22: 
                    case 23: 
                    case 24: 
                    case 25: 
                    case 26: 
                    case 27: 
                    case 28: 
                    case 30: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "kotlin/reflect/jvm/internal/impl/types/error/ErrorSimpleFunctionDescriptorImpl$1";
                        break;
                    }
                    case 2: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "modality";
                        break;
                    }
                    case 4: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "visibility";
                        break;
                    }
                    case 6: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "kind";
                        break;
                    }
                    case 9: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "name";
                        break;
                    }
                    case 11: 
                    case 17: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "parameters";
                        break;
                    }
                    case 13: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "substitution";
                        break;
                    }
                    case 15: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "userDataKey";
                        break;
                    }
                    case 19: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "type";
                        break;
                    }
                    case 29: {
                        objectArray2 = objectArray3;
                        objectArray3[0] = "additionalAnnotations";
                        break;
                    }
                }
                switch (n) {
                    default: {
                        objectArray = objectArray2;
                        objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/error/ErrorSimpleFunctionDescriptorImpl$1";
                        break;
                    }
                    case 1: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setOwner";
                        break;
                    }
                    case 3: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setModality";
                        break;
                    }
                    case 5: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setVisibility";
                        break;
                    }
                    case 7: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setKind";
                        break;
                    }
                    case 8: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setCopyOverrides";
                        break;
                    }
                    case 10: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setName";
                        break;
                    }
                    case 12: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setValueParameters";
                        break;
                    }
                    case 14: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setSubstitution";
                        break;
                    }
                    case 16: {
                        objectArray = objectArray2;
                        objectArray2[1] = "putUserData";
                        break;
                    }
                    case 18: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setTypeParameters";
                        break;
                    }
                    case 20: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setReturnType";
                        break;
                    }
                    case 21: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setExtensionReceiverParameter";
                        break;
                    }
                    case 22: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setDispatchReceiverParameter";
                        break;
                    }
                    case 23: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setOriginal";
                        break;
                    }
                    case 24: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setSignatureChange";
                        break;
                    }
                    case 25: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setPreserveSourceElement";
                        break;
                    }
                    case 26: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setDropOriginalInContainingParts";
                        break;
                    }
                    case 27: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setHiddenToOvercomeSignatureClash";
                        break;
                    }
                    case 28: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setHiddenForResolutionEverywhereBesideSupercalls";
                        break;
                    }
                    case 30: {
                        objectArray = objectArray2;
                        objectArray2[1] = "setAdditionalAnnotations";
                        break;
                    }
                }
                switch (n) {
                    default: {
                        objectArray = objectArray;
                        objectArray[2] = "setOwner";
                        break;
                    }
                    case 1: 
                    case 3: 
                    case 5: 
                    case 7: 
                    case 8: 
                    case 10: 
                    case 12: 
                    case 14: 
                    case 16: 
                    case 18: 
                    case 20: 
                    case 21: 
                    case 22: 
                    case 23: 
                    case 24: 
                    case 25: 
                    case 26: 
                    case 27: 
                    case 28: 
                    case 30: {
                        break;
                    }
                    case 2: {
                        objectArray = objectArray;
                        objectArray[2] = "setModality";
                        break;
                    }
                    case 4: {
                        objectArray = objectArray;
                        objectArray[2] = "setVisibility";
                        break;
                    }
                    case 6: {
                        objectArray = objectArray;
                        objectArray[2] = "setKind";
                        break;
                    }
                    case 9: {
                        objectArray = objectArray;
                        objectArray[2] = "setName";
                        break;
                    }
                    case 11: {
                        objectArray = objectArray;
                        objectArray[2] = "setValueParameters";
                        break;
                    }
                    case 13: {
                        objectArray = objectArray;
                        objectArray[2] = "setSubstitution";
                        break;
                    }
                    case 15: {
                        objectArray = objectArray;
                        objectArray[2] = "putUserData";
                        break;
                    }
                    case 17: {
                        objectArray = objectArray;
                        objectArray[2] = "setTypeParameters";
                        break;
                    }
                    case 19: {
                        objectArray = objectArray;
                        objectArray[2] = "setReturnType";
                        break;
                    }
                    case 29: {
                        objectArray = objectArray;
                        objectArray[2] = "setAdditionalAnnotations";
                        break;
                    }
                }
                String string2 = String.format(string, objectArray);
                switch (n) {
                    default: {
                        runtimeException = new IllegalArgumentException(string2);
                        break;
                    }
                    case 1: 
                    case 3: 
                    case 5: 
                    case 7: 
                    case 8: 
                    case 10: 
                    case 12: 
                    case 14: 
                    case 16: 
                    case 18: 
                    case 20: 
                    case 21: 
                    case 22: 
                    case 23: 
                    case 24: 
                    case 25: 
                    case 26: 
                    case 27: 
                    case 28: 
                    case 30: {
                        runtimeException = new IllegalStateException(string2);
                        break;
                    }
                }
                throw runtimeException;
            }
        };
    }

    @Override
    public boolean isSuspend() {
        return false;
    }

    @Override
    public <V> V getUserData(CallableDescriptor.UserDataKey<V> key) {
        return null;
    }

    @Override
    public void setOverriddenDescriptors(@NotNull Collection<? extends CallableMemberDescriptor> overriddenDescriptors) {
        if (overriddenDescriptors == null) {
            ErrorSimpleFunctionDescriptorImpl.$$$reportNull$$$0(8);
        }
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
            case 6: 
            case 7: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 6: 
            case 7: {
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
                objectArray3[0] = "ownerScope";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "newOwner";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kind";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "annotations";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "source";
                break;
            }
            case 6: 
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/types/error/ErrorSimpleFunctionDescriptorImpl";
                break;
            }
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "overriddenDescriptors";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/error/ErrorSimpleFunctionDescriptorImpl";
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "createSubstitutedCopy";
                break;
            }
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "copy";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 2: 
            case 3: 
            case 4: 
            case 5: {
                objectArray = objectArray;
                objectArray[2] = "createSubstitutedCopy";
                break;
            }
            case 6: 
            case 7: {
                break;
            }
            case 8: {
                objectArray = objectArray;
                objectArray[2] = "setOverriddenDescriptors";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 6: 
            case 7: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

