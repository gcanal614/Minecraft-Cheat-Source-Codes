/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.NameAndSignature;
import kotlin.reflect.jvm.internal.impl.load.java.SpecialBuiltinMembers;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BuiltinMethodsWithDifferentJvmName {
    private static final NameAndSignature REMOVE_AT_NAME_AND_SIGNATURE;
    private static final Map<NameAndSignature, Name> NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP;
    private static final Map<String, Name> SIGNATURE_TO_JVM_REPRESENTATION_NAME;
    @NotNull
    private static final List<Name> ORIGINAL_SHORT_NAMES;
    private static final Map<Name, List<Name>> JVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP;
    public static final BuiltinMethodsWithDifferentJvmName INSTANCE;

    @NotNull
    public final List<Name> getORIGINAL_SHORT_NAMES() {
        return ORIGINAL_SHORT_NAMES;
    }

    public final boolean getSameAsRenamedInJvmBuiltin(@NotNull Name $this$sameAsRenamedInJvmBuiltin) {
        Intrinsics.checkNotNullParameter($this$sameAsRenamedInJvmBuiltin, "$this$sameAsRenamedInJvmBuiltin");
        return ORIGINAL_SHORT_NAMES.contains($this$sameAsRenamedInJvmBuiltin);
    }

    @Nullable
    public final Name getJvmName(@NotNull SimpleFunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        String string = MethodSignatureMappingKt.computeJvmSignature(functionDescriptor);
        if (string == null) {
            return null;
        }
        return SIGNATURE_TO_JVM_REPRESENTATION_NAME.get(string);
    }

    public final boolean isBuiltinFunctionWithDifferentNameInJvm(@NotNull SimpleFunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        return KotlinBuiltIns.isBuiltIn(functionDescriptor) && DescriptorUtilsKt.firstOverridden$default(functionDescriptor, false, new Function1<CallableMemberDescriptor, Boolean>(functionDescriptor){
            final /* synthetic */ SimpleFunctionDescriptor $functionDescriptor;

            public final boolean invoke(@NotNull CallableMemberDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Map map2 = BuiltinMethodsWithDifferentJvmName.access$getSIGNATURE_TO_JVM_REPRESENTATION_NAME$p(BuiltinMethodsWithDifferentJvmName.INSTANCE);
                String string = MethodSignatureMappingKt.computeJvmSignature(this.$functionDescriptor);
                boolean bl = false;
                Map map3 = map2;
                if (map3 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<K, *>");
                }
                return map3.containsKey(string);
            }
            {
                this.$functionDescriptor = simpleFunctionDescriptor;
                super(1);
            }
        }, 1, null) != null;
    }

    @NotNull
    public final List<Name> getBuiltinFunctionNamesByJvmName(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        List<Name> list = JVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP.get(name);
        if (list == null) {
            list = CollectionsKt.emptyList();
        }
        return list;
    }

    public final boolean isRemoveAtByIndex(@NotNull SimpleFunctionDescriptor $this$isRemoveAtByIndex) {
        Intrinsics.checkNotNullParameter($this$isRemoveAtByIndex, "$this$isRemoveAtByIndex");
        return Intrinsics.areEqual($this$isRemoveAtByIndex.getName().asString(), "removeAt") && Intrinsics.areEqual(MethodSignatureMappingKt.computeJvmSignature($this$isRemoveAtByIndex), REMOVE_AT_NAME_AND_SIGNATURE.getSignature());
    }

    private BuiltinMethodsWithDifferentJvmName() {
    }

    /*
     * WARNING - void declaration
     */
    static {
        void $this$groupByTo$iv$iv;
        Object it;
        Iterable $this$mapTo$iv$iv;
        Object object;
        Object object2;
        Iterable $this$mapKeysTo$iv$iv;
        BuiltinMethodsWithDifferentJvmName builtinMethodsWithDifferentJvmName;
        INSTANCE = builtinMethodsWithDifferentJvmName = new BuiltinMethodsWithDifferentJvmName();
        String string = JvmPrimitiveType.INT.getDesc();
        Intrinsics.checkNotNullExpressionValue(string, "JvmPrimitiveType.INT.desc");
        REMOVE_AT_NAME_AND_SIGNATURE = SpecialBuiltinMembers.access$method("java/util/List", "removeAt", string, "Ljava/lang/Object;");
        boolean $i$f$signatures = false;
        SignatureBuildingComponents signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
        boolean bl = false;
        boolean bl2 = false;
        SignatureBuildingComponents $this$signatures = signatureBuildingComponents;
        boolean bl3 = false;
        Pair[] pairArray = new Pair[8];
        String string2 = $this$signatures.javaLang("Number");
        String string3 = JvmPrimitiveType.BYTE.getDesc();
        Intrinsics.checkNotNullExpressionValue(string3, "JvmPrimitiveType.BYTE.desc");
        pairArray[0] = TuplesKt.to(SpecialBuiltinMembers.access$method(string2, "toByte", "", string3), Name.identifier("byteValue"));
        String string4 = $this$signatures.javaLang("Number");
        String string5 = JvmPrimitiveType.SHORT.getDesc();
        Intrinsics.checkNotNullExpressionValue(string5, "JvmPrimitiveType.SHORT.desc");
        pairArray[1] = TuplesKt.to(SpecialBuiltinMembers.access$method(string4, "toShort", "", string5), Name.identifier("shortValue"));
        String string6 = $this$signatures.javaLang("Number");
        String string7 = JvmPrimitiveType.INT.getDesc();
        Intrinsics.checkNotNullExpressionValue(string7, "JvmPrimitiveType.INT.desc");
        pairArray[2] = TuplesKt.to(SpecialBuiltinMembers.access$method(string6, "toInt", "", string7), Name.identifier("intValue"));
        String string8 = $this$signatures.javaLang("Number");
        String string9 = JvmPrimitiveType.LONG.getDesc();
        Intrinsics.checkNotNullExpressionValue(string9, "JvmPrimitiveType.LONG.desc");
        pairArray[3] = TuplesKt.to(SpecialBuiltinMembers.access$method(string8, "toLong", "", string9), Name.identifier("longValue"));
        String string10 = $this$signatures.javaLang("Number");
        String string11 = JvmPrimitiveType.FLOAT.getDesc();
        Intrinsics.checkNotNullExpressionValue(string11, "JvmPrimitiveType.FLOAT.desc");
        pairArray[4] = TuplesKt.to(SpecialBuiltinMembers.access$method(string10, "toFloat", "", string11), Name.identifier("floatValue"));
        String string12 = $this$signatures.javaLang("Number");
        String string13 = JvmPrimitiveType.DOUBLE.getDesc();
        Intrinsics.checkNotNullExpressionValue(string13, "JvmPrimitiveType.DOUBLE.desc");
        pairArray[5] = TuplesKt.to(SpecialBuiltinMembers.access$method(string12, "toDouble", "", string13), Name.identifier("doubleValue"));
        pairArray[6] = TuplesKt.to(REMOVE_AT_NAME_AND_SIGNATURE, Name.identifier("remove"));
        String string14 = $this$signatures.javaLang("CharSequence");
        String string15 = JvmPrimitiveType.INT.getDesc();
        Intrinsics.checkNotNullExpressionValue(string15, "JvmPrimitiveType.INT.desc");
        String string16 = JvmPrimitiveType.CHAR.getDesc();
        Intrinsics.checkNotNullExpressionValue(string16, "JvmPrimitiveType.CHAR.desc");
        pairArray[7] = TuplesKt.to(SpecialBuiltinMembers.access$method(string14, "get", string15, string16), Name.identifier("charAt"));
        Map<NameAndSignature, Name> $this$mapKeys$iv = NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP = MapsKt.mapOf(pairArray);
        boolean $i$f$mapKeys = false;
        Map<NameAndSignature, Name> map2 = $this$mapKeys$iv;
        Object destination$iv$iv = new LinkedHashMap(MapsKt.mapCapacity($this$mapKeys$iv.size()));
        boolean $i$f$mapKeysTo = false;
        Iterable $this$associateByTo$iv$iv$iv = $this$mapKeysTo$iv$iv.entrySet();
        boolean $i$f$associateByTo = false;
        for (Object element$iv$iv$iv : $this$associateByTo$iv$iv$iv) {
            void it$iv$iv;
            Object it2;
            Map.Entry entry = (Map.Entry)element$iv$iv$iv;
            object2 = destination$iv$iv;
            boolean bl4 = false;
            object = ((NameAndSignature)it2.getKey()).getSignature();
            Map.Entry entry2 = (Map.Entry)element$iv$iv$iv;
            String string17 = object;
            Map map3 = object2;
            boolean bl5 = false;
            it2 = it$iv$iv.getValue();
            map3.put(string17, it2);
        }
        SIGNATURE_TO_JVM_REPRESENTATION_NAME = destination$iv$iv;
        Iterable $this$map$iv = NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP.keySet();
        boolean $i$f$map = false;
        $this$mapKeysTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            NameAndSignature nameAndSignature = (NameAndSignature)item$iv$iv;
            object2 = destination$iv$iv;
            boolean bl6 = false;
            object = ((NameAndSignature)it).getName();
            object2.add(object);
        }
        ORIGINAL_SHORT_NAMES = (List)destination$iv$iv;
        $this$map$iv = NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP.entrySet();
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            it = (Map.Entry)item$iv$iv;
            object2 = destination$iv$iv;
            boolean bl7 = false;
            object = new Pair(((NameAndSignature)it.getKey()).getName(), it.getValue());
            object2.add(object);
        }
        Iterable $this$groupBy$iv = (List)destination$iv$iv;
        boolean $i$f$groupBy = false;
        $this$mapTo$iv$iv = $this$groupBy$iv;
        destination$iv$iv = new LinkedHashMap();
        boolean $i$f$groupByTo = false;
        for (Object element$iv$iv : $this$groupByTo$iv$iv) {
            void it3;
            Object object3;
            it = (Pair)element$iv$iv;
            boolean $i$a$-groupBy-BuiltinMethodsWithDifferentJvmName$JVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP$42 = false;
            Name key$iv$iv = (Name)((Pair)it).getSecond();
            Object $this$getOrPut$iv$iv$iv = destination$iv$iv;
            boolean $i$f$getOrPut = false;
            Object value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
            if (value$iv$iv$iv == null) {
                boolean bl8 = false;
                List answer$iv$iv$iv = new ArrayList();
                $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                object3 = answer$iv$iv$iv;
            } else {
                object3 = value$iv$iv$iv;
            }
            List list$iv$iv = (List)object3;
            Pair $i$a$-groupBy-BuiltinMethodsWithDifferentJvmName$JVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP$42 = (Pair)element$iv$iv;
            object2 = list$iv$iv;
            boolean bl9 = false;
            object = (Name)it3.getFirst();
            object2.add(object);
        }
        JVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP = destination$iv$iv;
    }

    public static final /* synthetic */ Map access$getSIGNATURE_TO_JVM_REPRESENTATION_NAME$p(BuiltinMethodsWithDifferentJvmName $this) {
        BuiltinMethodsWithDifferentJvmName builtinMethodsWithDifferentJvmName = $this;
        return SIGNATURE_TO_JVM_REPRESENTATION_NAME;
    }
}

