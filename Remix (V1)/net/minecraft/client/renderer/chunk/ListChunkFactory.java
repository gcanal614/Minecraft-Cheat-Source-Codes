package net.minecraft.client.renderer.chunk;

import net.minecraft.world.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class ListChunkFactory implements IRenderChunkFactory
{
    @Override
    public RenderChunk func_178602_a(final World worldIn, final RenderGlobal p_178602_2_, final BlockPos p_178602_3_, final int p_178602_4_) {
        return new ListedRenderChunk(worldIn, p_178602_2_, p_178602_3_, p_178602_4_);
    }
}
