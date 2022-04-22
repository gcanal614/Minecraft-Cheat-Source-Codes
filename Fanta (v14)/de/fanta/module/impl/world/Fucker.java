/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.world;

import de.fanta.events.Event;
import de.fanta.events.listeners.EventPreMotion;
import de.fanta.events.listeners.EventReceivedPacket;
import de.fanta.module.Module;
import de.fanta.module.impl.combat.TestAura;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.Slider;
import de.fanta.utils.Rotations;
import de.fanta.utils.TimeUtil;
import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockCake;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.BlockOre;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Fucker
extends Module {
    public static BlockPos pos;
    TimeUtil time = new TimeUtil();
    double delaY;
    double range;

    public Fucker() {
        super("Fucker", 0, Module.Type.World, Color.red);
        this.settings.add(new Setting("Delay", new Slider(1.0, 5000.0, 1.0, 2.0)));
        this.settings.add(new Setting("Range", new Slider(1.0, 4.5, 1.0, 4.5)));
        this.settings.add(new Setting("Rotations", new CheckBox(false)));
        this.settings.add(new Setting("NoSwing", new CheckBox(false)));
    }

    @Override
    public void onEvent(Event event) {
        if (TestAura.target == null) {
            Block block;
            int posZ;
            int posY;
            int posX;
            int z;
            int x;
            int y;
            if (event instanceof EventReceivedPacket) {
                this.range = ((Slider)this.getSetting((String)"Range").getSetting()).curValue;
                y = (int)this.range;
                while ((double)y >= -this.range) {
                    x = (int)(-this.range);
                    while ((double)x <= this.range) {
                        z = (int)(-this.range);
                        while ((double)z <= this.range) {
                            posX = (int)(Fucker.mc.thePlayer.posX - 0.5 + (double)x);
                            posY = (int)(Fucker.mc.thePlayer.posY - 0.5 + (double)y);
                            posZ = (int)(Fucker.mc.thePlayer.posZ - 0.5 + (double)z);
                            pos = new BlockPos(posX, posY, posZ);
                            block = Fucker.mc.theWorld.getBlockState(pos).getBlock();
                            if (block instanceof BlockBed || block instanceof BlockCake || block instanceof BlockDragonEgg) {
                                PlayerControllerMP playerController = Fucker.mc.playerController;
                                long timeLeft = (long)(PlayerControllerMP.curBlockDamageMP / 2.0f);
                                this.delaY = ((Slider)this.getSetting((String)"Delay").getSetting()).curValue;
                                if (this.time.hasReached((long)this.delaY)) {
                                    this.time.reset();
                                    if (((CheckBox)this.getSetting((String)"NoSwing").getSetting()).state) {
                                        Fucker.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                                    } else {
                                        Fucker.mc.thePlayer.swingItem();
                                    }
                                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.DOWN));
                                    mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.DOWN));
                                    mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
                                }
                            }
                            ++z;
                        }
                        ++x;
                    }
                    --y;
                }
            }
            if (event instanceof EventPreMotion) {
                y = 4;
                while ((float)y >= -4.5f) {
                    x = -4;
                    while ((float)x <= 4.5f) {
                        z = -4;
                        while ((float)z <= 4.5f) {
                            posX = (int)(Fucker.mc.thePlayer.posX - 0.5 + (double)x);
                            posY = (int)(Fucker.mc.thePlayer.posY - 0.5 + (double)y);
                            posZ = (int)(Fucker.mc.thePlayer.posZ - 0.5 + (double)z);
                            pos = new BlockPos(posX, posY, posZ);
                            block = Fucker.mc.theWorld.getBlockState(pos).getBlock();
                            if (block instanceof BlockBed || block instanceof BlockCake || block instanceof BlockOre) {
                                Fucker.mc.thePlayer.rotationYawHead = Rotations.yaw;
                                Fucker.mc.thePlayer.rotationPitchHead = Rotations.pitch;
                                ((EventPreMotion)event).setPitch(Rotations.pitch);
                                ((EventPreMotion)event).setYaw(Rotations.yaw);
                                Fucker.lookAtPos((double)pos.getX() + 0.5, (double)pos.getY() - 0.5, (double)pos.getZ() + 0.5);
                            }
                            ++z;
                        }
                        ++x;
                    }
                    --y;
                }
            }
        }
    }

    public boolean isRotations() {
        return (Boolean)this.getSetting("Rotations").getSetting().getValue();
    }

    public static void lookAtPos(double x, double y, double z) {
        double dirx = Fucker.mc.thePlayer.posX - x;
        double diry = Fucker.mc.thePlayer.posY - y;
        double dirz = Fucker.mc.thePlayer.posZ - z;
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

