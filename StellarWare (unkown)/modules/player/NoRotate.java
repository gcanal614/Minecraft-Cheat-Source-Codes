package stellar.skid.modules.player;

import stellar.skid.events.EventTarget;
import stellar.skid.events.events.PacketEvent;
import stellar.skid.gui.screen.setting.Manager;
import stellar.skid.gui.screen.setting.Setting;
import stellar.skid.gui.screen.setting.SettingType;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.configurations.annotation.Property;
import stellar.skid.modules.configurations.property.object.BooleanProperty;
import stellar.skid.modules.configurations.property.object.PropertyFactory;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.checkerframework.checker.nullness.qual.NonNull;

import static stellar.skid.modules.EnumModuleType.PLAYER;

public final class NoRotate extends AbstractModule {

    @Property("ground-check")
    private final BooleanProperty ground_check = PropertyFactory.booleanTrue();

    /* constructors */
    public NoRotate(@NonNull ModuleManager moduleManager) {
        super(moduleManager, "NoRotate", "No Rotate", PLAYER, "Blocks server-sided rotate packets to prevent your head from rotating on flag");
        Manager.put(new Setting("GROUND_CHECK", "On Ground", SettingType.CHECKBOX, this, ground_check));
    }

    /* events */
    @EventTarget
    public void onReceive(PacketEvent e) {
        if (e.getState().equals(PacketEvent.State.INCOMING)) {
            if (e.getPacket() instanceof S08PacketPlayerPosLook) {
                if (mc.player.onGround || !ground_check.get()) {
                    S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) e.getPacket();

                    packet.setPitch(mc.player.rotationPitch);
                    packet.setYaw(mc.player.rotationYaw);
                }
            }
        }
    }
}
