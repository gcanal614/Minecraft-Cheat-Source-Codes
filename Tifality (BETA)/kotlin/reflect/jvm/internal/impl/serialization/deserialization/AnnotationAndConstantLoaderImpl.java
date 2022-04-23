/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoBufUtilKt;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.serialization.SerializerExtensionProtocol;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotatedCallableKind;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotationAndConstantLoader;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotationAndConstantLoaderImpl$WhenMappings;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotationDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ProtoContainer;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AnnotationAndConstantLoaderImpl
implements AnnotationAndConstantLoader<AnnotationDescriptor, ConstantValue<?>> {
    private final AnnotationDeserializer deserializer;
    private final SerializerExtensionProtocol protocol;

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public List<AnnotationDescriptor> loadClassAnnotations(@NotNull ProtoContainer.Class container) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(container, "container");
        List<ProtoBuf.Annotation> list = container.getClassProto().getExtension(this.protocol.getClassAnnotation());
        boolean bl = false;
        List<ProtoBuf.Annotation> list2 = list;
        if (list2 == null) {
            list2 = CollectionsKt.emptyList();
        }
        List<ProtoBuf.Annotation> annotations2 = list2;
        Iterable $this$map$iv = annotations2;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void proto;
            ProtoBuf.Annotation annotation = (ProtoBuf.Annotation)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl2 = false;
            AnnotationDescriptor annotationDescriptor = this.deserializer.deserializeAnnotation((ProtoBuf.Annotation)proto, container.getNameResolver());
            collection.add(annotationDescriptor);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public List<AnnotationDescriptor> loadCallableAnnotations(@NotNull ProtoContainer container, @NotNull MessageLite proto, @NotNull AnnotatedCallableKind kind) {
        void $this$mapTo$iv$iv;
        List<ProtoBuf.Annotation> list;
        List list2;
        block8: {
            block10: {
                block9: {
                    block7: {
                        Intrinsics.checkNotNullParameter(container, "container");
                        Intrinsics.checkNotNullParameter(proto, "proto");
                        Intrinsics.checkNotNullParameter((Object)kind, "kind");
                        list2 = proto;
                        if (!(list2 instanceof ProtoBuf.Constructor)) break block7;
                        list = ((ProtoBuf.Constructor)proto).getExtension(this.protocol.getConstructorAnnotation());
                        break block8;
                    }
                    if (!(list2 instanceof ProtoBuf.Function)) break block9;
                    list = ((ProtoBuf.Function)proto).getExtension(this.protocol.getFunctionAnnotation());
                    break block8;
                }
                if (!(list2 instanceof ProtoBuf.Property)) break block10;
                switch (AnnotationAndConstantLoaderImpl$WhenMappings.$EnumSwitchMapping$0[kind.ordinal()]) {
                    case 1: {
                        list = ((ProtoBuf.Property)proto).getExtension(this.protocol.getPropertyAnnotation());
                        break block8;
                    }
                    case 2: {
                        list = ((ProtoBuf.Property)proto).getExtension(this.protocol.getPropertyGetterAnnotation());
                        break block8;
                    }
                    case 3: {
                        list = ((ProtoBuf.Property)proto).getExtension(this.protocol.getPropertySetterAnnotation());
                        break block8;
                    }
                    default: {
                        String string = "Unsupported callable kind with property proto";
                        boolean bl = false;
                        throw (Throwable)new IllegalStateException(string.toString());
                    }
                }
            }
            String string = "Unknown message: " + proto;
            boolean bl = false;
            throw (Throwable)new IllegalStateException(string.toString());
        }
        list2 = list;
        boolean bl = false;
        List list3 = list2;
        if (list3 == null) {
            list3 = CollectionsKt.emptyList();
        }
        List annotations2 = list3;
        Iterable $this$map$iv = annotations2;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void annotationProto;
            ProtoBuf.Annotation annotation = (ProtoBuf.Annotation)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl2 = false;
            AnnotationDescriptor annotationDescriptor = this.deserializer.deserializeAnnotation((ProtoBuf.Annotation)annotationProto, container.getNameResolver());
            collection.add(annotationDescriptor);
        }
        return (List)destination$iv$iv;
    }

    @Override
    @NotNull
    public List<AnnotationDescriptor> loadPropertyBackingFieldAnnotations(@NotNull ProtoContainer container, @NotNull ProtoBuf.Property proto) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        return CollectionsKt.emptyList();
    }

    @Override
    @NotNull
    public List<AnnotationDescriptor> loadPropertyDelegateFieldAnnotations(@NotNull ProtoContainer container, @NotNull ProtoBuf.Property proto) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        return CollectionsKt.emptyList();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public List<AnnotationDescriptor> loadEnumEntryAnnotations(@NotNull ProtoContainer container, @NotNull ProtoBuf.EnumEntry proto) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        List<ProtoBuf.Annotation> list = proto.getExtension(this.protocol.getEnumEntryAnnotation());
        boolean bl = false;
        List<ProtoBuf.Annotation> list2 = list;
        if (list2 == null) {
            list2 = CollectionsKt.emptyList();
        }
        List<ProtoBuf.Annotation> annotations2 = list2;
        Iterable $this$map$iv = annotations2;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void annotationProto;
            ProtoBuf.Annotation annotation = (ProtoBuf.Annotation)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl2 = false;
            AnnotationDescriptor annotationDescriptor = this.deserializer.deserializeAnnotation((ProtoBuf.Annotation)annotationProto, container.getNameResolver());
            collection.add(annotationDescriptor);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public List<AnnotationDescriptor> loadValueParameterAnnotations(@NotNull ProtoContainer container, @NotNull MessageLite callableProto, @NotNull AnnotatedCallableKind kind, int parameterIndex, @NotNull ProtoBuf.ValueParameter proto) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(callableProto, "callableProto");
        Intrinsics.checkNotNullParameter((Object)kind, "kind");
        Intrinsics.checkNotNullParameter(proto, "proto");
        List<ProtoBuf.Annotation> list = proto.getExtension(this.protocol.getParameterAnnotation());
        boolean bl = false;
        List<ProtoBuf.Annotation> list2 = list;
        if (list2 == null) {
            list2 = CollectionsKt.emptyList();
        }
        List<ProtoBuf.Annotation> annotations2 = list2;
        Iterable $this$map$iv = annotations2;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void annotationProto;
            ProtoBuf.Annotation annotation = (ProtoBuf.Annotation)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl2 = false;
            AnnotationDescriptor annotationDescriptor = this.deserializer.deserializeAnnotation((ProtoBuf.Annotation)annotationProto, container.getNameResolver());
            collection.add(annotationDescriptor);
        }
        return (List)destination$iv$iv;
    }

    @Override
    @NotNull
    public List<AnnotationDescriptor> loadExtensionReceiverParameterAnnotations(@NotNull ProtoContainer container, @NotNull MessageLite proto, @NotNull AnnotatedCallableKind kind) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter((Object)kind, "kind");
        return CollectionsKt.emptyList();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public List<AnnotationDescriptor> loadTypeAnnotations(@NotNull ProtoBuf.Type proto, @NotNull NameResolver nameResolver) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        List<ProtoBuf.Annotation> list = proto.getExtension(this.protocol.getTypeAnnotation());
        boolean bl = false;
        List<ProtoBuf.Annotation> list2 = list;
        if (list2 == null) {
            list2 = CollectionsKt.emptyList();
        }
        Iterable $this$map$iv = list2;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ProtoBuf.Annotation annotation = (ProtoBuf.Annotation)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl2 = false;
            AnnotationDescriptor annotationDescriptor = this.deserializer.deserializeAnnotation((ProtoBuf.Annotation)it, nameResolver);
            collection.add(annotationDescriptor);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public List<AnnotationDescriptor> loadTypeParameterAnnotations(@NotNull ProtoBuf.TypeParameter proto, @NotNull NameResolver nameResolver) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        List<ProtoBuf.Annotation> list = proto.getExtension(this.protocol.getTypeParameterAnnotation());
        boolean bl = false;
        List<ProtoBuf.Annotation> list2 = list;
        if (list2 == null) {
            list2 = CollectionsKt.emptyList();
        }
        Iterable $this$map$iv = list2;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ProtoBuf.Annotation annotation = (ProtoBuf.Annotation)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl2 = false;
            AnnotationDescriptor annotationDescriptor = this.deserializer.deserializeAnnotation((ProtoBuf.Annotation)it, nameResolver);
            collection.add(annotationDescriptor);
        }
        return (List)destination$iv$iv;
    }

    @Override
    @Nullable
    public ConstantValue<?> loadPropertyConstant(@NotNull ProtoContainer container, @NotNull ProtoBuf.Property proto, @NotNull KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        ProtoBuf.Annotation.Argument.Value value = ProtoBufUtilKt.getExtensionOrNull(proto, this.protocol.getCompileTimeValue());
        if (value == null) {
            return null;
        }
        ProtoBuf.Annotation.Argument.Value value2 = value;
        return this.deserializer.resolveValue(expectedType, value2, container.getNameResolver());
    }

    public AnnotationAndConstantLoaderImpl(@NotNull ModuleDescriptor module, @NotNull NotFoundClasses notFoundClasses, @NotNull SerializerExtensionProtocol protocol) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        Intrinsics.checkNotNullParameter(protocol, "protocol");
        this.protocol = protocol;
        this.deserializer = new AnnotationDeserializer(module, notFoundClasses);
    }
}

