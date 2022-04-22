/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

public class ChatAllowedCharacters {
    public static final char[] allowedCharactersArray;

    static {
        char[] cArray = new char[15];
        cArray[0] = 47;
        cArray[1] = 10;
        cArray[2] = 13;
        cArray[3] = 9;
        cArray[5] = 12;
        cArray[6] = 96;
        cArray[7] = 63;
        cArray[8] = 42;
        cArray[9] = 92;
        cArray[10] = 60;
        cArray[11] = 62;
        cArray[12] = 124;
        cArray[13] = 34;
        cArray[14] = 58;
        allowedCharactersArray = cArray;
    }

    public static boolean isAllowedCharacter(char character) {
        return character != '\u00a7' && character >= ' ' && character != '\u007f';
    }

    public static String filterAllowedCharacters(String input) {
        StringBuilder stringbuilder = new StringBuilder();
        char[] cArray = input.toCharArray();
        int n = cArray.length;
        int n2 = 0;
        while (n2 < n) {
            char c0 = cArray[n2];
            if (ChatAllowedCharacters.isAllowedCharacter(c0)) {
                stringbuilder.append(c0);
            }
            ++n2;
        }
        return stringbuilder.toString();
    }
}

