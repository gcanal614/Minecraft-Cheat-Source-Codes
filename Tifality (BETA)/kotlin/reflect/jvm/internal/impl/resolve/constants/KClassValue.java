/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ClassLiteralValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class KClassValue
extends ConstantValue<Value> {
    public static final Companion Companion = new Companion(null);

    @Override
    @NotNull
    public KotlinType getType(@NotNull ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        Annotations annotations2 = Annotations.Companion.getEMPTY();
        ClassDescriptor classDescriptor = module.getBuiltIns().getKClass();
        Intrinsics.checkNotNullExpressionValue(classDescriptor, "module.builtIns.kClass");
        return KotlinTypeFactory.simpleNotNullType(annotations2, classDescriptor, CollectionsKt.listOf(new TypeProjectionImpl(this.getArgumentType(module))));
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final KotlinType getArgumentType(@NotNull ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        Value value = (Value)this.getValue();
        if (value instanceof Value.LocalClass) {
            return ((Value.LocalClass)this.getValue()).getType();
        }
        if (value instanceof Value.NormalClass) {
            void classId;
            ClassLiteralValue classLiteralValue = ((Value.NormalClass)this.getValue()).getValue();
            ClassId classId2 = classLiteralValue.component1();
            int arrayDimensions = classLiteralValue.component2();
            ClassDescriptor classDescriptor = FindClassInModuleKt.findClassAcrossModuleDependencies(module, (ClassId)classId);
            if (classDescriptor == null) {
                SimpleType simpleType2 = ErrorUtils.createErrorType("Unresolved type: " + classId + " (arrayDimensions=" + arrayDimensions + ')');
                Intrinsics.checkNotNullExpressionValue(simpleType2, "ErrorUtils.createErrorTy\u2026sions=$arrayDimensions)\")");
                return simpleType2;
            }
            ClassDescriptor descriptor2 = classDescriptor;
            SimpleType simpleType3 = descriptor2.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(simpleType3, "descriptor.defaultType");
            KotlinType type2 = TypeUtilsKt.replaceArgumentsWithStarProjections(simpleType3);
            boolean bl = false;
            int n = 0;
            n = 0;
            int n2 = arrayDimensions;
            while (n < n2) {
                int it = n++;
                boolean bl2 = false;
                SimpleType simpleType4 = module.getBuiltIns().getArrayType(Variance.INVARIANT, type2);
                Intrinsics.checkNotNullExpressionValue(simpleType4, "module.builtIns.getArray\u2026Variance.INVARIANT, type)");
                type2 = simpleType4;
            }
            return type2;
        }
        throw new NoWhenBranchMatchedException();
    }

    public KClassValue(@NotNull Value value) {
        Intrinsics.checkNotNullParameter(value, "value");
        super(value);
    }

    public KClassValue(@NotNull ClassLiteralValue value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this(new Value.NormalClass(value));
    }

    public KClassValue(@NotNull ClassId classId, int arrayDimensions) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        this(new ClassLiteralValue(classId, arrayDimensions));
    }

    public static abstract class Value {
        private Value() {
        }

        public /* synthetic */ Value(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public static final class NormalClass
        extends Value {
            @NotNull
            private final ClassLiteralValue value;

            @NotNull
            public final ClassId getClassId() {
                return this.value.getClassId();
            }

            public final int getArrayDimensions() {
                return this.value.getArrayNestedness();
            }

            @NotNull
            public final ClassLiteralValue getValue() {
                return this.value;
            }

            public NormalClass(@NotNull ClassLiteralValue value) {
                Intrinsics.checkNotNullParameter(value, "value");
                super(null);
                this.value = value;
            }

            @NotNull
            public String toString() {
                return "NormalClass(value=" + this.value + ")";
            }

            public int hashCode() {
                ClassLiteralValue classLiteralValue = this.value;
                return classLiteralValue != null ? ((Object)classLiteralValue).hashCode() : 0;
            }

            public boolean equals(@Nullable Object object) {
                block3: {
                    block2: {
                        if (this == object) break block2;
                        if (!(object instanceof NormalClass)) break block3;
                        NormalClass normalClass = (NormalClass)object;
                        if (!Intrinsics.areEqual(this.value, normalClass.value)) break block3;
                    }
                    return true;
                }
                return false;
            }
        }

        public static final class LocalClass
        extends Value {
            @NotNull
            private final KotlinType type;

            @NotNull
            public final KotlinType getType() {
                return this.type;
            }

            public LocalClass(@NotNull KotlinType type2) {
                Intrinsics.checkNotNullParameter(type2, "type");
                super(null);
                this.type = type2;
            }

            @NotNull
            public String toString() {
                return "LocalClass(type=" + this.type + ")";
            }

            public int hashCode() {
                KotlinType kotlinType = this.type;
                return kotlinType != null ? ((Object)kotlinType).hashCode() : 0;
            }

            public boolean equals(@Nullable Object object) {
                block3: {
                    block2: {
                        if (this == object) break block2;
                        if (!(object instanceof LocalClass)) break block3;
                        LocalClass localClass = (LocalClass)object;
                        if (!Intrinsics.areEqual(this.type, localClass.type)) break block3;
                    }
                    return true;
                }
                return false;
            }
        }
    }

    public static final class Companion {
        @Nullable
        public final ConstantValue<?> create(@NotNull KotlinType argumentType) {
            KClassValue kClassValue;
            Intrinsics.checkNotNullParameter(argumentType, "argumentType");
            if (KotlinTypeKt.isError(argumentType)) {
                return null;
            }
            KotlinType type2 = argumentType;
            int arrayDimensions = 0;
            while (KotlinBuiltIns.isArray(type2)) {
                Intrinsics.checkNotNullExpressionValue(CollectionsKt.single(type2.getArguments()).getType(), "type.arguments.single().type");
                ++arrayDimensions;
            }
            ClassifierDescriptor descriptor2 = type2.getConstructor().getDeclarationDescriptor();
            if (descriptor2 instanceof ClassDescriptor) {
                ClassId classId = DescriptorUtilsKt.getClassId(descriptor2);
                if (classId == null) {
                    return new KClassValue(new Value.LocalClass(argumentType));
                }
                ClassId classId2 = classId;
                kClassValue = new KClassValue(classId2, arrayDimensions);
            } else if (descriptor2 instanceof TypeParameterDescriptor) {
                ClassId classId = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.any.toSafe());
                Intrinsics.checkNotNullExpressionValue(classId, "ClassId.topLevel(KotlinB\u2026ns.FQ_NAMES.any.toSafe())");
                kClassValue = new KClassValue(classId, 0);
            } else {
                kClassValue = null;
            }
            return kClassValue;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

