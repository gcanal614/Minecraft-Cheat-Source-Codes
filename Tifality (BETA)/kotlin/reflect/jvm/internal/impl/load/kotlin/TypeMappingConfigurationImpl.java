/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.Collection;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType;
import kotlin.reflect.jvm.internal.impl.load.kotlin.TypeMappingConfiguration;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TypeMappingConfigurationImpl
implements TypeMappingConfiguration<JvmType> {
    public static final TypeMappingConfigurationImpl INSTANCE;

    @Override
    @NotNull
    public KotlinType commonSupertype(@NotNull Collection<? extends KotlinType> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        throw (Throwable)((Object)new AssertionError((Object)("There should be no intersection type in existing descriptors, but found: " + CollectionsKt.joinToString$default(types, null, null, null, 0, null, null, 63, null))));
    }

    @Override
    @Nullable
    public JvmType getPredefinedTypeForClass(@NotNull ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        return null;
    }

    @Override
    @Nullable
    public String getPredefinedInternalNameForClass(@NotNull ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        return null;
    }

    @Override
    public void processErrorType(@NotNull KotlinType kotlinType, @NotNull ClassDescriptor descriptor2) {
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
    }

    private TypeMappingConfigurationImpl() {
    }

    static {
        TypeMappingConfigurationImpl typeMappingConfigurationImpl;
        INSTANCE = typeMappingConfigurationImpl = new TypeMappingConfigurationImpl();
    }

    @Override
    @Nullable
    public String getPredefinedFullInternalNameForClass(@NotNull ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        return TypeMappingConfiguration.DefaultImpls.getPredefinedFullInternalNameForClass(this, classDescriptor);
    }

    @Override
    @Nullable
    public KotlinType preprocessType(@NotNull KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        return TypeMappingConfiguration.DefaultImpls.preprocessType(this, kotlinType);
    }

    @Override
    public boolean releaseCoroutines() {
        return TypeMappingConfiguration.DefaultImpls.releaseCoroutines(this);
    }
}

