/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationArgument;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMember;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameterListOwner;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaValueParameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface JavaMethod
extends JavaMember,
JavaTypeParameterListOwner {
    @NotNull
    public List<JavaValueParameter> getValueParameters();

    @NotNull
    public JavaType getReturnType();

    @Nullable
    public JavaAnnotationArgument getAnnotationParameterDefaultValue();

    public boolean getHasAnnotationParameterDefaultValue();

    public static final class DefaultImpls {
        public static boolean getHasAnnotationParameterDefaultValue(@NotNull JavaMethod $this) {
            return $this.getAnnotationParameterDefaultValue() != null;
        }
    }
}

