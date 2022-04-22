package me.injusttice.neutron.impl.modules.impl.movement;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.*;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.movement.MovementUtils;
import me.injusttice.neutron.utils.player.Timer;
import me.injusttice.neutron.utils.player.ValueUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.play.client.*;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.input.Keyboard;

public class Flight extends Module {

    Timer timer = new Timer();
    public boolean doneBow, damaged
            = false;
    public double movementSpeed;
    public boolean spoofGround;
    private int verusStage;
    private int i;
    private int state = 0;
    private double launchY;
    int damagedTicks = 0;
    int ticks = 0;

    public ModeSet mode = new ModeSet("Mode", "Vanilla", "Vanilla", "Creative", "Air Collide", "Funcraft", "Verus Bow", "Verus Float", "Verus Damage", "Collide", "Latest Verus","Hypixel");
    public ModeSet acms = new ModeSet("AirCollide Mode", "Slow", "Fast", "Slow");
    public DoubleSet flightSpeed = new DoubleSet("Flight Speed", 1, 0.1, 20, 0.01D);
    public BooleanSet viewBobbing = new BooleanSet("View Bobbing", false);
    public BooleanSet antiKick = new BooleanSet("Anti Kick", false);

    public Flight(){
        super("Flight", Keyboard.KEY_F, Category.MOVEMENT);
        addSettings(mode, acms, flightSpeed, viewBobbing, antiKick);
    }

    @EventTarget
    public void onMove(EventMove e) {
        switch (mode.getMode()) {
            case "Verus Bow":
                if (mc.thePlayer.hurtTime > 0 && !this.damaged) {
                    this.damaged = true;
                    mc.timer.timerSpeed = 1.0f;
                }
                if (!this.damaged) {
                    e.setX(0.0);
                    e.setZ(0.0);
                }
                break;
            case "Verus Float":
                if (mc.thePlayer.onGround) {
                    movementSpeed = 0.992;
                    e.setY(0.4499999868869782);
                    spoofGround = true;
                    verusStage = 0;
                } else if (verusStage <= 4) {
                    movementSpeed += 0.05;
                    e.setY(0.0);
                } else {
                    movementSpeed = 0.34;
                    spoofGround = false;
                }
                ++verusStage;
                mc.thePlayer.motionY = e.getY();
                MovementUtils.setSpeed1(movementSpeed - 1.0E-4);
                break;
        }
    }

    @EventTarget
    public void onBB(EventCollide event) {
        if (mc.thePlayer == null) return;

        switch(mode.getMode()) {
            case "Verus Float":
            case "Latest Verus":
                if(event.getBlock() instanceof BlockAir && event.getPosY() <= launchY) {
                    event.setBoundingBox(AxisAlignedBB.fromBounds(event.getPosX(), event.getPosY(), event.getPosZ(), event.getPosX() + 1, launchY, event.getPosZ() + 1));
                }
                break;
            case "Collide":
            case "Air Collide":
                if(mc.thePlayer.isSneaking()) return;
                if (event.getBlock() instanceof BlockAir && event.getPosY() < mc.thePlayer.posY) {
                    event.setBoundingBox(AxisAlignedBB.fromBounds(event.getPosX(), event.getPosY(), event.getPosZ(), event.getPosX() + 1, mc.thePlayer.posY, event.getPosZ() + 1));
                }
                break;
        }
    }

    @EventTarget
    public void onSend(EventSendPacket event) {
        switch (mode.getMode()) {
            case "Verus Float":
                if (event.getPacket() instanceof C03PacketPlayer && mc.thePlayer.ticksExisted > 15) {
                    C03PacketPlayer c03 = (C03PacketPlayer) event.getPacket();
                    event.setCancelled((mc.thePlayer.ticksExisted % 80 == 0));
                    c03.onGround = true;
                }
                break;
            case "Collide":
                if(event.getPacket() instanceof C03PacketPlayer) {
                    C03PacketPlayer c03 = (C03PacketPlayer) event.getPacket();
                    c03.onGround = true;
                }
                break;
        }
    }

    @EventTarget
    public void onMotion2(EventPreMotion e){
        switch (mode.getMode()) {
        }
    }

