package non.asset.utils;

import java.lang.reflect.Field;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import non.asset.event.bus.Handler;

public class PlayerUtils {

	public static Minecraft mc = Minecraft.getMinecraft();
	
	public static boolean falling() {
		if(mc.thePlayer.fallDistance > 3) {
			return true;
		}
		
		return false;
	}
	
	public static void swapShift(int slot) {
	      PlayerControllerMP var10000 = Minecraft.getMinecraft().playerController;
	      Minecraft.getMinecraft();
	      int var10001 = mc.thePlayer.inventoryContainer.windowId;
	      Minecraft.getMinecraft();
	      var10000.windowClick(var10001, slot, 0, 1, mc.thePlayer);
	}
	public static void swap(int slot, int hotbarNum) {
	   PlayerControllerMP var10000 = Minecraft.getMinecraft().playerController;
	   Minecraft.getMinecraft();
	   int var10001 = mc.thePlayer.inventoryContainer.windowId;
	   Minecraft.getMinecraft();
	   var10000.windowClick(var10001, slot, hotbarNum, 2, mc.thePlayer);
	}
	public static int getPotFromInventory() {
		int pot = -1;
		for(int i = 1; i < 45; ++i) {
			Minecraft.getMinecraft();
			if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
				Minecraft.getMinecraft();
				ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
				Item item = is.getItem();
				if (item instanceof ItemPotion) {
					ItemPotion potion = (ItemPotion)item;
					if (potion.getEffects(is) != null) {
						Iterator var6 = potion.getEffects(is).iterator();
						while(var6.hasNext()) {
							Object o = var6.next();
                  			PotionEffect effect = (PotionEffect)o;
                  			if (effect.getPotionID() == Potion.heal.id && ItemPotion.isSplash(is.getItemDamage())) {
                  				pot = i;
                     		}
                  		}
               		}
            	}
         	}
      	}
      	return pot;
   }

   public static int getPotsInInventory() {
      int counter = 0;
      for(int i = 1; i < 45; ++i) {
    	 Minecraft.getMinecraft();
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            Minecraft.getMinecraft();
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            Item item = is.getItem();
            if (item instanceof ItemPotion) {
               ItemPotion potion = (ItemPotion)item;
               if (potion.getEffects(is) != null) {
                  Iterator var6 = potion.getEffects(is).iterator();

                  while(var6.hasNext()) {
                     Object o = var6.next();
                     PotionEffect effect = (PotionEffect)o;
                     if (effect.getPotionID() == Potion.heal.id && ItemPotion.isSplash(is.getItemDamage())) {
                        ++counter;
                     }
                  }
               }
            }
         }
      }

      return counter;
   }

   public static int getSoupInInventory() {
      int counter = 0;
      for(int i = 1; i < 45; ++i) {
         Minecraft.getMinecraft();
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            Minecraft.getMinecraft();
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            Item item = is.getItem();
            if (item instanceof ItemSoup && item instanceof ItemSoup) {
               ++counter;
            }
         }
      }

      return counter;
   }

   public static boolean isFullInv() {
	   	for(int i = 9; i < 45; ++i) {
	   		ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
	   		if (itemStack == null || itemStack.getUnlocalizedName().contains("air")) {
	   			return false;
         	}
      	}
      	return true;
   	}
	
   	public static void C06(double posX, double posY, double posZ, float rotationYaw, float rotationPitch, boolean onGround) {
		Minecraft mc = Minecraft.getMinecraft();
		mc.thePlayer.sendQueue.addToSendQueue(new C06PacketPlayerPosLook(posX, posY, posZ, rotationYaw, rotationPitch, onGround));
	}
	public static void moreC06(double posX, double posY, double posZ, float rotationYaw, float rotationPitch, boolean onGround) {
		Minecraft mc = Minecraft.getMinecraft();
		mc.thePlayer.sendQueue.addToSendQueue(new C06PacketPlayerPosLook(mc.thePlayer.posX + posX, mc.thePlayer.posY + posY ,mc.thePlayer.posZ + posZ, mc.thePlayer.rotationYaw + rotationYaw, mc.thePlayer.rotationPitch + rotationPitch, onGround));
	}

	public static void setPosition(double x, double y, double z) {
		Minecraft mc = Minecraft.getMinecraft();
		mc.thePlayer.setPosition(x, y, z);
	}
	
	@Handler
    public static void onSafewalk(non.asset.event.impl.player.SafewalkEvent event, boolean cancel) {
        if (Minecraft.getMinecraft().thePlayer != null) {
            event.setCanceled(cancel);
        }
    }
	
	
	public static void useItem(boolean e1) {

		Minecraft mc = Minecraft.getMinecraft();
		
	 	KeyBinding useBinding = mc.gameSettings.keyBindUseItem;
	 	
 		Field field = null;
			
		try {
			field = useBinding.getClass().getDeclaredField("pressed");
		} catch (NoSuchFieldException | SecurityException e) {
			
			e.printStackTrace();
		}
 		field.setAccessible(true);
 		try {
			field.setBoolean(useBinding, e1);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			
			e.printStackTrace();
		}
 		
 	
	}
	
	public static void openTab(boolean b) {

		Minecraft mc = Minecraft.getMinecraft();
		
	 	KeyBinding tabBinding = mc.gameSettings.keyBindPlayerList;
	 	
	 	try {
	 		Field field = tabBinding.getClass().getDeclaredField("pressed");
	 		field.setAccessible(true);
	 		field.setBoolean(tabBinding, b);
	 		
	 	} catch (NoSuchFieldException | IllegalAccessException e){
	 		e.printStackTrace();
	 	}
	}
	
	public static void setPress(KeyBinding a, boolean b) {

		Minecraft mc = Minecraft.getMinecraft();
	 	
	 	try {
	 		Field field = a.getClass().getDeclaredField("pressed");
	 		field.setAccessible(true);
	 		field.setBoolean(a, b);
	 		
	 	} catch (NoSuchFieldException | IllegalAccessException e){
	 		e.printStackTrace();
	 	}
	}
	
}
