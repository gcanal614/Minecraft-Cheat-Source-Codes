package stellar.skid.modules.move;

import stellar.skid.events.EventTarget;
import stellar.skid.events.events.PlayerUpdateEvent;
import stellar.skid.events.events.StepConfirmEvent;
import stellar.skid.events.events.TickUpdateEvent;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.player.Phase;
import stellar.skid.utils.ServerUtils;
import stellar.skid.utils.Timer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import org.checkerframework.checker.nullness.qual.NonNull;

import static stellar.skid.modules.EnumModuleType.MOVEMENT;

public class Step extends AbstractModule {

    private boolean resetTimer;
    private Timer timer = new Timer();

    public Step(@NonNull ModuleManager moduleManager) {
        super(moduleManager, "Step", MOVEMENT, "");
    }

    @Override
    public void onEnable() {
        setSuffix("Watchdog");
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0F;
        mc.player.stepHeight = 0.625F;
    }

    @EventTarget
    public void onTick(TickUpdateEvent event) {
        setSuffix("Watchdog");
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent event) {
        //     if (timer.delay(400)) {
        mc.player.stepHeight = mc.player.isInLiquid() || mc.player.isOnLiquid() || isEnabled(Speed.class) ||
                isEnabled(Phase.class) || !mc.player.onGround ? 0.625F : 1.0F;
        //   }

        if (resetTimer) {
            mc.timer.timerSpeed = 1.0F;
            resetTimer = false;
        }
    }


    @EventTarget
    public void onStepConfirm(StepConfirmEvent event) {
        double diffY = mc.player.getEntityBoundingBox().minY - mc.player.posY;
        double posX = mc.player.posX, posY = mc.player.posY, posZ = mc.player.posZ;

        if (diffY > 0.625 && diffY <= 1.0) {
            if (ServerUtils.isHypixel()) {
                sendPacketNoEvent(new C0BPacketEntityAction(mc.player, C0BPacketEntityAction.Action.START_SNEAKING));
                mc.player.setSprinting(false);
                mc.player.setSpeed(0);
            }

            mc.timer.timerSpeed = 0.6f;
            resetTimer = true;

            double first = 0.41999998688698, second = 0.7531999805212;

            if (diffY != 1) {
                first *= diffY;
                second *= diffY;

                if (first > 0.425) {
                    first = 0.425;
                }

                if (second > 0.78) {
                    second = 0.78;
                } else if (second < 0.49) {
                    second = 0.49;
                }
            }

            sendPacket(new C04PacketPlayerPosition(posX, posY + first, posZ, false));
            sendPacket(new C04PacketPlayerPosition(posX, posY + second, posZ, false));

            if (ServerUtils.isHypixel()) {
                sendPacketNoEvent(new C0BPacketEntityAction(mc.player, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }

            mc.player.stepHeight = 0.625f;
        }
    }
}
