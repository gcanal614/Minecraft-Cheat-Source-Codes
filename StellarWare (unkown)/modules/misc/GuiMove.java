package stellar.skid.modules.misc;

import stellar.skid.gui.screen.setting.Manager;
import stellar.skid.gui.screen.setting.Setting;
import stellar.skid.gui.screen.setting.SettingType;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.configurations.annotation.Property;
import stellar.skid.modules.configurations.property.object.BooleanProperty;
import stellar.skid.modules.configurations.property.object.PropertyFactory;
import net.minecraft.util.MovementInput;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.lwjgl.input.Keyboard;

public final class GuiMove extends AbstractModule {

    @Property("sneak")
    private final BooleanProperty sneak = PropertyFactory.createBoolean(false);

    /* constructors */
    public GuiMove(@NonNull ModuleManager moduleManager) {
        super(moduleManager, "GuiMove", "Gui Move", EnumModuleType.MISC, "Allows you to walk with an opened GUI");
        Manager.put(new Setting("GM_SNEAK", "Sneak", SettingType.CHECKBOX, this, sneak));
    }

    public void updatePlayerMoveState() {
        MovementInput movementInput = mc.player.movementInput();

        movementInput.setMoveStrafe(0.0F);
        movementInput.setMoveForward(0.0F);

        if (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
            movementInput.setMoveForward(movementInput.getMoveForward() + 1);
        }

        if (Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
            movementInput.setMoveForward(movementInput.getMoveForward() - 1);
        }

        if (Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode())) {
            movementInput.setMoveStrafe(movementInput.getMoveStrafe() + 1);
        }

        if (Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) {
            movementInput.setMoveStrafe(movementInput.getMoveStrafe() - 1);
        }

        movementInput.setJump(Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()));
        movementInput.setSneak(sneak.get() && Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) || mc.gameSettings.keyBindSneak.isKeyDown());
    }
}
