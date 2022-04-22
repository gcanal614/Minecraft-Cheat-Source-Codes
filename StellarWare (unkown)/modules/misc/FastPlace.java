package stellar.skid.modules.misc;

import stellar.skid.gui.screen.setting.Manager;
import stellar.skid.gui.screen.setting.Setting;
import stellar.skid.gui.screen.setting.SettingType;
import stellar.skid.modules.AbstractModule;
import stellar.skid.modules.EnumModuleType;
import stellar.skid.modules.ModuleManager;
import stellar.skid.modules.configurations.annotation.Property;
import stellar.skid.modules.configurations.property.object.BooleanProperty;
import stellar.skid.modules.configurations.property.object.IntProperty;
import stellar.skid.modules.configurations.property.object.PropertyFactory;
import org.checkerframework.checker.nullness.qual.NonNull;

import static stellar.skid.modules.configurations.property.object.PropertyFactory.createInt;

public final class FastPlace extends AbstractModule {

    /* properties @off */
    @Property("place-delay")
    public final IntProperty placeDelay = createInt(3).minimum(1).maximum(4);
    @Property("blocks-only")
    public final BooleanProperty blocksOnly = PropertyFactory.booleanTrue();

    /* constructors @on */
    public FastPlace(@NonNull ModuleManager moduleManager) {
        super(moduleManager, "FastPlace", "Fast Place", EnumModuleType.MISC, "place blocks faster");
        Manager.put(new Setting("PLACE_DELAY", "Place Delay", SettingType.SLIDER, this, this.placeDelay, 1));
        Manager.put(new Setting("BLOCKS_ONLY", "Blocks Only", SettingType.CHECKBOX, this, this.blocksOnly));
    }
}
