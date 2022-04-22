package com.zerosense.mods.impl.MOVEMENT;

import com.zerosense.Events.Event;
import com.zerosense.Events.impl.EventMotion;
import com.zerosense.Events.impl.EventMove;
import com.zerosense.Events.impl.MoveUtil;
import com.zerosense.Settings.ModeSetting;
import com.zerosense.Settings.NumberSetting;
import com.zerosense.Utils.MoveEvent;
import com.zerosense.Utils.MoveUtils;
import com.zerosense.Utils.MovementUtil;
import com.zerosense.Utils.block.BlockUtils;
import com.zerosense.mods.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import org.lwjgl.input.Keyboard;
import com.zerosense.Utils.Timer;

import java.util.List;

public class Speed extends Module {

    Timer timer = new Timer();
    private double nextMotionSpeed;
    private double xMotionSpeed;
    private double zDist;
    private double moveSpeed;
    int stage;
    int ncpStage;
    boolean wasGrounded, lastDistanceReset;
    boolean gameType;
    double speed;
    double lastDist;
    double speedFactor;
    float airSpeed2;
    public boolean reset, doSlow;
    public double speedonground3;

    public NumberSetting airSpeed = new NumberSetting("AirSpeed", 0.01D, 1.00D, 0.01D, 0.02D);
    public ModeSetting mode = new ModeSetting("Mode", "Vanilla", "DEV", "Test", "Legit", "Redesky");

    public Speed() {
        super("Speed", Keyboard.KEY_X, Category.MOVEMENT);
        this.addSettings(mode, airSpeed);
    }

    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0F;
        mc.thePlayer.setSprinting(false);
        mc.thePlayer.speedInAir = 0.02F;

    }

    public void onEnable() {
        if (mc.thePlayer.isMoving()) {
            if (mode.is("DEV")) {

            }
        }
    }


    @Override
    public void onEvent(Event e) {
        if (e instanceof EventMotion) {
            EventMotion event = (EventMotion) e;
            if (mode.is("Legit")) {
                // code by me ( i am bad pro coder)

                if (mc.thePlayer.onGround && mc.thePlayer.isMoving()) {
                    mc.thePlayer.jump();
                    MovementUtil.setMotion(0.500);
                    mc.thePlayer.speedInAir = 0.03F;


                }

                if (mode.is("Vanilla")) {

                    if (mc.thePlayer.onGround && mc.thePlayer.isMoving()) {
                        mc.thePlayer.jump();
                        MovementUtil.setMotion(0.500);
                        mc.thePlayer.speedInAir = (float) airSpeed.getValue();


                    }
                }
                if (mode.is("Redesky")) {
                    if (mc.thePlayer.isMoving() && mc.thePlayer.onGround) {
                        mc.thePlayer.motionY = 0.48;
                        mc.thePlayer.speedInAir = 0.023F;
                    }
                }
            }
        }
    }
}









