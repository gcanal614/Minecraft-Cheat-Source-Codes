/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.builtins;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import org.jetbrains.annotations.NotNull;

public final class UnsignedType
extends Enum<UnsignedType> {
    public static final /* enum */ UnsignedType UBYTE;
    public static final /* enum */ UnsignedType USHORT;
    public static final /* enum */ UnsignedType UINT;
    public static final /* enum */ UnsignedType ULONG;
    private static final /* synthetic */ UnsignedType[] $VALUES;
    @NotNull
    private final Name typeName;
    @NotNull
    private final ClassId arrayClassId;
    @NotNull
    private final ClassId classId;

    static {
        UnsignedType[] unsignedTypeArray = new UnsignedType[4];
        UnsignedType[] unsignedTypeArray2 = unsignedTypeArray;
        ClassId classId = ClassId.fromString("kotlin/UByte");
        Intrinsics.checkNotNullExpressionValue(classId, "ClassId.fromString(\"kotlin/UByte\")");
        unsignedTypeArray[0] = UBYTE = new UnsignedType(classId);
        ClassId classId2 = ClassId.fromString("kotlin/UShort");
        Intrinsics.checkNotNullExpressionValue(classId2, "ClassId.fromString(\"kotlin/UShort\")");
        unsignedTypeArray[1] = USHORT = new UnsignedType(classId2);
        ClassId classId3 = ClassId.fromString("kotlin/UInt");
        Intrinsics.checkNotNullExpressionValue(classId3, "ClassId.fromString(\"kotlin/UInt\")");
        unsignedTypeArray[2] = UINT = new UnsignedType(classId3);
        ClassId classId4 = ClassId.fromString("kotlin/ULong");
        Intrinsics.checkNotNullExpressionValue(classId4, "ClassId.fromString(\"kotlin/ULong\")");
        unsignedTypeArray[3] = ULONG = new UnsignedType(classId4);
        $VALUES = unsignedTypeArray;
    }

    @NotNull
    public final Name getTypeName() {
        return this.typeName;
    }

    @NotNull
    public final ClassId getArrayClassId() {
        return this.arrayClassId;
    }

    @NotNull
    public final ClassId getClassId() {
        return this.classId;
    }

    private UnsignedType(ClassId classId) {
        this.classId = classId;
        Name name = this.classId.getShortClassName();
        Intrinsics.checkNotNullExpressionValue(name, "classId.shortClassName");
        this.typeName = name;
        this.arrayClassId = new ClassId(this.classId.getPackageFqName(), Name.identifier(this.typeName.asString() + "Array"));
    }

    public static UnsignedType[] values() {
        return (UnsignedType[])$VALUES.clone();
    }

    public static UnsignedType valueOf(String string) {
        return Enum.valueOf(UnsignedType.class, string);
    }
}

