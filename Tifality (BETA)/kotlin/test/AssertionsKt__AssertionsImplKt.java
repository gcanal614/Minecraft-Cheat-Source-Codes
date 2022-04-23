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
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.test.AssertionsKt;
import kotlin.test.UtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=5, d1={"\u0000B\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a \u0010\u0000\u001a\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0000\u001a\u001b\u0010\u0007\u001a\u00020\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0007\u00a2\u0006\u0002\b\u000b\u001a%\u0010\u0007\u001a\u00020\u00062\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0007\u00a2\u0006\u0002\b\u000b\u001a5\u0010\f\u001a\u0002H\r\"\b\b\u0000\u0010\r*\u00020\u00062\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\r0\u000f2\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0007\u00a2\u0006\u0004\b\u0010\u0010\u0011\u001a?\u0010\f\u001a\u0002H\r\"\b\b\u0000\u0010\r*\u00020\u00062\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\r0\u000f2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0007\u00a2\u0006\u0004\b\u0010\u0010\u0012\u001a@\u0010\u0013\u001a\u0002H\r\"\b\b\u0000\u0010\r*\u00020\u00062\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\r0\u000f2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\n0\u0015H\u0001\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016\u001a4\u0010\u0017\u001a(\u0012\f\u0012\n \u001a*\u0004\u0018\u00010\u00190\u0019 \u001a*\u0014\u0012\u000e\b\u0001\u0012\n \u001a*\u0004\u0018\u00010\u00190\u0019\u0018\u00010\u00180\u0018H\u0087\b\u00a2\u0006\u0002\u0010\u001b\u001a\u001a\u0010\u001c\u001a\u00020\n2\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0087\b\u00f8\u0001\u0001\u0082\u0002\u000b\n\u0002\b\u0019\n\u0005\b\u009920\u0001\u00a8\u0006\u001d"}, d2={"AssertionErrorWithCause", "Ljava/lang/AssertionError;", "Lkotlin/AssertionError;", "message", "", "cause", "", "assertFailsNoInline", "block", "Lkotlin/Function0;", "", "assertFails", "assertFailsWithNoInline", "T", "exceptionClass", "Lkotlin/reflect/KClass;", "assertFailsWith", "(Lkotlin/reflect/KClass;Lkotlin/jvm/functions/Function0;)Ljava/lang/Throwable;", "(Lkotlin/reflect/KClass;Ljava/lang/String;Lkotlin/jvm/functions/Function0;)Ljava/lang/Throwable;", "checkResultIsFailure", "blockResult", "Lkotlin/Result;", "(Lkotlin/reflect/KClass;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Throwable;", "currentStackTrace", "", "Ljava/lang/StackTraceElement;", "kotlin.jvm.PlatformType", "()[Ljava/lang/StackTraceElement;", "todo", "kotlin-test"}, xs="kotlin/test/AssertionsKt")
final class AssertionsKt__AssertionsImplKt {
    @PublishedApi
    @NotNull
    public static final <T extends Throwable> T checkResultIsFailure(@NotNull KClass<T> exceptionClass, @Nullable String message, @NotNull Object blockResult) {
        Intrinsics.checkNotNullParameter(exceptionClass, "exceptionClass");
        Object object = blockResult;
        boolean bl = false;
        boolean bl2 = false;
        Throwable throwable = Result.exceptionOrNull-impl(object);
        if (throwable == null) {
            Unit it = (Unit)object;
            boolean bl3 = false;
            String msg = UtilsKt.messagePrefix(message);
            Void void_ = AssertionsKt.getAsserter().fail(msg + "Expected an exception of " + JvmClassMappingKt.getJavaClass(exceptionClass) + " to be thrown, but was completed successfully.");
            throw new KotlinNothingValueException();
        }
        Throwable e = throwable;
        boolean bl4 = false;
        if (JvmClassMappingKt.getJavaClass(exceptionClass).isInstance(e)) {
            return (T)e;
        }
        Void void_ = AssertionsKt.getAsserter().fail(UtilsKt.messagePrefix(message) + "Expected an exception of " + JvmClassMappingKt.getJavaClass(exceptionClass) + " to be thrown, but was " + e, e);
        throw new KotlinNothingValueException();
    }

    @Deprecated(message="Provided for binary compatibility", level=DeprecationLevel.HIDDEN)
    @JvmName(name="assertFails")
    @NotNull
    public static final /* synthetic */ Throwable assertFails(@NotNull Function0<Unit> block) {
        Object object;
        Intrinsics.checkNotNullParameter(block, "block");
        boolean bl = false;
        String string = null;
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
        return AssertionsKt.checkResultIsFailure(string, object2);
    }

    @Deprecated(message="Provided for binary compatibility", level=DeprecationLevel.HIDDEN)
    @JvmName(name="assertFails")
    @NotNull
    public static final /* synthetic */ Throwable assertFails(@Nullable String message, @NotNull Function0<Unit> block) {
        Object object;
        Intrinsics.checkNotNullParameter(block, "block");
        boolean bl = false;
        String string = message;
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
        return AssertionsKt.checkResultIsFailure(string, object2);
    }

    @Deprecated(message="Provided for binary compatibility", level=DeprecationLevel.HIDDEN)
    @JvmName(name="assertFailsWith")
    @NotNull
    public static final /* synthetic */ <T extends Throwable> T assertFailsWith(@NotNull KClass<T> exceptionClass, @NotNull Function0<Unit> block) {
        Object object;
        Intrinsics.checkNotNullParameter(exceptionClass, "exceptionClass");
        Intrinsics.checkNotNullParameter(block, "block");
        boolean bl = false;
        String string = null;
        boolean bl2 = false;
        String string2 = string;
        KClass<T> kClass = exceptionClass;
        boolean bl3 = false;
        try {
            object = Result.Companion;
            Unit unit = block.invoke();
            boolean bl4 = false;
            object = Result.constructor-impl(unit);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl5 = false;
            object = Result.constructor-impl(ResultKt.createFailure(throwable));
        }
        Object object2 = object;
        return AssertionsKt.checkResultIsFailure(kClass, string2, object2);
    }

    @Deprecated(message="Provided for binary compatibility", level=DeprecationLevel.HIDDEN)
    @JvmName(name="assertFailsWith")
    @NotNull
    public static final /* synthetic */ <T extends Throwable> T assertFailsWith(@NotNull KClass<T> exceptionClass, @Nullable String message, @NotNull Function0<Unit> block) {
        Object object;
        Intrinsics.checkNotNullParameter(exceptionClass, "exceptionClass");
        Intrinsics.checkNotNullParameter(block, "block");
        boolean bl = false;
        String string = message;
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
        return AssertionsKt.checkResultIsFailure(kClass, string, object2);
    }

    @InlineOnly
    private static final void todo(Function0<Unit> block) {
        int $i$f$todo = 0;
        boolean bl = false;
        String string = "TODO at " + ((Throwable)new Exception()).getStackTrace()[0];
        boolean bl2 = false;
        System.out.println((Object)string);
    }

    @InlineOnly
    private static final StackTraceElement[] currentStackTrace() {
        int $i$f$currentStackTrace = 0;
        return ((Throwable)new Exception()).getStackTrace();
    }

    @NotNull
    public static final AssertionError AssertionErrorWithCause(@Nullable String message, @Nullable Throwable cause) {
        AssertionError assertionError = message == null ? new AssertionError() : new AssertionError((Object)message);
        ((Throwable)((Object)assertionError)).initCause(cause);
        return assertionError;
    }
}

