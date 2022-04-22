package com.zerosense.Utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class MovingObjectPosition {
  private BlockPos blockPos;
  
  public MovingObjectType typeOfHit;
  
  public EnumFacing sideHit;
  
  public Vec3 hitVec;
  
  public Entity entityHit;
  
  public MovingObjectPosition(Vec3 paramVec3, EnumFacing paramEnumFacing, BlockPos paramBlockPos) {
    this(MovingObjectType.BLOCK, paramVec3, paramEnumFacing, paramBlockPos);
  }
  
  public MovingObjectPosition(Vec3 paramVec3, EnumFacing paramEnumFacing) {
    this(MovingObjectType.BLOCK, paramVec3, paramEnumFacing, BlockPos.ORIGIN);
  }
  
  public MovingObjectPosition(Entity paramEntity) {
    this(paramEntity, new Vec3(paramEntity.posX, paramEntity.posY, paramEntity.posZ));
  }
  
  public MovingObjectPosition(MovingObjectType paramMovingObjectType, Vec3 paramVec3, EnumFacing paramEnumFacing, BlockPos paramBlockPos) {
    this.typeOfHit = paramMovingObjectType;
    this.blockPos = paramBlockPos;
    this.sideHit = paramEnumFacing;
    this.hitVec = new Vec3(paramVec3.xCoord, paramVec3.yCoord, paramVec3.zCoord);
  }
  
  public MovingObjectPosition(Entity paramEntity, Vec3 paramVec3) {
    this.typeOfHit = MovingObjectType.ENTITY;
    this.entityHit = paramEntity;
    this.hitVec = paramVec3;
  }
  
  public BlockPos getBlockPos() {
    return this.blockPos;
  }
  
  public String toString() {
    return "HitResult{type=" + this.typeOfHit + ", blockpos=" + this.blockPos + ", f=" + this.sideHit + ", pos=" + this.hitVec + ", entity=" + this.entityHit + '}';
  }
  
  public enum MovingObjectType {
    MISS, BLOCK, ENTITY;
  }
}
