/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.utils.ReportLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Jsr305State {
    @NotNull
    private final Lazy description$delegate;
    @NotNull
    private final ReportLevel global;
    @Nullable
    private final ReportLevel migration;
    @NotNull
    private final Map<String, ReportLevel> user;
    private final boolean enableCompatqualCheckerFrameworkAnnotations;
    @JvmField
    @NotNull
    public static final Jsr305State DEFAULT;
    @JvmField
    @NotNull
    public static final Jsr305State DISABLED;
    @JvmField
    @NotNull
    public static final Jsr305State STRICT;
    public static final Companion Companion;

    public final boolean getDisabled() {
        return this == DISABLED;
    }

    @NotNull
    public final ReportLevel getGlobal() {
        return this.global;
    }

    @Nullable
    public final ReportLevel getMigration() {
        return this.migration;
    }

    @NotNull
    public final Map<String, ReportLevel> getUser() {
        return this.user;
    }

    public final boolean getEnableCompatqualCheckerFrameworkAnnotations() {
        return this.enableCompatqualCheckerFrameworkAnnotations;
    }

    public Jsr305State(@NotNull ReportLevel global, @Nullable ReportLevel migration, @NotNull Map<String, ? extends ReportLevel> user, boolean enableCompatqualCheckerFrameworkAnnotations) {
        Intrinsics.checkNotNullParameter((Object)global, "global");
        Intrinsics.checkNotNullParameter(user, "user");
        this.global = global;
        this.migration = migration;
        this.user = user;
        this.enableCompatqualCheckerFrameworkAnnotations = enableCompatqualCheckerFrameworkAnnotations;
        this.description$delegate = LazyKt.lazy((Function0)new Function0<String[]>(this){
            final /* synthetic */ Jsr305State this$0;

            @NotNull
            public final String[] invoke() {
                boolean bl = false;
                List result2 = new ArrayList<E>();
                result2.add(this.this$0.getGlobal().getDescription());
                ReportLevel reportLevel = this.this$0.getMigration();
                if (reportLevel != null) {
                    ReportLevel reportLevel2 = reportLevel;
                    boolean bl2 = false;
                    boolean bl3 = false;
                    ReportLevel it = reportLevel2;
                    boolean bl4 = false;
                    result2.add("under-migration:" + it.getDescription());
                }
                Map<String, ReportLevel> $this$forEach$iv = this.this$0.getUser();
                boolean $i$f$forEach = false;
                Map<String, ReportLevel> map2 = $this$forEach$iv;
                boolean bl5 = false;
                Iterator<Map.Entry<String, ReportLevel>> iterator2 = map2.entrySet().iterator();
                while (iterator2.hasNext()) {
                    Map.Entry<String, ReportLevel> element$iv;
                    Map.Entry<String, ReportLevel> it = element$iv = iterator2.next();
                    boolean bl6 = false;
                    result2.add('@' + it.getKey() + ':' + it.getValue().getDescription());
                }
                Collection $this$toTypedArray$iv = result2;
                boolean $i$f$toTypedArray = false;
                Collection thisCollection$iv = $this$toTypedArray$iv;
                String[] stringArray = thisCollection$iv.toArray(new String[0]);
                if (stringArray == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
                }
                return stringArray;
            }
            {
                this.this$0 = jsr305State;
                super(0);
            }
        });
    }

    public /* synthetic */ Jsr305State(ReportLevel reportLevel, ReportLevel reportLevel2, Map map2, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 8) != 0) {
            bl = true;
        }
        this(reportLevel, reportLevel2, map2, bl);
    }

    static {
        Companion = new Companion(null);
        DEFAULT = new Jsr305State(ReportLevel.WARN, null, MapsKt.emptyMap(), false, 8, null);
        DISABLED = new Jsr305State(ReportLevel.IGNORE, ReportLevel.IGNORE, MapsKt.emptyMap(), false, 8, null);
        STRICT = new Jsr305State(ReportLevel.STRICT, ReportLevel.STRICT, MapsKt.emptyMap(), false, 8, null);
    }

    @NotNull
    public String toString() {
        return "Jsr305State(global=" + (Object)((Object)this.global) + ", migration=" + (Object)((Object)this.migration) + ", user=" + this.user + ", enableCompatqualCheckerFrameworkAnnotations=" + this.enableCompatqualCheckerFrameworkAnnotations + ")";
    }

    public int hashCode() {
        ReportLevel reportLevel = this.global;
        ReportLevel reportLevel2 = this.migration;
        Map<String, ReportLevel> map2 = this.user;
        int n = (((reportLevel != null ? ((Object)((Object)reportLevel)).hashCode() : 0) * 31 + (reportLevel2 != null ? ((Object)((Object)reportLevel2)).hashCode() : 0)) * 31 + (map2 != null ? ((Object)map2).hashCode() : 0)) * 31;
        int n2 = this.enableCompatqualCheckerFrameworkAnnotations ? 1 : 0;
        if (n2 != 0) {
            n2 = 1;
        }
        return n + n2;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof Jsr305State)) break block3;
                Jsr305State jsr305State = (Jsr305State)object;
                if (!Intrinsics.areEqual((Object)this.global, (Object)jsr305State.global) || !Intrinsics.areEqual((Object)this.migration, (Object)jsr305State.migration) || !Intrinsics.areEqual(this.user, jsr305State.user) || this.enableCompatqualCheckerFrameworkAnnotations != jsr305State.enableCompatqualCheckerFrameworkAnnotations) break block3;
            }
            return true;
        }
        return false;
    }

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

