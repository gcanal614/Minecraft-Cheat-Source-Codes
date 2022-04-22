package com.zerosense.Utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.zerosense.Events.Event;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import optifine.Reflector;
import optifine.ReflectorClass;
import optifine.ReflectorMethod;

public class BlockUtils2 {
  private List<Block> invalid = Arrays.asList(new Block[] { Blocks.air, (Block)Blocks.water, (Block)Blocks.fire, (Block)Blocks.flowing_water, (Block)Blocks.lava, (Block)Blocks.flowing_lava, (Block)Blocks.chest, Blocks.anvil, Blocks.enchanting_table });
  
  public static Minecraft mc;
  
  private static ReflectorClass ForgeBlock = new ReflectorClass(Block.class);
  
  private static ReflectorMethod ForgeBlock_setLightOpacity = new ReflectorMethod(ForgeBlock, "setLightOpacity");
  
  private static boolean directAccessValid = true;
  
  public Block getBlockByIDorName(String paramString) {
    Block block = null;
    try {
      block = Block.getBlockById(Integer.parseInt(paramString));
      if ((0x5C & 0xFFFFFFA3) != 0)
        return null; 
    } catch (NumberFormatException numberFormatException) {
      Block block1 = null;
      Iterator<Object> iterator = Block.blockRegistry.iterator();
      if ((0x1A & 0xFFFFFFE5) != 0)
        return null; 
      while (iterator.hasNext()) {
        Block block2 = (Block)iterator.next();
        block1 = block2;
        String str = block1.getLocalizedName().replace(" ", "");
        if (!str.toLowerCase().startsWith(paramString)) {
          if (str.toLowerCase().contains(paramString)) {
            if (2 <= (0x5 & 0xFFFFFFFA))
              return null; 
            break;
          } 
          continue;
        } 
        break;
      } 
      if (block1 != null)
        block = block1; 
    } 
    return block;
  }
  
