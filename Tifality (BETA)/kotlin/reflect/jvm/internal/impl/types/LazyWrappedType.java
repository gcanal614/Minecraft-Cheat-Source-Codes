/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.WrappedType;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

public final class LazyWrappedType
extends WrappedType {
    private final NotNullLazyValue<KotlinType> lazyValue;
    private final StorageManager storageManager;
    private final Function0<KotlinType> computation;

    @Override
    @NotNull
    protected KotlinType getDelegate() {
        return (KotlinType)this.lazyValue.invoke();
    }

    @Override
    public boolean isComputed() {
        return this.lazyValue.isComputed();
    }

    @Override
    @NotNull
    public LazyWrappedType refine(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return new LazyWrappedType(this.storageManager, (Function0<? extends KotlinType>)new Function0<KotlinType>(this, kotlinTypeRefiner){
            final /* synthetic */ LazyWrappedType this$0;
            final /* synthetic */ KotlinTypeRefiner $kotlinTypeRefiner;

            @NotNull
            public final KotlinType invoke() {
                return this.$kotlinTypeRefiner.refineType((KotlinType)LazyWrappedType.access$getComputation$p(this.this$0).invoke());
            }
            {
                this.this$0 = lazyWrappedType;
                this.$kotlinTypeRefiner = kotlinTypeRefiner;
                super(0);
            }
        });
    }

    public LazyWrappedType(@NotNull StorageManager storageManager, @NotNull Function0<? extends KotlinType> computation) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(computation, "computation");
        this.storageManager = storageManager;
        this.computation = computation;
        this.lazyValue = this.storageManager.createLazyValue(this.computation);
    }

    public static final /* synthetic */ Function0 access$getComputation$p(LazyWrappedType $this) {
        return $this.computation;
    }
}

