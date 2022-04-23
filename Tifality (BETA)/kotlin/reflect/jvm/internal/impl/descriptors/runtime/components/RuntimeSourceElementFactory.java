/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceFile;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaElement;
import kotlin.reflect.jvm.internal.impl.load.java.sources.JavaSourceElement;
import kotlin.reflect.jvm.internal.impl.load.java.sources.JavaSourceElementFactory;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaElement;
import org.jetbrains.annotations.NotNull;

public final class RuntimeSourceElementFactory
implements JavaSourceElementFactory {
    public static final RuntimeSourceElementFactory INSTANCE;

    @Override
    @NotNull
    public JavaSourceElement source(@NotNull JavaElement javaElement) {
        Intrinsics.checkNotNullParameter(javaElement, "javaElement");
        return new RuntimeSourceElement((ReflectJavaElement)javaElement);
    }

    private RuntimeSourceElementFactory() {
    }

    static {
        RuntimeSourceElementFactory runtimeSourceElementFactory;
        INSTANCE = runtimeSourceElementFactory = new RuntimeSourceElementFactory();
    }

    public static final class RuntimeSourceElement
    implements JavaSourceElement {
        @NotNull
        private final ReflectJavaElement javaElement;

        @NotNull
        public String toString() {
            return this.getClass().getName() + ": " + this.getJavaElement().toString();
        }

        @Override
        @NotNull
        public SourceFile getContainingFile() {
            SourceFile sourceFile = SourceFile.NO_SOURCE_FILE;
            Intrinsics.checkNotNullExpressionValue(sourceFile, "SourceFile.NO_SOURCE_FILE");
            return sourceFile;
        }

        @Override
        @NotNull
        public ReflectJavaElement getJavaElement() {
            return this.javaElement;
        }

        public RuntimeSourceElement(@NotNull ReflectJavaElement javaElement) {
            Intrinsics.checkNotNullParameter(javaElement, "javaElement");
            this.javaElement = javaElement;
        }
    }
}

