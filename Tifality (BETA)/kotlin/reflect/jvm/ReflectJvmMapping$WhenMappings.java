/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm;

import kotlin.Metadata;
import kotlin.reflect.jvm.internal.impl.load.kotlin.header.KotlinClassHeader;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=3)
public final class ReflectJvmMapping$WhenMappings {
    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

    static {
        $EnumSwitchMapping$0 = new int[KotlinClassHeader.Kind.values().length];
        ReflectJvmMapping$WhenMappings.$EnumSwitchMapping$0[KotlinClassHeader.Kind.FILE_FACADE.ordinal()] = 1;
        ReflectJvmMapping$WhenMappings.$EnumSwitchMapping$0[KotlinClassHeader.Kind.MULTIFILE_CLASS.ordinal()] = 2;
        ReflectJvmMapping$WhenMappings.$EnumSwitchMapping$0[KotlinClassHeader.Kind.MULTIFILE_CLASS_PART.ordinal()] = 3;
    }
}

