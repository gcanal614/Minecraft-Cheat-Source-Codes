/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm;

import kotlin.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;
import kotlin.reflect.jvm.ReflectLambdaKt;
import kotlin.reflect.jvm.internal.EmptyContainerForLocal;
import kotlin.reflect.jvm.internal.KFunctionImpl;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMetadataVersion;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmNameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\u00a8\u0006\u0004"}, d2={"reflect", "Lkotlin/reflect/KFunction;", "R", "Lkotlin/Function;", "kotlin-reflection"})
public final class ReflectLambdaKt {
    /*
     * WARNING - void declaration
     */
    @Nullable
    public static final <R> KFunction<R> reflect(@NotNull Function<? extends R> $this$reflect) {
        void nameResolver;
        Intrinsics.checkNotNullParameter($this$reflect, "$this$reflect");
        Metadata metadata2 = $this$reflect.getClass().getAnnotation(Metadata.class);
        if (metadata2 == null) {
            return null;
        }
        Metadata annotation = metadata2;
        Object object = annotation.d1();
        boolean bl = false;
        boolean bl2 = false;
        String[] p1 = object;
        boolean bl3 = false;
        String[] stringArray = p1;
        boolean bl4 = false;
        Object object2 = !(stringArray.length == 0) ? object : null;
        if (object2 == null) {
            return null;
        }
        String[] data2 = object2;
        Pair<JvmNameResolver, ProtoBuf.Function> pair = JvmProtoBufUtil.readFunctionDataFrom(data2, annotation.d2());
        object = pair.component1();
        ProtoBuf.Function proto = pair.component2();
        JvmMetadataVersion metadataVersion = new JvmMetadataVersion(annotation.mv(), (annotation.xi() & 8) != 0);
        Class<?> clazz = $this$reflect.getClass();
        MessageLite messageLite = proto;
        NameResolver nameResolver2 = (NameResolver)nameResolver;
        ProtoBuf.TypeTable typeTable = proto.getTypeTable();
        Intrinsics.checkNotNullExpressionValue(typeTable, "proto.typeTable");
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor)UtilKt.deserializeToDescriptor(clazz, messageLite, nameResolver2, new TypeTable(typeTable), metadataVersion, reflect.descriptor.1.INSTANCE);
        if (simpleFunctionDescriptor == null) {
            return null;
        }
        SimpleFunctionDescriptor descriptor2 = simpleFunctionDescriptor;
        return new KFunctionImpl(EmptyContainerForLocal.INSTANCE, descriptor2);
    }
}

