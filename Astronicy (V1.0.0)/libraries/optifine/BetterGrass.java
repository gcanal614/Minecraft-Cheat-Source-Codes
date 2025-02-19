package libraries.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

public class BetterGrass
{
    private static boolean betterGrass = true;
    private static boolean betterMycelium = true;
    private static boolean betterPodzol = true;
    private static boolean betterGrassSnow = true;
    private static boolean betterMyceliumSnow = true;
    private static boolean betterPodzolSnow = true;
    private static boolean grassMultilayer = false;
    private static TextureAtlasSprite spriteGrass = null;
    private static TextureAtlasSprite spriteGrassSide = null;
    private static TextureAtlasSprite spriteMycelium = null;
    private static TextureAtlasSprite spritePodzol = null;
    private static TextureAtlasSprite spriteSnow = null;
    private static boolean spritesLoaded = false;
    private static IBakedModel modelCubeGrass = null;
    private static IBakedModel modelCubeMycelium = null;
    private static IBakedModel modelCubePodzol = null;
    private static IBakedModel modelCubeSnow = null;
    private static boolean modelsLoaded = false;
    private static final String TEXTURE_GRASS_DEFAULT = "blocks/grass_top";
    private static final String TEXTURE_GRASS_SIDE_DEFAULT = "blocks/grass_side";
    private static final String TEXTURE_MYCELIUM_DEFAULT = "blocks/mycelium_top";
    private static final String TEXTURE_PODZOL_DEFAULT = "blocks/dirt_podzol_top";
    private static final String TEXTURE_SNOW_DEFAULT = "blocks/snow";

    public static void updateIcons(TextureMap p_updateIcons_0_)
    {
        spritesLoaded = false;
        modelsLoaded = false;
        loadProperties(p_updateIcons_0_);
    }

    public static void update()
    {
        if (spritesLoaded)
        {
            modelCubeGrass = BlockModelUtils.makeModelCube((TextureAtlasSprite)spriteGrass, 0);

            if (grassMultilayer)
            {
                IBakedModel ibakedmodel = BlockModelUtils.makeModelCube((TextureAtlasSprite)spriteGrassSide, -1);
                modelCubeGrass = BlockModelUtils.joinModelsCube(ibakedmodel, modelCubeGrass);
            }

            modelCubeMycelium = BlockModelUtils.makeModelCube((TextureAtlasSprite)spriteMycelium, -1);
            modelCubePodzol = BlockModelUtils.makeModelCube((TextureAtlasSprite)spritePodzol, 0);
            modelCubeSnow = BlockModelUtils.makeModelCube((TextureAtlasSprite)spriteSnow, -1);
            modelsLoaded = true;
        }
    }

    private static void loadProperties(TextureMap p_loadProperties_0_)
    {
        betterGrass = true;
        betterMycelium = true;
        betterPodzol = true;
        betterGrassSnow = true;
        betterMyceliumSnow = true;
        betterPodzolSnow = true;
        spriteGrass = p_loadProperties_0_.registerSprite(new ResourceLocation("blocks/grass_top"));
        spriteGrassSide = p_loadProperties_0_.registerSprite(new ResourceLocation("blocks/grass_side"));
        spriteMycelium = p_loadProperties_0_.registerSprite(new ResourceLocation("blocks/mycelium_top"));
        spritePodzol = p_loadProperties_0_.registerSprite(new ResourceLocation("blocks/dirt_podzol_top"));
        spriteSnow = p_loadProperties_0_.registerSprite(new ResourceLocation("blocks/snow"));
        spritesLoaded = true;
        String s = "optifine/bettergrass.properties";

        try
        {
            ResourceLocation resourcelocation = new ResourceLocation(s);

            if (!Config.hasResource(resourcelocation))
            {
                return;
            }

            InputStream inputstream = Config.getResourceStream(resourcelocation);

            if (inputstream == null)
            {
                return;
            }

            boolean flag = Config.isFromDefaultResourcePack(resourcelocation);

            if (flag)
            {
                Config.dbg("BetterGrass: Parsing default configuration " + s);
            }
            else
            {
                Config.dbg("BetterGrass: Parsing configuration " + s);
            }

            Properties properties = new Properties();
            properties.load(inputstream);
            betterGrass = getBoolean(properties, "grass", true);
            betterMycelium = getBoolean(properties, "mycelium", true);
            betterPodzol = getBoolean(properties, "podzol", true);
            betterGrassSnow = getBoolean(properties, "grass.snow", true);
            betterMyceliumSnow = getBoolean(properties, "mycelium.snow", true);
            betterPodzolSnow = getBoolean(properties, "podzol.snow", true);
            grassMultilayer = getBoolean(properties, "grass.multilayer", false);
            spriteGrass = registerSprite(properties, "texture.grass", "blocks/grass_top", p_loadProperties_0_);
            spriteGrassSide = registerSprite(properties, "texture.grass_side", "blocks/grass_side", p_loadProperties_0_);
            spriteMycelium = registerSprite(properties, "texture.mycelium", "blocks/mycelium_top", p_loadProperties_0_);
            spritePodzol = registerSprite(properties, "texture.podzol", "blocks/dirt_podzol_top", p_loadProperties_0_);
            spriteSnow = registerSprite(properties, "texture.snow", "blocks/snow", p_loadProperties_0_);
        }
        catch (IOException ioexception)
        {
            Config.warn("Error reading: " + s + ", " + ioexception.getClass().getName() + ": " + ioexception.getMessage());
        }
    }

