/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal;

import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin._Assertions;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.JvmFunctionSignature;
import kotlin.reflect.jvm.internal.JvmPropertySignature;
import kotlin.reflect.jvm.internal.KDeclarationContainerImpl;
import kotlin.reflect.jvm.internal.KPropertyImpl;
import kotlin.reflect.jvm.internal.KPropertyImplKt;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.RuntimeTypeMapper;
import kotlin.reflect.jvm.internal.UtilKt;
import kotlin.reflect.jvm.internal.calls.Caller;
import kotlin.reflect.jvm.internal.calls.CallerImpl;
import kotlin.reflect.jvm.internal.calls.InlineClassAwareCallerKt;
import kotlin.reflect.jvm.internal.calls.InternalUnderlyingValOfInlineClass;
import kotlin.reflect.jvm.internal.calls.ThrowingCaller;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u0000 \n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006*\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00022\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\f\u0010\t\u001a\u00020\b*\u00020\nH\u0002\"\"\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00028@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\u000b"}, d2={"boundReceiver", "", "Lkotlin/reflect/jvm/internal/KPropertyImpl$Accessor;", "getBoundReceiver", "(Lkotlin/reflect/jvm/internal/KPropertyImpl$Accessor;)Ljava/lang/Object;", "computeCallerForAccessor", "Lkotlin/reflect/jvm/internal/calls/Caller;", "isGetter", "", "isJvmFieldPropertyInCompanionObject", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyDescriptor;", "kotlin-reflection"})
public final class KPropertyImplKt {
    @Nullable
    public static final Object getBoundReceiver(@NotNull KPropertyImpl.Accessor<?, ?> $this$boundReceiver) {
        Intrinsics.checkNotNullParameter($this$boundReceiver, "$this$boundReceiver");
        return $this$boundReceiver.getProperty().getBoundReceiver();
    }

    private static final Caller<?> computeCallerForAccessor(KPropertyImpl.Accessor<?, ?> $this$computeCallerForAccessor, boolean isGetter) {
        Caller caller2;
        if (KDeclarationContainerImpl.Companion.getLOCAL_PROPERTY_SIGNATURE$kotlin_reflection().matches($this$computeCallerForAccessor.getProperty().getSignature())) {
            return ThrowingCaller.INSTANCE;
        }
        Function0<Boolean> $fun$isJvmStaticProperty$1 = new Function0<Boolean>($this$computeCallerForAccessor){
            final /* synthetic */ KPropertyImpl.Accessor $this_computeCallerForAccessor;

            public final boolean invoke() {
                return this.$this_computeCallerForAccessor.getProperty().getDescriptor().getAnnotations().hasAnnotation(UtilKt.getJVM_STATIC());
            }
            {
                this.$this_computeCallerForAccessor = accessor;
                super(0);
            }
        };
        Function0<Boolean> $fun$isNotNullProperty$2 = new Function0<Boolean>($this$computeCallerForAccessor){
            final /* synthetic */ KPropertyImpl.Accessor $this_computeCallerForAccessor;

            public final boolean invoke() {
                return !TypeUtils.isNullableType(this.$this_computeCallerForAccessor.getProperty().getDescriptor().getType());
            }
            {
                this.$this_computeCallerForAccessor = accessor;
                super(0);
            }
        };
        Function1<Field, CallerImpl<? extends Field>> $fun$computeFieldCaller$3 = new Function1<Field, CallerImpl<? extends Field>>($this$computeCallerForAccessor, isGetter, $fun$isNotNullProperty$2, $fun$isJvmStaticProperty$1){
            final /* synthetic */ KPropertyImpl.Accessor $this_computeCallerForAccessor;
            final /* synthetic */ boolean $isGetter;
            final /* synthetic */ computeCallerForAccessor.2 $isNotNullProperty$2;
            final /* synthetic */ computeCallerForAccessor.1 $isJvmStaticProperty$1;

            @NotNull
            public final CallerImpl<Field> invoke(@NotNull Field field) {
                Intrinsics.checkNotNullParameter(field, "field");
                return KPropertyImplKt.access$isJvmFieldPropertyInCompanionObject(this.$this_computeCallerForAccessor.getProperty().getDescriptor()) || !Modifier.isStatic(field.getModifiers()) ? (this.$isGetter ? (CallerImpl)(this.$this_computeCallerForAccessor.isBound() ? (CallerImpl.FieldGetter)new CallerImpl.FieldGetter.BoundInstance(field, KPropertyImplKt.getBoundReceiver(this.$this_computeCallerForAccessor)) : (CallerImpl.FieldGetter)new CallerImpl.FieldGetter.Instance(field)) : (CallerImpl)(this.$this_computeCallerForAccessor.isBound() ? (CallerImpl.FieldSetter)new CallerImpl.FieldSetter.BoundInstance(field, this.$isNotNullProperty$2.invoke(), KPropertyImplKt.getBoundReceiver(this.$this_computeCallerForAccessor)) : (CallerImpl.FieldSetter)new CallerImpl.FieldSetter.Instance(field, this.$isNotNullProperty$2.invoke()))) : (this.$isJvmStaticProperty$1.invoke() ? (this.$isGetter ? (CallerImpl)(this.$this_computeCallerForAccessor.isBound() ? (CallerImpl.FieldGetter)new CallerImpl.FieldGetter.BoundJvmStaticInObject(field) : (CallerImpl.FieldGetter)new CallerImpl.FieldGetter.JvmStaticInObject(field)) : (CallerImpl)(this.$this_computeCallerForAccessor.isBound() ? (CallerImpl.FieldSetter)new CallerImpl.FieldSetter.BoundJvmStaticInObject(field, this.$isNotNullProperty$2.invoke()) : (CallerImpl.FieldSetter)new CallerImpl.FieldSetter.JvmStaticInObject(field, this.$isNotNullProperty$2.invoke()))) : (this.$isGetter ? (CallerImpl)new CallerImpl.FieldGetter.Static(field) : (CallerImpl)new CallerImpl.FieldSetter.Static(field, this.$isNotNullProperty$2.invoke())));
            }
            {
                this.$this_computeCallerForAccessor = accessor;
                this.$isGetter = bl;
                this.$isNotNullProperty$2 = var3_3;
                this.$isJvmStaticProperty$1 = var4_4;
                super(1);
            }
        };
        JvmPropertySignature jvmSignature = RuntimeTypeMapper.INSTANCE.mapPropertySignature($this$computeCallerForAccessor.getProperty().getDescriptor());
        JvmPropertySignature jvmPropertySignature = jvmSignature;
        if (jvmPropertySignature instanceof JvmPropertySignature.KotlinProperty) {
            Method method;
            JvmProtoBuf.JvmMethodSignature accessorSignature;
            JvmProtoBuf.JvmPropertySignature jvmPropertySignature2 = ((JvmPropertySignature.KotlinProperty)jvmSignature).getSignature();
            boolean bl = false;
            boolean bl2 = false;
            JvmProtoBuf.JvmPropertySignature $this$run2 = jvmPropertySignature2;
            boolean bl3 = false;
            JvmProtoBuf.JvmMethodSignature jvmMethodSignature = accessorSignature = isGetter ? ($this$run2.hasGetter() ? $this$run2.getGetter() : null) : ($this$run2.hasSetter() ? $this$run2.getSetter() : null);
            if (jvmMethodSignature != null) {
                JvmProtoBuf.JvmMethodSignature jvmMethodSignature2 = jvmMethodSignature;
                bl2 = false;
                boolean $this$run2 = false;
                JvmProtoBuf.JvmMethodSignature signature2 = jvmMethodSignature2;
                boolean bl4 = false;
                method = $this$computeCallerForAccessor.getProperty().getContainer().findMethodBySignature(((JvmPropertySignature.KotlinProperty)jvmSignature).getNameResolver().getString(signature2.getName()), ((JvmPropertySignature.KotlinProperty)jvmSignature).getNameResolver().getString(signature2.getDesc()));
            } else {
                method = null;
            }
            Method accessor = method;
            if (accessor == null) {
                if (InlineClassesUtilsKt.isUnderlyingPropertyOfInlineClass($this$computeCallerForAccessor.getProperty().getDescriptor()) && Intrinsics.areEqual($this$computeCallerForAccessor.getProperty().getDescriptor().getVisibility(), Visibilities.INTERNAL)) {
                    GenericDeclaration genericDeclaration = InlineClassAwareCallerKt.toInlineClass($this$computeCallerForAccessor.getProperty().getDescriptor().getContainingDeclaration());
                    if (genericDeclaration == null || (genericDeclaration = InlineClassAwareCallerKt.getUnboxMethod(genericDeclaration, $this$computeCallerForAccessor.getProperty().getDescriptor())) == null) {
                        throw (Throwable)new KotlinReflectionInternalError("Underlying property of inline class " + $this$computeCallerForAccessor.getProperty() + " should have a field");
                    }
                    GenericDeclaration unboxMethod = genericDeclaration;
                    caller2 = $this$computeCallerForAccessor.isBound() ? (InternalUnderlyingValOfInlineClass)new InternalUnderlyingValOfInlineClass.Bound((Method)unboxMethod, KPropertyImplKt.getBoundReceiver($this$computeCallerForAccessor)) : (InternalUnderlyingValOfInlineClass)new InternalUnderlyingValOfInlineClass.Unbound((Method)unboxMethod);
                } else {
                    Field field = $this$computeCallerForAccessor.getProperty().getJavaField();
                    if (field == null) {
                        throw (Throwable)new KotlinReflectionInternalError("No accessors or field is found for property " + $this$computeCallerForAccessor.getProperty());
                    }
                    Field javaField = field;
                    caller2 = $fun$computeFieldCaller$3.invoke(javaField);
                }
            } else {
                caller2 = !Modifier.isStatic(accessor.getModifiers()) ? (Caller)($this$computeCallerForAccessor.isBound() ? (CallerImpl.Method)new CallerImpl.Method.BoundInstance(accessor, KPropertyImplKt.getBoundReceiver($this$computeCallerForAccessor)) : (CallerImpl.Method)new CallerImpl.Method.Instance(accessor)) : ($fun$isJvmStaticProperty$1.invoke() ? (Caller)($this$computeCallerForAccessor.isBound() ? (CallerImpl.Method)new CallerImpl.Method.BoundJvmStaticInObject(accessor) : (CallerImpl.Method)new CallerImpl.Method.JvmStaticInObject(accessor)) : (Caller)($this$computeCallerForAccessor.isBound() ? (CallerImpl.Method)new CallerImpl.Method.BoundStatic(accessor, KPropertyImplKt.getBoundReceiver($this$computeCallerForAccessor)) : (CallerImpl.Method)new CallerImpl.Method.Static(accessor)));
            }
        } else if (jvmPropertySignature instanceof JvmPropertySignature.JavaField) {
            caller2 = $fun$computeFieldCaller$3.invoke(((JvmPropertySignature.JavaField)jvmSignature).getField());
        } else if (jvmPropertySignature instanceof JvmPropertySignature.JavaMethodProperty) {
            Method method;
            if (isGetter) {
                method = ((JvmPropertySignature.JavaMethodProperty)jvmSignature).getGetterMethod();
            } else {
                method = ((JvmPropertySignature.JavaMethodProperty)jvmSignature).getSetterMethod();
                if (method == null) {
                    throw (Throwable)new KotlinReflectionInternalError("No source found for setter of Java method property: " + ((JvmPropertySignature.JavaMethodProperty)jvmSignature).getGetterMethod());
                }
            }
            Method method2 = method;
            caller2 = $this$computeCallerForAccessor.isBound() ? (CallerImpl.Method)new CallerImpl.Method.BoundInstance(method2, KPropertyImplKt.getBoundReceiver($this$computeCallerForAccessor)) : (CallerImpl.Method)new CallerImpl.Method.Instance(method2);
        } else {
            if (jvmPropertySignature instanceof JvmPropertySignature.MappedKotlinProperty) {
                JvmFunctionSignature.KotlinFunction kotlinFunction;
                if (isGetter) {
                    kotlinFunction = ((JvmPropertySignature.MappedKotlinProperty)jvmSignature).getGetterSignature();
                } else {
                    kotlinFunction = ((JvmPropertySignature.MappedKotlinProperty)jvmSignature).getSetterSignature();
                    if (kotlinFunction == null) {
                        throw (Throwable)new KotlinReflectionInternalError("No setter found for property " + $this$computeCallerForAccessor.getProperty());
                    }
                }
                JvmFunctionSignature.KotlinFunction signature3 = kotlinFunction;
                Method method = $this$computeCallerForAccessor.getProperty().getContainer().findMethodBySignature(signature3.getMethodName(), signature3.getMethodDesc());
                if (method == null) {
                    throw (Throwable)new KotlinReflectionInternalError("No accessor found for property " + $this$computeCallerForAccessor.getProperty());
                }
                Method accessor = method;
                boolean bl = !Modifier.isStatic(accessor.getModifiers());
                boolean bl5 = false;
                if (_Assertions.ENABLED && !bl) {
                    boolean bl6 = false;
                    String string = "Mapped property cannot have a static accessor: " + $this$computeCallerForAccessor.getProperty();
                    throw (Throwable)((Object)new AssertionError((Object)string));
                }
                return $this$computeCallerForAccessor.isBound() ? (CallerImpl.Method)new CallerImpl.Method.BoundInstance(accessor, KPropertyImplKt.getBoundReceiver($this$computeCallerForAccessor)) : (CallerImpl.Method)new CallerImpl.Method.Instance(accessor);
            }
            throw new NoWhenBranchMatchedException();
        }
        return InlineClassAwareCallerKt.createInlineClassAwareCallerIfNeeded$default(caller2, $this$computeCallerForAccessor.getDescriptor(), false, 2, null);
    }

    private static final boolean isJvmFieldPropertyInCompanionObject(PropertyDescriptor $this$isJvmFieldPropertyInCompanionObject) {
        DeclarationDescriptor declarationDescriptor = $this$isJvmFieldPropertyInCompanionObject.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "containingDeclaration");
        DeclarationDescriptor container = declarationDescriptor;
        if (!DescriptorUtils.isCompanionObject(container)) {
            return false;
        }
        DeclarationDescriptor outerClass = container.getContainingDeclaration();
        return DescriptorUtils.isInterface(outerClass) || DescriptorUtils.isAnnotationClass(outerClass) ? $this$isJvmFieldPropertyInCompanionObject instanceof DeserializedPropertyDescriptor && JvmProtoBufUtil.isMovedFromInterfaceCompanion(((DeserializedPropertyDescriptor)$this$isJvmFieldPropertyInCompanionObject).getProto()) : true;
    }

    public static final /* synthetic */ Caller access$computeCallerForAccessor(KPropertyImpl.Accessor $this$access_u24computeCallerForAccessor, boolean isGetter) {
        return KPropertyImplKt.computeCallerForAccessor($this$access_u24computeCallerForAccessor, isGetter);
    }

    public static final /* synthetic */ boolean access$isJvmFieldPropertyInCompanionObject(PropertyDescriptor $this$access_u24isJvmFieldPropertyInCompanionObject) {
        return KPropertyImplKt.isJvmFieldPropertyInCompanionObject($this$access_u24isJvmFieldPropertyInCompanionObject);
    }
}

