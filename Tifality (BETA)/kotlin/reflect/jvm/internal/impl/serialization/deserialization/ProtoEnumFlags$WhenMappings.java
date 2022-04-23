/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.types.Variance;

public final class ProtoEnumFlags$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;
    public static final /* synthetic */ int[] $EnumSwitchMapping$1;
    public static final /* synthetic */ int[] $EnumSwitchMapping$2;
    public static final /* synthetic */ int[] $EnumSwitchMapping$3;
    public static final /* synthetic */ int[] $EnumSwitchMapping$4;
    public static final /* synthetic */ int[] $EnumSwitchMapping$5;
    public static final /* synthetic */ int[] $EnumSwitchMapping$6;
    public static final /* synthetic */ int[] $EnumSwitchMapping$7;
    public static final /* synthetic */ int[] $EnumSwitchMapping$8;
    public static final /* synthetic */ int[] $EnumSwitchMapping$9;

    static {
        $EnumSwitchMapping$0 = new int[ProtoBuf.MemberKind.values().length];
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$0[ProtoBuf.MemberKind.DECLARATION.ordinal()] = 1;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$0[ProtoBuf.MemberKind.FAKE_OVERRIDE.ordinal()] = 2;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$0[ProtoBuf.MemberKind.DELEGATION.ordinal()] = 3;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$0[ProtoBuf.MemberKind.SYNTHESIZED.ordinal()] = 4;
        $EnumSwitchMapping$1 = new int[CallableMemberDescriptor.Kind.values().length];
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$1[CallableMemberDescriptor.Kind.DECLARATION.ordinal()] = 1;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$1[CallableMemberDescriptor.Kind.FAKE_OVERRIDE.ordinal()] = 2;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$1[CallableMemberDescriptor.Kind.DELEGATION.ordinal()] = 3;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$1[CallableMemberDescriptor.Kind.SYNTHESIZED.ordinal()] = 4;
        $EnumSwitchMapping$2 = new int[ProtoBuf.Modality.values().length];
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$2[ProtoBuf.Modality.FINAL.ordinal()] = 1;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$2[ProtoBuf.Modality.OPEN.ordinal()] = 2;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$2[ProtoBuf.Modality.ABSTRACT.ordinal()] = 3;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$2[ProtoBuf.Modality.SEALED.ordinal()] = 4;
        $EnumSwitchMapping$3 = new int[Modality.values().length];
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$3[Modality.FINAL.ordinal()] = 1;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$3[Modality.OPEN.ordinal()] = 2;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$3[Modality.ABSTRACT.ordinal()] = 3;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$3[Modality.SEALED.ordinal()] = 4;
        $EnumSwitchMapping$4 = new int[ProtoBuf.Visibility.values().length];
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$4[ProtoBuf.Visibility.INTERNAL.ordinal()] = 1;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$4[ProtoBuf.Visibility.PRIVATE.ordinal()] = 2;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$4[ProtoBuf.Visibility.PRIVATE_TO_THIS.ordinal()] = 3;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$4[ProtoBuf.Visibility.PROTECTED.ordinal()] = 4;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$4[ProtoBuf.Visibility.PUBLIC.ordinal()] = 5;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$4[ProtoBuf.Visibility.LOCAL.ordinal()] = 6;
        $EnumSwitchMapping$5 = new int[ProtoBuf.Class.Kind.values().length];
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$5[ProtoBuf.Class.Kind.CLASS.ordinal()] = 1;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$5[ProtoBuf.Class.Kind.INTERFACE.ordinal()] = 2;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$5[ProtoBuf.Class.Kind.ENUM_CLASS.ordinal()] = 3;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$5[ProtoBuf.Class.Kind.ENUM_ENTRY.ordinal()] = 4;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$5[ProtoBuf.Class.Kind.ANNOTATION_CLASS.ordinal()] = 5;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$5[ProtoBuf.Class.Kind.OBJECT.ordinal()] = 6;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$5[ProtoBuf.Class.Kind.COMPANION_OBJECT.ordinal()] = 7;
        $EnumSwitchMapping$6 = new int[ClassKind.values().length];
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$6[ClassKind.CLASS.ordinal()] = 1;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$6[ClassKind.INTERFACE.ordinal()] = 2;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$6[ClassKind.ENUM_CLASS.ordinal()] = 3;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$6[ClassKind.ENUM_ENTRY.ordinal()] = 4;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$6[ClassKind.ANNOTATION_CLASS.ordinal()] = 5;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$6[ClassKind.OBJECT.ordinal()] = 6;
        $EnumSwitchMapping$7 = new int[ProtoBuf.TypeParameter.Variance.values().length];
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$7[ProtoBuf.TypeParameter.Variance.IN.ordinal()] = 1;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$7[ProtoBuf.TypeParameter.Variance.OUT.ordinal()] = 2;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$7[ProtoBuf.TypeParameter.Variance.INV.ordinal()] = 3;
        $EnumSwitchMapping$8 = new int[ProtoBuf.Type.Argument.Projection.values().length];
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$8[ProtoBuf.Type.Argument.Projection.IN.ordinal()] = 1;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$8[ProtoBuf.Type.Argument.Projection.OUT.ordinal()] = 2;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$8[ProtoBuf.Type.Argument.Projection.INV.ordinal()] = 3;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$8[ProtoBuf.Type.Argument.Projection.STAR.ordinal()] = 4;
        $EnumSwitchMapping$9 = new int[Variance.values().length];
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$9[Variance.IN_VARIANCE.ordinal()] = 1;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$9[Variance.OUT_VARIANCE.ordinal()] = 2;
        ProtoEnumFlags$WhenMappings.$EnumSwitchMapping$9[Variance.INVARIANT.ordinal()] = 3;
    }
}