    private static TextureAtlasSprite registerSprite(Properties p_registerSprite_0_, String p_registerSprite_1_, String p_registerSprite_2_, TextureMap p_registerSprite_3_)
    {
        String s = p_registerSprite_0_.getProperty(p_registerSprite_1_);

        if (s == null)
        {
            s = p_registerSprite_2_;
        }

        ResourceLocation resourcelocation = new ResourceLocation("textures/" + s + ".png");

        if (!Config.hasResource(resourcelocation))
        {
            Config.warn("BetterGrass texture not found: " + resourcelocation);
            s = p_registerSprite_2_;
        }

        ResourceLocation resourcelocation1 = new ResourceLocation(s);
        TextureAtlasSprite textureatlassprite = p_registerSprite_3_.registerSprite(resourcelocation1);
        return textureatlassprite;
    }

    public static List getFaceQuads(IBlockAccess p_getFaceQuads_0_, IBlockState p_getFaceQuads_1_, BlockPos p_getFaceQuads_2_, EnumFacing p_getFaceQuads_3_, List p_getFaceQuads_4_)
    {
        if (p_getFaceQuads_3_ != EnumFacing.UP && p_getFaceQuads_3_ != EnumFacing.DOWN)
        {
            if (!modelsLoaded)
            {
                return p_getFaceQuads_4_;
            }
            else
            {
                Block block = p_getFaceQuads_1_.getBlock();
                return block instanceof BlockMycelium ? getFaceQuadsMycelium(p_getFaceQuads_0_, p_getFaceQuads_1_, p_getFaceQuads_2_, p_getFaceQuads_3_, p_getFaceQuads_4_) : (block instanceof BlockDirt ? getFaceQuadsDirt(p_getFaceQuads_0_, p_getFaceQuads_1_, p_getFaceQuads_2_, p_getFaceQuads_3_, p_getFaceQuads_4_) : (block instanceof BlockGrass ? getFaceQuadsGrass(p_getFaceQuads_0_, p_getFaceQuads_1_, p_getFaceQuads_2_, p_getFaceQuads_3_, p_getFaceQuads_4_) : p_getFaceQuads_4_));
            }
        }
        else
        {
            return p_getFaceQuads_4_;
        }
    }

    private static List getFaceQuadsMycelium(IBlockAccess p_getFaceQuadsMycelium_0_, IBlockState p_getFaceQuadsMycelium_1_, BlockPos p_getFaceQuadsMycelium_2_, EnumFacing p_getFaceQuadsMycelium_3_, List p_getFaceQuadsMycelium_4_)
    {
        Block block = p_getFaceQuadsMycelium_0_.getBlockState(p_getFaceQuadsMycelium_2_.up()).getBlock();
        boolean flag = block == Blocks.snow || block == Blocks.snow_layer;

        if (Config.isBetterGrassFancy())
        {
            if (flag)
            {
                if (betterMyceliumSnow && getBlockAt(p_getFaceQuadsMycelium_2_, p_getFaceQuadsMycelium_3_, p_getFaceQuadsMycelium_0_) == Blocks.snow_layer)
                {
                    return getQuads(modelCubeSnow, p_getFaceQuadsMycelium_3_);
                }
            }
            else if (betterMycelium && getBlockAt(p_getFaceQuadsMycelium_2_.down(), p_getFaceQuadsMycelium_3_, p_getFaceQuadsMycelium_0_) == Blocks.mycelium)
            {
                return getQuads(modelCubeMycelium, p_getFaceQuadsMycelium_3_);
            }
        }
        else if (flag)
        {
            if (betterMyceliumSnow)
            {
                return getQuads(modelCubeSnow, p_getFaceQuadsMycelium_3_);
            }
        }
        else if (betterMycelium)
        {
            return getQuads(modelCubeMycelium, p_getFaceQuadsMycelium_3_);
        }

        return p_getFaceQuadsMycelium_4_;
    }

