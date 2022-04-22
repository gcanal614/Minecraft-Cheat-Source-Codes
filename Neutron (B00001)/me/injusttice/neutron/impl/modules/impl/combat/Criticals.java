package me.injusttice.neutron.impl.modules.impl.combat;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventAttack;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.api.events.impl.EventSendPacket;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.movement.MovementUtils;
import me.injusttice.neutron.utils.network.PacketUtil;
import me.injusttice.neutron.utils.player.Timer;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Criticals extends Module {
    
    public ModeSet mode = new ModeSet("Mode", "Watchdog", "Watchdog", "Packet", "Morgan", "Spartan", "NCP", "Verus", "NoGround");
    public DoubleSet hurttime = new DoubleSet("HurtTime", 5.0D, 1.0D, 10.0D, 1.0D);
    private double[] watchdogOffsets;
    int safeTicks;
    Timer timer;

    public Criticals() {
        super("Criticals", 0, Category.COMBAT);
        addSettings(mode, hurttime);
        watchdogOffsets = new double[] { 0.0560000017285347, 0.01600000075995922, 0.003000000026077032 };
        timer = new Timer();
        safeTicks = 0;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (mode.getMode().equalsIgnoreCase("NoGround"))
            mc.thePlayer.jump();
    }

    @EventTarget
    public void onSend(EventSendPacket e) {
        if (e.getPacket() instanceof C03PacketPlayer && mode.getMode().equalsIgnoreCase("NoGround")) {
            C03PacketPlayer c = (C03PacketPlayer)e.getPacket();
            c.onGround = false;
        }
    }

    @EventTarget
    public void onPre(EventMotion e) {
        setDisplayName("Criticals §7" + mode.getMode() + " " + Math.round(hurttime.getValue()));
        safeTicks++;
    }

    @EventTarget
    public void onAttack(EventAttack e) {
        EntityLivingBase ent = (EntityLivingBase)e.getEntity();
        double x = mc.thePlayer.posX, y = mc.thePlayer.posY, z = mc.thePlayer.posZ;
        if ((ent.hurtTime < hurttime.getValue() || ent.hurtTime == 0) &&
                shouldCrit())
            switch (mode.getMode()) {
                case "NCP":
                    PacketUtil.sendC04(x, y + 0.11D, z, false, false);
                    PacketUtil.sendC04(x, y, z, false, false);
                    break;
                case "Spartan":
                    PacketUtil.sendC04(x, y + 0.2D, z, false, false);
                    PacketUtil.sendC04(x, y, z, false, false);
                    break;
                case "Morgan":
                    PacketUtil.sendC04(x, y + 0.00124D + ThreadLocalRandom.current().nextDouble(1.0E-4D, 9.0E-4D), z, false, false);
                    PacketUtil.sendC04(x, y + 8.5E-4D, z, false, false);
                    break;
                case "Packet":
                    PacketUtil.sendC04(x, y + 5.1E-4D, z, false, false);
                    PacketUtil.sendC04(x, y + 8.4E-4D, z, false, false);
                    PacketUtil.sendC04(x, y, z, false, false);
                    break;
                case "Verus":
                    if (ent.hurtTime == 0) {
                        if (safeTicks > 4) {
                            PacketUtil.sendC04(x, y + 0.42D, z, false, false);
                            PacketUtil.sendC04(x, y, z, false, false);
                            safeTicks = 0;
                        }
                        break;
                    }
                    PacketUtil.sendC04(x, y + 0.42D, z, false, false);
                    PacketUtil.sendC04(x, y, z, false, false);
                    break;
                case "Watchdog":
                    if (!mc.thePlayer.onGround || mc.thePlayer.isOnLadder() || mc.thePlayer.isInWeb || mc.thePlayer.isInWater() || mc.thePlayer.isInLava() || mc.thePlayer.ridingEntity != null) {
                        return;
                    }
                    if(timer.hasTimeElapsed(500, true)) {
                        for (double offset : watchdogOffsets) {
                            if (MovementUtils.isOnGround() && KillAura.target != null) {
                                PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset + Math.random() * 3.000000142492354E-4, mc.thePlayer.posZ, false));
                                mc.thePlayer.onCriticalHit(KillAura.target);
                            }
                        }
                    }
                    break;
            }
    }

    public boolean shouldCrit() {
        boolean isRealGround = (mc.thePlayer.onGround && MovementUtils.getOnRealGround((EntityLivingBase)mc.thePlayer, 1.0E-4D) && mc.thePlayer.isCollidedVertically);
        return (isRealGround && !mc.thePlayer.isInWater() && !mc.thePlayer.isOnLadder());
    }
}
