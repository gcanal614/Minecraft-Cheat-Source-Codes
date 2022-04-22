package stellar.skid.modules.move;

import stellar.skid.events.EventTarget;
import stellar.skid.events.events.PacketEvent;
import stellar.skid.gui.screen.setting.Manager;
import stellar.skid.gui.screen.setting.Setting;
import stellar.skid.gui.screen.setting.SettingType;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.configurations.annotation.Property;
import stellar.skid.modules.configurations.property.object.BooleanProperty;
import stellar.skid.modules.configurations.property.object.PropertyFactory;
import stellar.skid.modules.player.Freecam;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public final class Blink extends AbstractModule {

    /* fields */
    private EntityOtherPlayerMP blinkEntity;
    private final ArrayList<Packet> packetList = new ArrayList();

    @Property("lagback-check")
    private final BooleanProperty lagback_check = PropertyFactory.booleanFalse();

    /* constructors */
    public Blink(@NonNull ModuleManager moduleManager) {
        super(moduleManager, "Blink", EnumModuleType.MOVEMENT, "there was a cringe description but I removed it -gast");
        Manager.put(new Setting("BLINK_LB", "Lagback check", SettingType.CHECKBOX, this, lagback_check));
    }

    /* methods */
    @Override
    public void onEnable() {
        checkModule(Freecam.class);
        blinkEntity = new EntityOtherPlayerMP(mc.world, mc.player.getGameProfile());
        blinkEntity.copyLocationAndAnglesFrom(mc.player);
        blinkEntity.setRotationYawHead(mc.player.rotationYawHead);
        mc.world.addEntityToWorld(blinkEntity.getEntityID(), blinkEntity);
    }

    @Override
    public void onDisable() {
        mc.world.removeEntityFromWorld(blinkEntity.getEntityID());

        if (!packetList.isEmpty()) {
            packetList.forEach(this::sendPacket);
            packetList.clear();
        }
    }

    @EventTarget
    private void onPacketSend(PacketEvent event) {
        if (event.getState().equals(PacketEvent.State.OUTGOING)) {
            if (event.getPacket() instanceof C03PacketPlayer) {
                C03PacketPlayer packet = (C03PacketPlayer) event.getPacket();

                if (packet.isMoving()) {
                    packetList.add(event.getPacket());
                    event.setCancelled(true);
                }
            }

            if (event.getPacket() instanceof C0APacketAnimation
                    || event.getPacket() instanceof C02PacketUseEntity
                    || event.getPacket() instanceof C08PacketPlayerBlockPlacement) {
                event.setCancelled(true);
            }
        }
    }

    @EventTarget
    public void onReceive(PacketEvent event) {
        if (event.getState().equals(PacketEvent.State.INCOMING)) {
            if (lagback_check.get()) {
                if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                    checkModule(getClass());
                }
            }
        }
    }

    public EntityOtherPlayerMP getBlinkEntity() {
        return blinkEntity;
    }

    //todo disabler, step, EventMovementInput
}
