/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.movement;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventReceivedPacket;
import de.fanta.events.listeners.EventTick;
import de.fanta.events.listeners.PlayerMoveEvent;
import de.fanta.module.Module;
import de.fanta.module.impl.movement.Speed;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.utils.ChatUtil;
import de.fanta.utils.TimeUtil;
import java.awt.Color;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Longjump
extends Module {
    public static int disable = 0;
    boolean cubedmg = false;
    private int stage = 1;
    TimeUtil time = new TimeUtil();

    public Longjump() {
        super("Longjump", 0, Module.Type.Movement, Color.orange);
        this.settings.add(new Setting("Boost", new CheckBox(false)));
        this.settings.add(new Setting("LongjumpMode", new DropdownBox("Verus", new String[]{"Verus", "Watchdog", "WatchdogFloat", "Cubecraft"})));
    }

    /*
     * Exception decompiling
     */
    @Override
    public void onEnable() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Can't sort instructions [@NONE, blocks:[4] lbl36 : CaseStatement: default:\u000a, @NONE, blocks:[4] lbl36 : CaseStatement: default:\u000a]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.CompareByIndex.compare(CompareByIndex.java:25)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.CompareByIndex.compare(CompareByIndex.java:8)
         *     at java.util.TimSort.countRunAndMakeAscending(TimSort.java:360)
         *     at java.util.TimSort.sort(TimSort.java:220)
         *     at java.util.Arrays.sort(Arrays.java:1512)
         *     at java.util.ArrayList.sort(ArrayList.java:1464)
         *     at java.util.Collections.sort(Collections.java:177)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.buildSwitchCases(SwitchReplacer.java:271)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitch(SwitchReplacer.java:258)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:66)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:517)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public void onDisable() {
        disable = 0;
        Longjump.mc.timer.timerSpeed = 1.0f;
        this.cubedmg = false;
        super.onDisable();
    }

    @Override
    public void onEvent(Event event) {
        Packet p;
        switch (((DropdownBox)this.getSetting((String)"LongjumpMode").getSetting()).curOption) {
            case "Verus": {
                if (Client.INSTANCE.moduleManager.getModule("Speed").isState()) break;
                if (Longjump.mc.thePlayer.onGround) {
                    Longjump.mc.thePlayer.motionY = 0.42f;
                    Longjump.mc.thePlayer.jumpMovementFactor = 0.5f;
                    Longjump.setSpeed(6.0);
                    break;
                }
                Longjump.mc.timer.timerSpeed = 1.0f;
                int cfr_ignored_0 = Longjump.mc.thePlayer.ticksExisted % 5;
                break;
            }
            case "Cubecraft": {
                if (Longjump.mc.thePlayer.hurtTime > 0) {
                    this.cubedmg = true;
                }
                if (!this.cubedmg) {
                    Longjump.mc.timer.timerSpeed = 1.0f;
                    Longjump.setSpeed(-0.12);
                    Longjump.mc.gameSettings.keyBindForward.pressed = false;
                    Longjump.mc.gameSettings.keyBindBack.pressed = false;
                    Longjump.mc.gameSettings.keyBindLeft.pressed = false;
                    Longjump.mc.gameSettings.keyBindRight.pressed = false;
                } else {
                    Longjump.mc.gameSettings.keyBindForward.pressed = true;
                }
                if (!this.cubedmg) break;
                float cfr_ignored_1 = Longjump.mc.thePlayer.fallDistance;
                if (Longjump.mc.thePlayer.onGround) {
                    Longjump.mc.timer.timerSpeed = 0.1f;
                    Longjump.mc.thePlayer.motionY = 0.42f;
                } else {
                    Longjump.mc.timer.timerSpeed = 0.7f;
                }
                if (Longjump.mc.thePlayer.hurtTime != 0 && Longjump.mc.thePlayer.fallDistance > 0.0f) {
                    Longjump.mc.thePlayer.motionY += (double)0.004f;
                    Speed.setSpeed(0.42);
                }
                boolean cfr_ignored_2 = Longjump.mc.thePlayer.onGround;
                break;
            }
            case "Watchdog": {
                boolean boost;
                if (!(event instanceof EventTick)) break;
                if (Longjump.mc.thePlayer.onGround) {
                    NetHandlerPlayClient netHandler = mc.getMinecraft().getNetHandler();
                    EntityPlayerSP player = Longjump.mc.getMinecraft().thePlayer;
                    double x1 = player.posX;
                    double y1 = player.posY;
                    double z1 = player.posZ;
                    int i = 0;
                    while ((double)i < (double)Longjump.getMaxFallDist() / 0.05510000046342611) {
                        netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x1, y1 + (double)0.0601f, z1, false));
                        netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x1, y1 + (double)5.0E-4f, z1, false));
                        netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x1, y1 + (double)0.005f + 6.01000003516674E-8, z1, false));
                        ++i;
                    }
                    netHandler.addToSendQueue(new C03PacketPlayer(true));
                }
                Longjump.mc.thePlayer.jumpMovementFactor = 0.025f;
                boolean bl = boost = Math.abs(Longjump.mc.thePlayer.rotationYawHead - Longjump.mc.thePlayer.rotationYaw) < 90.0f;
                if ((Longjump.mc.gameSettings.keyBindForward.pressed || Longjump.mc.gameSettings.keyBindLeft.pressed || Longjump.mc.gameSettings.keyBindRight.pressed || Longjump.mc.gameSettings.keyBindBack.pressed) && Longjump.mc.thePlayer.onGround) {
                    Longjump.mc.thePlayer.motionY = 0.42f;
                    Longjump.mc.timer.timerSpeed = 0.1f;
                    Longjump.mc.thePlayer.setSprinting(true);
                } else {
                    Longjump.mc.thePlayer.setSprinting(true);
                    Longjump.mc.timer.timerSpeed = 1.0f;
                    if (Longjump.mc.thePlayer.hurtTime != 0) {
                        Longjump.mc.timer.timerSpeed = ((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Longjump").getSetting((String)"Boost").getSetting()).state ? 4.0f : 1.0f;
                        Longjump.mc.thePlayer.motionY += (double)0.02f;
                        if ((double)Longjump.mc.thePlayer.fallDistance > 0.1) {
                            Longjump.mc.thePlayer.motionY = 0.42f;
                        }
                        if (Longjump.mc.thePlayer.fallDistance > 0.0f) {
                            Longjump.mc.thePlayer.motionY = 0.25;
                        }
                        Speed.setSpeed5(0.45);
                    }
                }
                if (Longjump.mc.thePlayer.onGround) {
                    ++disable;
                }
                if (disable <= 1) break;
                Client.INSTANCE.moduleManager.getModule("Longjump").setState(false);
                Longjump.mc.timer.timerSpeed = 1.0f;
                break;
            }
            case "WatchdogFloat": {
                boolean boost;
                if (!(event instanceof EventTick)) break;
                if (Longjump.mc.thePlayer.onGround) {
                    NetHandlerPlayClient netHandler = mc.getMinecraft().getNetHandler();
                    EntityPlayerSP player = Longjump.mc.getMinecraft().thePlayer;
                    double x1 = player.posX;
                    double y1 = player.posY;
                    double z1 = player.posZ;
                    int i = 0;
                    while ((double)i < (double)Longjump.getMaxFallDist() / 0.05510000046342611) {
                        netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x1, y1 + (double)0.0601f, z1, false));
                        netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x1, y1 + (double)5.0E-4f, z1, false));
                        netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x1, y1 + (double)0.005f + 6.01000003516674E-8, z1, false));
                        ++i;
                    }
                    netHandler.addToSendQueue(new C03PacketPlayer(true));
                }
                Longjump.mc.thePlayer.jumpMovementFactor = 0.025f;
                boolean bl = boost = Math.abs(Longjump.mc.thePlayer.rotationYawHead - Longjump.mc.thePlayer.rotationYaw) < 90.0f;
                if ((Longjump.mc.gameSettings.keyBindForward.pressed || Longjump.mc.gameSettings.keyBindLeft.pressed || Longjump.mc.gameSettings.keyBindRight.pressed || Longjump.mc.gameSettings.keyBindBack.pressed) && Longjump.mc.thePlayer.onGround) {
                    Longjump.mc.thePlayer.motionY = 0.42f;
                    Longjump.mc.timer.timerSpeed = 0.05f;
                    Longjump.mc.thePlayer.setSprinting(true);
                } else {
                    Longjump.mc.thePlayer.setSprinting(true);
                    Longjump.mc.timer.timerSpeed = 1.0f;
                    if (Longjump.mc.thePlayer.hurtTime != 0) {
                        Longjump.mc.timer.timerSpeed = ((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Longjump").getSetting((String)"Boost").getSetting()).state ? 2.0f : 1.0f;
                        Longjump.mc.thePlayer.motionY += (double)0.02f;
                        if (Longjump.mc.thePlayer.fallDistance > 0.0f) {
                            Longjump.mc.thePlayer.motionY = 0.0;
                        }
                        Speed.setSpeed5(0.47);
                    }
                }
                if (Longjump.mc.thePlayer.onGround) {
                    ++disable;
                }
                if (disable <= 1) break;
                Client.INSTANCE.moduleManager.getModule("Longjump").setState(false);
                Longjump.mc.timer.timerSpeed = 1.0f;
            }
        }
        if (event instanceof EventReceivedPacket && (p = EventReceivedPacket.INSTANCE.getPacket()) instanceof S08PacketPlayerPosLook) {
            ChatUtil.sendChatInfo("its FakeDamage");
        }
    }

    public static void setSpeed1(PlayerMoveEvent moveEvent, double moveSpeed) {
        EntityPlayerSP cfr_ignored_0 = Longjump.mc.thePlayer;
        EntityPlayerSP cfr_ignored_1 = Longjump.mc.thePlayer;
        Longjump.setSpeed1(moveEvent, moveSpeed, Longjump.mc.thePlayer.rotationYaw, EntityPlayerSP.movementInput.moveStrafe, EntityPlayerSP.movementInput.moveForward);
    }

    public static void setSpeed1(PlayerMoveEvent moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += (float)(forward > 0.0 ? -45 : 45);
            } else if (strafe < 0.0) {
                yaw += (float)(forward > 0.0 ? 45 : -45);
            }
            strafe = 0.0;
            if (forward > 0.0) {
                forward = 1.0;
            } else if (forward < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        } else if (strafe < 0.0) {
            strafe = -1.0;
        }
        double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        moveEvent.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        moveEvent.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }

    public static void setSpeed(double speed) {
        boolean isMovingStraight;
        EntityPlayerSP player = Longjump.mc.getMinecraft().thePlayer;
        double yaw = player.rotationYaw;
        boolean isMoving = player.moveForward != 0.0f || player.moveStrafing != 0.0f;
        boolean isMovingForward = player.moveForward > 0.0f;
        boolean isMovingBackward = player.moveForward < 0.0f;
        boolean isMovingRight = player.moveStrafing > 0.0f;
        boolean isMovingLeft = player.moveStrafing < 0.0f;
        boolean isMovingSideways = isMovingLeft || isMovingRight;
        boolean bl = isMovingStraight = isMovingForward || isMovingBackward;
        if (isMoving) {
            if (isMovingForward && !isMovingSideways) {
                yaw += 0.0;
            } else if (isMovingBackward && !isMovingSideways) {
                yaw += 180.0;
            } else if (isMovingForward && isMovingLeft) {
                yaw += 45.0;
            } else if (isMovingForward) {
                yaw -= 45.0;
            } else if (!isMovingStraight && isMovingLeft) {
                yaw += 90.0;
            } else if (!isMovingStraight && isMovingRight) {
                yaw -= 90.0;
            } else if (isMovingBackward && isMovingLeft) {
                yaw += 135.0;
            } else if (isMovingBackward) {
                yaw -= 135.0;
            }
            yaw = Math.toRadians(yaw);
            player.motionX = -Math.sin(yaw) * speed;
            player.motionZ = Math.cos(yaw) * speed;
        }
    }

    public static float getDirection() {
        float var1 = Longjump.mc.thePlayer.rotationYaw;
        if (Longjump.mc.thePlayer.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (Longjump.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (Longjump.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (Longjump.mc.thePlayer.moveStrafing > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (Longjump.mc.thePlayer.moveStrafing < 0.0f) {
            var1 += 90.0f * forward;
        }
        return var1 *= (float)Math.PI / 180;
    }

    public static float getDirection2() {
        float var1 = Longjump.mc.thePlayer.rotationYaw;
        if (Longjump.mc.thePlayer.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (!(Longjump.mc.thePlayer.moveForward < 0.0f)) {
            float cfr_ignored_0 = Longjump.mc.thePlayer.moveForward;
        }
        float cfr_ignored_1 = Longjump.mc.thePlayer.moveStrafing;
        float cfr_ignored_2 = Longjump.mc.thePlayer.moveStrafing;
        return var1 *= (float)Math.PI / 180;
    }

    public static float getMaxFallDist() {
        PotionEffect potioneffect = Longjump.mc.getMinecraft().thePlayer.getActivePotionEffect(Potion.jump);
        int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
        return Longjump.mc.getMinecraft().thePlayer.getMaxFallHeight() + f;
    }
}

