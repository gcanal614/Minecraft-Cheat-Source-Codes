/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.UnsignedType;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class UnsignedTypes {
    private static final Set<Name> unsignedTypeNames;
    private static final HashMap<ClassId, ClassId> arrayClassIdToUnsignedClassId;
    private static final HashMap<ClassId, ClassId> unsignedClassIdToArrayClassId;
    private static final Set<Name> arrayClassesShortNames;
    public static final UnsignedTypes INSTANCE;

    public final boolean isShortNameOfUnsignedArray(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return arrayClassesShortNames.contains(name);
    }

    @Nullable
    public final ClassId getUnsignedClassIdByArrayClassId(@NotNull ClassId arrayClassId) {
        Intrinsics.checkNotNullParameter(arrayClassId, "arrayClassId");
        return arrayClassIdToUnsignedClassId.get(arrayClassId);
    }

    @JvmStatic
    public static final boolean isUnsignedType(@NotNull KotlinType type2) {
        Intrinsics.checkNotNullParameter(type2, "type");
        if (TypeUtils.noExpectedType(type2)) {
            return false;
        }
        ClassifierDescriptor classifierDescriptor = type2.getConstructor().getDeclarationDescriptor();
        if (classifierDescriptor == null) {
            return false;
        }
        Intrinsics.checkNotNullExpressionValue(classifierDescriptor, "type.constructor.declara\u2026escriptor ?: return false");
        ClassifierDescriptor descriptor2 = classifierDescriptor;
        return INSTANCE.isUnsignedClass(descriptor2);
    }

    public final boolean isUnsignedClass(@NotNull DeclarationDescriptor descriptor2) {
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        DeclarationDescriptor container = descriptor2.getContainingDeclaration();
        return container instanceof PackageFragmentDescriptor && Intrinsics.areEqual(((PackageFragmentDescriptor)container).getFqName(), KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME) && unsignedTypeNames.contains(descriptor2.getName());
    }

    private UnsignedTypes() {
    }

    /*
     * WARNING - void declaration
     */
    static {
        void var2_5;
        void $this$mapTo$iv;
        Name name;
        Collection collection;
        void $this$mapTo$iv$iv;
        UnsignedTypes unsignedTypes;
        INSTANCE = unsignedTypes = new UnsignedTypes();
        UnsignedType[] $this$map$iv322 = UnsignedType.values();
        boolean $i$f$map = false;
        UnsignedType[] unsignedTypeArray = $this$map$iv322;
        Collection destination$iv$iv = new ArrayList($this$map$iv322.length);
        boolean $i$f$mapTo = false;
        void var6_13 = $this$mapTo$iv$iv;
        int n = ((void)var6_13).length;
        for (int i = 0; i < n; ++i) {
            void it;
            void item$iv$iv;
            void var10_21 = item$iv$iv = var6_13[i];
            collection = destination$iv$iv;
            boolean bl = false;
            name = it.getTypeName();
            collection.add(name);
        }
        unsignedTypeNames = CollectionsKt.toSet((List)destination$iv$iv);
        boolean $this$map$iv322 = false;
        arrayClassIdToUnsignedClassId = new HashMap();
        $this$map$iv322 = false;
        unsignedClassIdToArrayClassId = new HashMap();
        UnsignedType[] $this$map$iv322 = UnsignedType.values();
        $i$f$map = false;
        Collection destination$iv = new LinkedHashSet();
        boolean $i$f$mapTo2 = false;
        void var4_10 = $this$mapTo$iv;
        int n2 = ((void)var4_10).length;
        for (int i = 0; i < n2; ++i) {
            void it;
            void item$iv;
            void var8_18 = item$iv = var4_10[i];
            collection = destination$iv;
            boolean bl = false;
            name = it.getArrayClassId().getShortClassName();
            collection.add(name);
        }
        arrayClassesShortNames = (Set)var2_5;
        for (UnsignedType unsignedType : UnsignedType.values()) {
            ((Map)arrayClassIdToUnsignedClassId).put(unsignedType.getArrayClassId(), unsignedType.getClassId());
            ((Map)unsignedClassIdToArrayClassId).put(unsignedType.getClassId(), unsignedType.getArrayClassId());
        }
    }
}

