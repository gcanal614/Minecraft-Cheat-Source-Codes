/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassData;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassDataFinder;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializedPackageFragment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DeserializedClassDataFinder
implements ClassDataFinder {
    private final PackageFragmentProvider packageFragmentProvider;

    @Override
    @Nullable
    public ClassData findClassData(@NotNull ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        FqName fqName2 = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(fqName2, "classId.packageFqName");
        List<PackageFragmentDescriptor> packageFragments2 = this.packageFragmentProvider.getPackageFragments(fqName2);
        for (PackageFragmentDescriptor fragment : packageFragments2) {
            if (!(fragment instanceof DeserializedPackageFragment)) continue;
            ClassData classData = ((DeserializedPackageFragment)fragment).getClassDataFinder().findClassData(classId);
            if (classData == null) continue;
            ClassData classData2 = classData;
            boolean bl = false;
            boolean bl2 = false;
            ClassData it = classData2;
            boolean bl3 = false;
            return it;
        }
        return null;
    }

    public DeserializedClassDataFinder(@NotNull PackageFragmentProvider packageFragmentProvider) {
        Intrinsics.checkNotNullParameter(packageFragmentProvider, "packageFragmentProvider");
        this.packageFragmentProvider = packageFragmentProvider;
    }
}

