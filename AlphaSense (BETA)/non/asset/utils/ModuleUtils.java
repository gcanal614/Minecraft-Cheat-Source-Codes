package non.asset.utils;

import java.awt.Color;

import net.minecraft.block.BlockFalling;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import non.asset.module.impl.visuals.TargetHUD;

public class ModuleUtils {
	
	public static int slot = 0;
	
	
	public static Minecraft mc = Minecraft.getMinecraft();
	
	public Minecraft mc() {
		return Minecraft.getMinecraft();
	}
	
	public float getChance(float what, float total) {
		float result = what / total * 100;
		return result;
	}
	
	private int getColorTSD(int count, int max) {
        float f = count;
        float f1 = max;
        float f2 = Math.max(0.0F, Math.min(f, f1) / f1);
        return Color.HSBtoRGB(f2 / 3.0F, 1.0F, 1.0F) | 0xFF000000;
    }
	public static double motion(double start, double end, double value) {
        if(start < end){
        	start += value;
        }
        if(start > end) {
        	start -= value;
        }
        if(start == end) {
            start = end;
        }
        return start;
	}
	
	public static long randomNumber(double d, double e) {
		return Math.round(e + (double)Math.random() * ((d - e)));
	}
	
	public static int findItem(int whatreturn, Item item) {
		Minecraft mc = Minecraft.getMinecraft();
        for (int index = 36; index < 45; index++) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (itemStack != null) {
                if (itemStack.getItem() instanceof ItemBow) {
                    if (itemStack.stackSize >= 1) {
                        return index - 36;
                    }
                }
            }
        }
        return whatreturn;
    }
	
	public static void getItem(int orWhat, Item item) {
		Minecraft mc = Minecraft.getMinecraft();
		boolean blockInHand = false;
		
		item = new ItemBow();
		
        slot = mc.thePlayer.inventory.currentItem;
        if (mc.thePlayer.getHeldItem() != null) {
            blockInHand = mc.thePlayer.getHeldItem().getItem() instanceof ItemBow && mc.thePlayer.getHeldItem().stackSize > 0;
	   	}	
    	if(slot != -1) {
    	   	mc.thePlayer.inventory.currentItem = slot;
            slot = 0;
    	}
    	slot = mc.thePlayer.inventory.currentItem;
    	mc.thePlayer.inventory.currentItem = findItem(orWhat, new ItemBow());
    	
    	mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(findItem(0, new ItemBow())));
    	
    }
}

