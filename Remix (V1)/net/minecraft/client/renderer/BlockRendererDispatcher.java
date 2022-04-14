package net.minecraft.client.renderer;

import net.minecraft.client.settings.*;
import net.minecraft.block.state.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.*;
import optifine.*;
import shadersmod.client.*;
import net.minecraft.crash.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.client.resources.*;

public class BlockRendererDispatcher implements IResourceManagerReloadListener
{
    private final GameSettings field_175026_b;
    private final BlockModelRenderer blockModelRenderer;
    private final ChestRenderer chestRenderer;
    private final BlockFluidRenderer fluidRenderer;
    private BlockModelShapes field_175028_a;
    
    public BlockRendererDispatcher(final BlockModelShapes p_i46237_1_, final GameSettings p_i46237_2_) {
        this.blockModelRenderer = new BlockModelRenderer();
        this.chestRenderer = new ChestRenderer();
        this.fluidRenderer = new BlockFluidRenderer();
        this.field_175028_a = p_i46237_1_;
        this.field_175026_b = p_i46237_2_;
    }
    
    public BlockModelShapes func_175023_a() {
        return this.field_175028_a;
    }
    
    public void func_175020_a(IBlockState p_175020_1_, final BlockPos p_175020_2_, final TextureAtlasSprite p_175020_3_, final IBlockAccess p_175020_4_) {
        final Block var5 = p_175020_1_.getBlock();
        final int var6 = var5.getRenderType();
        if (var6 == 3) {
            p_175020_1_ = var5.getActualState(p_175020_1_, p_175020_4_, p_175020_2_);
            final IBakedModel var7 = this.field_175028_a.func_178125_b(p_175020_1_);
            if (Reflector.ISmartBlockModel.isInstance(var7)) {
                final IBlockState var8 = (IBlockState)Reflector.call(var5, Reflector.ForgeBlock_getExtendedState, p_175020_1_, p_175020_4_, p_175020_2_);
                for (final EnumWorldBlockLayer layer : EnumWorldBlockLayer.values()) {
                    if (Reflector.callBoolean(var5, Reflector.ForgeBlock_canRenderInLayer, layer)) {
                        Reflector.callVoid(Reflector.ForgeHooksClient_setRenderLayer, layer);
                        final IBakedModel targetLayer = (IBakedModel)Reflector.call(var7, Reflector.ISmartBlockModel_handleBlockState, var8);
                        final IBakedModel damageModel = new SimpleBakedModel.Builder(targetLayer, p_175020_3_).func_177645_b();
                        this.blockModelRenderer.func_178259_a(p_175020_4_, damageModel, p_175020_1_, p_175020_2_, Tessellator.getInstance().getWorldRenderer());
                    }
                }
                return;
            }
            final IBakedModel var9 = new SimpleBakedModel.Builder(var7, p_175020_3_).func_177645_b();
            this.blockModelRenderer.func_178259_a(p_175020_4_, var9, p_175020_1_, p_175020_2_, Tessellator.getInstance().getWorldRenderer());
        }
    }
    
