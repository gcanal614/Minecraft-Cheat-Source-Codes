/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Map;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface AnnotationDescriptor {
    @NotNull
    public KotlinType getType();

    @Nullable
    public FqName getFqName();

    @NotNull
    public Map<Name, ConstantValue<?>> getAllValueArguments();

    @NotNull
    public SourceElement getSource();

    public static final class DefaultImpls {
        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Nullable
        public static FqName getFqName(@NotNull AnnotationDescriptor $this) {
            ClassDescriptor classDescriptor = DescriptorUtilsKt.getAnnotationClass($this);
            ClassDescriptor classDescriptor2 = classDescriptor;
            if (classDescriptor == null) return null;
            ClassDescriptor classDescriptor3 = classDescriptor2;
            boolean bl = false;
            boolean bl2 = false;
            DeclarationDescriptor p1 = classDescriptor3;
            boolean bl3 = false;
            if (ErrorUtils.isError(p1)) return null;
            ClassDescriptor classDescriptor4 = classDescriptor3;
            classDescriptor2 = classDescriptor4;
            if (classDescriptor4 == null) return null;
            FqName fqName2 = DescriptorUtilsKt.fqNameOrNull(classDescriptor2);
            return fqName2;
        }
    }
}

