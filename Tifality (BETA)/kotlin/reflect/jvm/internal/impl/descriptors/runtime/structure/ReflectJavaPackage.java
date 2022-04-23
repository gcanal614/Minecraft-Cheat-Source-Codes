/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaElement;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPackage;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectJavaPackage
extends ReflectJavaElement
implements JavaPackage {
    @NotNull
    private final FqName fqName;

    @Override
    @NotNull
    public Collection<JavaClass> getClasses(@NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        boolean bl = false;
        return CollectionsKt.emptyList();
    }

    @Override
    @NotNull
    public Collection<JavaPackage> getSubPackages() {
        boolean bl = false;
        return CollectionsKt.emptyList();
    }

    @NotNull
    public List<JavaAnnotation> getAnnotations() {
        return CollectionsKt.emptyList();
    }

    @Override
    @Nullable
    public JavaAnnotation findAnnotation(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return null;
    }

    @Override
    public boolean isDeprecatedInJavaDoc() {
        return false;
    }

    public boolean equals(@Nullable Object other) {
        return other instanceof ReflectJavaPackage && Intrinsics.areEqual(this.getFqName(), ((ReflectJavaPackage)other).getFqName());
    }

    public int hashCode() {
        return this.getFqName().hashCode();
    }

    @NotNull
    public String toString() {
        return this.getClass().getName() + ": " + this.getFqName();
    }

    @Override
    @NotNull
    public FqName getFqName() {
        return this.fqName;
    }

    public ReflectJavaPackage(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        this.fqName = fqName2;
    }
}

