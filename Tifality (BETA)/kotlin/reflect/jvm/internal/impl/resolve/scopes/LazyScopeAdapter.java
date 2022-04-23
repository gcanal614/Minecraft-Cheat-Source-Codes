/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.AbstractScopeAdapter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import org.jetbrains.annotations.NotNull;

public final class LazyScopeAdapter
extends AbstractScopeAdapter {
    private final NotNullLazyValue<MemberScope> lazyScope;

    @Override
    @NotNull
    protected MemberScope getWorkerScope() {
        return (MemberScope)this.lazyScope.invoke();
    }

    @JvmOverloads
    public LazyScopeAdapter(@NotNull StorageManager storageManager, @NotNull Function0<? extends MemberScope> getScope2) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(getScope2, "getScope");
        this.lazyScope = storageManager.createLazyValue((Function0)new Function0<MemberScope>(getScope2){
            final /* synthetic */ Function0 $getScope;

            @NotNull
            public final MemberScope invoke() {
                R r = this.$getScope.invoke();
                boolean bl = false;
                boolean bl2 = false;
                MemberScope it = (MemberScope)r;
                boolean bl3 = false;
                return it instanceof AbstractScopeAdapter ? ((AbstractScopeAdapter)it).getActualScope() : it;
            }
            {
                this.$getScope = function0;
                super(0);
            }
        });
    }

    public /* synthetic */ LazyScopeAdapter(StorageManager storageManager, Function0 function0, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            StorageManager storageManager2 = LockBasedStorageManager.NO_LOCKS;
            Intrinsics.checkNotNullExpressionValue(storageManager2, "LockBasedStorageManager.NO_LOCKS");
            storageManager = storageManager2;
        }
        this(storageManager, function0);
    }

    @JvmOverloads
    public LazyScopeAdapter(@NotNull Function0<? extends MemberScope> getScope2) {
        this(null, getScope2, 1, null);
    }
}

