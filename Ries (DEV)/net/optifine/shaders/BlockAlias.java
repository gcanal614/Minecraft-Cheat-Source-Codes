/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.shaders;

import java.util.ArrayList;
import java.util.HashSet;
import net.minecraft.src.Config;
import net.optifine.config.MatchBlock;

public class BlockAlias {
    private final int blockAliasId;
    private final MatchBlock[] matchBlocks;

    public BlockAlias(int blockAliasId, MatchBlock[] matchBlocks) {
        this.blockAliasId = blockAliasId;
        this.matchBlocks = matchBlocks;
    }

    public int getBlockAliasId() {
        return this.blockAliasId;
    }

    public boolean matches(int id, int metadata) {
        for (MatchBlock matchblock : this.matchBlocks) {
            if (!matchblock.matches(id, metadata)) continue;
            return true;
        }
        return false;
    }

    public int[] getMatchBlockIds() {
        HashSet<Integer> set = new HashSet<Integer>();
        for (MatchBlock matchblock : this.matchBlocks) {
            int j = matchblock.getBlockId();
            set.add(j);
        }
        Integer[] ainteger = set.toArray(new Integer[0]);
        return Config.toPrimitive(ainteger);
    }

    public MatchBlock[] getMatchBlocks(int matchBlockId) {
        ArrayList<MatchBlock> list = new ArrayList<MatchBlock>();
        for (MatchBlock matchblock : this.matchBlocks) {
            if (matchblock.getBlockId() != matchBlockId) continue;
            list.add(matchblock);
        }
        return list.toArray(new MatchBlock[0]);
    }

    public String toString() {
        return "block." + this.blockAliasId + "=" + Config.arrayToString(this.matchBlocks);
    }
}

