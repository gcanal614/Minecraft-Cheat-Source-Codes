/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirement;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberDescriptor;
import org.jetbrains.annotations.NotNull;

public interface DeserializedCallableMemberDescriptor
extends CallableMemberDescriptor,
DeserializedMemberDescriptor {

    public static final class DefaultImpls {
        @NotNull
        public static List<VersionRequirement> getVersionRequirements(@NotNull DeserializedCallableMemberDescriptor $this) {
            return DeserializedMemberDescriptor.DefaultImpls.getVersionRequirements($this);
        }
    }
}

