/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.renderer.KeywordStringsGenerated;
import org.jetbrains.annotations.NotNull;

public final class RenderingUtilsKt {
    @NotNull
    public static final String render(@NotNull Name $this$render) {
        String string;
        Intrinsics.checkNotNullParameter($this$render, "$this$render");
        if (RenderingUtilsKt.shouldBeEscaped($this$render)) {
            StringBuilder stringBuilder = new StringBuilder();
            char c = '`';
            String string2 = $this$render.asString();
            Intrinsics.checkNotNullExpressionValue(string2, "asString()");
            String string3 = string2;
            boolean bl = false;
            string = stringBuilder.append(String.valueOf(c) + string3).append('`').toString();
        } else {
            String string4 = $this$render.asString();
            string = string4;
            Intrinsics.checkNotNullExpressionValue(string4, "asString()");
        }
        return string;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static final boolean shouldBeEscaped(Name $this$shouldBeEscaped) {
        if ($this$shouldBeEscaped.isSpecial()) {
            return false;
        }
        String string = $this$shouldBeEscaped.asString();
        Intrinsics.checkNotNullExpressionValue(string, "asString()");
        String string2 = string;
        if (KeywordStringsGenerated.KEYWORDS.contains(string2)) return true;
        CharSequence $this$any$iv = string2;
        boolean $i$f$any = false;
        CharSequence charSequence = $this$any$iv;
        int n = 0;
        while (n < charSequence.length()) {
            char element$iv;
            char it = element$iv = charSequence.charAt(n);
            boolean bl = false;
            if (!Character.isLetterOrDigit(it) && it != '_') {
                return true;
            }
            boolean bl2 = false;
            if (bl2) {
                return true;
            }
            ++n;
        }
        return false;
    }

    @NotNull
    public static final String render(@NotNull FqNameUnsafe $this$render) {
        Intrinsics.checkNotNullParameter($this$render, "$this$render");
        List<Name> list = $this$render.pathSegments();
        Intrinsics.checkNotNullExpressionValue(list, "pathSegments()");
        return RenderingUtilsKt.renderFqName(list);
    }

    @NotNull
    public static final String renderFqName(@NotNull List<Name> pathSegments) {
        Intrinsics.checkNotNullParameter(pathSegments, "pathSegments");
        boolean bl = false;
        boolean bl2 = false;
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl3 = false;
        boolean bl4 = false;
        StringBuilder $this$buildString = stringBuilder;
        boolean bl5 = false;
        for (Name element : pathSegments) {
            if ($this$buildString.length() > 0) {
                $this$buildString.append(".");
            }
            $this$buildString.append(RenderingUtilsKt.render(element));
        }
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }
}

