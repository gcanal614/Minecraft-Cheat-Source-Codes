/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import org.jetbrains.annotations.Nullable;

public interface SourceFile {
    public static final SourceFile NO_SOURCE_FILE = new SourceFile(){

        @Override
        @Nullable
        public String getName() {
            return null;
        }
    };

    @Nullable
    public String getName();
}

