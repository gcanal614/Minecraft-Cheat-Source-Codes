/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ArrayValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.BooleanValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ByteValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.CharValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.DoubleValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.FloatValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.LongValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.NullValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ShortValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ConstantValueFactory {
    public static final ConstantValueFactory INSTANCE;

    @NotNull
    public final ArrayValue createArrayValue(@NotNull List<? extends ConstantValue<?>> value, @NotNull KotlinType type2) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(type2, "type");
        return new ArrayValue(value, (Function1<? super ModuleDescriptor, ? extends KotlinType>)new Function1<ModuleDescriptor, KotlinType>(type2){
            final /* synthetic */ KotlinType $type;

            @NotNull
            public final KotlinType invoke(@NotNull ModuleDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.$type;
            }
            {
                this.$type = kotlinType;
                super(1);
            }
        });
    }

    @Nullable
    public final ConstantValue<?> createConstantValue(@Nullable Object value) {
        Object object = value;
        return object instanceof Byte ? (ConstantValue)new ByteValue(((Number)value).byteValue()) : (object instanceof Short ? (ConstantValue)new ShortValue(((Number)value).shortValue()) : (object instanceof Integer ? (ConstantValue)new IntValue(((Number)value).intValue()) : (object instanceof Long ? (ConstantValue)new LongValue(((Number)value).longValue()) : (object instanceof Character ? (ConstantValue)new CharValue(((Character)value).charValue()) : (object instanceof Float ? (ConstantValue)new FloatValue(((Number)value).floatValue()) : (object instanceof Double ? (ConstantValue)new DoubleValue(((Number)value).doubleValue()) : (object instanceof Boolean ? (ConstantValue)new BooleanValue((Boolean)value) : (object instanceof String ? (ConstantValue)new StringValue((String)value) : (object instanceof byte[] ? (ConstantValue)this.createArrayValue(ArraysKt.toList((byte[])value), PrimitiveType.BYTE) : (object instanceof short[] ? (ConstantValue)this.createArrayValue(ArraysKt.toList((short[])value), PrimitiveType.SHORT) : (object instanceof int[] ? (ConstantValue)this.createArrayValue(ArraysKt.toList((int[])value), PrimitiveType.INT) : (object instanceof long[] ? (ConstantValue)this.createArrayValue(ArraysKt.toList((long[])value), PrimitiveType.LONG) : (object instanceof char[] ? (ConstantValue)this.createArrayValue(ArraysKt.toList((char[])value), PrimitiveType.CHAR) : (object instanceof float[] ? (ConstantValue)this.createArrayValue(ArraysKt.toList((float[])value), PrimitiveType.FLOAT) : (object instanceof double[] ? (ConstantValue)this.createArrayValue(ArraysKt.toList((double[])value), PrimitiveType.DOUBLE) : (object instanceof boolean[] ? (ConstantValue)this.createArrayValue(ArraysKt.toList((boolean[])value), PrimitiveType.BOOLEAN) : (object == null ? (ConstantValue)new NullValue() : null)))))))))))))))));
    }

    /*
     * WARNING - void declaration
     */
    private final ArrayValue createArrayValue(List<?> value, PrimitiveType componentType) {
        void $this$mapNotNullTo$iv$iv;
        void $this$mapNotNull$iv;
        Iterable iterable = CollectionsKt.toList((Iterable)value);
        ConstantValueFactory constantValueFactory = this;
        boolean $i$f$mapNotNull = false;
        void var6_6 = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator iterator2 = $this$forEach$iv$iv$iv.iterator();
        while (iterator2.hasNext()) {
            ConstantValue<?> constantValue;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator2.next();
            boolean bl = false;
            Object p1 = element$iv$iv;
            boolean bl2 = false;
            if (constantValueFactory.createConstantValue(p1) == null) continue;
            boolean bl3 = false;
            boolean bl4 = false;
            ConstantValue<?> it$iv$iv = constantValue;
            boolean bl5 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        List list = (List)destination$iv$iv;
        Function1 function1 = new Function1<ModuleDescriptor, KotlinType>(componentType){
            final /* synthetic */ PrimitiveType $componentType;

            @NotNull
            public final KotlinType invoke(@NotNull ModuleDescriptor module) {
                Intrinsics.checkNotNullParameter(module, "module");
                SimpleType simpleType2 = module.getBuiltIns().getPrimitiveArrayKotlinType(this.$componentType);
                Intrinsics.checkNotNullExpressionValue(simpleType2, "module.builtIns.getPrimi\u2026KotlinType(componentType)");
                return simpleType2;
            }
            {
                this.$componentType = primitiveType;
                super(1);
            }
        };
        List list2 = list;
        return new ArrayValue(list2, function1);
    }

    private ConstantValueFactory() {
    }

    static {
        ConstantValueFactory constantValueFactory;
        INSTANCE = constantValueFactory = new ConstantValueFactory();
    }
}

