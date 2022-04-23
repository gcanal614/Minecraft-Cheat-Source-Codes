/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.name;

import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;

public final class NameUtils {
    private static final Regex SANITIZE_AS_JAVA_INVALID_CHARACTERS;
    public static final NameUtils INSTANCE;

    @JvmStatic
    @NotNull
    public static final String sanitizeAsJavaIdentifier(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return SANITIZE_AS_JAVA_INVALID_CHARACTERS.replace((CharSequence)name, "_");
    }

    private NameUtils() {
    }

    static {
        NameUtils nameUtils;
        INSTANCE = nameUtils = new NameUtils();
        String string = "[^\\p{L}\\p{Digit}]";
        boolean bl = false;
        SANITIZE_AS_JAVA_INVALID_CHARACTERS = new Regex(string);
    }
}

