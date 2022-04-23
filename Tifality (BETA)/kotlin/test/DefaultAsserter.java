/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.test;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.test.Asserter;
import kotlin.test.AssertionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016J\u001c\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0017\u00a8\u0006\t"}, d2={"Lkotlin/test/DefaultAsserter;", "Lkotlin/test/Asserter;", "()V", "fail", "", "message", "", "cause", "", "kotlin-test"})
public final class DefaultAsserter
implements Asserter {
    public static final DefaultAsserter INSTANCE;

    @Override
    @NotNull
    public Void fail(@Nullable String message) {
        if (message == null) {
            throw (Throwable)((Object)new AssertionError());
        }
        throw (Throwable)((Object)new AssertionError((Object)message));
    }

    @Override
    @SinceKotlin(version="1.4")
    @NotNull
    public Void fail(@Nullable String message, @Nullable Throwable cause) {
        throw (Throwable)((Object)AssertionsKt.AssertionErrorWithCause(message, cause));
    }

    private DefaultAsserter() {
    }

    static {
        DefaultAsserter defaultAsserter;
        INSTANCE = defaultAsserter = new DefaultAsserter();
    }

    @Override
    public void assertTrue(@NotNull Function0<String> lazyMessage, boolean actual) {
        Intrinsics.checkNotNullParameter(lazyMessage, "lazyMessage");
        Asserter.DefaultImpls.assertTrue((Asserter)this, lazyMessage, actual);
    }

    @Override
    public void assertTrue(@Nullable String message, boolean actual) {
        Asserter.DefaultImpls.assertTrue((Asserter)this, message, actual);
    }

    @Override
    public void assertEquals(@Nullable String message, @Nullable Object expected, @Nullable Object actual) {
        Asserter.DefaultImpls.assertEquals(this, message, expected, actual);
    }

    @Override
    public void assertNotEquals(@Nullable String message, @Nullable Object illegal, @Nullable Object actual) {
        Asserter.DefaultImpls.assertNotEquals(this, message, illegal, actual);
    }

    @Override
    public void assertSame(@Nullable String message, @Nullable Object expected, @Nullable Object actual) {
        Asserter.DefaultImpls.assertSame(this, message, expected, actual);
    }

    @Override
    public void assertNotSame(@Nullable String message, @Nullable Object illegal, @Nullable Object actual) {
        Asserter.DefaultImpls.assertNotSame(this, message, illegal, actual);
    }

    @Override
    public void assertNull(@Nullable String message, @Nullable Object actual) {
        Asserter.DefaultImpls.assertNull(this, message, actual);
    }

    @Override
    public void assertNotNull(@Nullable String message, @Nullable Object actual) {
        Asserter.DefaultImpls.assertNotNull(this, message, actual);
    }
}

