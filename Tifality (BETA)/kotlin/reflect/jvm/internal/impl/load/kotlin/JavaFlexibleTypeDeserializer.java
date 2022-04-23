/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.RawTypeImpl;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.FlexibleTypeDeserializer;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;

public final class JavaFlexibleTypeDeserializer
implements FlexibleTypeDeserializer {
    public static final JavaFlexibleTypeDeserializer INSTANCE;

    @Override
    @NotNull
    public KotlinType create(@NotNull ProtoBuf.Type proto, @NotNull String flexibleId, @NotNull SimpleType lowerBound, @NotNull SimpleType upperBound) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(flexibleId, "flexibleId");
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        if (Intrinsics.areEqual(flexibleId, "kotlin.jvm.PlatformType") ^ true) {
            SimpleType simpleType2 = ErrorUtils.createErrorType("Error java flexible type with id: " + flexibleId + ". (" + lowerBound + ".." + upperBound + ')');
            Intrinsics.checkNotNullExpressionValue(simpleType2, "ErrorUtils.createErrorTy\u2026owerBound..$upperBound)\")");
            return simpleType2;
        }
        if (proto.hasExtension(JvmProtoBuf.isRaw)) {
            return new RawTypeImpl(lowerBound, upperBound);
        }
        return KotlinTypeFactory.flexibleType(lowerBound, upperBound);
    }

    private JavaFlexibleTypeDeserializer() {
    }

    static {
        JavaFlexibleTypeDeserializer javaFlexibleTypeDeserializer;
        INSTANCE = javaFlexibleTypeDeserializer = new JavaFlexibleTypeDeserializer();
    }
}