    public boolean func_175018_a(final IBlockState p_175018_1_, final BlockPos p_175018_2_, final IBlockAccess p_175018_3_, final WorldRenderer p_175018_4_) {
        try {
            final int var8 = p_175018_1_.getBlock().getRenderType();
            if (var8 == -1) {
                return false;
            }
            switch (var8) {
                case 1: {
                    if (Config.isShaders()) {
                        SVertexBuilder.pushEntity(p_175018_1_, p_175018_2_, p_175018_3_, p_175018_4_);
                    }
                    final boolean var9 = this.fluidRenderer.func_178270_a(p_175018_3_, p_175018_1_, p_175018_2_, p_175018_4_);
                    if (Config.isShaders()) {
                        SVertexBuilder.popEntity(p_175018_4_);
                    }
                    return var9;
                }
                case 2: {
                    return false;
                }
                case 3: {
                    final IBakedModel var10 = this.getModelFromBlockState(p_175018_1_, p_175018_3_, p_175018_2_);
                    if (Config.isShaders()) {
                        SVertexBuilder.pushEntity(p_175018_1_, p_175018_2_, p_175018_3_, p_175018_4_);
                    }
                    final boolean flag3 = this.blockModelRenderer.func_178259_a(p_175018_3_, var10, p_175018_1_, p_175018_2_, p_175018_4_);
                    if (Config.isShaders()) {
                        SVertexBuilder.popEntity(p_175018_4_);
                    }
                    return flag3;
                }
                default: {
                    return false;
                }
            }
        }
        catch (Throwable var12) {
            final CrashReport var11 = CrashReport.makeCrashReport(var12, "Tesselating block in world");
            final CrashReportCategory var13 = var11.makeCategory("Block being tesselated");
            CrashReportCategory.addBlockInfo(var13, p_175018_2_, p_175018_1_.getBlock(), p_175018_1_.getBlock().getMetaFromState(p_175018_1_));
            throw new ReportedException(var11);
        }
    }
    
    public BlockModelRenderer func_175019_b() {
        return this.blockModelRenderer;
    }
    
    private IBakedModel func_175017_a(final IBlockState p_175017_1_, final BlockPos p_175017_2_) {
        IBakedModel var3 = this.field_175028_a.func_178125_b(p_175017_1_);
        if (p_175017_2_ != null && this.field_175026_b.field_178880_u && var3 instanceof WeightedBakedModel) {
            var3 = ((WeightedBakedModel)var3).func_177564_a(MathHelper.func_180186_a(p_175017_2_));
        }
        return var3;
    }
    
    public IBakedModel getModelFromBlockState(IBlockState p_175022_1_, final IBlockAccess p_175022_2_, final BlockPos p_175022_3_) {
        final Block var4 = p_175022_1_.getBlock();
        if (p_175022_2_.getWorldType() != WorldType.DEBUG_WORLD) {
            try {
                p_175022_1_ = var4.getActualState(p_175022_1_, p_175022_2_, p_175022_3_);
            }
            catch (Exception ex) {}
        }
        IBakedModel var5 = this.field_175028_a.func_178125_b(p_175022_1_);
        if (p_175022_3_ != null && this.field_175026_b.field_178880_u && var5 instanceof WeightedBakedModel) {
            var5 = ((WeightedBakedModel)var5).func_177564_a(MathHelper.func_180186_a(p_175022_3_));
        }
        if (Reflector.ISmartBlockModel.isInstance(var5)) {
            final IBlockState extendedState = (IBlockState)Reflector.call(var4, Reflector.ForgeBlock_getExtendedState, p_175022_1_, p_175022_2_, p_175022_3_);
            var5 = (IBakedModel)Reflector.call(var5, Reflector.ISmartBlockModel_handleBlockState, extendedState);
        }
        return var5;
    }
    
    public void func_175016_a(final IBlockState p_175016_1_, final float p_175016_2_) {
        final int var3 = p_175016_1_.getBlock().getRenderType();
        if (var3 != -1) {
            switch (var3) {
                case 2: {
                    this.chestRenderer.func_178175_a(p_175016_1_.getBlock(), p_175016_2_);
                    break;
                }
                case 3: {
                    final IBakedModel var4 = this.func_175017_a(p_175016_1_, null);
                    this.blockModelRenderer.func_178266_a(var4, p_175016_1_, p_175016_2_, true);
                    break;
                }
            }
        }
    }
    
    public boolean func_175021_a(final Block p_175021_1_, final int p_175021_2_) {
        if (p_175021_1_ == null) {
            return false;
        }
        final int var3 = p_175021_1_.getRenderType();
        return var3 != 3 && var3 == 2;
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager resourceManager) {
        this.fluidRenderer.func_178268_a();
    }
}
