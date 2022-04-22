package com.zerosense.mods.impl.MOVEMENT;

import com.zerosense.Events.Event;
import com.zerosense.Events.impl.EventMotion;
import com.zerosense.Events.impl.EventMove;
import com.zerosense.Events.impl.EventPacket;
import com.zerosense.Events.impl.EventSendPacket;
import com.zerosense.Settings.ModeSetting;
import com.zerosense.Utils.*;
import com.zerosense.ZeroSense;
import com.zerosense.mods.Module;
import com.zerosense.mods.ModuleManager;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import org.lwjgl.input.Keyboard;

import java.security.Key;

import static com.zerosense.Events.Event.packet;
import static com.zerosense.Utils.PlayerUtil.mc;

public class Flight extends Module {


    Timer timer = new Timer();



    public static ModeSetting mode = new ModeSetting("Mode", "Vanilla", "VerusSlow", "Motion","Dev","OldVerus");

    public Flight() {
        super("Flight", Keyboard.KEY_F, Category.MOVEMENT);
        this.addSettings(mode);
    }
    double speed;


    @Override
    public void onEnable() {

        if ( mode.is("Verus") ) {
        mc.thePlayer.VAITOMANOCU();
        }
        }

    @Override
    public void onDisable() {
        this.mc.thePlayer.capabilities.isFlying = false;
        this.mc.timer.timerSpeed = 1.0F;
        mc.thePlayer.speedInAir = 0.02F;
        SpeedModifier.setSpeed(1F);

    }


    @Override
    public void onEvent(Event e) {
        if ( mode.is("Creative") ) {
            if ( mc.thePlayer.onGround ) {
                this.mc.thePlayer.jump();
                this.mc.thePlayer.capabilities.isFlying = true;
            }
        }
        if(mode.is("Dev")){
            
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition());
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook());
            mc.thePlayer.sendQueue.addToSendQueue(new C0FPacketConfirmTransaction());
            mc.thePlayer.motionY = 0;
            mc.thePlayer.onGround = true;
            SpeedModifier.setSpeed(5.0F);


        }
        EntityPlayerSP var10000;
        if(mode.is("OldVerus")){
            if (this.timer.hasTimeElapsed(500L, true)) {
                var10000 = this.mc.thePlayer;
                var10000.posY -= 0.20000000298023224D;
                this.mc.thePlayer.onGround = false;
                var10000 = this.mc.thePlayer;
                var10000.motionX *= 0.10000000149011612D;
                var10000 = this.mc.thePlayer;
                var10000.motionZ *= 0.10000000149011612D;
                mc.thePlayer.onGround = true;

            } else {
                mc.thePlayer.onGround = true;

                this.mc.thePlayer.motionY = 0.0D;
                this.mc.thePlayer.onGround = true;
            }
        }
        if ( mode.is("Vanilla") ) {

            if ( mc.thePlayer.isMoving() ) {

                mc.thePlayer.capabilities.isFlying = true;
                mc.thePlayer.speedInAir = 0.5f;

            } else {
                mc.thePlayer.motionY = 0;
                mc.thePlayer.motionX = 0;
                mc.thePlayer.motionZ = 0;

            }

        }
        if ( mode.is("VerusSlow") ) {

            mc.thePlayer.onGround = true;
            mc.thePlayer.motionY = 0;
           if(timer.hasTimeElapsed(1000, true)){
               SpeedModifier.setSpeed(15.0F);
           }

            if (mc.thePlayer.hurtTime > 0) {
                // Artemis.addClientChatMessage("d Dia thing");
                mc.thePlayer.motionX = 0;
                mc.thePlayer.motionZ = 0;


            }

    }



        if(mode.is("Motion")){





        }
    }
}




