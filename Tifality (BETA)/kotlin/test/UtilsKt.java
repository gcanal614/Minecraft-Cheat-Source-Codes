/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.test;

import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.test.Asserter;
import kotlin.test.AssertionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u0000\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0001H\u0000\u001a\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0001\u00a8\u0006\u0006"}, d2={"messagePrefix", "", "message", "overrideAsserter", "Lkotlin/test/Asserter;", "value", "kotlin-test"})
public final class UtilsKt {
    @NotNull
    public static final String messagePrefix(@Nullable String message) {
        return message == null ? "" : message + ". ";
    }

    @PublishedApi
    @Nullable
    public static final Asserter overrideAsserter(@Nullable Asserter value) {
        Asserter asserter = AssertionsKt.get_asserter();
        boolean bl = false;
        boolean bl2 = false;
        Asserter it = asserter;
        boolean bl3 = false;
        AssertionsKt.set_asserter(value);
        return asserter;
    }
}

