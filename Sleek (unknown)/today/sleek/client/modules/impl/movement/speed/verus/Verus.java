package today.sleek.client.modules.impl.movement.speed.verus;

import net.minecraft.potion.Potion;
import today.sleek.base.event.impl.MoveEvent;
import today.sleek.client.modules.impl.movement.speed.SpeedMode;
import today.sleek.client.utils.player.PlayerUtil;

public class Verus extends SpeedMode {

    public Verus() {
        super("Verus");
    }

    @Override
    public void onMove(MoveEvent event) {
        if (mc.thePlayer.isMoving()) {
            float sped2 = (float) (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.365 : 0.355);
            if (mc.thePlayer.onGround) {
                event.setMotionY(mc.thePlayer.motionY = PlayerUtil.getMotion(0.42f));
            }

            if (mc.thePlayer.hurtTime >= 1) {
                sped2 = getSpeed().getSpeed().getValue().floatValue();
            }

            PlayerUtil.setMotion(event, sped2);
        }
    }
}
