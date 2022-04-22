package me.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

public class AStarCustomPathFinder {
  private Vec3 startVec3;
  
  private Vec3 endVec3;
  
  private ArrayList<Vec3> path = new ArrayList<>();
  
  private ArrayList<Hub> hubs = new ArrayList<>();
  
  private ArrayList<Hub> hubsToWork = new ArrayList<>();
  
  private double minDistanceSquared = 9.0D;
  
  private boolean nearest = true;
  
  private static Vec3[] flatCardinalDirections = new Vec3[] { new Vec3(1.0D, 0.0D, 0.0D), 
      new Vec3(-1.0D, 0.0D, 0.0D), 
      new Vec3(0.0D, 0.0D, 1.0D), 
      new Vec3(0.0D, 0.0D, -1.0D) };
  
  public AStarCustomPathFinder(Vec3 startVec3, Vec3 endVec3) {
    this.startVec3 = startVec3.addVector(0.0D, 0.0D, 0.0D).floor();
    this.endVec3 = endVec3.addVector(0.0D, 0.0D, 0.0D).floor();
  }
  
  public ArrayList<Vec3> getPath() {
    return this.path;
  }
  
  public void compute() {
    compute(1000, 4);
  }
  
  public void compute(int loops, int depth) {
    this.path.clear();
    this.hubsToWork.clear();
    ArrayList<Vec3> initPath = new ArrayList<>();
    initPath.add(this.startVec3);
    this.hubsToWork.add(new Hub(this.startVec3, null, initPath, this.startVec3.squareDistanceTo(this.endVec3), 0.0D, 0.0D));
    int i;
    label38: for (i = 0; i < loops; i++) {
      Collections.sort(this.hubsToWork, new CompareHub());
      int j = 0;
      if (this.hubsToWork.size() == 0)
        break; 
      for (Object h : new ArrayList(this.hubsToWork)) {
    	  Hub hub = (Hub)h;
        j++;
        if (j > depth)
          break; 
        this.hubsToWork.remove(hub);
        this.hubs.add(hub);
        byte b;
        int k;
        Vec3[] arrayOfVec3;
        for (k = (arrayOfVec3 = flatCardinalDirections).length, b = 0; b < k; ) {
          Vec3 direction = arrayOfVec3[b];
          Vec3 loc = hub.getLoc().add(direction).floor();
          if (checkPositionValidity(loc, false) && 
            addHub(hub, loc, 0.0D))
            break label38; 
          b++;
        } 
        Vec3 loc1 = hub.getLoc().addVector(0.0D, 1.0D, 0.0D).floor();
        if (checkPositionValidity(loc1, false) && 
          addHub(hub, loc1, 0.0D))
          break label38; 
        Vec3 loc2 = hub.getLoc().addVector(0.0D, -1.0D, 0.0D).floor();
        if (checkPositionValidity(loc2, false) && 
          addHub(hub, loc2, 0.0D))
          break label38; 
      } 
    } 
    if (this.nearest) {
      Collections.sort(this.hubs, new CompareHub());
      this.path = ((Hub)this.hubs.get(0)).getPath();
    } 
  }
  
  public static boolean checkPositionValidity(Vec3 loc, boolean checkGround) {
    return checkPositionValidity((int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), checkGround);
  }
  
  public static boolean checkPositionValidity(int x, int y, int z, boolean checkGround) {
    BlockPos block1 = new BlockPos(x, y, z);
    BlockPos block2 = new BlockPos(x, y + 1, z);
    BlockPos block3 = new BlockPos(x, y - 1, z);
    return (!isBlockSolid(block1) && !isBlockSolid(block2) && (isBlockSolid(block3) || !checkGround) && isSafeToWalkOn(block3));
  }
  
