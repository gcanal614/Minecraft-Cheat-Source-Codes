package stellar.skid.modules.move;

import stellar.skid.events.EventTarget;
import stellar.skid.events.events.EventPostUpdate;
import stellar.skid.events.events.MotionUpdateEvent;
import stellar.skid.events.events.SlowdownEvent;
import stellar.skid.events.events.TickUpdateEvent;
import stellar.skid.gui.screen.setting.Manager;
import stellar.skid.gui.screen.setting.Setting;
import stellar.skid.gui.screen.setting.SettingType;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.combat.KillAura;
import stellar.skid.modules.configurations.annotation.Property;
import stellar.skid.modules.configurations.property.object.BooleanProperty;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.checkerframework.checker.nullness.qual.NonNull;
import stellar.skid.modules.configurations.property.object.PropertyFactory;
import stellar.skid.modules.configurations.property.object.StringProperty;

import static stellar.skid.modules.configurations.property.object.PropertyFactory.booleanFalse;

public final class NoSlow extends AbstractModule {

    /* properties @off */
    @Property("vanilla")
    private final BooleanProperty vanilla = booleanFalse();

    @Property("Noslow-Mode")
    private final StringProperty NoslowMode = PropertyFactory.createString("Vanilla").acceptableValues("NCP", "Vanilla");

    /* constructors @on */
    public NoSlow(@NonNull ModuleManager moduleManager) {
        super(moduleManager, "NoSlow", "No Slow", EnumModuleType.MOVEMENT, "No slow down when using items");
        Manager.put(new Setting("Noslow-Mode", "Noslow Mode", SettingType.COMBOBOX, this, NoslowMode));
    }

    @EventTarget
    public void onTick(TickUpdateEvent event) {
        setSuffix(vanilla.get() ? "Vanilla" : "NCP"  );
    }

    /* events */
    @EventTarget
    public void onBlock(MotionUpdateEvent event) {

        if(NoslowMode.get().equalsIgnoreCase("NCP")){

        if (mc.player.getHeldItem() != null && mc.player.getHeldItem().getItem() instanceof ItemSword
                    && mc.gameSettings.keyBindUseItem.isKeyDown()) {
            if (event.getState().equals(MotionUpdateEvent.State.PRE)) {
                sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            } else {
                sendPacket(new C08PacketPlayerBlockPlacement(mc.player.getHeldItem()));
            }
        }
        }
    }



    @EventTarget
    public void onSlowDown(SlowdownEvent event) {
        event.setCancelled(true);
    }


    @Override
    public void onEnable() {
        setSuffix(NoslowMode.get());
    }
}
