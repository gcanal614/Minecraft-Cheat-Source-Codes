package me.injusttice.neutron.impl.modules.impl.combat;

import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.api.events.impl.EventSendPacket;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.movement.MovementUtils;
import me.injusttice.neutron.utils.network.PacketUtil;
import me.injusttice.neutron.utils.player.Timer;
import me.injusttice.neutron.utils.world.RandomUtil;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Criticals2 extends Module {
    
    public ModeSet mode = new ModeSet("Mode", "Watchdog", "Watchdog", "Packet", "Ground", "NoGround");
    public ModeSet watchdogMode = new ModeSet("Watchdog Mode", "Packet 1", "Packet 1", "Packet 2", "Packet 3", "Packet 4");
    private double[] watchdogOffsets;
    private double[] ncpOffsets;
    private int groundTicks;
    public DoubleSet delay = new DoubleSet("Delay", 500, 0, 2000, 10);
    Timer timer;
    
    public Criticals2() {
        super("Criticals2", 0, Category.COMBAT);
        timer = new Timer();
        ncpOffsets = new double[] { 0.06251999735832214, 0.0 };
        watchdogOffsets = new double[] { 0.0560000017285347, 0.01600000075995922, 0.003000000026077032 };
        addSettings(mode, watchdogMode, delay);
    }
    
    @EventTarget
    public void onUpdate() {
        if (mode.is("Watchdog")) {
            setDisplayName("Criticals Watchdog" + watchdogMode.getMode());
        } else {
            setDisplayName("Criticals " + mode.getMode());
        }
    }
    
    @EventTarget
    public void onMotion(EventMotion e) {
        if (mode.is("NoGround") && mc.thePlayer.fallDistance <= 3.0f) {
            e.setOnGround(false);
        }
    }
    
    @EventTarget
    public void onSend(EventSendPacket e) {
        if(e.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer c03PacketPlayer = (C03PacketPlayer) e.getPacket();
            if (mode.is("Ground")) {
                if (mc.thePlayer.fallDistance > 0.0f) {
                    c03PacketPlayer.onGround = true;
                }
                if (mc.thePlayer.onGround && KillAura.target != null && e.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition || e.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
                    c03PacketPlayer.onGround = false;
                    mc.thePlayer.onCriticalHit(KillAura.target);
                }
            }
        }
        
        if(e.getPacket() instanceof C0APacketAnimation) {
            if (mode.is("Packet") && timer.hasTimeElapsed((long)delay.getValue(), true) && MovementUtils.isOnGround() && KillAura.target != null) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.1625, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 4.0E-6, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-6, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
                mc.thePlayer.onCriticalHit(KillAura.target);
            }
            if (mode.is("Watchdog")) {
                if (!mc.thePlayer.onGround || mc.thePlayer.isOnLadder() || mc.thePlayer.isInWeb || mc.thePlayer.isInWater() || mc.thePlayer.isInLava() || mc.thePlayer.ridingEntity != null) {
                    return;
                }
                if (watchdogMode.is("Packet 1") && timer.hasTimeElapsed((long)delay.getValue(), true)) {
                    for (double offset : watchdogOffsets) {
                        if (MovementUtils.isOnGround() && KillAura.target != null) {
                            PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset + Math.random() * 3.000000142492354E-4, mc.thePlayer.posZ, false));
                            mc.thePlayer.onCriticalHit(KillAura.target);
                        }
                    }
                }
                if (watchdogMode.is("Packet 2") && timer.hasTimeElapsed((long)delay.getValue(), true)) {
                    for (double offset : watchdogOffsets) {
                        if (MovementUtils.isOnGround() && KillAura.target != null) {
                            PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset + 0.045, mc.thePlayer.posZ, false));
                            mc.thePlayer.onCriticalHit(KillAura.target);
                        }
                    }
                }
                if (watchdogMode.is("Packet 3")) {
                    double random = RandomUtil.getRandom(4.0E-7, 4.0E-5);
                    if (timer.hasTimeElapsed((long)delay.getValue(), true)) {
                        double[] array;
                        double[] arrayOfDouble = array = new double[] { 0.007017625 + random, 0.007349825 + random, 0.006102874 + random };
                        for (double value : array) {
                            if (MovementUtils.isOnGround() && KillAura.target != null) {
                                PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + value, mc.thePlayer.posZ, false));
                                mc.thePlayer.onCriticalHit(KillAura.target);
                            }
                        }
                    }
                }
                if (watchdogMode.is("Packet 4") && timer.hasTimeElapsed((long)delay.getValue(), true) && MovementUtils.isOnGround() && KillAura.target != null) {
                    for (int i = 0; i <= 2; ++i) {
                        PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.101 - i * 0.02, mc.thePlayer.posZ, false));
                        mc.thePlayer.onCriticalHit(KillAura.target);
                    }
                }
            }
        }
    }
}
