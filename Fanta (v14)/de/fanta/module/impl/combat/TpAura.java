/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.combat;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventUpdate;
import de.fanta.module.Module;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.Slider;
import de.fanta.utils.FriendSystem;
import de.fanta.utils.PathFinder;
import de.fanta.utils.TimeUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.Vec3;

public class TpAura
extends Module {
    TimeUtil time = new TimeUtil();
    public double delay;

    public TpAura() {
        super("TpAura", 0, Module.Type.Combat, Color.yellow);
        this.settings.add(new Setting("Delay", new Slider(0.0, 5000.0, 1.0, 50.0)));
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventUpdate) {
            this.delay = ((Slider)this.getSetting((String)"Delay").getSetting()).curValue;
            if (this.time.hasReached((long)this.delay)) {
                this.attack(this.modes());
                this.time.reset();
            }
        }
    }

    public Entity modes() {
        EntityPlayer target = null;
        for (EntityPlayer entity : TpAura.mc.theWorld.playerEntities) {
            if (entity == TpAura.mc.thePlayer || target != null && !(entity.getDistanceToEntity(TpAura.mc.thePlayer) < target.getDistanceToEntity(TpAura.mc.thePlayer))) continue;
            target = entity;
            System.out.println(entity);
        }
        return target;
    }

    public void attack(Entity entity) {
        if (entity == null) {
            return;
        }
        List<Vec3> path = TpAura.calculatePath(TpAura.mc.thePlayer.getPositionVector(), entity.getPositionVector());
        for (Vec3 pos : path) {
            if (FriendSystem.isFriendString(entity.getName())) {
                return;
            }
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pos.xCoord, pos.yCoord, pos.zCoord, false));
        }
        TpAura.mc.thePlayer.swingItem();
        TpAura.mc.playerController.attackEntity(TpAura.mc.thePlayer, entity);
        TpAura.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
        Collections.reverse(path);
        for (Vec3 pos : path) {
            TpAura.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pos.xCoord, pos.yCoord, pos.zCoord, false));
        }
    }

    public static List<Vec3> calculatePath(Vec3 startPos, Vec3 endPos) {
        System.out.println("Test-1");
        PathFinder pathfinder = new PathFinder(startPos, endPos);
        System.out.println("Test");
        pathfinder.calculatePath(5000);
        System.out.println("Test2");
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
            } else {
                boolean canContinue = true;
                if (pathElm.squareDistanceTo(lastDashLoc) > 30.0) {
                    canContinue = false;
                } else {
                    double smallX = Math.min(lastDashLoc.xCoord, pathElm.xCoord);
                    double smallY = Math.min(lastDashLoc.yCoord, pathElm.yCoord);
                    double smallZ = Math.min(lastDashLoc.zCoord, pathElm.zCoord);
                    double bigX = Math.max(lastDashLoc.xCoord, pathElm.xCoord);
                    double bigY = Math.max(lastDashLoc.yCoord, pathElm.yCoord);
                    double bigZ = Math.max(lastDashLoc.zCoord, pathElm.zCoord);
                    int x = (int)smallX;
                    block1: while ((double)x <= bigX) {
                        int y = (int)smallY;
                        while ((double)y <= bigY) {
                            int z = (int)smallZ;
                            while ((double)z <= bigZ) {
                                if (!PathFinder.checkPositionValidity(x, y, z, false)) {
                                    canContinue = false;
                                    break block1;
                                }
                                ++z;
                            }
                            ++y;
                        }
                        ++x;
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
}

