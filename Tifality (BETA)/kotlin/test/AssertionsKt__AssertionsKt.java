/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.test;

import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.test.Asserter;
import kotlin.test.AsserterLookupKt;
import kotlin.test.AssertionsKt;
import kotlin.test.UtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=5, d1={"\u0000^\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0001\n\u0002\b\u0002\u001a2\u0010\n\u001a\u00020\u000b\"\t\b\u0000\u0010\f\u00a2\u0006\u0002\b\r2\u0006\u0010\u000e\u001a\u0002H\f2\u0006\u0010\u000f\u001a\u0002H\f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\u0002\u0010\u0012\u001a\u001f\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0016H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\b\u0017\u001a)\u0010\u0013\u001a\u00020\u00142\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0016H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\b\u0017\u001a7\u0010\u0018\u001a\u0002H\f\"\n\b\u0000\u0010\f\u0018\u0001*\u00020\u00142\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0016H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019\u001a9\u0010\u0018\u001a\u0002H\f\"\b\b\u0000\u0010\f*\u00020\u00142\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\f0\u001b2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0016H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001c\u0010\u001d\u001aC\u0010\u0018\u001a\u0002H\f\"\b\b\u0000\u0010\f*\u00020\u00142\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\f0\u001b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0016H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001c\u0010\u001e\u001a'\u0010\u001f\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020 2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0082\u0002\n\n\b\b\u0000\u001a\u0004\b\u0001\u0010\u0001\u001a-\u0010\u001f\u001a\u00020\u000b2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020 0\u0016\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001a2\u0010!\u001a\u00020\u000b\"\t\b\u0000\u0010\f\u00a2\u0006\u0002\b\r2\u0006\u0010\"\u001a\u0002H\f2\u0006\u0010\u000f\u001a\u0002H\f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\u0002\u0010\u0012\u001a8\u0010#\u001a\u0002H\f\"\b\b\u0000\u0010\f*\u00020$2\b\u0010\u000f\u001a\u0004\u0018\u0001H\f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0082\u0002\n\n\b\b\u0000\u001a\u0004\b\u0003\u0010\u0001\u00a2\u0006\u0002\u0010%\u001aR\u0010#\u001a\u00020\u000b\"\b\b\u0000\u0010\f*\u00020$\"\u0004\b\u0001\u0010&2\b\u0010\u000f\u001a\u0004\u0018\u0001H\f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H&0'\u0082\u0002\n\n\b\b\u0000\u001a\u0004\b\u0003\u0010\u0001\u00a2\u0006\u0002\u0010(\u001a2\u0010)\u001a\u00020\u000b\"\t\b\u0000\u0010\f\u00a2\u0006\u0002\b\r2\u0006\u0010\"\u001a\u0002H\f2\u0006\u0010\u000f\u001a\u0002H\f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\u0002\u0010\u0012\u001a\u001c\u0010*\u001a\u00020\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010$2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u001a2\u0010+\u001a\u00020\u000b\"\t\b\u0000\u0010\f\u00a2\u0006\u0002\b\r2\u0006\u0010\u000e\u001a\u0002H\f2\u0006\u0010\u000f\u001a\u0002H\f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\u0002\u0010\u0012\u001a%\u0010,\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020 2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0082\u0002\b\n\u0006\b\u0000\u001a\u0002\u0010\u0001\u001a-\u0010,\u001a\u00020\u000b2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020 0\u0016\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001a(\u0010-\u001a\u00020\u00142\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u000b0/H\u0001\u00f8\u0001\u0001\u00a2\u0006\u0002\u00100\u001a9\u00101\u001a\u00020\u000b\"\t\b\u0000\u0010\f\u00a2\u0006\u0002\b\r2\u0006\u0010\u000e\u001a\u0002H\f2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\f0\u0016\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u00a2\u0006\u0002\u00102\u001aC\u00101\u001a\u00020\u000b\"\t\b\u0000\u0010\f\u00a2\u0006\u0002\b\r2\u0006\u0010\u000e\u001a\u0002H\f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\f0\u0016\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0003 \u0001\u00a2\u0006\u0002\u00103\u001a\u0012\u00104\u001a\u0002052\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u001a \u00104\u001a\u0002052\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\n\b\u0002\u00106\u001a\u0004\u0018\u00010\u0014H\u0007\"&\u0010\u0000\u001a\u0004\u0018\u00010\u00018\u0000@\u0000X\u0081\u000e\u00a2\u0006\u0014\n\u0000\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007\"\u0011\u0010\b\u001a\u00020\u00018F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\u0005\u0082\u0002\u000b\n\u0005\b\u009920\u0001\n\u0002\b\u0019\u00a8\u00067"}, d2={"_asserter", "Lkotlin/test/Asserter;", "get_asserter$annotations", "()V", "get_asserter", "()Lkotlin/test/Asserter;", "set_asserter", "(Lkotlin/test/Asserter;)V", "asserter", "getAsserter", "assertEquals", "", "T", "Lkotlin/internal/OnlyInputTypes;", "expected", "actual", "message", "", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/String;)V", "assertFails", "", "block", "Lkotlin/Function0;", "assertFailsInline", "assertFailsWith", "(Ljava/lang/String;Lkotlin/jvm/functions/Function0;)Ljava/lang/Throwable;", "exceptionClass", "Lkotlin/reflect/KClass;", "assertFailsWithInline", "(Lkotlin/reflect/KClass;Lkotlin/jvm/functions/Function0;)Ljava/lang/Throwable;", "(Lkotlin/reflect/KClass;Ljava/lang/String;Lkotlin/jvm/functions/Function0;)Ljava/lang/Throwable;", "assertFalse", "", "assertNotEquals", "illegal", "assertNotNull", "", "(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;", "R", "Lkotlin/Function1;", "(Ljava/lang/Object;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)V", "assertNotSame", "assertNull", "assertSame", "assertTrue", "checkResultIsFailure", "blockResult", "Lkotlin/Result;", "(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Throwable;", "expect", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)V", "(Ljava/lang/Object;Ljava/lang/String;Lkotlin/jvm/functions/Function0;)V", "fail", "", "cause", "kotlin-test"}, xs="kotlin/test/AssertionsKt")
final class AssertionsKt__AssertionsKt {
    @Nullable
    private static Asserter _asserter;

