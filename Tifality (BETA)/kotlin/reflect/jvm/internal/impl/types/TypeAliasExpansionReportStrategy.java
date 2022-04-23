/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TypeAliasExpansionReportStrategy {
    public void conflictingProjection(@NotNull TypeAliasDescriptor var1, @Nullable TypeParameterDescriptor var2, @NotNull KotlinType var3);

    public void recursiveTypeAlias(@NotNull TypeAliasDescriptor var1);

    public void boundsViolationInSubstitution(@NotNull KotlinType var1, @NotNull KotlinType var2, @NotNull KotlinType var3, @NotNull TypeParameterDescriptor var4);

    public void repeatedAnnotation(@NotNull AnnotationDescriptor var1);

    public static final class DO_NOTHING
    implements TypeAliasExpansionReportStrategy {
        public static final DO_NOTHING INSTANCE;

        @Override
        public void conflictingProjection(@NotNull TypeAliasDescriptor typeAlias, @Nullable TypeParameterDescriptor typeParameter, @NotNull KotlinType substitutedArgument) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            Intrinsics.checkNotNullParameter(substitutedArgument, "substitutedArgument");
        }

        @Override
        public void recursiveTypeAlias(@NotNull TypeAliasDescriptor typeAlias) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        }

        @Override
        public void boundsViolationInSubstitution(@NotNull KotlinType bound, @NotNull KotlinType unsubstitutedArgument, @NotNull KotlinType argument, @NotNull TypeParameterDescriptor typeParameter) {
            Intrinsics.checkNotNullParameter(bound, "bound");
            Intrinsics.checkNotNullParameter(unsubstitutedArgument, "unsubstitutedArgument");
            Intrinsics.checkNotNullParameter(argument, "argument");
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        }

        @Override
        public void repeatedAnnotation(@NotNull AnnotationDescriptor annotation) {
            Intrinsics.checkNotNullParameter(annotation, "annotation");
        }

        private DO_NOTHING() {
        }

        static {
            DO_NOTHING dO_NOTHING;
            INSTANCE = dO_NOTHING = new DO_NOTHING();
        }
    }
}

