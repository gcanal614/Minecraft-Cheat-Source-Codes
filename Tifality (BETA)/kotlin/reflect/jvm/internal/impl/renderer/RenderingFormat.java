/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.renderer;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public abstract class RenderingFormat
extends Enum<RenderingFormat> {
    public static final /* enum */ RenderingFormat PLAIN;
    public static final /* enum */ RenderingFormat HTML;
    private static final /* synthetic */ RenderingFormat[] $VALUES;

    static {
        RenderingFormat[] renderingFormatArray = new RenderingFormat[2];
        RenderingFormat[] renderingFormatArray2 = renderingFormatArray;
        renderingFormatArray[0] = PLAIN = new PLAIN("PLAIN", 0);
        renderingFormatArray[1] = HTML = new HTML("HTML", 1);
        $VALUES = renderingFormatArray;
    }

    @NotNull
    public abstract String escape(@NotNull String var1);

    private RenderingFormat() {
    }

    public /* synthetic */ RenderingFormat(String $enum$name, int $enum$ordinal, DefaultConstructorMarker $constructor_marker) {
        this();
    }

    public static RenderingFormat[] values() {
        return (RenderingFormat[])$VALUES.clone();
    }

    public static RenderingFormat valueOf(String string) {
        return Enum.valueOf(RenderingFormat.class, string);
    }

    static final class PLAIN
    extends RenderingFormat {
        @Override
        @NotNull
        public String escape(@NotNull String string) {
            Intrinsics.checkNotNullParameter(string, "string");
            return string;
        }

        /*
         * WARNING - void declaration
         */
        PLAIN() {
            void var1_1;
        }
    }

    static final class HTML
    extends RenderingFormat {
        @Override
        @NotNull
        public String escape(@NotNull String string) {
            Intrinsics.checkNotNullParameter(string, "string");
            return StringsKt.replace$default(StringsKt.replace$default(string, "<", "&lt;", false, 4, null), ">", "&gt;", false, 4, null);
        }

        /*
         * WARNING - void declaration
         */
        HTML() {
            void var1_1;
        }
    }
}

