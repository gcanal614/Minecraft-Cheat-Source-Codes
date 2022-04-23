/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.utils.movement;

import club.tifality.Tifality;
import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.player.UpdatePositionEvent;
import club.tifality.utils.Wrapper;
import club.tifality.utils.movement.MovementUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public final class PlayerInfoCache {
    private static double lastDist;
    private static double prevLastDist;
    private static double baseMoveSpeed;

    public static double getPrevLastDist() {
        return prevLastDist;
    }

    public static double getLastDist() {
        return lastDist;
    }

    public static double getBaseMoveSpeed() {
        return baseMoveSpeed;
    }

    public static double getFriction(double moveSpeed) {
        return MovementUtils.calculateFriction(moveSpeed, lastDist, baseMoveSpeed);
    }

    static {
        Tifality.getInstance().getEventBus().subscribe(new PlayerUpdatePositionSubscriber());
    }

    private static class PlayerUpdatePositionSubscriber {
        private PlayerUpdatePositionSubscriber() {
        }

        @Listener
        private void onUpdatePositionEvent(UpdatePositionEvent event) {
            if (event.isPre()) {
                baseMoveSpeed = MovementUtils.getBaseMoveSpeed();
                EntityPlayerSP player = Wrapper.getPlayer();
                double xDif = player.posX - player.lastTickPosX;
                double zDif = player.posZ - player.lastTickPosZ;
                prevLastDist = lastDist;
                lastDist = StrictMath.sqrt(xDif * xDif + zDif * zDif);
            }
        }
    }
}

