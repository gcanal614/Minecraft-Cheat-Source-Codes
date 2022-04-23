/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public interface PackagePartProvider {
    @NotNull
    public List<String> findPackageParts(@NotNull String var1);

    public static final class Empty
    implements PackagePartProvider {
        public static final Empty INSTANCE;

        @Override
        @NotNull
        public List<String> findPackageParts(@NotNull String packageFqName) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            return CollectionsKt.emptyList();
        }

        private Empty() {
        }

        static {
            Empty empty;
            INSTANCE = empty = new Empty();
        }
    }
}

