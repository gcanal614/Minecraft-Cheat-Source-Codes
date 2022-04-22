/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.module.impl.movement;

import de.fanta.Client;
import de.fanta.events.Event;
import de.fanta.events.listeners.EventNoClip;
import de.fanta.events.listeners.EventPacket;
import de.fanta.events.listeners.EventPreMotion;
import de.fanta.events.listeners.EventRender2D;
import de.fanta.events.listeners.EventTick;
import de.fanta.events.listeners.PlayerMoveEvent;
import de.fanta.module.Module;
import de.fanta.module.impl.movement.Speed;
import de.fanta.setting.Setting;
import de.fanta.setting.settings.CheckBox;
import de.fanta.setting.settings.DropdownBox;
import de.fanta.setting.settings.Slider;
import de.fanta.utils.TimeUtil;
import java.awt.Color;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Flight
extends Module {
    TimeUtil time = new TimeUtil();
    double speed = 0.0;
    boolean cubedmg = false;
    boolean verusdmg = false;
    boolean high = false;
    private long startTime;
    public boolean turn;
    public static boolean Start = false;
    public static double motion;
    public static double MineplexSpeed;
    public static double RededarkSpeed;
    public static double KaroSpeed;
    public static double BlocksMC;
    public static double BlocksMCTimer;

    public Flight() {
        super("Flight", 0, Module.Type.Movement, Color.blue);
        this.settings.add(new Setting("Bobbing", new CheckBox(false)));
        this.settings.add(new Setting("MineplexFast", new CheckBox(false)));
        this.settings.add(new Setting("Modes", new DropdownBox("Vanilla", new String[]{"Motion", "Vanilla", "Hypixel", "Redesky", "Cubecraft", "MCCentral", "VanillaBypass", "LibreCraft", "MushBW", "Mush", "Verus", "Rededark", "Rededark2", "Karhu", "OldNCP", "LuckyNetwork", "BlocksMC", "VerusFloat", "WatchDug", "VerusColide", "Funcraft", "VerusJump"})));
        this.settings.add(new Setting("MotionSpeed", new Slider(1.2, 9.0, 0.1, 3.0)));
        this.settings.add(new Setting("VerusSpeed", new Slider(0.1, 3.0, 0.1, 3.0)));
        this.settings.add(new Setting("LuckySpeed", new Slider(0.1, 9.0, 0.1, 3.0)));
        this.settings.add(new Setting("RededarkSpeed", new Slider(1.2, 8.0, 0.1, 3.0)));
        this.settings.add(new Setting("KaroSpeed", new Slider(1.0, 8.0, 0.1, 3.0)));
        this.settings.add(new Setting("BlocksMCSpeed", new Slider(3.0, 9.0, 0.1, 3.0)));
        this.settings.add(new Setting("BlocksMCTimer", new Slider(1.0, 10.0, 0.1, 1.0)));
    }

    @Override
    public void onDisable() {
        switch (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption) {
            case "VerusFloat": {
                if (!Flight.mc.thePlayer.onGround) break;
                Flight.mc.thePlayer.motionY = 0.42f;
            }
        }
        Flight.mc.gameSettings.keyBindSprint.pressed = true;
        Flight.mc.thePlayer.setSprinting(true);
        Flight.mc.thePlayer.motionY = 0.0;
        PlayerMoveEvent.INSTANCE.setY(0.0);
        boolean high = false;
        this.cubedmg = false;
        this.verusdmg = false;
        Start = false;
        Flight.mc.timer.timerSpeed = 1.0f;
        Flight.mc.thePlayer.capabilities.allowFlying = false;
        Flight.mc.thePlayer.capabilities.isFlying = false;
        Flight.mc.thePlayer.onGround = false;
        super.onDisable();
        Flight.setSpeed(0.0);
        Flight.mc.thePlayer.motionY = 0.0;
        PlayerMoveEvent.INSTANCE.setY(0.0);
    }

    /*
     * Exception decompiling
     */
    @Override
    public void onEnable() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Can't sort instructions [@NONE, blocks:[11] lbl172 : CaseStatement: default:\u000a, @NONE, blocks:[11] lbl172 : CaseStatement: default:\u000a]
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
    public void onEvent(Event event) {
        block159: {
            block157: {
                BlocksMC = ((Slider)this.getSetting((String)"BlocksMCSpeed").getSetting()).curValue;
                BlocksMCTimer = ((Slider)this.getSetting((String)"BlocksMCTimer").getSetting()).curValue;
                switch (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption) {
                    case "Motion": {
                        motion = ((Slider)this.getSetting((String)"MotionSpeed").getSetting()).curValue;
                        Flight.setSpeed(Speed.getSpeed());
                        Flight.mc.thePlayer.motionY = 0.0;
                        if (Flight.mc.gameSettings.keyBindJump.pressed) {
                            Flight.mc.thePlayer.motionY = 1.0;
                        } else if (Flight.mc.gameSettings.keyBindSneak.pressed) {
                            Flight.mc.thePlayer.motionY = -1.0;
                        }
                        Flight.mc.thePlayer.onGround = true;
                        break;
                    }
                    case "Mush": {
                        if (!Flight.mc.gameSettings.keyBindJump.isPressed() && !(Flight.mc.thePlayer.motionY < -0.4) || Flight.mc.thePlayer.onGround) break;
                        Flight.mc.thePlayer.motionY = Flight.mc.thePlayer.ticksExisted % 2 == 1 ? -1.0 : (double)-0.05f;
                        if (!(event instanceof EventTick)) break;
                        Flight.mc.thePlayer.onGround = true;
                        break;
                    }
                    case "Cubecraft": {
                        if (event instanceof EventPacket) {
                            if (Flight.mc.thePlayer.ticksExisted % 5 == 0 && !Flight.mc.gameSettings.keyBindJump.pressed) {
                                event.setCancelled(true);
                            } else if (Flight.mc.gameSettings.keyBindJump.pressed && Flight.mc.thePlayer.ticksExisted % 2 == 0) {
                                event.setCancelled(true);
                            }
                        }
                        if (!(event instanceof PlayerMoveEvent)) break;
                        Flight.mc.gameSettings.keyBindSprint.pressed = false;
                        double x = Flight.mc.thePlayer.posX;
                        double y = Flight.mc.thePlayer.posY;
                        double z = Flight.mc.thePlayer.posZ;
                        ((PlayerMoveEvent)event).setY(0.0);
                        if (Flight.mc.gameSettings.keyBindJump.pressed) {
                            ((PlayerMoveEvent)event).setY(4.0);
                            Flight.mc.timer.timerSpeed = 0.1f;
                        }
                        if (!Flight.mc.gameSettings.keyBindForward.pressed) break;
                        if (Flight.mc.thePlayer.ticksExisted % 1 == 0) {
                            Flight.mc.timer.timerSpeed = 0.1f;
                            double yaw1 = Math.toRadians(Flight.mc.thePlayer.rotationYaw);
                            double speed1 = 14.0;
                            double xm = -Math.sin(yaw1) * speed1;
                            double zm = Math.cos(yaw1) * speed1;
                            ((PlayerMoveEvent)event).setX(xm);
                            if (!Flight.mc.gameSettings.keyBindJump.pressed) {
                                ((PlayerMoveEvent)event).setY(0.0);
                            }
                            ((PlayerMoveEvent)event).setZ(zm);
                            break;
                        }
                        ((PlayerMoveEvent)event).setY(-0.0);
                        ((PlayerMoveEvent)event).setX(0.0);
                        ((PlayerMoveEvent)event).setZ(0.0);
                        break;
                    }
                    case "VerusJump": {
                        if (event instanceof EventTick) {
                            Flight.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, new ItemStack(Items.water_bucket), 0.0f, 0.5f, 0.0f));
                            Flight.sendPacketUnlogged(new C08PacketPlayerBlockPlacement(new BlockPos(this.getX(), this.getY() - 1.5, this.getZ()), 1, new ItemStack(Blocks.stone.getItem(Flight.mc.theWorld, new BlockPos(-1, -1, -1))), 0.0f, 0.94f, 0.0f));
                        }
                        if (!Flight.mc.gameSettings.keyBindJump.isPressed() && !(Flight.mc.thePlayer.motionY < 0.0) || Flight.mc.thePlayer.onGround) break;
                        Flight.mc.thePlayer.motionY = Flight.mc.thePlayer.ticksExisted % 8 == 0 ? (!Flight.mc.gameSettings.keyBindJump.pressed ? 0.0 : (double)0.42f) : -0.0;
                        Speed.setSpeed(0.292);
                        break;
                    }
                    case "WatchDug": {
                        Flight.mc.gameSettings.keyBindJump.pressed = false;
                        if (Flight.mc.thePlayer.onGround) {
                            Flight.mc.thePlayer.motionY = 0.42f;
                            break;
                        }
                        float Y = (float)MathHelper.getRandomDoubleInRange(new Random(), -0.06, -0.15f);
                        if (Flight.mc.thePlayer.ticksExisted % 2 == 1) {
                            Flight.mc.thePlayer.motionY = Flight.mc.gameSettings.keyBindSneak.pressed ? -0.5 : (double)(-Y);
                            Flight.setSpeed(0.4);
                            break;
                        }
                        if (Flight.mc.gameSettings.keyBindJump.pressed) {
                            Flight.mc.thePlayer.motionY = 0.1f;
                            Flight.mc.thePlayer.onGround = true;
                            break;
                        }
                        Flight.mc.thePlayer.motionY = Y;
                        Flight.mc.thePlayer.onGround = false;
                        break;
                    }
                    case "VerusColide": {
                        Flight.mc.gameSettings.keyBindForward.pressed = true;
                        if (Flight.mc.thePlayer.hurtTime != 0) {
                            this.verusdmg = true;
                        }
                        if (!this.verusdmg) {
                            Flight.setSpeed(Flight.getSpeed());
                        }
                        if (Flight.mc.gameSettings.keyBindJump.pressed) {
                            Flight.mc.thePlayer.motionY = 1.0;
                            Flight.mc.timer.timerSpeed = 0.7f;
                        } else {
                            Flight.mc.timer.timerSpeed = 1.0f;
                            if (Flight.mc.gameSettings.keyBindSneak.pressed) {
                                Flight.mc.thePlayer.motionY = -1.0;
                                Flight.mc.timer.timerSpeed = 0.7f;
                            } else {
                                Flight.mc.thePlayer.motionY = 0.0;
                                Flight.mc.timer.timerSpeed = 1.0f;
                            }
                        }
                        if (!(event instanceof EventNoClip)) break;
                        ((EventNoClip)event).noClip = true;
                        Flight.setSpeed(3.2);
                        Flight.mc.thePlayer.onGround = true;
                        break;
                    }
                    case "VerusFloat": {
                        if (event instanceof EventTick) {
                            Flight.mc.thePlayer.onGround = true;
                            Flight.mc.thePlayer.motionY = Flight.mc.gameSettings.keyBindJump.pressed ? 1.0 : 0.0;
                        }
                        if (!(event instanceof EventNoClip)) break;
                        ((EventNoClip)event).noClip = true;
                        break;
                    }
                    case "Funcraft": {
                        Flight.mc.timer.timerSpeed = 0.5f;
                        if (!Flight.mc.gameSettings.keyBindJump.pressed) {
                            this.getPlayer().motionY = 0.0;
                        }
                        if (Flight.mc.gameSettings.keyBindJump.pressed) {
                            this.getPlayer().motionY = 1.0;
                        } else if (Flight.mc.gameSettings.keyBindSneak.pressed) {
                            this.getPlayer().motionY = -1.0;
                        }
                        if (!Flight.mc.thePlayer.isMoving()) break;
                        Speed.setSpeed(6.0);
                        break;
                    }
                    case "Karhu": {
                        if (Flight.mc.thePlayer.hurtTime > 0) {
                            this.verusdmg = true;
                        }
                        if (this.verusdmg) {
                            KaroSpeed = ((Slider)this.getSetting((String)"KaroSpeed").getSetting()).curValue;
                            Flight.setSpeed(9.0);
                            Flight.mc.timer.timerSpeed = Flight.mc.thePlayer.hurtTime != 0 ? 0.4f : 0.2f;
                            if (Flight.mc.thePlayer.fallDistance > 0.0f) {
                                Flight.mc.thePlayer.onGround = true;
                                Flight.mc.thePlayer.motionY = -0.005f;
                                break;
                            }
                            Flight.mc.thePlayer.onGround = false;
                            break;
                        }
                        Flight.mc.thePlayer.motionX = 0.0;
                        Flight.mc.thePlayer.motionZ = 0.0;
                        break;
                    }
                    case "LuckyNetwork": {
                        if (Flight.mc.thePlayer.hurtTime > 0) {
                            this.verusdmg = true;
                        }
                        if (!this.verusdmg) {
                            Flight.mc.timer.timerSpeed = 1.0f;
                            Flight.setSpeed(-0.12);
                            Flight.mc.gameSettings.keyBindForward.pressed = false;
                            Flight.mc.gameSettings.keyBindBack.pressed = false;
                            Flight.mc.gameSettings.keyBindLeft.pressed = false;
                            Flight.mc.gameSettings.keyBindRight.pressed = false;
                        } else {
                            Flight.mc.gameSettings.keyBindForward.pressed = true;
                        }
                        if (!this.verusdmg) break;
                        if (Flight.mc.thePlayer.onGround && Flight.mc.thePlayer.hurtTime > 0) {
                            Flight.mc.thePlayer.motionY = 0.42f;
                        }
                        KaroSpeed = ((Slider)this.getSetting((String)"LuckySpeed").getSetting()).curValue;
                        Flight.setSpeed(KaroSpeed);
                        Flight.mc.timer.timerSpeed = Flight.mc.thePlayer.ticksExisted % 2 == 1 ? 0.52f : 0.5f;
                        if (Flight.mc.thePlayer.onGround) break;
                        if (!Flight.mc.gameSettings.keyBindJump.pressed) {
                            Flight.mc.thePlayer.motionY *= 0.0;
                        }
                        if (Flight.mc.gameSettings.keyBindJump.pressed) {
                            Flight.mc.thePlayer.motionY = 2.0;
                        }
                        if (!Flight.mc.gameSettings.keyBindSneak.pressed) break;
                        Flight.mc.thePlayer.motionY = -2.0;
                        break;
                    }
                    case "Rededark": {
                        Flight.mc.thePlayer.onGround = true;
                        if ((double)Flight.mc.thePlayer.fallDistance > 1.5) {
                            Flight.mc.gameSettings.keyBindJump.pressed = true;
                            Flight.mc.timer.timerSpeed = 0.3f;
                            Flight.setSpeed(9.0);
                            break;
                        }
                        Flight.mc.gameSettings.keyBindJump.pressed = false;
                        break;
                    }
                    case "MushBW": {
                        if (this.time.hasReached(7000L)) {
                            Start = true;
                            this.time.reset();
                        }
                        if (!Start) {
                            Flight.mc.gameSettings.keyBindForward.pressed = false;
                        }
                        if (!Start) {
                            Flight.setSpeed(0.0);
                            Flight.mc.thePlayer.motionX = 0.0;
                            Flight.mc.thePlayer.motionZ = 0.0;
                        }
                        if (!Start) break;
                        Flight.setSpeed(4.0);
                        if (Flight.mc.thePlayer.onGround && !this.high) {
                            this.cubedmg = true;
                        }
                        if (!this.cubedmg) break;
                        Flight.mc.thePlayer.motionY = 0.0;
                        PlayerMoveEvent.INSTANCE.setY(0.0);
                        if (Flight.mc.thePlayer.ticksExisted % 2 != 0) break;
                        PlayerMoveEvent.INSTANCE.setY(Flight.mc.thePlayer.motionY += (double)0.03f);
                        if (this.high) break;
                        Flight.setSpeed(5.0);
                    }
                }
                if (!(event instanceof EventRender2D) || !event.isPre()) break block157;
                switch (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption) {
                    case "MushBW": {
                        if (Start) break;
                        GuiIngame.drawRect2((float)ScaledResolution.INSTANCE.getScaledWidth() / 2.0f + 8.0f, (float)ScaledResolution.INSTANCE.getScaledHeight() / 2.0f - 7.0f, (float)ScaledResolution.INSTANCE.getScaledWidth() / 2.0f + 82.0f, (float)ScaledResolution.INSTANCE.getScaledHeight() / 2.0f + 17.0f, Color.black.getRGB());
                        GuiIngame.drawRect2((float)ScaledResolution.INSTANCE.getScaledWidth() / 2.0f + 10.0f, (float)ScaledResolution.INSTANCE.getScaledHeight() / 2.0f - 5.0f, (float)ScaledResolution.INSTANCE.getScaledWidth() / 2.0f + 10.0f + 70.0f * Math.min(1.0f, (float)(System.currentTimeMillis() - this.startTime) / 7000.0f), (float)ScaledResolution.INSTANCE.getScaledHeight() / 2.0f + 15.0f, Color.green.getRGB());
                    }
                }
            }
            if (!(event instanceof EventPreMotion)) break block159;
            if (((CheckBox)this.getSetting((String)"Bobbing").getSetting()).state) {
                Flight.mc.thePlayer.cameraYaw = 0.1f;
            }
            double yaw = Math.toRadians(Flight.mc.thePlayer.rotationYawHead);
            double x1 = -Math.sin(yaw) * this.speed;
            double z1 = Math.cos(yaw) * this.speed;
            switch (((DropdownBox)this.getSetting((String)"Modes").getSetting()).curOption) {
                case "Vanilla": {
                    Flight.mc.thePlayer.capabilities.allowFlying = true;
                    Flight.mc.thePlayer.capabilities.isFlying = true;
                    Flight.setSpeed(0.1);
                    break;
                }
                case "Verus": {
                    if (Flight.mc.thePlayer.hurtTime > 0) {
                        this.verusdmg = true;
                    }
                    if (!this.verusdmg) {
                        Flight.mc.timer.timerSpeed = 1.0f;
                        Flight.setSpeed(-0.12);
                        Flight.mc.gameSettings.keyBindForward.pressed = false;
                        Flight.mc.gameSettings.keyBindBack.pressed = false;
                        Flight.mc.gameSettings.keyBindLeft.pressed = false;
                        Flight.mc.gameSettings.keyBindRight.pressed = false;
                    } else {
                        Flight.mc.gameSettings.keyBindForward.pressed = true;
                    }
                    if (!this.verusdmg) break;
                    float tmm = (float)MathHelper.getRandomDoubleInRange(new Random(), 93.0, 95.0);
                    if (this.time.hasReached(1L)) {
                        this.turn = !this.turn;
                        this.time.reset();
                    }
                    if (this.turn) {
                        Flight.mc.timer.timerSpeed = 0.07f;
                    }
                    if (!this.turn) {
                        Flight.mc.timer.timerSpeed = 0.08f;
                    }
                    Flight.setSpeed(0.0);
                    if (!Flight.mc.gameSettings.keyBindForward.pressed) break;
                    if (Flight.mc.gameSettings.keyBindJump.pressed) {
                        Flight.mc.thePlayer.motionY = Flight.mc.thePlayer.ticksExisted % 5 == 0 ? 3.0 : 0.0;
                    }
                    if (Flight.mc.gameSettings.keyBindSneak.pressed) {
                        Flight.mc.thePlayer.motionY = Flight.mc.thePlayer.ticksExisted % 5 == 0 ? -3.0 : 0.0;
                    }
                    if (!Flight.mc.gameSettings.keyBindSneak.pressed && !Flight.mc.gameSettings.keyBindForward.pressed) {
                        Flight.mc.thePlayer.motionY = 0.0;
                    }
                    double x = Flight.mc.thePlayer.posX;
                    double y = Flight.mc.thePlayer.posY;
                    double z = Flight.mc.thePlayer.posZ;
                    double speed1 = 9.0;
                    double xm = -Math.sin(yaw) * speed1;
                    double zm = Math.cos(yaw) * speed1;
                    Flight.mc.thePlayer.setPosition(x + xm, y, z + zm);
                    if (Flight.mc.thePlayer.ticksExisted % 1 == 0) {
                        MineplexSpeed = ((Slider)this.getSetting((String)"VerusSpeed").getSetting()).curValue;
                        Flight.mc.timer.timerSpeed = (float)MineplexSpeed;
                        double d = Math.toRadians(Flight.mc.thePlayer.rotationYaw);
                    }
                    Flight.mc.thePlayer.onGround = true;
                    Flight.mc.thePlayer.motionY = 0.0;
                    break;
                }
                case "BlocksMC": {
                    if (Flight.mc.thePlayer.hurtTime > 0) {
                        this.verusdmg = true;
                    }
                    if (!this.verusdmg) {
                        Flight.mc.timer.timerSpeed = 1.0f;
                        Flight.setSpeed(-0.12);
                        Flight.mc.gameSettings.keyBindForward.pressed = false;
                        Flight.mc.gameSettings.keyBindBack.pressed = false;
                        Flight.mc.gameSettings.keyBindLeft.pressed = false;
                        Flight.mc.gameSettings.keyBindRight.pressed = false;
                    } else {
                        Flight.mc.gameSettings.keyBindForward.pressed = true;
                    }
                    if (!this.verusdmg) break;
                    float tmm = (float)MathHelper.getRandomDoubleInRange(new Random(), 93.0, 95.0);
                    if (this.time.hasReached(1L)) {
                        this.turn = !this.turn;
                        this.time.reset();
                    }
                    if (this.turn) {
                        Flight.mc.timer.timerSpeed = 0.6f;
                    }
                    if (!this.turn) {
                        Flight.mc.timer.timerSpeed = 0.8f;
                    }
                    Flight.mc.thePlayer.motionY = 0.0;
                    if (Flight.mc.gameSettings.keyBindSneak.pressed) {
                        Flight.mc.thePlayer.motionY = -2.0;
                        Flight.mc.timer.timerSpeed = 0.4f;
                    } else {
                        Flight.mc.thePlayer.motionY = 0.0;
                    }
                    if (!Flight.mc.gameSettings.keyBindForward.pressed) break;
                    if (Flight.mc.thePlayer.hurtTime != 0) {
                        Flight.mc.timer.timerSpeed = 0.8f;
                    }
                    double x = Flight.mc.thePlayer.posX;
                    double y = Flight.mc.thePlayer.posY;
                    double z = Flight.mc.thePlayer.posZ;
                    double speed1 = 2.0;
                    double speed2 = 0.0;
                    double xm = -Math.sin(yaw) * speed1;
                    double zm = Math.cos(yaw) * speed1;
                    double xm1 = -Math.sin(yaw) * speed2;
                    double zm1 = Math.cos(yaw) * speed2;
                    int cfr_ignored_0 = Flight.mc.thePlayer.ticksExisted % 2;
                    Flight.mc.timer.timerSpeed = 0.6f;
                    Flight.setSpeed(BlocksMC);
                    if (Flight.mc.gameSettings.keyBindJump.pressed) {
                        Flight.mc.thePlayer.motionY = 2.0;
                        Flight.mc.timer.timerSpeed = 0.5f;
                    }
                    int cfr_ignored_1 = Flight.mc.thePlayer.ticksExisted % 2;
                    break;
                }
                case "OldNCP": {
                    double Y;
                    double z;
                    double y;
                    double x;
                    if (Flight.mc.thePlayer.hurtTime > 0) {
                        this.verusdmg = true;
                    }
                    if (!this.verusdmg) {
                        Flight.mc.timer.timerSpeed = 1.0f;
                        Flight.setSpeed(-0.12);
                        Flight.mc.gameSettings.keyBindForward.pressed = false;
                        Flight.mc.gameSettings.keyBindBack.pressed = false;
                        Flight.mc.gameSettings.keyBindLeft.pressed = false;
                        Flight.mc.gameSettings.keyBindRight.pressed = false;
                    } else {
                        Flight.mc.gameSettings.keyBindForward.pressed = true;
                    }
                    if (!this.verusdmg) break;
                    if (Flight.mc.gameSettings.keyBindForward.pressed) {
                        x = Flight.mc.thePlayer.posX;
                        y = Flight.mc.thePlayer.posY;
                        z = Flight.mc.thePlayer.posZ;
                        Flight.mc.thePlayer.motionY = 0.0;
                        Y = 0.0;
                        Flight.mc.thePlayer.setPosition(x, y + Y, z);
                        Flight.mc.timer.timerSpeed = 0.4f;
                    }
                    if (Flight.mc.thePlayer.isMoving()) {
                        Flight.setSpeed(3.0);
                    }
                    if (Flight.mc.gameSettings.keyBindJump.pressed) {
                        x = Flight.mc.thePlayer.posX;
                        y = Flight.mc.thePlayer.posY;
                        z = Flight.mc.thePlayer.posZ;
                        Flight.mc.thePlayer.motionY = 0.2;
                        Y = 0.2;
                        Flight.mc.thePlayer.setPosition(x, y + Y, z);
                    }
                    if (!Flight.mc.gameSettings.keyBindSneak.pressed) break;
                    x = Flight.mc.thePlayer.posX;
                    y = Flight.mc.thePlayer.posY;
                    z = Flight.mc.thePlayer.posZ;
                    Flight.mc.thePlayer.motionY = -0.2;
                    Y = -0.2;
                    Flight.mc.thePlayer.setPosition(x, y + Y, z);
                    break;
                }
                case "VanillaBypass": {
                    if (Flight.mc.thePlayer.fallDistance > 1.0f) {
                        Flight.mc.thePlayer.motionY = 0.42f;
                        Flight.setSpeed(4.0);
                    } else {
                        Flight.setSpeed(0.0);
                    }
                    Flight.mc.thePlayer.motionY = Flight.mc.thePlayer.ticksExisted % 4 == 0 ? (double)0.3f : (double)-0.1f;
                    if (Flight.mc.gameSettings.keyBindJump.pressed) {
                        Flight.mc.thePlayer.motionY = 0.5;
                    }
                    if (!Flight.mc.gameSettings.keyBindSneak.pressed) break;
                    Flight.mc.thePlayer.motionY = -1.0;
                    break;
                }
                case "Hypixel": {
                    boolean boost;
                    if (Flight.mc.thePlayer.onGround) {
                        NetHandlerPlayClient netHandler = mc.getMinecraft().getNetHandler();
                        EntityPlayerSP player = Flight.mc.getMinecraft().thePlayer;
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
                    boolean bl = boost = Math.abs(Flight.mc.thePlayer.rotationYawHead - Flight.mc.thePlayer.rotationYaw) < 90.0f;
                    if ((Flight.mc.gameSettings.keyBindForward.pressed || Flight.mc.gameSettings.keyBindLeft.pressed || Flight.mc.gameSettings.keyBindRight.pressed || Flight.mc.gameSettings.keyBindBack.pressed) && Flight.mc.thePlayer.onGround) {
                        Flight.mc.thePlayer.motionY = 0.52f;
                        Flight.mc.timer.timerSpeed = 0.1f;
                        Flight.mc.thePlayer.setSprinting(true);
                        break;
                    }
                    Flight.mc.thePlayer.setSprinting(true);
                    Flight.mc.timer.timerSpeed = 1.0f;
                    if (Flight.mc.thePlayer.hurtTime == 0) break;
                    Flight.mc.timer.timerSpeed = ((CheckBox)Client.INSTANCE.moduleManager.getModule((String)"Longjump").getSetting((String)"Boost").getSetting()).state ? 4.0f : 1.0f;
                    Flight.mc.thePlayer.motionY += (double)0.06f;
                    Speed.setSpeed5(0.4);
                    break;
                }
                case "Redesky": {
                    double x3 = -Math.sin(yaw) * this.speed;
                    double z3 = Math.cos(yaw) * this.speed;
                    BlockPos pos = new BlockPos(Flight.mc.thePlayer.posX, Flight.mc.thePlayer.posY - 0.3, Flight.mc.thePlayer.posZ);
                    Block block = Flight.mc.theWorld.getBlockState(pos).getBlock();
                    if (block.getMaterial() != Material.air || Flight.mc.thePlayer.fallDistance == 0.0f) break;
                    Flight.mc.thePlayer.motionY = 0.0;
                    Flight.mc.gameSettings.keyBindForward.pressed = false;
                    Flight.mc.thePlayer.motionZ = 0.0;
                    Flight.mc.thePlayer.motionX = 0.0;
                    if (!this.time.hasReached(180L)) break;
                    this.speed = 1.0;
                    Flight.mc.thePlayer.setPosition(Flight.mc.thePlayer.posX + x3, Flight.mc.thePlayer.posY, Flight.mc.thePlayer.posZ + z3);
                    mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Flight.mc.thePlayer.posX + x3, Flight.mc.thePlayer.posY, Flight.mc.thePlayer.posZ + z3, false));
                    break;
                }
                case "LibreCraft": {
                    Flight.mc.thePlayer.motionY = Flight.mc.thePlayer.ticksExisted % 4 == 0 ? (double)0.08f : (double)-0.008f;
                    if (Flight.mc.gameSettings.keyBindSneak.pressed) {
                        Flight.mc.thePlayer.motionY = -1.0;
                    }
                    if (Flight.mc.gameSettings.keyBindJump.pressed) {
                        Flight.mc.thePlayer.motionY = 1.0;
                    }
                    Flight.mc.thePlayer.onGround = true;
                    if (!(Flight.mc.thePlayer.moveForward > 0.0f)) break;
                    Flight.mc.thePlayer.motionX = -Math.sin(yaw) * 9.0;
                    Flight.mc.thePlayer.motionZ = Math.cos(yaw) * 9.0;
                    break;
                }
                case "MCCentral": {
                    Flight.mc.thePlayer.motionY = 0.0;
                    Flight.mc.thePlayer.onGround = true;
                    if (!(Flight.mc.thePlayer.moveForward > 0.0f)) break;
                    Flight.mc.thePlayer.motionX = -Math.sin(yaw) * 1.3;
                    Flight.mc.thePlayer.motionZ = Math.cos(yaw) * 1.3;
                    break;
                }
                case "Rededark2": {
                    RededarkSpeed = ((Slider)this.getSetting((String)"RededarkSpeed").getSetting()).curValue;
                    if (!(Flight.mc.thePlayer.fallDistance > 0.05f)) break;
                    Flight.mc.thePlayer.motionY = 0.0;
                    if (Flight.mc.thePlayer.isMoving()) {
                        Flight.setSpeed(RededarkSpeed);
                        break;
                    }
                    Flight.mc.thePlayer.motionX = 0.0;
                    Flight.mc.thePlayer.motionZ = 0.0;
                }
            }
        }
    }

    public static void setSpeed(double speed) {
        boolean isMovingStraight;
        EntityPlayerSP player = Flight.mc.getMinecraft().thePlayer;
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

    public static float getSpeed() {
        return (float)Math.sqrt(Flight.mc.getMinecraft().thePlayer.motionX * Flight.mc.getMinecraft().thePlayer.motionX + Flight.mc.getMinecraft().thePlayer.motionZ * Flight.mc.getMinecraft().thePlayer.motionZ);
    }

    public static void sendPacketUnlogged(Packet<? extends INetHandler> packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
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
}

