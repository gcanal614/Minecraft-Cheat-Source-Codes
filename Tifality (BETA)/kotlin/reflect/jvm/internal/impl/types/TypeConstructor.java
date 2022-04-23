/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TypeConstructor
extends TypeConstructorMarker {
    @NotNull
    public List<TypeParameterDescriptor> getParameters();

    @NotNull
    public Collection<KotlinType> getSupertypes();

    public boolean isDenotable();

    @Nullable
    public ClassifierDescriptor getDeclarationDescriptor();

    @NotNull
    public KotlinBuiltIns getBuiltIns();

    @NotNull
    public TypeConstructor refine(@NotNull KotlinTypeRefiner var1);
}

