/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.protobuf.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Flags {
    public static final BooleanFlagField SUSPEND_TYPE = FlagField.booleanFirst();
    public static final BooleanFlagField HAS_ANNOTATIONS = FlagField.booleanFirst();
    public static final FlagField<ProtoBuf.Visibility> VISIBILITY = FlagField.after((FlagField)HAS_ANNOTATIONS, (Internal.EnumLite[])ProtoBuf.Visibility.values());
    public static final FlagField<ProtoBuf.Modality> MODALITY = FlagField.after(VISIBILITY, (Internal.EnumLite[])ProtoBuf.Modality.values());
    public static final FlagField<ProtoBuf.Class.Kind> CLASS_KIND = FlagField.after(MODALITY, (Internal.EnumLite[])ProtoBuf.Class.Kind.values());
    public static final BooleanFlagField IS_INNER = FlagField.booleanAfter(CLASS_KIND);
    public static final BooleanFlagField IS_DATA = FlagField.booleanAfter(IS_INNER);
    public static final BooleanFlagField IS_EXTERNAL_CLASS = FlagField.booleanAfter(IS_DATA);
    public static final BooleanFlagField IS_EXPECT_CLASS = FlagField.booleanAfter(IS_EXTERNAL_CLASS);
    public static final BooleanFlagField IS_INLINE_CLASS = FlagField.booleanAfter(IS_EXPECT_CLASS);
    public static final BooleanFlagField IS_FUN_INTERFACE = FlagField.booleanAfter(IS_INLINE_CLASS);
    public static final BooleanFlagField IS_SECONDARY = FlagField.booleanAfter(VISIBILITY);
    public static final FlagField<ProtoBuf.MemberKind> MEMBER_KIND = FlagField.after(MODALITY, (Internal.EnumLite[])ProtoBuf.MemberKind.values());
    public static final BooleanFlagField IS_OPERATOR = FlagField.booleanAfter(MEMBER_KIND);
    public static final BooleanFlagField IS_INFIX = FlagField.booleanAfter(IS_OPERATOR);
    public static final BooleanFlagField IS_INLINE = FlagField.booleanAfter(IS_INFIX);
    public static final BooleanFlagField IS_TAILREC = FlagField.booleanAfter(IS_INLINE);
    public static final BooleanFlagField IS_EXTERNAL_FUNCTION = FlagField.booleanAfter(IS_TAILREC);
    public static final BooleanFlagField IS_SUSPEND = FlagField.booleanAfter(IS_EXTERNAL_FUNCTION);
    public static final BooleanFlagField IS_EXPECT_FUNCTION = FlagField.booleanAfter(IS_SUSPEND);
    public static final BooleanFlagField IS_VAR = FlagField.booleanAfter(MEMBER_KIND);
    public static final BooleanFlagField HAS_GETTER = FlagField.booleanAfter(IS_VAR);
    public static final BooleanFlagField HAS_SETTER = FlagField.booleanAfter(HAS_GETTER);
    public static final BooleanFlagField IS_CONST = FlagField.booleanAfter(HAS_SETTER);
    public static final BooleanFlagField IS_LATEINIT = FlagField.booleanAfter(IS_CONST);
    public static final BooleanFlagField HAS_CONSTANT = FlagField.booleanAfter(IS_LATEINIT);
    public static final BooleanFlagField IS_EXTERNAL_PROPERTY = FlagField.booleanAfter(HAS_CONSTANT);
    public static final BooleanFlagField IS_DELEGATED = FlagField.booleanAfter(IS_EXTERNAL_PROPERTY);
    public static final BooleanFlagField IS_EXPECT_PROPERTY = FlagField.booleanAfter(IS_DELEGATED);
    public static final BooleanFlagField DECLARES_DEFAULT_VALUE = FlagField.booleanAfter(HAS_ANNOTATIONS);
    public static final BooleanFlagField IS_CROSSINLINE = FlagField.booleanAfter(DECLARES_DEFAULT_VALUE);
    public static final BooleanFlagField IS_NOINLINE = FlagField.booleanAfter(IS_CROSSINLINE);
    public static final BooleanFlagField IS_NOT_DEFAULT = FlagField.booleanAfter(MODALITY);
    public static final BooleanFlagField IS_EXTERNAL_ACCESSOR = FlagField.booleanAfter(IS_NOT_DEFAULT);
    public static final BooleanFlagField IS_INLINE_ACCESSOR = FlagField.booleanAfter(IS_EXTERNAL_ACCESSOR);
    public static final BooleanFlagField IS_NEGATED = FlagField.booleanFirst();
    public static final BooleanFlagField IS_NULL_CHECK_PREDICATE = FlagField.booleanAfter(IS_NEGATED);
    public static final BooleanFlagField IS_UNSIGNED = FlagField.booleanFirst();

    public static int getAccessorFlags(boolean hasAnnotations, @NotNull ProtoBuf.Visibility visibility, @NotNull ProtoBuf.Modality modality, boolean isNotDefault, boolean isExternal, boolean isInlineAccessor) {
        if (visibility == null) {
            Flags.$$$reportNull$$$0(10);
        }
        if (modality == null) {
            Flags.$$$reportNull$$$0(11);
        }
        return HAS_ANNOTATIONS.toFlags(hasAnnotations) | MODALITY.toFlags(modality) | VISIBILITY.toFlags(visibility) | IS_NOT_DEFAULT.toFlags(isNotDefault) | IS_EXTERNAL_ACCESSOR.toFlags(isExternal) | IS_INLINE_ACCESSOR.toFlags(isInlineAccessor);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int n) {
        Object[] objectArray;
        Object[] objectArray2;
        Object[] objectArray3 = new Object[3];
        switch (n) {
            default: {
                objectArray2 = objectArray3;
                objectArray3[0] = "visibility";
                break;
            }
            case 1: 
            case 5: 
            case 8: 
            case 11: {
                objectArray2 = objectArray3;
                objectArray3[0] = "modality";
                break;
            }
            case 2: {
                objectArray2 = objectArray3;
                objectArray3[0] = "kind";
                break;
            }
            case 6: 
            case 9: {
                objectArray2 = objectArray3;
                objectArray3[0] = "memberKind";
                break;
            }
        }
        objectArray2[1] = "kotlin/reflect/jvm/internal/impl/metadata/deserialization/Flags";
        switch (n) {
            default: {
                objectArray = objectArray2;
                objectArray2[2] = "getClassFlags";
                break;
            }
            case 3: {
                objectArray = objectArray2;
                objectArray2[2] = "getConstructorFlags";
                break;
            }
            case 4: 
            case 5: 
            case 6: {
                objectArray = objectArray2;
                objectArray2[2] = "getFunctionFlags";
                break;
            }
            case 7: 
            case 8: 
            case 9: {
                objectArray = objectArray2;
                objectArray2[2] = "getPropertyFlags";
                break;
            }
            case 10: 
            case 11: {
                objectArray = objectArray2;
                objectArray2[2] = "getAccessorFlags";
                break;
            }
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objectArray));
    }

    private static class EnumLiteFlagField<E extends Internal.EnumLite>
    extends FlagField<E> {
        private final E[] values;

        public EnumLiteFlagField(int offset, E[] values2) {
            super(offset, EnumLiteFlagField.bitWidth(values2));
            this.values = values2;
        }

        private static <E> int bitWidth(@NotNull E[] enumEntries) {
            int length;
            if (enumEntries == null) {
                EnumLiteFlagField.$$$reportNull$$$0(0);
            }
            if ((length = enumEntries.length - 1) == 0) {
                return 1;
            }
            for (int i = 31; i >= 0; --i) {
                if ((length & 1 << i) == 0) continue;
                return i + 1;
            }
            throw new IllegalStateException("Empty enum: " + enumEntries.getClass());
        }

        @Override
        @Nullable
        public E get(int flags) {
            int maskUnshifted = (1 << this.bitWidth) - 1;
            int mask = maskUnshifted << this.offset;
            int value = (flags & mask) >> this.offset;
            for (E e : this.values) {
                if (e.getNumber() != value) continue;
                return e;
            }
            return null;
        }

        @Override
        public int toFlags(E value) {
            return value.getNumber() << this.offset;
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "enumEntries", "kotlin/reflect/jvm/internal/impl/metadata/deserialization/Flags$EnumLiteFlagField", "bitWidth"));
        }
    }

    public static class BooleanFlagField
    extends FlagField<Boolean> {
        public BooleanFlagField(int offset) {
            super(offset, 1);
        }

        @Override
        @NotNull
        public Boolean get(int flags) {
            Boolean bl = (flags & 1 << this.offset) != 0;
            if (bl == null) {
                BooleanFlagField.$$$reportNull$$$0(0);
            }
            return bl;
        }

        @Override
        public int toFlags(Boolean value) {
            return value != false ? 1 << this.offset : 0;
        }

        private static /* synthetic */ void $$$reportNull$$$0(int n) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "kotlin/reflect/jvm/internal/impl/metadata/deserialization/Flags$BooleanFlagField", "get"));
        }
    }

    public static abstract class FlagField<E> {
        public final int offset;
        public final int bitWidth;

        public static <E extends Internal.EnumLite> FlagField<E> after(FlagField<?> previousField, E[] values2) {
            int offset = previousField.offset + previousField.bitWidth;
            return new EnumLiteFlagField(offset, values2);
        }

        public static BooleanFlagField booleanFirst() {
            return new BooleanFlagField(0);
        }

        public static BooleanFlagField booleanAfter(FlagField<?> previousField) {
            int offset = previousField.offset + previousField.bitWidth;
            return new BooleanFlagField(offset);
        }

        private FlagField(int offset, int bitWidth) {
            this.offset = offset;
            this.bitWidth = bitWidth;
        }

        public abstract E get(int var1);

        public abstract int toFlags(E var1);
    }
}

