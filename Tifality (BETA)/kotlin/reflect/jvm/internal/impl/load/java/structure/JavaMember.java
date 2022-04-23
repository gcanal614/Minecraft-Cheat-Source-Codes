/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.java.structure;

import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationOwner;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaModifierListOwner;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaNamedElement;
import org.jetbrains.annotations.NotNull;

public interface JavaMember
extends JavaAnnotationOwner,
JavaModifierListOwner,
JavaNamedElement {
    @NotNull
    public JavaClass getContainingClass();
}

