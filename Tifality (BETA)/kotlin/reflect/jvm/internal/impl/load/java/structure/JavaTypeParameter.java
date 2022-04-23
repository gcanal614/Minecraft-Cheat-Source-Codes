/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.Collection;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifier;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifierType;
import org.jetbrains.annotations.NotNull;

public interface JavaTypeParameter
extends JavaClassifier {
    @NotNull
    public Collection<JavaClassifierType> getUpperBounds();
}

