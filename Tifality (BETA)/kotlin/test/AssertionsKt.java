/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.test;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KClass;
import kotlin.test.Asserter;
import kotlin.test.AssertionsKt__AssertionsImplKt;
import kotlin.test.AssertionsKt__AssertionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=4, d1={"kotlin/test/AssertionsKt__AssertionsImplKt", "kotlin/test/AssertionsKt__AssertionsKt"})
public final class AssertionsKt {
    @Nullable
    public static final Asserter get_asserter() {
        return AssertionsKt__AssertionsKt.get_asserter();
    }

    public static final void set_asserter(@Nullable Asserter asserter) {
        AssertionsKt__AssertionsKt.set_asserter(asserter);
    }

    @NotNull
    public static final Asserter getAsserter() {
        return AssertionsKt__AssertionsKt.getAsserter();
    }

    @NotNull
    public static final AssertionError AssertionErrorWithCause(@Nullable String message, @Nullable Throwable cause) {
        return AssertionsKt__AssertionsImplKt.AssertionErrorWithCause(message, cause);
    }

    public static final <T> void assertEquals(T expected, T actual, @Nullable String message) {
        AssertionsKt__AssertionsKt.assertEquals(expected, actual, message);
    }

    public static /* synthetic */ void assertEquals$default(Object object, Object object2, String string, int n, Object object3) {
        AssertionsKt__AssertionsKt.assertEquals$default(object, object2, string, n, object3);
    }

    @Deprecated(message="Provided for binary compatibility", level=DeprecationLevel.HIDDEN)
    @JvmName(name="assertFails")
    @NotNull
    public static final /* synthetic */ Throwable assertFails(@NotNull Function0<Unit> block) {
        return AssertionsKt__AssertionsImplKt.assertFails(block);
    }

    @Deprecated(message="Provided for binary compatibility", level=DeprecationLevel.HIDDEN)
    @JvmName(name="assertFails")
    @NotNull
    public static final /* synthetic */ Throwable assertFails(@Nullable String message, @NotNull Function0<Unit> block) {
        return AssertionsKt__AssertionsImplKt.assertFails(message, block);
    }

    @Deprecated(message="Provided for binary compatibility", level=DeprecationLevel.HIDDEN)
    @JvmName(name="assertFailsWith")
    @NotNull
    public static final /* synthetic */ <T extends Throwable> T assertFailsWith(@NotNull KClass<T> exceptionClass, @NotNull Function0<Unit> block) {
        return AssertionsKt__AssertionsImplKt.assertFailsWith(exceptionClass, block);
    }

    @Deprecated(message="Provided for binary compatibility", level=DeprecationLevel.HIDDEN)
    @JvmName(name="assertFailsWith")
    @NotNull
    public static final /* synthetic */ <T extends Throwable> T assertFailsWith(@NotNull KClass<T> exceptionClass, @Nullable String message, @NotNull Function0<Unit> block) {
        return AssertionsKt__AssertionsImplKt.assertFailsWith(exceptionClass, message, block);
    }

    public static final void assertFalse(boolean actual, @Nullable String message) {
        AssertionsKt__AssertionsKt.assertFalse(actual, message);
    }

    public static /* synthetic */ void assertFalse$default(boolean bl, String string, int n, Object object) {
        AssertionsKt__AssertionsKt.assertFalse$default(bl, string, n, object);
    }

    public static final void assertFalse(@Nullable String message, @NotNull Function0<Boolean> block) {
        AssertionsKt__AssertionsKt.assertFalse(message, block);
    }

    public static /* synthetic */ void assertFalse$default(String string, Function0 function0, int n, Object object) {
        AssertionsKt__AssertionsKt.assertFalse$default(string, function0, n, object);
    }

    public static final <T> void assertNotEquals(T illegal, T actual, @Nullable String message) {
        AssertionsKt__AssertionsKt.assertNotEquals(illegal, actual, message);
    }

    public static /* synthetic */ void assertNotEquals$default(Object object, Object object2, String string, int n, Object object3) {
        AssertionsKt__AssertionsKt.assertNotEquals$default(object, object2, string, n, object3);
    }

