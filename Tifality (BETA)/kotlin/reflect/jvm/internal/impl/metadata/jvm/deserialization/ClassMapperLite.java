/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public final class ClassMapperLite {
    private static final String kotlin;
    private static final Map<String, String> map;
    public static final ClassMapperLite INSTANCE;

    @JvmStatic
    @NotNull
    public static final String mapClass(@NotNull String classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        String string = map.get(classId);
        if (string == null) {
            string = 'L' + StringsKt.replace$default(classId, '.', '$', false, 4, null) + ';';
        }
        return string;
    }

    private ClassMapperLite() {
    }

    /*
     * WARNING - void declaration
     */
    static {
        ClassMapperLite classMapperLite;
        INSTANCE = classMapperLite = new ClassMapperLite();
        kotlin = CollectionsKt.joinToString$default(CollectionsKt.listOf(Character.valueOf('k'), Character.valueOf('o'), Character.valueOf('t'), Character.valueOf('l'), Character.valueOf('i'), Character.valueOf('n')), "", null, null, 0, null, null, 62, null);
        boolean bl = false;
        Map map2 = new LinkedHashMap();
        boolean bl2 = false;
        boolean bl3 = false;
        Map $this$apply = map2;
        boolean bl4 = false;
        List<String> primitives = CollectionsKt.listOf("Boolean", "Z", "Char", "C", "Byte", "B", "Short", "S", "Int", "I", "Float", "F", "Long", "J", "Double", "D");
        IntProgression intProgression = RangesKt.step(CollectionsKt.getIndices((Collection)primitives), 2);
        int n = intProgression.getFirst();
        int n2 = intProgression.getLast();
        int n3 = intProgression.getStep();
        int n4 = n;
        int n5 = n2;
        if (n3 >= 0 ? n4 <= n5 : n4 >= n5) {
            while (true) {
                void i;
                $this$apply.put(kotlin + '/' + primitives.get((int)i), primitives.get((int)(i + true)));
                $this$apply.put(kotlin + '/' + primitives.get((int)i) + "Array", '[' + primitives.get((int)(i + true)));
                if (i == n2) break;
                i += n3;
            }
        }
        $this$apply.put(kotlin + "/Unit", "V");
        Function2<String, String, Unit> $fun$add$1 = new Function2<String, String, Unit>($this$apply){
            final /* synthetic */ Map $this_apply;

            public final void invoke(@NotNull String kotlinSimpleName, @NotNull String javaInternalName) {
                Intrinsics.checkNotNullParameter(kotlinSimpleName, "kotlinSimpleName");
                Intrinsics.checkNotNullParameter(javaInternalName, "javaInternalName");
                this.$this_apply.put(ClassMapperLite.access$getKotlin$p(ClassMapperLite.INSTANCE) + '/' + kotlinSimpleName, 'L' + javaInternalName + ';');
            }
            {
                this.$this_apply = map2;
                super(2);
            }
        };
        $fun$add$1.invoke("Any", "java/lang/Object");
        $fun$add$1.invoke("Nothing", "java/lang/Void");
        $fun$add$1.invoke("Annotation", "java/lang/annotation/Annotation");
        for (String klass : CollectionsKt.listOf("String", "CharSequence", "Throwable", "Cloneable", "Number", "Comparable", "Enum")) {
            $fun$add$1.invoke(klass, "java/lang/" + klass);
        }
        for (String klass : CollectionsKt.listOf("Iterator", "Collection", "List", "Set", "Map", "ListIterator")) {
            $fun$add$1.invoke("collections/" + klass, "java/util/" + klass);
            $fun$add$1.invoke("collections/Mutable" + klass, "java/util/" + klass);
        }
        $fun$add$1.invoke("collections/Iterable", "java/lang/Iterable");
        $fun$add$1.invoke("collections/MutableIterable", "java/lang/Iterable");
        $fun$add$1.invoke("collections/Map.Entry", "java/util/Map$Entry");
        $fun$add$1.invoke("collections/MutableMap.MutableEntry", "java/util/Map$Entry");
        int klass = 0;
        int n6 = 22;
        while (klass <= n6) {
            void i;
            $fun$add$1.invoke("Function" + (int)i, kotlin + "/jvm/functions/Function" + (int)i);
            $fun$add$1.invoke("reflect/KFunction" + (int)i, kotlin + "/reflect/KFunction");
            ++i;
        }
        for (String klass2 : CollectionsKt.listOf("Char", "Byte", "Short", "Int", "Float", "Long", "Double", "String", "Enum")) {
            $fun$add$1.invoke(klass2 + ".Companion", kotlin + "/jvm/internal/" + klass2 + "CompanionObject");
        }
        map = map2;
    }

    public static final /* synthetic */ String access$getKotlin$p(ClassMapperLite $this) {
        ClassMapperLite classMapperLite = $this;
        return kotlin;
    }
}

