/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins;

import java.io.InputStream;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BuiltInsResourceLoader {
    @Nullable
    public final InputStream loadResource(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        Object object = this.getClass().getClassLoader();
        if (object == null || (object = ((ClassLoader)object).getResourceAsStream(path)) == null) {
            object = ClassLoader.getSystemResourceAsStream(path);
        }
        return object;
    }
}

