/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckingProcedure;
import kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckingProcedureCallbacks;
import org.jetbrains.annotations.NotNull;

class TypeCheckerProcedureCallbacksImpl
implements TypeCheckingProcedureCallbacks {
    TypeCheckerProcedureCallbacksImpl() {
    }

    @Override
    public boolean assertEqualTypes(@NotNull KotlinType a2, @NotNull KotlinType b2, @NotNull TypeCheckingProcedure typeCheckingProcedure) {
        if (a2 == null) {
            TypeCheckerProcedureCallbacksImpl.$$$reportNull$$$0(0);
        }
        if (b2 == null) {
            TypeCheckerProcedureCallbacksImpl.$$$reportNull$$$0(1);
        }
        if (typeCheckingProcedure == null) {
            TypeCheckerProcedureCallbacksImpl.$$$reportNull$$$0(2);
        }
        return typeCheckingProcedure.equalTypes(a2, b2);
    }

    @Override
    public boolean assertEqualTypeConstructors(@NotNull TypeConstructor a2, @NotNull TypeConstructor b2) {
        if (a2 == null) {
            TypeCheckerProcedureCallbacksImpl.$$$reportNull$$$0(3);
        }
        if (b2 == null) {
            TypeCheckerProcedureCallbacksImpl.$$$reportNull$$$0(4);
        }
        return a2.equals(b2);
    }

    @Override
    public boolean assertSubtype(@NotNull KotlinType subtype, @NotNull KotlinType supertype, @NotNull TypeCheckingProcedure typeCheckingProcedure) {
        if (subtype == null) {
            TypeCheckerProcedureCallbacksImpl.$$$reportNull$$$0(5);
        }
        if (supertype == null) {
            TypeCheckerProcedureCallbacksImpl.$$$reportNull$$$0(6);
        }
        if (typeCheckingProcedure == null) {
            TypeCheckerProcedureCallbacksImpl.$$$reportNull$$$0(7);
        }
        return typeCheckingProcedure.isSubtypeOf(subtype, supertype);
    }

    @Override
    public boolean capture(@NotNull KotlinType type2, @NotNull TypeProjection typeProjection) {
        if (type2 == null) {
            TypeCheckerProcedureCallbacksImpl.$$$reportNull$$$0(8);
        }
        if (typeProjection == null) {
            TypeCheckerProcedureCallbacksImpl.$$$reportNull$$$0(9);
        }
        return false;
    }

    @Override
    public boolean noCorrespondingSupertype(@NotNull KotlinType subtype, @NotNull KotlinType supertype) {
        if (subtype == null) {
            TypeCheckerProcedureCallbacksImpl.$$$reportNull$$$0(10);
        }
        if (supertype == null) {
            TypeCheckerProcedureCallbacksImpl.$$$reportNull$$$0(11);
        }
        return false;
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[3];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "a";
                break;
            }
            case 1: 
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "b";
                break;
            }
            case 2: 
            case 7: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeCheckingProcedure";
                break;
            }
            case 5: 
            case 10: {
                objectArray2 = objectArray3;
                objectArray3[0] = "subtype";
                break;
            }
            case 6: 
            case 11: {
                objectArray2 = objectArray3;
                objectArray3[0] = "supertype";
                break;
            }
            case 8: {
                objectArray2 = objectArray3;
                objectArray3[0] = "type";
                break;
            }
            case 9: {
                objectArray2 = objectArray3;
                objectArray3[0] = "typeProjection";
                break;
            }
        }
        objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/checker/TypeCheckerProcedureCallbacksImpl";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = "assertEqualTypes";
                break;
            }
            case 3: 
            case 4: {
                objectArray = objectArray2;
                objectArray2[2] = "assertEqualTypeConstructors";
                break;
            }
            case 5: 
            case 6: 
            case 7: {
                objectArray = objectArray2;
                objectArray2[2] = "assertSubtype";
                break;
            }
            case 8: 
            case 9: {
                objectArray = objectArray2;
                objectArray2[2] = "capture";
                break;
            }
            case 10: 
            case 11: {
                objectArray = objectArray2;
                objectArray2[2] = "noCorrespondingSupertype";
                break;
            }
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }
}

