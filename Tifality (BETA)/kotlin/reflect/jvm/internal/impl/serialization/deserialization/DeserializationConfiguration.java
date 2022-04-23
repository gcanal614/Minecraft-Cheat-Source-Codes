/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import org.jetbrains.annotations.NotNull;

public interface DeserializationConfiguration {
    public boolean getSkipMetadataVersionCheck();

    public boolean getSkipPrereleaseCheck();

    public boolean getReportErrorsOnPreReleaseDependencies();

    public boolean getReportErrorsOnIrDependencies();

    public boolean getTypeAliasesAllowed();

    public boolean getReleaseCoroutines();

    public static final class Default
    implements DeserializationConfiguration {
        public static final Default INSTANCE;

        private Default() {
        }

        static {
            Default default_;
            INSTANCE = default_ = new Default();
        }

        @Override
        public boolean getSkipMetadataVersionCheck() {
            return DefaultImpls.getSkipMetadataVersionCheck(this);
        }

        @Override
        public boolean getSkipPrereleaseCheck() {
            return DefaultImpls.getSkipPrereleaseCheck(this);
        }

        @Override
        public boolean getReportErrorsOnPreReleaseDependencies() {
            return DefaultImpls.getReportErrorsOnPreReleaseDependencies(this);
        }

        @Override
        public boolean getReportErrorsOnIrDependencies() {
            return DefaultImpls.getReportErrorsOnIrDependencies(this);
        }

        @Override
        public boolean getTypeAliasesAllowed() {
            return DefaultImpls.getTypeAliasesAllowed(this);
        }

        @Override
        public boolean getReleaseCoroutines() {
            return DefaultImpls.getReleaseCoroutines(this);
        }
    }

    public static final class DefaultImpls {
        public static boolean getSkipMetadataVersionCheck(@NotNull DeserializationConfiguration $this) {
            return false;
        }

        public static boolean getSkipPrereleaseCheck(@NotNull DeserializationConfiguration $this) {
            return false;
        }

        public static boolean getReportErrorsOnPreReleaseDependencies(@NotNull DeserializationConfiguration $this) {
            return false;
        }

        public static boolean getReportErrorsOnIrDependencies(@NotNull DeserializationConfiguration $this) {
            return false;
        }

        public static boolean getTypeAliasesAllowed(@NotNull DeserializationConfiguration $this) {
            return true;
        }

        public static boolean getReleaseCoroutines(@NotNull DeserializationConfiguration $this) {
            return false;
        }
    }
}

