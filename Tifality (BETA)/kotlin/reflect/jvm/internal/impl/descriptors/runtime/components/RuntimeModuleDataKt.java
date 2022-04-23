/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.ReflectionTypes;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectJavaClassFinder;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.RuntimeErrorReporter;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.RuntimeSourceElementFactory;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupTracker;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver;
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder;
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassesTracker;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaPropertyInitializerEvaluator;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaResolverCache;
import kotlin.reflect.jvm.internal.impl.load.java.components.SignaturePropagator;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaResolverComponents;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaResolverSettings;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaPackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ModuleClassResolver;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement;
import kotlin.reflect.jvm.internal.impl.load.kotlin.BinaryClassAnnotationAndConstantLoaderImpl;
import kotlin.reflect.jvm.internal.impl.load.kotlin.DeserializationComponentsForJava;
import kotlin.reflect.jvm.internal.impl.load.kotlin.DeserializedDescriptorResolver;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JavaClassDataFinder;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder;
import kotlin.reflect.jvm.internal.impl.load.kotlin.PackagePartProvider;
import kotlin.reflect.jvm.internal.impl.resolve.sam.SamConversionResolverImpl;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ContractDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ErrorReporter;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.checker.NewKotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.utils.Jsr305State;
import org.jetbrains.annotations.NotNull;

public final class RuntimeModuleDataKt {
    @NotNull
    public static final LazyJavaPackageFragmentProvider makeLazyJavaPackageFragmentFromClassLoaderProvider(@NotNull ClassLoader classLoader, @NotNull ModuleDescriptor module, @NotNull StorageManager storageManager, @NotNull NotFoundClasses notFoundClasses, @NotNull KotlinClassFinder reflectKotlinClassFinder, @NotNull DeserializedDescriptorResolver deserializedDescriptorResolver, @NotNull ModuleClassResolver singleModuleClassResolver, @NotNull PackagePartProvider packagePartProvider) {
        Intrinsics.checkNotNullParameter(classLoader, "classLoader");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        Intrinsics.checkNotNullParameter(reflectKotlinClassFinder, "reflectKotlinClassFinder");
        Intrinsics.checkNotNullParameter(deserializedDescriptorResolver, "deserializedDescriptorResolver");
        Intrinsics.checkNotNullParameter(singleModuleClassResolver, "singleModuleClassResolver");
        Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
        AnnotationTypeQualifierResolver annotationTypeQualifierResolver = new AnnotationTypeQualifierResolver(storageManager, Jsr305State.DISABLED);
        JavaClassFinder javaClassFinder = new ReflectJavaClassFinder(classLoader);
        SignaturePropagator signaturePropagator = SignaturePropagator.DO_NOTHING;
        Intrinsics.checkNotNullExpressionValue(signaturePropagator, "SignaturePropagator.DO_NOTHING");
        ErrorReporter errorReporter = RuntimeErrorReporter.INSTANCE;
        JavaResolverCache javaResolverCache = JavaResolverCache.EMPTY;
        Intrinsics.checkNotNullExpressionValue(javaResolverCache, "JavaResolverCache.EMPTY");
        JavaResolverComponents javaResolverComponents = new JavaResolverComponents(storageManager, javaClassFinder, reflectKotlinClassFinder, deserializedDescriptorResolver, signaturePropagator, errorReporter, javaResolverCache, JavaPropertyInitializerEvaluator.DoNothing.INSTANCE, new SamConversionResolverImpl(storageManager, CollectionsKt.emptyList()), RuntimeSourceElementFactory.INSTANCE, singleModuleClassResolver, packagePartProvider, SupertypeLoopChecker.EMPTY.INSTANCE, LookupTracker.DO_NOTHING.INSTANCE, module, new ReflectionTypes(module, notFoundClasses), annotationTypeQualifierResolver, new SignatureEnhancement(annotationTypeQualifierResolver, Jsr305State.DISABLED), JavaClassesTracker.Default.INSTANCE, JavaResolverSettings.Default.INSTANCE, NewKotlinTypeChecker.Companion.getDefault());
        return new LazyJavaPackageFragmentProvider(javaResolverComponents);
    }

    public static /* synthetic */ LazyJavaPackageFragmentProvider makeLazyJavaPackageFragmentFromClassLoaderProvider$default(ClassLoader classLoader, ModuleDescriptor moduleDescriptor, StorageManager storageManager, NotFoundClasses notFoundClasses, KotlinClassFinder kotlinClassFinder, DeserializedDescriptorResolver deserializedDescriptorResolver, ModuleClassResolver moduleClassResolver, PackagePartProvider packagePartProvider, int n, Object object) {
        if ((n & 0x80) != 0) {
            packagePartProvider = PackagePartProvider.Empty.INSTANCE;
        }
        return RuntimeModuleDataKt.makeLazyJavaPackageFragmentFromClassLoaderProvider(classLoader, moduleDescriptor, storageManager, notFoundClasses, kotlinClassFinder, deserializedDescriptorResolver, moduleClassResolver, packagePartProvider);
    }

    @NotNull
    public static final DeserializationComponentsForJava makeDeserializationComponentsForJava(@NotNull ModuleDescriptor module, @NotNull StorageManager storageManager, @NotNull NotFoundClasses notFoundClasses, @NotNull LazyJavaPackageFragmentProvider lazyJavaPackageFragmentProvider, @NotNull KotlinClassFinder reflectKotlinClassFinder, @NotNull DeserializedDescriptorResolver deserializedDescriptorResolver) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        Intrinsics.checkNotNullParameter(lazyJavaPackageFragmentProvider, "lazyJavaPackageFragmentProvider");
        Intrinsics.checkNotNullParameter(reflectKotlinClassFinder, "reflectKotlinClassFinder");
        Intrinsics.checkNotNullParameter(deserializedDescriptorResolver, "deserializedDescriptorResolver");
        JavaClassDataFinder javaClassDataFinder = new JavaClassDataFinder(reflectKotlinClassFinder, deserializedDescriptorResolver);
        BinaryClassAnnotationAndConstantLoaderImpl binaryClassAnnotationAndConstantLoader = new BinaryClassAnnotationAndConstantLoaderImpl(module, notFoundClasses, storageManager, reflectKotlinClassFinder);
        return new DeserializationComponentsForJava(storageManager, module, DeserializationConfiguration.Default.INSTANCE, javaClassDataFinder, binaryClassAnnotationAndConstantLoader, lazyJavaPackageFragmentProvider, notFoundClasses, RuntimeErrorReporter.INSTANCE, LookupTracker.DO_NOTHING.INSTANCE, ContractDeserializer.Companion.getDEFAULT(), NewKotlinTypeChecker.Companion.getDefault());
    }
}

