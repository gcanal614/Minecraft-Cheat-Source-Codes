/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NullabilityQualifierWithMigrationStatus {
    @NotNull
    private final NullabilityQualifier qualifier;
    private final boolean isForWarningOnly;

    @NotNull
    public final NullabilityQualifier getQualifier() {
        return this.qualifier;
    }

    public final boolean isForWarningOnly() {
        return this.isForWarningOnly;
    }

    public NullabilityQualifierWithMigrationStatus(@NotNull NullabilityQualifier qualifier, boolean isForWarningOnly) {
        Intrinsics.checkNotNullParameter((Object)qualifier, "qualifier");
        this.qualifier = qualifier;
        this.isForWarningOnly = isForWarningOnly;
    }

    public /* synthetic */ NullabilityQualifierWithMigrationStatus(NullabilityQualifier nullabilityQualifier, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            bl = false;
        }
        this(nullabilityQualifier, bl);
    }

    @NotNull
    public final NullabilityQualifierWithMigrationStatus copy(@NotNull NullabilityQualifier qualifier, boolean isForWarningOnly) {
        Intrinsics.checkNotNullParameter((Object)qualifier, "qualifier");
        return new NullabilityQualifierWithMigrationStatus(qualifier, isForWarningOnly);
    }

    public static /* synthetic */ NullabilityQualifierWithMigrationStatus copy$default(NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus, NullabilityQualifier nullabilityQualifier, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            nullabilityQualifier = nullabilityQualifierWithMigrationStatus.qualifier;
        }
        if ((n & 2) != 0) {
            bl = nullabilityQualifierWithMigrationStatus.isForWarningOnly;
        }
        return nullabilityQualifierWithMigrationStatus.copy(nullabilityQualifier, bl);
    }

    @NotNull
    public String toString() {
        return "NullabilityQualifierWithMigrationStatus(qualifier=" + (Object)((Object)this.qualifier) + ", isForWarningOnly=" + this.isForWarningOnly + ")";
    }

    public int hashCode() {
        NullabilityQualifier nullabilityQualifier = this.qualifier;
        int n = (nullabilityQualifier != null ? ((Object)((Object)nullabilityQualifier)).hashCode() : 0) * 31;
        int n2 = this.isForWarningOnly ? 1 : 0;
        if (n2 != 0) {
            n2 = 1;
        }
        return n + n2;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof NullabilityQualifierWithMigrationStatus)) break block3;
                NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus = (NullabilityQualifierWithMigrationStatus)object;
                if (!Intrinsics.areEqual((Object)this.qualifier, (Object)nullabilityQualifierWithMigrationStatus.qualifier) || this.isForWarningOnly != nullabilityQualifierWithMigrationStatus.isForWarningOnly) break block3;
            }
            return true;
        }
        return false;
    }
}

