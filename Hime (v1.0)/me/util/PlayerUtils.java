package me.util;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

public class PlayerUtils {
	public static PlayerUtils instance = new PlayerUtils();
	public static boolean aacdamage = false;
	public static double aacdamagevalue;
	private static Minecraft mc = Minecraft.getMinecraft();
	
	public static void damagePlayer(double value) {
		aacdamage = true;
		aacdamagevalue = value + 2.85D;
		Minecraft.getMinecraft().thePlayer.moveForward ++;
		Minecraft.getMinecraft().thePlayer.moveForward --;
		Minecraft.getMinecraft().thePlayer.moveStrafing --;
		Minecraft.getMinecraft().thePlayer.moveStrafing ++;
		Minecraft.getMinecraft().thePlayer.jump();
	}
	
	  public static boolean isBad(ItemStack item) {
		    return (!(item.getItem() instanceof net.minecraft.item.ItemArmor) && 
		    		!(item.getItem() instanceof ItemTool) && !(item.getItem() instanceof net.minecraft.item.ItemBlock) && 
		    		!(item.getItem() instanceof ItemSword) && !(item.getItem() instanceof net.minecraft.item.ItemEnderPearl) && 
		    		!(item.getItem() instanceof net.minecraft.item.ItemFood) && (!(item.getItem() instanceof ItemPotion) || isBadPotion(item)) && 
		    		!item.getDisplayName().toLowerCase().contains(EnumChatFormatting.GRAY + "(right click)"));
		  }
    public static boolean isInLiquid() {
        for(int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minY); x < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxX) + 1; ++x) {
            for(int z = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minZ); z < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxZ) + 1; ++z) {
                BlockPos pos = new BlockPos(x, (int)Minecraft.getMinecraft().thePlayer.boundingBox.minY, z);
                Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
                if(block != null && !(block instanceof BlockAir))
                    return block instanceof BlockLiquid;
            }
        }
        return false;
    }
    
    public static boolean isInLiquid(Entity e) {
        for(int x = MathHelper.floor_double(e.boundingBox.minY); x < MathHelper.floor_double(e.boundingBox.maxX) + 1; ++x) {
            for(int z = MathHelper.floor_double(e.boundingBox.minZ); z < MathHelper.floor_double(e.boundingBox.maxZ) + 1; ++z) {
                BlockPos pos = new BlockPos(x, (int)e.boundingBox.minY, z);
                Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
                if(block != null && !(block instanceof BlockAir))
                    return block instanceof BlockLiquid;
            }
        }
        return false;
    }
    
    public static boolean isBadPotion(ItemStack stack) {
      if (stack != null && stack.getItem() instanceof ItemPotion) {
        ItemPotion potion = (ItemPotion)stack.getItem();
        if (ItemPotion.isSplash(stack.getItemDamage()))
          for (Object o : potion.getEffects(stack)) {
            PotionEffect effect = (PotionEffect)o;
            if (effect.getPotionID() == Potion.poison.getId() || effect.getPotionID() == Potion.harm.getId() || effect.getPotionID() == Potion.moveSlowdown.getId() || effect.getPotionID() == Potion.weakness.getId())
              return true; 
          }  
      } 
      return false;
    }

    public static float getMinFallDist() {
        float minDist = 3.0F;
        if (mc.thePlayer.isPotionActive(Potion.jump))
            minDist += mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1.0F;
        return minDist;
    }
    
    public static boolean isBlockAbove() {
        for (double height = 0.0D; height <= 1.0D; height += 0.5D) {
            List<AxisAlignedBB> collidingList = mc.theWorld.getCollidingBoundingBoxes(
                    mc.thePlayer,
                    mc.thePlayer.getEntityBoundingBox().offset(0, height, 0));
            if (!collidingList.isEmpty())
                return true;
        }

        return false;
    }
    
    public static boolean damage2() {
        if (!mc.thePlayer.onGround || isBlockAbove()) return false;

        final EntityPlayerSP player = mc.thePlayer;
        final double randomOffset = Math.random() * 0.0003F;
        final double jumpHeight = 0.0625D - 1.0E-2D - randomOffset;
        final int packets = (int) ((getMinFallDist() / (jumpHeight - randomOffset)) + 1);

        for (int i = 0; i < packets; i++) {
            mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(
                    player.posX,
                    player.posY + jumpHeight,
                    player.posZ,
                    false));
            mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(
                    player.posX,
                    player.posY + randomOffset,
                    player.posZ,
                    false));
        }

        mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer(true));
        return true;
    }
    
    public static void damage3() {
        for (int i = 0; i < PlayerUtils.getMaxFallDist() / 0.051612F + 1.0; ++i) {
             mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.060152374F, mc.thePlayer.posZ, false));
             mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 5.01E-4D, mc.thePlayer.posZ, false));
             mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.006061691F + 6.01E-8D, mc.thePlayer.posZ, false));
        }
         mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer(true));
    }
    
    public static boolean isInsideBlock() {
        for(int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minX); x < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxX) + 1; x++) {
            for(int y = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minY); y < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxY) + 1; y++) {
                for(int z = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.minZ); z < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.boundingBox.maxZ) + 1; z++) {
                    Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if(block != null && !(block instanceof BlockAir)) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(Minecraft.getMinecraft().theWorld, new BlockPos(x, y, z), Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)));
                        if(block instanceof BlockHopper)
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        if(boundingBox != null && Minecraft.getMinecraft().thePlayer.boundingBox.intersectsWith(boundingBox))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    
    public static void damage() {
        for (int i = 0; i < (getMaxFallDist() / (0.06011F - 0.005F)) + 1; i++) {
            mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 0.06011F, Minecraft.getMinecraft().thePlayer.posZ, false));
            mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 0.000495765F, Minecraft.getMinecraft().thePlayer.posZ, false));
            mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 0.0049575F + 0.06011F * 0.000001, Minecraft.getMinecraft().thePlayer.posZ, false));
        }
        mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer(true));
    }

	 public static float getMaxFallDist() {
      PotionEffect potioneffect = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.jump);
      int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
      return (float)(Minecraft.getMinecraft().thePlayer.getMaxFallHeight() + f);
   }

	 
	  
}
