package me.injusttice.neutron.impl.modules.impl.movement;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventNigger;
import me.injusttice.neutron.api.events.impl.EventPreMotionUpdate;
import me.injusttice.neutron.api.events.impl.UpdateEvent;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.movement.MovementUtils;
import me.injusttice.neutron.api.events.impl.EventReceivePacket;
import me.injusttice.neutron.utils.movement.RotationUtils;
import me.injusttice.neutron.utils.player.ValueUtil;
import me.injusttice.neutron.utils.server.ServerUtil;
import me.injusttice.neutron.utils.server.ServerUtls;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;

public class Speed extends Module {

    public ModeSet mode = new ModeSet("Mode", "Vanilla Hop", "Vanilla Hop", "Vanilla Ground", "Hypixel", "HypixelLow", "Verus Hop");
    public BooleanSet newHypixel = new BooleanSet("Watchdog Hypixel",false);
    public BooleanSet watchdogTimerBoost = new BooleanSet("Watchdog Timer Boost",false);
    public DoubleSet speed1 = new DoubleSet("Speed", 2, 1, 10, 0.1D);
    public BooleanSet lagback = new BooleanSet("Lagback Check", false);
    public static boolean isTargetStrafing;
    boolean prevOnGround = false;
    private boolean wasOnGround, doSlow;
    private double moveSpeed;
    double lastDist;
    float ticks = 0.1F;

    private boolean shouldBoost, expBoost;
    private double baseSpeed, boostSpeed;