    @EventTarget
    public void onMotion(EventMotionUpdate e) {
        if (viewBobbing.isEnabled()) {
            mc.thePlayer.cameraYaw = 0.105F;
        }
        setDisplayName("Flight §7" + mode.getMode());
        switch (mode.getMode()) {

            case "Verus Bow":
                mc.thePlayer.motionY = 0.0;
                mc.thePlayer.onGround = true;
                mc.thePlayer.fallDistance = 0.0f;
                mc.thePlayer.isCollidedVertically = true;
                e.setOnGround(true);
                MovementUtils.setMotion(flightSpeed.getValue());
                break;
            case "Verus Damage":
                mc.thePlayer.motionY = 0.0;
                mc.thePlayer.onGround = true;
                mc.thePlayer.fallDistance = 0.0f;
                mc.thePlayer.isCollidedVertically = true;

                e.setOnGround(true);
                if (this.i > 0) {
                    MovementUtils.setMotion(flightSpeed.getValue());
                }
                --this.i;
                break;
            case "Verus Float":
                mc.getNetHandler().getNetworkManager().sendPacket(new C0CPacketInput());
                e.setOnGround(mc.thePlayer.onGround || spoofGround);
                break;
            case "Air Collide":
                switch(acms.getMode()) {
                    case "Slow":
                        if (mc.thePlayer.isMoving() && mc.thePlayer.onGround) {
                            if (mc.thePlayer.ticksExisted % 2 == 0) {
                                mc.thePlayer.setSpeed(0.50F);
                                e.setOnGround(true);
                                break;
                            }
                        }
                        break;
                    case "Fast":
                        if (mc.thePlayer.isMoving() && mc.thePlayer.onGround) {
                            if (mc.thePlayer.ticksExisted % 2 == 0) {
                                mc.thePlayer.setSpeed((float) flightSpeed.getValue());
                                e.setOnGround(true);
                                break;
                            }
                        }
                        break;
                }
                break;
            case "Collide":
                break;
            case "Latest Verus":
                if (mc.thePlayer.onGround && MovementUtils.isMoving()) {
                    mc.thePlayer.jump();
                    MovementUtils.setSpeed1(0.48F);
                } else {
                    MovementUtils.setSpeed1(MovementUtils.getSpeed());
                }
                break;
            case "Vanilla":
                mc.thePlayer.motionY = 0;

                if (!MovementUtils.isMoving()) {
                    MovementUtils.setMotion(0);
                } else {
                    MovementUtils.setSpeed1(flightSpeed.getValue());
                }
                if (mc.gameSettings.keyBindJump.pressed) {
                    mc.thePlayer.motionY = flightSpeed.getValue() * 0.5;
                }
                if (mc.gameSettings.keyBindSneak.pressed) {
                    mc.thePlayer.motionY = -flightSpeed.getValue() * 0.5;
                }

                e.setOnGround(true);
                break;
            case "Creative":
                mc.thePlayer.capabilities.isFlying = true;
                break;
            case "Hypixel":
                ticks ++;
                mc.timer.timerSpeed = .8f;
                if(state == 2 || ticks >= 11) {
                    mc.thePlayer.motionY = 0;
                    mc.thePlayer.onGround = true;
                    mc.timer.timerSpeed = 1.15f;
                }
                if (state != 0 ) {
                    return;
                }
                if(ticks == 1) {
                    mc.thePlayer.motionY = 0.39547834756;
                }
                if (ticks == 12) {
                    mc.timer.timerSpeed = .8f;
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.39547834756, mc.thePlayer.posZ, false));//mc.thePlayer.onGround = false;

                }
                if (ticks == 13) {
                    mc.timer.timerSpeed = .8f;
                    mc.thePlayer.posY = -0.4057689;
                }
                if (ticks != 14) {
                    return;
                }
                mc.thePlayer.motionY = -0.4057689;
                state = 1;
                break;
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if(mc.thePlayer == null) return;

        switch(mode.getMode()) {
            case "Verus Damage":
                if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, 3.0001, 0.0).expand(0.0, 0.0, 0.0)).isEmpty()) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.0001, mc.thePlayer.posZ, false));
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                }
                mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.42, mc.thePlayer.posZ);
                break;
            case "Verus Bow":
                if(mc.thePlayer.onGround && damaged) {
                    if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, 3.0001, 0.0).expand(0.0, 0.0, 0.0)).isEmpty()) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 3.0001, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
                    }
                    mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.42, mc.thePlayer.posZ);
                }
                break;
            case "Collide":
                mc.thePlayer.motionY = 0;
                break;
        }


        damaged = false;
        i = 20;
        verusStage = 0;
        movementSpeed = 0.0;
        ticks = 0;
        damagedTicks = 0;
        launchY = mc.thePlayer.posY;
        timer.reset();
    }

    @Override
    public void onDisable(){
        super.onDisable();
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionZ = 0;
        resetCapabilities();
        ticks = 0;
        state = 0;
        doneBow = false;
    }

    public boolean isGround() {
        return (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically && MovementUtils.getOnRealGround(mc.thePlayer, 1.0E-4D));
    }

    public static void resetCapabilities() {
        (Minecraft.getMinecraft()).gameSettings.keyBindJump.pressed = GameSettings.isKeyDown((Minecraft.getMinecraft()).gameSettings.keyBindJump);
        (Minecraft.getMinecraft()).thePlayer.stepHeight = 0.625F;
        (Minecraft.getMinecraft()).timer.timerSpeed = 1.0F;
        (Minecraft.getMinecraft()).thePlayer.isCollided = false;
        if(Minecraft.getMinecraft().thePlayer.isSpectator()) return;
        (Minecraft.getMinecraft()).thePlayer.capabilities.isFlying = false;
        (Minecraft.getMinecraft()).thePlayer.capabilities.allowFlying = (Minecraft.getMinecraft()).playerController.isInCreativeMode();
        (Minecraft.getMinecraft()).thePlayer.capabilities.isCreativeMode = (Minecraft.getMinecraft()).playerController.isInCreativeMode();
    }
}
