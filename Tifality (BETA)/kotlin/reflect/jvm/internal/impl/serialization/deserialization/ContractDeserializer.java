/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ContractDeserializer {
    public static final Companion Companion = kotlin.reflect.jvm.internal.impl.serialization.deserialization.ContractDeserializer$Companion.$$INSTANCE;

    @Nullable
    public Pair<CallableDescriptor.UserDataKey<?>, Object> deserializeContractFromFunction(@NotNull ProtoBuf.Function var1, @NotNull FunctionDescriptor var2, @NotNull TypeTable var3, @NotNull TypeDeserializer var4);

    public static final class Companion {
        @NotNull
        private static final ContractDeserializer DEFAULT;
        static final /* synthetic */ Companion $$INSTANCE;

        @NotNull
        public final ContractDeserializer getDEFAULT() {
            return DEFAULT;
        }

        private Companion() {
        }

        static {
            Companion companion;
            $$INSTANCE = companion = new Companion();
            DEFAULT = new ContractDeserializer(){

                @Nullable
                public Pair deserializeContractFromFunction(@NotNull ProtoBuf.Function proto, @NotNull FunctionDescriptor ownerFunction, @NotNull TypeTable typeTable, @NotNull TypeDeserializer typeDeserializer) {
                    Intrinsics.checkNotNullParameter(proto, "proto");
                    Intrinsics.checkNotNullParameter(ownerFunction, "ownerFunction");
                    Intrinsics.checkNotNullParameter(typeTable, "typeTable");
                    Intrinsics.checkNotNullParameter(typeDeserializer, "typeDeserializer");
                    return null;
                }
            };
        }
    }
}

