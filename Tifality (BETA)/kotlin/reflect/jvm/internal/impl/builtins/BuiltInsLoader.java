/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.builtins;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoader;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import org.jetbrains.annotations.NotNull;

public interface BuiltInsLoader {
    public static final Companion Companion = kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoader$Companion.$$INSTANCE;

    @NotNull
    public PackageFragmentProvider createPackageFragmentProvider(@NotNull StorageManager var1, @NotNull ModuleDescriptor var2, @NotNull Iterable<? extends ClassDescriptorFactory> var3, @NotNull PlatformDependentDeclarationFilter var4, @NotNull AdditionalClassPartsProvider var5, boolean var6);

    public static final class Companion {
        @NotNull
        private static final Lazy Instance$delegate;
        static final /* synthetic */ Companion $$INSTANCE;

        @NotNull
        public final BuiltInsLoader getInstance() {
            Lazy lazy = Instance$delegate;
            Object var2_2 = null;
            boolean bl = false;
            return (BuiltInsLoader)lazy.getValue();
        }

        private Companion() {
        }

        static {
            Companion companion;
            $$INSTANCE = companion = new Companion();
            Instance$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0)Instance.2.INSTANCE);
        }
    }
}