    @NotNull
    public static final Asserter getAsserter() {
        Asserter asserter = _asserter;
        if (asserter == null) {
            asserter = AsserterLookupKt.lookupAsserter();
        }
        return asserter;
    }

    public static /* synthetic */ void get_asserter$annotations() {
    }

    @Nullable
    public static final Asserter get_asserter() {
        return _asserter;
    }

    public static final void set_asserter(@Nullable Asserter asserter) {
        _asserter = asserter;
    }

    public static final void assertTrue(@Nullable String message, @NotNull Function0<Boolean> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        boolean bl = false;
        AssertionsKt.assertTrue(block.invoke(), message);
    }

    public static /* synthetic */ void assertTrue$default(String string, Function0 function0, int n, Object object) {
        if ((n & 1) != 0) {
            string = null;
        }
        AssertionsKt.assertTrue(string, function0);
    }

    public static final void assertTrue(boolean actual, @Nullable String message) {
        boolean bl = false;
        Asserter asserter = AssertionsKt.getAsserter();
        String string = message;
        if (string == null) {
            string = "Expected value to be true.";
        }
        asserter.assertTrue(string, actual);
    }

    public static /* synthetic */ void assertTrue$default(boolean bl, String string, int n, Object object) {
        if ((n & 2) != 0) {
            string = null;
        }
        AssertionsKt.assertTrue(bl, string);
    }

    public static final void assertFalse(@Nullable String message, @NotNull Function0<Boolean> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        boolean bl = false;
        AssertionsKt.assertFalse(block.invoke(), message);
    }

    public static /* synthetic */ void assertFalse$default(String string, Function0 function0, int n, Object object) {
        if ((n & 1) != 0) {
            string = null;
        }
        AssertionsKt.assertFalse(string, function0);
    }

    public static final void assertFalse(boolean actual, @Nullable String message) {
        boolean bl = false;
        Asserter asserter = AssertionsKt.getAsserter();
        String string = message;
        if (string == null) {
            string = "Expected value to be false.";
        }
        asserter.assertTrue(string, !actual);
    }

    public static /* synthetic */ void assertFalse$default(boolean bl, String string, int n, Object object) {
        if ((n & 2) != 0) {
            string = null;
        }
        AssertionsKt.assertFalse(bl, string);
    }

    public static final <T> void assertEquals(T expected, T actual, @Nullable String message) {
        AssertionsKt.getAsserter().assertEquals(message, expected, actual);
    }

    public static /* synthetic */ void assertEquals$default(Object object, Object object2, String string, int n, Object object3) {
        if ((n & 4) != 0) {
            string = null;
        }
        AssertionsKt.assertEquals(object, object2, string);
    }

