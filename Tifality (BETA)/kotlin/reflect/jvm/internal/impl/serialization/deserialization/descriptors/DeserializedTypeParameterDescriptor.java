/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.AbstractLazyTypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoTypeTableUtilKt;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationContext;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.NameResolverUtilKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ProtoEnumFlags;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedAnnotations;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;

public final class DeserializedTypeParameterDescriptor
extends AbstractLazyTypeParameterDescriptor {
    @NotNull
    private final DeserializedAnnotations annotations;
    private final DeserializationContext c;
    @NotNull
    private final ProtoBuf.TypeParameter proto;

    @Override
    @NotNull
    public DeserializedAnnotations getAnnotations() {
        return this.annotations;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    protected List<KotlinType> resolveUpperBounds() {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        List<ProtoBuf.Type> upperBounds2 = ProtoTypeTableUtilKt.upperBounds(this.proto, this.c.getTypeTable());
        if (upperBounds2.isEmpty()) {
            return CollectionsKt.listOf(DescriptorUtilsKt.getBuiltIns(this).getDefaultBound());
        }
        Iterable iterable = upperBounds2;
        TypeDeserializer typeDeserializer = this.c.getTypeDeserializer();
        boolean $i$f$map = false;
        void var5_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void p1;
            ProtoBuf.Type type2 = (ProtoBuf.Type)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            KotlinType kotlinType = typeDeserializer.type((ProtoBuf.Type)p1);
            collection.add(kotlinType);
        }
        return (List)destination$iv$iv;
    }

    @NotNull
    protected Void reportSupertypeLoopError(@NotNull KotlinType type2) {
        Intrinsics.checkNotNullParameter(type2, "type");
        throw (Throwable)new IllegalStateException("There should be no cycles for deserialized type parameters, but found for: " + this);
    }

    @NotNull
    public final ProtoBuf.TypeParameter getProto() {
        return this.proto;
    }

    public DeserializedTypeParameterDescriptor(@NotNull DeserializationContext c, @NotNull ProtoBuf.TypeParameter proto, int index) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(proto, "proto");
        StorageManager storageManager = c.getStorageManager();
        DeclarationDescriptor declarationDescriptor = c.getContainingDeclaration();
        Name name = NameResolverUtilKt.getName(c.getNameResolver(), proto.getName());
        ProtoBuf.TypeParameter.Variance variance = proto.getVariance();
        Intrinsics.checkNotNullExpressionValue(variance, "proto.variance");
        super(storageManager, declarationDescriptor, name, ProtoEnumFlags.INSTANCE.variance(variance), proto.getReified(), index, SourceElement.NO_SOURCE, SupertypeLoopChecker.EMPTY.INSTANCE);
        this.c = c;
        this.proto = proto;
        this.annotations = new DeserializedAnnotations(this.c.getStorageManager(), (Function0<? extends List<? extends AnnotationDescriptor>>)new Function0<List<? extends AnnotationDescriptor>>(this){
            final /* synthetic */ DeserializedTypeParameterDescriptor this$0;

            @NotNull
            public final List<AnnotationDescriptor> invoke() {
                return CollectionsKt.toList((Iterable)DeserializedTypeParameterDescriptor.access$getC$p(this.this$0).getComponents().getAnnotationAndConstantLoader().loadTypeParameterAnnotations(this.this$0.getProto(), DeserializedTypeParameterDescriptor.access$getC$p(this.this$0).getNameResolver()));
            }
            {
                this.this$0 = deserializedTypeParameterDescriptor;
                super(0);
            }
        });
    }

    public static final /* synthetic */ DeserializationContext access$getC$p(DeserializedTypeParameterDescriptor $this) {
        return $this.c;
    }
}

