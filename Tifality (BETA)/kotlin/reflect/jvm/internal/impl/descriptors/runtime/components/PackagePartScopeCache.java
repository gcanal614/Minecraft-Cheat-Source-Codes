/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.EmptyPackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectKotlinClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectKotlinClassFinder;
import kotlin.reflect.jvm.internal.impl.load.kotlin.DeserializedDescriptorResolver;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinderKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinJvmBinaryClass;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.ChainedMemberScope;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import org.jetbrains.annotations.NotNull;

public final class PackagePartScopeCache {
    private final ConcurrentHashMap<ClassId, MemberScope> cache;
    private final DeserializedDescriptorResolver resolver;
    private final ReflectKotlinClassFinder kotlinClassFinder;

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final MemberScope getPackagePartScope(@NotNull ReflectKotlinClass fileClass) {
        void $this$getOrPut$iv;
        Intrinsics.checkNotNullParameter(fileClass, "fileClass");
        ConcurrentMap concurrentMap = this.cache;
        ClassId key$iv = fileClass.getClassId();
        boolean $i$f$getOrPut = false;
        Object object = $this$getOrPut$iv.get(key$iv);
        if (object == null) {
            void $this$mapNotNullTo$iv$iv;
            List list;
            boolean bl;
            boolean bl2 = false;
            FqName fqName2 = fileClass.getClassId().getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(fqName2, "fileClass.classId.packageFqName");
            FqName fqName3 = fqName2;
            if (fileClass.getClassHeader().getKind() == KotlinClassHeader.Kind.MULTIFILE_CLASS) {
                void $this$mapNotNullTo$iv$iv2;
                Iterable $this$mapNotNull$iv = fileClass.getClassHeader().getMultifilePartNames();
                boolean $i$f$mapNotNull = false;
                Iterable iterable = $this$mapNotNull$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$mapNotNullTo = false;
                void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv2;
                boolean $i$f$forEach = false;
                Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
                while (iterator2.hasNext()) {
                    KotlinJvmBinaryClass kotlinJvmBinaryClass;
                    ClassId classId;
                    Object element$iv$iv$iv;
                    Object element$iv$iv = element$iv$iv$iv = iterator2.next();
                    boolean bl3 = false;
                    String partName = (String)element$iv$iv;
                    boolean bl4 = false;
                    JvmClassName jvmClassName = JvmClassName.byInternalName(partName);
                    Intrinsics.checkNotNullExpressionValue(jvmClassName, "JvmClassName.byInternalName(partName)");
                    Intrinsics.checkNotNullExpressionValue(ClassId.topLevel(jvmClassName.getFqNameForTopLevelClassMaybeWithDollars()), "ClassId.topLevel(JvmClas\u2026velClassMaybeWithDollars)");
                    if (KotlinClassFinderKt.findKotlinClass((KotlinClassFinder)this.kotlinClassFinder, classId) == null) continue;
                    boolean bl5 = false;
                    bl = false;
                    KotlinJvmBinaryClass it$iv$iv = kotlinJvmBinaryClass;
                    boolean bl6 = false;
                    destination$iv$iv.add(it$iv$iv);
                }
                list = (List)destination$iv$iv;
            } else {
                list = CollectionsKt.listOf(fileClass);
            }
            List parts = list;
            EmptyPackageFragmentDescriptor packageFragment = new EmptyPackageFragmentDescriptor(this.resolver.getComponents().getModuleDescriptor(), fqName3);
            Iterable $this$mapNotNull$iv = parts;
            boolean $i$f$mapNotNull = false;
            Iterable $i$f$mapNotNullTo = $this$mapNotNull$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo2 = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator3 = $this$forEach$iv$iv$iv.iterator();
            while (iterator3.hasNext()) {
                MemberScope memberScope2;
                Object element$iv$iv$iv;
                Object element$iv$iv = element$iv$iv$iv = iterator3.next();
                boolean bl7 = false;
                KotlinJvmBinaryClass part = (KotlinJvmBinaryClass)element$iv$iv;
                boolean bl8 = false;
                if (this.resolver.createKotlinPackagePartScope(packageFragment, part) == null) continue;
                bl = false;
                boolean bl9 = false;
                MemberScope it$iv$iv = memberScope2;
                boolean bl10 = false;
                destination$iv$iv.add(it$iv$iv);
            }
            List scopes = CollectionsKt.toList((List)destination$iv$iv);
            MemberScope memberScope3 = ChainedMemberScope.Companion.create("package " + fqName3 + " (" + fileClass + ')', scopes);
            boolean bl11 = false;
            boolean bl12 = false;
            MemberScope default$iv = memberScope3;
            boolean bl13 = false;
            object = $this$getOrPut$iv.putIfAbsent(key$iv, default$iv);
            if (object == null) {
                object = default$iv;
            }
        }
        Intrinsics.checkNotNullExpressionValue(object, "cache.getOrPut(fileClass\u2026ileClass)\", scopes)\n    }");
        return (MemberScope)object;
    }

    public PackagePartScopeCache(@NotNull DeserializedDescriptorResolver resolver, @NotNull ReflectKotlinClassFinder kotlinClassFinder) {
        Intrinsics.checkNotNullParameter(resolver, "resolver");
        Intrinsics.checkNotNullParameter(kotlinClassFinder, "kotlinClassFinder");
        this.resolver = resolver;
        this.kotlinClassFinder = kotlinClassFinder;
        this.cache = new ConcurrentHashMap();
    }
}

