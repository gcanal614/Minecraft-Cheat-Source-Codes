/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import org.jetbrains.annotations.NotNull;

public final class TypeTable {
    @NotNull
    private final List<ProtoBuf.Type> types;

    @NotNull
    public final ProtoBuf.Type get(int index) {
        return this.types.get(index);
    }

    /*
     * WARNING - void declaration
     */
    public TypeTable(@NotNull ProtoBuf.TypeTable typeTable) {
        List list;
        List list2;
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        TypeTable typeTable2 = this;
        boolean bl = false;
        boolean bl2 = false;
        TypeTable typeTable3 = typeTable2;
        TypeTable typeTable4 = this;
        boolean bl3 = false;
        List originalTypes = typeTable.getTypeList();
        if (typeTable.hasFirstNullable()) {
            void $this$mapIndexedTo$iv$iv;
            int firstNullable = typeTable.getFirstNullable();
            List<ProtoBuf.Type> list3 = typeTable.getTypeList();
            Intrinsics.checkNotNullExpressionValue(list3, "typeTable.typeList");
            Iterable $this$mapIndexed$iv = list3;
            boolean $i$f$mapIndexed = false;
            Iterable iterable = $this$mapIndexed$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
            boolean $i$f$mapIndexedTo = false;
            int index$iv$iv = 0;
            for (Object item$iv$iv : $this$mapIndexedTo$iv$iv) {
                void type2;
                void i;
                int n = index$iv$iv++;
                boolean bl4 = false;
                if (n < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                ProtoBuf.Type type3 = (ProtoBuf.Type)item$iv$iv;
                int n2 = n;
                Collection collection = destination$iv$iv;
                boolean bl5 = false;
                void var23_24 = i >= firstNullable ? type2.toBuilder().setNullable(true).build() : type2;
                collection.add(var23_24);
            }
            list2 = (List)destination$iv$iv;
        } else {
            list2 = originalTypes;
        }
        List list4 = list = list2;
        Intrinsics.checkNotNullExpressionValue(list4, "run {\n        val origin\u2026 else originalTypes\n    }");
        typeTable4.types = list4;
    }
}

