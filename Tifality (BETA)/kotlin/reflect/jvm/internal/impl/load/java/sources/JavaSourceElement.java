/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java.sources;

import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaElement;
import org.jetbrains.annotations.NotNull;

public interface JavaSourceElement
extends SourceElement {
    @NotNull
    public JavaElement getJavaElement();
}

