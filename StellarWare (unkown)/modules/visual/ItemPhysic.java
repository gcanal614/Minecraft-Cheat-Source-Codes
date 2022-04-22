package stellar.skid.modules.visual;

import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.modules.ModuleManager;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.lwjgl.input.Keyboard;

public final class ItemPhysic extends AbstractModule {

    /* constructors */
    public ItemPhysic(@NonNull ModuleManager moduleManager) {
        super(moduleManager, "ItemPhysic", "Item Physic", Keyboard.KEY_NONE, EnumModuleType.VISUALS,
                "Real items physic");
    }

}
