/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionBase;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

public class TypeProjectionImpl
extends TypeProjectionBase {
    private final Variance projection;
    private final KotlinType type;

    public TypeProjectionImpl(@NotNull Variance projection, @NotNull KotlinType type2) {
        if (projection == null) {
            TypeProjectionImpl.$$$reportNull$$$0(0);
        }
        if (type2 == null) {
            TypeProjectionImpl.$$$reportNull$$$0(1);
        }
        this.projection = projection;
        this.type = type2;
    }

    public TypeProjectionImpl(@NotNull KotlinType type2) {
        if (type2 == null) {
            TypeProjectionImpl.$$$reportNull$$$0(2);
        }
        this(Variance.INVARIANT, type2);
    }

    @Override
    @NotNull
    public Variance getProjectionKind() {
        Variance variance = this.projection;
        if (variance == null) {
            TypeProjectionImpl.$$$reportNull$$$0(3);
        }
        return variance;
    }

    @Override
    @NotNull
    public KotlinType getType() {
        KotlinType kotlinType = this.type;
        if (kotlinType == null) {
            TypeProjectionImpl.$$$reportNull$$$0(4);
        }
        return kotlinType;
    }

    @Override
    public boolean isStarProjection() {
        return false;
    }

    @Override
    @NotNull
    public TypeProjection refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        if (kotlinTypeRefiner == null) {
            TypeProjectionImpl.$$$reportNull$$$0(5);
        }
        return new TypeProjectionImpl(this.projection, kotlinTypeRefiner.refineType(this.type));
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
                objectArray3[0] = "projection";
                break;
            }
            case 1: 
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "type";
                break;
            }
            case 3: 
            case 4: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlin/reflect/jvm/internal/impl/types/TypeProjectionImpl";
                break;
            }
            case 5: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kotlinTypeRefiner";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[1] = "kotlin/reflect/jvm/internal/impl/types/TypeProjectionImpl";
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[1] = "getProjectionKind";
                break;
            }
            case 4: {
                objectArray = objectArray2;
                objectArray2[1] = "getType";
                break;
            }
        }
        switch (n) {
            default: {
                objectArray = objectArray;
                objectArray[2] = "<init>";
                break;
            }
            case 3: 
            case 4: {
                break;
            }
            case 5: {
                objectArray = objectArray;
                objectArray[2] = "refine";
                break;
            }
        }
        String string2 = String.format(string, objectArray);
        switch (n) {
            default: {
                runtimeException = new IllegalArgumentException(string2);
                break;
            }
            case 3: 
            case 4: {
                runtimeException = new IllegalStateException(string2);
                break;
            }
        }
        throw runtimeException;
    }
}

