/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.chunk;

import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.client.renderer.chunk.SetVisibility;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import optifine.IntegerCache;

public class VisGraph {
    private static final int field_178616_a = (int)Math.pow(16.0, 0.0);
    private static final int field_178614_b = (int)Math.pow(16.0, 1.0);
    private static final int field_178615_c = (int)Math.pow(16.0, 2.0);
    private final BitSet field_178612_d = new BitSet(4096);
    private static final int[] field_178613_e = new int[1352];
    private int field_178611_f = 4096;

    public void func_178606_a(BlockPos pos) {
        this.field_178612_d.set(VisGraph.getIndex(pos), true);
        --this.field_178611_f;
    }

    private static int getIndex(BlockPos pos) {
        return VisGraph.getIndex(pos.getX() & 0xF, pos.getY() & 0xF, pos.getZ() & 0xF);
    }

    private static int getIndex(int x2, int y2, int z2) {
        return x2 << 0 | y2 << 8 | z2 << 4;
    }

    public SetVisibility computeVisibility() {
        SetVisibility setvisibility = new SetVisibility();
        if (4096 - this.field_178611_f < 256) {
            setvisibility.setAllVisible(true);
        } else if (this.field_178611_f == 0) {
            setvisibility.setAllVisible(false);
        } else {
            for (int i2 : field_178613_e) {
                if (this.field_178612_d.get(i2)) continue;
                setvisibility.setManyVisible(this.func_178604_a(i2));
            }
        }
        return setvisibility;
    }

    public Set func_178609_b(BlockPos pos) {
        return this.func_178604_a(VisGraph.getIndex(pos));
    }

    private Set func_178604_a(int p_178604_1_) {
        EnumSet<EnumFacing> enumset = EnumSet.noneOf(EnumFacing.class);
        ArrayDeque<Integer> arraydeque = new ArrayDeque<Integer>(384);
        arraydeque.add(IntegerCache.valueOf(p_178604_1_));
        this.field_178612_d.set(p_178604_1_, true);
        while (!arraydeque.isEmpty()) {
            int i2 = (Integer)arraydeque.poll();
            this.func_178610_a(i2, enumset);
            for (EnumFacing enumfacing : EnumFacing.VALUES) {
                int j2 = this.func_178603_a(i2, enumfacing);
                if (j2 < 0 || this.field_178612_d.get(j2)) continue;
                this.field_178612_d.set(j2, true);
                arraydeque.add(IntegerCache.valueOf(j2));
            }
        }
        return enumset;
    }

    private void func_178610_a(int p_178610_1_, Set p_178610_2_) {
        int i2 = p_178610_1_ >> 0 & 0xF;
        if (i2 == 0) {
            p_178610_2_.add(EnumFacing.WEST);
        } else if (i2 == 15) {
            p_178610_2_.add(EnumFacing.EAST);
        }
        int j2 = p_178610_1_ >> 8 & 0xF;
        if (j2 == 0) {
            p_178610_2_.add(EnumFacing.DOWN);
        } else if (j2 == 15) {
            p_178610_2_.add(EnumFacing.UP);
        }
        int k2 = p_178610_1_ >> 4 & 0xF;
        if (k2 == 0) {
            p_178610_2_.add(EnumFacing.NORTH);
        } else if (k2 == 15) {
            p_178610_2_.add(EnumFacing.SOUTH);
        }
    }

    private int func_178603_a(int p_178603_1_, EnumFacing p_178603_2_) {
        switch (p_178603_2_) {
            case DOWN: {
                if ((p_178603_1_ >> 8 & 0xF) == 0) {
                    return -1;
                }
                return p_178603_1_ - field_178615_c;
            }
            case UP: {
                if ((p_178603_1_ >> 8 & 0xF) == 15) {
                    return -1;
                }
                return p_178603_1_ + field_178615_c;
            }
            case NORTH: {
                if ((p_178603_1_ >> 4 & 0xF) == 0) {
                    return -1;
                }
                return p_178603_1_ - field_178614_b;
            }
            case SOUTH: {
                if ((p_178603_1_ >> 4 & 0xF) == 15) {
                    return -1;
                }
                return p_178603_1_ + field_178614_b;
            }
            case WEST: {
                if ((p_178603_1_ >> 0 & 0xF) == 0) {
                    return -1;
                }
                return p_178603_1_ - field_178616_a;
            }
            case EAST: {
                if ((p_178603_1_ >> 0 & 0xF) == 15) {
                    return -1;
                }
                return p_178603_1_ + field_178616_a;
            }
        }
        return -1;
    }

    static {
        boolean flag = false;
        boolean flag1 = true;
        int i2 = 0;
        for (int j2 = 0; j2 < 16; ++j2) {
            for (int k2 = 0; k2 < 16; ++k2) {
                for (int l2 = 0; l2 < 16; ++l2) {
                    if (j2 != 0 && j2 != 15 && k2 != 0 && k2 != 15 && l2 != 0 && l2 != 15) continue;
                    VisGraph.field_178613_e[i2++] = VisGraph.getIndex(j2, k2, l2);
                }
            }
        }
    }
}

