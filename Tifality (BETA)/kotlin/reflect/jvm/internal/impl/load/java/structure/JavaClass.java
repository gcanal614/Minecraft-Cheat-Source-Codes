/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.Collection;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifier;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClassifierType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaConstructor;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaModifierListOwner;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameterListOwner;
import kotlin.reflect.jvm.internal.impl.load.java.structure.LightClassOriginKind;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface JavaClass
extends JavaClassifier,
JavaModifierListOwner,
JavaTypeParameterListOwner {
    @Nullable
    public FqName getFqName();

    @NotNull
    public Collection<JavaClassifierType> getSupertypes();

    @NotNull
    public Collection<Name> getInnerClassNames();

    @Nullable
    public JavaClass getOuterClass();

    public boolean isInterface();

    public boolean isAnnotationType();

    public boolean isEnum();

    @Nullable
    public LightClassOriginKind getLightClassOriginKind();

    @NotNull
    public Collection<JavaMethod> getMethods();

    @NotNull
    public Collection<JavaField> getFields();

    @NotNull
    public Collection<JavaConstructor> getConstructors();

    public boolean hasDefaultConstructor();
}

