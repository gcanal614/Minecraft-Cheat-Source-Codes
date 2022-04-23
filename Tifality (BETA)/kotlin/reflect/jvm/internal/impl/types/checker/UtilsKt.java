/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.SubtypePathNode;
import kotlin.reflect.jvm.internal.impl.types.checker.TypeCheckingProcedureCallbacks;
import kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class UtilsKt {
    @Nullable
    public static final KotlinType findCorrespondingSupertype(@NotNull KotlinType subtype, @NotNull KotlinType supertype, @NotNull TypeCheckingProcedureCallbacks typeCheckingProcedureCallbacks) {
        Intrinsics.checkNotNullParameter(subtype, "subtype");
        Intrinsics.checkNotNullParameter(supertype, "supertype");
        Intrinsics.checkNotNullParameter(typeCheckingProcedureCallbacks, "typeCheckingProcedureCallbacks");
        ArrayDeque<SubtypePathNode> queue = new ArrayDeque<SubtypePathNode>();
        queue.add(new SubtypePathNode(subtype, null));
        TypeConstructor supertypeConstructor = supertype.getConstructor();
        while (!queue.isEmpty()) {
            SubtypePathNode lastPathNode = (SubtypePathNode)queue.poll();
            KotlinType currentSubtype = lastPathNode.getType();
            TypeConstructor constructor = currentSubtype.getConstructor();
            if (typeCheckingProcedureCallbacks.assertEqualTypeConstructors(constructor, supertypeConstructor)) {
                KotlinType substituted = currentSubtype;
                boolean isAnyMarkedNullable = currentSubtype.isMarkedNullable();
                for (SubtypePathNode currentPathNode = lastPathNode.getPrevious(); currentPathNode != null; currentPathNode = currentPathNode.getPrevious()) {
                    KotlinType kotlinType;
                    boolean bl;
                    KotlinType currentType;
                    block10: {
                        currentType = currentPathNode.getType();
                        Iterable $this$any$iv = currentType.getArguments();
                        boolean $i$f$any = false;
                        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                            bl = false;
                        } else {
                            for (Object element$iv : $this$any$iv) {
                                TypeProjection it = (TypeProjection)element$iv;
                                boolean bl2 = false;
                                if (!(it.getProjectionKind() != Variance.INVARIANT)) continue;
                                bl = true;
                                break block10;
                            }
                            bl = false;
                        }
                    }
                    if (bl) {
                        KotlinType kotlinType2 = CapturedTypeConstructorKt.wrapWithCapturingSubstitution$default(TypeConstructorSubstitution.Companion.create(currentType), false, 1, null).buildSubstitutor().safeSubstitute(substituted, Variance.INVARIANT);
                        Intrinsics.checkNotNullExpressionValue(kotlinType2, "TypeConstructorSubstitut\u2026uted, Variance.INVARIANT)");
                        kotlinType = UtilsKt.approximate(kotlinType2);
                    } else {
                        KotlinType kotlinType3 = TypeConstructorSubstitution.Companion.create(currentType).buildSubstitutor().safeSubstitute(substituted, Variance.INVARIANT);
                        kotlinType = kotlinType3;
                        Intrinsics.checkNotNullExpressionValue(kotlinType3, "TypeConstructorSubstitut\u2026uted, Variance.INVARIANT)");
                    }
                    substituted = kotlinType;
                    isAnyMarkedNullable = isAnyMarkedNullable || currentType.isMarkedNullable();
                }
                TypeConstructor substitutedConstructor = substituted.getConstructor();
                if (!typeCheckingProcedureCallbacks.assertEqualTypeConstructors(substitutedConstructor, supertypeConstructor)) {
                    throw (Throwable)((Object)new AssertionError((Object)("Type constructors should be equals!\n" + "substitutedSuperType: " + UtilsKt.debugInfo(substitutedConstructor) + ", \n\n" + "supertype: " + UtilsKt.debugInfo(supertypeConstructor) + " \n" + typeCheckingProcedureCallbacks.assertEqualTypeConstructors(substitutedConstructor, supertypeConstructor))));
                }
                return TypeUtils.makeNullableAsSpecified(substituted, isAnyMarkedNullable);
            }
            Iterator<KotlinType> iterator2 = constructor.getSupertypes().iterator();
            while (iterator2.hasNext()) {
                KotlinType immediateSupertype;
                KotlinType kotlinType = immediateSupertype = iterator2.next();
                Intrinsics.checkNotNullExpressionValue(kotlinType, "immediateSupertype");
                queue.add(new SubtypePathNode(kotlinType, lastPathNode));
            }
        }
        return null;
    }

    private static final KotlinType approximate(KotlinType $this$approximate) {
        return CapturedTypeApproximationKt.approximateCapturedTypes($this$approximate).getUpper();
    }

    private static final String debugInfo(TypeConstructor $this$debugInfo) {
        boolean bl = false;
        boolean bl2 = false;
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl3 = false;
        boolean bl4 = false;
        StringBuilder $this$buildString = stringBuilder;
        boolean bl5 = false;
        Function1<String, StringBuilder> $fun$unaryPlus$1 = new Function1<String, StringBuilder>($this$buildString){
            final /* synthetic */ StringBuilder $this_buildString;

            @NotNull
            public final StringBuilder invoke(@NotNull String $this$unaryPlus) {
                Intrinsics.checkNotNullParameter($this$unaryPlus, "$this$unaryPlus");
                StringBuilder stringBuilder = this.$this_buildString;
                boolean bl = false;
                StringBuilder stringBuilder2 = stringBuilder.append($this$unaryPlus);
                Intrinsics.checkNotNullExpressionValue(stringBuilder2, "append(value)");
                return StringsKt.appendln(stringBuilder2);
            }
            {
                this.$this_buildString = stringBuilder;
                super(1);
            }
        };
        $fun$unaryPlus$1.invoke("type: " + $this$debugInfo);
        $fun$unaryPlus$1.invoke("hashCode: " + $this$debugInfo.hashCode());
        $fun$unaryPlus$1.invoke("javaClass: " + $this$debugInfo.getClass().getCanonicalName());
        for (DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor)$this$debugInfo.getDeclarationDescriptor(); declarationDescriptor != null; declarationDescriptor = declarationDescriptor.getContainingDeclaration()) {
            $fun$unaryPlus$1.invoke("fqName: " + DescriptorRenderer.FQ_NAMES_IN_TYPES.render(declarationDescriptor));
            $fun$unaryPlus$1.invoke("javaClass: " + declarationDescriptor.getClass().getCanonicalName());
        }
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }
}

