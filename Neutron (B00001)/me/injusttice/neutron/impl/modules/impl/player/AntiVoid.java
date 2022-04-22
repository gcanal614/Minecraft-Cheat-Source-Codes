package me.injusttice.neutron.impl.modules.impl.player;

import java.util.ArrayList;

import me.injusttice.neutron.NeutronMain;
import me.injusttice.neutron.api.events.EventTarget;
import me.injusttice.neutron.api.events.impl.EventMotion;
import me.injusttice.neutron.api.events.impl.EventReceivePacket;
import me.injusttice.neutron.api.events.impl.EventSendPacket;
import me.injusttice.neutron.impl.modules.Category;
import me.injusttice.neutron.impl.modules.Module;
import me.injusttice.neutron.api.settings.ModuleCategory;
import me.injusttice.neutron.api.settings.impl.BooleanSet;
import me.injusttice.neutron.api.settings.impl.DoubleSet;
import me.injusttice.neutron.api.settings.impl.ModeSet;
import me.injusttice.neutron.utils.movement.MoveUtils;
import me.injusttice.neutron.utils.movement.MovementUtils;
import me.injusttice.neutron.utils.network.PacketUtil;
import me.injusttice.neutron.utils.player.TimeHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class AntiVoid extends Module {
    public ModeSet modeSet = new ModeSet("Watchdog", "Teleport", new String[] { "Packet", "Teleport", "Verus", "Watchdog" });
    public double[] lastGroundPos = new double[3];
    public static TimeHelper timer = new TimeHelper();
    public static ArrayList<C03PacketPlayer> packets = new ArrayList<>();

    public DoubleSet dist = new DoubleSet("Distance", 5.0D, 1.0D, 10.0D, 1.0D);

    public ModuleCategory verusCategory = new ModuleCategory("Verus...");

    public BooleanSet verus_float = new BooleanSet("Float", false);

    public BooleanSet verus_prevent = new BooleanSet("Prevent Damage", true);

    public DoubleSet verus_motion = new DoubleSet("Motion", 2.0D, 1.0D, 3.0D, 0.05D);

    public BooleanSet verus_boost = new BooleanSet("Boost", true);

    public DoubleSet verus_boost_factor = new DoubleSet("Boost Factor", 3.0D, 1.0D, 10.0D, 0.1D);

    public ModuleCategory packetCategory = new ModuleCategory("Packet...");

    public DoubleSet packet_offSet = new DoubleSet("Offset", 3.0D, 1.0D, 10.0D, 0.1D);

    public DoubleSet packet_ammount = new DoubleSet("Ammount", 1.0D, 1.0D, 10.0D, 1.0D);

    public BooleanSet packet_ground = new BooleanSet("Ground", false);

    private double xGround;

    private double yGround;

    private double zGround;

    private boolean isCancelling;

    private boolean shouldCancel;

    private int ticksSinceTP;

    private ArrayList<Packet> cancelPackets;

    public AntiVoid() {
        super("AntiVoid", 0, Category.PLAYER);
        xGround = 0.0D;
        yGround = 0.0D;
        zGround = 0.0D;
        isCancelling = false;
        shouldCancel = false;
        ticksSinceTP = 0;
        cancelPackets = new ArrayList<>();
        addSettings(modeSet, dist, verusCategory, packetCategory);
        packetCategory.addCatSettings(packet_offSet, packet_ammount, packet_ground);
        verusCategory.addCatSettings(verus_float, verus_prevent, verus_motion, verus_boost, verus_boost_factor );
    }

    @EventTarget
    public void onPre(EventMotion e) {
        this.setDisplayName("Anti Void");
        switch (modeSet.getMode()) {
            case "Verus":
                ticksSinceTP++;
                if (ticksSinceTP < 15)
                    return;
                if (mc.thePlayer.onGround && MovementUtils.getOnRealGround((EntityLivingBase)mc.thePlayer, 1.0E-4D)) {
                    xGround = mc.thePlayer.posX;
                    yGround = mc.thePlayer.posY;
                    zGround = mc.thePlayer.posZ;
                    break;
                }
                if (MovementUtils.isOverVoid()) {
                    double coso = yGround - dist.getValue();
                    if (coso > localPlayer.posY) {
                        e.setOnGround(true);
                        if (verus_boost.isEnabled())
                            MovementUtils.setSpeed1(
                                    (float)(MovementUtils.getSpeed() * verus_boost_factor.getValue()));
                        if (verus_float.isEnabled()) {
                            localPlayer.motionY = 0.0D;
                            if (localPlayer.hurtTime > 0)
                                localPlayer.motionY = verus_motion.getValue();
                            break;
                        }
                        localPlayer.motionY = verus_motion.getValue();
                        break;
                    }
                    if (coso < localPlayer.posY - 3.35D && verus_prevent.isEnabled() &&
                            localPlayer.fallDistance > 2.9D) {
                        e.setOnGround(true);
                        localPlayer.fallDistance = 0.0F;
                    }
                }
                break;
            case "Teleport":
                if (mc.thePlayer.onGround && MovementUtils.getOnRealGround(mc.thePlayer, 1.0E-4D)) {
                    xGround = mc.thePlayer.posX;
                    yGround = mc.thePlayer.posY;
                    zGround = mc.thePlayer.posZ;
                    break;
                }
                if (shouldLagback())
                    localPlayer.setPosition(xGround, yGround, zGround);
                break;
            case "Packet":
                if (shouldLagback())
                    for (int a = 0; a < packet_ammount.getValue(); a++)
                        PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(localPlayer.posX, localPlayer.posY + packet_offSet.getValue(), localPlayer.posZ, packet_ground.isEnabled()));
                break;
            case "Watchdog":
                break;
        }
    }

    @EventTarget
    public void onGet(EventReceivePacket e) {
        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            ticksSinceTP = 0;
        }
        switch (modeSet.getMode()) {
            case "Watchdog":
                if (e.getPacket() instanceof S08PacketPlayerPosLook && packets.size() > 1) {
                    packets.clear();
                }
                break;
        }
    }

    @EventTarget
    public void onSend(EventSendPacket e) {
        boolean scaffoldToggled;
        switch (modeSet.getMode()) {
            case "Test":
                scaffoldToggled = NeutronMain.instance.moduleManager.getModuleByName("Scaffold").isToggled();
                if (scaffoldToggled)
                    return;
                if (e.getPacket() instanceof C03PacketPlayer) {
                    if (localPlayer.onGround) {
                        xGround = localPlayer.posX;
                        yGround = localPlayer.posY;
                        zGround = localPlayer.posZ;
                        shouldCancel = true;
                        if (isCancelling) {
                            for (Packet p : cancelPackets)
                                PacketUtil.sendPacketSilent(p);
                            cancelPackets.clear();
                            isCancelling = false;
                        }
                    }
                    if (MovementUtils.isOverVoid()) {
                        if (localPlayer.fallDistance < dist.getValue() && !localPlayer.onGround) {
                            e.setCancelled(true);
                            cancelPackets.add(e.getPacket());
                            isCancelling = true;
                            break;
                        }
                        if (shouldCancel) {
                            cancelPackets.clear();
                            isCancelling = true;
                            localPlayer.setPosition(xGround, yGround, zGround);
                            shouldCancel = false;
                        }
                    }
                }
                break;
            case "Watchdog":
                if (!packets.isEmpty() && mc.thePlayer.ticksExisted < 100)
                    packets.clear();

                if (e.getPacket() instanceof C03PacketPlayer) {
                    C03PacketPlayer packet = ((C03PacketPlayer) e.getPacket());
                    if (shouldLagback()) {
                        e.setCancelled(true);
                        packets.add(packet);

                        if (timer.hasReached(2000)) {
                            mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(lastGroundPos[0], lastGroundPos[1] - 1, lastGroundPos[2], true));
                        }
                    } else {
                        lastGroundPos[0] = mc.thePlayer.posX;
                        lastGroundPos[1] = mc.thePlayer.posY;
                        lastGroundPos[2] = mc.thePlayer.posZ;

                        if (!packets.isEmpty()) {
                            for (C03PacketPlayer p : packets)
                                mc.getNetHandler().getNetworkManager().sendPacketNoEvent(p);
                            packets.clear();
                        }
                        timer.reset();
                    }
                }
                break;
        }
    }

    public boolean shouldLagback() {
        return (localPlayer.fallDistance > dist.getValue() && MovementUtils.isOverVoid());
    }
}
