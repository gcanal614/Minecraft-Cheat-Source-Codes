/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DescriptorUtilKt {
    @Nullable
    public static final ClassDescriptor resolveClassByFqName(@NotNull ModuleDescriptor $this$resolveClassByFqName, @NotNull FqName fqName2, @NotNull LookupLocation lookupLocation) {
        ClassifierDescriptor classifierDescriptor;
        ClassifierDescriptor classifierDescriptor2;
        Intrinsics.checkNotNullParameter($this$resolveClassByFqName, "$this$resolveClassByFqName");
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        Intrinsics.checkNotNullParameter(lookupLocation, "lookupLocation");
        if (fqName2.isRoot()) {
            return null;
        }
        FqName fqName3 = fqName2.parent();
        Intrinsics.checkNotNullExpressionValue(fqName3, "fqName.parent()");
        MemberScope memberScope2 = $this$resolveClassByFqName.getPackage(fqName3).getMemberScope();
        Name name = fqName2.shortName();
        Intrinsics.checkNotNullExpressionValue(name, "fqName.shortName()");
        ClassifierDescriptor classifierDescriptor3 = memberScope2.getContributedClassifier(name, lookupLocation);
        if (!(classifierDescriptor3 instanceof ClassDescriptor)) {
            classifierDescriptor3 = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor)classifierDescriptor3;
        if (classDescriptor != null) {
            ClassDescriptor classDescriptor2 = classDescriptor;
            boolean bl = false;
            boolean bl2 = false;
            ClassDescriptor it = classDescriptor2;
            boolean bl3 = false;
            return it;
        }
        FqName fqName4 = fqName2.parent();
        Intrinsics.checkNotNullExpressionValue(fqName4, "fqName.parent()");
        Object object = DescriptorUtilKt.resolveClassByFqName($this$resolveClassByFqName, fqName4, lookupLocation);
        if (object != null && (object = object.getUnsubstitutedInnerClassesScope()) != null) {
            Name name2 = fqName2.shortName();
            Intrinsics.checkNotNullExpressionValue(name2, "fqName.shortName()");
            classifierDescriptor2 = object.getContributedClassifier(name2, lookupLocation);
        } else {
            classifierDescriptor2 = classifierDescriptor = null;
        }
        if (!(classifierDescriptor2 instanceof ClassDescriptor)) {
            classifierDescriptor = null;
        }
        return (ClassDescriptor)classifierDescriptor;
    }
}

