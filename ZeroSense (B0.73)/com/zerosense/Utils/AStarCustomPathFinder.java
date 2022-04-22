package com.zerosense.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.minecraft.client.Minecraft;

public final class AStarCustomPathFinder {
   private static final Minecraft MC = Minecraft.getMinecraft();
   private static final CustomVec3[] flatCardinalDirections = new CustomVec3[]{new CustomVec3(1.0D, 0.0D, 0.0D), new CustomVec3(-1.0D, 0.0D, 0.0D), new CustomVec3(0.0D, 0.0D, 1.0D), new CustomVec3(0.0D, 0.0D, -1.0D)};
   private final CustomVec3 startCustomVec3;
   private final CustomVec3 endCustomVec3;
   private final ArrayList hubs = new ArrayList();
   private final ArrayList hubsToWork = new ArrayList();
   private ArrayList path = new ArrayList();

   public AStarCustomPathFinder(CustomVec3 startCustomVec3, CustomVec3 endCustomVec3) {
      this.startCustomVec3 = startCustomVec3.addVector(0.0D, 0.0D, 0.0D).floor();
      this.endCustomVec3 = endCustomVec3.addVector(0.0D, 0.0D, 0.0D).floor();
   }

   public ArrayList getPath() {
      return this.path;
   }

   public void compute() {
      this.compute(1000, 4);
   }

   public void compute(int loops, int depth) {
      this.path.clear();
      this.hubsToWork.clear();
      ArrayList initPath = new ArrayList();
      CustomVec3 startCustomVec3 = this.startCustomVec3;
      initPath.add(startCustomVec3);
      CustomVec3[] flatCardinalDirections = AStarCustomPathFinder.flatCardinalDirections;
      this.hubsToWork.add(new Hub(startCustomVec3, (Hub)null, initPath, startCustomVec3.squareDistanceTo(this.endCustomVec3), 0.0D, 0.0D));

      label55:
      for(int i = 0; i < loops; ++i) {
         ArrayList hubsToWork = this.hubsToWork;
         int hubsToWorkSize = hubsToWork.size();
         hubsToWork.sort(new CompareHub());
         int j = 0;
         if (hubsToWorkSize == 0) {
            break;
         }

         for(int i1 = 0; i1 < hubsToWorkSize; ++i1) {
            Hub hub = (Hub)hubsToWork.get(i1);
            ++j;
            if (j > depth) {
               break;
            }

            hubsToWork.remove(hub);
            this.hubs.add(hub);
            CustomVec3 hLoc = hub.getLoc();
            int i2 = 0;
         }
      }

      this.hubs.sort(new CompareHub());
      this.path = ((Hub)this.hubs.get(0)).getPath();
   }

   public Hub isHubExisting(CustomVec3 loc) {
      List hubs = this.hubs;

      int i;
      for(i = hubs.size(); i < i; ++i) {
         Hub hub = (Hub)hubs.get(i);
         CustomVec3 hubLoc = hub.getLoc();
         if (hubLoc.getX() == loc.getX() && hubLoc.getY() == loc.getY() && hubLoc.getZ() == loc.getZ()) {
            return hub;
         }
      }

      List hubsToWork = this.hubsToWork;
      i = 0;

      for(int hubsToWorkSize = hubsToWork.size(); i < hubsToWorkSize; ++i) {
         Hub hub = (Hub)hubsToWork.get(i);
         CustomVec3 hubLoc = hub.getLoc();
         if (hubLoc.getX() == loc.getX() && hubLoc.getY() == loc.getY() && hubLoc.getZ() == loc.getZ()) {
            return hub;
         }
      }

      return null;
   }

   public boolean addHub(Hub parent, CustomVec3 loc, double cost) {
      Hub existingHub = this.isHubExisting(loc);
      double totalCost = cost;
      if (parent != null) {
         totalCost = cost + parent.getTotalCost();
      }

      CustomVec3 endCustomVec3 = this.endCustomVec3;
      ArrayList parentPath = parent.getPath();
      if (existingHub == null) {
         if (loc.getX() == endCustomVec3.getX() && loc.getY() == endCustomVec3.getY() && loc.getZ() == endCustomVec3.getZ()) {
            this.path.clear();
            this.path = parentPath;
            this.path.add(loc);
            return true;
         }

         parentPath.add(loc);
         this.hubsToWork.add(new Hub(loc, parent, parentPath, loc.squareDistanceTo(endCustomVec3), cost, totalCost));
      } else if (existingHub.getCost() > cost) {
         parentPath.add(loc);
         existingHub.setLoc(loc);
         existingHub.setParent(parent);
         existingHub.setPath(parentPath);
         existingHub.setSquareDistanceToFromTarget(loc.squareDistanceTo(endCustomVec3));
         existingHub.setCost(cost);
         existingHub.setTotalCost(totalCost);
      }

      return false;
   }

   public static class CompareHub implements Comparator {
      public int compare(Hub o1, Hub o2) {
         return (int)(o1.getSquareDistanceToFromTarget() + o1.getTotalCost() - (o2.getSquareDistanceToFromTarget() + o2.getTotalCost()));
      }

      @Override
      public int compare(Object o1, Object o2) {
         return 0;
      }
   }

   private static class Hub {
      private CustomVec3 loc;
      private Hub parent;
      private ArrayList path;
      private double squareDistanceToFromTarget;
      private double cost;
      private double totalCost;

      public Hub(CustomVec3 loc, Hub parent, ArrayList path, double squareDistanceToFromTarget, double cost, double totalCost) {
         this.loc = loc;
         this.parent = parent;
         this.path = path;
         this.squareDistanceToFromTarget = squareDistanceToFromTarget;
         this.cost = cost;
         this.totalCost = totalCost;
      }

      public CustomVec3 getLoc() {
         return this.loc;
      }

      public void setLoc(CustomVec3 loc) {
         this.loc = loc;
      }

      public Hub getParent() {
         return this.parent;
      }

      public void setParent(Hub parent) {
         this.parent = parent;
      }

      public ArrayList getPath() {
         return this.path;
      }

      public void setPath(ArrayList path) {
         this.path = path;
      }

      public double getSquareDistanceToFromTarget() {
         return this.squareDistanceToFromTarget;
      }

      public void setSquareDistanceToFromTarget(double squareDistanceToFromTarget) {
         this.squareDistanceToFromTarget = squareDistanceToFromTarget;
      }

      public double getCost() {
         return this.cost;
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
}
