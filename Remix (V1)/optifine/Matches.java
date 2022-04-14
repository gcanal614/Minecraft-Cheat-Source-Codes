package optifine;

import net.minecraft.block.state.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.biome.*;

public class Matches
{
    public static boolean block(final BlockStateBase blockStateBase, final MatchBlock[] matchBlocks) {
        if (matchBlocks == null) {
            return true;
        }
        for (int i = 0; i < matchBlocks.length; ++i) {
            final MatchBlock mb = matchBlocks[i];
            if (mb.matches(blockStateBase)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean block(final int blockId, final int metadata, final MatchBlock[] matchBlocks) {
        if (matchBlocks == null) {
            return true;
        }
        for (int i = 0; i < matchBlocks.length; ++i) {
            final MatchBlock mb = matchBlocks[i];
            if (mb.matches(blockId, metadata)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean blockId(final int blockId, final MatchBlock[] matchBlocks) {
        if (matchBlocks == null) {
            return true;
        }
        for (int i = 0; i < matchBlocks.length; ++i) {
            final MatchBlock mb = matchBlocks[i];
            if (mb.getBlockId() == blockId) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean metadata(final int metadata, final int[] metadatas) {
        if (metadatas == null) {
            return true;
        }
        for (int i = 0; i < metadatas.length; ++i) {
            if (metadatas[i] == metadata) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean sprite(final TextureAtlasSprite sprite, final TextureAtlasSprite[] sprites) {
        if (sprites == null) {
            return true;
        }
        for (int i = 0; i < sprites.length; ++i) {
            if (sprites[i] == sprite) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean biome(final BiomeGenBase biome, final BiomeGenBase[] biomes) {
        if (biomes == null) {
            return true;
        }
        for (int i = 0; i < biomes.length; ++i) {
            if (biomes[i] == biome) {
                return true;
            }
        }
        return false;
    }
}
