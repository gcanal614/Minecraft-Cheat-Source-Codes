/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.full;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.full.KCallables;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.UtilKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u001a9\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\u00022\u0016\u0010\u0011\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00130\u0012\"\u0004\u0018\u00010\u0013H\u0087@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014\u001a7\u0010\u0015\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00100\u00022\u0014\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u0016H\u0087@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017\u001a\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0019\u001a\u00020\u001aH\u0007\"$\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"$\u0010\u0007\u001a\u0004\u0018\u00010\u0001*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\b\u0010\u0004\u001a\u0004\b\t\u0010\u0006\"(\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b*\u0006\u0012\u0002\b\u00030\u00028FX\u0087\u0004\u00a2\u0006\f\u0012\u0004\b\f\u0010\u0004\u001a\u0004\b\r\u0010\u000e\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001b"}, d2={"extensionReceiverParameter", "Lkotlin/reflect/KParameter;", "Lkotlin/reflect/KCallable;", "getExtensionReceiverParameter$annotations", "(Lkotlin/reflect/KCallable;)V", "getExtensionReceiverParameter", "(Lkotlin/reflect/KCallable;)Lkotlin/reflect/KParameter;", "instanceParameter", "getInstanceParameter$annotations", "getInstanceParameter", "valueParameters", "", "getValueParameters$annotations", "getValueParameters", "(Lkotlin/reflect/KCallable;)Ljava/util/List;", "callSuspend", "R", "args", "", "", "(Lkotlin/reflect/KCallable;[Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "callSuspendBy", "", "(Lkotlin/reflect/KCallable;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findParameterByName", "name", "", "kotlin-reflection"})
@JvmName(name="KCallables")
public final class KCallables {
    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getInstanceParameter$annotations(KCallable kCallable) {
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public static final KParameter getInstanceParameter(@NotNull KCallable<?> $this$instanceParameter) {
        Object object;
        block2: {
            void var3_3;
            Intrinsics.checkNotNullParameter($this$instanceParameter, "$this$instanceParameter");
            Iterable $this$singleOrNull$iv = $this$instanceParameter.getParameters();
            boolean $i$f$singleOrNull = false;
            Object single$iv = null;
            boolean found$iv = false;
            for (Object element$iv : $this$singleOrNull$iv) {
                KParameter it = (KParameter)element$iv;
                boolean bl = false;
                if (!(it.getKind() == KParameter.Kind.INSTANCE)) continue;
                if (found$iv) {
                    object = null;
                    break block2;
                }
                single$iv = element$iv;
                found$iv = true;
            }
            object = !found$iv ? null : var3_3;
        }
        return (KParameter)object;
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getExtensionReceiverParameter$annotations(KCallable kCallable) {
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public static final KParameter getExtensionReceiverParameter(@NotNull KCallable<?> $this$extensionReceiverParameter) {
        Object object;
        block2: {
            void var3_3;
            Intrinsics.checkNotNullParameter($this$extensionReceiverParameter, "$this$extensionReceiverParameter");
            Iterable $this$singleOrNull$iv = $this$extensionReceiverParameter.getParameters();
            boolean $i$f$singleOrNull = false;
            Object single$iv = null;
            boolean found$iv = false;
            for (Object element$iv : $this$singleOrNull$iv) {
                KParameter it = (KParameter)element$iv;
                boolean bl = false;
                if (!(it.getKind() == KParameter.Kind.EXTENSION_RECEIVER)) continue;
                if (found$iv) {
                    object = null;
                    break block2;
                }
                single$iv = element$iv;
                found$iv = true;
            }
            object = !found$iv ? null : var3_3;
        }
        return (KParameter)object;
    }

    @SinceKotlin(version="1.1")
    public static /* synthetic */ void getValueParameters$annotations(KCallable kCallable) {
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final List<KParameter> getValueParameters(@NotNull KCallable<?> $this$valueParameters) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter($this$valueParameters, "$this$valueParameters");
        Iterable $this$filter$iv = $this$valueParameters.getParameters();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            KParameter it = (KParameter)element$iv$iv;
            boolean bl = false;
            if (!(it.getKind() == KParameter.Kind.VALUE)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @SinceKotlin(version="1.1")
    @Nullable
    public static final KParameter findParameterByName(@NotNull KCallable<?> $this$findParameterByName, @NotNull String name) {
        Object v0;
        block2: {
            Intrinsics.checkNotNullParameter($this$findParameterByName, "$this$findParameterByName");
            Intrinsics.checkNotNullParameter(name, "name");
            Iterable $this$singleOrNull$iv = $this$findParameterByName.getParameters();
            boolean $i$f$singleOrNull = false;
            Object single$iv = null;
            boolean found$iv = false;
            for (Object element$iv : $this$singleOrNull$iv) {
                KParameter it = (KParameter)element$iv;
                boolean bl = false;
                if (!Intrinsics.areEqual(it.getName(), name)) continue;
                if (found$iv) {
                    v0 = null;
                    break block2;
                }
                single$iv = element$iv;
                found$iv = true;
            }
            v0 = !found$iv ? null : single$iv;
        }
        return v0;
    }

    /*
     * Unable to fully structure code
     */
    @SinceKotlin(version="1.3")
    @Nullable
    public static final <R> Object callSuspend(@NotNull KCallable<? extends R> $this$callSuspend, @NotNull Object[] args, @NotNull Continuation<? super R> $completion) {
        if (!($completion instanceof callSuspend.1)) ** GOTO lbl-1000
        var7_3 = $completion;
        if ((var7_3.label & -2147483648) != 0) {
            var7_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl($completion){
                /* synthetic */ Object result;
                int label;
                Object L$0;
                Object L$1;

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return KCallables.callSuspend(null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var8_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                if (!$this$callSuspend.isSuspend()) {
                    return $this$callSuspend.call(Arrays.copyOf(args, args.length));
                }
                if (!($this$callSuspend instanceof KFunction)) {
                    throw (Throwable)new IllegalArgumentException("Cannot callSuspend on a property " + $this$callSuspend + ": suspend properties are not supported yet");
                }
                $continuation.L$0 = $this$callSuspend;
                $continuation.L$1 = args;
                $continuation.label = 1;
                it = $continuation;
                $i$a$-suspendCoroutineUninterceptedOrReturn-KCallables$callSuspend$result$1 = false;
                v0 = new SpreadBuilder(2);
                v0.addSpread(args);
                v0.add(it);
                v1 = $this$callSuspend.call(v0.toArray(new Object[v0.size()]));
                if (v1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    DebugProbesKt.probeCoroutineSuspended($continuation);
                }
                v2 = v1;
                if (v1 == var8_5) {
                    return var8_5;
                }
                ** GOTO lbl36
            }
            case 1: {
                args = (Object[])$continuation.L$1;
                $this$callSuspend = (KCallable)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v2 = result = $result;
lbl36:
                // 2 sources

                if (Intrinsics.areEqual($this$callSuspend.getReturnType().getClassifier(), Reflection.getOrCreateKotlinClass(Unit.class)) && !$this$callSuspend.getReturnType().isMarkedNullable()) {
                    return Unit.INSTANCE;
                }
                return result;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }

    /*
     * Unable to fully structure code
     */
    @SinceKotlin(version="1.3")
    @Nullable
    public static final <R> Object callSuspendBy(@NotNull KCallable<? extends R> $this$callSuspendBy, @NotNull Map<KParameter, ? extends Object> args, @NotNull Continuation<? super R> $completion) {
        if (!($completion instanceof callSuspendBy.1)) ** GOTO lbl-1000
        var8_3 = $completion;
        if ((var8_3.label & -2147483648) != 0) {
            var8_3.label -= -2147483648;
        } else lbl-1000:
        // 2 sources

        {
            $continuation = new ContinuationImpl($completion){
                /* synthetic */ Object result;
                int label;
                Object L$0;
                Object L$1;
                Object L$2;

                @Nullable
                public final Object invokeSuspend(@NotNull Object $result) {
                    this.result = $result;
                    this.label |= Integer.MIN_VALUE;
                    return KCallables.callSuspendBy(null, null, this);
                }
            };
        }
        $result = $continuation.result;
        var9_5 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch ($continuation.label) {
            case 0: {
                ResultKt.throwOnFailure($result);
                if (!$this$callSuspendBy.isSuspend()) {
                    return $this$callSuspendBy.callBy(args);
                }
                if (!($this$callSuspendBy instanceof KFunction)) {
                    throw (Throwable)new IllegalArgumentException("Cannot callSuspendBy on a property " + $this$callSuspendBy + ": suspend properties are not supported yet");
                }
                var3_6 = new Ref.ObjectRef<T>();
                v0 = UtilKt.asKCallableImpl($this$callSuspendBy);
                if (v0 == null) {
                    throw (Throwable)new KotlinReflectionInternalError("This callable does not support a default call: " + $this$callSuspendBy);
                }
                var3_6.element = v0;
                $continuation.L$0 = $this$callSuspendBy;
                $continuation.L$1 = args;
                $continuation.L$2 = kCallable;
                $continuation.label = 1;
                it = $continuation;
                $i$a$-suspendCoroutineUninterceptedOrReturn-KCallables$callSuspendBy$result$1 = false;
                v1 = ((KCallableImpl)kCallable.element).callDefaultMethod$kotlin_reflection(args, it);
                if (v1 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    DebugProbesKt.probeCoroutineSuspended($continuation);
                }
                v2 = v1;
                if (v1 == var9_5) {
                    return var9_5;
                }
                ** GOTO lbl40
            }
            case 1: {
                kCallable = (Ref.ObjectRef)$continuation.L$2;
                args = (Map)$continuation.L$1;
                $this$callSuspendBy = (KCallable)$continuation.L$0;
                ResultKt.throwOnFailure($result);
                v2 = result = $result;
lbl40:
                // 2 sources

                if (Intrinsics.areEqual($this$callSuspendBy.getReturnType().getClassifier(), Reflection.getOrCreateKotlinClass(Unit.class)) && !$this$callSuspendBy.getReturnType().isMarkedNullable()) {
                    return Unit.INSTANCE;
                }
                return result;
            }
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}

