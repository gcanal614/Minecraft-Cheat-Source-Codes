/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ProtoTypeTableUtilKt {
    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final List<ProtoBuf.Type> supertypes(@NotNull ProtoBuf.Class $this$supertypes, @NotNull TypeTable typeTable) {
        Intrinsics.checkNotNullParameter($this$supertypes, "$this$supertypes");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        List list = $this$supertypes.getSupertypeList();
        boolean bl = false;
        boolean bl2 = false;
        Collection p1 = list;
        boolean bl3 = false;
        Collection collection = p1;
        boolean bl4 = false;
        List list2 = !collection.isEmpty() ? list : null;
        if (list2 == null) {
            void $this$mapTo$iv$iv;
            List<Integer> list3 = $this$supertypes.getSupertypeIdList();
            Intrinsics.checkNotNullExpressionValue(list3, "supertypeIdList");
            Iterable $this$map$iv = list3;
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                Integer n = (Integer)item$iv$iv;
                Collection collection2 = destination$iv$iv;
                boolean bl5 = false;
                void v2 = it;
                Intrinsics.checkNotNullExpressionValue(v2, "it");
                ProtoBuf.Type type2 = typeTable.get(v2.intValue());
                collection2.add(type2);
            }
            list2 = (List)destination$iv$iv;
        }
        return list2;
    }

    @Nullable
    public static final ProtoBuf.Type type(@NotNull ProtoBuf.Type.Argument $this$type, @NotNull TypeTable typeTable) {
        Intrinsics.checkNotNullParameter($this$type, "$this$type");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        return $this$type.hasType() ? $this$type.getType() : ($this$type.hasTypeId() ? typeTable.get($this$type.getTypeId()) : null);
    }

    @Nullable
    public static final ProtoBuf.Type flexibleUpperBound(@NotNull ProtoBuf.Type $this$flexibleUpperBound, @NotNull TypeTable typeTable) {
        Intrinsics.checkNotNullParameter($this$flexibleUpperBound, "$this$flexibleUpperBound");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        return $this$flexibleUpperBound.hasFlexibleUpperBound() ? $this$flexibleUpperBound.getFlexibleUpperBound() : ($this$flexibleUpperBound.hasFlexibleUpperBoundId() ? typeTable.get($this$flexibleUpperBound.getFlexibleUpperBoundId()) : null);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final List<ProtoBuf.Type> upperBounds(@NotNull ProtoBuf.TypeParameter $this$upperBounds, @NotNull TypeTable typeTable) {
        Intrinsics.checkNotNullParameter($this$upperBounds, "$this$upperBounds");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        List list = $this$upperBounds.getUpperBoundList();
        boolean bl = false;
        boolean bl2 = false;
        Collection p1 = list;
        boolean bl3 = false;
        Collection collection = p1;
        boolean bl4 = false;
        List list2 = !collection.isEmpty() ? list : null;
        if (list2 == null) {
            void $this$mapTo$iv$iv;
            List<Integer> list3 = $this$upperBounds.getUpperBoundIdList();
            Intrinsics.checkNotNullExpressionValue(list3, "upperBoundIdList");
            Iterable $this$map$iv = list3;
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                Integer n = (Integer)item$iv$iv;
                Collection collection2 = destination$iv$iv;
                boolean bl5 = false;
                void v2 = it;
                Intrinsics.checkNotNullExpressionValue(v2, "it");
                ProtoBuf.Type type2 = typeTable.get(v2.intValue());
                collection2.add(type2);
            }
            list2 = (List)destination$iv$iv;
        }
        return list2;
    }

    @NotNull
    public static final ProtoBuf.Type returnType(@NotNull ProtoBuf.Function $this$returnType, @NotNull TypeTable typeTable) {
        ProtoBuf.Type type2;
        Intrinsics.checkNotNullParameter($this$returnType, "$this$returnType");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if ($this$returnType.hasReturnType()) {
            ProtoBuf.Type type3 = $this$returnType.getReturnType();
            type2 = type3;
            Intrinsics.checkNotNullExpressionValue(type3, "returnType");
        } else if ($this$returnType.hasReturnTypeId()) {
            type2 = typeTable.get($this$returnType.getReturnTypeId());
        } else {
            String string = "No returnType in ProtoBuf.Function";
            boolean bl = false;
            throw (Throwable)new IllegalStateException(string.toString());
        }
        return type2;
    }

    public static final boolean hasReceiver(@NotNull ProtoBuf.Function $this$hasReceiver) {
        Intrinsics.checkNotNullParameter($this$hasReceiver, "$this$hasReceiver");
        return $this$hasReceiver.hasReceiverType() || $this$hasReceiver.hasReceiverTypeId();
    }

    @Nullable
    public static final ProtoBuf.Type receiverType(@NotNull ProtoBuf.Function $this$receiverType, @NotNull TypeTable typeTable) {
        Intrinsics.checkNotNullParameter($this$receiverType, "$this$receiverType");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        return $this$receiverType.hasReceiverType() ? $this$receiverType.getReceiverType() : ($this$receiverType.hasReceiverTypeId() ? typeTable.get($this$receiverType.getReceiverTypeId()) : null);
    }

    @NotNull
    public static final ProtoBuf.Type returnType(@NotNull ProtoBuf.Property $this$returnType, @NotNull TypeTable typeTable) {
        ProtoBuf.Type type2;
        Intrinsics.checkNotNullParameter($this$returnType, "$this$returnType");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if ($this$returnType.hasReturnType()) {
            ProtoBuf.Type type3 = $this$returnType.getReturnType();
            type2 = type3;
            Intrinsics.checkNotNullExpressionValue(type3, "returnType");
        } else if ($this$returnType.hasReturnTypeId()) {
            type2 = typeTable.get($this$returnType.getReturnTypeId());
        } else {
            String string = "No returnType in ProtoBuf.Property";
            boolean bl = false;
            throw (Throwable)new IllegalStateException(string.toString());
        }
        return type2;
    }

    public static final boolean hasReceiver(@NotNull ProtoBuf.Property $this$hasReceiver) {
        Intrinsics.checkNotNullParameter($this$hasReceiver, "$this$hasReceiver");
        return $this$hasReceiver.hasReceiverType() || $this$hasReceiver.hasReceiverTypeId();
    }

    @Nullable
    public static final ProtoBuf.Type receiverType(@NotNull ProtoBuf.Property $this$receiverType, @NotNull TypeTable typeTable) {
        Intrinsics.checkNotNullParameter($this$receiverType, "$this$receiverType");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        return $this$receiverType.hasReceiverType() ? $this$receiverType.getReceiverType() : ($this$receiverType.hasReceiverTypeId() ? typeTable.get($this$receiverType.getReceiverTypeId()) : null);
    }

    @NotNull
    public static final ProtoBuf.Type type(@NotNull ProtoBuf.ValueParameter $this$type, @NotNull TypeTable typeTable) {
        ProtoBuf.Type type2;
        Intrinsics.checkNotNullParameter($this$type, "$this$type");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if ($this$type.hasType()) {
            ProtoBuf.Type type3 = $this$type.getType();
            type2 = type3;
            Intrinsics.checkNotNullExpressionValue(type3, "type");
        } else if ($this$type.hasTypeId()) {
            type2 = typeTable.get($this$type.getTypeId());
        } else {
            String string = "No type in ProtoBuf.ValueParameter";
            boolean bl = false;
            throw (Throwable)new IllegalStateException(string.toString());
        }
        return type2;
    }

    @Nullable
    public static final ProtoBuf.Type varargElementType(@NotNull ProtoBuf.ValueParameter $this$varargElementType, @NotNull TypeTable typeTable) {
        Intrinsics.checkNotNullParameter($this$varargElementType, "$this$varargElementType");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        return $this$varargElementType.hasVarargElementType() ? $this$varargElementType.getVarargElementType() : ($this$varargElementType.hasVarargElementTypeId() ? typeTable.get($this$varargElementType.getVarargElementTypeId()) : null);
    }

    @Nullable
    public static final ProtoBuf.Type outerType(@NotNull ProtoBuf.Type $this$outerType, @NotNull TypeTable typeTable) {
        Intrinsics.checkNotNullParameter($this$outerType, "$this$outerType");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        return $this$outerType.hasOuterType() ? $this$outerType.getOuterType() : ($this$outerType.hasOuterTypeId() ? typeTable.get($this$outerType.getOuterTypeId()) : null);
    }

    @Nullable
    public static final ProtoBuf.Type abbreviatedType(@NotNull ProtoBuf.Type $this$abbreviatedType, @NotNull TypeTable typeTable) {
        Intrinsics.checkNotNullParameter($this$abbreviatedType, "$this$abbreviatedType");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        return $this$abbreviatedType.hasAbbreviatedType() ? $this$abbreviatedType.getAbbreviatedType() : ($this$abbreviatedType.hasAbbreviatedTypeId() ? typeTable.get($this$abbreviatedType.getAbbreviatedTypeId()) : null);
    }

    @NotNull
    public static final ProtoBuf.Type underlyingType(@NotNull ProtoBuf.TypeAlias $this$underlyingType, @NotNull TypeTable typeTable) {
        ProtoBuf.Type type2;
        Intrinsics.checkNotNullParameter($this$underlyingType, "$this$underlyingType");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if ($this$underlyingType.hasUnderlyingType()) {
            ProtoBuf.Type type3 = $this$underlyingType.getUnderlyingType();
            type2 = type3;
            Intrinsics.checkNotNullExpressionValue(type3, "underlyingType");
        } else if ($this$underlyingType.hasUnderlyingTypeId()) {
            type2 = typeTable.get($this$underlyingType.getUnderlyingTypeId());
        } else {
            String string = "No underlyingType in ProtoBuf.TypeAlias";
            boolean bl = false;
            throw (Throwable)new IllegalStateException(string.toString());
        }
        return type2;
    }

    @NotNull
    public static final ProtoBuf.Type expandedType(@NotNull ProtoBuf.TypeAlias $this$expandedType, @NotNull TypeTable typeTable) {
        ProtoBuf.Type type2;
        Intrinsics.checkNotNullParameter($this$expandedType, "$this$expandedType");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        if ($this$expandedType.hasExpandedType()) {
            ProtoBuf.Type type3 = $this$expandedType.getExpandedType();
            type2 = type3;
            Intrinsics.checkNotNullExpressionValue(type3, "expandedType");
        } else if ($this$expandedType.hasExpandedTypeId()) {
            type2 = typeTable.get($this$expandedType.getExpandedTypeId());
        } else {
            String string = "No expandedType in ProtoBuf.TypeAlias";
            boolean bl = false;
            throw (Throwable)new IllegalStateException(string.toString());
        }
        return type2;
    }
}

