/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.config;

import net.minecraft.block.state.BlockStateBase;
import net.minecraft.src.Config;
import net.optifine.config.Matches;

public class MatchBlock {
    private final int blockId;
    private int[] metadatas = null;

    public MatchBlock(int blockId) {
        this.blockId = blockId;
    }

    public MatchBlock(int blockId, int metadata) {
        this.blockId = blockId;
        if (metadata >= 0 && metadata <= 15) {
            this.metadatas = new int[]{metadata};
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
        return blockState.getBlockId() == this.blockId && Matches.metadata(blockState.getMetadata(), this.metadatas);
    }

    public boolean matches(int id, int metadata) {
        return id == this.blockId && Matches.metadata(metadata, this.metadatas);
    }

    public void addMetadata(int metadata) {
        if (this.metadatas != null && metadata >= 0 && metadata <= 15) {
            for (int j : this.metadatas) {
                if (j != metadata) continue;
                return;
            }
            this.metadatas = Config.addIntToArray(this.metadatas, metadata);
        }
    }

    public String toString() {
        return "" + this.blockId + ":" + Config.arrayToString(this.metadatas);
    }
}

