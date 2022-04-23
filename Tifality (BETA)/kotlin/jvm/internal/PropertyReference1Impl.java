/*
 * Decompiled with CFR 0.152.
 */
package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;

public class PropertyReference1Impl
extends PropertyReference1 {
    public PropertyReference1Impl(KDeclarationContainer owner, String name, String signature2) {
        super(NO_RECEIVER, ((ClassBasedDeclarationContainer)owner).getJClass(), name, signature2, owner instanceof KClass ? 0 : 1);
    }

    @SinceKotlin(version="1.4")
    public PropertyReference1Impl(Class owner, String name, String signature2, int flags) {
        super(NO_RECEIVER, owner, name, signature2, flags);
    }

    @SinceKotlin(version="1.4")
    public PropertyReference1Impl(Object receiver, Class owner, String name, String signature2, int flags) {
        super(receiver, owner, name, signature2, flags);
    }

    public Object get(Object receiver) {
        return this.getGetter().call(receiver);
    }
}

