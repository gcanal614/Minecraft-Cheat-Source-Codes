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
import kotlin._Assertions;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class IndexedParametersSubstitution
extends TypeSubstitution {
    @NotNull
    private final TypeParameterDescriptor[] parameters;
    @NotNull
    private final TypeProjection[] arguments;
    private final boolean approximateCapturedTypes;

    @Override
    public boolean isEmpty() {
        TypeProjection[] typeProjectionArray = this.arguments;
        boolean bl = false;
        return typeProjectionArray.length == 0;
    }

    @Override
    public boolean approximateContravariantCapturedTypes() {
        return this.approximateCapturedTypes;
    }

    @Override
    @Nullable
    public TypeProjection get(@NotNull KotlinType key) {
        Intrinsics.checkNotNullParameter(key, "key");
        ClassifierDescriptor classifierDescriptor = key.getConstructor().getDeclarationDescriptor();
        if (!(classifierDescriptor instanceof TypeParameterDescriptor)) {
            classifierDescriptor = null;
        }
        TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor)classifierDescriptor;
        if (typeParameterDescriptor == null) {
            return null;
        }
        TypeParameterDescriptor parameter = typeParameterDescriptor;
        int index = parameter.getIndex();
        if (index < this.parameters.length && Intrinsics.areEqual(this.parameters[index].getTypeConstructor(), parameter.getTypeConstructor())) {
            return this.arguments[index];
        }
        return null;
    }

    @NotNull
    public final TypeParameterDescriptor[] getParameters() {
        return this.parameters;
    }

    @NotNull
    public final TypeProjection[] getArguments() {
        return this.arguments;
    }

    public IndexedParametersSubstitution(@NotNull TypeParameterDescriptor[] parameters2, @NotNull TypeProjection[] arguments2, boolean approximateCapturedTypes2) {
        Intrinsics.checkNotNullParameter(parameters2, "parameters");
        Intrinsics.checkNotNullParameter(arguments2, "arguments");
        this.parameters = parameters2;
        this.arguments = arguments2;
        this.approximateCapturedTypes = approximateCapturedTypes2;
        boolean bl = this.parameters.length <= this.arguments.length;
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl3 = false;
            String string = "Number of arguments should not be less then number of parameters, but: parameters=" + this.parameters.length + ", args=" + this.arguments.length;
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
    }

    public /* synthetic */ IndexedParametersSubstitution(TypeParameterDescriptor[] typeParameterDescriptorArray, TypeProjection[] typeProjectionArray, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            bl = false;
        }
        this(typeParameterDescriptorArray, typeProjectionArray, bl);
    }

    public IndexedParametersSubstitution(@NotNull List<? extends TypeParameterDescriptor> parameters2, @NotNull List<? extends TypeProjection> argumentsList) {
        Intrinsics.checkNotNullParameter(parameters2, "parameters");
        Intrinsics.checkNotNullParameter(argumentsList, "argumentsList");
        Collection $this$toTypedArray$iv = parameters2;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        TypeParameterDescriptor[] typeParameterDescriptorArray = thisCollection$iv.toArray(new TypeParameterDescriptor[0]);
        if (typeParameterDescriptorArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        $this$toTypedArray$iv = argumentsList;
        $i$f$toTypedArray = false;
        thisCollection$iv = $this$toTypedArray$iv;
        TypeProjection[] typeProjectionArray = thisCollection$iv.toArray(new TypeProjection[0]);
        if (typeProjectionArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        this(typeParameterDescriptorArray, typeProjectionArray, false, 4, null);
    }
}

