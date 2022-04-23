/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.movement;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.api.annotations.Priority;
import club.tifality.manager.event.impl.player.MoveEntityEvent;
import club.tifality.manager.event.impl.player.StepEvent;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.Property;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.EnumProperty;
import club.tifality.utils.Wrapper;
import club.tifality.utils.movement.MovementUtils;
import club.tifality.utils.timer.TimerUtil;
import net.minecraft.client.entity.EntityPlayerSP;

@ModuleInfo(label="Fly", category=ModuleCategory.MOVEMENT)
public final class Flight
extends Module {
    private final EnumProperty<FlightMode> flightModeProperty = new EnumProperty<FlightMode>("Mode", FlightMode.MOTION);
    private final Property<Boolean> viewBobbingProperty = new Property<Boolean>("Bobbing", true);
    private final DoubleProperty motionSpeedProperty = new DoubleProperty("Motion Speed", 2.0, () -> this.flightModeProperty.getValue() == FlightMode.MOTION, 0.1, 5.0, 0.1);
    private final TimerUtil timer = new TimerUtil();

    public Flight() {
        this.setSuffixListener(this.flightModeProperty);
    }

    @Override
    public void onEnable() {
        this.timer.reset();
    }

    @Override
    public void onDisable() {
        Wrapper.getTimer().timerSpeed = 1.0f;
    }

    @Listener
    public void onStepEvent(StepEvent e) {
        e.setStepHeight(0.0f);
    }

    @Listener(value=Priority.HIGH)
    private void onUpdatePositionEvent(UpdatePositionEvent e) {
        if (e.isPre()) {
            EntityPlayerSP player = Wrapper.getPlayer();
            if (this.viewBobbingProperty.getValue().booleanValue()) {
                player.cameraYaw = 0.105f;
            }
            if (this.flightModeProperty.getValue() == FlightMode.MOTION) {
                if (Wrapper.getGameSettings().keyBindJump.isKeyDown()) {
                    player.motionY = 1.0;
                } else if (Wrapper.getGameSettings().keyBindSneak.isKeyDown()) {
                    player.motionY = -1.0;
                } else {
                    Wrapper.getPlayer().motionY = 0.0;
                }
            }
        }
    }

    @Listener(value=Priority.HIGH)
    private void onMoveEntityEvent(MoveEntityEvent e) {
        if (this.flightModeProperty.getValue() == FlightMode.MOTION && MovementUtils.isMoving()) {
            MovementUtils.setSpeed(e, (Double)this.motionSpeedProperty.getValue());
        }
    }

    private static enum FlightMode {
        MOTION;

    }
}

