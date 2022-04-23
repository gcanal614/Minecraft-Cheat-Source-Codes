/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;

public abstract class DescriptorKindExclude {
    public abstract int getFullyExcludedDescriptorKinds();

    public String toString() {
        return this.getClass().getSimpleName();
    }

    public static final class NonExtensions
    extends DescriptorKindExclude {
        private static final int fullyExcludedDescriptorKinds;
        public static final NonExtensions INSTANCE;

        @Override
        public int getFullyExcludedDescriptorKinds() {
            return fullyExcludedDescriptorKinds;
        }

        private NonExtensions() {
        }

        static {
            NonExtensions nonExtensions;
            INSTANCE = nonExtensions = new NonExtensions();
            fullyExcludedDescriptorKinds = DescriptorKindFilter.Companion.getALL_KINDS_MASK() & ~(DescriptorKindFilter.Companion.getFUNCTIONS_MASK() | DescriptorKindFilter.Companion.getVARIABLES_MASK());
        }
    }

    public static final class TopLevelPackages
    extends DescriptorKindExclude {
        public static final TopLevelPackages INSTANCE;

        @Override
        public int getFullyExcludedDescriptorKinds() {
            return 0;
        }

        private TopLevelPackages() {
        }

        static {
            TopLevelPackages topLevelPackages;
            INSTANCE = topLevelPackages = new TopLevelPackages();
        }
    }
}

