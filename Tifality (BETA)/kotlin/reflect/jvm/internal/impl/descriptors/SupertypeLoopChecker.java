/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import org.jetbrains.annotations.NotNull;

public interface SupertypeLoopChecker {
    @NotNull
    public Collection<KotlinType> findLoopsInSupertypesAndDisconnect(@NotNull TypeConstructor var1, @NotNull Collection<? extends KotlinType> var2, @NotNull Function1<? super TypeConstructor, ? extends Iterable<? extends KotlinType>> var3, @NotNull Function1<? super KotlinType, Unit> var4);

    public static final class EMPTY
    implements SupertypeLoopChecker {
        public static final EMPTY INSTANCE;

        @Override
        @NotNull
        public Collection<KotlinType> findLoopsInSupertypesAndDisconnect(@NotNull TypeConstructor currentTypeConstructor, @NotNull Collection<? extends KotlinType> superTypes2, @NotNull Function1<? super TypeConstructor, ? extends Iterable<? extends KotlinType>> neighbors, @NotNull Function1<? super KotlinType, Unit> reportLoop) {
            Intrinsics.checkNotNullParameter(currentTypeConstructor, "currentTypeConstructor");
            Intrinsics.checkNotNullParameter(superTypes2, "superTypes");
            Intrinsics.checkNotNullParameter(neighbors, "neighbors");
            Intrinsics.checkNotNullParameter(reportLoop, "reportLoop");
            return superTypes2;
        }

        private EMPTY() {
        }

        static {
            EMPTY eMPTY;
            INSTANCE = eMPTY = new EMPTY();
        }
    }
}

