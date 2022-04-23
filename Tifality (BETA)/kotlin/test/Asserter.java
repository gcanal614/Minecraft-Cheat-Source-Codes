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
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.test.UtilsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u0003\n\u0000\bf\u0018\u00002\u00020\u0001J&\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001H\u0016J&\u0010\b\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\t\u001a\u0004\u0018\u00010\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001H\u0016J\u001c\u0010\n\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001H\u0016J&\u0010\u000b\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\t\u001a\u0004\u0018\u00010\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001H\u0016J\u001c\u0010\f\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001H\u0016J&\u0010\r\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001H\u0016J \u0010\u000e\u001a\u00020\u00032\u000e\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00102\u0006\u0010\u0007\u001a\u00020\u0011H\u0016J\u001a\u0010\u000e\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0007\u001a\u00020\u0011H\u0016J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&J\u001c\u0010\u0012\u001a\u00020\u00132\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H'\u00a8\u0006\u0016"}, d2={"Lkotlin/test/Asserter;", "", "assertEquals", "", "message", "", "expected", "actual", "assertNotEquals", "illegal", "assertNotNull", "assertNotSame", "assertNull", "assertSame", "assertTrue", "lazyMessage", "Lkotlin/Function0;", "", "fail", "", "cause", "", "kotlin-test"})
public interface Asserter {
    @NotNull
    public Void fail(@Nullable String var1);

    @SinceKotlin(version="1.4")
    @NotNull
    public Void fail(@Nullable String var1, @Nullable Throwable var2);

    public void assertTrue(@NotNull Function0<String> var1, boolean var2);

    public void assertTrue(@Nullable String var1, boolean var2);

    public void assertEquals(@Nullable String var1, @Nullable Object var2, @Nullable Object var3);

    public void assertNotEquals(@Nullable String var1, @Nullable Object var2, @Nullable Object var3);

    public void assertSame(@Nullable String var1, @Nullable Object var2, @Nullable Object var3);

    public void assertNotSame(@Nullable String var1, @Nullable Object var2, @Nullable Object var3);

    public void assertNull(@Nullable String var1, @Nullable Object var2);

    public void assertNotNull(@Nullable String var1, @Nullable Object var2);

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=3)
    public static final class DefaultImpls {
        public static void assertTrue(@NotNull Asserter $this, @NotNull Function0<String> lazyMessage, boolean actual) {
            Intrinsics.checkNotNullParameter(lazyMessage, "lazyMessage");
            if (!actual) {
                Void void_ = $this.fail(lazyMessage.invoke());
                throw new KotlinNothingValueException();
            }
        }

        public static void assertTrue(@NotNull Asserter $this, @Nullable String message, boolean actual) {
            $this.assertTrue(new Function0<String>(message){
                final /* synthetic */ String $message;

                @Nullable
                public final String invoke() {
                    return this.$message;
                }
                {
                    this.$message = string;
                    super(0);
                }
            }, actual);
        }

        public static void assertEquals(@NotNull Asserter $this, @Nullable String message, @Nullable Object expected, @Nullable Object actual) {
            $this.assertTrue(new Function0<String>(message, expected, actual){
                final /* synthetic */ String $message;
                final /* synthetic */ Object $expected;
                final /* synthetic */ Object $actual;

                @Nullable
                public final String invoke() {
                    return UtilsKt.messagePrefix(this.$message) + "Expected <" + this.$expected + ">, actual <" + this.$actual + ">.";
                }
                {
                    this.$message = string;
                    this.$expected = object;
                    this.$actual = object2;
                    super(0);
                }
            }, Intrinsics.areEqual(actual, expected));
        }

        public static void assertNotEquals(@NotNull Asserter $this, @Nullable String message, @Nullable Object illegal, @Nullable Object actual) {
            $this.assertTrue(new Function0<String>(message, actual){
                final /* synthetic */ String $message;
                final /* synthetic */ Object $actual;

                @Nullable
                public final String invoke() {
                    return UtilsKt.messagePrefix(this.$message) + "Illegal value: <" + this.$actual + ">.";
                }
                {
                    this.$message = string;
                    this.$actual = object;
                    super(0);
                }
            }, Intrinsics.areEqual(actual, illegal) ^ true);
        }

        public static void assertSame(@NotNull Asserter $this, @Nullable String message, @Nullable Object expected, @Nullable Object actual) {
            $this.assertTrue(new Function0<String>(message, expected, actual){
                final /* synthetic */ String $message;
                final /* synthetic */ Object $expected;
                final /* synthetic */ Object $actual;

                @Nullable
                public final String invoke() {
                    return UtilsKt.messagePrefix(this.$message) + "Expected <" + this.$expected + ">, actual <" + this.$actual + "> is not same.";
                }
                {
                    this.$message = string;
                    this.$expected = object;
                    this.$actual = object2;
                    super(0);
                }
            }, actual == expected);
        }

        public static void assertNotSame(@NotNull Asserter $this, @Nullable String message, @Nullable Object illegal, @Nullable Object actual) {
            $this.assertTrue(new Function0<String>(message, actual){
                final /* synthetic */ String $message;
                final /* synthetic */ Object $actual;

                @Nullable
                public final String invoke() {
                    return UtilsKt.messagePrefix(this.$message) + "Expected not same as <" + this.$actual + ">.";
                }
                {
                    this.$message = string;
                    this.$actual = object;
                    super(0);
                }
            }, actual != illegal);
        }

        public static void assertNull(@NotNull Asserter $this, @Nullable String message, @Nullable Object actual) {
            $this.assertTrue(new Function0<String>(message, actual){
                final /* synthetic */ String $message;
                final /* synthetic */ Object $actual;

                @Nullable
                public final String invoke() {
                    return UtilsKt.messagePrefix(this.$message) + "Expected value to be null, but was: <" + this.$actual + ">.";
                }
                {
                    this.$message = string;
                    this.$actual = object;
                    super(0);
                }
            }, actual == null);
        }

        public static void assertNotNull(@NotNull Asserter $this, @Nullable String message, @Nullable Object actual) {
            $this.assertTrue(new Function0<String>(message){
                final /* synthetic */ String $message;

                @Nullable
                public final String invoke() {
                    return UtilsKt.messagePrefix(this.$message) + "Expected value to be not null.";
                }
                {
                    this.$message = string;
                    super(0);
                }
            }, actual != null);
        }
    }
}

