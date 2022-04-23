/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.Constant;
import kotlin.reflect.jvm.internal.impl.load.java.EnumEntry;
import kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultValue;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.NumberWithRadix;
import kotlin.reflect.jvm.internal.impl.utils.NumbersKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class UtilsKt {
    /*
     * WARNING - void declaration
     */
    @Nullable
    public static final JavaDefaultValue lexicalCastFrom(@NotNull KotlinType $this$lexicalCastFrom, @NotNull String value) {
        Object object;
        Intrinsics.checkNotNullParameter($this$lexicalCastFrom, "$this$lexicalCastFrom");
        Intrinsics.checkNotNullParameter(value, "value");
        ClassifierDescriptor typeDescriptor = $this$lexicalCastFrom.getConstructor().getDeclarationDescriptor();
        if (typeDescriptor instanceof ClassDescriptor && ((ClassDescriptor)typeDescriptor).getKind() == ClassKind.ENUM_CLASS) {
            MemberScope memberScope2 = ((ClassDescriptor)typeDescriptor).getUnsubstitutedInnerClassesScope();
            Name name = Name.identifier(value);
            Intrinsics.checkNotNullExpressionValue(name, "Name.identifier(value)");
            ClassifierDescriptor descriptor2 = memberScope2.getContributedClassifier(name, NoLookupLocation.FROM_BACKEND);
            return descriptor2 instanceof ClassDescriptor && ((ClassDescriptor)descriptor2).getKind() == ClassKind.ENUM_ENTRY ? new EnumEntry((ClassDescriptor)descriptor2) : null;
        }
        KotlinType type2 = TypeUtilsKt.makeNotNullable($this$lexicalCastFrom);
        NumberWithRadix numberWithRadix = NumbersKt.extractRadix(value);
        String string = numberWithRadix.component1();
        int radix = numberWithRadix.component2();
        try {
            Object object2;
            if (KotlinBuiltIns.isBoolean(type2)) {
                object = value;
                boolean bl = false;
                object2 = Boolean.parseBoolean((String)object);
            } else {
                void number;
                object2 = KotlinBuiltIns.isChar(type2) ? StringsKt.singleOrNull(value) : (KotlinBuiltIns.isByte(type2) ? StringsKt.toByteOrNull((String)number, radix) : (KotlinBuiltIns.isShort(type2) ? StringsKt.toShortOrNull((String)number, radix) : (KotlinBuiltIns.isInt(type2) ? StringsKt.toIntOrNull((String)number, radix) : (KotlinBuiltIns.isLong(type2) ? StringsKt.toLongOrNull((String)number, radix) : (KotlinBuiltIns.isFloat(type2) ? StringsKt.toFloatOrNull(value) : (KotlinBuiltIns.isDouble(type2) ? StringsKt.toDoubleOrNull(value) : (KotlinBuiltIns.isString(type2) ? value : null)))))));
            }
            object = object2;
        }
        catch (IllegalArgumentException e) {
            object = null;
        }
        String result2 = object;
        return result2 != null ? new Constant(result2) : null;
    }
}

