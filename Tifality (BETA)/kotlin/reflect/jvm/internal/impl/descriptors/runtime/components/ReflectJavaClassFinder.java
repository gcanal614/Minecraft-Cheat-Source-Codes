/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectJavaClassFinderKt;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaPackage;
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPackage;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectJavaClassFinder
implements JavaClassFinder {
    private final ClassLoader classLoader;

    @Override
    @Nullable
    public JavaClass findClass(@NotNull JavaClassFinder.Request request) {
        Intrinsics.checkNotNullParameter(request, "request");
        ClassId classId = request.getClassId();
        FqName fqName2 = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(fqName2, "classId.packageFqName");
        FqName packageFqName = fqName2;
        String string = classId.getRelativeClassName().asString();
        Intrinsics.checkNotNullExpressionValue(string, "classId.relativeClassName.asString()");
        String relativeClassName = StringsKt.replace$default(string, '.', '$', false, 4, null);
        String name = packageFqName.isRoot() ? relativeClassName : packageFqName.asString() + "." + relativeClassName;
        Class<?> klass = ReflectJavaClassFinderKt.tryLoadClass(this.classLoader, name);
        return klass != null ? new ReflectJavaClass(klass) : null;
    }

    @Override
    @Nullable
    public JavaPackage findPackage(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return new ReflectJavaPackage(fqName2);
    }

    @Override
    @Nullable
    public Set<String> knownClassNamesInPackage(@NotNull FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return null;
    }

    public ReflectJavaClassFinder(@NotNull ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        this.classLoader = classLoader;
    }
}

