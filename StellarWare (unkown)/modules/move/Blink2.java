package stellar.skid.modules.move;

import net.minecraft.network.Packet;
import stellar.skid.events.EventTarget;
import stellar.skid.events.events.PacketDirection;
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
import java.util.ArrayList;
import java.util.function.Consumer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public final class Blink2 extends AbstractModule {
    private EntityOtherPlayerMP blinkEntity;
    private final ArrayList<Packet> packetList = new ArrayList();
    @Property("lagback-check")
    private final BooleanProperty lagback_check = PropertyFactory.booleanFalse();
    private static int z;

    public Blink2(ModuleManager moduleManager) {
        super(moduleManager, "Blink", EnumModuleType.EXPLOITS, "there was a cringe description but I removed it -gast");
        Manager.put(new Setting("BLINK_LB", "Lagback check", SettingType.CHECKBOX, this, this.lagback_check));
    }

    public void onEnable() {
        this.checkModule(Freecam.class);
        this.blinkEntity = new EntityOtherPlayerMP(this.mc.world, this.mc.player.getGameProfile());
        this.blinkEntity.copyLocationAndAnglesFrom(this.mc.player);
        this.blinkEntity.setRotationYawHead(this.mc.player.rotationYawHead);
        this.blinkEntity.inventory = this.mc.player.inventory;
        this.mc.world.addEntityToWorld(this.blinkEntity.getEntityID(), this.blinkEntity);
    }

    public void onDisable() {
        a();
        mc.world.removeEntityFromWorld(blinkEntity.getEntityID());

        if (!packetList.isEmpty()) {
            packetList.forEach(this::sendPacket);
            packetList.clear();
        }
    }

    @EventTarget
    private void onPacketSend(PacketEvent var1) {
        int var2 = a();
        if (var1.getState().equals(PacketEvent.State.OUTGOING)) {
            if(var1.getPacket() instanceof C03PacketPlayer) {
                C03PacketPlayer var3 = (C03PacketPlayer)var1.getPacket();
                if(var3.isMoving()) {
                    this.packetList.add(var1.getPacket());
                    var1.setCancelled(true);
                }
            }

            if(var1.getPacket() instanceof C0APacketAnimation || var1.getPacket() instanceof C02PacketUseEntity || var1.getPacket() instanceof C08PacketPlayerBlockPlacement) {
                var1.setCancelled(true);
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
        return this.blinkEntity;
    }

    public static void b(int var0) {
        z = var0;
    }

    public static int a() {
        return z;
    }

    public static int b() {
        int var0 = a();
        return 105;
    }

    static {
        b(69);
    }
}
