/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawSubstitution;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.RawType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;

public final class RawTypeImpl
extends FlexibleType
implements RawType {
    @Override
    @NotNull
    public SimpleType getDelegate() {
        return this.getLowerBound();
    }

    @Override
    @NotNull
    public MemberScope getMemberScope() {
        ClassifierDescriptor classifierDescriptor = this.getConstructor().getDeclarationDescriptor();
        if (!(classifierDescriptor instanceof ClassDescriptor)) {
            classifierDescriptor = null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor)classifierDescriptor;
        if (classDescriptor == null) {
            String string = "Incorrect classifier: " + this.getConstructor().getDeclarationDescriptor();
            boolean bl = false;
            throw (Throwable)new IllegalStateException(string.toString());
        }
        ClassDescriptor classDescriptor2 = classDescriptor;
        MemberScope memberScope2 = classDescriptor2.getMemberScope(RawSubstitution.INSTANCE);
        Intrinsics.checkNotNullExpressionValue(memberScope2, "classDescriptor.getMemberScope(RawSubstitution)");
        return memberScope2;
    }

    @Override
    @NotNull
    public RawTypeImpl replaceAnnotations(@NotNull Annotations newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        return new RawTypeImpl(this.getLowerBound().replaceAnnotations(newAnnotations), this.getUpperBound().replaceAnnotations(newAnnotations));
    }

    @Override
    @NotNull
    public RawTypeImpl makeNullableAsSpecified(boolean newNullability) {
        return new RawTypeImpl(this.getLowerBound().makeNullableAsSpecified(newNullability), this.getUpperBound().makeNullableAsSpecified(newNullability));
    }

    @Override
    @NotNull
    public String render(@NotNull DescriptorRenderer renderer, @NotNull DescriptorRendererOptions options) {
        boolean bl;
        String newArgs2;
        String upperRendered;
        String lowerRendered;
        render.3 $fun$replaceArgs$3;
        block6: {
            Intrinsics.checkNotNullParameter(renderer, "renderer");
            Intrinsics.checkNotNullParameter(options, "options");
            render.1 $fun$onlyOutDiffers$1 = render.1.INSTANCE;
            Function1<KotlinType, List<? extends String>> $fun$renderArguments$2 = new Function1<KotlinType, List<? extends String>>(renderer){
                final /* synthetic */ DescriptorRenderer $renderer;

                /*
                 * WARNING - void declaration
                 */
                @NotNull
                public final List<String> invoke(@NotNull KotlinType type2) {
                    void $this$mapTo$iv$iv;
                    Intrinsics.checkNotNullParameter(type2, "type");
                    Iterable $this$map$iv = type2.getArguments();
                    boolean $i$f$map = false;
                    Iterable iterable = $this$map$iv;
                    Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        void it;
                        TypeProjection typeProjection = (TypeProjection)item$iv$iv;
                        Collection collection = destination$iv$iv;
                        boolean bl = false;
                        String string = this.$renderer.renderTypeProjection((TypeProjection)it);
                        collection.add(string);
                    }
                    return (List)destination$iv$iv;
                }
                {
                    this.$renderer = descriptorRenderer2;
                    super(1);
                }
            };
            $fun$replaceArgs$3 = render.3.INSTANCE;
            lowerRendered = renderer.renderType(this.getLowerBound());
            upperRendered = renderer.renderType(this.getUpperBound());
            if (options.getDebugMode()) {
                return "raw (" + lowerRendered + ".." + upperRendered + ')';
            }
            if (this.getUpperBound().getArguments().isEmpty()) {
                return renderer.renderFlexibleType(lowerRendered, upperRendered, TypeUtilsKt.getBuiltIns(this));
            }
            List<String> lowerArgs = $fun$renderArguments$2.invoke((KotlinType)this.getLowerBound());
            List<String> upperArgs = $fun$renderArguments$2.invoke((KotlinType)this.getUpperBound());
            newArgs2 = CollectionsKt.joinToString$default(lowerArgs, ", ", null, null, 0, null, render.newArgs.1.INSTANCE, 30, null);
            Iterable $this$all$iv = CollectionsKt.zip((Iterable)lowerArgs, (Iterable)upperArgs);
            boolean $i$f$all = false;
            if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
                bl = true;
            } else {
                for (Object element$iv : $this$all$iv) {
                    Pair it = (Pair)element$iv;
                    boolean bl2 = false;
                    if (render.1.INSTANCE.invoke((String)it.getFirst(), (String)it.getSecond())) continue;
                    bl = false;
                    break block6;
                }
                bl = true;
            }
        }
        String newUpper = bl ? $fun$replaceArgs$3.invoke(upperRendered, newArgs2) : upperRendered;
        String newLower = $fun$replaceArgs$3.invoke(lowerRendered, newArgs2);
        if (Intrinsics.areEqual(newLower, newUpper)) {
            return newLower;
        }
        return renderer.renderFlexibleType(newLower, newUpper, TypeUtilsKt.getBuiltIns(this));
    }

    @Override
    @NotNull
    public FlexibleType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        KotlinType kotlinType = kotlinTypeRefiner.refineType(this.getLowerBound());
        if (kotlinType == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        }
        KotlinType kotlinType2 = kotlinTypeRefiner.refineType(this.getUpperBound());
        if (kotlinType2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        }
        return new RawTypeImpl((SimpleType)kotlinType, (SimpleType)kotlinType2, true);
    }

    private RawTypeImpl(SimpleType lowerBound, SimpleType upperBound, boolean disableAssertion) {
        super(lowerBound, upperBound);
        if (!disableAssertion) {
            boolean bl = KotlinTypeChecker.DEFAULT.isSubtypeOf(lowerBound, upperBound);
            boolean bl2 = false;
            if (_Assertions.ENABLED && !bl) {
                boolean bl3 = false;
                String string = "Lower bound " + lowerBound + " of a flexible type must be a subtype of the upper bound " + upperBound;
                throw (Throwable)((Object)new AssertionError((Object)string));
            }
        }
    }

    public RawTypeImpl(@NotNull SimpleType lowerBound, @NotNull SimpleType upperBound) {
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        this(lowerBound, upperBound, false);
    }
}

