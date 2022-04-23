/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;

public interface FlexibleTypeDeserializer {
    @NotNull
    public KotlinType create(@NotNull ProtoBuf.Type var1, @NotNull String var2, @NotNull SimpleType var3, @NotNull SimpleType var4);

    public static final class ThrowException
    implements FlexibleTypeDeserializer {
        public static final ThrowException INSTANCE;

        @Override
        @NotNull
        public KotlinType create(@NotNull ProtoBuf.Type proto, @NotNull String flexibleId, @NotNull SimpleType lowerBound, @NotNull SimpleType upperBound) {
            Intrinsics.checkNotNullParameter(proto, "proto");
            Intrinsics.checkNotNullParameter(flexibleId, "flexibleId");
            Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
            Intrinsics.checkNotNullParameter(upperBound, "upperBound");
            throw (Throwable)new IllegalArgumentException("This method should not be used.");
        }

        private ThrowException() {
        }

        static {
            ThrowException throwException;
            INSTANCE = throwException = new ThrowException();
        }
    }
}

