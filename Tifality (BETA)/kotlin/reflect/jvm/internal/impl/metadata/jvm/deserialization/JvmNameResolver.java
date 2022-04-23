/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmNameResolver$WhenMappings;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public final class JvmNameResolver
implements NameResolver {
    private final Set<Integer> localNameIndices;
    private final List<JvmProtoBuf.StringTableTypes.Record> records;
    @NotNull
    private final JvmProtoBuf.StringTableTypes types;
    @NotNull
    private final String[] strings;
    private static final String kotlin;
    @NotNull
    private static final List<String> PREDEFINED_STRINGS;
    private static final Map<String, Integer> PREDEFINED_STRINGS_MAP;
    public static final Companion Companion;

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public String getString(int index) {
        JvmProtoBuf.StringTableTypes.Record.Operation operation;
        int n;
        Object object;
        int n2;
        String string;
        JvmProtoBuf.StringTableTypes.Record record = this.records.get(index);
        String string2 = record.hasString() ? record.getString() : (string = record.hasPredefinedIndex() && 0 <= (n2 = record.getPredefinedIndex()) && ((Collection)PREDEFINED_STRINGS).size() > n2 ? PREDEFINED_STRINGS.get(record.getPredefinedIndex()) : this.strings[index]);
        if (record.getSubstringIndexCount() >= 2) {
            void begin;
            object = record.getSubstringIndexList();
            List<Integer> list = object;
            n = 0;
            Integer n3 = list.get(0);
            list = object;
            n = 0;
            Integer end = list.get(1);
            void v1 = begin;
            Intrinsics.checkNotNullExpressionValue(v1, "begin");
            if (0 <= v1.intValue()) {
                int n4 = begin.intValue();
                Integer n5 = end;
                Intrinsics.checkNotNullExpressionValue(n5, "end");
                if (n4 <= n5 && end <= string.length()) {
                    String string3 = string;
                    Intrinsics.checkNotNullExpressionValue(string3, "string");
                    object = string3;
                    int n6 = begin.intValue();
                    n = end;
                    boolean bl = false;
                    Object object2 = object;
                    if (object2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string4 = ((String)object2).substring(n6, n);
                    Intrinsics.checkNotNullExpressionValue(string4, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    string = string4;
                }
            }
        }
        if (record.getReplaceCharCount() >= 2) {
            void from;
            object = record.getReplaceCharList();
            List<Integer> list = object;
            n = 0;
            Integer begin = list.get(0);
            list = object;
            n = 0;
            Integer to = list.get(1);
            String string5 = string;
            Intrinsics.checkNotNullExpressionValue(string5, "string");
            string = StringsKt.replace$default(string5, (char)from.intValue(), (char)to.intValue(), false, 4, null);
        }
        if ((operation = record.getOperation()) == null) {
            operation = JvmProtoBuf.StringTableTypes.Record.Operation.NONE;
        }
        switch (JvmNameResolver$WhenMappings.$EnumSwitchMapping$0[operation.ordinal()]) {
            case 1: {
                break;
            }
            case 2: {
                String string6 = string;
                Intrinsics.checkNotNullExpressionValue(string6, "string");
                string = StringsKt.replace$default(string6, '$', '.', false, 4, null);
                break;
            }
            case 3: {
                if (string.length() >= 2) {
                    String string7 = string;
                    Intrinsics.checkNotNullExpressionValue(string7, "string");
                    String string8 = string7;
                    int n7 = 1;
                    int n8 = string.length() - 1;
                    boolean bl = false;
                    String string9 = string8;
                    if (string9 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                    }
                    String string10 = string9.substring(n7, n8);
                    Intrinsics.checkNotNullExpressionValue(string10, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    string = string10;
                }
                String string11 = string;
                Intrinsics.checkNotNullExpressionValue(string11, "string");
                string = StringsKt.replace$default(string11, '$', '.', false, 4, null);
                break;
            }
        }
        String string12 = string;
        Intrinsics.checkNotNullExpressionValue(string12, "string");
        return string12;
    }

    @Override
    @NotNull
    public String getQualifiedClassName(int index) {
        return this.getString(index);
    }

    @Override
    public boolean isLocalClassName(int index) {
        return this.localNameIndices.contains(index);
    }

    /*
     * WARNING - void declaration
     */
    public JvmNameResolver(@NotNull JvmProtoBuf.StringTableTypes types, @NotNull String[] strings) {
        void $this$apply;
        List<Integer> $this$run;
        Intrinsics.checkNotNullParameter(types, "types");
        Intrinsics.checkNotNullParameter(strings, "strings");
        this.types = types;
        this.strings = strings;
        List<Integer> list = this.types.getLocalNameList();
        boolean bl = false;
        boolean bl2 = false;
        List<Integer> list2 = list;
        JvmNameResolver jvmNameResolver = this;
        boolean bl3 = false;
        Object object = $this$run.isEmpty() ? SetsKt.emptySet() : CollectionsKt.toSet((Iterable)$this$run);
        jvmNameResolver.localNameIndices = object;
        list = new ArrayList<Integer>();
        bl = false;
        bl2 = false;
        $this$run = list;
        jvmNameResolver = this;
        boolean bl4 = false;
        List<JvmProtoBuf.StringTableTypes.Record> records = this.types.getRecordList();
        $this$apply.ensureCapacity(records.size());
        Iterator<JvmProtoBuf.StringTableTypes.Record> iterator2 = records.iterator();
        while (iterator2.hasNext()) {
            JvmProtoBuf.StringTableTypes.Record record;
            JvmProtoBuf.StringTableTypes.Record record2 = record = iterator2.next();
            Intrinsics.checkNotNullExpressionValue(record2, "record");
            int n = record2.getRange();
            boolean bl5 = false;
            int n2 = 0;
            n2 = 0;
            int n3 = n;
            while (n2 < n3) {
                int it = n2++;
                boolean bl6 = false;
                $this$apply.add(record);
            }
        }
        $this$apply.trimToSize();
        object = Unit.INSTANCE;
        jvmNameResolver.records = list;
    }

    /*
     * WARNING - void declaration
     */
    static {
        void $this$associateByTo$iv$iv;
        Companion = new Companion(null);
        kotlin = CollectionsKt.joinToString$default(CollectionsKt.listOf(Character.valueOf('k'), Character.valueOf('o'), Character.valueOf('t'), Character.valueOf('l'), Character.valueOf('i'), Character.valueOf('n')), "", null, null, 0, null, null, 62, null);
        PREDEFINED_STRINGS = CollectionsKt.listOf(kotlin + "/Any", kotlin + "/Nothing", kotlin + "/Unit", kotlin + "/Throwable", kotlin + "/Number", kotlin + "/Byte", kotlin + "/Double", kotlin + "/Float", kotlin + "/Int", kotlin + "/Long", kotlin + "/Short", kotlin + "/Boolean", kotlin + "/Char", kotlin + "/CharSequence", kotlin + "/String", kotlin + "/Comparable", kotlin + "/Enum", kotlin + "/Array", kotlin + "/ByteArray", kotlin + "/DoubleArray", kotlin + "/FloatArray", kotlin + "/IntArray", kotlin + "/LongArray", kotlin + "/ShortArray", kotlin + "/BooleanArray", kotlin + "/CharArray", kotlin + "/Cloneable", kotlin + "/Annotation", kotlin + "/collections/Iterable", kotlin + "/collections/MutableIterable", kotlin + "/collections/Collection", kotlin + "/collections/MutableCollection", kotlin + "/collections/List", kotlin + "/collections/MutableList", kotlin + "/collections/Set", kotlin + "/collections/MutableSet", kotlin + "/collections/Map", kotlin + "/collections/MutableMap", kotlin + "/collections/Map.Entry", kotlin + "/collections/MutableMap.MutableEntry", kotlin + "/collections/Iterator", kotlin + "/collections/MutableIterator", kotlin + "/collections/ListIterator", kotlin + "/collections/MutableListIterator");
        Iterable $this$associateBy$iv = CollectionsKt.withIndex((Iterable)PREDEFINED_STRINGS);
        boolean $i$f$associateBy = false;
        int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associateBy$iv, 10)), 16);
        Iterable iterable = $this$associateBy$iv;
        Map destination$iv$iv = new LinkedHashMap(capacity$iv);
        boolean $i$f$associateByTo = false;
        for (Object element$iv$iv : $this$associateByTo$iv$iv) {
            IndexedValue it;
            IndexedValue indexedValue = (IndexedValue)element$iv$iv;
            Map map2 = destination$iv$iv;
            boolean bl = false;
            String string = (String)it.getValue();
            it = (IndexedValue)element$iv$iv;
            boolean bl2 = false;
            Integer n = it.getIndex();
            map2.put(string, n);
        }
        PREDEFINED_STRINGS_MAP = destination$iv$iv;
    }

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

