/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.AbstractClassTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import org.jetbrains.annotations.NotNull;

public class ClassTypeConstructorImpl
extends AbstractClassTypeConstructor
implements TypeConstructor {
    private final ClassDescriptor classDescriptor;
    private final List<TypeParameterDescriptor> parameters;
    private final Collection<KotlinType> supertypes;

    public ClassTypeConstructorImpl(@NotNull ClassDescriptor classDescriptor, @NotNull List<? extends TypeParameterDescriptor> parameters2, @NotNull Collection<KotlinType> supertypes2, @NotNull StorageManager storageManager) {
        if (classDescriptor == null) {
            ClassTypeConstructorImpl.$$$reportNull$$$0(0);
        }
        if (parameters2 == null) {
            ClassTypeConstructorImpl.$$$reportNull$$$0(1);
        }
        if (supertypes2 == null) {
            ClassTypeConstructorImpl.$$$reportNull$$$0(2);
        }
        if (storageManager == null) {
            ClassTypeConstructorImpl.$$$reportNull$$$0(3);
        }
        super(storageManager);
        this.classDescriptor = classDescriptor;
        this.parameters = Collections.unmodifiableList(new ArrayList<TypeParameterDescriptor>(parameters2));
        this.supertypes = Collections.unmodifiableCollection(supertypes2);
    }

    @Override
    @NotNull
    public List<TypeParameterDescriptor> getParameters() {
        List<TypeParameterDescriptor> list = this.parameters;
        if (list == null) {
            ClassTypeConstructorImpl.$$$reportNull$$$0(4);
        }
        return list;
    }

    public String toString() {
        return DescriptorUtils.getFqName(this.classDescriptor).asString();
    }

    @Override
    public boolean isDenotable() {
        return true;
    }

    @Override
    @NotNull
    public ClassDescriptor getDeclarationDescriptor() {
        ClassDescriptor classDescriptor = this.classDescriptor;
        if (classDescriptor == null) {
            ClassTypeConstructorImpl.$$$reportNull$$$0(5);
        }
        return classDescriptor;
    }

    @Override
    @NotNull
    protected Collection<KotlinType> computeSupertypes() {
        Collection<KotlinType> collection = this.supertypes;
        if (collection == null) {
            ClassTypeConstructorImpl.$$$reportNull$$$0(6);
        }
        return collection;
    }

    @Override
    @NotNull
    protected SupertypeLoopChecker getSupertypeLoopChecker() {
        SupertypeLoopChecker.EMPTY eMPTY = SupertypeLoopChecker.EMPTY.INSTANCE;
        if (eMPTY == null) {
            ClassTypeConstructorImpl.$$$reportNull$$$0(7);
        }
        return eMPTY;
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
            case 4: 
            case 5: 
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
            case 4: 
            case 5: 
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
                objectArray3[0] = "classDescriptor";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "parameters";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "supertypes";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "storageManager";
                break;
            }
            case 4: 
            case 5: 
            case 6: 
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/types/ClassTypeConstructorImpl";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/ClassTypeConstructorImpl";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "getParameters";
                break;
            }
            case 5: {
                objectArray = objectArray2;
                objectArray2[1] = "getDeclarationDescriptor";
                break;
            }
            case 6: {
                objectArray = objectArray2;
                objectArray2[1] = "computeSupertypes";
                break;
            }
            case 7: {
                objectArray = objectArray2;
                objectArray2[1] = "getSupertypeLoopChecker";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 4: 
            case 5: 
            case 6: 
            case 7: {
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 4: 
            case 5: 
            case 6: 
            case 7: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

