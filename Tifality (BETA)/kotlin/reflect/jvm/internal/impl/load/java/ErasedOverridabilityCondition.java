/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.ErasedOverridabilityCondition;
import kotlin.reflect.jvm.internal.impl.load.java.ErasedOverridabilityCondition$WhenMappings;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawSubstitution;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl;
import kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ErasedOverridabilityCondition
implements ExternalOverridabilityCondition {
    @Override
    @NotNull
    public ExternalOverridabilityCondition.Result isOverridable(@NotNull CallableDescriptor superDescriptor, @NotNull CallableDescriptor subDescriptor, @Nullable ClassDescriptor subClassDescriptor) {
        ExternalOverridabilityCondition.Result result2;
        boolean bl;
        block12: {
            Sequence<KotlinType> signatureTypes2;
            OverridingUtil.OverrideCompatibilityInfo.Result basicOverridability;
            block14: {
                block13: {
                    Intrinsics.checkNotNullParameter(superDescriptor, "superDescriptor");
                    Intrinsics.checkNotNullParameter(subDescriptor, "subDescriptor");
                    if (!(subDescriptor instanceof JavaMethodDescriptor)) break block13;
                    List<TypeParameterDescriptor> list = ((JavaMethodDescriptor)subDescriptor).getTypeParameters();
                    Intrinsics.checkNotNullExpressionValue(list, "subDescriptor.typeParameters");
                    Collection collection = list;
                    boolean bl2 = false;
                    if (!(!collection.isEmpty())) break block14;
                }
                return ExternalOverridabilityCondition.Result.UNKNOWN;
            }
            OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfo = OverridingUtil.getBasicOverridabilityProblem(superDescriptor, subDescriptor);
            OverridingUtil.OverrideCompatibilityInfo.Result result3 = basicOverridability = overrideCompatibilityInfo != null ? overrideCompatibilityInfo.getResult() : null;
            if (basicOverridability != null) {
                return ExternalOverridabilityCondition.Result.UNKNOWN;
            }
            List<ValueParameterDescriptor> list = ((JavaMethodDescriptor)subDescriptor).getValueParameters();
            Intrinsics.checkNotNullExpressionValue(list, "subDescriptor.valueParameters");
            Sequence sequence = SequencesKt.map(CollectionsKt.asSequence((Iterable)list), isOverridable.signatureTypes.1.INSTANCE);
            KotlinType kotlinType = ((JavaMethodDescriptor)subDescriptor).getReturnType();
            Intrinsics.checkNotNull(kotlinType);
            ReceiverParameterDescriptor receiverParameterDescriptor = ((JavaMethodDescriptor)subDescriptor).getExtensionReceiverParameter();
            Sequence<KotlinType> $this$any$iv = signatureTypes2 = SequencesKt.plus(SequencesKt.plus(sequence, kotlinType), (Iterable)CollectionsKt.listOfNotNull(receiverParameterDescriptor != null ? receiverParameterDescriptor.getType() : null));
            boolean $i$f$any = false;
            Iterator<KotlinType> iterator2 = $this$any$iv.iterator();
            while (iterator2.hasNext()) {
                KotlinType element$iv;
                KotlinType it = element$iv = iterator2.next();
                boolean bl3 = false;
                Collection collection = it.getArguments();
                boolean bl4 = false;
                if (!(!collection.isEmpty() && !(it.unwrap() instanceof RawTypeImpl))) continue;
                bl = true;
                break block12;
            }
            bl = false;
        }
        if (bl) {
            return ExternalOverridabilityCondition.Result.UNKNOWN;
        }
        CallableDescriptor callableDescriptor = (CallableDescriptor)superDescriptor.substitute(RawSubstitution.INSTANCE.buildSubstitutor());
        if (callableDescriptor == null) {
            return ExternalOverridabilityCondition.Result.UNKNOWN;
        }
        CallableDescriptor erasedSuper = callableDescriptor;
        if (erasedSuper instanceof SimpleFunctionDescriptor) {
            List<TypeParameterDescriptor> list = ((SimpleFunctionDescriptor)erasedSuper).getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(list, "erasedSuper.typeParameters");
            Collection $i$f$any = list;
            boolean bl5 = false;
            if (!$i$f$any.isEmpty()) {
                SimpleFunctionDescriptor simpleFunctionDescriptor = ((SimpleFunctionDescriptor)erasedSuper).newCopyBuilder().setTypeParameters(CollectionsKt.<TypeParameterDescriptor>emptyList()).build();
                Intrinsics.checkNotNull(simpleFunctionDescriptor);
                erasedSuper = simpleFunctionDescriptor;
            }
        }
        OverridingUtil.OverrideCompatibilityInfo overrideCompatibilityInfo = OverridingUtil.DEFAULT.isOverridableByWithoutExternalConditions(erasedSuper, subDescriptor, false);
        Intrinsics.checkNotNullExpressionValue(overrideCompatibilityInfo, "OverridingUtil.DEFAULT.i\u2026er, subDescriptor, false)");
        OverridingUtil.OverrideCompatibilityInfo.Result result4 = overrideCompatibilityInfo.getResult();
        Intrinsics.checkNotNullExpressionValue((Object)result4, "OverridingUtil.DEFAULT.i\u2026Descriptor, false).result");
        OverridingUtil.OverrideCompatibilityInfo.Result overridabilityResult = result4;
        switch (ErasedOverridabilityCondition$WhenMappings.$EnumSwitchMapping$0[overridabilityResult.ordinal()]) {
            case 1: {
                result2 = ExternalOverridabilityCondition.Result.OVERRIDABLE;
                break;
            }
            default: {
                result2 = ExternalOverridabilityCondition.Result.UNKNOWN;
            }
        }
        return result2;
    }

    @Override
    @NotNull
    public ExternalOverridabilityCondition.Contract getContract() {
        return ExternalOverridabilityCondition.Contract.SUCCESS_ONLY;
    }
}

