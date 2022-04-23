/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin._Assertions;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptorVisitor;
import kotlin.reflect.jvm.internal.impl.descriptors.InvalidModuleException;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.CompositePackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.DeclarationDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.LazyPackageViewDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleDependencies;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleDependenciesImpl;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.platform.TargetPlatform;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefinerKt;
import kotlin.reflect.jvm.internal.impl.types.checker.Ref;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ModuleDescriptorImpl
extends DeclarationDescriptorImpl
implements ModuleDescriptor {
    private final Map<ModuleDescriptor.Capability<?>, Object> capabilities;
    private ModuleDependencies dependencies;
    private PackageFragmentProvider packageFragmentProviderForModuleContent;
    private boolean isValid;
    private final MemoizedFunctionToNotNull<FqName, PackageViewDescriptor> packages;
    private final Lazy packageFragmentProviderForWholeModuleWithDependencies$delegate;
    private final StorageManager storageManager;
    @NotNull
    private final KotlinBuiltIns builtIns;
    @Nullable
    private final TargetPlatform platform;
    @Nullable
    private final Name stableName;

    public boolean isValid() {
        return this.isValid;
    }

    public void assertValid() {
        if (!this.isValid()) {
            throw (Throwable)new InvalidModuleException("Accessing invalid module descriptor " + this);
        }
    }

    @Override
    @NotNull
    public List<ModuleDescriptor> getExpectedByModules() {
        ModuleDependencies $this$sure$iv = this.dependencies;
        boolean $i$f$sure = false;
        ModuleDependencies moduleDependencies = $this$sure$iv;
        if (moduleDependencies == null) {
            String string;
            boolean bl = false;
            String string2 = string = "Dependencies of module " + this.getId() + " were not set";
            throw (Throwable)((Object)new AssertionError((Object)string2));
        }
        return moduleDependencies.getExpectedByDependencies();
    }

    @Override
    @NotNull
    public PackageViewDescriptor getPackage(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        this.assertValid();
        return (PackageViewDescriptor)this.packages.invoke(fqName2);
    }

    @Override
    @NotNull
    public Collection<FqName> getSubPackagesOf(@NotNull FqName fqName2, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        this.assertValid();
        return this.getPackageFragmentProvider().getSubPackagesOf(fqName2, nameFilter);
    }

    private final CompositePackageFragmentProvider getPackageFragmentProviderForWholeModuleWithDependencies() {
        Lazy lazy = this.packageFragmentProviderForWholeModuleWithDependencies$delegate;
        ModuleDescriptorImpl moduleDescriptorImpl = this;
        Object var3_3 = null;
        boolean bl = false;
        return (CompositePackageFragmentProvider)lazy.getValue();
    }

    private final boolean isInitialized() {
        return this.packageFragmentProviderForModuleContent != null;
    }

    public final void setDependencies(@NotNull ModuleDependencies dependencies) {
        Intrinsics.checkNotNullParameter(dependencies, "dependencies");
        boolean bl = this.dependencies == null;
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl3 = false;
            String string = "Dependencies of " + this.getId() + " were already set";
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        this.dependencies = dependencies;
    }

    public final void setDependencies(ModuleDescriptorImpl ... descriptors) {
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
        this.setDependencies(ArraysKt.toList(descriptors));
    }

    public final void setDependencies(@NotNull List<ModuleDescriptorImpl> descriptors) {
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
        this.setDependencies(descriptors, SetsKt.<ModuleDescriptorImpl>emptySet());
    }

    public final void setDependencies(@NotNull List<ModuleDescriptorImpl> descriptors, @NotNull Set<ModuleDescriptorImpl> friends) {
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
        Intrinsics.checkNotNullParameter(friends, "friends");
        this.setDependencies(new ModuleDependenciesImpl(descriptors, friends, CollectionsKt.<ModuleDescriptorImpl>emptyList()));
    }

    @Override
    public boolean shouldSeeInternalsOf(@NotNull ModuleDescriptor targetModule) {
        Intrinsics.checkNotNullParameter(targetModule, "targetModule");
        if (Intrinsics.areEqual(this, targetModule)) {
            return true;
        }
        ModuleDependencies moduleDependencies = this.dependencies;
        Intrinsics.checkNotNull(moduleDependencies);
        if (CollectionsKt.contains((Iterable)moduleDependencies.getModulesWhoseInternalsAreVisible(), targetModule)) {
            return true;
        }
        if (this.getExpectedByModules().contains(targetModule)) {
            return true;
        }
        return targetModule.getExpectedByModules().contains(this);
    }

    private final String getId() {
        String string = this.getName().toString();
        Intrinsics.checkNotNullExpressionValue(string, "name.toString()");
        return string;
    }

    public final void initialize(@NotNull PackageFragmentProvider providerForModuleContent) {
        Intrinsics.checkNotNullParameter(providerForModuleContent, "providerForModuleContent");
        boolean bl = !this.isInitialized();
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl3 = false;
            String string = "Attempt to initialize module " + this.getId() + " twice";
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        this.packageFragmentProviderForModuleContent = providerForModuleContent;
    }

    @NotNull
    public final PackageFragmentProvider getPackageFragmentProvider() {
        this.assertValid();
        return this.getPackageFragmentProviderForWholeModuleWithDependencies();
    }

    @Override
    @Nullable
    public <T> T getCapability(@NotNull ModuleDescriptor.Capability<T> capability) {
        Intrinsics.checkNotNullParameter(capability, "capability");
        Object object = this.capabilities.get(capability);
        if (!(object instanceof Object)) {
            object = null;
        }
        return (T)object;
    }

    @Override
    @NotNull
    public KotlinBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    @JvmOverloads
    public ModuleDescriptorImpl(@NotNull Name moduleName, @NotNull StorageManager storageManager, @NotNull KotlinBuiltIns builtIns, @Nullable TargetPlatform platform, @NotNull Map<ModuleDescriptor.Capability<?>, ? extends Object> capabilities, @Nullable Name stableName) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(capabilities, "capabilities");
        super(Annotations.Companion.getEMPTY(), moduleName);
        this.storageManager = storageManager;
        this.builtIns = builtIns;
        this.platform = platform;
        this.stableName = stableName;
        if (!moduleName.isSpecial()) {
            throw (Throwable)new IllegalArgumentException("Module name must be special: " + moduleName);
        }
        this.capabilities = MapsKt.toMutableMap(capabilities);
        this.capabilities.put(KotlinTypeRefinerKt.getREFINER_CAPABILITY(), new Ref<Object>(null));
        this.isValid = true;
        this.packages = this.storageManager.createMemoizedFunction((Function1)new Function1<FqName, PackageViewDescriptor>(this){
            final /* synthetic */ ModuleDescriptorImpl this$0;

            @NotNull
            public final PackageViewDescriptor invoke(@NotNull FqName fqName2) {
                Intrinsics.checkNotNullParameter(fqName2, "fqName");
                return new LazyPackageViewDescriptorImpl(this.this$0, fqName2, ModuleDescriptorImpl.access$getStorageManager$p(this.this$0));
            }
            {
                this.this$0 = moduleDescriptorImpl;
                super(1);
            }
        });
        this.packageFragmentProviderForWholeModuleWithDependencies$delegate = LazyKt.lazy((Function0)new Function0<CompositePackageFragmentProvider>(this){
            final /* synthetic */ ModuleDescriptorImpl this$0;

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final CompositePackageFragmentProvider invoke() {
                Collection<PackageFragmentProvider> collection;
                void $this$mapTo$iv$iv;
                ModuleDependencies $this$sure$iv = ModuleDescriptorImpl.access$getDependencies$p(this.this$0);
                boolean $i$f$sure = false;
                ModuleDependencies moduleDependencies = $this$sure$iv;
                if (moduleDependencies == null) {
                    String string;
                    boolean bl = false;
                    String string2 = string = "Dependencies of module " + ModuleDescriptorImpl.access$getId$p(this.this$0) + " were not set before querying module content";
                    throw (Throwable)((Object)new AssertionError((Object)string2));
                }
                ModuleDependencies moduleDependencies2 = moduleDependencies;
                List<ModuleDescriptorImpl> dependenciesDescriptors = moduleDependencies2.getAllDependencies();
                $i$f$sure = dependenciesDescriptors.contains(this.this$0);
                boolean bl = false;
                if (_Assertions.ENABLED && !$i$f$sure) {
                    boolean $i$a$-assert-ModuleDescriptorImpl$packageFragmentProviderForWholeModuleWithDependencies$2$22 = false;
                    String $i$a$-assert-ModuleDescriptorImpl$packageFragmentProviderForWholeModuleWithDependencies$2$22 = "Module " + ModuleDescriptorImpl.access$getId$p(this.this$0) + " is not contained in his own dependencies, this is probably a misconfiguration";
                    throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-ModuleDescriptorImpl$packageFragmentProviderForWholeModuleWithDependencies$2$22));
                }
                Iterable $this$forEach$iv = dependenciesDescriptors;
                boolean $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    ModuleDescriptorImpl dependency = (ModuleDescriptorImpl)element$iv;
                    boolean bl2 = false;
                    boolean bl3 = ModuleDescriptorImpl.access$isInitialized$p(dependency);
                    boolean bl4 = false;
                    if (!_Assertions.ENABLED || bl3) continue;
                    boolean $i$a$-assert-ModuleDescriptorImpl$packageFragmentProviderForWholeModuleWithDependencies$2$2$22 = false;
                    String $i$a$-assert-ModuleDescriptorImpl$packageFragmentProviderForWholeModuleWithDependencies$2$2$22 = "Dependency module " + ModuleDescriptorImpl.access$getId$p(dependency) + " was not initialized by the time contents of dependent module " + ModuleDescriptorImpl.access$getId$p(this.this$0) + " were queried";
                    throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-ModuleDescriptorImpl$packageFragmentProviderForWholeModuleWithDependencies$2$2$22));
                }
                Iterable $this$map$iv = dependenciesDescriptors;
                boolean $i$f$map = false;
                Iterable $i$a$-assert-ModuleDescriptorImpl$packageFragmentProviderForWholeModuleWithDependencies$2$22 = $this$map$iv;
                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    PackageFragmentProvider packageFragmentProvider;
                    void it;
                    ModuleDescriptorImpl moduleDescriptorImpl = (ModuleDescriptorImpl)item$iv$iv;
                    collection = destination$iv$iv;
                    boolean bl5 = false;
                    Intrinsics.checkNotNull(ModuleDescriptorImpl.access$getPackageFragmentProviderForModuleContent$p((ModuleDescriptorImpl)it));
                    collection.add(packageFragmentProvider);
                }
                collection = (List)destination$iv$iv;
                List list = collection;
                return new CompositePackageFragmentProvider(list);
            }
            {
                this.this$0 = moduleDescriptorImpl;
                super(0);
            }
        });
    }

    public /* synthetic */ ModuleDescriptorImpl(Name name, StorageManager storageManager, KotlinBuiltIns kotlinBuiltIns, TargetPlatform targetPlatform, Map map2, Name name2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 8) != 0) {
            targetPlatform = null;
        }
        if ((n & 0x10) != 0) {
            map2 = MapsKt.emptyMap();
        }
        if ((n & 0x20) != 0) {
            name2 = null;
        }
        this(name, storageManager, kotlinBuiltIns, targetPlatform, map2, name2);
    }

    @JvmOverloads
    public ModuleDescriptorImpl(@NotNull Name moduleName, @NotNull StorageManager storageManager, @NotNull KotlinBuiltIns builtIns, @Nullable TargetPlatform platform) {
        this(moduleName, storageManager, builtIns, platform, null, null, 48, null);
    }

    @Override
    @Nullable
    public DeclarationDescriptor getContainingDeclaration() {
        return ModuleDescriptor.DefaultImpls.getContainingDeclaration(this);
    }

    @Override
    public <R, D> R accept(@NotNull DeclarationDescriptorVisitor<R, D> visitor2, D data2) {
        Intrinsics.checkNotNullParameter(visitor2, "visitor");
        return ModuleDescriptor.DefaultImpls.accept(this, visitor2, data2);
    }

    public static final /* synthetic */ StorageManager access$getStorageManager$p(ModuleDescriptorImpl $this) {
        return $this.storageManager;
    }

    public static final /* synthetic */ ModuleDependencies access$getDependencies$p(ModuleDescriptorImpl $this) {
        return $this.dependencies;
    }

    public static final /* synthetic */ String access$getId$p(ModuleDescriptorImpl $this) {
        return $this.getId();
    }

    public static final /* synthetic */ boolean access$isInitialized$p(ModuleDescriptorImpl $this) {
        return $this.isInitialized();
    }

    public static final /* synthetic */ PackageFragmentProvider access$getPackageFragmentProviderForModuleContent$p(ModuleDescriptorImpl $this) {
        return $this.packageFragmentProviderForModuleContent;
    }
}

