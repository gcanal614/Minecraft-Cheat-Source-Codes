/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.structure;

import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.annotations.Nullable;

public interface JavaLiteralAnnotationArgument
extends JavaAnnotationArgument {
    @Nullable
    public Object getValue();
}

