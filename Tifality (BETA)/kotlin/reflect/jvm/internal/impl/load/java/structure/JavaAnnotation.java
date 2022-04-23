/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.Collection;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaElement;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface JavaAnnotation
extends JavaElement {
    @NotNull
    public Collection<JavaAnnotationArgument> getArguments();

    @Nullable
    public ClassId getClassId();

    public boolean isIdeExternalAnnotation();

    @Nullable
    public JavaClass resolve();

    public static final class DefaultImpls {
        public static boolean isIdeExternalAnnotation(@NotNull JavaAnnotation $this) {
            return false;
        }
    }
}

