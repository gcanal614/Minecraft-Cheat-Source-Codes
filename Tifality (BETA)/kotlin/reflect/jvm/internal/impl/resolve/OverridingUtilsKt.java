/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;
import org.jetbrains.annotations.NotNull;

public final class OverridingUtilsKt {
    @NotNull
    public static final <H> Collection<H> selectMostSpecificInEachOverridableGroup(@NotNull Collection<? extends H> $this$selectMostSpecificInEachOverridableGroup, @NotNull Function1<? super H, ? extends CallableDescriptor> descriptorByHandle) {
        Intrinsics.checkNotNullParameter($this$selectMostSpecificInEachOverridableGroup, "$this$selectMostSpecificInEachOverridableGroup");
        Intrinsics.checkNotNullParameter(descriptorByHandle, "descriptorByHandle");
        if ($this$selectMostSpecificInEachOverridableGroup.size() <= 1) {
            return $this$selectMostSpecificInEachOverridableGroup;
        }
        LinkedList<? extends H> queue = new LinkedList<H>($this$selectMostSpecificInEachOverridableGroup);
        SmartSet<Object> result2 = SmartSet.Companion.create();
        while (true) {
            H mostSpecific;
            Collection<H> overridableGroup2;
            Collection collection = queue;
            boolean bl = false;
            if (!(!collection.isEmpty())) break;
            Object nextHandle = CollectionsKt.first((List)queue);
            SmartSet conflictedHandles = SmartSet.Companion.create();
            Intrinsics.checkNotNullExpressionValue(OverridingUtil.extractMembersOverridableInBothWays(nextHandle, (Collection)queue, descriptorByHandle, new Function1<H, Unit>(conflictedHandles){
                final /* synthetic */ SmartSet $conflictedHandles;

                public final void invoke(H it) {
                    H h = it;
                    Intrinsics.checkNotNullExpressionValue(h, "it");
                    this.$conflictedHandles.add(h);
                }
                {
                    this.$conflictedHandles = smartSet;
                    super(1);
                }
            }), "OverridingUtil.extractMe\u2026nflictedHandles.add(it) }");
            if (overridableGroup2.size() == 1 && conflictedHandles.isEmpty()) {
                Object t = CollectionsKt.single((Iterable)overridableGroup2);
                Intrinsics.checkNotNullExpressionValue(t, "overridableGroup.single()");
                result2.add(t);
                continue;
            }
            Intrinsics.checkNotNullExpressionValue(OverridingUtil.selectMostSpecificMember(overridableGroup2, descriptorByHandle), "OverridingUtil.selectMos\u2026roup, descriptorByHandle)");
            CallableDescriptor mostSpecificDescriptor = descriptorByHandle.invoke(mostSpecific);
            Iterable $this$filterNotTo$iv = overridableGroup2;
            boolean $i$f$filterNotTo = false;
            Iterator iterator2 = $this$filterNotTo$iv.iterator();
            while (iterator2.hasNext()) {
                Object element$iv;
                Object it = element$iv = iterator2.next();
                boolean bl2 = false;
                Object t = it;
                Intrinsics.checkNotNullExpressionValue(t, "it");
                if (OverridingUtil.isMoreSpecific(mostSpecificDescriptor, descriptorByHandle.invoke(t))) continue;
                ((Collection)conflictedHandles).add(element$iv);
            }
            Collection collection2 = conflictedHandles;
            boolean bl3 = false;
            if (!collection2.isEmpty()) {
                result2.addAll(conflictedHandles);
            }
            result2.add(mostSpecific);
        }
        return result2;
    }
}

