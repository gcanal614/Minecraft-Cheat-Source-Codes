/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal;

import java.lang.reflect.Field;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KFunction;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.KProperty;
import kotlin.reflect.full.IllegalPropertyDelegateAccessException;
import kotlin.reflect.jvm.internal.JvmPropertySignature;
import kotlin.reflect.jvm.internal.KCallableImpl;
import kotlin.reflect.jvm.internal.KDeclarationContainerImpl;
import kotlin.reflect.jvm.internal.KPropertyImplKt;
import kotlin.reflect.jvm.internal.ReflectProperties;
import kotlin.reflect.jvm.internal.ReflectionObjectRenderer;
import kotlin.reflect.jvm.internal.RuntimeTypeMapper;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.calls.Caller;
import kotlin.reflect.jvm.internal.calls.InlineClassAwareCallerKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyAccessorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAbi;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\b\n\u0002\b\u0006\b \u0018\u0000 >*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0004=>?@B)\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0002\u0010\u000bB\u0017\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eB3\b\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\r\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0002\u0010\u0011J\n\u00104\u001a\u0004\u0018\u00010\u0017H\u0004J\u0013\u00105\u001a\u00020)2\b\u00106\u001a\u0004\u0018\u00010\nH\u0096\u0002J\u001e\u00107\u001a\u0004\u0018\u00010\n2\b\u00108\u001a\u0004\u0018\u00010\u00172\b\u00109\u001a\u0004\u0018\u00010\nH\u0004J\b\u0010:\u001a\u00020;H\u0016J\b\u0010<\u001a\u00020\u0007H\u0016R\u001c\u0010\u0012\u001a\u0010\u0012\f\u0012\n \u0014*\u0004\u0018\u00010\r0\r0\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00170\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0013\u0010\t\u001a\u0004\u0018\u00010\n8F\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0018\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u001b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b!\u0010\u001dR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\"\u0010#R\u0018\u0010$\u001a\b\u0012\u0004\u0012\u00028\u00000%X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b&\u0010'R\u0014\u0010(\u001a\u00020)8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b(\u0010*R\u0014\u0010+\u001a\u00020)8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b+\u0010*R\u0014\u0010,\u001a\u00020)8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b,\u0010*R\u0014\u0010-\u001a\u00020)8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b-\u0010*R\u0013\u0010.\u001a\u0004\u0018\u00010\u00178F\u00a2\u0006\u0006\u001a\u0004\b/\u00100R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u00102\u00a8\u0006A"}, d2={"Lkotlin/reflect/jvm/internal/KPropertyImpl;", "V", "Lkotlin/reflect/jvm/internal/KCallableImpl;", "Lkotlin/reflect/KProperty;", "container", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "name", "", "signature", "boundReceiver", "", "(Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyDescriptor;", "(Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;)V", "descriptorInitialValue", "rawBoundReceiver", "(Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;Ljava/lang/String;Ljava/lang/String;Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Ljava/lang/Object;)V", "_descriptor", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazySoftVal;", "kotlin.jvm.PlatformType", "_javaField", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazyVal;", "Ljava/lang/reflect/Field;", "getBoundReceiver", "()Ljava/lang/Object;", "caller", "Lkotlin/reflect/jvm/internal/calls/Caller;", "getCaller", "()Lkotlin/reflect/jvm/internal/calls/Caller;", "getContainer", "()Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "defaultCaller", "getDefaultCaller", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getter", "Lkotlin/reflect/jvm/internal/KPropertyImpl$Getter;", "getGetter", "()Lkotlin/reflect/jvm/internal/KPropertyImpl$Getter;", "isBound", "", "()Z", "isConst", "isLateinit", "isSuspend", "javaField", "getJavaField", "()Ljava/lang/reflect/Field;", "getName", "()Ljava/lang/String;", "getSignature", "computeDelegateField", "equals", "other", "getDelegate", "field", "receiver", "hashCode", "", "toString", "Accessor", "Companion", "Getter", "Setter", "kotlin-reflection"})
public abstract class KPropertyImpl<V>
extends KCallableImpl<V>
implements KProperty<V> {
    private final ReflectProperties.LazyVal<Field> _javaField;
    private final ReflectProperties.LazySoftVal<PropertyDescriptor> _descriptor;
    @NotNull
    private final KDeclarationContainerImpl container;
    @NotNull
    private final String name;
    @NotNull
    private final String signature;
    private final Object rawBoundReceiver;
    @NotNull
    private static final Object EXTENSION_PROPERTY_DELEGATE;
    public static final Companion Companion;

    @Nullable
    public final Object getBoundReceiver() {
        return InlineClassAwareCallerKt.coerceToExpectedReceiverType(this.rawBoundReceiver, this.getDescriptor());
    }

    @Override
    public boolean isBound() {
        return Intrinsics.areEqual(this.rawBoundReceiver, CallableReference.NO_RECEIVER) ^ true;
    }

    @Nullable
    public final Field getJavaField() {
        return this._javaField.invoke();
    }

    @Nullable
    protected final Field computeDelegateField() {
        return this.getDescriptor().isDelegated() ? this.getJavaField() : null;
    }

    @Nullable
    protected final Object getDelegate(@Nullable Field field, @Nullable Object receiver) {
        Object object;
        try {
            if (receiver == EXTENSION_PROPERTY_DELEGATE && this.getDescriptor().getExtensionReceiverParameter() == null) {
                throw (Throwable)new RuntimeException("" + '\'' + this + "' is not an extension property and thus getExtensionDelegate() " + "is not going to work, use getDelegate() instead");
            }
            Field field2 = field;
            object = field2 != null ? field2.get(receiver) : null;
        }
        catch (IllegalAccessException e) {
            throw (Throwable)new IllegalPropertyDelegateAccessException(e);
        }
        return object;
    }

    @Override
    @NotNull
    public abstract Getter<V> getGetter();

    @Override
    @NotNull
    public PropertyDescriptor getDescriptor() {
        PropertyDescriptor propertyDescriptor = this._descriptor.invoke();
        Intrinsics.checkNotNullExpressionValue(propertyDescriptor, "_descriptor()");
        return propertyDescriptor;
    }

    @Override
    @NotNull
    public Caller<?> getCaller() {
        return this.getGetter().getCaller();
    }

    @Override
    @Nullable
    public Caller<?> getDefaultCaller() {
        return this.getGetter().getDefaultCaller();
    }

    @Override
    public boolean isLateinit() {
        return this.getDescriptor().isLateInit();
    }

    @Override
    public boolean isConst() {
        return this.getDescriptor().isConst();
    }

    @Override
    public boolean isSuspend() {
        return false;
    }

    public boolean equals(@Nullable Object other) {
        KPropertyImpl<?> kPropertyImpl = UtilKt.asKPropertyImpl(other);
        if (kPropertyImpl == null) {
            return false;
        }
        KPropertyImpl<?> that = kPropertyImpl;
        return Intrinsics.areEqual(this.getContainer(), that.getContainer()) && Intrinsics.areEqual(this.getName(), that.getName()) && Intrinsics.areEqual(this.signature, that.signature) && Intrinsics.areEqual(this.rawBoundReceiver, that.rawBoundReceiver);
    }

    public int hashCode() {
        return (this.getContainer().hashCode() * 31 + this.getName().hashCode()) * 31 + this.signature.hashCode();
    }

    @NotNull
    public String toString() {
        return ReflectionObjectRenderer.INSTANCE.renderProperty(this.getDescriptor());
    }

    @Override
    @NotNull
    public KDeclarationContainerImpl getContainer() {
        return this.container;
    }

    @Override
    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public final String getSignature() {
        return this.signature;
    }

    private KPropertyImpl(KDeclarationContainerImpl container, String name, String signature2, PropertyDescriptor descriptorInitialValue, Object rawBoundReceiver) {
        this.container = container;
        this.name = name;
        this.signature = signature2;
        this.rawBoundReceiver = rawBoundReceiver;
        ReflectProperties.LazyVal lazyVal = ReflectProperties.lazy((Function0)new Function0<Field>(this){
            final /* synthetic */ KPropertyImpl this$0;

            @Nullable
            public final Field invoke() {
                Object object;
                JvmPropertySignature jvmSignature = RuntimeTypeMapper.INSTANCE.mapPropertySignature(this.this$0.getDescriptor());
                JvmPropertySignature jvmPropertySignature = jvmSignature;
                if (jvmPropertySignature instanceof JvmPropertySignature.KotlinProperty) {
                    PropertyDescriptor descriptor2 = ((JvmPropertySignature.KotlinProperty)jvmSignature).getDescriptor();
                    JvmMemberSignature.Field field = JvmProtoBufUtil.getJvmFieldSignature$default(JvmProtoBufUtil.INSTANCE, ((JvmPropertySignature.KotlinProperty)jvmSignature).getProto(), ((JvmPropertySignature.KotlinProperty)jvmSignature).getNameResolver(), ((JvmPropertySignature.KotlinProperty)jvmSignature).getTypeTable(), false, 8, null);
                    if (field != null) {
                        Object object2;
                        Class<?> clazz;
                        JvmMemberSignature.Field field2 = field;
                        boolean bl = false;
                        boolean bl2 = false;
                        JvmMemberSignature.Field it = field2;
                        boolean bl3 = false;
                        if (JvmAbi.isPropertyWithBackingFieldInOuterClass(descriptor2) || JvmProtoBufUtil.isMovedFromInterfaceCompanion(((JvmPropertySignature.KotlinProperty)jvmSignature).getProto())) {
                            clazz = this.this$0.getContainer().getJClass().getEnclosingClass();
                        } else {
                            object2 = descriptor2.getContainingDeclaration();
                            boolean bl4 = false;
                            boolean bl5 = false;
                            DeclarationDescriptor containingDeclaration = object2;
                            boolean bl6 = false;
                            clazz = containingDeclaration instanceof ClassDescriptor ? UtilKt.toJavaClass((ClassDescriptor)containingDeclaration) : this.this$0.getContainer().getJClass();
                        }
                        Class<?> owner = clazz;
                        try {
                            Class<?> clazz2 = owner;
                            object2 = clazz2 != null ? clazz2.getDeclaredField(it.getName()) : null;
                        }
                        catch (NoSuchFieldException e) {
                            object2 = null;
                        }
                        object = object2;
                    } else {
                        object = null;
                    }
                } else if (jvmPropertySignature instanceof JvmPropertySignature.JavaField) {
                    object = ((JvmPropertySignature.JavaField)jvmSignature).getField();
                } else if (jvmPropertySignature instanceof JvmPropertySignature.JavaMethodProperty) {
                    object = null;
                } else if (jvmPropertySignature instanceof JvmPropertySignature.MappedKotlinProperty) {
                    object = null;
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                return object;
            }
            {
                this.this$0 = kPropertyImpl;
                super(0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(lazyVal, "ReflectProperties.lazy {\u2026y -> null\n        }\n    }");
        this._javaField = lazyVal;
        ReflectProperties.LazySoftVal<PropertyDescriptor> lazySoftVal = ReflectProperties.lazySoft(descriptorInitialValue, (Function0)new Function0<PropertyDescriptor>(this){
            final /* synthetic */ KPropertyImpl this$0;

            public final PropertyDescriptor invoke() {
                return this.this$0.getContainer().findPropertyDescriptor(this.this$0.getName(), this.this$0.getSignature());
            }
            {
                this.this$0 = kPropertyImpl;
                super(0);
            }
        });
        Intrinsics.checkNotNullExpressionValue(lazySoftVal, "ReflectProperties.lazySo\u2026or(name, signature)\n    }");
        this._descriptor = lazySoftVal;
    }

    public KPropertyImpl(@NotNull KDeclarationContainerImpl container, @NotNull String name, @NotNull String signature2, @Nullable Object boundReceiver) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(signature2, "signature");
        this(container, name, signature2, null, boundReceiver);
    }

    public KPropertyImpl(@NotNull KDeclarationContainerImpl container, @NotNull PropertyDescriptor descriptor2) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
        String string = descriptor2.getName().asString();
        Intrinsics.checkNotNullExpressionValue(string, "descriptor.name.asString()");
        this(container, string, RuntimeTypeMapper.INSTANCE.mapPropertySignature(descriptor2).asString(), descriptor2, CallableReference.NO_RECEIVER);
    }

    static {
        Companion = new Companion(null);
        EXTENSION_PROPERTY_DELEGATE = new Object();
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u0001*\u0006\b\u0002\u0010\u0002 \u00012\b\u0012\u0004\u0012\u0002H\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00010\u00042\b\u0012\u0004\u0012\u0002H\u00020\u0005B\u0005\u00a2\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\u0010X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0015R\u0014\u0010\u0017\u001a\u00020\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0015R\u0014\u0010\u0018\u001a\u00020\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0015R\u0014\u0010\u0019\u001a\u00020\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u0015R\u0014\u0010\u001a\u001a\u00020\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u0015R\u0018\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00010\u001cX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006\u001f"}, d2={"Lkotlin/reflect/jvm/internal/KPropertyImpl$Accessor;", "PropertyType", "ReturnType", "Lkotlin/reflect/jvm/internal/KCallableImpl;", "Lkotlin/reflect/KProperty$Accessor;", "Lkotlin/reflect/KFunction;", "()V", "container", "Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "getContainer", "()Lkotlin/reflect/jvm/internal/KDeclarationContainerImpl;", "defaultCaller", "Lkotlin/reflect/jvm/internal/calls/Caller;", "getDefaultCaller", "()Lkotlin/reflect/jvm/internal/calls/Caller;", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyAccessorDescriptor;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "isBound", "", "()Z", "isExternal", "isInfix", "isInline", "isOperator", "isSuspend", "property", "Lkotlin/reflect/jvm/internal/KPropertyImpl;", "getProperty", "()Lkotlin/reflect/jvm/internal/KPropertyImpl;", "kotlin-reflection"})
    public static abstract class Accessor<PropertyType, ReturnType>
    extends KCallableImpl<ReturnType>
    implements KFunction<ReturnType>,
    KProperty.Accessor<PropertyType> {
        @Override
        @NotNull
        public abstract KPropertyImpl<PropertyType> getProperty();

        @Override
        @NotNull
        public abstract PropertyAccessorDescriptor getDescriptor();

        @Override
        @NotNull
        public KDeclarationContainerImpl getContainer() {
            return this.getProperty().getContainer();
        }

        @Override
        @Nullable
        public Caller<?> getDefaultCaller() {
            return null;
        }

        @Override
        public boolean isBound() {
            return this.getProperty().isBound();
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
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\b&\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004R\u001f\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00068VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\u000b\u001a\u00020\f8VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0015"}, d2={"Lkotlin/reflect/jvm/internal/KPropertyImpl$Getter;", "V", "Lkotlin/reflect/jvm/internal/KPropertyImpl$Accessor;", "Lkotlin/reflect/KProperty$Getter;", "()V", "caller", "Lkotlin/reflect/jvm/internal/calls/Caller;", "getCaller", "()Lkotlin/reflect/jvm/internal/calls/Caller;", "caller$delegate", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazyVal;", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyGetterDescriptor;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "descriptor$delegate", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazySoftVal;", "name", "", "getName", "()Ljava/lang/String;", "kotlin-reflection"})
    public static abstract class Getter<V>
    extends Accessor<V, V>
    implements KProperty.Getter<V> {
        static final /* synthetic */ KProperty[] $$delegatedProperties;
        @NotNull
        private final ReflectProperties.LazySoftVal descriptor$delegate = ReflectProperties.lazySoft((Function0)new Function0<PropertyGetterDescriptor>(this){
            final /* synthetic */ Getter this$0;

            public final PropertyGetterDescriptor invoke() {
                PropertyGetterDescriptor propertyGetterDescriptor = this.this$0.getProperty().getDescriptor().getGetter();
                if (propertyGetterDescriptor == null) {
                    propertyGetterDescriptor = DescriptorFactory.createDefaultGetter(this.this$0.getProperty().getDescriptor(), Annotations.Companion.getEMPTY());
                }
                return propertyGetterDescriptor;
            }
            {
                this.this$0 = getter;
                super(0);
            }
        });
        @NotNull
        private final ReflectProperties.LazyVal caller$delegate = ReflectProperties.lazy(new Function0<Caller<?>>(this){
            final /* synthetic */ Getter this$0;

            public final Caller<?> invoke() {
                return KPropertyImplKt.access$computeCallerForAccessor(this.this$0, true);
            }
            {
                this.this$0 = getter;
                super(0);
            }
        });

        static {
            $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Getter.class), "descriptor", "getDescriptor()Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Getter.class), "caller", "getCaller()Lkotlin/reflect/jvm/internal/calls/Caller;"))};
        }

        @Override
        @NotNull
        public String getName() {
            return "<get-" + this.getProperty().getName() + '>';
        }

        @Override
        @NotNull
        public PropertyGetterDescriptor getDescriptor() {
            return (PropertyGetterDescriptor)this.descriptor$delegate.getValue(this, $$delegatedProperties[0]);
        }

        @Override
        @NotNull
        public Caller<?> getCaller() {
            return (Caller)this.caller$delegate.getValue(this, $$delegatedProperties[1]);
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\b&\u0018\u0000*\u0004\b\u0001\u0010\u00012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00030\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0004B\u0005\u00a2\u0006\u0002\u0010\u0005R\u001f\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u00078VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001b\u0010\f\u001a\u00020\r8VX\u0096\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0016"}, d2={"Lkotlin/reflect/jvm/internal/KPropertyImpl$Setter;", "V", "Lkotlin/reflect/jvm/internal/KPropertyImpl$Accessor;", "", "Lkotlin/reflect/KMutableProperty$Setter;", "()V", "caller", "Lkotlin/reflect/jvm/internal/calls/Caller;", "getCaller", "()Lkotlin/reflect/jvm/internal/calls/Caller;", "caller$delegate", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazyVal;", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertySetterDescriptor;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "descriptor$delegate", "Lkotlin/reflect/jvm/internal/ReflectProperties$LazySoftVal;", "name", "", "getName", "()Ljava/lang/String;", "kotlin-reflection"})
    public static abstract class Setter<V>
    extends Accessor<V, Unit>
    implements KMutableProperty.Setter<V> {
        static final /* synthetic */ KProperty[] $$delegatedProperties;
        @NotNull
        private final ReflectProperties.LazySoftVal descriptor$delegate = ReflectProperties.lazySoft((Function0)new Function0<PropertySetterDescriptor>(this){
            final /* synthetic */ Setter this$0;

            public final PropertySetterDescriptor invoke() {
                PropertySetterDescriptor propertySetterDescriptor = this.this$0.getProperty().getDescriptor().getSetter();
                if (propertySetterDescriptor == null) {
                    propertySetterDescriptor = DescriptorFactory.createDefaultSetter(this.this$0.getProperty().getDescriptor(), Annotations.Companion.getEMPTY(), Annotations.Companion.getEMPTY());
                }
                return propertySetterDescriptor;
            }
            {
                this.this$0 = setter;
                super(0);
            }
        });
        @NotNull
        private final ReflectProperties.LazyVal caller$delegate = ReflectProperties.lazy(new Function0<Caller<?>>(this){
            final /* synthetic */ Setter this$0;

            public final Caller<?> invoke() {
                return KPropertyImplKt.access$computeCallerForAccessor(this.this$0, false);
            }
            {
                this.this$0 = setter;
                super(0);
            }
        });

        static {
            $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Setter.class), "descriptor", "getDescriptor()Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Setter.class), "caller", "getCaller()Lkotlin/reflect/jvm/internal/calls/Caller;"))};
        }

        @Override
        @NotNull
        public String getName() {
            return "<set-" + this.getProperty().getName() + '>';
        }

        @Override
        @NotNull
        public PropertySetterDescriptor getDescriptor() {
            return (PropertySetterDescriptor)this.descriptor$delegate.getValue(this, $$delegatedProperties[0]);
        }

        @Override
        @NotNull
        public Caller<?> getCaller() {
            return (Caller)this.caller$delegate.getValue(this, $$delegatedProperties[1]);
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lkotlin/reflect/jvm/internal/KPropertyImpl$Companion;", "", "()V", "EXTENSION_PROPERTY_DELEGATE", "getEXTENSION_PROPERTY_DELEGATE", "()Ljava/lang/Object;", "kotlin-reflection"})
    public static final class Companion {
        @NotNull
        public final Object getEXTENSION_PROPERTY_DELEGATE() {
            return EXTENSION_PROPERTY_DELEGATE;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

