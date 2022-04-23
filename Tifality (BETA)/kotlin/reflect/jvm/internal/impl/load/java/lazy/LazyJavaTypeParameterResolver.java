/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.TypeParameterResolver;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaTypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameterListOwner;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LazyJavaTypeParameterResolver
implements TypeParameterResolver {
    private final Map<JavaTypeParameter, Integer> typeParameters;
    private final MemoizedFunctionToNullable<JavaTypeParameter, LazyJavaTypeParameterDescriptor> resolve;
    private final LazyJavaResolverContext c;
    private final DeclarationDescriptor containingDeclaration;
    private final int typeParametersIndexOffset;

    @Override
    @Nullable
    public TypeParameterDescriptor resolveTypeParameter(@NotNull JavaTypeParameter javaTypeParameter) {
        Intrinsics.checkNotNullParameter(javaTypeParameter, "javaTypeParameter");
        LazyJavaTypeParameterDescriptor lazyJavaTypeParameterDescriptor = (LazyJavaTypeParameterDescriptor)this.resolve.invoke(javaTypeParameter);
        return lazyJavaTypeParameterDescriptor != null ? (TypeParameterDescriptor)lazyJavaTypeParameterDescriptor : this.c.getTypeParameterResolver().resolveTypeParameter(javaTypeParameter);
    }

    public LazyJavaTypeParameterResolver(@NotNull LazyJavaResolverContext c, @NotNull DeclarationDescriptor containingDeclaration, @NotNull JavaTypeParameterListOwner typeParameterOwner, int typeParametersIndexOffset) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(typeParameterOwner, "typeParameterOwner");
        this.c = c;
        this.containingDeclaration = containingDeclaration;
        this.typeParametersIndexOffset = typeParametersIndexOffset;
        this.typeParameters = CollectionsKt.mapToIndex((Iterable)typeParameterOwner.getTypeParameters());
        this.resolve = this.c.getStorageManager().createMemoizedFunctionWithNullableValues((Function1)new Function1<JavaTypeParameter, LazyJavaTypeParameterDescriptor>(this){
            final /* synthetic */ LazyJavaTypeParameterResolver this$0;

            @Nullable
            public final LazyJavaTypeParameterDescriptor invoke(@NotNull JavaTypeParameter typeParameter) {
                LazyJavaTypeParameterDescriptor lazyJavaTypeParameterDescriptor;
                Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
                Integer n = (Integer)LazyJavaTypeParameterResolver.access$getTypeParameters$p(this.this$0).get(typeParameter);
                if (n != null) {
                    Integer n2 = n;
                    boolean bl = false;
                    boolean bl2 = false;
                    int index = ((Number)n2).intValue();
                    boolean bl3 = false;
                    lazyJavaTypeParameterDescriptor = new LazyJavaTypeParameterDescriptor(ContextKt.child(LazyJavaTypeParameterResolver.access$getC$p(this.this$0), this.this$0), typeParameter, LazyJavaTypeParameterResolver.access$getTypeParametersIndexOffset$p(this.this$0) + index, LazyJavaTypeParameterResolver.access$getContainingDeclaration$p(this.this$0));
                } else {
                    lazyJavaTypeParameterDescriptor = null;
                }
                return lazyJavaTypeParameterDescriptor;
            }
            {
                this.this$0 = lazyJavaTypeParameterResolver;
                super(1);
            }
        });
    }

    public static final /* synthetic */ Map access$getTypeParameters$p(LazyJavaTypeParameterResolver $this) {
        return $this.typeParameters;
    }

    public static final /* synthetic */ LazyJavaResolverContext access$getC$p(LazyJavaTypeParameterResolver $this) {
        return $this.c;
    }

    public static final /* synthetic */ int access$getTypeParametersIndexOffset$p(LazyJavaTypeParameterResolver $this) {
        return $this.typeParametersIndexOffset;
    }

    public static final /* synthetic */ DeclarationDescriptor access$getContainingDeclaration$p(LazyJavaTypeParameterResolver $this) {
        return $this.containingDeclaration;
    }
}

