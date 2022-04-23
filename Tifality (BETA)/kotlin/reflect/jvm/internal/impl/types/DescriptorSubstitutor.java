/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.HashMap;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.TypeParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DescriptorSubstitutor {
    @NotNull
    public static TypeSubstitutor substituteTypeParameters(@NotNull List<TypeParameterDescriptor> typeParameters2, @NotNull TypeSubstitution originalSubstitution, @NotNull DeclarationDescriptor newContainingDeclaration, @NotNull List<TypeParameterDescriptor> result2) {
        TypeSubstitutor substitutor;
        if (typeParameters2 == null) {
            DescriptorSubstitutor.$$$reportNull$$$0(0);
        }
        if (originalSubstitution == null) {
            DescriptorSubstitutor.$$$reportNull$$$0(1);
        }
        if (newContainingDeclaration == null) {
            DescriptorSubstitutor.$$$reportNull$$$0(2);
        }
        if (result2 == null) {
            DescriptorSubstitutor.$$$reportNull$$$0(3);
        }
        if ((substitutor = DescriptorSubstitutor.substituteTypeParameters(typeParameters2, originalSubstitution, newContainingDeclaration, result2, null)) == null) {
            throw new AssertionError((Object)"Substitution failed");
        }
        TypeSubstitutor typeSubstitutor2 = substitutor;
        if (typeSubstitutor2 == null) {
            DescriptorSubstitutor.$$$reportNull$$$0(4);
        }
        return typeSubstitutor2;
    }

    @Nullable
    public static TypeSubstitutor substituteTypeParameters(@NotNull List<TypeParameterDescriptor> typeParameters2, @NotNull TypeSubstitution originalSubstitution, @NotNull DeclarationDescriptor newContainingDeclaration, @NotNull List<TypeParameterDescriptor> result2, @Nullable boolean[] wereChanges) {
        if (typeParameters2 == null) {
            DescriptorSubstitutor.$$$reportNull$$$0(5);
        }
        if (originalSubstitution == null) {
            DescriptorSubstitutor.$$$reportNull$$$0(6);
        }
        if (newContainingDeclaration == null) {
            DescriptorSubstitutor.$$$reportNull$$$0(7);
        }
        if (result2 == null) {
            DescriptorSubstitutor.$$$reportNull$$$0(8);
        }
        HashMap<TypeConstructor, TypeProjectionImpl> mutableSubstitution = new HashMap<TypeConstructor, TypeProjectionImpl>();
        HashMap<TypeParameterDescriptor, TypeParameterDescriptorImpl> substitutedMap = new HashMap<TypeParameterDescriptor, TypeParameterDescriptorImpl>();
        int index = 0;
        for (TypeParameterDescriptor descriptor2 : typeParameters2) {
            TypeParameterDescriptorImpl substituted = TypeParameterDescriptorImpl.createForFurtherModification(newContainingDeclaration, descriptor2.getAnnotations(), descriptor2.isReified(), descriptor2.getVariance(), descriptor2.getName(), index++, SourceElement.NO_SOURCE, descriptor2.getStorageManager());
            mutableSubstitution.put(descriptor2.getTypeConstructor(), new TypeProjectionImpl(substituted.getDefaultType()));
            substitutedMap.put(descriptor2, substituted);
            result2.add(substituted);
        }
        TypeSubstitutor substitutor = TypeSubstitutor.createChainedSubstitutor(originalSubstitution, TypeConstructorSubstitution.createByConstructorsMap(mutableSubstitution));
        for (TypeParameterDescriptor descriptor3 : typeParameters2) {
            TypeParameterDescriptorImpl substituted = (TypeParameterDescriptorImpl)substitutedMap.get(descriptor3);
            for (KotlinType upperBound : descriptor3.getUpperBounds()) {
                KotlinType substitutedBound = substitutor.substitute(upperBound, Variance.IN_VARIANCE);
                if (substitutedBound == null) {
                    return null;
                }
                if (substitutedBound != upperBound && wereChanges != null) {
                    wereChanges[0] = true;
                }
                substituted.addUpperBound(substitutedBound);
            }
            substituted.setInitialized();
        }
        return substitutor;
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
            case 4: {
                string = "@NotNull method %s.%s must not return null";
                break;
            }
        }
        switch (n) {
            default: {
                n2 = 3;
                break;
            }
            case 4: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeParameters";
                break;
            }
            case 1: 
            case 6: {
                objectArray2 = objectArray3;
                objectArray3[0] = "originalSubstitution";
                break;
            }
            case 2: 
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "newContainingDeclaration";
                break;
            }
            case 3: 
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "result";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/types/DescriptorSubstitutor";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/DescriptorSubstitutor";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "substituteTypeParameters";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "substituteTypeParameters";
                break;
            }
            case 4: {
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 4: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

