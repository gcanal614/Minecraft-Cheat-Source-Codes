/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities;
import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ProtoEnumFlags$WhenMappings;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ProtoEnumFlags {
    public static final ProtoEnumFlags INSTANCE;

    /*
     * Enabled aggressive block sorting
     */
    @NotNull
    public final CallableMemberDescriptor.Kind memberKind(@Nullable ProtoBuf.MemberKind memberKind) {
        CallableMemberDescriptor.Kind kind;
        ProtoBuf.MemberKind memberKind2 = memberKind;
        if (memberKind2 != null) {
            switch (ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$0[memberKind2.ordinal()]) {
                case 1: {
                    kind = CallableMemberDescriptor.Kind.DECLARATION;
                    return kind;
                }
                case 2: {
                    kind = CallableMemberDescriptor.Kind.FAKE_OVERRIDE;
                    return kind;
                }
                case 3: {
                    kind = CallableMemberDescriptor.Kind.DELEGATION;
                    return kind;
                }
                case 4: {
                    kind = CallableMemberDescriptor.Kind.SYNTHESIZED;
                    return kind;
                }
            }
        }
        kind = CallableMemberDescriptor.Kind.DECLARATION;
        return kind;
    }

    /*
     * Enabled aggressive block sorting
     */
    @NotNull
    public final Modality modality(@Nullable ProtoBuf.Modality modality) {
        Modality modality2;
        ProtoBuf.Modality modality3 = modality;
        if (modality3 != null) {
            switch (ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$2[modality3.ordinal()]) {
                case 1: {
                    modality2 = Modality.FINAL;
                    return modality2;
                }
                case 2: {
                    modality2 = Modality.OPEN;
                    return modality2;
                }
                case 3: {
                    modality2 = Modality.ABSTRACT;
                    return modality2;
                }
                case 4: {
                    modality2 = Modality.SEALED;
                    return modality2;
                }
            }
        }
        modality2 = Modality.FINAL;
        return modality2;
    }

    /*
     * Unable to fully structure code
     */
    @NotNull
    public final Visibility visibility(@Nullable ProtoBuf.Visibility visibility) {
        v0 = visibility;
        if (v0 == null) ** GOTO lbl-1000
        switch (ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$4[v0.ordinal()]) {
            case 1: {
                v1 = Visibilities.INTERNAL;
                break;
            }
            case 2: {
                v1 = Visibilities.PRIVATE;
                break;
            }
            case 3: {
                v1 = Visibilities.PRIVATE_TO_THIS;
                break;
            }
            case 4: {
                v1 = Visibilities.PROTECTED;
                break;
            }
            case 5: {
                v1 = Visibilities.PUBLIC;
                break;
            }
            case 6: {
                v1 = Visibilities.LOCAL;
                break;
            }
            default: lbl-1000:
            // 2 sources

            {
                v1 = Visibilities.PRIVATE;
            }
        }
        Intrinsics.checkNotNullExpressionValue(v1, "when (visibility) {\n    \u2026isibilities.PRIVATE\n    }");
        return v1;
    }

    /*
     * Enabled aggressive block sorting
     */
    @NotNull
    public final ClassKind classKind(@Nullable ProtoBuf.Class.Kind kind) {
        ClassKind classKind;
        ProtoBuf.Class.Kind kind2 = kind;
        if (kind2 != null) {
            switch (ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$5[kind2.ordinal()]) {
                case 1: {
                    classKind = ClassKind.CLASS;
                    return classKind;
                }
                case 2: {
                    classKind = ClassKind.INTERFACE;
                    return classKind;
                }
                case 3: {
                    classKind = ClassKind.ENUM_CLASS;
                    return classKind;
                }
                case 4: {
                    classKind = ClassKind.ENUM_ENTRY;
                    return classKind;
                }
                case 5: {
                    classKind = ClassKind.ANNOTATION_CLASS;
                    return classKind;
                }
                case 6: 
                case 7: {
                    classKind = ClassKind.OBJECT;
                    return classKind;
                }
            }
        }
        classKind = ClassKind.CLASS;
        return classKind;
    }

    @NotNull
    public final Variance variance(@NotNull ProtoBuf.TypeParameter.Variance variance) {
        Variance variance2;
        Intrinsics.checkNotNullParameter(variance, "variance");
        switch (ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$7[variance.ordinal()]) {
            case 1: {
                variance2 = Variance.IN_VARIANCE;
                break;
            }
            case 2: {
                variance2 = Variance.OUT_VARIANCE;
                break;
            }
            case 3: {
                variance2 = Variance.INVARIANT;
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return variance2;
    }

    @NotNull
    public final Variance variance(@NotNull ProtoBuf.Type.Argument.Projection projection) {
        Variance variance;
        Intrinsics.checkNotNullParameter(projection, "projection");
        switch (ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$8[projection.ordinal()]) {
            case 1: {
                variance = Variance.IN_VARIANCE;
                break;
            }
            case 2: {
                variance = Variance.OUT_VARIANCE;
                break;
            }
            case 3: {
                variance = Variance.INVARIANT;
                break;
            }
            case 4: {
                throw (Throwable)new IllegalArgumentException("Only IN, OUT and INV are supported. Actual argument: " + projection);
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return variance;
    }

    private ProtoEnumFlags() {
    }

    static {
        ProtoEnumFlags protoEnumFlags;
        INSTANCE = protoEnumFlags = new ProtoEnumFlags();
    }
}

