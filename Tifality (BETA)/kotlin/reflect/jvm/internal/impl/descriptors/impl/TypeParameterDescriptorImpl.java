/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractTypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TypeParameterDescriptorImpl
extends AbstractTypeParameterDescriptor {
    @Nullable
    private final Function1<KotlinType, Void> reportCycleError;
    private final List<KotlinType> upperBounds;
    private boolean initialized;

    @NotNull
    public static TypeParameterDescriptor createWithDefaultBound(@NotNull DeclarationDescriptor containingDeclaration, @NotNull Annotations annotations2, boolean reified, @NotNull Variance variance, @NotNull Name name, int index, @NotNull StorageManager storageManager) {
        if (containingDeclaration == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(0);
        }
        if (annotations2 == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(1);
        }
        if (variance == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(2);
        }
        if (name == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(3);
        }
        if (storageManager == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(4);
        }
        TypeParameterDescriptorImpl typeParameterDescriptor = TypeParameterDescriptorImpl.createForFurtherModification(containingDeclaration, annotations2, reified, variance, name, index, SourceElement.NO_SOURCE, storageManager);
        typeParameterDescriptor.addUpperBound(DescriptorUtilsKt.getBuiltIns(containingDeclaration).getDefaultBound());
        typeParameterDescriptor.setInitialized();
        TypeParameterDescriptorImpl typeParameterDescriptorImpl = typeParameterDescriptor;
        if (typeParameterDescriptorImpl == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(5);
        }
        return typeParameterDescriptorImpl;
    }

    public static TypeParameterDescriptorImpl createForFurtherModification(@NotNull DeclarationDescriptor containingDeclaration, @NotNull Annotations annotations2, boolean reified, @NotNull Variance variance, @NotNull Name name, int index, @NotNull SourceElement source, @NotNull StorageManager storageManager) {
        if (containingDeclaration == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(6);
        }
        if (annotations2 == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(7);
        }
        if (variance == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(8);
        }
        if (name == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(9);
        }
        if (source == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(10);
        }
        if (storageManager == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(11);
        }
        return TypeParameterDescriptorImpl.createForFurtherModification(containingDeclaration, annotations2, reified, variance, name, index, source, null, SupertypeLoopChecker.EMPTY.INSTANCE, storageManager);
    }

    public static TypeParameterDescriptorImpl createForFurtherModification(@NotNull DeclarationDescriptor containingDeclaration, @NotNull Annotations annotations2, boolean reified, @NotNull Variance variance, @NotNull Name name, int index, @NotNull SourceElement source, @Nullable Function1<KotlinType, Void> reportCycleError, @NotNull SupertypeLoopChecker supertypeLoopsResolver, @NotNull StorageManager storageManager) {
        if (containingDeclaration == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(12);
        }
        if (annotations2 == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(13);
        }
        if (variance == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(14);
        }
        if (name == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(15);
        }
        if (source == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(16);
        }
        if (supertypeLoopsResolver == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(17);
        }
        if (storageManager == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(18);
        }
        return new TypeParameterDescriptorImpl(containingDeclaration, annotations2, reified, variance, name, index, source, reportCycleError, supertypeLoopsResolver, storageManager);
    }

    private TypeParameterDescriptorImpl(@NotNull DeclarationDescriptor containingDeclaration, @NotNull Annotations annotations2, boolean reified, @NotNull Variance variance, @NotNull Name name, int index, @NotNull SourceElement source, @Nullable Function1<KotlinType, Void> reportCycleError, @NotNull SupertypeLoopChecker supertypeLoopsChecker, @NotNull StorageManager storageManager) {
        if (containingDeclaration == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(19);
        }
        if (annotations2 == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(20);
        }
        if (variance == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(21);
        }
        if (name == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(22);
        }
        if (source == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(23);
        }
        if (supertypeLoopsChecker == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(24);
        }
        if (storageManager == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(25);
        }
        super(storageManager, containingDeclaration, annotations2, name, variance, reified, index, source, supertypeLoopsChecker);
        this.upperBounds = new ArrayList<KotlinType>(1);
        this.initialized = false;
        this.reportCycleError = reportCycleError;
    }

    private void checkInitialized() {
        if (!this.initialized) {
            throw new IllegalStateException("Type parameter descriptor is not initialized: " + this.nameForAssertions());
        }
    }

    private void checkUninitialized() {
        if (this.initialized) {
            throw new IllegalStateException("Type parameter descriptor is already initialized: " + this.nameForAssertions());
        }
    }

    private String nameForAssertions() {
        return this.getName() + " declared in " + DescriptorUtils.getFqName(this.getContainingDeclaration());
    }

    public void setInitialized() {
        this.checkUninitialized();
        this.initialized = true;
    }

    public void addUpperBound(@NotNull KotlinType bound) {
        if (bound == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(26);
        }
        this.checkUninitialized();
        this.doAddUpperBound(bound);
    }

    private void doAddUpperBound(KotlinType bound) {
        if (KotlinTypeKt.isError(bound)) {
            return;
        }
        this.upperBounds.add(bound);
    }

    @Override
    protected void reportSupertypeLoopError(@NotNull KotlinType type2) {
        if (type2 == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(27);
        }
        if (this.reportCycleError == null) {
            return;
        }
        this.reportCycleError.invoke(type2);
    }

    @Override
    @NotNull
    protected List<KotlinType> resolveUpperBounds() {
        this.checkInitialized();
        List<KotlinType> list = this.upperBounds;
        if (list == null) {
            TypeParameterDescriptorImpl.$$$reportNull$$$0(28);
        }
        return list;
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
            case 28: {
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
            case 28: {
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
            case 1: 
            case 7: 
            case 13: 
            case 20: {
                objectArray2 = objectArray3;
                objectArray3[0] = "annotations";
                break;
            }
            case 2: 
            case 8: 
            case 14: 
            case 21: {
                objectArray2 = objectArray3;
                objectArray3[0] = "variance";
                break;
            }
            case 3: 
            case 9: 
            case 15: 
            case 22: {
                objectArray2 = objectArray3;
                objectArray3[0] = "name";
                break;
            }
            case 4: 
            case 11: 
            case 18: 
            case 25: {
                objectArray2 = objectArray3;
                objectArray3[0] = "storageManager";
                break;
            }
            case 5: 
            case 28: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/TypeParameterDescriptorImpl";
                break;
            }
            case 10: 
            case 16: 
            case 23: {
                objectArray2 = objectArray3;
                objectArray3[0] = "source";
                break;
            }
            case 17: {
                objectArray2 = objectArray3;
                objectArray3[0] = "supertypeLoopsResolver";
                break;
            }
            case 24: {
                objectArray2 = objectArray3;
                objectArray3[0] = "supertypeLoopsChecker";
                break;
            }
            case 26: {
                objectArray2 = objectArray3;
                objectArray3[0] = "bound";
                break;
            }
            case 27: {
                objectArray2 = objectArray3;
                objectArray3[0] = "type";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/TypeParameterDescriptorImpl";
                break;
            }
            case 5: {
                objectArray = objectArray2;
                objectArray2[1] = "createWithDefaultBound";
                break;
            }
            case 28: {
                objectArray = objectArray2;
                objectArray2[1] = "resolveUpperBounds";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "createWithDefaultBound";
                break;
            }
            case 5: 
            case 28: {
                break;
            }
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: {
                objectArray = objectArray;
                objectArray[2] = "createForFurtherModification";
                break;
            }
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: 
            case 24: 
            case 25: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 26: {
                objectArray = objectArray;
                objectArray[2] = "addUpperBound";
                break;
            }
            case 27: {
                objectArray = objectArray;
                objectArray[2] = "reportSupertypeLoopError";
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
            case 28: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

