/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImplKt;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionBase;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

public final class StarProjectionImpl
extends TypeProjectionBase {
    private final Lazy _type$delegate;
    private final TypeParameterDescriptor typeParameter;

    @Override
    public boolean isStarProjection() {
        return true;
    }

    @Override
    @NotNull
    public Variance getProjectionKind() {
        return Variance.OUT_VARIANCE;
    }

    private final KotlinType get_type() {
        Lazy lazy = this._type$delegate;
        StarProjectionImpl starProjectionImpl = this;
        Object var3_3 = null;
        boolean bl = false;
        return (KotlinType)lazy.getValue();
    }

    @Override
    @NotNull
    public KotlinType getType() {
        return this.get_type();
    }

    @Override
    @NotNull
    public TypeProjection refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    public StarProjectionImpl(@NotNull TypeParameterDescriptor typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        this.typeParameter = typeParameter;
        this._type$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0)new Function0<KotlinType>(this){
            final /* synthetic */ StarProjectionImpl this$0;

            @NotNull
            public final KotlinType invoke() {
                return StarProjectionImplKt.starProjectionType(StarProjectionImpl.access$getTypeParameter$p(this.this$0));
            }
            {
                this.this$0 = starProjectionImpl;
                super(0);
            }
        });
    }

    public static final /* synthetic */ TypeParameterDescriptor access$getTypeParameter$p(StarProjectionImpl $this) {
        return $this.typeParameter;
    }
}

