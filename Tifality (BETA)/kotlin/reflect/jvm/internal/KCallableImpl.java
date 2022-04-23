/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KParameter;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;
import kotlin.reflect.full.IllegalCallableAccessException;
import kotlin.reflect.jvm.KTypesJvm;
import kotlin.reflect.jvm.ReflectJvmMapping;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KDeclarationContainerImpl;
import kotlin.reflect.jvm.internal.KParameterImpl;
import kotlin.reflect.jvm.internal.KTypeImpl;
import kotlin.reflect.jvm.internal.KTypeParameterImpl;
import kotlin.reflect.jvm.internal.KTypeParameterOwnerImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.ReflectProperties;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.calls.Caller;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.ParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaCallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u001b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b \u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J%\u00106\u001a\u00028\u00002\u0016\u00107\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010908\"\u0004\u0018\u000109H\u0016\u00a2\u0006\u0002\u0010:J#\u0010;\u001a\u00028\u00002\u0014\u00107\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u0001090<H\u0002\u00a2\u0006\u0002\u0010=J#\u0010>\u001a\u00028\u00002\u0014\u00107\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u0001090<H\u0016\u00a2\u0006\u0002\u0010=J3\u0010?\u001a\u00028\u00002\u0014\u00107\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u0001090<2\f\u0010@\u001a\b\u0012\u0002\b\u0003\u0018\u00010AH\u0000\u00a2\u0006\u0004\bB\u0010CJ\u0010\u0010D\u001a\u0002092\u0006\u0010E\u001a\u00020,H\u0002J\n\u0010F\u001a\u0004\u0018\u00010GH\u0002R(\u0010\u0005\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\b \t*\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00070\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R(\u0010\n\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\f \t*\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b0\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0010\u0012\f\u0012\n \t*\u0004\u0018\u00010\u000e0\u000e0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R(\u0010\u000f\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0010 \t*\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u00070\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u0015X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0012\u0010\u0018\u001a\u00020\u0019X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0018\u0010\u001c\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0015X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u0017R\u0012\u0010\u001e\u001a\u00020\u001fX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b \u0010!R\u0014\u0010\"\u001a\u00020#8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\"\u0010$R\u0014\u0010%\u001a\u00020#8DX\u0084\u0004\u00a2\u0006\u0006\u001a\u0004\b%\u0010$R\u0012\u0010&\u001a\u00020#X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b&\u0010$R\u0014\u0010'\u001a\u00020#8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b'\u0010$R\u0014\u0010(\u001a\u00020#8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b(\u0010$R\u001a\u0010)\u001a\b\u0012\u0004\u0012\u00020\f0\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b*\u0010\u0013R\u0014\u0010+\u001a\u00020,8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b-\u0010.R\u001a\u0010/\u001a\b\u0012\u0004\u0012\u0002000\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b1\u0010\u0013R\u0016\u00102\u001a\u0004\u0018\u0001038VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b4\u00105\u00a8\u0006H"}, d2={"Lkotlin/reflect/jvm/internal/KCallableImpl;", "R", "Lkotlin/reflect/KCallable;", "Lkotlin/reflect/jvm/internal/KTypeParameterOwnerImpl;", "()V", "_annotations", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazySoftVal;", "", "", "kotlin.jvm.PlatformType", "_parameters", "Ljava/util/ArrayList;", "Lkotlin/reflect/KParameter;", "_returnType", "Lkotlin/reflect/jvm/internal/KTypeImpl;", "_typeParameters", "Lkotlin/reflect/jvm/internal/KTypeParameterImpl;", "annotations", "getAnnotations", "()Ljava/util/List;", "caller", "Lkotlin/reflect/jvm/internal/calls/Caller;", "getCaller", "()Lkotlin/reflect/jvm/internal/calls/Caller;", "container", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "getContainer", "()Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "defaultCaller", "getDefaultCaller", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/CallableMemberDescriptor;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "isAbstract", "", "()Z", "isAnnotationConstructor", "isBound", "isFinal", "isOpen", "parameters", "getParameters", "returnType", "Lkotlin/reflect/KType;", "getReturnType", "()Lkotlin/reflect/KType;", "typeParameters", "Lkotlin/reflect/KTypeParameter;", "getTypeParameters", "visibility", "Lkotlin/reflect/KVisibility;", "getVisibility", "()Lkotlin/reflect/KVisibility;", "call", "args", "", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "callAnnotationConstructor", "", "(Ljava/util/Map;)Ljava/lang/Object;", "callBy", "callDefaultMethod", "continuationArgument", "Lkotlin/coroutines/Continuation;", "callDefaultMethod$kotlin_reflection", "(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "defaultEmptyArray", "type", "extractContinuationArgument", "Ljava/lang/reflect/Type;", "kotlin-reflection"})
public abstract class KCallableImpl<R>
implements KCallable<R>,
KTypeParameterOwnerImpl {
    private final ReflectProperties.LazySoftVal<List<Annotation>> _annotations;
    private final ReflectProperties.LazySoftVal<ArrayList<KParameter>> _parameters;
    private final ReflectProperties.LazySoftVal<KTypeImpl> _returnType;
    private final ReflectProperties.LazySoftVal<List<KTypeParameterImpl>> _typeParameters;

    @NotNull
    public abstract CallableMemberDescriptor getDescriptor();

    @NotNull
    public abstract Caller<?> getCaller();

    @Nullable
    public abstract Caller<?> getDefaultCaller();

    @NotNull
    public abstract KDeclarationContainerImpl getContainer();

    public abstract boolean isBound();

    @Override
    @NotNull
    public List<Annotation> getAnnotations() {
        List<Annotation> list = this._annotations.invoke();
        Intrinsics.checkNotNullExpressionValue(list, "_annotations()");
        return list;
    }

    @Override
    @NotNull
    public List<KParameter> getParameters() {
        ArrayList<KParameter> arrayList = this._parameters.invoke();
        Intrinsics.checkNotNullExpressionValue(arrayList, "_parameters()");
        return arrayList;
    }

    @Override
    @NotNull
    public KType getReturnType() {
        KTypeImpl kTypeImpl = this._returnType.invoke();
        Intrinsics.checkNotNullExpressionValue(kTypeImpl, "_returnType()");
        return kTypeImpl;
    }

    @Override
    @NotNull
    public List<KTypeParameter> getTypeParameters() {
        List<KTypeParameter> list = this._typeParameters.invoke();
        Intrinsics.checkNotNullExpressionValue(list, "_typeParameters()");
        return list;
    }

    @Override
    @Nullable
    public KVisibility getVisibility() {
        Visibility visibility = this.getDescriptor().getVisibility();
        Intrinsics.checkNotNullExpressionValue(visibility, "descriptor.visibility");
        return UtilKt.toKVisibility(visibility);
    }

    @Override
    public boolean isFinal() {
        return this.getDescriptor().getModality() == Modality.FINAL;
    }

    @Override
    public boolean isOpen() {
        return this.getDescriptor().getModality() == Modality.OPEN;
    }

    @Override
    public boolean isAbstract() {
        return this.getDescriptor().getModality() == Modality.ABSTRACT;
    }

    protected final boolean isAnnotationConstructor() {
        return Intrinsics.areEqual(this.getName(), "<init>") && this.getContainer().getJClass().isAnnotation();
    }

    @Override
    public R call(Object ... args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        boolean $i$f$reflectionCall = false;
        try {
            boolean bl = false;
            return (R)this.getCaller().call(args2);
        }
        catch (IllegalAccessException e$iv) {
            throw (Throwable)new IllegalCallableAccessException(e$iv);
        }
    }

    @Override
    public R callBy(@NotNull Map<KParameter, ? extends Object> args2) {
        Intrinsics.checkNotNullParameter(args2, "args");
        return this.isAnnotationConstructor() ? this.callAnnotationConstructor(args2) : this.callDefaultMethod$kotlin_reflection(args2, null);
    }

    public final R callDefaultMethod$kotlin_reflection(@NotNull Map<KParameter, ? extends Object> args2, @Nullable Continuation<?> continuationArgument) {
        Object object;
        Intrinsics.checkNotNullParameter(args2, "args");
        List<KParameter> parameters2 = this.getParameters();
        ArrayList<Object> arguments2 = new ArrayList<Object>(parameters2.size());
        int mask = 0;
        ArrayList<Integer> masks = new ArrayList<Integer>(1);
        int index = 0;
        boolean anyOptional = false;
        for (KParameter parameter : parameters2) {
            if (index != 0 && index % 32 == 0) {
                masks.add(mask);
                mask = 0;
            }
            if (args2.containsKey(parameter)) {
                arguments2.add(args2.get(parameter));
            } else if (parameter.isOptional()) {
                arguments2.add(UtilKt.isInlineClassType(parameter.getType()) ? null : UtilKt.defaultPrimitiveValue(ReflectJvmMapping.getJavaType(parameter.getType())));
                mask |= 1 << index % 32;
                anyOptional = true;
            } else if (parameter.isVararg()) {
                arguments2.add(this.defaultEmptyArray(parameter.getType()));
            } else {
                throw (Throwable)new IllegalArgumentException("No argument provided for a required parameter: " + parameter);
            }
            if (parameter.getKind() != KParameter.Kind.VALUE) continue;
            ++index;
        }
        if (continuationArgument != null) {
            arguments2.add(continuationArgument);
        }
        if (!anyOptional) {
            Collection $this$toTypedArray$iv = arguments2;
            boolean $i$f$toTypedArray = false;
            Collection thisCollection$iv = $this$toTypedArray$iv;
            Object[] objectArray = thisCollection$iv.toArray(new Object[0]);
            if (objectArray == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            return this.call(Arrays.copyOf(objectArray, objectArray.length));
        }
        masks.add(mask);
        Caller<?> caller2 = this.getDefaultCaller();
        if (caller2 == null) {
            throw (Throwable)new KotlinReflectionInternalError("This callable does not support a default call: " + this.getDescriptor());
        }
        Caller<?> caller3 = caller2;
        arguments2.addAll((Collection)masks);
        arguments2.add(null);
        boolean $i$f$reflectionCall = false;
        try {
            boolean bl = false;
            Collection $this$toTypedArray$iv = arguments2;
            boolean $i$f$toTypedArray = false;
            Collection thisCollection$iv = $this$toTypedArray$iv;
            Object[] objectArray = thisCollection$iv.toArray(new Object[0]);
            if (objectArray == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            object = caller3.call(objectArray);
        }
        catch (IllegalAccessException e$iv) {
            throw (Throwable)new IllegalCallableAccessException(e$iv);
        }
        return (R)object;
    }

    /*
     * WARNING - void declaration
     */
    private final R callAnnotationConstructor(Map<KParameter, ? extends Object> args2) {
        Object object;
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = this.getParameters();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            Object object2;
            void parameter;
            KParameter kParameter = (KParameter)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            if (args2.containsKey(parameter)) {
                object2 = args2.get(parameter);
                if (object2 == null) {
                    throw (Throwable)new IllegalArgumentException("Annotation argument value cannot be null (" + parameter + ')');
                }
            } else if (parameter.isOptional()) {
                object2 = null;
            } else if (parameter.isVararg()) {
                object2 = this.defaultEmptyArray(parameter.getType());
            } else {
                throw (Throwable)new IllegalArgumentException("No argument provided for a required parameter: " + parameter);
            }
            Object object3 = object2;
            collection.add(object3);
        }
        List arguments2 = (List)destination$iv$iv;
        Caller<?> caller2 = this.getDefaultCaller();
        if (caller2 == null) {
            throw (Throwable)new KotlinReflectionInternalError("This callable does not support a default call: " + this.getDescriptor());
        }
        Caller<?> caller3 = caller2;
        boolean $i$f$reflectionCall = false;
        try {
            boolean bl = false;
            Collection $this$toTypedArray$iv = arguments2;
            boolean $i$f$toTypedArray = false;
            Collection thisCollection$iv = $this$toTypedArray$iv;
            Object[] objectArray = thisCollection$iv.toArray(new Object[0]);
            if (objectArray == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            object = caller3.call(objectArray);
        }
        catch (IllegalAccessException e$iv) {
            throw (Throwable)new IllegalCallableAccessException(e$iv);
        }
        return (R)object;
    }

    private final Object defaultEmptyArray(KType type2) {
        Class<KClass<?>> clazz = JvmClassMappingKt.getJavaClass(KTypesJvm.getJvmErasure(type2));
        boolean bl = false;
        boolean bl2 = false;
        Class<KClass<?>> $this$run = clazz;
        boolean bl3 = false;
        if (!$this$run.isArray()) {
            throw (Throwable)new KotlinReflectionInternalError("Cannot instantiate the default empty array of type " + $this$run.getSimpleName() + ", because it is not an array type");
        }
        Object object = Array.newInstance($this$run.getComponentType(), 0);
        Intrinsics.checkNotNullExpressionValue(object, "type.jvmErasure.java.run\u2026\"\n            )\n        }");
        return object;
    }

    private final Type extractContinuationArgument() {
        CallableMemberDescriptor callableMemberDescriptor = this.getDescriptor();
        if (!(callableMemberDescriptor instanceof FunctionDescriptor)) {
            callableMemberDescriptor = null;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor)callableMemberDescriptor;
        if (functionDescriptor != null) {
            if (functionDescriptor.isSuspend()) {
                ParameterizedType continuationType;
                Type type2 = CollectionsKt.lastOrNull(this.getCaller().getParameterTypes());
                if (!(type2 instanceof ParameterizedType)) {
                    type2 = null;
                }
                ParameterizedType parameterizedType = continuationType = (ParameterizedType)type2;
                if (Intrinsics.areEqual(parameterizedType != null ? parameterizedType.getRawType() : null, Continuation.class)) {
                    Type[] wildcard;
                    Type[] typeArray = continuationType.getActualTypeArguments();
                    Intrinsics.checkNotNullExpressionValue(typeArray, "continuationType.actualTypeArguments");
                    Type type3 = ArraysKt.single(typeArray);
                    if (!(type3 instanceof WildcardType)) {
                        type3 = null;
                    }
                    Type[] typeArray2 = wildcard = (Type[])type3;
                    return wildcard != null && (typeArray2 = typeArray2.getLowerBounds()) != null ? ArraysKt.first(typeArray2) : null;
                }
            }
        }
        return null;
    }

    public KCallableImpl() {
        ReflectProperties.LazySoftVal lazySoftVal = ReflectProperties.lazySoft((Function0)new Function0<List<? extends Annotation>>(this){
            final /* synthetic */ KCallableImpl this$0;

            public final List<Annotation> invoke() {
                return UtilKt.computeAnnotations(this.this$0.getDescriptor());
            }
            {
                this.this$0 = kCallableImpl;
                super(0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(lazySoftVal, "ReflectProperties.lazySo\u2026or.computeAnnotations() }");
        this._annotations = lazySoftVal;
        ReflectProperties.LazySoftVal lazySoftVal2 = ReflectProperties.lazySoft((Function0)new Function0<ArrayList<KParameter>>(this){
            final /* synthetic */ KCallableImpl this$0;

            /*
             * WARNING - void declaration
             */
            public final ArrayList<KParameter> invoke() {
                void var2_2;
                CallableMemberDescriptor descriptor2 = this.this$0.getDescriptor();
                ArrayList<KParameterImpl> result2 = new ArrayList<KParameterImpl>();
                int index = 0;
                if (!this.this$0.isBound()) {
                    ReceiverParameterDescriptor extensionReceiver;
                    ReceiverParameterDescriptor instanceReceiver = UtilKt.getInstanceReceiverParameter(descriptor2);
                    if (instanceReceiver != null) {
                        result2.add(new KParameterImpl(this.this$0, index++, KParameter.Kind.INSTANCE, (Function0<? extends ParameterDescriptor>)new Function0<ParameterDescriptor>(instanceReceiver){
                            final /* synthetic */ ReceiverParameterDescriptor $instanceReceiver;

                            @NotNull
                            public final ParameterDescriptor invoke() {
                                return this.$instanceReceiver;
                            }
                            {
                                this.$instanceReceiver = receiverParameterDescriptor;
                                super(0);
                            }
                        }));
                    }
                    if ((extensionReceiver = descriptor2.getExtensionReceiverParameter()) != null) {
                        result2.add(new KParameterImpl(this.this$0, index++, KParameter.Kind.EXTENSION_RECEIVER, (Function0<? extends ParameterDescriptor>)new Function0<ParameterDescriptor>(extensionReceiver){
                            final /* synthetic */ ReceiverParameterDescriptor $extensionReceiver;

                            @NotNull
                            public final ParameterDescriptor invoke() {
                                return this.$extensionReceiver;
                            }
                            {
                                this.$extensionReceiver = receiverParameterDescriptor;
                                super(0);
                            }
                        }));
                    }
                }
                int instanceReceiver = 0;
                List<ValueParameterDescriptor> list = descriptor2.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(list, "descriptor.valueParameters");
                int extensionReceiver = ((Collection)list).size();
                while (instanceReceiver < extensionReceiver) {
                    void i;
                    result2.add(new KParameterImpl(this.this$0, index++, KParameter.Kind.VALUE, (Function0<? extends ParameterDescriptor>)new Function0<ParameterDescriptor>(descriptor2, (int)i){
                        final /* synthetic */ CallableMemberDescriptor $descriptor;
                        final /* synthetic */ int $i;

                        @NotNull
                        public final ParameterDescriptor invoke() {
                            ValueParameterDescriptor valueParameterDescriptor = this.$descriptor.getValueParameters().get(this.$i);
                            Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "descriptor.valueParameters[i]");
                            return valueParameterDescriptor;
                        }
                        {
                            this.$descriptor = callableMemberDescriptor;
                            this.$i = n;
                            super(0);
                        }
                    }));
                    ++i;
                }
                if (this.this$0.isAnnotationConstructor() && descriptor2 instanceof JavaCallableMemberDescriptor) {
                    List $this$sortBy$iv = result2;
                    boolean $i$f$sortBy = false;
                    if ($this$sortBy$iv.size() > 1) {
                        boolean bl = false;
                        CollectionsKt.sortWith($this$sortBy$iv, (Comparator)new Comparator<T>(){

                            public final int compare(T a2, T b2) {
                                boolean bl = false;
                                KParameter it = (KParameter)a2;
                                boolean bl2 = false;
                                Comparable comparable = (Comparable)((Object)it.getName());
                                it = (KParameter)b2;
                                Comparable comparable2 = comparable;
                                bl2 = false;
                                String string = it.getName();
                                return ComparisonsKt.compareValues(comparable2, (Comparable)((Object)string));
                            }
                        });
                    }
                }
                result2.trimToSize();
                return var2_2;
            }
            {
                this.this$0 = kCallableImpl;
                super(0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(lazySoftVal2, "ReflectProperties.lazySo\u2026ze()\n        result\n    }");
        this._parameters = lazySoftVal2;
        ReflectProperties.LazySoftVal lazySoftVal3 = ReflectProperties.lazySoft((Function0)new Function0<KTypeImpl>(this){
            final /* synthetic */ KCallableImpl this$0;

            public final KTypeImpl invoke() {
                KotlinType kotlinType = this.this$0.getDescriptor().getReturnType();
                Intrinsics.checkNotNull(kotlinType);
                Intrinsics.checkNotNullExpressionValue(kotlinType, "descriptor.returnType!!");
                return new KTypeImpl(kotlinType, (Function0<? extends Type>)new Function0<Type>(this){
                    final /* synthetic */ _returnType.1 this$0;

                    @NotNull
                    public final Type invoke() {
                        Type type2 = KCallableImpl.access$extractContinuationArgument(this.this$0.this$0);
                        if (type2 == null) {
                            type2 = this.this$0.this$0.getCaller().getReturnType();
                        }
                        return type2;
                    }
                    {
                        this.this$0 = var1_1;
                        super(0);
                    }
                });
            }
            {
                this.this$0 = kCallableImpl;
                super(0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(lazySoftVal3, "ReflectProperties.lazySo\u2026eturnType\n        }\n    }");
        this._returnType = lazySoftVal3;
        ReflectProperties.LazySoftVal lazySoftVal4 = ReflectProperties.lazySoft((Function0)new Function0<List<? extends KTypeParameterImpl>>(this){
            final /* synthetic */ KCallableImpl this$0;

            /*
             * WARNING - void declaration
             */
            public final List<KTypeParameterImpl> invoke() {
                void $this$mapTo$iv$iv;
                List<TypeParameterDescriptor> list = this.this$0.getDescriptor().getTypeParameters();
                Intrinsics.checkNotNullExpressionValue(list, "descriptor.typeParameters");
                Iterable $this$map$iv = list;
                boolean $i$f$map = false;
                Iterable iterable = $this$map$iv;
                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    void descriptor2;
                    TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor)item$iv$iv;
                    Collection collection = destination$iv$iv;
                    boolean bl = false;
                    KTypeParameterOwnerImpl kTypeParameterOwnerImpl = this.this$0;
                    void v2 = descriptor2;
                    Intrinsics.checkNotNullExpressionValue(v2, "descriptor");
                    KTypeParameterImpl kTypeParameterImpl = new KTypeParameterImpl(kTypeParameterOwnerImpl, (TypeParameterDescriptor)v2);
                    collection.add(kTypeParameterImpl);
                }
                return (List)destination$iv$iv;
            }
            {
                this.this$0 = kCallableImpl;
                super(0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(lazySoftVal4, "ReflectProperties.lazySo\u2026this, descriptor) }\n    }");
        this._typeParameters = lazySoftVal4;
    }

    public static final /* synthetic */ Type access$extractContinuationArgument(KCallableImpl $this) {
        return $this.extractContinuationArgument();
    }
}

