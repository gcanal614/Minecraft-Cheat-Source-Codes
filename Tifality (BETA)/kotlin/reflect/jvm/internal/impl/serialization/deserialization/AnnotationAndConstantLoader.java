/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotatedCallableKind;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ProtoContainer;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AnnotationAndConstantLoader<A, C> {
    @NotNull
    public List<A> loadClassAnnotations(@NotNull ProtoContainer.Class var1);

    @NotNull
    public List<A> loadCallableAnnotations(@NotNull ProtoContainer var1, @NotNull MessageLite var2, @NotNull AnnotatedCallableKind var3);

    @NotNull
    public List<A> loadPropertyBackingFieldAnnotations(@NotNull ProtoContainer var1, @NotNull ProtoBuf.Property var2);

    @NotNull
    public List<A> loadPropertyDelegateFieldAnnotations(@NotNull ProtoContainer var1, @NotNull ProtoBuf.Property var2);

    @NotNull
    public List<A> loadEnumEntryAnnotations(@NotNull ProtoContainer var1, @NotNull ProtoBuf.EnumEntry var2);

    @NotNull
    public List<A> loadValueParameterAnnotations(@NotNull ProtoContainer var1, @NotNull MessageLite var2, @NotNull AnnotatedCallableKind var3, int var4, @NotNull ProtoBuf.ValueParameter var5);

    @NotNull
    public List<A> loadExtensionReceiverParameterAnnotations(@NotNull ProtoContainer var1, @NotNull MessageLite var2, @NotNull AnnotatedCallableKind var3);

    @NotNull
    public List<A> loadTypeAnnotations(@NotNull ProtoBuf.Type var1, @NotNull NameResolver var2);

    @NotNull
    public List<A> loadTypeParameterAnnotations(@NotNull ProtoBuf.TypeParameter var1, @NotNull NameResolver var2);

    @Nullable
    public C loadPropertyConstant(@NotNull ProtoContainer var1, @NotNull ProtoBuf.Property var2, @NotNull KotlinType var3);
}

