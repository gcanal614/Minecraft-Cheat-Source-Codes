/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.calls.AnnotationConstructorCallerKt;
import kotlin.reflect.jvm.internal.calls.Caller;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0002\b\u0004\b\u0000\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001:\u0002 !B?\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006\u00a2\u0006\u0002\u0010\u000eJ\u001b\u0010\u001c\u001a\u0004\u0018\u00010\u00102\n\u0010\u001d\u001a\u0006\u0012\u0002\b\u00030\u001eH\u0016\u00a2\u0006\u0002\u0010\u001fR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u0011\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u00168VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006\""}, d2={"Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller;", "Lkotlin/reflect/jvm/internal/calls/Caller;", "", "jClass", "Ljava/lang/Class;", "parameterNames", "", "", "callMode", "Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$CallMode;", "origin", "Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$Origin;", "methods", "Ljava/lang/reflect/Method;", "(Ljava/lang/Class;Ljava/util/List;Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$CallMode;Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$Origin;Ljava/util/List;)V", "defaultValues", "", "erasedParameterTypes", "member", "getMember", "()Ljava/lang/Void;", "parameterTypes", "Ljava/lang/reflect/Type;", "getParameterTypes", "()Ljava/util/List;", "returnType", "getReturnType", "()Ljava/lang/reflect/Type;", "call", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "CallMode", "Origin", "kotlin-reflection"})
public final class AnnotationConstructorCaller
implements Caller {
    @NotNull
    private final List<Type> parameterTypes;
    private final List<Class<?>> erasedParameterTypes;
    private final List<Object> defaultValues;
    private final Class<?> jClass;
    private final List<String> parameterNames;
    private final CallMode callMode;
    private final List<Method> methods;

    @Nullable
    public Void getMember() {
        return null;
    }

    @Override
    @NotNull
    public Type getReturnType() {
        return this.jClass;
    }

    @Override
    @NotNull
    public List<Type> getParameterTypes() {
        return this.parameterTypes;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @Nullable
    public Object call(@NotNull Object[] args2) {
        void $this$mapIndexedTo$iv$iv;
        Intrinsics.checkNotNullParameter(args2, "args");
        this.checkArguments(args2);
        Object[] $this$mapIndexed$iv = args2;
        boolean $i$f$mapIndexed = false;
        Object[] objectArray = $this$mapIndexed$iv;
        Collection destination$iv$iv = new ArrayList($this$mapIndexed$iv.length);
        boolean $i$f$mapIndexedTo = false;
        int index$iv$iv = 0;
        for (void item$iv$iv : $this$mapIndexedTo$iv$iv) {
            Object object;
            void index;
            void arg;
            Object value;
            int n = index$iv$iv++;
            void var13_12 = item$iv$iv;
            int n2 = n;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            Object object2 = value = arg == null && this.callMode == CallMode.CALL_BY_NAME ? this.defaultValues.get((int)index) : AnnotationConstructorCallerKt.access$transformKotlinToJvm(arg, this.erasedParameterTypes.get((int)index));
            if (value == null) {
                Void void_ = AnnotationConstructorCallerKt.access$throwIllegalArgumentType((int)index, this.parameterNames.get((int)index), this.erasedParameterTypes.get((int)index));
                throw null;
            }
            collection.add(object);
        }
        List values2 = (List)destination$iv$iv;
        return AnnotationConstructorCallerKt.createAnnotationInstance(this.jClass, MapsKt.toMap(CollectionsKt.zip((Iterable)this.parameterNames, values2)), this.methods);
    }

    public AnnotationConstructorCaller(@NotNull Class<?> jClass, @NotNull List<String> parameterNames, @NotNull CallMode callMode, @NotNull Origin origin, @NotNull List<Method> methods2) {
        Method method;
        Object object;
        Method it;
        Collection<Class<?>> collection;
        Iterable $this$mapTo$iv$iv;
        Iterable $this$map$iv;
        Intrinsics.checkNotNullParameter(jClass, "jClass");
        Intrinsics.checkNotNullParameter(parameterNames, "parameterNames");
        Intrinsics.checkNotNullParameter((Object)callMode, "callMode");
        Intrinsics.checkNotNullParameter((Object)origin, "origin");
        Intrinsics.checkNotNullParameter(methods2, "methods");
        this.jClass = jClass;
        this.parameterNames = parameterNames;
        this.callMode = callMode;
        this.methods = methods2;
        Iterable iterable = this.methods;
        AnnotationConstructorCaller annotationConstructorCaller = this;
        boolean $i$f$map = false;
        void var8_9 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            Method method2 = (Method)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            object = it.getGenericReturnType();
            collection.add((Class<?>)object);
        }
        collection = (List)destination$iv$iv;
        annotationConstructorCaller.parameterTypes = collection;
        $this$map$iv = this.methods;
        annotationConstructorCaller = this;
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            it = (Method)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            Class<?> clazz = method.getReturnType();
            boolean bl2 = false;
            boolean bl3 = false;
            Class<?> it2 = clazz;
            boolean bl4 = false;
            Class<?> clazz2 = ReflectClassUtilKt.getWrapperByPrimitive(it2);
            if (clazz2 == null) {
                clazz2 = it2;
            }
            object = clazz2;
            collection.add((Class<?>)object);
        }
        collection = (List)destination$iv$iv;
        annotationConstructorCaller.erasedParameterTypes = collection;
        $this$map$iv = this.methods;
        annotationConstructorCaller = this;
        $i$f$map = false;
        $this$mapTo$iv$iv = $this$map$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            method = (Method)item$iv$iv;
            collection = destination$iv$iv;
            boolean bl = false;
            object = method.getDefaultValue();
            collection.add((Class<?>)object);
        }
        collection = (List)destination$iv$iv;
        annotationConstructorCaller.defaultValues = collection;
        if (this.callMode == CallMode.POSITIONAL_CALL && origin == Origin.JAVA) {
            iterable = CollectionsKt.minus((Iterable)this.parameterNames, "value");
            boolean bl = false;
            if (!iterable.isEmpty()) {
                throw (Throwable)new UnsupportedOperationException("Positional call of a Java annotation constructor is allowed only if there are no parameters or one parameter named \"value\". This restriction exists because Java annotations (in contrast to Kotlin)do not impose any order on their arguments. Use KCallable#callBy instead.");
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public /* synthetic */ AnnotationConstructorCaller(Class clazz, List list, CallMode callMode, Origin origin, List list2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 0x10) != 0) {
            void $this$mapTo$iv$iv;
            Iterable $this$map$iv = list;
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void name;
                String string = (String)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                Method method = clazz.getDeclaredMethod((String)name, new Class[0]);
                collection.add(method);
            }
            list2 = (List)destination$iv$iv;
        }
        this(clazz, list, callMode, origin, list2);
    }

    public void checkArguments(@NotNull Object[] args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        Caller.DefaultImpls.checkArguments(this, args2);
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$CallMode;", "", "(Ljava/lang/String;I)V", "CALL_BY_NAME", "POSITIONAL_CALL", "kotlin-reflection"})
    public static final class CallMode
    extends Enum<CallMode> {
        public static final /* enum */ CallMode CALL_BY_NAME;
        public static final /* enum */ CallMode POSITIONAL_CALL;
        private static final /* synthetic */ CallMode[] $VALUES;

        static {
            CallMode[] callModeArray = new CallMode[2];
            CallMode[] callModeArray2 = callModeArray;
            callModeArray[0] = CALL_BY_NAME = new CallMode();
            callModeArray[1] = POSITIONAL_CALL = new CallMode();
            $VALUES = callModeArray;
        }

        public static CallMode[] values() {
            return (CallMode[])$VALUES.clone();
        }

        public static CallMode valueOf(String string) {
            return Enum.valueOf(CallMode.class, string);
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lkotlin/reflect/jvm/internal/calls/AnnotationConstructorCaller$Origin;", "", "(Ljava/lang/String;I)V", "JAVA", "KOTLIN", "kotlin-reflection"})
    public static final class Origin
    extends Enum<Origin> {
        public static final /* enum */ Origin JAVA;
        public static final /* enum */ Origin KOTLIN;
        private static final /* synthetic */ Origin[] $VALUES;

        static {
            Origin[] originArray = new Origin[2];
            Origin[] originArray2 = originArray;
            originArray[0] = JAVA = new Origin();
            originArray[1] = KOTLIN = new Origin();
            $VALUES = originArray;
        }

        public static Origin[] values() {
            return (Origin[])$VALUES.clone();
        }

        public static Origin valueOf(String string) {
            return Enum.valueOf(Origin.class, string);
        }
    }
}

