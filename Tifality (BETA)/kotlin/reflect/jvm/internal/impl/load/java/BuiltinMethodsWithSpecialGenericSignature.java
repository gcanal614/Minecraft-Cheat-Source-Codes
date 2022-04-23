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
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.BuiltinMethodsWithSpecialGenericSignature;
import kotlin.reflect.jvm.internal.impl.load.java.NameAndSignature;
import kotlin.reflect.jvm.internal.impl.load.java.SpecialBuiltinMembers;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BuiltinMethodsWithSpecialGenericSignature {
    private static final List<NameAndSignature> ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES;
    private static final List<String> ERASED_COLLECTION_PARAMETER_SIGNATURES;
    @NotNull
    private static final List<String> ERASED_COLLECTION_PARAMETER_NAMES;
    private static final Map<NameAndSignature, TypeSafeBarrierDescription> GENERIC_PARAMETERS_METHODS_TO_DEFAULT_VALUES_MAP;
    private static final Map<String, TypeSafeBarrierDescription> SIGNATURE_TO_DEFAULT_VALUES_MAP;
    private static final Set<Name> ERASED_VALUE_PARAMETERS_SHORT_NAMES;
    @NotNull
    private static final Set<String> ERASED_VALUE_PARAMETERS_SIGNATURES;
    public static final BuiltinMethodsWithSpecialGenericSignature INSTANCE;

    private final boolean getHasErasedValueParametersInJava(CallableMemberDescriptor $this$hasErasedValueParametersInJava) {
        return CollectionsKt.contains((Iterable)ERASED_VALUE_PARAMETERS_SIGNATURES, MethodSignatureMappingKt.computeJvmSignature($this$hasErasedValueParametersInJava));
    }

    @JvmStatic
    @Nullable
    public static final FunctionDescriptor getOverriddenBuiltinFunctionWithErasedValueParametersInJava(@NotNull FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Name name = functionDescriptor.getName();
        Intrinsics.checkNotNullExpressionValue(name, "functionDescriptor.name");
        if (!INSTANCE.getSameAsBuiltinMethodWithErasedValueParameters(name)) {
            return null;
        }
        return (FunctionDescriptor)DescriptorUtilsKt.firstOverridden$default(functionDescriptor, false, getOverriddenBuiltinFunctionWithErasedValueParametersInJava.1.INSTANCE, 1, null);
    }

    public final boolean getSameAsBuiltinMethodWithErasedValueParameters(@NotNull Name $this$sameAsBuiltinMethodWithErasedValueParameters) {
        Intrinsics.checkNotNullParameter($this$sameAsBuiltinMethodWithErasedValueParameters, "$this$sameAsBuiltinMethodWithErasedValueParameters");
        return ERASED_VALUE_PARAMETERS_SHORT_NAMES.contains($this$sameAsBuiltinMethodWithErasedValueParameters);
    }

    @JvmStatic
    @Nullable
    public static final SpecialSignatureInfo getSpecialSignatureInfo(@NotNull CallableMemberDescriptor $this$getSpecialSignatureInfo) {
        Intrinsics.checkNotNullParameter($this$getSpecialSignatureInfo, "$this$getSpecialSignatureInfo");
        if (!ERASED_VALUE_PARAMETERS_SHORT_NAMES.contains($this$getSpecialSignatureInfo.getName())) {
            return null;
        }
        Object object = DescriptorUtilsKt.firstOverridden$default($this$getSpecialSignatureInfo, false, getSpecialSignatureInfo.builtinSignature.1.INSTANCE, 1, null);
        if (object == null || (object = MethodSignatureMappingKt.computeJvmSignature((CallableDescriptor)object)) == null) {
            return null;
        }
        Object builtinSignature2 = object;
        if (ERASED_COLLECTION_PARAMETER_SIGNATURES.contains(builtinSignature2)) {
            return SpecialSignatureInfo.ONE_COLLECTION_PARAMETER;
        }
        TypeSafeBarrierDescription defaultValue = MapsKt.getValue(SIGNATURE_TO_DEFAULT_VALUES_MAP, builtinSignature2);
        return defaultValue == TypeSafeBarrierDescription.NULL ? SpecialSignatureInfo.OBJECT_PARAMETER_GENERIC : SpecialSignatureInfo.OBJECT_PARAMETER_NON_GENERIC;
    }

    private BuiltinMethodsWithSpecialGenericSignature() {
    }

    /*
     * WARNING - void declaration
     */
    static {
        Iterable $this$mapTo$iv$iv;
        void $this$mapKeysTo$iv$iv;
        Object object;
        Object it;
        Object object2;
        Iterable $this$mapTo$iv$iv2;
        BuiltinMethodsWithSpecialGenericSignature builtinMethodsWithSpecialGenericSignature;
        INSTANCE = builtinMethodsWithSpecialGenericSignature = new BuiltinMethodsWithSpecialGenericSignature();
        Iterable $this$map$iv = SetsKt.setOf("containsAll", "removeAll", "retainAll");
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv2) {
            String string = (String)item$iv$iv;
            object2 = destination$iv$iv;
            boolean bl = false;
            String string2 = JvmPrimitiveType.BOOLEAN.getDesc();
            Intrinsics.checkNotNullExpressionValue(string2, "JvmPrimitiveType.BOOLEAN.desc");
            object = SpecialBuiltinMembers.access$method("java/util/Collection", (String)it, "Ljava/util/Collection;", string2);
            object2.add(object);
        }
        ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES = (List)destination$iv$iv;
        $this$map$iv = ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES;
        $i$f$map = false;
        $this$mapTo$iv$iv2 = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv2) {
            it = (NameAndSignature)item$iv$iv;
            object2 = destination$iv$iv;
            boolean bl = false;
            object = ((NameAndSignature)it).getSignature();
            object2.add(object);
        }
        ERASED_COLLECTION_PARAMETER_SIGNATURES = (List)destination$iv$iv;
        $this$map$iv = ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES;
        $i$f$map = false;
        $this$mapTo$iv$iv2 = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv2) {
            it = (NameAndSignature)item$iv$iv;
            object2 = destination$iv$iv;
            boolean bl = false;
            object = ((NameAndSignature)it).getName().asString();
            object2.add(object);
        }
        ERASED_COLLECTION_PARAMETER_NAMES = (List)destination$iv$iv;
        boolean $i$f$signatures = false;
        SignatureBuildingComponents $i$f$map2 = SignatureBuildingComponents.INSTANCE;
        boolean $this$mapTo$iv$iv32 = false;
        boolean destination$iv$iv2 = false;
        SignatureBuildingComponents $this$signatures = $i$f$map2;
        boolean bl = false;
        Pair[] pairArray = new Pair[10];
        String string = $this$signatures.javaUtil("Collection");
        String string3 = JvmPrimitiveType.BOOLEAN.getDesc();
        Intrinsics.checkNotNullExpressionValue(string3, "JvmPrimitiveType.BOOLEAN.desc");
        pairArray[0] = TuplesKt.to(SpecialBuiltinMembers.access$method(string, "contains", "Ljava/lang/Object;", string3), TypeSafeBarrierDescription.FALSE);
        String string4 = $this$signatures.javaUtil("Collection");
        String string5 = JvmPrimitiveType.BOOLEAN.getDesc();
        Intrinsics.checkNotNullExpressionValue(string5, "JvmPrimitiveType.BOOLEAN.desc");
        pairArray[1] = TuplesKt.to(SpecialBuiltinMembers.access$method(string4, "remove", "Ljava/lang/Object;", string5), TypeSafeBarrierDescription.FALSE);
        String string6 = $this$signatures.javaUtil("Map");
        String string7 = JvmPrimitiveType.BOOLEAN.getDesc();
        Intrinsics.checkNotNullExpressionValue(string7, "JvmPrimitiveType.BOOLEAN.desc");
        pairArray[2] = TuplesKt.to(SpecialBuiltinMembers.access$method(string6, "containsKey", "Ljava/lang/Object;", string7), TypeSafeBarrierDescription.FALSE);
        String string8 = $this$signatures.javaUtil("Map");
        String string9 = JvmPrimitiveType.BOOLEAN.getDesc();
        Intrinsics.checkNotNullExpressionValue(string9, "JvmPrimitiveType.BOOLEAN.desc");
        pairArray[3] = TuplesKt.to(SpecialBuiltinMembers.access$method(string8, "containsValue", "Ljava/lang/Object;", string9), TypeSafeBarrierDescription.FALSE);
        String string10 = $this$signatures.javaUtil("Map");
        String string11 = JvmPrimitiveType.BOOLEAN.getDesc();
        Intrinsics.checkNotNullExpressionValue(string11, "JvmPrimitiveType.BOOLEAN.desc");
        pairArray[4] = TuplesKt.to(SpecialBuiltinMembers.access$method(string10, "remove", "Ljava/lang/Object;Ljava/lang/Object;", string11), TypeSafeBarrierDescription.FALSE);
        pairArray[5] = TuplesKt.to(SpecialBuiltinMembers.access$method($this$signatures.javaUtil("Map"), "getOrDefault", "Ljava/lang/Object;Ljava/lang/Object;", "Ljava/lang/Object;"), TypeSafeBarrierDescription.MAP_GET_OR_DEFAULT);
        pairArray[6] = TuplesKt.to(SpecialBuiltinMembers.access$method($this$signatures.javaUtil("Map"), "get", "Ljava/lang/Object;", "Ljava/lang/Object;"), TypeSafeBarrierDescription.NULL);
        pairArray[7] = TuplesKt.to(SpecialBuiltinMembers.access$method($this$signatures.javaUtil("Map"), "remove", "Ljava/lang/Object;", "Ljava/lang/Object;"), TypeSafeBarrierDescription.NULL);
        String string12 = $this$signatures.javaUtil("List");
        String string13 = JvmPrimitiveType.INT.getDesc();
        Intrinsics.checkNotNullExpressionValue(string13, "JvmPrimitiveType.INT.desc");
        pairArray[8] = TuplesKt.to(SpecialBuiltinMembers.access$method(string12, "indexOf", "Ljava/lang/Object;", string13), TypeSafeBarrierDescription.INDEX);
        String string14 = $this$signatures.javaUtil("List");
        String string15 = JvmPrimitiveType.INT.getDesc();
        Intrinsics.checkNotNullExpressionValue(string15, "JvmPrimitiveType.INT.desc");
        pairArray[9] = TuplesKt.to(SpecialBuiltinMembers.access$method(string14, "lastIndexOf", "Ljava/lang/Object;", string15), TypeSafeBarrierDescription.INDEX);
        Map<NameAndSignature, TypeSafeBarrierDescription> $this$mapKeys$iv = GENERIC_PARAMETERS_METHODS_TO_DEFAULT_VALUES_MAP = MapsKt.mapOf(pairArray);
        boolean $i$f$mapKeys = false;
        Map<NameAndSignature, TypeSafeBarrierDescription> $this$mapTo$iv$iv32 = $this$mapKeys$iv;
        Object destination$iv$iv3 = new LinkedHashMap(MapsKt.mapCapacity($this$mapKeys$iv.size()));
        boolean $i$f$mapKeysTo = false;
        Iterable $this$associateByTo$iv$iv$iv = $this$mapKeysTo$iv$iv.entrySet();
        boolean $i$f$associateByTo = false;
        for (Object element$iv$iv$iv : $this$associateByTo$iv$iv$iv) {
            void it$iv$iv;
            Object it2;
            Map.Entry entry = (Map.Entry)element$iv$iv$iv;
            object2 = destination$iv$iv3;
            boolean bl2 = false;
            object = ((NameAndSignature)it2.getKey()).getSignature();
            Map.Entry entry2 = (Map.Entry)element$iv$iv$iv;
            Object object3 = object;
            Object object4 = object2;
            boolean bl3 = false;
            it2 = it$iv$iv.getValue();
            object4.put(object3, it2);
        }
        SIGNATURE_TO_DEFAULT_VALUES_MAP = destination$iv$iv3;
        Set<NameAndSignature> allMethods = SetsKt.plus(GENERIC_PARAMETERS_METHODS_TO_DEFAULT_VALUES_MAP.keySet(), (Iterable)ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES);
        Iterable $this$map$iv2 = allMethods;
        boolean $i$f$map3 = false;
        destination$iv$iv3 = $this$map$iv2;
        Collection destination$iv$iv4 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        boolean $i$f$mapTo2 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it3;
            NameAndSignature element$iv$iv$iv = (NameAndSignature)item$iv$iv;
            object2 = destination$iv$iv4;
            boolean bl4 = false;
            object = it3.getName();
            object2.add(object);
        }
        ERASED_VALUE_PARAMETERS_SHORT_NAMES = CollectionsKt.toSet((List)destination$iv$iv4);
        $this$map$iv2 = allMethods;
        $i$f$map3 = false;
        $this$mapTo$iv$iv = $this$map$iv2;
        destination$iv$iv4 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv2, 10));
        $i$f$mapTo2 = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            NameAndSignature it3 = (NameAndSignature)item$iv$iv;
            object2 = destination$iv$iv4;
            boolean bl5 = false;
            object = it3.getSignature();
            object2.add(object);
        }
        ERASED_VALUE_PARAMETERS_SIGNATURES = CollectionsKt.toSet((List)destination$iv$iv4);
    }

    public static final /* synthetic */ boolean access$getHasErasedValueParametersInJava$p(BuiltinMethodsWithSpecialGenericSignature $this, CallableMemberDescriptor $this$access_u24hasErasedValueParametersInJava_u24p) {
        return $this.getHasErasedValueParametersInJava($this$access_u24hasErasedValueParametersInJava_u24p);
    }

    public static final class TypeSafeBarrierDescription
    extends Enum<TypeSafeBarrierDescription> {
        public static final /* enum */ TypeSafeBarrierDescription NULL;
        public static final /* enum */ TypeSafeBarrierDescription INDEX;
        public static final /* enum */ TypeSafeBarrierDescription FALSE;
        public static final /* enum */ TypeSafeBarrierDescription MAP_GET_OR_DEFAULT;
        private static final /* synthetic */ TypeSafeBarrierDescription[] $VALUES;
        @Nullable
        private final Object defaultValue;

        static {
            TypeSafeBarrierDescription[] typeSafeBarrierDescriptionArray = new TypeSafeBarrierDescription[4];
            TypeSafeBarrierDescription[] typeSafeBarrierDescriptionArray2 = typeSafeBarrierDescriptionArray;
            typeSafeBarrierDescriptionArray[0] = NULL = new TypeSafeBarrierDescription(null);
            typeSafeBarrierDescriptionArray[1] = INDEX = new TypeSafeBarrierDescription(-1);
            typeSafeBarrierDescriptionArray[2] = FALSE = new TypeSafeBarrierDescription(false);
            typeSafeBarrierDescriptionArray[3] = MAP_GET_OR_DEFAULT = new MAP_GET_OR_DEFAULT("MAP_GET_OR_DEFAULT", 3);
            $VALUES = typeSafeBarrierDescriptionArray;
        }

        private TypeSafeBarrierDescription(Object defaultValue) {
            this.defaultValue = defaultValue;
        }

        public /* synthetic */ TypeSafeBarrierDescription(String $enum$name, int $enum$ordinal, Object defaultValue, DefaultConstructorMarker $constructor_marker) {
            this(defaultValue);
        }

        public static TypeSafeBarrierDescription[] values() {
            return (TypeSafeBarrierDescription[])$VALUES.clone();
        }

        public static TypeSafeBarrierDescription valueOf(String string) {
            return Enum.valueOf(TypeSafeBarrierDescription.class, string);
        }

        static final class MAP_GET_OR_DEFAULT
        extends TypeSafeBarrierDescription {
            /*
             * WARNING - void declaration
             */
            MAP_GET_OR_DEFAULT() {
                void var1_1;
            }
        }
    }

    public static final class SpecialSignatureInfo
    extends Enum<SpecialSignatureInfo> {
        public static final /* enum */ SpecialSignatureInfo ONE_COLLECTION_PARAMETER;
        public static final /* enum */ SpecialSignatureInfo OBJECT_PARAMETER_NON_GENERIC;
        public static final /* enum */ SpecialSignatureInfo OBJECT_PARAMETER_GENERIC;
        private static final /* synthetic */ SpecialSignatureInfo[] $VALUES;
        @Nullable
        private final String valueParametersSignature;
        private final boolean isObjectReplacedWithTypeParameter;

        static {
            SpecialSignatureInfo[] specialSignatureInfoArray = new SpecialSignatureInfo[3];
            SpecialSignatureInfo[] specialSignatureInfoArray2 = specialSignatureInfoArray;
            specialSignatureInfoArray[0] = ONE_COLLECTION_PARAMETER = new SpecialSignatureInfo("Ljava/util/Collection<+Ljava/lang/Object;>;", false);
            specialSignatureInfoArray[1] = OBJECT_PARAMETER_NON_GENERIC = new SpecialSignatureInfo(null, true);
            specialSignatureInfoArray[2] = OBJECT_PARAMETER_GENERIC = new SpecialSignatureInfo("Ljava/lang/Object;", true);
            $VALUES = specialSignatureInfoArray;
        }

        private SpecialSignatureInfo(String valueParametersSignature, boolean isObjectReplacedWithTypeParameter) {
            this.valueParametersSignature = valueParametersSignature;
            this.isObjectReplacedWithTypeParameter = isObjectReplacedWithTypeParameter;
        }

        public static SpecialSignatureInfo[] values() {
            return (SpecialSignatureInfo[])$VALUES.clone();
        }

        public static SpecialSignatureInfo valueOf(String string) {
            return Enum.valueOf(SpecialSignatureInfo.class, string);
        }
    }
}

