/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.JvmFunctionSignature;
import kotlin.reflect.jvm.internal.KotlinReflectionInternalError;
import kotlin.reflect.jvm.internal.RuntimeTypeMapperKt;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectClassUtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAbi;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmPackagePartSource;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.ProtoBufUtilKt;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
import kotlin.reflect.jvm.internal.impl.name.NameUtils;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedPropertyDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001:\u0004\u0005\u0006\u0007\bB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&\u0082\u0001\u0004\t\n\u000b\f\u00a8\u0006\r"}, d2={"Lkotlin/reflect/jvm/internal/JvmPropertySignature;", "", "()V", "asString", "", "JavaField", "JavaMethodProperty", "KotlinProperty", "MappedKotlinProperty", "Lkotlin/reflect/jvm/internal/JvmPropertySignature$KotlinProperty;", "Lkotlin/reflect/jvm/internal/JvmPropertySignature$JavaMethodProperty;", "Lkotlin/reflect/jvm/internal/JvmPropertySignature$JavaField;", "Lkotlin/reflect/jvm/internal/JvmPropertySignature$MappedKotlinProperty;", "kotlin-reflection"})
public abstract class JvmPropertySignature {
    @NotNull
    public abstract String asString();

    private JvmPropertySignature() {
    }

    public /* synthetic */ JvmPropertySignature(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\b\u0010\u0019\u001a\u00020\u0016H\u0016J\b\u0010\u001a\u001a\u00020\u0016H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006\u001b"}, d2={"Lkotlin/reflect/jvm/internal/JvmPropertySignature$KotlinProperty;", "Lkotlin/reflect/jvm/internal/JvmPropertySignature;", "descriptor", "Lkotlin/reflect/jvm/internal/impl/descriptors/PropertyDescriptor;", "proto", "Lkotlin/reflect/jvm/internal/impl/metadata/ProtoBuf$Property;", "signature", "Lkotlin/reflect/jvm/internal/impl/metadata/jvm/JvmProtoBuf$JvmPropertySignature;", "nameResolver", "Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/NameResolver;", "typeTable", "Lkotlin/reflect/jvm/internal/impl/metadata/deserialization/TypeTable;", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;Lorg/jetbrains/kotlin/metadata/jvm/JvmProtoBuf$JvmPropertySignature;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getNameResolver", "()Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "getProto", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "getSignature", "()Lorg/jetbrains/kotlin/metadata/jvm/JvmProtoBuf$JvmPropertySignature;", "string", "", "getTypeTable", "()Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "asString", "getManglingSuffix", "kotlin-reflection"})
    public static final class KotlinProperty
    extends JvmPropertySignature {
        private final String string;
        @NotNull
        private final PropertyDescriptor descriptor;
        @NotNull
        private final ProtoBuf.Property proto;
        @NotNull
        private final JvmProtoBuf.JvmPropertySignature signature;
        @NotNull
        private final NameResolver nameResolver;
        @NotNull
        private final TypeTable typeTable;

        private final String getManglingSuffix() {
            DeclarationDescriptor containingDeclaration;
            block6: {
                Object object;
                block8: {
                    block7: {
                        DeclarationDescriptor declarationDescriptor = this.descriptor.getContainingDeclaration();
                        Intrinsics.checkNotNullExpressionValue(declarationDescriptor, "descriptor.containingDeclaration");
                        containingDeclaration = declarationDescriptor;
                        if (!Intrinsics.areEqual(this.descriptor.getVisibility(), Visibilities.INTERNAL) || !(containingDeclaration instanceof DeserializedClassDescriptor)) break block6;
                        ProtoBuf.Class classProto = ((DeserializedClassDescriptor)containingDeclaration).getClassProto();
                        GeneratedMessageLite.ExtendableMessage extendableMessage = classProto;
                        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, Integer> generatedExtension = JvmProtoBuf.classModuleName;
                        Intrinsics.checkNotNullExpressionValue(generatedExtension, "JvmProtoBuf.classModuleName");
                        object = ProtoBufUtilKt.getExtensionOrNull(extendableMessage, generatedExtension);
                        if (object == null) break block7;
                        Integer n = object;
                        NameResolver nameResolver = this.nameResolver;
                        boolean bl = false;
                        boolean bl2 = false;
                        int p1 = ((Number)n).intValue();
                        boolean bl3 = false;
                        object = nameResolver.getString(p1);
                        if (object != null) break block8;
                    }
                    object = "main";
                }
                Object moduleName = object;
                return "$" + NameUtils.sanitizeAsJavaIdentifier((String)moduleName);
            }
            if (Intrinsics.areEqual(this.descriptor.getVisibility(), Visibilities.PRIVATE) && containingDeclaration instanceof PackageFragmentDescriptor) {
                PropertyDescriptor propertyDescriptor = this.descriptor;
                if (propertyDescriptor == null) {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPropertyDescriptor");
                }
                DeserializedContainerSource packagePartSource = ((DeserializedPropertyDescriptor)propertyDescriptor).getContainerSource();
                if (packagePartSource instanceof JvmPackagePartSource && ((JvmPackagePartSource)packagePartSource).getFacadeClassName() != null) {
                    return "$" + ((JvmPackagePartSource)packagePartSource).getSimpleName().asString();
                }
            }
            return "";
        }

        @Override
        @NotNull
        public String asString() {
            return this.string;
        }

        @NotNull
        public final PropertyDescriptor getDescriptor() {
            return this.descriptor;
        }

        @NotNull
        public final ProtoBuf.Property getProto() {
            return this.proto;
        }

        @NotNull
        public final JvmProtoBuf.JvmPropertySignature getSignature() {
            return this.signature;
        }

        @NotNull
        public final NameResolver getNameResolver() {
            return this.nameResolver;
        }

        @NotNull
        public final TypeTable getTypeTable() {
            return this.typeTable;
        }

        /*
         * WARNING - void declaration
         */
        public KotlinProperty(@NotNull PropertyDescriptor descriptor2, @NotNull ProtoBuf.Property proto, @NotNull JvmProtoBuf.JvmPropertySignature signature2, @NotNull NameResolver nameResolver, @NotNull TypeTable typeTable) {
            String string;
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
            Intrinsics.checkNotNullParameter(proto, "proto");
            Intrinsics.checkNotNullParameter(signature2, "signature");
            Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
            Intrinsics.checkNotNullParameter(typeTable, "typeTable");
            super(null);
            this.descriptor = descriptor2;
            this.proto = proto;
            this.signature = signature2;
            this.nameResolver = nameResolver;
            this.typeTable = typeTable;
            if (this.signature.hasGetter()) {
                StringBuilder stringBuilder = new StringBuilder();
                JvmProtoBuf.JvmMethodSignature jvmMethodSignature = this.signature.getGetter();
                Intrinsics.checkNotNullExpressionValue(jvmMethodSignature, "signature.getter");
                StringBuilder stringBuilder2 = stringBuilder.append(this.nameResolver.getString(jvmMethodSignature.getName()));
                JvmProtoBuf.JvmMethodSignature jvmMethodSignature2 = this.signature.getGetter();
                Intrinsics.checkNotNullExpressionValue(jvmMethodSignature2, "signature.getter");
                string = stringBuilder2.append(this.nameResolver.getString(jvmMethodSignature2.getDesc())).toString();
            } else {
                void name;
                JvmMemberSignature.Field field = JvmProtoBufUtil.getJvmFieldSignature$default(JvmProtoBufUtil.INSTANCE, this.proto, this.nameResolver, this.typeTable, false, 8, null);
                if (field == null) {
                    throw (Throwable)new KotlinReflectionInternalError("No field signature for property: " + this.descriptor);
                }
                JvmMemberSignature.Field field2 = field;
                String string2 = field2.component1();
                String desc = field2.component2();
                string = JvmAbi.getterName((String)name) + this.getManglingSuffix() + "()" + desc;
            }
            this.string = string;
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0005J\b\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u000b"}, d2={"Lkotlin/reflect/jvm/internal/JvmPropertySignature$JavaMethodProperty;", "Lkotlin/reflect/jvm/internal/JvmPropertySignature;", "getterMethod", "Ljava/lang/reflect/Method;", "setterMethod", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", "getGetterMethod", "()Ljava/lang/reflect/Method;", "getSetterMethod", "asString", "", "kotlin-reflection"})
    public static final class JavaMethodProperty
    extends JvmPropertySignature {
        @NotNull
        private final Method getterMethod;
        @Nullable
        private final Method setterMethod;

        @Override
        @NotNull
        public String asString() {
            return RuntimeTypeMapperKt.access$getSignature$p(this.getterMethod);
        }

        @NotNull
        public final Method getGetterMethod() {
            return this.getterMethod;
        }

        @Nullable
        public final Method getSetterMethod() {
            return this.setterMethod;
        }

        public JavaMethodProperty(@NotNull Method getterMethod, @Nullable Method setterMethod) {
            Intrinsics.checkNotNullParameter(getterMethod, "getterMethod");
            super(null);
            this.getterMethod = getterMethod;
            this.setterMethod = setterMethod;
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lkotlin/reflect/jvm/internal/JvmPropertySignature$JavaField;", "Lkotlin/reflect/jvm/internal/JvmPropertySignature;", "field", "Ljava/lang/reflect/Field;", "(Ljava/lang/reflect/Field;)V", "getField", "()Ljava/lang/reflect/Field;", "asString", "", "kotlin-reflection"})
    public static final class JavaField
    extends JvmPropertySignature {
        @NotNull
        private final Field field;

        @Override
        @NotNull
        public String asString() {
            return JvmAbi.getterName(this.field.getName()) + "()" + ReflectClassUtilKt.getDesc(this.field.getType());
        }

        @NotNull
        public final Field getField() {
            return this.field;
        }

        public JavaField(@NotNull Field field) {
            Intrinsics.checkNotNullParameter(field, "field");
            super(null);
            this.field = field;
        }
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0005J\b\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u000b"}, d2={"Lkotlin/reflect/jvm/internal/JvmPropertySignature$MappedKotlinProperty;", "Lkotlin/reflect/jvm/internal/JvmPropertySignature;", "getterSignature", "Lkotlin/reflect/jvm/internal/JvmFunctionSignature$KotlinFunction;", "setterSignature", "(Lkotlin/reflect/jvm/internal/JvmFunctionSignature$KotlinFunction;Lkotlin/reflect/jvm/internal/JvmFunctionSignature$KotlinFunction;)V", "getGetterSignature", "()Lkotlin/reflect/jvm/internal/JvmFunctionSignature$KotlinFunction;", "getSetterSignature", "asString", "", "kotlin-reflection"})
    public static final class MappedKotlinProperty
    extends JvmPropertySignature {
        @NotNull
        private final JvmFunctionSignature.KotlinFunction getterSignature;
        @Nullable
        private final JvmFunctionSignature.KotlinFunction setterSignature;

        @Override
        @NotNull
        public String asString() {
            return this.getterSignature.asString();
        }

        @NotNull
        public final JvmFunctionSignature.KotlinFunction getGetterSignature() {
            return this.getterSignature;
        }

        @Nullable
        public final JvmFunctionSignature.KotlinFunction getSetterSignature() {
            return this.setterSignature;
        }

        public MappedKotlinProperty(@NotNull JvmFunctionSignature.KotlinFunction getterSignature, @Nullable JvmFunctionSignature.KotlinFunction setterSignature) {
            Intrinsics.checkNotNullParameter(getterSignature, "getterSignature");
            super(null);
            this.getterSignature = getterSignature;
            this.setterSignature = setterSignature;
        }
    }
}

