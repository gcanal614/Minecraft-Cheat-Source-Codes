/*
 * Decompiled with CFR 0.152.
 */
package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.PropertyReference0;
import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;

public class PropertyReference0Impl
extends PropertyReference0 {
    public PropertyReference0Impl(KDeclarationContainer owner, String name, String signature2) {
        super(NO_RECEIVER, ((ClassBasedDeclarationContainer)owner).getJClass(), name, signature2, owner instanceof KClass ? 0 : 1);
    }

    @SinceKotlin(version="1.4")
    public PropertyReference0Impl(Class owner, String name, String signature2, int flags) {
        super(NO_RECEIVER, owner, name, signature2, flags);
    }

    @SinceKotlin(version="1.4")
    public PropertyReference0Impl(Object receiver, Class owner, String name, String signature2, int flags) {
        super(receiver, owner, name, signature2, flags);
    }

    public Object get() {
        return this.getGetter().call(new Object[0]);
    }
}

