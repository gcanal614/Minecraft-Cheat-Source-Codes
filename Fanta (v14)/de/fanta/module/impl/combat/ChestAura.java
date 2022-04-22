/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.combat;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventBlockRightClick;
import de.fanta.events.listeners.EventPreMotion;
import de.fanta.module.Module;
import de.fanta.module.impl.combat.Killaura;
import de.fanta.utils.Rotations;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;

public class ChestAura
extends Module {
    private List<BlockPos> clickedChests = new ArrayList<BlockPos>();

    public ChestAura() {
        super("Chestaura", 0, Module.Type.Combat, Color.YELLOW);
    }

    @Override
    public void onEvent(Event event) {
        BlockPos bp;
        if (event instanceof EventPreMotion) {
            if (Killaura.hasTarget()) {
                return;
            }
            if (this.distance(this.getNearest().getPos().getX(), this.getNearest().getPos().getY(), this.getNearest().getPos().getZ(), (float)ChestAura.mc.thePlayer.posX, (float)ChestAura.mc.thePlayer.posY, (float)ChestAura.mc.thePlayer.posZ) > 3.5f) {
                return;
            }
            EventPreMotion preMotion = (EventPreMotion)event;
            TileEntityChest nearest = this.getNearest();
            ChestAura.lookAtPos((double)nearest.getPos().getX() + 0.5, (double)nearest.getPos().getY() - 0.5, (double)nearest.getPos().getZ() + 0.5);
            if (!this.clickedChests.contains(nearest.getPos())) {
                if (ChestAura.mc.currentScreen == null) {
                    mc.rightClickMouse();
                }
                ChestAura.mc.thePlayer.rotationYawHead = Rotations.yaw;
                ChestAura.mc.thePlayer.rotationPitchHead = Rotations.pitch;
                ((EventPreMotion)event).setPitch(Rotations.pitch);
                ((EventPreMotion)event).setYaw(Rotations.yaw);
            }
        }
        if (event instanceof EventBlockRightClick && (ChestAura.mc.theWorld.getBlockState(bp = ((EventBlockRightClick)event).getBlockpos()).getBlock() == Blocks.chest || ChestAura.mc.theWorld.getBlockState(bp).getBlock() == Blocks.trapped_chest || ChestAura.mc.theWorld.getBlockState(bp).getBlock() == Blocks.ender_chest)) {
            this.clickedChests.add(bp);
        }
    }

    public TileEntityChest getNearest() {
        TileEntityChest nearest = null;
        for (TileEntity te : ChestAura.mc.theWorld.loadedTileEntityList) {
            if (!(te instanceof TileEntityChest)) continue;
            if (nearest == null) {
                nearest = (TileEntityChest)te;
                continue;
            }
            if (!(this.distance(nearest.getPos().getX(), nearest.getPos().getY(), nearest.getPos().getZ(), (float)ChestAura.mc.thePlayer.posX, (float)ChestAura.mc.thePlayer.posY, (float)ChestAura.mc.thePlayer.posZ) > this.distance(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ(), (float)ChestAura.mc.thePlayer.posX, (float)ChestAura.mc.thePlayer.posY, (float)ChestAura.mc.thePlayer.posZ))) continue;
            nearest = (TileEntityChest)te;
        }
        return nearest;
    }

    public float distance(float x1, float y1, float z1, float x2, float y2, float z2) {
        float dis = (float)Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1));
        return dis;
    }

    public static void lookAtPos(double x, double y, double z) {
        double dirx = ChestAura.mc.thePlayer.posX - x;
        double diry = ChestAura.mc.thePlayer.posY - y;
        double dirz = ChestAura.mc.thePlayer.posZ - z;
        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        float yaw = (float)Math.atan2(dirz /= len, dirx /= len);
        float pitch = (float)Math.asin(diry /= len);
        pitch = (float)((double)pitch * 180.0 / Math.PI);
        yaw = (float)((double)yaw * 180.0 / Math.PI);
        yaw = (float)((double)yaw + 90.0);
        float f2 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= yaw % f3;
        pitch -= pitch % (f3 * f2);
        Rotations.setYaw(yaw, 180.0f);
        Rotations.setPitch(pitch, 90.0f);
    }
}

