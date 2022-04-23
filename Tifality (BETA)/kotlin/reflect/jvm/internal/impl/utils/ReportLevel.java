/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

public final class ReportLevel
extends Enum<ReportLevel> {
    public static final /* enum */ ReportLevel IGNORE;
    public static final /* enum */ ReportLevel WARN;
    public static final /* enum */ ReportLevel STRICT;
    private static final /* synthetic */ ReportLevel[] $VALUES;
    @NotNull
    private final String description;
    public static final Companion Companion;

    static {
        ReportLevel[] reportLevelArray = new ReportLevel[3];
        ReportLevel[] reportLevelArray2 = reportLevelArray;
        reportLevelArray[0] = IGNORE = new ReportLevel("ignore");
        reportLevelArray[1] = WARN = new ReportLevel("warn");
        reportLevelArray[2] = STRICT = new ReportLevel("strict");
        $VALUES = reportLevelArray;
        Companion = new Companion(null);
    }

    public final boolean isWarning() {
        return this == WARN;
    }

    public final boolean isIgnore() {
        return this == IGNORE;
    }

    @NotNull
    public final String getDescription() {
        return this.description;
    }

    private ReportLevel(String description2) {
        this.description = description2;
    }

    public static ReportLevel[] values() {
        return (ReportLevel[])$VALUES.clone();
    }

    public static ReportLevel valueOf(String string) {
        return Enum.valueOf(ReportLevel.class, string);
    }

    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