    private static List getFaceQuadsDirt(IBlockAccess p_getFaceQuadsDirt_0_, IBlockState p_getFaceQuadsDirt_1_, BlockPos p_getFaceQuadsDirt_2_, EnumFacing p_getFaceQuadsDirt_3_, List p_getFaceQuadsDirt_4_)
    {
        Block block = getBlockAt(p_getFaceQuadsDirt_2_, EnumFacing.UP, p_getFaceQuadsDirt_0_);

        if (p_getFaceQuadsDirt_1_.getValue(BlockDirt.VARIANT) != BlockDirt.DirtType.PODZOL)
        {
            return p_getFaceQuadsDirt_4_;
        }
        else
        {
            boolean flag = block == Blocks.snow || block == Blocks.snow_layer;

            if (Config.isBetterGrassFancy())
            {
                if (flag)
                {
                    if (betterPodzolSnow && getBlockAt(p_getFaceQuadsDirt_2_, p_getFaceQuadsDirt_3_, p_getFaceQuadsDirt_0_) == Blocks.snow_layer)
                    {
                        return getQuads(modelCubeSnow, p_getFaceQuadsDirt_3_);
                    }
                }
                else if (betterPodzol)
                {
                    BlockPos blockpos = p_getFaceQuadsDirt_2_.down().offset(p_getFaceQuadsDirt_3_);
                    IBlockState iblockstate = p_getFaceQuadsDirt_0_.getBlockState(blockpos);

                    if (iblockstate.getBlock() == Blocks.dirt && iblockstate.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL)
                    {
                        return getQuads(modelCubePodzol, p_getFaceQuadsDirt_3_);
                    }
                }
            }
            else if (flag)
            {
                if (betterPodzolSnow)
                {
                    return getQuads(modelCubeSnow, p_getFaceQuadsDirt_3_);
                }
            }
            else if (betterPodzol)
            {
                return getQuads(modelCubePodzol, p_getFaceQuadsDirt_3_);
            }

            return p_getFaceQuadsDirt_4_;
        }
    }

    private static List getFaceQuadsGrass(IBlockAccess p_getFaceQuadsGrass_0_, IBlockState p_getFaceQuadsGrass_1_, BlockPos p_getFaceQuadsGrass_2_, EnumFacing p_getFaceQuadsGrass_3_, List p_getFaceQuadsGrass_4_)
    {
        Block block = p_getFaceQuadsGrass_0_.getBlockState(p_getFaceQuadsGrass_2_.up()).getBlock();
        boolean flag = block == Blocks.snow || block == Blocks.snow_layer;

        if (Config.isBetterGrassFancy())
        {
            if (flag)
            {
                if (betterGrassSnow && getBlockAt(p_getFaceQuadsGrass_2_, p_getFaceQuadsGrass_3_, p_getFaceQuadsGrass_0_) == Blocks.snow_layer)
                {
                    return getQuads(modelCubeSnow, p_getFaceQuadsGrass_3_);
                }
            }
            else if (betterGrass && getBlockAt(p_getFaceQuadsGrass_2_.down(), p_getFaceQuadsGrass_3_, p_getFaceQuadsGrass_0_) == Blocks.grass)
            {
                return getQuads(modelCubeGrass, p_getFaceQuadsGrass_3_);
            }
        }
        else if (flag)
        {
            if (betterGrassSnow)
            {
                return getQuads(modelCubeSnow, p_getFaceQuadsGrass_3_);
            }
        }
        else if (betterGrass)
        {
            return getQuads(modelCubeGrass, p_getFaceQuadsGrass_3_);
        }

        return p_getFaceQuadsGrass_4_;
    }

    private static List getQuads(IBakedModel p_getQuads_0_, EnumFacing p_getQuads_1_)
    {
        return p_getQuads_1_ == null ? p_getQuads_0_.getGeneralQuads() : p_getQuads_0_.getFaceQuads(p_getQuads_1_);
    }

    private static Block getBlockAt(BlockPos p_getBlockAt_0_, EnumFacing p_getBlockAt_1_, IBlockAccess p_getBlockAt_2_)
    {
        BlockPos blockpos = p_getBlockAt_0_.offset(p_getBlockAt_1_);
        Block block = p_getBlockAt_2_.getBlockState(blockpos).getBlock();
        return block;
    }

    private static boolean getBoolean(Properties p_getBoolean_0_, String p_getBoolean_1_, boolean p_getBoolean_2_)
    {
        String s = p_getBoolean_0_.getProperty(p_getBoolean_1_);
        return s == null ? p_getBoolean_2_ : Boolean.parseBoolean(s);
    }
}
