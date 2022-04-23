/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.IndexedParametersSubstitution;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class TypeConstructorSubstitution
extends TypeSubstitution {
    public static final Companion Companion = new Companion(null);

    @Override
    @Nullable
    public TypeProjection get(@NotNull KotlinType key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.get(key.getConstructor());
    }

    @Nullable
    public abstract TypeProjection get(@NotNull TypeConstructor var1);

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final TypeConstructorSubstitution createByConstructorsMap(@NotNull Map<TypeConstructor, ? extends TypeProjection> map2) {
        return kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution$Companion.createByConstructorsMap$default(Companion, map2, false, 2, null);
    }

    @JvmStatic
    @NotNull
    public static final TypeSubstitution create(@NotNull TypeConstructor typeConstructor2, @NotNull List<? extends TypeProjection> arguments2) {
        return Companion.create(typeConstructor2, arguments2);
    }

    public static final class Companion {
        @JvmStatic
        @JvmOverloads
        @NotNull
        public final TypeConstructorSubstitution createByConstructorsMap(@NotNull Map<TypeConstructor, ? extends TypeProjection> map2, boolean approximateCapturedTypes2) {
            Intrinsics.checkNotNullParameter(map2, "map");
            return new TypeConstructorSubstitution(map2, approximateCapturedTypes2){
                final /* synthetic */ Map $map;
                final /* synthetic */ boolean $approximateCapturedTypes;

                @Nullable
                public TypeProjection get(@NotNull TypeConstructor key) {
                    Intrinsics.checkNotNullParameter(key, "key");
                    return (TypeProjection)this.$map.get(key);
                }

                public boolean isEmpty() {
                    return this.$map.isEmpty();
                }

                public boolean approximateCapturedTypes() {
                    return this.$approximateCapturedTypes;
                }
                {
                    this.$map = $captured_local_variable$0;
                    this.$approximateCapturedTypes = $captured_local_variable$1;
                }
            };
        }

        public static /* synthetic */ TypeConstructorSubstitution createByConstructorsMap$default(Companion companion, Map map2, boolean bl, int n, Object object) {
            if ((n & 2) != 0) {
                bl = false;
            }
            return companion.createByConstructorsMap(map2, bl);
        }

        @JvmStatic
        @NotNull
        public final TypeSubstitution create(@NotNull KotlinType kotlinType) {
            Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
            return this.create(kotlinType.getConstructor(), kotlinType.getArguments());
        }

        /*
         * WARNING - void declaration
         */
        @JvmStatic
        @NotNull
        public final TypeSubstitution create(@NotNull TypeConstructor typeConstructor2, @NotNull List<? extends TypeProjection> arguments2) {
            Intrinsics.checkNotNullParameter(typeConstructor2, "typeConstructor");
            Intrinsics.checkNotNullParameter(arguments2, "arguments");
            List<TypeParameterDescriptor> list = typeConstructor2.getParameters();
            Intrinsics.checkNotNullExpressionValue(list, "typeConstructor.parameters");
            List<TypeParameterDescriptor> parameters2 = list;
            TypeParameterDescriptor typeParameterDescriptor = CollectionsKt.lastOrNull(parameters2);
            if (typeParameterDescriptor != null ? typeParameterDescriptor.isCapturedFromOuterDeclaration() : false) {
                Collection<TypeConstructor> collection;
                void $this$mapTo$iv$iv;
                void $this$map$iv;
                List<TypeParameterDescriptor> list2 = typeConstructor2.getParameters();
                Intrinsics.checkNotNullExpressionValue(list2, "typeConstructor.parameters");
                Iterable iterable = list2;
                Companion companion = this;
                boolean $i$f$map = false;
                void var6_7 = $this$map$iv;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    void it;
                    TypeParameterDescriptor typeParameterDescriptor2 = (TypeParameterDescriptor)item$iv$iv;
                    collection = destination$iv$iv;
                    boolean bl = false;
                    void v3 = it;
                    Intrinsics.checkNotNullExpressionValue(v3, "it");
                    TypeConstructor typeConstructor3 = v3.getTypeConstructor();
                    collection.add(typeConstructor3);
                }
                collection = (List)destination$iv$iv;
                return kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution$Companion.createByConstructorsMap$default(companion, MapsKt.toMap(CollectionsKt.zip((Iterable)collection, (Iterable)arguments2)), false, 2, null);
            }
            return new IndexedParametersSubstitution(parameters2, arguments2);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

