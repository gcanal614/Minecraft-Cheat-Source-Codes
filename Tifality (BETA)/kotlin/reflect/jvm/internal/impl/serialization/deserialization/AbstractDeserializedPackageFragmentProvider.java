/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializedPackageFragment;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.KotlinMetadataFinder;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractDeserializedPackageFragmentProvider
implements PackageFragmentProvider {
    @NotNull
    protected DeserializationComponents components;
    private final MemoizedFunctionToNullable<FqName, PackageFragmentDescriptor> fragments;
    @NotNull
    private final StorageManager storageManager;
    @NotNull
    private final KotlinMetadataFinder finder;
    @NotNull
    private final ModuleDescriptor moduleDescriptor;

    @NotNull
    protected final DeserializationComponents getComponents() {
        DeserializationComponents deserializationComponents = this.components;
        if (deserializationComponents == null) {
            Intrinsics.throwUninitializedPropertyAccessException("components");
        }
        return deserializationComponents;
    }

    protected final void setComponents(@NotNull DeserializationComponents deserializationComponents) {
        Intrinsics.checkNotNullParameter(deserializationComponents, "<set-?>");
        this.components = deserializationComponents;
    }

    @Nullable
    protected abstract DeserializedPackageFragment findPackage(@NotNull FqName var1);

    @Override
    @NotNull
    public List<PackageFragmentDescriptor> getPackageFragments(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return CollectionsKt.listOfNotNull(this.fragments.invoke(fqName2));
    }

    @Override
    @NotNull
    public Collection<FqName> getSubPackagesOf(@NotNull FqName fqName2, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return SetsKt.emptySet();
    }

    @NotNull
    protected final StorageManager getStorageManager() {
        return this.storageManager;
    }

    @NotNull
    protected final KotlinMetadataFinder getFinder() {
        return this.finder;
    }

    @NotNull
    protected final ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    public AbstractDeserializedPackageFragmentProvider(@NotNull StorageManager storageManager, @NotNull KotlinMetadataFinder finder, @NotNull ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(finder, "finder");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        this.storageManager = storageManager;
        this.finder = finder;
        this.moduleDescriptor = moduleDescriptor;
        this.fragments = this.storageManager.createMemoizedFunctionWithNullableValues((Function1)new Function1<FqName, PackageFragmentDescriptor>(this){
            final /* synthetic */ AbstractDeserializedPackageFragmentProvider this$0;

            @Nullable
            public final PackageFragmentDescriptor invoke(@NotNull FqName fqName2) {
                DeserializedPackageFragment deserializedPackageFragment;
                Intrinsics.checkNotNullParameter(fqName2, "fqName");
                DeserializedPackageFragment deserializedPackageFragment2 = this.this$0.findPackage(fqName2);
                if (deserializedPackageFragment2 != null) {
                    DeserializedPackageFragment deserializedPackageFragment3 = deserializedPackageFragment2;
                    boolean bl = false;
                    boolean bl2 = false;
                    DeserializedPackageFragment $this$apply = deserializedPackageFragment3;
                    boolean bl3 = false;
                    $this$apply.initialize(this.this$0.getComponents());
                    deserializedPackageFragment = deserializedPackageFragment3;
                } else {
                    deserializedPackageFragment = null;
                }
                return deserializedPackageFragment;
            }
            {
                this.this$0 = abstractDeserializedPackageFragmentProvider;
                super(1);
            }
        });
    }
}

