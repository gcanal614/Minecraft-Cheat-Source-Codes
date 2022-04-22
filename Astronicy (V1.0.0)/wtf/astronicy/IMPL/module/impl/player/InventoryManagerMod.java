package wtf.astronicy.IMPL.module.impl.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.player.MotionUpdateEvent;
import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.module.registery.Aliases;
import wtf.astronicy.IMPL.module.registery.Bind;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import wtf.astronicy.IMPL.module.options.Option;
import wtf.astronicy.IMPL.module.options.impl.BoolOption;
import wtf.astronicy.IMPL.module.options.impl.DoubleOption;
import wtf.astronicy.IMPL.utils.InventoryUtils;
import wtf.astronicy.IMPL.utils.TimerUtility;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.DamageSource;

@Bind("Z")
@ModName("Inventory Manager")
@Category(ModuleCategory.PLAYER)
@Aliases({"inventorymanager", "invmanager", "manager"})
public final class InventoryManagerMod extends Module {
   public static final TimerUtility INV_STOPWATCH = new TimerUtility();
   private List allSwords = new ArrayList();
   private List[] allArmors = new List[4];
   private List trash = new ArrayList();
   private boolean cleaning;
   private int[] bestArmorSlot;
   private int bestSwordSlot;
   private final BoolOption invOnly = new BoolOption("Only Inventory", true);
   private final DoubleOption swordSlot = new DoubleOption("Sword Slot", 1.0D, 1.0D, 9.0D, 1.0D);
   private final DoubleOption delay = new DoubleOption("Delay", 250.0D, 0.0D, 1000.0D, 1.0D);

   public InventoryManagerMod() {
      this.addOptions(new Option[]{this.swordSlot, this.delay, this.invOnly});
   }

   @Listener(MotionUpdateEvent.class)
   public void onEvent(MotionUpdateEvent event) {
      if(invOnly.getValue()){
         if(!(mc.currentScreen instanceof GuiInventory)) return;
      }
      if (event.isPre()) {
         this.collectItems();
         this.collectBestArmor();
         this.collectTrash();
         int trashSize = this.trash.size();
         boolean trashPresent = trashSize > 0;
         EntityPlayerSP player = mc.thePlayer;
         int windowId = player.openContainer.windowId;
         int bestSwordSlot = this.bestSwordSlot;
         if (trashPresent) {
            if (!this.cleaning) {
               this.cleaning = true;
               player.sendQueue.addToSendQueueSilent(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
            }

            for(int i = 0; i < trashSize; ++i) {
               int slot = (Integer)this.trash.get(i);
               if (this.checkDelay()) {
                  break;
               }

               mc.playerController.windowClick(windowId, slot < 9 ? slot + 36 : slot, 1, 4, player);
               INV_STOPWATCH.reset();
            }

            if (this.cleaning) {
               player.sendQueue.addToSendQueueSilent(new C0DPacketCloseWindow(windowId));
               this.cleaning = false;
            }
         }

         if (bestSwordSlot != -1 && !this.checkDelay()) {
            mc.playerController.windowClick(windowId, bestSwordSlot < 9 ? bestSwordSlot + 36 : bestSwordSlot, ((Double)this.swordSlot.getValue()).intValue() - 1, 2, player);
            INV_STOPWATCH.reset();
         }
      }

   }

   private boolean checkDelay() {
      return !INV_STOPWATCH.elapsed(((Double)this.delay.getValue()).longValue());
   }

   public void collectItems() {
      this.bestSwordSlot = -1;
      this.allSwords.clear();
      float bestSwordDamage = -1.0F;

      for(int i = 0; i < 36; ++i) {
         ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
         if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemSword) {
            float damageLevel = InventoryUtils.getDamageLevel(itemStack);
            this.allSwords.add(i);
            if (bestSwordDamage < damageLevel) {
               bestSwordDamage = damageLevel;
               this.bestSwordSlot = i;
            }
         }
      }

   }

   private void collectBestArmor() {
      int[] bestArmorDamageReducement = new int[4];
      this.bestArmorSlot = new int[4];
      Arrays.fill(bestArmorDamageReducement, -1);
      Arrays.fill(this.bestArmorSlot, -1);

      int i;
      ItemStack itemStack;
      ItemArmor armor;
      int armorType;
      for(i = 0; i < this.bestArmorSlot.length; ++i) {
         itemStack = mc.thePlayer.inventory.armorItemInSlot(i);
         this.allArmors[i] = new ArrayList();
         if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemArmor) {
            armor = (ItemArmor)itemStack.getItem();
            armorType = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{itemStack}, DamageSource.generic);
            bestArmorDamageReducement[i] = armorType;
         }
      }

      for(i = 0; i < 36; ++i) {
         itemStack = mc.thePlayer.inventory.getStackInSlot(i);
         if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemArmor) {
            armor = (ItemArmor)itemStack.getItem();
            armorType = 3 - armor.armorType;
            this.allArmors[armorType].add(i);
            int slotProtectionLevel = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{itemStack}, DamageSource.generic);
            if (bestArmorDamageReducement[armorType] < slotProtectionLevel) {
               bestArmorDamageReducement[armorType] = slotProtectionLevel;
               this.bestArmorSlot[armorType] = i;
            }
         }
      }

   }

   private void collectTrash() {
      this.trash.clear();

      int i;
      for(i = 0; i < 36; ++i) {
         ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
         if (itemStack != null && itemStack.getItem() != null && !InventoryUtils.isValidItem(itemStack)) {
            this.trash.add(i);
         }
      }

      for(i = 0; i < this.allArmors.length; ++i) {
         List armorItem = this.allArmors[i];
         if (armorItem != null) {
            List integers = this.trash;
            int i1 = 0;

            for(int armorItemSize = armorItem.size(); i1 < armorItemSize; ++i1) {
               Integer slot = (Integer)armorItem.get(i1);
               if (slot != this.bestArmorSlot[i]) {
                  integers.add(slot);
               }
            }
         }
      }

      List integers = this.trash;

      for(int allSwordsSize = this.allSwords.size(); i < allSwordsSize; ++i) {
         Integer slot = (Integer)this.allSwords.get(i);
         if (slot != this.bestSwordSlot) {
            integers.add(slot);
         }
      }

   }
}