    public static final <T> void assertNotEquals(T illegal, T actual, @Nullable String message) {
        AssertionsKt.getAsserter().assertNotEquals(message, illegal, actual);
    }

    public static /* synthetic */ void assertNotEquals$default(Object object, Object object2, String string, int n, Object object3) {
        if ((n & 4) != 0) {
            string = null;
        }
        AssertionsKt.assertNotEquals(object, object2, string);
    }

    public static final <T> void assertSame(T expected, T actual, @Nullable String message) {
        AssertionsKt.getAsserter().assertSame(message, expected, actual);
    }

    public static /* synthetic */ void assertSame$default(Object object, Object object2, String string, int n, Object object3) {
        if ((n & 4) != 0) {
            string = null;
        }
        AssertionsKt.assertSame(object, object2, string);
    }

    public static final <T> void assertNotSame(T illegal, T actual, @Nullable String message) {
        AssertionsKt.getAsserter().assertNotSame(message, illegal, actual);
    }

    public static /* synthetic */ void assertNotSame$default(Object object, Object object2, String string, int n, Object object3) {
        if ((n & 4) != 0) {
            string = null;
        }
        AssertionsKt.assertNotSame(object, object2, string);
    }

    @NotNull
    public static final <T> T assertNotNull(@Nullable T actual, @Nullable String message) {
        boolean bl = false;
        AssertionsKt.getAsserter().assertNotNull(message, actual);
        T t = actual;
        Intrinsics.checkNotNull(t);
        return t;
    }

    public static /* synthetic */ Object assertNotNull$default(Object object, String string, int n, Object object2) {
        if ((n & 2) != 0) {
            string = null;
        }
        return AssertionsKt.assertNotNull(object, string);
    }

    public static final <T, R> void assertNotNull(@Nullable T actual, @Nullable String message, @NotNull Function1<? super T, ? extends R> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        boolean bl = false;
        AssertionsKt.getAsserter().assertNotNull(message, actual);
        if (actual != null) {
            block.invoke(actual);
        }
    }

    public static /* synthetic */ void assertNotNull$default(Object object, String string, Function1 function1, int n, Object object2) {
        if ((n & 2) != 0) {
            string = null;
        }
        AssertionsKt.assertNotNull(object, string, function1);
    }

    public static final void assertNull(@Nullable Object actual, @Nullable String message) {
        AssertionsKt.getAsserter().assertNull(message, actual);
    }

    public static /* synthetic */ void assertNull$default(Object object, String string, int n, Object object2) {
        if ((n & 2) != 0) {
            string = null;
        }
        AssertionsKt.assertNull(object, string);
    }

    @NotNull
    public static final Void fail(@Nullable String message) {
        Void void_ = AssertionsKt.getAsserter().fail(message);
        throw new KotlinNothingValueException();
    }

    public static /* synthetic */ Void fail$default(String string, int n, Object object) {
        if ((n & 1) != 0) {
            string = null;
        }
        return AssertionsKt.fail(string);
    }

    @SinceKotlin(version="1.4")
    @NotNull
    public static final Void fail(@Nullable String message, @Nullable Throwable cause) {
        Void void_ = AssertionsKt.getAsserter().fail(message, cause);
        throw new KotlinNothingValueException();
    }

    public static /* synthetic */ Void fail$default(String string, Throwable throwable, int n, Object object) {
        if ((n & 1) != 0) {
            string = null;
        }
        if ((n & 2) != 0) {
            throwable = null;
        }
        return AssertionsKt.fail(string, throwable);
    }

    public static final <T> void expect(T expected, @NotNull Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        boolean bl = false;
        AssertionsKt.assertEquals$default(expected, block.invoke(), null, 4, null);
    }

    public static final <T> void expect(T expected, @Nullable String message, @NotNull Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        boolean bl = false;
        AssertionsKt.assertEquals(expected, block.invoke(), message);
    }

    @InlineOnly
    @JvmName(name="assertFailsInline")
    private static final Throwable assertFailsInline(Function0<Unit> block) {
        Object object;
        int $i$f$assertFailsInline = 0;
        String string = null;
        boolean bl = false;
        try {
            object = Result.Companion;
            Unit unit = block.invoke();
            boolean bl2 = false;
            object = Result.constructor-impl(unit);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl3 = false;
            object = Result.constructor-impl(ResultKt.createFailure(throwable));
        }
        Object object2 = object;
        return AssertionsKt.checkResultIsFailure(string, object2);
    }

