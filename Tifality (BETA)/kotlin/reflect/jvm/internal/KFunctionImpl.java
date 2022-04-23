/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionBase;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KFunction;
import kotlin.reflect.KParameter;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.FunctionWithAllInvokes;
import kotlin.reflect.jvm.internal.JvmFunctionSignature;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KDeclarationContainerImpl;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.ReflectProperties;
import kotlin.reflect.jvm.internal.ReflectionObjectRenderer;
import kotlin.reflect.jvm.internal.RuntimeTypeMapper;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.calls.AnnotationConstructorCaller;
import kotlin.reflect.jvm.internal.calls.Caller;
import kotlin.reflect.jvm.internal.calls.CallerImpl;
import kotlin.reflect.jvm.internal.calls.CallerKt;
import kotlin.reflect.jvm.internal.calls.InlineClassAwareCallerKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.InlineClassManglingRulesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0000\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00032\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00042\u00020\u0005B)\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0002\u0010\fB\u0017\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\r\u001a\u00020\u000e\u00a2\u0006\u0002\u0010\u000fB5\b\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0002\u0010\u0012J&\u00102\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u000304032\n\u00105\u001a\u0006\u0012\u0002\b\u0003042\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u00106\u001a\u0002072\u0006\u00105\u001a\u000208H\u0002J\u0010\u00109\u001a\u0002072\u0006\u00105\u001a\u000208H\u0002J\u0010\u0010:\u001a\u0002072\u0006\u00105\u001a\u000208H\u0002J\u0013\u0010;\u001a\u00020)2\b\u0010<\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\b\u0010=\u001a\u00020\u0014H\u0016J\b\u0010>\u001a\u00020\tH\u0016R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u000b\u001a\u0004\u0018\u00010\u00028BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u001f\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001a8VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R!\u0010!\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001a8VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b#\u0010\u001e\u001a\u0004\b\"\u0010\u001cR\u001b\u0010\r\u001a\u00020\u000e8VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b$\u0010%R\u0014\u0010(\u001a\u00020)8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b(\u0010*R\u0014\u0010+\u001a\u00020)8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b+\u0010*R\u0014\u0010,\u001a\u00020)8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b,\u0010*R\u0014\u0010-\u001a\u00020)8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b-\u0010*R\u0014\u0010.\u001a\u00020)8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b.\u0010*R\u0014\u0010/\u001a\u00020)8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b/\u0010*R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b0\u00101R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006?"}, d2={"Lkotlin/reflect/jvm/internal/KFunctionImpl;", "Lkotlin/reflect/jvm/internal/KCallableImpl;", "", "Lkotlin/reflect/KFunction;", "Lkotlin/jvm/internal/FunctionBase;", "Lkotlin/reflect/jvm/internal/FunctionWithAllInvokes;", "container", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "name", "", "signature", "boundReceiver", "(Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/FunctionDescriptor;", "(Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)V", "descriptorInitialValue", "rawBoundReceiver", "(Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;Ljava/lang/String;Ljava/lang/String;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Ljava/lang/Object;)V", "arity", "", "getArity", "()I", "getBoundReceiver", "()Ljava/lang/Object;", "caller", "Lkotlin/reflect/jvm/internal/calls/Caller;", "getCaller", "()Lkotlin/reflect/jvm/internal/calls/Caller;", "caller$delegate", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazyVal;", "getContainer", "()Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "defaultCaller", "getDefaultCaller", "defaultCaller$delegate", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "descriptor$delegate", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazySoftVal;", "isBound", "", "()Z", "isExternal", "isInfix", "isInline", "isOperator", "isSuspend", "getName", "()Ljava/lang/String;", "createConstructorCaller", "Lkotlin/reflect/jvm/internal/calls/CallerImpl;", "Ljava/lang/reflect/Constructor;", "member", "createInstanceMethodCaller", "Lkotlin/reflect/jvm/internal/calls/CallerImpl$Method;", "Ljava/lang/reflect/Method;", "createJvmStaticInObjectCaller", "createStaticMethodCaller", "equals", "other", "hashCode", "toString", "kotlin-reflection"})
public final class KFunctionImpl
extends KCallableImpl<Object>
implements FunctionBase<Object>,
KFunction<Object>,
FunctionWithAllInvokes {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    @NotNull
    private final ReflectProperties.LazySoftVal descriptor$delegate;
    @NotNull
    private final ReflectProperties.LazyVal caller$delegate;
    @Nullable
    private final ReflectProperties.LazyVal defaultCaller$delegate;
    @NotNull
    private final KDeclarationContainerImpl container;
    private final String signature;
    private final Object rawBoundReceiver;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(KFunctionImpl.class), "descriptor", "getDescriptor()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(KFunctionImpl.class), "caller", "getCaller()Lkotlin/reflect/jvm/internal/calls/Caller;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(KFunctionImpl.class), "defaultCaller", "getDefaultCaller()Lkotlin/reflect/jvm/internal/calls/Caller;"))};
    }

    @Override
    public boolean isBound() {
        return Intrinsics.areEqual(this.rawBoundReceiver, CallableReference.NO_RECEIVER) ^ true;
    }

    @Override
    @NotNull
    public FunctionDescriptor getDescriptor() {
        return (FunctionDescriptor)this.descriptor$delegate.getValue(this, $$delegatedProperties[0]);
    }

    @Override
    @NotNull
    public String getName() {
        String string = this.getDescriptor().getName().asString();
        Intrinsics.checkNotNullExpressionValue(string, "descriptor.name.asString()");
        return string;
    }

    @Override
    @NotNull
    public Caller<?> getCaller() {
        return (Caller)this.caller$delegate.getValue(this, $$delegatedProperties[1]);
    }

    @Override
    @Nullable
    public Caller<?> getDefaultCaller() {
        return (Caller)this.defaultCaller$delegate.getValue(this, $$delegatedProperties[2]);
    }

    private final Object getBoundReceiver() {
        return InlineClassAwareCallerKt.coerceToExpectedReceiverType(this.rawBoundReceiver, this.getDescriptor());
    }

    private final CallerImpl.Method createStaticMethodCaller(Method member) {
        return this.isBound() ? (CallerImpl.Method)new CallerImpl.Method.BoundStatic(member, this.getBoundReceiver()) : (CallerImpl.Method)new CallerImpl.Method.Static(member);
    }

    private final CallerImpl.Method createJvmStaticInObjectCaller(Method member) {
        return this.isBound() ? (CallerImpl.Method)new CallerImpl.Method.BoundJvmStaticInObject(member) : (CallerImpl.Method)new CallerImpl.Method.JvmStaticInObject(member);
    }

    private final CallerImpl.Method createInstanceMethodCaller(Method member) {
        return this.isBound() ? (CallerImpl.Method)new CallerImpl.Method.BoundInstance(member, this.getBoundReceiver()) : (CallerImpl.Method)new CallerImpl.Method.Instance(member);
    }

    private final CallerImpl<Constructor<?>> createConstructorCaller(Constructor<?> member, FunctionDescriptor descriptor2) {
        return InlineClassManglingRulesKt.shouldHideConstructorDueToInlineClassTypeValueParameters(descriptor2) ? (this.isBound() ? (CallerImpl)new CallerImpl.AccessorForHiddenBoundConstructor(member, this.getBoundReceiver()) : (CallerImpl)new CallerImpl.AccessorForHiddenConstructor(member)) : (this.isBound() ? (CallerImpl)new CallerImpl.BoundConstructor(member, this.getBoundReceiver()) : (CallerImpl)new CallerImpl.Constructor(member));
    }

    @Override
    public int getArity() {
        return CallerKt.getArity(this.getCaller());
    }

    @Override
    public boolean isInline() {
        return this.getDescriptor().isInline();
    }

    @Override
    public boolean isExternal() {
        return this.getDescriptor().isExternal();
    }

    @Override
    public boolean isOperator() {
        return this.getDescriptor().isOperator();
    }

    @Override
    public boolean isInfix() {
        return this.getDescriptor().isInfix();
    }

    @Override
    public boolean isSuspend() {
        return this.getDescriptor().isSuspend();
    }

    public boolean equals(@Nullable Object other) {
        KFunctionImpl kFunctionImpl = UtilKt.asKFunctionImpl(other);
        if (kFunctionImpl == null) {
            return false;
        }
        KFunctionImpl that = kFunctionImpl;
        return Intrinsics.areEqual(this.getContainer(), that.getContainer()) && Intrinsics.areEqual(this.getName(), that.getName()) && Intrinsics.areEqual(this.signature, that.signature) && Intrinsics.areEqual(this.rawBoundReceiver, that.rawBoundReceiver);
    }

    public int hashCode() {
        return (this.getContainer().hashCode() * 31 + this.getName().hashCode()) * 31 + this.signature.hashCode();
    }

    @NotNull
    public String toString() {
        return ReflectionObjectRenderer.INSTANCE.renderFunction(this.getDescriptor());
    }

    @Override
    @NotNull
    public KDeclarationContainerImpl getContainer() {
        return this.container;
    }

    private KFunctionImpl(KDeclarationContainerImpl container, String name, String signature2, FunctionDescriptor descriptorInitialValue, Object rawBoundReceiver) {
        this.container = container;
        this.signature = signature2;
        this.rawBoundReceiver = rawBoundReceiver;
        this.descriptor$delegate = ReflectProperties.lazySoft(descriptorInitialValue, (Function0)new Function0<FunctionDescriptor>(this, name){
            final /* synthetic */ KFunctionImpl this$0;
            final /* synthetic */ String $name;

            public final FunctionDescriptor invoke() {
                return this.this$0.getContainer().findFunctionDescriptor(this.$name, KFunctionImpl.access$getSignature$p(this.this$0));
            }
            {
                this.this$0 = kFunctionImpl;
                this.$name = string;
                super(0);
            }
        });
        this.caller$delegate = ReflectProperties.lazy((Function0)new Function0<Caller<? extends Member>>(this){
            final /* synthetic */ KFunctionImpl this$0;

            /*
             * WARNING - void declaration
             */
            public final Caller<Member> invoke() {
                CallerImpl callerImpl;
                Member member;
                JvmFunctionSignature jvmSignature = RuntimeTypeMapper.INSTANCE.mapSignature(this.this$0.getDescriptor());
                Object object = jvmSignature;
                if (object instanceof JvmFunctionSignature.KotlinConstructor) {
                    if (this.this$0.isAnnotationConstructor()) {
                        Collection<String> collection;
                        void $this$mapTo$iv$iv;
                        void $this$map$iv;
                        Iterable iterable = this.this$0.getParameters();
                        Class<?> clazz = this.this$0.getContainer().getJClass();
                        boolean $i$f$map = false;
                        void var6_9 = $this$map$iv;
                        Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                        boolean $i$f$mapTo = false;
                        for (T item$iv$iv : $this$mapTo$iv$iv) {
                            String string;
                            void it;
                            KParameter kParameter = (KParameter)item$iv$iv;
                            collection = destination$iv$iv;
                            boolean bl = false;
                            Intrinsics.checkNotNull(it.getName());
                            collection.add(string);
                        }
                        collection = (List)destination$iv$iv;
                        DefaultConstructorMarker defaultConstructorMarker = null;
                        int n = 16;
                        List list = null;
                        AnnotationConstructorCaller.Origin origin = AnnotationConstructorCaller.Origin.KOTLIN;
                        AnnotationConstructorCaller.CallMode callMode = AnnotationConstructorCaller.CallMode.POSITIONAL_CALL;
                        List list2 = collection;
                        Class<?> clazz2 = clazz;
                        return new AnnotationConstructorCaller(clazz2, list2, callMode, origin, list, n, defaultConstructorMarker);
                    }
                    member = this.this$0.getContainer().findConstructorBySignature(((JvmFunctionSignature.KotlinConstructor)jvmSignature).getConstructorDesc());
                } else if (object instanceof JvmFunctionSignature.KotlinFunction) {
                    member = this.this$0.getContainer().findMethodBySignature(((JvmFunctionSignature.KotlinFunction)jvmSignature).getMethodName(), ((JvmFunctionSignature.KotlinFunction)jvmSignature).getMethodDesc());
                } else if (object instanceof JvmFunctionSignature.JavaMethod) {
                    member = ((JvmFunctionSignature.JavaMethod)jvmSignature).getMethod();
                } else if (object instanceof JvmFunctionSignature.JavaConstructor) {
                    member = ((JvmFunctionSignature.JavaConstructor)jvmSignature).getConstructor();
                } else {
                    if (object instanceof JvmFunctionSignature.FakeJavaAnnotationConstructor) {
                        Collection<String> collection;
                        void $this$mapTo$iv$iv;
                        void $this$map$iv;
                        List<Method> methods2 = ((JvmFunctionSignature.FakeJavaAnnotationConstructor)jvmSignature).getMethods();
                        Iterable $i$f$map = methods2;
                        Class<?> clazz = this.this$0.getContainer().getJClass();
                        boolean $i$f$map2 = false;
                        void destination$iv$iv = $this$map$iv;
                        Collection destination$iv$iv2 = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                        boolean $i$f$mapTo = false;
                        for (T item$iv$iv : $this$mapTo$iv$iv) {
                            void it;
                            Method bl = (Method)item$iv$iv;
                            collection = destination$iv$iv2;
                            boolean bl2 = false;
                            void v1 = it;
                            Intrinsics.checkNotNullExpressionValue(v1, "it");
                            String string = v1.getName();
                            collection.add(string);
                        }
                        collection = (List)destination$iv$iv2;
                        List<Method> list = methods2;
                        AnnotationConstructorCaller.Origin origin = AnnotationConstructorCaller.Origin.JAVA;
                        AnnotationConstructorCaller.CallMode callMode = AnnotationConstructorCaller.CallMode.POSITIONAL_CALL;
                        List list3 = collection;
                        Class<?> clazz3 = clazz;
                        return new AnnotationConstructorCaller(clazz3, list3, callMode, origin, list);
                    }
                    throw new NoWhenBranchMatchedException();
                }
                Member member2 = member;
                object = member2;
                if (object instanceof Constructor) {
                    callerImpl = KFunctionImpl.access$createConstructorCaller(this.this$0, (Constructor)member2, this.this$0.getDescriptor());
                } else if (object instanceof Method) {
                    callerImpl = !Modifier.isStatic(((Method)member2).getModifiers()) ? KFunctionImpl.access$createInstanceMethodCaller(this.this$0, (Method)member2) : (this.this$0.getDescriptor().getAnnotations().findAnnotation(UtilKt.getJVM_STATIC()) != null ? KFunctionImpl.access$createJvmStaticInObjectCaller(this.this$0, (Method)member2) : KFunctionImpl.access$createStaticMethodCaller(this.this$0, (Method)member2));
                } else {
                    throw (Throwable)new KotlinReflectionInternalError("Could not compute caller for function: " + this.this$0.getDescriptor() + " (member = " + member2 + ')');
                }
                return InlineClassAwareCallerKt.createInlineClassAwareCallerIfNeeded$default(callerImpl, this.this$0.getDescriptor(), false, 2, null);
            }
            {
                this.this$0 = kFunctionImpl;
                super(0);
            }
        });
        this.defaultCaller$delegate = ReflectProperties.lazy((Function0)new Function0<Caller<? extends Member>>(this){
            final /* synthetic */ KFunctionImpl this$0;

            /*
             * Unable to fully structure code
             */
            @Nullable
            public final Caller<Member> invoke() {
                block12: {
                    block13: {
                        block11: {
                            jvmSignature = RuntimeTypeMapper.INSTANCE.mapSignature(this.this$0.getDescriptor());
                            var3_2 = jvmSignature;
                            if (var3_2 instanceof JvmFunctionSignature.KotlinFunction) {
                                v0 = this.this$0.getContainer();
                                v1 = ((JvmFunctionSignature.KotlinFunction)jvmSignature).getMethodName();
                                v2 = ((JvmFunctionSignature.KotlinFunction)jvmSignature).getMethodDesc();
                                v3 = this.this$0.getCaller().getMember();
                                Intrinsics.checkNotNull(v3);
                                v4 = v0.findDefaultMethod(v1, v2, Modifier.isStatic(v3.getModifiers()) == false);
                            } else if (var3_2 instanceof JvmFunctionSignature.KotlinConstructor) {
                                if (this.this$0.isAnnotationConstructor()) {
                                    var4_3 = this.this$0.getParameters();
                                    var16_5 = this.this$0.getContainer().getJClass();
                                    $i$f$map = false;
                                    var6_9 = $this$map$iv;
                                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                                    $i$f$mapTo = false;
                                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                                        var11_19 = (KParameter)item$iv$iv;
                                        var17_23 = destination$iv$iv;
                                        $i$a$-map-KFunctionImpl$defaultCaller$2$member$1 = false;
                                        Intrinsics.checkNotNull(it.getName());
                                        var17_23.add(var18_25);
                                    }
                                    var17_23 = (List)destination$iv$iv;
                                    var19_27 = null;
                                    var20_28 = 16;
                                    var21_29 = null;
                                    var22_30 = AnnotationConstructorCaller.Origin.KOTLIN;
                                    var23_31 = AnnotationConstructorCaller.CallMode.CALL_BY_NAME;
                                    var24_32 = var17_23;
                                    var25_33 = var16_5;
                                    return new AnnotationConstructorCaller(var25_33, var24_32, var23_31, var22_30, var21_29, var20_28, var19_27);
                                }
                                v4 = this.this$0.getContainer().findDefaultConstructor(((JvmFunctionSignature.KotlinConstructor)jvmSignature).getConstructorDesc());
                            } else {
                                if (var3_2 instanceof JvmFunctionSignature.FakeJavaAnnotationConstructor) {
                                    methods = ((JvmFunctionSignature.FakeJavaAnnotationConstructor)jvmSignature).getMethods();
                                    $i$f$map = methods;
                                    var16_6 = this.this$0.getContainer().getJClass();
                                    $i$f$map = false;
                                    destination$iv$iv = $this$map$iv;
                                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                                    $i$f$mapTo = false;
                                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                                        $i$a$-map-KFunctionImpl$defaultCaller$2$member$1 = (Method)item$iv$iv;
                                        var17_24 = destination$iv$iv;
                                        $i$a$-map-KFunctionImpl$defaultCaller$2$member$2 = false;
                                        v5 = it;
                                        Intrinsics.checkNotNullExpressionValue(v5, "it");
                                        var18_26 = v5.getName();
                                        var17_24.add(var18_26);
                                    }
                                    var17_24 = (List)destination$iv$iv;
                                    var26_35 = methods;
                                    var27_36 = AnnotationConstructorCaller.Origin.JAVA;
                                    var28_37 = AnnotationConstructorCaller.CallMode.CALL_BY_NAME;
                                    var29_38 = var17_24;
                                    var30_39 = var16_6;
                                    return new AnnotationConstructorCaller(var30_39, var29_38, var28_37, var27_36, var26_35);
                                }
                                v4 = null;
                            }
                            member = v4;
                            var3_2 = member;
                            if (!(var3_2 instanceof Constructor)) break block11;
                            v6 = KFunctionImpl.access$createConstructorCaller(this.this$0, (Constructor)member, this.this$0.getDescriptor());
                            break block12;
                        }
                        if (!(var3_2 instanceof Method)) break block13;
                        if (this.this$0.getDescriptor().getAnnotations().findAnnotation(UtilKt.getJVM_STATIC()) == null) ** GOTO lbl-1000
                        v7 = this.this$0.getDescriptor().getContainingDeclaration();
                        if (v7 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                        }
                        if (!((ClassDescriptor)v7).isCompanionObject()) {
                            v8 = KFunctionImpl.access$createJvmStaticInObjectCaller(this.this$0, (Method)member);
                        } else lbl-1000:
                        // 2 sources

                        {
                            v8 = KFunctionImpl.access$createStaticMethodCaller(this.this$0, (Method)member);
                        }
                        v6 = v8;
                        break block12;
                    }
                    v6 = null;
                }
                return v6 != null ? InlineClassAwareCallerKt.createInlineClassAwareCallerIfNeeded(v6, this.this$0.getDescriptor(), true) : null;
            }
            {
                this.this$0 = kFunctionImpl;
                super(0);
            }
        });
    }

    /* synthetic */ KFunctionImpl(KDeclarationContainerImpl kDeclarationContainerImpl, String string, String string2, FunctionDescriptor functionDescriptor, Object object, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 0x10) != 0) {
            object = CallableReference.NO_RECEIVER;
        }
        this(kDeclarationContainerImpl, string, string2, functionDescriptor, object);
    }

    public KFunctionImpl(@NotNull KDeclarationContainerImpl container, @NotNull String name, @NotNull String signature2, @Nullable Object boundReceiver) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(signature2, "signature");
        this(container, name, signature2, null, boundReceiver);
    }

    public KFunctionImpl(@NotNull KDeclarationContainerImpl container, @NotNull FunctionDescriptor descriptor2) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        String string = descriptor2.getName().asString();
        Intrinsics.checkNotNullExpressionValue(string, "descriptor.name.asString()");
        this(container, string, RuntimeTypeMapper.INSTANCE.mapSignature(descriptor2).asString(), descriptor2, null, 16, null);
    }

    @Override
    @Nullable
    public Object invoke() {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14, @Nullable Object p15) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14, @Nullable Object p15, @Nullable Object p16) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14, @Nullable Object p15, @Nullable Object p16, @Nullable Object p17) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14, @Nullable Object p15, @Nullable Object p16, @Nullable Object p17, @Nullable Object p18) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14, @Nullable Object p15, @Nullable Object p16, @Nullable Object p17, @Nullable Object p18, @Nullable Object p19) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14, @Nullable Object p15, @Nullable Object p16, @Nullable Object p17, @Nullable Object p18, @Nullable Object p19, @Nullable Object p20) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14, @Nullable Object p15, @Nullable Object p16, @Nullable Object p17, @Nullable Object p18, @Nullable Object p19, @Nullable Object p20, @Nullable Object p21) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21);
    }

    @Override
    @Nullable
    public Object invoke(@Nullable Object p1, @Nullable Object p2, @Nullable Object p3, @Nullable Object p4, @Nullable Object p5, @Nullable Object p6, @Nullable Object p7, @Nullable Object p8, @Nullable Object p9, @Nullable Object p10, @Nullable Object p11, @Nullable Object p12, @Nullable Object p13, @Nullable Object p14, @Nullable Object p15, @Nullable Object p16, @Nullable Object p17, @Nullable Object p18, @Nullable Object p19, @Nullable Object p20, @Nullable Object p21, @Nullable Object p22) {
        return FunctionWithAllInvokes.DefaultImpls.invoke(this, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22);
    }

    public static final /* synthetic */ String access$getSignature$p(KFunctionImpl $this) {
        return $this.signature;
    }

    public static final /* synthetic */ CallerImpl access$createConstructorCaller(KFunctionImpl $this, Constructor member, FunctionDescriptor descriptor2) {
        return $this.createConstructorCaller(member, descriptor2);
    }

    public static final /* synthetic */ CallerImpl.Method access$createInstanceMethodCaller(KFunctionImpl $this, Method member) {
        return $this.createInstanceMethodCaller(member);
    }

    public static final /* synthetic */ CallerImpl.Method access$createJvmStaticInObjectCaller(KFunctionImpl $this, Method member) {
        return $this.createJvmStaticInObjectCaller(member);
    }

    public static final /* synthetic */ CallerImpl.Method access$createStaticMethodCaller(KFunctionImpl $this, Method member) {
        return $this.createStaticMethodCaller(member);
    }
}

