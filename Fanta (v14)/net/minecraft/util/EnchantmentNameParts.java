/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

import java.util.Random;

public class EnchantmentNameParts {
    private static final EnchantmentNameParts instance = new EnchantmentNameParts();
    private Random rand = new Random();
    private String[] namePartsArray = "the elder scrolls klaatu berata niktu xyzzy bless curse light darkness fire air earth water hot dry cold wet ignite snuff embiggen twist shorten stretch fiddle destroy imbue galvanize enchant free limited range of towards inside sphere cube self other ball mental physical grow shrink demon elemental spirit animal creature beast humanoid undead fresh stale ".split(" ");

    public static EnchantmentNameParts getInstance() {
        return instance;
    }

    public String generateNewRandomName() {
        int i = this.rand.nextInt(2) + 3;
        String s = "";
        int j = 0;
        while (j < i) {
            if (j > 0) {
                s = String.valueOf(s) + " ";
            }
            s = String.valueOf(s) + this.namePartsArray[this.rand.nextInt(this.namePartsArray.length)];
            ++j;
        }
        return s;
    }

    public void reseedRandomGenerator(long seed) {
        this.rand.setSeed(seed);
    }
}

