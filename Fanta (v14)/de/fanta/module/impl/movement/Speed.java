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
import de.fanta.module.impl.combat.Killaura;
import de.fanta.module.impl.movement.Longjump;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.setting.settings.Slider;
import de.fanta.utils.ChatUtil;
import de.fanta.utils.TimeUtil;
import java.awt.Color;
import java.util.Random;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Speed
extends Module {
    public boolean turn;
    boolean verusdmg = false;
    boolean verusB3733SpoofGround;
    boolean verusB3733FirstHop;
    boolean verusB3733IsGround;
    public int verusB3733Stage;
    public double verusB3733Speed;
    private int stage = 1;
    TimeUtil time = new TimeUtil();
    double Mineplex;
    double Verus;
    TimeUtil time2 = new TimeUtil();
    public float mineplexMotion;
    public float mineplexSpeed;

    public Speed() {
        super("Speed", 46, Module.Type.Movement, new Color(50, 255, 10));
        this.settings.add(new Setting("Hover", new CheckBox(false)));
        this.settings.add(new Setting("DamageBoost", new CheckBox(false)));
        this.settings.add(new Setting("Timer", new CheckBox(false)));
        this.settings.add(new Setting("Minepex-Motion", new Slider(1.0, 60.0, 1.0, 60.0)));
        this.settings.add(new Setting("Boost", new Slider(0.4, 2.0, 1.0, 1.0)));
        this.settings.add(new Setting("Modes", new DropdownBox("Vanilla", new String[]{"Vanilla", "Intave", "NCP-Bhob", "OldAAC", "AAC", "Mineplex", "Watchdog", "HypixelLowHob", "AntiAC", "LibreCraft", "Mineplex2", "VerusGround", "AAC 3.3.13", "Verus", "TP", "BlocksMC", "VerusFloat", "OldWatchdog"})));
    }

    public static double getSpeed() {
        return Math.sqrt(Speed.mc.getMinecraft().thePlayer.motionX * Speed.mc.getMinecraft().thePlayer.motionX + Speed.mc.getMinecraft().thePlayer.motionZ * Speed.mc.getMinecraft().thePlayer.motionZ);
    }

    /*
     * Exception decompiling
     */
    @Override
    public void onEnable() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Can't sort instructions [@NONE, blocks:[4] lbl29 : CaseStatement: default:\u000a, @NONE, blocks:[4] lbl29 : CaseStatement: default:\u000a]
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
        Speed.mc.gameSettings.keyBindJump.pressed = false;
        this.mineplexSpeed = 0.0f;
        this.verusdmg = false;
        Speed.mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }

    @Override
    public void onEvent(Event event) {
        block138: {
            this.Mineplex = ((Slider)this.getSetting((String)"Minepex-Motion").getSetting()).curValue;
            this.Verus = ((Slider)this.getSetting((String)"Boost").getSetting()).curValue;
            if (!(event instanceof EventTick)) break block138;
            String string = ((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption;
            string.hashCode();
            double speed = 0.0;
            double yaw = Math.toRadians(Speed.mc.thePlayer.rotationYaw);
            double xZ = -Math.sin(yaw) * speed;
            double zZ = Math.cos(yaw) * speed;
            switch (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption) {
                case "Vanilla": {
                    if (((CheckBox)this.getSetting((String)"Hover").getSetting()).state && !this.isBlockUnder()) {
                        Speed.mc.thePlayer.motionY = 0.0;
                        Speed.setSpeed(3.0);
                    }
                    if (Speed.mc.thePlayer.onGround) {
                        Speed.mc.thePlayer.motionY = 0.25;
                        break;
                    }
                    if (!this.isBlockUnder()) break;
                    Speed.setSpeed(2.0);
                    break;
                }
                case "OldWatchdog": {
                    if (Speed.mc.thePlayer.onGround) {
                        Speed.mc.thePlayer.jump();
                        break;
                    }
                    float yMotion = (float)MathHelper.getRandomDoubleInRange(new Random(), -0.1, -0.2);
                    Speed.mc.thePlayer.motionY = yMotion;
                    break;
                }
                case "Intave": {
                    if (((CheckBox)this.getSetting((String)"Hover").getSetting()).state && !this.isBlockUnder()) {
                        Speed.mc.thePlayer.motionY = 0.0;
                    }
                    if (Speed.mc.thePlayer.onGround) {
                        Speed.mc.thePlayer.jump();
                        break;
                    }
                    if (Speed.mc.thePlayer.fallDistance != 0.0f && (double)Speed.mc.thePlayer.fallDistance > 0.2) {
                        Speed.mc.timer.timerSpeed = 1.1f;
                        break;
                    }
                    Speed.mc.timer.timerSpeed = 1.0f;
                    break;
                }
                case "LibreCraft": {
                    if (((CheckBox)this.getSetting((String)"Hover").getSetting()).state && !this.isBlockUnder()) {
                        Speed.mc.thePlayer.motionY = 0.0;
                    }
                    if (Speed.mc.thePlayer.onGround) {
                        Speed.mc.thePlayer.motionY = 0.32f;
                        Speed.mc.thePlayer.onGround = true;
                        Speed.mc.thePlayer.motionX = -Math.sin(yaw) * 3.0;
                        Speed.mc.thePlayer.motionZ = Math.cos(yaw) * 3.0;
                        break;
                    }
                    if (Speed.mc.thePlayer.fallDistance == 0.0f || !(Speed.mc.thePlayer.moveForward > 0.0f)) break;
                    Speed.mc.thePlayer.motionX = -Math.sin(yaw) * 5.0;
                    Speed.mc.thePlayer.motionZ = Math.cos(yaw) * 5.0;
                    break;
                }
                case "AntiAC": {
                    if (((CheckBox)this.getSetting((String)"Hover").getSetting()).state && !this.isBlockUnder()) {
                        Speed.mc.thePlayer.motionY = 0.0;
                    }
                    if (!(event instanceof EventTick)) break;
                    if (Speed.mc.thePlayer.onGround) {
                        Speed.mc.thePlayer.jump();
                        break;
                    }
                    float Y = (float)MathHelper.getRandomDoubleInRange(new Random(), -0.4, -0.7f);
                    Speed.mc.thePlayer.motionY = Y;
                    break;
                }
                case "OldAAC": {
                    if (((CheckBox)this.getSetting((String)"Hover").getSetting()).state && !this.isBlockUnder()) {
                        Speed.mc.thePlayer.motionY = 0.0;
                    }
                    if (!Speed.mc.thePlayer.onGround) break;
                    Speed.mc.thePlayer.jump();
                    break;
                }
                case "AAC 3.3.13": {
                    boolean boost;
                    boolean bl = boost = Math.abs(Speed.mc.thePlayer.rotationYawHead - Speed.mc.thePlayer.rotationYaw) < 90.0f;
                    if (!Speed.mc.thePlayer.isMoving() || Speed.mc.thePlayer.hurtTime >= 5) break;
                    if (Speed.mc.thePlayer.onGround) {
                        if (!Client.INSTANCE.moduleManager.getModule("Scaffold").isState()) {
                            Speed.mc.thePlayer.jump();
                        } else {
                            Speed.mc.thePlayer.motionY = 0.4;
                        }
                        float f = Speed.getDirection();
                        Client.INSTANCE.moduleManager.getModule("Scaffold").isState();
                        break;
                    }
                    Speed.mc.thePlayer.speedInAir = 0.022f;
                    double currentSpeed = Math.sqrt(Speed.mc.thePlayer.motionX * Speed.mc.thePlayer.motionX + Speed.mc.thePlayer.motionZ * Speed.mc.thePlayer.motionZ);
                    double speed1 = boost ? 1.0074 : 1.0074;
                    double direction = Speed.getDirection();
                    Speed.mc.thePlayer.motionX = -Math.sin(direction) * speed1 * currentSpeed;
                    Speed.mc.thePlayer.motionZ = Math.cos(direction) * speed1 * currentSpeed;
                    break;
                }
                case "BlocksMC": {
                    if (Speed.mc.thePlayer.hurtTime > 0) {
                        this.verusdmg = true;
                    }
                    if (!this.verusdmg) {
                        Speed.mc.timer.timerSpeed = 1.0f;
                        Speed.setSpeed(-0.12);
                        Speed.mc.gameSettings.keyBindForward.pressed = false;
                        Speed.mc.gameSettings.keyBindBack.pressed = false;
                        Speed.mc.gameSettings.keyBindLeft.pressed = false;
                        Speed.mc.gameSettings.keyBindRight.pressed = false;
                    } else {
                        Speed.mc.gameSettings.keyBindForward.pressed = true;
                    }
                    if (!this.verusdmg) break;
                    if (Speed.mc.thePlayer.onGround) {
                        Speed.mc.thePlayer.motionY = 0.42f;
                        Speed.mc.timer.timerSpeed = 0.1f;
                    } else {
                        Speed.mc.timer.timerSpeed = 0.8f;
                    }
                    Speed.mc.thePlayer.onGround = true;
                    Speed.setSpeed(2.0);
                    break;
                }
                case "Verus": {
                    if (((CheckBox)this.getSetting((String)"Hover").getSetting()).state && !Client.INSTANCE.moduleManager.getModule("Scaffold").isState() && !this.isBlockUnder() && (double)Speed.mc.thePlayer.fallDistance > 1.7) {
                        Speed.mc.thePlayer.motionY = 0.0;
                        Speed.mc.thePlayer.onGround = true;
                    }
                    if (((CheckBox)this.getSetting((String)"DamageBoost").getSetting()).state && Speed.mc.thePlayer.hurtTime != 0 && Speed.mc.thePlayer.fallDistance < 3.0f) {
                        Speed.setSpeed(this.Verus);
                    } else {
                        Speed.setSpeed(0.292);
                    }
                    if (Speed.mc.thePlayer.onGround) {
                        Speed.mc.thePlayer.jump();
                        break;
                    }
                    Speed.mc.thePlayer.jumpMovementFactor = 0.1f;
                    break;
                }
                case "VerusFloat": {
                    Packet packet;
                    if (event instanceof EventReceivedPacket && (packet = EventReceivedPacket.INSTANCE.getPacket()) instanceof C03PacketPlayer) {
                        C03PacketPlayer packetPlayer = (C03PacketPlayer)packet;
                        if (this.verusB3733SpoofGround) {
                            packetPlayer.onGround = true;
                        }
                    }
                    if (!Speed.mc.thePlayer.isMoving()) {
                        this.verusB3733Stage = 0;
                        this.verusB3733FirstHop = true;
                        return;
                    }
                    if (!this.verusB3733IsGround) {
                        this.verusB3733IsGround = this.getPlayer().onGround;
                        return;
                    }
                    if (this.verusB3733FirstHop) {
                        if (Speed.mc.thePlayer.isMoving() && this.getPlayer().onGround) {
                            this.getPlayer().motionY = 0.42f;
                            this.verusB3733SpoofGround = true;
                            this.verusB3733Stage = 0;
                            break;
                        }
                        if (this.verusB3733Stage <= 7) {
                            this.getPlayer().motionY = 0.0;
                            ++this.verusB3733Stage;
                            break;
                        }
                        this.verusB3733SpoofGround = false;
                        this.verusB3733FirstHop = false;
                        break;
                    }
                    if (Speed.mc.thePlayer.isMoving() && this.getPlayer().onGround) {
                        this.verusB3733Speed = 0.5;
                        this.getPlayer().motionY = 0.42f;
                        this.verusB3733SpoofGround = true;
                        this.verusB3733Stage = 0;
                    } else if (this.verusB3733Stage <= 7) {
                        this.verusB3733Speed += 0.12;
                        this.getPlayer().motionY = 0.0;
                        ++this.verusB3733Stage;
                    } else {
                        this.verusB3733Speed = 0.24;
                        this.verusB3733SpoofGround = false;
                    }
                    Speed.setSpeed(this.verusB3733Speed - 1.0E-4);
                    break;
                }
                case "AAC": {
                    if (((CheckBox)this.getSetting((String)"Hover").getSetting()).state && !this.isBlockUnder()) {
                        Speed.mc.thePlayer.motionY = 0.0;
                    }
                    if (Speed.mc.thePlayer.onGround) {
                        Speed.mc.thePlayer.jump();
                        break;
                    }
                    if (Speed.mc.thePlayer.fallDistance != 0.0f && (double)Speed.mc.thePlayer.fallDistance > 0.2) {
                        Speed.mc.timer.timerSpeed = 1.08f;
                        Speed.mc.thePlayer.motionY -= 0.001;
                        break;
                    }
                    Speed.mc.thePlayer.setPosition(Speed.mc.thePlayer.posX, Speed.mc.thePlayer.posY - 0.001, Speed.mc.thePlayer.posZ);
                    Speed.mc.timer.timerSpeed = 1.0f;
                }
            }
        }
        switch (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption) {
            case "VerusGround": {
                if (((CheckBox)this.getSetting((String)"Hover").getSetting()).state && !this.isBlockUnder()) {
                    Speed.mc.thePlayer.motionY = 0.0;
                }
                if (!(event instanceof PlayerMoveEvent)) break;
                Speed.mc.timer.timerSpeed = 1.0f;
                Speed.setSpeed1(PlayerMoveEvent.INSTANCE, 5.5875);
                if (!Speed.mc.thePlayer.onGround) break;
                if (!Speed.mc.thePlayer.isCollidedHorizontally) {
                    Speed.mc.thePlayer.motionY = -0.42;
                    PlayerMoveEvent.INSTANCE.setY(-0.42);
                    break;
                }
                Speed.mc.thePlayer.motionY = 0.44;
                PlayerMoveEvent.INSTANCE.setY(0.44);
                break;
            }
            case "NCP-Bhob": {
                if (((CheckBox)this.getSetting((String)"Hover").getSetting()).state && !this.isBlockUnder()) {
                    Speed.mc.thePlayer.motionY = 0.0;
                }
                if (Client.INSTANCE.moduleManager.getModule("Longjump").isState()) {
                    boolean boost;
                    if (!(event instanceof EventTick)) break;
                    if (Speed.mc.thePlayer.onGround) {
                        NetHandlerPlayClient netHandler = mc.getMinecraft().getNetHandler();
                        EntityPlayerSP player = Speed.mc.getMinecraft().thePlayer;
                        double x = player.posX;
                        double y = player.posY;
                        double z = player.posZ;
                        int i = 0;
                        while ((double)i < (double)Speed.getMaxFallDist() / 0.05510000046342611 + 0.2) {
                            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + (double)0.0601f, z, false));
                            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + (double)5.0E-4f, z, false));
                            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + (double)0.005f + 6.01000003516674E-8, z, false));
                            ++i;
                        }
                        netHandler.addToSendQueue(new C03PacketPlayer(true));
                    }
                    boolean bl = boost = Math.abs(Speed.mc.thePlayer.rotationYawHead - Speed.mc.thePlayer.rotationYaw) < 90.0f;
                    if ((Speed.mc.gameSettings.keyBindForward.pressed || Speed.mc.gameSettings.keyBindLeft.pressed || Speed.mc.gameSettings.keyBindRight.pressed || Speed.mc.gameSettings.keyBindBack.pressed) && Speed.mc.thePlayer.onGround) {
                        Speed.mc.thePlayer.motionY = 0.52f;
                        Speed.mc.timer.timerSpeed = 0.1f;
                        Speed.mc.thePlayer.setSprinting(true);
                    } else {
                        Speed.mc.thePlayer.setSprinting(true);
                        Speed.mc.timer.timerSpeed = 1.0f;
                        if (Speed.mc.thePlayer.hurtTime != 0) {
                            Speed.mc.timer.timerSpeed = ((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Longjump").getSetting((String)"Boost").getSetting()).state ? 4.0f : 1.0f;
                            Speed.mc.thePlayer.motionY += (double)0.06f;
                            Speed.setSpeed5(0.4);
                        }
                    }
                    if (Speed.mc.thePlayer.onGround) {
                        ++Longjump.disable;
                    }
                    if (Longjump.disable <= 1) break;
                    Client.INSTANCE.moduleManager.getModule("Longjump").setState(false);
                    break;
                }
                if (!(Speed.mc.thePlayer.fallDistance < 2.0f)) break;
                if (event instanceof EventTick) {
                    mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Speed.mc.thePlayer.posX, Speed.mc.thePlayer.posY, Speed.mc.thePlayer.posZ, false));
                }
                if (!Speed.mc.thePlayer.onGround) {
                    Speed.mc.timer.timerSpeed = 1.0f;
                }
                if (Speed.mc.thePlayer.onGround) {
                    Speed.mc.timer.timerSpeed = 1.0f;
                    Speed.mc.thePlayer.motionY = 0.42f;
                    PlayerMoveEvent.INSTANCE.setY(0.42f);
                }
                if (!(event instanceof PlayerMoveEvent)) break;
                Speed.setSpeed(0.2895);
                break;
            }
            case "HypixelLowHob": {
                if (((CheckBox)this.getSetting((String)"Hover").getSetting()).state && !this.isBlockUnder()) {
                    Speed.mc.thePlayer.motionY = 0.0;
                }
                if (!Speed.mc.thePlayer.onGround) {
                    Speed.setSpeed1(PlayerMoveEvent.INSTANCE, 0.3);
                    break;
                }
            }
            case "Mineplex2": {
                if (((CheckBox)this.getSetting((String)"Hover").getSetting()).state && !this.isBlockUnder()) {
                    Speed.mc.thePlayer.motionY = 0.0;
                }
                if (Speed.mc.thePlayer.isInWeb) break;
                if (Speed.mc.thePlayer.isCollidedHorizontally) {
                    this.mineplexMotion = 0.02f;
                }
                if (Speed.mc.thePlayer.onGround) {
                    Speed.mc.thePlayer.motionZ = 0.0;
                    Speed.mc.thePlayer.motionX = 0.0;
                    this.mineplexMotion += 0.45f;
                    Speed.mc.thePlayer.motionY = 0.34;
                    break;
                }
                this.mineplexMotion -= this.mineplexMotion / 60.0f;
                break;
            }
            case "TP": {
                if (((CheckBox)this.getSetting((String)"Hover").getSetting()).state && !this.isBlockUnder()) {
                    Speed.mc.thePlayer.motionY = 0.0;
                    Speed.setSpeed(Speed.getSpeed());
                }
                Speed.setSpeed(0.85);
                double x = Speed.mc.thePlayer.posX;
                double y = Speed.mc.thePlayer.posY;
                double z = Speed.mc.thePlayer.posZ;
                if (!Speed.mc.thePlayer.onGround) break;
                Speed.mc.thePlayer.motionY = 0.5;
                break;
            }
            case "Watchdog": {
                if (((CheckBox)this.getSetting((String)"Hover").getSetting()).state && !this.isBlockUnder()) {
                    Speed.mc.thePlayer.motionY = 0.0;
                }
                if (Client.INSTANCE.moduleManager.getModule("TP").isState()) {
                    boolean boost;
                    Packet p;
                    if (event instanceof EventReceivedPacket && (p = EventReceivedPacket.INSTANCE.getPacket()) instanceof S08PacketPlayerPosLook) {
                        ChatUtil.sendChatInfo("its FakeDamage");
                    }
                    if (!(event instanceof EventTick)) break;
                    mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Speed.mc.thePlayer.posX, Speed.mc.thePlayer.posY, Speed.mc.thePlayer.posZ, false));
                    if (Speed.mc.thePlayer.onGround) {
                        NetHandlerPlayClient netHandler = mc.getMinecraft().getNetHandler();
                        EntityPlayerSP player = Speed.mc.getMinecraft().thePlayer;
                        double x1 = player.posX;
                        double y1 = player.posY;
                        double z1 = player.posZ;
                        int i = 0;
                        while ((double)i < (double)Speed.getMaxFallDist() / 0.05510000046342611 + 0.2) {
                            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x1, y1 + (double)0.0601f, z1, false));
                            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x1, y1 + (double)5.0E-4f, z1, false));
                            netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x1, y1 + (double)0.005f + 6.01000003516674E-8, z1, false));
                            ++i;
                        }
                        netHandler.addToSendQueue(new C03PacketPlayer(true));
                    }
                    Speed.mc.thePlayer.jumpMovementFactor = 0.025f;
                    boolean bl = boost = Math.abs(Speed.mc.thePlayer.rotationYawHead - Speed.mc.thePlayer.rotationYaw) < 90.0f;
                    if ((Speed.mc.gameSettings.keyBindForward.pressed || Speed.mc.gameSettings.keyBindLeft.pressed || Speed.mc.gameSettings.keyBindRight.pressed || Speed.mc.gameSettings.keyBindBack.pressed) && Speed.mc.thePlayer.onGround) {
                        Speed.mc.thePlayer.motionY = 0.42f;
                        Speed.mc.timer.timerSpeed = 0.1f;
                        Speed.mc.thePlayer.setSprinting(true);
                    } else {
                        Speed.mc.thePlayer.setSprinting(true);
                        Speed.mc.timer.timerSpeed = 1.0f;
                        if (Speed.mc.thePlayer.hurtTime != 0) {
                            Speed.mc.timer.timerSpeed = ((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Longjump").getSetting((String)"Boost").getSetting()).state ? 4.0f : 1.0f;
                            Speed.mc.thePlayer.motionY += (double)0.01f;
                            if ((double)Speed.mc.thePlayer.fallDistance > 0.1) {
                                Speed.mc.thePlayer.motionY = 0.42f;
                            }
                            if (Speed.mc.thePlayer.fallDistance > 0.0f) {
                                Speed.mc.thePlayer.motionY = 0.12f;
                            }
                            Speed.setSpeed5(0.45);
                        }
                    }
                    if (Speed.mc.thePlayer.onGround) {
                        ++Longjump.disable;
                    }
                    if (Longjump.disable <= 1) break;
                    Client.INSTANCE.moduleManager.getModule("Longjump").setState(false);
                    break;
                }
                if (event instanceof EventTick) {
                    Speed.mc.thePlayer.jumpMovementFactor = 0.027f;
                    if (Speed.mc.thePlayer.ticksExisted % 3 == 0) {
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Speed.mc.thePlayer.posX, Speed.mc.thePlayer.posY, Speed.mc.thePlayer.posZ, false));
                    }
                }
                if (Speed.mc.thePlayer.onGround) {
                    Speed.mc.gameSettings.keyBindJump.pressed = true;
                }
                if (!(event instanceof PlayerMoveEvent)) break;
                Speed.setSpeed(Speed.getSpeed());
                if (!((CheckBox)this.getSetting((String)"Timer").getSetting()).state) break;
                Speed.mc.timer.timerSpeed = 1.2f;
                break;
            }
            case "Mineplex": {
                if (((CheckBox)this.getSetting((String)"Hover").getSetting()).state && !this.isBlockUnder()) {
                    Speed.mc.thePlayer.motionY = 0.0;
                }
                if (!(event instanceof PlayerMoveEvent)) break;
                float tmm = (float)MathHelper.getRandomDoubleInRange(new Random(), 93.0, 95.0);
                float Y = (float)MathHelper.getRandomDoubleInRange(new Random(), -0.0, -0.0);
                float Y2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.015, 0.016);
                float slowdown1 = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.0, 0.0);
                float Y3 = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.45, 0.46);
                Speed.mc.thePlayer.setSprinting(true);
                if (!Speed.mc.gameSettings.keyBindForward.pressed && !Speed.mc.gameSettings.keyBindLeft.pressed && !Speed.mc.gameSettings.keyBindRight.pressed && !Speed.mc.gameSettings.keyBindBack.pressed) break;
                double speed11 = 0.0;
                Speed.mc.timer.timerSpeed = 1.0f;
                ++this.stage;
                if (Speed.mc.thePlayer.isCollidedHorizontally) {
                    this.stage = 50;
                    this.mineplexMotion = 0.02f;
                }
                if (Speed.mc.thePlayer.onGround && (Speed.mc.thePlayer.moveForward != 0.0f || Speed.mc.thePlayer.moveStrafing != 0.0f)) {
                    this.mineplexMotion += 0.65f;
                    Speed.mc.thePlayer.motionY = 0.42f;
                    PlayerMoveEvent.INSTANCE.setY(0.42f);
                    this.stage = 0;
                    speed11 = 0.0;
                }
                if (!Speed.mc.thePlayer.onGround) {
                    if (Speed.mc.thePlayer.motionY > (double)Y) {
                        Speed.mc.thePlayer.motionY += (double)Y2;
                    }
                    double slowdown = slowdown1;
                    this.mineplexMotion = (float)((double)this.mineplexMotion - (double)this.mineplexMotion / this.Mineplex);
                    speed11 = (double)this.mineplexMotion - (double)this.stage * slowdown;
                    if (speed11 < 0.0) {
                        speed11 = 0.0;
                    }
                }
                Speed.setSpeed1(PlayerMoveEvent.INSTANCE, speed11);
            }
        }
    }

    public static void setSpeed1(PlayerMoveEvent moveEvent, double moveSpeed) {
        EntityPlayerSP cfr_ignored_0 = Speed.mc.thePlayer;
        EntityPlayerSP cfr_ignored_1 = Speed.mc.thePlayer;
        Speed.setSpeed1(moveEvent, moveSpeed, Speed.mc.thePlayer.rotationYaw, EntityPlayerSP.movementInput.moveStrafe, EntityPlayerSP.movementInput.moveForward);
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

    public static void setSpeed5(PlayerMoveEvent moveEvent, double moveSpeed) {
        EntityPlayerSP cfr_ignored_0 = Speed.mc.thePlayer;
        EntityPlayerSP cfr_ignored_1 = Speed.mc.thePlayer;
        Speed.setSpeed1(moveEvent, moveSpeed, Speed.mc.thePlayer.rotationYaw, EntityPlayerSP.movementInput.moveStrafe, EntityPlayerSP.movementInput.moveForward);
    }

    public static void setSpeed5(PlayerMoveEvent moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += (float)(forward > 0.0 ? -35 : 35);
            } else if (strafe < 0.0) {
                yaw += (float)(forward > 0.0 ? 35 : -35);
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

    public static void setSpeed6(PlayerMoveEvent moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (forward != 0.0) {
            if (!(strafe > 0.0)) {
                // empty if block
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

    public static void setSpeedTest(double speed) {
        boolean isMovingStraight;
        EntityPlayerSP player = Speed.mc.getMinecraft().thePlayer;
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

    public static void setSpeed(double speed) {
        boolean isMovingStraight;
        EntityPlayerSP player = Speed.mc.getMinecraft().thePlayer;
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

    public static void setSpeed5(double speed) {
        boolean isMovingStraight;
        EntityPlayerSP player = Speed.mc.getMinecraft().thePlayer;
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
            } else if (!(isMovingBackward && !isMovingSideways || isMovingForward && isMovingLeft)) {
                if (isMovingForward) {
                    yaw -= 45.0;
                } else if (!isMovingStraight && isMovingLeft || !isMovingStraight && isMovingRight || !isMovingBackward || !isMovingLeft) {
                    // empty if block
                }
            }
            yaw = Math.toRadians(yaw);
            player.motionX = -Math.sin(yaw) * speed;
            player.motionZ = Math.cos(yaw) * speed;
        }
    }

    public static float getDirection() {
        float var1 = Speed.mc.thePlayer.rotationYaw;
        if (Speed.mc.thePlayer.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (Speed.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (Speed.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (Speed.mc.thePlayer.moveStrafing > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (Speed.mc.thePlayer.moveStrafing < 0.0f) {
            var1 += 90.0f * forward;
        }
        return var1 *= (float)Math.PI / 180;
    }

    public static float getDirection2() {
        float var1 = Speed.mc.thePlayer.rotationYaw;
        if (Speed.mc.thePlayer.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (!(Speed.mc.thePlayer.moveForward < 0.0f)) {
            float cfr_ignored_0 = Speed.mc.thePlayer.moveForward;
        }
        float cfr_ignored_1 = Speed.mc.thePlayer.moveStrafing;
        float cfr_ignored_2 = Speed.mc.thePlayer.moveStrafing;
        return var1 *= (float)Math.PI / 180;
    }

    public static float getMaxFallDist() {
        PotionEffect potioneffect = Speed.mc.getMinecraft().thePlayer.getActivePotionEffect(Potion.jump);
        int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
        return Speed.mc.getMinecraft().thePlayer.getMaxFallHeight() + f;
    }

    public static void setStrafeSpeed(double strafeSpeed) {
        Speed.mc.thePlayer.motionX = -(Math.sin(Speed.getFaceDirection()) * strafeSpeed);
        Speed.mc.thePlayer.motionZ = Math.cos(Speed.getFaceDirection()) * strafeSpeed;
    }

    public static float getFaceDirection() {
        float var1 = Speed.mc.thePlayer.rotationYaw;
        if (Speed.mc.thePlayer.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (Speed.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (Speed.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (Speed.mc.thePlayer.moveStrafing > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (Speed.mc.thePlayer.moveStrafing < 0.0f) {
            var1 += 90.0f * forward;
        }
        return var1 *= (float)Math.PI / 180;
    }

    public boolean isBlockUnder() {
        int i = (int)Speed.mc.thePlayer.posY;
        while (i >= 0) {
            BlockPos position = new BlockPos(Speed.mc.thePlayer.posX, (double)i, Speed.mc.thePlayer.posZ);
            if (!(Speed.mc.theWorld.getBlockState(position).getBlock() instanceof BlockAir)) {
                return true;
            }
            --i;
        }
        return false;
    }

    public boolean doStrafeAtSpeed(PlayerMoveEvent event, double moveSpeed) {
        boolean strafe = this.canStrafe();
        if (Killaura.kTarget != null && strafe) {
            Speed.setSpeed1(event, moveSpeed);
        }
        return strafe;
    }

    public boolean canStrafe() {
        return Client.INSTANCE.moduleManager.getModule("Killaura").isState();
    }
}

