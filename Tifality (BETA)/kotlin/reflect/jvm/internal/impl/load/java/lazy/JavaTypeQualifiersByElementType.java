/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.EnumMap;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class JavaTypeQualifiersByElementType {
    @NotNull
    private final EnumMap<AnnotationTypeQualifierResolver.QualifierApplicabilityType, NullabilityQualifierWithMigrationStatus> nullabilityQualifiers;

    @Nullable
    public final JavaTypeQualifiers get(@Nullable AnnotationTypeQualifierResolver.QualifierApplicabilityType applicabilityType) {
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus = this.nullabilityQualifiers.get((Object)applicabilityType);
        if (nullabilityQualifierWithMigrationStatus == null) {
            return null;
        }
        Intrinsics.checkNotNullExpressionValue(nullabilityQualifierWithMigrationStatus, "nullabilityQualifiers[ap\u2026ilityType] ?: return null");
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus2 = nullabilityQualifierWithMigrationStatus;
        return new JavaTypeQualifiers(nullabilityQualifierWithMigrationStatus2.getQualifier(), null, false, nullabilityQualifierWithMigrationStatus2.isForWarningOnly());
    }

    @NotNull
    public final EnumMap<AnnotationTypeQualifierResolver.QualifierApplicabilityType, NullabilityQualifierWithMigrationStatus> getNullabilityQualifiers() {
        return this.nullabilityQualifiers;
    }

    public JavaTypeQualifiersByElementType(@NotNull EnumMap<AnnotationTypeQualifierResolver.QualifierApplicabilityType, NullabilityQualifierWithMigrationStatus> nullabilityQualifiers) {
        Intrinsics.checkNotNullParameter(nullabilityQualifiers, "nullabilityQualifiers");
        this.nullabilityQualifiers = nullabilityQualifiers;
    }
}

