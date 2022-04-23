/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.Collection;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class KotlinTypeRefiner {
    @NotNull
    public abstract KotlinType refineType(@NotNull KotlinType var1);

    @NotNull
    public abstract Collection<KotlinType> refineSupertypes(@NotNull ClassDescriptor var1);

    @Nullable
    public abstract ClassifierDescriptor refineDescriptor(@NotNull DeclarationDescriptor var1);

    @Nullable
    public abstract ClassDescriptor findClassAcrossModuleDependencies(@NotNull ClassId var1);

    public abstract boolean isRefinementNeededForModule(@NotNull ModuleDescriptor var1);

    public abstract boolean isRefinementNeededForTypeConstructor(@NotNull TypeConstructor var1);

    @NotNull
    public abstract <S extends MemberScope> S getOrPutScopeForClass(@NotNull ClassDescriptor var1, @NotNull Function0<? extends S> var2);

    public static final class Default
    extends KotlinTypeRefiner {
        public static final Default INSTANCE;

        @Override
        @NotNull
        public KotlinType refineType(@NotNull KotlinType type2) {
            Intrinsics.checkNotNullParameter(type2, "type");
            return type2;
        }

        @Override
        @NotNull
        public Collection<KotlinType> refineSupertypes(@NotNull ClassDescriptor classDescriptor) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            TypeConstructor typeConstructor2 = classDescriptor.getTypeConstructor();
            Intrinsics.checkNotNullExpressionValue(typeConstructor2, "classDescriptor.typeConstructor");
            Collection<KotlinType> collection = typeConstructor2.getSupertypes();
            Intrinsics.checkNotNullExpressionValue(collection, "classDescriptor.typeConstructor.supertypes");
            return collection;
        }

        @Override
        @Nullable
        public ClassDescriptor refineDescriptor(@NotNull DeclarationDescriptor descriptor2) {
            Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
            return null;
        }

        @Override
        @Nullable
        public ClassDescriptor findClassAcrossModuleDependencies(@NotNull ClassId classId) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            return null;
        }

        @Override
        public boolean isRefinementNeededForModule(@NotNull ModuleDescriptor moduleDescriptor) {
            Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
            return false;
        }

        @Override
        public boolean isRefinementNeededForTypeConstructor(@NotNull TypeConstructor typeConstructor2) {
            Intrinsics.checkNotNullParameter(typeConstructor2, "typeConstructor");
            return false;
        }

        @Override
        @NotNull
        public <S extends MemberScope> S getOrPutScopeForClass(@NotNull ClassDescriptor classDescriptor, @NotNull Function0<? extends S> compute) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            Intrinsics.checkNotNullParameter(compute, "compute");
            return (S)((MemberScope)compute.invoke());
        }

        private Default() {
        }

        static {
            Default default_;
            INSTANCE = default_ = new Default();
        }
    }
}

