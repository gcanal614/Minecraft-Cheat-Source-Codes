package stellar.skid.modules.misc;

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
import stellar.skid.modules.configurations.property.object.PropertyFactory;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.lwjgl.input.Keyboard;

public final class AutoTool extends AbstractModule {

    @Property("switch-back")
    private BooleanProperty switch_back = PropertyFactory.booleanTrue();

    private int oldSlot;
    private int tick;

    /* constructors */
    public AutoTool(@NonNull ModuleManager moduleManager) {
        super(moduleManager, "AutoTool", "Auto Tool", Keyboard.KEY_NONE, EnumModuleType.MISC, "Switches to the best tool");
        Manager.put(new Setting("AT_SWITCH_BACK", "Switch Back", SettingType.CHECKBOX, this, switch_back));
    }

    /* methods */
    @EventTarget
    public void onPre(MotionUpdateEvent event) {
        if (event.getState().equals(MotionUpdateEvent.State.PRE)) {
            if (mc.playerController.isBreakingBlock()) {
                tick++;

                if (tick == 1) {
                    oldSlot = mc.player.inventory.currentItem;
                }

                mc.player.updateTool(mc.objectMouseOver.getBlockPos());
            } else if (tick > 0) {
                if (switch_back.get()) {
                    mc.player.inventory.currentItem = oldSlot;
                }

                tick = 0;
            }
        }
    }
}