/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers;

import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.AbstractReceiverValue;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.ReceiverValue;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TransientReceiver
extends AbstractReceiverValue {
    public TransientReceiver(@NotNull KotlinType type2) {
        if (type2 == null) {
            TransientReceiver.$$$reportNull$$$0(0);
        }
        this(type2, null);
    }

    private TransientReceiver(@NotNull KotlinType type2, @Nullable ReceiverValue original) {
        if (type2 == null) {
            TransientReceiver.$$$reportNull$$$0(1);
        }
        super(type2, original);
    }

    public String toString() {
        return "{Transient} : " + this.getType();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[3];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "type";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "newType";
                break;
            }
        }
        objectArray2[1] = "kotlin/reflect/jvm/internal/impl/resolve/scopes/receivers/TransientReceiver";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = "<init>";
                break;
            }
            case 2: {
                objectArray = objectArray2;
                objectArray2[2] = "replaceType";
                break;
            }
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }
}

