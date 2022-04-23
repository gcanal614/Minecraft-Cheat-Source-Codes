/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckerProcedureCallbacksImpl;
import kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckingProcedure;
import org.jetbrains.annotations.NotNull;

public class KotlinTypeCheckerImpl
implements KotlinTypeChecker {
    private final TypeCheckingProcedure procedure;

    @NotNull
    public static KotlinTypeChecker withAxioms(final @NotNull KotlinTypeChecker.TypeConstructorEquality equalityAxioms) {
        if (equalityAxioms == null) {
            KotlinTypeCheckerImpl.$$$reportNull$$$0(0);
        }
        return new KotlinTypeCheckerImpl(new TypeCheckingProcedure(new TypeCheckerProcedureCallbacksImpl(){

            @Override
            public boolean assertEqualTypeConstructors(@NotNull TypeConstructor constructor1, @NotNull TypeConstructor constructor2) {
                if (constructor1 == null) {
                    1.$$$reportNull$$$0(0);
                }
                if (constructor2 == null) {
                    1.$$$reportNull$$$0(1);
                }
                return constructor1.equals(constructor2) || equalityAxioms.equals(constructor1, constructor2);
            }

            private static /* synthetic */ void $$$reportNull$$$0(int n) {
                Object[] objectArray;
                Object[] objectArray2 = new Object[3];
                switch (n) {
                    default: {
                        objectArray = objectArray2;
                        objectArray2[0] = "constructor1";
                        break;
                    }
                    case 1: {
                        objectArray = objectArray2;
                        objectArray2[0] = "constructor2";
                        break;
                    }
                }
                objectArray[1] = "kotlin/reflect/jvm/internal/impl/types/checker/KotlinTypeCheckerImpl$1";
                objectArray[2] = "assertEqualTypeConstructors";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
            }
        }));
    }

    protected KotlinTypeCheckerImpl(@NotNull TypeCheckingProcedure procedure) {
        if (procedure == null) {
            KotlinTypeCheckerImpl.$$$reportNull$$$0(1);
        }
        this.procedure = procedure;
    }

    @Override
    public boolean isSubtypeOf(@NotNull KotlinType subtype, @NotNull KotlinType supertype) {
        if (subtype == null) {
            KotlinTypeCheckerImpl.$$$reportNull$$$0(2);
        }
        if (supertype == null) {
            KotlinTypeCheckerImpl.$$$reportNull$$$0(3);
        }
        return this.procedure.isSubtypeOf(subtype, supertype);
    }

    @Override
    public boolean equalTypes(@NotNull KotlinType a2, @NotNull KotlinType b2) {
        if (a2 == null) {
            KotlinTypeCheckerImpl.$$$reportNull$$$0(4);
        }
        if (b2 == null) {
            KotlinTypeCheckerImpl.$$$reportNull$$$0(5);
        }
        return this.procedure.equalTypes(a2, b2);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[3];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "equalityAxioms";
                break;
            }
            case 1: {
                objectArray2 = objectArray3;
                objectArray3[0] = "procedure";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "subtype";
                break;
            }
            case 3: {
                objectArray2 = objectArray3;
                objectArray3[0] = "supertype";
                break;
            }
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "a";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "b";
                break;
            }
        }
        objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/checker/KotlinTypeCheckerImpl";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = "withAxioms";
                break;
            }
            case 1: {
                objectArray = objectArray2;
                objectArray2[2] = "<init>";
                break;
            }
            case 2: 
            case 3: {
                objectArray = objectArray2;
                objectArray2[2] = "isSubtypeOf";
                break;
            }
            case 4: 
            case 5: {
                objectArray = objectArray2;
                objectArray2[2] = "equalTypes";
                break;
            }
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }
}

