/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmMemberSignature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MemberSignature {
    @NotNull
    private final String signature;
    public static final Companion Companion = new Companion(null);

    @NotNull
    public final String getSignature$descriptors_jvm() {
        return this.signature;
    }

    private MemberSignature(String signature2) {
        this.signature = signature2;
    }

    public /* synthetic */ MemberSignature(String signature2, DefaultConstructorMarker $constructor_marker) {
        this(signature2);
    }

    @NotNull
    public String toString() {
        return "MemberSignature(signature=" + this.signature + ")";
    }

    public int hashCode() {
        String string = this.signature;
        return string != null ? string.hashCode() : 0;
    }

    public boolean equals(@Nullable Object object) {
        block3: {
            block2: {
                if (this == object) break block2;
                if (!(object instanceof MemberSignature)) break block3;
                MemberSignature memberSignature = (MemberSignature)object;
                if (!Intrinsics.areEqual(this.signature, memberSignature.signature)) break block3;
            }
            return true;
        }
        return false;
    }

    public static final class Companion {
        @JvmStatic
        @NotNull
        public final MemberSignature fromMethod(@NotNull NameResolver nameResolver, @NotNull JvmProtoBuf.JvmMethodSignature signature2) {
            Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
            Intrinsics.checkNotNullParameter(signature2, "signature");
            return this.fromMethodNameAndDesc(nameResolver.getString(signature2.getName()), nameResolver.getString(signature2.getDesc()));
        }

        @JvmStatic
        @NotNull
        public final MemberSignature fromMethodNameAndDesc(@NotNull String name, @NotNull String desc) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            return new MemberSignature(name + desc, null);
        }

        @JvmStatic
        @NotNull
        public final MemberSignature fromFieldNameAndDesc(@NotNull String name, @NotNull String desc) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            return new MemberSignature(name + '#' + desc, null);
        }

        @JvmStatic
        @NotNull
        public final MemberSignature fromJvmMemberSignature(@NotNull JvmMemberSignature signature2) {
            MemberSignature memberSignature;
            Intrinsics.checkNotNullParameter(signature2, "signature");
            JvmMemberSignature jvmMemberSignature = signature2;
            if (jvmMemberSignature instanceof JvmMemberSignature.Method) {
                memberSignature = this.fromMethodNameAndDesc(signature2.getName(), signature2.getDesc());
            } else if (jvmMemberSignature instanceof JvmMemberSignature.Field) {
                memberSignature = this.fromFieldNameAndDesc(signature2.getName(), signature2.getDesc());
            } else {
                throw new NoWhenBranchMatchedException();
            }
            return memberSignature;
        }

        @JvmStatic
        @NotNull
        public final MemberSignature fromMethodSignatureAndParameterIndex(@NotNull MemberSignature signature2, int index) {
            Intrinsics.checkNotNullParameter(signature2, "signature");
            return new MemberSignature(signature2.getSignature$descriptors_jvm() + '@' + index, null);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

