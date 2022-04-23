/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.movement;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.property.Property;
import club.tifality.utils.Rotation;
import club.tifality.utils.RotationUtils;
import club.tifality.utils.movement.MovementUtils;
import net.minecraft.potion.Potion;

@ModuleInfo(label="Sprint", category=ModuleCategory.MOVEMENT)
public final class Sprint
extends Module {
    public final Property<Boolean> allDirectionsValue = new Property<Boolean>("Omni", true);
    public final Property<Boolean> blindnessValue = new Property<Boolean>("Blindness", true);
    public final Property<Boolean> foodValue = new Property<Boolean>("Food", true);
    public final Property<Boolean> checkServerSide = new Property<Boolean>("CheckServerSide", false);
    public final Property<Boolean> checkServerSideGround = new Property<Boolean>("CheckServerSideOnlyGround", false);

    @Listener
    public void onUpdate(UpdatePositionEvent event) {
        block5: {
            block4: {
                if (!MovementUtils.isMoving() || Sprint.mc.thePlayer.isSneaking() || this.blindnessValue.get().booleanValue() && Sprint.mc.thePlayer.isPotionActive(Potion.blindness) || this.foodValue.get().booleanValue() && !((float)Sprint.mc.thePlayer.getFoodStats().getFoodLevel() > 6.0f) && !Sprint.mc.thePlayer.capabilities.allowFlying) break block4;
                if (!this.checkServerSide.get().booleanValue() || !Sprint.mc.thePlayer.onGround && this.checkServerSideGround.get().booleanValue() || this.allDirectionsValue.get().booleanValue() || RotationUtils.targetRotation == null) break block5;
                Rotation rotation = new Rotation(Sprint.mc.thePlayer.rotationYaw, Sprint.mc.thePlayer.rotationPitch);
                if (!(RotationUtils.getRotationDifference(rotation) > 30.0)) break block5;
            }
            Sprint.mc.thePlayer.setSprinting(false);
            return;
        }
        if (this.allDirectionsValue.get().booleanValue() || Sprint.mc.thePlayer.movementInput.moveForward >= 0.8f) {
            Sprint.mc.thePlayer.setSprinting(true);
        }
    }
}

