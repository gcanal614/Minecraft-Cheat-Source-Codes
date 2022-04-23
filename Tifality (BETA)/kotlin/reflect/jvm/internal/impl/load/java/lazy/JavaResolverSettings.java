/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.load.java.lazy;

public interface JavaResolverSettings {
    public static final Companion Companion = kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaResolverSettings$Companion.$$INSTANCE;

    public boolean isReleaseCoroutines();

    public static final class Default
    implements JavaResolverSettings {
        public static final Default INSTANCE;

        @Override
        public boolean isReleaseCoroutines() {
            return false;
        }

        private Default() {
        }

        static {
            Default default_;
            INSTANCE = default_ = new Default();
        }
    }

    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
        }

        static {
            Companion companion;
            $$INSTANCE = companion = new Companion();
        }
    }
}

