package non.asset.module.impl.Combat;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import non.asset.event.bus.Handler;
import non.asset.event.impl.game.PacketEvent;
import non.asset.event.impl.player.UpdateEvent;
import non.asset.module.Module;
import non.asset.utils.OFC.MoveUtil;
import non.asset.utils.OFC.Printer;
import non.asset.utils.value.impl.EnumValue;
import non.asset.utils.value.impl.NumberValue;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Velocity extends Module {
    private NumberValue<Integer> horizontalModifier = new NumberValue<>("Horizontal", 0, 0, 100, 1);
    private NumberValue<Integer> verticalModifier = new NumberValue<>("Vertical", 0, 0, 100, 1);
    private EnumValue<mode> Mode = new EnumValue<>("Mode", mode.NORMAL);

    public Velocity() {
        super("Velocity", Category.COMBAT);
        setRenderLabel("Velocity");
    }

    private enum mode {
        NORMAL, AAC
    }

    @Handler
    public void onPacket(PacketEvent event) {
        final int vertical = verticalModifier.getValue();
        final int horizontal = horizontalModifier.getValue();
        if (getMc().thePlayer == null || getMc().theWorld == null) return;
        switch (Mode.getValue()) {
            case NORMAL:
            if (!event.isSending()) {
                if (event.getPacket() instanceof S12PacketEntityVelocity) {
                    S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();
                    if (packet.getEntityID() == getMc().thePlayer.getEntityId()) {
                        if (packet.getEntityID() != getMc().thePlayer.getEntityId()) return;
                        if (vertical != 0 || horizontal != 0) {
                            packet.setMotionX(horizontal * packet.getMotionX() / 100);
                            packet.setMotionY(vertical * packet.getMotionY() / 100);
                            packet.setMotionZ(horizontal * packet.getMotionZ() / 100);
                        } else event.setCanceled(true);
                    }
                }
                if (event.getPacket() instanceof S27PacketExplosion) {
                    S27PacketExplosion packet = (S27PacketExplosion) event.getPacket();
                    if (vertical != 0 || horizontal != 0) {
                        packet.setField_149152_f(horizontal * packet.getField_149152_f() / 100);
                        packet.setField_149153_g(vertical * packet.getField_149153_g() / 100);
                        packet.setField_149159_h(horizontal * packet.getField_149159_h() / 100);
                    } else event.setCanceled(true);
                }
            }
            break;
            case AAC:
                if (!event.isSending()) {
                    if (event.getPacket() instanceof S12PacketEntityVelocity) {
                        S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();
                        if (packet.getEntityID() != getMc().thePlayer.getEntityId()) return;
                        //packet.setMotionX(120 * packet.getMotionX() / 100);
                        //packet.setMotionY(100 * packet.getMotionY() / 100);
                        //packet.setMotionZ(120 * packet.getMotionZ() / 100);
                    }
                }
                break;
        }
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
        switch (Mode.getValue()) {
            case NORMAL:
                break;
            case AAC:
                if (event.isPre()) {
                    if (getMc().thePlayer.hurtResistantTime > 13 && getMc().thePlayer.hurtResistantTime < 20 && !getMc().thePlayer.onGround) {
                        if (getMc().thePlayer.hurtResistantTime == 19) {
                            getMc().thePlayer.motionX *= 0.85;
                            getMc().thePlayer.motionZ *= 0.85;
                        }
                        else {
                            //getMc().thePlayer.motionY -= 0.01499;
                            getMc().thePlayer.onGround = true;
                            double tick = Math.max(0, 16 - getMc().thePlayer.hurtResistantTime);
                            //MoveUtil.setSpeed(MoveUtil.getSpeed() * (0.1 + tick / 20));
                            double speed = Math.min(0.05 + (tick * 0.01), 0.3125);
                           // MoveUtil.setSpeed(speed);
                           // Printer.print("" + speed);
                            //MoveUtil.setSpeed(0.3);
                            //MoveUtil.setSpeed(MoveUtil.getSpeed() * 0.6);
                            // getMc().thePlayer.onGround = true;
                        }
                    }
                }
                break;
        }
    }
}
