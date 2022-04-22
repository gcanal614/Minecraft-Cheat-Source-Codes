package stellar.skid.modules.player;

import stellar.skid.events.EventTarget;
import stellar.skid.events.events.LoadWorldEvent;
import stellar.skid.events.events.PacketEvent;
import stellar.skid.gui.screen.setting.Manager;
import stellar.skid.gui.screen.setting.Setting;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.configurations.annotation.Property;
import stellar.skid.modules.configurations.property.object.FloatProperty;
import stellar.skid.modules.configurations.property.object.PropertyFactory;
import stellar.skid.modules.move.Scaffold;
import stellar.skid.utils.Timer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static stellar.skid.gui.screen.setting.SettingType.SLIDER;
import static stellar.skid.modules.EnumModuleType.PLAYER;

public final class AntiVoid extends AbstractModule {

    private double x, y, z;
    private Timer pullTimer = new Timer();
    private List<Packet> packets = new CopyOnWriteArrayList();

    @Property("pull-ms")
    private FloatProperty pull_milliseconds = PropertyFactory.createFloat(1000.0F).minimum(1000.0F).maximum(3000.0F);

    public AntiVoid(@NonNull ModuleManager moduleManager) {
        super(moduleManager, "AntiVoid", "Anti Void", PLAYER, "do not fall retard");
        Manager.put(new Setting("AV_PULLMS", "Pull MS", SLIDER, this, pull_milliseconds, 100));
    }

    @EventTarget
    public void onPacket(PacketEvent event) {
        if (event.getState() == PacketEvent.State.OUTGOING) {
            if (event.getPacket() instanceof C03PacketPlayer) {
                C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();

                if (packet.isOnGround()) {
                    x = packet.getX();
                    y = packet.getY();
                    z = packet.getZ();
                    pullTimer.reset();

                } else if (!isEnabled(Scaffold.class) && !mc.player.isBlockUnder()) {
                    event.setCancelled(true);
                    packets.add(event.getPacket());
                    if (pullTimer.delay(pull_milliseconds.get()) && mc.player.motionY < 0) {
                        mc.player.setPosition(x, y, z);
                        packets.clear();
                    }
                }

                if (!isEnabled(Scaffold.class) && (packet.isOnGround() || mc.player.isBlockUnder())) {
                    if (!packets.isEmpty()) {
                        packets.forEach(this::sendPacketNoEvent);
                        packets.clear();
                    }
                }
            }
        }
    }

    @EventTarget
    public void onLoad(LoadWorldEvent event) {
        pullTimer.reset();
    }
}
