package net.minecraft.creativetab;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class CreativeTabs {
   public static final CreativeTabs[] creativeTabArray = new CreativeTabs[12];
   public static final CreativeTabs tabInventory;
   private boolean drawTitle = true;
   public static final CreativeTabs tabTools;
   private boolean hasScrollbar = true;
   private final String tabLabel;
   private static final String __OBFID = "CL_00000005";
   public static final CreativeTabs tabMaterials;
   public static final CreativeTabs tabBlock = new CreativeTabs(0, "buildingBlocks") {
      private static final String __OBFID = "CL_00000006";

      public Item getTabIconItem() {
         return Item.getItemFromBlock(Blocks.brick_block);
      }
   };
   public static final CreativeTabs tabCombat;
   public static final CreativeTabs tabFood;
   public static final CreativeTabs tabMisc;
   private String theTexture = "items.png";
   private ItemStack iconItemStack;
   public static final CreativeTabs tabTransport = new CreativeTabs(3, "transportation") {
      private static final String __OBFID = "CL_00000012";

      public Item getTabIconItem() {
         return Item.getItemFromBlock(Blocks.golden_rail);
      }
   };
   private final int tabIndex;
   public static final CreativeTabs tabRedstone = new CreativeTabs(2, "redstone") {
      private static final String __OBFID = "CL_00000011";

      public Item getTabIconItem() {
         return Items.redstone;
      }
   };
   public static final CreativeTabs tabAllSearch;
   private EnumEnchantmentType[] enchantmentTypes;
   public static final CreativeTabs tabBrewing;
   public static final CreativeTabs tabDecorations = new CreativeTabs(1, "decorations") {
      private static final String __OBFID = "CL_00000010";

      public int getIconItemDamage() {
         return BlockDoublePlant.EnumPlantType.PAEONIA.func_176936_a();
      }

      public Item getTabIconItem() {
         return Item.getItemFromBlock(Blocks.double_plant);
      }
   };

   public boolean drawInForegroundOfTab() {
      return this.drawTitle;
   }

   public int getIconItemDamage() {
      return 0;
   }

   public boolean hasRelevantEnchantmentType(EnumEnchantmentType var1) {
      if (this.enchantmentTypes == null) {
         return false;
      } else {
         EnumEnchantmentType[] var2 = this.enchantmentTypes;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            EnumEnchantmentType var5 = var2[var4];
            if (var5 == var1) {
               return true;
            }
         }

         return false;
      }
   }

   public EnumEnchantmentType[] getRelevantEnchantmentTypes() {
      return this.enchantmentTypes;
   }

   public CreativeTabs setRelevantEnchantmentTypes(EnumEnchantmentType... var1) {
      this.enchantmentTypes = var1;
      return this;
   }

   public boolean shouldHidePlayerInventory() {
      return this.hasScrollbar;
   }

   public int getTabColumn() {
      return this.tabIndex % 6;
   }

   public CreativeTabs(int var1, String var2) {
      this.tabIndex = var1;
      this.tabLabel = var2;
      creativeTabArray[var1] = this;
   }

   public ItemStack getIconItemStack() {
      if (this.iconItemStack == null) {
         this.iconItemStack = new ItemStack(this.getTabIconItem(), 1, this.getIconItemDamage());
      }

      return this.iconItemStack;
   }

   public void displayAllReleventItems(List var1) {
      Iterator var2 = Item.itemRegistry.iterator();

      while(var2.hasNext()) {
         Item var3 = (Item)var2.next();
         if (var3 != null && var3.getCreativeTab() == this) {
            var3.getSubItems(var3, this, var1);
         }
      }

      if (this.getRelevantEnchantmentTypes() != null) {
         this.addEnchantmentBooksToList(var1, this.getRelevantEnchantmentTypes());
      }

   }

   public CreativeTabs setNoScrollbar() {
      this.hasScrollbar = false;
      return this;
   }

   public String getTranslatedTabLabel() {
      return String.valueOf((new StringBuilder("itemGroup.")).append(this.getTabLabel()));
   }

   public boolean isTabInFirstRow() {
      return this.tabIndex < 6;
   }

   public void addEnchantmentBooksToList(List var1, EnumEnchantmentType... var2) {
      Enchantment[] var3 = Enchantment.enchantmentsList;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Enchantment var6 = var3[var5];
         if (var6 != null && var6.type != null) {
            boolean var7 = false;

            for(int var8 = 0; var8 < var2.length && !var7; ++var8) {
               if (var6.type == var2[var8]) {
                  var7 = true;
               }
            }

            if (var7) {
               var1.add(Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(var6, var6.getMaxLevel())));
            }
         }
      }

   }

   public CreativeTabs setBackgroundImageName(String var1) {
      this.theTexture = var1;
      return this;
   }

   public String getTabLabel() {
      return this.tabLabel;
   }

   static {
      tabMisc = (new CreativeTabs(4, "misc") {
         private static final String __OBFID = "CL_00000014";

         public Item getTabIconItem() {
            return Items.lava_bucket;
         }
      }).setRelevantEnchantmentTypes(new EnumEnchantmentType[]{EnumEnchantmentType.ALL});
      tabAllSearch = (new CreativeTabs(5, "search") {
         private static final String __OBFID = "CL_00000015";

         public Item getTabIconItem() {
            return Items.compass;
         }
      }).setBackgroundImageName("item_search.png");
      tabFood = new CreativeTabs(6, "food") {
         private static final String __OBFID = "CL_00000016";

         public Item getTabIconItem() {
            return Items.apple;
         }
      };
      tabTools = (new CreativeTabs(7, "tools") {
         private static final String __OBFID = "CL_00000017";

         public Item getTabIconItem() {
            return Items.iron_axe;
         }
      }).setRelevantEnchantmentTypes(new EnumEnchantmentType[]{EnumEnchantmentType.DIGGER, EnumEnchantmentType.FISHING_ROD, EnumEnchantmentType.BREAKABLE});
      tabCombat = (new CreativeTabs(8, "combat") {
         private static final String __OBFID = "CL_00000018";

         public Item getTabIconItem() {
            return Items.golden_sword;
         }
      }).setRelevantEnchantmentTypes(new EnumEnchantmentType[]{EnumEnchantmentType.ARMOR, EnumEnchantmentType.ARMOR_FEET, EnumEnchantmentType.ARMOR_HEAD, EnumEnchantmentType.ARMOR_LEGS, EnumEnchantmentType.ARMOR_TORSO, EnumEnchantmentType.BOW, EnumEnchantmentType.WEAPON});
      tabBrewing = new CreativeTabs(9, "brewing") {
         private static final String __OBFID = "CL_00000007";

         public Item getTabIconItem() {
            return Items.potionitem;
         }
      };
      tabMaterials = new CreativeTabs(10, "materials") {
         private static final String __OBFID = "CL_00000008";

         public Item getTabIconItem() {
            return Items.stick;
         }
      };
      tabInventory = (new CreativeTabs(11, "inventory") {
         private static final String __OBFID = "CL_00000009";

         public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.chest);
         }
      }).setBackgroundImageName("inventory.png").setNoScrollbar().setNoTitle();
   }

   public CreativeTabs setNoTitle() {
      this.drawTitle = false;
      return this;
   }

   public abstract Item getTabIconItem();

   public int getTabIndex() {
      return this.tabIndex;
   }

   public String getBackgroundImageName() {
      return this.theTexture;
   }
}
