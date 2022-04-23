/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.NoWhenBranchMatchedException;
import kotlin._Assertions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmTypeFactory;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

final class JvmTypeFactoryImpl
implements JvmTypeFactory<JvmType> {
    public static final JvmTypeFactoryImpl INSTANCE;

    @Override
    @NotNull
    public JvmType boxType(@NotNull JvmType possiblyPrimitiveType) {
        JvmType jvmType;
        Intrinsics.checkNotNullParameter(possiblyPrimitiveType, "possiblyPrimitiveType");
        if (possiblyPrimitiveType instanceof JvmType.Primitive && ((JvmType.Primitive)possiblyPrimitiveType).getJvmPrimitiveType() != null) {
            JvmClassName jvmClassName = JvmClassName.byFqNameWithoutInnerClasses(((JvmType.Primitive)possiblyPrimitiveType).getJvmPrimitiveType().getWrapperFqName());
            Intrinsics.checkNotNullExpressionValue(jvmClassName, "JvmClassName.byFqNameWit\u2026mitiveType.wrapperFqName)");
            String string = jvmClassName.getInternalName();
            Intrinsics.checkNotNullExpressionValue(string, "JvmClassName.byFqNameWit\u2026apperFqName).internalName");
            jvmType = this.createObjectType(string);
        } else {
            jvmType = possiblyPrimitiveType;
        }
        return jvmType;
    }

    @Override
    @NotNull
    public JvmType createFromString(@NotNull String representation) {
        JvmType jvmType;
        int n;
        String string;
        Object object;
        int n2;
        char firstChar;
        block8: {
            Intrinsics.checkNotNullParameter(representation, "representation");
            CharSequence charSequence = representation;
            boolean bl = false;
            boolean bl2 = charSequence.length() > 0;
            bl = false;
            if (_Assertions.ENABLED && !bl2) {
                boolean $i$a$-assert-JvmTypeFactoryImpl$createFromString$22 = false;
                String $i$a$-assert-JvmTypeFactoryImpl$createFromString$22 = "empty string as JvmType";
                throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-JvmTypeFactoryImpl$createFromString$22));
            }
            firstChar = representation.charAt(0);
            JvmPrimitiveType[] $this$firstOrNull$iv = JvmPrimitiveType.values();
            boolean $i$f$firstOrNull = false;
            JvmPrimitiveType[] jvmPrimitiveTypeArray = $this$firstOrNull$iv;
            n2 = jvmPrimitiveTypeArray.length;
            for (int i = 0; i < n2; ++i) {
                JvmPrimitiveType element$iv;
                JvmPrimitiveType it = element$iv = jvmPrimitiveTypeArray[i];
                boolean bl3 = false;
                if (!(it.getDesc().charAt(0) == firstChar)) continue;
                object = element$iv;
                break block8;
            }
            object = null;
        }
        if (object != null) {
            string = object;
            n = 0;
            boolean bl = false;
            String it = string;
            boolean bl4 = false;
            return new JvmType.Primitive((JvmPrimitiveType)((Object)it));
        }
        switch (firstChar) {
            case 'V': {
                jvmType = new JvmType.Primitive(null);
                break;
            }
            case '[': {
                string = representation;
                n = 1;
                boolean bl = false;
                String string2 = string.substring(n);
                Intrinsics.checkNotNullExpressionValue(string2, "(this as java.lang.String).substring(startIndex)");
                jvmType = new JvmType.Array(this.createFromString(string2));
                break;
            }
            default: {
                boolean bl = firstChar == 'L' && StringsKt.endsWith$default((CharSequence)representation, ';', false, 2, null);
                n = 0;
                if (_Assertions.ENABLED && !bl) {
                    boolean bl5 = false;
                    String string3 = "Type that is not primitive nor array should be Object, but '" + representation + "' was found";
                    throw (Throwable)((Object)new AssertionError((Object)string3));
                }
                String string4 = representation;
                n = 1;
                int n3 = representation.length() - 1;
                n2 = 0;
                String string5 = string4.substring(n, n3);
                Intrinsics.checkNotNullExpressionValue(string5, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                jvmType = new JvmType.Object(string5);
            }
        }
        return jvmType;
    }

    @Override
    @NotNull
    public JvmType.Object createObjectType(@NotNull String internalName) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        return new JvmType.Object(internalName);
    }

    @Override
    @NotNull
    public String toString(@NotNull JvmType type2) {
        Object object;
        Intrinsics.checkNotNullParameter(type2, "type");
        JvmType jvmType = type2;
        if (jvmType instanceof JvmType.Array) {
            object = "[" + this.toString(((JvmType.Array)type2).getElementType());
        } else if (jvmType instanceof JvmType.Primitive) {
            Object object2 = ((JvmType.Primitive)type2).getJvmPrimitiveType();
            if (object2 == null || (object2 = object2.getDesc()) == null) {
                object2 = "V";
            }
            object = object2;
            Intrinsics.checkNotNullExpressionValue(object2, "type.jvmPrimitiveType?.desc ?: \"V\"");
        } else if (jvmType instanceof JvmType.Object) {
            object = "L" + ((JvmType.Object)type2).getInternalName() + ";";
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return object;
    }

    @Override
    @NotNull
    public JvmType getJavaLangClassType() {
        return this.createObjectType("java/lang/Class");
    }

    private JvmTypeFactoryImpl() {
    }

    static {
        JvmTypeFactoryImpl jvmTypeFactoryImpl;
        INSTANCE = jvmTypeFactoryImpl = new JvmTypeFactoryImpl();
    }
}

