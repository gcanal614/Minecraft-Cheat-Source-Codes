/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.utils.SmartList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractClassTypeConstructor
extends AbstractTypeConstructor
implements TypeConstructor {
    private int hashCode;

    public AbstractClassTypeConstructor(@NotNull StorageManager storageManager) {
        if (storageManager == null) {
            AbstractClassTypeConstructor.$$$reportNull$$$0(0);
        }
        super(storageManager);
        this.hashCode = 0;
    }

    public final int hashCode() {
        int currentHashCode = this.hashCode;
        if (currentHashCode != 0) {
            return currentHashCode;
        }
        ClassDescriptor descriptor2 = this.getDeclarationDescriptor();
        currentHashCode = AbstractClassTypeConstructor.hasMeaningfulFqName(descriptor2) ? DescriptorUtils.getFqName(descriptor2).hashCode() : System.identityHashCode(this);
        this.hashCode = currentHashCode;
        return currentHashCode;
    }

    @Override
    @NotNull
    public abstract ClassDescriptor getDeclarationDescriptor();

    @Override
    @NotNull
    public KotlinBuiltIns getBuiltIns() {
        KotlinBuiltIns kotlinBuiltIns = DescriptorUtilsKt.getBuiltIns(this.getDeclarationDescriptor());
        if (kotlinBuiltIns == null) {
            AbstractClassTypeConstructor.$$$reportNull$$$0(1);
        }
        return kotlinBuiltIns;
    }

    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TypeConstructor)) {
            return false;
        }
        if (other.hashCode() != this.hashCode()) {
            return false;
        }
        if (((TypeConstructor)other).getParameters().size() != this.getParameters().size()) {
            return false;
        }
        ClassDescriptor myDescriptor = this.getDeclarationDescriptor();
        ClassifierDescriptor otherDescriptor = ((TypeConstructor)other).getDeclarationDescriptor();
        if (!AbstractClassTypeConstructor.hasMeaningfulFqName(myDescriptor) || otherDescriptor != null && !AbstractClassTypeConstructor.hasMeaningfulFqName(otherDescriptor)) {
            return false;
        }
        if (otherDescriptor instanceof ClassDescriptor) {
            return AbstractClassTypeConstructor.areFqNamesEqual(myDescriptor, (ClassDescriptor)otherDescriptor);
        }
        return false;
    }

    private static boolean areFqNamesEqual(ClassDescriptor first, ClassDescriptor second) {
        if (!first.getName().equals(second.getName())) {
            return false;
        }
        DeclarationDescriptor a2 = first.getContainingDeclaration();
        for (DeclarationDescriptor b2 = second.getContainingDeclaration(); a2 != null && b2 != null; a2 = a2.getContainingDeclaration(), b2 = b2.getContainingDeclaration()) {
            if (a2 instanceof ModuleDescriptor) {
                return b2 instanceof ModuleDescriptor;
            }
            if (b2 instanceof ModuleDescriptor) {
                return false;
            }
            if (a2 instanceof PackageFragmentDescriptor) {
                return b2 instanceof PackageFragmentDescriptor && ((PackageFragmentDescriptor)a2).getFqName().equals(((PackageFragmentDescriptor)b2).getFqName());
            }
            if (b2 instanceof PackageFragmentDescriptor) {
                return false;
            }
            if (a2.getName().equals(b2.getName())) continue;
            return false;
        }
        return true;
    }

    private static boolean hasMeaningfulFqName(@NotNull ClassifierDescriptor descriptor2) {
        if (descriptor2 == null) {
            AbstractClassTypeConstructor.$$$reportNull$$$0(2);
        }
        return !ErrorUtils.isError(descriptor2) && !DescriptorUtils.isLocal(descriptor2);
    }

    @Override
    @NotNull
    protected Collection<KotlinType> getAdditionalNeighboursInSupertypeGraph(boolean useCompanions) {
        DeclarationDescriptor containingDeclaration = this.getDeclarationDescriptor().getContainingDeclaration();
        if (!(containingDeclaration instanceof ClassDescriptor)) {
            List<KotlinType> list = Collections.emptyList();
            if (list == null) {
                AbstractClassTypeConstructor.$$$reportNull$$$0(3);
            }
            return list;
        }
        SmartList<KotlinType> additionalNeighbours = new SmartList<KotlinType>();
        ClassDescriptor containingClassDescriptor = (ClassDescriptor)containingDeclaration;
        additionalNeighbours.add(containingClassDescriptor.getDefaultType());
        ClassDescriptor companion = containingClassDescriptor.getCompanionObjectDescriptor();
        if (useCompanions && companion != null) {
            additionalNeighbours.add(companion.getDefaultType());
        }
        SmartList<KotlinType> smartList = additionalNeighbours;
        if (smartList == null) {
            AbstractClassTypeConstructor.$$$reportNull$$$0(4);
        }
        return smartList;
    }

    @Override
    @Nullable
    protected KotlinType defaultSupertypeIfEmpty() {
        if (KotlinBuiltIns.isSpecialClassWithNoSupertypes(this.getDeclarationDescriptor())) {
            return null;
        }
        return this.getBuiltIns().getAnyType();
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
            case 1: 
            case 3: 
            case 4: {
                n2 = 2;
                break;
            }
        }
        Object[] objectArray3 = new Object[n2];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "storageManager";
                break;
            }
            case 1: 
            case 3: 
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/types/AbstractClassTypeConstructor";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "descriptor";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/AbstractClassTypeConstructor";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[1] = "getBuiltIns";
                break;
            }
            case 3: 
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "getAdditionalNeighboursInSupertypeGraph";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 1: 
            case 3: 
            case 4: {
                break;
            }
            case 2: {
                objectArray = objectArray;
                objectArray[2] = "hasMeaningfulFqName";
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
            case 4: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

