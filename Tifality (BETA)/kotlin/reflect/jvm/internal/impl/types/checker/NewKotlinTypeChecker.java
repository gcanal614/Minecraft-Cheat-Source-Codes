/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.checker.NewKotlinTypeCheckerImpl;
import org.jetbrains.annotations.NotNull;

public interface NewKotlinTypeChecker
extends KotlinTypeChecker {
    public static final Companion Companion = kotlin.reflect.jvm.internal.impl.types.checker.NewKotlinTypeChecker$Companion.$$INSTANCE;

    @NotNull
    public KotlinTypeRefiner getKotlinTypeRefiner();

    @NotNull
    public OverridingUtil getOverridingUtil();

    public static final class Companion {
        @NotNull
        private static final NewKotlinTypeCheckerImpl Default;
        static final /* synthetic */ Companion $$INSTANCE;

        @NotNull
        public final NewKotlinTypeCheckerImpl getDefault() {
            return Default;
        }

        private Companion() {
        }

        static {
            Companion companion;
            $$INSTANCE = companion = new Companion();
            Default = new NewKotlinTypeCheckerImpl(KotlinTypeRefiner.Default.INSTANCE);
        }
    }
}

