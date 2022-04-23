/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.io.InputStream;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectJavaClassFinderKt;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectKotlinClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectKotlinClassFinderKt;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins.BuiltInSerializerProtocol;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins.BuiltInsResourceLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectKotlinClassFinder
implements KotlinClassFinder {
    private final BuiltInsResourceLoader builtInsResourceLoader;
    private final ClassLoader classLoader;

    /*
     * Enabled aggressive block sorting
     */
    private final KotlinClassFinder.Result findKotlinClass(String fqName2) {
        KotlinClassFinder.Result.KotlinClass kotlinClass2;
        Object object = ReflectJavaClassFinderKt.tryLoadClass(this.classLoader, fqName2);
        if (object != null) {
            Object object2 = object;
            boolean bl = false;
            boolean bl2 = false;
            Class<?> it = object2;
            boolean bl3 = false;
            object = ReflectKotlinClass.Factory.create(it);
            if (object != null) {
                object2 = object;
                bl = false;
                bl2 = false;
                KotlinJvmBinaryClass p1 = (KotlinJvmBinaryClass)object2;
                boolean bl4 = false;
                kotlinClass2 = new KotlinClassFinder.Result.KotlinClass(p1);
                return kotlinClass2;
            }
        }
        kotlinClass2 = null;
        return kotlinClass2;
    }

    @Override
    @Nullable
    public KotlinClassFinder.Result findKotlinClassOrContent(@NotNull ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return this.findKotlinClass(ReflectKotlinClassFinderKt.access$toRuntimeFqName(classId));
    }

    @Override
    @Nullable
    public KotlinClassFinder.Result findKotlinClassOrContent(@NotNull JavaClass javaClass) {
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        Object object = javaClass.getFqName();
        if (object == null || (object = ((FqName)object).asString()) == null) {
            return null;
        }
        Intrinsics.checkNotNullExpressionValue(object, "javaClass.fqName?.asString() ?: return null");
        return this.findKotlinClass((String)object);
    }

    @Override
    @Nullable
    public InputStream findBuiltInsData(@NotNull FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        if (!packageFqName.startsWith(KotlinBuiltIns.BUILT_INS_PACKAGE_NAME)) {
            return null;
        }
        return this.builtInsResourceLoader.loadResource(BuiltInSerializerProtocol.INSTANCE.getBuiltInsFilePath(packageFqName));
    }

    public ReflectKotlinClassFinder(@NotNull ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        this.classLoader = classLoader;
        this.builtInsResourceLoader = new BuiltInsResourceLoader();
    }
}

