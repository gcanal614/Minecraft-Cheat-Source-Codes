/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.annotations.NotNull;

public interface JavaArrayAnnotationArgument
extends JavaAnnotationArgument {
    @NotNull
    public List<JavaAnnotationArgument> getElements();
}

