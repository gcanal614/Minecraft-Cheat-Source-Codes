/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaElement;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;
import org.jetbrains.annotations.NotNull;

public interface JavaTypeParameterListOwner
extends JavaElement {
    @NotNull
    public List<JavaTypeParameter> getTypeParameters();
}

