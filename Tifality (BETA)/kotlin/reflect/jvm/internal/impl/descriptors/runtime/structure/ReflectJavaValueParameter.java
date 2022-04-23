/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.annotation.Annotation;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotation;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaAnnotationOwnerKt;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaElement;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.ReflectJavaType;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaValueParameter;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ReflectJavaValueParameter
extends ReflectJavaElement
implements JavaValueParameter {
    @NotNull
    private final ReflectJavaType type;
    private final Annotation[] reflectAnnotations;
    private final String reflectName;
    private final boolean isVararg;

    @NotNull
    public List<ReflectJavaAnnotation> getAnnotations() {
        return ReflectJavaAnnotationOwnerKt.getAnnotations(this.reflectAnnotations);
    }

    @Override
    @Nullable
    public ReflectJavaAnnotation findAnnotation(@NotNull FqName fqName2) {
        Intrinsics.checkNotNullParameter(fqName2, "fqName");
        return ReflectJavaAnnotationOwnerKt.findAnnotation(this.reflectAnnotations, fqName2);
    }

    @Override
    public boolean isDeprecatedInJavaDoc() {
        return false;
    }

    @Override
    @Nullable
    public Name getName() {
        Name name;
        String string = this.reflectName;
        if (string != null) {
            String string2 = string;
            boolean bl = false;
            boolean bl2 = false;
            String p1 = string2;
            boolean bl3 = false;
            name = Name.guessByFirstCharacter(p1);
        } else {
            name = null;
        }
        return name;
    }

    @NotNull
    public String toString() {
        return this.getClass().getName() + ": " + (this.isVararg() ? "vararg " : "") + this.getName() + ": " + this.getType();
    }

    @Override
    @NotNull
    public ReflectJavaType getType() {
        return this.type;
    }

    @Override
    public boolean isVararg() {
        return this.isVararg;
    }

    public ReflectJavaValueParameter(@NotNull ReflectJavaType type2, @NotNull Annotation[] reflectAnnotations, @Nullable String reflectName, boolean isVararg) {
        Intrinsics.checkNotNullParameter(type2, "type");
        Intrinsics.checkNotNullParameter(reflectAnnotations, "reflectAnnotations");
        this.type = type2;
        this.reflectAnnotations = reflectAnnotations;
        this.reflectName = reflectName;
        this.isVararg = isVararg;
    }
}

