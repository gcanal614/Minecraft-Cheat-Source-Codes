/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.JvmFunctionSignature;
import kotlin.reflect.jvm.internal.JvmPropertySignature;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.CloneableClassScope;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaClass;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaConstructor;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaField;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaMethod;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAbi;
import kotlin.reflect.jvm.internal.impl.load.java.SpecialBuiltinMembers;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaClassConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.sources.JavaSourceElement;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaElement;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoBufUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedCallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedPropertyDescriptor;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c0\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0012\u0010\u000e\u001a\u00020\u00042\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0007J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\f\u001a\u00020\u0014H\u0002J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u0006\u0012\u0002\b\u00030\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\t\u00a8\u0006\u001c"}, d2={"Lkotlin/reflect/jvm/internal/RuntimeTypeMapper;", "", "()V", "JAVA_LANG_VOID", "Lkotlin/reflect/jvm/internal/impl/name/ClassId;", "primitiveType", "Lkotlin/reflect/jvm/internal/impl/builtins/PrimitiveType;", "Ljava/lang/Class;", "getPrimitiveType", "(Ljava/lang/Class;)Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "isKnownBuiltInFunction", "", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/FunctionDescriptor;", "mapJvmClassToKotlinClassId", "klass", "mapJvmFunctionSignature", "Lkotlin/reflect/jvm/internal/JvmFunctionSignature$KotlinFunction;", "mapName", "", "Lkotlin/reflect/jvm/internal/impl/descriptors/CallableMemberDescriptor;", "mapPropertySignature", "Lkotlin/reflect/jvm/internal/JvmPropertySignature;", "possiblyOverriddenProperty", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyDescriptor;", "mapSignature", "Lkotlin/reflect/jvm/internal/JvmFunctionSignature;", "possiblySubstitutedFunction", "kotlin-reflection"})
public final class RuntimeTypeMapper {
    private static final ClassId JAVA_LANG_VOID;
    public static final RuntimeTypeMapper INSTANCE;

