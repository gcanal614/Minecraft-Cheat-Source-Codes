/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassData;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassDataFinder;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ProtoBasedClassDataFinder
implements ClassDataFinder {
    private final Map<ClassId, ProtoBuf.Class> classIdToProto;
    private final NameResolver nameResolver;
    private final BinaryVersion metadataVersion;
    private final Function1<ClassId, SourceElement> classSource;

    @NotNull
    public final Collection<ClassId> getAllClassIds() {
        return this.classIdToProto.keySet();
    }

    @Override
    @Nullable
    public ClassData findClassData(@NotNull ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        ProtoBuf.Class clazz = this.classIdToProto.get(classId);
        if (clazz == null) {
            return null;
        }
        ProtoBuf.Class classProto = clazz;
        return new ClassData(this.nameResolver, classProto, this.metadataVersion, this.classSource.invoke(classId));
    }

    /*
     * WARNING - void declaration
     */
    public ProtoBasedClassDataFinder(@NotNull ProtoBuf.PackageFragment proto, @NotNull NameResolver nameResolver, @NotNull BinaryVersion metadataVersion, @NotNull Function1<? super ClassId, ? extends SourceElement> classSource) {
        Map map2;
        void $this$associateByTo$iv$iv;
        void $this$associateBy$iv;
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(classSource, "classSource");
        this.nameResolver = nameResolver;
        this.metadataVersion = metadataVersion;
        this.classSource = classSource;
        List<ProtoBuf.Class> list = proto.getClass_List();
        Intrinsics.checkNotNullExpressionValue(list, "proto.class_List");
        Iterable iterable = list;
        ProtoBasedClassDataFinder protoBasedClassDataFinder = this;
        boolean $i$f$associateBy = false;
        int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associateBy$iv, 10)), 16);
        void var8_9 = $this$associateBy$iv;
        Map destination$iv$iv = new LinkedHashMap(capacity$iv);
        boolean $i$f$associateByTo = false;
        for (Object element$iv$iv : $this$associateByTo$iv$iv) {
            void klass;
            ProtoBuf.Class clazz = (ProtoBuf.Class)element$iv$iv;
            map2 = destination$iv$iv;
            boolean bl = false;
            void v1 = klass;
            Intrinsics.checkNotNullExpressionValue(v1, "klass");
            ClassId classId = NameResolverUtilKt.getClassId(this.nameResolver, v1.getFqName());
            map2.put(classId, element$iv$iv);
        }
        protoBasedClassDataFinder.classIdToProto = map2 = destination$iv$iv;
    }
}

