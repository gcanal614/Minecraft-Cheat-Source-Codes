/*
 * Decompiled with CFR 0.152.
 */
package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.PropertyReference2;
import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;

public class PropertyReference2Impl
extends PropertyReference2 {
    public PropertyReference2Impl(KDeclarationContainer owner, String name, String signature2) {
        super(((ClassBasedDeclarationContainer)owner).getJClass(), name, signature2, owner instanceof KClass ? 0 : 1);
    }

    @SinceKotlin(version="1.4")
    public PropertyReference2Impl(Class owner, String name, String signature2, int flags) {
        super(owner, name, signature2, flags);
    }

    public Object get(Object receiver1, Object receiver2) {
        return this.getGetter().call(receiver1, receiver2);
    }
}

