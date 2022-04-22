/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.movement;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventTick;
import de.fanta.module.Module;
import de.fanta.module.impl.movement.Speed;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.setting.settings.Slider;
import java.awt.Color;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class Step
extends Module {
    public static double StepHight;
    private boolean notGround = false;

    public Step() {
        super("Step", 0, Module.Type.Movement, Color.GREEN);
        this.settings.add(new Setting("Modes", new DropdownBox("Vanilla", new String[]{"Vanilla", "Intave", "NCP", "AAC 3.3.13", "Legit", "Spartan"})));
        this.settings.add(new Setting("StepHight", new Slider(1.0, 90.0, 0.1, 4.0)));
    }

    @Override
    public void onDisable() {
        Step.mc.thePlayer.stepHeight = 0.5f;
    }

    @Override
    public void onEvent(Event event) {
        block33: {
            if (!(event instanceof EventTick) || !Step.mc.thePlayer.isCollidedHorizontally) break block33;
            double yaw = Math.toRadians(Step.mc.thePlayer.rotationYaw);
            switch (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption) {
                case "Vanilla": {
                    StepHight = ((Slider)this.getSetting((String)"StepHight").getSetting()).curValue;
                    Step.mc.thePlayer.stepHeight = (float)StepHight;
                    break;
                }
                case "Intave": {
                    if (Step.mc.thePlayer.onGround) {
                        Step.mc.thePlayer.jump();
                    } else if (Step.mc.thePlayer.motionY < 0.03) {
                        Step.mc.thePlayer.motionY += 0.08;
                    }
                    if (!(Step.mc.thePlayer.motionY > 0.03)) break;
                    Step.mc.thePlayer.setPosition(Step.mc.thePlayer.posX, Step.mc.thePlayer.posY - 0.001, Step.mc.thePlayer.posZ);
                    Step.mc.thePlayer.onGround = true;
                    Step.mc.thePlayer.motionX = -Math.sin(yaw) * 0.14;
                    Step.mc.thePlayer.motionZ = Math.cos(yaw) * 0.14;
                    break;
                }
                case "Spartan": {
                    StepHight = ((Slider)this.getSetting((String)"StepHight").getSetting()).curValue;
                    Step.mc.thePlayer.stepHeight = (float)StepHight;
                    Step.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                    Step.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(this.getX(), this.getY() - 1.5, this.getZ()), 1, new ItemStack(Blocks.stone.getItem(Step.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
                    break;
                }
                case "AAC 3.3.13": {
                    if (Client.INSTANCE.moduleManager.getModule("Speed").isState()) break;
                    if (Step.mc.thePlayer.onGround) {
                        Step.mc.thePlayer.jump();
                    } else if (Step.mc.thePlayer.motionY < 0.03) {
                        Step.mc.thePlayer.motionY += 0.08;
                    }
                    if (!(Step.mc.thePlayer.motionY > 0.03)) break;
                    Step.mc.thePlayer.setPosition(Step.mc.thePlayer.posX, Step.mc.thePlayer.posY - 0.001, Step.mc.thePlayer.posZ);
                    Step.mc.thePlayer.onGround = true;
                    Step.mc.thePlayer.motionX = -Math.sin(yaw) * -0.05;
                    Step.mc.thePlayer.motionZ = Math.cos(yaw) * -0.05;
                    break;
                }
                case "Legit": {
                    Step.mc.thePlayer.jump();
                    break;
                }
                case "NCP": {
                    if (Step.mc.thePlayer.isCollidedHorizontally && Step.mc.thePlayer.isMoving()) {
                        if (Step.mc.thePlayer.onGround) {
                            Step.mc.thePlayer.motionY += 0.45;
                            this.notGround = false;
                        }
                    } else {
                        Step.mc.thePlayer.stepHeight = 0.6f;
                        if (!this.notGround) {
                            if ((double)Step.mc.thePlayer.fallDistance >= 0.5) {
                                this.notGround = false;
                                return;
                            }
                            Step.mc.thePlayer.motionY += -10.0;
                            this.notGround = true;
                        }
                    }
                    Step.mc.thePlayer.stepHeight = 0.5f;
                    Speed.setSpeed(0.4);
                }
            }
        }
    }

    public double getX() {
        return this.getPlayer().posX;
    }

    public double getY() {
        return this.getPlayer().posY;
    }

    public double getZ() {
        return this.getPlayer().posZ;
    }

    public static void sendPacketUnlogged(Packet<? extends INetHandler> packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }
}

