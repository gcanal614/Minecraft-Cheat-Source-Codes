/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.config;

import net.minecraft.block.state.BlockStateBase;
import net.minecraft.src.Config;
import net.optifine.config.Matches;

public class MatchBlock {
    private int blockId = -1;
    private int[] metadatas = null;

    public MatchBlock(int blockId) {
        this.blockId = blockId;
    }

    public MatchBlock(int blockId, int metadata2) {
        this.blockId = blockId;
        if (metadata2 >= 0 && metadata2 <= 15) {
            this.metadatas = new int[]{metadata2};
        }
    }

    public MatchBlock(int blockId, int[] metadatas) {
        this.blockId = blockId;
        this.metadatas = metadatas;
    }

    public int getBlockId() {
        return this.blockId;
    }

    public int[] getMetadatas() {
        return this.metadatas;
    }

    public boolean matches(BlockStateBase blockState) {
        return blockState.getBlockId() != this.blockId ? false : Matches.metadata(blockState.getMetadata(), this.metadatas);
    }

    public boolean matches(int id, int metadata2) {
        return id != this.blockId ? false : Matches.metadata(metadata2, this.metadatas);
    }

    public void addMetadata(int metadata2) {
        if (this.metadatas != null && metadata2 >= 0 && metadata2 <= 15) {
            for (int i = 0; i < this.metadatas.length; ++i) {
                if (this.metadatas[i] != metadata2) continue;
                return;
            }
            this.metadatas = Config.addIntToArray(this.metadatas, metadata2);
        }
    }

    public String toString() {
        return "" + this.blockId + ":" + Config.arrayToString(this.metadatas);
    }
}

