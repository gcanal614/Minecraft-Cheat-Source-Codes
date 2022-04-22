package wtf.astronicy.IMPL.module.impl.player;

import java.util.HashSet;
import java.util.Set;

import wtf.astronicy.Astronicy;
import wtf.astronicy.API.events.api.basicbus.api.annotations.Listener;
import wtf.astronicy.API.events.packet.ReceivePacketEvent;
import wtf.astronicy.API.events.player.MotionUpdateEvent;
import wtf.astronicy.IMPL.module.ModuleCategory;
import wtf.astronicy.IMPL.module.impl.Module;
import wtf.astronicy.IMPL.module.impl.combat.AuraMod;
import wtf.astronicy.IMPL.module.registery.Aliases;
import wtf.astronicy.IMPL.module.registery.Bind;
import wtf.astronicy.IMPL.module.registery.Category;
import wtf.astronicy.IMPL.module.registery.ModName;
import wtf.astronicy.IMPL.module.options.Option;
import wtf.astronicy.IMPL.module.options.impl.BoolOption;
import wtf.astronicy.IMPL.module.options.impl.DoubleOption;
import wtf.astronicy.IMPL.utils.TimerUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

@Bind("Z")
@ModName("Chest Stealer")
@Category(ModuleCategory.WORLD)
@Aliases({"stealer", "cheststealer"})
public class ChestStealer extends Module {
   private static final int REMOVE_SIZE = 128;
   public final BoolOption autoSteal = new BoolOption("Auto Steal", true);
   public final DoubleOption delay = new DoubleOption("Delay", 150.0D, 0.0D, 500.0D, 5.0D);
   private final Set openedChests = new HashSet();
   private final TimerUtility openStopwatch = new TimerUtility();
   private final TimerUtility itemStealStopwatch = new TimerUtility();
   private AuraMod aura;

   public ChestStealer() {
      this.addOptions(new Option[]{this.autoSteal, this.delay});
   }

   public void onEnabled() {
      if (this.aura == null) {
         this.aura = (AuraMod) Astronicy.MANAGER_REGISTRY.moduleManager.getModuleOrNull(AuraMod.class);
      }

      this.openStopwatch.reset();
      this.itemStealStopwatch.reset();
   }

   @Listener(MotionUpdateEvent.class)
   public final void onMotionUpdate(MotionUpdateEvent event) {
      boolean auraSafe = true;
      if (auraSafe) {
         Minecraft minecraft = mc;
         EntityPlayerSP player = minecraft.thePlayer;
         WorldClient world = minecraft.theWorld;
         PlayerControllerMP controller = minecraft.playerController;
         boolean pre = event.isPre();
         int index;
         if (pre && minecraft.currentScreen instanceof GuiChest && this.autoSteal.getValue()) {
            GuiChest chest = (GuiChest)minecraft.currentScreen;
            if (this.isValidChest(chest)) {
               if (this.isChestEmpty(chest) || this.isInventoryFull()) {
                  player.closeScreen();
                  this.itemStealStopwatch.reset();
                  this.openStopwatch.reset();
                  return;
               }

               for(index = 0; index < chest.getLowerChestInventory().getSizeInventory(); ++index) {
                  ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
                  if (stack != null && this.isValidItem(stack) && this.itemStealStopwatch.elapsed(((Double)this.delay.getValue()).longValue())) {
                     controller.windowClick(chest.inventorySlots.windowId, index, 0, 1, player);
                     this.itemStealStopwatch.reset();
                  }
               }
            }
         }
      }

   }

   @Listener(ReceivePacketEvent.class)
   public final void onReceivePacket(ReceivePacketEvent event) {
      if (event.getPacket() instanceof S18PacketEntityTeleport) {
      }

   }

   private void andAndEnsureSetSize(Set set, TileEntity chest) {
      if (set.size() > 128) {
         set.clear();
      }

      set.add(chest);
   }

   private Vec3 getVec(BlockPos pos) {
      return new Vec3((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
   }

   private double getDistanceToBlockPos(BlockPos pos) {
      return mc.thePlayer.getDistance((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
   }

   private boolean isValidChest(GuiChest chest) {
      return chest.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Chest") || chest.getLowerChestInventory().getDisplayName().getUnformattedText().equalsIgnoreCase("LOW");
   }

   private boolean isValidItem(ItemStack itemStack) {
      return itemStack.getItem() instanceof ItemArmor || itemStack.getItem() instanceof ItemSword || itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemFood || itemStack.getItem() instanceof ItemPotion || itemStack.getItem() instanceof ItemBlock;
   }

   private boolean isChestEmpty(GuiChest chest) {
      for(int index = 0; index < chest.getLowerChestInventory().getSizeInventory(); ++index) {
         ItemStack stack = chest.getLowerChestInventory().getStackInSlot(index);
         if (stack != null && this.isValidItem(stack)) {
            return false;
         }
      }

      return true;
   }

   private boolean isInventoryFull() {
      for(int index = 9; index <= 44; ++index) {
         ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
         if (stack == null) {
            return false;
         }
      }

      return true;
   }
}
