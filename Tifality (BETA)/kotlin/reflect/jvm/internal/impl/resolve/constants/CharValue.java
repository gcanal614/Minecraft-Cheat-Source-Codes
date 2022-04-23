/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerValueConstant;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import org.jetbrains.annotations.NotNull;

public final class CharValue
extends IntegerValueConstant<Character> {
    @Override
    @NotNull
    public SimpleType getType(@NotNull ModuleDescriptor module) {
        Intrinsics.checkNotNullParameter(module, "module");
        SimpleType simpleType2 = module.getBuiltIns().getCharType();
        Intrinsics.checkNotNullExpressionValue(simpleType2, "module.builtIns.charType");
        return simpleType2;
    }

    @Override
    @NotNull
    public String toString() {
        String string = "\\u%04X ('%s')";
        Object[] objectArray = new Object[]{(int)((Character)this.getValue()).charValue(), this.getPrintablePart(((Character)this.getValue()).charValue())};
        boolean bl = false;
        String string2 = String.format(string, Arrays.copyOf(objectArray, objectArray.length));
        Intrinsics.checkNotNullExpressionValue(string2, "java.lang.String.format(this, *args)");
        return string2;
    }

    private final String getPrintablePart(char c) {
        String string;
        switch (c) {
            case '\b': {
                string = "\\b";
                break;
            }
            case '\t': {
                string = "\\t";
                break;
            }
            case '\n': {
                string = "\\n";
                break;
            }
            case '\f': {
                string = "\\f";
                break;
            }
            case '\r': {
                string = "\\r";
                break;
            }
            default: {
                string = this.isPrintableUnicode(c) ? String.valueOf(c) : "?";
            }
        }
        return string;
    }

    private final boolean isPrintableUnicode(char c) {
        byte t = (byte)Character.getType(c);
        return t != 0 && t != 13 && t != 14 && t != 15 && t != 16 && t != 18 && t != 19;
    }

    public CharValue(char value) {
        super(Character.valueOf(value));
    }
}

