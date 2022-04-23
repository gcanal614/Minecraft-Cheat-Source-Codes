/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import kotlin.Lazy;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaResolverComponents;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaTypeQualifiersByElementType;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.TypeParameterResolver;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolver;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LazyJavaResolverContext {
    @Nullable
    private final Lazy defaultTypeQualifiers$delegate;
    @NotNull
    private final JavaTypeResolver typeResolver;
    @NotNull
    private final JavaResolverComponents components;
    @NotNull
    private final TypeParameterResolver typeParameterResolver;
    @NotNull
    private final Lazy<JavaTypeQualifiersByElementType> delegateForDefaultTypeQualifiers;

    @Nullable
    public final JavaTypeQualifiersByElementType getDefaultTypeQualifiers() {
        Lazy lazy = this.defaultTypeQualifiers$delegate;
        LazyJavaResolverContext lazyJavaResolverContext = this;
        Object var3_3 = null;
        boolean bl = false;
        return (JavaTypeQualifiersByElementType)lazy.getValue();
    }

    @NotNull
    public final JavaTypeResolver getTypeResolver() {
        return this.typeResolver;
    }

    @NotNull
    public final StorageManager getStorageManager() {
        return this.components.getStorageManager();
    }

    @NotNull
    public final ModuleDescriptor getModule() {
        return this.components.getModule();
    }

    @NotNull
    public final JavaResolverComponents getComponents() {
        return this.components;
    }

    @NotNull
    public final TypeParameterResolver getTypeParameterResolver() {
        return this.typeParameterResolver;
    }

    @NotNull
    public final Lazy<JavaTypeQualifiersByElementType> getDelegateForDefaultTypeQualifiers$descriptors_jvm() {
        return this.delegateForDefaultTypeQualifiers;
    }

    public LazyJavaResolverContext(@NotNull JavaResolverComponents components, @NotNull TypeParameterResolver typeParameterResolver, @NotNull Lazy<JavaTypeQualifiersByElementType> delegateForDefaultTypeQualifiers) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(typeParameterResolver, "typeParameterResolver");
        Intrinsics.checkNotNullParameter(delegateForDefaultTypeQualifiers, "delegateForDefaultTypeQualifiers");
        this.components = components;
        this.typeParameterResolver = typeParameterResolver;
        this.defaultTypeQualifiers$delegate = this.delegateForDefaultTypeQualifiers = delegateForDefaultTypeQualifiers;
        this.typeResolver = new JavaTypeResolver(this, this.typeParameterResolver);
    }
}

