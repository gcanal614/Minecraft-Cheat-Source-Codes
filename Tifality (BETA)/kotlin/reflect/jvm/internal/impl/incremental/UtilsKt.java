/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.incremental;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LocationInfo;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupTracker;
import kotlin.reflect.jvm.internal.impl.incremental.components.Position;
import kotlin.reflect.jvm.internal.impl.incremental.components.ScopeKind;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import org.jetbrains.annotations.NotNull;

public final class UtilsKt {
    public static final void record(@NotNull LookupTracker $this$record, @NotNull LookupLocation from, @NotNull ClassDescriptor scopeOwner, @NotNull Name name) {
        Intrinsics.checkNotNullParameter($this$record, "$this$record");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(scopeOwner, "scopeOwner");
        Intrinsics.checkNotNullParameter(name, "name");
        if ($this$record == LookupTracker.DO_NOTHING.INSTANCE) {
            return;
        }
        LocationInfo locationInfo = from.getLocation();
        if (locationInfo == null) {
            return;
        }
        LocationInfo location = locationInfo;
        Position position = $this$record.getRequiresPosition() ? location.getPosition() : Position.Companion.getNO_POSITION();
        String string = location.getFilePath();
        String string2 = DescriptorUtils.getFqName(scopeOwner).asString();
        Intrinsics.checkNotNullExpressionValue(string2, "DescriptorUtils.getFqName(scopeOwner).asString()");
        String string3 = name.asString();
        Intrinsics.checkNotNullExpressionValue(string3, "name.asString()");
        $this$record.record(string, position, string2, ScopeKind.CLASSIFIER, string3);
    }

    public static final void record(@NotNull LookupTracker $this$record, @NotNull LookupLocation from, @NotNull PackageFragmentDescriptor scopeOwner, @NotNull Name name) {
        Intrinsics.checkNotNullParameter($this$record, "$this$record");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(scopeOwner, "scopeOwner");
        Intrinsics.checkNotNullParameter(name, "name");
        String string = scopeOwner.getFqName().asString();
        Intrinsics.checkNotNullExpressionValue(string, "scopeOwner.fqName.asString()");
        String string2 = name.asString();
        Intrinsics.checkNotNullExpressionValue(string2, "name.asString()");
        UtilsKt.recordPackageLookup($this$record, from, string, string2);
    }

    public static final void recordPackageLookup(@NotNull LookupTracker $this$recordPackageLookup, @NotNull LookupLocation from, @NotNull String packageFqName, @NotNull String name) {
        Intrinsics.checkNotNullParameter($this$recordPackageLookup, "$this$recordPackageLookup");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name, "name");
        if ($this$recordPackageLookup == LookupTracker.DO_NOTHING.INSTANCE) {
            return;
        }
        LocationInfo locationInfo = from.getLocation();
        if (locationInfo == null) {
            return;
        }
        LocationInfo location = locationInfo;
        Position position = $this$recordPackageLookup.getRequiresPosition() ? location.getPosition() : Position.Companion.getNO_POSITION();
        $this$recordPackageLookup.record(location.getFilePath(), position, packageFqName, ScopeKind.PACKAGE, name);
    }
}

