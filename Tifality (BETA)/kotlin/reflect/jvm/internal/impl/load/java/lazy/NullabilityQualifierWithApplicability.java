/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.Collection;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NullabilityQualifierWithApplicability {
    @NotNull
    private final NullabilityQualifierWithMigrationStatus nullabilityQualifier;
    @NotNull
    private final Collection<AnnotationTypeQualifierResolver.QualifierApplicabilityType> qualifierApplicabilityTypes;

    public NullabilityQualifierWithApplicability(@NotNull NullabilityQualifierWithMigrationStatus nullabilityQualifier, @NotNull Collection<? extends AnnotationTypeQualifierResolver.QualifierApplicabilityType> qualifierApplicabilityTypes) {
        Intrinsics.checkNotNullParameter(nullabilityQualifier, "nullabilityQualifier");
        Intrinsics.checkNotNullParameter(qualifierApplicabilityTypes, "qualifierApplicabilityTypes");
        this.nullabilityQualifier = nullabilityQualifier;
        this.qualifierApplicabilityTypes = qualifierApplicabilityTypes;
    }

    @NotNull
    public final NullabilityQualifierWithMigrationStatus component1() {
        return this.nullabilityQualifier;
    }

    @NotNull
    public final Collection<AnnotationTypeQualifierResolver.QualifierApplicabilityType> component2() {
        return this.qualifierApplicabilityTypes;
    }

    @NotNull
    public String toString() {
        return "NullabilityQualifierWithApplicability(nullabilityQualifier=" + this.nullabilityQualifier + ", qualifierApplicabilityTypes=" + this.qualifierApplicabilityTypes + ")";
    }

    public int hashCode() {
        NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus = this.nullabilityQualifier;
        Collection<AnnotationTypeQualifierResolver.QualifierApplicabilityType> collection = this.qualifierApplicabilityTypes;
        return (nullabilityQualifierWithMigrationStatus != null ? ((Object)nullabilityQualifierWithMigrationStatus).hashCode() : 0) * 31 + (collection != null ? ((Object)collection).hashCode() : 0);
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof NullabilityQualifierWithApplicability)) break block3;
                NullabilityQualifierWithApplicability nullabilityQualifierWithApplicability = (NullabilityQualifierWithApplicability)object;
                if (!Intrinsics.areEqual(this.nullabilityQualifier, nullabilityQualifierWithApplicability.nullabilityQualifier) || !Intrinsics.areEqual(this.qualifierApplicabilityTypes, nullabilityQualifierWithApplicability.qualifierApplicabilityTypes)) break block3;
            }
            return true;
        }
        return false;
    }
}

