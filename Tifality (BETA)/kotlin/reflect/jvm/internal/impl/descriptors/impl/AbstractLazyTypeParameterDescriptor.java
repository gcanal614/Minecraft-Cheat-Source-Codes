/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractTypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractLazyTypeParameterDescriptor
extends AbstractTypeParameterDescriptor {
    public AbstractLazyTypeParameterDescriptor(@NotNull StorageManager storageManager, @NotNull DeclarationDescriptor containingDeclaration, @NotNull Name name, @NotNull Variance variance, boolean isReified, int index, @NotNull SourceElement source, @NotNull SupertypeLoopChecker supertypeLoopChecker) {
        if (storageManager == null) {
            AbstractLazyTypeParameterDescriptor.$$$reportNull$$$0(0);
        }
        if (containingDeclaration == null) {
            AbstractLazyTypeParameterDescriptor.$$$reportNull$$$0(1);
        }
        if (name == null) {
            AbstractLazyTypeParameterDescriptor.$$$reportNull$$$0(2);
        }
        if (variance == null) {
            AbstractLazyTypeParameterDescriptor.$$$reportNull$$$0(3);
        }
        if (source == null) {
            AbstractLazyTypeParameterDescriptor.$$$reportNull$$$0(4);
        }
        if (supertypeLoopChecker == null) {
            AbstractLazyTypeParameterDescriptor.$$$reportNull$$$0(5);
        }
        super(storageManager, containingDeclaration, Annotations.Companion.getEMPTY(), name, variance, isReified, index, source, supertypeLoopChecker);
    }

    @Override
    public String toString() {
        return String.format("%s%s%s", this.isReified() ? "reified " : "", this.getVariance() == Variance.INVARIANT ? "" : (Object)((Object)this.getVariance()) + " ", this.getName());
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2 = new Object[3];
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[0] = "storageManager";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[0] = "containingDeclaration";
                break;
            }
            case 2: {
                objectArray = objectArray2;
                objectArray2[0] = "name";
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[0] = "variance";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[0] = "source";
                break;
            }
            case 5: {
                objectArray = objectArray2;
                objectArray2[0] = "supertypeLoopChecker";
                break;
            }
        }
        objectArray[1] = "kotlin/reflect/jvm/internal/impl/descriptors/impl/AbstractLazyTypeParameterDescriptor";
        objectArray[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }
}

