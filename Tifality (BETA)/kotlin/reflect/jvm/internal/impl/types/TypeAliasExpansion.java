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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeAliasExpansion {
    @Nullable
    private final TypeAliasExpansion parent;
    @NotNull
    private final TypeAliasDescriptor descriptor;
    @NotNull
    private final List<TypeProjection> arguments;
    @NotNull
    private final Map<TypeParameterDescriptor, TypeProjection> mapping;
    public static final Companion Companion = new Companion(null);

    @Nullable
    public final TypeProjection getReplacement(@NotNull TypeConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        ClassifierDescriptor descriptor2 = constructor.getDeclarationDescriptor();
        return descriptor2 instanceof TypeParameterDescriptor ? this.mapping.get(descriptor2) : null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isRecursion(@NotNull TypeAliasDescriptor descriptor2) {
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        if (Intrinsics.areEqual(this.descriptor, descriptor2)) return true;
        TypeAliasExpansion typeAliasExpansion = this.parent;
        if (typeAliasExpansion == null) return false;
        boolean bl = typeAliasExpansion.isRecursion(descriptor2);
        if (!bl) return false;
        return true;
    }

    @NotNull
    public final TypeAliasDescriptor getDescriptor() {
        return this.descriptor;
    }

    @NotNull
    public final List<TypeProjection> getArguments() {
        return this.arguments;
    }

    private TypeAliasExpansion(TypeAliasExpansion parent, TypeAliasDescriptor descriptor2, List<? extends TypeProjection> arguments2, Map<TypeParameterDescriptor, ? extends TypeProjection> mapping) {
        this.parent = parent;
        this.descriptor = descriptor2;
        this.arguments = arguments2;
        this.mapping = mapping;
    }

    public /* synthetic */ TypeAliasExpansion(TypeAliasExpansion parent, TypeAliasDescriptor descriptor2, List arguments2, Map mapping, DefaultConstructorMarker $constructor_marker) {
        this(parent, descriptor2, arguments2, mapping);
    }

    public static final class Companion {
        /*
         * WARNING - void declaration
         */
        @NotNull
        public final TypeAliasExpansion create(@Nullable TypeAliasExpansion parent, @NotNull TypeAliasDescriptor typeAliasDescriptor, @NotNull List<? extends TypeProjection> arguments2) {
            void $this$mapTo$iv$iv;
            Intrinsics.checkNotNullParameter(typeAliasDescriptor, "typeAliasDescriptor");
            Intrinsics.checkNotNullParameter(arguments2, "arguments");
            TypeConstructor typeConstructor2 = typeAliasDescriptor.getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor2, "typeAliasDescriptor.typeConstructor");
            List<TypeParameterDescriptor> list = typeConstructor2.getParameters();
            Intrinsics.checkNotNullExpressionValue(list, "typeAliasDescriptor.typeConstructor.parameters");
            Iterable $this$map$iv = list;
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                void v2 = it;
                Intrinsics.checkNotNullExpressionValue(v2, "it");
                TypeParameterDescriptor typeParameterDescriptor2 = v2.getOriginal();
                collection.add(typeParameterDescriptor2);
            }
            List typeParameters2 = (List)destination$iv$iv;
            Map mappedArguments = MapsKt.toMap(CollectionsKt.zip((Iterable)typeParameters2, (Iterable)arguments2));
            return new TypeAliasExpansion(parent, typeAliasDescriptor, arguments2, mappedArguments, null);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