    @SinceKotlin(version="1.1")
    @InlineOnly
    @JvmName(name="assertFailsInline")
    private static final Throwable assertFailsInline(String message, Function0<Unit> block) {
        Object object;
        int $i$f$assertFailsInline = 0;
        String string = message;
        boolean bl = false;
        try {
            object = Result.Companion;
            Unit unit = block.invoke();
            boolean bl2 = false;
            object = Result.constructor-impl(unit);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl3 = false;
            object = Result.constructor-impl(ResultKt.createFailure(throwable));
        }
        Object object2 = object;
        return AssertionsKt.checkResultIsFailure(string, object2);
    }

    @PublishedApi
    @NotNull
    public static final Throwable checkResultIsFailure(@Nullable String message, @NotNull Object blockResult) {
        Object object = blockResult;
        boolean bl = false;
        boolean bl2 = false;
        Throwable throwable = Result.exceptionOrNull-impl(object);
        if (throwable == null) {
            Unit it = (Unit)object;
            boolean bl3 = false;
            Void void_ = AssertionsKt.getAsserter().fail(UtilsKt.messagePrefix(message) + "Expected an exception to be thrown, but was completed successfully.");
            throw new KotlinNothingValueException();
        }
        Throwable e = throwable;
        boolean bl4 = false;
        return e;
    }

    @InlineOnly
    private static final /* synthetic */ <T extends Throwable> T assertFailsWith(String message, Function0<Unit> block) {
        Object object;
        int $i$f$assertFailsWith = 0;
        Intrinsics.reifiedOperationMarker(4, "T");
        KClass kClass = Reflection.getOrCreateKotlinClass(Throwable.class);
        boolean bl = false;
        String string = message;
        KClass kClass2 = kClass;
        boolean bl2 = false;
        try {
            object = Result.Companion;
            Unit unit = block.invoke();
            boolean bl3 = false;
            object = Result.constructor-impl(unit);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl4 = false;
            object = Result.constructor-impl(ResultKt.createFailure(throwable));
        }
        Object object2 = object;
        return AssertionsKt.checkResultIsFailure(kClass2, string, object2);
    }

    static /* synthetic */ Throwable assertFailsWith$default(String message, Function0 block, int n, Object object) {
        Object object2;
        if ((n & 1) != 0) {
            message = null;
        }
        boolean $i$f$assertFailsWith = false;
        Intrinsics.reifiedOperationMarker(4, "T");
        object = Reflection.getOrCreateKotlinClass(Throwable.class);
        boolean bl = false;
        String string = message;
        Object object3 = object;
        boolean bl2 = false;
        try {
            object2 = Result.Companion;
            Object r = block.invoke();
            boolean bl3 = false;
            object2 = Result.constructor-impl(r);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl4 = false;
            object2 = Result.constructor-impl(ResultKt.createFailure(throwable));
        }
        Object object4 = object2;
        return AssertionsKt.checkResultIsFailure(object3, string, object4);
    }

    @InlineOnly
    @JvmName(name="assertFailsWithInline")
    private static final <T extends Throwable> T assertFailsWithInline(KClass<T> exceptionClass, Function0<Unit> block) {
        Object object;
        int $i$f$assertFailsWithInline = 0;
        String string = null;
        boolean bl = false;
        String string2 = string;
        KClass<T> kClass = exceptionClass;
        boolean bl2 = false;
        try {
            object = Result.Companion;
            Unit unit = block.invoke();
            boolean bl3 = false;
            object = Result.constructor-impl(unit);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl4 = false;
            object = Result.constructor-impl(ResultKt.createFailure(throwable));
        }
        Object object2 = object;
        return AssertionsKt.checkResultIsFailure(kClass, string2, object2);
    }

    @InlineOnly
    @JvmName(name="assertFailsWithInline")
    private static final <T extends Throwable> T assertFailsWithInline(KClass<T> exceptionClass, String message, Function0<Unit> block) {
        Object object;
        int $i$f$assertFailsWithInline = 0;
        String string = message;
        KClass<T> kClass = exceptionClass;
        boolean bl = false;
        try {
            object = Result.Companion;
            Unit unit = block.invoke();
            boolean bl2 = false;
            object = Result.constructor-impl(unit);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl3 = false;
            object = Result.constructor-impl(ResultKt.createFailure(throwable));
        }
        Object object2 = object;
        return AssertionsKt.checkResultIsFailure(kClass, string, object2);
    }
}