    @NotNull
    public final JvmFunctionSignature mapSignature(@NotNull FunctionDescriptor possiblySubstitutedFunction) {
        FunctionDescriptor function;
        Intrinsics.checkNotNullParameter(possiblySubstitutedFunction, "possiblySubstitutedFunction");
        CallableMemberDescriptor callableMemberDescriptor = DescriptorUtils.unwrapFakeOverride((CallableMemberDescriptor)possiblySubstitutedFunction);
        Intrinsics.checkNotNullExpressionValue(callableMemberDescriptor, "DescriptorUtils.unwrapFa\u2026siblySubstitutedFunction)");
        FunctionDescriptor functionDescriptor = ((FunctionDescriptor)callableMemberDescriptor).getOriginal();
        Intrinsics.checkNotNullExpressionValue(functionDescriptor, "DescriptorUtils.unwrapFa\u2026titutedFunction).original");
        FunctionDescriptor functionDescriptor2 = function = functionDescriptor;
        if (functionDescriptor2 instanceof DeserializedCallableMemberDescriptor) {
            MessageLite proto = ((DeserializedCallableMemberDescriptor)((Object)function)).getProto();
            if (proto instanceof ProtoBuf.Function) {
                JvmMemberSignature.Method method = JvmProtoBufUtil.INSTANCE.getJvmMethodSignature((ProtoBuf.Function)proto, ((DeserializedCallableMemberDescriptor)((Object)function)).getNameResolver(), ((DeserializedCallableMemberDescriptor)((Object)function)).getTypeTable());
                if (method != null) {
                    JvmMemberSignature.Method method2 = method;
                    boolean bl = false;
                    boolean bl2 = false;
                    JvmMemberSignature.Method signature2 = method2;
                    boolean bl3 = false;
                    return new JvmFunctionSignature.KotlinFunction(signature2);
                }
            }
            if (proto instanceof ProtoBuf.Constructor) {
                JvmMemberSignature.Method method = JvmProtoBufUtil.INSTANCE.getJvmConstructorSignature((ProtoBuf.Constructor)proto, ((DeserializedCallableMemberDescriptor)((Object)function)).getNameResolver(), ((DeserializedCallableMemberDescriptor)((Object)function)).getTypeTable());
                if (method != null) {
                    JvmMemberSignature.Method method3 = method;
                    boolean bl = false;
                    boolean bl4 = false;
                    JvmMemberSignature.Method signature3 = method3;
                    boolean bl5 = false;
                    DeclarationDescriptor declarationDescriptor = possiblySubstitutedFunction.getContainingDeclaration();
                    Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "possiblySubstitutedFunction.containingDeclaration");
                    return InlineClassesUtilsKt.isInlineClass(declarationDescriptor) ? (JvmFunctionSignature)new JvmFunctionSignature.KotlinFunction(signature3) : (JvmFunctionSignature)new JvmFunctionSignature.KotlinConstructor(signature3);
                }
            }
            return this.mapJvmFunctionSignature(function);
        }
        if (functionDescriptor2 instanceof JavaMethodDescriptor) {
            Object object;
            SourceElement sourceElement = ((JavaMethodDescriptor)function).getSource();
            if (!(sourceElement instanceof JavaSourceElement)) {
                sourceElement = null;
            }
            JavaSourceElement javaSourceElement = (JavaSourceElement)sourceElement;
            JavaElement javaElement = javaSourceElement != null ? javaSourceElement.getJavaElement() : null;
            if (!(javaElement instanceof ReflectJavaMethod)) {
                javaElement = null;
            }
            if ((object = (ReflectJavaMethod)javaElement) == null || (object = ((ReflectJavaMethod)object).getMember()) == null) {
                throw (Throwable)new KotlinReflectionInternalError("Incorrect resolution sequence for Java method " + function);
            }
            Object method = object;
            return new JvmFunctionSignature.JavaMethod((Method)method);
        }
        if (functionDescriptor2 instanceof JavaClassConstructorDescriptor) {
            JvmFunctionSignature jvmFunctionSignature;
            SourceElement sourceElement = ((JavaClassConstructorDescriptor)function).getSource();
            if (!(sourceElement instanceof JavaSourceElement)) {
                sourceElement = null;
            }
            JavaSourceElement javaSourceElement = (JavaSourceElement)sourceElement;
            JavaElement element = javaSourceElement != null ? javaSourceElement.getJavaElement() : null;
            if (element instanceof ReflectJavaConstructor) {
                jvmFunctionSignature = new JvmFunctionSignature.JavaConstructor((Constructor<?>)((ReflectJavaConstructor)element).getMember());
            } else if (element instanceof ReflectJavaClass && ((ReflectJavaClass)element).isAnnotationType()) {
                jvmFunctionSignature = new JvmFunctionSignature.FakeJavaAnnotationConstructor((Class<?>)((ReflectJavaClass)element).getElement());
            } else {
                throw (Throwable)new KotlinReflectionInternalError("Incorrect resolution sequence for Java constructor " + function + " (" + element + ')');
            }
            return jvmFunctionSignature;
        }
        if (this.isKnownBuiltInFunction(function)) {
            return this.mapJvmFunctionSignature(function);
        }
        throw (Throwable)new KotlinReflectionInternalError("Unknown origin of " + function + " (" + function.getClass() + ')');
    }

    @NotNull
    public final JvmPropertySignature mapPropertySignature(@NotNull PropertyDescriptor possiblyOverriddenProperty) {
        JvmFunctionSignature.KotlinFunction kotlinFunction;
        JvmFunctionSignature.KotlinFunction kotlinFunction2;
        PropertyDescriptor property;
        Intrinsics.checkNotNullParameter(possiblyOverriddenProperty, "possiblyOverriddenProperty");
        CallableMemberDescriptor callableMemberDescriptor = DescriptorUtils.unwrapFakeOverride((CallableMemberDescriptor)possiblyOverriddenProperty);
        Intrinsics.checkNotNullExpressionValue(callableMemberDescriptor, "DescriptorUtils.unwrapFa\u2026ssiblyOverriddenProperty)");
        PropertyDescriptor propertyDescriptor = ((PropertyDescriptor)callableMemberDescriptor).getOriginal();
        Intrinsics.checkNotNullExpressionValue(propertyDescriptor, "DescriptorUtils.unwrapFa\u2026rriddenProperty).original");
        CallableMemberDescriptor callableMemberDescriptor2 = property = propertyDescriptor;
        if (callableMemberDescriptor2 instanceof DeserializedPropertyDescriptor) {
            ProtoBuf.Property proto = ((DeserializedPropertyDescriptor)property).getProto();
            GeneratedMessageLite.ExtendableMessage extendableMessage = proto;
            GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, JvmProtoBuf.JvmPropertySignature> generatedExtension = JvmProtoBuf.propertySignature;
            Intrinsics.checkNotNullExpressionValue(generatedExtension, "JvmProtoBuf.propertySignature");
            JvmProtoBuf.JvmPropertySignature signature2 = ProtoBufUtilKt.getExtensionOrNull(extendableMessage, generatedExtension);
            if (signature2 != null) {
                return new JvmPropertySignature.KotlinProperty(property, proto, signature2, ((DeserializedPropertyDescriptor)property).getNameResolver(), ((DeserializedPropertyDescriptor)property).getTypeTable());
            }
        } else if (callableMemberDescriptor2 instanceof JavaPropertyDescriptor) {
            JvmPropertySignature jvmPropertySignature;
            SourceElement sourceElement = ((JavaPropertyDescriptor)property).getSource();
            if (!(sourceElement instanceof JavaSourceElement)) {
                sourceElement = null;
            }
            JavaSourceElement javaSourceElement = (JavaSourceElement)sourceElement;
            JavaElement element = javaSourceElement != null ? javaSourceElement.getJavaElement() : null;
            JavaElement javaElement = element;
            if (javaElement instanceof ReflectJavaField) {
                jvmPropertySignature = new JvmPropertySignature.JavaField(((ReflectJavaField)element).getMember());
            } else if (javaElement instanceof ReflectJavaMethod) {
                Method method = ((ReflectJavaMethod)element).getMember();
                PropertySetterDescriptor propertySetterDescriptor = property.getSetter();
                SourceElement sourceElement2 = propertySetterDescriptor != null ? propertySetterDescriptor.getSource() : null;
                if (!(sourceElement2 instanceof JavaSourceElement)) {
                    sourceElement2 = null;
                }
                JavaSourceElement javaSourceElement2 = (JavaSourceElement)sourceElement2;
                JavaElement javaElement2 = javaSourceElement2 != null ? javaSourceElement2.getJavaElement() : null;
                if (!(javaElement2 instanceof ReflectJavaMethod)) {
                    javaElement2 = null;
                }
                ReflectJavaMethod reflectJavaMethod = (ReflectJavaMethod)javaElement2;
                jvmPropertySignature = new JvmPropertySignature.JavaMethodProperty(method, reflectJavaMethod != null ? reflectJavaMethod.getMember() : null);
            } else {
                throw (Throwable)new KotlinReflectionInternalError("Incorrect resolution sequence for Java field " + property + " (source = " + element + ')');
            }
            return jvmPropertySignature;
        }
        PropertyGetterDescriptor propertyGetterDescriptor = property.getGetter();
        Intrinsics.checkNotNull(propertyGetterDescriptor);
        callableMemberDescriptor2 = propertyGetterDescriptor;
        RuntimeTypeMapper runtimeTypeMapper = this;
        boolean bl = false;
        boolean bl2 = false;
        FunctionDescriptor p1 = (FunctionDescriptor)callableMemberDescriptor2;
        boolean bl3 = false;
        JvmFunctionSignature.KotlinFunction kotlinFunction3 = kotlinFunction2 = runtimeTypeMapper.mapJvmFunctionSignature(p1);
        PropertySetterDescriptor propertySetterDescriptor = property.getSetter();
        if (propertySetterDescriptor != null) {
            callableMemberDescriptor2 = propertySetterDescriptor;
            runtimeTypeMapper = this;
            bl = false;
            bl2 = false;
            p1 = (FunctionDescriptor)callableMemberDescriptor2;
            kotlinFunction2 = kotlinFunction3;
            boolean bl4 = false;
            JvmFunctionSignature.KotlinFunction kotlinFunction4 = runtimeTypeMapper.mapJvmFunctionSignature(p1);
            kotlinFunction3 = kotlinFunction2;
            kotlinFunction = kotlinFunction4;
        } else {
            kotlinFunction = null;
        }
        JvmFunctionSignature.KotlinFunction kotlinFunction5 = kotlinFunction;
        JvmFunctionSignature.KotlinFunction kotlinFunction6 = kotlinFunction3;
        return new JvmPropertySignature.MappedKotlinProperty(kotlinFunction6, kotlinFunction5);
    }

    private final boolean isKnownBuiltInFunction(FunctionDescriptor descriptor2) {
        if (DescriptorFactory.isEnumValueOfMethod(descriptor2) || DescriptorFactory.isEnumValuesMethod(descriptor2)) {
            return true;
        }
        return Intrinsics.areEqual(descriptor2.getName(), CloneableClassScope.Companion.getCLONE_NAME()) && descriptor2.getValueParameters().isEmpty();
    }

    private final JvmFunctionSignature.KotlinFunction mapJvmFunctionSignature(FunctionDescriptor descriptor2) {
        return new JvmFunctionSignature.KotlinFunction(new JvmMemberSignature.Method(this.mapName(descriptor2), MethodSignatureMappingKt.computeJvmDescriptor$default(descriptor2, false, false, 1, null)));
    }

    private final String mapName(CallableMemberDescriptor descriptor2) {
        String string = SpecialBuiltinMembers.getJvmMethodNameIfSpecial(descriptor2);
        if (string == null) {
            CallableMemberDescriptor callableMemberDescriptor = descriptor2;
            String string2 = callableMemberDescriptor instanceof PropertyGetterDescriptor ? JvmAbi.getterName(DescriptorUtilsKt.getPropertyIfAccessor(descriptor2).getName().asString()) : (callableMemberDescriptor instanceof PropertySetterDescriptor ? JvmAbi.setterName(DescriptorUtilsKt.getPropertyIfAccessor(descriptor2).getName().asString()) : descriptor2.getName().asString());
            string = string2;
            Intrinsics.checkNotNullExpressionValue(string2, "when (descriptor) {\n    \u2026name.asString()\n        }");
        }
        return string;
    }

    @NotNull
    public final ClassId mapJvmClassToKotlinClassId(@NotNull Class<?> klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        if (klass.isArray()) {
            PrimitiveType primitiveType = this.getPrimitiveType(klass.getComponentType());
            if (primitiveType != null) {
                PrimitiveType primitiveType2 = primitiveType;
                boolean bl = false;
                boolean bl2 = false;
                PrimitiveType it = primitiveType2;
                boolean bl3 = false;
                return new ClassId(KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME, it.getArrayTypeName());
            }
            ClassId classId = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.array.toSafe());
            Intrinsics.checkNotNullExpressionValue(classId, "ClassId.topLevel(KotlinB\u2026.FQ_NAMES.array.toSafe())");
            return classId;
        }
        if (Intrinsics.areEqual(klass, Void.TYPE)) {
            return JAVA_LANG_VOID;
        }
        PrimitiveType primitiveType = this.getPrimitiveType(klass);
        if (primitiveType != null) {
            PrimitiveType primitiveType3 = primitiveType;
            boolean bl = false;
            boolean bl4 = false;
            PrimitiveType it = primitiveType3;
            boolean bl5 = false;
            return new ClassId(KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME, it.getTypeName());
        }
        ClassId classId = ReflectClassUtilKt.getClassId(klass);
        if (!classId.isLocal()) {
            FqName fqName2 = classId.asSingleFqName();
            Intrinsics.checkNotNullExpressionValue(fqName2, "classId.asSingleFqName()");
            ClassId classId2 = JavaToKotlinClassMap.INSTANCE.mapJavaToKotlin(fqName2);
            if (classId2 != null) {
                ClassId classId3 = classId2;
                boolean bl = false;
                boolean bl6 = false;
                ClassId it = classId3;
                boolean bl7 = false;
                return it;
            }
        }
        return classId;
    }

    private final PrimitiveType getPrimitiveType(Class<?> $this$primitiveType) {
        PrimitiveType primitiveType;
        if ($this$primitiveType.isPrimitive()) {
            JvmPrimitiveType jvmPrimitiveType = JvmPrimitiveType.get($this$primitiveType.getSimpleName());
            Intrinsics.checkNotNullExpressionValue((Object)jvmPrimitiveType, "JvmPrimitiveType.get(simpleName)");
            primitiveType = jvmPrimitiveType.getPrimitiveType();
        } else {
            primitiveType = null;
        }
        return primitiveType;
    }

    private RuntimeTypeMapper() {
    }

    static {
        RuntimeTypeMapper runtimeTypeMapper;
        INSTANCE = runtimeTypeMapper = new RuntimeTypeMapper();
        ClassId classId = ClassId.topLevel(new FqName("java.lang.Void"));
        Intrinsics.checkNotNullExpressionValue(classId, "ClassId.topLevel(FqName(\"java.lang.Void\"))");
        JAVA_LANG_VOID = classId;
    }
}

