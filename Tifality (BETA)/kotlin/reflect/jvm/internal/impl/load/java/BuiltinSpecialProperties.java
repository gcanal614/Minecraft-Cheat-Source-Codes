/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.BuiltinSpecialProperties;
import kotlin.reflect.jvm.internal.impl.load.java.SpecialBuiltinMembers;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BuiltinSpecialProperties {
    @NotNull
    private static final Map<FqName, Name> PROPERTY_FQ_NAME_TO_JVM_GETTER_NAME_MAP;
    private static final Map<Name, List<Name>> GETTER_JVM_NAME_TO_PROPERTIES_SHORT_NAME_MAP;
    private static final Set<FqName> SPECIAL_FQ_NAMES;
    @NotNull
    private static final Set<Name> SPECIAL_SHORT_NAMES;
    public static final BuiltinSpecialProperties INSTANCE;

    @NotNull
    public final Set<Name> getSPECIAL_SHORT_NAMES$descriptors_jvm() {
        return SPECIAL_SHORT_NAMES;
    }

    public final boolean hasBuiltinSpecialPropertyFqName(@NotNull CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "callableMemberDescriptor");
        if (!SPECIAL_SHORT_NAMES.contains(callableMemberDescriptor.getName())) {
            return false;
        }
        return this.hasBuiltinSpecialPropertyFqNameImpl(callableMemberDescriptor);
    }

    private final boolean hasBuiltinSpecialPropertyFqNameImpl(CallableMemberDescriptor $this$hasBuiltinSpecialPropertyFqNameImpl) {
        boolean bl;
        block5: {
            if (CollectionsKt.contains((Iterable)SPECIAL_FQ_NAMES, DescriptorUtilsKt.fqNameOrNull($this$hasBuiltinSpecialPropertyFqNameImpl)) && $this$hasBuiltinSpecialPropertyFqNameImpl.getValueParameters().isEmpty()) {
                return true;
            }
            if (!KotlinBuiltIns.isBuiltIn($this$hasBuiltinSpecialPropertyFqNameImpl)) {
                return false;
            }
            Collection<? extends CallableMemberDescriptor> collection = $this$hasBuiltinSpecialPropertyFqNameImpl.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(collection, "overriddenDescriptors");
            Iterable $this$any$iv = collection;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    CallableMemberDescriptor it = (CallableMemberDescriptor)element$iv;
                    boolean bl2 = false;
                    CallableMemberDescriptor callableMemberDescriptor = it;
                    Intrinsics.checkNotNullExpressionValue(callableMemberDescriptor, "it");
                    if (!INSTANCE.hasBuiltinSpecialPropertyFqName(callableMemberDescriptor)) continue;
                    bl = true;
                    break block5;
                }
                bl = false;
            }
        }
        return bl;
    }

    @NotNull
    public final List<Name> getPropertyNameCandidatesBySpecialGetterName(@NotNull Name name1) {
        Intrinsics.checkNotNullParameter(name1, "name1");
        List<Name> list = GETTER_JVM_NAME_TO_PROPERTIES_SHORT_NAME_MAP.get(name1);
        if (list == null) {
            list = CollectionsKt.emptyList();
        }
        return list;
    }

    @Nullable
    public final String getBuiltinSpecialPropertyGetterName(@NotNull CallableMemberDescriptor $this$getBuiltinSpecialPropertyGetterName) {
        Intrinsics.checkNotNullParameter($this$getBuiltinSpecialPropertyGetterName, "$this$getBuiltinSpecialPropertyGetterName");
        boolean bl = KotlinBuiltIns.isBuiltIn($this$getBuiltinSpecialPropertyGetterName);
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl3 = false;
            String string = "This method is defined only for builtin members, but " + $this$getBuiltinSpecialPropertyGetterName + " found";
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        CallableMemberDescriptor callableMemberDescriptor = DescriptorUtilsKt.firstOverridden$default(DescriptorUtilsKt.getPropertyIfAccessor($this$getBuiltinSpecialPropertyGetterName), false, getBuiltinSpecialPropertyGetterName.descriptor.1.INSTANCE, 1, null);
        if (callableMemberDescriptor == null) {
            return null;
        }
        CallableMemberDescriptor descriptor2 = callableMemberDescriptor;
        Name name = PROPERTY_FQ_NAME_TO_JVM_GETTER_NAME_MAP.get(DescriptorUtilsKt.getFqNameSafe(descriptor2));
        return name != null ? name.asString() : null;
    }

    private BuiltinSpecialProperties() {
    }

    /*
     * WARNING - void declaration
     */
    static {
        Object list$iv$iv;
        Iterable $this$groupByTo$iv$iv;
        Object object;
        Pair it;
        Object object2;
        Iterable $this$mapTo$iv$iv;
        BuiltinSpecialProperties builtinSpecialProperties;
        INSTANCE = builtinSpecialProperties = new BuiltinSpecialProperties();
        Pair[] pairArray = new Pair[8];
        FqNameUnsafe fqNameUnsafe = KotlinBuiltIns.FQ_NAMES._enum;
        Intrinsics.checkNotNullExpressionValue(fqNameUnsafe, "BUILTIN_NAMES._enum");
        pairArray[0] = TuplesKt.to(SpecialBuiltinMembers.access$childSafe(fqNameUnsafe, "name"), Name.identifier("name"));
        FqNameUnsafe fqNameUnsafe2 = KotlinBuiltIns.FQ_NAMES._enum;
        Intrinsics.checkNotNullExpressionValue(fqNameUnsafe2, "BUILTIN_NAMES._enum");
        pairArray[1] = TuplesKt.to(SpecialBuiltinMembers.access$childSafe(fqNameUnsafe2, "ordinal"), Name.identifier("ordinal"));
        FqName fqName2 = KotlinBuiltIns.FQ_NAMES.collection;
        Intrinsics.checkNotNullExpressionValue(fqName2, "BUILTIN_NAMES.collection");
        pairArray[2] = TuplesKt.to(SpecialBuiltinMembers.access$child(fqName2, "size"), Name.identifier("size"));
        FqName fqName3 = KotlinBuiltIns.FQ_NAMES.map;
        Intrinsics.checkNotNullExpressionValue(fqName3, "BUILTIN_NAMES.map");
        pairArray[3] = TuplesKt.to(SpecialBuiltinMembers.access$child(fqName3, "size"), Name.identifier("size"));
        FqNameUnsafe fqNameUnsafe3 = KotlinBuiltIns.FQ_NAMES.charSequence;
        Intrinsics.checkNotNullExpressionValue(fqNameUnsafe3, "BUILTIN_NAMES.charSequence");
        pairArray[4] = TuplesKt.to(SpecialBuiltinMembers.access$childSafe(fqNameUnsafe3, "length"), Name.identifier("length"));
        FqName fqName4 = KotlinBuiltIns.FQ_NAMES.map;
        Intrinsics.checkNotNullExpressionValue(fqName4, "BUILTIN_NAMES.map");
        pairArray[5] = TuplesKt.to(SpecialBuiltinMembers.access$child(fqName4, "keys"), Name.identifier("keySet"));
        FqName fqName5 = KotlinBuiltIns.FQ_NAMES.map;
        Intrinsics.checkNotNullExpressionValue(fqName5, "BUILTIN_NAMES.map");
        pairArray[6] = TuplesKt.to(SpecialBuiltinMembers.access$child(fqName5, "values"), Name.identifier("values"));
        FqName fqName6 = KotlinBuiltIns.FQ_NAMES.map;
        Intrinsics.checkNotNullExpressionValue(fqName6, "BUILTIN_NAMES.map");
        pairArray[7] = TuplesKt.to(SpecialBuiltinMembers.access$child(fqName6, "entries"), Name.identifier("entrySet"));
        PROPERTY_FQ_NAME_TO_JVM_GETTER_NAME_MAP = MapsKt.mapOf(pairArray);
        Iterable $this$map$iv = PROPERTY_FQ_NAME_TO_JVM_GETTER_NAME_MAP.entrySet();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Object destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            Map.Entry entry = (Map.Entry)item$iv$iv;
            object2 = destination$iv$iv;
            boolean bl = false;
            object = new Pair(((FqName)it.getKey()).shortName(), it.getValue());
            object2.add(object);
        }
        Iterable $this$groupBy$iv = (List)destination$iv$iv;
        boolean $i$f$groupBy = false;
        $this$mapTo$iv$iv = $this$groupBy$iv;
        destination$iv$iv = new LinkedHashMap();
        boolean $i$f$groupByTo = false;
        for (Object element$iv$iv : $this$groupByTo$iv$iv) {
            void it2;
            Object object3;
            it = (Pair)element$iv$iv;
            boolean $i$a$-groupBy-BuiltinSpecialProperties$GETTER_JVM_NAME_TO_PROPERTIES_SHORT_NAME_MAP$42 = false;
            Object b2 = it.getSecond();
            Intrinsics.checkNotNullExpressionValue(b2, "it.second");
            Name key$iv$iv = (Name)b2;
            Object $this$getOrPut$iv$iv$iv = destination$iv$iv;
            boolean $i$f$getOrPut = false;
            Object value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
            if (value$iv$iv$iv == null) {
                boolean bl = false;
                List answer$iv$iv$iv = new ArrayList();
                $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                object3 = answer$iv$iv$iv;
            } else {
                object3 = value$iv$iv$iv;
            }
            list$iv$iv = (List)object3;
            Pair $i$a$-groupBy-BuiltinSpecialProperties$GETTER_JVM_NAME_TO_PROPERTIES_SHORT_NAME_MAP$42 = (Pair)element$iv$iv;
            object2 = list$iv$iv;
            boolean bl = false;
            object = (Name)it2.getFirst();
            object2.add(object);
        }
        GETTER_JVM_NAME_TO_PROPERTIES_SHORT_NAME_MAP = destination$iv$iv;
        SPECIAL_FQ_NAMES = PROPERTY_FQ_NAME_TO_JVM_GETTER_NAME_MAP.keySet();
        $this$map$iv = SPECIAL_FQ_NAMES;
        $i$f$map = false;
        $this$groupByTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void p1;
            list$iv$iv = (FqName)item$iv$iv;
            object2 = destination$iv$iv;
            boolean bl = false;
            object = p1.shortName();
            object2.add(object);
        }
        SPECIAL_SHORT_NAMES = CollectionsKt.toSet((List)destination$iv$iv);
    }
}

