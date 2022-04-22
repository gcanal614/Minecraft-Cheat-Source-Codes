package com.zerosense.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BedInfo {
  public EnumFacing face;
  
  public BlockPos pos;
  
  public static Minecraft mc;
  

  
  public BedInfo(BlockPos paramBlockPos, EnumFacing paramEnumFacing) {
    this.pos = paramBlockPos;
    this.face = paramEnumFacing;
  }
}
