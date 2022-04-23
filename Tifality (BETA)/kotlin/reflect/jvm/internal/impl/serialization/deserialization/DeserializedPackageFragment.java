/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PackageFragmentDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassDataFinder;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import org.jetbrains.annotations.NotNull;

public abstract class DeserializedPackageFragment
extends PackageFragmentDescriptorImpl {
    @NotNull
    private final StorageManager storageManager;

    public abstract void initialize(@NotNull DeserializationComponents var1);

    @NotNull
    public abstract ClassDataFinder getClassDataFinder();

    public boolean hasTopLevelClass(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        MemberScope scope2 = this.getMemberScope();
        return scope2 instanceof DeserializedMemberScope && ((DeserializedMemberScope)scope2).getClassNames$deserialization().contains(name);
    }

    public DeserializedPackageFragment(@NotNull FqName fqName2, @NotNull StorageManager storageManager, @NotNull ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(module, "module");
        super(module, fqName2);
        this.storageManager = storageManager;
    }
}

