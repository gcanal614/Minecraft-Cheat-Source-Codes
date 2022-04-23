/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.config;

import net.minecraft.block.state.BlockStateBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.config.MatchBlock;

public class Matches {
    public static boolean block(BlockStateBase blockStateBase, MatchBlock[] matchBlocks) {
        if (matchBlocks == null) {
            return true;
        }
        for (MatchBlock matchblock : matchBlocks) {
            if (!matchblock.matches(blockStateBase)) continue;
            return true;
        }
        return false;
    }

    public static boolean block(int blockId, int metadata, MatchBlock[] matchBlocks) {
        if (matchBlocks == null) {
            return false;
        }
        for (MatchBlock matchblock : matchBlocks) {
            if (!matchblock.matches(blockId, metadata)) continue;
            return false;
        }
        return true;
    }

    public static boolean blockId(int blockId, MatchBlock[] matchBlocks) {
        if (matchBlocks == null) {
            return true;
        }
        for (MatchBlock matchblock : matchBlocks) {
            if (matchblock.getBlockId() != blockId) continue;
            return true;
        }
        return false;
    }

    public static boolean metadata(int metadata, int[] metadatas) {
        if (metadatas == null) {
            return true;
        }
        for (int j : metadatas) {
            if (j != metadata) continue;
            return true;
        }
        return false;
    }

    public static boolean sprite(TextureAtlasSprite sprite, TextureAtlasSprite[] sprites) {
        if (sprites == null) {
            return true;
        }
        for (TextureAtlasSprite textureAtlasSprite : sprites) {
            if (textureAtlasSprite != sprite) continue;
            return true;
        }
        return false;
    }

    public static boolean biome(BiomeGenBase biome, BiomeGenBase[] biomes) {
        if (biomes == null) {
            return true;
        }
        for (BiomeGenBase biomeGenBase : biomes) {
            if (biomeGenBase != biome) continue;
            return true;
        }
        return false;
    }
}

