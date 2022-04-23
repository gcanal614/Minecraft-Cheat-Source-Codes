/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.ResolutionAnchorProvider;
import kotlin.reflect.jvm.internal.impl.resolve.ResolutionAnchorProviderKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FindClassInModuleKt {
    @Nullable
    public static final ClassifierDescriptor findClassifierAcrossModuleDependencies(@NotNull ModuleDescriptor $this$findClassifierAcrossModuleDependencies, @NotNull ClassId classId) {
        ClassifierDescriptor classifierDescriptor;
        block21: {
            ModuleDescriptor anchor$iv;
            Intrinsics.checkNotNullParameter($this$findClassifierAcrossModuleDependencies, "$this$findClassifierAcrossModuleDependencies");
            Intrinsics.checkNotNullParameter(classId, "classId");
            ModuleDescriptor $this$withAnchorFallback$iv = $this$findClassifierAcrossModuleDependencies;
            boolean $i$f$withAnchorFallback = false;
            ResolutionAnchorProvider resolutionAnchorProvider = $this$withAnchorFallback$iv.getCapability(ResolutionAnchorProviderKt.getRESOLUTION_ANCHOR_PROVIDER_CAPABILITY());
            ModuleDescriptor moduleDescriptor = anchor$iv = resolutionAnchorProvider != null ? resolutionAnchorProvider.getResolutionAnchor($this$withAnchorFallback$iv) : null;
            if (anchor$iv == null) {
                ModuleDescriptor $this$withAnchorFallback = $this$withAnchorFallback$iv;
                boolean bl = false;
                FqName fqName2 = classId.getPackageFqName();
                Intrinsics.checkNotNullExpressionValue(fqName2, "classId.packageFqName");
                PackageViewDescriptor packageViewDescriptor = $this$withAnchorFallback.getPackage(fqName2);
                List<Name> list = classId.getRelativeClassName().pathSegments();
                Intrinsics.checkNotNullExpressionValue(list, "classId.relativeClassName.pathSegments()");
                List<Name> segments = list;
                MemberScope memberScope2 = packageViewDescriptor.getMemberScope();
                Name name = CollectionsKt.first(segments);
                Intrinsics.checkNotNullExpressionValue(name, "segments.first()");
                ClassifierDescriptor classifierDescriptor2 = memberScope2.getContributedClassifier(name, NoLookupLocation.FROM_DESERIALIZATION);
                if (classifierDescriptor2 == null) {
                    classifierDescriptor = null;
                } else {
                    ClassifierDescriptor topLevelClass;
                    ClassifierDescriptor result2 = topLevelClass = classifierDescriptor2;
                    for (Name name2 : segments.subList(1, segments.size())) {
                        if (!(result2 instanceof ClassDescriptor)) {
                            classifierDescriptor = null;
                            break block21;
                        }
                        MemberScope memberScope3 = ((ClassDescriptor)result2).getUnsubstitutedInnerClassesScope();
                        Name name3 = name2;
                        Intrinsics.checkNotNullExpressionValue(name3, "name");
                        ClassifierDescriptor classifierDescriptor3 = memberScope3.getContributedClassifier(name3, NoLookupLocation.FROM_DESERIALIZATION);
                        if (!(classifierDescriptor3 instanceof ClassDescriptor)) {
                            classifierDescriptor3 = null;
                        }
                        ClassDescriptor classDescriptor = (ClassDescriptor)classifierDescriptor3;
                        if (classDescriptor == null) {
                            classifierDescriptor = null;
                            break block21;
                        }
                        result2 = classDescriptor;
                    }
                    classifierDescriptor = result2;
                }
            } else {
                ClassifierDescriptor result3;
                ClassifierDescriptor topLevelClass;
                ClassifierDescriptor classifierDescriptor4;
                List<Name> segments;
                PackageViewDescriptor packageViewDescriptor;
                boolean bl;
                ModuleDescriptor $this$withAnchorFallback;
                block22: {
                    $this$withAnchorFallback = $this$withAnchorFallback$iv;
                    bl = false;
                    FqName fqName3 = classId.getPackageFqName();
                    Intrinsics.checkNotNullExpressionValue(fqName3, "classId.packageFqName");
                    packageViewDescriptor = $this$withAnchorFallback.getPackage(fqName3);
                    List<Name> list = classId.getRelativeClassName().pathSegments();
                    Intrinsics.checkNotNullExpressionValue(list, "classId.relativeClassName.pathSegments()");
                    segments = list;
                    MemberScope memberScope4 = packageViewDescriptor.getMemberScope();
                    Name name = CollectionsKt.first(segments);
                    Intrinsics.checkNotNullExpressionValue(name, "segments.first()");
                    ClassifierDescriptor classifierDescriptor5 = memberScope4.getContributedClassifier(name, NoLookupLocation.FROM_DESERIALIZATION);
                    if (classifierDescriptor5 == null) {
                        classifierDescriptor4 = null;
                    } else {
                        result3 = topLevelClass = classifierDescriptor5;
                        for (Name name4 : segments.subList(1, segments.size())) {
                            if (!(result3 instanceof ClassDescriptor)) {
                                classifierDescriptor4 = null;
                                break block22;
                            }
                            MemberScope memberScope5 = ((ClassDescriptor)result3).getUnsubstitutedInnerClassesScope();
                            Name name5 = name4;
                            Intrinsics.checkNotNullExpressionValue(name5, "name");
                            ClassifierDescriptor classifierDescriptor6 = memberScope5.getContributedClassifier(name5, NoLookupLocation.FROM_DESERIALIZATION);
                            if (!(classifierDescriptor6 instanceof ClassDescriptor)) {
                                classifierDescriptor6 = null;
                            }
                            ClassDescriptor classDescriptor = (ClassDescriptor)classifierDescriptor6;
                            if (classDescriptor == null) {
                                classifierDescriptor4 = null;
                                break block22;
                            }
                            result3 = classDescriptor;
                        }
                        classifierDescriptor4 = classifierDescriptor = result3;
                    }
                }
                if (classifierDescriptor4 == null) {
                    $this$withAnchorFallback = anchor$iv;
                    bl = false;
                    FqName fqName4 = classId.getPackageFqName();
                    Intrinsics.checkNotNullExpressionValue(fqName4, "classId.packageFqName");
                    packageViewDescriptor = $this$withAnchorFallback.getPackage(fqName4);
                    List<Name> list = classId.getRelativeClassName().pathSegments();
                    Intrinsics.checkNotNullExpressionValue(list, "classId.relativeClassName.pathSegments()");
                    segments = list;
                    MemberScope memberScope6 = packageViewDescriptor.getMemberScope();
                    Name name = CollectionsKt.first(segments);
                    Intrinsics.checkNotNullExpressionValue(name, "segments.first()");
                    ClassifierDescriptor classifierDescriptor7 = memberScope6.getContributedClassifier(name, NoLookupLocation.FROM_DESERIALIZATION);
                    if (classifierDescriptor7 == null) {
                        classifierDescriptor = null;
                    } else {
                        result3 = topLevelClass = classifierDescriptor7;
                        for (Name name4 : segments.subList(1, segments.size())) {
                            if (!(result3 instanceof ClassDescriptor)) {
                                classifierDescriptor = null;
                                break block21;
                            }
                            MemberScope memberScope7 = ((ClassDescriptor)result3).getUnsubstitutedInnerClassesScope();
                            Name name6 = name4;
                            Intrinsics.checkNotNullExpressionValue(name6, "name");
                            ClassifierDescriptor classifierDescriptor8 = memberScope7.getContributedClassifier(name6, NoLookupLocation.FROM_DESERIALIZATION);
                            if (!(classifierDescriptor8 instanceof ClassDescriptor)) {
                                classifierDescriptor8 = null;
                            }
                            ClassDescriptor classDescriptor = (ClassDescriptor)classifierDescriptor8;
                            if (classDescriptor == null) {
                                classifierDescriptor = null;
                                break block21;
                            }
                            result3 = classDescriptor;
                        }
                        classifierDescriptor = result3;
                    }
                }
            }
        }
        return classifierDescriptor;
    }

    @Nullable
    public static final ClassDescriptor findClassAcrossModuleDependencies(@NotNull ModuleDescriptor $this$findClassAcrossModuleDependencies, @NotNull ClassId classId) {
        Intrinsics.checkNotNullParameter($this$findClassAcrossModuleDependencies, "$this$findClassAcrossModuleDependencies");
        Intrinsics.checkNotNullParameter(classId, "classId");
        ClassifierDescriptor classifierDescriptor = FindClassInModuleKt.findClassifierAcrossModuleDependencies($this$findClassAcrossModuleDependencies, classId);
        if (!(classifierDescriptor instanceof ClassDescriptor)) {
            classifierDescriptor = null;
        }
        return (ClassDescriptor)classifierDescriptor;
    }

    @NotNull
    public static final ClassDescriptor findNonGenericClassAcrossDependencies(@NotNull ModuleDescriptor $this$findNonGenericClassAcrossDependencies, @NotNull ClassId classId, @NotNull NotFoundClasses notFoundClasses) {
        Intrinsics.checkNotNullParameter($this$findNonGenericClassAcrossDependencies, "$this$findNonGenericClassAcrossDependencies");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        ClassDescriptor existingClass = FindClassInModuleKt.findClassAcrossModuleDependencies($this$findNonGenericClassAcrossDependencies, classId);
        if (existingClass != null) {
            return existingClass;
        }
        List<Integer> typeParametersCount2 = SequencesKt.toList(SequencesKt.map(SequencesKt.generateSequence(classId, (Function1)findNonGenericClassAcrossDependencies.typeParametersCount.1.INSTANCE), findNonGenericClassAcrossDependencies.typeParametersCount.2.INSTANCE));
        return notFoundClasses.getClass(classId, typeParametersCount2);
    }

    @Nullable
    public static final TypeAliasDescriptor findTypeAliasAcrossModuleDependencies(@NotNull ModuleDescriptor $this$findTypeAliasAcrossModuleDependencies, @NotNull ClassId classId) {
        Intrinsics.checkNotNullParameter($this$findTypeAliasAcrossModuleDependencies, "$this$findTypeAliasAcrossModuleDependencies");
        Intrinsics.checkNotNullParameter(classId, "classId");
        ClassifierDescriptor classifierDescriptor = FindClassInModuleKt.findClassifierAcrossModuleDependencies($this$findTypeAliasAcrossModuleDependencies, classId);
        if (!(classifierDescriptor instanceof TypeAliasDescriptor)) {
            classifierDescriptor = null;
        }
        return (TypeAliasDescriptor)classifierDescriptor;
    }
}

