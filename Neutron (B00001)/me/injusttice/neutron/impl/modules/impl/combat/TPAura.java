package me.injusttice.neutron.impl.modules.impl.combat;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.api.events.impl.EventRender3D;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.utils.network.PacketUtil;
import me.injusttice.neutron.utils.world.Vec3;
import me.injusttice.neutron.utils.movement.RotationUtils;
import me.injusttice.neutron.utils.pathfinding.AStarCustomPathFinder;
import me.injusttice.neutron.utils.player.Timer;
import me.injusttice.neutron.utils.render.Render2DUtils;
import me.injusttice.neutron.utils.render.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.entity.item.EntityArmorStand;
import java.awt.Color;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.client.renderer.entity.RenderManager;
import java.util.Collections;

import net.minecraft.network.play.client.C03PacketPlayer;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.entity.EntityLivingBase;

import java.util.List;
import java.util.ArrayList;

public class TPAura extends Module {
    
    public DoubleSet cps = new DoubleSet("APS", 5.0, 1.0, 20.0, 1.0);
    public DoubleSet maxTargets= new DoubleSet("Max Targets", 1.0, 1.0, 5.0, 1.0);
    public DoubleSet range = new DoubleSet("Range", 300.0, 50.0, 300.0, 10.0);
    public BooleanSet keepSprint = new BooleanSet("Keep Sprint", false);
    private ArrayList<Vec3> path;
    private List<Vec3>[] test;
    private List<EntityLivingBase> targets;
    private Timer cpstimer;
    public static Timer timer;
    public static boolean canReach;
    int ticks;
    double startX;
    double startY;
    double startZ;
    private float lastHealth;
    double dashDistance;
    
    public TPAura() {
        super("TPAura", 0, Category.COMBAT);
        addSettings(cps, range, maxTargets, keepSprint);
        path = new ArrayList<>();
        test = (List<Vec3>[])new ArrayList[50];
        targets = new CopyOnWriteArrayList<>();
        cpstimer = new Timer();
        ticks = 0;
        lastHealth = 0.0f;
        dashDistance = 5.0;
    }
    
