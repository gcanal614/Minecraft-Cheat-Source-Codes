/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationOwner;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifier;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface JavaClassifierType
extends JavaAnnotationOwner,
JavaType {
    @Nullable
    public JavaClassifier getClassifier();

    @NotNull
    public List<JavaType> getTypeArguments();

    public boolean isRaw();

    @NotNull
    public String getClassifierQualifiedName();

    @NotNull
    public String getPresentableText();
}

