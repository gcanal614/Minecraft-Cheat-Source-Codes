/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class ChunkCache
implements IBlockAccess {
    protected int chunkX;
    protected int chunkZ;
    protected Chunk[][] chunkArray;
    protected boolean hasExtendedLevels;
    protected World worldObj;

    public ChunkCache(World worldIn, BlockPos posFromIn, BlockPos posToIn, int subIn) {
        this.worldObj = worldIn;
        this.chunkX = posFromIn.getX() - subIn >> 4;
        this.chunkZ = posFromIn.getZ() - subIn >> 4;
        int i = posToIn.getX() + subIn >> 4;
        int j = posToIn.getZ() + subIn >> 4;
        this.chunkArray = new Chunk[i - this.chunkX + 1][j - this.chunkZ + 1];
        this.hasExtendedLevels = true;
        int k = this.chunkX;
        while (k <= i) {
            int l = this.chunkZ;
            while (l <= j) {
                this.chunkArray[k - this.chunkX][l - this.chunkZ] = worldIn.getChunkFromChunkCoords(k, l);
                ++l;
            }
            ++k;
        }
        int i1 = posFromIn.getX() >> 4;
        while (i1 <= posToIn.getX() >> 4) {
            int j1 = posFromIn.getZ() >> 4;
            while (j1 <= posToIn.getZ() >> 4) {
                Chunk chunk = this.chunkArray[i1 - this.chunkX][j1 - this.chunkZ];
                if (chunk != null && !chunk.getAreLevelsEmpty(posFromIn.getY(), posToIn.getY())) {
                    this.hasExtendedLevels = false;
                }
                ++j1;
            }
            ++i1;
        }
    }

    @Override
    public boolean extendedLevelsInChunkCache() {
        return this.hasExtendedLevels;
    }

    @Override
    public TileEntity getTileEntity(BlockPos pos) {
        int i = (pos.getX() >> 4) - this.chunkX;
        int j = (pos.getZ() >> 4) - this.chunkZ;
        return this.chunkArray[i][j].getTileEntity(pos, Chunk.EnumCreateEntityType.IMMEDIATE);
    }

    @Override
    public int getCombinedLight(BlockPos pos, int lightValue) {
        int i = this.getLightForExt(EnumSkyBlock.SKY, pos);
        int j = this.getLightForExt(EnumSkyBlock.BLOCK, pos);
        if (j < lightValue) {
            j = lightValue;
        }
        return i << 20 | j << 4;
    }

    @Override
    public IBlockState getBlockState(BlockPos pos) {
        if (pos.getY() >= 0 && pos.getY() < 256) {
            Chunk chunk;
            int i = (pos.getX() >> 4) - this.chunkX;
            int j = (pos.getZ() >> 4) - this.chunkZ;
            if (i >= 0 && i < this.chunkArray.length && j >= 0 && j < this.chunkArray[i].length && (chunk = this.chunkArray[i][j]) != null) {
                return chunk.getBlockState(pos);
            }
        }
        return Blocks.air.getDefaultState();
    }

    @Override
    public BiomeGenBase getBiomeGenForCoords(BlockPos pos) {
        return this.worldObj.getBiomeGenForCoords(pos);
    }

    private int getLightForExt(EnumSkyBlock p_175629_1_, BlockPos pos) {
        if (p_175629_1_ == EnumSkyBlock.SKY && this.worldObj.provider.getHasNoSky()) {
            return 0;
        }
        if (pos.getY() >= 0 && pos.getY() < 256) {
            if (this.getBlockState(pos).getBlock().getUseNeighborBrightness()) {
                int l = 0;
                EnumFacing[] enumFacingArray = EnumFacing.values();
                int n = enumFacingArray.length;
                int n2 = 0;
                while (n2 < n) {
                    EnumFacing enumfacing = enumFacingArray[n2];
                    int k = this.getLightFor(p_175629_1_, pos.offset(enumfacing));
                    if (k > l) {
                        l = k;
                    }
                    if (l >= 15) {
                        return l;
                    }
                    ++n2;
                }
                return l;
            }
            int i = (pos.getX() >> 4) - this.chunkX;
            int j = (pos.getZ() >> 4) - this.chunkZ;
            return this.chunkArray[i][j].getLightFor(p_175629_1_, pos);
        }
        return p_175629_1_.defaultLightValue;
    }

    @Override
    public boolean isAirBlock(BlockPos pos) {
        return this.getBlockState(pos).getBlock().getMaterial() == Material.air;
    }

    public int getLightFor(EnumSkyBlock p_175628_1_, BlockPos pos) {
        if (pos.getY() >= 0 && pos.getY() < 256) {
            int i = (pos.getX() >> 4) - this.chunkX;
            int j = (pos.getZ() >> 4) - this.chunkZ;
            return this.chunkArray[i][j].getLightFor(p_175628_1_, pos);
        }
        return p_175628_1_.defaultLightValue;
    }

    @Override
    public int getStrongPower(BlockPos pos, EnumFacing direction) {
        IBlockState iblockstate = this.getBlockState(pos);
        return iblockstate.getBlock().getStrongPower(this, pos, iblockstate, direction);
    }

    @Override
    public WorldType getWorldType() {
        return this.worldObj.getWorldType();
    }
}

