/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.movement;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventRender3D;
import de.fanta.events.listeners.EventUpdate;
import de.fanta.module.Module;
import de.fanta.utils.ChatUtil;
import de.fanta.utils.PathFinder;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class Teleport
extends Module {
    private BlockPos pos = null;
    private boolean teleported;
    private List<Vec3> positions;

    public Teleport() {
        super("Teleport", 0, Module.Type.Movement, Color.orange);
    }

    @Override
    public void onEnable() {
        this.teleported = false;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        double blockX = (double)Teleport.mc.objectMouseOver.getBlockPos().getX() + 1.5;
        double blockY = Teleport.mc.objectMouseOver.getBlockPos().getY() + 1;
        double blockZ = (double)Teleport.mc.objectMouseOver.getBlockPos().getZ() + 1.5;
        this.pos = new BlockPos(0, 0, 0);
        super.onDisable();
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate && Teleport.mc.gameSettings.keyBindAttack.isKeyDown() && Teleport.mc.objectMouseOver != null && Teleport.mc.objectMouseOver.getBlockPos() != null) {
            double blockX = (double)Teleport.mc.objectMouseOver.getBlockPos().getX() + 1.5;
            double blockY = Teleport.mc.objectMouseOver.getBlockPos().getY() + 1;
            double blockZ = (double)Teleport.mc.objectMouseOver.getBlockPos().getZ() + 1.5;
            this.pos = new BlockPos(blockX, blockY, blockZ);
            if (!Teleport.mc.gameSettings.keyBindAttack.isKeyDown() || !Teleport.mc.gameSettings.keyBindSneak.pressed) {
                Teleport.mc.timer.timerSpeed = 1.0f;
            }
            ChatUtil.sendChatMessageWithPrefix("Selected position: " + blockX + " " + blockY + " " + blockZ);
            if (Teleport.mc.gameSettings.keyBindSneak.pressed && !this.teleported) {
                Teleport.mc.thePlayer.onGround = true;
                this.teleported = true;
                this.positions = this.calculatePath(new Vec3(Teleport.mc.thePlayer.posX, Teleport.mc.thePlayer.posY, Teleport.mc.thePlayer.posZ), new Vec3(this.pos.getX(), this.pos.getY(), this.pos.getZ()));
                this.positions.add(new Vec3(this.pos.getX(), this.pos.getY(), this.pos.getZ()));
                ChatUtil.sendChatMessage("" + this.positions.size());
                for (Vec3 vec : this.positions) {
                    mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(vec.xCoord, vec.yCoord, vec.zCoord, true));
                }
                Client.INSTANCE.moduleManager.getModule("Teleport").setState(false);
                int i = 0;
                while (i < 5) {
                    Teleport.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                    ++i;
                }
            }
        }
        boolean cfr_ignored_0 = event instanceof EventRender3D;
    }

    private List<Vec3> calculatePath(Vec3 startPos, Vec3 endPos) {
        PathFinder pathfinder = new PathFinder(startPos, endPos);
        pathfinder.calculatePath(5000);
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
                if (pathElm.squareDistanceTo(lastDashLoc) > 15.0) {
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