    public Speed() {
        super("Speed", 0, Category.MOVEMENT);
        addSettings(mode, newHypixel, watchdogTimerBoost, speed1, lagback);
        lastDist = 0.0;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        isTargetStrafing = false;
        if (mc.thePlayer != null) {
            moveSpeed = MovementUtils.getSpeed();
        }
        doSlow = false;
        if(mode.is("Ncp Fast")) {
            ticks = 0.0F;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        isTargetStrafing = false;
        lastDist = 0.0;
        doSlow = false;
        mc.timer.timerSpeed = 1f;
        mc.thePlayer.stepHeight = 0.625f;
        if(mode.is("Ncp Fast")) {
            //ticks = 0.2F;
            mc.thePlayer.setSpeed(0);
        }
    }

    @EventTarget
    public void onReceive(EventReceivePacket e) {
        if(lagback.isEnabled()) {
            if (e.getPacket() instanceof S08PacketPlayerPosLook) {
                NeutronMain.addChatMessage("Disabled Speed Due To A LagBack!");
                toggle();
            }
        }
    }

    @EventTarget
    public void onEvent(EventNigger e) {
        if (e instanceof UpdateEvent) {
            if (e.isPre()) {
                setDisplayName("Speed §7" + mode.getMode());
                switch (mode.getMode()) {
                    case "Vanilla Hop":
                        if (MovementUtils.isMoving() && mc.thePlayer.onGround) {
                            mc.thePlayer.jump();
                        } else {
                            if (!MovementUtils.isMoving()) {
                                MovementUtils.setMotion(0);
                            }
                        }
                        MovementUtils.setSpeed1(MovementUtils.getBaseMoveSpeed() * speed1.getValue());
                        break;
                    case "Vanilla Ground":
                        MovementUtils.setSpeed1(MovementUtils.getBaseMoveSpeed() * speed1.getValue());
                        if (!MovementUtils.isMoving()) {
                            MovementUtils.setMotion(0);
                        }
                        break;
                    case "Verus Hop":
                        if (mc.thePlayer.onGround && MovementUtils.isMoving()) {
                            mc.thePlayer.motionY = .25f;
                        } else {
                            if (!MovementUtils.isMoving()) {
                                MovementUtils.setMotion(0);
                            }
                        }
                        if(mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                            MovementUtils.setSpeed1(0.35);
                        } else {
                            MovementUtils.setSpeed1(0.32);
                        }
                        break;
                    case "Hypixel":
                        if(mc.thePlayer.isMoving() && !mc.thePlayer.isCollidedHorizontally) {
                            if(mc.thePlayer.onGround) {
                                mc.thePlayer.jump();
                                mc.thePlayer.motionY = 0.42F;
                                mc.timer.timerSpeed = 1.05F;
                                if(!mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                                    mc.thePlayer.setSpeed((float) (0.4+ticks));
                                } else {
                                    if(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 0) {
                                        mc.thePlayer.setSpeed((float) 0.45+ticks);
                                    }
                                    if(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 1) {
                                        mc.thePlayer.setSpeed((float) 0.55+ticks);
                                    }
                                    if(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 2) {
                                        mc.thePlayer.setSpeed((float) 0.65+ticks);
                                    }
                                }
                                prevOnGround = true;
                            } else {
                                if(prevOnGround) {
                                    prevOnGround = false;
                                    if(ticks < 0.035) {
                                        ticks += 0.04F;
                                    }
                                }
                                if(ticks > 0.035) {
                                    ticks -= 0.001F;
                                }
                                mc.thePlayer.jumpMovementFactor = 0.0265F;
                                mc.timer.timerSpeed = 1.05F;
                                double direction = mc.thePlayer.getDirection();
                                double speed = 1.0;
                                if(mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                                    if(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 0) {
                                        speed = 1.0 + (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.015 : 0);
                                    }
                                    if(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 1) {
                                        speed = 1.0 + (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.025 : 0);
                                    }
                                    if(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 2) {
                                        speed = 1.0 + (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.03 : 0);
                                    }
                                }

                                double currentMotion = Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);

                                mc.thePlayer.setSpeed((float) (currentMotion * speed));

                            }
                        } else {
                            ticks = 0;
                            mc.thePlayer.setSpeed(0);
                        }
                        break;
                    case "HypixelLow":
                        if(mc.thePlayer != null && mc.theWorld != null) {
                            if(mc.thePlayer.isMoving()) {
                                mc.timer.timerSpeed = 1.05F;
                                if(mc.thePlayer.onGround) {
                                    mc.thePlayer.jump();
                                    if(mc.thePlayer.isCollidedHorizontally) {
                                        mc.thePlayer.jump();
                                    } else {
                                        mc.thePlayer.motionY = 0.31F;
                                    }
                                    if(!mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                                        mc.thePlayer.setSpeed((float) 0.4);
                                    } else {
                                        if(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 0) {
                                            mc.thePlayer.setSpeed((float) 0.45);
                                        }
                                        if(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 1) {
                                            mc.thePlayer.setSpeed((float) 0.55);
                                        }
                                        if(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() == 2) {
                                            mc.thePlayer.setSpeed((float) 0.7);
                                        }
                                    }
                                } else {
                                    mc.thePlayer.setSpeed((float) mc.thePlayer.getCurrentMotion());
                                    //mc.thePlayer.motionY = -0.1F;
                                }
                            } else {
                                mc.thePlayer.setSpeed(0);
                            }
                        }
                        break;
                }
            }
        }
    }


    public void setSpeed(double speed) {
        speed = (float) speed;
        mc.thePlayer.motionX = (-(Math.sin(getDirection()) * speed));
        mc.thePlayer.motionZ = (Math.cos(getDirection()) * speed);
    }

    public double getDirection() {
        float rotationYaw = mc.thePlayer.rotationYaw;

        if (mc.thePlayer.moveForward < 0F)
            rotationYaw += 180F;

        float forward = 1F;
        if (mc.thePlayer.moveForward < 0F)
            forward = -0.5F;
        else if (mc.thePlayer.moveForward > 0F)
            forward = 0.5F;

        if (mc.thePlayer.moveStrafing > 0F)
            rotationYaw -= 90F * forward;

        if (mc.thePlayer.moveStrafing < 0F)
            rotationYaw += 90F * forward;

        return Math.toRadians(rotationYaw);
    }

    public static double getMotion() {
        Minecraft localMinecraft = Minecraft.getMinecraft();
        double d1 = localMinecraft.thePlayer.motionX;
        double d2 = localMinecraft.thePlayer.motionZ;
        return Math.sqrt(d1 * d1 + d2 * d2);
    }
}
