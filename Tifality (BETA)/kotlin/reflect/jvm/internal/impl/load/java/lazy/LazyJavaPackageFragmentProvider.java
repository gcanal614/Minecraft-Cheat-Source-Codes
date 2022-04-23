/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.List;
import kotlin.LazyKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaResolverComponents;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.TypeParameterResolver;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaPackageFragment;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPackage;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.storage.CacheWithNotNullValues;
import org.jetbrains.annotations.NotNull;

public final class LazyJavaPackageFragmentProvider
implements PackageFragmentProvider {
    private final LazyJavaResolverContext c;
    private final CacheWithNotNullValues<FqName, LazyJavaPackageFragment> packageFragments;

    private final LazyJavaPackageFragment getPackageFragment(FqName fqName2) {
        JavaPackage javaPackage = this.c.getComponents().getFinder().findPackage(fqName2);
        if (javaPackage == null) {
            return null;
        }
        JavaPackage jPackage = javaPackage;
        return this.packageFragments.computeIfAbsent(fqName2, new Function0<LazyJavaPackageFragment>(this, jPackage){
            final /* synthetic */ LazyJavaPackageFragmentProvider this$0;
            final /* synthetic */ JavaPackage $jPackage;

            @NotNull
            public final LazyJavaPackageFragment invoke() {
                return new LazyJavaPackageFragment(LazyJavaPackageFragmentProvider.access$getC$p(this.this$0), this.$jPackage);
            }
            {
                this.this$0 = lazyJavaPackageFragmentProvider;
                this.$jPackage = javaPackage;
                super(0);
            }
        });
    }

    @NotNull
    public List<LazyJavaPackageFragment> getPackageFragments(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return CollectionsKt.listOfNotNull(this.getPackageFragment(fqName2));
    }

    @NotNull
    public List<FqName> getSubPackagesOf(@NotNull FqName fqName2, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        LazyJavaPackageFragment lazyJavaPackageFragment = this.getPackageFragment(fqName2);
        List<FqName> list = lazyJavaPackageFragment != null ? lazyJavaPackageFragment.getSubPackageFqNames$descriptors_jvm() : null;
        boolean bl = false;
        List<FqName> list2 = list;
        if (list2 == null) {
            list2 = CollectionsKt.emptyList();
        }
        return list2;
    }

    public LazyJavaPackageFragmentProvider(@NotNull JavaResolverComponents components) {
        Intrinsics.checkNotNullParameter(components, "components");
        this.c = new LazyJavaResolverContext(components, TypeParameterResolver.EMPTY.INSTANCE, LazyKt.lazyOf(null));
        this.packageFragments = this.c.getStorageManager().createCacheWithNotNullValues();
    }

    public static final /* synthetic */ LazyJavaResolverContext access$getC$p(LazyJavaPackageFragmentProvider $this) {
        return $this.c;
    }
}

