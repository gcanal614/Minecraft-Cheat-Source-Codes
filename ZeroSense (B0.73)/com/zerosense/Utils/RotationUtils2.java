package com.zerosense.Utils;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class RotationUtils2 {
  public float hitDelay;
  
  public float blockDamage;
  
  public static Minecraft mc;
  
  public static float[] getRotationFromPosition(double paramDouble1, double paramDouble2, double paramDouble3) {
    Minecraft.getMinecraft();
    double d1 = paramDouble1 - mc.thePlayer.posX;
    Minecraft.getMinecraft();
    double d2 = paramDouble2 - mc.thePlayer.posZ;
    Minecraft.getMinecraft();
    double d3 = paramDouble3 - mc.thePlayer.posY - 0.6D;
    double d4 = MathHelper.sqrt_double(d1 * d1 + d2 * d2);
    float f1 = (float)(Math.atan2(d2, d1) * 180.0D / Math.PI) - 90.0F;
    float f2 = (float)-(Math.atan2(d3, d4) * 180.0D / Math.PI);
    return new float[] { f1, f2 };
  }
  
  public float getDistanceToEntity(TileEntity paramTileEntity) {
    float f1 = (float)(mc.thePlayer.posX - paramTileEntity.getPos().getX());
    float f2 = (float)(mc.thePlayer.posY - paramTileEntity.getPos().getY());
    float f3 = (float)(mc.thePlayer.posZ - paramTileEntity.getPos().getZ());
    return MathHelper.sqrt_float(f1 * f1 + f2 * f2 + f3 * f3);
  }
  
  public boolean isVisibleFOV(TileEntity paramTileEntity, EntityPlayerSP paramEntityPlayerSP, int paramInt) {
    if (Math.abs(getRotationsTileEntity(paramTileEntity)[0].floatValue() - paramEntityPlayerSP.rotationYaw) % 360.0F > 180.0F) {
      if (3 >= 4) {
      } else {

      }
    }
      return (Math.abs(getRotationsTileEntity(paramTileEntity)[0].floatValue() - paramEntityPlayerSP.rotationYaw) % 360.0F <= paramInt);
  }
  
  public void breakBlock(double paramDouble1, double paramDouble2, double paramDouble3) {
    mc.thePlayer.swingItem();
    mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, getBlockPos(paramDouble1, paramDouble2, paramDouble3), getEnumFacing((int)paramDouble1, (int)paramDouble2, (int)paramDouble3)));
    mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, getBlockPos(paramDouble1, paramDouble2, paramDouble3), getEnumFacing((int)paramDouble1, (int)paramDouble2, (int)paramDouble3)));
  }
  
  public boolean canBlockBeSeen(int paramInt1, int paramInt2, int paramInt3) {
    Vec3 vec31 = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
    Vec3 vec32 = new Vec3((paramInt1 + 0.5F), (paramInt2 + 0.5F), (paramInt3 + 0.5F));
    if ((mc.theWorld.rayTraceBlocks(vec31, vec32)).field_178784_b != null) {
      if ((0x63 & 0xFFFFFF9C) > 2) {
      } else {

      }
    }
    return ((mc.theWorld.rayTraceBlocks(vec31, vec32) != null) ? Boolean.valueOf(false) : null).booleanValue();
  }
  
  public TileEntityChest getBestEntity(double paramDouble) {
    TileEntityChest tileEntityChest = null;
    double d = paramDouble;
    for (Object tileEntityChest1 : mc.theWorld.loadedEntityList) {
      TileEntityChest tileEntityChest2 = (TileEntityChest) tileEntityChest1;
      if (getDistanceToEntity((TileEntity)tileEntityChest2) <= 6.0F) {
        double d1 = getDistanceToEntity((TileEntity)tileEntityChest2);
        if (d1 > d)
          continue; 
        d = d1;
        tileEntityChest = tileEntityChest2;
      } 
    } 
    return tileEntityChest;
  }
  
  public EnumFacing getEnumFacing(float paramFloat1, float paramFloat2, float paramFloat3) {
    return EnumFacing.func_176737_a(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public float getDistanceToVec(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
    float f1 = (float)(paramDouble1 - paramDouble4);
    float f2 = (float)(paramDouble2 - paramDouble5);
    float f3 = (float)(paramDouble3 - paramDouble6);
    return MathHelper.sqrt_float(f1 * f1 + f2 * f2 + f3 * f3);
  }
  
  public void moveRight() {
    mc.gameSettings.keyBindRight.pressed = true;
  }
  
  public static float getNewAngle(float paramFloat) {
    paramFloat %= 360.0F;
    if (paramFloat >= 180.0F)
      paramFloat -= 360.0F; 
    if (paramFloat < -180.0F)
      paramFloat += 360.0F; 
    return paramFloat;
  }
  
  public void placeBlock(int paramInt1, int paramInt2, int paramInt3) {
    mc.thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(getBlockPos(paramInt1, paramInt2, paramInt3), getEnumFacing(paramInt1, paramInt2, paramInt3).getIndex(), mc.thePlayer.getHeldItem(), 0.0F, 0.0F, 0.0F));
  }
  
  public BlockPos getBlockPos(double paramDouble1, double paramDouble2, double paramDouble3) {
    return new BlockPos(paramDouble1, paramDouble2, paramDouble3);
  }
  
  public Block getBlock(double paramDouble1, double paramDouble2, double paramDouble3) {
    paramDouble1 = MathHelper.floor_double(paramDouble1);
    paramDouble2 = MathHelper.floor_double(paramDouble2);
    paramDouble3 = MathHelper.floor_double(paramDouble3);
    return mc.theWorld.getChunkFromBlockCoords(new BlockPos(paramDouble1, paramDouble2, paramDouble3)).getBlock(new BlockPos(paramDouble1, paramDouble2, paramDouble3));
  }
  
  public void movePlayerToBlock(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat) {
    faceBlock(paramDouble1 + 0.5D, paramDouble2 + 0.5D, paramDouble3 + 0.5D);
    moveForward();
    if (mc.thePlayer.onGround && mc.thePlayer.isCollidedHorizontally && !Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()) && !mc.thePlayer.isInWater())
      mc.thePlayer.jump(); 
    if (canReach(paramDouble1, paramDouble2, paramDouble3, paramFloat))
      stopMoving(); 
  }
  
  public static float[] getAverageRotations(List<EntityLivingBase> paramList) {
    double d1 = 0.0D;
    double d2 = 0.0D;
    double d3 = 0.0D;
    for (Entity entity : paramList) {
      d1 += entity.posX;
      d2 += entity.boundingBox.maxY - 2.0D;
      d3 += entity.posZ;
    } 
    d1 /= paramList.size();
    d2 /= paramList.size();
    d3 /= paramList.size();
    return new float[] { getRotationFromPosition(d1, d3, d2)[0], getRotationFromPosition(d1, d3, d2)[1] };
  }
  
  public static float[] faceBlock(double paramDouble1, double paramDouble2, double paramDouble3) {
    double d1 = paramDouble1 - mc.thePlayer.posX;
    double d2 = paramDouble3 - mc.thePlayer.posZ;
    double d3 = paramDouble2 - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
    double d4 = MathHelper.sqrt_double(d1 * d1 + d2 * d2);
    float f1 = (float)(Math.atan2(d2, d1) * 180.0D / Math.PI) - 90.0F;
    float f2 = (float)-(Math.atan2(d3, d4) * 180.0D / Math.PI);
    float f3 = mc.thePlayer.rotationYaw;
    float f4 = mc.thePlayer.rotationPitch;
    f3 += MathHelper.wrapAngleTo180_float(f1 - f3);
    (new float[2])[0] = f3;
    f4 += MathHelper.wrapAngleTo180_float(f2 - f4);
    return new float[] { 0, f4 };
  }
  
  public void moveBack() {
    mc.gameSettings.keyBindBack.pressed = true;
  }
  
  public static float getTrajAngleSolutionLow(float paramFloat1, float paramFloat2, float paramFloat3) {
    float f1 = 0.006F;
    float f2 = paramFloat3 * paramFloat3 * paramFloat3 * paramFloat3 - 0.006F * (0.006F * paramFloat1 * paramFloat1 + 2.0F * paramFloat2 * paramFloat3 * paramFloat3);
    return (float)Math.toDegrees(Math.atan(((paramFloat3 * paramFloat3) - Math.sqrt(f2)) / (0.006F * paramFloat1)));
  }
  
  public boolean canReach(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat) {
    double d = getDistance(paramDouble1, paramDouble2, paramDouble3);
    return (d < paramFloat && d > -paramFloat);
  }
  
  public boolean isContainerEmpty(Container paramContainer) {
    byte b1 = 0;
    byte b2 = (byte) ((paramContainer.inventorySlots.size() == 90) ? 54 : 27);
    while (b1 < b2) {
      if (paramContainer.getSlot(b1).getHasStack())
        return false; 
      b1++;
    } 
    return true;
  }
  
  public void breakBlockLegit(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.hitDelay++;
    mc.thePlayer.swingItem();
    if (this.blockDamage == 0.0F)
      mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, getBlockPos(paramInt1, paramInt2, paramInt3), getEnumFacing(paramInt1, paramInt2, paramInt3)));
    if (this.hitDelay >= paramInt4) {
      this.blockDamage += getBlock(paramInt1, paramInt2, paramInt3).getPlayerRelativeBlockHardness((EntityPlayer)mc.thePlayer, (World)mc.theWorld, new BlockPos(paramInt1, paramInt2, paramInt3));
      mc.theWorld.sendBlockBreakProgress(mc.thePlayer.getEntityId(), new BlockPos(paramInt1, paramInt2, paramInt3), (int)(this.blockDamage * 10.0F) - 1);
      if (this.blockDamage >= (mc.playerController.isInCreativeMode() ? 0.0F : 1.0F)) {
        mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, getBlockPos(paramInt1, paramInt2, paramInt3), getEnumFacing(paramInt1, paramInt2, paramInt3)));
        mc.playerController.func_178888_a(getBlockPos(paramInt1, paramInt2, paramInt3), getEnumFacing(paramInt1, paramInt2, paramInt3));
        this.blockDamage = 0.0F;
        this.hitDelay = 0.0F;
      } 
    } 
  }
  
  private Float[] getRotationsTileEntity(TileEntity paramTileEntity) {
    double d1 = paramTileEntity.getPos().getX() - mc.thePlayer.posX;
    double d2 = paramTileEntity.getPos().getY() - mc.thePlayer.posZ;
    double d3 = (paramTileEntity.getPos().getZ() + 1) - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
    double d4 = MathHelper.sqrt_double(d1 * d1 + d2 * d2);
    float f1 = (float)Math.toDegrees(-Math.atan(d1 / d2));
    float f2 = (float)-Math.toDegrees(Math.atan(d3 / d4));
    if (d2 < 0.0D && d1 < 0.0D) {
      f1 = (float)(90.0D + Math.toDegrees(Math.atan(d2 / d1)));
    } else if (d2 < 0.0D && d1 > 0.0D) {
      f1 = (float)(-90.0D + Math.toDegrees(Math.atan(d2 / d1)));
    } 
    return new Float[] { Float.valueOf(f1), Float.valueOf(f2) };
  }
  
  public float getDistance(double paramDouble1, double paramDouble2, double paramDouble3) {
    float f1 = (float)(mc.thePlayer.posX - paramDouble1);
    float f2 = (float)(mc.thePlayer.posY - paramDouble2);
    float f3 = (float)(mc.thePlayer.posZ - paramDouble3);
    return MathHelper.sqrt_float(f1 * f1 + f2 * f2 + f3 * f3);
  }
  
  public boolean shouldHitBlock(int paramInt1, int paramInt2, int paramInt3, double paramDouble) {
    Block block = getBlock(paramInt1, paramInt2, paramInt3);
    boolean bool = (block instanceof net.minecraft.block.BlockLiquid) ? false : true;
    boolean bool1 = canBlockBeSeen(paramInt1, paramInt2, paramInt3);
    return (bool && bool1);
  }
  
  public static float getDistanceBetweenAngles(float paramFloat1, float paramFloat2) {
    float f = Math.abs(paramFloat1 - paramFloat2) % 360.0F;
    if (f > 180.0F)
      f = 360.0F - f; 
    return f;
  }
  
  public int getNextSlotInContainer(Container paramContainer) {
    byte b1 = 0;
    byte b2 = (byte) ((paramContainer.inventorySlots.size() == 90) ? 54 : 27);
    while (b1 < b2) {
      if (paramContainer.getInventory().get(b1) != null)
        return b1; 
      b1++;
    } 
    return -1;
  }
  
  public void moveForward() {
    mc.gameSettings.keyBindForward.pressed = true;
  }
  
  public void stopMoving() {
    mc.gameSettings.keyBindForward.pressed = false;
    mc.gameSettings.keyBindLeft.pressed = false;
    mc.gameSettings.keyBindRight.pressed = false;
    mc.gameSettings.keyBindBack.pressed = false;
  }
  
  public void moveLeft() {
    mc.gameSettings.keyBindLeft.pressed = true;
  }
  
  public static float[] getRotations(EntityLivingBase paramEntityLivingBase) {
    double d1 = paramEntityLivingBase.posX;
    double d2 = paramEntityLivingBase.posZ;
    double d3 = paramEntityLivingBase.posY + (paramEntityLivingBase.getEyeHeight() / 2.0F) - 0.5D;
    return getRotationFromPosition(d1, d2, d3);
  }
}
