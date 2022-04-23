/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.full;

import kotlin.Metadata;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KProperty1;
import kotlin.reflect.full.KClasses;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=3)
final class KClasses$isSubclassOf$1
extends PropertyReference1 {
    public static final KProperty1 INSTANCE = new KClasses$isSubclassOf$1();

    KClasses$isSubclassOf$1() {
    }

    @Override
    public String getName() {
        return "superclasses";
    }

    @Override
    public String getSignature() {
        return "getSuperclasses(Lkotlin/reflect/KClass;)Ljava/util/List;";
    }

    @Override
    public KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(KClasses.class, "kotlin-reflection");
    }

    @Nullable
    public Object get(@Nullable Object receiver) {
        return KClasses.getSuperclasses((KClass)receiver);
    }
}

