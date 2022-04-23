/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedFunctionEnhancementInfo;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnhancementInfo;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.annotations.NotNull;

final class SignatureEnhancementBuilder {
    private final Map<String, PredefinedFunctionEnhancementInfo> signatures;

    @NotNull
    public final Map<String, PredefinedFunctionEnhancementInfo> build() {
        return this.signatures;
    }

    public SignatureEnhancementBuilder() {
        boolean bl = false;
        this.signatures = new LinkedHashMap();
    }

    public final class ClassEnhancementBuilder {
        @NotNull
        private final String className;

        public final void function(@NotNull String name, @NotNull Function1<? super FunctionEnhancementBuilder, Unit> block) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(block, "block");
            Map map2 = SignatureEnhancementBuilder.this.signatures;
            Object object = new FunctionEnhancementBuilder(name);
            boolean bl = false;
            boolean bl2 = false;
            block.invoke((FunctionEnhancementBuilder)object);
            object = ((FunctionEnhancementBuilder)object).build();
            bl = false;
            map2.put(((Pair)object).getFirst(), ((Pair)object).getSecond());
        }

        @NotNull
        public final String getClassName() {
            return this.className;
        }

        public ClassEnhancementBuilder(String className) {
            Intrinsics.checkNotNullParameter(className, "className");
            this.className = className;
        }

        public final class FunctionEnhancementBuilder {
            private final List<Pair<String, TypeEnhancementInfo>> parameters;
            private Pair<String, TypeEnhancementInfo> returnType;
            @NotNull
            private final String functionName;

            /*
             * WARNING - void declaration
             */
            public final void parameter(@NotNull String type2, JavaTypeQualifiers ... qualifiers) {
                TypeEnhancementInfo typeEnhancementInfo;
                Intrinsics.checkNotNullParameter(type2, "type");
                Intrinsics.checkNotNullParameter(qualifiers, "qualifiers");
                Collection collection = this.parameters;
                String string = type2;
                Object object = qualifiers;
                boolean bl = false;
                if (((JavaTypeQualifiers[])object).length == 0) {
                    typeEnhancementInfo = null;
                } else {
                    Map map2;
                    void $this$associateByTo$iv$iv;
                    void $this$associateBy$iv;
                    object = ArraysKt.withIndex(qualifiers);
                    String string2 = string;
                    boolean $i$f$associateBy = false;
                    int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associateBy$iv, 10)), 16);
                    void var7_8 = $this$associateBy$iv;
                    Map destination$iv$iv = new LinkedHashMap(capacity$iv);
                    boolean $i$f$associateByTo = false;
                    for (Object element$iv$iv : $this$associateByTo$iv$iv) {
                        IndexedValue it;
                        IndexedValue indexedValue = (IndexedValue)element$iv$iv;
                        map2 = destination$iv$iv;
                        boolean bl2 = false;
                        Integer n = it.getIndex();
                        it = (IndexedValue)element$iv$iv;
                        boolean bl3 = false;
                        JavaTypeQualifiers javaTypeQualifiers = (JavaTypeQualifiers)it.getValue();
                        map2.put(n, javaTypeQualifiers);
                    }
                    map2 = destination$iv$iv;
                    string = string2;
                    Map map3 = map2;
                    typeEnhancementInfo = new TypeEnhancementInfo(map3);
                }
                object = TuplesKt.to(string, typeEnhancementInfo);
                bl = false;
                collection.add(object);
            }

            /*
             * WARNING - void declaration
             */
            public final void returns(@NotNull String type2, JavaTypeQualifiers ... qualifiers) {
                Map map2;
                void $this$associateByTo$iv$iv;
                void $this$associateBy$iv;
                Intrinsics.checkNotNullParameter(type2, "type");
                Intrinsics.checkNotNullParameter(qualifiers, "qualifiers");
                Iterable<IndexedValue<JavaTypeQualifiers>> iterable = ArraysKt.withIndex(qualifiers);
                String string = type2;
                FunctionEnhancementBuilder functionEnhancementBuilder = this;
                boolean $i$f$associateBy = false;
                int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associateBy$iv, 10)), 16);
                void var6_8 = $this$associateBy$iv;
                Map destination$iv$iv = new LinkedHashMap(capacity$iv);
                boolean $i$f$associateByTo = false;
                for (Object element$iv$iv : $this$associateByTo$iv$iv) {
                    IndexedValue it;
                    IndexedValue indexedValue = (IndexedValue)element$iv$iv;
                    map2 = destination$iv$iv;
                    boolean bl = false;
                    Integer n = it.getIndex();
                    it = (IndexedValue)element$iv$iv;
                    boolean bl2 = false;
                    JavaTypeQualifiers javaTypeQualifiers = (JavaTypeQualifiers)it.getValue();
                    map2.put(n, javaTypeQualifiers);
                }
                Map map3 = map2 = destination$iv$iv;
                functionEnhancementBuilder.returnType = TuplesKt.to(string, new TypeEnhancementInfo(map3));
            }

            public final void returns(@NotNull JvmPrimitiveType type2) {
                Intrinsics.checkNotNullParameter((Object)type2, "type");
                String string = type2.getDesc();
                Intrinsics.checkNotNullExpressionValue(string, "type.desc");
                this.returnType = TuplesKt.to(string, null);
            }

            @NotNull
            public final Pair<String, PredefinedFunctionEnhancementInfo> build() {
                Object object;
                Pair it;
                Collection<Object> collection;
                Iterable $this$mapTo$iv$iv;
                Iterable $this$map$iv;
                SignatureBuildingComponents signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
                boolean bl = false;
                boolean bl2 = false;
                SignatureBuildingComponents $this$with = signatureBuildingComponents;
                boolean bl3 = false;
                Iterable iterable = this.parameters;
                Object object2 = this.functionName;
                SignatureBuildingComponents signatureBuildingComponents2 = $this$with;
                String string = ClassEnhancementBuilder.this.getClassName();
                Object object3 = $this$with;
                boolean $i$f$map = false;
                void var12_12 = $this$map$iv;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    Pair pair = (Pair)item$iv$iv;
                    collection = destination$iv$iv;
                    boolean bl4 = false;
                    object = (String)it.getFirst();
                    collection.add(object);
                }
                collection = (List)destination$iv$iv;
                String string2 = ((SignatureBuildingComponents)object3).signature(string, signatureBuildingComponents2.jvmDescriptor((String)object2, (List<String>)collection, this.returnType.getFirst()));
                $this$map$iv = this.parameters;
                object2 = this.returnType.getSecond();
                object3 = string2;
                $i$f$map = false;
                $this$mapTo$iv$iv = $this$map$iv;
                destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                $i$f$mapTo = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    it = (Pair)item$iv$iv;
                    collection = destination$iv$iv;
                    boolean bl5 = false;
                    object = (TypeEnhancementInfo)it.getSecond();
                    collection.add(object);
                }
                Collection<Object> collection2 = collection = (List)destination$iv$iv;
                Object object4 = object2;
                return TuplesKt.to(object3, new PredefinedFunctionEnhancementInfo((TypeEnhancementInfo)object4, (List<TypeEnhancementInfo>)collection2));
            }

            public FunctionEnhancementBuilder(String functionName) {
                Intrinsics.checkNotNullParameter(functionName, "functionName");
                this.functionName = functionName;
                boolean bl = false;
                this.parameters = new ArrayList();
                this.returnType = TuplesKt.to("V", null);
            }
        }
    }
}

