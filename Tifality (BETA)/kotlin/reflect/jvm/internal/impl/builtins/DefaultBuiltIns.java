/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.builtins;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import org.jetbrains.annotations.NotNull;

public final class DefaultBuiltIns
extends KotlinBuiltIns {
    @NotNull
    private static final DefaultBuiltIns Instance;
    public static final Companion Companion;

    public DefaultBuiltIns(boolean loadBuiltInsFromCurrentClassLoader) {
        super(new LockBasedStorageManager("DefaultBuiltIns"));
        if (loadBuiltInsFromCurrentClassLoader) {
            this.createBuiltInsModule(false);
        }
    }

    public /* synthetic */ DefaultBuiltIns(boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            bl = true;
        }
        this(bl);
    }

    public DefaultBuiltIns() {
        this(false, 1, null);
    }

    static {
        Companion = new Companion(null);
        Instance = new DefaultBuiltIns(false, 1, null);
    }

    @NotNull
    public static final DefaultBuiltIns getInstance() {
        Companion companion = Companion;
        return Instance;
    }

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

