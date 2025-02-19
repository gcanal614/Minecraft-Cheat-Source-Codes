/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.chunk;

import java.util.BitSet;
import java.util.Set;
import net.minecraft.util.EnumFacing;

public class SetVisibility {
    private static final int COUNT_FACES = EnumFacing.values().length;
    private final BitSet bitSet = new BitSet(COUNT_FACES * COUNT_FACES);

    public void setManyVisible(Set<EnumFacing> p_178620_1_) {
        for (EnumFacing enumfacing : p_178620_1_) {
            for (EnumFacing enumfacing1 : p_178620_1_) {
                this.setVisible(enumfacing, enumfacing1, true);
            }
        }
    }

    public void setVisible(EnumFacing facing, EnumFacing facing2, boolean p_178619_3_) {
        this.bitSet.set(facing.ordinal() + facing2.ordinal() * COUNT_FACES, p_178619_3_);
        this.bitSet.set(facing2.ordinal() + facing.ordinal() * COUNT_FACES, p_178619_3_);
    }

    public void setAllVisible(boolean visible) {
        this.bitSet.set(0, this.bitSet.size(), visible);
    }

    public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
        return this.bitSet.get(facing.ordinal() + facing2.ordinal() * COUNT_FACES);
    }

    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(' ');
        EnumFacing[] enumFacingArray = EnumFacing.values();
        int n = enumFacingArray.length;
        int n2 = 0;
        while (n2 < n) {
            EnumFacing enumfacing = enumFacingArray[n2];
            stringbuilder.append(' ').append(enumfacing.toString().toUpperCase().charAt(0));
            ++n2;
        }
        stringbuilder.append('\n');
        enumFacingArray = EnumFacing.values();
        n = enumFacingArray.length;
        n2 = 0;
        while (n2 < n) {
            EnumFacing enumfacing2 = enumFacingArray[n2];
            stringbuilder.append(enumfacing2.toString().toUpperCase().charAt(0));
            EnumFacing[] enumFacingArray2 = EnumFacing.values();
            int n3 = enumFacingArray2.length;
            int n4 = 0;
            while (n4 < n3) {
                EnumFacing enumfacing1 = enumFacingArray2[n4];
                if (enumfacing2 == enumfacing1) {
                    stringbuilder.append("  ");
                } else {
                    boolean flag = this.isVisible(enumfacing2, enumfacing1);
                    stringbuilder.append(' ').append(flag ? (char)'Y' : 'n');
                }
                ++n4;
            }
            stringbuilder.append('\n');
            ++n2;
        }
        return stringbuilder.toString();
    }
}

