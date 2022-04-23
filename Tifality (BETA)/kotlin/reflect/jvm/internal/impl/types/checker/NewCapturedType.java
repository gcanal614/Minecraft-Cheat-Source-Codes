/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NewCapturedType
extends SimpleType
implements CapturedTypeMarker {
    @NotNull
    private final CaptureStatus captureStatus;
    @NotNull
    private final NewCapturedTypeConstructor constructor;
    @Nullable
    private final UnwrappedType lowerType;
    @NotNull
    private final Annotations annotations;
    private final boolean isMarkedNullable;
    private final boolean isProjectionNotNull;

    @Override
    @NotNull
    public List<TypeProjection> getArguments() {
        boolean bl = false;
        return CollectionsKt.emptyList();
    }

    @Override
    @NotNull
    public MemberScope getMemberScope() {
        MemberScope memberScope2 = ErrorUtils.createErrorScope("No member resolution should be done on captured type!", true);
        Intrinsics.checkNotNullExpressionValue(memberScope2, "ErrorUtils.createErrorSc\u2026on captured type!\", true)");
        return memberScope2;
    }

    @Override
    @NotNull
    public NewCapturedType replaceAnnotations(@NotNull Annotations newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
        return new NewCapturedType(this.captureStatus, this.getConstructor(), this.lowerType, newAnnotations, this.isMarkedNullable(), false, 32, null);
    }

    @Override
    @NotNull
    public NewCapturedType makeNullableAsSpecified(boolean newNullability) {
        return new NewCapturedType(this.captureStatus, this.getConstructor(), this.lowerType, this.getAnnotations(), newNullability, false, 32, null);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public NewCapturedType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        UnwrappedType unwrappedType;
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        CaptureStatus captureStatus = this.captureStatus;
        NewCapturedTypeConstructor newCapturedTypeConstructor = this.getConstructor().refine(kotlinTypeRefiner);
        UnwrappedType unwrappedType2 = this.lowerType;
        if (unwrappedType2 != null) {
            void it;
            UnwrappedType unwrappedType3 = unwrappedType2;
            boolean bl = false;
            boolean bl2 = false;
            UnwrappedType unwrappedType4 = unwrappedType3;
            NewCapturedTypeConstructor newCapturedTypeConstructor2 = newCapturedTypeConstructor;
            CaptureStatus captureStatus2 = captureStatus;
            boolean bl3 = false;
            UnwrappedType unwrappedType5 = kotlinTypeRefiner.refineType((KotlinType)it).unwrap();
            captureStatus = captureStatus2;
            newCapturedTypeConstructor = newCapturedTypeConstructor2;
            unwrappedType = unwrappedType5;
        } else {
            unwrappedType = null;
        }
        DefaultConstructorMarker defaultConstructorMarker = null;
        int n = 32;
        boolean bl = false;
        boolean bl4 = this.isMarkedNullable();
        Annotations annotations2 = this.getAnnotations();
        UnwrappedType unwrappedType6 = unwrappedType;
        NewCapturedTypeConstructor newCapturedTypeConstructor3 = newCapturedTypeConstructor;
        CaptureStatus captureStatus3 = captureStatus;
        return new NewCapturedType(captureStatus3, newCapturedTypeConstructor3, unwrappedType6, annotations2, bl4, bl, n, defaultConstructorMarker);
    }

    @NotNull
    public final CaptureStatus getCaptureStatus() {
        return this.captureStatus;
    }

    @Override
    @NotNull
    public NewCapturedTypeConstructor getConstructor() {
        return this.constructor;
    }

    @Nullable
    public final UnwrappedType getLowerType() {
        return this.lowerType;
    }

    @Override
    @NotNull
    public Annotations getAnnotations() {
        return this.annotations;
    }

    @Override
    public boolean isMarkedNullable() {
        return this.isMarkedNullable;
    }

    public final boolean isProjectionNotNull() {
        return this.isProjectionNotNull;
    }

    public NewCapturedType(@NotNull CaptureStatus captureStatus, @NotNull NewCapturedTypeConstructor constructor, @Nullable UnwrappedType lowerType, @NotNull Annotations annotations2, boolean isMarkedNullable, boolean isProjectionNotNull) {
        Intrinsics.checkNotNullParameter((Object)captureStatus, "captureStatus");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        this.captureStatus = captureStatus;
        this.constructor = constructor;
        this.lowerType = lowerType;
        this.annotations = annotations2;
        this.isMarkedNullable = isMarkedNullable;
        this.isProjectionNotNull = isProjectionNotNull;
    }

    public /* synthetic */ NewCapturedType(CaptureStatus captureStatus, NewCapturedTypeConstructor newCapturedTypeConstructor, UnwrappedType unwrappedType, Annotations annotations2, boolean bl, boolean bl2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 8) != 0) {
            annotations2 = Annotations.Companion.getEMPTY();
        }
        if ((n & 0x10) != 0) {
            bl = false;
        }
        if ((n & 0x20) != 0) {
            bl2 = false;
        }
        this(captureStatus, newCapturedTypeConstructor, unwrappedType, annotations2, bl, bl2);
    }

    public NewCapturedType(@NotNull CaptureStatus captureStatus, @Nullable UnwrappedType lowerType, @NotNull TypeProjection projection, @NotNull TypeParameterDescriptor typeParameter) {
        Intrinsics.checkNotNullParameter((Object)captureStatus, "captureStatus");
        Intrinsics.checkNotNullParameter(projection, "projection");
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        this(captureStatus, new NewCapturedTypeConstructor(projection, null, null, typeParameter, 6, null), lowerType, null, false, false, 56, null);
    }
}