    @NotNull
    public static final <T> T assertNotNull(@Nullable T actual, @Nullable String message) {
        return AssertionsKt__AssertionsKt.assertNotNull(actual, message);
    }

    public static /* synthetic */ Object assertNotNull$default(Object object, String string, int n, Object object2) {
        return AssertionsKt__AssertionsKt.assertNotNull$default(object, string, n, object2);
    }

    public static final <T, R> void assertNotNull(@Nullable T actual, @Nullable String message, @NotNull Function1<? super T, ? extends R> block) {
        AssertionsKt__AssertionsKt.assertNotNull(actual, message, block);
    }

    public static /* synthetic */ void assertNotNull$default(Object object, String string, Function1 function1, int n, Object object2) {
        AssertionsKt__AssertionsKt.assertNotNull$default(object, string, function1, n, object2);
    }

    public static final <T> void assertNotSame(T illegal, T actual, @Nullable String message) {
        AssertionsKt__AssertionsKt.assertNotSame(illegal, actual, message);
    }

    public static /* synthetic */ void assertNotSame$default(Object object, Object object2, String string, int n, Object object3) {
        AssertionsKt__AssertionsKt.assertNotSame$default(object, object2, string, n, object3);
    }

    public static final void assertNull(@Nullable Object actual, @Nullable String message) {
        AssertionsKt__AssertionsKt.assertNull(actual, message);
    }

    public static /* synthetic */ void assertNull$default(Object object, String string, int n, Object object2) {
        AssertionsKt__AssertionsKt.assertNull$default(object, string, n, object2);
    }

    public static final <T> void assertSame(T expected, T actual, @Nullable String message) {
        AssertionsKt__AssertionsKt.assertSame(expected, actual, message);
    }

    public static /* synthetic */ void assertSame$default(Object object, Object object2, String string, int n, Object object3) {
        AssertionsKt__AssertionsKt.assertSame$default(object, object2, string, n, object3);
    }

    public static final void assertTrue(boolean actual, @Nullable String message) {
        AssertionsKt__AssertionsKt.assertTrue(actual, message);
    }

    public static /* synthetic */ void assertTrue$default(boolean bl, String string, int n, Object object) {
        AssertionsKt__AssertionsKt.assertTrue$default(bl, string, n, object);
    }

    public static final void assertTrue(@Nullable String message, @NotNull Function0<Boolean> block) {
        AssertionsKt__AssertionsKt.assertTrue(message, block);
    }

    public static /* synthetic */ void assertTrue$default(String string, Function0 function0, int n, Object object) {
        AssertionsKt__AssertionsKt.assertTrue$default(string, function0, n, object);
    }

    @PublishedApi
    @NotNull
    public static final Throwable checkResultIsFailure(@Nullable String message, @NotNull Object blockResult) {
        return AssertionsKt__AssertionsKt.checkResultIsFailure(message, blockResult);
    }

    @PublishedApi
    @NotNull
    public static final <T extends Throwable> T checkResultIsFailure(@NotNull KClass<T> exceptionClass, @Nullable String message, @NotNull Object blockResult) {
        return AssertionsKt__AssertionsImplKt.checkResultIsFailure(exceptionClass, message, blockResult);
    }

    public static final <T> void expect(T expected, @NotNull Function0<? extends T> block) {
        AssertionsKt__AssertionsKt.expect(expected, block);
    }

    public static final <T> void expect(T expected, @Nullable String message, @NotNull Function0<? extends T> block) {
        AssertionsKt__AssertionsKt.expect(expected, message, block);
    }

    @NotNull
    public static final Void fail(@Nullable String message) {
        return AssertionsKt__AssertionsKt.fail(message);
    }

    public static /* synthetic */ Void fail$default(String string, int n, Object object) {
        return AssertionsKt__AssertionsKt.fail$default(string, n, object);
    }

    @SinceKotlin(version="1.4")
    @NotNull
    public static final Void fail(@Nullable String message, @Nullable Throwable cause) {
        return AssertionsKt__AssertionsKt.fail(message, cause);
    }

    public static /* synthetic */ Void fail$default(String string, Throwable throwable, int n, Object object) {
        return AssertionsKt__AssertionsKt.fail$default(string, throwable, n, object);
    }
}

