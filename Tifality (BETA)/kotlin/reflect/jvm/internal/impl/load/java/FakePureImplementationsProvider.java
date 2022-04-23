/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class FakePureImplementationsProvider {
    private static final HashMap<FqName, FqName> pureImplementations;
    public static final FakePureImplementationsProvider INSTANCE;

    @Nullable
    public final FqName getPurelyImplementedInterface(@NotNull FqName classFqName) {
        Intrinsics.checkNotNullParameter(classFqName, "classFqName");
        return pureImplementations.get(classFqName);
    }

    /*
     * WARNING - void declaration
     */
    private final void implementedWith(FqName $this$implementedWith, List<FqName> implementations) {
        void $this$associateWithTo$iv;
        Iterable iterable = implementations;
        Map destination$iv = pureImplementations;
        boolean $i$f$associateWithTo = false;
        for (Object element$iv : $this$associateWithTo$iv) {
            FqName fqName2 = (FqName)element$iv;
            Object t = element$iv;
            Map map2 = destination$iv;
            boolean bl = false;
            FqName fqName3 = $this$implementedWith;
            map2.put(t, fqName3);
        }
    }

    /*
     * WARNING - void declaration
     */
    private final List<FqName> fqNameListOf(String ... names) {
        void $this$mapTo$iv$iv;
        String[] $this$map$iv = names;
        boolean $i$f$map = false;
        String[] stringArray = $this$map$iv;
        Collection destination$iv$iv = new ArrayList($this$map$iv.length);
        boolean $i$f$mapTo = false;
        void var7_7 = $this$mapTo$iv$iv;
        int n = ((void)var7_7).length;
        for (int i = 0; i < n; ++i) {
            void p1;
            void item$iv$iv;
            void var11_11 = item$iv$iv = var7_7[i];
            Collection collection = destination$iv$iv;
            boolean bl = false;
            FqName fqName2 = new FqName((String)p1);
            collection.add(fqName2);
        }
        return (List)destination$iv$iv;
    }

    private FakePureImplementationsProvider() {
    }

    static {
        FakePureImplementationsProvider fakePureImplementationsProvider;
        INSTANCE = fakePureImplementationsProvider = new FakePureImplementationsProvider();
        boolean bl = false;
        pureImplementations = new HashMap();
        FqName fqName2 = KotlinBuiltIns.FQ_NAMES.mutableList;
        Intrinsics.checkNotNullExpressionValue(fqName2, "FQ_NAMES.mutableList");
        fakePureImplementationsProvider.implementedWith(fqName2, fakePureImplementationsProvider.fqNameListOf("java.util.ArrayList", "java.util.LinkedList"));
        FqName fqName3 = KotlinBuiltIns.FQ_NAMES.mutableSet;
        Intrinsics.checkNotNullExpressionValue(fqName3, "FQ_NAMES.mutableSet");
        fakePureImplementationsProvider.implementedWith(fqName3, fakePureImplementationsProvider.fqNameListOf("java.util.HashSet", "java.util.TreeSet", "java.util.LinkedHashSet"));
        FqName fqName4 = KotlinBuiltIns.FQ_NAMES.mutableMap;
        Intrinsics.checkNotNullExpressionValue(fqName4, "FQ_NAMES.mutableMap");
        fakePureImplementationsProvider.implementedWith(fqName4, fakePureImplementationsProvider.fqNameListOf("java.util.HashMap", "java.util.TreeMap", "java.util.LinkedHashMap", "java.util.concurrent.ConcurrentHashMap", "java.util.concurrent.ConcurrentSkipListMap"));
        fakePureImplementationsProvider.implementedWith(new FqName("java.util.function.Function"), fakePureImplementationsProvider.fqNameListOf("java.util.function.UnaryOperator"));
        fakePureImplementationsProvider.implementedWith(new FqName("java.util.function.BiFunction"), fakePureImplementationsProvider.fqNameListOf("java.util.function.BinaryOperator"));
    }
}

