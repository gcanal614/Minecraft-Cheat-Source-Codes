package stellar.skid.modules.player;

import stellar.skid.events.EventTarget;
import stellar.skid.events.events.MotionUpdateEvent;
import stellar.skid.gui.screen.setting.Manager;
import stellar.skid.gui.screen.setting.Setting;
import stellar.skid.gui.screen.setting.SettingType;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.configurations.annotation.Property;
import stellar.skid.modules.configurations.property.object.BooleanProperty;
import stellar.skid.modules.configurations.property.object.DoubleProperty;
import stellar.skid.utils.ServerUtils;
import stellar.skid.utils.Servers;
import stellar.skid.utils.Timer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import org.checkerframework.checker.nullness.qual.NonNull;
import viaversion.viafabric.ViaFabric;

import static stellar.skid.modules.configurations.property.object.PropertyFactory.booleanFalse;
import static stellar.skid.modules.configurations.property.object.PropertyFactory.createDouble;

public class AutoHead extends AbstractModule {

    private Timer timer = new Timer();

    @Property("health")
    private final DoubleProperty health = createDouble(14.0D).minimum(1.0D).maximum(20.0D);
    @Property("force-absorption")
    private final BooleanProperty force_absorption = booleanFalse();
    @Property("check-regen")
    private final BooleanProperty check_regen = booleanFalse();

    public AutoHead(@NonNull ModuleManager moduleManager) {
        super(moduleManager, EnumModuleType.COMBAT, "AutoHead", "Auto Head");
        Manager.put(new Setting("AH_FORCE_ABS", "Force absorption", SettingType.CHECKBOX, this, force_absorption));
        Manager.put(new Setting("AH_CHECK_REGEN", "Check Regen", SettingType.CHECKBOX, this, check_regen, () -> !force_absorption.get()));
        Manager.put(new Setting("AH_HEALTH", "Health", SettingType.SLIDER, this, health, 1.0D, () -> !force_absorption.get()));
    }

    public int headSlot() {
        return mc.player.getSlotByItem(Items.skull);
    }

    @EventTarget
    public void onMotion(MotionUpdateEvent event) {
        if (event.getState().equals(MotionUpdateEvent.State.POST)) {
            if (!ServerUtils.serverIs(Servers.LOBBY) && !ServerUtils.serverIs(Servers.PRE) && shouldUse(headSlot())) {
                ItemStack stack = mc.player.inventory.getStackInSlot(headSlot());

                if (stack != null && stack.getItem() instanceof ItemSkull) {
                    if (timer.delay(500)) {
                        sendPacketNoEvent(new C09PacketHeldItemChange(headSlot()));
                        sendPacketNoEvent(new C08PacketPlayerBlockPlacement(mc.player.getHeldItem()));
                        sendPacketNoEvent(new C09PacketHeldItemChange(mc.player.inventory.currentItem));

                        if (ViaFabric.clientSideVersion == stellarWare.viaVersion() && stack.stackSize == 1) {
                            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 36 + headSlot(), 2, 3, mc.player);
                        }

                        timer.reset();
                    }
                }
            }
        }
    }

    private boolean shouldUse(int slot) {
        return slot != -1 && (force_absorption.get() ? mc.player.getAbsorptionAmount() <= 0 : mc.player.getHealth() < health.get() && (!check_regen.get() || !mc.player.isPotionActive(Potion.regeneration)));
    }
}


