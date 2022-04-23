/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.test;

import java.util.List;
import java.util.ServiceLoader;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.test.Asserter;
import kotlin.test.AsserterContributor;
import kotlin.test.DefaultAsserter;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=2, d1={"\u0000\u0014\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\b\u0010\u0004\u001a\u00020\u0005H\u0000\"\u001c\u0010\u0000\u001a\u0010\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2={"contributors", "", "Lkotlin/test/AsserterContributor;", "kotlin.jvm.PlatformType", "lookupAsserter", "Lkotlin/test/Asserter;", "kotlin-test"})
public final class AsserterLookupKt {
    private static final List<AsserterContributor> contributors;

    @NotNull
    public static final Asserter lookupAsserter() {
        for (AsserterContributor contributor : contributors) {
            Asserter asserter = contributor.contribute();
            if (asserter == null) continue;
            return asserter;
        }
        return DefaultAsserter.INSTANCE;
    }

    static {
        ServiceLoader<AsserterContributor> serviceLoader = ServiceLoader.load(AsserterContributor.class);
        Intrinsics.checkNotNullExpressionValue(serviceLoader, "ServiceLoader.load(Asser\u2026rContributor::class.java)");
        contributors = CollectionsKt.toList((Iterable)serviceLoader);
    }
}

