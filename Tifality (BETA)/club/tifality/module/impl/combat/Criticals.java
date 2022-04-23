/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.module.impl.combat;

import club.tifality.manager.api.annotations.Listener;
import club.tifality.manager.event.impl.packet.PacketSendEvent;
import club.tifality.manager.event.impl.player.AttackEvent;
import club.tifality.module.Module;
import club.tifality.module.ModuleCategory;
import club.tifality.module.ModuleInfo;
import club.tifality.module.ModuleManager;
import club.tifality.module.impl.movement.Flight;
import club.tifality.property.impl.DoubleProperty;
import club.tifality.property.impl.EnumProperty;
import club.tifality.property.impl.Representation;
import club.tifality.utils.RandomUtils;
import club.tifality.utils.timer.TimerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(label="Criticals", category=ModuleCategory.COMBAT)
public final class Criticals
extends Module {
    private final DoubleProperty delayProperty = new DoubleProperty("Delay", 500.0, () -> this.criticalsModeProperty.getValue() != CriticalsMode.GROUND, 0.0, 1000.0, 10.0, Representation.MILLISECONDS);
    private final DoubleProperty hurtTimeProperty = new DoubleProperty("Hurt Time", 10.0, () -> this.criticalsModeProperty.getValue() != CriticalsMode.GROUND, 0.0, 20.0, 1.0);
    public final EnumProperty<CriticalsMode> criticalsModeProperty = new EnumProperty<CriticalsMode>("Mode", CriticalsMode.WATCHDOG);
    private final TimerUtil timer = new TimerUtil();

    @Listener
    public void onAttackEvent(AttackEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            EntityPlayer target = (EntityPlayer)event.getEntity();
            if (!Criticals.mc.thePlayer.onGround || Criticals.mc.thePlayer.isOnLadder() || Criticals.mc.thePlayer.isInWeb || Criticals.mc.thePlayer.isInWater() || Criticals.mc.thePlayer.isInLava() || Criticals.mc.thePlayer.ridingEntity != null || (double)target.hurtTime > (Double)this.hurtTimeProperty.get() || !this.timer.hasElapsed(((Double)this.delayProperty.get()).longValue()) || ModuleManager.getInstance(Flight.class).isEnabled()) {
                return;
            }
            if (this.criticalsModeProperty.get() == CriticalsMode.WATCHDOG) {
                double[] arrayOfDouble;
                double random = RandomUtils.getRandom(4.0E-7, 4.0E-5);
                for (double value : arrayOfDouble = new double[]{0.007017625 + random, 0.007349825 + random, 0.006102874 + random}) {
                    mc.getNetHandler().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + value, Criticals.mc.thePlayer.posZ, false));
                }
                Criticals.mc.thePlayer.onCriticalHit(target);
            }
            if (this.criticalsModeProperty.get() == CriticalsMode.TACO) {
                for (int i = 0; i <= 2; ++i) {
                    mc.getNetHandler().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + 0.101 - (double)i * 0.02, Criticals.mc.thePlayer.posZ, false));
                }
            }
        }
    }

    @Listener
    public void onPacketSendEvent(PacketSendEvent event) {
        if (this.criticalsModeProperty.get() == CriticalsMode.GROUND && event.getPacket() instanceof C03PacketPlayer) {
            ((C03PacketPlayer)event.getPacket()).onGround = false;
        }
    }

    public Criticals() {
        this.setSuffixListener(this.criticalsModeProperty);
    }

    @Override
    public void onEnable() {
        this.timer.reset();
        if (this.criticalsModeProperty.get() == CriticalsMode.GROUND) {
            Criticals.mc.thePlayer.jump();
        }
    }

    public static enum CriticalsMode {
        TACO(new double[]{0.0}),
        WATCHDOG(new double[]{0.0}),
        GROUND(new double[]{0.0});

        private final double[] offsets;

        private CriticalsMode(double[] offsets) {
            this.offsets = offsets;
        }
    }
}

