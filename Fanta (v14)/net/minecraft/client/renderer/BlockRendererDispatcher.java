/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  optifine.Config
 *  optifine.Reflector
 *  optifine.ReflectorMethod
 *  shadersmod.client.SVertexBuilder
 */
package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.ChestRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.client.resources.model.WeightedBakedModel;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import optifine.Config;
import optifine.Reflector;
import optifine.ReflectorMethod;
import shadersmod.client.SVertexBuilder;

public class BlockRendererDispatcher
implements IResourceManagerReloadListener {
    private BlockModelShapes blockModelShapes;
    private final GameSettings gameSettings;
    private final BlockModelRenderer blockModelRenderer = new BlockModelRenderer();
    private final ChestRenderer chestRenderer = new ChestRenderer();
    private final BlockFluidRenderer fluidRenderer = new BlockFluidRenderer();
    private static final String __OBFID = "CL_00002520";

    public BlockRendererDispatcher(BlockModelShapes blockModelShapesIn, GameSettings gameSettingsIn) {
        this.blockModelShapes = blockModelShapesIn;
        this.gameSettings = gameSettingsIn;
    }

    public BlockModelShapes getBlockModelShapes() {
        return this.blockModelShapes;
    }

    public void renderBlockDamage(IBlockState state, BlockPos pos, TextureAtlasSprite texture, IBlockAccess blockAccess) {
        Block block = state.getBlock();
        int i = block.getRenderType();
        if (i == 3) {
            IBakedModel ibakedmodel = this.blockModelShapes.getModelForState(state = block.getActualState(state, blockAccess, pos));
            if (Reflector.ISmartBlockModel.isInstance((Object)ibakedmodel)) {
                IBlockState iblockstate = (IBlockState)Reflector.call((Object)block, (ReflectorMethod)Reflector.ForgeBlock_getExtendedState, (Object[])new Object[]{state, blockAccess, pos});
                EnumWorldBlockLayer[] enumWorldBlockLayerArray = EnumWorldBlockLayer.values();
                int n = enumWorldBlockLayerArray.length;
                int n2 = 0;
                while (n2 < n) {
                    EnumWorldBlockLayer enumworldblocklayer = enumWorldBlockLayerArray[n2];
                    if (Reflector.callBoolean((Object)block, (ReflectorMethod)Reflector.ForgeBlock_canRenderInLayer, (Object[])new Object[]{enumworldblocklayer})) {
                        Reflector.callVoid((ReflectorMethod)Reflector.ForgeHooksClient_setRenderLayer, (Object[])new Object[]{enumworldblocklayer});
                        IBakedModel ibakedmodel2 = (IBakedModel)Reflector.call((Object)ibakedmodel, (ReflectorMethod)Reflector.ISmartBlockModel_handleBlockState, (Object[])new Object[]{iblockstate});
                        IBakedModel ibakedmodel3 = new SimpleBakedModel.Builder(ibakedmodel2, texture).makeBakedModel();
                        this.blockModelRenderer.renderModel(blockAccess, ibakedmodel3, state, pos, Tessellator.getInstance().getWorldRenderer());
                    }
                    ++n2;
                }
                return;
            }
            IBakedModel ibakedmodel1 = new SimpleBakedModel.Builder(ibakedmodel, texture).makeBakedModel();
            this.blockModelRenderer.renderModel(blockAccess, ibakedmodel1, state, pos, Tessellator.getInstance().getWorldRenderer());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean renderBlock(IBlockState state, BlockPos pos, IBlockAccess blockAccess, WorldRenderer worldRendererIn) {
        try {
            int i = state.getBlock().getRenderType();
            if (i == -1) {
                return false;
            }
            switch (i) {
                case 1: {
                    if (Config.isShaders()) {
                        SVertexBuilder.pushEntity((IBlockState)state, (BlockPos)pos, (IBlockAccess)blockAccess, (WorldRenderer)worldRendererIn);
                    }
                    boolean flag1 = this.fluidRenderer.renderFluid(blockAccess, state, pos, worldRendererIn);
                    if (Config.isShaders()) {
                        SVertexBuilder.popEntity((WorldRenderer)worldRendererIn);
                    }
                    return flag1;
                }
                case 2: {
                    return false;
                }
                case 3: {
                    IBakedModel ibakedmodel = this.getModelFromBlockState(state, blockAccess, pos);
                    if (Config.isShaders()) {
                        SVertexBuilder.pushEntity((IBlockState)state, (BlockPos)pos, (IBlockAccess)blockAccess, (WorldRenderer)worldRendererIn);
                    }
                    boolean flag = this.blockModelRenderer.renderModel(blockAccess, ibakedmodel, state, pos, worldRendererIn);
                    if (Config.isShaders()) {
                        SVertexBuilder.popEntity((WorldRenderer)worldRendererIn);
                    }
                    return flag;
                }
            }
            return false;
        }
        catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Tesselating block in world");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being tesselated");
            CrashReportCategory.addBlockInfo(crashreportcategory, pos, state.getBlock(), state.getBlock().getMetaFromState(state));
            throw new ReportedException(crashreport);
        }
    }

    public BlockModelRenderer getBlockModelRenderer() {
        return this.blockModelRenderer;
    }

    private IBakedModel getBakedModel(IBlockState state, BlockPos pos) {
        IBakedModel ibakedmodel = this.blockModelShapes.getModelForState(state);
        if (pos != null && this.gameSettings.allowBlockAlternatives && ibakedmodel instanceof WeightedBakedModel) {
            ibakedmodel = ((WeightedBakedModel)ibakedmodel).getAlternativeModel(MathHelper.getPositionRandom(pos));
        }
        return ibakedmodel;
    }

    public IBakedModel getModelFromBlockState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        Block block = state.getBlock();
        if (worldIn.getWorldType() != WorldType.DEBUG_WORLD) {
            try {
                state = block.getActualState(state, worldIn, pos);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        IBakedModel ibakedmodel = this.blockModelShapes.getModelForState(state);
        if (pos != null && this.gameSettings.allowBlockAlternatives && ibakedmodel instanceof WeightedBakedModel) {
            ibakedmodel = ((WeightedBakedModel)ibakedmodel).getAlternativeModel(MathHelper.getPositionRandom(pos));
        }
        if (Reflector.ISmartBlockModel.isInstance((Object)ibakedmodel)) {
            IBlockState iblockstate = (IBlockState)Reflector.call((Object)block, (ReflectorMethod)Reflector.ForgeBlock_getExtendedState, (Object[])new Object[]{state, worldIn, pos});
            ibakedmodel = (IBakedModel)Reflector.call((Object)ibakedmodel, (ReflectorMethod)Reflector.ISmartBlockModel_handleBlockState, (Object[])new Object[]{iblockstate});
        }
        return ibakedmodel;
    }

    public void renderBlockBrightness(IBlockState state, float brightness) {
        int i = state.getBlock().getRenderType();
        if (i != -1) {
            switch (i) {
                default: {
                    break;
                }
                case 2: {
                    this.chestRenderer.renderChestBrightness(state.getBlock(), brightness);
                    break;
                }
                case 3: {
                    IBakedModel ibakedmodel = this.getBakedModel(state, null);
                    this.blockModelRenderer.renderModelBrightness(ibakedmodel, state, brightness, true);
                }
            }
        }
    }

    public boolean isRenderTypeChest(Block p_175021_1_, int p_175021_2_) {
        if (p_175021_1_ == null) {
            return false;
        }
        int i = p_175021_1_.getRenderType();
        return i == 3 ? false : i == 2;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        this.fluidRenderer.initAtlasSprites();
    }
}

