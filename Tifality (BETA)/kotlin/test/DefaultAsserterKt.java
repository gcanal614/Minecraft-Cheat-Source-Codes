/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.test;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.test.DefaultAsserter;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0007\u00a8\u0006\u0002"}, d2={"DefaultAsserter", "Lkotlin/test/DefaultAsserter;", "kotlin-test"})
public final class DefaultAsserterKt {
    @Deprecated(message="DefaultAsserter is an object now, constructor call is not required anymore", replaceWith=@ReplaceWith(imports={"kotlin.test.DefaultAsserter"}, expression="DefaultAsserter"), level=DeprecationLevel.ERROR)
    @NotNull
    public static final DefaultAsserter DefaultAsserter() {
        return DefaultAsserter.INSTANCE;
    }
}

