/*
 * Decompiled with CFR 0.152.
 */
package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.MutablePropertyReference0;
import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;

public class MutablePropertyReference0Impl
extends MutablePropertyReference0 {
    public MutablePropertyReference0Impl(KDeclarationContainer owner, String name, String signature2) {
        super(NO_RECEIVER, ((ClassBasedDeclarationContainer)owner).getJClass(), name, signature2, owner instanceof KClass ? 0 : 1);
    }

    @SinceKotlin(version="1.4")
    public MutablePropertyReference0Impl(Class owner, String name, String signature2, int flags) {
        super(NO_RECEIVER, owner, name, signature2, flags);
    }

    @SinceKotlin(version="1.4")
    public MutablePropertyReference0Impl(Object receiver, Class owner, String name, String signature2, int flags) {
        super(receiver, owner, name, signature2, flags);
    }

    @Override
    public Object get() {
        return this.getGetter().call(new Object[0]);
    }

    public void set(Object value) {
        this.getSetter().call(value);
    }
}

