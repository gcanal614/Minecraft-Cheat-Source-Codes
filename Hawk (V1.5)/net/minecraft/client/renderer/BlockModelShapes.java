package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class BlockModelShapes {
   private final ModelManager modelManager;
   private static final String __OBFID = "CL_00002529";
   private final Map field_178129_a = Maps.newIdentityHashMap();
   private final BlockStateMapper blockStateMapper = new BlockStateMapper();

   public void func_178123_a(Block... var1) {
      this.blockStateMapper.registerBuiltInBlocks(var1);
   }

   public BlockStateMapper getBlockStateMapper() {
      return this.blockStateMapper;
   }

   public TextureAtlasSprite func_178122_a(IBlockState var1) {
      Block var2 = var1.getBlock();
      IBakedModel var3 = this.func_178125_b(var1);
      if (var3 == null || var3 == this.modelManager.getMissingModel()) {
         label63: {
            if (var2 != Blocks.wall_sign && var2 != Blocks.standing_sign && var2 != Blocks.chest && var2 != Blocks.trapped_chest && var2 != Blocks.standing_banner && var2 != Blocks.wall_banner) {
               if (var2 == Blocks.ender_chest) {
                  return this.modelManager.func_174952_b().getAtlasSprite("minecraft:blocks/obsidian");
               }

               if (var2 != Blocks.flowing_lava && var2 != Blocks.lava) {
                  if (var2 != Blocks.flowing_water && var2 != Blocks.water) {
                     if (var2 == Blocks.skull) {
                        return this.modelManager.func_174952_b().getAtlasSprite("minecraft:blocks/soul_sand");
                     }

                     if (var2 == Blocks.barrier) {
                        return this.modelManager.func_174952_b().getAtlasSprite("minecraft:items/barrier");
                     }
                     break label63;
                  }

                  return this.modelManager.func_174952_b().getAtlasSprite("minecraft:blocks/water_still");
               }

               return this.modelManager.func_174952_b().getAtlasSprite("minecraft:blocks/lava_still");
            }

            return this.modelManager.func_174952_b().getAtlasSprite("minecraft:blocks/planks_oak");
         }
      }

      if (var3 == null) {
         var3 = this.modelManager.getMissingModel();
      }

      return var3.getTexture();
   }

   private void func_178119_d() {
      this.func_178123_a(Blocks.air, Blocks.flowing_water, Blocks.water, Blocks.flowing_lava, Blocks.lava, Blocks.piston_extension, Blocks.chest, Blocks.ender_chest, Blocks.trapped_chest, Blocks.standing_sign, Blocks.skull, Blocks.end_portal, Blocks.barrier, Blocks.wall_sign, Blocks.wall_banner, Blocks.standing_banner);
      this.func_178121_a(Blocks.stone, (new StateMap.Builder()).func_178440_a(BlockStone.VARIANT_PROP).build());
      this.func_178121_a(Blocks.prismarine, (new StateMap.Builder()).func_178440_a(BlockPrismarine.VARIANTS).build());
      this.func_178121_a(Blocks.leaves, (new StateMap.Builder()).func_178440_a(BlockOldLeaf.VARIANT_PROP).func_178439_a("_leaves").func_178442_a(BlockLeaves.field_176236_b, BlockLeaves.field_176237_a).build());
      this.func_178121_a(Blocks.leaves2, (new StateMap.Builder()).func_178440_a(BlockNewLeaf.field_176240_P).func_178439_a("_leaves").func_178442_a(BlockLeaves.field_176236_b, BlockLeaves.field_176237_a).build());
      this.func_178121_a(Blocks.cactus, (new StateMap.Builder()).func_178442_a(BlockCactus.AGE_PROP).build());
      this.func_178121_a(Blocks.reeds, (new StateMap.Builder()).func_178442_a(BlockReed.field_176355_a).build());
      this.func_178121_a(Blocks.jukebox, (new StateMap.Builder()).func_178442_a(BlockJukebox.HAS_RECORD).build());
      this.func_178121_a(Blocks.command_block, (new StateMap.Builder()).func_178442_a(BlockCommandBlock.TRIGGERED_PROP).build());
      this.func_178121_a(Blocks.cobblestone_wall, (new StateMap.Builder()).func_178440_a(BlockWall.field_176255_P).func_178439_a("_wall").build());
      this.func_178121_a(Blocks.double_plant, (new StateMap.Builder()).func_178440_a(BlockDoublePlant.VARIANT_PROP).build());
      this.func_178121_a(Blocks.oak_fence_gate, (new StateMap.Builder()).func_178442_a(BlockFenceGate.field_176465_b).build());
      this.func_178121_a(Blocks.spruce_fence_gate, (new StateMap.Builder()).func_178442_a(BlockFenceGate.field_176465_b).build());
      this.func_178121_a(Blocks.birch_fence_gate, (new StateMap.Builder()).func_178442_a(BlockFenceGate.field_176465_b).build());
      this.func_178121_a(Blocks.jungle_fence_gate, (new StateMap.Builder()).func_178442_a(BlockFenceGate.field_176465_b).build());
      this.func_178121_a(Blocks.dark_oak_fence_gate, (new StateMap.Builder()).func_178442_a(BlockFenceGate.field_176465_b).build());
      this.func_178121_a(Blocks.acacia_fence_gate, (new StateMap.Builder()).func_178442_a(BlockFenceGate.field_176465_b).build());
      this.func_178121_a(Blocks.tripwire, (new StateMap.Builder()).func_178442_a(BlockTripWire.field_176295_N, BlockTripWire.field_176293_a).build());
      this.func_178121_a(Blocks.double_wooden_slab, (new StateMap.Builder()).func_178440_a(BlockPlanks.VARIANT_PROP).func_178439_a("_double_slab").build());
      this.func_178121_a(Blocks.wooden_slab, (new StateMap.Builder()).func_178440_a(BlockPlanks.VARIANT_PROP).func_178439_a("_slab").build());
      this.func_178121_a(Blocks.tnt, (new StateMap.Builder()).func_178442_a(BlockTNT.field_176246_a).build());
      this.func_178121_a(Blocks.fire, (new StateMap.Builder()).func_178442_a(BlockFire.field_176543_a).build());
      this.func_178121_a(Blocks.redstone_wire, (new StateMap.Builder()).func_178442_a(BlockRedstoneWire.POWER).build());
      this.func_178121_a(Blocks.oak_door, (new StateMap.Builder()).func_178442_a(BlockDoor.POWERED_PROP).build());
      this.func_178121_a(Blocks.spruce_door, (new StateMap.Builder()).func_178442_a(BlockDoor.POWERED_PROP).build());
      this.func_178121_a(Blocks.birch_door, (new StateMap.Builder()).func_178442_a(BlockDoor.POWERED_PROP).build());
      this.func_178121_a(Blocks.jungle_door, (new StateMap.Builder()).func_178442_a(BlockDoor.POWERED_PROP).build());
      this.func_178121_a(Blocks.acacia_door, (new StateMap.Builder()).func_178442_a(BlockDoor.POWERED_PROP).build());
      this.func_178121_a(Blocks.dark_oak_door, (new StateMap.Builder()).func_178442_a(BlockDoor.POWERED_PROP).build());
      this.func_178121_a(Blocks.iron_door, (new StateMap.Builder()).func_178442_a(BlockDoor.POWERED_PROP).build());
      this.func_178121_a(Blocks.wool, (new StateMap.Builder()).func_178440_a(BlockColored.COLOR).func_178439_a("_wool").build());
      this.func_178121_a(Blocks.carpet, (new StateMap.Builder()).func_178440_a(BlockColored.COLOR).func_178439_a("_carpet").build());
      this.func_178121_a(Blocks.stained_hardened_clay, (new StateMap.Builder()).func_178440_a(BlockColored.COLOR).func_178439_a("_stained_hardened_clay").build());
      this.func_178121_a(Blocks.stained_glass_pane, (new StateMap.Builder()).func_178440_a(BlockColored.COLOR).func_178439_a("_stained_glass_pane").build());
      this.func_178121_a(Blocks.stained_glass, (new StateMap.Builder()).func_178440_a(BlockColored.COLOR).func_178439_a("_stained_glass").build());
      this.func_178121_a(Blocks.sandstone, (new StateMap.Builder()).func_178440_a(BlockSandStone.field_176297_a).build());
      this.func_178121_a(Blocks.red_sandstone, (new StateMap.Builder()).func_178440_a(BlockRedSandstone.TYPE).build());
      this.func_178121_a(Blocks.tallgrass, (new StateMap.Builder()).func_178440_a(BlockTallGrass.field_176497_a).build());
      this.func_178121_a(Blocks.bed, (new StateMap.Builder()).func_178442_a(BlockBed.OCCUPIED_PROP).build());
      this.func_178121_a(Blocks.yellow_flower, (new StateMap.Builder()).func_178440_a(Blocks.yellow_flower.func_176494_l()).build());
      this.func_178121_a(Blocks.red_flower, (new StateMap.Builder()).func_178440_a(Blocks.red_flower.func_176494_l()).build());
      this.func_178121_a(Blocks.stone_slab, (new StateMap.Builder()).func_178440_a(BlockStoneSlab.field_176556_M).func_178439_a("_slab").build());
      this.func_178121_a(Blocks.stone_slab2, (new StateMap.Builder()).func_178440_a(BlockStoneSlabNew.field_176559_M).func_178439_a("_slab").build());
      this.func_178121_a(Blocks.monster_egg, (new StateMap.Builder()).func_178440_a(BlockSilverfish.VARIANT_PROP).func_178439_a("_monster_egg").build());
      this.func_178121_a(Blocks.stonebrick, (new StateMap.Builder()).func_178440_a(BlockStoneBrick.VARIANT_PROP).build());
      this.func_178121_a(Blocks.dispenser, (new StateMap.Builder()).func_178442_a(BlockDispenser.TRIGGERED).build());
      this.func_178121_a(Blocks.dropper, (new StateMap.Builder()).func_178442_a(BlockDropper.TRIGGERED).build());
      this.func_178121_a(Blocks.log, (new StateMap.Builder()).func_178440_a(BlockOldLog.VARIANT_PROP).func_178439_a("_log").build());
      this.func_178121_a(Blocks.log2, (new StateMap.Builder()).func_178440_a(BlockNewLog.field_176300_b).func_178439_a("_log").build());
      this.func_178121_a(Blocks.planks, (new StateMap.Builder()).func_178440_a(BlockPlanks.VARIANT_PROP).func_178439_a("_planks").build());
      this.func_178121_a(Blocks.sapling, (new StateMap.Builder()).func_178440_a(BlockSapling.TYPE_PROP).func_178439_a("_sapling").build());
      this.func_178121_a(Blocks.sand, (new StateMap.Builder()).func_178440_a(BlockSand.VARIANT_PROP).build());
      this.func_178121_a(Blocks.hopper, (new StateMap.Builder()).func_178442_a(BlockHopper.field_176429_b).build());
      this.func_178121_a(Blocks.flower_pot, (new StateMap.Builder()).func_178442_a(BlockFlowerPot.field_176444_a).build());
      this.func_178121_a(Blocks.quartz_block, new StateMapperBase(this) {
         final BlockModelShapes this$0;
         private static final String __OBFID = "CL_00002528";

         protected ModelResourceLocation func_178132_a(IBlockState var1) {
            BlockQuartz.EnumType var2 = (BlockQuartz.EnumType)var1.getValue(BlockQuartz.VARIANT_PROP);
            switch(var2) {
            case DEFAULT:
            default:
               return new ModelResourceLocation("quartz_block", "normal");
            case CHISELED:
               return new ModelResourceLocation("chiseled_quartz_block", "normal");
            case LINES_Y:
               return new ModelResourceLocation("quartz_column", "axis=y");
            case LINES_X:
               return new ModelResourceLocation("quartz_column", "axis=x");
            case LINES_Z:
               return new ModelResourceLocation("quartz_column", "axis=z");
            }
         }

         {
            this.this$0 = var1;
         }
      });
      this.func_178121_a(Blocks.deadbush, new StateMapperBase(this) {
         private static final String __OBFID = "CL_00002527";
         final BlockModelShapes this$0;

         protected ModelResourceLocation func_178132_a(IBlockState var1) {
            return new ModelResourceLocation("dead_bush", "normal");
         }

         {
            this.this$0 = var1;
         }
      });
      this.func_178121_a(Blocks.pumpkin_stem, new StateMapperBase(this) {
         private static final String __OBFID = "CL_00002526";
         final BlockModelShapes this$0;

         {
            this.this$0 = var1;
         }

         protected ModelResourceLocation func_178132_a(IBlockState var1) {
            LinkedHashMap var2 = Maps.newLinkedHashMap(var1.getProperties());
            if (var1.getValue(BlockStem.FACING_PROP) != EnumFacing.UP) {
               var2.remove(BlockStem.AGE_PROP);
            }

            return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(var1.getBlock()), this.func_178131_a(var2));
         }
      });
      this.func_178121_a(Blocks.melon_stem, new StateMapperBase(this) {
         final BlockModelShapes this$0;
         private static final String __OBFID = "CL_00002525";

         protected ModelResourceLocation func_178132_a(IBlockState var1) {
            LinkedHashMap var2 = Maps.newLinkedHashMap(var1.getProperties());
            if (var1.getValue(BlockStem.FACING_PROP) != EnumFacing.UP) {
               var2.remove(BlockStem.AGE_PROP);
            }

            return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(var1.getBlock()), this.func_178131_a(var2));
         }

         {
            this.this$0 = var1;
         }
      });
      this.func_178121_a(Blocks.dirt, new StateMapperBase(this) {
         private static final String __OBFID = "CL_00002524";
         final BlockModelShapes this$0;

         protected ModelResourceLocation func_178132_a(IBlockState var1) {
            LinkedHashMap var2 = Maps.newLinkedHashMap(var1.getProperties());
            String var3 = BlockDirt.VARIANT.getName((Comparable)var2.remove(BlockDirt.VARIANT));
            if (BlockDirt.DirtType.PODZOL != var1.getValue(BlockDirt.VARIANT)) {
               var2.remove(BlockDirt.SNOWY);
            }

            return new ModelResourceLocation(var3, this.func_178131_a(var2));
         }

         {
            this.this$0 = var1;
         }
      });
      this.func_178121_a(Blocks.double_stone_slab, new StateMapperBase(this) {
         final BlockModelShapes this$0;
         private static final String __OBFID = "CL_00002523";

         protected ModelResourceLocation func_178132_a(IBlockState var1) {
            LinkedHashMap var2 = Maps.newLinkedHashMap(var1.getProperties());
            String var3 = BlockStoneSlab.field_176556_M.getName((Comparable)var2.remove(BlockStoneSlab.field_176556_M));
            var2.remove(BlockStoneSlab.field_176555_b);
            String var4 = (Boolean)var1.getValue(BlockStoneSlab.field_176555_b) ? "all" : "normal";
            return new ModelResourceLocation(String.valueOf((new StringBuilder(String.valueOf(var3))).append("_double_slab")), var4);
         }

         {
            this.this$0 = var1;
         }
      });
      this.func_178121_a(Blocks.double_stone_slab2, new StateMapperBase(this) {
         private static final String __OBFID = "CL_00002522";
         final BlockModelShapes this$0;

         {
            this.this$0 = var1;
         }

         protected ModelResourceLocation func_178132_a(IBlockState var1) {
            LinkedHashMap var2 = Maps.newLinkedHashMap(var1.getProperties());
            String var3 = BlockStoneSlabNew.field_176559_M.getName((Comparable)var2.remove(BlockStoneSlabNew.field_176559_M));
            var2.remove(BlockStoneSlab.field_176555_b);
            String var4 = (Boolean)var1.getValue(BlockStoneSlabNew.field_176558_b) ? "all" : "normal";
            return new ModelResourceLocation(String.valueOf((new StringBuilder(String.valueOf(var3))).append("_double_slab")), var4);
         }
      });
   }

   public ModelManager func_178126_b() {
      return this.modelManager;
   }

   public IBakedModel func_178125_b(IBlockState var1) {
      IBakedModel var2 = (IBakedModel)this.field_178129_a.get(var1);
      if (var2 == null) {
         var2 = this.modelManager.getMissingModel();
      }

      return var2;
   }

   public void func_178121_a(Block var1, IStateMapper var2) {
      this.blockStateMapper.func_178447_a(var1, var2);
   }

   public void func_178124_c() {
      this.field_178129_a.clear();
      Iterator var1 = this.blockStateMapper.func_178446_a().entrySet().iterator();

      while(var1.hasNext()) {
         Entry var2 = (Entry)var1.next();
         this.field_178129_a.put(var2.getKey(), this.modelManager.getModel((ModelResourceLocation)var2.getValue()));
      }

   }

   public BlockModelShapes(ModelManager var1) {
      this.modelManager = var1;
      this.func_178119_d();
   }

   static final class SwitchEnumType {
      private static final String __OBFID = "CL_00002521";
      static final int[] field_178257_a = new int[BlockQuartz.EnumType.values().length];

      static {
         try {
            field_178257_a[BlockQuartz.EnumType.DEFAULT.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_178257_a[BlockQuartz.EnumType.CHISELED.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_178257_a[BlockQuartz.EnumType.LINES_Y.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_178257_a[BlockQuartz.EnumType.LINES_X.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_178257_a[BlockQuartz.EnumType.LINES_Z.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
