/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;

public final class EnumValue
extends ConstantValue<Pair<? extends ClassId, ? extends Name>> {
    @NotNull
    private final ClassId enumClassId;
    @NotNull
    private final Name enumEntryName;

    /*
     * Enabled aggressive block sorting
     */
    @Override
    @NotNull
    public KotlinType getType(@NotNull ModuleDescriptor module) {
        KotlinType kotlinType;
        Intrinsics.checkNotNullParameter(module, "module");
        Annotated annotated = FindClassInModuleKt.findClassAcrossModuleDependencies(module, this.enumClassId);
        if (annotated != null) {
            ClassDescriptor classDescriptor = annotated;
            boolean bl = false;
            boolean bl2 = false;
            DeclarationDescriptor p1 = classDescriptor;
            boolean bl3 = false;
            annotated = DescriptorUtils.isEnumClass(p1) ? classDescriptor : null;
            if (annotated != null && (annotated = annotated.getDefaultType()) != null) {
                kotlinType = (KotlinType)annotated;
                return kotlinType;
            }
        }
        SimpleType simpleType2 = ErrorUtils.createErrorType("Containing class for error-class based enum entry " + this.enumClassId + '.' + this.enumEntryName);
        Intrinsics.checkNotNullExpressionValue(simpleType2, "ErrorUtils.createErrorTy\u2026mClassId.$enumEntryName\")");
        kotlinType = simpleType2;
        return kotlinType;
    }

    @Override
    @NotNull
    public String toString() {
        return "" + this.enumClassId.getShortClassName() + '.' + this.enumEntryName;
    }

    @NotNull
    public final Name getEnumEntryName() {
        return this.enumEntryName;
    }

    public EnumValue(@NotNull ClassId enumClassId, @NotNull Name enumEntryName) {
        Intrinsics.checkNotNullParameter(enumClassId, "enumClassId");
        Intrinsics.checkNotNullParameter(enumEntryName, "enumEntryName");
        super(TuplesKt.to(enumClassId, enumEntryName));
        this.enumClassId = enumClassId;
        this.enumEntryName = enumEntryName;
    }
}

