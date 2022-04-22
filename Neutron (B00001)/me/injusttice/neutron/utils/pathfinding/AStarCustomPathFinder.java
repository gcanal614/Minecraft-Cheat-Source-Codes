package me.injusttice.neutron.utils.pathfinding;

import me.injusttice.neutron.utils.world.Vec3;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockWall;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockSlab;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import java.util.Comparator;
import java.util.Collections;
import java.util.ArrayList;

public class AStarCustomPathFinder {
    
    private Vec3 startVec3;
    private Vec3 endVec3;
    private ArrayList<Vec3> path;
    private ArrayList<Hub> hubs;
    private ArrayList<Hub> hubsToWork;
    private double minDistanceSquared;
    private boolean nearest;
    private static Vec3[] flatCardinalDirections;
    
    public AStarCustomPathFinder(Vec3 startVec3, Vec3 endVec3) {
        this.path = new ArrayList<Vec3>();
        this.hubs = new ArrayList<Hub>();
        this.hubsToWork = new ArrayList<Hub>();
        this.minDistanceSquared = 9.0;
        this.nearest = true;
        this.startVec3 = startVec3.addVector(0.0, 0.0, 0.0).floor();
        this.endVec3 = endVec3.addVector(0.0, 0.0, 0.0).floor();
    }
    
    public ArrayList<Vec3> getPath() {
        return this.path;
    }
    
    public void compute() {
        this.compute(1000, 4);
    }
    
    public void compute(int loops, int depth) {
        this.path.clear();
        this.hubsToWork.clear();
        ArrayList<Vec3> initPath = new ArrayList<Vec3>();
        initPath.add(this.startVec3);
        this.hubsToWork.add(new Hub(this.startVec3, null, initPath, this.startVec3.squareDistanceTo(this.endVec3), 0.0, 0.0));
    Label_0339:
        for (int i = 0; i < loops; ++i) {
            Collections.sort(this.hubsToWork, new CompareHub());
            int j = 0;
            if (this.hubsToWork.size() == 0) {
                break;
            }
            for (Hub hub : new ArrayList<Hub>(this.hubsToWork)) {
                if (++j > depth) {
                    break;
                }
                this.hubsToWork.remove(hub);
                this.hubs.add(hub);
                for (Vec3 direction : AStarCustomPathFinder.flatCardinalDirections) {
                    Vec3 loc = hub.getLoc().add(direction).floor();
                    if (checkPositionValidity(loc, false) && this.addHub(hub, loc, 0.0)) {
                        break Label_0339;
                    }
                }
                Vec3 loc2 = hub.getLoc().addVector(0.0, 1.0, 0.0).floor();
                if (checkPositionValidity(loc2, false) && this.addHub(hub, loc2, 0.0)) {
                    break Label_0339;
                }
                Vec3 loc3 = hub.getLoc().addVector(0.0, -1.0, 0.0).floor();
                if (checkPositionValidity(loc3, false) && this.addHub(hub, loc3, 0.0)) {
                    break Label_0339;
                }
            }
        }
        if (this.nearest) {
            Collections.sort(this.hubs, new CompareHub());
            this.path = this.hubs.get(0).getPath();
        }
    }
    
    public static boolean checkPositionValidity(Vec3 loc, boolean checkGround) {
        return checkPositionValidity((int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), checkGround);
    }
    
    public static boolean checkPositionValidity(int x, int y, int z, boolean checkGround) {
        BlockPos block1 = new BlockPos(x, y, z);
        BlockPos block2 = new BlockPos(x, y + 1, z);
        BlockPos block3 = new BlockPos(x, y - 1, z);
        return !isBlockSolid(block1) && !isBlockSolid(block2) && (isBlockSolid(block3) || !checkGround) && isSafeToWalkOn(block3);
    }
    
    private static boolean isBlockSolid(BlockPos block) {
        return Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()).isSolidFullCube() || Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockSlab || Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockStairs || Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockCactus || Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockChest || Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockEnderChest || Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockSkull || Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockPane || Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockFence || Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockWall || Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockGlass || Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockPistonBase || Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockPistonExtension || Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockPistonMoving || Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockStainedGlass || Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockTrapDoor;
    }
    
    private static boolean isSafeToWalkOn(BlockPos block) {
        return !(Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockFence) && !(Minecraft.getMinecraft().theWorld.getBlock(block.getX(), block.getY(), block.getZ()) instanceof BlockWall);
    }
    
    public Hub isHubExisting(Vec3 loc) {
        for (Hub hub : this.hubs) {
            if (hub.getLoc().getX() == loc.getX() && hub.getLoc().getY() == loc.getY() && hub.getLoc().getZ() == loc.getZ()) {
                return hub;
            }
        }
        for (Hub hub : this.hubsToWork) {
            if (hub.getLoc().getX() == loc.getX() && hub.getLoc().getY() == loc.getY() && hub.getLoc().getZ() == loc.getZ()) {
                return hub;
            }
        }
        return null;
    }
    
    public boolean addHub(Hub parent, Vec3 loc, double cost) {
        Hub existingHub = this.isHubExisting(loc);
        double totalCost = cost;
        if (parent != null) {
            totalCost += parent.getTotalCost();
        }
        if (existingHub == null) {
            if ((loc.getX() == this.endVec3.getX() && loc.getY() == this.endVec3.getY() && loc.getZ() == this.endVec3.getZ()) || (this.minDistanceSquared != 0.0 && loc.squareDistanceTo(this.endVec3) <= this.minDistanceSquared)) {
                this.path.clear();
                (this.path = parent.getPath()).add(loc);
                return true;
            }
            ArrayList<Vec3> path = new ArrayList<Vec3>(parent.getPath());
            path.add(loc);
            this.hubsToWork.add(new Hub(loc, parent, path, loc.squareDistanceTo(this.endVec3), cost, totalCost));
        }
        else if (existingHub.getCost() > cost) {
            ArrayList<Vec3> path = new ArrayList<Vec3>(parent.getPath());
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
    
    static {
        AStarCustomPathFinder.flatCardinalDirections = new Vec3[] { new Vec3(1.0, 0.0, 0.0), new Vec3(-1.0, 0.0, 0.0), new Vec3(0.0, 0.0, 1.0), new Vec3(0.0, 0.0, -1.0) };
    }
    
    private class Hub
    {
        private Vec3 loc;
        private Hub parent;
        private ArrayList<Vec3> path;
        private double squareDistanceToFromTarget;
        private double cost;
        private double totalCost;
        
        public Hub(Vec3 loc, Hub parent, ArrayList<Vec3> path, double squareDistanceToFromTarget, double cost, double totalCost) {
            this.loc = null;
            this.parent = null;
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
    
    public class CompareHub implements Comparator<Hub>
    {
        @Override
        public int compare(Hub o1, Hub o2) {
            return (int)(o1.getSquareDistanceToFromTarget() + o1.getTotalCost() - (o2.getSquareDistanceToFromTarget() + o2.getTotalCost()));
        }
    }
}
