/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.Flags;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.constants.AnnotationValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.BooleanValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ByteValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.CharValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValueFactory;
import kotlin.reflect.jvm.internal.impl.resolve.constants.DoubleValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ErrorValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.FloatValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.KClassValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.LongValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ShortValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.UByteValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.UIntValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ULongValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.UShortValue;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotationDeserializer$WhenMappings;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.NameResolverUtilKt;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;

public final class AnnotationDeserializer {
    private final ModuleDescriptor module;
    private final NotFoundClasses notFoundClasses;

    private final KotlinBuiltIns getBuiltIns() {
        return this.module.getBuiltIns();
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final AnnotationDescriptor deserializeAnnotation(@NotNull ProtoBuf.Annotation proto, @NotNull NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        ClassDescriptor annotationClass = this.resolveClass(NameResolverUtilKt.getClassId(nameResolver, proto.getId()));
        Map<Name, ConstantValue<Object>> arguments2 = MapsKt.emptyMap();
        if (proto.getArgumentCount() != 0 && !ErrorUtils.isError(annotationClass) && DescriptorUtils.isAnnotationClass(annotationClass)) {
            Collection<ClassConstructorDescriptor> collection = annotationClass.getConstructors();
            Intrinsics.checkNotNullExpressionValue(collection, "annotationClass.constructors");
            ClassConstructorDescriptor constructor = (ClassConstructorDescriptor)CollectionsKt.singleOrNull((Iterable)collection);
            if (constructor != null) {
                void $this$mapNotNullTo$iv$iv;
                void $this$associateByTo$iv$iv;
                List<ValueParameterDescriptor> list = constructor.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(list, "constructor.valueParameters");
                Iterable $this$associateBy$iv = list;
                boolean $i$f$associateBy = false;
                int capacity$iv22 = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associateBy$iv, 10)), 16);
                Iterable iterable = $this$associateBy$iv;
                Map destination$iv$iv = new LinkedHashMap(capacity$iv22);
                boolean $i$f$associateByTo = false;
                for (Object element$iv$iv : $this$associateByTo$iv$iv) {
                    void it;
                    ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor)element$iv$iv;
                    Map map2 = destination$iv$iv;
                    boolean bl = false;
                    void v2 = it;
                    Intrinsics.checkNotNullExpressionValue(v2, "it");
                    Name name = v2.getName();
                    map2.put(name, element$iv$iv);
                }
                Map parameterByName = destination$iv$iv;
                List<ProtoBuf.Annotation.Argument> list2 = proto.getArgumentList();
                Intrinsics.checkNotNullExpressionValue(list2, "proto.argumentList");
                Iterable $this$mapNotNull$iv = list2;
                boolean $i$f$mapNotNull = false;
                Iterable capacity$iv22 = $this$mapNotNull$iv;
                Collection destination$iv$iv2 = new ArrayList();
                boolean $i$f$mapNotNullTo = false;
                void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                boolean $i$f$forEach = false;
                Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
                while (iterator2.hasNext()) {
                    Pair<Name, ConstantValue<?>> pair;
                    Object element$iv$iv$iv;
                    Object element$iv$iv = element$iv$iv$iv = iterator2.next();
                    boolean bl = false;
                    ProtoBuf.Annotation.Argument it = (ProtoBuf.Annotation.Argument)element$iv$iv;
                    boolean bl2 = false;
                    ProtoBuf.Annotation.Argument argument = it;
                    Intrinsics.checkNotNullExpressionValue(argument, "it");
                    if (this.resolveArgument(argument, parameterByName, nameResolver) == null) continue;
                    boolean bl3 = false;
                    boolean bl4 = false;
                    Pair<Name, ConstantValue<?>> it$iv$iv = pair;
                    boolean bl5 = false;
                    destination$iv$iv2.add(it$iv$iv);
                }
                arguments2 = MapsKt.toMap((List)destination$iv$iv2);
            }
        }
        return new AnnotationDescriptorImpl(annotationClass.getDefaultType(), arguments2, SourceElement.NO_SOURCE);
    }

    private final Pair<Name, ConstantValue<?>> resolveArgument(ProtoBuf.Annotation.Argument proto, Map<Name, ? extends ValueParameterDescriptor> parameterByName, NameResolver nameResolver) {
        ValueParameterDescriptor valueParameterDescriptor = parameterByName.get(NameResolverUtilKt.getName(nameResolver, proto.getNameId()));
        if (valueParameterDescriptor == null) {
            return null;
        }
        ValueParameterDescriptor parameter = valueParameterDescriptor;
        Name name = NameResolverUtilKt.getName(nameResolver, proto.getNameId());
        KotlinType kotlinType = parameter.getType();
        Intrinsics.checkNotNullExpressionValue(kotlinType, "parameter.type");
        ProtoBuf.Annotation.Argument.Value value = proto.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "proto.value");
        return new Pair(name, this.resolveValueAndCheckExpectedType(kotlinType, value, nameResolver));
    }

    private final ConstantValue<?> resolveValueAndCheckExpectedType(KotlinType expectedType, ProtoBuf.Annotation.Argument.Value value, NameResolver nameResolver) {
        ConstantValue constantValue = this.resolveValue(expectedType, value, nameResolver);
        boolean bl = false;
        boolean bl2 = false;
        ConstantValue it = constantValue;
        boolean bl3 = false;
        ConstantValue constantValue2 = this.doesValueConformToExpectedType(it, expectedType, value) ? constantValue : null;
        if (constantValue2 == null) {
            constantValue2 = ErrorValue.Companion.create("Unexpected argument value: actual type " + value.getType() + " != expected type " + expectedType);
        }
        return constantValue2;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @NotNull
    public final ConstantValue<?> resolveValue(@NotNull KotlinType expectedType, @NotNull ProtoBuf.Annotation.Argument.Value value, @NotNull NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Boolean bl = Flags.IS_UNSIGNED.get(value.getFlags());
        Intrinsics.checkNotNullExpressionValue(bl, "Flags.IS_UNSIGNED.get(value.flags)");
        boolean isUnsigned = bl;
        ProtoBuf.Annotation.Argument.Value.Type type2 = value.getType();
        if (type2 != null) {
            switch (AnnotationDeserializer$WhenMappings.$EnumSwitchMapping$0[type2.ordinal()]) {
                case 1: {
                    ConstantValue constantValue;
                    void $this$letIf$iv;
                    byte by = (byte)value.getIntValue();
                    AnnotationDeserializer this_$iv = this;
                    boolean $i$f$letIf = false;
                    if (isUnsigned) {
                        void p1 = $this$letIf$iv;
                        boolean bl2 = false;
                        constantValue = new UByteValue((byte)p1);
                    } else {
                        void p1 = $this$letIf$iv;
                        boolean bl3 = false;
                        constantValue = new ByteValue((byte)p1);
                    }
                    ConstantValue constantValue2 = constantValue;
                    return constantValue2;
                }
                case 2: {
                    ConstantValue constantValue2 = new CharValue((char)value.getIntValue());
                    return constantValue2;
                }
                case 3: {
                    ConstantValue constantValue;
                    short $this$letIf$iv = (short)value.getIntValue();
                    AnnotationDeserializer this_$iv = this;
                    boolean $i$f$letIf = false;
                    if (isUnsigned) {
                        short p1 = $this$letIf$iv;
                        boolean bl4 = false;
                        constantValue = new UShortValue(p1);
                    } else {
                        short p1 = $this$letIf$iv;
                        boolean bl5 = false;
                        constantValue = new ShortValue(p1);
                    }
                    ConstantValue constantValue2 = constantValue;
                    return constantValue2;
                }
                case 4: {
                    ConstantValue constantValue;
                    int $this$letIf$iv = (int)value.getIntValue();
                    AnnotationDeserializer this_$iv = this;
                    boolean $i$f$letIf = false;
                    if (isUnsigned) {
                        int p1 = $this$letIf$iv;
                        boolean bl6 = false;
                        constantValue = new UIntValue(p1);
                    } else {
                        int p1 = $this$letIf$iv;
                        boolean bl7 = false;
                        constantValue = new IntValue(p1);
                    }
                    ConstantValue constantValue2 = constantValue;
                    return constantValue2;
                }
                case 5: {
                    ConstantValue constantValue;
                    long $this$letIf$iv = value.getIntValue();
                    AnnotationDeserializer this_$iv = this;
                    boolean $i$f$letIf = false;
                    if (isUnsigned) {
                        long p1 = $this$letIf$iv;
                        boolean bl8 = false;
                        constantValue = new ULongValue(p1);
                    } else {
                        long p1 = $this$letIf$iv;
                        boolean bl9 = false;
                        constantValue = new LongValue(p1);
                    }
                    ConstantValue constantValue2 = constantValue;
                    return constantValue2;
                }
                case 6: {
                    ConstantValue constantValue2 = new FloatValue(value.getFloatValue());
                    return constantValue2;
                }
                case 7: {
                    ConstantValue constantValue2 = new DoubleValue(value.getDoubleValue());
                    return constantValue2;
                }
                case 8: {
                    ConstantValue constantValue2 = new BooleanValue(value.getIntValue() != 0L);
                    return constantValue2;
                }
                case 9: {
                    ConstantValue constantValue2 = new StringValue(nameResolver.getString(value.getStringValue()));
                    return constantValue2;
                }
                case 10: {
                    ConstantValue constantValue2 = new KClassValue(NameResolverUtilKt.getClassId(nameResolver, value.getClassId()), value.getArrayDimensionCount());
                    return constantValue2;
                }
                case 11: {
                    ConstantValue constantValue2 = new EnumValue(NameResolverUtilKt.getClassId(nameResolver, value.getClassId()), NameResolverUtilKt.getName(nameResolver, value.getEnumValueId()));
                    return constantValue2;
                }
                case 12: {
                    ProtoBuf.Annotation annotation = value.getAnnotation();
                    Intrinsics.checkNotNullExpressionValue(annotation, "value.annotation");
                    ConstantValue constantValue2 = new AnnotationValue(this.deserializeAnnotation(annotation, nameResolver));
                    return constantValue2;
                }
                case 13: {
                    void $this$mapTo$iv$iv;
                    void $this$map$iv;
                    ConstantValue constantValue2;
                    List<ProtoBuf.Annotation.Argument.Value> list = value.getArrayElementList();
                    Intrinsics.checkNotNullExpressionValue(list, "value.arrayElementList");
                    Iterable this_$iv = list;
                    ConstantValueFactory constantValueFactory = ConstantValueFactory.INSTANCE;
                    boolean $i$f$map = false;
                    void $i$f$letIf = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    Iterator iterator2 = $this$mapTo$iv$iv.iterator();
                    while (true) {
                        void it;
                        Collection<ConstantValue<?>> collection;
                        if (!iterator2.hasNext()) {
                            collection = (List)destination$iv$iv;
                            constantValue2 = constantValueFactory.createArrayValue((List<? extends ConstantValue<?>>)collection, expectedType);
                            return constantValue2;
                        }
                        Object item$iv$iv = iterator2.next();
                        ProtoBuf.Annotation.Argument.Value value2 = (ProtoBuf.Annotation.Argument.Value)item$iv$iv;
                        collection = destination$iv$iv;
                        boolean bl10 = false;
                        SimpleType simpleType2 = this.getBuiltIns().getAnyType();
                        Intrinsics.checkNotNullExpressionValue(simpleType2, "builtIns.anyType");
                        KotlinType kotlinType = simpleType2;
                        void v11 = it;
                        Intrinsics.checkNotNullExpressionValue(v11, "it");
                        ConstantValue<?> constantValue = this.resolveValue(kotlinType, (ProtoBuf.Annotation.Argument.Value)v11, nameResolver);
                        collection.add(constantValue);
                    }
                }
            }
        }
        String string = "Unsupported annotation argument type: " + value.getType() + " (expected " + expectedType + ')';
        boolean bl11 = false;
        throw (Throwable)new IllegalStateException(string.toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    private final boolean doesValueConformToExpectedType(ConstantValue<?> result2, KotlinType expectedType, ProtoBuf.Annotation.Argument.Value value) {
        ProtoBuf.Annotation.Argument.Value.Type type2 = value.getType();
        if (type2 != null) {
            switch (AnnotationDeserializer$WhenMappings.$EnumSwitchMapping$1[type2.ordinal()]) {
                case 1: {
                    ClassDescriptor expectedClass;
                    ClassifierDescriptor classifierDescriptor = expectedType.getConstructor().getDeclarationDescriptor();
                    if (!(classifierDescriptor instanceof ClassDescriptor)) {
                        classifierDescriptor = null;
                    }
                    if ((expectedClass = (ClassDescriptor)classifierDescriptor) == null) return true;
                    if (KotlinBuiltIns.isKClass(expectedClass)) return true;
                    return false;
                }
                case 2: {
                    ProtoBuf.Annotation.Argument.Value value2;
                    ConstantValue constantValue;
                    boolean expectedClass = result2 instanceof ArrayValue && ((List)((ArrayValue)result2).getValue()).size() == value.getArrayElementList().size();
                    boolean bl = false;
                    boolean bl2 = false;
                    if (!expectedClass) {
                        boolean bl3 = false;
                        String string = "Deserialized ArrayValue should have the same number of elements as the original array value: " + result2;
                        throw (Throwable)new IllegalStateException(string.toString());
                    }
                    KotlinType kotlinType = this.getBuiltIns().getArrayElementType(expectedType);
                    Intrinsics.checkNotNullExpressionValue(kotlinType, "builtIns.getArrayElementType(expectedType)");
                    KotlinType expectedElementType = kotlinType;
                    Iterable $this$all$iv = CollectionsKt.getIndices((Collection)((ArrayValue)result2).getValue());
                    boolean $i$f$all = false;
                    if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                        return true;
                    }
                    Iterator iterator2 = $this$all$iv.iterator();
                    do {
                        int element$iv;
                        if (!iterator2.hasNext()) {
                            return true;
                        }
                        int i = element$iv = ((IntIterator)iterator2).nextInt();
                        boolean bl4 = false;
                        constantValue = (ConstantValue)((List)((ArrayValue)result2).getValue()).get(i);
                        value2 = value.getArrayElement(i);
                        Intrinsics.checkNotNullExpressionValue(value2, "value.getArrayElement(i)");
                    } while (this.doesValueConformToExpectedType(constantValue, expectedElementType, value2));
                    return false;
                }
            }
        }
        boolean bl = Intrinsics.areEqual(result2.getType(this.module), expectedType);
        return bl;
    }

    private final ClassDescriptor resolveClass(ClassId classId) {
        return FindClassInModuleKt.findNonGenericClassAcrossDependencies(this.module, classId, this.notFoundClasses);
    }

    public AnnotationDeserializer(@NotNull ModuleDescriptor module, @NotNull NotFoundClasses notFoundClasses) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        this.module = module;
        this.notFoundClasses = notFoundClasses;
    }
}