    @EventTarget
    public void onMotion(EventMotion e){
        if(e.isPre()) {
            targets = getTargets();
            if (cpstimer.hasTimeElapsed((long)(1000.0 / cps.getValue()), true) && targets.size() > 0) {
                test = (List<Vec3>[])new ArrayList[50];
                for (int i = 0; i < ((targets.size() > 1) ? maxTargets.getValue() : targets.size()); ++i) {
                    EntityLivingBase T = targets.get(i);
                    if (mc.thePlayer.getDistanceToEntity(T) > range.getValue()) {
                        return;
                    }
                    Vec3 topFrom = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                    Vec3 to = new Vec3(T.posX, T.posY, T.posZ);
                    path = computePath(topFrom, to);
                    test[i] = path;
                    for (Vec3 pathElm : path) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), true));
                    }
                    mc.thePlayer.swingItem();
                    if(!keepSprint.isEnabled()) {
                        mc.playerController.attackEntity(mc.thePlayer, T);
                    } else {
                        PacketUtil.sendPacket(new C02PacketUseEntity(T, C02PacketUseEntity.Action.ATTACK));
                    }
                    Collections.reverse(path);
                    for (Vec3 pathElm : path) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pathElm.getX(), pathElm.getY(), pathElm.getZ(), true));
                    }
                    float[] rots = RotationUtils.getRotations(T);
                    e.setYaw(rots[0]);
                    e.setPitch(rots[1]);
                }
                cpstimer.reset();
            }
        }
    }

    @EventTarget
    public void onRender(EventRender3D e3) {
        if(targets.size() > 0) {
            for (int j = 0; j < path.size(); ++j) {
                Vec3 pathElm2 = path.get(j);
                Vec3 pathOther = path.get((j < path.size() - 1) ? (j + 1) : j);
                double n = pathElm2.getX() + (pathElm2.getX() - pathElm2.getX()) * mc.timer.renderPartialTicks;
                mc.getRenderManager();
                double x = n - RenderManager.renderPosX;
                    double n2 = pathElm2.getY() + (pathElm2.getY() - pathElm2.getY()) * mc.timer.renderPartialTicks;
                mc.getRenderManager();
                double y = n2 - RenderManager.renderPosY;
                double n3 = pathElm2.getZ() + (pathElm2.getZ() - pathElm2.getZ()) * mc.timer.renderPartialTicks;
                mc.getRenderManager();
                double z = n3 - RenderManager.renderPosZ;
                double n4 = pathOther.getX() + (pathOther.getX() - pathOther.getX()) * mc.timer.renderPartialTicks;
                mc.getRenderManager();
                double x2 = n4 - RenderManager.renderPosX;
                double n5 = pathOther.getY() + (pathOther.getY() - pathOther.getY()) * mc.timer.renderPartialTicks;
                mc.getRenderManager();
                double y2 = n5 - RenderManager.renderPosY;
                double n6 = pathOther.getZ() + (pathOther.getZ() - pathOther.getZ()) * mc.timer.renderPartialTicks;
                mc.getRenderManager();
                double z2 = n6 - RenderManager.renderPosZ;
                AxisAlignedBB var12 = new AxisAlignedBB(x2, y2, z2, x, y, z);
                Render2DUtils.glColor(new Color(255, 255, 255).getRGB());
                RenderUtil.drawOutlinedBoundingBox(var12);
            }
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        startX = mc.thePlayer.posX;
        startY = mc.thePlayer.posY;
        startZ = mc.thePlayer.posZ;
    }
    
    public boolean canAttack(EntityLivingBase player) {
        return player != mc.thePlayer && !(player instanceof EntityArmorStand) && player != null;
    }
    
    private List<EntityLivingBase> getTargets() {
        List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
        for (Object o3 : mc.theWorld.getLoadedEntityList()) {
            if (o3 instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase)o3;
                if (!canAttack(entity)) {
                    continue;
                }
                targets.add(entity);
            }
        }
        targets.sort((o1, o2) -> (int)(o1.getDistanceToEntity(mc.thePlayer) * 1000.0f - o2.getDistanceToEntity(mc.thePlayer) * 1000.0f));
        return targets;
    }
    
    private ArrayList<Vec3> computePath(Vec3 topFrom, Vec3 to) {
        if (!canPassThrow(new BlockPos(topFrom.mc()))) {
            topFrom = topFrom.addVector(0.0, 1.0, 0.0);
        }
        AStarCustomPathFinder pathfinder = new AStarCustomPathFinder(topFrom, to);
        pathfinder.compute();
        int i = 0;
        Vec3 lastLoc = null;
        Vec3 lastDashLoc = null;
        ArrayList<Vec3> path = new ArrayList<Vec3>();
        ArrayList<Vec3> pathFinderPath = pathfinder.getPath();
        for (Vec3 pathElm : pathFinderPath) {
            if (i == 0 || i == pathFinderPath.size() - 1) {
                if (lastLoc != null) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                }
                path.add(pathElm.addVector(0.5, 0.0, 0.5));
                lastDashLoc = pathElm;
            }
            else {
                boolean canContinue = true;
                Label_0356: {
                    if (pathElm.squareDistanceTo(lastDashLoc) > dashDistance * dashDistance) {
                        canContinue = false;
                    }
                    else {
                        double smallX = Math.min(lastDashLoc.getX(), pathElm.getX());
                        double smallY = Math.min(lastDashLoc.getY(), pathElm.getY());
                        double smallZ = Math.min(lastDashLoc.getZ(), pathElm.getZ());
                        double bigX = Math.max(lastDashLoc.getX(), pathElm.getX());
                        double bigY = Math.max(lastDashLoc.getY(), pathElm.getY());
                        double bigZ = Math.max(lastDashLoc.getZ(), pathElm.getZ());
                        for (int x = (int)smallX; x <= bigX; ++x) {
                            for (int y = (int)smallY; y <= bigY; ++y) {
                                for (int z = (int)smallZ; z <= bigZ; ++z) {
                                    if (!AStarCustomPathFinder.checkPositionValidity(x, y, z, false)) {
                                        canContinue = false;
                                        break Label_0356;
                                    }
                                }
                            }
                        }
                    }
                }
                if (!canContinue) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                    lastDashLoc = lastLoc;
                }
            }
            lastLoc = pathElm;
            ++i;
        }
        return path;
    }
    
    private boolean canPassThrow(BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock();
        return block.getMaterial() == Material.air || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine || block == Blocks.ladder || block == Blocks.water || block == Blocks.flowing_water || block == Blocks.wall_sign || block == Blocks.standing_sign;
    }
    
    static {
        timer = new Timer();
    }
}
