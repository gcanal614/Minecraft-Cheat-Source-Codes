/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.resolve.constants.UnsignedValueConstant;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;

public final class UByteValue
extends UnsignedValueConstant<Byte> {
    @Override
    @NotNull
    public KotlinType getType(@NotNull ModuleDescriptor module) {
        KotlinType kotlinType;
        Intrinsics.checkNotNullParameter(module, "module");
        ClassId classId = KotlinBuiltIns.FQ_NAMES.uByte;
        Intrinsics.checkNotNullExpressionValue(classId, "KotlinBuiltIns.FQ_NAMES.uByte");
        Annotated annotated = FindClassInModuleKt.findClassAcrossModuleDependencies(module, classId);
        if (annotated != null && (annotated = annotated.getDefaultType()) != null) {
            kotlinType = (KotlinType)annotated;
        } else {
            SimpleType simpleType2 = ErrorUtils.createErrorType("Unsigned type UByte not found");
            Intrinsics.checkNotNullExpressionValue(simpleType2, "ErrorUtils.createErrorTy\u2026ed type UByte not found\")");
            kotlinType = simpleType2;
        }
        return kotlinType;
    }

    @Override
    @NotNull
    public String toString() {
        return ((Number)this.getValue()).byteValue() + ".toUByte()";
    }

    public UByteValue(byte byteValue) {
        super(byteValue);
    }
}