  public static boolean isOnIce() {
    if (mc.thePlayer == null)
      return false; 
    boolean bool = false;
    int i = (int)(mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.01D, 0.0D)).minY;
    for (int j = MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).minX); j < MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).maxX) + 1; j++) {
      for (int k = MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).minZ); k < MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).maxZ) + 1; k++) {
        Block block = getBlock(j, i, k);
        if (block != null && !(block instanceof net.minecraft.block.BlockAir)) {
          if (!(block instanceof net.minecraft.block.BlockIce) && !(block instanceof net.minecraft.block.BlockPackedIce))
            return false; 
          bool = true;
        } 
      } 
    } 
    return bool;
  }
  
  public static boolean isOnLadder() {
    if (mc.thePlayer == null)
      return false; 
    boolean bool = false;
    int i = (int)(mc.thePlayer.getEntityBoundingBox().offset(0.0D, 1.0D, 0.0D)).minY;
    for (int j = MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).minX); j < MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).maxX) + 1; j++) {
      for (int k = MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).minZ); k < MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).maxZ) + 1; k++) {
        Block block = getBlock(j, i, k);
        if (block != null && !(block instanceof net.minecraft.block.BlockAir)) {
          if (!(block instanceof net.minecraft.block.BlockLadder) && !(block instanceof net.minecraft.block.BlockVine))
            return false; 
          bool = true;
        } 
      } 
    } 
    return !(!bool && !mc.thePlayer.isOnLadder());
  }
  
  public static boolean canSeeBlock(float paramFloat1, float paramFloat2, float paramFloat3) {
    return (getFacing(new BlockPos(paramFloat1, paramFloat2, paramFloat3)) != null);
  }
  
  public static Block getBlockAtPosC(EntityPlayer paramEntityPlayer, double paramDouble1, double paramDouble2, double paramDouble3) {
    return getBlock(new BlockPos(paramEntityPlayer.posX - paramDouble1, paramEntityPlayer.posY - paramDouble2, paramEntityPlayer.posZ - paramDouble3));
  }
  
  public static BlockData getBlockData(BlockPos paramBlockPos, List paramList) {
    if (!paramList.contains(mc.theWorld.getBlockState(paramBlockPos.add(0, -1, 0)).getBlock())) {
      if ((0x43 & 0xFFFFFFBC) != 0)
        return null; 
    } else {
    
    } 
    return !paramList.contains(mc.theWorld.getBlockState(paramBlockPos.add(-1, 0, 0)).getBlock()) ? new BlockData(paramBlockPos.add(-1, 0, 0), EnumFacing.EAST) : (!paramList.contains(mc.theWorld.getBlockState(paramBlockPos.add(1, 0, 0)).getBlock()) ? new BlockData(paramBlockPos.add(1, 0, 0), EnumFacing.WEST) : (!paramList.contains(mc.theWorld.getBlockState(paramBlockPos.add(0, 0, -1)).getBlock()) ? new BlockData(paramBlockPos.add(0, 0, -1), EnumFacing.SOUTH) : (!paramList.contains(mc.theWorld.getBlockState(paramBlockPos.add(0, 0, 1)).getBlock()) ? new BlockData(paramBlockPos.add(0, 0, 1), EnumFacing.NORTH) : null)));
  }
  
  public static EnumFacing getFacing(BlockPos paramBlockPos) {
    EnumFacing[] arrayOfEnumFacing1 = { EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.DOWN };
    EnumFacing[] arrayOfEnumFacing2 = arrayOfEnumFacing1;
    int i = arrayOfEnumFacing1.length;
    byte b = 0;
    if ((0x46 & 0xFFFFFFB9) > 2)
      return null; 
    while (b < i) {
      EnumFacing enumFacing = arrayOfEnumFacing2[b];
      EntitySnowball entitySnowball = new EntitySnowball((World)mc.theWorld);
      entitySnowball.posX = paramBlockPos.getX() + 0.5D;
      entitySnowball.posY = paramBlockPos.getY() + 0.5D;
      entitySnowball.posZ = paramBlockPos.getZ() + 0.5D;
      entitySnowball.posX += enumFacing.getDirectionVec().getX() * 0.5D;
      entitySnowball.posY += enumFacing.getDirectionVec().getY() * 0.5D;
      entitySnowball.posZ += enumFacing.getDirectionVec().getZ() * 0.5D;
      if (mc.thePlayer.canEntityBeSeen((Entity)entitySnowball))
        return enumFacing; 
      b++;
    } 
    return null;
  }
  
  public BlockData getBlockData1(BlockPos paramBlockPos) {
    List<Block> list = this.invalid;
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(paramBlockPos.add(0, -1, 0)).getBlock()))
      return new BlockData(paramBlockPos.add(0, -1, 0), EnumFacing.UP); 
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(paramBlockPos.add(-1, 0, 0)).getBlock()))
      return new BlockData(paramBlockPos.add(-1, 0, 0), EnumFacing.EAST); 
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(paramBlockPos.add(1, 0, 0)).getBlock()))
      return new BlockData(paramBlockPos.add(1, 0, 0), EnumFacing.WEST); 
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(paramBlockPos.add(0, 0, -1)).getBlock()))
      return new BlockData(paramBlockPos.add(0, 0, -1), EnumFacing.SOUTH); 
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(paramBlockPos.add(0, 0, 1)).getBlock()))
      return new BlockData(paramBlockPos.add(0, 0, 1), EnumFacing.NORTH); 
    BlockPos blockPos1 = paramBlockPos.add(-1, 0, 0);
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(blockPos1.add(-1, 0, 0)).getBlock()))
      return new BlockData(blockPos1.add(-1, 0, 0), EnumFacing.EAST); 
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(blockPos1.add(1, 0, 0)).getBlock()))
      return new BlockData(blockPos1.add(1, 0, 0), EnumFacing.WEST); 
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(blockPos1.add(0, 0, -1)).getBlock()))
      return new BlockData(blockPos1.add(0, 0, -1), EnumFacing.SOUTH); 
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(blockPos1.add(0, 0, 1)).getBlock()))
      return new BlockData(blockPos1.add(0, 0, 1), EnumFacing.NORTH); 
    BlockPos blockPos2 = paramBlockPos.add(1, 0, 0);
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(blockPos2.add(-1, 0, 0)).getBlock()))
      return new BlockData(blockPos2.add(-1, 0, 0), EnumFacing.EAST); 
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(blockPos2.add(1, 0, 0)).getBlock()))
      return new BlockData(blockPos2.add(1, 0, 0), EnumFacing.WEST); 
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(blockPos2.add(0, 0, -1)).getBlock()))
      return new BlockData(blockPos2.add(0, 0, -1), EnumFacing.SOUTH); 
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(blockPos2.add(0, 0, 1)).getBlock()))
      return new BlockData(blockPos2.add(0, 0, 1), EnumFacing.NORTH); 
    BlockPos blockPos3 = paramBlockPos.add(0, 0, -1);
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(blockPos3.add(-1, 0, 0)).getBlock()))
      return new BlockData(blockPos3.add(-1, 0, 0), EnumFacing.EAST); 
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(blockPos3.add(1, 0, 0)).getBlock()))
      return new BlockData(blockPos3.add(1, 0, 0), EnumFacing.WEST); 
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(blockPos3.add(0, 0, -1)).getBlock()))
      return new BlockData(blockPos3.add(0, 0, -1), EnumFacing.SOUTH); 
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(blockPos3.add(0, 0, 1)).getBlock()))
      return new BlockData(blockPos3.add(0, 0, 1), EnumFacing.NORTH); 
    BlockPos blockPos4 = paramBlockPos.add(0, 0, 1);
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(blockPos4.add(-1, 0, 0)).getBlock()))
      return new BlockData(blockPos4.add(-1, 0, 0), EnumFacing.EAST); 
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(blockPos4.add(1, 0, 0)).getBlock()))
      return new BlockData(blockPos4.add(1, 0, 0), EnumFacing.WEST); 
    Minecraft.getMinecraft();
    if (!list.contains(mc.theWorld.getBlockState(blockPos4.add(0, 0, -1)).getBlock()))
      return new BlockData(blockPos4.add(0, 0, -1), EnumFacing.SOUTH); 
    Minecraft.getMinecraft();
    return !list.contains(mc.theWorld.getBlockState(blockPos4.add(0, 0, 1)).getBlock()) ? new BlockData(blockPos4.add(0, 0, 1), EnumFacing.NORTH) : null;
  }
  
  public static float changeRotation(float paramFloat1, float paramFloat2, float paramFloat3) {
    float f = MathHelper.wrapAngleTo180_float(paramFloat2 - paramFloat1);
    if (f > paramFloat3)
      f = paramFloat3; 
    if (f < -paramFloat3)
      f = -paramFloat3; 
    return paramFloat1 + f;
  }
  
  public static float[] getFacingRotations(int paramInt1, int paramInt2, int paramInt3, EnumFacing paramEnumFacing) {
    EntitySnowball entitySnowball = new EntitySnowball((World)mc.theWorld);
    entitySnowball.posX = paramInt1 + 0.5D;
    entitySnowball.posY = paramInt2 + 0.5D;
    entitySnowball.posZ = paramInt3 + 0.5D;
    entitySnowball.posX += paramEnumFacing.getDirectionVec().getX() * 0.25D;
    entitySnowball.posY += paramEnumFacing.getDirectionVec().getY() * 0.25D;
    entitySnowball.posZ += paramEnumFacing.getDirectionVec().getZ() * 0.25D;
    return faceTarget((Entity)entitySnowball, 100.0F, 100.0F, false);
  }
  
  public void onEvent(Event paramEvent) {
  }
  
  public static Block getBlockAbovePlayer(EntityPlayer paramEntityPlayer, double paramDouble) {
    return getBlock(new BlockPos(paramEntityPlayer.posX, paramEntityPlayer.posY + paramEntityPlayer.height + paramDouble, paramEntityPlayer.posZ));
  }
  
  public int getBlockSlot() {
    for (byte b = 36; b < 45; b++) {
      Minecraft.getMinecraft();
      ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(b).getStack();
      if (itemStack != null && itemStack.getItem() instanceof net.minecraft.item.ItemBlock)
        return b - 36; 
    } 
    return -1;
  }
  
  public boolean isInsideBlock() {
    for (int i = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); i < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + 1; i++) {
      for (int j = MathHelper.floor_double(mc.thePlayer.boundingBox.minY); j < MathHelper.floor_double(mc.thePlayer.boundingBox.maxY) + 1; j++) {
        for (int k = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); k < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + 1; k++) {
          Block block = mc.theWorld.getBlockState(new BlockPos(i, j, k)).getBlock();
          if (block != null && !(block instanceof net.minecraft.block.BlockAir)) {
            AxisAlignedBB axisAlignedBB = block.getCollisionBoundingBox((World)mc.theWorld, new BlockPos(i, j, k), mc.theWorld.getBlockState(new BlockPos(i, j, k)));
            if (axisAlignedBB != null && mc.thePlayer.boundingBox.intersectsWith(axisAlignedBB))
              return true; 
          } 
        } 
      } 
    } 
    return false;
  }
  
  public static boolean isOnLiquid() {
    boolean bool = false;
    if (getBlockAtPosC((EntityPlayer)mc.thePlayer, 0.30000001192092896D, 0.10000000149011612D, 0.30000001192092896D).getMaterial().isLiquid() && getBlockAtPosC((EntityPlayer)mc.thePlayer, -0.30000001192092896D, 0.10000000149011612D, -0.30000001192092896D).getMaterial().isLiquid())
      bool = true; 
    return bool;
  }
  
  public static void setLightOpacity(Block paramBlock, int paramInt) {
    if (directAccessValid)
      try {
        paramBlock.setLightOpacity(paramInt);
        return;
      } catch (IllegalAccessError illegalAccessError) {
        directAccessValid = false;
        if (!ForgeBlock_setLightOpacity.exists())
          throw illegalAccessError; 
      }  
    Reflector.callVoid(paramBlock, ForgeBlock_setLightOpacity, new Object[] { Integer.valueOf(paramInt) });
  }
  
  public int getBestSlot() {
    Minecraft.getMinecraft();
    if (mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemBlock) {
      Minecraft.getMinecraft();
      return mc.thePlayer.inventory.currentItem;
    } 
    for (byte b = 0; b < 8; b++) {
      Minecraft.getMinecraft();
      if (mc.thePlayer.inventory.getStackInSlot(b) != null) {
        Minecraft.getMinecraft();
        if (mc.thePlayer.inventory.getStackInSlot(b).getItem() instanceof net.minecraft.item.ItemBlock)
          return b; 
      } 
    } 
    return -1;
  }
  
  public static Block getBlockUnderPlayer(EntityPlayer paramEntityPlayer, double paramDouble) {
    return getBlock(new BlockPos(paramEntityPlayer.posX, paramEntityPlayer.posY - paramDouble, paramEntityPlayer.posZ));
  }
  
  public static Block getBlock(BlockPos paramBlockPos) {
    return mc.theWorld.getBlockState(paramBlockPos).getBlock();
  }
  
  public static Block getBlock(int paramInt1, int paramInt2, int paramInt3) {
    return mc.theWorld.getBlockState(new BlockPos(paramInt1, paramInt2, paramInt3)).getBlock();
  }
  
  public static float[] getAngles(EntityPlayerSP paramEntityPlayerSP, BlockPos paramBlockPos) {
    double d1 = paramBlockPos.getX() + 0.5D - paramEntityPlayerSP.posX;
    double d2 = paramBlockPos.getY() - paramEntityPlayerSP.posY + paramEntityPlayerSP.getEyeHeight();
    double d3 = paramBlockPos.getZ() + 0.5D - paramEntityPlayerSP.posZ;
    double d4 = Math.sqrt(d1 * d1 + d3 * d3);
    float f1 = (float)(Math.atan2(d3, d1) * 180.0D / Math.PI) - 90.0F;
    float f2 = (float)-(Math.atan2(d2, d4) * 180.0D / Math.PI);
    return new float[] { f1, f2 };
  }
  
  public static float[] faceTarget(Entity paramEntity, float paramFloat1, float paramFloat2, boolean paramBoolean) {
    double d1;
    double d2 = paramEntity.posX - mc.thePlayer.posX;
    double d3 = paramEntity.posZ - mc.thePlayer.posZ;
    if (paramEntity instanceof EntityLivingBase) {
      EntityLivingBase entityLivingBase = (EntityLivingBase)paramEntity;
      d1 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
    } else {
      d1 = ((paramEntity.getEntityBoundingBox()).minY + (paramEntity.getEntityBoundingBox()).maxY) / 2.0D - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
    } 
    Random random = new Random();
    double d4 = MathHelper.sqrt_double(d2 * d2 + d3 * d3);
    float f1 = (float)(Math.atan2(d3, d2) * 180.0D / Math.PI) - 90.0F;
    if (paramEntity instanceof EntityPlayer) {
      if (4 <= (0x38 & 0xFFFFFFC7))
        return null; 
    } else {
    
    } 
    float f2 = (float)-(Math.atan2(d1 - 0.0D, d4) * 180.0D / Math.PI);
    float f3 = changeRotation(mc.thePlayer.rotationPitch, f2, paramFloat2);
    float f4 = changeRotation(mc.thePlayer.rotationYaw, f1, paramFloat1);
    return new float[] { f4, f3 };
  }
  
  public static boolean isBlockUnderPlayer(Material paramMaterial, float paramFloat) {
    return (getBlockAtPosC((EntityPlayer)mc.thePlayer, 0.3100000023841858D, paramFloat, 0.3100000023841858D).getMaterial() == paramMaterial && getBlockAtPosC((EntityPlayer)mc.thePlayer, -0.3100000023841858D, paramFloat, -0.3100000023841858D).getMaterial() == paramMaterial && getBlockAtPosC((EntityPlayer)mc.thePlayer, -0.3100000023841858D, paramFloat, 0.3100000023841858D).getMaterial() == paramMaterial && getBlockAtPosC((EntityPlayer)mc.thePlayer, 0.3100000023841858D, paramFloat, -0.3100000023841858D).getMaterial() == paramMaterial);
  }
  
  public static class BlockData {
    public EnumFacing face;
    
    public BlockPos position;
    
    public BlockData(BlockPos param1BlockPos, EnumFacing param1EnumFacing) {
      this.position = param1BlockPos;
      this.face = param1EnumFacing;
    }
  }
}
