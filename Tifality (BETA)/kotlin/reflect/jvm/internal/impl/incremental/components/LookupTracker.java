/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.incremental.components;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.incremental.components.Position;
import kotlin.reflect.jvm.internal.impl.incremental.components.ScopeKind;
import org.jetbrains.annotations.NotNull;

public interface LookupTracker {
    public boolean getRequiresPosition();

    public void record(@NotNull String var1, @NotNull Position var2, @NotNull String var3, @NotNull ScopeKind var4, @NotNull String var5);

    public static final class DO_NOTHING
    implements LookupTracker {
        public static final DO_NOTHING INSTANCE;

        @Override
        public boolean getRequiresPosition() {
            return false;
        }

        @Override
        public void record(@NotNull String filePath, @NotNull Position position, @NotNull String scopeFqName, @NotNull ScopeKind scopeKind, @NotNull String name) {
            Intrinsics.checkNotNullParameter(filePath, "filePath");
            Intrinsics.checkNotNullParameter(position, "position");
            Intrinsics.checkNotNullParameter(scopeFqName, "scopeFqName");
            Intrinsics.checkNotNullParameter((Object)scopeKind, "scopeKind");
            Intrinsics.checkNotNullParameter(name, "name");
        }

        private DO_NOTHING() {
        }

        static {
            DO_NOTHING dO_NOTHING;
            INSTANCE = dO_NOTHING = new DO_NOTHING();
        }
    }
}