  private static boolean isBlockSolid(BlockPos block) {
    return !(!(Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()).isSolidFullCube() && 
      !((Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof net.minecraft.block.BlockSlab) && 
      !((Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof net.minecraft.block.BlockStairs) && 
      !((Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof net.minecraft.block.BlockCactus) && 
      !((Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof net.minecraft.block.BlockChest) && 
      !((Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof net.minecraft.block.BlockEnderChest) && 
      !((Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof net.minecraft.block.BlockSkull) && 
      !((Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof net.minecraft.block.BlockPane) && 
      !((Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof net.minecraft.block.BlockFence) && 
      !((Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof net.minecraft.block.BlockWall) && 
      !((Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof net.minecraft.block.BlockGlass) && 
      !((Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof net.minecraft.block.BlockPistonBase) && 
      !((Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof net.minecraft.block.BlockPistonExtension) && 
      !((Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof net.minecraft.block.BlockPistonMoving) && 
      !((Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof net.minecraft.block.BlockStainedGlass) && 
      !((Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof net.minecraft.block.BlockTrapDoor));
  }
  
  private static boolean isSafeToWalkOn(BlockPos block) {
    return (!((Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof net.minecraft.block.BlockFence) && 
      !((Minecraft.getMinecraft()).theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof net.minecraft.block.BlockWall));
  }
  
  public Hub isHubExisting(Vec3 loc) {
    for (Hub hub : this.hubs) {
      if (hub.getLoc().getX() == loc.getX() && hub.getLoc().getY() == loc.getY() && hub.getLoc().getZ() == loc.getZ())
        return hub; 
    } 
    for (Hub hub : this.hubsToWork) {
      if (hub.getLoc().getX() == loc.getX() && hub.getLoc().getY() == loc.getY() && hub.getLoc().getZ() == loc.getZ())
        return hub; 
    } 
    return null;
  }
  
  public boolean addHub(Hub parent, Vec3 loc, double cost) {
    Hub existingHub = isHubExisting(loc);
    double totalCost = cost;
    if (parent != null)
      totalCost += parent.getTotalCost(); 
    if (existingHub == null) {
      if ((loc.getX() == this.endVec3.getX() && loc.getY() == this.endVec3.getY() && loc.getZ() == this.endVec3.getZ()) || (this.minDistanceSquared != 0.0D && loc.squareDistanceTo(this.endVec3) <= this.minDistanceSquared)) {
        this.path.clear();
        this.path = parent.getPath();
        this.path.add(loc);
        return true;
      } 
      ArrayList<Vec3> path = new ArrayList<>(parent.getPath());
      path.add(loc);
      this.hubsToWork.add(new Hub(loc, parent, path, loc.squareDistanceTo(this.endVec3), cost, totalCost));
    } else if (existingHub.getCost() > cost) {
      ArrayList<Vec3> path = new ArrayList<>(parent.getPath());
      path.add(loc);
      existingHub.setLoc(loc);
      existingHub.setParent(parent);
      existingHub.setPath(path);
      existingHub.setSquareDistanceToFromTarget(loc.squareDistanceTo(this.endVec3));
      existingHub.setCost(cost);
      existingHub.setTotalCost(totalCost);
    } 
    return false;
  }
  
  private class Hub {
    private Vec3 loc = null;
    
    private Hub parent = null;
    
    private ArrayList<Vec3> path;
    
    private double squareDistanceToFromTarget;
    
    private double cost;
    
    private double totalCost;
    
    public Hub(Vec3 loc, Hub parent, ArrayList<Vec3> path, double squareDistanceToFromTarget, double cost, double totalCost) {
      this.loc = loc;
      this.parent = parent;
      this.path = path;
      this.squareDistanceToFromTarget = squareDistanceToFromTarget;
      this.cost = cost;
      this.totalCost = totalCost;
    }
    
    public Vec3 getLoc() {
      return this.loc;
    }
    
    public Hub getParent() {
      return this.parent;
    }
    
    public ArrayList<Vec3> getPath() {
      return this.path;
    }
    
    public double getSquareDistanceToFromTarget() {
      return this.squareDistanceToFromTarget;
    }
    
    public double getCost() {
      return this.cost;
    }
    
    public void setLoc(Vec3 loc) {
      this.loc = loc;
    }
    
    public void setParent(Hub parent) {
      this.parent = parent;
    }
    
    public void setPath(ArrayList<Vec3> path) {
      this.path = path;
    }
    
    public void setSquareDistanceToFromTarget(double squareDistanceToFromTarget) {
      this.squareDistanceToFromTarget = squareDistanceToFromTarget;
    }
    
    public void setCost(double cost) {
      this.cost = cost;
    }
    
    public double getTotalCost() {
      return this.totalCost;
    }
    
    public void setTotalCost(double totalCost) {
      this.totalCost = totalCost;
    }
  }
  
  public class CompareHub implements Comparator<Hub> {
    public int compare(AStarCustomPathFinder.Hub o1, AStarCustomPathFinder.Hub o2) {
      return (int)(
        o1.getSquareDistanceToFromTarget() + o1.getTotalCost() - o2.getSquareDistanceToFromTarget() + o2.getTotalCost());
    }
  }
}
