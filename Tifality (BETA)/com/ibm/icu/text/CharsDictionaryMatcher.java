/*
 * Decompiled with CFR 0.152.
 */
package com.ibm.icu.text;

import com.ibm.icu.text.DictionaryMatcher;
import com.ibm.icu.text.UCharacterIterator;
import com.ibm.icu.util.BytesTrie;
import com.ibm.icu.util.CharsTrie;
import java.text.CharacterIterator;

class CharsDictionaryMatcher
extends DictionaryMatcher {
    private CharSequence characters;

    public CharsDictionaryMatcher(CharSequence chars) {
        this.characters = chars;
    }

    public int matches(CharacterIterator text_, int maxLength, int[] lengths, int[] count_, int limit, int[] values2) {
        UCharacterIterator text = UCharacterIterator.getInstance(text_);
        CharsTrie uct = new CharsTrie(this.characters, 0);
        int c = text.nextCodePoint();
        BytesTrie.Result result2 = uct.firstForCodePoint(c);
        int numChars = 1;
        int count = 0;
        while (true) {
            if (result2.hasValue()) {
                if (count < limit) {
                    if (values2 != null) {
                        values2[count] = uct.getValue();
                    }
                    lengths[count] = numChars;
                    ++count;
                }
                if (result2 == BytesTrie.Result.FINAL_VALUE) {
                    break;
                }
            } else if (result2 == BytesTrie.Result.NO_MATCH) break;
            if (numChars >= maxLength) break;
            c = text.nextCodePoint();
            ++numChars;
            result2 = uct.nextForCodePoint(c);
        }
        count_[0] = count;
        return numChars;
    }

    public int getType() {
        return 1;
    }
}

