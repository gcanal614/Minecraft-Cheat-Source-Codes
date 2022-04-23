/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty1;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import org.jetbrains.annotations.Nullable;

final class MemberDeserializer$containsSuspendFunctionType$1
extends PropertyReference1 {
    public static final KProperty1 INSTANCE = new MemberDeserializer$containsSuspendFunctionType$1();

    MemberDeserializer$containsSuspendFunctionType$1() {
    }

    @Override
    public String getName() {
        return "isSuspendFunctionType";
    }

    @Override
    public String getSignature() {
        return "isSuspendFunctionType(Lorg/jetbrains/kotlin/types/KotlinType;)Z";
    }

    @Override
    public KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(FunctionTypesKt.class, "deserialization");
    }

    @Nullable
    public Object get(@Nullable Object receiver) {
        return FunctionTypesKt.isSuspendFunctionType((UnwrappedType)receiver);
    }
}

