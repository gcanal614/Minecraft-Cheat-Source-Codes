/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.DeserializedDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.MemberDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DescriptorWithContainerSource;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DeserializedMemberDescriptor
extends DeserializedDescriptor,
MemberDescriptor,
DescriptorWithContainerSource {
    @NotNull
    public MessageLite getProto();

    @NotNull
    public NameResolver getNameResolver();

    @NotNull
    public TypeTable getTypeTable();

    @NotNull
    public VersionRequirementTable getVersionRequirementTable();

    @NotNull
    public List<VersionRequirement> getVersionRequirements();

    @Nullable
    public DeserializedContainerSource getContainerSource();

    public static final class CoroutinesCompatibilityMode
    extends Enum<CoroutinesCompatibilityMode> {
        public static final /* enum */ CoroutinesCompatibilityMode COMPATIBLE;
        public static final /* enum */ CoroutinesCompatibilityMode NEEDS_WRAPPER;
        public static final /* enum */ CoroutinesCompatibilityMode INCOMPATIBLE;
        private static final /* synthetic */ CoroutinesCompatibilityMode[] $VALUES;

        static {
            CoroutinesCompatibilityMode[] coroutinesCompatibilityModeArray = new CoroutinesCompatibilityMode[3];
            CoroutinesCompatibilityMode[] coroutinesCompatibilityModeArray2 = coroutinesCompatibilityModeArray;
            coroutinesCompatibilityModeArray[0] = COMPATIBLE = new CoroutinesCompatibilityMode();
            coroutinesCompatibilityModeArray[1] = NEEDS_WRAPPER = new CoroutinesCompatibilityMode();
            coroutinesCompatibilityModeArray[2] = INCOMPATIBLE = new CoroutinesCompatibilityMode();
            $VALUES = coroutinesCompatibilityModeArray;
        }

        public static CoroutinesCompatibilityMode[] values() {
            return (CoroutinesCompatibilityMode[])$VALUES.clone();
        }

        public static CoroutinesCompatibilityMode valueOf(String string) {
            return Enum.valueOf(CoroutinesCompatibilityMode.class, string);
        }
    }

    public static final class DefaultImpls {
        @NotNull
        public static List<VersionRequirement> getVersionRequirements(@NotNull DeserializedMemberDescriptor $this) {
            return VersionRequirement.Companion.create($this.getProto(), $this.getNameResolver(), $this.getVersionRequirementTable());
        }
    }
}

