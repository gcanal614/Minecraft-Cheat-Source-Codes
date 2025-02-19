/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  optifine.Config
 */
package net.minecraft.block;

import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import optifine.Config;

public class BlockLeavesBase
extends Block {
    protected boolean fancyGraphics;
    private static final String __OBFID = "CL_00000326";
    private static Map mapOriginalOpacity = new IdentityHashMap();

    protected BlockLeavesBase(Material materialIn, boolean fancyGraphics) {
        super(materialIn);
        this.fancyGraphics = fancyGraphics;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return Config.isCullFacesLeaves() && worldIn.getBlockState(pos).getBlock() == this ? false : super.shouldSideBeRendered(worldIn, pos, side);
    }

    public static void setLightOpacity(Block p_setLightOpacity_0_, int p_setLightOpacity_1_) {
        if (!mapOriginalOpacity.containsKey(p_setLightOpacity_0_)) {
            mapOriginalOpacity.put(p_setLightOpacity_0_, p_setLightOpacity_0_.getLightOpacity());
        }
        p_setLightOpacity_0_.setLightOpacity(p_setLightOpacity_1_);
    }

    public static void restoreLightOpacity(Block p_restoreLightOpacity_0_) {
        if (mapOriginalOpacity.containsKey(p_restoreLightOpacity_0_)) {
            int i = (Integer)mapOriginalOpacity.get(p_restoreLightOpacity_0_);
            BlockLeavesBase.setLightOpacity(p_restoreLightOpacity_0_, i);
        }
    }
}

