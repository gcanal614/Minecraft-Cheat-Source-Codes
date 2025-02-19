/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeCapture;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import javax.annotation.Nullable;

@Beta
public abstract class TypeParameter<T>
extends TypeCapture<T> {
    final TypeVariable<?> typeVariable;

    protected TypeParameter() {
        Type type2 = this.capture();
        Preconditions.checkArgument(type2 instanceof TypeVariable, "%s should be a type variable.", type2);
        this.typeVariable = (TypeVariable)type2;
    }

    public final int hashCode() {
        return this.typeVariable.hashCode();
    }

    public final boolean equals(@Nullable Object o) {
        if (o instanceof TypeParameter) {
            TypeParameter that = (TypeParameter)o;
            return this.typeVariable.equals(that.typeVariable);
        }
        return false;
    }

    public String toString() {
        return this.typeVariable.toString();
    }
}

