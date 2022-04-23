/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.annotations.NotNull;

public final class CompanionObjectMapping {
    private static final LinkedHashSet<ClassId> classIds;
    public static final CompanionObjectMapping INSTANCE;

    @NotNull
    public final Set<ClassId> allClassesWithIntrinsicCompanions() {
        Set<ClassId> set = Collections.unmodifiableSet((Set)classIds);
        Intrinsics.checkNotNullExpressionValue(set, "Collections.unmodifiableSet(classIds)");
        return set;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isMappedIntrinsicCompanionObject(@NotNull ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        if (!DescriptorUtils.isCompanionObject(classDescriptor)) return false;
        ClassId classId = DescriptorUtilsKt.getClassId(classDescriptor);
        if (!CollectionsKt.contains((Iterable)classIds, classId != null ? classId.getOuterClassId() : null)) return false;
        return true;
    }

    private CompanionObjectMapping() {
    }

    /*
     * WARNING - void declaration
     */
    static {
        void var2_3;
        void $this$mapTo$iv;
        Object object;
        Collection collection;
        void $this$mapTo$iv$iv;
        CompanionObjectMapping companionObjectMapping;
        INSTANCE = companionObjectMapping = new CompanionObjectMapping();
        Set<PrimitiveType> set = PrimitiveType.NUMBER_TYPES;
        Intrinsics.checkNotNullExpressionValue(set, "PrimitiveType.NUMBER_TYPES");
        Iterable $this$map$iv = set;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void p1;
            PrimitiveType primitiveType = (PrimitiveType)((Object)item$iv$iv);
            collection = destination$iv$iv;
            boolean bl = false;
            object = KotlinBuiltIns.getPrimitiveFqName((PrimitiveType)p1);
            collection.add(object);
        }
        $this$map$iv = CollectionsKt.plus((Collection)CollectionsKt.plus((Collection)CollectionsKt.plus((Collection)((List)destination$iv$iv), KotlinBuiltIns.FQ_NAMES.string.toSafe()), KotlinBuiltIns.FQ_NAMES._boolean.toSafe()), KotlinBuiltIns.FQ_NAMES._enum.toSafe());
        $i$f$map = false;
        Collection destination$iv = new LinkedHashSet();
        boolean $i$f$mapTo2 = false;
        for (Object item$iv : $this$mapTo$iv) {
            void p1;
            FqName fqName2 = (FqName)item$iv;
            collection = destination$iv;
            boolean bl = false;
            object = ClassId.topLevel((FqName)p1);
            collection.add(object);
        }
        classIds = (LinkedHashSet)var2_3;
    }
}

