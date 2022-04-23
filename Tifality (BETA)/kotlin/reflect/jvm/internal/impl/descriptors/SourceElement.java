/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.descriptors.SourceFile;
import org.jetbrains.annotations.NotNull;

public interface SourceElement {
    public static final SourceElement NO_SOURCE = new SourceElement(){

        public String toString() {
            return "NO_SOURCE";
        }

        @Override
        @NotNull
        public SourceFile getContainingFile() {
            SourceFile sourceFile = SourceFile.NO_SOURCE_FILE;
            if (sourceFile == null) {
                1.$$$reportNull$$$0(0);
            }
            return sourceFile;
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "kotlin/reflect/jvm/internal/impl/descriptors/SourceElement$1", "getContainingFile"));
        }
    };

    @NotNull
    public SourceFile getContainingFile();
}

