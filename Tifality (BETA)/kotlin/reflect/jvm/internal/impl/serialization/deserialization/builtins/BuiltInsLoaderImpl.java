/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoader;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProviderImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupTracker;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.sam.SamConversionResolverImpl;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotationAndConstantLoader;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotationAndConstantLoaderImpl;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassDataFinder;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ContractDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializedClassDataFinder;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ErrorReporter;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.FlexibleTypeDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.LocalClassifierTypeSettings;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins.BuiltInSerializerProtocol;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins.BuiltInsPackageFragmentImpl;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins.BuiltInsResourceLoader;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BuiltInsLoaderImpl
implements BuiltInsLoader {
    private final BuiltInsResourceLoader resourceLoader = new BuiltInsResourceLoader();

    @Override
    @NotNull
    public PackageFragmentProvider createPackageFragmentProvider(@NotNull StorageManager storageManager, @NotNull ModuleDescriptor builtInsModule, @NotNull Iterable<? extends ClassDescriptorFactory> classDescriptorFactories, @NotNull PlatformDependentDeclarationFilter platformDependentDeclarationFilter, @NotNull AdditionalClassPartsProvider additionalClassPartsProvider, boolean isFallback) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(builtInsModule, "builtInsModule");
        Intrinsics.checkNotNullParameter(classDescriptorFactories, "classDescriptorFactories");
        Intrinsics.checkNotNullParameter(platformDependentDeclarationFilter, "platformDependentDeclarationFilter");
        Intrinsics.checkNotNullParameter(additionalClassPartsProvider, "additionalClassPartsProvider");
        Set<FqName> set = KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAMES;
        Intrinsics.checkNotNullExpressionValue(set, "KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAMES");
        return this.createBuiltInPackageFragmentProvider(storageManager, builtInsModule, set, classDescriptorFactories, platformDependentDeclarationFilter, additionalClassPartsProvider, isFallback, (Function1<? super String, ? extends InputStream>)new Function1<String, InputStream>(this.resourceLoader){

            @Nullable
            public final InputStream invoke(@NotNull String p1) {
                Intrinsics.checkNotNullParameter(p1, "p1");
                return ((BuiltInsResourceLoader)this.receiver).loadResource(p1);
            }

            public final KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(BuiltInsResourceLoader.class);
            }

            public final String getName() {
                return "loadResource";
            }

            public final String getSignature() {
                return "loadResource(Ljava/lang/String;)Ljava/io/InputStream;";
            }
        });
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final PackageFragmentProvider createBuiltInPackageFragmentProvider(@NotNull StorageManager storageManager, @NotNull ModuleDescriptor module, @NotNull Set<FqName> packageFqNames, @NotNull Iterable<? extends ClassDescriptorFactory> classDescriptorFactories, @NotNull PlatformDependentDeclarationFilter platformDependentDeclarationFilter, @NotNull AdditionalClassPartsProvider additionalClassPartsProvider, boolean isFallback, @NotNull Function1<? super String, ? extends InputStream> loadResource) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(packageFqNames, "packageFqNames");
        Intrinsics.checkNotNullParameter(classDescriptorFactories, "classDescriptorFactories");
        Intrinsics.checkNotNullParameter(platformDependentDeclarationFilter, "platformDependentDeclarationFilter");
        Intrinsics.checkNotNullParameter(additionalClassPartsProvider, "additionalClassPartsProvider");
        Intrinsics.checkNotNullParameter(loadResource, "loadResource");
        Iterable $this$map$iv = packageFqNames;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            InputStream inputStream;
            void fqName2;
            FqName fqName3 = (FqName)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            String resourcePath = BuiltInSerializerProtocol.INSTANCE.getBuiltInsFilePath((FqName)fqName2);
            if (loadResource.invoke(resourcePath) == null) {
                throw (Throwable)new IllegalStateException("Resource not found in classpath: " + resourcePath);
            }
            BuiltInsPackageFragmentImpl builtInsPackageFragmentImpl = BuiltInsPackageFragmentImpl.Companion.create((FqName)fqName2, storageManager, module, inputStream, isFallback);
            collection.add(builtInsPackageFragmentImpl);
        }
        List packageFragments2 = (List)destination$iv$iv;
        PackageFragmentProviderImpl provider = new PackageFragmentProviderImpl(packageFragments2);
        NotFoundClasses notFoundClasses = new NotFoundClasses(storageManager, module);
        DeserializationConfiguration deserializationConfiguration = DeserializationConfiguration.Default.INSTANCE;
        ClassDataFinder classDataFinder2 = new DeserializedClassDataFinder(provider);
        AnnotationAndConstantLoader annotationAndConstantLoader = new AnnotationAndConstantLoaderImpl(module, notFoundClasses, BuiltInSerializerProtocol.INSTANCE);
        PackageFragmentProvider packageFragmentProvider = provider;
        LocalClassifierTypeSettings localClassifierTypeSettings = LocalClassifierTypeSettings.Default.INSTANCE;
        ErrorReporter errorReporter = ErrorReporter.DO_NOTHING;
        Intrinsics.checkNotNullExpressionValue(errorReporter, "ErrorReporter.DO_NOTHING");
        DeserializationComponents components = new DeserializationComponents(storageManager, module, deserializationConfiguration, classDataFinder2, annotationAndConstantLoader, packageFragmentProvider, localClassifierTypeSettings, errorReporter, LookupTracker.DO_NOTHING.INSTANCE, FlexibleTypeDeserializer.ThrowException.INSTANCE, classDescriptorFactories, notFoundClasses, ContractDeserializer.Companion.getDEFAULT(), additionalClassPartsProvider, platformDependentDeclarationFilter, BuiltInSerializerProtocol.INSTANCE.getExtensionRegistry(), null, new SamConversionResolverImpl(storageManager, CollectionsKt.emptyList()), null, 327680, null);
        for (BuiltInsPackageFragmentImpl packageFragment : packageFragments2) {
            packageFragment.initialize(components);
        }
        return provider;
    }

    public static /* synthetic */ PackageFragmentProvider createBuiltInPackageFragmentProvider$default(BuiltInsLoaderImpl builtInsLoaderImpl, StorageManager storageManager, ModuleDescriptor moduleDescriptor, Set set, Iterable iterable, PlatformDependentDeclarationFilter platformDependentDeclarationFilter, AdditionalClassPartsProvider additionalClassPartsProvider, boolean bl, Function1 function1, int n, Object object) {
        if ((n & 0x20) != 0) {
            additionalClassPartsProvider = AdditionalClassPartsProvider.None.INSTANCE;
        }
        return builtInsLoaderImpl.createBuiltInPackageFragmentProvider(storageManager, moduleDescriptor, set, iterable, platformDependentDeclarationFilter, additionalClassPartsProvider, bl, function1);
    }
}

