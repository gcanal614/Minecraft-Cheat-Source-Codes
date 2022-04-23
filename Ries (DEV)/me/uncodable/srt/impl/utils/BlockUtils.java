/*
 * Decompiled with CFR 0.152.
 */
package me.uncodable.srt.impl.utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

public class BlockUtils {
    private static final Minecraft MC = Minecraft.getMinecraft();
    private static final BlockPos pos = new BlockPos(BlockUtils.MC.thePlayer.posX, BlockUtils.MC.thePlayer.getEntityBoundingBox().minY - 0.5, BlockUtils.MC.thePlayer.posZ);

    public static Block getBlockUnderneath() {
        return BlockUtils.MC.theWorld.getBlockState(pos).getBlock();
    }
}

