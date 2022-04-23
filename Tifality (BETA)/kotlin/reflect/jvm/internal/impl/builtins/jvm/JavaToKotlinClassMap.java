/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.CompanionObjectMapping;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.FqNamesUtilKt;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JavaToKotlinClassMap {
    private static final String NUMBERED_FUNCTION_PREFIX;
    private static final String NUMBERED_K_FUNCTION_PREFIX;
    private static final String NUMBERED_SUSPEND_FUNCTION_PREFIX;
    private static final String NUMBERED_K_SUSPEND_FUNCTION_PREFIX;
    private static final ClassId FUNCTION_N_CLASS_ID;
    @NotNull
    private static final FqName FUNCTION_N_FQ_NAME;
    private static final ClassId K_FUNCTION_CLASS_ID;
    private static final HashMap<FqNameUnsafe, ClassId> javaToKotlin;
    private static final HashMap<FqNameUnsafe, ClassId> kotlinToJava;
    private static final HashMap<FqNameUnsafe, FqName> mutableToReadOnly;
    private static final HashMap<FqNameUnsafe, FqName> readOnlyToMutable;
    @NotNull
    private static final List<PlatformMutabilityMapping> mutabilityMappings;
    public static final JavaToKotlinClassMap INSTANCE;

    @NotNull
    public final FqName getFUNCTION_N_FQ_NAME() {
        return FUNCTION_N_FQ_NAME;
    }

    @NotNull
    public final List<PlatformMutabilityMapping> getMutabilityMappings() {
        return mutabilityMappings;
    }

    @Nullable
    public final ClassId mapJavaToKotlin(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return javaToKotlin.get(fqName2.toUnsafe());
    }

    @Nullable
    public final ClassDescriptor mapJavaToKotlin(@NotNull FqName fqName2, @NotNull KotlinBuiltIns builtIns, @Nullable Integer functionTypeArity) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        ClassId kotlinClassId = functionTypeArity != null && Intrinsics.areEqual(fqName2, FUNCTION_N_FQ_NAME) ? KotlinBuiltIns.getFunctionClassId(functionTypeArity) : this.mapJavaToKotlin(fqName2);
        return kotlinClassId != null ? builtIns.getBuiltInClassByFqName(kotlinClassId.asSingleFqName()) : null;
    }

    public static /* synthetic */ ClassDescriptor mapJavaToKotlin$default(JavaToKotlinClassMap javaToKotlinClassMap, FqName fqName2, KotlinBuiltIns kotlinBuiltIns, Integer n, int n2, Object object) {
        if ((n2 & 4) != 0) {
            n = null;
        }
        return javaToKotlinClassMap.mapJavaToKotlin(fqName2, kotlinBuiltIns, n);
    }

    @Nullable
    public final ClassId mapKotlinToJava(@NotNull FqNameUnsafe kotlinFqName) {
        Intrinsics.checkNotNullParameter(kotlinFqName, "kotlinFqName");
        return this.isKotlinFunctionWithBigArity(kotlinFqName, NUMBERED_FUNCTION_PREFIX) ? FUNCTION_N_CLASS_ID : (this.isKotlinFunctionWithBigArity(kotlinFqName, NUMBERED_SUSPEND_FUNCTION_PREFIX) ? FUNCTION_N_CLASS_ID : (this.isKotlinFunctionWithBigArity(kotlinFqName, NUMBERED_K_FUNCTION_PREFIX) ? K_FUNCTION_CLASS_ID : (this.isKotlinFunctionWithBigArity(kotlinFqName, NUMBERED_K_SUSPEND_FUNCTION_PREFIX) ? K_FUNCTION_CLASS_ID : kotlinToJava.get(kotlinFqName))));
    }

    private final boolean isKotlinFunctionWithBigArity(FqNameUnsafe kotlinFqName, String prefix) {
        String string = kotlinFqName.asString();
        Intrinsics.checkNotNullExpressionValue(string, "kotlinFqName.asString()");
        String arityString = StringsKt.substringAfter(string, prefix, "");
        CharSequence charSequence = arityString;
        boolean bl = false;
        if (charSequence.length() > 0 && !StringsKt.startsWith$default((CharSequence)arityString, '0', false, 2, null)) {
            Integer arity = StringsKt.toIntOrNull(arityString);
            return arity != null && arity >= 23;
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    private final void addMapping(PlatformMutabilityMapping platformMutabilityMapping) {
        void readOnlyClassId;
        void javaClassId;
        PlatformMutabilityMapping platformMutabilityMapping2 = platformMutabilityMapping;
        ClassId classId = platformMutabilityMapping2.component1();
        ClassId classId2 = platformMutabilityMapping2.component2();
        ClassId mutableClassId = platformMutabilityMapping2.component3();
        this.add((ClassId)javaClassId, (ClassId)readOnlyClassId);
        FqName fqName2 = mutableClassId.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqName2, "mutableClassId.asSingleFqName()");
        this.addKotlinToJava(fqName2, (ClassId)javaClassId);
        FqName fqName3 = readOnlyClassId.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqName3, "readOnlyClassId.asSingleFqName()");
        FqName readOnlyFqName = fqName3;
        FqName fqName4 = mutableClassId.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqName4, "mutableClassId.asSingleFqName()");
        FqName mutableFqName = fqName4;
        Map map2 = mutableToReadOnly;
        FqNameUnsafe fqNameUnsafe = mutableClassId.asSingleFqName().toUnsafe();
        Intrinsics.checkNotNullExpressionValue(fqNameUnsafe, "mutableClassId.asSingleFqName().toUnsafe()");
        map2.put(fqNameUnsafe, readOnlyFqName);
        Map map3 = readOnlyToMutable;
        FqNameUnsafe fqNameUnsafe2 = readOnlyFqName.toUnsafe();
        Intrinsics.checkNotNullExpressionValue(fqNameUnsafe2, "readOnlyFqName.toUnsafe()");
        map3.put(fqNameUnsafe2, mutableFqName);
    }

    private final void add(ClassId javaClassId, ClassId kotlinClassId) {
        this.addJavaToKotlin(javaClassId, kotlinClassId);
        FqName fqName2 = kotlinClassId.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqName2, "kotlinClassId.asSingleFqName()");
        this.addKotlinToJava(fqName2, javaClassId);
    }

    private final void addTopLevel(Class<?> javaClass, FqNameUnsafe kotlinFqName) {
        FqName fqName2 = kotlinFqName.toSafe();
        Intrinsics.checkNotNullExpressionValue(fqName2, "kotlinFqName.toSafe()");
        this.addTopLevel(javaClass, fqName2);
    }

    private final void addTopLevel(Class<?> javaClass, FqName kotlinFqName) {
        ClassId classId = this.classId(javaClass);
        ClassId classId2 = ClassId.topLevel(kotlinFqName);
        Intrinsics.checkNotNullExpressionValue(classId2, "ClassId.topLevel(kotlinFqName)");
        this.add(classId, classId2);
    }

    private final void addJavaToKotlin(ClassId javaClassId, ClassId kotlinClassId) {
        Map map2 = javaToKotlin;
        FqNameUnsafe fqNameUnsafe = javaClassId.asSingleFqName().toUnsafe();
        Intrinsics.checkNotNullExpressionValue(fqNameUnsafe, "javaClassId.asSingleFqName().toUnsafe()");
        map2.put(fqNameUnsafe, kotlinClassId);
    }

    private final void addKotlinToJava(FqName kotlinFqNameUnsafe, ClassId javaClassId) {
        Map map2 = kotlinToJava;
        FqNameUnsafe fqNameUnsafe = kotlinFqNameUnsafe.toUnsafe();
        Intrinsics.checkNotNullExpressionValue(fqNameUnsafe, "kotlinFqNameUnsafe.toUnsafe()");
        map2.put(fqNameUnsafe, javaClassId);
    }

    @NotNull
    public final Collection<ClassDescriptor> mapPlatformClass(@NotNull FqName fqName2, @NotNull KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        ClassDescriptor classDescriptor = JavaToKotlinClassMap.mapJavaToKotlin$default(this, fqName2, builtIns, null, 4, null);
        if (classDescriptor == null) {
            return SetsKt.emptySet();
        }
        ClassDescriptor kotlinAnalog = classDescriptor;
        FqName fqName3 = readOnlyToMutable.get(DescriptorUtilsKt.getFqNameUnsafe(kotlinAnalog));
        if (fqName3 == null) {
            return SetsKt.setOf(kotlinAnalog);
        }
        Intrinsics.checkNotNullExpressionValue(fqName3, "readOnlyToMutable[kotlin\u2026eturn setOf(kotlinAnalog)");
        FqName kotlinMutableAnalogFqName = fqName3;
        ClassDescriptor[] classDescriptorArray = new ClassDescriptor[2];
        classDescriptorArray[0] = kotlinAnalog;
        ClassDescriptor classDescriptor2 = builtIns.getBuiltInClassByFqName(kotlinMutableAnalogFqName);
        Intrinsics.checkNotNullExpressionValue(classDescriptor2, "builtIns.getBuiltInClass\u2026otlinMutableAnalogFqName)");
        classDescriptorArray[1] = classDescriptor2;
        return CollectionsKt.listOf(classDescriptorArray);
    }

    public final boolean isMutable(@Nullable FqNameUnsafe fqNameUnsafe) {
        Map map2 = mutableToReadOnly;
        boolean bl = false;
        Map map3 = map2;
        if (map3 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<K, *>");
        }
        return map3.containsKey(fqNameUnsafe);
    }

    public final boolean isMutable(@NotNull ClassDescriptor mutable) {
        Intrinsics.checkNotNullParameter(mutable, "mutable");
        return this.isMutable(DescriptorUtils.getFqName(mutable));
    }

    public final boolean isMutable(@NotNull KotlinType type2) {
        Intrinsics.checkNotNullParameter(type2, "type");
        ClassDescriptor classDescriptor = TypeUtils.getClassDescriptor(type2);
        return classDescriptor != null && this.isMutable(classDescriptor);
    }

    public final boolean isReadOnly(@Nullable FqNameUnsafe fqNameUnsafe) {
        Map map2 = readOnlyToMutable;
        boolean bl = false;
        Map map3 = map2;
        if (map3 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<K, *>");
        }
        return map3.containsKey(fqNameUnsafe);
    }

    public final boolean isReadOnly(@NotNull ClassDescriptor readOnly) {
        Intrinsics.checkNotNullParameter(readOnly, "readOnly");
        return this.isReadOnly(DescriptorUtils.getFqName(readOnly));
    }

    public final boolean isReadOnly(@NotNull KotlinType type2) {
        Intrinsics.checkNotNullParameter(type2, "type");
        ClassDescriptor classDescriptor = TypeUtils.getClassDescriptor(type2);
        return classDescriptor != null && this.isReadOnly(classDescriptor);
    }

    @NotNull
    public final ClassDescriptor convertMutableToReadOnly(@NotNull ClassDescriptor mutable) {
        Intrinsics.checkNotNullParameter(mutable, "mutable");
        return this.convertToOppositeMutability(mutable, (Map<FqNameUnsafe, FqName>)mutableToReadOnly, "mutable");
    }

    @NotNull
    public final ClassDescriptor convertReadOnlyToMutable(@NotNull ClassDescriptor readOnly) {
        Intrinsics.checkNotNullParameter(readOnly, "readOnly");
        return this.convertToOppositeMutability(readOnly, (Map<FqNameUnsafe, FqName>)readOnlyToMutable, "read-only");
    }

    private final ClassId classId(Class<?> clazz) {
        ClassId classId;
        boolean bl = !clazz.isPrimitive() && !clazz.isArray();
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl3 = false;
            String string = "Invalid class: " + clazz;
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        Class<?> outer = clazz.getDeclaringClass();
        if (outer == null) {
            ClassId classId2 = ClassId.topLevel(new FqName(clazz.getCanonicalName()));
            classId = classId2;
            Intrinsics.checkNotNullExpressionValue(classId2, "ClassId.topLevel(FqName(clazz.canonicalName))");
        } else {
            ClassId classId3 = this.classId(outer).createNestedClassId(Name.identifier(clazz.getSimpleName()));
            classId = classId3;
            Intrinsics.checkNotNullExpressionValue(classId3, "classId(outer).createNes\u2026tifier(clazz.simpleName))");
        }
        return classId;
    }

    private final ClassDescriptor convertToOppositeMutability(ClassDescriptor descriptor2, Map<FqNameUnsafe, FqName> map2, String mutabilityKindName) {
        FqName fqName2 = map2.get(DescriptorUtils.getFqName(descriptor2));
        if (fqName2 == null) {
            throw (Throwable)new IllegalArgumentException("Given class " + descriptor2 + " is not a " + mutabilityKindName + " collection");
        }
        FqName oppositeClassFqName = fqName2;
        ClassDescriptor classDescriptor = DescriptorUtilsKt.getBuiltIns(descriptor2).getBuiltInClassByFqName(oppositeClassFqName);
        Intrinsics.checkNotNullExpressionValue(classDescriptor, "descriptor.builtIns.getB\u2026Name(oppositeClassFqName)");
        return classDescriptor;
    }

    private JavaToKotlinClassMap() {
    }

    static {
        int i;
        JavaToKotlinClassMap this_$iv;
        ClassId kotlinReadOnly$iv;
        JavaToKotlinClassMap javaToKotlinClassMap;
        INSTANCE = javaToKotlinClassMap = new JavaToKotlinClassMap();
        NUMBERED_FUNCTION_PREFIX = FunctionClassDescriptor.Kind.Function.getPackageFqName().toString() + "." + FunctionClassDescriptor.Kind.Function.getClassNamePrefix();
        NUMBERED_K_FUNCTION_PREFIX = FunctionClassDescriptor.Kind.KFunction.getPackageFqName().toString() + "." + FunctionClassDescriptor.Kind.KFunction.getClassNamePrefix();
        NUMBERED_SUSPEND_FUNCTION_PREFIX = FunctionClassDescriptor.Kind.SuspendFunction.getPackageFqName().toString() + "." + FunctionClassDescriptor.Kind.SuspendFunction.getClassNamePrefix();
        NUMBERED_K_SUSPEND_FUNCTION_PREFIX = FunctionClassDescriptor.Kind.KSuspendFunction.getPackageFqName().toString() + "." + FunctionClassDescriptor.Kind.KSuspendFunction.getClassNamePrefix();
        ClassId classId = ClassId.topLevel(new FqName("kotlin.jvm.functions.FunctionN"));
        Intrinsics.checkNotNullExpressionValue(classId, "ClassId.topLevel(FqName(\u2026vm.functions.FunctionN\"))");
        FUNCTION_N_CLASS_ID = classId;
        FqName fqName2 = FUNCTION_N_CLASS_ID.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqName2, "FUNCTION_N_CLASS_ID.asSingleFqName()");
        FUNCTION_N_FQ_NAME = fqName2;
        ClassId classId2 = ClassId.topLevel(new FqName("kotlin.reflect.KFunction"));
        Intrinsics.checkNotNullExpressionValue(classId2, "ClassId.topLevel(FqName(\u2026tlin.reflect.KFunction\"))");
        K_FUNCTION_CLASS_ID = classId2;
        javaToKotlin = new HashMap();
        kotlinToJava = new HashMap();
        mutableToReadOnly = new HashMap();
        readOnlyToMutable = new HashMap();
        PlatformMutabilityMapping[] platformMutabilityMappingArray = new PlatformMutabilityMapping[8];
        JavaToKotlinClassMap javaToKotlinClassMap2 = javaToKotlinClassMap;
        ClassId classId3 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.iterable);
        Intrinsics.checkNotNullExpressionValue(classId3, "ClassId.topLevel(FQ_NAMES.iterable)");
        ClassId classId4 = classId3;
        Intrinsics.checkNotNullExpressionValue(KotlinBuiltIns.FQ_NAMES.mutableIterable, "FQ_NAMES.mutableIterable");
        JvmPrimitiveType[] kotlinMutable$iv = KotlinBuiltIns.FQ_NAMES.mutableIterable;
        int $i$f$mutabilityMapping = 0;
        FqName fqName3 = kotlinReadOnly$iv.getPackageFqName();
        FqName fqName4 = kotlinReadOnly$iv.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(fqName4, "kotlinReadOnly.packageFqName");
        ClassId mutableClassId$iv = new ClassId(fqName3, FqNamesUtilKt.tail((FqName)kotlinMutable$iv, fqName4), false);
        platformMutabilityMappingArray[0] = new PlatformMutabilityMapping(this_$iv.classId(Iterable.class), kotlinReadOnly$iv, mutableClassId$iv);
        this_$iv = javaToKotlinClassMap;
        ClassId classId5 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.iterator);
        Intrinsics.checkNotNullExpressionValue(classId5, "ClassId.topLevel(FQ_NAMES.iterator)");
        kotlinReadOnly$iv = classId5;
        FqName fqName5 = KotlinBuiltIns.FQ_NAMES.mutableIterator;
        Intrinsics.checkNotNullExpressionValue(fqName5, "FQ_NAMES.mutableIterator");
        kotlinMutable$iv = fqName5;
        $i$f$mutabilityMapping = 0;
        FqName fqName6 = kotlinReadOnly$iv.getPackageFqName();
        FqName fqName7 = kotlinReadOnly$iv.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(fqName7, "kotlinReadOnly.packageFqName");
        mutableClassId$iv = new ClassId(fqName6, FqNamesUtilKt.tail((FqName)kotlinMutable$iv, fqName7), false);
        platformMutabilityMappingArray[1] = new PlatformMutabilityMapping(this_$iv.classId(Iterator.class), kotlinReadOnly$iv, mutableClassId$iv);
        this_$iv = javaToKotlinClassMap;
        ClassId classId6 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.collection);
        Intrinsics.checkNotNullExpressionValue(classId6, "ClassId.topLevel(FQ_NAMES.collection)");
        kotlinReadOnly$iv = classId6;
        FqName fqName8 = KotlinBuiltIns.FQ_NAMES.mutableCollection;
        Intrinsics.checkNotNullExpressionValue(fqName8, "FQ_NAMES.mutableCollection");
        kotlinMutable$iv = fqName8;
        $i$f$mutabilityMapping = 0;
        FqName fqName9 = kotlinReadOnly$iv.getPackageFqName();
        FqName fqName10 = kotlinReadOnly$iv.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(fqName10, "kotlinReadOnly.packageFqName");
        mutableClassId$iv = new ClassId(fqName9, FqNamesUtilKt.tail((FqName)kotlinMutable$iv, fqName10), false);
        platformMutabilityMappingArray[2] = new PlatformMutabilityMapping(this_$iv.classId(Collection.class), kotlinReadOnly$iv, mutableClassId$iv);
        this_$iv = javaToKotlinClassMap;
        ClassId classId7 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.list);
        Intrinsics.checkNotNullExpressionValue(classId7, "ClassId.topLevel(FQ_NAMES.list)");
        kotlinReadOnly$iv = classId7;
        FqName fqName11 = KotlinBuiltIns.FQ_NAMES.mutableList;
        Intrinsics.checkNotNullExpressionValue(fqName11, "FQ_NAMES.mutableList");
        kotlinMutable$iv = fqName11;
        $i$f$mutabilityMapping = 0;
        FqName fqName12 = kotlinReadOnly$iv.getPackageFqName();
        FqName fqName13 = kotlinReadOnly$iv.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(fqName13, "kotlinReadOnly.packageFqName");
        mutableClassId$iv = new ClassId(fqName12, FqNamesUtilKt.tail((FqName)kotlinMutable$iv, fqName13), false);
        platformMutabilityMappingArray[3] = new PlatformMutabilityMapping(this_$iv.classId(List.class), kotlinReadOnly$iv, mutableClassId$iv);
        this_$iv = javaToKotlinClassMap;
        ClassId classId8 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.set);
        Intrinsics.checkNotNullExpressionValue(classId8, "ClassId.topLevel(FQ_NAMES.set)");
        kotlinReadOnly$iv = classId8;
        FqName fqName14 = KotlinBuiltIns.FQ_NAMES.mutableSet;
        Intrinsics.checkNotNullExpressionValue(fqName14, "FQ_NAMES.mutableSet");
        kotlinMutable$iv = fqName14;
        $i$f$mutabilityMapping = 0;
        FqName fqName15 = kotlinReadOnly$iv.getPackageFqName();
        FqName fqName16 = kotlinReadOnly$iv.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(fqName16, "kotlinReadOnly.packageFqName");
        mutableClassId$iv = new ClassId(fqName15, FqNamesUtilKt.tail((FqName)kotlinMutable$iv, fqName16), false);
        platformMutabilityMappingArray[4] = new PlatformMutabilityMapping(this_$iv.classId(Set.class), kotlinReadOnly$iv, mutableClassId$iv);
        this_$iv = javaToKotlinClassMap;
        ClassId classId9 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.listIterator);
        Intrinsics.checkNotNullExpressionValue(classId9, "ClassId.topLevel(FQ_NAMES.listIterator)");
        kotlinReadOnly$iv = classId9;
        FqName fqName17 = KotlinBuiltIns.FQ_NAMES.mutableListIterator;
        Intrinsics.checkNotNullExpressionValue(fqName17, "FQ_NAMES.mutableListIterator");
        kotlinMutable$iv = fqName17;
        $i$f$mutabilityMapping = 0;
        FqName fqName18 = kotlinReadOnly$iv.getPackageFqName();
        FqName fqName19 = kotlinReadOnly$iv.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(fqName19, "kotlinReadOnly.packageFqName");
        mutableClassId$iv = new ClassId(fqName18, FqNamesUtilKt.tail((FqName)kotlinMutable$iv, fqName19), false);
        platformMutabilityMappingArray[5] = new PlatformMutabilityMapping(this_$iv.classId(ListIterator.class), kotlinReadOnly$iv, mutableClassId$iv);
        this_$iv = javaToKotlinClassMap;
        ClassId classId10 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.map);
        Intrinsics.checkNotNullExpressionValue(classId10, "ClassId.topLevel(FQ_NAMES.map)");
        kotlinReadOnly$iv = classId10;
        FqName fqName20 = KotlinBuiltIns.FQ_NAMES.mutableMap;
        Intrinsics.checkNotNullExpressionValue(fqName20, "FQ_NAMES.mutableMap");
        kotlinMutable$iv = fqName20;
        $i$f$mutabilityMapping = 0;
        FqName fqName21 = kotlinReadOnly$iv.getPackageFqName();
        FqName fqName22 = kotlinReadOnly$iv.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(fqName22, "kotlinReadOnly.packageFqName");
        mutableClassId$iv = new ClassId(fqName21, FqNamesUtilKt.tail((FqName)kotlinMutable$iv, fqName22), false);
        platformMutabilityMappingArray[6] = new PlatformMutabilityMapping(this_$iv.classId(Map.class), kotlinReadOnly$iv, mutableClassId$iv);
        this_$iv = javaToKotlinClassMap;
        ClassId classId11 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.map).createNestedClassId(KotlinBuiltIns.FQ_NAMES.mapEntry.shortName());
        Intrinsics.checkNotNullExpressionValue(classId11, "ClassId.topLevel(FQ_NAME\u2026MES.mapEntry.shortName())");
        kotlinReadOnly$iv = classId11;
        FqName fqName23 = KotlinBuiltIns.FQ_NAMES.mutableMapEntry;
        Intrinsics.checkNotNullExpressionValue(fqName23, "FQ_NAMES.mutableMapEntry");
        kotlinMutable$iv = fqName23;
        $i$f$mutabilityMapping = 0;
        FqName fqName24 = kotlinReadOnly$iv.getPackageFqName();
        FqName fqName25 = kotlinReadOnly$iv.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(fqName25, "kotlinReadOnly.packageFqName");
        mutableClassId$iv = new ClassId(fqName24, FqNamesUtilKt.tail((FqName)kotlinMutable$iv, fqName25), false);
        platformMutabilityMappingArray[7] = new PlatformMutabilityMapping(this_$iv.classId(Map.Entry.class), kotlinReadOnly$iv, mutableClassId$iv);
        mutabilityMappings = CollectionsKt.listOf(platformMutabilityMappingArray);
        FqNameUnsafe fqNameUnsafe = KotlinBuiltIns.FQ_NAMES.any;
        Intrinsics.checkNotNullExpressionValue(fqNameUnsafe, "FQ_NAMES.any");
        javaToKotlinClassMap.addTopLevel(Object.class, fqNameUnsafe);
        FqNameUnsafe fqNameUnsafe2 = KotlinBuiltIns.FQ_NAMES.string;
        Intrinsics.checkNotNullExpressionValue(fqNameUnsafe2, "FQ_NAMES.string");
        javaToKotlinClassMap.addTopLevel(String.class, fqNameUnsafe2);
        FqNameUnsafe fqNameUnsafe3 = KotlinBuiltIns.FQ_NAMES.charSequence;
        Intrinsics.checkNotNullExpressionValue(fqNameUnsafe3, "FQ_NAMES.charSequence");
        javaToKotlinClassMap.addTopLevel(CharSequence.class, fqNameUnsafe3);
        FqName fqName26 = KotlinBuiltIns.FQ_NAMES.throwable;
        Intrinsics.checkNotNullExpressionValue(fqName26, "FQ_NAMES.throwable");
        javaToKotlinClassMap.addTopLevel(Throwable.class, fqName26);
        FqNameUnsafe fqNameUnsafe4 = KotlinBuiltIns.FQ_NAMES.cloneable;
        Intrinsics.checkNotNullExpressionValue(fqNameUnsafe4, "FQ_NAMES.cloneable");
        javaToKotlinClassMap.addTopLevel(Cloneable.class, fqNameUnsafe4);
        FqNameUnsafe fqNameUnsafe5 = KotlinBuiltIns.FQ_NAMES.number;
        Intrinsics.checkNotNullExpressionValue(fqNameUnsafe5, "FQ_NAMES.number");
        javaToKotlinClassMap.addTopLevel(Number.class, fqNameUnsafe5);
        FqName fqName27 = KotlinBuiltIns.FQ_NAMES.comparable;
        Intrinsics.checkNotNullExpressionValue(fqName27, "FQ_NAMES.comparable");
        javaToKotlinClassMap.addTopLevel(Comparable.class, fqName27);
        FqNameUnsafe fqNameUnsafe6 = KotlinBuiltIns.FQ_NAMES._enum;
        Intrinsics.checkNotNullExpressionValue(fqNameUnsafe6, "FQ_NAMES._enum");
        javaToKotlinClassMap.addTopLevel(Enum.class, fqNameUnsafe6);
        FqName fqName28 = KotlinBuiltIns.FQ_NAMES.annotation;
        Intrinsics.checkNotNullExpressionValue(fqName28, "FQ_NAMES.annotation");
        javaToKotlinClassMap.addTopLevel(Annotation.class, fqName28);
        for (PlatformMutabilityMapping platformCollection : mutabilityMappings) {
            javaToKotlinClassMap.addMapping(platformCollection);
        }
        for (JvmPrimitiveType jvmType : JvmPrimitiveType.values()) {
            ClassId classId12 = ClassId.topLevel(jvmType.getWrapperFqName());
            Intrinsics.checkNotNullExpressionValue(classId12, "ClassId.topLevel(jvmType.wrapperFqName)");
            ClassId classId13 = ClassId.topLevel(KotlinBuiltIns.getPrimitiveFqName(jvmType.getPrimitiveType()));
            Intrinsics.checkNotNullExpressionValue(classId13, "ClassId.topLevel(KotlinB\u2026e(jvmType.primitiveType))");
            javaToKotlinClassMap.add(classId12, classId13);
        }
        for (ClassId classId14 : CompanionObjectMapping.INSTANCE.allClassesWithIntrinsicCompanions()) {
            ClassId classId15 = ClassId.topLevel(new FqName("kotlin.jvm.internal." + classId14.getShortClassName().asString() + "CompanionObject"));
            Intrinsics.checkNotNullExpressionValue(classId15, "ClassId.topLevel(FqName(\u2026g() + \"CompanionObject\"))");
            ClassId classId16 = classId14.createNestedClassId(SpecialNames.DEFAULT_NAME_FOR_COMPANION_OBJECT);
            Intrinsics.checkNotNullExpressionValue(classId16, "classId.createNestedClas\u2026AME_FOR_COMPANION_OBJECT)");
            javaToKotlinClassMap.add(classId15, classId16);
        }
        int classId14 = 0;
        int n = 23;
        while (classId14 < n) {
            ClassId classId17 = ClassId.topLevel(new FqName("kotlin.jvm.functions.Function" + i));
            Intrinsics.checkNotNullExpressionValue(classId17, "ClassId.topLevel(FqName(\u2026m.functions.Function$i\"))");
            ClassId classId18 = KotlinBuiltIns.getFunctionClassId(i);
            Intrinsics.checkNotNullExpressionValue(classId18, "KotlinBuiltIns.getFunctionClassId(i)");
            javaToKotlinClassMap.add(classId17, classId18);
            javaToKotlinClassMap.addKotlinToJava(new FqName(NUMBERED_K_FUNCTION_PREFIX + i), K_FUNCTION_CLASS_ID);
            ++i;
        }
        n = 22;
        for (i = 0; i < n; ++i) {
            FunctionClassDescriptor.Kind kSuspendFunction = FunctionClassDescriptor.Kind.KSuspendFunction;
            String kSuspendFun = kSuspendFunction.getPackageFqName().toString() + "." + kSuspendFunction.getClassNamePrefix();
            javaToKotlinClassMap.addKotlinToJava(new FqName(kSuspendFun + i), K_FUNCTION_CLASS_ID);
        }
        FqName fqName29 = KotlinBuiltIns.FQ_NAMES.nothing.toSafe();
        Intrinsics.checkNotNullExpressionValue(fqName29, "FQ_NAMES.nothing.toSafe()");
        javaToKotlinClassMap.addKotlinToJava(fqName29, javaToKotlinClassMap.classId(Void.class));
    }

    public static final class PlatformMutabilityMapping {
        @NotNull
        private final ClassId javaClass;
        @NotNull
        private final ClassId kotlinReadOnly;
        @NotNull
        private final ClassId kotlinMutable;

        @NotNull
        public final ClassId getJavaClass() {
            return this.javaClass;
        }

        public PlatformMutabilityMapping(@NotNull ClassId javaClass, @NotNull ClassId kotlinReadOnly, @NotNull ClassId kotlinMutable) {
            Intrinsics.checkNotNullParameter(javaClass, "javaClass");
            Intrinsics.checkNotNullParameter(kotlinReadOnly, "kotlinReadOnly");
            Intrinsics.checkNotNullParameter(kotlinMutable, "kotlinMutable");
            this.javaClass = javaClass;
            this.kotlinReadOnly = kotlinReadOnly;
            this.kotlinMutable = kotlinMutable;
        }

        @NotNull
        public final ClassId component1() {
            return this.javaClass;
        }

        @NotNull
        public final ClassId component2() {
            return this.kotlinReadOnly;
        }

        @NotNull
        public final ClassId component3() {
            return this.kotlinMutable;
        }

        @NotNull
        public String toString() {
            return "PlatformMutabilityMapping(javaClass=" + this.javaClass + ", kotlinReadOnly=" + this.kotlinReadOnly + ", kotlinMutable=" + this.kotlinMutable + ")";
        }

        public int hashCode() {
            ClassId classId = this.javaClass;
            ClassId classId2 = this.kotlinReadOnly;
            ClassId classId3 = this.kotlinMutable;
            return ((classId != null ? ((Object)classId).hashCode() : 0) * 31 + (classId2 != null ? ((Object)classId2).hashCode() : 0)) * 31 + (classId3 != null ? ((Object)classId3).hashCode() : 0);
        }

        public boolean equals(@Nullable Object object) {
            block3: {
                block2: {
                    if (this == object) break block2;
                    if (!(object instanceof PlatformMutabilityMapping)) break block3;
                    PlatformMutabilityMapping platformMutabilityMapping = (PlatformMutabilityMapping)object;
                    if (!Intrinsics.areEqual(this.javaClass, platformMutabilityMapping.javaClass) || !Intrinsics.areEqual(this.kotlinReadOnly, platformMutabilityMapping.kotlinReadOnly) || !Intrinsics.areEqual(this.kotlinMutable, platformMutabilityMapping.kotlinMutable)) break block3;
                }
                return true;
            }
            return false;
        }
    }
}

